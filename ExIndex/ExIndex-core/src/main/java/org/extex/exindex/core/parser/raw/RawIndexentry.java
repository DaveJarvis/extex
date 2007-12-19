/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser.raw;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class RawIndexentry {

    /**
     * The field <tt>key</tt> contains the key.
     */
    private Key key;

    /**
     * The field <tt>attribute</tt> contains the attribute.
     */
    private String attribute;

    /**
     * The field <tt>ref</tt> contains the page reference.
     */
    private RefSpec ref;

    /**
     * Creates a new object.
     * 
     * @param key the key; It can not be <code>null</code>
     * @param attribute the attribute or <code>null</code>
     * @param ref the reference
     */
    public RawIndexentry(Key key, String attribute, RefSpec ref) {

        super();
        if (key == null || ref == null) {
            throw new IllegalArgumentException();
        }
        this.key = key;
        this.attribute = attribute;
        this.ref = ref;
    }

    /**
     * Getter for attr.
     * 
     * @return the attr
     */
    public String getAttr() {

        return attribute;
    }

    /**
     * Getter for key.
     * 
     * @return the key
     */
    public Key getKey() {

        return key;
    }

    /**
     * Getter for ref.
     * 
     * @return the ref
     */
    public RefSpec getRef() {

        return ref;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("(indexentry");
        if (key.getPrintKey() == null) {
            toStringAppendList(sb, " :key ", key.getMainKey());
        } else if (key.getMainKey() == key.getPrintKey()) {
            toStringAppendList(sb, " :key ", key.getMainKey());
        } else {
            toStringAppendList(sb, " :key ", key.getMainKey());
            toStringAppendList(sb, " :print ", key.getPrintKey());
        }
        if (attribute != null) {
            sb.append(" :attr ");
            StringUtils.putPrintable(sb, attribute);
        }
        if (ref instanceof XRef) {
            sb.append(" :xref ");
            sb.append(ref.toString());
        } else if (ref instanceof OpenLocRef) {
            sb.append(" :locref ");
            sb.append(ref.toString());
            sb.append(" :open-range");
        } else if (ref instanceof CloseLocRef) {
            sb.append(" :locref ");
            sb.append(ref.toString());
            sb.append(" :close-range");
        } else {
            sb.append(" :locref ");
            sb.append(ref.toString());
        }
        sb.append(")\n");
        return sb.toString();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param sb the buffer
     * @param tag the initial tag
     * @param a the array of values
     */
    private void toStringAppendList(StringBuilder sb, String tag, String[] a) {

        if (a == null) {
            return;
        }
        sb.append(tag);
        sb.append("(");
        boolean first = true;
        for (String s : a) {
            if (first) {
                first = false;
            } else {
                sb.append(" ");
            }
            StringUtils.putPrintable(sb, s);
        }
        sb.append(")");
    }

}
