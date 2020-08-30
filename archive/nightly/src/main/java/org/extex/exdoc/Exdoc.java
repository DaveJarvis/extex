/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exdoc;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.extex.exdoc.util.Traverser;

/**
 * Collect the doc snippets from Java code and store them in XML files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class Exdoc {

    /**
     * The main program for this class.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new Exdoc(Traverser.createLogger()).run(args);
    }

    /**
     * The field {@code logger} contains the logger.
     */
    private Logger logger;

    /**
     * Creates a new object.
     *
     * @param logger the logger
     */
    private Exdoc(Logger logger) {

        setLogger(logger);
    }

    /**
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Run the program with some command line arguments.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code; 0 means that no error occurred
     */
    public int run(String[] args) {

        try {
            if (args.length == 0) {
                new ExDocXml(logger).run(args);
            } else if ("-xml".equals(args[0])) {
                new ExDocXml(logger).run(shift(args));
            } else if ("-html".equals(args[0])) {
                new ExDocHtml(logger).run(shift(args));
            } else if ("-tex".equals(args[0])) {
                new ExDocTeX(logger).run(shift(args));
            } else {
                new ExDocXml(logger).run(args);
            }
        } catch (FileNotFoundException e) {
            logger.severe(e.getMessage() + ": File not found");
            return 1;
        } catch (Exception e) {
//            e.printStackTrace();
            logger.severe(e.getMessage());
            return 1;
        }
        return 0;
    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Shift a string array by one position to the left;
     *
     * @param a the array to shift
     *
     * @return the shifted array
     */
    private String[] shift(String[] a) {

        int len = a.length;
        String[] b = new String[len - 1];
        for (int i = 1; i < len; i++) {
            b[i - 1] = a[i];
        }
        return b;
    }

}
