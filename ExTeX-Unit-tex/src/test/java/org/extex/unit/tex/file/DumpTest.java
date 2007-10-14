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

package org.extex.unit.tex.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Iterator;

import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;
import org.extex.color.Color;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.InterpreterPanicException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.font.CoreFontFactory;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.loader.SerialLoader;
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
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.test.NoFlagsPrimitiveTester;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.Direction;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\dump</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class DumpTest extends NoFlagsPrimitiveTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(DumpTest.class);
    }

    /**
     * Creates a new object.
     */
    public DumpTest() {

        super("dump", "", "", "Beginning to dump on file ."
                + System.getProperty("file.separator") + "texput.fmt\n");
        new File("texput.fmt").delete();
    }

    /**
     * <testcase primitive="\dump"> Test case checking that <tt>\dump</tt>
     * writes a format file. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertOutput(// --- input code ---
            "\\dump \\end",
            // --- log message ---
            "Beginning to dump on file ."
                    + System.getProperty("file.separator") + "texput.fmt\n",
            // --- output message ---
            "");
        File fmt = new File("texput.fmt");
        assertTrue(fmt.exists());
        fmt.delete();
    }

    /**
     * <testcase primitive="\dump"> Test case checking that <tt>\dump</tt>
     * complains about a missing output stream factory if none is set.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test100() throws Exception {

        try {
            ControlSequenceToken cs =
                    (ControlSequenceToken) new TokenFactoryImpl().createToken(
                        Catcode.ESCAPE, UnicodeChar.get('\\'), "dump",
                        Namespace.DEFAULT_NAMESPACE);
            new Dump(cs).execute(Flags.NONE, new Context() {

                /**
                 * The field <tt>serialVersionUID</tt> contains the version
                 * number.
                 */
                private static final long serialVersionUID = 1L;

                /**
                 * @see org.extex.interpreter.context.Context#addUnit(
                 *      org.extex.interpreter.unit.UnitInfo)
                 */
                public void addUnit(UnitInfo info) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(
                 *      org.extex.interpreter.context.observer.group.AfterGroupObserver)
                 */
                public void afterGroup(AfterGroupObserver observer) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(
                 *      org.extex.scanner.type.token.Token)
                 */
                public void afterGroup(Token t) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
                 */
                public void clearSplitMarks() {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#closeGroup(
                 *      org.extex.typesetter.Typesetter,
                 *      org.extex.interpreter.TokenSource)
                 */
                public void closeGroup(Typesetter typesetter, TokenSource source) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(
                 *      java.lang.String)
                 */
                public String esc(String name) {

                    return name;
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(
                 *      org.extex.scanner.type.token.Token)
                 */
                public String esc(Token token) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#escapechar()
                 */
                public UnicodeChar escapechar() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#get(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Object get(Object extension, Object key) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getAfterassignment()
                 */
                public Token getAfterassignment() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getBottomMark(
                 *      java.lang.Object)
                 */
                public Tokens getBottomMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getBox(
                 *      java.lang.String)
                 */
                public Box getBox(String name) {

                    return null;
                }

                /**
                 * @see org.extex.scanner.api.Tokenizer#getCatcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public Catcode getCatcode(UnicodeChar c) {

                    return null;
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
                 * @see org.extex.interpreter.context.Context#getConditional()
                 */
                public Conditional getConditional() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#getCount(
                 *      java.lang.String)
                 */
                public Count getCount(String name) {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.PageContext#getCountOption(
                 *      java.lang.String)
                 */
                public FixedCount getCountOption(String name) {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getDelcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public MathDelimiter getDelcode(UnicodeChar c) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#getDimen(
                 *      java.lang.String)
                 */
                public Dimen getDimen(String name) {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.PageContext#getDimenOption(
                 *      java.lang.String)
                 */
                public FixedDimen getDimenOption(String name) {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
                 */
                public int getErrorCount() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getFirstMark(
                 *      java.lang.Object)
                 */
                public Tokens getFirstMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFont(
                 *      java.lang.String)
                 */
                public Font getFont(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFontFactory()
                 */
                public CoreFontFactory getFontFactory() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getGlue(
                 *      java.lang.String)
                 */
                public Glue getGlue(String name) {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.PageContext#getGlueOption(
                 *      java.lang.String)
                 */
                public FixedGlue getGlueOption(String name) {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#getGroupInfos()
                 */
                public GroupInfo[] getGroupInfos() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#getGroupLevel()
                 */
                public long getGroupLevel() {

                    return 0;
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

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getIfLevel()
                 */
                public long getIfLevel() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getInFile(
                 *      java.lang.String)
                 */
                public InFile getInFile(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#getInteraction()
                 */
                public Interaction getInteraction() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getLanguage(
                 *      java.lang.String)
                 */
                public Language getLanguage(String language) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getLanguageManager()
                 */
                public LanguageManager getLanguageManager() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getLccode(
                 *      org.extex.core.UnicodeChar)
                 */
                public UnicodeChar getLccode(UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMagnification()
                 */
                public long getMagnification() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMathcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public MathCode getMathcode(UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMuskip(
                 *      java.lang.String)
                 */
                public Muskip getMuskip(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getNamespace()
                 */
                public String getNamespace() {

                    return "";
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getOutFile(
                 *      java.lang.String)
                 */
                public OutFile getOutFile(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getParshape()
                 */
                public ParagraphShape getParshape() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getSfcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public FixedCount getSfcode(UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(
                 *      java.lang.Object)
                 */
                public Tokens getSplitBottomMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(
                 *      java.lang.Object)
                 */
                public Tokens getSplitFirstMark(Object name) {

                    return null;
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

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getTokenizer()
                 */
                public Tokenizer getTokenizer() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToks(
                 *      java.lang.String)
                 */
                public Tokens getToks(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToksOrNull(
                 *      java.lang.String)
                 */
                public Tokens getToksOrNull(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getTopMark(
                 *      java.lang.Object)
                 */
                public Tokens getTopMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getTypesettingContext()
                 */
                public TypesettingContext getTypesettingContext() {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.PageContext#getTypesettingContextFactory()
                 */
                public TypesettingContextFactory getTypesettingContextFactory() {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getUccode(
                 *      org.extex.core.UnicodeChar)
                 */
                public UnicodeChar getUccode(UnicodeChar lc) {

                    return null;
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

                    return true;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#openGroup(
                 *      org.extex.interpreter.context.group.GroupType,
                 *      org.extex.core.Locator,
                 *      org.extex.scanner.type.token.Token)
                 */
                public void openGroup(GroupType id, Locator locator, Token start)
                        throws HelpingException {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#popConditional()
                 */
                public Conditional popConditional() throws HelpingException {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#popDirection()
                 */
                public Direction popDirection() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushConditional(
                 *      org.extex.core.Locator, boolean,
                 *      org.extex.interpreter.type.Code, long, boolean)
                 */
                public void pushConditional(Locator locator, boolean value,
                        Code primitive, long branch, boolean neg) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushDirection(
                 *      org.extex.typesetter.tc.Direction)
                 */
                public void pushDirection(Direction dir) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.color.Color, boolean)
                 */
                public void set(Color color, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.typesetter.tc.Direction, boolean)
                 */
                public void set(Direction direction, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.typesetter.tc.font.Font, boolean)
                 */
                public void set(Font font, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.language.Language, boolean)
                 */
                public void set(Language language, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      java.lang.Object, java.lang.Object, java.lang.Object,
                 *      boolean)
                 */
                public void set(Object extension, Object key, Object value,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.typesetter.tc.TypesettingContext, boolean)
                 */
                public void set(TypesettingContext context, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setAfterassignment(
                 *      org.extex.scanner.type.token.Token)
                 */
                public void setAfterassignment(Token token) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setBox(
                 *      java.lang.String, org.extex.interpreter.type.box.Box,
                 *      boolean)
                 */
                public void setBox(String name, Box value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setCatcode(
                 *      org.extex.core.UnicodeChar,
                 *      org.extex.scanner.type.Catcode, boolean)
                 */
                public void setCatcode(UnicodeChar c, Catcode catcode,
                        boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.ContextCode#setCode(
                 *      org.extex.scanner.type.token.CodeToken,
                 *      org.extex.interpreter.type.Code, boolean)
                 */
                public void setCode(CodeToken t, Code code, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#setCount(
                 *      java.lang.String, long, boolean)
                 */
                public void setCount(String name, long value, boolean global)
                        throws HelpingException {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.PageContext#setCountOption(
                 *      java.lang.String, long)
                 */
                public void setCountOption(String name, long value)
                        throws GeneralException {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setDelcode(
                 *      org.extex.core.UnicodeChar,
                 *      org.extex.typesetter.type.math.MathDelimiter, boolean)
                 */
                public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(
                 *      java.lang.String, org.extex.core.dimen.Dimen, boolean)
                 */
                public void setDimen(String name, Dimen value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(
                 *      java.lang.String, long, boolean)
                 */
                public void setDimen(String name, long value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFont(
                 *      java.lang.String, org.extex.typesetter.tc.font.Font,
                 *      boolean)
                 */
                public void setFont(String name, Font font, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFontFactory(
                 *      org.extex.font.CoreFontFactory)
                 */
                public void setFontFactory(CoreFontFactory fontFactory) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setGlue(
                 *      java.lang.String, org.extex.core.glue.Glue, boolean)
                 */
                public void setGlue(String name, Glue value, boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setId(
                 *      java.lang.String)
                 */
                public void setId(String id) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setInFile(
                 *      java.lang.String, org.extex.scanner.type.file.InFile,
                 *      boolean)
                 */
                public void setInFile(String name, InFile file, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#setInteraction(
                 *      org.extex.interpreter.interaction.Interaction)
                 */
                public void setInteraction(Interaction interaction) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setLanguageManager(
                 *      org.extex.language.LanguageManager)
                 */
                public void setLanguageManager(LanguageManager manager) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setLccode(
                 *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar,
                 *      boolean)
                 */
                public void setLccode(UnicodeChar uc, UnicodeChar lc,
                        boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setMagnification(
                 *      long, boolean)
                 */
                public void setMagnification(long mag, boolean lock) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setMark(
                 *      java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setMark(Object name, Tokens mark) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setMathcode(
                 *      org.extex.core.UnicodeChar,
                 *      org.extex.typesetter.type.math.MathCode, boolean)
                 */
                public void setMathcode(UnicodeChar uc, MathCode code,
                        boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setMuskip(
                 *      java.lang.String, org.extex.core.muskip.Muskip, boolean)
                 */
                public void setMuskip(String name, Muskip value, boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setNamespace(
                 *      java.lang.String, boolean)
                 */
                public void setNamespace(String namespace, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setOutFile(
                 *      java.lang.String, org.extex.scanner.type.file.OutFile,
                 *      boolean)
                 */
                public void setOutFile(String name, OutFile file, boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setParshape(
                 *      org.extex.typesetter.paragraphBuilder.ParagraphShape)
                 */
                public void setParshape(ParagraphShape shape) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setSfcode(
                 *      org.extex.core.UnicodeChar, org.extex.core.count.Count,
                 *      boolean)
                 */
                public void setSfcode(UnicodeChar uc, Count code, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setSplitMark(
                 *      java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setSplitMark(Object name, Tokens mark) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setStandardTokenStream(
                 *      org.extex.scanner.api.TokenStream)
                 */
                public void setStandardTokenStream(
                        TokenStream standardTokenStream) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setTokenFactory(
                 *      org.extex.scanner.type.token.TokenFactory)
                 */
                public void setTokenFactory(TokenFactory factory) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#setToks(
                 *      java.lang.String, org.extex.scanner.type.tokens.Tokens,
                 *      boolean)
                 */
                public void setToks(String name, Tokens toks, boolean global) {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#setUccode(
                 *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar,
                 *      boolean)
                 */
                public void setUccode(UnicodeChar lc, UnicodeChar uc,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#startMarks()
                 */
                public void startMarks() {

                    // not needed
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.context.Context#unitIterator()
                 */
                public Iterator<UnitInfo> unitIterator() {

                    return null;
                }

            }, null, null);
            assertFalse(true);
        } catch (RuntimeException e) {
            assertEquals("Missing output stream factory", e.getMessage());
        }
    }

    /**
     * <testcase primitive="\dump"> Test case checking that <tt>\dump</tt>
     * needs <tt>\jobname</tt> containing the target file. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test101() throws Exception {

        try {
            ControlSequenceToken cs =
                    (ControlSequenceToken) new TokenFactoryImpl().createToken(
                        Catcode.ESCAPE, UnicodeChar.get('\\'), "dump",
                        Namespace.DEFAULT_NAMESPACE);
            Dump dump = new Dump(cs);
            dump.setOutputStreamFactory(new OutputStreamFactory() {

                /**
                 * @see org.extex.backend.outputStream.OutputStreamFactory#getOutputStream(
                 *      java.lang.String, java.lang.String)
                 */
                public OutputStream getOutputStream(String name, String type) {

                    return null;
                }

                /**
                 * @see org.extex.backend.outputStream.OutputStreamFactory#register(
                 *      org.extex.backend.outputStream.OutputStreamObserver)
                 */
                public void register(OutputStreamObserver observer) {

                    // not needed
                }

                /**
                 * @see org.extex.backend.outputStream.OutputStreamFactory#setExtension(
                 *      java.lang.String)
                 */
                public void setExtension(String extension) {

                    // not needed
                }
            });
            dump.execute(Flags.NONE, new Context() {

                /**
                 * The field <tt>serialVersionUID</tt> contains the version
                 * number.
                 */
                private static final long serialVersionUID = 1L;

                /**
                 * @see org.extex.interpreter.context.Context#addUnit(
                 *      org.extex.interpreter.unit.UnitInfo)
                 */
                public void addUnit(UnitInfo info) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.interpreter.context.observer.group.AfterGroupObserver)
                 */
                public void afterGroup(AfterGroupObserver observer) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.scanner.type.token.Token)
                 */
                public void afterGroup(Token t) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
                 */
                public void clearSplitMarks() {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#closeGroup(org.extex.typesetter.Typesetter,
                 *      org.extex.interpreter.TokenSource)
                 */
                public void closeGroup(Typesetter typesetter, TokenSource source) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(
                 *      java.lang.String)
                 */
                public String esc(String name) {

                    return name;
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(
                 *      org.extex.scanner.type.token.Token)
                 */
                public String esc(Token token) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#escapechar()
                 */
                public UnicodeChar escapechar() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#get(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Object get(Object extension, Object key) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getAfterassignment()
                 */
                public Token getAfterassignment() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getBottomMark(
                 *      java.lang.Object)
                 */
                public Tokens getBottomMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getBox(
                 *      java.lang.String)
                 */
                public Box getBox(String name) {

                    return null;
                }

                /**
                 * @see org.extex.scanner.api.Tokenizer#getCatcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public Catcode getCatcode(UnicodeChar c) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCode#getCode(
                 *      org.extex.scanner.type.token.CodeToken)
                 */
                public Code getCode(CodeToken t) {

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
                public Count getCount(String name) {

                    return null;
                }

                public FixedCount getCountOption(String name) {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getDelcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public MathDelimiter getDelcode(UnicodeChar c) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#getDimen(java.lang.String)
                 */
                public Dimen getDimen(String name) {

                    return null;
                }

                public FixedDimen getDimenOption(String name) {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
                 */
                public int getErrorCount() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getFirstMark(
                 *      java.lang.Object)
                 */
                public Tokens getFirstMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFont(java.lang.String)
                 */
                public Font getFont(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFontFactory()
                 */
                public CoreFontFactory getFontFactory() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getGlue(
                 *      java.lang.String)
                 */
                public Glue getGlue(String name) {

                    return null;
                }

                public FixedGlue getGlueOption(String name) {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#getGroupInfos()
                 */
                public GroupInfo[] getGroupInfos() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#getGroupLevel()
                 */
                public long getGroupLevel() {

                    return 0;
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

                    return null;
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
                public InFile getInFile(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#getInteraction()
                 */
                public Interaction getInteraction() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getLanguage(
                 *      java.lang.String)
                 */
                public Language getLanguage(String language) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getLanguageManager()
                 */
                public LanguageManager getLanguageManager() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getLccode(org.extex.core.UnicodeChar)
                 */
                public UnicodeChar getLccode(UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMagnification()
                 */
                public long getMagnification() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMathcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public MathCode getMathcode(UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMuskip(
                 *      java.lang.String)
                 */
                public Muskip getMuskip(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getNamespace()
                 */
                public String getNamespace() {

                    return "";
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getOutFile(java.lang.String)
                 */
                public OutFile getOutFile(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getParshape()
                 */
                public ParagraphShape getParshape() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getSfcode(
                 *      org.extex.core.UnicodeChar)
                 */
                public FixedCount getSfcode(UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(
                 *      java.lang.Object)
                 */
                public Tokens getSplitBottomMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(
                 *      java.lang.Object)
                 */
                public Tokens getSplitFirstMark(Object name) {

                    return null;
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

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getTokenizer()
                 */
                public Tokenizer getTokenizer() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToks(
                 *      java.lang.String)
                 */
                public Tokens getToks(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToksOrNull(
                 *      java.lang.String)
                 */
                public Tokens getToksOrNull(String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getTopMark(java.lang.Object)
                 */
                public Tokens getTopMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getTypesettingContext()
                 */
                public TypesettingContext getTypesettingContext() {

                    return null;
                }

                public TypesettingContextFactory getTypesettingContextFactory() {

                    // not needed
                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getUccode(
                 *      org.extex.core.UnicodeChar)
                 */
                public UnicodeChar getUccode(UnicodeChar lc) {

                    return null;
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

                    return true;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#openGroup(
                 *      org.extex.interpreter.context.group.GroupType,
                 *      org.extex.core.Locator,
                 *      org.extex.scanner.type.token.Token)
                 */
                public void openGroup(GroupType id, Locator locator, Token start)
                        throws HelpingException {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#popConditional()
                 */
                public Conditional popConditional() throws HelpingException {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#popDirection()
                 */
                public Direction popDirection() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushConditional(
                 *      org.extex.core.Locator, boolean,
                 *      org.extex.interpreter.type.Code, long, boolean)
                 */
                public void pushConditional(Locator locator, boolean value,
                        Code primitive, long branch, boolean neg) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushDirection(
                 *      org.extex.typesetter.tc.Direction)
                 */
                public void pushDirection(Direction dir) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.color.Color, boolean)
                 */
                public void set(Color color, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.typesetter.tc.Direction, boolean)
                 */
                public void set(Direction direction, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.typesetter.tc.font.Font, boolean)
                 */
                public void set(Font font, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.language.Language, boolean)
                 */
                public void set(Language language, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      java.lang.Object, java.lang.Object, java.lang.Object,
                 *      boolean)
                 */
                public void set(Object extension, Object key, Object value,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(
                 *      org.extex.typesetter.tc.TypesettingContext, boolean)
                 */
                public void set(TypesettingContext context, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setAfterassignment(
                 *      org.extex.scanner.type.token.Token)
                 */
                public void setAfterassignment(Token token) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setBox(
                 *      java.lang.String, org.extex.interpreter.type.box.Box,
                 *      boolean)
                 */
                public void setBox(String name, Box value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setCatcode(
                 *      org.extex.core.UnicodeChar,
                 *      org.extex.scanner.type.Catcode, boolean)
                 */
                public void setCatcode(UnicodeChar c, Catcode catcode,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextCode#setCode(
                 *      org.extex.scanner.type.token.CodeToken,
                 *      org.extex.interpreter.type.Code, boolean)
                 */
                public void setCode(CodeToken t, Code code, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#setCount(
                 *      java.lang.String, long, boolean)
                 */
                public void setCount(String name, long value, boolean global)
                        throws HelpingException {

                    // not needed
                }

                public void setCountOption(String name, long value)
                        throws GeneralException {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setDelcode(
                 *      org.extex.core.UnicodeChar,
                 *      org.extex.typesetter.type.math.MathDelimiter, boolean)
                 */
                public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String,
                 *      org.extex.core.dimen.Dimen, boolean)
                 */
                public void setDimen(String name, Dimen value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String,
                 *      long, boolean)
                 */
                public void setDimen(String name, long value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFont(java.lang.String,
                 *      org.extex.typesetter.tc.font.Font, boolean)
                 */
                public void setFont(String name, Font font, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFontFactory(org.extex.font.CoreFontFactory)
                 */
                public void setFontFactory(CoreFontFactory fontFactory) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setGlue(java.lang.String,
                 *      org.extex.core.glue.Glue, boolean)
                 */
                public void setGlue(String name, Glue value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setId(
                 *      java.lang.String)
                 */
                public void setId(String id) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setInFile(java.lang.String,
                 *      org.extex.scanner.type.file.InFile, boolean)
                 */
                public void setInFile(String name, InFile file, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#setInteraction(
                 *      org.extex.interpreter.interaction.Interaction)
                 */
                public void setInteraction(Interaction interaction) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setLanguageManager(
                 *      org.extex.language.LanguageManager)
                 */
                public void setLanguageManager(LanguageManager manager) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setLccode(
                 *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar,
                 *      boolean)
                 */
                public void setLccode(UnicodeChar uc, UnicodeChar lc,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMagnification(
                 *      long, boolean)
                 */
                public void setMagnification(long mag, boolean lock) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setMark(
                 *      java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setMark(Object name, Tokens mark) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMathcode(
                 *      org.extex.core.UnicodeChar,
                 *      org.extex.typesetter.type.math.MathCode, boolean)
                 */
                public void setMathcode(UnicodeChar uc, MathCode code,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMuskip(java.lang.String,
                 *      org.extex.core.muskip.Muskip, boolean)
                 */
                public void setMuskip(String name, Muskip value, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setNamespace(
                 *      java.lang.String, boolean)
                 */
                public void setNamespace(String namespace, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setOutFile(java.lang.String,
                 *      org.extex.scanner.type.file.OutFile, boolean)
                 */
                public void setOutFile(String name, OutFile file, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setParshape(
                 *      org.extex.typesetter.paragraphBuilder.ParagraphShape)
                 */
                public void setParshape(ParagraphShape shape) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setSfcode(
                 *      org.extex.core.UnicodeChar, org.extex.core.count.Count,
                 *      boolean)
                 */
                public void setSfcode(UnicodeChar uc, Count code, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setSplitMark(
                 *      java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setSplitMark(Object name, Tokens mark) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setStandardTokenStream(
                 *      org.extex.scanner.api.TokenStream)
                 */
                public void setStandardTokenStream(
                        TokenStream standardTokenStream) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setTokenFactory(
                 *      org.extex.scanner.type.token.TokenFactory)
                 */
                public void setTokenFactory(TokenFactory factory) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#setToks(
                 *      java.lang.String, org.extex.scanner.type.tokens.Tokens,
                 *      boolean)
                 */
                public void setToks(String name, Tokens toks, boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setUccode(
                 *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar,
                 *      boolean)
                 */
                public void setUccode(UnicodeChar lc, UnicodeChar uc,
                        boolean global) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#startMarks()
                 */
                public void startMarks() {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#unitIterator()
                 */
                public Iterator<UnitInfo> unitIterator() {

                    return null;
                }

            }, null, null);
            assertFalse(true);
        } catch (InterpreterPanicException e) {
            assertEquals("Missing \\jobname for dump", e.getLocalizedMessage());
        }
    }

    /**
     * <testcase primitive="\dump"> Test case checking that the format written
     * by <tt>\dump</tt> contains the correct values. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        long count1 = 128L;

        assertOutput(// --- input code ---
            "\\font\\x= cmr10 \\count1=" + Long.toString(count1)
                    + " \\dump \\end",
            // --- log message ---
            "Beginning to dump on file ."
                    + System.getProperty("file.separator") + "texput.fmt\n",
            // --- output message ---
            "");

        File fmt = new File("texput.fmt");
        Context context = new SerialLoader().load(new FileInputStream(fmt));
        assertNotNull(context);
        Calendar calendar = Calendar.getInstance();
        assertEquals("texput "
                + //
                calendar.get(Calendar.YEAR) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "."
                + calendar.get(Calendar.DAY_OF_MONTH), context.getId());
        assertEquals(0L, context.getIfLevel());
        assertEquals(0L, context.getGroupLevel());
        assertNull(context.getFontFactory());
        assertNull(context.getTokenFactory());
        assertNull(context.getConditional());
        assertNull(context.getAfterassignment());
        assertEquals(1000L, context.getMagnification());
        assertEquals(count1, context.getCount("1").getValue());
        Code code =
                context.getCode((CodeToken) new TokenFactoryImpl().createToken(
                    Catcode.ESCAPE, null, "x", Namespace.DEFAULT_NAMESPACE));
        assertNotNull(code);
        fmt.delete();
    }

    /**
     * <testcase primitive="\dump"> Test case checking that <tt>\dump</tt> can
     * not be used inside a group. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroupError1() throws Exception {

        assertFailure(DEFINE_BRACES + "{\\dump}", //
            "You can't dump inside a group");
    }

}
