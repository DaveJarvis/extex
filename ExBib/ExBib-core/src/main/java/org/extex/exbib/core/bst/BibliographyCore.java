/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
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
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.framework.i18n.LocalizerFactory;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is the core implementation of a bibliography.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibliographyCore implements Bibliography, Observable {

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db;

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
    private final ObserverList endParseObservers = new ObserverList();

    /**
     * The field <tt>parseObservers</tt> contains the list of observers
     * triggered when the parsing is completed.
     */
    private final ObserverList parseObservers = new ObserverList();

    /**
     * The field <tt>startParseObservers</tt> contains the list of observers
     * triggered when the parsing starts.
     */
    private final ObserverList startParseObservers = new ObserverList();

    /**
     * The field <tt>startReadObservers</tt> contains the list of observers
     * triggered when the parsing ends.
     */
    private final ObserverList startReadObservers = new ObserverList();

    /**
     * The field <tt>setOptionObservers</tt> contains the list of observers
     * triggered when an option is set.
     */
    private final ObserverList setOptionObservers = new ObserverList();

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
    private Logger logger;

    /**
     * The field <tt>options</tt> contains the options.
     */
    private Map<String, Token> options = new HashMap<>();

    /**
     * Create a new {@link Bibliography} object.
     * 
     * @param db The database associated with this processor.
     * @param log This argument is a writer which receives the logging output.
     *        It is an object implementing the interface <code>Writer</code>.
     */
    public BibliographyCore(DB db, Logger log) {

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
     */
    @Override
    public void addBibliographyDatabase(String... sa) {

        bibliographyDatabases.addAll( Arrays.asList( sa ) );
    }

    /**
     * Setter for bib style. The bib style is the name of he BST file to use for
     * processing the database.
     * 
     * @param style the new bib style
     */
    @Override
    public void addBibliographyStyle(String... style) {

        bibliographyStyles.addAll( Arrays.asList( style ) );
    }

    /**
     * Setter for citations. The citations already existing in the processor
     * context are augmented by the ones given.
     * 
     * @param sa the String list of citations
     */
    @Override
    public void addCitation(String... sa) {

        for (final String s : sa) {
            citations.put(s.toLowerCase(Locale.ENGLISH), s);
        }
    }

    /**
     * Add an entry.
     * 
     * @param entry the name of the entry
     */
    public void addEntry(String entry) {

        theEntries.add(entry);
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
     * Configure the current instance. Here the configuration data is shuffled
     * into the options. For this purpose the tags with the name
     * <code>option</code> contains the value. The attribute <code>name</code>
     * determines the name of the option.
     * 
     * <pre>
     *   &lt;config&gt;
     *     &lt;option name="name<sub>1</sub>"&gt;value<sub>1</sub>&lt;/option&gt;
     *     &lt;option name="name<sub>2</sub>"&gt;value<sub>2</sub>&lt;/option&gt;
     *     &lt;option name="name<sub>3</sub>"&gt;value<sub>3</sub>&lt;/option&gt;
     *   &lt;/config&gt;
     * </pre>
     * 
     * @param config the configuration to consult
     * 
     * @throws ConfigurationException in case of an error
     */
    public void configure(Configuration config) throws ConfigurationException {

        Iterator<Configuration> it = config.iterator("option");
        while (it.hasNext()) {
            Configuration cfg = it.next();
            String name = cfg.getAttribute("name");
            if (name == null) {
                throw new ConfigurationMissingAttributeException("name", cfg);
            }
            String value = cfg.getValue();
            try {
                setOption(name, value);
            } catch (ExBibException e) {
                throw new ConfigurationWrapperException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Bibliography#countBibliographyStyles()
     */
    @Override
    public int countBibliographyStyles() {

        return bibliographyStyles.size();
    }

    @Override
    public int countCitations() {

        return citations.size();
    }

    @Override
    public int countDatabases() {

        return bibliographyDatabases.size();
    }

    /**
     * Getter for bib style. The bib style is the name of he BST file to use for
     * processing the database.
     */
    @Override
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

        return citations.get(key.toLowerCase(Locale.ENGLISH));
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
     * @return the expanded value of the macro or <code>null</code> if none has
     *         been found.
     */
    public String getMacro(String name) {

        return db.getExpandedMacro(name);
    }

    /**
     * Getter for an option.
     * 
     * @param key the key
     * 
     * @return the option value or <code>null</code>
     */
    public Token getOption(String key) {

        return options.get(key);
    }

    /**
     * Getter for an option.
     * 
     * @param key the key
     * @param defaultValue the value in case that the option is not defined
     * 
     * @return the option value or the default fallback
     */
    public Token getOption(String key, Token defaultValue) {

        Token value = options.get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Getter for the options.
     * 
     * @return the options
     */
    @Override
    public Map<String, Token> getOptions() {

        return options;
    }

    /**
     * Load all databases named into the processor context in turn.
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

    @Override
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        if ("startParse".equals(name)) {
            startParseObservers.add(observer);
        } else if ("parse".equals(name)) {
            parseObservers.add(observer);
        } else if ("endParse".equals(name)) {
            endParseObservers.add(observer);
        } else if ("startRead".equals(name)) {
            startReadObservers.add(observer);
        } else if ("setOption".equals(name)) {
            setOptionObservers.add(observer);
        } else {
            throw new NotObservableException(name);
        }
    }

    @Override
    public void reset() {

        citations = new HashMap<>();
        bibliographyStyles = new ArrayList<>();
        bibliographyDatabases = new ArrayList<>();
        theEntries = new ArrayList<>();
        options = new HashMap<>();
    }

    /**
     * Setter for the database.
     * 
     * @param database the database to be used
     */
    public void setDb(DB database) {

        this.db = database;
    }

    /**
     * Setter for the logger.
     * 
     * @param logger the new logger
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    @Override
    public boolean setOption(String name, String value)
            throws ExBibIllegalValueException {

        return setOption(name, (value.matches("-?[0-9]+") //
                ? new TInteger(value, null)
                : new TString(value, null)));
    }

    /**
     * Setter for an options.
     * 
     * @param name the name
     * @param value the value
     * 
     * @return <code>true</code> iff the option is known and has been set
     */
    @Override
    public boolean setOption(String name, Token value) {

        options.put(name, value);

        setOptionObservers.update(this, name);

        if ("min.crossref".equals(name)) {
            if (value instanceof TInteger) {
                db.setMinCrossrefs(((TInteger) value).getInt());
            } else {
                warning(LocalizerFactory.getLocalizer(BibliographyCore.class)
                    .format("number.format.error", name, value.getValue()));
            }
            return true;
        }

        return false;
    }

    /**
     * Write a warning to the logger if a logger is present. Otherwise simply do
     * nothing.
     * 
     * @param message the message
     */
    public void warning(String message) {

        if (logger != null) {
            logger.warning(message);
        }
    }

}
