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
import org.extex.latexParser.impl.LaTeXParserImpl;
import org.extex.latexParser.impl.SyntaxError;
import org.extex.resource.FileFinder;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.exception.ScannerException;

/**
 * TODO gene: missing JavaDoc.
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

        for (String s : args) {
            new LaTeXValidator().run(s);
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
     * TODO gene: missing JavaDoc
     * 
     * @param source the name of the source
     */
    private void run(String source) {

        try {
            LaTeXParserImpl parser = new LaTeXParserImpl();
            parser.setResourceFinder(finder);

            parser.parse(source);

        } catch (SyntaxError e) {
            System.err.println(source + ": " + e.getMessage());
        } catch (ScannerException e) {
            System.err.println(source + ": " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(source + ": file not found");
        } catch (IOException e) {
            System.err.println(source + ": IO error: " + e.toString());
        } catch (RuntimeException e) {
            System.err.println(source + ": " + e.toString());
        }
    }
}
