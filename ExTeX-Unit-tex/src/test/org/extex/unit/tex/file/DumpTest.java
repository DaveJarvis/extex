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

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Iterator;

import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.font.CoreFontFactory;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Color;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.context.tc.Direction;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.InterpreterPanicException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.loader.SerialLoader;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.file.InFile;
import org.extex.interpreter.type.file.OutFile;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.math.MathCode;
import org.extex.interpreter.type.math.MathDelimiter;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.scanner.TokenStream;
import org.extex.scanner.Tokenizer;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.test.NoFlagsPrimitiveTester;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;

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
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(DumpTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public DumpTest(final String arg) {

        super(arg, "dump", "", "", "Beginning to dump on file ."
                + System.getProperty("file.separator") + "texput.fmt\n");
        new File("texput.fmt").delete();
    }

    /**
     * <testcase primitive="\dump">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGroupError1() throws Exception {

        assertFailure(DEFINE_BRACES + "{\\dump}", //
            "You can't dump inside a group");
    }

    /**
     * <testcase primitive="\dump">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertOutput(//--- input code ---
            "\\dump \\end",
            //--- log message ---
            "Beginning to dump on file ."
                    + System.getProperty("file.separator") + "texput.fmt\n", "");
    }

    /**
     * <testcase primitive="\dump">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        final int count1 = 128;

        assertOutput(//--- input code ---
            "\\font\\x= cmr10 \\count1=" + Integer.toString(count1)
                    + " \\dump \\end",
            //--- log message ---
            "Beginning to dump on file ."
                    + System.getProperty("file.separator") + "texput.fmt\n", "");

        File fmt = new File("texput.fmt");
        Context context = new SerialLoader().load(new FileInputStream(fmt));
        assertNotNull(context);
        Calendar calendar = Calendar.getInstance();
        assertEquals("texput "
                + //
                calendar.get(Calendar.YEAR) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "."
                + calendar.get(Calendar.DAY_OF_MONTH), context.getId());
        assertEquals(0, context.getIfLevel());
        assertEquals(0, context.getGroupLevel());
        assertNull(context.getFontFactory());
        assertNull(context.getTokenFactory());
        assertNull(context.getConditional());
        assertNull(context.getAfterassignment());
        assertEquals(1000, context.getMagnification());
        assertEquals(count1, context.getCount("1").getValue());
        Code code =
                context.getCode((CodeToken) new TokenFactoryImpl().createToken(
                    Catcode.ESCAPE, null, "x", Namespace.DEFAULT_NAMESPACE));
        assertNotNull(code);
        fmt.delete();
    }

    /**
     * <testcase primitive="\dump">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test100() throws Exception {

        try {
            new Dump("dump").execute(Flags.NONE, new Context() {

                /**
                 * The field <tt>serialVersionUID</tt> contains the ...
                 */
                private static final long serialVersionUID = 1L;

                /**
                 * @see org.extex.interpreter.context.Context#addUnit(org.extex.interpreter.unit.UnitInfo)
                 */
                public void addUnit(final UnitInfo info) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(java.lang.String)
                 */
                public String esc(final String name) {

                    return name;
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(org.extex.scanner.type.token.Token)
                 */
                public String esc(final Token token) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#escapechar()
                 */
                public UnicodeChar escapechar() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#get(java.lang.Object, java.lang.Object)
                 */
                public Object get(final Object extension, final Object key) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getAfterassignment()
                 */
                public Token getAfterassignment() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getBox(java.lang.String)
                 */
                public Box getBox(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getConditional()
                 */
                public Conditional getConditional() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getDelcode(org.extex.core.UnicodeChar)
                 */
                public MathDelimiter getDelcode(final UnicodeChar c) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getGlue(java.lang.String)
                 */
                public Glue getGlue(final String name) {

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
                 * @see org.extex.interpreter.context.Context#getLanguage(java.lang.String)
                 */
                public Language getLanguage(final String language) {

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
                public UnicodeChar getLccode(final UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMagnification()
                 */
                public long getMagnification() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMathcode(org.extex.core.UnicodeChar)
                 */
                public MathCode getMathcode(final UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMuskip(java.lang.String)
                 */
                public Muskip getMuskip(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getNamespace()
                 */
                public String getNamespace() {

                    return "";
                }

                /**
                 * @see org.extex.interpreter.context.Context#getParshape()
                 */
                public ParagraphShape getParshape() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getSfcode(org.extex.core.UnicodeChar)
                 */
                public Count getSfcode(final UnicodeChar uc) {

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
                 * @see org.extex.interpreter.context.Context#getTypesettingContext()
                 */
                public TypesettingContext getTypesettingContext() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getUccode(org.extex.core.UnicodeChar)
                 */
                public UnicodeChar getUccode(final UnicodeChar lc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#popConditional()
                 */
                public Conditional popConditional() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#popDirection()
                 */
                public Direction popDirection() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushConditional(org.extex.core.Locator, boolean, org.extex.interpreter.type.Code, long, boolean)
                 */
                public void pushConditional(final Locator locator,
                        final boolean value, final Code primitive,
                        final long branch, final boolean neg) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushDirection(org.extex.interpreter.context.tc.Direction)
                 */
                public void pushDirection(final Direction dir) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.Color, boolean)
                 */
                public void set(final Color color, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.tc.Direction, boolean)
                 */
                public void set(final Direction direction, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.type.font.Font, boolean)
                 */
                public void set(final Font font, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.language.Language, boolean)
                 */
                public void set(final Language language, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(java.lang.Object, java.lang.Object, java.lang.Object, boolean)
                 */
                public void set(final Object extension, final Object key,
                        final Object value, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.tc.TypesettingContext, boolean)
                 */
                public void set(final TypesettingContext context,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setAfterassignment(org.extex.scanner.type.token.Token)
                 */
                public void setAfterassignment(final Token token) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setBox(java.lang.String, org.extex.interpreter.type.box.Box, boolean)
                 */
                public void setBox(final String name, final Box value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setCatcode(org.extex.core.UnicodeChar, org.extex.scanner.type.Catcode, boolean)
                 */
                public void setCatcode(final UnicodeChar c,
                        final Catcode catcode, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setDelcode(org.extex.core.UnicodeChar, org.extex.interpreter.type.math.MathDelimiter, boolean)
                 */
                public void setDelcode(final UnicodeChar c,
                        final MathDelimiter delimiter, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setGlue(java.lang.String, org.extex.core.glue.Glue, boolean)
                 */
                public void setGlue(final String name, final Glue value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setId(java.lang.String)
                 */
                public void setId(final String id) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setLanguageManager(org.extex.language.LanguageManager)
                 */
                public void setLanguageManager(final LanguageManager manager) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setLccode(org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
                 */
                public void setLccode(final UnicodeChar uc,
                        final UnicodeChar lc, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMagnification(long, boolean)
                 */
                public void setMagnification(final long mag, final boolean lock) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMathcode(org.extex.core.UnicodeChar, org.extex.interpreter.type.math.MathCode, boolean)
                 */
                public void setMathcode(final UnicodeChar uc,
                        final MathCode code, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMuskip(java.lang.String, org.extex.core.muskip.Muskip, boolean)
                 */
                public void setMuskip(final String name, final Muskip value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setNamespace(java.lang.String, boolean)
                 */
                public void setNamespace(final String namespace,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setParshape(org.extex.typesetter.paragraphBuilder.ParagraphShape)
                 */
                public void setParshape(final ParagraphShape shape) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setSfcode(org.extex.core.UnicodeChar, org.extex.core.count.Count, boolean)
                 */
                public void setSfcode(final UnicodeChar uc, final Count code,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setStandardTokenStream(org.extex.scanner.TokenStream)
                 */
                public void setStandardTokenStream(
                        final TokenStream standardTokenStream) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setTokenFactory(org.extex.scanner.type.token.TokenFactory)
                 */
                public void setTokenFactory(final TokenFactory factory) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setUccode(org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
                 */
                public void setUccode(final UnicodeChar lc,
                        final UnicodeChar uc, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#unitIterator()
                 */
                public Iterator unitIterator() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCode#getCode(org.extex.scanner.type.token.CodeToken)
                 */
                public Code getCode(final CodeToken t) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCode#setCode(org.extex.scanner.type.token.CodeToken, org.extex.interpreter.type.Code, boolean)
                 */
                public void setCode(final CodeToken t, final Code code,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#getCount(java.lang.String)
                 */
                public Count getCount(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#setCount(java.lang.String, long, boolean)
                 */
                public void setCount(final String name, final long value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#getDimen(java.lang.String)
                 */
                public Dimen getDimen(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String, org.extex.core.dimen.Dimen, boolean)
                 */
                public void setDimen(final String name, final Dimen value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String, long, boolean)
                 */
                public void setDimen(final String name, final long value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getInFile(java.lang.String)
                 */
                public InFile getInFile(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getOutFile(java.lang.String)
                 */
                public OutFile getOutFile(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setInFile(java.lang.String, org.extex.interpreter.type.file.InFile, boolean)
                 */
                public void setInFile(final String name, final InFile file,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setOutFile(java.lang.String, org.extex.interpreter.type.file.OutFile, boolean)
                 */
                public void setOutFile(final String name, final OutFile file,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFont(java.lang.String)
                 */
                public Font getFont(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFontFactory()
                 */
                public CoreFontFactory getFontFactory() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFont(java.lang.String, org.extex.interpreter.type.font.Font, boolean)
                 */
                public void setFont(final String name, final Font font,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFontFactory(org.extex.font.CoreFontFactory)
                 */
                public void setFontFactory(final CoreFontFactory fontFactory) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.interpreter.context.observer.group.AfterGroupObserver)
                 */
                public void afterGroup(final AfterGroupObserver observer) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.scanner.type.token.Token)
                 */
                public void afterGroup(final Token t) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#closeGroup(org.extex.typesetter.Typesetter, org.extex.interpreter.TokenSource)
                 */
                public void closeGroup(final Typesetter typesetter,
                        final TokenSource source) {

                    //not needed
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
                 * @see org.extex.interpreter.context.ContextGroup#isGlobalGroup()
                 */
                public boolean isGlobalGroup() {

                    return true;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#openGroup(org.extex.interpreter.context.group.GroupType, org.extex.core.Locator, org.extex.scanner.type.token.Token)
                 */
                public void openGroup(final GroupType id,
                        final Locator locator, final Token start) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
                 */
                public int getErrorCount() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextErrorCount#incrementErrorCount()
                 */
                public int incrementErrorCount() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#getInteraction()
                 */
                public Interaction getInteraction() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#setInteraction(org.extex.interpreter.interaction.Interaction)
                 */
                public void setInteraction(final Interaction interaction) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToks(java.lang.String)
                 */
                public Tokens getToks(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToksOrNull(java.lang.String)
                 */
                public Tokens getToksOrNull(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#setToks(java.lang.String, org.extex.scanner.type.tokens.Tokens, boolean)
                 */
                public void setToks(final String name, final Tokens toks,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
                 */
                public void clearSplitMarks() {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getBottomMark(java.lang.Object)
                 */
                public Tokens getBottomMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getFirstMark(java.lang.Object)
                 */
                public Tokens getFirstMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(java.lang.Object)
                 */
                public Tokens getSplitBottomMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(java.lang.Object)
                 */
                public Tokens getSplitFirstMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getTopMark(java.lang.Object)
                 */
                public Tokens getTopMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setMark(java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setMark(final Object name, final Tokens mark) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setSplitMark(java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setSplitMark(final Object name, final Tokens mark) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#startMarks()
                 */
                public void startMarks() {

                    //not needed
                }

                /**
                 * @see org.extex.scanner.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
                 */
                public Catcode getCatcode(final UnicodeChar c) {

                    return null;
                }
            }, null, null);
            assertFalse(true);
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase primitive="\dump">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test101() throws Exception {

        try {
            Dump dump = new Dump("dump");
            dump.enableLocalization(LocalizerFactory.getLocalizer(Dump.class));
            dump.setOutputStreamFactory(new OutputStreamFactory() {

                /**
                 * @see org.extex.backend.outputStream.OutputStreamFactory#getOutputStream(java.lang.String, java.lang.String)
                 */
                public OutputStream getOutputStream(final String name,
                        final String type) {

                    return null;
                }

                /**
                 * @see org.extex.backend.outputStream.OutputStreamFactory#register(org.extex.backend.outputStream.OutputStreamObserver)
                 */
                public void register(final OutputStreamObserver observer) {

                    //not needed
                }

                /**
                 * @see org.extex.backend.outputStream.OutputStreamFactory#setExtension(java.lang.String)
                 */
                public void setExtension(final String extension) {

                    //not needed
                }
            });
            dump.execute(Flags.NONE, new Context() {

                /**
                 * The field <tt>serialVersionUID</tt> contains the ...
                 */
                private static final long serialVersionUID = 1L;

                /**
                 * @see org.extex.interpreter.context.Context#addUnit(org.extex.interpreter.unit.UnitInfo)
                 */
                public void addUnit(final UnitInfo info) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(java.lang.String)
                 */
                public String esc(final String name) {

                    return name;
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(org.extex.scanner.type.token.Token)
                 */
                public String esc(final Token token) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#escapechar()
                 */
                public UnicodeChar escapechar() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#get(java.lang.Object, java.lang.Object)
                 */
                public Object get(final Object extension, final Object key) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getAfterassignment()
                 */
                public Token getAfterassignment() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getBox(java.lang.String)
                 */
                public Box getBox(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getConditional()
                 */
                public Conditional getConditional() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getDelcode(org.extex.core.UnicodeChar)
                 */
                public MathDelimiter getDelcode(final UnicodeChar c) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getGlue(java.lang.String)
                 */
                public Glue getGlue(final String name) {

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
                 * @see org.extex.interpreter.context.Context#getLanguage(java.lang.String)
                 */
                public Language getLanguage(final String language) {

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
                public UnicodeChar getLccode(final UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMagnification()
                 */
                public long getMagnification() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMathcode(org.extex.core.UnicodeChar)
                 */
                public MathCode getMathcode(final UnicodeChar uc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getMuskip(java.lang.String)
                 */
                public Muskip getMuskip(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getNamespace()
                 */
                public String getNamespace() {

                    return "";
                }

                /**
                 * @see org.extex.interpreter.context.Context#getParshape()
                 */
                public ParagraphShape getParshape() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getSfcode(org.extex.core.UnicodeChar)
                 */
                public Count getSfcode(final UnicodeChar uc) {

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
                 * @see org.extex.interpreter.context.Context#getTypesettingContext()
                 */
                public TypesettingContext getTypesettingContext() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getUccode(org.extex.core.UnicodeChar)
                 */
                public UnicodeChar getUccode(final UnicodeChar lc) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#popConditional()
                 */
                public Conditional popConditional() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#popDirection()
                 */
                public Direction popDirection() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushConditional(org.extex.core.Locator, boolean, org.extex.interpreter.type.Code, long, boolean)
                 */
                public void pushConditional(final Locator locator,
                        final boolean value, final Code primitive,
                        final long branch, final boolean neg) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#pushDirection(org.extex.interpreter.context.tc.Direction)
                 */
                public void pushDirection(final Direction dir) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.Color, boolean)
                 */
                public void set(final Color color, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.tc.Direction, boolean)
                 */
                public void set(final Direction direction, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.type.font.Font, boolean)
                 */
                public void set(final Font font, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.language.Language, boolean)
                 */
                public void set(final Language language, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(java.lang.Object, java.lang.Object, java.lang.Object, boolean)
                 */
                public void set(final Object extension, final Object key,
                        final Object value, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#set(org.extex.interpreter.context.tc.TypesettingContext, boolean)
                 */
                public void set(final TypesettingContext context,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setAfterassignment(org.extex.scanner.type.token.Token)
                 */
                public void setAfterassignment(final Token token) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setBox(java.lang.String, org.extex.interpreter.type.box.Box, boolean)
                 */
                public void setBox(final String name, final Box value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setCatcode(org.extex.core.UnicodeChar, org.extex.scanner.type.Catcode, boolean)
                 */
                public void setCatcode(final UnicodeChar c,
                        final Catcode catcode, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setDelcode(org.extex.core.UnicodeChar, org.extex.interpreter.type.math.MathDelimiter, boolean)
                 */
                public void setDelcode(final UnicodeChar c,
                        final MathDelimiter delimiter, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setGlue(java.lang.String, org.extex.core.glue.Glue, boolean)
                 */
                public void setGlue(final String name, final Glue value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setId(java.lang.String)
                 */
                public void setId(final String id) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setLanguageManager(org.extex.language.LanguageManager)
                 */
                public void setLanguageManager(final LanguageManager manager) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setLccode(org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
                 */
                public void setLccode(final UnicodeChar uc,
                        final UnicodeChar lc, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMagnification(long, boolean)
                 */
                public void setMagnification(final long mag, final boolean lock) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMathcode(org.extex.core.UnicodeChar, org.extex.interpreter.type.math.MathCode, boolean)
                 */
                public void setMathcode(final UnicodeChar uc,
                        final MathCode code, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setMuskip(java.lang.String, org.extex.core.muskip.Muskip, boolean)
                 */
                public void setMuskip(final String name, final Muskip value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setNamespace(java.lang.String, boolean)
                 */
                public void setNamespace(final String namespace,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setParshape(org.extex.typesetter.paragraphBuilder.ParagraphShape)
                 */
                public void setParshape(final ParagraphShape shape) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setSfcode(org.extex.core.UnicodeChar, org.extex.core.count.Count, boolean)
                 */
                public void setSfcode(final UnicodeChar uc, final Count code,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setStandardTokenStream(org.extex.scanner.TokenStream)
                 */
                public void setStandardTokenStream(
                        final TokenStream standardTokenStream) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setTokenFactory(org.extex.scanner.type.token.TokenFactory)
                 */
                public void setTokenFactory(final TokenFactory factory) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#setUccode(org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
                 */
                public void setUccode(final UnicodeChar lc,
                        final UnicodeChar uc, final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#unitIterator()
                 */
                public Iterator unitIterator() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCode#getCode(org.extex.scanner.type.token.CodeToken)
                 */
                public Code getCode(final CodeToken t) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCode#setCode(org.extex.scanner.type.token.CodeToken, org.extex.interpreter.type.Code, boolean)
                 */
                public void setCode(final CodeToken t, final Code code,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#getCount(java.lang.String)
                 */
                public Count getCount(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextCount#setCount(java.lang.String, long, boolean)
                 */
                public void setCount(final String name, final long value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#getDimen(java.lang.String)
                 */
                public Dimen getDimen(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String, org.extex.core.dimen.Dimen, boolean)
                 */
                public void setDimen(final String name, final Dimen value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextDimen#setDimen(java.lang.String, long, boolean)
                 */
                public void setDimen(final String name, final long value,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getInFile(java.lang.String)
                 */
                public InFile getInFile(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#getOutFile(java.lang.String)
                 */
                public OutFile getOutFile(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setInFile(java.lang.String, org.extex.interpreter.type.file.InFile, boolean)
                 */
                public void setInFile(final String name, final InFile file,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFile#setOutFile(java.lang.String, org.extex.interpreter.type.file.OutFile, boolean)
                 */
                public void setOutFile(final String name, final OutFile file,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFont(java.lang.String)
                 */
                public Font getFont(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#getFontFactory()
                 */
                public CoreFontFactory getFontFactory() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFont(java.lang.String, org.extex.interpreter.type.font.Font, boolean)
                 */
                public void setFont(final String name, final Font font,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextFont#setFontFactory(org.extex.font.CoreFontFactory)
                 */
                public void setFontFactory(final CoreFontFactory fontFactory) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.interpreter.context.observer.group.AfterGroupObserver)
                 */
                public void afterGroup(final AfterGroupObserver observer) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.scanner.type.token.Token)
                 */
                public void afterGroup(final Token t) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#closeGroup(org.extex.typesetter.Typesetter, org.extex.interpreter.TokenSource)
                 */
                public void closeGroup(final Typesetter typesetter,
                        final TokenSource source) {

                    //not needed
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
                 * @see org.extex.interpreter.context.ContextGroup#isGlobalGroup()
                 */
                public boolean isGlobalGroup() {

                    return true;
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#openGroup(org.extex.interpreter.context.group.GroupType, org.extex.core.Locator, org.extex.scanner.type.token.Token)
                 */
                public void openGroup(final GroupType id,
                        final Locator locator, final Token start) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextErrorCount#getErrorCount()
                 */
                public int getErrorCount() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextErrorCount#incrementErrorCount()
                 */
                public int incrementErrorCount() {

                    return 0;
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#getInteraction()
                 */
                public Interaction getInteraction() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextInteraction#setInteraction(org.extex.interpreter.interaction.Interaction)
                 */
                public void setInteraction(final Interaction interaction) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToks(java.lang.String)
                 */
                public Tokens getToks(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#getToksOrNull(java.lang.String)
                 */
                public Tokens getToksOrNull(final String name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextTokens#setToks(java.lang.String, org.extex.scanner.type.tokens.Tokens, boolean)
                 */
                public void setToks(final String name, final Tokens toks,
                        final boolean global) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
                 */
                public void clearSplitMarks() {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getBottomMark(java.lang.Object)
                 */
                public Tokens getBottomMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getFirstMark(java.lang.Object)
                 */
                public Tokens getFirstMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitBottomMark(java.lang.Object)
                 */
                public Tokens getSplitBottomMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getSplitFirstMark(java.lang.Object)
                 */
                public Tokens getSplitFirstMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getTopMark(java.lang.Object)
                 */
                public Tokens getTopMark(final Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setMark(java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setMark(final Object name, final Tokens mark) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#setSplitMark(java.lang.Object, org.extex.scanner.type.tokens.Tokens)
                 */
                public void setSplitMark(final Object name, final Tokens mark) {

                    //not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#startMarks()
                 */
                public void startMarks() {

                    //not needed
                }

                /**
                 * @see org.extex.scanner.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
                 */
                public Catcode getCatcode(final UnicodeChar c) {

                    return null;
                }
            }, null, null);
            assertFalse(true);
        } catch (InterpreterPanicException e) {
            assertTrue(true);
        }
    }

}
