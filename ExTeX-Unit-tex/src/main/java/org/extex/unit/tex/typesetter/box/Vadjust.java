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

package org.extex.unit.tex.typesetter.box;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.CantUseInException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.AdjustNode;

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
public class Vadjust extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Vadjust(String name) {

        super(name);
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException, ConfigurationException {

        Mode mode = typesetter.getMode();
        if (!mode.isHmode()) {
            throw new CantUseInException(printableControlSequence(context),
                mode.toString());
        }
        Flags flags = prefix.copy();
        prefix.clear();
        Box box =
                new Box(context, source, typesetter, false, null,
                    GroupType.VBOX_GROUP, source.getLastToken());

        typesetter.add(new AdjustNode(box.getNodes()));
        prefix.set(flags);
    }

}