/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package de.dante.extex.interpreter.context.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.dante.extex.font.FontFactory;
import de.dante.extex.font.NullFont;
import de.dante.extex.hyphenation.HyphenationManager;
import de.dante.extex.hyphenation.HyphenationTable;
import de.dante.extex.hyphenation.impl.HyphenationManagerImpl;
import de.dante.extex.i18n.GeneralHelpingException;
import de.dante.extex.interpreter.Code;
import de.dante.extex.interpreter.Conditional;
import de.dante.extex.interpreter.Interaction;
import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.Tokenizer;
import de.dante.extex.interpreter.context.Color;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.context.Direction;
import de.dante.extex.interpreter.context.TypesettingContext;
import de.dante.extex.interpreter.context.TypesettingContextFactory;
import de.dante.extex.interpreter.type.Box;
import de.dante.extex.interpreter.type.Count;
import de.dante.extex.interpreter.type.Dimen;
import de.dante.extex.interpreter.type.Font;
import de.dante.extex.interpreter.type.Glue;
import de.dante.extex.interpreter.type.InFile;
import de.dante.extex.interpreter.type.Muskip;
import de.dante.extex.interpreter.type.OutFile;
import de.dante.extex.interpreter.type.Tokens;
import de.dante.extex.scanner.ActiveCharacterToken;
import de.dante.extex.scanner.Catcode;
import de.dante.extex.scanner.ControlSequenceToken;
import de.dante.extex.scanner.Token;
import de.dante.extex.scanner.TokenFactory;
import de.dante.extex.scanner.TokenFactoryImpl;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;
import de.dante.util.Locator;
import de.dante.util.UnicodeChar;
import de.dante.util.configuration.Configuration;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.configuration.ConfigurationMissingException;
import de.dante.util.observer.NotObservableException;
import de.dante.util.observer.Observable;
import de.dante.util.observer.Observer;
import de.dante.util.observer.ObserverList;

/**
 * This is a reference implementation for an interpreter context.
 *
 * The groups are implemented as a linked list of single groups. In contrast to
 * the Knuthian implementation in TeX no undo stack is used.
 * <p>
 * Several operations have to be dealt with:
 * </p>
 * <ul>
 * <li>For each new group a new instance of a {@link Group Group}is created
 * with the old one as next group.</li>
 * <li>If a group is closed then the next group is used as current group and
 * the formerly current group is discarted.</li>
 * <li>If a value has to be found in a group then the next chain has to be
 * traced down until the value is found. <br />An implementation variant might
 * want to insert the value found into the higher groups; all or some of them
 * to speed up the next access. This optimization is currently not implemented.
 * </li>
 * <li>If a local value has to be stored then it can be stored in the local
 * group only.</li>
 * <li>If a global value has to be stored then the group chain has to be
 * traversed and the value has to be set in all approrpiate groups: There are
 * several implementation variants
 * <ul>
 * <li>Clear the value in all groups and set it in the bottommost group.</li>
 * <li>Set the value in all groups where it has a local value.</li>
 * <li>Set the value in all groups.</li>
 * </ul>
 * Here the third approach is used which is suspected to be a little more
 * efficient on the cost of slightly more memory consumption.</li>
 * </ul>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class ContextImpl implements Context, Observable, Serializable {

    /**
     * The constant <tt>TYPESETTING_CONTEXT_TAG</tt> contains the name of the
     * configuration tag for the typesetting context.
     */
    private static final String TYPESETTING_CONTEXT_TAG = "TypesettingContext";

    /**
     * The constant <tt>GROUP_TAG</tt> ...
     */
    private static final String GROUP_TAG = "Group";

    /**
     * The constant <tt>CLASS_ATTRIBUTE</tt> ...
     */
    private static final String CLASS_ATTRIBUTE = "class";

    /**
     * The constant <tt>MAGNIFICATION_MAX</tt> contains the maximal allowed
     * magnification value. This is the fallback value which can be changed in
     * the configuration.
     */
    private static final long MAGNIFICATION_MAX = 0x8000;

    /**
     * The field <tt>group</tt> contains the entry to the linked list of groups.
     * The current group is the first one.
     */
    private Group group = null;

    /**
     * The field <tt>afterassignment</tt> contains the ...
     */
    private Token afterassignment = null;

    /**
     * The field <tt>groupFactory</tt> contains the factory to acquire
     * a new group.
     */
    private transient GroupFactory groupFactory;

    /**
     * The field <tt>hyphenationManager</tt> contains the ...
     */
    private HyphenationManager hyphenationManager = new HyphenationManagerImpl();

    /**
     * The field <tt>conditionalStack</tt> contains the  stack for conditionals.
     */
    private List conditionalStack = new ArrayList();

    /**
     * The token factory implementation to use.
     */
    private transient TokenFactory tokenFactory = new TokenFactoryImpl();

    /**
     * The field <tt>magnificationMax</tt> contains the maximal allowed
     * maginification value. This is initialized to MAGNIFICATION_MAX and
     * may be overwritten from within the configuration.
     */
    private long magnificationMax = MAGNIFICATION_MAX;

    /**
     * The field <tt>magnificationLock</tt> is used to determine whether the
     * magnification has already been set to a new value.
     * It it is <code>true</code> then it is not
     * desirable to change the value of <i>magnification</i>.
     */
    private boolean magnificationLock = false;

    /**
     * The field <tt>magnification</tt> contains the magnification for the
     * whole document in permille. The value is always greater than 0 and
     * less or equal to magnificationMax.
     */
    private long magnification = Math.max(1000, MAGNIFICATION_MAX);

    /**
     * The field <tt>observersInteraction</tt> contains the observer list which
     * is used for the observers registered to receive notifications
     * when the interaction is changed. The argument is the new interaction
     * mode.
     */
    private ObserverList observersInteraction = new ObserverList();

    /**
     * The field <tt>tcFactory</tt> contains the factory to acquire new
     * instances of a TypesettingContext.
     */
    private TypesettingContextFactory tcFactory;

    /**
     * The field <tt>fontFactory</tt> contains the font factory to use.
     */
    private FontFactory fontFactory;

    /**
     * Creates a new object.
     *
     * @param configuration the configuration to use
     *
     * @throws ConfigurationException in case of an configuration error
     * @throws GeneralException in case of an execution error
     */
    public ContextImpl(final Configuration configuration)
            throws ConfigurationException,
                GeneralException {

        super();
        groupFactory = new GroupFactory(configuration
                .getConfiguration(GROUP_TAG));
        group = groupFactory.newInstance(group);

        Configuration typesettingConfig = configuration
                .getConfiguration(TYPESETTING_CONTEXT_TAG);

        if (typesettingConfig == null) {
            throw new ConfigurationMissingException(TYPESETTING_CONTEXT_TAG,
                    configuration.toString());
        }

        tcFactory = new TypesettingContextFactory(typesettingConfig);
        TypesettingContext typesettingContext = tcFactory.newInstance();

        typesettingContext.setFont(new NullFont());
        //typesettingContext.setLanguage(config.getValue("Language"));
        setTypesettingContext(typesettingContext);

        magnificationMax = configuration
                .getValueAsInteger("maximalMagnification",
                                   (int) MAGNIFICATION_MAX);

    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setActive(java.lang.String,
     *      de.dante.extex.interpreter.Code, boolean)
     */
    public void setActive(final String name, final Code code,
            final boolean global) {

        group.setActive(name, code, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getActive(java.lang.String)
     */
    public Code getActive(final String name) {

        return group.getActive(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getAfterassignment()
     */
    public Token getAfterassignment() {

        return afterassignment;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setAfterassignment(de.dante.extex.scanner.Token)
     */
    public void setAfterassignment(final Token token) {

        afterassignment = token;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setCatcode(de.dante.util.UnicodeChar,
     *      de.dante.extex.scanner.Catcode, boolean)
     */
    public void setCatcode(final UnicodeChar c, final Catcode cc,
            final boolean global) {

        group.setCatcode(c, cc, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setBox(java.lang.String,
     *      de.dante.extex.interpreter.type.Box, boolean)
     */
    public void setBox(final String name, final Box value, final boolean global) {

        group.setBox(name, value, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getBox(java.lang.String)
     */
    public Box getBox(final String name) {

        return group.getBox(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getCode(de.dante.extex.scanner.Token)
     */
    public Code getCode(final Token t) throws GeneralException {

        if (t == null) {
            return null;
        } else if (t instanceof ControlSequenceToken) {
            return getMacro(t.getValue());
        } else if (t instanceof ActiveCharacterToken) {
            return getActive(t.getValue());
        }

        return null;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setCode(de.dante.extex.scanner.Token,
     *      de.dante.extex.interpreter.Code, boolean)
     */
    public void setCode(final Token t, final Code code, final boolean global)
            throws GeneralException {

        if (t instanceof ControlSequenceToken) {
            setMacro(t.getValue(), code, global);
        } else if (t instanceof ActiveCharacterToken) {
            setActive(t.getValue(), code, global);
        } else {
            throw new GeneralHelpingException("TTP.MissingCtrlSeq");
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setCount(java.lang.String,
     *         long, boolean)
     */
    public void setCount(final String name, final long value,
            final boolean global) {

        Count count = new Count(value);
        group.setCount(name, count, global);

        //TODO: use existing Register instead of making a new one
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getCount(java.lang.String)
     */
    public Count getCount(final String name) {

        return group.getCount(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getDelcode(de.dante.util.UnicodeChar)
     */
    public Count getDelcode(final UnicodeChar c) {

        return group.getDelcode(c);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setDelcode(de.dante.util.UnicodeChar,
     *     de.dante.extex.interpreter.type.Count, boolean)
     */
    public void setDelcode(final UnicodeChar c, final Count code,
            final boolean global) {

        group.setDelcode(c, code, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setDimen(java.lang.String,
     *         de.dante.extex.interpreter.type.Dimen, boolean)
     */
    public void setDimen(final String name, final Dimen value,
            final boolean global) {

        group.setDimen(name, value, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setDimen(java.lang.String,
     *         long, boolean)
     */
    public void setDimen(final String name, final long value,
            final boolean global) {

        Dimen dimen = new Dimen(value);
        group.setDimen(name, dimen, global);

        //TODO: use existing Register instead of making a new one
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getDimen(java.lang.String)
     */
    public Dimen getDimen(final String name) {

        return group.getDimen(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getFont(java.lang.String)
     */
    public Font getFont(final String name) {

        return this.group.getFont(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setFont(java.lang.String, de.dante.extex.interpreter.type.Font, boolean)
     */
    public void setFont(final String name, final Font font, final boolean global) {

        this.group.setFont(name, font, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getFontFactory()
     */
    public FontFactory getFontFactory() {

        return fontFactory;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setFontFactory(de.dante.extex.font.FontFactory)
     */
    public void setFontFactory(final FontFactory factory) {

        this.fontFactory = factory;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#isGlobalGroup()
     */
    public boolean isGlobalGroup() {

        return (group.getNext() == null);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getGlue(java.lang.String)
     */
    public Glue getGlue(final String name) {

        return group.getSkip(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setGlue(java.lang.String,
     *      de.dante.extex.interpreter.type.Glue, boolean)
     */
    public void setGlue(final String name, final Glue value,
            final boolean global) {

        group.setSkip(name, value, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getHyphenationTable(int)
     */
    public HyphenationTable getHyphenationTable(final int language) {

        return hyphenationManager.getHyphenationTable(Integer
                .toString(language));
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setInteraction(de.dante.extex.interpreter.Interaction,
     *      boolean)
     */
    public void setInteraction(final Interaction interaction,
            final boolean global) throws GeneralException {

        group.setInteraction(interaction, global);
        observersInteraction.update(this, interaction);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getInteraction()
     */
    public Interaction getInteraction() {

        return group.getInteraction();
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setMacro(java.lang.String,
     *      de.dante.extex.interpreter.Code, boolean)
     */
    public void setMacro(final String name, final Code code,
            final boolean global) {

        group.setMacro(name, code, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getMacro(java.lang.String)
     */
    public Code getMacro(final String name) {

        return group.getMacro(name);
    }

    /**
     * Setter for the magnification. The magnification is a global value which
     * can be assigned at most once. It contains the magnification factor in
     * permille. The default value is 1000. It can only take positive numbers
     * as values. The maximal value is taken from the configuration option
     * <tt>maximalMaginification</tt>.
     * The default value for the maximal magnification is 32768.
     *
     * @see de.dante.extex.interpreter.context.Context#setMagnification(long)
     */
    public void setMagnification(final long mag) throws GeneralHelpingException {

        if (magnificationLock && this.magnification != mag) {
            throw new GeneralHelpingException("TTP.IncompatMag", Long
                    .toString(mag));
        }

        magnificationLock = true;

        long maximalMagnification;

        if (mag < 1 || mag > magnificationMax) {
            throw new GeneralHelpingException("TTP.IllegalMag", Long
                    .toString(mag));
        }

        magnification = mag;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getMagnification()
     */
    public long getMagnification() {

        return magnification;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getMathcode(de.dante.util.UnicodeChar)
     */
    public Count getMathcode(final UnicodeChar c) {

        return group.getMathcode(c);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setMathcode(de.dante.util.UnicodeChar,
     *      Count, boolean)
     */
    public void setMathcode(final UnicodeChar c, final Count code,
            final boolean global) {

        group.setMathcode(c, code, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getMuskip(java.lang.String)
     */
    public Muskip getMuskip(final String name) {

        return group.getMuskip(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setMuskip(java.lang.String,
     *      de.dante.extex.interpreter.type.Muskip, boolean)
     */
    public void setMuskip(final String name, final Muskip value,
            final boolean global) {

        group.setMuskip(name, value, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getSfcode(de.dante.util.UnicodeChar)
     */
    public Count getSfcode(final UnicodeChar c) {

        return group.getSfcode(c);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setSfcode(de.dante.util.UnicodeChar,
     *      de.dante.extex.interpreter.type.Count, boolean)
     */
    public void setSfcode(final UnicodeChar c, final Count code,
            final boolean global) {

        group.setSfcode(c, code, global);
    }

    /**
     * Getter for the token factory.
     *
     * @return the token factory
     */
    public TokenFactory getTokenFactory() {

        return tokenFactory;
    }

    /**
     * Getter for the tokenizer.
     *
     * @return the tokenizer
     */
    public Tokenizer getTokenizer() {

        return (Tokenizer) group;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setTypesettingContext(de.dante.extex.interpreter.context.Color)
     */
    public void setTypesettingContext(final Color color)
            throws ConfigurationException {

        tcFactory.newInstance(group.getTypesettingContext(), color);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setTypesettingContext(de.dante.extex.interpreter.context.Direction)
     */
    public void setTypesettingContext(final Direction direction)
            throws ConfigurationException {

        tcFactory.newInstance(group.getTypesettingContext(), direction);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setTypesettingContext(de.dante.extex.interpreter.type.Font)
     */
    public void setTypesettingContext(final Font font)
            throws ConfigurationException {

        tcFactory.newInstance(group.getTypesettingContext(), font);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setTypesettingContext(int)
     */
    public void setTypesettingContext(final int angle)
            throws ConfigurationException {

        tcFactory.newInstance(group.getTypesettingContext(), angle);
    }

    /**
     * Setter for the typesetting context in the current group.
     *
     * @param context the new context to use
     */
    public void setTypesettingContext(final TypesettingContext context) {

        group.setTypesettingContext(context);
    }

    /**
     * Setter for the typesetting context in the specified groups.
     *
     * @param context the new context to use
     * @param global if <code>true</code> then the new value is set in all
     *            groups, otherwise only in the current group.
     */
    public void setTypesettingContext(final TypesettingContext context,
            final boolean global) {

        group.setTypesettingContext(context, global);
    }

    /**
     * Getter for the typesetting context.
     *
     * @return the typesetting context
     */
    public TypesettingContext getTypesettingContext() {

        return group.getTypesettingContext();
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#afterGroup(de.dante.extex.scanner.Token)
     */
    public void afterGroup(final Token t) {

        group.afterGroup(t);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#afterGroup(de.dante.util.Observer)
     */
    public void afterGroup(final Observer observer) {

        group.afterGroup(observer);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#closeGroup(de.dante.extex.typesetter.Typesetter,
     *     de.dante.extex.interpreter.TokenSource)
     */
    public void closeGroup(final Typesetter typesetter, final TokenSource source)
            throws GeneralException {

        Group next = group.getNext();

        if (next == null) {
            throw new GeneralHelpingException("TTP.TooManyRightBraces");
        }

        if (group.getInteraction() != next.getInteraction()) {
            observersInteraction.update(this, next.getInteraction());
        }

        group.runAfterGroup(this, typesetter);

        Tokens toks = group.getAfterGroup();
        group = next;

        if (toks != null) {
            source.push(toks);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#popConditional()
     */
    public Conditional popConditional() throws GeneralException {

        int size = conditionalStack.size();
        if (size <= 0) {
            return null;
        }
        return ((Conditional) conditionalStack.remove(size - 1));
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#pushConditional(de.dante.util.Locator, boolean)
     */
    public void pushConditional(final Locator locator, final boolean value) {

        conditionalStack.add(new Conditional(locator, value));
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#openGroup()
     */
    public void openGroup() throws ConfigurationException {

        group = groupFactory.newInstance(group);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getToks(java.lang.String)
     */
    public Tokens getToks(final String name) {

        return group.getToks(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setToks(java.lang.String,
     *      de.dante.extex.interpreter.type.Tokens, boolean)
     */
    public void setToks(final String name, final Tokens toks,
            final boolean global) {

        group.setToks(name, toks, global);
    }

    /**
     * Getter for group.
     *
     * @return the group.
     */
    protected Group getGroup() {

        return group;
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getInFile(java.lang.String)
     */
    public InFile getInFile(final String name) {

        return group.getInFile(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getOutFile(java.lang.String)
     */
    public OutFile getOutFile(final String name) {

        return group.getOutFile(name);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setInFile(java.lang.String,
     *      de.dante.extex.interpreter.type.InFile, boolean)
     */
    public void setInFile(final String name, final InFile file,
            final boolean global) {

        group.setInFile(name, file, global);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setOutFile(java.lang.String,
     *      de.dante.extex.interpreter.type.OutFile, boolean)
     */
    public void setOutFile(final String name, final OutFile file,
            final boolean global) {

        group.setOutFile(name, file, global);
    }

    /**
     * @see de.dante.util.observer.Observable#registerObserver(java.lang.String,
     *      de.dante.util.Observer)
     */
    public void registerObserver(final String name, final Observer observer)
            throws NotObservableException {

        if ("interaction".equals(name)) {
            observersInteraction.add(observer);
        } else {
            throw new NotObservableException(name);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getLccode(de.dante.util.UnicodeChar)
     */
    public UnicodeChar getLccode(final UnicodeChar uc) {

        return group.getLccode(uc);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#getUccode(de.dante.util.UnicodeChar)
     */
    public UnicodeChar getUccode(final UnicodeChar lc) {

        return group.getUccode(lc);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setLccode(de.dante.util.UnicodeChar,
     *      de.dante.util.UnicodeChar)
     */
    public void setLccode(final UnicodeChar uc, final UnicodeChar lc) {

        group.setLccode(uc, lc);
    }

    /**
     * @see de.dante.extex.interpreter.context.Context#setUccode(de.dante.util.UnicodeChar,
     *      de.dante.util.UnicodeChar)
     */
    public void setUccode(final UnicodeChar lc, final UnicodeChar uc) {

        group.setUccode(lc, uc);
    }

}