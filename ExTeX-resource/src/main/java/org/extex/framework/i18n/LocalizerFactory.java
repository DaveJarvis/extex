/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.framework.i18n;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This factory provides means to get a {@link Localizer}. It implements the
 * singleton pattern by providing static methods only.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class LocalizerFactory {

    /**
     * This inner class is the one and only implementation of a Localizer
     * delivered by this factory.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static class BasicLocalizer implements Localizer {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 2011L;

        /**
         * The field {@code bundle} contains the resource bundle for this
         * instance or {@code null} if none has been loaded yet.
         */
        private transient ResourceBundle bundle = null;

        /**
         * The field {@code bundleName} contains the name of the resource
         * bundle to use.
         */
        private String bundleName;

        /**
         * Creates a new object.
         * 
         * @param name name of the resource bundle
         */
        public BasicLocalizer(String name) {

            bundleName = name;
        }

        /**
         * Getter for the value of a format string associated to a given key.
         * 
         * @param key the key in the resource bundle to search for
         * @return the resource string or the String {@code ???}<i>key</i>
         *         {@code ???} if none is found
         */
        @Override
        public String format(String key) {

            if (bundle == null) {
                bundle = ResourceBundle.getBundle(bundleName);
            }
            try {
                return bundle.getString(key);
            } catch (MissingResourceException e) {
                return "???" + key + "???";
            }
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key. The argument object's value of toString()
         * replaces the substring {@code '{0}'} in the format.
         * 
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * 
         * @return the expanded format string
         */
        @Override
        public String format(String fmt, Object a) {

            return MessageFormat.format(format(fmt), new Object[]{a});
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key. The argument object's value of toString()
         * replaces the substring {@code '{0}'} and {@code '{1}'} in the
         * format.
         * 
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * @param b the Object used for the substring {@code {1}}
         * 
         * @return the expanded format string
         */
        @Override
        public String format(String fmt, Object a, Object b) {

            return MessageFormat.format(format(fmt), new Object[]{a, b});
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key. The argument object's value of toString()
         * replaces the substring {@code '{0}'},{@code '{1}'}, and
         * {@code '{2}'} in the format.
         * 
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * @param b the Object used for the substring {@code {1}}
         * @param c the Object used for the substring {@code {2}}
         * 
         * @return the expanded format string
         */
        @Override
        public String format(String fmt, Object a, Object b, Object c) {

            return MessageFormat.format(format(fmt), new Object[]{a, b, c});
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key. The argument object's value of toString()
         * replaces the substring {@code '{0}'},{@code '{1}'},{@code '{2}'},
         * and {@code '{3}'} in the format.
         * 
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * @param b the Object used for the substring {@code {1}}
         * @param c the Object used for the substring {@code {2}}
         * @param d the Object used for the substring {@code {3}}
         * 
         * @return the expanded format string
         */
        @Override
        public String format(String fmt, Object a, Object b, Object c, Object d) {

            return MessageFormat.format(format(fmt), new Object[]{a, b, c, d});
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key. The argument object's value of toString()
         * replaces the substring {@code '{0}'},{@code '{1}'},{@code '{2}'},
         * and {@code '{3}'} in the format.
         * 
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * @param b the Object used for the substring {@code {1}}
         * @param c the Object used for the substring {@code {2}}
         * @param d the Object used for the substring {@code {3}}
         * @param e the Object used for the substring {@code {4}}
         * 
         * @return the expanded format string
         */
        @Override
        public String format(String fmt, Object a, Object b, Object c,
                Object d, Object e) {

            return MessageFormat.format(format(fmt),
                new Object[]{a, b, c, d, e});
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key. The argument object's value of toString()
         * replaces the substring {@code '{0}'},{@code '{1}'},{@code '{2}'},
         * and so on in the format.
         * 
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substrings {@code {<i>n</i>}}
         * 
         * @return the expanded format string
         */
        @Override
        public String format(String fmt, Object[] a) {

            return MessageFormat.format(format(fmt), a);
        }

        /**
         * Getter for the value of a format string associated to a given key.
         * 
         * @param key the key in the resource bundle to search for
         * 
         * @return the resource string or {@code null}
         */
        @Override
        public String getFormat(String key) {

            if (bundle == null) {
                bundle = ResourceBundle.getBundle(bundleName);
            }
            try {
                return bundle.getString(key);
            } catch (MissingResourceException e) {
                return null;
            }
        }

        /**
         * Get the value of a format string associated to a given key in the
         * resource bundle and print it to the given writer.
         * 
         * @param writer the target output writer
         * @param fmt the key in the resource bundle to search for
         */
        @Override
        public void message(PrintStream writer, String fmt) {

            writer.println(format(fmt));
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key and print the result to a writer. The
         * argument object's value of toString() replaces the substring
         * {@code '{0}'} in the format.
         * 
         * @param writer the target output writer
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         */
        @Override
        public void message(PrintStream writer, String fmt, Object a) {

            writer.println(MessageFormat.format(format(fmt), new Object[]{a}));
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key and print the result to a writer. The
         * argument object's value of toString() replaces the substring
         * {@code '{0}'} and {@code '{1}'} in the format.
         * 
         * @param writer the target output writer
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * @param b the Object used for the substring {@code {1}}
         */
        @Override
        public void message(PrintStream writer, String fmt, Object a, Object b) {

            writer.println(MessageFormat
                .format(format(fmt), new Object[]{a, b}));
        }

        /**
         * Apply the given argument to the format string stored in the resource
         * bundle under the given key and print the result to a writer. The
         * argument object's value of toString() replaces the substring
         * {@code '{0}'}, {@code '{1}'}, and {@code '{2}'} in the format.
         * 
         * @param writer the target output writer
         * @param fmt the key in the resource bundle to search for
         * @param a the Object used for the substring {@code {0}}
         * @param b the Object used for the substring {@code {1}}
         * @param c the Object used for the substring {@code {2}}
         */
        @Override
        public void message(PrintStream writer, String fmt, Object a, Object b,
                Object c) {

            writer.println(MessageFormat.format(format(fmt), new Object[]{a, b,
                    c}));
        }

    }

    /**
     * The field {@code cache} contains the map of localizers already
     * constructed. The localizers are cached to minimize the overhead of
     * acquiring the same localizer several times.
     */
    private static final Map<String, Localizer> CACHE =
            new HashMap<String, Localizer>();

    /**
     * The field {@code locale} contains the locale to use.
     */
    private static Locale locale = Locale.getDefault();

    /**
     * Return the {@link Localizer} associated to a given name.
     * 
     * @param theClass the class of the localizer
     * 
     * @return the localizer for the given name
     */
    public static Localizer getLocalizer(Class<?> theClass) {

        return getLocalizer(theClass.getName());
    }

    /**
     * Return the {@link Localizer} associated to a given name.
     * 
     * @param name the name of the localizer
     * 
     * @return the localizer for the given name
     */
    public static Localizer getLocalizer(String name) {

        if (locale != Locale.getDefault()) {
            locale = Locale.getDefault();
            CACHE.clear();
        }
        Localizer loc = CACHE.get(name);
        if (loc == null) {
            loc = new BasicLocalizer(name);
            CACHE.put(name, loc);
        }
        return loc;
    }

    /**
     * Creates a new object. The constructor is private to avoid abuse.
     */
    private LocalizerFactory() {

        // not used
    }

}
