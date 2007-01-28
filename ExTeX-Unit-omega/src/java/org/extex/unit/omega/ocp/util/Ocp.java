/*
 * Copyright (C) 2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp.util;

import java.io.InputStream;
import java.io.Serializable;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.Code;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.resource.ResourceFinder;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4411 $
 */
public class Ocp implements Code, OcpConvertible, Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2006;

    /**
     * This is a factory method for OCPs.
     *
     * @param resource the name of the resource
     * @param finder the resource finder
     *
     * @return the OCP encountered
     *
     * @throws InterpreterException in case of an error
     */
    public static Ocp load(final String resource, final ResourceFinder finder)
            throws InterpreterException {

        try {
            InputStream stream = finder.findResource(resource, "ocp");
        } catch (ConfigurationException e) {
            throw new InterpreterException(e);
        }
        return new Ocp(resource);
    }

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>ocp</tt> contains the ...
     */
    private Ocp ocp;

    /**
     * Creates a new object.
     *
     * @param resource the name of the resource
     */
    public Ocp(final String resource) {

        super();
        // TODO gene: load unimplemented
    }

    /**
     * @see org.extex.unit.omega.ocp.util.OcpConvertible#convertOcp(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Ocp convertOcp(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return ocp;
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

        //TODO gene: unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * @see org.extex.interpreter.type.Code#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * @see org.extex.interpreter.type.Code#isIf()
     */
    public boolean isIf() {

        return false;
    }

    /**
     * @see org.extex.interpreter.type.Code#isOuter()
     */
    public boolean isOuter() {

        return false;
    }

    /**
     * @see org.extex.interpreter.type.Code#setName(java.lang.String)
     */
    public void setName(final String name) {

        this.name = name;
    }

}
