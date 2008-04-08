/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.bibio.BibPrinter;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This interface defines the capabilities of a bibliographic database.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface DB extends Configurable {

    /**
     * Get the list of all entries.
     * 
     * @return the entries
     */
    List<Entry> getEntries();

    /**
     * Get a single entry stored under the given reference key. The key is
     * normalized before the entry is sought, i.e. the search is case
     * insensitive.
     * <p>
     * If no record is stored under the given key then <code>null</code> is
     * returned.
     * </p>
     * 
     * @param key the reference key
     * 
     * @return the record or null
     */
    Entry getEntry(String key);

    /**
     * Compute the expanded representation of a macro. The expansion is
     * performed by recursively replacing macro names by the definitions and
     * concatenating the resulting list of values.
     * <p>
     * Any macro is not during the expansion then the empty string is used
     * instead.
     * </p>
     * <p>
     * If the named macro the expansion is started with does not exist then
     * <code>null</code> is returned.
     * </p>
     * 
     * @param key the name of the macro
     * 
     * @return the value of the macro with all macros resolved or
     *         <code>null</code> if the macro is not defined
     */
    String getExpandedMacro(String key);

    /**
     * Retrieve the definition of a macro.
     * 
     * @param name the name of the macro
     * 
     * @return the value of a macro or <code>null</code> if the macro is not
     *         defined
     */
    Value getMacro(String name);

    /**
     * Getter for the list of macro (i.e. STRING) names.
     * 
     * @return the list of macro names
     */
    List<String> getMacroNames();

    /**
     * Getter for the preamble
     * 
     * @return the preamble
     */
    Value getPreamble();

    /**
     * Load the contents of a file into this object.
     * 
     * @param file the name of the file to load
     * @param citation the mapping of citations in normalized form, i.e. lower
     *        case, to their original form
     * @param processor the processor context
     * 
     * @return the list of references which could not be resolved
     * 
     * @throws ExBibException in case of an syntax error
     * @throws ConfigurationException in case of an invalid configuration
     * @throws FileNotFoundException in case that the requested file could not
     *         be opened for reading
     */
    List<String> load(String file, Map<String, String> citation,
            Processor processor)
            throws ExBibException,
                ConfigurationException,
                FileNotFoundException;

    /**
     * Creates a new entry for this database. Usually no Entry should be created
     * with the constructor. This method is preferred since it links the Entry
     * into the database correctly.
     * 
     * @param type the type for the new entry
     * @param key the key for the new entry
     * @param locator the locator from the user's perspective
     * 
     * @return a new entry
     */
    Entry makeEntry(String type, String key, Locator locator);

    /**
     * Register an observer.
     * 
     * @param name the name of the event to observe
     * @param observer the observer to register
     * 
     * @throws NotObservableException the given event is not observable
     */
    void registerObserver(String name, Observer observer)
            throws NotObservableException;

    /**
     * Write the database to the given BibWriter. Thus it is possible to
     * decouple the database from the printing methods and allow the program to
     * use different external representations
     * 
     * @param writer the target writer
     * 
     * @throws IOException just in case
     */
    void save(BibPrinter writer) throws IOException;

    /**
     * Setter for the BibReaderFactory.
     * 
     * @param factory the factory to use
     */
    void setBibReaderFactory(BibReaderFactory factory);

    /**
     * Setter for the sorter.
     * 
     * @param sorter the new
     *        {@link org.extex.exbib.core.db.sorter.Sorter Sorter}
     */
    public void setSorter(Sorter sorter);

    /**
     * Sort the entries according to the configured sorter.
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     */
    void sort() throws ConfigurationException;

    /**
     * Creates an alias for an existing entry. The alias is a means to access
     * the entry under a different name. Nevertheless changes to one affects
     * both of them.
     * 
     * @param alias the key for the new entry
     * @param key the key for the existing entry to link to
     * @param locator the locator
     * 
     * @throws ExBibEntryUndefinedException in case that the key can not be
     *         resolved
     */
    void storeAlias(String alias, String key, Locator locator)
            throws ExBibEntryUndefinedException;

    /**
     * Store the given comment in the database.
     * 
     * @param comment the comment to store
     */
    void storeComment(String comment);

    /**
     * Store the given preamble in the database. The value given is appended to
     * the Value already present.
     * 
     * @param pre the preamble to store
     */
    void storePreamble(Value pre);

    /**
     * Store a string definition in the database.
     * 
     * @param name the name of the string definition. The name is not case
     *        sensitive. Thus it is translated to lower case internally
     * @param value the value of the string definition
     */
    void storeString(String name, Value value);

}
