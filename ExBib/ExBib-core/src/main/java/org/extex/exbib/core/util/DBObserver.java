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

import java.text.MessageFormat;
import java.util.logging.Logger;

/**
 * Observer which counts the databases read and prints messages to a writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class DBObserver implements Observer {

    /**
     * The field {@code logger} contains the logger for the output produced.
     */
    private final Logger logger;

    /**
     * The field {@code dbCount} contains the counter for invocations of
     * update().
     */
    private int dbCount = 0;

    /**
     * The field {@code pattern} contains the pattern for the messages.
     */
    private final String pattern;

    /**
     * Creates a new object.
     * 
     * @param logger the target logger
     * @param pattern the pattern for the messages
     */
    public DBObserver(Logger logger, String pattern) {

        this.logger = logger;
        this.pattern = pattern;
    }

    /**
*      org.extex.exbib.core.util.Observable, java.lang.Object)
     */
    public void update(Observable source, Object db) {

        logger.info(MessageFormat.format(pattern, Integer.toString(++dbCount),
            db));
    }

}
