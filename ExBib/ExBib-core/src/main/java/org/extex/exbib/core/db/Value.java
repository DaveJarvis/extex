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

package org.extex.exbib.core.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The value is a list of {@link ValueItem ValueItem}s. According to the
 * definition of BibT<sub>E</sub>X a {@link ValueItem ValueItem} can be one of
 * the following
 * <dl>
 * <dt>block</dt>
 * <dd>this is a sequence of characters enclosed in matching braces.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{abc{def}ghi}</pre>
 * 
 * </dd>
 * <dt>string</dt>
 * <dd>this is a sequence of characters enclosed in double quotes.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>"abc{def}ghi"</pre>
 * 
 * </dd>
 * <dt>number</dt>
 * <dd>this is a nonempty sequence of digits
 * <p>
 * Example:
 * </p>
 * 
 * <pre>1234</pre>
 * 
 * </dd>
 * <dt>macro</dt>
 * <dd>this is a sequence of allowed characters, where any character is allowed
 * except whitespace and one of the characters '{', '}', '#', '(', ')', ',', and
 * '"'.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>abc.def-ghi</pre>
 * 
 * </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Value {

    /** The list of items stored in the value */
    private List<ValueItem> value = new ArrayList<ValueItem>();

    /**
     * Creates a new empty object.
     */
    public Value() {

        super();
    }

    /**
     * Creates a new object with one initial element.
     * 
     * @param item the initial element
     */
    public Value(ValueItem item) {

        super();
        add(item);
    }

    /**
     * Add all value items contained in a value to the current value.
     * 
     * @param item the value to append
     */
    public void add(Value item) {

        Iterator<ValueItem> iterator = item.iterator();

        while (iterator.hasNext()) {
            value.add(iterator.next());
        }
    }

    /**
     * Add a new value item to the end of the value.
     * 
     * @param item the value item to add
     */
    public void add(ValueItem item) {

        value.add(item);
    }

    /**
     * Expand the whole value into a single String. All macros are replaced by
     * their values and delimiting braces or quotes are omitted.
     * 
     * @param db the database context
     * 
     * @return the expanded string representation
     */
    public String expand(DB db) {

        StringBuffer sb = new StringBuffer();
        Iterator<ValueItem> iterator = value.iterator();

        while (iterator.hasNext()) {
            iterator.next().expand(sb, db);
        }

        return sb.toString();
    }

    /**
     * Tests whether the value is empty.
     * 
     * @return <code>true</code> iff the value does not contain any elements.
     */
    public boolean isEmpty() {

        return value.isEmpty();
    }

    /**
     * Getter for an Iterator for all elements.
     * 
     * @return the iterator
     */
    public Iterator<ValueItem> iterator() {

        return value.iterator();
    }

    /**
     * Compute a string representation of this object.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        Iterator<ValueItem> iterator = value.iterator();

        while (iterator.hasNext()) {
            if (sb.length() > 0) {
                sb.append('#');
            }

            sb.append(iterator.next().toString());
        }

        return sb.toString();
    }

    /**
     * This is a method which is invoked when this object is visited.
     * 
     * @param visitor the visitor to be informed
     * @param db the database context
     * 
     * @throws IOException in case of an I/O error
     */
    public void visit(ValueVisitor visitor, DB db) throws IOException {

        visitor.visitValue(this, db);
    }
}