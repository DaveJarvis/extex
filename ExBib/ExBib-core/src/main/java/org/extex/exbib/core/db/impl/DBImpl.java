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

package org.extex.exbib.core.db.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.db.sorter.SorterFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.bibio.BibPrinter;
import org.extex.exbib.core.io.bibio.BibReader;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;
import org.extex.exbib.core.util.ObserverList;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;

/**
 * This is a simple implementation of a BibT<sub>E</sub>X compatible database.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class DBImpl implements DB, Observable {

    /**
     * The field <tt>bibReaderFactory</tt> contains the factory used to get
     * new Readers.
     */
    private BibReaderFactory bibReaderFactory = null;

    /**
     * The field <tt>entries</tt> contains the list of entries to preserve the
     * original order.
     */
    private List<Entry> entries = new ArrayList<Entry>();

    /**
     * The field <tt>entryHash</tt> contains the Hash of entries to get fast
     * access.
     */
    private Map<String, Entry> entryHash = new HashMap<String, Entry>();

    /** Map String -> Value */
    private Map<String, Value> strings = new HashMap<String, Value>();

    /**
     * The field <tt>makeAliasHook</tt> contains the observers for the
     * creation of an alias.
     */
    private ObserverList makeAliasHook = new ObserverList();

    /**
     * The field <tt>makeEntryHook</tt> contains the observers for the
     * creation of an entry.
     */
    private ObserverList makeEntryHook = new ObserverList();

    /**
     * The field <tt>makeMacroHook</tt> contains the observers for the
     * creation of a macro.
     */
    private ObserverList makeMacroHook = new ObserverList();

    /**
     * The field <tt>makePreambleHook</tt> contains the observers for the
     * creation of a preamble.
     */
    private ObserverList makePreambleHook = new ObserverList();

    /**
     * The field <tt>makeStringHook</tt> contains the observers for the
     * creation of a string.
     */
    private ObserverList makeStringHook = new ObserverList();

    /**
     * The field <tt>sorter</tt> contains the sorter to use.
     */
    private Sorter sorter = null;

    /**
     * The field <tt>preamble</tt> contains the collected preambles.
     */
    private Value preamble = new Value();

    /**
     * Create a new empty database.
     */
    public DBImpl() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        SorterFactory sf = new SorterFactory(config.getConfiguration("Sorter"));
        sorter = sf.newInstance();
    }

    /**
     * Getter for the list of entries stored in the database.
     * 
     * @return the list of entries
     */
    public List<Entry> getEntries() {

        return entries;
    }

    /**
     * Get a single entry stored under the given reference key. The key is
     * normalized before the entry is sought, i.e. the search is case
     * insensitive.
     * 
     * If no record is stored under the given key then <code>null</code> is
     * returned.
     * 
     * @param key the reference key
     * 
     * @return the record or <code>null</code>
     */
    public Entry getEntry(String key) {

        return entryHash.get(key.toLowerCase());
    }

    /**
     * @see org.extex.exbib.core.db.DB#getExpandedMacro(java.lang.String)
     */
    public String getExpandedMacro(String key) {

        Value value = getMacro(key);

        return value == null ? null : value.expand(this);
    }

    /**
     * @see org.extex.exbib.core.db.DB#getMacro(java.lang.String)
     */
    public Value getMacro(String name) {

        return strings.get(name.toLowerCase());
    }

    /**
     * @see org.extex.exbib.core.db.DB#getMacroNames()
     */
    public List<String> getMacroNames() {

        return new ArrayList<String>(strings.keySet());
    }

    /**
     * Getter for the preamble.
     * 
     * @return the preamble
     */
    public Value getPreamble() {

        return preamble;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.DB#load(java.lang.String, java.util.Map,
     *      org.extex.exbib.core.bst.Processor)
     */
    public List<String> load(String file, Map<String, String> citation,
            Processor processor)
            throws ExBibException,
                ConfigurationException,
                FileNotFoundException {

        BibReader reader = bibReaderFactory.newInstance(file);

        reader.load(this, processor);

        List<String> missing = new ArrayList<String>();

        if (citation == null || citation.containsKey("*")) {
            return missing;
        }

        Entry entry;
        List<String> open = new ArrayList<String>();
        Map<String, String> cite = new HashMap<String, String>();
        List<String> stack = new ArrayList<String>(citation.keySet());
        Iterator<String> iterator = stack.iterator();

        while (iterator.hasNext()) {
            String key = (iterator.next()).toLowerCase();

            if ((entry = getEntry(key)) != null) {
                cite.put(entry.getKey().toLowerCase(), "1");
            } else {
                missing.add(key);
            }
        }

        do {
            iterator = stack.iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                entry = getEntry(key);

                if (entry == null) {
                    missing.add(key);
                } else {
                    String crossref = entry.get("crossref").expand(this);

                    if (crossref != null && !cite.containsKey(crossref)) {
                        cite.put(crossref, "2");
                        open.add(crossref);
                    }
                }
            }

            stack = open;
            open = new ArrayList<String>();
        } while (!stack.isEmpty());

        Iterator<Entry> iter = entries.iterator();
        List<Entry> newEntries = new ArrayList<Entry>();

        while (iter.hasNext()) {
            entry = iter.next();

            String key = entry.getKey();

            if (cite.containsKey(key.toLowerCase())) {
                stack.add(entry.getKey());
            }
        }

        entries = newEntries;

        return missing;
    }

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
    public Entry makeEntry(String type, String key, Locator locator) {

        Entry entry = new Entry(locator);
        entry.setKey(key);
        entry.setType(type);
        entries.add(entry);
        entryHash.put(key.toLowerCase(), entry);
        makeEntryHook.update(this, entry);

        return entry;
    }

    /**
     * @see org.extex.exbib.core.util.Observable#registerObserver(java.lang.String,
     *      org.extex.exbib.core.util.Observer)
     */
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        if (name.equals("makeAlias")) {
            makeAliasHook.add(observer);
        } else if (name.equals("makeMacro")) {
            makeMacroHook.add(observer);
        } else if (name.equals("makePreamble")) {
            makePreambleHook.add(observer);
        } else if (name.equals("makeEntry")) {
            makeEntryHook.add(observer);
        } else if (name.equals("makeString")) {
            makeStringHook.add(observer);
        } else {
            throw new NotObservableException(name);
        }
    }

    /**
     * Write the database to the given BibWriter. Thus it is possible to
     * decouple the database from the printing methods and allow the program to
     * use different external representations
     * 
     * @param writer the target writer
     * 
     * @throws IOException just in case
     */
    public void save(BibPrinter writer) throws IOException {

        writer.print(this);
    }

    /**
     * Setter for the reader factory.
     * 
     * @param factory the new value
     */
    public void setBibReaderFactory(BibReaderFactory factory) {

        bibReaderFactory = factory;
    }

    /**
     * Setter for the sorter.
     * 
     * @param sorter the new
     *        {@link org.extex.exbib.core.db.sorter.Sorter Sorter}
     */
    public void setSorter(Sorter sorter) {

        this.sorter = sorter;
    }

    /**
     * Sort the database according to the configured sorter.
     * 
     * @throws ConfigurationException if no sorter has been configured jet
     */
    public void sort() throws ConfigurationException {

        if (sorter == null) {
            throw new ConfigurationMissingException("sorter");
        }

        sorter.sort(entries);
    }

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
    public void storeAlias(String alias, String key, Locator locator)
            throws ExBibEntryUndefinedException {

        Entry entry = getEntry(key);

        if (entry == null) {
            throw new ExBibEntryUndefinedException(key, locator);
        }

        entryHash.put(alias, entry);
        makeAliasHook.update(this, alias);
    }

    /**
     * Store the given comment in the database.
     * 
     * @param comment the comment to store
     */
    public void storeComment(String comment) {

        // not yet
    }

    /**
     * Store the given preamble in the database.
     * 
     * @param pre the preamble to store
     */
    public void storePreamble(Value pre) {

        preamble.add(pre);
        makePreambleHook.update(this, pre);
    }

    /**
     * Store a string definition in the database.
     * 
     * @param name the name of the string definition. The name is not case
     *        sensitive. Thus it is translated to lower case internally
     * @param value the value of the string definition
     */
    public void storeString(String name, Value value) {

        strings.put(name.toLowerCase(), value);
        makeStringHook.update(this, name);
    }

}