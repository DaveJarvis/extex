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
import java.util.logging.Logger;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.latexParser.api.NodeList;
import org.extex.latexParser.impl.LaTeXParserImpl;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.exception.SystemException;
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

        new LaTeXValidator().run(args);
    }

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration problem
     */
    public LaTeXValidator() throws ConfigurationException {

        super();
        Configuration config = ConfigurationFactory.newInstance("path");
        finder = new FileFinder(config);
    }

    /**
     * Perform one run and take care of all exceptions.
     * 
     * @param source the name of the source
     * @param logger the logger
     * 
     * @return the node list of the read entities or <code>null</code> in case
     *         of an error
     */
    public NodeList run(String source, Logger logger) {

        try {
            return new LaTeXParserImpl(finder, logger).parse(source);
        } catch (SyntaxError e) {
            logger.severe(source + ":" + e.getLineNumber() + ": "
                    + e.getMessage() + "\n");
        } catch (SystemException e) {
            logger.severe(source + ": " + e.getCause().toString() + "\n");
        } catch (ScannerException e) {
            logger.severe(source + ": " + e.getMessage() + "\n");
        } catch (FileNotFoundException e) {
            logger.severe(source + ": file not found " + e.getMessage() + "\n");
        } catch (IOException e) {
            logger.severe(source + ": IO error: " + e.toString() + "\n");
        } catch (RuntimeException e) {
            logger.severe(source + ": " + e.toString() + "\n");
        }
        return null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args
     */
    private void run(String[] args) {

        Logger logger = Logger.getLogger("LaTeXValidator");

        for (String source : args) {
            run(source, logger);
        }
    }
}
