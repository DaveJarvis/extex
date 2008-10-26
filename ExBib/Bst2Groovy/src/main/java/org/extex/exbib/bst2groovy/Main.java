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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class Main {

    /**
     * This is the main program entry point.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(new Main().run());
    }

    /**
     * Run.
     * 
     * @return the exit code
     */
    public int run() {

        Logger logger = Logger.getLogger(getClass().getName());
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
            bst2Groovy.addBibliographyStyle("alpha.bst");
            bst2Groovy.load();
            OutputStreamWriter w = new OutputStreamWriter(System.out);
            bst2Groovy.write(w);
            w.flush();
        } catch (IOException e) {
            logger.severe(e.toString());
            return -1;
        } catch (RuntimeException e) {
            logger.severe(e.toString());
            return -1;
        } catch (ExBibBstNotFoundException e) {
            logger.severe(e.toString());
            return -1;
        } catch (ExBibException e) {
            logger.severe(e.toString());
            return -1;
        }
        return 0;
    }

}
