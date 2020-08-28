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

package org.extex.ocpware.writer;

import java.text.MessageFormat;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is an abstract base class for &Omega;CP writers which handles the
 * application of a resource bundle.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractWriter implements OcpWriter {

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;


    public AbstractWriter() {

        bundle = ResourceBundle.getBundle(getClass().getName());
    }

    /**
     * Compute the sum of length of a list of arrays.
     * 
     * @param a a list of arrays
     * 
     * @return the sum of length
     */
    protected int room(List<int[]> a) {

        int mem = 0;
        for (int i = 0; i < a.size(); i++) {
            mem += a.get(i).length;
        }
        return mem;
    }

    /**
     * Apply a message format and handle exceptions gracefully.
     * 
     * @param key the resource key
     * @param a the arguments
     * 
     * @return the formatted result
     */
    protected String format(String key, Object... a) {

        try {
            String fmt = bundle.getString(key);
            return MessageFormat.format(fmt, a);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }

    /**
     * Extract a boolean value from a resource bundle.
     *
     * @param key the key in the resource bundle
     *
     * @return the boolean value; it defaults to <code>false</code>
     */
    protected boolean booleanResource(String key) {

        try {
            String fmt = bundle.getString(key);
            return Boolean.getBoolean(fmt);
        } catch (MissingResourceException e) {
            return false;
        }
    }

}
