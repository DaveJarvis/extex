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

package org.extex.exbib.core.io.bibio;

import java.io.IOException;
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
import org.extex.exbib.core.io.Writer;

/**
 * This class implements a writer for databases in Lisp format.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibPrinterLispImpl implements BibPrinter, ValueVisitor {

    /**
     * Translate the characters '"', '&lt;', '&gt;' and '&amp;' to XML entities.
     * 
     * @param s the string to encode
     * 
     * @return the encoded version of the string
     */
    private static String encodeArg(String s) {

        StringBuffer sb = new StringBuffer(s);

        for (int i = sb.length() - 1; i > 0; i--) {
            switch (sb.charAt(i)) {
                case '\\':
                    sb.replace(i, i + 1, "\\\\");
                    break;
                case '"':
                    sb.replace(i, i + 1, "\\\"");
                    break;
                default: // leave alone
            }
        }

        return sb.toString();
    }

    /**
     * Translate the characters '&lt;', '&gt;' and '&amp;' to XML entities.
     * 
     * @param s the string to encode
     * 
     * @return the encoded version of the string
     */
    private static String encodeSymbol(String s) {

        StringBuffer sb = new StringBuffer(s);

        for (int i = sb.length() - 1; i > 0; i--) {
            switch (sb.charAt(i)) {
                case '>':
                    sb.replace(i, i + 1, "&gt;");
                    break;
                case '<':
                    sb.replace(i, i + 1, "&lt;");
                    break;
                case '&':
                    sb.replace(i, i + 1, "&amp;");
                    break;
                default: // leave alone
            }
        }

        return sb.toString();
    }

    /**
     * Translate the characters '&lt;', '&gt;' and '&amp;' to '_'.
     * 
     * @param s the string to encode
     * 
     * @return the encoded version of the string
     */
    private static String encodeXMLtag(String s) {

        StringBuffer sb = new StringBuffer(s);

        for (int i = sb.length() - 1; i > 0; i--) {
            switch (sb.charAt(i)) {
                case '>':
                case '<':
                case '&':
                    sb.replace(i, i + 1, "_");
                    break;
                default: // leave alone
            }
        }

        return sb.toString();
    }

    /**
     * The field <tt>writer</tt> contains the output writer.
     */
    private Writer writer = null;

    /**
     * Creates a new object.
     * 
     * @param writer the target writer
     */
    public BibPrinterLispImpl(Writer writer) {

        super();
        this.writer = writer;
    }

    /**
     * Print the database as XML.
     * 
     * @param db the database context
     * 
     * @throws IOException in case of an I/O error
     */
    public void print(DB db) throws IOException {

        writer.print("(database\n");

        Value preamble = db.getPreamble();

        if (preamble != null && !preamble.isEmpty()) {
            writer.print("  (preamble ");
            preamble.visit(this, db);
            writer.print(")\n\n");
        }

        List<String> macros = db.getMacroNames();
        Iterator<String> iterator = macros.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            writer.print("  (string '", encodeSymbol(name), " ");
            db.getMacro(name).visit(this, db);
            writer.print(")\n");
        }

        Iterator<Entry> entryIterator = db.getEntries().iterator();

        while (entryIterator.hasNext()) {
            Entry e = entryIterator.next();
            writer.print("\n  (", encodeXMLtag(e.getType()), " \"");
            writer.print(e.getKey(), "\"");
            iterator = e.getKeys().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                writer.print("\n    (", encodeXMLtag(key), " ");
                e.get(key).visit(this, db);
                writer.print(")");
            }

            writer.print("\n  )\n");
        }

        writer.print("\n)\n");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitBlock(
     *      org.extex.exbib.core.db.VBlock, org.extex.exbib.core.db.DB)
     */
    public void visitBlock(VBlock value, DB db) throws IOException {

        writer.print("\"", encodeArg(value.getContent()), "\"");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitMacro(
     *      org.extex.exbib.core.db.VMacro, org.extex.exbib.core.db.DB)
     */
    public void visitMacro(VMacro value, DB db) throws IOException {

        writer.print("'", encodeArg(value.getContent()).toLowerCase(), " ");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitNumber(
     *      org.extex.exbib.core.db.VNumber, org.extex.exbib.core.db.DB)
     */
    public void visitNumber(VNumber value, DB db) throws IOException {

        writer.print(encodeSymbol(value.getContent()));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.ValueVisitor#visitString(
     *      org.extex.exbib.core.db.VString, org.extex.exbib.core.db.DB)
     */
    public void visitString(VString value, DB db) throws IOException {

        writer.print("\"", encodeArg(value.getContent()), "\"");
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
                writer.print(" ");
            }

            iterator.next().visit(this, db);
        }
    }
}