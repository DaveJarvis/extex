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

package org.extex.unit.tex.typesetter.paragraph;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\par</code>.
 * 
 * <doc name="par">
 * <h3>The Primitive <tt>\par</tt></h3>
 * <p>
 * The primitive <tt>\par</tt> signals the end of a paragraph. If <logo>ExTeX</logo>
 * is in a horizontal mode then the preceding material is typeset and the
 * paragraph is added to the vertical list. <logo>ExTeX</logo> goes into a
 * vertical mode afterwards.
 * </p>
 * <p>
 * If <logo>ExTeX</logo> is in a vertical mode then this primitive is simply
 * ignored.
 * </p>
 * <p>
 * The scanner rules of <logo>TeX</logo> determine that the macro <tt>\par</tt>
 * is inserted for any number of subsequent empty lines. This means that in a
 * normal text there might be a lot of invocations of <tt>\par</tt> even if
 * none of them is written explicitly.
 * </p>
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;par&rang;
 *      &rarr; <tt>\par</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    abc \par  def </pre>
 * 
 * </doc>
 * 
 * 
 * <doc name="parindent" type="register">
 * <h3>The Dimen Parameter <tt>\parindent</tt></h3>
 * <p>
 * The dimen parameter <tt>\parindent</tt> contains indentation to be applied
 * for each paragraph.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;parindent&rang;
 *      &rarr; <tt>\parindent</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantDimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen&rang;}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \parindent=12em  </pre>
 * 
 * </doc>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Par extends AbstractCode {

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
    public Par(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        typesetter.par();
    }

}
