/*
 * Copyright (C) 2003-2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
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

package org.extex.exbib.main.bibtex;

import java.util.logging.Logger;

import org.extex.exbib.core.i18n.Messages;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;

/**
 * Observer which counts the databases read and prints messages to a writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class DBObserver implements Observer {

    /**
     * The field <tt>logger</tt> contains the logger for the output produced.
     */
    private Logger logger;

    /**
     * The field <tt>dbCount</tt> contains the counter for invocations of
     * update().
     */
    private int dbCount = 0;

    /**
     * Creates a new object.
     * 
     * @param logger the target logger
     */
    public DBObserver(Logger logger) {

        super();
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.util.Observer#update(
     *      org.extex.exbib.core.util.Observable, java.lang.Object)
     */
    public void update(Observable source, Object o) {

        logger.info(Messages.format("DBObserver.Database_file_#", Integer
            .toString(++dbCount), o));
    }

}
