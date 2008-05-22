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

package org.extex.exbib.core.db;

import java.io.IOException;

import org.extex.exbib.core.io.Writer;

/**
 * Enumeration for sections.
 */
public enum Section {

    /**
     * The field <tt>Preamble</tt> contains the preamble section.
     */
    Preamble {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.Section#visit(
         *      org.extex.exbib.core.io.Writer,
         *      org.extex.exbib.core.db.SectionVisitor,
         *      org.extex.exbib.core.db.DB)
         */
        @Override
        public void visit(Writer writer, SectionVisitor visitor, DB db)
                throws IOException {

            visitor.visitPreamble(db);
        }
    },
    /**
     * The field <tt>Strings</tt> contains the strings section.
     */
    Strings {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.Section#visit(
         *      org.extex.exbib.core.io.Writer,
         *      org.extex.exbib.core.db.SectionVisitor,
         *      org.extex.exbib.core.db.DB)
         */
        @Override
        public void visit(Writer writer, SectionVisitor visitor, DB db)
                throws IOException {

            visitor.visitStrings(db);
        }
    },
    /**
     * The field <tt>Entries</tt> contains the entries section.
     */
    Entries {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.db.Section#visit(
         *      org.extex.exbib.core.io.Writer,
         *      org.extex.exbib.core.db.SectionVisitor,
         *      org.extex.exbib.core.db.DB)
         */
        @Override
        public void visit(Writer writer, SectionVisitor visitor, DB db)
                throws IOException {

            visitor.visitEntries(db);
        }
    };

    /**
     * Invoke the visitor's appropriate method.
     * 
     * @param writer the writer
     * @param visitor the visitor
     * @param db the database
     * 
     * @throws IOException in case of an I/O error
     */
    public abstract void visit(Writer writer, SectionVisitor visitor, DB db)
            throws IOException;

}
