/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \par}.
 * 
 * <p>The Primitive {@code \par}</p>
 * <p>
 * The primitive {@code \par} signals the end of a paragraph. If
 * εχTeX is in a horizontal mode then the preceding material is
 * typeset and the paragraph is added to the vertical list.
 * εχTeX goes into a vertical mode afterwards.
 * </p>
 * <p>
 * If εχTeX is in a vertical mode then this primitive is simply
 * ignored.
 * </p>
 * <p>
 * The scanner rules of TeX determine that the macro {@code \par} is inserted for any
 * number of subsequent empty lines. This means that in a normal text there
 * might be a lot of invocations of {@code \par} even if none of them is
 * written explicitly.
 * </p>
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;par&rang;
 *      &rarr; {@code \par}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    abc \par  def </pre>
 * 
 *
 * 
 * <p>The Dimen Parameter {@code \parindent}</p>
 * <p>
 * The dimen parameter {@code \parindent} contains indentation to be applied
 * for each paragraph.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;parindent&rang;
 *      &rarr; {@code \parindent} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantDimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \parindent=12em  </pre>
 * 
 *
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Par extends AbstractCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Par(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        typesetter.par();
    }

}
