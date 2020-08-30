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

package org.extex.latexParser.impl.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.InvocationTargetException;

import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Memory;
import org.extex.latexParser.impl.exception.SystemException;
import org.extex.latexParser.impl.macro.GenericMacro;
import org.extex.scanner.api.exception.ScannerException;

/**
 * This utility class provides a means to load a specification of macros or
 * active characters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class DefinitionLoader {

    /**
     * Load a file of specifications.
     * 
     * @param stream the stream
     * @param parser the parser
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    public static void load(InputStream stream, Memory parser)
            throws IOException,
                ScannerException {

        LineNumberReader reader =
                new LineNumberReader(new InputStreamReader(stream));
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.startsWith("\\")) {
                    loadMacro(line, parser);
                } else if (line.matches("[ ]*%.*") || line.matches("[ ]*")) {
                    // ignore comments and empty lines
                } else {
                    loadActive(line, parser);
                }
            }
        } finally {
            reader.close();
        }

    }

    /**
     * Load an active character.
     * 
     * @param s
     * @param parser the parser
     */
    private static void loadActive(String s, Memory parser) {

        char c = s.charAt(0);
        if (s.length() == 1) {
            parser.def(c, new GenericMacro(null));
            return;
        }
        // TODO gene: loadActive unimplemented
        throw new RuntimeException("loadActive unimplemented");
    }

    /**
     * Load a macro.
     * 
     * @param s the string to parse
     * @param parser the parser
     * 
     * @throws SystemException in case of an error
     */
    private static void loadMacro(String s, Memory parser)
            throws SystemException {

        int i = s.indexOf('[', 2);
        if (i > 0) {
            int eq = s.indexOf('=', 2);
            if (eq > 0) {

                // TODO gene: loadMacro unimplemented
                throw new RuntimeException("loadMacro []= unimplemented");
            }

            parser.def(s.substring(1, i), new GenericMacro(s.substring(i)));
            return;
        }
        i = s.indexOf('=', 2);
        if (i > 0) {
            String name = s.substring(1, i);
            parser.def(name, makeMacro(s.substring(i + 1), null));
        }

        parser.def(s.substring(1), new GenericMacro(null));
    }

    /**
     * Create a macro.
     * 
     * @param className the name of the class
     * @param spec the specification
     * 
     * @return the new macro
     * 
     * @throws SystemException in case of an error
     */
    private static Macro makeMacro(String className, String spec)
            throws SystemException {

        try {
            Class<?> theClass = Class.forName(className);
            return (Macro) theClass.getConstructor(String.class).newInstance(
                spec);
        } catch (ClassNotFoundException e) {
            throw new SystemException("class not found: " + className, e);
        } catch (ClassCastException e) {
            throw new SystemException("class cast", e);
        } catch (InstantiationException e) {
            throw new SystemException("instantiation", e);
        } catch (IllegalAccessException e) {
            throw new SystemException("illegal access", e);
        } catch (IllegalArgumentException e) {
            throw new SystemException("illegal argument", e);
        } catch (SecurityException e) {
            throw new SystemException("security", e);
        } catch (InvocationTargetException e) {
            throw new SystemException("invocation target", e);
        } catch (NoSuchMethodException e) {
            throw new SystemException("no such method", e);
        }
    }


    private DefinitionLoader() {

        super();
    }

}
