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

package org.extex.interpreter.primitives.typesetter.box;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.CantUseInException;
import org.extex.interpreter.type.box.Box;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.AdjustNode;
import org.extex.unit.tex.register.box.AbstractBox;
import org.extex.util.framework.configuration.exception.ConfigurationException;


/**
 * This class provides an implementation for the primitive <code>\vadjust</code>.
 *
 * <doc name="vadjust">
 * <h3>The Primitive <tt>\vadjust</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;vadjust&rang;
 *       &rarr; <tt>\vadjust</tt> ... </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \vadjust{\kern2pt}  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Vadjust extends AbstractBox {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Vadjust(final String name) {

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

        Mode mode = typesetter.getMode();
        if (!mode.isHmode()) {
            throw new CantUseInException(printableControlSequence(context),
                    mode.toString());
        }
        Flags flags = prefix.copy();
        prefix.clear();
        Box b = new Box(context, source, typesetter, false, null,
                GroupType.VBOX_GROUP, source.getLastToken());

        try {
            typesetter.add(new AdjustNode(b.getNodes()));
        } catch (TypesetterException e) {
            throw new InterpreterException(e);
        } catch (ConfigurationException e) {
            throw new InterpreterException(e);
        }
        prefix.set(flags);
    }

}
