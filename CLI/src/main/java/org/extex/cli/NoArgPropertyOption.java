/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.cli;

import java.util.Properties;

import org.extex.cli.exception.UnknownOptionCliException;

/**
 * This option stores a predefined value in the properties given.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NoArgPropertyOption extends NoArgOption {

    /**
     * The field <tt>pname</tt> contains the name of the property.
     */
    private String pname;

    /**
     * The field <tt>properties</tt> contains the properties to store the
     * value in.
     */
    private Properties properties;

    /**
     * The field <tt>arg</tt> contains the value to store.
     */
    private String arg;

    /**
     * Creates a new object.
     * 
     * @param tag the name of the tag in the resource bundle for help
     * @param pname the name of the property to set
     * @param arg the value to set
     * @param properties the properties
     */
    public NoArgPropertyOption(String tag, String pname, String arg,
            Properties properties) {

        super(tag);
        this.pname = pname;
        this.properties = properties;
        this.arg = arg;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.cli.NoArgOption#run(java.lang.String)
     */
    @Override
    protected int run(String a) throws UnknownOptionCliException {

        properties.setProperty(pname, arg);
        return CLI.EXIT_CONTINUE;
    }

}
