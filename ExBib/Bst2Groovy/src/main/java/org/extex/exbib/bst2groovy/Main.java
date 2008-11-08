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

package org.extex.exbib.bst2groovy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.cli.CLI;
import org.extex.cli.NoArgOption;
import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.NonNumericArgumentCliException;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.cli.exception.UnusedArgumentCliException;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This is the main program for the BST to Groovy compiler.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Main extends CLI {

    /**
     * Process the command line options and return the exit code.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code
     */
    private static int commandLine(String[] args) {

        try {
            return new Main().run(args);
        } catch (UnknownOptionCliException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        } catch (MissingArgumentCliException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        } catch (NonNumericArgumentCliException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        } catch (UnusedArgumentCliException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        }
    }

    /**
     * This is the main program entry point.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(commandLine(args));
    }

    /**
     * The field <tt>out</tt> contains the output file name or <code>null</code>
     * for stdout.
     */
    private String out = null;

    /**
     * The field <tt>in</tt> contains the inout file name or null for stdin..
     */
    private String in = null;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Creates a new object.
     * 
     */
    public Main() {

        declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String arg) throws UnknownOptionCliException {

                if (arg.startsWith("-")) {
                    throw new UnknownOptionCliException(arg);
                }
                in = arg;
                return CLI.EXIT_CONTINUE;
            }

            @Override
            public int run(String a, String arg, List<String> args)
                    throws UnusedArgumentCliException,
                        UnknownOptionCliException {

                if (a.startsWith("-")) {
                    throw new UnknownOptionCliException(a);
                }
                throw new UnusedArgumentCliException(a);
            }
        });
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return the exit code
     */
    private int run() {

        logger.setLevel(Level.SEVERE);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        try {
            Bst2Groovy bst2Groovy = new Bst2Groovy();
            ResourceFinderFactory rff = new ResourceFinderFactory();
            ResourceFinder finder =
                    rff.createResourceFinder(ConfigurationFactory
                        .newInstance("config/path/testFinder"), logger, System
                        .getProperties(), null);
            // finder.enableTracing(true);
            bst2Groovy.setResourceFinder(finder);
            bst2Groovy.addBibliographyStyle(in);
            bst2Groovy.load();
            OutputStreamWriter w =
                    new OutputStreamWriter(out == null || "-".equals(out)
                            ? System.out
                            : new FileOutputStream(out));
            bst2Groovy.write(w);
            w.flush();
            w.close();
        } catch (IOException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        } catch (RuntimeException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        } catch (ExBibBstNotFoundException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        } catch (ExBibException e) {
            logger.severe(e.toString());
            return CLI.EXIT_FAIL;
        }
        return CLI.EXIT_OK;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.cli.CLI#run(java.lang.String[])
     */
    @Override
    public int run(String[] argv)
            throws UnknownOptionCliException,
                MissingArgumentCliException,
                NonNumericArgumentCliException,
                UnusedArgumentCliException {

        super.run(argv);
        return run();
    }

}
