/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.io.bibio;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This is a utility class for tests.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class BiblioTester {

    /**
     * Read a database from file.
     * 
     * @param bibFile the name of the file
     * 
     * @return the database
     * 
     * @throws FileNotFoundException if the file could not be opened for reading
     * @throws IOException in case of an I/O error
     * @throws ExBibException in case of an error
     */
    public static DB loadBib(String bibFile)
            throws FileNotFoundException,
                IOException,
                ExBibException {

        Configuration cfg =
                ConfigurationFactory.newInstance("path/testFinder.xml");
        ResourceFinder finder =
                new ResourceFinderFactory().createResourceFinder(cfg, null,
                    null, null);
        BibReader r = new BibReaderImpl();
        r.setResourceFinder(finder);
        r.open(bibFile, (String) null);
        DB db = new DBImpl();
        try {
            r.load(db);
        } finally {
            r.close();
        }
        return db;
    }

    /**
     * Creates a new object.
     */
    private BiblioTester() {

        super();
    }

}
