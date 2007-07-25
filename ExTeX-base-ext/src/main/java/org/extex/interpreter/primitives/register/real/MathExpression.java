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

package org.extex.interpreter.primitives.register.real;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.Theable;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * Math. Expressions to get a real-value. It use the JEval
 * {@link http://jeval.sourceforge.net/}.
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \mathexpr{2*7}
 * \real7=\mathexpr{7+4-2*3}
 * \count99=\mathexpr{7+4-2*3}
 * </pre>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class MathExpression extends AbstractMath
        implements
            Theable,
            RealConvertible,
            CountConvertible {

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     * @throws GeneralException if an error occurred.
     */
    @SuppressWarnings("unchecked")
    public MathExpression(String name) throws GeneralException {

        super(name);

        evaluator = new Evaluator();

    }

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer =
            LocalizerFactory.getLocalizer(MathExpression.class);

    /**
     * JEval Evaluator.
     */
    private Evaluator evaluator;

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.primitives.register.real.AbstractMath#calculate(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    protected Real calculate(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Real real = new Real(0);

        // \mathexpr{7+5+3}
        String expr = source.scanTokensAsString(context, getName());

        try {
            String result = evaluator.evaluate(expr);
            if (result != null) {
                real.setValue(Double.parseDouble(result));
            }
        } catch (EvaluationException e) {

            throw new HelpingException(localizer, "MathExpr.error", e
                .getLocalizedMessage());

        }

        return real;
    }

}
