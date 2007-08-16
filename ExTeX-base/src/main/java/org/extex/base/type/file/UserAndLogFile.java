/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.base.type.file;

import java.io.IOException;
import java.util.logging.Logger;

import org.extex.scanner.type.tokens.Tokens;

/**
 * This implementation of an OutFile encapsulates a Logger. It outputs the items
 * to the log file and the console.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UserAndLogFile extends OutputFile {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>logger</tt> contains the encapsulated logger.
     */
    private transient Logger logger;

    /**
     * Creates a new object.
     * 
     * @param logger the target Logger
     */
    public UserAndLogFile(Logger logger) {

        super(null);
        this.logger = logger;
    }

    /**
     * Close the current file.
     * 
     * @throws IOException in case of an error
     * 
     * @see org.extex.scanner.type.file.OutFile#close()
     */
    @Override
    public void close() throws IOException {

        this.logger = null;
    }

    /**
     * Check whether the output file is open.
     * 
     * @return <code>true</code> iff the instance is open
     * 
     * @see org.extex.scanner.type.file.OutFile#isOpen()
     */
    @Override
    public boolean isOpen() {

        return logger != null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.base.type.file.OutputFile#open(java.lang.String)
     */
    @Override
    public void open(String encoding) {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.type.file.OutputFile#write(
     *      org.extex.scanner.type.tokens.Tokens)
     */
    @Override
    public void write(Tokens toks) throws IOException {

        if (logger != null) {
            logger.info(toks.toText());
        }
    }

}
