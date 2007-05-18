/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.NotObservableException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountParser;
import org.extex.interpreter.parser.DimenParser;
import org.extex.interpreter.parser.GlueParser;
import org.extex.interpreter.parser.Parser;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.TokenStream;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This interface describes a class to acquire
 * {@link org.extex.scanner.type.token.Token Token}s from. Beside the pure
 * getter for the next token some higher-level parsing methods are provided here
 * as well.
 * 
 * <p>
 * There are two classes of methods for reading something from a token stream.
 * The methods starting with <tt>get</tt> perform the raw reading, whereas the
 * methods starting with <tt>scan</tt> perform expansion as well.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4388 $
 */
public interface TokenSource extends CountParser, DimenParser, GlueParser {

    /**
     * Put a given stream on top of the stream stack. The reading occurs on this
     * new stream before resorting to the previous streams.
     * 
     * @param stream the new stream to read from
     */
    void addStream(TokenStream stream);

    /**
     * All input streams are closed and not further Token is available for
     * processing. This normally means that the interpreter is forced to
     * terminate more or less gracefully.
     * 
     * @param context the interpreter context
     * 
     * @throws HelpingException in case of an error
     */
    void closeAllStreams(Context context) throws HelpingException;

    /**
     * Close all streams on the stack until a file stream is found. This file
     * stream is closed as last one. The other streams are left unchanged. If no
     * file stream is found the all streams are closed and none is left.
     * 
     * @param context the interpreter context
     * 
     * @throws HelpingException in case of an error
     */
    void closeNextFileStream(Context context) throws HelpingException;

    /**
     * Tries to execute a token.
     * 
     * @param token the Token to execute
     * @param context the interpreter context
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error<br>
     *         especially<br>
     *         ErrorLimitException in case that the error limit has been reached
     */
    void execute(Token token, Context context, Typesetter typesetter)
            throws HelpingException;

    /**
     * Scan and execute tokens until the group ends.
     * 
     * @throws HelpingException in case of an error<br>
     *         especially<br>
     *         ErrorLimitException in case that the error limit has been reached
     */
    void executeGroup() throws HelpingException;

    /**
     * Expand some tokens.
     * 
     * @param tokens the tokens to expand
     * @param typesetter the typesetter to use
     * 
     * @return the expanded tokens
     * 
     * @throws GeneralException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     */
    Tokens expand(Tokens tokens, Typesetter typesetter)
            throws GeneralException,
                ConfigurationException;

    /**
     * Parse the specification of a box.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="box">
     * <h3>A Box</h3>
     * 
     * <pre class="syntax">
     *    &lang;box&rang;
     * </pre>
     * 
     * </doc>
     * 
     * During the parsing the flags should be reset to something uncritical and
     * restored at the end.
     * 
     * @param flags the flags to be restored
     * @param context the interpreter context
     * @param typesetter the typesetter to use
     * @param insert the token to insert either at the beginning of the box or
     *        after the box has been gathered. If it is <code>null</code> then
     *        nothing is inserted
     * 
     * @return the box gathered
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Box getBox(Flags flags, Context context, Typesetter typesetter, Token insert)
            throws HelpingException,
                ConfigurationException,
                TypesetterException;

    /**
     * Get the next token from the token stream and check that it is a control
     * sequence or active character. At the end of all input streams the control
     * sequence "inaccessible" is inserted and an exception is thrown. Thus this
     * method will never return <code>null</code>.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="control sequence">
     * <h3>A Control Sequence</h3>
     * 
     * <p>
     * A control sequence is either a active character or an escape sequence.
     * Macros can be assigned to control sequences only.
     * </p>
     * <p>
     * The definition of a control sequence highly depends on the current
     * configuration of the parser as stored in the category codes. An active
     * character is one with category code 13. A escape sequence starts with a
     * character with category code 0 followed by an arbitrary number of letters
     * &ndash; category code 11 &ndash; or a single character with any other
     * category code.
     * </p>
     * 
     * <h4>Syntax</h4>
     * The formal description of this syntactic entity is the following:
     * 
     * <pre class="syntax">
     *    &lang;control sequence&rang;
     *      &rarr; &lt;i&gt;?&lt;sub&gt;13&lt;/sub&gt;&lt;/i&gt;
     *       |  &lt;i&gt;?&lt;sub&gt;0&lt;/sub&gt;&lt;/i&gt;&lt;i&gt;?&lt;/i&gt;
     *       |  &lt;i&gt;?&lt;sub&gt;0&lt;/sub&gt;&lt;/i&gt;&lt;i&gt;?&lt;sub&gt;11&lt;/sub&gt;&lt;/i&gt;*
     * </pre>
     * 
     * <h4>Examples</h4>
     * 
     * <pre class="TeXSample">
     *     &tilde;
     * </pre>
     * 
     * <p>
     * </p>
     * 
     * <pre class="TeXSample">
     *     \abc
     * </pre>
     * 
     * <p>
     * </p>
     * </doc>
     * 
     * @param context the interpreter context
     * @param typesetter the typesetter
     * 
     * @return the token read
     * 
     * @throws HelpingException in case that the token stream is at its end or
     *         that the token read is not a control sequence token
     */
    CodeToken getControlSequence(Context context, Typesetter typesetter)
            throws HelpingException;

    /**
     * Parse the specification of a font.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="font">
     * <h3>A Font</h3>
     * 
     * <pre class="syntax">
     *    &lang;font&rang;
     * </pre>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * @param primitive the name of the primitive for error messages
     * 
     * @return a font specification
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Font getFont(Context context, String primitive)
            throws HelpingException,
                TypesetterException;

    /**
     * Get tokens from the token stream searching for a sequence of letter
     * tokens. If all tokens are found then they are removed from the input
     * stream and <code>true</code> is returned. Otherwise all tokens are left
     * in the input stream and <code>false</code> is returned.
     * <p>
     * Spaces before the keyword are removed from the input stream. Those spaces
     * are not restored, even if the keyword is not found.
     * </p>
     * <p>
     * Space tokens after the keyword are removed from the input stream.
     * </p>
     * 
     * @param context the interpreter context
     * @param keyword the tokens to scan
     * 
     * @return <code>true</code> iff the tokens could have been successfully
     *         removed from the input stream
     * 
     * @throws HelpingException in case of an error
     */
    boolean getKeyword(Context context, String keyword) throws HelpingException;

    /**
     * Getter for the token just previously read from the token source. This is
     * something like a look-back function.
     * 
     * @return the last token or <code>null</code> if not available
     */
    Token getLastToken();

    /**
     * Getter for the locator. The locator provides a means to get the
     * information where something is coming from. Usually it points to a line
     * in a file.
     * 
     * @return the current locator
     */
    Locator getLocator();

    /**
     * Get the next token which has not the category code
     * {@link org.extex.scanner.type.Catcode#SPACE SPACE}.
     * 
     * @param context the interpreter context
     * 
     * @return the next non-space token or <code>null</code> at EOF
     * 
     * @throws HelpingException in case of an error
     */
    Token getNonSpace(Context context) throws HelpingException;

    /**
     * Skip spaces and if the next non-space character is an equal sign skip it
     * as well and all spaces afterwards.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="equals">
     * 
     * <h3>The Optional Equals</h3>
     * 
     * <p>
     * The syntactic entity &lang;equals&rang; skips initial spaces and an
     * equals sign of category 12 if one is found. In the latter case the
     * following spaces are skipped as well.
     * </p>
     * 
     * <pre class="syntax">
     *    &lang;equals&rang;
     *      &rarr; {@linkplain org.extex.interpreter.TokenSource#skipSpace()
     *             &lang;optional spaces&rang;}
     *       |  {@linkplain org.extex.interpreter.TokenSource#skipSpace()
     *             &lang;optional spaces&rang;}
     * <tt>
     * =
     * </tt>
     * &lt;sub&gt;12&lt;/sub&gt; {@linkplain
     *             org.extex.interpreter.TokenSource#skipSpace()
     *             &lang;optional spaces&rang;}
     * </pre>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * 
     * @throws HelpingException in case of an error
     */
    void getOptionalEquals(Context context) throws HelpingException;

    /**
     * Get the next token form the input streams. If the current input stream is
     * at its end then the next one on the streamStack is used until a token
     * could be read. If all stream are at the end then <code>null</code> is
     * returned.
     * 
     * <p>
     * This method corresponds to the following syntax specification:
     * </p>
     * 
     * <doc type="syntax" name="token">
     * <h3>A Token</h3>
     * 
     * <pre class="syntax">
     *    &lang;token&rang;
     * </pre>
     * 
     * <p>
     * A single token depends on the category code of the characters.
     * </p>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * 
     * @return the next token or <code>null</code>
     * 
     * @throws HelpingException in case of an error
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [332]"
     */
    Token getToken(Context context) throws HelpingException;

    /**
     * Get the next tokens form the input streams between <code>{</code> and
     * <code>}</code>. If the current input stream is at its end then the
     * next one on the streamStack is used until a token could be read. If all
     * streams are at the end then an exception is thrown.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="replacement text">
     * <h3>A Replacement Text</h3>
     * 
     * <p>
     * </p>
     * 
     * <pre class="syntax">
     *     &lang;replacement text&rang;
     * </pre>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the next tokens or <code>null</code>
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Tokens getTokens(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException;

    /**
     * Getter for the token stream factory. The token stream factory can be used
     * to acquire a new token stream.
     * 
     * @return the token stream factory
     */
    TokenStreamFactory getTokenStreamFactory();

    /**
     * Parse some data type.
     * 
     * @param c the class
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the object obtained or <code>null</code> if at end of file
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException
     */
    @SuppressWarnings("unchecked")
    Object parse(Class c, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException;

    /**
     * Push back a token onto the input stream for subsequent reading.
     * 
     * @param token the token to push
     * 
     * @throws HelpingException in case of an error
     */
    void push(Token token) throws HelpingException;

    /**
     * Push back a list of tokens onto the input stream for subsequent reading.
     * 
     * @param tokens the tokens to push
     * 
     * @throws HelpingException in case of an error
     */
    void push(Token[] tokens) throws HelpingException;

    /**
     * Push back a list of tokens onto the input stream for subsequent reading.
     * In case that the argument is <code>null</code> then it is silently
     * ignored.
     * 
     * @param tokens the tokens to push
     * 
     * @throws HelpingException in case of an error
     */
    void push(Tokens tokens) throws HelpingException;

    /**
     * Register a new parser for some data type.
     * 
     * @param c the class
     * @param p the parser for the class
     * 
     * @return the old parser for this class or <code>null</code> if none has
     *         been registered
     */
    @SuppressWarnings("unchecked")
    Parser register(Class c, Parser p);

    /**
     * Scan the input stream for tokens making up a character code, this is a
     * sequence of digits with category code <tt>OTHER</tt>. The number can
     * be preceded by optional white space. Alternate representations for an
     * character code exist.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="character">
     * <h3>A Character Code</h3>
     * 
     * <pre class="syntax">
     *    &lang;character code&rang;
     * </pre>
     * 
     * <p>
     * A character is a positive number.
     * </p>
     * <p>
     * Tokens are expanded while gathering the requested values.
     * </p>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * @param typesetter the typesetter
     * @param primitive the name of the invoking primitive for error handling
     * 
     * @return the value of the integer scanned
     * 
     * @throws HelpingException in case of an error<br>
     *         especially<br>
     *         InvalidCharacterException in case that no number is found or if
     *         it is out of the allowed range<br>
     *         EofException in case that an end of file has been encountered
     *         before the character code was complete
     * @throws TypesetterException in case of an error in the typesetter
     */
    UnicodeChar scanCharacterCode(Context context, Typesetter typesetter,
            String primitive) throws HelpingException, TypesetterException;

    /**
     * Scan the input for the next token which has not the category code SPACE.
     * 
     * @param context the interpreter context
     * 
     * @return the next non-space token or <code>null</code> at EOF
     * 
     * @throws HelpingException in case of an error in
     *         {@link #scanToken(Context) scanToken()}
     * @throws TypesetterException in case of an error in the typesetter
     */
    Token scanNonSpace(Context context)
            throws HelpingException,
                TypesetterException;

    /**
     * Scan the input streams for an entity to denote a register name. Upon EOF
     * <code>null</code> is returned.
     * 
     * <doc type="syntax" name="register name">
     * <h3>A Register Name</h3>
     * <p>
     * A register name determines under which key a register can be addressed.
     * In <logo>TeX</logo> this used to be a positive number only. This has
     * been extended to allow also a token list in braces.
     * </p>
     * <p>
     * The alternative is controlled by the integer register
     * <tt>\maxregister</tt>. The following interpretation of the value of
     * this count is used:
     * <ul>
     * <li>If the value of this integer register is negative then a arbitrary
     * non-negative number is allowed as register name as well as any list of
     * tokens enclosed in braces.</li>
     * <li>If the value of this integer register is not-negative then a only a
     * non-negative number is allowed as register name which does not exceed the
     * value of the integer register.</li>
     * </ul>
     * </p>
     * <p>
     * The value of the integer register <tt>\maxRegister</tt> is set
     * differently for various configurations of <logo>ExTeX</logo>:
     * <ul>
     * <li><logo>TeX</logo> uses the value 255.</li>
     * <li><logo>eTeX</logo> uses the value 32767.</li>
     * <li><logo>Omega</logo> uses the value 65536.</li>
     * <li><logo>ExTeX</logo> uses the value -1.</li>
     * </ul>
     * </p>
     * <p>
     * Te integer register <tt>\maxRegister</tt> is defined in the name space
     * <tt>system</tt>. Thus special actions have to be used to access it.
     * </p>
     * 
     * <h4>Syntax</h4>
     * 
     * <pre class="syntax">
     *    &lang;register name&rang;
     *        &rarr; {@linkplain
     *         org.extex.interpreter.TokenSource#scanTokens(Context,boolean,boolean,String)
     *         &lang;tokens&rang;}
     *         | {@linkplain
     *             org.extex.scanner.CountParser#scanNumber(Context,TokenSource,Typesetter)
     *         &lang;number&rang;}
     * </pre>
     * 
     * <h4>Examples</h4>
     * 
     * <pre class="TeXSample">
     *   123
     *   {abc}
     * </pre>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param primitive the name of the invoking primitive for error handling
     * 
     * @return the register name encountered
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    String scanRegisterName(Context context, TokenSource source,
            Typesetter typesetter, String primitive)
            throws HelpingException,
                TypesetterException;

    /**
     * Get the next expanded token form the input streams. If the current input
     * stream is at its end then the next one on the streamStack is used until a
     * token could be read. If all streams are at the end then <code>null</code>
     * is returned.
     * 
     * @param context the interpreter context
     * 
     * @return the next token or <code>null</code>
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Token scanToken(Context context)
            throws HelpingException,
                TypesetterException;

    /**
     * Get the next expanded token form the input streams between <code>{</code>
     * and <code>}</code>. If the current input stream is at its end then the
     * next one on the streamStack is used until a token could be read. If all
     * stream are at the end then <code>null</code> is returned. Nevertheless
     * if some tokens could have been read before the end of file occurred then
     * those tokens are returned &ndash; even if no matching brace is found.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="general text">
     * <h3>A General text</h3>
     * 
     * <p>
     * 
     * <pre class="syntax">
     *     &lang;general text&rang;
     * </pre>
     * 
     * </p>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context
     * @param primitive the name of the invoking primitive for error handling
     * @param reportUndefined indicator that an undefined control sequence leads
     *        to an exception. This parameter is effective only if
     *        ignoreUndefined is <code>false</code>
     * @param ignoreUndefined indicator that an undefined control sequence
     *        should be treated as <tt>\relax</tt>
     * 
     * @return the next tokens read so far. The return value is guaranteed not
     *         to be <code>null</code> under any circumstances
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Tokens scanTokens(Context context, boolean reportUndefined,
            boolean ignoreUndefined, String primitive)
            throws HelpingException,
                TypesetterException;

    /**
     * Get the next expanded token form the input streams between a left brace
     * character (usually <code>{</code>) and a right brace character
     * (usually <code>}</code>) and convert it to a <code>String</code>.
     * If the current input stream is at its end then the next one on the
     * streamStack is used until a token could be read. If all stream are at the
     * end then <code>null</code> is returned.
     * 
     * @param context the interpreter context
     * @param primitive the name of the invoking primitive for error handling
     * 
     * @return the next tokens as <code>String</code> or <code>null</code>
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    String scanTokensAsString(Context context, String primitive)
            throws HelpingException,
                TypesetterException;

    /**
     * Get the next expanded token form the input streams between <code>{</code>
     * and <code>}</code>. If the current input stream is at its end then the
     * next one on the streamStack is used until a token could be read. If all
     * stream are at the end then <code>null</code> is returned.
     * <p>
     * Normally all expandable tokens are expanded. This method honors the
     * protected mark and does not try to expand protected code.
     * </p>
     * 
     * @param context the interpreter context
     * @param primitive the name of the invoking primitive for error handling
     * @param reportUndefined indicator that an undefined control sequence leads
     *        to an exception. This parameter is effective only if
     *        ignoreUndefined is <code>false</code>
     * @param ignoreUndefined indicator that an undefined control sequence
     *        should be treated as <tt>\relax</tt>
     * 
     * @return the next tokens or <code>null</code>
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Tokens scanUnprotectedTokens(Context context, boolean reportUndefined,
            boolean ignoreUndefined, String primitive)
            throws HelpingException,
                TypesetterException;

    /**
     * Skip spaces and check whether any tokens are left.
     * 
     * <p>
     * This method corresponds to the following specification:
     * </p>
     * 
     * <doc type="syntax" name="optional spaces">
     * <h3>Optional Spaces</h3>
     * 
     * <p>
     * This syntactic entity corresponds to an arbitrary number of white-space
     * characters. White-space characters include space, ta and newline
     * characters.
     * </p>
     * 
     * <pre class="syntax">
     *    &lang;optional spaces&rang;
     *      &rarr; [ \t\n]*
     * </pre>
     * 
     * </doc>
     * 
     */
    void skipSpace();

    /**
     * Send the string to the named observer. The observer must be capable to
     * deal with a string argument.
     * 
     * @param name name of the observer
     * @param text the text to send to the observer
     * 
     * @throws HelpingException in case of an error
     * @throws NotObservableException in case that the named observer is not
     *         accessible
     */
    void update(String name, String text)
            throws HelpingException,
                NotObservableException;

}
