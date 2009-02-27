/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.MacroParamToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.macro.LetCode;
import org.extex.unit.tex.macro.exceptions.EofInDefException;
import org.extex.unit.tex.macro.exceptions.EofInMatchException;
import org.extex.unit.tex.macro.exceptions.ExtraRightBraceInDefException;
import org.extex.unit.tex.macro.exceptions.MissingLeftBraceInDefException;
import org.extex.unit.tex.macro.exceptions.NonConsequtiveParamsException;
import org.extex.unit.tex.macro.exceptions.OuterInDefException;
import org.extex.unit.tex.typesetter.paragraph.Par;

/**
 * This class provides a container for the pattern of a macro.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class MacroPattern extends Tokens {

    /**
     * This matcher acquires a single token or a group and stores it in an
     * argument.
     */
    private class ArgumentMatcher implements Matcher {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * The field <tt>no</tt> contains the index of the argument.
         */
        private int no;

        /**
         * Creates a new object.
         * 
         * @param no the index of the argument
         */
        ArgumentMatcher(int no) {

            super();
            this.no = no;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.tex.macro.util.MacroPattern.Matcher#match(
         *      org.extex.scanner.type.tokens.Tokens[],
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter, boolean, CodeToken)
         */
        public void match(Tokens[] args, Context context, TokenSource source,
                Typesetter typesetter, boolean notLong, CodeToken cs)
                throws HelpingException,
                    TypesetterException {

            args[no] = MacroPattern.getTokenOrBlock(context, source, //
                typesetter, cs);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "#" + Integer.toString(no);
        }

    }

    /**
     * This matcher collects the tokens to a terminating token. If groups are
     * opened on the way then they have to be closed before the terminating
     * token is accepted.
     */
    private class CollectingMatcher implements Matcher {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * The field <tt>no</tt> contains the index of the argument.
         */
        private int no;

        /**
         * The field <tt>t</tt> contains the terminating token.
         */
        private Token token;

        /**
         * Creates a new object.
         * 
         * @param token the token
         * @param no the index of the argument
         */
        CollectingMatcher(Token token, int no) {

            super();
            this.token = token;
            this.no = no;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.tex.macro.util.MacroPattern.Matcher#match(
         *      org.extex.scanner.type.tokens.Tokens[],
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter, boolean, CodeToken)
         */
        public void match(Tokens[] args, Context context, TokenSource source,
                Typesetter typesetter, boolean notLong, CodeToken cs)
                throws HelpingException {

            args[no] = scanTo(context, source, token, notLong);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "#" + Integer.toString(no) + token.toText();
        }

    };

    /**
     * Matcher which recognized just a single constant token.
     */
    private class ConstantMatcher implements Matcher {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * The field <tt>token</tt> contains the token to look for.
         */
        private Token token;

        /**
         * Creates a new object.
         * 
         * @param token the token
         */
        ConstantMatcher(Token token) {

            super();
            this.token = token;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.tex.macro.util.MacroPattern.Matcher#match(
         *      org.extex.scanner.type.tokens.Tokens[],
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter, boolean, CodeToken)
         */
        public void match(Tokens[] args, Context context, TokenSource source,
                Typesetter typesetter, boolean notLong, CodeToken cs)
                throws HelpingException {

            Token t = source.getToken(context);
            if (!token.equals(t)) {
                throw new HelpingException(getLocalizer(),
                    "TTP.UseDoesntMatch", //
                    cs.toText(context.escapechar()));
            }
            if (notLong && t.eq(Catcode.ESCAPE, "par")) {
                throw new HelpingException(getLocalizer(), "TTP.RunawayArg", //
                    cs.toText(context.escapechar()));
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return token.toText();
        }

    }

    /**
     * This matcher collects the tokens from a group.
     */
    private class GroupMatcher implements Matcher {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * The field <tt>no</tt> contains the index of the argument.
         */
        private int no;

        /**
         * Creates a new object.
         * 
         * @param no the index of the argument
         */
        GroupMatcher(int no) {

            super();
            this.no = no;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.tex.macro.util.MacroPattern.Matcher#match(
         *      org.extex.scanner.type.tokens.Tokens[],
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter, boolean, CodeToken)
         */
        public void match(Tokens[] args, Context context, TokenSource source,
                Typesetter typesetter, boolean notLong, CodeToken cs)
                throws HelpingException,
                    TypesetterException {

            Token t = source.getToken(context);

            if (t == null) {
                throw new EofInMatchException(cs.toText(context.escapechar()));
            } else if (!(t instanceof LeftBraceToken)) {
                throw new HelpingException(getLocalizer(),
                    "TTP.UseDoesntMatch", //
                    cs.toText(context.escapechar()));
            } else if (t instanceof RightBraceToken) {
                throw new ExtraRightBraceInDefException(cs.toText(context
                    .escapechar()));
            }
            source.push(t);
            try {
                args[no] = source.getTokens(context, source, typesetter);
            } catch (EofException e) {
                throw new EofInMatchException(cs.toText(context.escapechar()));
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "#{" + Integer.toString(no);
        }

    }

    /**
     * This interface describes a function object performing a match.
     */
    private interface Matcher extends Serializable {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        static final long serialVersionUID = 2007L;

        /**
         * Perform a match and digest the required tokens. As a side effect the
         * argument array can be filled with recognized argument values.
         * 
         * @param args the arguments to be filled in
         * @param context the interpreter context
         * @param source the source for new tokens
         * @param typesetter the typesetter
         * @param notLong the inverted long indicator
         * @param cs the control sequence for error reporting
         * 
         * @throws TypesetterException in case of an error in the typesetter
         * @throws HelpingException in case of an error
         */
        void match(Tokens[] args, Context context, TokenSource source,
                Typesetter typesetter, boolean notLong, CodeToken cs)
                throws HelpingException,
                    TypesetterException;

    }

    /**
     * The constant <tt>EMPTY</tt> contains the empty macro pattern. This can
     * be used in a poor man's factory.
     */
    public static final MacroPattern EMPTY = new MacroPattern(Tokens.EMPTY);

    /**
     * The constant <tt>NOT_COMPILED</tt> contains the flag for the compiler
     * to leave out the compiler.
     */
    private static final boolean NOT_COMPILED = true;

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Getter for the localizer.
     * 
     * @return the localizer
     */
    private static Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(MacroPattern.class);
    }

    /**
     * Parse a pattern specification.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param longP indicator for the long flag
     * @param cs the macro being defined
     * 
     * @return the tokens read
     * 
     * @throws HelpingException in case of an error
     */
    public static MacroPattern getPattern(Context context, TokenSource source,
            boolean longP, CodeToken cs) throws HelpingException {

        MacroPattern pattern = new MacroPattern(cs);
        boolean afterHash = false;
        int no = 0;

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {

            if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code == null) {
                    // undefined
                } else if (code.isOuter()) {
                    throw new OuterInDefException(cs.toText(context
                        .escapechar()));
                } else if (code instanceof LetCode) {
                    t = ((LetCode) code).getLetToken();
                }
            } else if (t instanceof LeftBraceToken) {
                source.push(t);
                pattern.setArity(no);
                if (afterHash) {
                    pattern.removeLast();
                    pattern.add(t);
                }
                if (!NOT_COMPILED) {
                    pattern.compile(context);
                }
                return pattern;
            } else if (t instanceof RightBraceToken) {
                throw new MissingLeftBraceInDefException(cs.toText(context
                    .escapechar()));
            }

            if (afterHash) {
                if (t instanceof OtherToken) {
                    if (t.getChar().getCodePoint() - '1' != no) {
                        throw new NonConsequtiveParamsException(cs
                            .toText(context.escapechar()));
                    }
                    no++;
                } else if (!(t instanceof MacroParamToken)) {
                    throw new NonConsequtiveParamsException(cs.toText(context
                        .escapechar()));
                }
                afterHash = false;
            } else {
                afterHash = (t instanceof MacroParamToken);
            }
            pattern.add(t);
        }

        throw new EofInDefException(cs.toText(context.escapechar()));
    }

    /**
     * Get a single token or a block if the first token is a
     * {@link LeftBraceToken}.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param cs the control sequence for error reporting
     * 
     * @return the tokens accumulated
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected static Tokens getTokenOrBlock(Context context,
            TokenSource source, Typesetter typesetter, CodeToken cs)
            throws HelpingException,
                TypesetterException {

        Token t = source.getToken(context);

        if (t == null) {
            throw new EofInMatchException(cs.toText(context.escapechar()));
        } else if (t instanceof LeftBraceToken) {
            source.push(t);
            try {
                return source.getTokens(context, source, typesetter);
            } catch (EofException e) {
                throw new EofInMatchException(cs.toText(context.escapechar()));
            }
        } else if (t instanceof RightBraceToken) {
            throw new ExtraRightBraceInDefException(cs.toText(context
                .escapechar()));
        }

        return new Tokens(t);
    }

    /**
     * The field <tt>arity</tt> contains the arity, i.e. the number or
     * parameters.
     */
    private int arity = 0;

    /**
     * The field <tt>cs</tt> contains the token of the invoking control
     * sequence.
     */
    private CodeToken cs;

    /**
     * The field <tt>matcher</tt> contains the compiled matcher list.
     */
    private Matcher[] matcher;

    /**
     * Creates a new object.
     * 
     * @param cs the name of the invoking macro
     */
    protected MacroPattern(CodeToken cs) {

        super();
        this.cs = cs;
    }

    /**
     * Creates a new object.
     * 
     * @param tokens the tokens contained
     */
    protected MacroPattern(Tokens tokens) {

        super();
        add(tokens);
    }

    /**
     * Compile the pattern into a list of function elements to perform the
     * operations required for doing the matching.
     * 
     * @param context the interpreter context
     * 
     * @throws HelpingException in case of an error
     */
    protected void compile(Context context) throws HelpingException {

        List<Matcher> ml = new ArrayList<Matcher>();
        boolean afterHash = false;
        int length = length();

        for (int i = 0; i < length; i++) {
            Token t = get(i);

            if (afterHash) {
                afterHash = false;
                if (t instanceof MacroParamToken) {
                    // this is an extension to TeX
                    ml.add(new ConstantMatcher(t));
                } else if (t instanceof LeftBraceToken) {
                    // this can happen at the end only
                    ml.add(new GroupMatcher(arity - 1));
                } else {
                    if (!(t instanceof OtherToken)) {
                        throw new NonConsequtiveParamsException(cs
                            .toText(context.escapechar()));
                    }
                    UnicodeChar uc = t.getChar();
                    if (uc == null) {
                        throw new NonConsequtiveParamsException(cs
                            .toText(context.escapechar()));
                    }
                    int no = uc.getCodePoint() - '1';
                    if (no < 0 || no >= 9) {
                        throw new NonConsequtiveParamsException(cs
                            .toText(context.escapechar()));
                    }

                    if (i + 1 < length) {
                        t = get(i + 1);
                        if (!(t instanceof MacroParamToken)) {
                            ml.add(new CollectingMatcher(t, no));
                            i++;
                            continue;
                        }
                    }
                    ml.add(new ArgumentMatcher(no));
                }
            } else if (t instanceof MacroParamToken) {
                afterHash = true;
            } else {
                ml.add(new ConstantMatcher(t));
            }
        }

        matcher = new Matcher[ml.size()];
        int i = 0;
        for (Matcher m : ml) {
            matcher[i++] = m;
        }
    }

    /**
     * Match the pattern of this macro with the next tokens from the token
     * source. As a result the matching arguments are stored in an array of
     * {@link org.extex.scanner.type.tokens.Tokens Tokens}. This array is
     * returned.
     * <p>
     * Note that the first argument &ndash; denoted #1 &ndash; can be found at
     * position 0 of the array.
     * </p>
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param notLong
     * @param observer the observer for argument matching events
     * 
     * @return a new array of parameters which have been found during the
     *         matching. Note that some of the elements of the array might be
     *         <code>null</code>.
     * 
     * @throws HelpingException in case of an error during the matching
     * @throws TypesetterException in case of an error in the typesetter
     */
    public Tokens[] match(Context context, TokenSource source,
            Typesetter typesetter, boolean notLong,
            ArgumentMatchingObserver observer)
            throws HelpingException,
                TypesetterException {

        if (NOT_COMPILED) {
            return matchRaw(context, source, typesetter, notLong, observer);
        }

        Tokens[] args = new Tokens[arity];
        for (Matcher m : matcher) {
            m.match(args, context, source, typesetter, notLong, cs);
        }
        return args;

    }

    /**
     * Match the pattern of this macro with the next tokens from the token
     * source. As a result the matching arguments are stored in an array of
     * {@link org.extex.scanner.type.tokens.Tokens Tokens}. This array is
     * returned.
     * <p>
     * Note that the first argument &ndash; denoted #1 &ndash; can be found at
     * position 0 of the array.
     * </p>
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param notLong
     * @param observer the observer for argument matching events
     * 
     * @return a new array of parameters which have been found during the
     *         matching. Note that some of the elements of the array might be
     *         <code>null</code>.
     * 
     * @throws HelpingException in case of an error during the matching
     * @throws TypesetterException in case of an error in the typesetter
     */
    private Tokens[] matchRaw(Context context, TokenSource source,
            Typesetter typesetter, boolean notLong,
            ArgumentMatchingObserver observer)
            throws HelpingException,
                TypesetterException {

        Tokens[] args = new Tokens[arity];
        int len = length();

        for (int i = 0; i < len;) {
            Token ti = get(i++);
            if (ti instanceof MacroParamToken) {
                i = scanParameter(context, source, typesetter, //
                    args, len, i, observer, notLong);
            } else {
                Token t = source.getToken(context);
                if (!ti.equals(t)) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.UseDoesntMatch", //
                        cs.toText(context.escapechar()));
                } else if (notLong && t.eq(Catcode.ESCAPE, "par")) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.RunawayArg", cs.toText(context.escapechar()));
                }
            }
        }

        return args;
    }

    /**
     * Match a single parameter.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param args the array of Tokens to fill
     * @param len the length of the patterns
     * @param index the starting index
     * @param observer the observer for tracing
     * @param notlong
     * 
     * @return the index of the character after the parameter
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private int scanParameter(Context context, TokenSource source,
            Typesetter typesetter, Tokens[] args, int len, int index,
            ArgumentMatchingObserver observer, boolean notlong)
            throws HelpingException,
                TypesetterException {

        Token t = get(index);

        if (t instanceof MacroParamToken) {
            // ##
            Token x = source.getToken(context);
            if (t.equals(x)) {
                return index + 1;
            }
            throw new HelpingException(getLocalizer(), "TTP.UseDoesntMatch", //
                cs.toText(context.escapechar()));
        }

        if (!(t instanceof OtherToken)) {
            throw new HelpingException(getLocalizer(), "TTP.IllegalParamNum",
                cs.toText(context.escapechar()));
        }
        int no = t.getChar().getCodePoint() - '1';
        if (no < 0 || no >= args.length) {
            throw new HelpingException(getLocalizer(), "TTP.IllegalParamNum",
                cs.toText(context.escapechar()));
        }
        int i = index + 1;
        if (i >= len) {
            args[no] = getTokenOrBlock(context, source, typesetter, cs);
        } else {
            t = get(i);
            if (t instanceof MacroParamToken) {
                args[no] = getTokenOrBlock(context, source, typesetter, cs);
            } else {
                args[no] = scanTo(context, source, t, notlong);
                i = index + 2;
            }
        }

        if (observer != null) {
            observer.observeArgument(no, args[no], cs);
        }
        return i;
    }

    /**
     * Collect all tokens until a given token is found.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param to the terminating token
     * @param notLong
     * 
     * @return the tokens accumulated in between
     * 
     * @throws HelpingException in case of an error
     */
    private Tokens scanTo(Context context, TokenSource source, Token to,
            boolean notLong) throws HelpingException {

        Tokens toks = new Tokens();
        int depth = 0;
        Token t = source.getToken(context);
        int groups = 0;
        boolean group = (t instanceof LeftBraceToken);

        for (; t != null; t = source.getToken(context)) {
            if (depth == 0 && t.equals(to)) {
                if (group
                        && groups == 1
                        && toks.get(toks.length() - 1) instanceof RightBraceToken) {
                    toks.removeLast();
                    toks.removeFirst();
                }
                return toks;

            } else if (t instanceof LeftBraceToken) {
                groups++;
                depth++;
            } else if (t instanceof RightBraceToken) {
                depth--;
                if (depth < 0) {
                    throw new ExtraRightBraceInDefException(cs.toText(context
                        .escapechar()));
                }
            } else if (notLong && t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof Par) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.RunawayArg", cs.toText(context.escapechar()));
                }
            }
            toks.add(t);
        }

        throw new EofInMatchException(cs.toText(context.escapechar()));
    }

    /**
     * Setter for the arity.
     * 
     * @param arity the new arity
     */
    protected void setArity(int arity) {

        this.arity = arity;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.type.tokens.Tokens#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        int n = this.length();
        for (int i = 0; i < n; i++) {
            sb.append(get(i).toText());
        }
        return sb.toString();
    }

}
