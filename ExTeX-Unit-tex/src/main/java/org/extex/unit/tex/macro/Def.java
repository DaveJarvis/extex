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

package org.extex.unit.tex.macro;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Code;
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
import org.extex.unit.tex.macro.util.MacroCode;
import org.extex.unit.tex.macro.util.MacroPattern;
import org.extex.unit.tex.macro.util.ProtectedMacroCode;

/**
 * This class provides an implementation for the primitive <code>\def</code>.
 * 
 * <doc name="def">
 * <h3>The Primitive <tt>\def</tt></h3>
 * <p>
 * The primitive <tt>\def</tt> defines a new macro. The macro is assigned to a
 * control sequence or an active character.
 * </p>
 * <p>
 * A macro has a body with the expansion tokens. Whenever the macro is expanded
 * the tokens from the body are used.
 * </p>
 * <p>
 * A macro can have a non-trivial pattern for matching arguments.
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;def&rang;
 *       &rarr; &lang;prefix&rang; <tt>\def</tt> {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;} &lang;parameter text&rang; <tt>{</tt> &lang;replacement text&rang; <tt>}</tt>
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       | <tt>\global</tt> &lang;prefix&rang;
 *       | <tt>\long</tt> &lang;prefix&rang;
 *       | <tt>\outer</tt> &lang;prefix&rang;
 *       | <tt>\protected</tt> &lang;prefix&rang;</pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \def\a#1{--#1--}  </pre>
 *  <pre class="TeXSample">
 *    \def\a#1#{--#1--}  </pre>
 *  <pre class="TeXSample">
 *    \def\a#1#2{--#2--#1--}  </pre>
 *  <pre class="TeXSample">
 *    \def\a#1:#2.{--#2--#1--}  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Def extends AbstractAssignment {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Def(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        MacroPattern pattern = getPattern(context, source);
        String csName = printable(context, cs);
        Tokens body;
        try {
            body =
                    prefix.clearExpanded() //
                            ? source.scanUnprotectedTokens(context, false,
                                false, getName())//
                            : source.getTokens(context, source, typesetter);
            // TODO gene: maybe the treatment of # is incorrect
        } catch (EofException e) {
            throw new EofInToksException(csName);
        }

        MacroCode macroCode = (prefix.clearProtected() //
                ? new ProtectedMacroCode(csName, prefix, pattern, body) //
                : new MacroCode(csName, prefix, pattern, body));

        context.setCode(cs, macroCode, prefix.clearGlobal());
    }

    /**
     * Parse the pattern.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * 
     * @return the tokens read
     * 
     * @throws HelpingException in case of an error
     */
    protected MacroPattern getPattern(Context context, TokenSource source)
            throws HelpingException {

        MacroPattern pattern = new MacroPattern();
        int no = 1;
        boolean afterHash = false;

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {

            if (t instanceof LeftBraceToken) {
                source.push(t);
                pattern.setArity(no);
                return pattern;
            } else if (t instanceof RightBraceToken) {
                throw new HelpingException(getLocalizer(),
                    "TTP.MissingLeftDefBrace",
                    printableControlSequence(context));
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code == null) {
                    // undefined
                } else if (code.isOuter()) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.OuterInDef", printableControlSequence(context));
                } else if (code instanceof LetCode) {
                    t = ((LetCode) code).getToken();
                }
            }

            if (afterHash) {
                if (t instanceof OtherToken) {
                    if (t.getChar().getCodePoint() != '0' + no) {
                        throw new HelpingException(getLocalizer(),
                            "TTP.NonConsequtiveParams",
                            printableControlSequence(context));
                    }
                    no++;
                } else if (!(t instanceof MacroParamToken)) {
                    throw new HelpingException(getLocalizer(),
                        "TTP.NonConsequtiveParams",
                        printableControlSequence(context));
                }
                afterHash = false;
            } else {
                afterHash = (t instanceof MacroParamToken);
            }
            pattern.add(t);
        }

        throw new HelpingException(getLocalizer(), "TTP.EOFinDef",
            printableControlSequence(context));
    }

}
