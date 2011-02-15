/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.util;

import java.util.logging.Logger;

/**
 * The tracing observer prints a message to the given writer prefixed by a
 * String.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TracingObserver implements Observer {

    /**
     * The field <tt>prefix</tt> contains the prefix for the tracing line.
     */
    private String prefix;

    /**
     * The field <tt>writer</tt> contains the writer for the output produced.
     */
    private Logger logger;

    /**
     * Creates a new object.
     * 
     * @param logger the target writer
     * @param prefix the prefix
     */
    public TracingObserver(Logger logger, String prefix) {

        this.logger = logger;
        this.prefix = prefix;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.util.Observer#update(
     *      org.extex.exbib.core.util.Observable, java.lang.Object)
     */
    public void update(Observable source, Object obj) {

        logger.info(prefix + " " + (obj != null ? obj.toString() : "") + "\n");
    }
}
