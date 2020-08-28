/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main.xindy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.xindy.XindyParserFactory;
import org.extex.exindex.main.ExIndex;
import org.extex.exindex.main.exception.MainException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.resource.ResourceFinder;

/**
 * This parser factory arranges to use a filter if one is given.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FilteringParserFactory extends XindyParserFactory {

    /**
     * The field <tt>LOCALIZER</tt> contains the the localizer.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(ExIndex.class);

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field <tt>filter</tt> contains the name of the filter.
     */
    private String filter = null;

    /**
     * The field <tt>parser</tt> contains the name of the parser.
     */
    private String parser = "xindy";


    public FilteringParserFactory() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParserFactory#create(java.lang.String,
     *      java.lang.String, Indexer)
     */
    @Override
    public RawIndexParser create(String resource, String charset,
            Indexer indexer) throws RawIndexException, IOException {

        InputStream stream;
        if (resource == null) {
            stream = System.in;
        } else if (resource.endsWith(".raw")) {
            stream = finder.findResource(resource, "raw");
        } else if (resource.endsWith(".idx")) {
            stream = finder.findResource(resource, "idx");
            parser = "makeindex";
        } else {
            stream = finder.findResource(resource, "raw");
            if (stream == null) {
                stream = finder.findResource(resource, "idx");
                parser = "makeindex";
            }
        }

        if (stream == null) {
            return null;
        }

        Reader reader = new InputStreamReader(stream, charset);
        if (filter != null) {
            stream = getClass().getClassLoader().getResourceAsStream(//
                "org/extex/exindex/filter/" + filter + ".filter");
            if (stream != null) {
                Properties p = new Properties();
                p.load(stream);
                String clazz = (String) p.get("class");
                if (clazz == null) {
                    throw new MainException(LOCALIZER.format(
                        "FilterMissingClass", filter));
                }
                try {
                    Class<?> theClass = Class.forName(clazz);
                    if (!Reader.class.isAssignableFrom(theClass)) {
                        throw new MainException(LOCALIZER.format(
                            "FilterClassCast", filter, clazz));
                    }
                    reader =
                            (Reader) theClass.getConstructor(
                                new Class[]{Reader.class}).newInstance(
                                new Object[]{reader});
                } catch (ClassNotFoundException e) {
                    throw new MainException(LOCALIZER.format(
                        "FilterClassNotFound", filter, clazz), e);
                } catch (NoSuchMethodException e) {
                    throw new MainException(LOCALIZER.format(
                        "FilterNoSuchMethod", filter, clazz), e);
                } catch (SecurityException e) {
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, e.toString()), e);
                } catch (IllegalArgumentException e) {
                    throw new MainException(LOCALIZER.format(
                        "FilterIllegalArgument", filter, e.toString()), e);
                } catch (InstantiationException e) {
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, e.toString()), e);
                } catch (IllegalAccessException e) {
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, e.toString()), e);
                } catch (InvocationTargetException e) {
                    Throwable ex = (e.getCause() != null ? e.getCause() : e);
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, ex.toString()), e);
                } finally {
                    stream.close();
                }

            }
            // TODO gene: filter with external program
            throw new MainException(LOCALIZER.format("UnknownFilter", filter));
        }

        stream = getClass().getClassLoader().getResourceAsStream(//
            "org/extex/exindex/parser/" + parser + ".parser");
        if (stream == null) {
            throw new MainException(LOCALIZER.format("ParserMissing", parser));
        }
        Properties p = new Properties();
        p.load(stream);
        String clazz = (String) p.get("class");
        if (clazz == null) {
            throw new MainException(LOCALIZER.format("ParserMissingClass",
                parser));
        }

        try {
            Constructor<?> cons =
                    Class.forName(clazz).getConstructor(Reader.class,
                        String.class, Indexer.class);
            return (RawIndexParser) cons.newInstance(reader, resource, indexer);
        } catch (SecurityException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (NoSuchMethodException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (ClassNotFoundException e) {
            throw new MainException(LOCALIZER.format("ParserMissingClass",
                parser));
        } catch (IllegalArgumentException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (InstantiationException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (IllegalAccessException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (InvocationTargetException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        }
    }

    /**
     * Setter for the filter.
     * 
     * @param filter the filter to set
     */
    public void setFilter(String filter) {

        this.filter = filter;
    }

    /**
     * Setter for the parser.
     * 
     * @param parser the parser to set
     */
    public void setParser(String parser) {

        this.parser = parser;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParserFactory#setResourceFinder(org.extex.resource.ResourceFinder)
     */
    @Override
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

}
