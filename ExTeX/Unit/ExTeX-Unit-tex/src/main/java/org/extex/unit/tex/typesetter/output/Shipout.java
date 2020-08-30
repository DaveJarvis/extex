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

package org.extex.unit.tex.typesetter.output;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \shipout}
 * .
 * 
 * <p>The Primitive {@code \shipout}</p>
 * <p>
 * The primitive {@code \shipout} takes a box and send the contents of the box
 * to the back-end. If the box is void then this primitives ignores it and does
 * not contact the back-end.
 * </p>
 * <p>
 * In addition the count register {@code \deadcyles} is reset to 0. This count
 * register is used to break out of infinite loops when no material is shipped
 * out in the output routine.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;shipout&rang;
 *      &rarr; {@code \shipout} {@linkplain
 *        org.extex.interpreter.TokenSource#getBox(Flags,Context,Typesetter, org.extex.scanner.type.token.Token)
 *        &lang;box&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \shipout\box255  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Shipout extends AbstractCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * @param token the initial token for the primitive
     */
    public Shipout(CodeToken token) {

        super(token);
    }

    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Box box = source.getBox(prefix, context, typesetter, null);

        if (box != null && !box.isVoid()) {
            typesetter.shipout(box.getNodes());
            context.setCount("deadcyles", 0, true);
        }
    }

}
