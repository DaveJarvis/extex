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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \dump}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
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


    public DumpTest() {

        setPrimitive("dump");
        setOut( "Beginning to dump on file ."
                + System.getProperty("file.separator") + "texput.fmt\n");
        new File("texput.fmt").delete();
    }

    /**
     * <testcase primitive="\dump"> Test case checking that {@code \dump}
     * writes a format file.
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
     * <testcase primitive="\dump"> Test case checking that {@code \dump}
     * complains about a missing output stream factory if none is set.
     *
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
                 * The field {@code serialVersionUID} contains the version
                 * number.
                 */
                private static final long serialVersionUID = 1L;

                /**
                 * @see org.extex.interpreter.context.Context#addUnit(org.extex.interpreter.unit.UnitInfo)
                 */
                @Override
                public void addUnit(UnitInfo info) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.interpreter.context.observer.group.AfterGroupObserver)
                 */
                @Override
                public void afterGroup(AfterGroupObserver observer) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#afterGroup(org.extex.scanner.type.token.Token)
                 */
                @Override
                public void afterGroup(Token t) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#clearSplitMarks()
                 */
                @Override
                public void clearSplitMarks() {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.ContextGroup#closeGroup(org.extex.typesetter.Typesetter,
                 *      org.extex.interpreter.TokenSource)
                 */
                @Override
                public void closeGroup(Typesetter typesetter, TokenSource source) {

                    // not needed
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(java.lang.String)
                 */
                @Override
                public String esc(String name) {

                    return name;
                }

                /**
                 * @see org.extex.interpreter.context.Context#esc(org.extex.scanner.type.token.Token)
                 */
                @Override
                public String esc(Token token) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#escapechar()
                 */
                @Override
                public UnicodeChar escapechar() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#get(java.lang.Object,
                 *      java.lang.Object)
                 */
                @Override
                public Object get(Object extension, Object key) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getAfterassignment()
                 */
                @Override
                public Token getAfterassignment() {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.ContextMark#getBottomMark(java.lang.Object)
                 */
                @Override
                public Tokens getBottomMark(Object name) {

                    return null;
                }

                /**
                 * @see org.extex.interpreter.context.Context#getBox(java.lang.String)
                 */
                @Override
                public Box getBox(String name) {

                    return null;
                }

                /**
                 * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
                 */
                @Override
                public Catcode getCatcode(UnicodeChar c) {

                    return null;
                }

            @Override
                public Code getCode(CodeToken t) {

                    return null;
                }

            @Override
                public Conditional getConditional() {

                    return null;
                }

            @Override
                public Count getCount(String name) {

                    return null;
                }

            @Override
                public FixedCount getCountOption(String name) {

                    // not needed
                    return null;
                }

            @Override
                public MathDelimiter getDelcode(UnicodeChar c) {

                    return null;
                }

            @Override
                public Dimen getDimen(String name) {

                    return null;
                }

            @Override
                public FixedDimen getDimenOption(String name) {

                    // not needed
                    return null;
                }

            @Override
                public int getErrorCount() {

                    return 0;
                }

            @Override
                public Tokens getFirstMark(Object name) {

                    return null;
                }

            @Override
                public Font getFont(String name) {

                    return null;
                }

            @Override
                public CoreFontFactory getFontFactory() {

                    return null;
                }

            @Override
                public Glue getGlue(String name) {

                    return null;
                }

            @Override
                public FixedGlue getGlueOption(String name) {

                    // not needed
                    return null;
                }

            @Override
                public GroupInfo[] getGroupInfos() {

                    return null;
                }

            @Override
                public long getGroupLevel() {

                    return 0;
                }

            @Override
                public GroupType getGroupType() {

                    return null;
                }

            @Override
                public String getId() {

                    return null;
                }

            @Override
                public long getIfLevel() {

                    return 0;
                }

            @Override
                public InFile getInFile(String name) {

                    return null;
                }

            @Override
                public Interaction getInteraction() {

                    return null;
                }

            @Override
                public Language getLanguage(String language) {

                    return null;
                }

            @Override
                public LanguageManager getLanguageManager() {

                    return null;
                }

            @Override
                public UnicodeChar getLccode(UnicodeChar uc) {

                    return null;
                }

            @Override
                public long getMagnification() {

                    return 0;
                }

            @Override
                public MathCode getMathcode(UnicodeChar uc) {

                    return null;
                }

            @Override
                public Muskip getMuskip(String name) {

                    return null;
                }

            @Override
                public String getNamespace() {

                    return "";
                }

            @Override
                public OutFile getOutFile(String name) {

                    return null;
                }

            @Override
                public ParagraphShape getParshape() {

                    return null;
                }

            @Override
                public FixedCount getSfcode(UnicodeChar uc) {

                    return null;
                }

            @Override
                public Tokens getSplitBottomMark(Object name) {

                    return null;
                }

            @Override
                public Tokens getSplitFirstMark(Object name) {

                    return null;
                }

            @Override
                public TokenStream getStandardTokenStream() {

                    return null;
                }

            @Override
                public TokenFactory getTokenFactory() {

                    return null;
                }

            @Override
                public Tokenizer getTokenizer() {

                    return null;
                }

            @Override
                public Tokens getToks(String name) {

                    return null;
                }

            @Override
                public Tokens getToksOrNull(String name) {

                    return null;
                }

            @Override
                public Tokens getTopMark(Object name) {

                    return null;
                }

            @Override
                public TypesettingContext getTypesettingContext() {

                    return null;
                }

            @Override
                public TypesettingContextFactory getTypesettingContextFactory() {

                    // not needed
                    return null;
                }

            @Override
                public UnicodeChar getUccode(UnicodeChar lc) {

                    return null;
                }

            @Override
                public int incrementErrorCount() {

                    return 0;
                }

            @Override
                public boolean isGlobalGroup() {

                    return true;
                }

                /**
            *      org.extex.core.Locator,
                 *      org.extex.scanner.type.token.Token)
                 */
                @Override
                public void openGroup(GroupType id, Locator locator, Token start)
                        throws HelpingException {

                    // not needed
                }

            @Override
                public Conditional popConditional() throws HelpingException {

                    return null;
                }

            @Override
                public Direction popDirection() {

                    return null;
                }

                /**
            *      boolean, org.extex.interpreter.type.Code, long, boolean)
                 */
                @Override
                public void pushConditional(Locator locator, boolean value,
                        Code primitive, long branch, boolean neg) {

                    // not needed
                }

            @Override
                public void pushDirection(Direction dir) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Color color, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Direction direction, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Font font, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Language language, boolean global) {

                    // not needed
                }

                /**
            *      java.lang.Object, java.lang.Object, boolean)
                 */
                @Override
                public void set(Object extension, Object key, Object value,
                        boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(TypesettingContext context, boolean global) {

                    // not needed
                }

            @Override
                public void setAfterassignment(Token token) {

                    // not needed
                }

                /**
            *      org.extex.interpreter.type.box.Box, boolean)
                 */
                @Override
                public void setBox(String name, Box value, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.Catcode, boolean)
                 */
                @Override
                public void setCatcode(UnicodeChar c, Catcode catcode,
                        boolean global) {

                    // not needed
                }

                /**
            *      org.extex.interpreter.type.Code, boolean)
                 */
                @Override
                public void setCode(CodeToken t, Code code, boolean global) {

                    // not needed
                }

                /**
            *      long, boolean)
                 */
                @Override
                public void setCount(String name, long value, boolean global)
                        throws HelpingException {

                    // not needed
                }

                /**
            *      long)
                 */
                @Override
                public void setCountOption(String name, long value)
                        throws GeneralException {

                    // not needed
                }

                /**
            *      org.extex.typesetter.type.math.MathDelimiter, boolean)
                 */
                @Override
                public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                        boolean global) {

                    // not needed
                }

                /**
            *      org.extex.core.dimen.Dimen, boolean)
                 */
                @Override
                public void setDimen(String name, Dimen value, boolean global) {

                    // not needed
                }

                /**
            *      long, boolean)
                 */
                @Override
                public void setDimen(String name, long value, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.typesetter.tc.font.Font, boolean)
                 */
                @Override
                public void setFont(String name, Font font, boolean global) {

                    // not needed
                }

            @Override
                public void setFontFactory(CoreFontFactory fontFactory) {

                    // not needed
                }

                /**
            *      org.extex.core.glue.Glue, boolean)
                 */
                @Override
                public void setGlue(String name, Glue value, boolean global) {

                    // not needed
                }

            @Override
                public void setId(String id) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.file.InFile, boolean)
                 */
                @Override
                public void setInFile(String name, InFile file, boolean global) {

                    // not needed
                }

            @Override
                public void setInteraction(Interaction interaction) {

                    // not needed
                }

            @Override
                public void setLanguageManager(LanguageManager manager) {

                    // not needed
                }

                /**
            *      org.extex.core.UnicodeChar, boolean)
                 */
                @Override
                public void setLccode(UnicodeChar uc, UnicodeChar lc,
                        boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void setMagnification(long mag, boolean lock) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.tokens.Tokens)
                 */
                @Override
                public void setMark(Object name, Tokens mark) {

                    // not needed
                }

                /**
            *      org.extex.typesetter.type.math.MathCode, boolean)
                 */
                @Override
                public void setMathcode(UnicodeChar uc, MathCode code,
                        boolean global) {

                    // not needed
                }

                /**
            *      org.extex.core.muskip.Muskip, boolean)
                 */
                @Override
                public void setMuskip(String name, Muskip value, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void setNamespace(String namespace, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.file.OutFile, boolean)
                 */
                @Override
                public void setOutFile(String name, OutFile file, boolean global) {

                    // not needed
                }

            @Override
                public void setParshape(ParagraphShape shape) {

                    // not needed
                }

                /**
            *      org.extex.core.count.Count, boolean)
                 */
                @Override
                public void setSfcode(UnicodeChar uc, Count code, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.tokens.Tokens)
                 */
                @Override
                public void setSplitMark(Object name, Tokens mark) {

                    // not needed
                }

            @Override
                public void setStandardTokenStream(
                        TokenStream standardTokenStream) {

                    // not needed
                }

            @Override
                public void setTokenFactory(TokenFactory factory) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.tokens.Tokens, boolean)
                 */
                @Override
                public void setToks(String name, Tokens toks, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.core.UnicodeChar, boolean)
                 */
                @Override
                public void setUccode(UnicodeChar lc, UnicodeChar uc,
                        boolean global) {

                    // not needed
                }

            @Override
                public void startMarks() {

                    // not needed
                }

            @Override
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
     * <testcase primitive="\dump"> Test case checking that {@code \dump} needs
     * {@code \jobname} containing the target file.
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
            *      java.lang.String)
                 */
                @Override
                public OutputStream getOutputStream(String name, String type) {

                    return null;
                }

            @Override
                public void register(OutputStreamObserver observer) {

                    // not needed
                }

            @Override
                public void setExtension(String extension) {

                    // not needed
                }
            });
            dump.execute(Flags.NONE, new Context() {

                /**
                 * The field {@code serialVersionUID} contains the version
                 * number.
                 */
                private static final long serialVersionUID = 1L;

            @Override
                public void addUnit(UnitInfo info) {

                    // not needed
                }

            @Override
                public void afterGroup(AfterGroupObserver observer) {

                    // not needed
                }

            @Override
                public void afterGroup(Token t) {

                    // not needed
                }

            @Override
                public void clearSplitMarks() {

                    // not needed
                }

                /**
            *      org.extex.interpreter.TokenSource)
                 */
                @Override
                public void closeGroup(Typesetter typesetter, TokenSource source) {

                    // not needed
                }

            @Override
                public String esc(String name) {

                    return name;
                }

            @Override
                public String esc(Token token) {

                    return null;
                }

            @Override
                public UnicodeChar escapechar() {

                    return null;
                }

                /**
            *      java.lang.Object)
                 */
                @Override
                public Object get(Object extension, Object key) {

                    return null;
                }

            @Override
                public Token getAfterassignment() {

                    return null;
                }

            @Override
                public Tokens getBottomMark(Object name) {

                    return null;
                }

            @Override
                public Box getBox(String name) {

                    return null;
                }

            @Override
                public Catcode getCatcode(UnicodeChar c) {

                    return null;
                }

            @Override
                public Code getCode(CodeToken t) {

                    return null;
                }

            @Override
                public Conditional getConditional() {

                    return null;
                }

            @Override
                public Count getCount(String name) {

                    return null;
                }

            @Override
                public FixedCount getCountOption(String name) {

                    // not needed
                    return null;
                }

            @Override
                public MathDelimiter getDelcode(UnicodeChar c) {

                    return null;
                }

            @Override
                public Dimen getDimen(String name) {

                    return null;
                }

            @Override
                public FixedDimen getDimenOption(String name) {

                    // not needed
                    return null;
                }

            @Override
                public int getErrorCount() {

                    return 0;
                }

            @Override
                public Tokens getFirstMark(Object name) {

                    return null;
                }

            @Override
                public Font getFont(String name) {

                    return null;
                }

            @Override
                public CoreFontFactory getFontFactory() {

                    return null;
                }

            @Override
                public Glue getGlue(String name) {

                    return null;
                }

            @Override
                public FixedGlue getGlueOption(String name) {

                    // not needed
                    return null;
                }

            @Override
                public GroupInfo[] getGroupInfos() {

                    return null;
                }

            @Override
                public long getGroupLevel() {

                    return 0;
                }

            @Override
                public GroupType getGroupType() {

                    return null;
                }

            @Override
                public String getId() {

                    return null;
                }

            @Override
                public long getIfLevel() {

                    return 0;
                }

            @Override
                public InFile getInFile(String name) {

                    return null;
                }

            @Override
                public Interaction getInteraction() {

                    return null;
                }

            @Override
                public Language getLanguage(String language) {

                    return null;
                }

            @Override
                public LanguageManager getLanguageManager() {

                    return null;
                }

            @Override
                public UnicodeChar getLccode(UnicodeChar uc) {

                    return null;
                }

            @Override
                public long getMagnification() {

                    return 0;
                }

            @Override
                public MathCode getMathcode(UnicodeChar uc) {

                    return null;
                }

            @Override
                public Muskip getMuskip(String name) {

                    return null;
                }

            @Override
                public String getNamespace() {

                    return "";
                }

            @Override
                public OutFile getOutFile(String name) {

                    return null;
                }

            @Override
                public ParagraphShape getParshape() {

                    return null;
                }

            @Override
                public FixedCount getSfcode(UnicodeChar uc) {

                    return null;
                }

            @Override
                public Tokens getSplitBottomMark(Object name) {

                    return null;
                }

            @Override
                public Tokens getSplitFirstMark(Object name) {

                    return null;
                }

            @Override
                public TokenStream getStandardTokenStream() {

                    return null;
                }

            @Override
                public TokenFactory getTokenFactory() {

                    return null;
                }

            @Override
                public Tokenizer getTokenizer() {

                    return null;
                }

            @Override
                public Tokens getToks(String name) {

                    return null;
                }

            @Override
                public Tokens getToksOrNull(String name) {

                    return null;
                }

            @Override
                public Tokens getTopMark(Object name) {

                    return null;
                }

            @Override
                public TypesettingContext getTypesettingContext() {

                    return null;
                }

            @Override
                public TypesettingContextFactory getTypesettingContextFactory() {

                    // not needed
                    return null;
                }

            @Override
                public UnicodeChar getUccode(UnicodeChar lc) {

                    return null;
                }

            @Override
                public int incrementErrorCount() {

                    return 0;
                }

            @Override
                public boolean isGlobalGroup() {

                    return true;
                }

                /**
            *      org.extex.core.Locator,
                 *      org.extex.scanner.type.token.Token)
                 */
                @Override
                public void openGroup(GroupType id, Locator locator, Token start)
                        throws HelpingException {

                    // not needed
                }

            @Override
                public Conditional popConditional() throws HelpingException {

                    return null;
                }

            @Override
                public Direction popDirection() {

                    return null;
                }

                /**
            *      boolean, org.extex.interpreter.type.Code, long, boolean)
                 */
                @Override
                public void pushConditional(Locator locator, boolean value,
                        Code primitive, long branch, boolean neg) {

                    // not needed
                }

            @Override
                public void pushDirection(Direction dir) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Color color, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Direction direction, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Font font, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(Language language, boolean global) {

                    // not needed
                }

                /**
            *      java.lang.Object, java.lang.Object, boolean)
                 */
                @Override
                public void set(Object extension, Object key, Object value,
                        boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void set(TypesettingContext context, boolean global) {

                    // not needed
                }

            @Override
                public void setAfterassignment(Token token) {

                    // not needed
                }

                /**
            *      org.extex.interpreter.type.box.Box, boolean)
                 */
                @Override
                public void setBox(String name, Box value, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.Catcode, boolean)
                 */
                @Override
                public void setCatcode(UnicodeChar c, Catcode catcode,
                        boolean global) {

                    // not needed
                }

                /**
            *      org.extex.interpreter.type.Code, boolean)
                 */
                @Override
                public void setCode(CodeToken t, Code code, boolean global) {

                    // not needed
                }

                /**
            *      long, boolean)
                 */
                @Override
                public void setCount(String name, long value, boolean global)
                        throws HelpingException {

                    // not needed
                }

                /**
            *      long)
                 */
                @Override
                public void setCountOption(String name, long value)
                        throws GeneralException {

                    // not needed
                }

                /**
            *      org.extex.typesetter.type.math.MathDelimiter, boolean)
                 */
                @Override
                public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                        boolean global) {

                    // not needed
                }

                /**
            *      org.extex.core.dimen.Dimen, boolean)
                 */
                @Override
                public void setDimen(String name, Dimen value, boolean global) {

                    // not needed
                }

                /**
            *      long, boolean)
                 */
                @Override
                public void setDimen(String name, long value, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.typesetter.tc.font.Font, boolean)
                 */
                @Override
                public void setFont(String name, Font font, boolean global) {

                    // not needed
                }

            @Override
                public void setFontFactory(CoreFontFactory fontFactory) {

                    // not needed
                }

                /**
            *      org.extex.core.glue.Glue, boolean)
                 */
                @Override
                public void setGlue(String name, Glue value, boolean global) {

                    // not needed
                }

            @Override
                public void setId(String id) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.file.InFile, boolean)
                 */
                @Override
                public void setInFile(String name, InFile file, boolean global) {

                    // not needed
                }

            @Override
                public void setInteraction(Interaction interaction) {

                    // not needed
                }

            @Override
                public void setLanguageManager(LanguageManager manager) {

                    // not needed
                }

                /**
            *      org.extex.core.UnicodeChar, boolean)
                 */
                @Override
                public void setLccode(UnicodeChar uc, UnicodeChar lc,
                        boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void setMagnification(long mag, boolean lock) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.tokens.Tokens)
                 */
                @Override
                public void setMark(Object name, Tokens mark) {

                    // not needed
                }

                /**
            *      org.extex.typesetter.type.math.MathCode, boolean)
                 */
                @Override
                public void setMathcode(UnicodeChar uc, MathCode code,
                        boolean global) {

                    // not needed
                }

                /**
            *      org.extex.core.muskip.Muskip, boolean)
                 */
                @Override
                public void setMuskip(String name, Muskip value, boolean global) {

                    // not needed
                }

                /**
            *      boolean)
                 */
                @Override
                public void setNamespace(String namespace, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.file.OutFile, boolean)
                 */
                @Override
                public void setOutFile(String name, OutFile file, boolean global) {

                    // not needed
                }

            @Override
                public void setParshape(ParagraphShape shape) {

                    // not needed
                }

                /**
            *      org.extex.core.count.Count, boolean)
                 */
                @Override
                public void setSfcode(UnicodeChar uc, Count code, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.tokens.Tokens)
                 */
                @Override
                public void setSplitMark(Object name, Tokens mark) {

                    // not needed
                }

            @Override
                public void setStandardTokenStream(
                        TokenStream standardTokenStream) {

                    // not needed
                }

            @Override
                public void setTokenFactory(TokenFactory factory) {

                    // not needed
                }

                /**
            *      org.extex.scanner.type.tokens.Tokens, boolean)
                 */
                @Override
                public void setToks(String name, Tokens toks, boolean global) {

                    // not needed
                }

                /**
            *      org.extex.core.UnicodeChar, boolean)
                 */
                @Override
                public void setUccode(UnicodeChar lc, UnicodeChar uc,
                        boolean global) {

                    // not needed
                }

            @Override
                public void startMarks() {

                    // not needed
                }

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
     * by {@code \dump} contains the correct values.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
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
        assertEquals(
            "texput "
                    +
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
     * <testcase primitive="\dump"> Test case checking that {@code \dump} can
     * not be used inside a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroupError1() throws Exception {

        assertFailure(DEFINE_BRACES + "{\\dump}",
            "You can't dump inside a group");
    }

}
