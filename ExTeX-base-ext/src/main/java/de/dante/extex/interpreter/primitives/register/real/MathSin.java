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

package de.dante.extex.interpreter.primitives.register.real;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.Theable;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

import de.dante.extex.interpreter.type.real.Real;
import de.dante.extex.interpreter.type.real.RealConvertible;

/**
 * Math. Sinus.
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \the\mathsin 0.234
 * \real7=\mathsin 0.56
 * \real8=\mathsin\real7
 * \count99=\mathsin 1.34
 * </pre>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class MathSin extends AbstractMath
        implements
            Theable,
            RealConvertible,
            CountConvertible {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     * @throws GeneralException ...
     */
    public MathSin(String name) throws GeneralException {

        super(name);

    }

    /**
     * {@inheritDoc}
     * 
     * @see de.dante.extex.interpreter.primitives.register.real.AbstractMath#calculate(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    protected Real calculate(Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        Real real = new Real(context, source, typesetter);
        return new Real(Math.sin(real.getValue()));
    }

}
