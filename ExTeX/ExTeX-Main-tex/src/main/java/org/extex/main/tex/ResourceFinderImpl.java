/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.main.tex;

import java.io.IOException;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.InteractionAware;
import org.extex.resource.InteractionIndicator;
import org.extex.resource.RecursiveFinder;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;

/**
 * This ResourceFinder queries the user for the name of the file to use and
 * tries to find it via its parent.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ResourceFinderImpl
        implements
            ResourceFinder,
            RecursiveFinder,
            LogEnabled,
            InteractionAware {

    /**
     * The field <tt>configuration</tt> contains the currently used
     * configuration.
     */
    private Configuration configuration;

    /**
     * The field <tt>logger</tt> contains the current logger or
     * <code>null</code> if none has been set yet.
     */
    private Logger logger = null;

    /**
     * The field <tt>parent</tt> contains the parent resource finder or
     * <code>null</code> if none has been set yet.
     */
    private ResourceFinder parent = null;

    /**
     * The field <tt>provider</tt> contains the interaction provider.
     */
    private InteractionIndicator provider = null;

    /**
     * Creates a new object.
     * 
     * @param theConfiguration the configuration to use
     */
    public ResourceFinderImpl(Configuration theConfiguration) {

        this.configuration = theConfiguration;
    }

    /**
     * Setter for the logger.
     * 
     * @param theLogger the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
    public void enableLogging(Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * Setter for the trace flag. The trace flag is currently ignored.
     * 
     * @param flag the trace flag
     * 
     * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
     */
    @Override
    public void enableTracing(boolean flag) {

        // not supported
    }

    /**
     * Find a resource which can be used for reading. If the search fails then
     * <code>null</code> is returned.
     * 
     * @param name the base name of the resource
     * @param type the type, i.e. the extension
     * 
     * @return the file or <code>null</code> if none could be found
     * 
     * @throws ConfigurationException in case of an exception
     * 
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public NamedInputStream findResource(String name, String type)
            throws ConfigurationException {

        Configuration cfg = configuration.findConfiguration(type);
        if (cfg == null) {
            cfg = configuration.findConfiguration("default");
            if (cfg == null) {
                return null;
            }
        }
        String skip = cfg.getAttribute("skip");
        if (skip != null && Boolean.valueOf(skip).booleanValue()) {
            return null;
        }

        String line = name;
        Localizer localizer =
                LocalizerFactory.getLocalizer(ResourceFinderImpl.class);

        if (provider != null && !provider.isInteractive()) {
            return null;
        }

        if (!line.equals("")) {
            logger.severe("\n! "
                    + localizer.format("CLI.FileNotFound", name, type));
        }

        logger.severe(localizer.format("CLI.PromptFile"));
        line = readLine();

        if (line == null) {
            return null;
        }
        return parent.findResource(line, type);
    }

    /**
     * Read a line of characters from the standard input stream. Leading spaces
     * are ignored. At end of file <code>null</code> is returned.
     * 
     * @return the line read or <code>null</code> to signal EOF
     */
    private String readLine() {

        StringBuilder sb = new StringBuilder();

        try {
            for (int c = System.in.read(); c > 0 && c != '\n'; c =
                    System.in.read()) {

                if (c != '\r' && (c != ' ' || sb.length() > 0)) {
                    sb.append((char) c);
                }
            }
        } catch (IOException e) {
            return null;
        }

        return (sb.length() > 0 ? sb.toString() : null);
    }

    /**
     * Setter for the interaction provider.
     * 
     * @param interactionProvider the provider
     * 
     * @see org.extex.resource.InteractionAware#setInteractionProvider(org.extex.resource.InteractionIndicator)
     */
    @Override
    public void setInteractionProvider(InteractionIndicator interactionProvider) {

        this.provider = interactionProvider;
    }

    /**
     * Setter for the parent resource finder.
     * 
     * @param parent the parent resource finder
     * 
     * @see org.extex.resource.RecursiveFinder#setParent(org.extex.resource.ResourceFinder)
     */
    @Override
    public void setParent(ResourceFinder parent) {

        this.parent = parent;
    }

}
