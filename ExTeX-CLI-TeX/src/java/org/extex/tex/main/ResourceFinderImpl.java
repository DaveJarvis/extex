/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.tex.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.extex.interpreter.interaction.Interaction;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.i18n.LocalizerFactory;
import org.extex.util.framework.logger.LogEnabled;
import org.extex.util.resource.InteractionAware;
import org.extex.util.resource.InteractionProvider;
import org.extex.util.resource.RecursiveFinder;
import org.extex.util.resource.ResourceFinder;

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
    private InteractionProvider provider = null;

    /**
     * Creates a new object.
     *
     * @param theConfiguration the configuration to use
     *
     * @throws ConfigurationException in case of an error in the configuration
     */
    public ResourceFinderImpl(final Configuration theConfiguration) {

        super();
        this.configuration = theConfiguration;
    }

    /**
     * Setter for the logger.
     *
     * @param theLogger the logger to use
     *
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * Setter for the trace flag.
     * The trace flag is currently ignored.
     *
     * @param flag the trace flag
     *
     * @see org.extex.util.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(final boolean flag) {

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
     * @see org.extex.util.resource.ResourceFinder#findResource(
     *      java.lang.String,
     *      java.lang.String)
     */
    public InputStream findResource(final String name, final String type)
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
        Localizer localizer = LocalizerFactory
                .getLocalizer(ResourceFinderImpl.class);

        if (provider != null) {
            Interaction interaction = provider.getInteraction();
            if (interaction != Interaction.ERRORSTOPMODE) {
                return null;
            }
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
        InputStream stream = parent.findResource(line, type);
        return stream;
    }

    /**
     * Read a line of characters from the standard input stream.
     * Leading spaces are ignored. At end of file <code>null</code> is returned.
     *
     * @return the line read or <code>null</code> to signal EOF
     */
    private String readLine() {

        StringBuffer sb = new StringBuffer();

        try {
            for (int c = System.in.read(); c > 0 && c != '\n'; c = System.in
                    .read()) {

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
     * @param provider the provider
     *
     * @see org.extex.util.resource.InteractionAware#setInteractionProvider(
     *      org.extex.util.resource.InteractionProvider)
     */
    public void setInteractionProvider(final InteractionProvider provider) {

        this.provider = provider;
    }

    /**
     * Setter for the parent resource finder.
     *
     * @param parent the parent resource finder
     *
     * @see org.extex.util.resource.RecursiveFinder#setParent(
     *      org.extex.util.resource.ResourceFinder)
     */
    public void setParent(final ResourceFinder parent) {

        this.parent = parent;
    }

}
