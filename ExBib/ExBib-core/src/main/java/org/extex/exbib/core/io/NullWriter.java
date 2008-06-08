/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io;

import java.io.IOException;
import java.io.PrintStream;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * The NullWriter acts like a {@link Writer Writer} but does not perform any
 * action beside invoking the "next" Writers. It can be used as a neutral
 * element in a Writer chain.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class NullWriter implements Writer {

    /**
     * Creates a new object.
     */
    public NullWriter() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param ps ignored
     */
    public NullWriter(PrintStream ps) {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration cfg) throws ConfigurationException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String[])
     */
    public void print(String... args) throws IOException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void println(String... args) {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) {

        //
    }

}
