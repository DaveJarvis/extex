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

            /**
             * @see org.extex.interpreter.TokenSource#addStream(org.extex.scanner.TokenStream)
             */
            public void addStream(final TokenStream stream) {

            }

            /**
             * @see org.extex.interpreter.TokenSource#closeAllStreams(org.extex.interpreter.context.Context)
             */
            public void closeAllStreams(final Context context)
                    throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#closeNextFileStream(org.extex.interpreter.context.Context)
             */
            public void closeNextFileStream(final Context context)
                    throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#execute(org.extex.scanner.type.token.Token, org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter)
             */
            public void execute(final Token token, final Context context,
                    final Typesetter typesetter) throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#executeGroup()
             */
            public void executeGroup() throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#expand(org.extex.interpreter.type.tokens.Tokens, org.extex.typesetter.Typesetter)
             */
            public Tokens expand(final Tokens tokens,
                    final Typesetter typesetter) throws GeneralException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getBox(org.extex.interpreter.Flags, org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter)
             */
            public Box getBox(final Flags flags, final Context context,
                    final Typesetter typesetter) throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getControlSequence(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter)
             */
            public CodeToken getControlSequence(final Context context,
                    final Typesetter typesetter) throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getFont(org.extex.interpreter.context.Context, java.lang.String)
             */
            public Font getFont(final Context context, final String primitive)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getKeyword(org.extex.interpreter.context.Context, java.lang.String)
             */
            public boolean getKeyword(final Context context,
                    final String keyword) throws InterpreterException {

                return false;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getLastToken()
             */
            public Token getLastToken() {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getLocator()
             */
            public Locator getLocator() {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getNonSpace(org.extex.interpreter.context.Context)
             */
            public Token getNonSpace(final Context context)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getOptionalEquals(org.extex.interpreter.context.Context)
             */
            public void getOptionalEquals(final Context context)
                    throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#getToken(org.extex.interpreter.context.Context)
             */
            public Token getToken(final Context context)
                    throws InterpreterException {

                try {
                    return new TokenFactoryImpl().createToken(Catcode.ESCAPE,
                        null, "x", "");
                } catch (CatcodeException e) {
                    throw new InterpreterException(e);
                }
            }

            /**
             * @see org.extex.interpreter.TokenSource#getTokenStreamFactory()
             */
            public TokenStreamFactory getTokenStreamFactory() {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#getTokens(org.extex.interpreter.context.Context, org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
             */
            public Tokens getTokens(final Context context,
                    final TokenSource source, final Typesetter typesetter)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#push(org.extex.scanner.type.token.Token)
             */
            public void push(final Token token) throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#push(org.extex.scanner.type.token.Token[])
             */
            public void push(final Token[] tokens) throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#push(org.extex.interpreter.type.tokens.Tokens)
             */
            public void push(final Tokens tokens) throws InterpreterException {

            }

            /**
             * @see org.extex.interpreter.TokenSource#scanCharacterCode(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter, java.lang.String)
             */
            public UnicodeChar scanCharacterCode(final Context context,
                    final Typesetter typesetter, final String primitive)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanNonSpace(org.extex.interpreter.context.Context)
             */
            public Token scanNonSpace(final Context context)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanNumber(org.extex.interpreter.context.Context)
             */
            public long scanNumber(final Context context)
                    throws InterpreterException {

                return 0;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanNumber(org.extex.interpreter.context.Context, org.extex.scanner.type.token.Token)
             */
            public long scanNumber(final Context context, final Token token)
                    throws InterpreterException {

                return 0;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanRegisterName(org.extex.interpreter.context.Context, org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter, java.lang.String)
             */
            public String scanRegisterName(final Context context,
                    final TokenSource source, final Typesetter typesetter,
                    final String primitive) throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanToken(org.extex.interpreter.context.Context)
             */
            public Token scanToken(final Context context)
                    throws InterpreterException {

                return getToken(context);
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanTokens(org.extex.interpreter.context.Context, boolean, boolean, java.lang.String)
             */
            public Tokens scanTokens(final Context context,
                    final boolean reportUndefined,
                    final boolean ignoreUndefined, final String primitive)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanTokensAsString(org.extex.interpreter.context.Context, java.lang.String)
             */
            public String scanTokensAsString(final Context context,
                    final String primitive) throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#scanUnprotectedTokens(org.extex.interpreter.context.Context, boolean, boolean, java.lang.String)
             */
            public Tokens scanUnprotectedTokens(final Context context,
                    final boolean reportUndefined,
                    final boolean ignoreUndefined, final String primitive)
                    throws InterpreterException {

                return null;
            }

            /**
             * @see org.extex.interpreter.TokenSource#skipSpace()
             */
            public void skipSpace() {

            }

            /**
             * @see org.extex.interpreter.TokenSource#update(java.lang.String, java.lang.String)
             */
            public void update(final String name, final String text)
                    throws InterpreterException,
                        NotObservableException {

            }
        }, null);
        assertTrue(x);
    }

}
