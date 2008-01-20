/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.parser.RawIndexParserFactory;
import org.extex.exindex.core.parser.exindex.ExIndexParserFactory;
import org.extex.exindex.lisp.exception.LException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;

/**
 * This class is an indexer for testing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6688 $
 */
public class TestableIndexer extends Indexer {

    /**
     * The field <tt>FINDER</tt> contains the resource finder.
     */
    private static final ResourceFinder FINDER = new ResourceFinder() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
         */
        public void enableTracing(boolean flag) {

            // nay
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        public InputStream findResource(String name, String type)
                throws ConfigurationException {

            return getClass().getClassLoader().getResourceAsStream(name);
        }

    };

    /**
     * Creates a new object.
     * 
     * @throws NoSuchMethodException in case of an error
     * @throws LException in case of an error
     * @throws SecurityException in case of an error
     * @throws InvocationTargetException in case of an error
     * @throws IllegalAccessException in case of an error
     * @throws InstantiationException in case of an error
     * @throws IllegalArgumentException in case of an error
     */
    public TestableIndexer()
            throws SecurityException,
                LException,
                NoSuchMethodException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        super();
        setResourceFinder(FINDER);
        RawIndexParserFactory parserFactory = new ExIndexParserFactory();
        parserFactory.setResourceFinder(FINDER);
        setParserFactory(parserFactory);
    }

    /**
     * Creates a new object.
     * 
     * @param rf the resource finder to use
     * 
     * @throws NoSuchMethodException in case of an error
     * @throws LException in case of an error
     * @throws SecurityException in case of an error
     * @throws InvocationTargetException in case of an error
     * @throws IllegalAccessException in case of an error
     * @throws InstantiationException in case of an error
     * @throws IllegalArgumentException in case of an error
     */
    public TestableIndexer(ResourceFinder rf)
            throws SecurityException,
                LException,
                NoSuchMethodException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        super();
        setResourceFinder(rf);
        RawIndexParserFactory parserFactory = new ExIndexParserFactory();
        parserFactory.setResourceFinder(rf);
        setParserFactory(parserFactory);
    }

}
