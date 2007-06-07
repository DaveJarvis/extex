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

package org.extex.unit.tex.macro.util;

import org.extex.core.Locator;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ComparableCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.interpreter.type.Showable;
import org.extex.scanner.TokenStream;
import org.extex.scanner.Tokenizer;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.MacroParamToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.macro.LetCode;
import org.extex.unit.tex.typesetter.paragraph.Par;

/**
 * This class provides an implementation for any macro code bound to a control
 * sequence or active character.
 * 
 * <doc name="macros" type="howto">
 * <h3>The Macro Code</h3>
 * <p>
 * TODO missing documentation
 * </p>
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4408 $
 */
public class MacroCode extends AbstractCode
        implements
            PrefixCode,
            ExpandableCode,
            ComparableCode,
            Showable {

    /**
     * This inner class provides the tokens of a macro as a token stream.
     */
    private class MacroTokenStream implements TokenStream {

        /**
         * The field <tt>tokens</tt> contains the tokens.
         */
        private Tokens tokens;

        /**
         * The field <tt>locator</tt> contains the locator.
         */
        private Locator locator;

        /**
         * Creates a new object.
         * 
         * @param tokens the tokens
         * @param locator the locator of the invocation
         */
        public MacroTokenStream(Tokens tokens, Locator locator) {

            super();
            this.tokens = tokens;
            this.locator = locator;
        }

        /**
         * Close this stream if it is a file stream.
         * 
         * @return <code>true</code> if the closing was successful
         * 
         * @see org.extex.scanner.TokenStream#closeFileStream()
         */
        public boolean closeFileStream() {

            return false;
        }

        /**
         * Get the next token from the token stream. If tokens are on the
         * push-back stack then those are delivered otherwise new tokens might
         * be extracted utilizing the token factory and the tokenizer.
         * 
         * @param factory the token factory
         * @param tokenizer the tokenizer
         * 
         * @return the next Token or <code>null</code> if no more tokens are
         *         available
         * 
         * @throws ScannerException in case of an error
         * 
         * @see org.extex.scanner.TokenStream#get(
         *      org.extex.scanner.type.token.TokenFactory,
         *      org.extex.scanner.Tokenizer)
         */
        public Token get(TokenFactory factory, Tokenizer tokenizer)
                throws ScannerException {

            return tokens.pop();
        }

        /**
         * Getter for the locator. The locator describes the place the tokens
         * have been read from in terms of the user. This information is meant
         * for the end user to track down problems.
         * 
         * @return the locator
         * 
         * @see org.extex.scanner.TokenStream#getLocator()
         */
        public Locator getLocator() {

            Locator loc = new Locator(getName(), -1, null, -1);
            loc.setCause(locator);
            return loc;
        }

        /**
         * Check to see if a further token can be acquired from the token
         * stream.
         * 
         * @return <code>true</code> if the stream is at its end
         * 
         * @throws ScannerException in case that an error has been encountered.
         *         Especially if an IO exceptions occurs it is delivered as
         *         chained exception in a ScannerException.
         * 
         * @see org.extex.scanner.TokenStream#isEof()
         */
        public boolean isEof() throws ScannerException {

            return tokens.length() == 0;
        }

        /**
         * Check to see if the token stream is currently at the end of line.
         * 
         * @return <code>true</code> if the stream is at end of line
         * 
         * @throws ScannerException in case that an error has been encountered.
         *         Especially if an IO exceptions occurs it is delivered as
         *         chained exception in a ScannerException.
         * 
         * @see org.extex.scanner.TokenStream#isEol()
         */
        public boolean isEol() throws ScannerException {

            return false;
        }

        /**
         * Check whether the current stream is associated with a file to read
         * from.
         * 
         * @return <code>true</code> if the stream is a file stream
         * 
         * @see org.extex.scanner.TokenStream#isFileStream()
         */
        public boolean isFileStream() {

            return false;
        }

        /**
         * Push back a token into the stream. If the token is <code>null</code>
         * then nothing happens: a <code>null</code> token is not pushed!
         * <p>
         * Note that it is up to the implementation to accept tokens not
         * produced with the token factory for push back. In general the
         * behavior in such a case is not defined and should be avoided.
         * </p>
         * 
         * @param token the token to push back
         * @see "<logo>TeX</logo> &ndash; The Program [325]"
         * 
         * @see org.extex.scanner.TokenStream#put(
         *      org.extex.scanner.type.token.Token)
         */
        public void put(Token token) {

            tokens.push(token);
        }
    }

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>body</tt> contains the tokens of the macro expansion
     * text.
     */
    private Tokens body;

    /**
     * The field <tt>notLong</tt> contains the negated <tt>\long</tt> flag.
     * This field indicates that no macros <tt>\par</tt> are allowed in macro
     * parameter values.
     */
    private boolean notLong;

    /**
     * The field <tt>outerP</tt> contains the indicator for outer definitions.
     */
    private boolean outerP;

    /**
     * The field <tt>pattern</tt> contains the specification for the argument
     * matching.
     */
    private MacroPattern pattern;

    /**
     * Creates a new object.
     * 
     * @param name the initial name of the macro
     * @param flags the flags controlling the behavior of the macro
     * @param pattern the pattern for the acquiring of the arguments
     * @param body the expansion text
     */
    public MacroCode(String name, Flags flags, MacroPattern pattern, Tokens body) {

        super(name);
        this.body = body;
        this.pattern = pattern;
        this.notLong = !flags.clearLong();
        this.outerP = flags.clearOuter();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ComparableCode#compare(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.Context)
     */
    public boolean compare(Token token, Context context)
            throws HelpingException {

        if (!(token instanceof CodeToken)) {
            return false;
        }

        Code code = context.getCode((CodeToken) token);

        if (!(code instanceof MacroCode)) {
            return false;
        }
        MacroCode macro = (MacroCode) code;
        if (notLong != macro.notLong //
                || outerP != macro.outerP //
                || !pattern.equals(macro.pattern)) {
            return false;
        }
        return body.equals(macro.body);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Tokens[] args = matchPattern(context, source, typesetter);
        Tokens toks = new Tokens();
        int len = body.length();
        int no = 1;

        for (int i = 0; i < len; i++) {
            Token t = body.get(i);

            if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof LetCode) {
                    t = ((LetCode) code).getToken();
                }
            }

            if (t instanceof MacroParamToken) {
                t = body.get(++i);
                if (t == null) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.EOFinMatch", printableControlSequence(context));
                } else if (t instanceof MacroParamToken) {
                    toks.add(t);
                } else if (t instanceof OtherToken && t.getChar().isDigit()) {
                    no = t.getChar().getCodePoint() - '0';
                    if (args[no] == null) {
                        throw new ImpossibleException("MacroCode:NullArg");
                    }
                    toks.add(args[no]);
                } else {
                    throw new ImpossibleException("MacroCode:IllegalArg");
                }
            } else {
                toks.add(t);
            }
        }

        source.addStream(new MacroTokenStream(toks, source.getLocator()));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        execute(prefix, context, source, typesetter);
    }

    /**
     * Getter for localizer.
     * 
     * @return the localizer.
     * 
     * @see org.extex.interpreter.type.AbstractCode#getLocalizer()
     */
    @Override
    protected Localizer getLocalizer() {

        Localizer localizer = super.getLocalizer();
        return (localizer != null ? localizer : LocalizerFactory
            .getLocalizer(MacroCode.class));
    }

    /**
     * Get a single token or a block if the first token is a LeftBraceToken.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the tokens accumulated
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private Tokens getTokenOrBlock(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getToken(context);

        if (t == null) {
            throw new HelpingException(getLocalizer(), "TTP.EOFinMatch",
                getName());
        } else if (t instanceof LeftBraceToken) {
            source.push(t);
            Tokens toks;
            try {
                toks = source.getTokens(context, source, typesetter);
            } catch (EofException e) {
                throw new HelpingException(getLocalizer(), "TTP.EOFinMatch",
                    getName());
            }
            return toks;
        }

        return new Tokens(t);
    }

    /**
     * Getter for the outer flag.
     * 
     * @return <code>true</code> iff the code is defined outer.
     * 
     * @see org.extex.interpreter.type.Code#isOuter()
     */
    @Override
    public boolean isOuter() {

        return outerP;
    }

    /**
     * Match a single parameter.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param args the array of Tokens to fill
     * @param len the length of the patterns
     * @param i the starting index
     * 
     * @return the index of the character after the parameter
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private int matchParameter(Context context, TokenSource source,
            Typesetter typesetter, Tokens[] args, int len, int i)
            throws HelpingException,
                TypesetterException {

        Token t;

        if (i >= len) {
            t = source.getToken(context);
            source.push(t);

            if (t instanceof LeftBraceToken) {
                return i;
            }
            throw new HelpingException(getLocalizer(), "TTP.UseDoesntMatch",
                getName());
        }
        Token ti = pattern.get(i);
        if (ti instanceof MacroParamToken) {
            t = source.getToken(context);
            if (ti.equals(t)) {
                return i;
            }
        } else if (ti instanceof OtherToken && ti.getChar().isDigit()) {
            int no = ti.getChar().getCodePoint() - '0';
            int i1 = i + 1;
            if (i1 < len && !(pattern.get(i1) instanceof MacroParamToken)) {
                // TODO gene: #1##
                args[no] = scanTo(context, source, pattern.get(i1));
                return i + 2;
            }
            args[no] = getTokenOrBlock(context, source, typesetter);
            return i1;
        }
        throw new HelpingException(getLocalizer(), "TTP.UseDoesntMatch",
            getName());
    }

    /**
     * Match the pattern of this macro with the next tokens from the token
     * source. As a result the matching arguments are stored in an array of
     * {@link org.extex.scanner.type.tokens.Tokens Tokens}. This array is
     * returned.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return a new array of parameters which have been found during the
     *         matching. Note that some of th elements of the array might be
     *         <code>null</code>.
     * 
     * @throws HelpingException in case of an error during the matching
     * @throws TypesetterException in case of an error in the typesetter
     */
    private Tokens[] matchPattern(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Tokens[] args = new Tokens[pattern.getArity()];
        Token ti;
        int len = pattern.length();
        int i = 0;

        while (i < len) {
            ti = pattern.get(i++);
            if (ti instanceof MacroParamToken) {
                i = matchParameter(context, source, typesetter, args, len, i);
            } else {
                Token t = source.getToken(context);
                if (!ti.equals(t)) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.UseDoesntMatch", getName());
                } else if (notLong && t.equals(Catcode.ESCAPE, "par")) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.RunawayArg", getName());
                }
            }
        }

        return args;
    }

    /**
     * Collect all tokens until a given token is found.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param to the terminating token
     * 
     * @return the tokens accumulated in between
     * 
     * @throws HelpingException in case of an error
     */
    private Tokens scanTo(Context context, TokenSource source, Token to)
            throws HelpingException {

        Tokens toks = new Tokens();
        int depth = 0;

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {
            if (depth == 0 && t.equals(to)) {
                return toks;
            } else if (t instanceof LeftBraceToken) {
                depth++;
            } else if (t instanceof RightBraceToken) {
                depth--;
                if (depth < 0) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.ExtraRightBrace", getName());
                }
            } else if (notLong && t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof Par) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.RunawayArg", getName());
                }
            }
            toks.add(t);
        }

        throw new HelpingException(getLocalizer(), "TTP.EOFinMatch", getName());
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * 
     * @return the description of the primitive as list of Tokens
     * @see org.extex.interpreter.type.Showable#show(
     *      org.extex.interpreter.context.Context)
     */
    public Tokens show(Context context) throws HelpingException {

        try {
            StringBuffer sb = new StringBuffer();
            boolean sep = false;
            if (!notLong) {
                sb.append(context.esc(getLocalizer().format("TTP.long")));
                sep = true;
            }
            if (outerP) {
                sb.append(context.esc(getLocalizer().format("TTP.outer")));
                sep = true;
            }
            if (sep) {
                sb.append(" ");
            }
            sb.append(getLocalizer().format("TTP.macro"));
            sb.append(":\n");
            Tokens toks = context.getTokenFactory().toTokens(sb);
            show(pattern, context, toks);
            toks.add(context.getTokenFactory().toTokens("->"));
            show(body, context, toks);
            return toks;
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * Determine the printable representation of the object and append it to a
     * list of Tokens.
     * 
     * @param tokens the tokens to add
     * @param context the processor context
     * @param toks the tokens to add to
     * 
     * @throws CatcodeException in case of an error
     */
    private void show(Tokens tokens, Context context, Tokens toks)
            throws CatcodeException {

        TokenFactory factory = context.getTokenFactory();
        Token t;

        for (int i = 0; i < tokens.length(); i++) {
            t = tokens.get(i);
            if (t instanceof ControlSequenceToken) {
                long esc = context.getCount("escapechar").getValue();
                if (esc >= 0) {
                    toks.add(factory.createToken(Catcode.OTHER, (char) (esc),
                        Namespace.DEFAULT_NAMESPACE));
                }
                toks.add(factory.toTokens(t.toString()));
                // } else if (t instanceof MacroParamToken) {
                // toks.add(factory.createToken(Catcode.OTHER, '#',
                // Namespace.DEFAULT_NAMESPACE));
            } else {
                toks.add(factory.createToken(Catcode.OTHER, t.getChar(),
                    Namespace.DEFAULT_NAMESPACE));
            }
        }
    }

}
