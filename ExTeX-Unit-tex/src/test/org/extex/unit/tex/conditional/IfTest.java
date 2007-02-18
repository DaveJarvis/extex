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
            DEFINE_BRACES + "\\def\\a{*}"
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
            DEFINE_BRACES + "\\def\\a{*}"
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
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*"
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
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*"
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
     * The field <tt>X_SOURCE</tt> contains the ...
     */
    private static final TokenSource X_SOURCE = new TokenSource() {

        /**
         * Put a given stream on top of the stream stack. The reading occurs
         * on this new stream before resorting to the previous streams.
         *
         * @param stream the new stream to read from
         *
         * @see org.extex.interpreter.TokenSource#addStream(
         *      org.extex.scanner.TokenStream)
         */
        public void addStream(final TokenStream stream) {

        }

        /**
         * All input streams are closed and not further Token is available for
         * processing. This normally means that the interpreter is forced to
         * terminate more or less gracefully.
         *
         * @param context the interpreter context
         *
         * @see org.extex.interpreter.TokenSource#closeAllStreams(
         *      org.extex.interpreter.context.Context)
         */
        public void closeAllStreams(final Context context) {

        }

        /**
         * Close all streams on the stack until a file stream is found. This
         * file stream is closed as last one. The other streams are left
         * unchanged. If no file stream is found the all streams are closed
         * and none is left.
         *
         * @param context the interpreter context
         *
         * @see org.extex.interpreter.TokenSource#closeNextFileStream(
         *      org.extex.interpreter.context.Context)
         */
        public void closeNextFileStream(final Context context) {

        }

        /**
         * Tries to execute a token.
         *
         * @param token the Token to execute
         * @param context the interpreter context
         * @param typesetter the typesetter
         *
         * @see org.extex.interpreter.TokenSource#execute(
         *       org.extex.scanner.type.token.Token,
         *       org.extex.interpreter.context.Context,
         *       org.extex.typesetter.Typesetter)
         */
        public void execute(final Token token, final Context context,
                final Typesetter typesetter) {

        }

        /**
         * Scan and execute tokens until the group ends.
         *
         * @see org.extex.interpreter.TokenSource#executeGroup()
         */
        public void executeGroup() {

        }

        /**
         * Expand some tokens.
         *
         * @param tokens the tokens to expand
         * @param typesetter the typesetter to use
         *
         * @return the expanded tokens
         *
         * @see org.extex.interpreter.TokenSource#expand(
         *      org.extex.interpreter.type.tokens.Tokens,
         *      org.extex.typesetter.Typesetter)
         */
        public Tokens expand(final Tokens tokens, final Typesetter typesetter) {

            return null;
        }

        /**
         * Parse the specification of a box.
         *
         * @param flags the flags to be restored
         * @param context the interpreter context
         * @param typesetter the typesetter to use
         * @param insert the token to insert either at the beginning of the box or
         *   after the box has been gathered. If it is <code>null</code> then
         *   nothing is inserted
         *
         * @return the box gathered
         *
         * @see org.extex.interpreter.TokenSource#getBox(
         *      org.extex.interpreter.Flags,
         *      org.extex.interpreter.context.Context,
         *      org.extex.typesetter.Typesetter, Token)
         */
        public Box getBox(final Flags flags, final Context context,
                final Typesetter typesetter, final Token insert) {

            return null;
        }

        /**
         * Get the next token from the token stream and check that it is a control
         * sequence or active character. At the end of all input streams the control
         * sequence "inaccessible" is inserted and an exception is thrown. Thus this
         * method will never return <code>null</code>.
         *
         * @param context the interpreter context
         * @param typesetter the typesetter
         *
         * @return the token read
         *
         * @see org.extex.interpreter.TokenSource#getControlSequence(
         *      org.extex.interpreter.context.Context,
         *      org.extex.typesetter.Typesetter)
         */
        public CodeToken getControlSequence(final Context context,
                final Typesetter typesetter) {

            return null;
        }

        /**
         * Parse the specification of a font.
         *
         * @param context the interpreter context
         * @param primitive the name of the primitive for error messages
         *
         * @return a font specification
         *
         * @throws InterpreterException in case of an error
         *
         * @see org.extex.interpreter.TokenSource#getFont(
         *       org.extex.interpreter.context.Context, java.lang.String)
         */
        public Font getFont(final Context context, final String primitive)
                throws InterpreterException {

            return null;
        }

        /**
         * Get tokens from the token stream searching for a sequence of letter
         * tokens.
         *
         * @param context the interpreter context
         * @param keyword the tokens to scan
         *
         * @return <code>true</code> iff the tokens could have been successfully
         *         removed from the input stream
         *
         * @throws InterpreterException in case of an error
         *
         * @see org.extex.interpreter.TokenSource#getKeyword(
         *      org.extex.interpreter.context.Context, java.lang.String)
         */
        public boolean getKeyword(final Context context, final String keyword)
                throws InterpreterException {

            return false;
        }

        /**
         * Getter for the token just previously read from the token source. This
         * is something like a look-back function.
         *
         * @return the last token or <code>null</code> if not available
         *
         * @see org.extex.interpreter.TokenSource#getLastToken()
         */
        public Token getLastToken() {

            return null;
        }

        /**
         * Getter for the locator. The locator provides a means to get the
         * information where something is coming from. Usually it points to a
         * line in a file.
         *
         * @return the current locator
         *
         * @see org.extex.interpreter.TokenSource#getLocator()
         */
        public Locator getLocator() {

            return null;
        }

        /**
         * Get the next token which has not the category code
         * {@link org.extex.scanner.type.Catcode#SPACE SPACE}.
         *
         * @param context the interpreter context
         *
         * @return the next non-space token or <code>null</code> at EOF
         *
         * @see org.extex.interpreter.TokenSource#getNonSpace(
         *       org.extex.interpreter.context.Context)
         */
        public Token getNonSpace(final Context context) {

            return null;
        }

        /**
         * Skip spaces and if the next non-space character is an equal sign skip
         * it as well and all spaces afterwards.
         *
         * @param context the interpreter context
         *
         * @see org.extex.interpreter.TokenSource#getOptionalEquals(
         *      org.extex.interpreter.context.Context)
         */
        public void getOptionalEquals(final Context context) {

        }

        /**
         * Get the next token form the input streams.
         *
         * @param context the interpreter context
         *
         * @return the next token or <code>null</code>
         *
         * @throws InterpreterException in case of an error
         *
         * @see org.extex.interpreter.TokenSource#getToken(
         *      org.extex.interpreter.context.Context)
         */
        public Token getToken(final Context context)
                throws InterpreterException {

            try {
                return new TokenFactoryImpl().createToken(Catcode.ESCAPE, null,
                    "x", "");
            } catch (CatcodeException e) {
                throw new InterpreterException(e);
            }
        }

        /**
         * Getter for the token stream factory. The token stream factory can be
         * used to acquire a new token stream.
         *
         * @return the token stream factory
         *
         * @see org.extex.interpreter.TokenSource#getTokenStreamFactory()
         */
        public TokenStreamFactory getTokenStreamFactory() {

            return null;
        }

        /**
         * Get the next tokens form the input streams between <code>{</code> and
         * <code>}</code>.
         *
         * @param context the interpreter context
         * @param source the source for new tokens
         * @param typesetter the typesetter
         *
         * @return the next tokens or <code>null</code>
         *
         * @see org.extex.interpreter.TokenSource#getTokens(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter)
         */
        public Tokens getTokens(final Context context,
                final TokenSource source, final Typesetter typesetter) {

            return null;
        }

        /**
         * Push back a token onto the input stream for subsequent reading.
         *
         * @param token the token to push
         *
         * @see org.extex.interpreter.TokenSource#push(
         *      org.extex.scanner.type.token.Token)
         */
        public void push(final Token token) {

        }

        /**
         * Push back a list of tokens onto the input stream for subsequent reading.
         *
         * @param tokens the tokens to push
         *
         * @see org.extex.interpreter.TokenSource#push(
         *      org.extex.scanner.type.token.Token[])
         */
        public void push(final Token[] tokens) {

        }

        /**
         * Push back a list of tokens onto the input stream for subsequent reading.
         * In case that the argument is <code>null</code> then it is silently
         * ignored.
         *
         * @param tokens the tokens to push
         *
         * @see org.extex.interpreter.TokenSource#push(
         *      org.extex.interpreter.type.tokens.Tokens)
         */
        public void push(final Tokens tokens) throws InterpreterException {

        }

        /**
         * Scan the input stream for tokens making up a character code, this is a
         * sequence of digits with category code <tt>OTHER</tt>. The number can
         * be preceded by optional white space. Alternate representations for an
         * character code exist.
         *
         * @param context the interpreter context
         * @param typesetter the typesetter
         * @param primitive the name of the invoking primitive for error handling
         *
         * @return the value of the integer scanned
         *
         * @see org.extex.interpreter.TokenSource#scanCharacterCode(
         *      org.extex.interpreter.context.Context,
         *      org.extex.typesetter.Typesetter, java.lang.String)
         */
        public UnicodeChar scanCharacterCode(final Context context,
                final Typesetter typesetter, final String primitive) {

            return null;
        }

        /**
         * Scan the input for the next token which has not the category code SPACE.
         *
         * @param context the interpreter context
         *
         * @return the next non-space token or <code>null</code> at EOF
         *
         * @see org.extex.interpreter.TokenSource#scanNonSpace(
         *      org.extex.interpreter.context.Context)
         */
        public Token scanNonSpace(final Context context) {

            return null;
        }

        /**
         * @deprecated use Count.scanNumber() instead
         * @see org.extex.interpreter.TokenSource#scanNumber(
         *      org.extex.interpreter.context.Context)
         */
        public long scanNumber(final Context context)
                throws InterpreterException {

            return 0;
        }

        /**
         * @deprecated use Count.scanNumber() instead
         * @see org.extex.interpreter.TokenSource#scanNumber(
         *       org.extex.interpreter.context.Context,
         *       org.extex.scanner.type.token.Token)
         */
        public long scanNumber(final Context context, final Token token)
                throws InterpreterException {

            return 0;
        }

        /**
         * Scan the input streams for an entity to denote a register name. Upon EOF
         * <code>null</code> is returned.
         *
         * @param context the interpreter context
         * @param source the source for new tokens
         * @param typesetter the typesetter
         * @param primitive the name of the invoking primitive for error handling
         *
         * @return the register name encountered
         *
         * @see org.extex.interpreter.TokenSource#scanRegisterName(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      java.lang.String)
         */
        public String scanRegisterName(final Context context,
                final TokenSource source, final Typesetter typesetter,
                final String primitive) {

            return null;
        }

        /**
         * Get the next expanded token form the input streams. If the current input
         * stream is at its end then the next one on the streamStack is used until a
         * token could be read. If all streams are at the end then <code>null</code>
         * is returned.
         *
         * @param context the interpreter context
         *
         * @return the next token or <code>null</code>
         *
         * @throws InterpreterException in case of an error
         *
         * @see org.extex.interpreter.TokenSource#scanToken(
         *      org.extex.interpreter.context.Context)
         */
        public Token scanToken(final Context context)
                throws InterpreterException {

            return getToken(context);
        }

        /**
         * Get the next expanded token form the input streams between <code>{</code>
         * and <code>}</code>.
         *
         * @param context the interpreter context
         * @param primitive the name of the invoking primitive for error handling
         * @param reportUndefined indicator that an undefined control sequence leads
         *            to an exception. This parameter is effective only if
         *            ignoreUndefined is <code>false</code>
         * @param ignoreUndefined indicator that an undefined control sequence
         *            should be treated as <tt>\relax</tt>
         *
         * @return the next tokens or <code>null</code>
         *
         * @see org.extex.interpreter.TokenSource#scanTokens(
         *      org.extex.interpreter.context.Context, boolean, boolean,
         *      java.lang.String)
         */
        public Tokens scanTokens(final Context context,
                final boolean reportUndefined, final boolean ignoreUndefined,
                final String primitive) {

            return null;
        }

        /**
         * Get the next expanded token form the input streams between a left brace
         * character (usually <code>{</code>) and a right brace character
         * (usually <code>}</code>) and convert it to a <code>String</code>.
         *
         * @param context the interpreter context
         * @param primitive the name of the invoking primitive for error handling
         *
         * @return the next tokens as <code>String</code> or <code>null</code>
         *
         * @see org.extex.interpreter.TokenSource#scanTokensAsString(
         *      org.extex.interpreter.context.Context, java.lang.String)
         */
        public String scanTokensAsString(final Context context,
                final String primitive) {

            return null;
        }

        /**
         * Get the next expanded token form the input streams between <code>{</code>
         * and <code>}</code>. If the current input stream is at its end then the
         * next one on the streamStack is used until a token could be read. If all
         * stream are at the end then <code>null</code> is returned.
         *
         * @param context the interpreter context
         * @param primitive the name of the invoking primitive for error handling
         * @param reportUndefined indicator that an undefined control sequence leads
         *            to an exception. This parameter is effective only if
         *            ignoreUndefined is <code>false</code>
         * @param ignoreUndefined indicator that an undefined control sequence
         *            should be treated as <tt>\relax</tt>
         *
         * @return the next tokens or <code>null</code>
         *
         * @see org.extex.interpreter.TokenSource#scanUnprotectedTokens(
         *      org.extex.interpreter.context.Context, boolean, boolean,
         *      java.lang.String)
         */
        public Tokens scanUnprotectedTokens(final Context context,
                final boolean reportUndefined, final boolean ignoreUndefined,
                final String primitive) {

            return null;
        }

        /**
         * Skip spaces and check whether any tokens are left.
         *
         * @see org.extex.interpreter.TokenSource#skipSpace()
         */
        public void skipSpace() {

        }

        /**
         * Send the string to the named observer. The observer must be capable to
         * deal with a string argument.
         *
         * @param name name of the observer
         * @param text the text to send to the observer
         *
         * @see org.extex.interpreter.TokenSource#update(
         *       java.lang.String, java.lang.String)
         */
        public void update(final String name, final String text) {

        }
    };

    /**
     * <testcase primitive="\if">
     *  Test case checking that <tt>\if</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test20() throws Exception {

        assertTrue(new If("if").conditional(null, X_SOURCE, null));
    }

}
