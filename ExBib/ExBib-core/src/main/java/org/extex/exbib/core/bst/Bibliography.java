/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst;

import java.util.List;
import java.util.Map;

import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;

/**
 * This interface allows a communication from the AuxReader to an object storing
 * the data read, i.e. a {@link org.extex.exbib.core.db.DB DB}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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
    void addBibliographyDatabase(String... s);

    /**
     * Adder for the bibliography styles.
     * <p>
     * In generalization to B<small>IB</small>T<sub>E</sub>X several styles can
     * be specified. In this case the functions specified in the styles are
     * merged.
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
    void addCitation(String... s);

    /**
     * Count the number of bibliography styles consumed.
     * 
     * @return the number of bibliography styles
     */
    int countBibliographyStyles();

    /**
     * Count the number of citations consumed.
     * 
     * @return the number of citations
     */
    int countCitations();

    /**
     * Count the number of databases read in.
     * 
     * @return the number of databases
     */
    int countDatabases();

    /**
     * Getter for the bib style.
     * 
     * @return the list of bib styles
     */
    List<String> getBibliographyStyles();

    /**
     * Getter for the options.
     * 
     * @return the options
     */
    public Map<String, Token> getOptions();

    /**
     * Reset the bibliography to its initial state.
     * <ul>
     * <li>All entries from the database are eliminated.</li>
     * <li>The loaded program is cleared.</li>
     * </ul>
     */
    void reset();

    /**
     * Setter for an options.
     * 
     * @param name the name
     * @param value the value
     * 
     * @return <code>true</code> iff the option is known and has been set
     * 
     * @throws ExBibIllegalValueException in case of an illegal value
     * @throws ExBibFunctionExistsException in case of a redefinition
     */
    boolean setOption(String name, String value)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

    /**
     * Setter for an options.
     * 
     * @param name the name
     * @param value the value
     * 
     * @return <code>true</code> iff the option is known and has been set
     * 
     * @throws ExBibIllegalValueException in case of an illegal value
     * @throws ExBibFunctionExistsException in case of a redefinition
     */
    boolean setOption(String name, Token value)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

}
