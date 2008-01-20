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

package org.extex.exindex.core.parser.xindy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.RawIndexParserFactory;
import org.extex.resource.ResourceFinder;

/**
 * This class is a factory for raw index parsers which delivers a
 * {@link XindyParser}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class XindyParserFactory implements RawIndexParserFactory {

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder = null;

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.RawIndexParserFactory#create(
     *      java.lang.String, java.lang.String, Indexer)
     */
    public RawIndexParser create(String resource, String charset,
            Indexer indexer) throws RawIndexException, IOException {

        InputStream stream = finder.findResource(resource, "raw");
        if (stream == null) {
            return null;
        }
        Reader reader =
                (charset == null || charset.equals("") ? new InputStreamReader(
                    stream) : new InputStreamReader(stream, charset));
        return new XindyParser(reader, resource, indexer);
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
