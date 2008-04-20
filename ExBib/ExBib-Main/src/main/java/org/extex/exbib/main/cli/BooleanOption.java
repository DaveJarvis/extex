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

import java.util.List;

import org.extex.exbib.main.cli.exception.MissingArgumentCliException;

/**
 * This class represents a base class for options with a boolean parameter.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class BooleanOption extends Option {

    /**
     * Creates a new object.
     * 
     * @param tag the tag for the description of the option
     */
    public BooleanOption(String tag) {

        super(tag);
    }

    /**
     * Do whatever the option requires to be done.
     * 
     * @param a the name the option has actually used
     * @param arg the argument
     * 
     * @return the exit code
     */
    protected abstract int run(String a, boolean arg);

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.cli.Option#run(java.lang.String,
     *      java.util.List)
     */
    @Override
    public int run(String a, List<String> arg)
            throws MissingArgumentCliException {

        return run(a, true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.cli.Option#run(java.lang.String,
     *      java.lang.String, java.util.List)
     */
    @Override
    public int run(String a, String firstArg, List<String> arg) {

        return run(a, Boolean.parseBoolean(firstArg));
    }

}
