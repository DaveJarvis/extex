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

package org.extex.exbib.core.name;

import java.util.List;

import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;

/**
 * This factory manages names. It implements a pipe of parsed values. This is a
 * tradeoff between the time to parse and the memory to keep the parsed values.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class NameFactory {

    /**
     * This class is a container for a pair consisting of a string and a list of
     * names.
     */
    private static class PipeItem {

        /**
         * The field <tt>key</tt> contains the key.
         */
        private String key;

        /**
         * The field <tt>value</tt> contains the value.
         */
        private List<Name> value;

        /**
         * Creates a new object.
         * 
         * @param key the key
         * @param value the value
         */
        protected PipeItem(String key, List<Name> value) {

            super();
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return key + " -> " + value.toString() + "\n";
        }

    }

    /**
     * The field <tt>PIPE_SIZE</tt> contains the size of the cache.
     */
    private static final int PIPE_SIZE = 8;

    /**
     * The field <tt>highlander</tt> contains the only one.
     */
    private static NameFactory highlander = new NameFactory();

    /**
     * Getter for the only instance.
     * 
     * @return the only instance
     */
    public static NameFactory getFactory() {

        return highlander;
    }

    /**
     * The field <tt>pipe</tt> contains the cache.
     */
    private PipeItem[] pipe = new PipeItem[PIPE_SIZE];

    /**
     * The field <tt>pp</tt> contains the pointer to the next insertion point
     * in the pipe.
     */
    private int pp = 0;

    /**
     * Creates a new object.
     */
    private NameFactory() {

        //
    }

    /**
     * Getter for the name list associated with this object. The name list can
     * be used to store the result of parsing the value to names. Thus it works
     * as cache.
     * 
     * @param s the string to be decomposed into names
     * @param locator the locator for error messages
     * 
     * @return the names which is never <code>null</code>
     * 
     * @throws ExBibNoNameException in case of an error
     * @throws ExBibSyntaxException in case of an error
     * @throws ExBibImpossibleException in case of an error which should not
     *         happen
     */
    public List<Name> getNames(String s, Locator locator)
            throws ExBibSyntaxException,
                ExBibNoNameException,
                ExBibImpossibleException {

        String key = s.trim();

        for (int i = 0; i < PIPE_SIZE; i++) {
            PipeItem pi = pipe[i];
            if (pi != null && pi.key.equals(s)) {
                pp = (i + 1) % PIPE_SIZE;
                return pi.value;
            }
        }

        List<Name> names = Name.parse(key, locator);
        if (pipe[pp] == null) {
            pipe[pp] = new PipeItem(key, names);
        } else {
            pipe[pp].key = key;
            pipe[pp].value = names;
        }

        return names;
    }

}
