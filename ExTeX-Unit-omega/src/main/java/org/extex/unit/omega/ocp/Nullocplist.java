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
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Showable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.util.OcpList;
import org.extex.unit.omega.ocp.util.OcpListBuilder;
import org.extex.unit.omega.ocp.util.OcpListBuilderCommand;
import org.extex.unit.omega.ocp.util.OmegaOcpException;

/**
 * This class provides an implementation for the primitive
 * <code>\nullocplist</code>.
 * 
 * <doc name="nullocplist">
 * <h3>The Primitive <tt>\nullocplist</tt></h3>
 * <p>
 * The primitive <tt>\nullocplist</tt> can be used to build up an &Omega;PC
 * list. It is valid in the context of the primitive <tt>\ocplist</tt> only.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;nullocplist&rang;
 *      &rarr; <tt>\nullocplist</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 * \nullocplist </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Nullocplist extends AbstractCode
        implements
            Showable,
            OcpListBuilder {

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
    public Nullocplist(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.omega.ocp.util.OcpListBuilder#build(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public OcpListBuilderCommand build(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        return new OcpListBuilderCommand() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.unit.omega.ocp.util.OcpListBuilderCommand#apply(
             *      org.extex.unit.omega.ocp.util.OcpList)
             */
            public OcpList apply(OcpList list) {

                return new OcpList("");
            }
        };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        throw new OmegaOcpException(getName());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.omega.ocp.util.OcpListBuilder#isTerminator()
     */
    public boolean isTerminator() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Showable#show(
     *      org.extex.interpreter.context.Context)
     */
    public Tokens show(Context context) throws HelpingException {

        try {
            return context.getTokenFactory().toTokens("select ocp list ");
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
