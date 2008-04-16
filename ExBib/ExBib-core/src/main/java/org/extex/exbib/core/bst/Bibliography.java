/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst;

import java.util.logging.Logger;

import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.auxio.AuxReaderImpl;

/**
 * This interface allows a communication from the
 * {@link AuxReaderImpl AuxReader} to an object storing the data read, i.e. a
 * {@link org.extex.exbib.core.db.DB DB}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public interface Bibliography {

    /**
     * Adder for bibliography databases.
     * <p>
     * The Strings passed in as argument are appended to the ones already
     * present.
     * </p>
     * 
     * @param s the array of Strings to add
     */
    void addBibliographyDatabase(String[] s);

    /**
     * Adder for the bibliography styles.
     * <p>
     * In generalization to BibT<sub>E</sub>X several styles can be specified.
     * In this case the functions specified in the styles are merged.
     * </p>
     * <p>
     * The Strings passed in as argument are appended to the ones already
     * present.
     * </p>
     * 
     * @param s the array of Strings of bibliography styles
     */
    void addBibliographyStyle(String... s);

    /**
     * Add a list of reference keys to be considered for output.
     * 
     * <p>
     * The Strings passed in as argument are appended to the ones already
     * present.
     * </p>
     * 
     * @param s the String array of reference keys
     */
    void addCitation(String[] s);

    /**
     * Run the procedures stored in the processor context. For each procedure
     * the observers `run' are called before it is executed.
     * 
     * @param outWriter the writer output is sent to
     * @param logger the logger
     * 
     * @throws ExBibException in case of an error
     */
    void process(Writer outWriter, Logger logger) throws ExBibException;

    /**
     * Reset the bibliography to its initial state.
     * <ul>
     * <li>All entries from the database are eliminated.</li>
     * <li>The loaded program is cleared.</li>
     * </ul>
     * 
     * @throws ExBibIllegalValueException in case that the name of a function
     *         which is initialized is <code>null</code> or empty. This should
     *         not happen
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists. This should not happen
     */
    void reset()
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

}
