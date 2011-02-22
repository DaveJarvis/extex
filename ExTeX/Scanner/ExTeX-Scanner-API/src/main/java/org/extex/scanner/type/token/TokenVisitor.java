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

package org.extex.scanner.type.token;

/**
 * This interface describes the capabilities for a visitor class on the token
 * types. This interface is used to implement the visitor pattern.
 * 
 * 
 * @param <RET_TYPE> the return type
 * @param <ARG_TYPE> the argument type
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface TokenVisitor<RET_TYPE, ARG_TYPE> {

    /**
     * This visit method is invoked on an active token. In <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this is e.g. ~.
     * 
     * @param token the active token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitActive(ActiveCharacterToken token, ARG_TYPE arg)
            throws Exception;

    /**
     * This visit method is invoked on a cr token.
     * 
     * @param token the cr token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitCr(CrToken token, ARG_TYPE arg) throws Exception;

    /**
     * This visit method is invoked on an escape token. In <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this normally means a control sequence.
     * 
     * @param token the control sequence token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitEscape(ControlSequenceToken token, ARG_TYPE arg)
            throws Exception;

    /**
     * This visit method is invoked on a left brace token.
     * 
     * @param token the left brace token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitLeftBrace(LeftBraceToken token, ARG_TYPE arg)
            throws Exception;

    /**
     * This visit method is invoked on a letter token.
     * 
     * @param token the letter token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitLetter(LetterToken token, ARG_TYPE arg) throws Exception;

    /**
     * This visit method is invoked on a macro parameter token. In <logo>T<span
     * style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this normally is a #.
     * 
     * @param token the macro param token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitMacroParam(MacroParamToken token, ARG_TYPE arg)
            throws Exception;

    /**
     * This visit method is invoked on a math shift token. In <logo>T<span
     * style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this normally is a $.
     * 
     * @param token the math shift token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitMathShift(MathShiftToken token, ARG_TYPE arg)
            throws Exception;

    /**
     * This visit method is invoked on an other token.
     * 
     * @param token the other token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitOther(OtherToken token, ARG_TYPE arg) throws Exception;

    /**
     * This visit method is invoked on a right brace token.
     * 
     * @param token the right brace token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitRightBrace(RightBraceToken token, ARG_TYPE arg)
            throws Exception;

    /**
     * This visit method is invoked on a space token.
     * 
     * @param token the space token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitSpace(SpaceToken token, ARG_TYPE arg) throws Exception;

    /**
     * This visit method is invoked on a sub mark token. In <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this normally is a _.
     * 
     * @param token the sub mark token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitSubMark(SubMarkToken token, ARG_TYPE arg) throws Exception;

    /**
     * This visit method is invoked on a sup mark token. In <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this normally is a ^.
     * 
     * @param token the sup mark token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitSupMark(SupMarkToken token, ARG_TYPE arg) throws Exception;

    /**
     * This visit method is invoked on a tab mark token. In <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> this normally is a &.
     * 
     * @param token the tab mark token to visit
     * @param arg the first argument to pass
     * 
     * @return some value
     * 
     * @throws Exception in case of an error
     */
    RET_TYPE visitTabMark(TabMarkToken token, ARG_TYPE arg) throws Exception;

}
