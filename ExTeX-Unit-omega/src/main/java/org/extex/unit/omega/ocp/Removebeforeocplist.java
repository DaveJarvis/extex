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

import org.extex.base.parser.ScaledNumberParser;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.ocpware.type.OcpProgram;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.util.OcpList;
import org.extex.unit.omega.ocp.util.OcpUtil;

/**
 * This class provides an implementation for the primitive
 * <code>\removebeforeocplist</code>.
 * 
 * <doc name="removebeforeocplist">
 * <h3>The Primitive <tt>\removebeforeocplist</tt></h3>
 * <p>
 * The primitive <tt>\removebeforeocplist</tt> can be used to build up an
 * &Omega;PC list. It is valid in the context of the primitive <tt>\ocplist</tt>
 * only.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;removebeforeocplist&rang;
 *      &rarr; <tt>\removebeforeocplist</tt> &lang;<i>float</i>&rang; &lang;<i>ocp register</i>&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 * \removebeforeocplist  1.5 \myopc </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Removebeforeocplist extends AbstractOcplist {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Removebeforeocplist(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.omega.ocp.util.OcplistConvertible#convertOcplist(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public OcpList convertOcplist(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        long scaled;
        try {
            scaled = ScaledNumberParser.parse(context, source, typesetter);
        } catch (TypesetterException e) {
            throw new NoHelpException(e);
        }
        OcpProgram prog =
                OcpUtil.scanOcpCode(context, source, typesetter,
                    toText(context));
        OcpList list = scanOcplist(context, source, typesetter);
        list.removeBefore(scaled, prog);
        return list;
    }

}
