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

package org.extex.exbib.main.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * The field <tt>options</tt> contains the mapping from actual arguments
     * to closures for options.
     */
    private Map<String, Option> options = new HashMap<String, Option>();

    /**
     * Register handler for options.
     * 
     * @param name the name of the argument
     * @param opt the option
     * 
     * @return <code>true</code> iff something has been registered
     */
    public boolean declare(String name, Option opt) {

        if (name == null) {
            options.put(name, opt);
            return true;
        }
        boolean modified = false;
        String n = name;
        while (options.get(n) == null) {
            options.put(n, opt);
            modified = true;
            int i = n.length();
            if (i <= 1) {
                return true;
            }
            n = n.substring(0, i - 1);
        }
        return modified;
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
     *         a numeric option
     */
    public int run(List<String> args)
            throws UnknownOptionCliException,
                MissingArgumentCliException,
                NonNumericArgumentCliException,
                UnusedArgumentCliException {

        String arg = null;

        while (args.size() > 0) {
            String opt = args.remove(0);
            int i = opt.indexOf('=');
            if (i >= 0) {
                arg = opt.substring(i + 1);
                opt = opt.substring(0, i);
            }
            Option option = options.get(opt);
            if (option == null) {
                // try fallback for unknown options
                option = options.get(null);
            }
            if (option == null) {
                throw new UnknownOptionCliException(opt);
            }
            int ret = (i >= 0 //
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
     *         a numeric option
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

        Map<String, List<String>> xx = new HashMap<String, List<String>>();
        for (String s : options.keySet()) {
            if (s == null) {
                continue;
            }
            Option opt = options.get(s);
            if (opt == null || opt.getTag() == null) {
                continue;
            }
            String tag = opt.getTag();
            List<String> m = xx.get(tag);
            if (m == null) {
                m = new ArrayList<String>();
                xx.put(tag, m);
            }
            m.add(s);
        }

        StringBuilder sb = new StringBuilder();

        List<String> ks = new ArrayList<String>(xx.keySet());
        Collections.sort(ks);
        for (String tag : ks) {
            List<String> list = xx.get(tag);
            Collections.sort(list);
            for (String s : list) {
                sb.append('\t');
                sb.append(s);
                sb.append('\n');
            }
            sb.append("\t\t");
            sb.append(tag);
            sb.append('\n');
        }

        return sb.toString();
    }
}
