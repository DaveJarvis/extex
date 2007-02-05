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

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;
import org.extex.font.CoreFontFactory;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.Flags;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.Tokenizer;
import org.extex.interpreter.context.Color;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.context.tc.Direction;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.InterpreterPanicException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.loader.SerialLoader;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.file.InFile;
import org.extex.interpreter.type.file.OutFile;
import org.extex.interpreter.type.font.Font;
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
import org.extex.test.NoFlagsPrimitiveTester;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.i18n.LocalizerFactory;

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

        assertOutput(//--- input code ---
            "\\font\\x= cmr10 \\count1=123 \\dump \\end",
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
        assertEquals(123, context.getCount("1").getValue());
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

                public void addUnit(UnitInfo info) {

                }

                public String esc(String name) {

                    return name;
                }

                public String esc(Token token) {

                    return null;
                }

                public UnicodeChar escapechar() {

                    return null;
                }

                public Object get(Object extension, Object key) {

                    return null;
                }

                public Token getAfterassignment() {

                    return null;
                }

                public Box getBox(String name) {

                    return null;
                }

                public Conditional getConditional() {

                    return null;
                }

                public MathDelimiter getDelcode(UnicodeChar c) {

                    return null;
                }

                public Glue getGlue(String name) {

                    return null;
                }

                public String getId() {

                    return null;
                }

                public long getIfLevel() {

                    return 0;
                }

                public Language getLanguage(String language)
                        throws InterpreterException {

                    return null;
                }

                public LanguageManager getLanguageManager() {

                    return null;
                }

                public UnicodeChar getLccode(UnicodeChar uc) {

                    return null;
                }

                public long getMagnification() {

                    return 0;
                }

                public MathCode getMathcode(UnicodeChar uc) {

                    return null;
                }

                public Muskip getMuskip(String name) {

                    return null;
                }

                public String getNamespace() {

                    return "";
                }

                public ParagraphShape getParshape() {

                    return null;
                }

                public Count getSfcode(UnicodeChar uc) {

                    return null;
                }

                public TokenStream getStandardTokenStream() {

                    return null;
                }

                public TokenFactory getTokenFactory() {

                    return null;
                }

                public Tokenizer getTokenizer() {

                    return null;
                }

                public TypesettingContext getTypesettingContext() {

                    return null;
                }

                public UnicodeChar getUccode(UnicodeChar lc) {

                    return null;
                }

                public Conditional popConditional() throws InterpreterException {

                    return null;
                }

                public Direction popDirection() {

                    return null;
                }

                public void pushConditional(Locator locator, boolean value,
                        Code primitive, long branch, boolean neg) {

                }

                public void pushDirection(Direction dir) {

                }

                public void set(Color color, boolean global)
                        throws ConfigurationException {

                }

                public void set(Direction direction, boolean global)
                        throws ConfigurationException {

                }

                public void set(Font font, boolean global)
                        throws ConfigurationException {

                }

                public void set(Language language, boolean global)
                        throws ConfigurationException {

                }

                public void set(Object extension, Object key, Object value,
                        boolean global) {

                }

                public void set(TypesettingContext context, boolean global) {

                }

                public void setAfterassignment(Token token) {

                }

                public void setBox(String name, Box value, boolean global) {

                }

                public void setCatcode(UnicodeChar c, Catcode catcode,
                        boolean global) throws HelpingException {

                }

                public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                        boolean global) {

                }

                public void setGlue(String name, Glue value, boolean global)
                        throws InterpreterException {

                }

                public void setId(String id) {

                }

                public void setLanguageManager(LanguageManager manager)
                        throws ConfigurationException {

                }

                public void setLccode(UnicodeChar uc, UnicodeChar lc,
                        boolean global) {

                }

                public void setMagnification(long mag, boolean lock)
                        throws HelpingException {

                }

                public void setMathcode(UnicodeChar uc, MathCode code,
                        boolean global) {

                }

                public void setMuskip(String name, Muskip value, boolean global) {

                }

                public void setNamespace(String namespace, boolean global) {

                }

                public void setParshape(ParagraphShape shape) {

                }

                public void setSfcode(UnicodeChar uc, Count code, boolean global) {

                }

                public void setStandardTokenStream(
                        TokenStream standardTokenStream) {

                }

                public void setTokenFactory(TokenFactory factory) {

                }

                public void setUccode(UnicodeChar lc, UnicodeChar uc,
                        boolean global) {

                }

                public Iterator unitIterator() {

                    return null;
                }

                public Code getCode(CodeToken t) throws InterpreterException {

                    return null;
                }

                public void setCode(CodeToken t, Code code, boolean global)
                        throws InterpreterException {

                }

                public Count getCount(String name) {

                    return null;
                }

                public void setCount(String name, long value, boolean global)
                        throws InterpreterException {

                }

                public Dimen getDimen(String name) {

                    return null;
                }

                public void setDimen(String name, Dimen value, boolean global)
                        throws InterpreterException {

                }

                public void setDimen(String name, long value, boolean global)
                        throws InterpreterException {

                }

                public InFile getInFile(String name) {

                    return null;
                }

                public OutFile getOutFile(String name) {

                    return null;
                }

                public void setInFile(String name, InFile file, boolean global) {

                }

                public void setOutFile(String name, OutFile file, boolean global) {

                }

                public Font getFont(String name) {

                    return null;
                }

                public CoreFontFactory getFontFactory() {

                    return null;
                }

                public void setFont(String name, Font font, boolean global) {

                }

                public void setFontFactory(CoreFontFactory fontFactory) {

                }

                public void afterGroup(AfterGroupObserver observer) {

                }

                public void afterGroup(Token t) throws InterpreterException {

                }

                public void closeGroup(Typesetter typesetter, TokenSource source)
                        throws InterpreterException {

                }

                public GroupInfo[] getGroupInfos() {

                    return null;
                }

                public long getGroupLevel() {

                    return 0;
                }

                public GroupType getGroupType() {

                    return null;
                }

                public boolean isGlobalGroup() {

                    return true;
                }

                public void openGroup(GroupType id, Locator locator, Token start)
                        throws ConfigurationException,
                            InterpreterException {

                }

                public int getErrorCount() {

                    return 0;
                }

                public int incrementErrorCount() {

                    return 0;
                }

                public Interaction getInteraction() {

                    return null;
                }

                public void setInteraction(Interaction interaction)
                        throws InterpreterException {

                }

                public Tokens getToks(String name) {

                    return null;
                }

                public Tokens getToksOrNull(String name) {

                    return null;
                }

                public void setToks(String name, Tokens toks, boolean global)
                        throws InterpreterException {

                }

                public void clearSplitMarks() {

                }

                public Tokens getBottomMark(Object name) {

                    return null;
                }

                public Tokens getFirstMark(Object name) {

                    return null;
                }

                public Tokens getSplitBottomMark(Object name) {

                    return null;
                }

                public Tokens getSplitFirstMark(Object name) {

                    return null;
                }

                public Tokens getTopMark(Object name) {

                    return null;
                }

                public void setMark(Object name, Tokens mark) {

                }

                public void setSplitMark(Object name, Tokens mark) {

                }

                public void startMarks() {

                }

                public Catcode getCatcode(UnicodeChar c) {

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

                public OutputStream getOutputStream(String name, String type)
                        throws DocumentWriterException {

                    return null;
                }

                public void register(OutputStreamObserver observer) {

                }

                public void setExtension(String extension) {

                }
            });
            dump.execute(Flags.NONE, new Context() {

                public void addUnit(UnitInfo info) {

                }

                public String esc(String name) {

                    return name;
                }

                public String esc(Token token) {

                    return null;
                }

                public UnicodeChar escapechar() {

                    return null;
                }

                public Object get(Object extension, Object key) {

                    return null;
                }

                public Token getAfterassignment() {

                    return null;
                }

                public Box getBox(String name) {

                    return null;
                }

                public Conditional getConditional() {

                    return null;
                }

                public MathDelimiter getDelcode(UnicodeChar c) {

                    return null;
                }

                public Glue getGlue(String name) {

                    return null;
                }

                public String getId() {

                    return null;
                }

                public long getIfLevel() {

                    return 0;
                }

                public Language getLanguage(String language)
                        throws InterpreterException {

                    return null;
                }

                public LanguageManager getLanguageManager() {

                    return null;
                }

                public UnicodeChar getLccode(UnicodeChar uc) {

                    return null;
                }

                public long getMagnification() {

                    return 0;
                }

                public MathCode getMathcode(UnicodeChar uc) {

                    return null;
                }

                public Muskip getMuskip(String name) {

                    return null;
                }

                public String getNamespace() {

                    return "";
                }

                public ParagraphShape getParshape() {

                    return null;
                }

                public Count getSfcode(UnicodeChar uc) {

                    return null;
                }

                public TokenStream getStandardTokenStream() {

                    return null;
                }

                public TokenFactory getTokenFactory() {

                    return null;
                }

                public Tokenizer getTokenizer() {

                    return null;
                }

                public TypesettingContext getTypesettingContext() {

                    return null;
                }

                public UnicodeChar getUccode(UnicodeChar lc) {

                    return null;
                }

                public Conditional popConditional() throws InterpreterException {

                    return null;
                }

                public Direction popDirection() {

                    return null;
                }

                public void pushConditional(Locator locator, boolean value,
                        Code primitive, long branch, boolean neg) {

                }

                public void pushDirection(Direction dir) {

                }

                public void set(Color color, boolean global)
                        throws ConfigurationException {

                }

                public void set(Direction direction, boolean global)
                        throws ConfigurationException {

                }

                public void set(Font font, boolean global)
                        throws ConfigurationException {

                }

                public void set(Language language, boolean global)
                        throws ConfigurationException {

                }

                public void set(Object extension, Object key, Object value,
                        boolean global) {

                }

                public void set(TypesettingContext context, boolean global) {

                }

                public void setAfterassignment(Token token) {

                }

                public void setBox(String name, Box value, boolean global) {

                }

                public void setCatcode(UnicodeChar c, Catcode catcode,
                        boolean global) throws HelpingException {

                }

                public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                        boolean global) {

                }

                public void setGlue(String name, Glue value, boolean global)
                        throws InterpreterException {

                }

                public void setId(String id) {

                }

                public void setLanguageManager(LanguageManager manager)
                        throws ConfigurationException {

                }

                public void setLccode(UnicodeChar uc, UnicodeChar lc,
                        boolean global) {

                }

                public void setMagnification(long mag, boolean lock)
                        throws HelpingException {

                }

                public void setMathcode(UnicodeChar uc, MathCode code,
                        boolean global) {

                }

                public void setMuskip(String name, Muskip value, boolean global) {

                }

                public void setNamespace(String namespace, boolean global) {

                }

                public void setParshape(ParagraphShape shape) {

                }

                public void setSfcode(UnicodeChar uc, Count code, boolean global) {

                }

                public void setStandardTokenStream(
                        TokenStream standardTokenStream) {

                }

                public void setTokenFactory(TokenFactory factory) {

                }

                public void setUccode(UnicodeChar lc, UnicodeChar uc,
                        boolean global) {

                }

                public Iterator unitIterator() {

                    return null;
                }

                public Code getCode(CodeToken t) throws InterpreterException {

                    return null;
                }

                public void setCode(CodeToken t, Code code, boolean global)
                        throws InterpreterException {

                }

                public Count getCount(String name) {

                    return null;
                }

                public void setCount(String name, long value, boolean global)
                        throws InterpreterException {

                }

                public Dimen getDimen(String name) {

                    return null;
                }

                public void setDimen(String name, Dimen value, boolean global)
                        throws InterpreterException {

                }

                public void setDimen(String name, long value, boolean global)
                        throws InterpreterException {

                }

                public InFile getInFile(String name) {

                    return null;
                }

                public OutFile getOutFile(String name) {

                    return null;
                }

                public void setInFile(String name, InFile file, boolean global) {

                }

                public void setOutFile(String name, OutFile file, boolean global) {

                }

                public Font getFont(String name) {

                    return null;
                }

                public CoreFontFactory getFontFactory() {

                    return null;
                }

                public void setFont(String name, Font font, boolean global) {

                }

                public void setFontFactory(CoreFontFactory fontFactory) {

                }

                public void afterGroup(AfterGroupObserver observer) {

                }

                public void afterGroup(Token t) throws InterpreterException {

                }

                public void closeGroup(Typesetter typesetter, TokenSource source)
                        throws InterpreterException {

                }

                public GroupInfo[] getGroupInfos() {

                    return null;
                }

                public long getGroupLevel() {

                    return 0;
                }

                public GroupType getGroupType() {

                    return null;
                }

                public boolean isGlobalGroup() {

                    return true;
                }

                public void openGroup(GroupType id, Locator locator, Token start)
                        throws ConfigurationException,
                            InterpreterException {

                }

                public int getErrorCount() {

                    return 0;
                }

                public int incrementErrorCount() {

                    return 0;
                }

                public Interaction getInteraction() {

                    return null;
                }

                public void setInteraction(Interaction interaction)
                        throws InterpreterException {

                }

                public Tokens getToks(String name) {

                    return null;
                }

                public Tokens getToksOrNull(String name) {

                    return null;
                }

                public void setToks(String name, Tokens toks, boolean global)
                        throws InterpreterException {

                }

                public void clearSplitMarks() {

                }

                public Tokens getBottomMark(Object name) {

                    return null;
                }

                public Tokens getFirstMark(Object name) {

                    return null;
                }

                public Tokens getSplitBottomMark(Object name) {

                    return null;
                }

                public Tokens getSplitFirstMark(Object name) {

                    return null;
                }

                public Tokens getTopMark(Object name) {

                    return null;
                }

                public void setMark(Object name, Tokens mark) {

                }

                public void setSplitMark(Object name, Tokens mark) {

                }

                public void startMarks() {

                }

                public Catcode getCatcode(UnicodeChar c) {

                    return null;
                }
            }, null, null);
            assertFalse(true);
        } catch (InterpreterPanicException e) {
            assertTrue(true);
        }
    }

}
