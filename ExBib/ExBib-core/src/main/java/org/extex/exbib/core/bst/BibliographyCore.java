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

package org.extex.exbib.core.bst;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;
import org.extex.exbib.core.util.ObserverList;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is the core implementation of a bibliography.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibliographyCore implements Bibliography, Observable {

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>citations</tt> contains the mapping from normalized forms
     * of citation strings to their original representation. The normalization
     * simply converts strings to their lowercase counterpart.
     */
    private Map<String, String> citations;

    /**
     * The field <tt>endParseObservers</tt> contains the list of observers
     * triggered when the parsing is started.
     */
    private ObserverList endParseObservers = new ObserverList();

    /**
     * The field <tt>parseObservers</tt> contains the list of observers
     * triggered when the parsing is completed.
     */
    private ObserverList parseObservers = new ObserverList();

    /**
     * The field <tt>startParseObservers</tt> contains the list of observers
     * triggered when the parsing starts.
     */
    private ObserverList startParseObservers = new ObserverList();

    /**
     * The field <tt>startReadObservers</tt> contains the list of observers
     * triggered when the parsing ends.
     */
    private ObserverList startReadObservers = new ObserverList();

    /**
     * The field <tt>bibliographyDatabases</tt> contains the list of
     * bibliography databases to consider.
     */
    private List<String> bibliographyDatabases;

    /**
     * The field <tt>bibliographyStyles</tt> contains the list of bibliography
     * styles to load and use.
     */
    private List<String> bibliographyStyles;

    /**
     * The field <tt>theEntries</tt> contains the list of entries.
     */
    private List<String> theEntries;

    /**
     * The field <tt>logger</tt> contains the writer for logging purposes.
     */
    private Logger logger = null;

    /**
     * Create a new {@link Bibliography} object.
     * 
     * @param db The database associated with this processor.
     * @param log This argument is a writer which receives the logging output.
     *        It is an object implementing the interface <code>Writer</code>.
     */
    public BibliographyCore(DB db, Logger log) {

        super();
        this.db = db;
        this.logger = log;

        reset();
    }

    /**
     * Setter for bib data.
     * <p>
     * This setter takes an array of names of bibliography databases. Those are
     * stored in the processor context and passed to the database as required.
     * </p>
     * 
     * @param sa the array of resources to add
     * 
     * @see org.extex.exbib.core.bst.Bibliography#addBibliographyDatabase(
     *      java.lang.String[])
     */
    public void addBibliographyDatabase(String... sa) {

        for (int i = 0; i < sa.length; i++) {
            bibliographyDatabases.add(sa[i]);
        }
    }

    /**
     * Setter for bib style. The bib style is the name of he BST file to use for
     * processing the database.
     * 
     * @param style the new bib style
     * 
     * @see org.extex.exbib.core.bst.Bibliography#addBibliographyStyle(
     *      java.lang.String[])
     */
    public void addBibliographyStyle(String... style) {

        for (int i = 0; i < style.length; i++) {
            bibliographyStyles.add(style[i]);
        }
    }

    /**
     * Setter for citations. The citations already existing in the processor
     * context are augmented by the ones given.
     * 
     * @param sa the String list of citations
     * 
     * @see org.extex.exbib.core.bst.Bibliography#addCitation(java.lang.String[])
     */
    public void addCitation(String... sa) {

        for (int i = 0; i < sa.length; i++) {
            citations.put(sa[i].toLowerCase(), sa[i]);
        }
    }

    /**
     * Store an additional <tt>STRING</tt> in the database. To delete a
     * <tt>STRING</tt> the value <code>null</code> can be used.
     * 
     * @param name the name of the macro to add
     * @param value the value as Token
     */
    public void addMacro(String name, Token value) {

        db.storeString(name, (value == null ? null : new Value(new VString(
            value.getValue()))));
    }

    /**
     * Configure the current instance.
     * 
     * @param config the configuration to consult
     * 
     * @throws ConfigurationException in case of an error
     */
    public void configure(Configuration config) throws ConfigurationException {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Bibliography#countBibliographyStyles()
     */
    public int countBibliographyStyles() {

        return bibliographyStyles.size();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Bibliography#countCitations()
     */
    public int countCitations() {

        return citations.size();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Bibliography#countDatabases()
     */
    public int countDatabases() {

        return bibliographyDatabases.size();
    }

    /**
     * Getter for bib style. The bib style is the name of he BST file to use for
     * processing the database.
     * 
     * @see org.extex.exbib.core.bst.Bibliography#getBibliographyStyles()
     */
    public List<String> getBibliographyStyles() {

        return bibliographyStyles;
    }

    /**
     * Get the original cite key for a given key. I.e. the casing might be
     * different.
     * 
     * @param key the citation key
     * 
     * @return the original citation key
     */
    public String getCite(String key) {

        return citations.get(key.toLowerCase());
    }

    /**
     * Getter for the database.
     * 
     * @return the database
     */
    public DB getDB() {

        return db;
    }

    /**
     * Getter for the entries.
     * 
     * @return the list of entries
     */
    public List<String> getEntries() {

        return theEntries;
    }

    /**
     * Getter for the log writer.
     * 
     * @return the log writer
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Getter for a certain macro.
     * 
     * @param name the name of the macro to search for
     * 
     * @return the expanded value of the macro or <code>null</code> if none
     *         has been found.
     */
    public String getMacro(String name) {

        return db.getExpandedMacro(name);
    }

    /**
     * Load all databases named in the processor context in turn.
     * 
     * @throws ExBibException in case that something went wrong
     * @throws ConfigurationException in case that the configuration is invalid
     * @throws FileNotFoundException in case that the requested file or one of
     *         the subsequent files (@input) could not be found
     */
    public void loadDatabases()
            throws ExBibException,
                FileNotFoundException,
                ConfigurationException {

        for (String file : bibliographyDatabases) {
            startReadObservers.update(this, file);
            db.load(file, citations);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.util.Observable#registerObserver(
     *      java.lang.String, org.extex.exbib.core.util.Observer)
     */
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        if (name.equals("startParse")) {
            startParseObservers.add(observer);
        } else if (name.equals("parse")) {
            parseObservers.add(observer);
        } else if (name.equals("endParse")) {
            endParseObservers.add(observer);
        } else if (name.equals("startRead")) {
            startReadObservers.add(observer);
        } else {
            throw new NotObservableException(name);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Bibliography#reset()
     */
    public void reset() {

        citations = new HashMap<String, String>();
        bibliographyStyles = new ArrayList<String>();
        bibliographyDatabases = new ArrayList<String>();
        theEntries = new ArrayList<String>();
    }

    /**
     * Setter for the database.
     * 
     * @param db the database to be used
     */
    public void setDB(DB db) {

        this.db = db;
    }

    /**
     * Setter for the logger.
     * 
     * @param logger the new logger
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Bibliography#setOption(java.lang.String,
     *      java.lang.String)
     */
    public void setOption(String name, String value) {

        if ("exbib.min.crossref".equals(name)) {
            try {
                db.setMinCrossrefs(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                logger.warning(LocalizerFactory.getLocalizer(
                    BibliographyCore.class).format("number.format.error", name,
                    value));
            }
            return;
        }

        logger.warning(LocalizerFactory.getLocalizer(BibliographyCore.class)
            .format("unused.option", name, value));
    }

}
