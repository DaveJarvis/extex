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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueComponent;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.typesetter.AbstractVerticalCode;

/**
 * This class provides an implementation for the primitive <code>\vfil</code>.
 * 
 * <doc name="vfil">
 * <h3>The Primitive <tt>\vfil</tt></h3>
 * <p>
 * The primitive <tt>\vfill</tt> inserts vertical glue into the current list.
 * It switches to vertical mode if necessary. The amount of glue inserted has
 * the natural height of 0pt and a stretchability of 1fil.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;vfil&rang;
 *        &rarr; <tt>\vfil</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \vfil  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Vfil extends AbstractVerticalCode implements VerticalSkip {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>FIL</tt> contains the amount of 1 fil.
     */
    private static final Glue FIL =
            new Glue(Dimen.ZERO, GlueComponent.ONE_FIL, GlueComponent.ZERO);

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Vfil(String name) {

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

        ensureVerticalMode(typesetter);
        typesetter.add(FIL);
    }

    /**
     * This method acquires a vertical glue.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the amount of vertical skip
     * 
     * @see org.extex.unit.tex.typesetter.spacing.VerticalSkip#getGlue(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public FixedGlue getGlue(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return FIL;
    }

}
