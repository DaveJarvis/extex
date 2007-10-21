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

package org.extex.latexParser.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.latexParser.api.NodeList;
import org.extex.latexParser.impl.LaTeXParserImpl;
import org.extex.latexParser.impl.SyntaxError;
import org.extex.latexParser.impl.SystemException;
import org.extex.resource.FileFinder;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.exception.ScannerException;

/**
 * This class provides the entry point for the LaTeX validator. This includes a
 * main program.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LaTeXValidator {

    /**
     * Command line interface to the LaTeX validator.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        for (String source : args) {
            try {
                new LaTeXValidator().run(source);

            } catch (SyntaxError e) {
                System.err.println(source + ":" + e.getLineNumber() + ": "
                        + e.getMessage());
            } catch (SystemException e) {
                System.err.println(source + ": " + e.getCause().toString());
            } catch (ScannerException e) {
                System.err.println(source + ": " + e.getMessage());
            } catch (FileNotFoundException e) {
                System.err.println(source + ": file not found "
                        + e.getMessage());
            } catch (IOException e) {
                System.err.println(source + ": IO error: " + e.toString());
            } catch (RuntimeException e) {
                System.err.println(source + ": " + e.toString());
            }
        }
    }

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object.
     */
    public LaTeXValidator() {

        super();
        Configuration config = ConfigurationFactory.newInstance("path");
        finder = new FileFinder(config);
    }

    /**
     * Perform one run and take care of all exceptions.
     * 
     * @param source the name of the source
     * 
     * @return the node list of the read entities
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    public NodeList run(String source) throws ScannerException, IOException {

        return new LaTeXParserImpl(finder).parse(source);
    }
}
