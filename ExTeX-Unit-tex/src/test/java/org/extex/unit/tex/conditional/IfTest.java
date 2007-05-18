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

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Mudimen;
import org.extex.core.muskip.Muskip;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.Parser;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.TokenStream;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.test.ExTeXLauncher;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

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
    public static void main(String[] args) {

        junit.textui.TestRunner.run(IfTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public IfTest(String arg) {

        super(arg);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEof1() throws Exception {

        assertFailure(// --- input code ---
            "\\if",
            // --- output channel ---
            "Unexpected end of file while processing \\if");
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\if aa true\\else false\\fi\\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\if ab true\\else false\\fi\\end",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase> The TeXbook
     * 
     * @throws Exception in case of an error
     */
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}"
                    + "\\if *\\a true\\else false\\fi\\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test11() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}"
                    + "\\if \\a* true\\else false\\fi\\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*"
                    + "\\if \\a\\b true\\else false\\fi\\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test13() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*"
                    + "\\if \\b\\a true\\else false\\fi\\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test14() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\a\\c true\\else false\\fi\\end",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test15() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\a\\par true\\else false\\fi\\end",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test16() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{*}\\let\\b=*\\def\\c{/}"
                    + "\\if \\par\\let true\\else false\\fi\\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * The field <tt>X_SOURCE</tt> contains the mock token source.
     */
    private static final TokenSource X_SOURCE = new TokenSource() {

        /**
         * Put a given stream on top of the stream stack. The reading occurs on
         * this new stream before resorting to the previous streams.
         * 
         * @param stream the new stream to read from
         * 
         * @see org.extex.interpreter.TokenSource#addStream(
         *      org.extex.scanner.TokenStream)
         */
        public void addStream(TokenStream stream) {

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
        public void closeAllStreams(Context context) {

        }

        /**
         * Close all streams on the stack until a file stream is found. This
         * file stream is closed as last one. The other streams are left
         * unchanged. If no file stream is found the all streams are closed and
         * none is left.
         * 
         * @param context the interpreter context
         * 
         * @see org.extex.interpreter.TokenSource#closeNextFileStream(
         *      org.extex.interpreter.context.Context)
         */
        public void closeNextFileStream(Context context) {

        }

        /**
         * Tries to execute a token.
         * 
         * @param token the Token to execute
         * @param context the interpreter context
         * @param typesetter the typesetter
         * 
         * @see org.extex.interpreter.TokenSource#execute(
         *      org.extex.scanner.type.token.Token,
         *      org.extex.interpreter.context.Context,
         *      org.extex.typesetter.Typesetter)
         */
        public void execute(Token token, Context context, Typesetter typesetter) {

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
         *      org.extex.scanner.type.tokens.Tokens,
         *      org.extex.typesetter.Typesetter)
         */
        public Tokens expand(Tokens tokens, Typesetter typesetter) {

            return null;
        }

        /**
         * Parse the specification of a box.
         * 
         * @param flags the flags to be restored
         * @param context the interpreter context
         * @param typesetter the typesetter to use
         * @param insert the token to insert either at the beginning of the box
         *        or after the box has been gathered. If it is <code>null</code>
         *        then nothing is inserted
         * 
         * @return the box gathered
         * 
         * @see org.extex.interpreter.TokenSource#getBox(
         *      org.extex.interpreter.Flags,
         *      org.extex.interpreter.context.Context,
         *      org.extex.typesetter.Typesetter, Token)
         */
        public Box getBox(Flags flags, Context context, Typesetter typesetter,
                Token insert) throws TypesetterException {

            return null;
        }

        /**
         * Get the next token from the token stream and check that it is a
         * control sequence or active character. At the end of all input streams
         * the control sequence "inaccessible" is inserted and an exception is
         * thrown. Thus this method will never return <code>null</code>.
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
        public CodeToken getControlSequence(Context context,
                Typesetter typesetter) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.TokenSource#getFont(org.extex.interpreter.context.Context,
         *      java.lang.String)
         */
        public Font getFont(Context context, String primitive)
                throws TypesetterException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.TokenSource#getKeyword(org.extex.interpreter.context.Context,
         *      java.lang.String)
         */
        public boolean getKeyword(Context context, String keyword) {

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
         *      org.extex.interpreter.context.Context)
         */
        public Token getNonSpace(Context context) {

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
        public void getOptionalEquals(Context context) {

        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.TokenSource#getToken(
         *      org.extex.interpreter.context.Context)
         */
        public Token getToken(Context context) throws HelpingException {

            try {
                return new TokenFactoryImpl().createToken(Catcode.ESCAPE, null,
                    "x", "");
            } catch (CatcodeException e) {
                throw new NoHelpException(e);
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
         * Get the next tokens form the input streams between <code>{</code>
         * and <code>}</code>.
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
        public Tokens getTokens(Context context, TokenSource source,
                Typesetter typesetter) throws TypesetterException {

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
        public void push(Token token) {

        }

        /**
         * Push back a list of tokens onto the input stream for subsequent
         * reading.
         * 
         * @param tokens the tokens to push
         * 
         * @see org.extex.interpreter.TokenSource#push(
         *      org.extex.scanner.type.token.Token[])
         */
        public void push(Token[] tokens) {

        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.TokenSource#push(org.extex.scanner.type.tokens.Tokens)
         */
        public void push(Tokens tokens) {

        }

        /**
         * Scan the input stream for tokens making up a character code, this is
         * a sequence of digits with category code <tt>OTHER</tt>. The number
         * can be preceded by optional white space. Alternate representations
         * for an character code exist.
         * 
         * @param context the interpreter context
         * @param typesetter the typesetter
         * @param primitive the name of the invoking primitive for error
         *        handling
         * 
         * @return the value of the integer scanned
         * 
         * @see org.extex.interpreter.TokenSource#scanCharacterCode(
         *      org.extex.interpreter.context.Context,
         *      org.extex.typesetter.Typesetter, java.lang.String)
         */
        public UnicodeChar scanCharacterCode(Context context,
                Typesetter typesetter, String primitive)
                throws TypesetterException {

            return null;
        }

        /**
         * Scan the input for the next token which has not the category code
         * SPACE.
         * 
         * @param context the interpreter context
         * 
         * @return the next non-space token or <code>null</code> at EOF
         * 
         * @see org.extex.interpreter.TokenSource#scanNonSpace(
         *      org.extex.interpreter.context.Context)
         */
        public Token scanNonSpace(Context context) throws TypesetterException {

            return null;
        }

        /**
         * Scan the input streams for an entity to denote a register name. Upon
         * EOF <code>null</code> is returned.
         * 
         * @param context the interpreter context
         * @param source the source for new tokens
         * @param typesetter the typesetter
         * @param primitive the name of the invoking primitive for error
         *        handling
         * 
         * @return the register name encountered
         * 
         * @see org.extex.interpreter.TokenSource#scanRegisterName(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter, java.lang.String)
         */
        public String scanRegisterName(Context context, TokenSource source,
                Typesetter typesetter, String primitive)
                throws TypesetterException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.TokenSource#scanToken(org.extex.interpreter.context.Context)
         */
        public Token scanToken(Context context)
                throws HelpingException,
                    TypesetterException {

            return getToken(context);
        }

        /**
         * Get the next expanded token form the input streams between
         * <code>{</code> and <code>}</code>.
         * 
         * @param context the interpreter context
         * @param primitive the name of the invoking primitive for error
         *        handling
         * @param reportUndefined indicator that an undefined control sequence
         *        leads to an exception. This parameter is effective only if
         *        ignoreUndefined is <code>false</code>
         * @param ignoreUndefined indicator that an undefined control sequence
         *        should be treated as <tt>\relax</tt>
         * 
         * @return the next tokens or <code>null</code>
         * 
         * @see org.extex.interpreter.TokenSource#scanTokens(
         *      org.extex.interpreter.context.Context, boolean, boolean,
         *      java.lang.String)
         */
        public Tokens scanTokens(Context context, boolean reportUndefined,
                boolean ignoreUndefined, String primitive)
                throws TypesetterException {

            return null;
        }

        /**
         * Get the next expanded token form the input streams between a left
         * brace character (usually <code>{</code>) and a right brace
         * character (usually <code>}</code>) and convert it to a
         * <code>String</code>.
         * 
         * @param context the interpreter context
         * @param primitive the name of the invoking primitive for error
         *        handling
         * 
         * @return the next tokens as <code>String</code> or <code>null</code>
         * 
         * @see org.extex.interpreter.TokenSource#scanTokensAsString(
         *      org.extex.interpreter.context.Context, java.lang.String)
         */
        public String scanTokensAsString(Context context, String primitive)
                throws TypesetterException {

            return null;
        }

        /**
         * Get the next expanded token form the input streams between
         * <code>{</code> and <code>}</code>. If the current input stream
         * is at its end then the next one on the streamStack is used until a
         * token could be read. If all stream are at the end then
         * <code>null</code> is returned.
         * 
         * @param context the interpreter context
         * @param primitive the name of the invoking primitive for error
         *        handling
         * @param reportUndefined indicator that an undefined control sequence
         *        leads to an exception. This parameter is effective only if
         *        ignoreUndefined is <code>false</code>
         * @param ignoreUndefined indicator that an undefined control sequence
         *        should be treated as <tt>\relax</tt>
         * 
         * @return the next tokens or <code>null</code>
         * 
         * @see org.extex.interpreter.TokenSource#scanUnprotectedTokens(
         *      org.extex.interpreter.context.Context, boolean, boolean,
         *      java.lang.String)
         */
        public Tokens scanUnprotectedTokens(Context context,
                boolean reportUndefined, boolean ignoreUndefined,
                String primitive) throws TypesetterException {

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
         * Send the string to the named observer. The observer must be capable
         * to deal with a string argument.
         * 
         * @param name name of the observer
         * @param text the text to send to the observer
         * 
         * @see org.extex.interpreter.TokenSource#update( java.lang.String,
         *      java.lang.String)
         */
        public void update(String name, String text) {

        }

        public long parseInteger(Context context, TokenSource source,
                Typesetter typesetter) {

            return 0;
        }

        public long parseNumber(Context context, TokenSource source,
                Typesetter typesetter) {

            return 0;
        }

        public Dimen parseDimen(Context context, TokenSource source,
                Typesetter typesetter) {

            return null;
        }

        public Glue parseGlue(Context context, TokenSource source,
                Typesetter typesetter) {

            return null;
        }

        public Mudimen parseMudimen(Context context, TokenSource source,
                Typesetter typesetter) {

            return null;
        }

        public Muskip parseMuskip(Context context, TokenSource source,
                Typesetter typesetter) {

            return null;
        }

        @SuppressWarnings("unchecked")
        public Object parse(Class c, Context context, TokenSource source,
                Typesetter typesetter)
                throws HelpingException,
                    TypesetterException {

            return null;
        }

        @SuppressWarnings("unchecked")
        public void register(Class c, Parser p) {

        }
    };

    /**
     * <testcase primitive="\if"> Test case checking that <tt>\if</tt> ...
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test20() throws Exception {

        assertTrue(new If("if").conditional(null, X_SOURCE, null));
    }

}
