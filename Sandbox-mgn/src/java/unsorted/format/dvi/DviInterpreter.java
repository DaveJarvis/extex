/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package unsorted.format.dvi;

import java.io.IOException;

import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.util.file.random.RandomAccessR;

import unsorted.format.dvi.exception.DviException;

/**
 * Interface for a DVI interpreter.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface DviInterpreter {

    /**
     * Interpreter for DVI
     * 
     * @param rar the input
     * @throws IOException in case of a IO-error.
     * @throws DviException in case of a DVI-error.
     * @throws FontException in case of a font-error.
     * @throws ConfigurationException from the config system.
     */
    void interpret(RandomAccessR rar)
            throws IOException,
                DviException,
                FontException,
                ConfigurationException;

}
