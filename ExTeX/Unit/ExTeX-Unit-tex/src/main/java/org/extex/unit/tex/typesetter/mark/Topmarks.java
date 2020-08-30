/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter.mark;

import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class provides an implementation for the primitive
 * {@code \topmarks}.
 * 
 * <p>The Primitive {@code \topmarks}</p>
 * <p>
 * The primitive {@code \topmarks} provides access to the topmost mark of the
 * given class encountered on the current page &ndash; when processing the page
 * in the output routine.
 * </p>
 * <p>
 * The primitive is a tokens register. It can be used wherever a tokens value is
 * expected. The value is empty when no top mark of the class is available yet.
 * </p>
 * <p>
 * The primitive {@code \topmark} is an abbreviation for {@code \topmarks0}.
 * </p>
 * <p>
 * The class of the top mark is determined when the {@code \marks} primitive is
 * used to insert a mark into the current list. In TeX the class used to be a number in the range 0&ndash;255; the
 * limit has been raised in ε-TeX to 32767 and in εχTeX to allow any number or tokens value.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;topmarks&rang;
 *      &rarr; {@code \topmarks} {@linkplain
 *        org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getKey(
 *        org.extex.interpreter.context.Context,
 *        org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 *        &lang;mark name&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \topmarks42  </pre>
 * 
 * <pre class="TeXSample">
 *    \topmarks{abc}  </pre>
 * 
 * <pre class="TeXSample">
 *    \topmarks\count0  </pre>
 * 
 * <pre class="TeXSample">
 *    \toks0=\topmark3  </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Topmarks extends AbstractMarksCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Topmarks(CodeToken token) {

        super(token);
    }

    /**
     * Get the value for this mark.
     * 
     * @param context the interpreter context
     * @param key the key
     * 
     * @return the value
     * 
     * @see org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getValue(org.extex.interpreter.context.Context,
     *      java.lang.String)
     */
    @Override
    protected Tokens getValue(Context context, String key) {

        return context.getTopMark(key);
    }

}
