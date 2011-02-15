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

package org.extex.exindex.core.type.markup;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.type.MarkupContainer;
import org.extex.exindex.core.util.StringUtils;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

/**
 * This class provides a map of arrays with a default value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Markup {

    /**
     * Type-safe index.
     */
    public static class Position {

        /**
         * The field <tt>no</tt> contains the number.
         */
        private int no;

        /**
         * The field <tt>name</tt> contains the name.
         */
        private String name;

        /**
         * Creates a new object.
         * 
         * @param no the number
         * @param name the name
         */
        protected Position(int no, String name) {

            this.no = no;
            this.name = name;
        }

        /**
         * Getter for name.
         * 
         * @return the name
         */
        public String getName() {

            return name;
        }

        /**
         * Getter for no.
         * 
         * @return the no
         */
        public int getNo() {

            return no;
        }

    }

    /**
     * The field <tt>OPEN</tt> contains the position for the open string.
     */
    public static final Position OPEN = new Position(0, ":OPEN");

    /**
     * The field <tt>CLOSE</tt> contains the position for the close string.
     */
    public static final Position CLOSE = new Position(1, ":CLOSE");

    /**
     * The field <tt>SEP</tt> contains the position for the separator string.
     */
    public static final Position SEP = new Position(2, ":SEP");

    /**
     * The field <tt>ATTR</tt> contains the position for the attribute string.
     */
    public static final Position ATTR = new Position(3, ":ATTR");

    /**
     * The field <tt>OPEN_HEAD</tt> contains the position for the open string.
     */
    public static final Position OPEN_HEAD = new Position(3, ":OPEN_HEAD");

    /**
     * The field <tt>CLOSE_HEAD</tt> contains the position for the close
     * string.
     */
    public static final Position CLOSE_HEAD = new Position(4, ":CLOSE_HEAD");

    /**
     * The field <tt>size</tt> contains the size.
     */
    private static final int SIZE = 5;

    /**
     * The field <tt>map</tt> contains the content.
     */
    private Map<String, String[]> map = new HashMap<String, String[]>();

    /**
     * The field <tt>displayName</tt> contains the name of debugging.
     */
    private String displayName;

    /**
     * Creates a new object.
     * 
     * @param displayName the name for debugging
     * @param args the arguments for the defaults
     */
    public Markup(String displayName, String... args) {

        this.displayName = displayName;
        if (args.length > 0) {
            setDefault(args);
        }
    }

    /**
     * Get an element at a certain position. If the position is empty then an
     * attempt is made to use the default value at position <code>null</code>.
     * If everything fails the empty string is returned.
     * 
     * @param key the key
     * @param index the index
     * 
     * @return the specified element or <tt>nil</tt>
     */
    public String get(String key, Position index) {

        String[] value = map.get(key);
        if (value == null && key != null) {
            value = map.get(null);
        }
        return (value == null ? "" : value[index.getNo()]);
    }

    /**
     * Set a value for a key and index.
     * 
     * @param key the key
     * @param value the values to set
     */
    public void set(String key, String... value) {

        String[] a = map.get(key);
        if (a == null) {
            a = new String[SIZE];
            map.put(key, a);
            for (int i = 0; i < SIZE; i++) {
                a[i] = "";
            }
        }

        for (int index = 0; index < value.length && index < SIZE; index++) {
            a[index] = value[index];
        }
    }

    /**
     * Set a default value for an index.
     * 
     * @param value the value to set
     */
    public void setDefault(String... value) {

        String[] a = map.get(null);
        if (a == null) {
            a = new String[SIZE];
            map.put(null, a);
            for (int i = 0; i < SIZE; i++) {
                a[i] = "";
            }
        }

        for (int index = 0; index < value.length && index < SIZE; index++) {
            a[index] = value[index];
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder(displayName);
        sb.append(": {");
        for (String key : map.keySet()) {
            sb.append(" ");
            sb.append(key);
            sb.append("=[");
            for (String val : map.get(key)) {
                sb.append(" ");
                StringUtils.putPrintable(sb, val);
            }
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Write tracing output to a writer.
     * 
     * @param writer the target writer
     * @param markupContainer the container for markup
     * @param clazz the class or <code>null</code>
     * @param index the index
     * @param trace the indicator for tracing
     * @throws IOException in case of an I/O error
     * @throws LNonMatchingTypeException in case of an error
     */
    public void write(Writer writer, MarkupContainer markupContainer,
            String clazz, Position index, boolean trace)
            throws IOException,
                LNonMatchingTypeException {

        writer.write(get(clazz, index));
        if (trace) {
            Markup markupTrace = markupContainer.getMarkup("markup-trace");
            writer.write(markupTrace.get(null, Markup.OPEN));
            writer.write(displayName);
            writer.write(index.getName());
            if (clazz != null) {
                writer.write("[");
                writer.write(clazz);
                writer.write("]");
            }
            writer.write(markupTrace.get(null, Markup.CLOSE));
        }
    }

}
