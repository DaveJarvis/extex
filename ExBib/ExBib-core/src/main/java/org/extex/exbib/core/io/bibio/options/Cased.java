/*
 * Copyright (C) 2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
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

package org.extex.exbib.core.io.bibio.options;

import java.io.IOException;

import org.extex.exbib.core.io.Writer;

/**
 * Enumeration for case converted writing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public enum Cased {

    /**
     * The field <tt>Upper</tt> contains the translator to upper case.
     */
    Upper {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.bibio.options.Cased#write(
         *      org.extex.exbib.core.io.Writer, java.lang.String)
         */
        @Override
        public void write(Writer writer, String s) throws IOException {

            writer.print(s.toUpperCase());
        }
    },
    /**
     * The field <tt>Lower</tt> contains the translator to lower case.
     */
    Lower {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.bibio.options.Cased#write(
         *      org.extex.exbib.core.io.Writer, java.lang.String)
         */
        @Override
        public void write(Writer writer, String s) throws IOException {

            writer.print(s.toLowerCase());
        }
    },
    /**
     * The field <tt>AsIs</tt> contains the identity.
     */
    AsIs {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.bibio.options.Cased#write(
         *      org.extex.exbib.core.io.Writer, java.lang.String)
         */
        @Override
        public void write(Writer writer, String s) throws IOException {

            writer.print(s);
        }
    };

    /**
     * Write a transformed string.
     * 
     * @param writer the writer
     * @param s the string
     * 
     * @throws IOException in case of an I/O error
     */
    public abstract void write(Writer writer, String s) throws IOException;
}
