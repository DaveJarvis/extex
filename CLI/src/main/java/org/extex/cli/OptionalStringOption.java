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

import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.UnknownOptionCliException;

/**
 * This class represents a base class for options with an optional string
 * parameters.
 * <p>
 * This option can for instance be used to recognize command line parameters
 * with additional argument:
 * </p>
 * 
 * <pre>
 *     --abc
 * </pre>
 * 
 * <p>
 * or:
 * </p>
 * 
 * <pre>
 *     --abc=xzy
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class OptionalStringOption extends Option {

    /**
     * Creates a new object.
     * 
     * @param tag the tag for the description of the option
     */
    public OptionalStringOption(String tag) {

        super(tag);
    }

    /**
*      java.util.List)
     */
    @Override
    public int run(String a, List<String> arg)
            throws MissingArgumentCliException,
                UnknownOptionCliException {

        if (arg.isEmpty()) {
            return run(a, (String) null);
        }
        return run(a, arg.remove(0));
    }

    /**
     * Do whatever the option requires to be done.
     * 
     * @param a the name the option has actually used
     * @param arg the argument
     * 
     * @return the exit code
     * 
     * @throws UnknownOptionCliException just in case
     */
    protected abstract int run(String a, String arg)
            throws UnknownOptionCliException;

    /**
*      java.lang.String, java.util.List)
     */
    @Override
    public int run(String a, String firstArg, List<String> arg)
            throws UnknownOptionCliException {

        return run(a, firstArg);
    }

}
