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
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.util.OcpList;
import org.extex.unit.omega.ocp.util.OcplistConvertible;
import org.extex.unit.omega.ocp.util.OmegaOcpException;

/**
 * This is the abstract base class for &Omega;CP lists.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractOcplist extends AbstractCode
        implements
            OcplistConvertible {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Scan an &Omega;CP list.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the &Omega;CP list
     *
     * @throws HelpingException in case of an error
     */
    public static OcpList scanOcplist(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        CodeToken t = source.getControlSequence(context, typesetter);
        Code code = context.getCode(t);
        if (!(code instanceof OcplistConvertible)) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(AbstractOcplist.class), "Message");
        }
        OcpList list =
                ((OcplistConvertible) code).convertOcplist(context, source,
                    typesetter);
        return list;
    }

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public AbstractOcplist(String name) {

        super(name);
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

}
