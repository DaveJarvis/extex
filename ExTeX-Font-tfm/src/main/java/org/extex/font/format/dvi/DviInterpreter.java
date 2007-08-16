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

package org.extex.font.format.dvi;

import java.io.IOException;

import org.extex.font.exception.FontException;
import org.extex.util.file.random.RandomAccessR;

/**
 * Interface for a DVI interpreter.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface DviInterpreter {

    /**
     * Interpreter for DVI.
     * 
     * @param rar the input
     * @throws IOException in case of a IO-error.
     * @throws FontException in case of a font-error.
     */
    void interpret(RandomAccessR rar) throws IOException, FontException;
}
