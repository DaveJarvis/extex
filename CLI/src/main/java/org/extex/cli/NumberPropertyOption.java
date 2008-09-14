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

import java.util.List;
import java.util.Properties;

import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.NonNumericArgumentCliException;

/**
 * This class represents a base class for options with a number parameters.
 * <p>
 * This option can for instance be used to recognize command line parameters
 * with additional argument:
 * </p>
 * 
 * <pre>
 *     --abc 123
 * </pre>
 * 
 * <p>
 * or:
 * </p>
 * 
 * <pre>
 *     --abc=123
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NumberPropertyOption extends Option {

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
     * @param tag the tag for the description of the option
     * @param pname the name of the property to set
     * @param properties the properties
     */
    public NumberPropertyOption(String tag, String pname, Properties properties) {

        super(tag);
        this.pname = pname;
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.cli.Option#run(java.lang.String,
     *      java.util.List)
     */
    @Override
    public int run(String a, List<String> arg)
            throws MissingArgumentCliException,
                NonNumericArgumentCliException {

        if (arg.isEmpty()) {
            throw new MissingArgumentCliException(a);
        }
        return run(a, arg.remove(0), arg);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.cli.Option#run(java.lang.String,
     *      java.lang.String, java.util.List)
     */
    @Override
    public int run(String a, String firstArg, List<String> arg)
            throws NonNumericArgumentCliException {

        try {
            Integer.parseInt(firstArg);
        } catch (NumberFormatException e) {
            throw new NonNumericArgumentCliException(a, firstArg);
        }
        properties.setProperty(pname, firstArg);
        return CLI.EXIT_CONTINUE;
    }

}
