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
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.util.OcpList;
import org.extex.unit.omega.ocp.util.OcpListBuilder;

/**
 * This class provides an implementation for the primitive <code>\ocplist</code>.
 * 
 * <doc name="ocplist">
 * <h3>The Primitive <tt>\ocplist</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ocplist&rang;
 *      &rarr; <tt>\ocplist</tt> ...  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 * \ocplist ... </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class OcplistPrimitive extends AbstractAssignment {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public OcplistPrimitive(String name) {

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
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        OcpList list = scanList(context, source, typesetter);
        context.setCode(cs, list, prefix.clearGlobal());
    }

    /**
     * This method collects the constituents of an &Omega;CP list and applies
     * them back to front. This is necessary to follow the resverse definition
     * in Omega.
     * 
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @return the list found
     * 
     * @throws HelpingException in case of an error
     */
    private OcpList scanList(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        CodeToken t = source.getControlSequence(context, typesetter);
        Code code = context.getCode(t);
        if (!(code instanceof OcpListBuilder)) {
            throw new HelpingException(getLocalizer(), "Message");
        }
        OcpList list;
        if (((OcpListBuilder) code).isTerminator()) {
            list = new OcpList("");
        } else {
            list = scanList(context, source, typesetter);
        }
        return ((OcpListBuilder) code).build(list, context, source, typesetter);
    }

}
