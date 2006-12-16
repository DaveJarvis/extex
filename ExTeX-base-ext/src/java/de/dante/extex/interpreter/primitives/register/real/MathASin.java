/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.register.real;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.util.exception.GeneralException;

import de.dante.extex.interpreter.type.real.Real;
import de.dante.extex.interpreter.type.real.RealConvertible;

/**
 * Math. the arc sine of an angle, in the range of -pi/2 through pi/2.
 *
 * <p>Example</p>
 * <pre>
 * \the\mathasin 0.234
 * \real7=\mathasin 0.56
 * \real8=\mathasin\real7
 * \count99=\mathasin 1.34
 * </pre>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class MathASin extends AbstractMath
        implements
            Theable,
            RealConvertible,
            CountConvertible {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     * @throws GeneralException ...
     */
    public MathASin(final String name) throws GeneralException {

        super(name);

    }

    /**
     * Calculate
     * @param context   the context
     * @param source    the tokensource
     * @return  the real value
     * @throws InterpreterException if a error occoured
     */
    protected Real calculate(final Context context, final TokenSource source)
            throws InterpreterException {

        Real real = new Real(context, source);
        return new Real(Math.asin(real.getValue()));
    }
}
