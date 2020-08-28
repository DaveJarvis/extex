/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exdoc.util;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Resolve XML entities.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5413 $
 */
public class ExdocEntityResolver implements EntityResolver {


    public ExdocEntityResolver() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
     *      java.lang.String)
     */
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException,
                IOException {

        String name =
                getClass().getName().replace('.', '/').replaceFirst(
                    "ExdocEntityResolver$", "html.dtd");
        InputSource source =
                new InputSource(getClass().getClassLoader()
                    .getResourceAsStream(name));
        source.setPublicId(publicId);
        source.setSystemId(systemId);
        return source;
    }
}
