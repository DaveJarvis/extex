/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main.cli;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.extex.exbib.main.cli.exception.MissingArgumentCliException;
import org.extex.exbib.main.cli.exception.NonNumericArgumentCliException;
import org.extex.exbib.main.cli.exception.UnknownOptionCliException;
import org.extex.exbib.main.cli.exception.UnusedArgumentCliException;

/**
 * This is a base class which is able to parse a command line specification.
 * Similar to the idea of SAX parsers handlers can be registered to be informed
 * on the different options encountered.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CLI {

    /**
     * The field <tt>EXIT_FAIL</tt> contains the exit code for failure.
     */
    public static final int EXIT_FAIL = -1;

    /**
     * The field <tt>EXIT_OK</tt> contains the exit code for success.
     */
    public static final int EXIT_OK = 0;

    /**
     * The field <tt>EXIT_CONTINUE</tt> contains the exit code for no exit.
     */
    public static final int EXIT_CONTINUE = Integer.MAX_VALUE;

    /**
     * The field <tt>options</tt> contains the mapping from actual arguments to
     * closures for options.
     */
    private Map<String, Option> options = new HashMap<String, Option>();

    /**
     * Register handler for options. The option is bound to any string starting
     * with the given name unless this name is already bound to an
     * {@link Option}. Thus the sequence of declarations is important.
     * <p>
     * Consider the following invocations:
     * </p>
     * 
     * <pre>
     *   declareOption("--version", option1);
     *   declareOption("--verbose", option2);
     * </pre>
     * <p>
     * Afterwards the options are bound as follows:
     * </p>
     * 
     * <pre>
     *   "" &rarr; option1
     *   "-" &rarr; option1
     *   "--" &rarr; option1
     *   "--v" &rarr; option1
     *   "--ve" &rarr; option1
     *   "--ver" &rarr; option1
     *   "--vers" &rarr; option1
     *   "--versi" &rarr; option1
     *   "--versio" &rarr; option1
     *   "--version" &rarr; option1
     *   "--verb" &rarr; option2
     *   "--verbo" &rarr; option2
     *   "--verbos" &rarr; option2
     *   "--verbose" &rarr; option2
     * </pre>
     * 
     * <p>
     * The name <code>null</code> has a special meaning. It denotes the fallback
     * handler for any option not otherwise recognized. When declaring a default
     * handler any previously existing fallback handler is overwritten.
     * </p>
     * 
     * @param name the name of the argument
     * @param opt the option handler
     * 
     * @return <code>true</code> iff something has been registered
     */
    public boolean declareOption(String name, Option opt) {

        if (name == null) {
            options.put(null, opt);
            return true;
        }
        boolean modified = false;
        String n = name;
        int i = n.length();
        while (options.get(n) == null) {
            options.put(n, opt);
            modified = true;
            if (i <= 1) {
                break;
            }
            n = n.substring(0, --i);
        }
        return modified;
    }

    /**
     * Describe the command line arguments.
     * 
     * @param bundle the resource bundle for the descriptions
     * @param tagBefore the tag of the resource bundle entry to be inserted at
     * the beginning; it can be <code>null</code>; then nothing is inserted at
     * the beginning
     * @param tagAfter the tag of the resource bundle entry to be inserted at
     * the end; it can be <code>null</code>; then nothing is inserted at the end
     * @param args additional arguments for the before and after text
     * 
     * @return the description
     */
    public String describeOptions(ResourceBundle bundle, String tagBefore,
            String tagAfter, Object... args) {

        StringBuilder buffer = new StringBuilder();

        Map<String, List<String>> variantsMap =
                new HashMap<String, List<String>>();
        for (String s : options.keySet()) {
            if (s == null) {
                continue;
            }
            Option opt = options.get(s);
            if (opt == null || opt.getTag() == null) {
                continue;
            }
            String tag = opt.getTag();
            List<String> m = variantsMap.get(tag);
            if (m == null) {
                m = new ArrayList<String>();
                variantsMap.put(tag, m);
            }
            m.add(s);
        }

        if (tagBefore != null) {
            buffer.append(MessageFormat.format(bundle.getString(tagBefore),
                args));
        }
        List<String> keys = new ArrayList<String>(variantsMap.keySet());
        Collections.sort(keys);
        for (String tag : keys) {
            List<String> list = variantsMap.get(tag);
            Collections.sort(list);
            String last = null;
            boolean b = false;

            for (String s : list) {
                if (last == null) {
                    buffer.append('\t');
                    buffer.append(s);
                } else if (s.startsWith(last)) {
                    if (!b) {
                        buffer.append('[');
                        b = true;
                    }
                    buffer.append(s.substring(last.length()));
                } else {
                    if (b) {
                        buffer.append(']');
                        b = false;
                    }
                    buffer.append(" | ");
                    buffer.append(s);
                }
                last = s;
            }

            if (last != null && b) {
                buffer.append(']');
                b = false;
            }
            buffer.append(bundle.getObject(tag));
            buffer.append('\n');
        }
        if (tagAfter != null) {
            buffer.append(MessageFormat
                .format(bundle.getString(tagAfter), args));
        }
        return buffer.toString();
    }

    /**
     * Parse the options from a list of strings and run the callback handlers
     * upon the options found.
     * 
     * @param args the list of arguments
     * 
     * @return the exit code
     * 
     * @throws UnknownOptionCliException in case of an unknown exception
     * @throws MissingArgumentCliException in case of a missing argument
     * @throws UnusedArgumentCliException in case of an superfluous argument
     * @throws NonNumericArgumentCliException in case of a non-numeric value for
     * a numeric option
     */
    public int run(List<String> args)
            throws UnknownOptionCliException,
                MissingArgumentCliException,
                NonNumericArgumentCliException,
                UnusedArgumentCliException {

        String arg = null;

        while (args.size() > 0) {
            String opt = args.remove(0);
            int eq = opt.indexOf('=');
            if (eq >= 0) {
                arg = opt.substring(eq + 1);
                opt = opt.substring(0, eq);
            }
            Option option = options.get(opt);
            if (option == null) {
                // try fallback for unknown options
                option = options.get(null);
                if (option == null) {
                    throw new UnknownOptionCliException(opt);
                }
            }
            int ret = (eq >= 0 //
                    ? option.run(opt, arg, args)
                    : option.run(opt, args));
            if (ret != EXIT_CONTINUE) {
                return ret;
            }
        }

        return EXIT_CONTINUE;
    }

    /**
     * Parse the options from an array of strings and run the callback handlers
     * upon the options found.
     * 
     * @param argv the arguments
     * 
     * @return the exit code
     * 
     * @throws UnknownOptionCliException in case of an unknown exception
     * @throws MissingArgumentCliException in case of a missing argument
     * @throws UnusedArgumentCliException in case of an superfluous argument
     * @throws NonNumericArgumentCliException in case of a non-numeric value for
     * a numeric option
     */
    public int run(String[] argv)
            throws UnknownOptionCliException,
                MissingArgumentCliException,
                NonNumericArgumentCliException,
                UnusedArgumentCliException {

        List<String> args = new ArrayList<String>();
        for (String a : argv) {
            args.add(a);
        }

        return run(args);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return describeOptions(ResourceBundle.getBundle(getClass().getName()),
            null, null);
    }

}
