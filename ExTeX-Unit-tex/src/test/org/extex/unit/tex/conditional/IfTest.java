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

package org.extex.unit.tex.conditional;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.TokenStream;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.test.ExTeXLauncher;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;
import org.extex.util.exception.NotObservableException;

/**
 * This is a test suite for the primitive <tt>\if</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IfTest extends ExTeXLauncher {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(IfTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public IfTest(final String arg) {

        super(arg);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof1() throws Exception {

        assertFailure(//--- input code ---
            "\\if",
            //--- output channel ---
            "Unexpected end of file while processing \\if");
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(//--- input code ---
            "\\if aa true\\else false\\fi\\end",
            //--- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(//--- input code ---
            "\\if ab true\\else false\\fi\\end",
            //--- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     * The TeXbook
     *
     * @throws Exception in case of an error
     */
    public void test10() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if *\\a true\\else false\\fi\\end",
            //--- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test11() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\a* true\\else false\\fi\\end",
            //--- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\a\\b true\\else false\\fi\\end",
            //--- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test13() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\b\\a true\\else false\\fi\\end",
            //--- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test14() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\a\\c true\\else false\\fi\\end",
            //--- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test15() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\a\\par true\\else false\\fi\\end",
            //--- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test16() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\par\\let true\\else false\\fi\\end",
            //--- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test20() throws Exception {

        If if1 = new If("if");
        boolean x = if1.conditional(null, new TokenSource() {

            public void addStream(TokenStream stream) {

            }

            public void closeAllStreams(Context context)
                    throws InterpreterException {

            }

            public void closeNextFileStream(Context context)
                    throws InterpreterException {

            }

            public void execute(Token token, Context context,
                    Typesetter typesetter) throws InterpreterException {

            }

            public void executeGroup() throws InterpreterException {

            }

            public Tokens expand(Tokens tokens, Typesetter typesetter)
                    throws GeneralException {

                return null;
            }

            public Box getBox(Flags flags, Context context,
                    Typesetter typesetter) throws InterpreterException {

                return null;
            }

            public CodeToken getControlSequence(Context context, Typesetter typesetter)
                    throws InterpreterException {

                return null;
            }

            public Font getFont(Context context, String primitive)
                    throws InterpreterException {

                return null;
            }

            public boolean getKeyword(Context context, String keyword)
                    throws InterpreterException {

                return false;
            }

            public Token getLastToken() {

                return null;
            }

            public Locator getLocator() {

                return null;
            }

            public Token getNonSpace(Context context)
                    throws InterpreterException {

                return null;
            }

            public void getOptionalEquals(Context context)
                    throws InterpreterException {

            }

            public Token getToken(final Context context)
                    throws InterpreterException {

                try {
                    return new TokenFactoryImpl().createToken(Catcode.ESCAPE,
                        null, "x", "");
                } catch (CatcodeException e) {
                    throw new InterpreterException(e);
                }
            }

            public TokenStreamFactory getTokenStreamFactory() {

                return null;
            }

            public Tokens getTokens(Context context, TokenSource source,
                    Typesetter typesetter) throws InterpreterException {

                return null;
            }

            public void push(Token token) throws InterpreterException {

            }

            public void push(Token[] tokens) throws InterpreterException {

            }

            public void push(Tokens tokens) throws InterpreterException {

            }

            public UnicodeChar scanCharacterCode(Context context,
                    Typesetter typesetter, String primitive)
                    throws InterpreterException {

                return null;
            }

            public Token scanNonSpace(Context context)
                    throws InterpreterException {

                return null;
            }

            public long scanNumber(Context context) throws InterpreterException {

                return 0;
            }

            public long scanNumber(Context context, Token token)
                    throws InterpreterException {

                return 0;
            }

            public String scanRegisterName(Context context, TokenSource source,
                    Typesetter typesetter, String primitive)
                    throws InterpreterException {

                return null;
            }

            public Token scanToken(Context context) throws InterpreterException {

                return getToken(context);
            }

            public Tokens scanTokens(Context context, boolean reportUndefined,
                    boolean ignoreUndefined, String primitive)
                    throws InterpreterException {

                return null;
            }

            public String scanTokensAsString(Context context, String primitive)
                    throws InterpreterException {

                return null;
            }

            public Tokens scanUnprotectedTokens(Context context,
                    boolean reportUndefined, boolean ignoreUndefined,
                    String primitive) throws InterpreterException {

                return null;
            }

            public void skipSpace() {

            }

            public void update(String name, String text)
                    throws InterpreterException,
                        NotObservableException {

            }
        }, null);
        assertTrue(x);
    }

}
