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
import org.extex.util.framework.configuration.exception.ConfigurationException;

import de.dante.extex.interpreter.type.real.Real;
import de.dante.extex.interpreter.type.real.RealConvertible;

/**
 * Math. Euler's number e raised to the power of a real value.
 *
 * <p>Example</p>
 * <pre>
 * \the\mathexp 0.234
 * \real7=\mathexp 0.56
 * \real8=\mathexp\real7
 * \count99=\mathexp 1.34
 * </pre>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class MathExp extends AbstractMath
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
    public MathExp(final String name) throws GeneralException {

        super(name);

    }

    /**
     * Calculate
     * @param context   the context
     * @param source    the token source
     *
     * @return  the real-value
     *
     * @throws GeneralException if a error occurred
     * @throws ConfigurationException in case of an configuration error
     */
    protected Real calculate(final Context context, final TokenSource source)
            throws InterpreterException,
                ConfigurationException {

        Real real = new Real(context, source);
        return new Real(Math.exp(real.getValue()));
    }
}
