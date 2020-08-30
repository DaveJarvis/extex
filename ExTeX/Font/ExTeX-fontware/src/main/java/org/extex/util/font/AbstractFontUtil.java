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

package org.extex.util.font;

import java.io.File;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.ExTeX;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.logging.LogFormatter;

/**
 * Abstract class for all font utilities.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class AbstractFontUtil {

    /**
     * The console handler.
     */
    private Handler consoleHandler;

    /**
     * The field {@code localizer} contains the localizer. It is initiated with
     * a localizer for the name of this class.
     */
    private Localizer localizer = LocalizerFactory
        .getLocalizer(AbstractFontUtil.class);

    /**
     * The field {@code logger} contains the logger currently in use.
     */
    private Logger logger;

    /**
     * The directory for the output.
     */
    private String outdir = ".";

    /**
     * Create a new object.
     */
    public AbstractFontUtil() {

        this(AbstractFontUtil.class);
    }

    /**
     * Create a new object.
     * 
     * @param loggerclass The class for the logger
     */
    public AbstractFontUtil(Class<?> loggerclass) {

        // logger
        logger = Logger.getLogger(loggerclass.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);

        localizer = LocalizerFactory.getLocalizer(loggerclass);

    }

    /**
     * Create the comment with ExTeX-version and date.
     * 
     * @param key The key for the property.
     * @return Returns the comment with the ExTeX-Version and the date.
     */
    protected String createVersion(String key) {

        Calendar cal = Calendar.getInstance();
        return getLocalizer().format(key, ExTeX.getVersion(),
            cal.getTime().toString());
    }

    /**
     * Getter for consoleHandler.
     * 
     * @return the consoleHandler
     */
    public Handler getConsoleHandler() {

        return consoleHandler;
    }

    /**
     * Getter for localizer.
     * 
     * @return the localizer
     */
    public Localizer getLocalizer() {

        return localizer;
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
     * Getter for outdir.
     * 
     * @return the outdir
     */
    public String getOutdir() {

        return outdir;
    }

    /**
     * Returns only the name of a file.
     * 
     * @param f The file.
     * @return Returns only the name of a file.
     */
    protected String removeExtensions(File f) {

        return removeExtensions(f.getName());
    }

    /**
     * Returns only the name of a file.
     * 
     * @param n The name.
     * @return Returns only the name of a file.
     */
    protected String removeExtensions(String n) {

        StringBuilder buf = new StringBuilder();

        boolean dotfound = false;
        for (int i = n.length() - 1; i >= 0; i--) {
            char c = n.charAt(i);
            if (!dotfound && c == '.') {
                dotfound = true;
                continue;
            }
            if (dotfound) {
                if (c == '/' || c == '\\') {
                    break;
                }
                buf.insert(0, c);
            }
        }

        return buf.toString();
    }

    /**
     * Setter for outdir.
     * 
     * @param outdir the outdir to set
     */
    public void setOutdir(String outdir) {

        this.outdir = outdir;
    }

}
