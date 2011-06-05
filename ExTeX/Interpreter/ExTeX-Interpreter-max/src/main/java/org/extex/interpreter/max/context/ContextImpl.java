/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.max.context;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.color.Color;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.font.CoreFontFactory;
import org.extex.font.exception.FontException;
import org.extex.framework.Registrar;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.ConditionalSwitch;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.ContextInternals;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.code.CodeObservable;
import org.extex.interpreter.context.observer.code.CodeObserver;
import org.extex.interpreter.context.observer.conditional.ConditionalObservable;
import org.extex.interpreter.context.observer.conditional.ConditionalObserver;
import org.extex.interpreter.context.observer.count.CountObservable;
import org.extex.interpreter.context.observer.count.CountObserver;
import org.extex.interpreter.context.observer.dimen.DimenObservable;
import org.extex.interpreter.context.observer.dimen.DimenObserver;
import org.extex.interpreter.context.observer.glue.GlueObservable;
import org.extex.interpreter.context.observer.glue.GlueObserver;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.context.observer.group.GroupObservable;
import org.extex.interpreter.context.observer.group.GroupObserver;
import org.extex.interpreter.context.observer.interaction.InteractionObservable;
import org.extex.interpreter.context.observer.interaction.InteractionObserver;
import org.extex.interpreter.context.observer.load.LoadedObservable;
import org.extex.interpreter.context.observer.load.LoadedObserver;
import org.extex.interpreter.context.observer.tokens.TokensObservable;
import org.extex.interpreter.context.observer.tokens.TokensObserver;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.stream.TokenStreamOptions;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.FixedTokens;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.Direction;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.FontImpl;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;

/**
 * This is a reference implementation for an interpreter context.
 * 
 * The groups are implemented as a linked list of single groups. In contrast to
 * the Knuthian implementation in <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> no undo stack is used.
 * <p>
 * Several operations have to be dealt with:
 * </p>
 * <ul>
 * <li>For each new group a new instance of a
 * {@link org.extex.interpreter.max.context.Group Group} is created with the old
 * one as next group.</li>
 * <li>If a group is closed then the next group is used as current group and the
 * formerly current group is discarded.</li>
 * <li>If a value has to be found in a group then the next chain has to be
 * traced down until the value is found. <br />
 * An implementation variant might want to insert the value found into the
 * higher groups; all or some of them to speed up the next access. This
 * optimization is currently not implemented.</li>
 * <li>If a local value has to be stored then it can be stored in the local
 * group only.</li>
 * <li>If a global value has to be stored then the group chain has to be
 * traversed and the value has to be set in all appropriate groups: There are
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
 * @version $Revision: 4770 $
 */
public class ContextImpl
        implements
            ContextInternals,
            CodeObservable,
            ConditionalObservable,
            CountObservable,
            DimenObservable,
            GlueObservable,
            GroupObservable,
            InteractionObservable,
            LoadedObservable,
            TokensObservable,
            Tokenizer,
            DocumentWriterOptions,
            TypesetterOptions,
            TokenStreamOptions,
            LogEnabled,
            Configurable,
            Serializable {

    /**
     * The field <tt>bottommarks</tt> contains the bottom marks.
     */
    private static Map<Object, Tokens> bottommarks =
            new HashMap<Object, Tokens>();

    /**
     * The field <tt>firstmarks</tt> contains the first marks.
     */
    private static Map<Object, Tokens> firstmarks =
            new HashMap<Object, Tokens>();

    /**
     * The constant <tt>GROUP_TAG</tt> contains the name of the tag for the
     * sub-configuration for the group factory.
     */
    private static final String GROUP_TAG = "Group";

    /**
     * The constant <tt>MAGNIFICATION_MAX</tt> contains the maximal allowed
     * magnification value. This is the fallback value which can be changed in
     * the configuration.
     */
    private static final long MAGNIFICATION_MAX = 0x8000;

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060512L;

    /**
     * The field <tt>topmarks</tt> contains the top marks.
     */
    private static Map<Object, Tokens> topmarks = new HashMap<Object, Tokens>();

    /**
     * The constant <tt>TYPESETTING_CONTEXT_TAG</tt> contains the name of the
     * configuration tag for the typesetting context.
     */
    private static final String TYPESETTING_CONTEXT_TAG = "TypesettingContext";

    /**
     * The field <tt>afterassignment</tt> contains the token to be inserted
     * after an assignment is completed or <code>null</code>.
     */
    private Token afterassignment = null;

    /**
     * The field <tt>changeCodeObservers</tt> contains the list of observers
     * registered for change event on the code.
     */
    private transient Map<Token, List<CodeObserver>> changeCodeObservers;

    /**
     * The field <tt>changeCountObservers</tt> contains the list of observers
     * registered for change event on the count registers.
     */
    private transient Map<String, List<CountObserver>> changeCountObservers;

    /**
     * The field <tt>changeDimenObservers</tt> contains the list of observers
     * registered for change event on the dimen registers.
     */
    private transient Map<String, List<DimenObserver>> changeDimenObservers;

    /**
     * The field <tt>dimenChangeObservers</tt> contains the list of observers
     * registered for change event on the glue (skip) registers.
     */
    private transient Map<String, List<GlueObserver>> changeGlueObservers;

    /**
     * The field <tt>observersInteraction</tt> contains the observer list which
     * is used for the observers registered to receive notifications when the
     * interaction is changed. The argument is the new interaction mode.
     */
    private transient List<InteractionObserver> changeInteractionObservers;

    /**
     * The field <tt>toksChangeObservers</tt> contains the list of observers
     * registered for change event on the tokens registers.
     */
    private transient Map<String, List<TokensObserver>> changeToksObservers;

    /**
     * The field <tt>conditionalObservers</tt> contains the observer for
     * conditionals. The value <code>null</code> is treated like the empty list.
     */
    private transient List<ConditionalObserver> conditionalObservers = null;

    /**
     * The field <tt>conditionalStack</tt> contains the stack for conditionals.
     */
    private List<Conditional> conditionalStack = new ArrayList<Conditional>();

    /**
     * The field <tt>dirStack</tt> contains the stack of directions.
     */
    private Stack<Direction> dirStack = new Stack<Direction>();

    /**
     * The field <tt>errorCount</tt> contains the error counter.
     */
    private int errorCount = 0;

    /**
     * The field <tt>fontFactory</tt> contains the font factory to use.
     */
    private transient CoreFontFactory fontFactory;

    /**
     * The field <tt>group</tt> contains the entry to the linked list of groups.
     * The current group is the first one.
     */
    private Group group = null;

    /**
     * The field <tt>groupFactory</tt> contains the factory to acquire a new
     * group.
     */
    private transient GroupFactory groupFactory;

    /**
     * The field <tt>groupObservers</tt> contains the list of observers
     * registered for change event on groups.
     */
    private transient List<GroupObserver> groupObservers;

    /**
     * The field <tt>id</tt> contains the is string. The id string is the
     * classification of the original source as given in the format file. The id
     * string can be <code>null</code> if not known yet.
     */
    private String id = null;

    /**
     * The field <tt>interaction</tt> contains the currently active interaction
     * mode.
     */
    private Interaction interaction = Interaction.ERRORSTOPMODE;

    /**
     * The field <tt>languageManager</tt> contains the language manager.
     */
    private LanguageManager languageManager;

    /**
     * The field <tt>loadObserver</tt> contains the list of observers for the
     * load event. Note that this list is stored within the format. Thus it is
     * <i>not</i> <tt>transient</tt>.
     */
    private List<LoadedObserver> loadObservers = null;

    /**
     * The field <tt>localizer</tt> contains the localizer to use.
     */
    private transient Localizer localizer = null;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private transient Logger logger = null;

    /**
     * The field <tt>magnification</tt> contains the magnification for the whole
     * document in permille. The value is always greater than 0 and less or
     * equal to <tt>magnificationMax</tt>.
     */
    private long magnification = Math.min(1000, MAGNIFICATION_MAX);

    /**
     * The field <tt>magnificationLock</tt> is used to determine whether the
     * magnification has already been set to a new value. It it is
     * <code>true</code> then it is not desirable to change the value of
     * <i>magnification</i>.
     */
    private boolean magnificationLock = false;

    /**
     * The field <tt>magnificationMax</tt> contains the maximal allowed
     * magnification value. This is initialized to MAGNIFICATION_MAX and may be
     * overwritten from within the configuration.
     */
    private long magnificationMax = MAGNIFICATION_MAX;

    /**
     * The field <tt>parshape</tt> contains the object containing the dimensions
     * of the paragraph.
     */
    private ParagraphShape parshape = null;

    /**
     * The field <tt>splitBottomMarks</tt> contains the split bottom marks.
     */
    private Map<Object, Tokens> splitBottomMarks =
            new Hashtable<Object, Tokens>();

    /**
     * The field <tt>splitFirstMarks</tt> contains the split first marks.
     */
    private Map<Object, Tokens> splitFirstMarks =
            new Hashtable<Object, Tokens>();

    /**
     * The field <tt>standardTokenStream</tt> contains the standard token
     * stream. This token stream usually is fed by the user.
     */
    private transient TokenStream standardTokenStream = null;

    /**
     * The field <tt>tokenFactory</tt> contains the token factory implementation
     * to use.
     */
    private transient TokenFactory tokenFactory;

    /**
     * The field <tt>tcFactory</tt> contains the factory to acquire new
     * instances of a TypesettingContext.
     */
    private transient TypesettingContextFactory typesettingContextFactory;

    /**
     * The field <tt>units</tt> contains the list of unit infos.
     */
    private List<UnitInfo> units = new ArrayList<UnitInfo>();

    /**
     * Creates a new object.
     */
    public ContextImpl() {

        init();
    }

    /**
     * Add a unit to the list of loaded units. The units can be notified when
     * the context is loaded from a format.
     * 
     * @param info the info of the unit loaded
     * 
     * @see #unitIterator()
     * 
     * @see org.extex.interpreter.context.Context#addUnit(org.extex.interpreter.unit.UnitInfo)
     */
    @Override
    public void addUnit(UnitInfo info) {

        if (info == null) {
            throw new IllegalArgumentException("addUnit()");
        }
        units.add(info);
    }

    /**
     * Register a observer to be called at the end of the group. The end of the
     * group is reached when the group is closed.
     * 
     * @param observer the observer to register
     * 
     * @see org.extex.interpreter.context.Context#afterGroup(AfterGroupObserver)
     */
    @Override
    public void afterGroup(AfterGroupObserver observer) {

        group.afterGroup(observer);
    }

    /**
     * Add a token to the tokens inserted after the group has been closed.
     * 
     * @param t the token to add
     * 
     * @see org.extex.interpreter.context.Context#afterGroup(org.extex.scanner.type.token.Token)
     */
    @Override
    public void afterGroup(Token t) {

        group.afterGroup(t);
    }

    /**
     * This method clears all split marks.
     * 
     * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
     */
    @Override
    public void clearSplitMarks() {

        splitFirstMarks.clear();
        splitBottomMarks.clear();
    }

    /**
     * Perform all actions required upon the closing of a group.
     * 
     * @param typesetter the typesetter to invoke if needed
     * @param source the source to get Tokens from if needed
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.context.Context#closeGroup(org.extex.typesetter.Typesetter,
     *      org.extex.interpreter.TokenSource)
     */
    @Override
    public void closeGroup(Typesetter typesetter, TokenSource source)
            throws HelpingException {

        Group next = group.getNext();

        if (next == null) {
            throw new HelpingException(getLocalizer(), "TTP.TooManyRightBraces");
        }

        group.runAfterGroup();

        Tokens toks = group.getAfterGroup();
        group = next;

        if (toks != null) {
            source.push(toks);
        }

        if (groupObservers != null) {
            try {
                for (GroupObserver obs : groupObservers) {
                    obs.receiveCloseGroup(this);
                }
            } catch (HelpingException e) {
                throw e;
            } catch (Exception e) {
                throw new NoHelpException(e);
            }
        }
    }

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param configuration the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration configuration) {

        magnificationMax =
                configuration.getValueAsInteger("maximalMagnification",
                    (int) MAGNIFICATION_MAX);

        groupFactory =
                new GroupFactory(configuration.getConfiguration(GROUP_TAG));
        if (group == null) {
            try {
                openGroup(GroupType.BOTTOM_LEVEL_GROUP, null, null);
            } catch (HelpingException e) {
                throw new ConfigurationWrapperException(e);
            }
        }

        Configuration typesettingConfig =
                configuration.getConfiguration(TYPESETTING_CONTEXT_TAG);

        if (typesettingConfig == null) {
            throw new ConfigurationMissingException(TYPESETTING_CONTEXT_TAG,
                configuration.toString());
        }
        if (typesettingContextFactory == null) {
            typesettingContextFactory = new TypesettingContextFactory();
        }
        typesettingContextFactory.configure(typesettingConfig);
        TypesettingContext tc;
        TypesettingContext oldTc = getTypesettingContext();

        if (languageManager != null) {
            typesettingContextFactory.setLanguageManager(languageManager);
            tc = typesettingContextFactory.initial();
            tc =
                    typesettingContextFactory.newInstance(tc,
                        languageManager.getLanguage("0"));
        } else {
            tc = typesettingContextFactory.initial();
        }
        if (oldTc != null) {
            tc = typesettingContextFactory.newInstance(oldTc);
        }
        set(tc, true);
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
     * Attach the current escape character in front of a name and return the
     * result. If the escape character is not set then the argument is returned
     * unchanged.
     * <p>
     * This method is meant to produce a printable version of a control sequence
     * for error messages.
     * </p>
     * 
     * @param name the name of the macro
     * 
     * @return the control sequence including the escape character
     * 
     * @see org.extex.interpreter.context.Context#esc(java.lang.String)
     */
    @Override
    public String esc(String name) {

        UnicodeChar escapechar = escapechar();
        return (escapechar != null ? escapechar.toString() + name : name);
    }

    /**
     * This method is meant to produce a printable version of a control sequence
     * for error messages.
     * 
     * @param token the token
     * 
     * @return the control sequence including the escape character
     * 
     * @see org.extex.interpreter.context.Context#esc(org.extex.scanner.type.token.Token)
     */
    @Override
    public String esc(Token token) {

        return token.toText(escapechar());
    }

    /**
     * Return the current escape character or <code>\0<code> if it is undefined.
     * The escape character is retrieved from the count register
     * <tt>\escapechar</tt>.
     * 
     * @return the escape character
     * 
     * @see org.extex.interpreter.context.Context#escapechar()
     */
    @Override
    public UnicodeChar escapechar() {

        long esc = getCount("escapechar").getValue();

        return (esc >= 0 ? UnicodeChar.get((int) esc) : null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#get(java.lang.Object,
     *      java.lang.Object)
     */
    @Override
    public Object get(Object extension, Object key) {

        return group.get(extension, key);
    }

    /**
     * Getter for the after assignment token.
     * 
     * @return the after assignment token
     * 
     * @see org.extex.interpreter.context.Context#getAfterassignment()
     */
    @Override
    public Token getAfterassignment() {

        return afterassignment;
    }

    /**
     * Getter for the bottom mark.
     * 
     * @param name the name of the mark
     * 
     * @return the bottom mark
     * 
     * @see org.extex.interpreter.context.ContextMark#getBottomMark(java.lang.Object)
     */
    @Override
    public Tokens getBottomMark(Object name) {

        Tokens mark = bottommarks.get(name);
        if (mark == null) {
            mark = firstmarks.get(name);
            if (mark == null) {
                mark = topmarks.get(name);
            }
        }
        return mark;
    }

    /**
     * Getter for the {@link org.extex.interpreter.type.box.Box box} register.
     * Count registers are named, either with a number or an arbitrary string.
     * 
     * @param name the name or number of the count register
     * 
     * @return the count register or <code>null</code> if it is void
     * 
     * @see org.extex.interpreter.context.Context#getBox(java.lang.String)
     */
    @Override
    public Box getBox(String name) {

        return group.getBox(name);
    }

    /**
     * Get the {@link Catcode Catcode} for a given Unicode character.
     * 
     * @param uc the Unicode character to get the catcode for.
     * 
     * @return the catcode for the character
     * 
     * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
     */
    @Override
    public Catcode getCatcode(UnicodeChar uc) {

        return group.getCatcode(uc);
    }

    /**
     * Convenience method to get the code assigned to a Token. If the Token is a
     * ControlSequenceToken then the macro is returned. If the Token is a
     * ActiveCharacterToken then the active value is returned.
     * 
     * @param t the Token to differentiate on
     * 
     * @return the code for the token
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.context.ContextCode#getCode(org.extex.scanner.type.token.CodeToken)
     */
    @Override
    public Code getCode(CodeToken t) throws HelpingException {

        return group.getCode(t);
    }

    /**
     * Getter for the currently active conditional.
     * 
     * @return the currently active conditional or <code>null</code> if none
     * 
     * @see org.extex.interpreter.context.Context#getConditional()
     */
    @Override
    public Conditional getConditional() {

        int size = conditionalStack.size();
        if (size <= 0) {
            return null;
        }
        return conditionalStack.get(size - 1);
    }

    /**
     * Getter for the {@link org.extex.core.count.Count count} register. Count
     * registers are named, either with a number or an arbitrary string.
     * <p>
     * The return value is guaranteed to be a valid object. <code>null</code>
     * will never be returned.
     * </p>
     * 
     * @param name the name or number of the count register
     * 
     * @return the count register or <code>null</code> if it is not defined
     * 
     * @see org.extex.interpreter.context.Context#getCount(java.lang.String)
     */
    @Override
    public Count getCount(String name) {

        return group.getCount(name);
    }

    /**
     * Getter for a count register.
     * 
     * @param name the name of the register
     * 
     * @return the content of the count register
     * 
     * @see org.extex.typesetter.TypesetterOptions#getCountOption(java.lang.String)
     */
    @Override
    public FixedCount getCountOption(String name) {

        return group.getCount(name);
    }

    /**
     * Getter for the delimiter code mapping.
     * 
     * @param c the character to which the delimiter code is assigned
     * 
     * @return the delimiter code for the given character
     * 
     * @see org.extex.interpreter.context.Context#getDelcode(org.extex.core.UnicodeChar)
     */
    @Override
    public MathDelimiter getDelcode(UnicodeChar c) {

        return group.getDelcode(c);
    }

    /**
     * Get the current value of the dimen register with a given name.
     * 
     * @param name the name or the number of the register
     * 
     * @return the dimen register for the given name
     * 
     * @see org.extex.interpreter.context.Context#getDimen(java.lang.String)
     */
    @Override
    public Dimen getDimen(String name) {

        return group.getDimen(name);
    }

    /**
     * Getter for a dimen register.
     * 
     * @param name the name of the register
     * 
     * @return the content of the dimen register
     * 
     * @see org.extex.typesetter.TypesetterOptions#getDimenOption(java.lang.String)
     */
    @Override
    public FixedDimen getDimenOption(String name) {

        return group.getDimen(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
     */
    @Override
    public int getErrorCount() {

        return errorCount;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextMark#getFirstMark(java.lang.Object)
     */
    @Override
    public Tokens getFirstMark(Object name) {

        Tokens mark = firstmarks.get(name);
        if (mark == null) {
            mark = topmarks.get(name);
        }
        return mark;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getFont(java.lang.String)
     */
    @Override
    public Font getFont(String name) {

        Font font = this.group.getFont(name);
        if (font == null) {
            try {
                font = new FontImpl(fontFactory.getInstance(null));
            } catch (FontException e) {
                return null;
            }
        }
        return font;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getFontFactory()
     */
    @Override
    public CoreFontFactory getFontFactory() {

        return fontFactory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getGlue(java.lang.String)
     */
    @Override
    public Glue getGlue(String name) {

        return group.getSkip(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.TypesetterOptions#getGlueOption(java.lang.String)
     */
    @Override
    public FixedGlue getGlueOption(String name) {

        return group.getSkip(name);
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
     * Getter for the array of group information describing the currently open
     * groups. The elements represent the groups in ascending order. Thus the
     * element 0 always represents the global group. This one is guaranteed to
     * be present. This means that the arras has always at least one element.
     * 
     * @return the array of group infos
     * 
     * @see org.extex.interpreter.context.ContextGroup#getGroupInfos()
     */
    @Override
    public GroupInfo[] getGroupInfos() {

        int level = (int) group.getLevel() + 1;
        GroupInfoImpl[] gi = new GroupInfoImpl[level];

        Group g = group;
        while (level-- > 0) {
            gi[level] =
                    new GroupInfoImpl(g.getLocator(), g.getType(), g.getStart());
            g = g.getNext();
        }

        return gi;
    }

    /**
     * Getter for the group level. The group level is the number of groups which
     * are currently open. Thus this number of groups can be closed.
     * 
     * @return the group level
     * 
     * @see org.extex.interpreter.context.Context#getGroupLevel()
     */
    @Override
    public long getGroupLevel() {

        return group.getLevel();
    }

    /**
     * Getter for the group type.
     * 
     * @return the group type
     * 
     * @see org.extex.interpreter.context.ContextGroup#getGroupType()
     */
    @Override
    public GroupType getGroupType() {

        return group.getType();
    }

    /**
     * Getter for the id string. The id string is the classification of the
     * original source as given in the format file. The id string can be
     * <code>null</code> if not known yet.
     * 
     * @return the id string
     * 
     * @see org.extex.interpreter.context.Context#getId()
     */
    @Override
    public String getId() {

        return id;
    }

    /**
     * Getter for the current if level.
     * 
     * @return the current if level
     * 
     * @see org.extex.interpreter.context.Context#getIfLevel()
     */
    @Override
    public long getIfLevel() {

        return conditionalStack.size();
    }

    /**
     * Getter for a input file register. In the case that the named descriptor
     * doe not exist yet a new one is returned. Especially if the name is
     * <code>null</code> then the default input stream is used.
     * 
     * @param name the name or the number of the file register
     * 
     * @return the input file descriptor
     * 
     * @see org.extex.interpreter.context.Context#getInFile(java.lang.String)
     */
    @Override
    public InFile getInFile(String name) {

        return group.getInFile(name);
    }

    /**
     * Getter for the interaction. The interaction determines how verbose the
     * actions are reported and how the interaction with the user is performed
     * in case of an error.
     * 
     * @return the current interaction
     * 
     * @see org.extex.interpreter.context.Context#getInteraction()
     */
    @Override
    public Interaction getInteraction() {

        return (interaction != null ? interaction : Interaction.ERRORSTOPMODE);
    }

    /**
     * Getter for the hyphenation record for a given language. The language is
     * used to find the hyphenation table. If the language is not known an
     * attempt is made to create it. Otherwise the default hyphenation table is
     * returned.
     * 
     * @param language the name of the language to use
     * 
     * @return the hyphenation table for the requested language
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.context.Context#getLanguage(String)
     */
    @Override
    public Language getLanguage(String language) throws HelpingException {

        return languageManager.getLanguage(language);
    }

    /**
     * Getter for the language manager.
     * 
     * @return the language manager
     * 
     * @see org.extex.interpreter.context.Context#getLanguageManager()
     */
    @Override
    public LanguageManager getLanguageManager() {

        return languageManager;
    }

    /**
     * Getter for the lccode mapping of upper case characters to their lower
     * case equivalent.
     * 
     * @param uc the upper case character
     * 
     * @return the lower case equivalent or null if none exists
     * 
     * @see org.extex.interpreter.context.Context#getLccode(org.extex.core.UnicodeChar)
     */
    @Override
    public UnicodeChar getLccode(UnicodeChar uc) {

        return group.getLccode(uc);
    }

    /**
     * Getter for localizer.
     * 
     * @return the localizer.
     */
    protected Localizer getLocalizer() {

        if (localizer == null) {
            localizer = LocalizerFactory.getLocalizer(getClass().getName());
        }
        return localizer;
    }

    /**
     * Getter for the magnification factor in per mille. The default value is
     * 1000. It can only take positive numbers as values.
     * 
     * @return the magnification factor
     * 
     * @see org.extex.interpreter.context.Context#getMagnification()
     */
    @Override
    public long getMagnification() {

        return magnification;
    }

    /**
     * Getter for the math code of a character.
     * 
     * @param uc the character index
     * 
     * @return the math code
     * 
     * @see org.extex.interpreter.context.Context#getMathcode(org.extex.core.UnicodeChar)
     */
    @Override
    public MathCode getMathcode(UnicodeChar uc) {

        return group.getMathcode(uc);
    }

    /**
     * Getter for a muskip register.
     * 
     * @param name the name or the number of the register
     * 
     * @return the named muskip or <code>null</code> if none is set
     * 
     * @see org.extex.interpreter.context.Context#getMuskip(java.lang.String)
     */
    @Override
    public Muskip getMuskip(String name) {

        return group.getMuskip(name);
    }

    /**
     * Getter for the current name space.
     * 
     * @return the current name space
     * 
     * @see org.extex.interpreter.context.Context#getNamespace()
     */
    @Override
    public String getNamespace() {

        return group.getNamespace();
    }

    /**
     * Getter for an output file descriptor.
     * 
     * @param name the name or the number of the file register
     * 
     * @return the output file descriptor
     * 
     * @see org.extex.interpreter.context.Context#getOutFile(java.lang.String)
     */
    @Override
    public OutFile getOutFile(String name) {

        return group.getOutFile(name);
    }

    /**
     * Getter for the parshape. The parshape is a feature of the context which
     * does not interact with the grouping mechanism.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getParshape()
     */
    @Override
    public ParagraphShape getParshape() {

        return this.parshape;
    }

    /**
     * Getter for the space factor code of a character.
     * 
     * @param uc the Unicode character
     * 
     * @return the space factor code.
     * 
     * @see org.extex.interpreter.context.Context#getSfcode(org.extex.core.UnicodeChar)
     */
    @Override
    public FixedCount getSfcode(UnicodeChar uc) {

        return group.getSfcode(uc);
    }

    /**
     * Getter for the split bottom mark.
     * 
     * @param name the name of the mark
     * 
     * @return the split bottom mark
     * 
     * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(java.lang.Object)
     */
    @Override
    public Tokens getSplitBottomMark(Object name) {

        return splitBottomMarks.get(name);
    }

    /**
     * Getter for the split first mark.
     * 
     * @param name the name of the mark
     * 
     * @return the split first mark
     * 
     * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(java.lang.Object)
     */
    @Override
    public Tokens getSplitFirstMark(Object name) {

        return splitFirstMarks.get(name);
    }

    /**
     * Getter for standardTokenStream.
     * 
     * @return the standardTokenStream
     * 
     * @see org.extex.interpreter.context.Context#getStandardTokenStream()
     */
    @Override
    public TokenStream getStandardTokenStream() {

        return group.getStandardTokenStream();
    }

    /**
     * Getter for the token factory.
     * 
     * @return the token factory
     */
    @Override
    public TokenFactory getTokenFactory() {

        return tokenFactory;
    }

    /**
     * Getter for the tokenizer.
     * 
     * @return the tokenizer
     */
    @Override
    public Tokenizer getTokenizer() {

        return group;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.DocumentWriterOptions#getTokensOption(java.lang.String)
     */
    @Override
    public String getTokensOption(String name) {

        return group.getToks(name).toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getToks(java.lang.String)
     */
    @Override
    public Tokens getToks(String name) {

        return group.getToks(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.stream.TokenStreamOptions#getToksOption(java.lang.String)
     */
    @Override
    public FixedTokens getToksOption(String name) {

        return group.getToks(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getToksOrNull(java.lang.String)
     */
    @Override
    public Tokens getToksOrNull(String name) {

        return group.getToksOrNull(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextMark#getTopMark(java.lang.Object)
     */
    @Override
    public Tokens getTopMark(Object name) {

        return topmarks.get(name);
    }

    /**
     * Getter for the typesetting context.
     * 
     * @return the typesetting context
     * 
     * @see org.extex.typesetter.TypesetterOptions#getTypesettingContext()
     */
    @Override
    public TypesettingContext getTypesettingContext() {

        return group.getTypesettingContext();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextInternals#getTypesettingContextFactory()
     */
    @Override
    public TypesettingContextFactory getTypesettingContextFactory() {

        return typesettingContextFactory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getUccode(org.extex.core.UnicodeChar)
     */
    @Override
    public UnicodeChar getUccode(UnicodeChar lc) {

        return group.getUccode(lc);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextErrorCount#incrementErrorCount()
     */
    @Override
    public int incrementErrorCount() {

        return ++errorCount;
    }

    /**
     * Initialize the internal state of the instance.
     */
    private void init() {

        changeCodeObservers = new HashMap<Token, List<CodeObserver>>();
        changeCountObservers = new HashMap<String, List<CountObserver>>();
        changeDimenObservers = new HashMap<String, List<DimenObserver>>();
        changeGlueObservers = new HashMap<String, List<GlueObserver>>();
        changeToksObservers = new HashMap<String, List<TokensObserver>>();
        changeInteractionObservers = new ArrayList<InteractionObserver>();
        groupObservers = null;

        LanguageObserver languageObserver = new LanguageObserver();
        registerCountObserver("language", languageObserver);
        registerTokensObserver("lang", languageObserver);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#isGlobalGroup()
     */
    @Override
    public boolean isGlobalGroup() {

        return (group.getNext() == null);
    }

    /**
     * This method can be used to open another group. The current group is
     * pushed onto the stack to be reactivated when the new group will be
     * closed.
     * 
     * @param type the type of the group
     * @param locator the locator for the start
     * @param start the token which started the group
     * @throws ConfigurationException in case of an error in the configuration,
     *         e.g. the class for the group can not be determined.
     * 
     * @see #closeGroup(Typesetter, TokenSource)
     * 
     *      {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextGroup#openGroup(org.extex.interpreter.context.group.GroupType,
     *      org.extex.core.Locator, org.extex.scanner.type.token.Token)
     */
    @Override
    public void openGroup(GroupType type, Locator locator, Token start)
            throws HelpingException {

        group = groupFactory.newInstance(group, locator, start, type);
        group.setStandardTokenStream(standardTokenStream);
        if (groupObservers != null) {
            try {
                for (GroupObserver obs : groupObservers) {
                    obs.receiveOpenGroup(this);
                }
            } catch (HelpingException e) {
                throw e;
            } catch (Exception e) {
                throw new NoHelpException(e);
            }
        }
    }

    /**
     * Pop the management information for a conditional from the stack and
     * return it. If the stack is empty then <code>null</code> is returned.
     * 
     * @return the formerly topmost element from the conditional stack
     * 
     *         {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#popConditional()
     */
    @Override
    public Conditional popConditional() throws HelpingException {

        int len = conditionalStack.size();
        if (len <= 0) {
            return null;
        }
        Conditional cond = conditionalStack.remove(len - 1);

        if (conditionalObservers != null) {

            for (ConditionalObserver obs : conditionalObservers) {
                try {
                    obs.receiveEndConditional(this, cond);
                } catch (Exception e) {
                    logger.log(Level.INFO, "", e);
                }
            }
        }
        return cond;
    }

    /**
     * Pop a direction from the direction stack.
     * 
     * @return the topmost direction on the stack or <code>null</code> if the
     *         stack is empty
     * 
     * @see org.extex.interpreter.context.Context#popDirection()
     */
    @Override
    public Direction popDirection() {

        return (dirStack.isEmpty() ? null : dirStack.pop());
    }

    /**
     * Put a value onto the conditional stack.
     * 
     * @param locator the locator for the start of the if statement
     * @param isIfThenElse the value to push
     * @param primitive the name of the primitive which triggered this operation
     * @param branch the branch number
     * @param neg negation indicator
     * 
     * @see org.extex.interpreter.context.Context#pushConditional(org.extex.core.Locator,
     *      boolean, org.extex.interpreter.type.Code, long, boolean)
     */
    @Override
    public void pushConditional(Locator locator, boolean isIfThenElse,
            Code primitive, long branch, boolean neg) {

        Conditional cond = isIfThenElse
        //
                ? new Conditional(locator, primitive, branch, neg)
                : new ConditionalSwitch(locator, primitive, branch, neg);
        conditionalStack.add(cond);

        if (conditionalObservers != null) {
            try {
                for (ConditionalObserver obs : conditionalObservers) {
                    obs.receiveStartConditional(this, cond);
                }
            } catch (Exception e) {
                logger.log(Level.INFO, "", e);
            }
        }
    }

    /**
     * Push a direction onto the direction stack.
     * 
     * @param dir the direction
     * 
     * @see org.extex.interpreter.context.Context#pushDirection(org.extex.typesetter.tc.Direction)
     */
    @Override
    public void pushDirection(Direction dir) {

        dirStack.push(dir);
    }

    /**
     * This method maps instances to their normal representations if required.
     * It is used during the deserialization.
     * 
     * @return the normalized object
     * 
     * @throws ObjectStreamException in case of an error
     */
    public Object readResolve() throws ObjectStreamException {

        Registrar.reconnect(this);
        init();
        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.observer.load.LoadedObservable#receiveLoad(org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    @Override
    public void receiveLoad(TokenSource source, Typesetter typesetter)
            throws HelpingException {

        if (loadObservers != null) {
            for (LoadedObserver obs : loadObservers) {
                obs.receiveLoaded(this, source, typesetter);
            }
        }
    }

    /**
     * Register an observer for code change events. Code change events are
     * triggered when the assignment of a macro or active character changes. In
     * this case the appropriate method in the observer is invoked.
     * 
     * @param token the token to be observed. This should be a macro or active
     *        character token.
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.code.CodeObservable#registerCodeChangeObserver(org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.observer.code.CodeObserver)
     */
    @Override
    public synchronized void registerCodeChangeObserver(Token token,
            CodeObserver observer) {

        List<CodeObserver> observerList = changeCodeObservers.get(token);
        if (null == observerList) {
            observerList = new ArrayList<CodeObserver>();
            changeCodeObservers.put(token, observerList);
        }
        observerList.add(observer);
    }

    /**
     * Register an observer for conditional events. Conditional events are
     * triggered when a conditional is started or ended.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.conditional.ConditionalObservable#registerConditionalObserver(org.extex.interpreter.context.observer.conditional.ConditionalObserver)
     */
    @Override
    public synchronized void registerConditionalObserver(
            ConditionalObserver observer) {

        if (conditionalObservers == null) {
            conditionalObservers = new ArrayList<ConditionalObserver>();
        }
        conditionalObservers.add(observer);
    }

    /**
     * Register an observer for count change events. Count change events are
     * triggered when a value is assigned to a count register. In this case the
     * appropriate method in the observer is invoked.
     * <p>
     * A single count register can be observed by giving a name of the count
     * register to observe. Only changes to this register trigger the
     * notification. If this name is <code>null</code> the changes to all
     * registers are reported to the observer.
     * </p>
     * 
     * @param name the name or the number of the register
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.count.CountObservable#registerCountObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.count.CountObserver)
     */
    @Override
    public synchronized void registerCountObserver(String name,
            CountObserver observer) {

        List<CountObserver> list = changeCountObservers.get(name);
        if (list == null) {
            list = new ArrayList<CountObserver>();
            changeCountObservers.put(name, list);
        }
        list.add(observer);
    }

    /**
     * Register an observer for dimen change events. Count change events are
     * triggered when a value is assigned to a dimen register. In this case the
     * appropriate method in the observer is invoked.
     * <p>
     * A single dimen register can be observed by giving a name of the dimen
     * register to observe. Only changes to this register trigger the
     * notification. If this name is <code>null</code> the changes to all
     * registers are reported to the observer.
     * </p>
     * 
     * @param name the name or the number of the register
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.dimen.DimenObservable#registerDimenObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.dimen.DimenObserver)
     */
    @Override
    public synchronized void registerDimenObserver(String name,
            DimenObserver observer) {

        List<DimenObserver> list = changeDimenObservers.get(name);
        if (list == null) {
            list = new ArrayList<DimenObserver>();
            changeDimenObservers.put(name, list);
        }
        list.add(observer);
    }

    /**
     * Register an observer for glue change events. Count change events are
     * triggered when a value is assigned to a glue register. In this case the
     * appropriate method in the observer is invoked.
     * <p>
     * A single glue register can be observed by giving a name of the glue
     * register to observe. Only changes to this register trigger the
     * notification. If this name is <code>null</code> the changes to all
     * registers are reported to the observer.
     * </p>
     * 
     * @param name the name or the number of the register
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.glue.GlueObservable#registerGlueObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.glue.GlueObserver)
     */
    @Override
    public synchronized void registerGlueObserver(String name,
            GlueObserver observer) {

        List<GlueObserver> list = changeGlueObservers.get(name);
        if (list == null) {
            list = new ArrayList<GlueObserver>();
            changeGlueObservers.put(name, list);
        }
        list.add(observer);
    }

    /**
     * Register an observer for group change events. Group change events are
     * triggered when a group is opened or closed. In this case the appropriate
     * method in the observer is invoked.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.group.GroupObservable#registerGroupObserver(org.extex.interpreter.context.observer.group.GroupObserver)
     */
    @Override
    public synchronized void registerGroupObserver(GroupObserver observer) {

        if (groupObservers == null) {
            groupObservers = new ArrayList<GroupObserver>();
        }
        groupObservers.add(observer);
    }

    /**
     * Register an observer for interaction mode change events. Interaction mode
     * change events are triggered when a new value is assigned to the
     * interaction mode. In this case the appropriate method in the observer is
     * invoked.
     * <p>
     * A single count register can be observed by giving a name of the count
     * register to observe. Only changes to this register trigger the
     * notification. If this name is <code>null</code> the changes to all
     * registers are reported to the observer.
     * </p>
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.interaction.InteractionObservable#registerInteractionObserver(org.extex.interpreter.context.observer.interaction.InteractionObserver)
     */
    @Override
    public synchronized void registerInteractionObserver(
            InteractionObserver observer) {

        changeInteractionObservers.add(observer);
    }

    /**
     * Register an observer for load events. Code change events are triggered
     * when the context is loaded.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.load.LoadedObservable#registerLoadObserver(org.extex.interpreter.context.observer.load.LoadedObserver)
     */
    @Override
    public void registerLoadObserver(LoadedObserver observer) {

        if (loadObservers == null) {
            loadObservers = new ArrayList<LoadedObserver>();
        }
        loadObservers.add(observer);
    }

    /**
     * Register an observer for tokens change events. Tokens change events are
     * triggered when an assignment to a tokens register is performed. In this
     * case the appropriate method in the observer is invoked.
     * 
     * @param name the token to be observed. This should be a macro or active
     *        character token.
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.tokens.TokensObservable#registerTokensObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.tokens.TokensObserver)
     */
    @Override
    public synchronized void registerTokensObserver(String name,
            TokensObserver observer) {

        List<TokensObserver> list = changeToksObservers.get(name);
        if (list == null) {
            list = new ArrayList<TokensObserver>();
            changeToksObservers.put(name, list);
        }
        list.add(observer);

    }

    /**
     * This method is able to invoke all observers for a code change event.
     * 
     * @param t the code token to change the binding for
     * @param code the new code binding
     * @param observerList the list of observers
     * 
     * @throws HelpingException in case of a problem in an observer
     */
    private void runCodeObservers(CodeToken t, Code code,
            List<CodeObserver> observerList) throws HelpingException {

        try {
            for (CodeObserver obs : observerList) {
                obs.receiveCodeChange(this, t, code);
            }
        } catch (HelpingException e) {
            throw e;
        } catch (Exception e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * This method is able to invoke all observers for a count change event.
     * 
     * @param name the name of the count register
     * @param count the new value
     * @param observerList the list of observers
     * 
     * @throws HelpingException in case of a problem in an observer
     */
    private void runCountObservers(String name, Count count,
            List<CountObserver> observerList) throws HelpingException {

        try {
            for (CountObserver obs : observerList) {
                obs.receiveCountChange(this, name, count);
            }
        } catch (HelpingException e) {
            throw e;
        } catch (Exception e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * This method is able to invoke all observers for a dimen change event.
     * 
     * @param name the name of the count register
     * @param dimen the new value
     * @param observerList the list of observers
     * 
     * @throws HelpingException in case of a problem in an observer
     */
    private void runDimenObservers(String name, Dimen dimen,
            List<DimenObserver> observerList) throws HelpingException {

        try {
            for (DimenObserver obs : observerList) {
                obs.receiveDimenChange(this, name, dimen);
            }
        } catch (HelpingException e) {
            throw e;
        } catch (Exception e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * This method is able to invoke all observers for a glue change event.
     * 
     * @param name the name of the count register
     * @param glue the new value
     * @param observerList the list of observers
     * 
     * @throws HelpingException in case of a problem in an observer
     */
    private void runGlueObservers(String name, Glue glue,
            List<GlueObserver> observerList) throws HelpingException {

        try {
            for (GlueObserver obs : observerList) {
                obs.receiveGlueChange(this, name, glue);
            }
        } catch (HelpingException e) {
            throw e;
        } catch (Exception e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * This method is able to invoke all observers for a tokens change event.
     * 
     * @param name the name of the count register
     * @param toks the new value
     * @param observerList the list of observers
     * 
     * @throws HelpingException in case of a problem in an observer
     */
    private void runTokensObservers(String name, Tokens toks,
            List<TokensObserver> observerList) throws HelpingException {

        try {
            for (TokensObserver obs : observerList) {
                obs.receiveTokensChange(this, name, toks);
            }
        } catch (HelpingException e) {
            throw e;
        } catch (Exception e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * Setter for the color in the current typesetting context.
     * 
     * @param color the new color
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws ConfigurationException in case of an error in the configuration.
     * 
     * @see org.extex.interpreter.context.Context#set(org.extex.color.Color,
     *      boolean)
     */
    @Override
    public void set(Color color, boolean global) throws ConfigurationException {

        group.setTypesettingContext(typesettingContextFactory.newInstance(
            group.getTypesettingContext(), color), global);
    }

    /**
     * Setter for the direction in the current typesetting context.
     * 
     * @param direction the new direction
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws ConfigurationException in case of an error in the configuration.
     * 
     * @see org.extex.interpreter.context.Context#set(org.extex.typesetter.tc.Direction,
     *      boolean)
     */
    @Override
    public void set(Direction direction, boolean global)
            throws ConfigurationException {

        group.setTypesettingContext(typesettingContextFactory.newInstance(
            group.getTypesettingContext(), direction), global);
    }

    /**
     * Setter for the font in the current typesetting context.
     * 
     * @param font the new font
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws ConfigurationException in case of an error in the configuration.
     * 
     * @see org.extex.interpreter.context.Context#set(org.extex.typesetter.tc.font.Font,
     *      boolean)
     */
    @Override
    public void set(Font font, boolean global) throws ConfigurationException {

        group.setTypesettingContext(typesettingContextFactory.newInstance(
            group.getTypesettingContext(), font), global);
    }

    /**
     * Setter for the language in the current typesetting context.
     * 
     * @param language the new language
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws ConfigurationException in case of an error in the configuration.
     * 
     * @see org.extex.interpreter.context.Context#set(org.extex.language.Language,
     *      boolean)
     */
    @Override
    public void set(Language language, boolean global)
            throws ConfigurationException {

        group.setTypesettingContext(typesettingContextFactory.newInstance(
            group.getTypesettingContext(), language), global);
    }

    /**
     * Setter for a value from an extended section of the context.
     * 
     * @param extension the name of the extension
     * @param key the key for the value
     * @param value the value to store
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#set(java.lang.Object,
     *      java.lang.Object, java.lang.Object, boolean)
     */
    @Override
    public void set(Object extension, Object key, Object value, boolean global) {

        group.set(extension, key, value, global);
    }

    /**
     * Setter for the typesetting context in the specified groups.
     * 
     * @param context the new context to use
     * @param global if <code>true</code> then the new value is set in all
     *        groups, otherwise only in the current group.
     */
    @Override
    public void set(TypesettingContext context, boolean global) {

        group.setTypesettingContext(context, global);
    }

    /**
     * Setter for the after assignment token.
     * 
     * @param token the after assignment token
     * 
     * @see org.extex.interpreter.context.Context#setAfterassignment(org.extex.scanner.type.token.Token)
     */
    @Override
    public void setAfterassignment(Token token) {

        afterassignment = token;
    }

    /**
     * Setter for the {@link org.extex.interpreter.type.box.Box box} register in
     * the current group. Count registers are named, either with a number or an
     * arbitrary string. The numbered registers where limited to 256 in
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>. This restriction does no longer hold for
     * <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param name the name or the number of the register
     * @param value the new value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setBox(java.lang.String,
     *      org.extex.interpreter.type.box.Box, boolean)
     */
    @Override
    public void setBox(String name, Box value, boolean global) {

        group.setBox(name, value, global);
    }

    /**
     * Setter for the catcode of a character in the specified groups.
     * 
     * @param c the character to assign a catcode for
     * @param catcode the catcode of the character
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setCatcode(org.extex.core.UnicodeChar,
     *      org.extex.scanner.type.Catcode, boolean)
     */
    @Override
    public void setCatcode(UnicodeChar c, Catcode catcode, boolean global) {

        group.setCatcode(c, catcode, global);
    }

    /**
     * Setter for the code assigned to a Token. The Token has to be either a
     * {@link org.extex.scanner.type.token.ActiveCharacterToken
     * ActiveCharacterToken} or a
     * {@link org.extex.scanner.type.token.ControlSequenceToken
     * ControlSequenceToken}.
     * 
     * @param t the Token to set the code for
     * @param code the code for the token
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.context.ContextCode#setCode(org.extex.scanner.type.token.CodeToken,
     *      org.extex.interpreter.type.Code, boolean)
     */
    @Override
    public void setCode(CodeToken t, Code code, boolean global)
            throws HelpingException {

        group.setCode(t, code, global);

        List<CodeObserver> observerList = changeCodeObservers.get(t);
        if (null != observerList) {
            runCodeObservers(t, code, observerList);
        }
        observerList = changeCodeObservers.get(null);
        if (null != observerList) {
            runCodeObservers(t, code, observerList);
        }
    }

    /**
     * Setter for the {@link org.extex.core.count.Count count} register in all
     * requested groups. Count registers are named, either with a number or an
     * arbitrary string. The numbered registers where limited to 256 in
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>. This restriction does no longer hold for
     * <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param name the name or the number of the register
     * @param value the new value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     *        {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setCount(java.lang.String,
     *      long, boolean)
     */
    @Override
    public void setCount(String name, long value, boolean global)
            throws HelpingException {

        Count count = new Count(value);
        group.setCount(name, count, global);

        List<CountObserver> observerList = changeCountObservers.get(name);
        if (null != observerList) {
            runCountObservers(name, count, observerList);
        }
        observerList = changeCountObservers.get(null);
        if (null != observerList) {
            runCountObservers(name, count, observerList);
        }
    }

    /**
     * Setter for a count register.
     * 
     * @param name the name of the register
     * @param value the value
     * 
     *        {@inheritDoc}
     * 
     * @see org.extex.typesetter.TypesetterOptions#setCountOption(java.lang.String,
     *      long)
     */
    @Override
    public void setCountOption(String name, long value) throws GeneralException {

        setCount(name, value, false);
    }

    /**
     * Setter for the delimiter code mapping.
     * 
     * @param c the character to which the delimiter code is assigned
     * @param delimiter the delimiter code
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setDelcode(org.extex.core.UnicodeChar,
     *      org.extex.typesetter.type.math.MathDelimiter, boolean)
     */
    @Override
    public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
            boolean global) {

        group.setDelcode(c, delimiter, global);
    }

    /**
     * Setter for the {@link org.extex.core.dimen.Dimen Dimen} register in all
     * requested groups. Dimen registers are named, either with a number or an
     * arbitrary string. The numbered registers where limited to 256 in
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>. This restriction does no longer hold for
     * <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param name the name or the number of the register
     * @param value the new value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws HelpingException in case of problems in an observer
     * 
     * @see org.extex.interpreter.context.Context#setDimen(java.lang.String,
     *      org.extex.core.dimen.Dimen, boolean)
     */
    @Override
    public void setDimen(String name, Dimen value, boolean global)
            throws HelpingException {

        group.setDimen(name, value, global);

        List<DimenObserver> observerList = changeDimenObservers.get(name);
        if (null != observerList) {
            runDimenObservers(name, value, observerList);
        }
        observerList = changeDimenObservers.get(null);
        if (null != observerList) {
            runDimenObservers(name, value, observerList);
        }
    }

    /**
     * Setter for the {@link org.extex.core.dimen.Dimen Dimen} register in all
     * requested groups. Dimen registers are named, either with a number or an
     * arbitrary string. The numbered registers where limited to 256 in
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>. This restriction does no longer hold for
     * <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param name the name or the number of the register
     * @param value the new value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws HelpingException in case of problems in an observer
     * 
     * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String,
     *      long, boolean)
     */
    @Override
    public void setDimen(String name, long value, boolean global)
            throws HelpingException {

        setDimen(name, new Dimen(value), global);
    }

    /**
     * Setter for font registers.
     * 
     * @param name the name or the number of the register
     * @param font the new Font value
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setFont(java.lang.String,
     *      org.extex.typesetter.tc.font.Font, boolean)
     */
    @Override
    public void setFont(String name, Font font, boolean global) {

        group.setFont(name, font, global);
    }

    /**
     * Setter for the font factory.
     * 
     * @param factory the font factory to set.
     * 
     * @see org.extex.interpreter.context.Context#setFontFactory(org.extex.font.CoreFontFactory)
     */
    @Override
    public void setFontFactory(CoreFontFactory factory) {

        this.fontFactory = factory;
    }

    /**
     * Setter for a glue register.
     * 
     * @param name the name of the glue register
     * @param value the glue value to set
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.context.Context#setGlue(java.lang.String,
     *      org.extex.core.glue.Glue, boolean)
     */
    @Override
    public void setGlue(String name, Glue value, boolean global)
            throws HelpingException {

        group.setSkip(name, value, global);

        List<GlueObserver> observerList = changeGlueObservers.get(name);
        if (null != observerList) {
            runGlueObservers(name, value, observerList);
        }
        observerList = changeGlueObservers.get(null);
        if (null != observerList) {
            runGlueObservers(name, value, observerList);
        }
    }

    /**
     * Setter for the id string. The id string is the classification of the
     * original source like given in the format file.
     * 
     * @param theId the id string
     * 
     * @see org.extex.interpreter.context.Context#setId(java.lang.String)
     */
    @Override
    public void setId(String theId) {

        this.id = theId;
    }

    /**
     * Setter for the {@link org.extex.scanner.type.file.InFile InFile} register
     * in all requested groups. InFile registers are named, either with a number
     * or an arbitrary string. The numbered registers where limited to 16 in
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>. This restriction does no longer hold for
     * <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param name the name or the number of the file register
     * @param file the input file descriptor
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setInFile(java.lang.String,
     *      org.extex.scanner.type.file.InFile, boolean)
     */
    @Override
    public void setInFile(String name, InFile file, boolean global) {

        group.setInFile(name, file, global);
    }

    /**
     * Setter for the interaction in all requested groups. The interaction
     * determines how verbose the actions are reported and how the interaction
     * with the user is performed in case of an error.
     * 
     * @param interaction the new value of the interaction
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.context.ContextInteraction#setInteraction(org.extex.interpreter.interaction.Interaction)
     */
    @Override
    public void setInteraction(Interaction interaction) throws HelpingException {

        if (this.interaction != interaction) {
            this.interaction = interaction;
            try {
                for (InteractionObserver obs : changeInteractionObservers) {
                    obs.receiveInteractionChange(this, interaction);
                }
            } catch (HelpingException e) {
                throw e;
            } catch (Exception e) {
                throw new NoHelpException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.language.LanguageManagerCarrier#setLanguageManager(org.extex.language.LanguageManager)
     */
    @Override
    public void setLanguageManager(LanguageManager manager)
            throws ConfigurationException {

        if (languageManager == null) {
            set(manager.getLanguage("0"), true);
        }
        this.languageManager = manager;
        typesettingContextFactory.setLanguageManager(languageManager);
    }

    /**
     * Declare the translation from an upper case character to a lower case
     * character.
     * 
     * @param uc upper case character
     * @param lc lower case equivalent
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setLccode(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar, boolean)
     */
    @Override
    public void setLccode(UnicodeChar uc, UnicodeChar lc, boolean global) {

        group.setLccode(uc, lc, global);
    }

    /**
     * Setter for the magnification. The magnification is a global value which
     * can be assigned at most once. It contains the magnification factor in
     * permille. The default value is 1000. It can only take positive numbers as
     * values. The maximal value is taken from the configuration option
     * <tt>maximalMaginification</tt>. The default value for the maximal
     * magnification is 32768.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setMagnification(long,
     *      boolean)
     */
    @Override
    public void setMagnification(long mag, boolean lock)
            throws HelpingException {

        if (magnificationLock && this.magnification != mag) {
            throw new HelpingException(getLocalizer(), "TTP.IncompatibleMag", //
                Long.toString(mag), Long.toString(magnification));
        }

        magnificationLock |= lock;

        if (mag < 1 || mag > magnificationMax) {
            throw new HelpingException(getLocalizer(), "TTP.IllegalMag", //
                Long.toString(mag));
        }

        magnification = mag;
    }

    /**
     * Setter for a mark. The information for first mark and top mark are
     * updated if necessary.
     * 
     * @param name the name of the mark
     * @param mark the vale of the mark
     * 
     * @see org.extex.interpreter.context.Context#setMark(java.lang.Object,
     *      org.extex.scanner.type.tokens.Tokens)
     */
    @Override
    public void setMark(Object name, Tokens mark) {

        if (firstmarks.get(name) == null) {
            firstmarks.put(name, mark);
        }
        bottommarks.put(name, mark);
    }

    /**
     * Setter for the math code of a character
     * 
     * @param uc the character index
     * @param code the new math code
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setMathcode(org.extex.core.UnicodeChar,
     *      MathCode, boolean)
     */
    @Override
    public void setMathcode(UnicodeChar uc, MathCode code, boolean global) {

        group.setMathcode(uc, code, global);
    }

    /**
     * Setter for a muskip register.
     * 
     * @param name the name or the number of the register
     * @param value the new value
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setMuskip(java.lang.String,
     *      org.extex.core.muskip.Muskip, boolean)
     */
    @Override
    public void setMuskip(String name, Muskip value, boolean global) {

        group.setMuskip(name, value, global);
    }

    /**
     * Setter for the name space.
     * 
     * @param namespace the new name space
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setNamespace(java.lang.String,
     *      boolean)
     */
    @Override
    public void setNamespace(String namespace, boolean global) {

        group.setNamespace(namespace, global);
    }

    /**
     * Setter for a output file descriptor.
     * 
     * @param name the name or the number of the file register
     * @param file the descriptor of the output file
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setOutFile(java.lang.String,
     *      org.extex.scanner.type.file.OutFile, boolean)
     */
    @Override
    public void setOutFile(String name, OutFile file, boolean global) {

        group.setOutFile(name, file, global);
    }

    /**
     * Setter for the paragraph shape.
     * 
     * @param shape the new paragraph shape
     * 
     * @see org.extex.interpreter.context.Context#setParshape(org.extex.typesetter.paragraphBuilder.ParagraphShape)
     */
    @Override
    public void setParshape(ParagraphShape shape) {

        this.parshape = shape;
    }

    /**
     * Setter for the space factor code in the specified groups. Any character
     * has an associated space factor. This value can be set with the current
     * method.
     * 
     * @param uc the Unicode character to assign the sfcode to
     * @param code the new sfcode
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setSfcode(org.extex.core.UnicodeChar,
     *      org.extex.core.count.Count, boolean)
     */
    @Override
    public void setSfcode(UnicodeChar uc, Count code, boolean global) {

        group.setSfcode(uc, code, global);
    }

    /**
     * Setter for a split mark. The information for first mark and top mark are
     * updated is necessary.
     * 
     * @param name the name of the mark
     * @param mark the vale of the mark
     * 
     * @see org.extex.interpreter.context.ContextMark#setSplitMark(java.lang.Object,
     *      org.extex.scanner.type.tokens.Tokens)
     */
    @Override
    public void setSplitMark(Object name, Tokens mark) {

        if (splitFirstMarks.get(name) == null) {
            splitFirstMarks.put(name, mark);
        }
        splitBottomMarks.put(name, mark);
    }

    /**
     * Setter for standardTokenStream.
     * 
     * @param standardTokenStream the standardTokenStream to set.
     * 
     * @see org.extex.interpreter.context.Context#setStandardTokenStream(org.extex.scanner.api.TokenStream)
     */
    @Override
    public void setStandardTokenStream(TokenStream standardTokenStream) {

        this.standardTokenStream = standardTokenStream;
        group.setStandardTokenStream(standardTokenStream);
    }

    /**
     * Setter for the token factory
     * 
     * @param factory the new value of the factory
     * 
     * @see org.extex.interpreter.context.Context#setTokenFactory(org.extex.scanner.type.token.TokenFactory)
     */
    @Override
    public void setTokenFactory(TokenFactory factory) {

        tokenFactory = factory;
    }

    /**
     * Setter for the {@link org.extex.scanner.type.tokens.Tokens Tokens}
     * register in the specified groups. Tokens registers are named, either with
     * a number or an arbitrary string. The numbered registers where limited to
     * 256 in <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>. This restriction does no longer hold for
     * <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param name the name or the number of the register
     * @param toks the new value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @throws HelpingException in case of a problem in an observer
     * 
     * @see org.extex.interpreter.context.Context#setToks(java.lang.String,
     *      org.extex.scanner.type.tokens.Tokens, boolean)
     */
    @Override
    public void setToks(String name, Tokens toks, boolean global)
            throws HelpingException {

        group.setToks(name, toks, global);

        List<TokensObserver> observerList = changeToksObservers.get(name);
        if (null != observerList) {
            runTokensObservers(name, toks, observerList);
        }
        observerList = changeToksObservers.get(null);
        if (null != observerList) {
            runTokensObservers(name, toks, observerList);
        }
    }

    /**
     * Declare the translation from a lower case character to an upper case
     * character.
     * 
     * @param lc lower case character
     * @param uc uppercase equivalent
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.context.Context#setUccode(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar, boolean)
     */
    @Override
    public void setUccode(UnicodeChar lc, UnicodeChar uc, boolean global) {

        group.setUccode(lc, uc, global);
    }

    /**
     * This method indicated that a new page is started. The values of first
     * mark, bottom mark, and top mark should be updated properly.
     * 
     * @see org.extex.interpreter.context.Context#startMarks()
     */
    @Override
    public void startMarks() {

        topmarks.putAll(firstmarks);
        topmarks.putAll(bottommarks);
        firstmarks.clear();
        bottommarks.clear();
    }

    /**
     * Get an iterator to enumerate all unit infos.
     * 
     * @return the iterator for unit infos
     * 
     * @see org.extex.interpreter.context.Context#unitIterator()
     */
    @Override
    public Iterator<UnitInfo> unitIterator() {

        return units.iterator();
    }

    /**
     * Remove a registered observer for code change events. Code change events
     * are triggered when the assignment of a macro or active character changes.
     * In this case the appropriate method in the observer is invoked.
     * 
     * @param name the token to be observed. This should be a macro or active
     *        character token.
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.code.CodeObservable#unregisterCodeChangeObserver(org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.observer.code.CodeObserver)
     */
    @Override
    public synchronized void unregisterCodeChangeObserver(Token name,
            CodeObserver observer) {

        List<CodeObserver> observerList = changeCodeObservers.get(name);
        if (null != observerList) {
            observerList.remove(observer);
        }
    }

    /**
     * Remove a registered observer for conditional events. Conditional events
     * are triggered when a conditional is started or ended.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.conditional.ConditionalObservable#unregisterConditionalObserver(org.extex.interpreter.context.observer.conditional.ConditionalObserver)
     */
    @Override
    public synchronized void unregisterConditionalObserver(
            ConditionalObserver observer) {

        if (conditionalObservers != null) {
            conditionalObservers.remove(observer);
            if (conditionalObservers.size() == 0) {
                conditionalObservers = null;
            }
        }
    }

    /**
     * Remove a registered observer for count change events. Count change events
     * are triggered when a value is assigned to a count register. In this case
     * the appropriate method in the observer is invoked.
     * <p>
     * A single count register can be observed by giving a name of the count
     * register to observe. The de-registration removes all instances of the
     * observer for this register. If none is registered then nothing happens.
     * </p>
     * <p>
     * If this name is <code>null</code> then the observer for all registers is
     * removed. Note that the observers for named registers are not effected.
     * They have to be unregistered individually.
     * </p>
     * 
     * @param name the name or the number of the register
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.count.CountObservable#unregisterCountObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.count.CountObserver)
     */
    @Override
    public synchronized void unregisterCountObserver(String name,
            CountObserver observer) {

        List<CountObserver> list = changeCountObservers.get(name);
        if (list != null) {
            while (list.remove(observer)) {
                // just removing the observer is enough
            }
        }
    }

    /**
     * Remove a registered observer for dimen change events. Count change events
     * are triggered when a value is assigned to a dimen register. In this case
     * the appropriate method in the observer is invoked.
     * <p>
     * A single dimen register can be observed by giving a name of the dimen
     * register to observe. The de-registration removes all instances of the
     * observer for this register. If none is registered then nothing happens.
     * </p>
     * <p>
     * If this name is <code>null</code> then the observer for all registers is
     * removed. Note that the observers for named registers are not effected.
     * They have to be unregistered individually.
     * </p>
     * 
     * @param name the name or the number of the register
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.dimen.DimenObservable#unregisterDimenObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.dimen.DimenObserver)
     */
    @Override
    public synchronized void unregisterDimenObserver(String name,
            DimenObserver observer) {

        List<DimenObserver> list = changeDimenObservers.get(name);
        if (list != null) {
            while (list.remove(observer)) {
                // just removing the observer is enough
            }
        }
    }

    /**
     * Remove a registered observer for glue change events. Count change events
     * are triggered when a value is assigned to a glue register. In this case
     * the appropriate method in the observer is invoked.
     * <p>
     * A single glue register can be observed by giving a name of the glue
     * register to observe. The de-registration removes all instances of the
     * observer for this register. If none is registered then nothing happens.
     * </p>
     * <p>
     * If this name is <code>null</code> then the observer for all registers is
     * removed. Note that the observers for named registers are not effected.
     * They have to be unregistered individually.
     * </p>
     * 
     * @param name the name or the number of the register
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.glue.GlueObservable#unregisterGlueObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.glue.GlueObserver)
     */
    @Override
    public synchronized void unregisterGlueObserver(String name,
            GlueObserver observer) {

        List<GlueObserver> list = changeGlueObservers.get(name);
        if (list != null) {
            while (list.remove(observer)) {
                // just removing the observer is enough
            }
        }
    }

    /**
     * Remove a registered observer for group change events. Group change events
     * are triggered when a group is opened or closed. In this case the
     * appropriate method in the observer is invoked.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.group.GroupObservable#unregisterGroupObserver(org.extex.interpreter.context.observer.group.GroupObserver)
     */
    @Override
    public synchronized void unregisterGroupObserver(GroupObserver observer) {

        if (groupObservers != null) {
            groupObservers.remove(observer);
            if (groupObservers.size() == 0) {
                groupObservers = null;
            }
        }
    }

    /**
     * Remove a registered observer for interaction mode change events.
     * Interaction mode change events are triggered when a new value is assigned
     * to the interaction mode. In this case the appropriate method in the
     * observer is invoked.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.interaction.InteractionObservable#unregisterInteractionObserver(org.extex.interpreter.context.observer.interaction.InteractionObserver)
     */
    @Override
    public synchronized void unregisterInteractionObserver(
            InteractionObserver observer) {

        while (changeInteractionObservers.remove(observer)) {
            // just removing the observer is enough
        }
    }

    /**
     * Remove a registered observer for load events. Code change events are
     * triggered when the context is loaded.
     * 
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.load.LoadedObservable#unregisterLoadObserver(org.extex.interpreter.context.observer.load.LoadedObserver)
     */
    @Override
    public void unregisterLoadObserver(LoadedObserver observer) {

        if (loadObservers != null) {
            loadObservers.remove(observer);
            if (loadObservers.size() == 0) {
                loadObservers = null;
            }
        }
    }

    /**
     * Remove a registered observer for tokens change events. Tokens change
     * events are triggered when an assignment to a tokens register is
     * performed. In this case the appropriate method in the observer is
     * invoked.
     * 
     * @param name the token to be observed. This should be a macro or active
     *        character token.
     * @param observer the observer to receive the events
     * 
     * @see org.extex.interpreter.context.observer.tokens.TokensObservable#unregisterTokensChangeObserver(java.lang.String,
     *      org.extex.interpreter.context.observer.tokens.TokensObserver)
     */
    @Override
    public synchronized void unregisterTokensChangeObserver(String name,
            TokensObserver observer) {

        List<TokensObserver> list = changeToksObservers.get(name);
        if (list != null) {
            while (list.remove(observer)) {
                // just removing the observer is enough
            }
        }
    }

}
