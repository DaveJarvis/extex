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

package org.extex.exbib.core.i18n;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.extex.exbib.core.io.Writer;

/**
 * This class provides means for using externalized strings and formats for
 * messages. The Strings used are read from the resource <tt>config.bcd</tt>
 * or one of its localized variants.
 * 
 * @deprecated use the localizer instead
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
public class Messages {

    /**
     * The field <tt>BUNDLE_NAME</tt> contains the name of the resource bundle
     * to use.
     */
    private static final String BUNDLE_NAME = "config.bcd";

    /**
     * The field <tt>RESOURCE_BUNDLE</tt> contains the the resource bundle for
     * further use.
     */
    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Getter for the value of a format string associated to a given key.
     * 
     * @param key the key in the resource bundle to search for
     * 
     * @return the resource string or the String <tt>???</tt><i>key</i><tt>???</tt>
     *         if none is found
     */
    public static String format(String key) {

        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }

    /**
     * Apply the given argument to the format string stored in the resource
     * bundle under the given key. The argument object's value of toString()
     * replaces the substring <tt>'{0}'</tt> in the format.
     * 
     * @param fmt the key in the resource bundle to search for
     * @param a the Object used for the substring <tt>{0}</tt>
     * 
     * @return the expanded format string
     */
    public static String format(String fmt, Object a) {

        return MessageFormat.format(format(fmt), new Object[]{a});
    }

    /**
     * Apply the given argument to the format string stored in the resource
     * bundle under the given key. The argument object's value of toString()
     * replaces the substring <tt>'{0}'</tt> and <tt>'{1}'</tt> in the
     * format.
     * 
     * @param fmt the key in the resource bundle to search for
     * @param a the Object used for the substring <tt>{0}</tt>
     * @param b the Object used for the substring <tt>{1}</tt>
     * 
     * @return the expanded format string
     */
    public static String format(String fmt, Object a, Object b) {

        return MessageFormat.format(format(fmt), new Object[]{a, b});
    }

    /**
     * Apply the given argument to the format string stored in the resource
     * bundle under the given key. The argument object's value of toString()
     * replaces the substring <tt>'{0}'</tt>, <tt>'{1}'</tt>, and
     * <tt>'{2}'</tt> in the format.
     * 
     * @param fmt the key in the resource bundle to search for
     * @param a the Object used for the substring <tt>{0}</tt>
     * @param b the Object used for the substring <tt>{1}</tt>
     * @param c the Object used for the substring <tt>{2}</tt>
     * 
     * @return the expanded format string
     */
    public static String format(String fmt, Object a, Object b, Object c) {

        return MessageFormat.format(format(fmt), new Object[]{a, b, c});
    }

    /**
     * Get the value of a format string associated to a given key in the
     * resource bundle and print it to the given writer.
     * 
     * @param writer the target output writer
     * @param fmt the key in the resource bundle to search for
     * 
     * @throws IOException in case of an IO error
     */
    public static void message(Writer writer, String fmt) throws IOException {

        writer.println(Messages.format(fmt));
    }

    /**
     * Apply the given argument to the format string stored in the resource
     * bundle under the given key and print the result to a writer. The argument
     * object's value of toString() replaces the substring <tt>'{0}'</tt> in
     * the format.
     * 
     * @param writer the target output writer
     * @param fmt the key in the resource bundle to search for
     * @param a the Object used for the substring <tt>{0}</tt>
     * 
     * @throws IOException in case of an IO error
     */
    public static void message(Writer writer, String fmt, Object a)
            throws IOException {

        writer.println(MessageFormat.format(format(fmt), new Object[]{a}));
    }

    /**
     * Apply the given argument to the format string stored in the resource
     * bundle under the given key and print the result to a writer. The argument
     * object's value of toString() replaces the substring <tt>'{0}'</tt> and
     * <tt>'{1}'</tt> in the format.
     * 
     * @param writer the target output writer
     * @param fmt the key in the resource bundle to search for
     * @param a the Object used for the substring <tt>{0}</tt>
     * @param b the Object used for the substring <tt>{1}</tt>
     * 
     * @throws IOException in case of an IO error
     */
    public static void message(Writer writer, String fmt, Object a, Object b)
            throws IOException {

        writer.println(MessageFormat.format(format(fmt), new Object[]{a, b}));
    }

    /**
     * Apply the given argument to the format string stored in the resource
     * bundle under the given key and print the result to a writer. The argument
     * object's value of toString() replaces the substring <tt>'{0}'</tt>,
     * <tt>'{1}'</tt>, and <tt>'{2}'</tt> in the format.
     * 
     * @param writer the target output writer
     * @param fmt the key in the resource bundle to search for
     * @param a the Object used for the substring <tt>{0}</tt>
     * @param b the Object used for the substring <tt>{1}</tt>
     * @param c the Object used for the substring <tt>{2}</tt>
     * 
     * @throws IOException in case of an IO error
     */
    public static void message(Writer writer, String fmt, Object a, Object b,
            Object c) throws IOException {

        writer
            .println(MessageFormat.format(format(fmt), new Object[]{a, b, c}));
    }

}
