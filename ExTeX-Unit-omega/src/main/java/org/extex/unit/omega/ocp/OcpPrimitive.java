/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.resource.ResourceConsumer;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.unit.omega.ocp.util.Ocp;

/**
 * This class provides an implementation for the primitive <code>\ocp</code>.
 *
 * <doc name="ocp">
 * <h3>The Primitive <tt>\ocp</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;ocp&rang;
 *      &rarr; <tt>\ocp</tt> ...  </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 * \ocp\abc=def </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class OcpPrimitive extends AbstractFileCode implements ResourceConsumer {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2006L;

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private transient ResourceFinder finder;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public OcpPrimitive(String name) {

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
            throws InterpreterException,
                ConfigurationException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        String file = scanFileName(context, source);
        context.setCode(cs, Ocp.load(file, finder), prefix.clearGlobal());
    }

    /**
     * Setter for the resource finder.
     *
     * @param resourceFinder the resource finder
     *
     * @see org.extex.resource.ResourceConsumer#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder resourceFinder) {

        this.finder = resourceFinder;
    }

}
