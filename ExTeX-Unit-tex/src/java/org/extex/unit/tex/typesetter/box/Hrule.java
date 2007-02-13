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

package org.extex.unit.tex.typesetter.box;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.RuleConvertible;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive <code>\hrule</code>.
 *
 * <doc name="hrule">
 * <h3>The Primitive <tt>\hrule</tt></h3>
 * <p>
 *  This primitive produces a horizontal rule. This is a rectangular area of
 *  specified dimensions. If not overwritten the width and depth are 0pt and
 *  the height is 0.4&nbsp;pt (26214&nbsp;sp).
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;hrule&rang;
 *        &rarr; <tt>\hrule</tt> &lang;rule specification&rang;
 *
 *    &lang;rule specification&rang;
 *        &rarr; {@linkplain
 *            org.extex.interpreter.TokenSource#skipSpace()
 *            &lang;optional&nbsp;spaces&rang;}
 *         |  &lang;rule dimension&rang; &lang;rule specification&rang;
 *
 *    &lang;rule dimension&rang;
 *        &rarr; <tt>width</tt> {@linkplain
 *        org.extex.interpreter.type.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}
 *         |  <tt>height</tt> {@linkplain
 *        org.extex.interpreter.type.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}
 *         |  <tt>depth</tt> {@linkplain
 *        org.extex.interpreter.type.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}   </pre>
 *
 * <p>
 *  The color from the typographic context is taken as foreground color for the
 *  rule. The default color is black.
 * </p>
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \hrule  </pre>
 *  <pre class="TeXSample">
 *    \hrule width 2pt  </pre>
 *  <pre class="TeXSample">
 *    \hrule width 2pt depth 3mm height \dimen4  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Hrule extends AbstractCode implements RuleConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The constant <tt>DEFAULT_RULE</tt> contains the equivalent to 0.4pt.
     */
    private static final long DEFAULT_RULE = 26214;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Hrule(final String name) {

        super(name);
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     * @see "<logo>TeX</logo> &ndash; The Program [463]"
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        try {
            typesetter.add(getRule(context, source, typesetter));
        } catch (ConfigurationException e) {
            throw new InterpreterException(e);
        }
    }

    /**
     * @see org.extex.interpreter.type.box.RuleConvertible#getRule(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public RuleNode getRule(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        Mode mode = typesetter.getMode();
        if (mode.isHmode()) {
            try {
                typesetter.par();
                //            throw new HelpingException(getLocalizer(), "TTP.CantUseHrule",
                //                    printableControlSequence(context));
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
        }
        Dimen width = new Dimen(0);
        Dimen height = new Dimen(DEFAULT_RULE);
        Dimen depth = new Dimen(0);

        for (;;) {
            if (source.getKeyword(context, "width")) {
                width = Dimen.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "height")) {
                height = Dimen.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "depth")) {
                depth = Dimen.parse(context, source, typesetter);
            } else {
                break;
            }
        }

        return new RuleNode(width, height, depth, context
            .getTypesettingContext(), true);
    }

}
