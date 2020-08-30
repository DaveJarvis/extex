/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.base.parser.*;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.NotObservableException;
import org.extex.core.exception.helping.*;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Mudimen;
import org.extex.core.muskip.Muskip;
import org.extex.framework.configuration.Configurable;
import org.extex.interpreter.Flags;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.MissingParserException;
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
import org.extex.interpreter.parser.CountParser;
import org.extex.interpreter.parser.DimenParser;
import org.extex.interpreter.parser.GlueParser;
import org.extex.interpreter.parser.Parser;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.CsConvertible;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.stream.observer.file.OpenFileObservable;
import org.extex.scanner.stream.observer.file.OpenFileObserver;
import org.extex.scanner.stream.observer.reader.OpenReaderObservable;
import org.extex.scanner.stream.observer.reader.OpenReaderObserver;
import org.extex.scanner.stream.observer.string.OpenStringObservable;
import org.extex.scanner.stream.observer.string.OpenStringObserver;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.*;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.math.MathClass;
import org.extex.unit.base.register.count.IntegerCode;
import org.extex.unit.base.register.toks.ToksParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the layer above the input streams and the tokenizer. It
 * has additional methods for parsing. The details of the token streams are
 * mostly hidden.
 * 
 * <p>
 * This class is the companion to {@link Max}; its name is a joke for friends of
 * Wilhelm Busch.
 * </p>
 *
 * <p>The Integer Register {@code \maxRegister}</p>
 * <p>
 * The integer register {@code \maxRegister} controls the scanning of register
 * names. The following interpretation for the values is used:
 * </p>
 * <ul>
 * <li>If the value is positive than the register name must be a number in the
 * range from 0 to the value given.</li>
 * <li>If the value is zero then the register name must be a number. The number
 * is not restricted any further.</li>
 * <li>If the value is less then zero then the register name can be a number or
 * a token list (in braces).</li>
 * </ul>
 * 
 * <p>
 * The integer register {@code \max.register} is not affected by grouping. This
 * means that any assignment is always global.
 * </p>
 * <p>
 * The primitive {@code \maxRegister} is usually defined in the name space
 * {@code system}. Thus you have to take special means to access it.
 * </p>
 * 
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *   {\namespace{system}\maxRegister=1024}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
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
     * The constant {@code MAX_CHAR_CODE} contains the maximum value for a
     * character code. In original TeX this value would be 255.
     */
    private static final long MAX_CHAR_CODE = Long.MAX_VALUE;

    /**
     * The field {@code countParser} contains the parser for number quantities.
     */
    private CountParser countParser;

    /**
     * The field {@code dimenParser} contains the parser for length quantities.
     */
    private DimenParser dimenParser;

    /**
     * The field {@code glueParser} contains the parser for glue quantities.
     */
    private GlueParser glueParser;

    /**
     * The field {@code lastToken} contains the last token read.
     */
    private Token lastToken ;

    /**
     * The field {@code maxRegister} contains the indicator for the max
     * register value. Positive values are interpreted literally. Negative
     * values have a special meaning indicating that arbitrary token lists are
     * allowed in addition to arbitrary numbers.
     */
    private IntegerCode maxRegister = new IntegerCode(null, 255);

    /**
     * The field {@code observersCloseStream} contains the observer list is
     * used for the observers which are registered to receive notifications when
     * a stream is closed.
     */
    private StreamCloseObserverList observersCloseStream ;

    /**
     * The field {@code observersEOF} contains the observer list is used for
     * the observers which are registered to receive a notification when all
     * streams are at their end. The argument is always {@code null}.
     */
    private EofObserver observersEOF ;

    /**
     * The field {@code observersPop} contains the observer list is used for
     * the observers which are registered to receive a notification when a new
     * token is about to be delivered. The argument is the token to be
     * delivered.
     */
    private PopObserver observersPop ;

    /**
     * The field {@code observersPush} contains the observer list is used for
     * the observers which are registered to receive a notification when a new
     * token is pushed back to the input stream. The argument is the token to be
     * pushed.
     */
    private PushObserver observersPush ;

    /**
     * The field {@code parsers} contains the list of registered parsers.
     */
    @SuppressWarnings("rawtypes")
    private final Map<Class, Parser> parsers = new HashMap<>();

    /**
     * The field {@code skipSpaces} contains the indicator that space tokens
     * should be discarded before the next token is delivered.
     */
    private boolean skipSpaces = false;

    /**
     * The field {@code stream} contains the current stream to read tokens
     * from. For efficiency this stream is kept in a variable instead of
     * accessing the streamStack each time it is needed.
     */
    private TokenStream stream ;

    /**
     * The field {@code streamStack} contains the stack of streams to read from
     * &ndash; except of the current one which is stored in the variable
     * {@code stream}.
     */
    private final ArrayList<TokenStream> streamStack = new ArrayList<>();

    /**
     * The field {@code tokenStreamFactory} contains the factory for new token
     * streams.
     */
    private TokenStreamFactory tokenStreamFactory = null;


    public Moritz() {

        try {
            register(Count.class, new ConstantCountParser());
            register(Dimen.class, new ConstantDimenParser());
            register(Glue.class, new ConstantGlueParser());
            register(Mudimen.class, new ConstantMudimenParser());
            register(Muskip.class, new ConstantMuskipParser());
            register(MathClass.class, new MathClassParser());
        } catch (HelpingException e) {
            throw new RuntimeException(e); // TODO gene: improve it?
        }
        registerObserver(new StartObserver() {

            /**
             * @see org.extex.interpreter.observer.start.StartObserver#update(org.extex.interpreter.Interpreter)
             */
            @Override
            public void update(Interpreter interpreter) throws HelpingException {

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
                    throw new NoHelpException(e);
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
     * @see org.extex.interpreter.TokenSource#addStream(org.extex.scanner.api.TokenStream)
     */
    @Override
    public void addStream(TokenStream theStream) {

        streamStack.add(this.stream);
        this.stream = theStream;
    }

@Override
    public void closeAllStreams(Context context) throws HelpingException {

        while (stream != null) {
            closeStream(context);
        }
    }

@Override
    public void closeNextFileStream(Context context) throws HelpingException {

        while (stream != null) {
            if (closeStream(context)) {
                return;
            }
        }
    }

    /**
     * Close the topmost stream and pop another one to the top if one is left.
     * If the closed stream has been a file stream then the tokens from the
     * tokens register {@code everyeof} are inserted into the token stream.
     * 
     *  <p>The Tokens Parameter {@code \everyeof}</p>
* <p>
     * The tokens parameter {@code \everyeof} is used whenever a file is
     * closed. in this case the tokens contained in this parameter are inserted
     * into the current input stream. Thus thus tokens are processed before the
     * expansion continues to look for any other tokens from other sources.
     * </p>
     * 
     * 
     * <p>Syntax</p>
 The formal description of this primitive is the
     * following:
     * 
     * <pre class="syntax">
     *    &lang;everyeof&rang;
     *      &rarr; {@code \everyeof} {@linkplain
     *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
     *        &lang;equals&rang;} {@linkplain
     *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
     *        &lang;tokens&rang;}  </pre>
     * 
     * <p>Examples</p>

     * 
     * <pre class="TeXSample">
     *    \everyeof={\message{bye bye}}  </pre>
     * 
     *
     * @param context the interpreter context
     * 
     * @return {@code true} iff the closed file has been a file stream
     * 
     * @throws HelpingException in case of an error
     */
    private boolean closeStream(Context context) throws HelpingException {

        if (observersCloseStream != null) {
            observersCloseStream.update(stream);
        }
        boolean isFile = stream.isFileStream();
        int last = streamStack.size() - 1;
        stream = last >= 0 ? streamStack.remove( last) : null;
        if (isFile) {
            push(context.getToks(ToksParameter.getKey("everyeof", context)));
            return true;
        }
        // skipSpaces = false; // macro code needs this and \ignorespaces
        // disagrees
        return false;
    }

    /**
     * Parse the specification of a box.
     * 
     * @param flags the flags to be restored
     * @param context the interpreter context
     * @param typesetter the typesetter to use
     * @param insert the token to insert either at the beginning of the box or
     *        after the box has been gathered. If it is {@code null} then
     *        nothing is inserted
     * 
     * @return the box gathered
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws org.extex.framework.configuration.exception.ConfigurationException
     *         in case of an configuration error
     * 
     * @see org.extex.interpreter.TokenSource#getBox(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter, org.extex.scanner.type.token.Token)
     */
    @Override
    public Box getBox(Flags flags, Context context, Typesetter typesetter,
            Token insert) throws HelpingException, TypesetterException {

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
     * Get the next token from the token stream and check that it is a control
     * sequence or active character. At the end of all input streams the control
     * sequence "inaccessible" is inserted and an exception is thrown. Thus this
     * method will never return {@code null}.
     * 
     * @param context the interpreter context
     * @param typesetter the typesetter
     * 
     * @return the token read
     * 
     * @throws HelpingException in case that the token stream is at its end or
     *         that the token read is not a control sequence token
     * 
     * @see org.extex.interpreter.TokenSource#getControlSequence(org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter)
     */
    @Override
    public CodeToken getControlSequence(Context context, Typesetter typesetter)
            throws HelpingException {

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
        } catch (HelpingException e) {
            throw e;
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
        push(t);
        throw new HelpingException(getLocalizer(), "TTP.MissingCtrlSeq");
    }

    /**
*      CodeToken)
     */
    @Override
    public Font getFont(Context context, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

        Token t = getToken(context);

        if (t == null) {
            throw new EofException(context.esc(primitive));

        } else if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code == null) {
                throw new UndefinedControlSequenceException(t.toText());

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
     * {@code true} is returned. Otherwise all tokens are left in the input
     * stream and {@code false} is returned.
     * 
     * @param context the interpreter context
     * @param s the tokens to scan
     * 
     * @return {@code true} iff the tokens could have been successfully
     *         removed from the input stream
     * 
     * @throws HelpingException in case that no number is found or the end of
     *         file has been reached before an integer could be acquired
     */
    @Override
    public boolean getKeyword(Context context, String s)
            throws HelpingException {

        skipSpaces = true;
        if (getKeyword(context, s, 0)) {
            skipSpaces = true;
            return true;
        }
        return false;
    }

    /**
     * Scans the input token stream for a given sequence of tokens. Those tokens
     * may have the category codes {@code LETTER} or {@code OTHER}.
     * 
     * @param context the interpreter context
     * @param s the string to use as reference
     * @param i the index in s to start working at
     * 
     * @return {@code true} iff the keyword has been found
     * 
     * @throws HelpingException in case of an error
     */
    private boolean getKeyword(Context context, String s, int i)
            throws HelpingException {

        if (i < s.length()) {
            Token t = getToken(context);

            if (t == null) {
                return false;
            } else if (!(t.eq(Catcode.LETTER, s.charAt(i))
                    || t.eq(Catcode.OTHER, s.charAt(i)))
                    || !getKeyword(context, s, i + 1)) {
                put(t);
                return false;
            }

        }
        return true;
    }

@Override
    public Token getLastToken() {

        return lastToken;
    }

@Override
    public Locator getLocator() {

        return stream == null ? null : stream.getLocator();
    }

@Override
    public Token getNonSpace(Context context) throws HelpingException {

        skipSpaces = true;
        return getToken(context);
    }

    /**
     * Scan a number with a given first token.
     * 
     * @param token the first token to consider
     * 
     * @return the value of the integer scanned
     * @throws HelpingException in case that no number is found or the end of
     *         file has been reached before an integer could be acquired
     * @throws MissingNumberException in case that no number is found or the end
     *         of file has been reached before an integer could be acquired
     */
    public long getNumber(Token token)
            throws HelpingException,
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
                            && t.isa(Catcode.OTHER) && t.getChar().isDigit();
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
                    for (t = getToken(context); t instanceof OtherToken;
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

                    for (t = getToken(context);
                    t instanceof OtherToken || t instanceof LetterToken;
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
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.TokenSource#getOptionalEquals(org.extex.interpreter.context.Context)
     */
    @Override
    public void getOptionalEquals(Context context) throws HelpingException {

        skipSpaces = true;
        Token t = getToken(context);

        if (t != null && t.eq(Catcode.OTHER, '=')) {
            skipSpaces = true;
        } else {
            put(t);
        }
    }

    /**
     * Get the next token from the input streams. If the current input stream is
     * at its end then the next one on the streamStack is used until a token
     * could be read. If all stream are at the end then {@code null} is
     * returned.
     * <p>
     * Whenever a file stream is closed then the tokens from the stream are
     * discarded. This holds also for the tokens pushed back onto this stream.
     * </p>
     * 
     * @param context the interpreter context
     * 
     * @return the next token or {@code null}
     * 
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.TokenSource#getToken(org.extex.interpreter.context.Context)
     */
    @Override
    public Token getToken(Context context) throws HelpingException {

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
            throw new NoHelpException(e);
        }

        if (observersEOF != null) {
            observersEOF.update();
        }
        lastToken = null;
        return null;
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens getTokens(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

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
    @Override
    public TokenStreamFactory getTokenStreamFactory() {

        return tokenStreamFactory;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Object parse(Class c, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Parser parser = parsers.get(c);
        if (parser == null) {
            throw new MissingParserException(c.toString());
        }
        return parser.parse(context, source, typesetter);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Dimen parseDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return dimenParser.parseDimen(context, source, typesetter);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Glue parseGlue(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return glueParser.parseGlue(context, source, typesetter);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long parseInteger(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return countParser.parseInteger(context, source, typesetter);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long parseNumber(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return countParser.parseNumber(context, source, typesetter);
    }

@Override
    public void push(Token token) throws HelpingException {

        if (token == null) {
            return;
        }
        if (observersPush != null) {
            observersPush.update(token);
        }

        if (stream == null) {
            stream = getTokenStreamFactory().getStream("");
        }
        stream.put(token);
    }

@Override
    public void push(Token[] tokens) throws HelpingException {

        if (stream == null) {
            stream = getTokenStreamFactory().getStream("");
        }

        for (int i = tokens.length - 1; i >= 0; i--) {

            if (observersPush != null) {
                observersPush.update(tokens[i]);
            }

            stream.put(tokens[i]);
        }
    }

@Override
    public void push(Tokens tokens) throws HelpingException {

        if (tokens == null || tokens.length() == 0) {
            return;
        }

        if (stream == null) {
            stream = getTokenStreamFactory().getStream("");
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
     * @throws HelpingException in case of a configuration problem
     */
    private void put(Token t) throws HelpingException {

        if (t == null) {
            return;
        }
        if (stream == null) {
            stream = getTokenStreamFactory().getStream("");
        }
        stream.put(t);

    }

    /**
*      org.extex.interpreter.parser.Parser)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Parser register(Class c, Parser p) throws HelpingException {

        Parser old = parsers.put(c, p);
        if (c == Count.class) {
            if (p instanceof CountParser) {
                countParser = (CountParser) p;
            } else {
                throw new HelpingException(getLocalizer(), "IllegalCountParser");
            }
        } else if (c == Dimen.class) {
            if (p instanceof DimenParser) {
                dimenParser = (DimenParser) p;
            } else {
                throw new HelpingException(getLocalizer(), "IllegalDimenParser");
            }
        } else if (c == Glue.class) {
            if (p instanceof GlueParser) {
                glueParser = (GlueParser) p;
            } else {
                throw new HelpingException(getLocalizer(), "IllegalGLueParser");
            }
        }
        return old;
    }

    /**
     * Add an observer for the eof event. This observer is triggered by an end
     * of file on the token stream. This means that all tokens have been
     * processed and all stream are at their end.
     * 
     * @param observer the observer to add
     */
    @Override
    public void registerObserver(EofObserver observer) {

        observersEOF = EofObserverList.register(observersEOF, observer);
    }

    /**
     * Register an open file observer for later use.
     * 
     * @param observer the observer to be registered
     * 
     * @see org.extex.scanner.stream.observer.file.OpenFileObservable#registerObserver(org.extex.scanner.stream.observer.file.OpenFileObserver)
     */
    @Override
    public void registerObserver(OpenFileObserver observer) {

        tokenStreamFactory.registerObserver(observer);
    }

    /**
     * Register an open reader observer for later use.
     * 
     * @param observer the observer to be registered
     * 
     * @see org.extex.scanner.stream.observer.reader.OpenReaderObservable#registerObserver(org.extex.scanner.stream.observer.reader.OpenReaderObserver)
     */
    @Override
    public void registerObserver(OpenReaderObserver observer) {

        tokenStreamFactory.registerObserver(observer);
    }

    /**
     * Register an open string observer for later use.
     * 
     * @param observer the observer to be registered
     * 
     * @see org.extex.scanner.stream.observer.string.OpenStringObservable#registerObserver(org.extex.scanner.stream.observer.string.OpenStringObserver)
     */
    @Override
    public void registerObserver(OpenStringObserver observer) {

        tokenStreamFactory.registerObserver(observer);
    }

    /**
     * Add an observer for the pop event. This observer is triggered by a pop
     * operation on the token stream. This means that a token has been
     * extracted.
     * 
     * @param observer the observer to add
     */
    @Override
    public void registerObserver(PopObserver observer) {

        observersPop = PopObserverList.register(observersPop, observer);
    }

    /**
     * Add an observer for the push event. This observer is triggered by a push
     * operation on the token stream. This means that a token has been placed on
     * the current stream for subsequent reading.
     * 
     * @param observer the observer to add
     */
    @Override
    public void registerObserver(PushObserver observer) {

        observersPush = PushObserverList.register(observersPush, observer);
    }

    /**
     * Register an stream close observer for later use.
     * 
     * @param observer the observer to be registered
     * 
     * @see org.extex.interpreter.observer.streamClose.StreamCloseObservable#registerObserver(org.extex.interpreter.observer.streamClose.StreamCloseObserver)
     */
    @Override
    public void registerObserver(StreamCloseObserver observer) {

        observersCloseStream =
                StreamCloseObserverList.register(observersCloseStream,
                    observer);
    }

    /**
*      org.extex.typesetter.Typesetter, CodeToken)
     */
    @Override
    public UnicodeChar scanCharacterCode(Context context,
            Typesetter typesetter, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

        long cc;

        Token t = getNonSpace(context);
        if (t instanceof LeftBraceToken) {
            push(t);
            String name = scanTokensAsString(context, primitive);

            UnicodeChar uc = UnicodeChar.get(name);
            if (uc == null) {
                throw new InvalidCharacterNameException(name);
            }

            return uc;

        }

        push(t);
        cc = countParser.parseInteger(context, this, typesetter);

        if (cc < 0 || cc > MAX_CHAR_CODE) {
            throw new BadCharacterException(cc);
        }
        return UnicodeChar.get((int) cc);
    }

@Override
    public Token scanNonSpace(Context context)
            throws HelpingException,
                TypesetterException {

        Token t;
        do {
            skipSpaces = true;
            t = scanToken(context);
        } while (t != null && t.isa(Catcode.SPACE));

        return t;
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      CodeToken)
     */
    @Override
    public String scanRegisterName(Context context, TokenSource source,
            Typesetter typesetter, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

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
        long registerNumber =
                countParser.parseInteger(context, source, typesetter);
        if (registerNumber < 0 || maxRegisterValue >= 0
                && registerNumber > maxRegisterValue) {
            throw new IllegalRegisterException(Long.toString(registerNumber));
        }
        return Long.toString(registerNumber);
    }

@Override
    public Token scanToken(Context context)
            throws HelpingException,
                TypesetterException {

        return expand(getToken(context));
    }

    /**
*      boolean, boolean, org.extex.scanner.type.token.CodeToken)
     */
    @Override
    public Tokens scanTokens(Context context, boolean reportUndefined,
            boolean ignoreUndefined, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

        Tokens toks = new Tokens();
        skipSpaces = true;
        Token token = scanToken(context);

        if (token == null) {
            throw new EofException(primitive.toText());
        } else if (token instanceof CodeToken) {
            Code code = context.getCode((CodeToken) token);
            if (code instanceof TokensConvertible) {
                return ((TokensConvertible) code).convertTokens(context, this,
                    getTypesetter());
            }
            throw new MissingLeftBraceException(primitive.toText());
        } else if (!(token instanceof LeftBraceToken)) {
            throw new MissingLeftBraceException(primitive.toText());
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
                    throw new UndefinedControlSequenceException(
                        token.toString());
                } else {
                    toks.add(token);
                }

            } else {
                toks.add(token);
            }
        }
    }

    /**
*      CodeToken)
     */
    @Override
    public String scanTokensAsString(Context context, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

        Tokens tokens = scanTokens(context, false, false, primitive);
        if (tokens == null) {
            throw new EofException(primitive.toText());
        }
        return tokens.toText();
    }

    /**
*      boolean, boolean, CodeToken)
     */
    @Override
    public Tokens scanUnprotectedTokens(Context context,
            boolean reportUndefined, boolean ignoreUndefined,
            CodeToken primitive) throws HelpingException, TypesetterException {

        Tokens toks = new Tokens();
        skipSpaces = true;
        Token token = getToken(context);

        if (token == null) {
            throw new EofException(primitive.toText());
        } else if (!token.isa(Catcode.LEFTBRACE)) {
            throw new MissingLeftBraceException(primitive.toText());
        }

        int balance = 1;

        for (;;) {

            token = expandUnproteced(getToken(context), toks);

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
                    throw new UndefinedControlSequenceException(
                        token.toString());
                } else {
                    toks.add(token);
                }

            } else if (token == null) {
                throw new EofException();
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
     * @throws org.extex.framework.configuration.exception.ConfigurationException
     *         this exception is never thrown. It is defined here to provide an
     *         exit for derived classes
     */
    @Override
    public void setTokenStreamFactory(TokenStreamFactory factory) {

        tokenStreamFactory = factory;
    }

    /**
     * Skip spaces and check whether any tokens are left.
     * 
     * @see org.extex.interpreter.TokenSource#skipSpace()
     */
    @Override
    public void skipSpace() {

        skipSpaces = true;
    }

@Override
    public String toString() {
        final Locator locator = getLocator();
        return locator == null ? "" : locator.toString();
    }

    /**
     * Send the string to the named observer. The observer must be capable to
     * deal with a string argument.
     * 
     * @param name name of the observer
     * @param text the text to send to the observer
     * 
     * @throws NotObservableException in case that the named observer is not
     *         accessible
     * 
     * @see org.extex.interpreter.TokenSource#update(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void update(String name, String text) throws NotObservableException {

        throw new NotObservableException(name);
    }

}
