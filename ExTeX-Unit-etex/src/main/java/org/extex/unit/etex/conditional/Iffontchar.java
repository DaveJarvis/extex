/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional;

import org.extex.core.UnicodeChar;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.font.Font;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive
 * <code>\iffontchar</code>.
 * 
 * <doc name="iffontchar">
 * <h3>The Primitive <tt>\iffontchar</tt></h3>
 * <p>
 * The primitive <tt>\iffontchar</tt> can be used to check whether a certain
 * glyph exists in a font. For this purpose it takes a font and the code of a
 * character and performs the test. If the character exists the then branch is
 * expanded otherwise the else branch.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;iffontchar&rang;
 *      &rarr; <tt>\iffontchar</tt> ... &lang;true text&rang; <tt>\fi</tt>
 *      | <tt>\iffontchar</tt> ... &lang;true text&rang; <tt>\else</tt> &lang;false text&rang; <tt>\fi</tt> </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \iffontchar abc \fi  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Iffontchar extends AbstractIf {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Iffontchar(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.conditional.AbstractIf#conditional(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Font font = source.getFont(context, getName());
        UnicodeChar uc =
                source.scanCharacterCode(context, typesetter, getName());
        return font.hasGlyph(uc);
    }

}
