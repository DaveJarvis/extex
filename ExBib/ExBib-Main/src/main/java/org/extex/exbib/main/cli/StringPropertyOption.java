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

package org.extex.exbib.main.cli;

import java.util.Properties;

import org.extex.exbib.main.cli.exception.UnknownOptionCliException;

/**
 * This option takes a string argument and stores it in a property.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StringPropertyOption extends StringOption {

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
     * Creates a new object.
     * 
     * @param tag the name of the tag in the resource bundle for help
     * @param pname the name of the property to set
     * @param properties the properties
     */
    public StringPropertyOption(String tag, String pname, Properties properties) {

        super(tag);
        this.pname = pname;
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.cli.StringOption#run(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected int run(String a, String arg) throws UnknownOptionCliException {

        properties.setProperty(pname, arg);
        return CLI.EXIT_CONTINUE;
    }

    /**
     * Setter for the property.
     * 
     * @param arg the value
     */
    protected void set(String arg) {

        properties.setProperty(pname, arg);
    }

}
