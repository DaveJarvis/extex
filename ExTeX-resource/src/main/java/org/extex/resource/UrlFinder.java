/*
 * Copyright (C) 2004-2008 The ExTeX Group and individual authors listed below
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

package org.extex.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.resource.io.NamedInputStream;

/**
 * This file finder searches for the resource on the net with several
 * extensions.
 * 
 * <h2>Configuration</h2> The file finder can be configured to influence its
 * actions. The following example shows a configuration for a file finder:
 * 
 * <pre>
 * &lt;Finder class="org.extex.resource.FileFinder"
 *         trace="false"
 *         default="default"&gt;
 *   &lt;tex&gt;
 *     &lt;path property="extex.texinputs"/&gt;
 *     &lt;path property="texinputs"/&gt;
 *     &lt;path env="EXTEX_TEXINPUTS"/&gt;
 *     &lt;path env="TEXINPUTS"/&gt;
 *     &lt;path&gt;.&lt;/path&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *     &lt;extension&gt;.tex&lt;/extension&gt;
 *   &lt;/tex&gt;
 *   &lt;fmt&gt;
 *     &lt;path property="extex.texinputs"/&gt;
 *     &lt;path property="texinputs"/&gt;
 *     &lt;path env="EXTEX_TEXINPUTS"/&gt;
 *     &lt;path env="TEXINPUTS"/&gt;
 *     &lt;path&gt;.&lt;/path&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *     &lt;extension&gt;.fmt&lt;/extension&gt;
 *   &lt;/fmt&gt;
 *   &lt;default&gt;
 *     &lt;path property="extex.texinputs"/&gt;
 *     &lt;path property="texinputs"/&gt;
 *     &lt;path env="EXTEX_TEXINPUTS"/&gt;
 *     &lt;path env="TEXINPUTS"/&gt;
 *     &lt;path&gt;.&lt;/path&gt;
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
 * Each sub-configuration takes the tags <tt>path</tt> and <tt>extension</tt> in
 * arbitrary number. <tt>path</tt> contains the path prepended before the
 * resource name. <tt>extension</tt> contains the extension appended after the
 * resource name.
 * </p>
 * <p>
 * <tt>path</tt> can carry the attribute <tt>property</tt>. In this case the
 * value is ignored and the value is taken from the property named in the
 * attribute. Otherwise the value of the tag is taken as path. The value taken
 * from the property can contain several paths. They are separated by the
 * separator specified for the platform. For instance on windows the separator
 * <tt>;</tt> is used and on Unix the separator <tt>:</tt> is used.
 * </p>
 * <p>
 * <tt>path</tt> can carry the attribute <tt>env</tt>. In this case the value is
 * ignored and the value is taken from the environment variable named in the
 * attribute. Otherwise the value of the tag is taken as path. The value taken
 * from the environment variable can contain several paths. They are separated
 * by the separator specified for the platform. For instance on windows the
 * separator <tt>;</tt> is used and on Unix the separator <tt>:</tt> is used.
 * </p>
 * <p>
 * When the full file name contains the string <tt>{type}</tt> this string is
 * replaced by the type currently sought. This can for instance be used in
 * default specification to attach the type as extension.
 * </p>
 * <p>
 * All combinations of path, resource name and extension are tried in turn. If
 * one combination leads to a readable file then an input stream to this file is
 * used.
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
 * @version $Revision: 7167 $
 */
public class UrlFinder extends AbstractFinder
        implements
            PropertyAware,
            EnvironmentAware {

    /**
     * The constant <tt>PATH_TAG</tt> contains the name of the tag to get the
     * path information.
     */
    private static final String PATH_TAG = "path";

    /**
     * The field <tt>properties</tt> contains the properties instance to use.
     */
    private Properties properties = new Properties();

    /**
     * The field <tt>environment</tt> contains the environment.
     */
    private Map<String, String> environment = System.getenv();

    /**
     * Creates a new object.
     * 
     * @param configuration the encapsulated configuration object
     * 
     * @throws ConfigurationMissingException in case of an error
     */
    public UrlFinder(Configuration configuration)
            throws ConfigurationMissingException {

        super(configuration);
    }

    /**
     * Try to find a file on some paths by adding extensions.
     * 
     * @param name the name of the file to find
     * @param paths a list of paths to explore
     * @param cfg the configuration
     * @param type the current type
     * 
     * @return the input stream for the file or <code>null</code> if none was
     *         found.
     */
    private NamedInputStream find(String name, List<String> paths,
            Configuration cfg, String type) {

        for (String p : paths) {
            NamedInputStream stream = find(name, p, cfg, type);
            if (stream != null) {
                return stream;
            }
        }
        return null;
    }

    /**
     * Try to find a file by adding extensions.
     * 
     * @param basename the name of the file to find
     * @param paths the path of the file to find
     * @param cfg the configuration
     * @param type the current type
     * 
     * @return the input stream for the file or <code>null</code> if none was
     *         found.
     */
    private NamedInputStream find(String basename, String paths,
            Configuration cfg, String type) {

        String path = paths.replaceAll("\\{type\\}", type);

        for (String ext : cfg.getValues(EXTENSION_TAG)) {

            String name = (basename + ext).replaceAll("\\{type\\}", type);
            NamedInputStream nis = openFile(path, name);
            if (nis != null) {
                return nis;
            }
            nis = openUrl(path, name);
            if (nis != null) {
                return nis;
            }
        }
        return null;
    }

    /**
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    public NamedInputStream findResource(String name, String type)
            throws ConfigurationException {

        trace("Searching", name, type);

        NamedInputStream stream = null;
        Configuration config = getConfiguration();
        Configuration cfg = config.findConfiguration(type);
        if (cfg == null) {
            String t = config.getAttribute("default");
            if (t == null) {
                throw new ConfigurationMissingAttributeException("default",
                    config);
            }
            cfg = config.getConfiguration(t);

            trace("ConfigurationNotFound", type, t);
        }

        if (new File(name).isAbsolute()) {

            return find(name, "", cfg, type);
        }

        Iterator<Configuration> iterator = cfg.iterator(PATH_TAG);
        while (iterator.hasNext()) {
            Configuration c = iterator.next();
            String path = null;
            String p = c.getAttribute("property");
            if (p != null) {
                path = properties.getProperty(p, null);
                if (path == null) {
                    trace("UndefinedProperty", p, null);
                    continue;
                }
            } else {
                String env = c.getAttribute("env");
                if (env != null) {
                    path = environment.get(env);
                    if (path == null) {
                        trace("UndefinedEnv", env, null);
                        continue;
                    }
                }
            }
            if (path != null) {
                List<String> list = new ArrayList<String>();
                for (String s : path.split(System.getProperty("path.separator",
                    ":"))) {
                    list.add(s);
                }

                stream = find(name, list, cfg, type);
            } else {
                stream = find(name, c.getValue(), cfg, type);
            }
            if (stream != null) {
                return stream;
            }
        }

        trace("Failed", name, null);

        return null;
    }

    /**
     * Try to open a file.
     * 
     * @param path the path of the file to find
     * @param name the name of the file to find
     * 
     * @return the input stream for the file or <code>null</code> if none was
     *         found.
     */
    private NamedInputStream openFile(String path, String name) {

        File file = ("".equals(path) ? new File(name) : new File(path, name));

        trace("Try", file.toString(), null);
        if (file.canRead()) {
            try {
                InputStream stream = new FileInputStream(file);
                trace("Found", file.toString(), null);
                return new NamedInputStream(stream, file.toString());
            } catch (FileNotFoundException e) {
                // Ignore unreadable files.
                // This should not happen since it has already been
                // tested before.
                trace("NotFound", file.toString(), null);
            }
        }
        return null;
    }

    /**
     * Try to open a URL.
     * 
     * @param path the path of the file to find
     * @param name the name of the file to find
     * 
     * @return the input stream for the file or <code>null</code> if none was
     *         found.
     */
    private NamedInputStream openUrl(String path, String name) {

        String n = (path.equals("") || path.endsWith("/") //
        ? path : path + "/") + name;

        if (!n.matches("^[a-z0-9]+://.+")) {
            return null;
        }
        URL url;
        try {
            url = new URL(n);
        } catch (MalformedURLException e) {
            trace("Malformed", n, null);
            return null;
        }

        trace("TryUrl", url.toString(), null);
        try {
            InputStream stream = url.openStream();
            trace("Found", url.toString(), null);
            return new NamedInputStream(stream, url.toString());
        } catch (IOException e) {
            // Ignore unreadable files.
            trace("NotFound", url.toString(), null);
        }
        return null;
    }

    /**
     * Setter for the environment. The default for the environment is the system
     * environment.
     * 
     * @param environment the environment
     */
    public void setEnvironment(Map<String, String> environment) {

        this.environment = environment;
    }

    /**
     * @see org.extex.resource.PropertyAware#setProperties(java.util.Properties)
     */
    public void setProperties(Properties properties) {

        this.properties = properties;
    }

}
