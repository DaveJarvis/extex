/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.builder.latex;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * This class provides means to deal with messages to the user.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Message {

    /**
     * The field <tt>bundle</tt> contains the cached resource bundle.
     */
    private static ResourceBundle bundle;

    /**
     * Get a pattern from the resource bundle and merge in the given arguments.
     * 
     * @param key the key
     * @param arguments the optional arguments
     * 
     * @return the formatted string
     */
    public static String get(String key, Object... arguments) {

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(Message.class.getName());
        }
        String pattern = bundle.getString(key);
        return MessageFormat.format(pattern != null ? pattern : key, arguments);
    }

    /**
     * Creates a new object.
     * 
     */
    private Message() {

        // 
    }

}
