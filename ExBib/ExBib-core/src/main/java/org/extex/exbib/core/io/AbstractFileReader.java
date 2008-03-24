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

package org.extex.exbib.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;
import org.extex.exbib.core.util.ObserverList;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;

/**
 * This is the base class for all file reading classes in ExBib.
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public abstract class AbstractFileReader implements Observable, Configurable {

    /**
     * The field <tt>cfg</tt> contains the configuration for all file readers.
     */
    private Configuration cfg = null;

    /**
     * The field <tt>filename</tt> contains the name of the file to read.
     */
    private File filename = null;

    /**
     * The field <tt>reader</tt> contains the internal reader to be used.
     */
    private LineNumberReader reader = null;

    /**
     * The field <tt>locator</tt> contains the locator from the user's
     * perspective.
     */
    private Locator locator = null;

    /**
     * The field <tt>foundHook</tt> contains the observers registered for the
     * found event.
     */
    private ObserverList foundHook = new ObserverList();

    /**
     * The field <tt>searchHook</tt> contains the observers registered for the
     * search event.
     */
    private ObserverList searchHook = new ObserverList();

    /**
     * The field <tt>line</tt> contains the temporary memory for the line.
     */
    private StringBuffer line = new StringBuffer();

    /**
     * Create a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public AbstractFileReader() throws ConfigurationException {

        super();
    }

    /**
     * Determine whether another read can be performed on the reader.
     * 
     * @return <code>true</code> if read may be performed
     */
    public boolean canRead() {

        return (reader != null);
    }

    /**
     * Close the current reader without destroying it. No further reading can be
     * performed.
     * 
     * @throws IOException in case of an I/O error
     */
    public void close() throws IOException {

        try {
            reader.close();
        } finally {
            reader = null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        cfg = config;
    }

    /**
     * Search for a file by altering the path and the extension according to the
     * configuration for the given file type.
     * 
     * @param name the name of the file to find
     * @param type the type of the file to find. The type might be different
     *        from the extension
     * 
     * @return a FileReader for the requested target
     * 
     * @throws FileNotFoundException in case of an error
     * @throws ConfigurationException in case of a configuration error
     */
    private FileReader findFile(String name, String type)
            throws FileNotFoundException,
                ConfigurationException {

        if (cfg == null) {
            throw new ConfigurationMissingException(getClass().getName());
        }

        Configuration typeConfig = cfg.getConfiguration(type);
        List<String> ext = typeConfig.getValues("extension");
        List<String> path = typeConfig.getValues("path");
        Iterator<String> pathIterator = path.iterator();

        while (pathIterator.hasNext()) {
            String dir = pathIterator.next();
            Iterator<String> extIterator = ext.iterator();

            while (extIterator.hasNext()) {
                filename = new File(dir, name + extIterator.next());
                searchHook.update(this, filename);

                if (filename.exists() && filename.canRead()) {
                    try {
                        FileReader reader = new FileReader(filename);
                        foundHook.update(this, filename);
                        return reader;
                    } catch (FileNotFoundException e) {
                        throw new FileNotFoundException(name);
                    }
                }
            }
        }

        throw new FileNotFoundException(name);
    }

    /**
     * Getter for the line buffer
     * 
     * @return the line buffer
     */
    public StringBuffer getBuffer() {

        if (line == null && reader != null) {
            read();
        }

        return line;
    }

    /**
     * Getter for the file name from which reading is performed.
     * 
     * @return the file name
     */
    public String getFilename() {

        return filename.getPath();
    }

    /**
     * Getter for the locator.
     * 
     * @return the locator
     */
    public Locator getLocator() {

        return new Locator(filename.getPath(), reader.getLineNumber());
    }

    /**
     * Open the reader for subsequent reading operations.
     * 
     * @param name the name of the file to open
     * @param type the type of the file to open
     * 
     * @return a Reader for the requested file
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     * @throws FileNotFoundException in case that the named file could not be
     *         opened for reading
     */
    public Reader open(String name, String type)
            throws FileNotFoundException,
                ConfigurationException {

        reader = new LineNumberReader(findFile(name, type));
        return reader;
    }

    /**
     * Attempt to fill the line buffer with an additional line.
     * 
     * @return the internal line buffer
     */
    public StringBuffer read() {

        if (line == null) {
            return null;
        }

        try {
            String nextLine = reader.readLine();

            if (nextLine == null) {
                line = null;
            } else {
                line.append(nextLine);
                locator =
                        new Locator(filename.getPath(), reader.getLineNumber());
            }
        } catch (IOException e) {
            line = null;
        }

        return line;
    }

    /**
     * @see org.extex.exbib.core.util.Observable#registerObserver(
     *      java.lang.String, org.extex.exbib.core.util.Observer)
     */
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        if (name.equals("search")) {
            searchHook.add(observer);
        } else if (name.equals("found")) {
            foundHook.add(observer);
        } else {
            throw new NotObservableException(name);
        }
    }

    /**
     * Setter for the line buffer.
     * 
     * @param buffer the line buffer
     */
    public void setBuffer(StringBuffer buffer) {

        line = buffer;
    }

    /**
     * Setter for the file name from which reading is performed.
     * 
     * @param file the file name
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     * @throws FileNotFoundException in case that the named file could not be
     *         opened for reading
     */
    public void setFilename(String file)
            throws FileNotFoundException,
                ConfigurationException {

        filename = new File(file);
    }

    /**
     * Returns a printable representation of this object.
     * 
     * @return the printable representation
     */
    @Override
    public String toString() {

        return filename.getPath();
    }

}
