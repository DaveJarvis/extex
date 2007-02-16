/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.context;

import java.util.Iterator;

import org.extex.font.CoreFontFactory;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.Tokenizer;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.context.tc.Direction;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextFactory;
import org.extex.interpreter.context.tc.TypesettingContextImpl;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.file.InFile;
import org.extex.interpreter.type.file.OutFile;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.interpreter.type.math.MathCode;
import org.extex.interpreter.type.math.MathDelimiter;
import org.extex.interpreter.type.muskip.Muskip;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.scanner.TokenStream;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.util.exception.GeneralException;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This mock implementation of a context does nothing useful but provide dummy
 * methods. It is meant as a base for derived mock implementations in test
 * classes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4491 $
 */
public class MockContext implements Context, TypesetterOptions {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field <tt>tc</tt> contains the ...
     */
    private transient TypesettingContext tc = new TypesettingContextImpl();

    /**
     * The field <tt>tokenFactory</tt> contains the token factory.
     */
    private transient TokenFactory tokenFactory = new TokenFactoryImpl();

    /**
     * Creates a new object.
     */
    public MockContext() {

        super();
    }

    /**
     * Add a unit to the list of loaded units. The units can be notified when
     * the context is loaded from a format.
     *
     * @param info the info of the unit loaded
     *
     * @see org.extex.interpreter.context.Context#addUnit(
     *      org.extex.interpreter.unit.UnitInfo)
     */
    public void addUnit(final UnitInfo info) {

    }

    /**
     * Register a observer to be called at the end of the group. The end of the
     * group is reached when the group is closed.
     *
     * @param observer the observer to register
     *
     * @see org.extex.interpreter.context.ContextGroup#afterGroup(
     *      org.extex.interpreter.context.observer.group.AfterGroupObserver)
     */
    public void afterGroup(final AfterGroupObserver observer) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * Add a token to the tokens inserted after the group has been closed.
     *
     * @param t the token to add
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.context.ContextGroup#afterGroup(
     *      org.extex.scanner.type.token.Token)
     */
    public void afterGroup(final Token t) throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * This method clears all split marks.
     *
     * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
     */
    public void clearSplitMarks() {

    }

    /**
     * @see org.extex.interpreter.context.ContextGroup#closeGroup(
     *      org.extex.typesetter.Typesetter,
     *      org.extex.interpreter.TokenSource)
     */
    public void closeGroup(final Typesetter typesetter, final TokenSource source)
            throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#esc(java.lang.String)
     */
    public String esc(final String name) {

        return "\\" + name;
    }

    /**
     * @see org.extex.interpreter.context.Context#esc(
     *      org.extex.scanner.type.token.Token)
     */
    public String esc(final Token token) {

        return token.toText();
    }

    /**
     * @see org.extex.interpreter.context.Context#escapechar()
     */
    public UnicodeChar escapechar() {

        return UnicodeChar.get('\\');
    }

    /**
     * @see org.extex.interpreter.context.ContextCode#expand(
     *      org.extex.interpreter.type.tokens.Tokens,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens expand(final Tokens tokens, final Typesetter typesetter)
            throws GeneralException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#get(
     *      java.lang.Object,
     *      java.lang.Object)
     */
    public Object get(final Object extension, final Object key) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getAfterassignment()
     */
    public Token getAfterassignment() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#getBottomMark(
     *      java.lang.Object)
     */
    public Tokens getBottomMark(final Object name) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getBox(java.lang.String)
     */
    public Box getBox(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.Tokenizer#getCatcode(
     *      org.extex.type.UnicodeChar)
     */
    public Catcode getCatcode(final UnicodeChar c) {

        if (c.isLetter()) {
            return Catcode.LETTER;
        }
        switch (c.getCodePoint()) {
            case ' ':
                return Catcode.SPACE;
            case '{':
                return Catcode.LEFTBRACE;
            case '}':
                return Catcode.RIGHTBRACE;
            case '\\':
                return Catcode.ESCAPE;
            default:
                return Catcode.OTHER;
        }
    }

    /**
     * @see org.extex.interpreter.context.ContextCode#getCode(
     *      org.extex.scanner.type.token.CodeToken)
     */
    public Code getCode(final CodeToken t) throws InterpreterException {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getConditional()
     */
    public Conditional getConditional() {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.ContextCount#getCount(
     *      java.lang.String)
     */
    public Count getCount(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.typesetter.TypesetterOptions#getCountOption(
     *      java.lang.String)
     */
    public FixedCount getCountOption(final String name) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getDelcode(
     *      org.extex.type.UnicodeChar)
     */
    public MathDelimiter getDelcode(final UnicodeChar c) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextDimen#getDimen(
     *      java.lang.String)
     */
    public Dimen getDimen(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.typesetter.TypesetterOptions#getDimenOption(
     *      java.lang.String)
     */
    public FixedDimen getDimenOption(final String name) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
     */
    public int getErrorCount() {

        return 0;
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#getFirstMark(java.lang.Object)
     */
    public Tokens getFirstMark(final Object name) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.ContextFont#getFont(
     *      java.lang.String)
     */
    public Font getFont(final String name) {

        return tc.getFont();
    }

    /**
     * @see org.extex.interpreter.context.ContextFont#getFontFactory()
     */
    public CoreFontFactory getFontFactory() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getGlue(java.lang.String)
     */
    public Glue getGlue(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.typesetter.TypesetterOptions#getGlueOption(
     *      java.lang.String)
     */
    public FixedGlue getGlueOption(final String name) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.ContextGroup#getGroupInfos()
     */
    public GroupInfo[] getGroupInfos() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextGroup#getGroupLevel()
     */
    public long getGroupLevel() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextGroup#getGroupType()
     */
    public GroupType getGroupType() {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getId()
     */
    public String getId() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getIfLevel()
     */
    public long getIfLevel() {

        return 0;
    }

    /**
     * @see org.extex.interpreter.context.ContextFile#getInFile(java.lang.String)
     */
    public InFile getInFile(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getInteraction()
     */
    public Interaction getInteraction() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getLanguage(java.lang.String)
     */
    public Language getLanguage(final String language)
            throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getLanguageManager()
     */
    public LanguageManager getLanguageManager() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getLccode(org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLccode(final UnicodeChar uc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getMagnification()
     */
    public long getMagnification() {

        return 1000;
    }

    /**
     * @see org.extex.interpreter.context.Context#getMathcode(
     *      org.extex.type.UnicodeChar)
     */
    public MathCode getMathcode(final UnicodeChar uc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getMuskip(java.lang.String)
     */
    public Muskip getMuskip(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.Tokenizer#getNamespace()
     */
    public String getNamespace() {

        return Namespace.DEFAULT_NAMESPACE;
    }

    /**
     * @see org.extex.interpreter.context.ContextFile#getOutFile(java.lang.String)
     */
    public OutFile getOutFile(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getParshape()
     */
    public ParagraphShape getParshape() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getSfcode(org.extex.type.UnicodeChar)
     */
    public Count getSfcode(final UnicodeChar uc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(java.lang.Object)
     */
    public Tokens getSplitBottomMark(final Object name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(java.lang.Object)
     */
    public Tokens getSplitFirstMark(final Object name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getStandardTokenStream()
     */
    public TokenStream getStandardTokenStream() {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getTokenFactory()
     */
    public TokenFactory getTokenFactory() {

        return tokenFactory;
    }

    /**
     * @see org.extex.interpreter.context.Context#getTokenizer()
     */
    public Tokenizer getTokenizer() {

        return this;
    }

    /**
     * @see org.extex.interpreter.context.ContextTokens#getToks(java.lang.String)
     */
    public Tokens getToks(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextTokens#getToksOrNull(java.lang.String)
     */
    public Tokens getToksOrNull(final String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#getTopMark(java.lang.Object)
     */
    public Tokens getTopMark(final Object name) {

        return null;
    }

    /**
     * @see org.extex.interpreter.context.Context#getTypesettingContext()
     */
    public TypesettingContext getTypesettingContext() {

        return tc;
    }

    /**
     * @see org.extex.typesetter.TypesetterOptions#getTypesettingContextFactory()
     */
    public TypesettingContextFactory getTypesettingContextFactory() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#getUccode(org.extex.type.UnicodeChar)
     */
    public UnicodeChar getUccode(final UnicodeChar lc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextErrorCount#incrementErrorCount()
     */
    public int incrementErrorCount() {

        return 0;
    }

    /**
     * @see org.extex.interpreter.context.ContextGroup#isGlobalGroup()
     */
    public boolean isGlobalGroup() {

        return false;
    }

    /**
     * @see org.extex.interpreter.context.ContextGroup#openGroup(
     *      org.extex.interpreter.context.group.GroupType,
     *      org.extex.type.Locator,
     *      org.extex.scanner.type.token.Token)
     */
    public void openGroup(GroupType id, Locator locator, Token start)
            throws ConfigurationException,
                InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#popConditional()
     */
    public Conditional popConditional() throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#popDirection()
     */
    public Direction popDirection() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#pushConditional(
     *      org.extex.type.Locator,
     *      boolean,
     *      org.extex.interpreter.type.Code,
     *      long,
     *      boolean)
     */
    public void pushConditional(final Locator locator, final boolean value,
            final Code primitive, final long branch, final boolean neg) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#pushDirection(org.extex.interpreter.context.tc.Direction)
     */
    public void pushDirection(final Direction dir) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.interpreter.context.Color,
     *      boolean)
     */
    public void set(final Color color, final boolean global)
            throws ConfigurationException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.interpreter.context.tc.Direction,
     *      boolean)
     */
    public void set(final Direction direction, final boolean global)
            throws ConfigurationException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.interpreter.type.font.Font,
     *      boolean)
     */
    public void set(final Font font, final boolean global)
            throws ConfigurationException {

        ((TypesettingContextImpl) tc).setFont(font);
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.language.Language,
     *      boolean)
     */
    public void set(final Language language, final boolean global)
            throws ConfigurationException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      java.lang.Object,
     *      java.lang.Object,
     *      java.lang.Object,
     *      boolean)
     */
    public void set(final Object extension, final Object key,
            final Object value, final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.tc.TypesettingContext, boolean)
     */
    public void set(final TypesettingContext context, final boolean global) {

        tc = context;
    }

    /**
     * @see org.extex.interpreter.context.Context#setAfterassignment(org.extex.scanner.type.token.Token)
     */
    public void setAfterassignment(final Token token) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setBox(java.lang.String, org.extex.interpreter.type.box.Box, boolean)
     */
    public void setBox(final String name, final Box value, final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setCatcode(org.extex.type.UnicodeChar, org.extex.scanner.type.Catcode, boolean)
     */
    public void setCatcode(final UnicodeChar c, final Catcode cc,
            final boolean global) throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextCode#setCode(
     *      org.extex.scanner.type.token.CodeToken,
     *      org.extex.interpreter.type.Code,
     *      boolean)
     */
    public void setCode(final CodeToken t, final Code code, final boolean global)
            throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextCount#setCount(java.lang.String, long, boolean)
     */
    public void setCount(final String name, final long value,
            final boolean global) throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.typesetter.TypesetterOptions#setCountOption(
     *      java.lang.String,
     *      long)
     */
    public void setCountOption(final String name, final long value)
            throws GeneralException {

    }

    /**
     * @see org.extex.interpreter.context.Context#setDelcode(org.extex.type.UnicodeChar, MathDelimiter, boolean)
     */
    public void setDelcode(final UnicodeChar c, final MathDelimiter delimiter,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String, org.extex.interpreter.type.dimen.Dimen, boolean)
     */
    public void setDimen(final String name, final Dimen value,
            final boolean global) throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String, long, boolean)
     */
    public void setDimen(final String name, final long value,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextFont#setFont(java.lang.String, org.extex.interpreter.type.font.Font, boolean)
     */
    public void setFont(final String name, final Font font, final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextFont#setFontFactory(
     *      org.extex.font.CoreFontFactory)
     */
    public void setFontFactory(final CoreFontFactory fontFactory) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setGlue(java.lang.String, org.extex.interpreter.type.glue.Glue, boolean)
     */
    public void setGlue(final String name, final Glue value,
            final boolean global) throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setId(java.lang.String)
     */
    public void setId(final String id) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextFile#setInFile(java.lang.String, org.extex.interpreter.type.file.InFile, boolean)
     */
    public void setInFile(final String name, final InFile file,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextInteraction#setInteraction(
     *      org.extex.interpreter.interaction.Interaction)
     */
    public void setInteraction(final Interaction interaction)
            throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setLanguageManager(
     *      org.extex.language.LanguageManager)
     */
    public void setLanguageManager(final LanguageManager manager) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setLccode(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      boolean)
     */
    public void setLccode(final UnicodeChar uc, final UnicodeChar lc,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setMagnification(long, boolean)
     */
    public void setMagnification(final long mag, boolean lock)
            throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#setMark(
     *      java.lang.Object,
     *      org.extex.interpreter.type.tokens.Tokens)
     */
    public void setMark(final Object name, final Tokens mark) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setMathcode(
     *      org.extex.type.UnicodeChar,
     *      MathCode,
     *      boolean)
     */
    public void setMathcode(final UnicodeChar uc, final MathCode code,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setMuskip(java.lang.String, org.extex.interpreter.type.muskip.Muskip, boolean)
     */
    public void setMuskip(final String name, final Muskip value,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setNamespace(java.lang.String, boolean)
     */
    public void setNamespace(final String namespace, final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextFile#setOutFile(java.lang.String, org.extex.interpreter.type.file.OutFile, boolean)
     */
    public void setOutFile(final String name, final OutFile file,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setParshape(org.extex.typesetter.paragraphBuilder.ParagraphShape)
     */
    public void setParshape(final ParagraphShape shape) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setSfcode(org.extex.type.UnicodeChar, org.extex.interpreter.type.count.Count, boolean)
     */
    public void setSfcode(final UnicodeChar uc, final Count code,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.ContextMark#setSplitMark(
     *      java.lang.Object,
     *      org.extex.interpreter.type.tokens.Tokens)
     */
    public void setSplitMark(final Object name, final Tokens mark) {

    }

    /**
     * @see org.extex.interpreter.context.Context#setStandardTokenStream(org.extex.scanner.TokenStream)
     */
    public void setStandardTokenStream(final TokenStream standardTokenStream) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setTokenFactory(
     *      org.extex.scanner.type.token.TokenFactory)
     */
    public void setTokenFactory(final TokenFactory factory) {

        tokenFactory = factory;
    }

    /**
     * @see org.extex.interpreter.context.ContextTokens#setToks(
     *      java.lang.String,
     *      org.extex.interpreter.type.tokens.Tokens, boolean)
     */
    public void setToks(final String name, final Tokens toks,
            final boolean global) throws InterpreterException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setTypesettingContext(
     *       org.extex.interpreter.context.tc.TypesettingContext)
     */
    public void setTypesettingContext(final TypesettingContext context) {

        tc = context;
    }

    /**
     * @see org.extex.interpreter.context.Context#setUccode(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      boolean)
     */
    public void setUccode(final UnicodeChar lc, final UnicodeChar uc,
            final boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#startMarks()
     */
    public void startMarks() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#unitIterator()
     */
    public Iterator unitIterator() {

        // TODO gene: unitIterator unimplemented
        return null;
    }

}
