/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.NonNumericArgumentCliException;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.cli.exception.UnusedArgumentCliException;

/**
 * This abstract base class describes the closure for an option.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class Option {

    /**
     * The field <tt>tag</tt> contains the the tag for the description of the
     * option.
     */
    private final String tag;

    /**
     * Creates a new object.
     * 
     * @param tag the tag for the description of the option
     */
    public Option(String tag) {

        this.tag = tag;
    }

    /**
     * Getter for the tag for the description of the option.
     * 
     * @return the tag
     */
    String getTag() {

        return tag;
    }

    /**
     * Do whatever the option requires to be done.
     * 
     * @param a the name the option has actually used
     * @param arg the list of all remaining arguments
     * 
     * @return the exit code
     * 
     * @throws MissingArgumentCliException in case of a missing argument
     * @throws NonNumericArgumentCliException in case of a non-numeric value for
     *         a numeric option
     * @throws UnknownOptionCliException just in case
     */
    public abstract int run(String a, List<String> arg)
            throws MissingArgumentCliException,
                NonNumericArgumentCliException,
                UnknownOptionCliException;

    /**
     * Do whatever the option requires to be done.
     * 
     * @param a the name the option has actually used
     * @param firstArg the first argument already cut off
     * @param arg the list of all remaining arguments
     * 
     * @return the exit code
     * 
     * @throws UnusedArgumentCliException in case of an superfluous argument
     * @throws NonNumericArgumentCliException in case of a non-numeric value for
     *         a numeric option
     * @throws UnknownOptionCliException just in case
     */
    public abstract int run(String a, String firstArg, List<String> arg)
            throws UnusedArgumentCliException,
                NonNumericArgumentCliException,
                UnknownOptionCliException;
}
