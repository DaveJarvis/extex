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

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.RuleConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.RuleNode;

/**
 * This class provides an implementation for the primitive <code>\vrule</code>.
 * 
 * <doc name="vrule">
 * <h3>The Primitive <tt>\vrule</tt></h3>
 * <p>
 * This primitive produces a vertical rule. This is a rectangular area of
 * specified dimensions. If not overwritten the height and depth are 0pt and the
 * width is 0.4&nbsp;pt (26214&nbsp;sp).
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;vrule&rang;
 *        &rarr; <tt>\vrule</tt>&lang;rule specification&rang;
 *
 *    &lang;rule specification&rang;
 *        &rarr; {@linkplain org.extex.interpreter.TokenSource#skipSpace()
 *            &lang;optional&nbsp;spaces&rang;}
 *         |  &lang;rule dimension&rang; &lang;rule specification&rang;
 *
 *    &lang;rule dimension&rang;
 *        &rarr; <tt>width</tt> {@linkplain
 *        org.extex.core.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}
 *         |  <tt>height</tt> {@linkplain
 *        org.extex.core.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}
 *         |  <tt>depth</tt> {@linkplain
 *        org.extex.core.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}   </pre>
 * 
 * <p>
 * The color from the typographic context is taken as foreground color for the
 * rule. The default color is black.
 * </p>
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \vrule  </pre>
 *  <pre class="TeXSample">
 *    \vrule height 2pt  </pre>
 *  <pre class="TeXSample">
 *    \vrule width 2pt depth 3mm height \dimen4  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Vrule extends AbstractCode implements RuleConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The constant <tt>DEFAULT_RULE</tt> contains the equivalent to 0.4pt.
     */
    private static final long DEFAULT_RULE = 26214;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Vrule(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        typesetter.add(getRule(context, source, typesetter));
    }

    /**
     * Getter for the content as Rule.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use
     * 
     * @return an appropriate Box
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.box.RuleConvertible#getRule(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public RuleNode getRule(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Mode mode = typesetter.getMode();
        if (mode.isVmode()) {
            typesetter.par();
        }
        Dimen width = new Dimen(DEFAULT_RULE);
        Dimen height = new Dimen(0);
        Dimen depth = new Dimen(0);

        for (;;) {
            if (source.getKeyword(context, "width")) {
                width = source.parseDimen(context, source, typesetter);
            } else if (source.getKeyword(context, "height")) {
                height = source.parseDimen(context, source, typesetter);
            } else if (source.getKeyword(context, "depth")) {
                depth = source.parseDimen(context, source, typesetter);
            } else {
                break;
            }
        }

        return new RuleNode(width, height, depth, context
            .getTypesettingContext(), false);
    }

}
