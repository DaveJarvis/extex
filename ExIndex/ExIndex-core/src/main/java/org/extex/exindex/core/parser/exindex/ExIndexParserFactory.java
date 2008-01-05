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

package org.extex.exindex.core.parser.exindex;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.extex.exindex.core.exception.ParserException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.RawIndexParserFactory;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.resource.ResourceFinder;

/**
 * This class is a factory for raw index parsers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExIndexParserFactory implements RawIndexParserFactory {

    /**
     * The field <tt>LOCALIZER</tt> contains the the localizer.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(ExIndexParserFactory.class);

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object.
     */
    public ExIndexParserFactory() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParserFactory#create(
     *      java.lang.String, java.lang.String, LInterpreter)
     */
    public RawIndexParser create(String resource, String charset,
            LInterpreter interpreter) throws ParserException, IOException {

        String parser = "xindy";
        InputStream stream;
        if (resource == null) {
            stream = System.in;
        } else {
            stream = finder.findResource(resource, "raw");
            if (stream == null) {
                stream = finder.findResource(resource, "idx");
                parser = "makeindex";
                if (stream == null) {
                    return null;
                }
            }
        }
        Reader reader = new InputStreamReader(stream, charset);
        stream = getClass().getClassLoader().getResourceAsStream(//
            "org/extex/exindex/parser/" + parser + ".parser");
        if (stream == null) {
            throw new ParserException(LOCALIZER.format("ParserMissing", parser));
        }
        Properties p = new Properties();
        p.load(stream);
        String clazz = (String) p.get("class");
        if (clazz == null) {
            throw new ParserException(LOCALIZER.format("ParserMissingClass",
                parser));
        }

        try {
            Constructor<?> cons =
                    Class.forName(clazz).getConstructor(Reader.class,
                        String.class, LInterpreter.class);
            return (RawIndexParser) cons.newInstance(reader, resource, this);
        } catch (SecurityException e) {
            throw new ParserException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (NoSuchMethodException e) {
            throw new ParserException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (ClassNotFoundException e) {
            throw new ParserException(LOCALIZER.format("ParserMissingClass",
                parser));
        } catch (IllegalArgumentException e) {
            throw new ParserException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (InstantiationException e) {
            throw new ParserException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (IllegalAccessException e) {
            throw new ParserException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        } catch (InvocationTargetException e) {
            throw new ParserException(LOCALIZER.format("ParserError", parser, e
                .toString()), e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParserFactory#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

}
