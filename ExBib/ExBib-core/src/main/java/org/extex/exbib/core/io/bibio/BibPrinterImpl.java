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

package org.extex.exbib.core.io.bibio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.VBlock;
import org.extex.exbib.core.db.VMacro;
import org.extex.exbib.core.db.VNumber;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.ValueItem;
import org.extex.exbib.core.db.ValueVisitor;
import org.extex.exbib.core.io.StreamWriter;
import org.extex.exbib.core.io.Writer;

/**
 * ...
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibPrinterImpl implements BibPrinter, ValueVisitor {

    /** the target writer */
    private Writer theWriter;

    /**
     * Creates a new object.
     */
    public BibPrinterImpl() {

        super();
    }

    /**
     * Prints as database as BibT<sub>E</sub>X file.
     * 
     * @param db the database context
     * 
     * @throws IOException in case of an I/O error
     */
    public void print(DB db) throws IOException {

        if (theWriter == null) {
            return;
        }

        Value preamble = db.getPreamble();

        if (preamble != null && !preamble.isEmpty()) {
            theWriter.print("@Preamble{");
            preamble.visit(this, db);
            theWriter.print("}\n\n");
        }

        List<String> macros = db.getMacroNames();
        Iterator<String> iterator = macros.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            theWriter.print("@String{", name, " = ");
            db.getMacro(name).visit(this, db);
            theWriter.print("}\n");
        }

        Iterator<Entry> entryIterator = db.getEntries().iterator();

        while (entryIterator.hasNext()) {
            Entry e = entryIterator.next();
            theWriter.print("\n@", e.getType(), "{ ");
            theWriter.print(e.getKey());
            iterator = e.getKeys().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                theWriter.print(",\n\t", key, " = ");
                e.get(key).visit(this, db);
            }

            theWriter.print("\n}\n");
        }
    }

    /**
     * Setter for the target.
     * 
     * @param file the file to write to
     * @param encoding the encoding name
     * 
     * @throws FileNotFoundException in case that the file could not be opened
     *         for writing
     * @throws UnsupportedEncodingException in case that the given encoding is
     *         unknown
     */
    public void setDestination(String file, String encoding)
            throws FileNotFoundException,
                UnsupportedEncodingException {

        theWriter = new StreamWriter(file, encoding);
    }

    /**
     * Setter for the writer.
     * 
     * @param writer the new writer to be used
     */
    public void setDestination(Writer writer) {

        theWriter = writer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitBlock(
     *      org.extex.exbib.core.db.VBlock, org.extex.exbib.core.db.DB)
     */
    public void visitBlock(VBlock value, DB db) throws IOException {

        theWriter.print("{", value.getContent(), "}");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitMacro(
     *      org.extex.exbib.core.db.VMacro, org.extex.exbib.core.db.DB)
     */
    public void visitMacro(VMacro value, DB db) throws IOException {

        theWriter.print(value.getContent());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitNumber(
     *      org.extex.exbib.core.db.VNumber, org.extex.exbib.core.db.DB)
     */
    public void visitNumber(VNumber value, DB db) throws IOException {

        theWriter.print(value.getContent());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitString(
     *      org.extex.exbib.core.db.VString, org.extex.exbib.core.db.DB)
     */
    public void visitString(VString value, DB db) throws IOException {

        theWriter.print("\"", value.getContent(), "\"");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitValue(
     *      org.extex.exbib.core.db.Value, org.extex.exbib.core.db.DB)
     */
    public void visitValue(Value value, DB db) throws IOException {

        Iterator<ValueItem> iterator = value.iterator();
        boolean first = true;

        while (iterator.hasNext()) {
            if (first) {
                first = false;
            } else {
                theWriter.print(" # ");
            }

            iterator.next().visit(this, db);
        }
    }

}
