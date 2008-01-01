/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command.type;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.type.MarkupContainer;
import org.extex.exindex.core.util.StringUtils;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class provides a map of arrays with a default value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkup implements LValue {

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

            super();
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
     * The field <tt>numMap</tt> contains the content.
     */
    private Map<String, int[]> numMap = new HashMap<String, int[]>();

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
    public LMarkup(String displayName, String... args) {

        super();
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
     * Get an element at a certain position. If the position is empty then an
     * attempt is made to use the default value at position <code>null</code>.
     * If everything fails 0 is returned.
     * 
     * @param key the key
     * @param index the index
     * 
     * @return the specified element or <tt>nil</tt>
     */
    public int getNumber(String key, int index) {

        int[] value = numMap.get(key);
        if (value == null && key != null) {
            value = numMap.get(null);
        }
        return (value == null ? 0 : value[index]);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        boolean first = true;
        stream.print("#{");
        for (String key : map.keySet()) {
            String[] a = map.get(key);
            if (first) {
                first = false;
            } else {
                stream.print("\n");
            }
            if (key == null) {
                stream.print("<default>");
            } else {
                new LString(key).print(stream);
            }
            stream.print(" =>");
            if (a == null) {
                stream.print(" nil");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < SIZE; i++) {
                    sb.append(' ');
                    StringUtils.putPrintable(sb, a[i]);
                }
                stream.print(sb.toString());
            }
        }
        stream.print("}");
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
     * Add a value to the end.
     * 
     * @param key the key
     * @param index the index
     * @param value the value to add
     */
    public void setNumber(String key, int index, int value) {

        int[] a = numMap.get(key);
        if (a == null) {
            a = new int[2];
            numMap.put(key, a);
            for (int i = 0; i < SIZE; i++) {
                a[i] = 0;
            }
        }
        a[index] = value;
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
            LMarkup markupTrace = markupContainer.getMarkup("markup-trace");
            writer.write(markupTrace.get(null, LMarkup.OPEN));
            writer.write(displayName);
            writer.write(index.getName());
            if (clazz != null) {
                writer.write("[");
                writer.write(clazz);
                writer.write("]");
            }
            writer.write(markupTrace.get(null, LMarkup.CLOSE));
        }
    }

}
