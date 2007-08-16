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

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.util.Ocp;
import org.extex.unit.omega.ocp.util.OcpUtil;

/**
 * This class provides an implementation for the primitive <code>\ocp</code>.
 * 
 * <doc name="ocp">
 * <h3>The Primitive <tt>\ocp</tt></h3>
 * <p>
 * The primitive <tt>\ocp</tt> can be used to define a control sequence which
 * holds an &Omega;CP program.
 * </p>
 * <p>
 * This primitive is an assignment. This means that the value of
 * <tt>\globaldefs</tt> and <tt>\afterassignment</tt> are taken into
 * account.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ocp&rang;
 *      &rarr; &lang;prefix&rang; <tt>\ocp</tt> {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;}  {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} &lang;resource name&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 * \ocp\abc=def </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class OcpPrimitive extends AbstractAssignment implements ResourceAware {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws TypesetterException, HelpingException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        String resource =
                OcpUtil.scanOcpFileName(context, source,
                    printableControlSequence(context));
        Ocp ocp = Ocp.load(resource, finder);
        if (ocp == null) {
            throw new HelpingException(getLocalizer(), "message", resource);
        }
        context.setCode(cs, ocp, prefix.clearGlobal());
    }

    /**
     * Setter for the resource finder.
     * 
     * @param resourceFinder the resource finder
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder resourceFinder) {

        this.finder = resourceFinder;
    }

}
