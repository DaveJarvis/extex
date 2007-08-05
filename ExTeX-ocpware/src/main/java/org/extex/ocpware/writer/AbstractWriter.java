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

package org.extex.ocpware.writer;

import java.text.MessageFormat;
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

    /**
     * Creates a new object.
     */
    public AbstractWriter() {

        super();
        bundle = ResourceBundle.getBundle(getClass().getName());
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

}
