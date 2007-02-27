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

package org.extex.interpreter.max;

import java.util.ArrayList;

import org.extex.interpreter.Flags;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.Tokenizer;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.IllegalRegisterException;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.BadCharacterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.exception.helping.InvalidCharacterNameException;
import org.extex.interpreter.exception.helping.MissingLeftBraceException;
import org.extex.interpreter.exception.helping.MissingNumberException;
import org.extex.interpreter.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.observer.eof.EofObservable;
import org.extex.interpreter.observer.eof.EofObserver;
import org.extex.interpreter.observer.eof.EofObserverList;
import org.extex.interpreter.observer.pop.PopObservable;
import org.extex.interpreter.observer.pop.PopObserver;
import org.extex.interpreter.observer.pop.PopObserverList;
import org.extex.interpreter.observer.push.PushObservable;
import org.extex.interpreter.observer.push.PushObserver;
import org.extex.interpreter.observer.push.PushObserverList;
import org.extex.interpreter.observer.start.StartObserver;
import org.extex.interpreter.observer.streamClose.StreamCloseObservable;
import org.extex.interpreter.observer.streamClose.StreamCloseObserver;
import org.extex.interpreter.observer.streamClose.StreamCloseObserverList;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.CsConvertible;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.TokenStream;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.stream.observer.file.OpenFileObservable;
import org.extex.scanner.stream.observer.file.OpenFileObserver;
import org.extex.scanner.stream.observer.reader.OpenReaderObservable;
import org.extex.scanner.stream.observer.reader.OpenReaderObserver;
import org.extex.scanner.stream.observer.string.OpenStringObservable;
import org.extex.scanner.stream.observer.string.OpenStringObserver;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.register.count.util.IntegerCode;
import org.extex.unit.base.register.toks.ToksParameter;
import org.extex.util.exception.GeneralException;
import org.extex.util.exception.NotObservableException;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides the layer above the input streams and the tokenizer. It
 * has additional methods for parsing. The details of the token streams are
 * mostly hidden.
 *
 * <p>
 * This class is the companion to Max. (The name is a joke for friends of
 * Wilhelm Busch)
 * </p>
 *
 *
 * <doc name="maxRegister" type="register">
 * <h3>The Integer Register <tt>\maxRegister</tt></h3>
 * <p>
 *  The integer register <tt>\maxRegister</tt> controls the scanning of
 *  register names. The following interpretation for the values is used:
 * </p>
 * <ul>
 *  <li>
 *   If the value is positive than the register name must be a number in the
 *   range from 0 to the value given.
 *  </li>
 *  <li>
 *   If the value is zero then the register name must be a number. The number is
 *   not restricted any further.
 *  </li>
 *  <li>
 *   If the value is less then zero then the register name can be a number or
 *   a token list (in braces).
 *  </li>
 * </ul>
 *
 * <p>
 *  The integer register <tt>\max.register</tt> is not affected by grouping.
 *  This means that any assignment is always global.
 * </p>
 * <p>
 *  The primitive <tt>\maxRegister</tt> is usually defined in the name space
 *  <tt>system</tt>. Thus you have to take special means to access it.
 * </p>
 *
 * <h4>Examples</h4>
 * <p>
 * </p>
 * <pre class="TeXSample">
 *   {\namespace{system}\maxRegister=1024}  </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4408 $
 */
public class Moritz extends Max
        implements
            TokenSource,
            Configurable,
            StreamCloseObservable,
            PopObservable,
            PushObservable,
            EofObservable,
            OpenFileObservable,
            OpenStringObservable,
            OpenReaderObservable {

    /**
     * The constant <tt>MAX_CHAR_CODE</tt> contains the maximum value for a
     * character code. In original <logo>TeX</logo> this value would be 255.
     */
    private static final long MAX_CHAR_CODE = Long.MAX_VALUE;

    /**
     * The field <tt>lastToken</tt> contains the last token read.
     */
    private Token lastToken = null;

    /**
     * The field <tt>maxRegister</tt> contains the indicator for the max
     * register value. Positive values are interpreted literally. Negative
     * values have a special meaning indicating that arbitrary token lists
     * are allowed in addition to arbitrary numbers.
     */
    private IntegerCode maxRegister = new IntegerCode("maxRegister", 255);

    /**
     * The field <tt>observersCloseStream</tt> contains the observer list is
     * used for the observers which are registered to receive notifications when
     * a stream is closed.
     */
    private StreamCloseObserverList observersCloseStream = null;

    /**
     * The field <tt>observersEOF</tt> contains the observer list is used for
     * the observers which are registered to receive a notification when all
     * streams are at their end. The argument is always <code>null</code>.
     */
    private EofObserver observersEOF = null;

    /**
     * The field <tt>observersPop</tt> contains the observer list is used for
     * the observers which are registered to receive a notification when a new
     * token is about to be delivered. The argument is the token to be
     * delivered.
     */
    private PopObserver observersPop = null;

    /**
     * The field <tt>observersPush</tt> contains the observer list is used for
     * the observers which are registered to receive a notification when a new
     * token is pushed back to the input stream. The argument is the token to
     * be pushed.
     */
    private PushObserver observersPush = null;

    /**
     * The field <tt>skipSpaces</tt> contains the indicator that space tokens
     * should be discarded before the next token is delivered.
     */
    private boolean skipSpaces = false;

    /**
     * The field <tt>stream</tt> contains the current stream to read tokens
     * from. For efficiency this stream is kept in a variable instead of
     * accessing the streamStack each time it is needed.
     */
    private TokenStream stream = null;

    /**
     * The field <tt>streamStack</tt> contains the stack of streams to read
     * from &ndash; except of the current one which is stored in the variable
     * <code>stream</code>.
     */
    private ArrayList streamStack = new ArrayList();

    /**
     * The field <tt>tokenStreamFactory</tt> contains the factory for new
     * token streams.
     */
    private TokenStreamFactory tokenStreamFactory = null;

    /**
     * Creates a new object.
     */
    public Moritz() {

        super();
        registerObserver(new StartObserver() {

            /**
             * @see org.extex.interpreter.observer.start.StartObserver#update(
             *      org.extex.interpreter.Interpreter)
             */
            public void update(final Interpreter interpreter)
                    throws InterpreterException {

                try {
                    Context c = getContext();
                    CodeToken t =
                            (CodeToken) c.getTokenFactory().createToken(
                                Catcode.ESCAPE, UnicodeChar.get('\\'),
                                "maxRegister", Namespace.SYSTEM_NAMESPACE);
                    Code code = c.getCode(t);
                    if (code instanceof IntegerCode) {
                        maxRegister = (IntegerCode) code;
                    }
                } catch (CatcodeException e) {
                    throw new InterpreterException(e);
                }
            }
        });
    }

    /**
     * Put a given stream on top of the stream stack. The reading occurs on this
     * new stream before resorting to the previous streams.
     *
     * @param theStream the new stream to read from
     *
     * @see org.extex.interpreter.TokenSource#addStream(
     *      org.extex.scanner.TokenStream)
     */
    public void addStream(final TokenStream theStream) {

        streamStack.add(this.stream);
        this.stream = theStream;
    }

    /**
     * @see org.extex.interpreter.TokenSource#closeAllStreams(
     *      org.extex.interpreter.context.Context)
     */
    public void closeAllStreams(final Context context)
            throws InterpreterException {

        while (stream != null) {
            closeStream(context);
        }
    }

    /**
     * @see org.extex.interpreter.TokenSource#closeNextFileStream(
     *      org.extex.interpreter.context.Context)
     */
    public void closeNextFileStream(final Context context)
            throws InterpreterException {

        while (stream != null) {
            if (closeStream(context)) {
                return;
            }
        }
    }

    /**
     * Close the topmost stream and pop another one to the top if one is left.
     * If the closed stream has been a file stream then the tokens from the
     * tokens register <tt>everyeof</tt> are inserted into the token stream.
     *
     * <doc name="everyeof" type="register">
     * <h3>The Tokens Parameter <tt>\everyeof</tt></h3>
     * <p>
     *  The tokens parameter <tt>\everyeof</tt> is used whenever a file is
     *  closed. in this case the tokens contained in this parameter are inserted
     *  into the current input stream. Thus thus tokens are processed before
     *  the expansion continues to look for any other tokens from other sources.
     * </p>
     *
     *
     * <h4>Syntax</h4>
     *  The formal description of this primitive is the following:
     *  <pre class="syntax">
     *    &lang;everyveof&rang;
     *      &rarr; <tt>\everyeof</tt> {@linkplain
     *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
     *        &lang;equals&rang;} {@linkplain
     *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
     *        &lang;tokens&rang;}  </pre>
     *
     * <h4>Examples</h4>
     *  <pre class="TeXSample">
     *    \everyeof={\message{bye bye}}  </pre>
     * </doc>
     *
     * @param context the interpreter context
     *
     * @return <code>true</code> iff the closed file has been a file stream
     *
     * @throws InterpreterException in case of an error
     */
    private boolean closeStream(final Context context)
            throws InterpreterException {

        if (observersCloseStream != null) {
            observersCloseStream.update(stream);
        }
        boolean isFile = stream.isFileStream();
        int last = streamStack.size() - 1;
        stream = last >= 0 ? (TokenStream) streamStack.remove(last) : null;
        if (isFile) {
            push(context.getToks(ToksParameter.getKey("everyeof", context)));
            return true;
        }
        skipSpaces = false; // macro code needs this
        return false;
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
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.TokenSource#getBox(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public Box getBox(final Flags flags, final Context context,
            final Typesetter typesetter, final Token insert)
            throws InterpreterException {

        Flags f = null;
        if (flags != null) {
            f = flags.copy();
            flags.clear();
        }
        Token t = getToken(context);
        if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof Boxable) {
                if (flags != null) {
                    flags.set(f);
                }
                return ((Boxable) code).getBox(context, this, typesetter,
                    insert);
            }
        }
        throw new HelpingException(getLocalizer(), "TTP.BoxExpected");
    }

    /**
     * Get the next token from the token stream and check that it is a
     * control sequence or active character.
     * At the end of all input streams the control sequence "inaccessible"
     * is inserted and an exception is thrown. Thus this method will never
     * return <code>null</code>.
     *
     * @param context the interpreter context
     * @param typesetter the typesetter
     *
     * @return the token read
     *
     * @throws InterpreterException in case that the token stream is at its
     *  end or that the token read is not a control sequence token
     *
     * @see org.extex.interpreter.TokenSource#getControlSequence(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter)
     */
    public CodeToken getControlSequence(final Context context,
            final Typesetter typesetter) throws InterpreterException {

        Token t = getToken(context);

        if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof CsConvertible) {
                t = ((CsConvertible) code).convertCs(context, this, typesetter);
            }
            return (CodeToken) t;

        }
        try {
            push(context.getTokenFactory().createToken(Catcode.ESCAPE,
                context.escapechar(), "inaccessible ", context.getNamespace()));
        } catch (InterpreterException e) {
            throw e;
        } catch (GeneralException e) {
            throw new InterpreterException(e);
        }
        push(t);
        throw new HelpingException(getLocalizer(), "TTP.MissingCtrlSeq");
    }

    /**
     * @see org.extex.interpreter.TokenSource#getFont(
     *      org.extex.interpreter.context.Context,
     *      java.lang.String)
     */
    public Font getFont(final Context context, final String primitive)
            throws InterpreterException {

        Token t = getToken(context);

        if (t == null) {
            throw new EofException(context.esc(primitive));

        } else if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code == null) {
                throw new UndefinedControlSequenceException(AbstractCode
                    .printable(context, t));

            } else if (code instanceof FontConvertible) {
                return ((FontConvertible) code).convertFont(context, this,
                    getTypesetter());
            }

        }
        throw new HelpingException(getLocalizer(), "TTP.MissingFontIdent");

    }

    /**
     * Scan the expanded token stream for a sequence of letter tokens. If all
     * tokens are found then they are removed from the input stream and
     * <code>true</code> is returned. Otherwise all tokens are left in the
     * input stream and <code>false</code> is returned.
     *
     * @param context the interpreter context
     * @param s the tokens to scan
     *
     * @return <code>true</code> iff the tokens could have been successfully
     *  removed from the input stream
     *
     * @throws InterpreterException in case that no number is found or the
     *  end of file has been reached before an integer could be acquired
     */
    public boolean getKeyword(final Context context, final String s)
            throws InterpreterException {

        skipSpaces = true;
        if (getKeyword(context, s, 0)) {
            skipSpaces = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Scans the input token stream for a given sequence of tokens. Those tokens
     * may have the category codes <tt>LETTER</tt> or <tt>OTHER</tt>.
     *
     * @param context the interpreter context
     * @param s the string to use as reference
     * @param i the index in s to start working at
     *
     * @return <code>true</code> iff the keyword has been found
     *
     * @throws InterpreterException in case of an error
     */
    private boolean getKeyword(final Context context, final String s,
            final int i) throws InterpreterException {

        if (i < s.length()) {
            Token t = getToken(context);

            if (t == null) {
                return false;
            } else if (!(t.equals(Catcode.LETTER, s.charAt(i)) //
                    || t.equals(Catcode.OTHER, s.charAt(i)))
                    || !getKeyword(context, s, i + 1)) {
                put(t);
                return false;
            }

        }
        return true;
    }

    /**
     * @see org.extex.interpreter.TokenSource#getLastToken()
     */
    public Token getLastToken() {

        return lastToken;
    }

    /**
     * @see org.extex.interpreter.TokenSource#getLocator()
     */
    public Locator getLocator() {

        return stream == null ? null : stream.getLocator();
    }

    /**
     * @see org.extex.interpreter.TokenSource#getNonSpace(
     *      org.extex.interpreter.context.Context)
     */
    public Token getNonSpace(final Context context) throws InterpreterException {

        skipSpaces = true;
        return getToken(context);
    }

    /**
     * Scan a number with a given first token.
     *
     * @param token the first token to consider
     *
     * @return the value of the integer scanned
     * @throws InterpreterException in case that no number is found or the
     *  end of file has been reached before an integer could be acquired
     * @throws MissingNumberException in case that no number is found or
     *  the end of file has been reached before an integer could be acquired
     */
    public long getNumber(final Token token)
            throws InterpreterException,
                MissingNumberException {

        Context context = getContext();
        long n = 0;
        Token t;

        if (token != null && token.isa(Catcode.OTHER)) {
            switch (token.getChar().getCodePoint()) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    n = token.getChar().getCodePoint() - '0';

                    for (t = getToken(context); t != null
                            && t.isa(Catcode.OTHER) && t.getChar().isDigit(); //
                    t = getToken(context)) {
                        n = n * 10 + t.getChar().getCodePoint() - '0';
                    }

                    if (t != null) {
                        put(t);
                    }
                    skipSpaces = true;
                    return n;

                case '`':
                    t = getToken(context);

                    if (t instanceof ControlSequenceToken) {
                        String val = ((ControlSequenceToken) t).getName();
                        if (val.length() != 1) {
                            throw new HelpingException(getLocalizer(),
                                "TTP.NonNumericToken", t.toString());
                        }
                        return val.charAt(0);
                    } else if (t != null) {
                        return t.getChar().getCodePoint();
                    }
                    // fall through to error handling
                    break;

                case '\'':
                    for (t = getToken(context); t instanceof OtherToken; //
                    t = getToken(context)) {
                        int no = t.getChar().getCodePoint() - '0';
                        if (no < 0 || no >= 7) {
                            break;
                        }
                        n = n * 8 + no;
                    }

                    put(t);
                    skipSpaces = true;
                    return n;

                case '"':

                    for (t = getToken(context); //
                    t instanceof OtherToken || t instanceof LetterToken; //
                    t = getToken(context)) {
                        int no = t.getChar().getCodePoint();
                        switch (no) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                n = n * 16 + no - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                n = n * 16 + no - 'a' + 10;
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                n = n * 16 + no - 'A' + 10;
                                break;
                            default:
                                put(t);
                                skipSpaces = true;
                                return n;
                        }
                    }

                    put(t);
                    skipSpaces = true;
                    return n;

                default:
                    // fall through to error handling
            }
        }

        throw new MissingNumberException();
    }

    /**
     * Skip spaces and if the next non-space character is an equal sign skip it
     * as well and all spaces afterwards.
     *
     * @param context the interpreter context
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.TokenSource#getOptionalEquals(
     *      org.extex.interpreter.context.Context)
     */
    public void getOptionalEquals(final Context context)
            throws InterpreterException {

        skipSpaces = true;
        Token t = getToken(context);

        if (t != null && t.equals(Catcode.OTHER, '=')) {
            skipSpaces = true;
        } else {
            put(t);
        }
    }

    /**
     * Get the next token from the input streams. If the current input stream is
     * at its end then the next one on the streamStack is used until a token
     * could be read. If all stream are at the end then <code>null</code> is
     * returned.
     * <p>
     * Whenever a file stream is closed then the tokens from the stream are
     * discarded. This holds also for the tokens pushed back onto this stream.
     * </p>
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
    public Token getToken(final Context context) throws InterpreterException {

        TokenFactory factory = context.getTokenFactory();
        Tokenizer tokenizer = context.getTokenizer();

        try {

            while (stream != null) {

                for (Token t = stream.get(factory, tokenizer); t != null; t =
                        stream.get(factory, tokenizer)) {

                    if (observersPop != null) {
                        observersPop.update(t);
                    }
                    if (!skipSpaces || !(t instanceof SpaceToken)) {
                        lastToken = t;
                        skipSpaces = false;
                        return t;
                    }
                }

                closeStream(context);
            }

        } catch (ScannerException e) {
            throw new InterpreterException(e);
        }

        if (observersEOF != null) {
            observersEOF.update();
        }
        lastToken = null;
        return null;
    }

    /**
     * @see org.extex.interpreter.TokenSource#getTokens(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens getTokens(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Tokens toks = new Tokens();
        Token token = getToken(context);

        if (token instanceof LeftBraceToken) {
            int balance = 1;

            for (token = getToken(context); token != null; token =
                    getToken(context)) {

                if (token.isa(Catcode.LEFTBRACE)) {
                    ++balance;
                } else if (token instanceof RightBraceToken && --balance <= 0) {
                    return toks;
                }

                toks.add(token);
            }

            throw new EofInToksException(""); // TODO provide location

        } else if (token instanceof CodeToken) {
            Code code = context.getCode((CodeToken) token);
            if (code instanceof TokensConvertible) {
                return ((TokensConvertible) code).convertTokens(context,
                    source, typesetter);
            }

        } else if (token == null) {
            throw new EofException(getLocalizer().format("Tokens.Text"));
        }

        throw new MissingLeftBraceException("???");
    }

    /**
     * Getter for the token stream factory.
     *
     * @return the token stream factory
     */
    public TokenStreamFactory getTokenStreamFactory() {

        return tokenStreamFactory;
    }

    /**
     * Push back a token onto the input stream for subsequent reading.
     *
     * @param token the token to push
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.TokenSource#push(
     *      org.extex.scanner.type.token.Token)
     */
    public void push(final Token token) throws InterpreterException {

        if (token == null) {
            return;
        }
        if (observersPush != null) {
            observersPush.update(token);
        }

        if (stream == null) {
            stream = getTokenStreamFactory().newInstance("");
        }
        stream.put(token);
    }

    /**
     * Push back a list of tokens onto the input stream for subsequent reading.
     *
     * @param tokens the tokens to push
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.TokenSource#push(
     *      org.extex.scanner.type.token.Token[])
     */
    public void push(final Token[] tokens) throws InterpreterException {

        if (stream == null) {
            try {
                stream = getTokenStreamFactory().newInstance("");
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
        }

        for (int i = tokens.length - 1; i >= 0; i--) {

            if (observersPush != null) {
                observersPush.update(tokens[i]);
            }

            stream.put(tokens[i]);
        }
    }

    /**
     * Push back a list of tokens onto the input stream for subsequent reading.
     * In case that the argument is <code>null</code> then it is silently
     * ignored.
     * <p>
     *  If the current stream is <code>null</code> then all streams are at their
     *  end. In this case a new Token stream is created to hold the tokens.
     * </p>
     *
     * @param tokens the tokens to push
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.TokenSource#push(
     *      org.extex.interpreter.type.tokens.Tokens)
     */
    public void push(final Tokens tokens) throws InterpreterException {

        if (tokens == null || tokens.length() == 0) {
            return;
        }

        if (stream == null) {
            stream = getTokenStreamFactory().newInstance("");
        }

        for (int i = tokens.length() - 1; i >= 0; i--) {
            Token t = tokens.get(i);
            if (observersPush != null) {
                observersPush.update(t);
            }
            stream.put(t);
        }
    }

    /**
     * Push a token back to the input stream.
     *
     * @param t the token to push
     *
     * @throws InterpreterException in case of a configuration problem
     */
    private void put(final Token t) throws InterpreterException {

        if (t == null) {
            return;
        }
        if (stream == null) {
            try {
                stream = getTokenStreamFactory().newInstance("");
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
        }
        stream.put(t);

    }

    /**
     * Add an observer for the eof event.
     * This observer is triggered by an end of file on the token stream.
     * This means that all tokens have been processed and all stream are at
     * their end.
     *
     * @param observer the observer to add
     */
    public void registerObserver(final EofObserver observer) {

        observersEOF = EofObserverList.register(observersEOF, observer);
    }

    /**
     * Register an open file observer for later use.
     *
     * @param observer the observer to be registered
     *
     * @see org.extex.scanner.stream.observer.file.OpenFileObservable#registerObserver(
     *      org.extex.scanner.stream.observer.file.OpenFileObserver)
     */
    public void registerObserver(final OpenFileObserver observer) {

        tokenStreamFactory.registerObserver(observer);
    }

    /**
     * Register an open reader observer for later use.
     *
     * @param observer the observer to be registered
     *
     * @see org.extex.scanner.stream.observer.reader.OpenReaderObservable#registerObserver(
     *      org.extex.scanner.stream.observer.reader.OpenReaderObserver)
     */
    public void registerObserver(final OpenReaderObserver observer) {

        tokenStreamFactory.registerObserver(observer);
    }

    /**
     * Register an open string observer for later use.
     *
     * @param observer the observer to be registered
     *
     * @see org.extex.scanner.stream.observer.string.OpenStringObservable#registerObserver(
     *      org.extex.scanner.stream.observer.string.OpenStringObserver)
     */
    public void registerObserver(final OpenStringObserver observer) {

        tokenStreamFactory.registerObserver(observer);
    }

    /**
     * Add an observer for the pop event.
     * This observer is triggered by a pop operation on the token stream.
     * This means that a token has been extracted.
     *
     * @param observer the observer to add
     */
    public void registerObserver(final PopObserver observer) {

        observersPop = PopObserverList.register(observersPop, observer);
    }

    /**
     * Add an observer for the push event.
     * This observer is triggered by a push operation on the token stream.
     * This means that a token has been placed on the current stream for
     * subsequent reading.
     *
     * @param observer the observer to add
     */
    public void registerObserver(final PushObserver observer) {

        observersPush = PushObserverList.register(observersPush, observer);
    }

    /**
     * Register an stream close observer for later use.
     *
     * @param observer the observer to be registered
     *
     * @see org.extex.interpreter.observer.streamClose.StreamCloseObservable#registerObserver(
     *      org.extex.interpreter.observer.streamClose.StreamCloseObserver)
     */
    public void registerObserver(final StreamCloseObserver observer) {

        observersCloseStream =
                StreamCloseObserverList.register(observersCloseStream, //
                    observer);
    }

    /**
     * This method scans a character code.
     * <p>
     *  The character code is either a number &ndash; after expansion &ndash; or
     *  the name of a Unicode character in braces.
     * </p>
     *
     * @see org.extex.interpreter.TokenSource#scanCharacterCode(
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter,
     *      java.lang.String)
     */
    public UnicodeChar scanCharacterCode(final Context context,
            final Typesetter typesetter, final String primitive)
            throws InterpreterException {

        long cc;

        Token t = getNonSpace(context);
        if (t instanceof LeftBraceToken) {
            push(t);
            String name = scanTokensAsString(context, primitive);
            if (name == null) {
                throw new InvalidCharacterNameException("");
            }

            UnicodeChar uc = UnicodeChar.get(name);
            if (uc == null) {
                throw new InvalidCharacterNameException(name);
            }

            return uc;

        } else {

            push(t);
            cc = Count.scanInteger(context, this, typesetter);

            if (cc < 0 || cc > MAX_CHAR_CODE) {
                throw new BadCharacterException(cc);
            }
            return UnicodeChar.get((int) cc);
        }

    }

    /**
     * Scan the input for the next token which has not the catcode SPACE.
     *
     * @param context the interpreter context
     *
     * @return the next non-space token or <code>null</code> at EOF
     *
     * @throws InterpreterException
     *  in case of an error in {@link #scanToken(Context) scanToken()}
     *
     * @see org.extex.interpreter.TokenSource#scanNonSpace(
     *      org.extex.interpreter.context.Context)
     */
    public Token scanNonSpace(final Context context)
            throws InterpreterException {

        Token t;
        do {
            skipSpaces = true;
            t = scanToken(context);
        } while (t != null && t.isa(Catcode.SPACE));

        return t;
    }

    /**
     * @see org.extex.interpreter.TokenSource#scanNumber(Context)
     */
    public long scanNumber(final Context context) throws InterpreterException {

        return Count.scanNumber(context, this, getTypesetter(),
            getNonSpace(context));
    }

    /**
     * @see org.extex.interpreter.TokenSource#scanNumber(
     *      org.extex.interpreter.context.Context,
     *      org.extex.scanner.type.token.Token)
     */
    public long scanNumber(final Context context, final Token token)
            throws InterpreterException {

        return Count.scanNumber(context, this, getTypesetter(), token);
    }

    /**
     * @see org.extex.interpreter.TokenSource#scanRegisterName(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter, java.lang.String)
     */
    public String scanRegisterName(final Context context,
            final TokenSource source, final Typesetter typesetter,
            final String primitive) throws InterpreterException {

        skipSpaces = true;
        Token token = getToken(context);

        if (token == null) {

            throw new MissingNumberException();
        }

        long maxRegisterValue = maxRegister.getValue();
        if (maxRegisterValue < 0 && token.isa(Catcode.LEFTBRACE)) {
            push(token);
            return scanTokensAsString(context, primitive);
        }

        source.push(token);
        long registerNumber = Count.scanInteger(context, source, typesetter);
        if (registerNumber < 0 || maxRegisterValue >= 0
                && registerNumber > maxRegisterValue) {
            throw new IllegalRegisterException(Long.toString(registerNumber));
        }
        return Long.toString(registerNumber);
    }

    /**
     * Expand tokens from the input stream until an not expandable token is
     * found. This not expandable token is returned.
     *
     * @param context the interpreter context
     *
     * @return the next not expandable token
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.TokenSource#scanToken(
     *      org.extex.interpreter.context.Context)
     */
    public Token scanToken(final Context context) throws InterpreterException {

        return expand(getToken(context));
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
     * @return the next tokens read so far. The return value is guaranteed not
     *   to be <code>null</code> under any circumstances
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.TokenSource#scanTokens(
     *      org.extex.interpreter.context.Context,
     *      boolean,
     *      boolean,
     *      java.lang.String)
     */
    public Tokens scanTokens(final Context context,
            final boolean reportUndefined, final boolean ignoreUndefined,
            final String primitive) throws InterpreterException {

        Tokens toks = new Tokens();
        skipSpaces = true;
        Token token = scanToken(context);

        if (token == null) {
            throw new EofException(primitive);
        } else if (!(token instanceof LeftBraceToken)) {
            throw new MissingLeftBraceException(primitive);
        }

        int balance = 1;

        for (;;) {

            token = expand(getToken(context));

            if (token == null) {
                return toks;
            }

            if (token instanceof LeftBraceToken) {
                toks.add(token);
                ++balance;

            } else if (token instanceof RightBraceToken && --balance <= 0) {
                return toks;

            } else if (token instanceof CodeToken) {
                if (ignoreUndefined) {
                    // ignored as requested
                } else if (reportUndefined
                        && context.getCode((CodeToken) token) == null) {
                    throw new UndefinedControlSequenceException(token
                        .toString());
                } else {
                    toks.add(token);
                }

            } else {
                toks.add(token);
            }
        }
    }

    /**
     * @see org.extex.interpreter.TokenSource#scanTokensAsString(
     *      org.extex.interpreter.context.Context,
     *      java.lang.String)
     */
    public String scanTokensAsString(final Context context,
            final String primitive) throws InterpreterException {

        return scanTokens(context, false, false, primitive).toText();
    }

    /**
     * @see org.extex.interpreter.TokenSource#scanUnprotectedTokens(
     *      org.extex.interpreter.context.Context,
     *      boolean,
     *      boolean,
     *      java.lang.String)
     */
    public Tokens scanUnprotectedTokens(final Context context,
            final boolean reportUndefined, final boolean ignoreUndefined,
            final String primitive) throws InterpreterException {

        Tokens toks = new Tokens();
        skipSpaces = true;
        Token token = getToken(context);

        if (token == null) {
            throw new EofException(primitive);
        } else if (!token.isa(Catcode.LEFTBRACE)) {
            throw new MissingLeftBraceException(primitive);
        }

        int balance = 1;

        for (;;) {

            token = expandUnproteced(getToken(context), toks);

            if (token == null) {
                throw new EofException();
            }

            if (token instanceof LeftBraceToken) {
                toks.add(token);
                ++balance;

            } else if (token instanceof RightBraceToken && --balance <= 0) {
                return toks;

            } else if (token instanceof CodeToken) {
                if (ignoreUndefined) {
                    // ignored as requested
                } else if (reportUndefined
                        && context.getCode((CodeToken) token) == null) {
                    throw new UndefinedControlSequenceException(token
                        .toString());
                } else {
                    toks.add(token);
                }

            } else {
                toks.add(token);
            }
        }
    }

    /**
     * Setter for the token stream factory.
     *
     * @param factory the token stream factory
     *
     * @throws ConfigurationException this exception is never thrown. It is
     *  defined here to provide an exit for derived classes
     */
    public void setTokenStreamFactory(final TokenStreamFactory factory) {

        tokenStreamFactory = factory;
    }

    /**
     * Skip spaces and check whether any tokens are left.
     *
     * @see org.extex.interpreter.TokenSource#skipSpace()
     */
    public void skipSpace() {

        skipSpaces = true;
    }

    /**
     * Send the string to the named observer. The observer must be capable to
     * deal with a string argument.
     *
     * @param name name of the observer
     * @param text the text to send to the observer
     *
     * @throws InterpreterException in case of an error
     * @throws NotObservableException in case that the named observer is not
     *   accessible
     *
     * @see org.extex.interpreter.TokenSource#update(java.lang.String,
     *      java.lang.String)
     */
    public void update(final String name, final String text)
            throws NotObservableException {

        throw new NotObservableException(name);
    }
}
