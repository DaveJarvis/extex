/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This observer collects information about function calls and prints a
 * statistics.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FuncallObserver implements Observer {

    /**
     * The class Int provides an integer counter.
     * 
     * @author $Author$
     * @version $Revision$
     */
    private static class Int {

        /**
         * The field <tt>value</tt> contains the integer value.
         */
        private int value = 0;

        /**
         * Creates a new object.
         * 
         * @param val the initial value
         */
        public Int(int val) {

            value = val;
        }

        /**
         * Getter for the current value.
         * 
         * @return the value
         */
        public int getValue() {

            return value;
        }

        /**
         * Increment the current value by 1.
         */
        public void inc() {

            value++;
        }

        /**
         * Return a string representation for this object.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return Integer.toString(value);
        }
    }

    /**
     * The field <tt>stat</tt> contains the map to store collected statistics.
     */
    private Map<String, Int> stat = new HashMap<String, Int>();

    /**
     * The field <tt>logger</tt> contains the logger for any output.
     */
    private Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param logger the logger for output
     */
    public FuncallObserver(Logger logger) {

        this.logger = logger;
    }

    /**
     * Print the collected statistics to the writer given to the constructor.
     * 
     * @throws IOException in case something could not be written
     */
    public void print() throws IOException {

        Localizer localizer = LocalizerFactory.getLocalizer(getClass());
        logger.fine(localizer.format("profile"));

        int funcalls = 0;
        List<String> list = new ArrayList<String>(stat.keySet());
        Collections.sort(list);

        for (String key : list) {
            int count = ((stat.get(key))).getValue();
            funcalls += count;
            logger.fine(count + "\t-- " + key);
        }

        logger.fine(localizer.format("total", Integer.valueOf(funcalls)));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.util.Observer#update(org.extex.exbib.core.util.Observable,
     *      java.lang.Object)
     */
    @Override
    public void update(Observable source, Object o) {

        String name = o.toString();

        if (stat.containsKey(name)) {
            stat.get(name).inc();
        } else {
            stat.put(name, new Int(1));
        }
    }

}
