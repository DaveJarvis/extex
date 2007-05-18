/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.skip;

import org.extex.base.parser.MuskipParser;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.GlueConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\mutoglue</code>.
 * 
 * <doc name="mutoglue">
 * <h3>The Primitive <tt>\mutoglue</tt></h3>
 * <p>
 * The primitive <tt>\mutoglue</tt> converts a muglue specification to a glue
 * specification. For this conversion 1mu=1pt is assumed. This primitive can be
 * used wherever a glue is expected.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mutoglue&rang;
 *      &rarr; <tt>\mutoglue</tt> &lang;muglue&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \skip0=\mutoglue1mu  </pre>
 *  <pre class="TeXSample">
 *    \skip0=\glueexpr\mutoglue1mu\relax  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Mutoglue extends AbstractCode implements GlueConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060513L;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public Mutoglue(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.GlueConvertible#convertGlue(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Glue convertGlue(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Muskip muskip = MuskipParser.parse(context, source, typesetter);

        return new Glue(muskip.getLength(), muskip.getStretch(), muskip
            .getShrink());
    }

}
