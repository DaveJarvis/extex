/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.scanner.type;

/**
 * This interface describes the capabilities for a visitor class on the category
 * codes.
 * This interface is used to implement the visitor pattern.
 *
 * @param <RET> the type of the return value 
 * @param <ARG1> the type of the first argument
 * @param <ARG2> the type of the second argument
 * @param <ARG3> the type of the third argument
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public interface CatcodeVisitor<RET, ARG1, ARG2, ARG3> {

    /**
     * This visit method is invoked on an active token.
     * In <logo>TeX</logo> this is e.g. ~.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitActive(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a comment token.
     * In <logo>TeX</logo> this normally is a %.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitComment(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a cr token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitCr(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on an escape token.
     * In <logo>TeX</logo> this normally means a control sequence.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitEscape(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on an ignore token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitIgnore(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on an invalid token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitInvalid(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a left brace token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitLeftBrace(ARG1 arg1, ARG2 arg2, ARG3 arg3)
            throws Exception;

    /**
     * This visit method is invoked on a letter token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitLetter(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a macro parameter token.
     * In <logo>TeX</logo> this normally is a #.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitMacroParam(ARG1 arg1, ARG2 arg2, ARG3 arg3)
            throws Exception;

    /**
     * This visit method is invoked on a math shift token.
     * In <logo>TeX</logo> this normally is a $.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitMathShift(ARG1 arg1, ARG2 arg2, ARG3 arg3)
            throws Exception;

    /**
     * This visit method is invoked on an other token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitOther(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a right brace token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitRightBrace(ARG1 arg1, ARG2 arg2, ARG3 arg3)
            throws Exception;

    /**
     * This visit method is invoked on a space token.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitSpace(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a sub mark token.
     * In <logo>TeX</logo> this normally is a _.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitSubMark(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a sup mark token.
     * In <logo>TeX</logo> this normally is a ^.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitSupMark(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

    /**
     * This visit method is invoked on a tab mark token.
     * In <logo>TeX</logo> this normally is a &.
     *
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     *
     * @return some value
     *
     * @throws Exception in case of an error
     */
    RET visitTabMark(ARG1 arg1, ARG2 arg2, ARG3 arg3) throws Exception;

}
