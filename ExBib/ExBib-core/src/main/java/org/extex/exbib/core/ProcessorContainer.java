/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.DBFactory;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a container for a named set of bibliographies.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ProcessorContainer implements Configurable, Iterable<String> {

    /**
     * This is a pair of name and observer.
     */
    private class NamedObserver {

        /**
         * The field <tt>name</tt> contains the name.
         */
        private String name;

        /**
         * The field <tt>observer</tt> contains the observer.
         */
        private Observer observer;

        /**
         * Creates a new object.
         * 
         * @param name the name
         * @param observer the observer
         */
        public NamedObserver(String name, Observer observer) {

            super();
            this.name = name;
            this.observer = observer;
        }

        /**
         * Getter for name.
         * 
         * @return the name
         */
        public String getName() {

            return name;
        }

        /**
         * Getter for observer.
         * 
         * @return the observer
         */
        public Observer getObserver() {

            return observer;
        }

    }

    /**
     * The field <tt>obsList</tt> contains the observers.
     */
    private List<NamedObserver> obsList = new ArrayList<NamedObserver>();

    /**
     * The field <tt>bibliographies</tt> contains the bibliographies.
     */
    private Map<String, Processor> bibliographies =
            new HashMap<String, Processor>();

    /**
     * The field <tt>dbFactory</tt> contains the database factory.
     */
    private DBFactory dbFactory;

    /**
     * The field <tt>processorFactory</tt> contains the processor factory.
     */
    private ProcessorFactory processorFactory;

    /**
     * The field <tt>bibReaderFactory</tt> contains the bib reader factory.
     */
    private BibReaderFactory bibReaderFactory;

    /**
     * The field <tt>minCrossrefs</tt> contains the <tt>min.crossrefs</tt>
     * parameter for the database.
     */
    private int minCrossrefs = 2;

    /**
     * The field <tt>fallback</tt> contains the default name for a processor.
     */
    private String fallback;

    /**
     * The field <tt>sorter</tt> contains the sorter.
     */
    private Sorter sorter;

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     */
    public ProcessorContainer(Configuration config) {

        super();
        configure(config);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        fallback = config.getAttribute("fallback");

        dbFactory = new DBFactory(config.getConfiguration("DB"));
        processorFactory =
                new ProcessorFactory(config.getConfiguration("Processor"));
    }

    /**
     * Find or create a bibliography for a given key.
     * 
     * @param key the key for the bibliography to find; the value
     *        <code>null</code> denotes the default bibliography
     * 
     * @return the bibliography for the key. This is guaranteed to be not
     *         <code>null</code>
     * 
     * @throws ExBibException
     * @throws ConfigurationException
     */
    public Processor findBibliography(String key)
            throws ConfigurationException,
                ExBibException {

        String name = (key == null ? fallback : key);

        Processor processor = bibliographies.get(name);
        if (processor == null) {
            DB db = dbFactory.newInstance(bibReaderFactory, minCrossrefs);
            if (sorter != null) {
                db.setSorter(sorter);
            }

            processor = processorFactory.newInstance(db, null);
            for (NamedObserver no : obsList) {
                try {
                    processor.registerObserver(no.getName(), no.getObserver());
                } catch (NotObservableException e) {
                    throw new ExBibException(e);
                }
            }
            bibliographies.put(name, processor);
        }
        return processor;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param key the key
     * 
     * @return the bibliography or <code>null</code>
     */
    public Processor getBibliography(String key) {

        return bibliographies.get(key == null ? fallback : key);
    }

    /**
     * Getter for minCrossrefs.
     * 
     * @return the minCrossrefs
     */
    public int getMinCrossrefs() {

        return minCrossrefs;
    }

    /**
     * Check whether the container is empty.
     * 
     * @return <code>true</code> if the container is empty
     */
    public boolean isEmpty() {

        return bibliographies.isEmpty();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<String> iterator() {

        return bibliographies.keySet().iterator();
    }

    /**
     * Registers an observer.
     * 
     * @param name the name of the action to register for
     * @param observer the observer to invoke upon the action
     * 
     * @throws NotObservableException in case that the name does not correspond
     *         to an observable action
     */
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        obsList.add(new NamedObserver(name, observer));
    }

    /**
     * Setter for bibReaderFactory.
     * 
     * @param bibReaderFactory the bibReaderFactory to set
     */
    public void setBibReaderFactory(BibReaderFactory bibReaderFactory) {

        this.bibReaderFactory = bibReaderFactory;
    }

    /**
     * Setter for minCrossrefs.
     * 
     * @param minCrossrefs the minCrossrefs to set
     */
    public void setMinCrossrefs(int minCrossrefs) {

        this.minCrossrefs = minCrossrefs;
    }

    /**
     * Setter for sorter.
     * 
     * @param sorter the sorter to set
     */
    public void setSorter(Sorter sorter) {

        this.sorter = sorter;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return bibliographies.toString();
    }

}
