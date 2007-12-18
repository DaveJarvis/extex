/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.exception;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception signals that an error in the indexer has been encountered.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6631 $
 */
public class IndexerException extends Exception {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * The field <tt>line</tt> contains the line number.
     */
    private String line;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource
     * @param line the line number
     */
    public IndexerException(String resource, String line) {

        super((String) null);
        this.resource = resource;
        this.line = line;
    }

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource
     * @param line the line number
     * @param message the message
     */
    public IndexerException(String resource, String line, String message) {

        super(message);
        this.resource = resource;
        this.line = line;
    }

    /**
     * Getter for line.
     * 
     * @return the line
     */
    public String getLine() {

        return line;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        String message = getMessage();

        return LocalizerFactory.getLocalizer(IndexerException.class).format(
            (message == null ? "NoMessage" : "Message"), resource, line,
            message);
    }

    /**
     * Getter for resource.
     * 
     * @return the resource
     */
    public String getResource() {

        return resource;
    }

}
