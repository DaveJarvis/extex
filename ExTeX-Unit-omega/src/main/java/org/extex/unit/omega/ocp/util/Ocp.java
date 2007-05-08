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

package org.extex.unit.omega.ocp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

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
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     */
    public static Ocp load(String resource, ResourceFinder finder)
            throws HelpingException,
                ConfigurationException {

        InputStream stream = null;
        try {
            stream = finder.findResource(resource, "ocp");
            return new Ocp(resource, stream);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignored on purpose
                }
            }
        }
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
     * @param stream the stream to read the resource from
     */
    public Ocp(String resource, InputStream stream) {

        super();
        // TODO gene: load unimplemented
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.omega.ocp.util.OcpConvertible#convertOcp(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Ocp convertOcp(Context context, TokenSource source,
            Typesetter typesetter) {

        return ocp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        // TODO gene: unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * Getter for the name.
     * 
     * @return the name
     * 
     * @see org.extex.interpreter.type.Code#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * This simple little method distinguishes the conditionals from the other
     * primitives. This is necessary for the processing of all \if* primitives.
     * 
     * @return <code>true</code> iff this is some sort if <tt>\if</tt>.
     * 
     * @see org.extex.interpreter.type.Code#isIf()
     */
    public boolean isIf() {

        return false;
    }

    /**
     * Getter for the outer flag.
     * 
     * @return <code>true</code> iff the code is defined outer.
     * 
     * @see org.extex.interpreter.type.Code#isOuter()
     */
    public boolean isOuter() {

        return false;
    }

    /**
     * Setter for the name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

}
