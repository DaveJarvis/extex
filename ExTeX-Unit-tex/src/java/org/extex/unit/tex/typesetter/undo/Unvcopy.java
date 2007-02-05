/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter.undo;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.box.Box;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.NodeList;
import org.extex.unit.tex.register.box.AbstractBox;
import org.extex.util.framework.configuration.exception.ConfigurationException;


/**
 * This class provides an implementation for the primitive
 * <code>&#x5c;unvcopy</code>.
 *
 * <doc name="unvcopy">
 * <h3>The Primitive <tt>&#x5c;unvcopy</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;unvcopy&rang;
 *       &rarr; <tt>&#x5c;unvcopy</tt> {@linkplain
 *          org.extex.interpreter.TokenSource#scanNumber(Context)
 *          &lang;8-bit&nbsp;number&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    &#x5c;unvcopy42  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Unvcopy extends AbstractBox {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Unvcopy(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter, getName());
        Box b = context.getBox(key);

        if (b == null || b.isVoid()) {
            // nothing to do
        } else if (b.isVbox()) {
            NodeList nl = b.getNodes();
            try {
                for (int i = 0; i < nl.size(); i++) {
                    typesetter.add(nl.get(i));
                }
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
        } else {
            throw new HelpingException(getLocalizer(), "TTP.IncompatibleUnbox");
        }
    }

}
