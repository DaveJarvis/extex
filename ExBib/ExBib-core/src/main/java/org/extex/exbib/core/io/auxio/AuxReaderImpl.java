/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.io.auxio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.exbib.core.bst.Bibliography;
import org.extex.exbib.core.engine.Engine;
import org.extex.exbib.core.exceptions.InitializationException;
import org.extex.exbib.core.io.AbstractFileReader;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is the core of the aux file reading as performed by BibTeX.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class AuxReaderImpl extends AbstractFileReader implements Engine {

    /**
     * The field <tt>PATTERN</tt> contains the pattern for the recognized
     * macros.
     */
    private static final Pattern PATTERN =
            Pattern.compile("^\\\\(bibdata|bibstyle|citation)\\{([^{}]*)\\}");

    /**
     * The field <tt>reader</tt> contains the internal reader is buffered and
     * provides line numbers.
     */
    private LineNumberReader reader = null;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public AuxReaderImpl() throws ConfigurationException {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.engine.Engine#process(
     *      org.extex.exbib.core.bst.Bibliography)
     */
    public int[] process(Bibliography listener) throws ConfigurationException {

        if (reader == null) {
            throw new InitializationException(getClass().getName()
                    + "#process()", "reader");
        }

        String line;
        int noStyle = 0;
        int noCite = 0;
        int noData = 0;

        try {
            while ((line = reader.readLine()) != null) {
                Matcher m = PATTERN.matcher(line);

                if (m.matches()) {
                    String type = m.group(1);

                    if (type.equals("bibdata")) {
                        listener.addBibliographyDatabase(m.group(2).split(","));
                        noData++;
                    } else if (type.equals("bibstyle")) {
                        listener.addBibliographyStyle(m.group(2).split(","));
                        noStyle++;
                    } else if (type.equals("citation")) {
                        listener.addCitation(m.group(2).split(","));
                        noCite++;
                    }
                }
            }
        } catch (IOException e) {
            // TODO gene: error handling unimplemented
            e.printStackTrace();
            throw new RuntimeException("unimplemented");
        }

        try {
            close();
        } catch (IOException e) {
            // TODO gene: error handling unimplemented
            e.printStackTrace();
            throw new RuntimeException("unimplemented");
        }
        return new int[]{noData, noStyle, noCite};
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.engine.Engine#setFilename(java.lang.String)
     */
    public void setFilename(String file)
            throws FileNotFoundException,
                ConfigurationException {

        reader = open(file, "aux");
    }

}
