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

package org.extex.unit.tex.conditional;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.file.InFile;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.conditional.AbstractIf;
import org.extex.unit.base.file.AbstractFileCode;

/**
 * This class provides an implementation for the primitive <code>\ifeof</code>.
 *
 * <doc name="ifeof">
 * <h3>The Primitive <tt>\ifeof</tt></h3>
 * <p>
 *  This primitive tests for end of file on the given read register. The read
 *  register is specified as a (expanded) number.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;ifeof&rang;
 *      &rarr; <tt>\ifeof</tt> {@linkplain
 *        org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;} &lang;true text&rang; <tt>\fi</tt>
 *      | <tt>\ifeof</tt> {@linkplain
 *        org.extex.core.count.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;} &lang;true text&rang; <tt>\else</tt> &lang;false text&rang; <tt>\fi</tt> </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \ifeof 3 -E-O-F- \else ready \fi  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4439 $
 */
public class Ifeof extends AbstractIf {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Ifeof(String name) {

        super(name);
    }

    /**
     * This method computes the boolean value of the conditional.
     * If the result is <code>true</code> then the then branch is expanded and
     * the else branch is skipped. Otherwise the then branch is skipped and the
     * else branch is expanded.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the boolean value
     *
     * @throws InterpreterException in case of en error
     *
     * @see org.extex.unit.base.conditional.AbstractIf#conditional(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        String key =
                AbstractFileCode.scanInFileKey(context, source, typesetter);

        InFile inFile = context.getInFile(key);
        if (inFile == null) {
            return true;
        }
        return inFile.isEof();
    }

}