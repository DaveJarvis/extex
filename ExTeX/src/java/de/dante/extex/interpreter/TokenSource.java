/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter;

import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.type.box.Box;
import de.dante.extex.interpreter.type.font.Font;
import de.dante.extex.interpreter.type.tokens.Tokens;
import de.dante.extex.scanner.CodeToken;
import de.dante.extex.scanner.Token;
import de.dante.extex.scanner.stream.TokenStream;
import de.dante.extex.scanner.stream.TokenStreamFactory;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;
import de.dante.util.Locator;
import de.dante.util.UnicodeChar;
import de.dante.util.observer.NotObservableException;

/**
 * This interface describes a class to acquire
 * {@link de.dante.extex.scanner.Token Token}s from.
 * Beside the pure getter for the next token some higher-level parsing methods
 * are provided here as well.
 *
 * <p>There are two classes of methods for reading something from a token
 * stream. The methods starting with <tt>get</tt> perform the raw reading,
 * whereas the methods starting with <tt>scan</tt> perform expansion as well.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface TokenSource {

    /**
     * Put a given stream on top of the stream stack. The reading occurs on
     * this new stream before resorting to the previous streams.
     *
     * @param stream the new stream to read from
     */
    void addStream(TokenStream stream);

    /**
     * All input streams are closed and not further Token is available for
     * processing. This normally means that the interpreter is forced to
     * terminate more or less gracefully.
     *
     * @param context the interprester context
     *
     * @throws GeneralException in case of an error
     */
    void closeAllStreams(Context context) throws GeneralException;

    /**
     * Close all streams on the stack until a file stream is found. This file
     * stream is closed as last one. The other streams are left unchanged.
     * If no file stream is found the all streams are closed and none is left.
     *
     * @param context the interpreter context
     *
     * @throws GeneralException in case of an error
     */
    void closeNextFileStream(Context context) throws GeneralException;

    /**
     * Tries to execute a token.
     *
     * @param token the Token to execute
     *
     * @throws GeneralException in case of an error
     */
    void execute(final Token token) throws GeneralException;

    /**
     * Scan and execute tokens until the group ends.
     *
     * @throws GeneralException in case of an error
     */
    void executeGroup() throws GeneralException;

    /**
     * Parse the specification of a box.
     *
     * <doc type="syntax" name="box">
     * This method parses the following syntactic entity:
     * <pre class="syntax">
     *   &lang;box&rang; </pre>
     * </doc>
     *
     * @param context the interpreter context
     * @param typesetter the typesetter to use
     *
     * @return the box gathered
     *
     * @throws GeneralException in case of an error
     */
    Box getBox(Context context, Typesetter typesetter) throws GeneralException;

    /**
     * Get the next token from the token stream and check that it is a
     * control sequence or active character.
     * At the end of all input streams the control sequence "inaccessible"
     * is inserted and an exception is thrown. Thus this method will never
     * return <code>null</code>.
     *
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     *
     * <doc type="syntax" name="control sequence">
     * <pre class="syntax">
     *   &lang;control sequence&rang; </pre>
     * <p>
     *  A control sequence is either a active character or an escape sequence.
     * </p>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @return the token read
     *
     * @throws GeneralException in case that the token stream is at its end or
     *   that the token read is not a control sequence token
     */
    CodeToken getControlSequence(Context context) throws GeneralException;

    /**
     * Parse the specification of a font.
     *
     * <doc type="syntax" name="box">
     * This method parses the following syntactic entity:
     * <pre class="syntax">
     *   &lang;font&rang; </pre>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @return a font specification
     *
     * @throws GeneralException in case of an error
     */
    Font getFont(Context context) throws GeneralException;

    /**
     * @deprecated use getKeyword(Context, String) instead
     */
    boolean getKeyword(String keyword) throws GeneralException;

    /**
     * Get tokens from the token stream searching for a sequence of letter
     * tokens. If all tokens are found then they are removed from the input
     * stream and <code>true</code> is returned. Otherwise all tokens are left
     * in the input stream and <code>false</code> is returned.
     * <p>
     * Spaces before the keyword are removed from the input stream. Those
     * speces are not restored, even if the keyword is not found.
     * </p>
     * <p>
     * Space tokens after the keyword are removed from the input stream.
     * </p>
     *
     * @param context the interpreter context
     * @param keyword the tokens to scan
     *
     * @return <code>true</code> iff the tokens could have been successfully
     *  removed from the input stream
     *
     * @throws GeneralException in case of an error
     */
    boolean getKeyword(Context context, String keyword) throws GeneralException;

    /**
     * @deprecated use getKeyword(Context, String) instead
     */
    boolean getKeyword(String s, boolean space) throws GeneralException;

    /**
     * Getter for the locator.
     * The locator provides a means to get the information where something is
     * coming from. Usually it points to a line in a file.
     *
     * @return the current locator
     */
    Locator getLocator();

    /**
     * @deprecated use getNonSpace(Context) instead
     */
    Token getNonSpace() throws GeneralException;

    /**
     * Get the next token which has not the catcode
     * {@link de.dante.extex.scanner.Catcode#SPACE SPACE}.
     *
     * @param context the interpreter context
     *
     * @return the next non-space token or <code>null</code> at EOF
     *
     * @throws GeneralException in case of an error
     */
    Token getNonSpace(Context context) throws GeneralException;

    /**
     * @deprecated use getOptionalEquals(Context) instead
     */
    void getOptionalEquals() throws GeneralException;

    /**
     * Skip spaces and if the next non-space character is an equal sign skip it
     * as well and all spaces afterwards.
     *
     * <doc type="syntax" name="equals">
     * This method parses the following syntactic entity:
     * <pre class="syntax">
     *   &lang;equals&rang;
     *     &rarr; {@linkplain #skipSpace() &lang;optional spaces&rang;}
     *      |  {@linkplain #skipSpace()
     *          &lang;optional spaces&rang;} <tt>=</tt><sub>12</sub> </pre>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @throws GeneralException in case of an error
     */
    void getOptionalEquals(Context context) throws GeneralException;

    /**
     * @deprecated use getToken(Context) instead
     */
    Token getToken() throws GeneralException;

    /**
     * Get the next token form the input streams. If the current input stream
     * is at its end then the next one on the streamStack is used until a token
     * could be read. If all stream are at the end then <code>null</code> is
     * returned.
     *
     * <p>
     *  This method corresponds to the following syntax specification:
     * </p>
     * <doc type="syntax" name="token">
     * <p>
     *  <pre class="syntax">
     *    &lang;token&rang;  </pre>
     * </p>
     * <p>
     *  A single token depends on the category code of the characters.
     * </p>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @return the next token or <code>null</code>
     *
     * @throws GeneralException in case of an error
     *
     * @see "TeX -- The Program [332]"
     */
    Token getToken(Context context) throws GeneralException;

    /**
     * @deprecated use getToken(Context) instead
     */
    Tokens getTokens() throws GeneralException;

    /**
     * Get the next tokens form the input streams between <code>{</code> and
     * <code>}</code>. If the current input stream is at its end then the
     * next one on the streamStack is used until a token could be read. If all
     * streams are at the end then <code>null</code> is returned.
     *
     * <doc type="syntax" name="replacement text">
     * <p>
     *  This method corresponds to the following syntax specification:
     *  <pre class="syntax">
     *    &lang;replacement text&rang;  </pre>
     * </p>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @return the next tokens or <code>null</code>
     *
     * @throws GeneralException in case of an error
     */
    Tokens getTokens(Context context) throws GeneralException;

    /**
     * Getter for the token stream factory.
     * The token stream factory can be used to acquire a new token stream.
     *
     * @return the token stream factory
     */
    TokenStreamFactory getTokenStreamFactory();

    /**
     * Push back a token onto the input stream for subsequent reading.
     *
     * @param token the token to push
     *
     * @throws GeneralException in case of an error
     */
    void push(Token token) throws GeneralException;

    /**
     * Push back a list of tokens onto the input stream for subsequent reading.
     *
     * @param tokens the tokens to push
     *
     * @throws GeneralException in case of an error
     */
    void push(Token[] tokens) throws GeneralException;

    /**
     * Push back a list of tokens onto the input stream for subsequent reading.
     * In case that the argument is <code>null</code> then it is silently
     * ignored.
     *
     * @param tokens the tokens to push
     *
     * @throws GeneralException in case of an error
     */
    void push(Tokens tokens) throws GeneralException;

    /**
     * Scan the input stream for tokens making up a character code, this is a
     * sequence of digits with catcode <tt>OTHER</tt>. The number can be
     * preceeded by optional whitespace. Alternate representations for an
     * character code exist.
     *
     * @param context the interpreter context
     *
     * @return the value of the integer scanned
     *
     * @throws GeneralException in case that no number is found or the end of
     *  file has been reached before an integer could be acquired
     */
    UnicodeChar scanCharacterCode(Context context) throws GeneralException;

    /**
     * Scan the input stream for tokens making up an integer, this is a number
     * optionally preceeded by a sign (+ or -). The number can be preceeded by
     * optional whitespace. Whitespace is also ignored between the sign and the
     * number. All non-whitespace characters must have the catcode OTHER.
     *
     * <doc type="syntax" name="number">
     * <pre class="syntax">
     *   &lang;number&rang; </pre>
     * <p>
     *  A number consists of a non-empty sequence of digits with catcode
     *  {@link de.dante.extex.scanner.Catcode#OTHER OTHER}. The number is
     *  optionally precceded by whitespace and a sign <tt>+</tt> or <tt>-</tt>.
     * </p>
     * <p>
     *  Tokens are expanded while gathering the requested values.
     * </p>
     * </doc>
     *
     * @param context the interpreter context
     *
     *
     * @return the value of the integer scanned
     *
     * @throws GeneralException in case of an error
     */
    long scanInteger(Context context) throws GeneralException;

    /**
     * @deprecated use scanNonSpace(Context) instead
     */
    Token scanNonSpace() throws GeneralException;

    /**
     * Scan the input for the next token which has not the catcode SPACE.
     *
     * @param context the interpreter contex
     *
     * @return the next non-space token or <code>null</code> at EOF
     *
     * @throws GeneralException
     *  in case of an error in {@link #scanToken(Context) scanToken()}
     */
    public Token scanNonSpace(Context context) throws GeneralException;

    /**
     * @deprecated use scanNumber(Context) instead
     */
    long scanNumber() throws GeneralException;

    /**
     * Scan the input stream for tokens making up a number, this is a sequence
     * of digits with catcode <tt>OTHER</tt>. The number can be preceeded by
     * optional whitespace. Alternate representations for an integer exist.
     *
     * @param context the interpreter context
     *
     * @return the value of the integer scanned
     *
     * @throws GeneralException in case of an error
     */
    long scanNumber(Context context) throws GeneralException;

    /**
     * Scan the input stream for tokens making up a number, i.e. a sequence of
     * digits with catcode OTHER. The number can be preceeded by optional
     * whitespace.
     * <p>
     *  This method implements the generalization of several syntactic
     *  definitions from TeX:
     * </p>
     *
     * <doc type="syntax" name="8-bit number">
     * <pre class="syntax">
     *   &lang;8-bit number&rang; </pre>
     * <p>
     *  A number consists of a non-empty sequence of digits with catcode
     *  {@link de.dante.extex.scanner.Catcode#OTHER OTHER}.
     *  The check for a maximal value of 255 is not performed in ExTeX.
     * </p>
     * </doc>
     *
     * @param context the interpreter context
     * @param token the first token to consider
     *
     * @return the value of the integer scanned
     *
     * @throws GeneralException in case of an error
     */
    long scanNumber(Context context, Token token) throws GeneralException;

    /**
     * @deprecated use scanNumber(Context, Token) instead
     */
    long scanNumber(Token token) throws GeneralException;

    /**
     * Scan the input streams for an entity to denote a register name.
     * Upon EOF <code>null</code> is returned.
     *
     * @param context the interpreter context
     *
     * @return the register name encountered
     *
     * @throws GeneralException in case of an error
     */
    String scanRegisterName(Context context) throws GeneralException;

    /**
     * Get the next expanded token form the input streams. If the current input
     * stream is at its end then the next one on the streamStack is used until
     * a token could be read. If all stream are at the end then
     * <code>null</code> is returned.
     *
     * @param context the interpreter context
     *
     * @return the next token or <code>null</code>
     *
     * @throws GeneralException in case of an error
     */
    Token scanToken(Context context) throws GeneralException;

    /**
     * Get the next expanded token form the input streams between <code>{</code>
     * and <code>}</code>. If the current input stream is at its end then the
     * next one on the streamStack is used until a token could be read. If all
     * stream are at the end then <code>null</code> is returned.
     *
     * <doc type="syntax" name="general text">
     * <p>
     *  This method corresponds to the following syntax specification:
     *  <pre class="syntax">
     *    &lang;general text&rang;  </pre>
     * </p>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @return the next tokens or <code>null</code>
     *
     * @throws GeneralException in case of an error
     */
    Tokens scanTokens(Context context) throws GeneralException;

    /**
     * @deprecated use scanTokensAsString(Context) instead
     */
    String scanTokensAsString() throws GeneralException;

    /**
     * Get the next expanded token form the input streams between a leftbace
     * character (usually <code>{</code>) and a rightbrace character
     * (usually <code>}</code>) and convert it to a <code>String</code>. If the
     * current input stream is at its end then the next one on the streamStack
     * is used until a token could be read. If all stream are at the end then
     * <code>null</code> is returned.
     *
     * @param context the interpreter context
     *
     * @return the next tokens as <code>String</code> or <code>null</code>
     *
     * @throws GeneralException in case of an error
     */
    String scanTokensAsString(Context context) throws GeneralException;

    /**
     * Skip spaces and check whether any tokens are left.
     *
     * <p>
     *  This method corresponds to the following specification:
     *  <pre class="syntax">
     *    &lang;optional spaces&rang;  </pre>
     * </p>
     *
     * @throws GeneralException in case of an error
     */
    void skipSpace() throws GeneralException;

    /**
     * Send the string to the named observer. The observer must be capable to
     * deal with a string argument.
     *
     * @param name name of the observer
     * @param text the text to send to the observer
     *
     * @throws GeneralException in case of an error
     * @throws NotObservableException in case that the named observer is not
     *             accessible
     */
    void update(String name, String text)
            throws GeneralException,
                NotObservableException;

}