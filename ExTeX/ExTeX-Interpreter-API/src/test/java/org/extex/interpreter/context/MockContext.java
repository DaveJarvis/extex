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

import org.extex.color.Color;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.font.CoreFontFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.Direction;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;

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
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>tc</tt> contains the typesetting context.
     */
    private TypesettingContext tc = new TypesettingContextImpl();

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
    public void addUnit(UnitInfo info) {

        // not needed
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
    public void afterGroup(AfterGroupObserver observer) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.scanner.type.token.Token)
     */
    public void afterGroup(Token t) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * This method clears all split marks.
     * 
     * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
     */
    public void clearSplitMarks() {

        // not needed
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextGroup#closeGroup(
     *      org.extex.typesetter.Typesetter, org.extex.interpreter.TokenSource)
     */
    public void closeGroup(Typesetter typesetter, TokenSource source) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#esc(java.lang.String)
     */
    public String esc(String name) {

        return "\\" + name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#esc(
     *      org.extex.scanner.type.token.Token)
     */
    public String esc(Token token) {

        return token.toText();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#escapechar()
     */
    public UnicodeChar escapechar() {

        return UnicodeChar.get('\\');
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#get(java.lang.Object,
     *      java.lang.Object)
     */
    public Object get(Object extension, Object key) {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getAfterassignment()
     */
    public Token getAfterassignment() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextMark#getBottomMark(
     *      java.lang.Object)
     */
    public Tokens getBottomMark(Object name) {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#getBox(java.lang.String)
     */
    public Box getBox(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
     */
    public Catcode getCatcode(UnicodeChar c) {

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextCode#getCode(
     *      org.extex.scanner.type.token.CodeToken)
     */
    public Code getCode(CodeToken t) {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getConditional()
     */
    public Conditional getConditional() {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextCount#getCount(
     *      java.lang.String)
     */
    public Count getCount(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.TypesetterOptions#getCountOption(
     *      java.lang.String)
     */
    public FixedCount getCountOption(String name) {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getDelcode(
     *      org.extex.core.UnicodeChar)
     */
    public MathDelimiter getDelcode(UnicodeChar c) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextDimen#getDimen(
     *      java.lang.String)
     */
    public Dimen getDimen(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.TypesetterOptions#getDimenOption(
     *      java.lang.String)
     */
    public FixedDimen getDimenOption(String name) {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
     */
    public int getErrorCount() {

        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextMark#getFirstMark(java.lang.Object)
     */
    public Tokens getFirstMark(Object name) {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextFont#getFont(java.lang.String)
     */
    public Font getFont(String name) {

        return tc.getFont();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextFont#getFontFactory()
     */
    public CoreFontFactory getFontFactory() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getGlue(java.lang.String)
     */
    public Glue getGlue(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.TypesetterOptions#getGlueOption(
     *      java.lang.String)
     */
    public FixedGlue getGlueOption(String name) {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextGroup#getGroupInfos()
     */
    public GroupInfo[] getGroupInfos() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextGroup#getGroupLevel()
     */
    public long getGroupLevel() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextGroup#getGroupType()
     */
    public GroupType getGroupType() {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getId()
     */
    public String getId() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getIfLevel()
     */
    public long getIfLevel() {

        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextFile#getInFile(java.lang.String)
     */
    public InFile getInFile(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getInteraction()
     */
    public Interaction getInteraction() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getLanguage(java.lang.String)
     */
    public Language getLanguage(String language) throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getLanguageManager()
     */
    public LanguageManager getLanguageManager() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getLccode(org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLccode(UnicodeChar uc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getMagnification()
     */
    public long getMagnification() {

        return 1000;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getMathcode(
     *      org.extex.core.UnicodeChar)
     */
    public MathCode getMathcode(UnicodeChar uc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getMuskip(java.lang.String)
     */
    public Muskip getMuskip(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.scanner.api.Tokenizer#getNamespace()
     */
    public String getNamespace() {

        return Namespace.DEFAULT_NAMESPACE;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextFile#getOutFile(java.lang.String)
     */
    public OutFile getOutFile(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getParshape()
     */
    public ParagraphShape getParshape() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getSfcode(org.extex.core.UnicodeChar)
     */
    public FixedCount getSfcode(UnicodeChar uc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(java.lang.Object)
     */
    public Tokens getSplitBottomMark(Object name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(java.lang.Object)
     */
    public Tokens getSplitFirstMark(Object name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getStandardTokenStream()
     */
    public TokenStream getStandardTokenStream() {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getTokenFactory()
     */
    public TokenFactory getTokenFactory() {

        return tokenFactory;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getTokenizer()
     */
    public Tokenizer getTokenizer() {

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextTokens#getToks(java.lang.String)
     */
    public Tokens getToks(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextTokens#getToksOrNull(java.lang.String)
     */
    public Tokens getToksOrNull(String name) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextMark#getTopMark(java.lang.Object)
     */
    public Tokens getTopMark(Object name) {

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getTypesettingContext()
     */
    public TypesettingContext getTypesettingContext() {

        return tc;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.TypesetterOptions#getTypesettingContextFactory()
     */
    public TypesettingContextFactory getTypesettingContextFactory() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.Context#getUccode(org.extex.core.UnicodeChar)
     */
    public UnicodeChar getUccode(UnicodeChar lc) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextErrorCount#incrementErrorCount()
     */
    public int incrementErrorCount() {

        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextGroup#isGlobalGroup()
     */
    public boolean isGlobalGroup() {

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.ContextGroup#openGroup(
     *      org.extex.interpreter.context.group.GroupType,
     *      org.extex.core.Locator, org.extex.scanner.type.token.Token)
     */
    public void openGroup(GroupType id, Locator locator, Token start)
            throws ConfigurationException,
                HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#popConditional()
     */
    public Conditional popConditional() throws HelpingException {

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
     *      org.extex.core.Locator, boolean, org.extex.interpreter.type.Code,
     *      long, boolean)
     */
    public void pushConditional(Locator locator, boolean value, Code primitive,
            long branch, boolean neg) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#pushDirection(org.extex.typesetter.tc.Direction)
     */
    public void pushDirection(Direction dir) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set( org.extex.color.Color,
     *      boolean)
     */
    public void set(Color color, boolean global) throws ConfigurationException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.typesetter.tc.Direction, boolean)
     */
    public void set(Direction direction, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.typesetter.tc.font.Font, boolean)
     */
    public void set(Font font, boolean global) {

        ((TypesettingContextImpl) tc).setFont(font);
    }

    /**
     * @see org.extex.interpreter.context.Context#set(
     *      org.extex.language.Language, boolean)
     */
    public void set(Language language, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set( java.lang.Object,
     *      java.lang.Object, java.lang.Object, boolean)
     */
    public void set(Object extension, Object key, Object value, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#set(org.extex.typesetter.tc.TypesettingContext,
     *      boolean)
     */
    public void set(TypesettingContext context, boolean global) {

        tc = context;
    }

    /**
     * @see org.extex.interpreter.context.Context#setAfterassignment(org.extex.scanner.type.token.Token)
     */
    public void setAfterassignment(Token token) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.context.Context#setBox(java.lang.String,
     *      org.extex.interpreter.type.box.Box, boolean)
     */
    public void setBox(String name, Box value, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setCatcode(org.extex.core.UnicodeChar,
     *      org.extex.scanner.type.Catcode, boolean)
     */
    public void setCatcode(UnicodeChar c, Catcode cc, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextCode#setCode(
     *      org.extex.scanner.type.token.CodeToken,
     *      org.extex.interpreter.type.Code, boolean)
     */
    public void setCode(CodeToken t, Code code, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextCount#setCount(java.lang.String,
     *      long, boolean)
     */
    public void setCount(String name, long value, boolean global)
            throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.TypesetterOptions#setCountOption(
     *      java.lang.String, long)
     */
    public void setCountOption(String name, long value) {

        // not needed
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setDelcode(org.extex.core.UnicodeChar,
     *      MathDelimiter, boolean)
     */
    public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
            boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String,
     *      org.extex.core.dimen.Dimen, boolean)
     */
    public void setDimen(String name, Dimen value, boolean global)
            throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String,
     *      long, boolean)
     */
    public void setDimen(String name, long value, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextFont#setFont(java.lang.String,
     *      org.extex.typesetter.tc.font.Font, boolean)
     */
    public void setFont(String name, Font font, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextFont#setFontFactory(
     *      org.extex.font.CoreFontFactory)
     */
    public void setFontFactory(CoreFontFactory fontFactory) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setGlue(java.lang.String,
     *      org.extex.core.glue.Glue, boolean)
     */
    public void setGlue(String name, Glue value, boolean global)
            throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setId(java.lang.String)
     */
    public void setId(String id) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextFile#setInFile(
     *      java.lang.String, org.extex.scanner.type.file.InFile, boolean)
     */
    public void setInFile(String name, InFile file, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextInteraction#setInteraction(
     *      org.extex.interpreter.interaction.Interaction)
     */
    public void setInteraction(Interaction interaction) throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setLanguageManager(
     *      org.extex.language.LanguageManager)
     */
    public void setLanguageManager(LanguageManager manager) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setLccode(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
     */
    public void setLccode(UnicodeChar uc, UnicodeChar lc, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setMagnification(long,
     *      boolean)
     */
    public void setMagnification(long mag, boolean lock)
            throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextMark#setMark( java.lang.Object,
     *      org.extex.scanner.type.tokens.Tokens)
     */
    public void setMark(Object name, Tokens mark) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setMathcode(
     *      org.extex.core.UnicodeChar, MathCode, boolean)
     */
    public void setMathcode(UnicodeChar uc, MathCode code, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setMuskip(java.lang.String,
     *      org.extex.core.muskip.Muskip, boolean)
     */
    public void setMuskip(String name, Muskip value, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setNamespace(java.lang.String,
     *      boolean)
     */
    public void setNamespace(String namespace, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextFile#setOutFile(
     *      java.lang.String, org.extex.scanner.type.file.OutFile, boolean)
     */
    public void setOutFile(String name, OutFile file, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setParshape(org.extex.typesetter.paragraphBuilder.ParagraphShape)
     */
    public void setParshape(ParagraphShape shape) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setSfcode(org.extex.core.UnicodeChar,
     *      org.extex.core.count.Count, boolean)
     */
    public void setSfcode(UnicodeChar uc, Count code, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.ContextMark#setSplitMark(
     *      java.lang.Object, org.extex.scanner.type.tokens.Tokens)
     */
    public void setSplitMark(Object name, Tokens mark) {

        // not needed
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setStandardTokenStream(org.extex.scanner.api.TokenStream)
     */
    public void setStandardTokenStream(TokenStream standardTokenStream) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setTokenFactory(
     *      org.extex.scanner.type.token.TokenFactory)
     */
    public void setTokenFactory(TokenFactory factory) {

        tokenFactory = factory;
    }

    /**
     * @see org.extex.interpreter.context.ContextTokens#setToks(
     *      java.lang.String, org.extex.scanner.type.tokens.Tokens, boolean)
     */
    public void setToks(String name, Tokens toks, boolean global)
            throws HelpingException {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#setUccode(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
     */
    public void setUccode(UnicodeChar lc, UnicodeChar uc, boolean global) {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#startMarks()
     */
    public void startMarks() {

        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.Context#unitIterator()
     */
    public Iterator<UnitInfo> unitIterator() {

        return null;
    }

}
