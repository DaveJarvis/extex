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

package org.extex.unit.omega.typesetter;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueComponent;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.typesetter.AbstractHorizontalCode;
import org.extex.unit.tex.typesetter.spacing.HorizontalSkip;

/**
 * This class provides an implementation for the primitive <code>\hfi</code>.
 * 
 * <doc name="hfi">
 * <h3>The Primitive <tt>\hfi</tt></h3>
 * <p>
 * The primitive <tt>\hfi</tt> inserts a new infinite glue into the output.
 * The value of <tt>\hfi</tt> is an infinite quantity which is less than
 * <tt>\hfil</tt>. This means that <tt>\hfil</tt> or a larger value
 * suppress this glue. On the other side if no greater value is present then
 * this value suppresses any finite value.
 * </p>
 * <p>
 * This quantity has been introduced by <logo>Omega</logo>.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;hfi&rang;
 *        &rarr; <tt>\hfi</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \hfi  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Hfi extends AbstractHorizontalCode implements HorizontalSkip {

    /**
     * The field <tt>FIL</tt> contains the glue to insert for this primitive.
     */
    private static final Glue FI =
            new Glue(GlueComponent.ZERO, GlueComponent.ONE_FI,
                GlueComponent.ZERO);

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Hfi(String name) {

        super(name);
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

        switchToHorizontalMode(typesetter);
        typesetter.add(FI);
    }

    /**
     * This method acquires horizontal glue.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the amount of skip
     * 
     * @see org.extex.unit.tex.typesetter.spacing.HorizontalSkip#getGlue(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public FixedGlue getGlue(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return FI;
    }

}
