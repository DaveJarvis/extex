/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.stream.exception;

import org.extex.scanner.exception.ScannerException;

/**
 * ScannerException, if a wrong Unicode name is given.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class ScannerNoUnicodeNameException extends ScannerException {

    /**
     * The field <tt>serialVersionUID</tt> contains th version number for
     * serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new Object
     */
    public ScannerNoUnicodeNameException() {

        super();
    }

    /**
     * Create a new Object.
     * 
     * @param wrongUnicodeName The wrong name.
     */
    public ScannerNoUnicodeNameException(String wrongUnicodeName) {

        super(wrongUnicodeName);
    }
}
