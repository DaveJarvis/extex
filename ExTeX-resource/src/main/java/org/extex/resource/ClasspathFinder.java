/*
 * Copyright (C) 2005-2010 The ExTeX Group and individual authors listed below
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

package org.extex.resource;

import java.io.InputStream;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.resource.io.NamedInputStream;

/**
 * This resource finder utilizes the Java class finder to search in the class
 * path. Thus it is possible to find resources inside a jar archive.
 * 
 * <h2>Configuration</h2>
 * <p>
 * The resource finder can be configured to influence its actions. The following
 * example shows a configuration for a resource finder:
 * </p>
 * 
 * <pre>
 * &lt;Finder class="org.extex.util.resource.ClasspathFinder"
 *         trace="false"
 *         default="default"&gt;
 *   &lt;tex&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *     &lt;extension&gt;.tex&lt;/extension&gt;
 *   &lt;/tex&gt;
 *   &lt;fmt&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *     &lt;extension&gt;.fmt&lt;/extension&gt;
 *   &lt;/fmt&gt;
 *   &lt;default&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *   &lt;/default&gt;
 * &lt;/Finder&gt;
 * </pre>
 * 
 * <p>
 * Whenever a resource is sought its type is used to find the appropriate
 * parameters for the search. If the sub-configuration with the name of the type
 * exists then this sub-configuration is used. For instance if the resource
 * <tt>tex</tt> with the type <tt>fmt</tt> is sought then the sub-configuration
 * <tt>fmt</tt> determines how to find this file.
 * </p>
 * <p>
 * If no sub-configuration of the given type is present then the attribute
 * <tt>default</tt> is used to find the default sub-configuration. In the
 * example given above this default configuration is called <tt>default</tt>.
 * Nevertheless it would also be possible to point the default configuration to
 * another existing configuration. The attribute <tt>default</tt> is mandatory.
 * </p>
 * <p>
 * Each sub-configuration takes the tag <tt>extension</tt> in arbitrary number.
 * <tt>extension</tt> contains the extension appended after the resource name.
 * </p>
 * <p>
 * All combinations of resource name and extension are tried in turn. If one
 * combination leads to a readable input stream then it is used.
 * </p>
 * <p>
 * The attribute <tt>trace</tt> can be used to force a tracing of the actions in
 * the log file. The tracing is performed only if a logger is present when
 * needed. The tracing flag can be overwritten at run-time. The attribute
 * <tt>trace</tt> is optional.
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ClasspathFinder extends AbstractFinder {

    /**
     * Creates a new object.
     * 
     * @param configuration the encapsulated configuration object
     * 
     * @throws ConfigurationMissingException in case of an error
     */
    public ClasspathFinder(Configuration configuration)
            throws ConfigurationMissingException {

        super(configuration);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    public NamedInputStream findResource(String name, String type)
            throws ConfigurationException {

        ClassLoader classLoader = this.getClass().getClassLoader();

        Configuration configuration = getConfiguration();
        Configuration cfg = configuration.findConfiguration(type);
        if (cfg == null) {
            String t = configuration.getAttribute(ATTR_DEFAULT);
            if (t == null) {
                throw new ConfigurationMissingAttributeException(ATTR_DEFAULT,
                    configuration);
            }
            cfg = configuration.getConfiguration(t);
            if (cfg == null) {
                return null;
            }
        }
        String t = cfg.getAttribute(ATTR_SKIP);
        if (t != null && Boolean.valueOf(t).booleanValue()) {

            trace("Skipped", type, null);
            return null;
        }
        String prefix = cfg.getAttribute("prefix");
        if (prefix == null) {
            prefix = "";
        }

        for (String ext : cfg.getValues("extension")) {

            String fullName = prefix + name + ext;
            fullName = fullName.replaceAll("\\{type\\}", type);
            trace("Try", fullName, null);
            InputStream stream = classLoader.getResourceAsStream(fullName);

            if (stream != null) {
                trace("Found", fullName, null);
                return new NamedInputStream(stream, "classpath://" + fullName);
            }
            trace("NotFound", fullName, null);
        }
        return null;
    }

}
