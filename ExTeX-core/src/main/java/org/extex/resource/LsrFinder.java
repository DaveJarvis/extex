/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This resource finder searches a file in a <tt>ls-R</tt> file database as
 * present in a texmf tree. For this purpose the <tt>ls-R</tt> file databases
 * found are read and stored internally.
 *
 * <h2>Configuration</h2>
 * The lsr finder can be configured to influence its actions. The following
 * example shows a configuration for a lsr finder:
 *
 * <pre>
 * &lt;Finder class=&quot;de.dante.util.resource.LsrFinder&quot;
 *          default=&quot;default&quot;
 *          capacity=&quot;1234567&quot;
 *          trace=&quot;false&quot;&gt;
 *   &lt;path property=&quot;extex.font.path&quot;&gt;&lt;/path&gt;
 *   &lt;path property=&quot;texmf.path&quot;&gt;&lt;/path&gt;
 *   &lt;tfm&gt;&lt;extension&gt;.tfm&lt;/extension&gt;&lt;/tfm&gt;
 *   &lt;efm&gt;&lt;extension&gt;.efm&lt;/extension&gt;&lt;/efm&gt;
 *   &lt;pfb&gt;&lt;extension&gt;.pfb&lt;/extension&gt;&lt;/pfb&gt;
 *   &lt;ttf&gt;&lt;extension&gt;.ttf&lt;/extension&gt;&lt;/ttf&gt;
 *   &lt;default&gt;&lt;extension/&gt;&lt;/default&gt;
 * &lt;/Finder&gt;
 * </pre>
 *
 * <p>
 * Whenever a resource is sought the first step is to ensure that the file
 * databases are read in. For this purpose the <tt>path</tt> tag is used. The
 * <tt>path</tt> tags name directories which may contain file databases. The
 * file databases have a fixed name <tt>ls-R</tt>.
 * </p>
 * <p>
 * <tt>path</tt> can carry the attribute <tt>property</tt>. In this case
 * the value is ignored and the value is taken from the property named in the
 * attribute. Otherwise the value of the tag is taken as path. The value taken
 * from the property can contain several paths. They are separated by the
 * separator specified for the platform. For instance on windows the separator
 * <tt>;</tt> is used and on Unix the separator <tt>:</tt> is used.
 * </p>
 * <p>
 * To find a resource its type is used to find the appropriate parameters for
 * the search. If the sub-configuration with the name of the type exists then
 * this sub-configuration is used. For instance if the resource <tt>tex</tt>
 * with the type <tt>fmt</tt> is sought then the sub-configuration
 * <tt>fmt</tt> determines how to find this file.
 * </p>
 * <p>
 * If no sub-configuration of the given type is present then the attribute
 * <tt>default</tt> is used to find the default sub-configuration. In the
 * example given above this default configuration is called <tt>default</tt>.
 * Nevertheless it would also be possible to point the default configuration to
 * another existing configuration. The attribute <tt>default</tt> is
 * mandatory.
 * </p>
 * <p>
 * Each sub-configuration takes the <tt>extension</tt> in arbitrary number.
 * <tt>extension</tt> contains the extension appended after the resource name.
 * </p>
 * <p>
 * All combinations of resource name and extension are tried in turn to be found
 * in the file database. If one combination leads to a readable file then an
 * input stream to this file is used.
 * </p>
 * <p>
 * The attribute <tt>trace</tt> can be used to force a tracing of the actions
 * in the log file. The tracing is performed only if a logger is present when
 * needed. The tracing flag can be overwritten at run-time. The attribute
 * <tt>trace</tt> is optional.
 * </p>
 * <p>
 * The attribute <tt>capacity</tt> can be used to configure the initial
 * capacity of the internal cache for the file database. If this number is less
 * than one than an internal default is used. This value should be larger than
 * the number of files expected for best performance. The attribute
 * <tt>capacity</tt> is optional.
 * </p>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LsrFinder extends AbstractFinder implements PropertyConfigurable {

    /**
     * The field <tt>ATTR_PROPERTY</tt> contains the attribute name for the
     * property access.
     */
    private static final String ATTR_PROPERTY = "property";

    /**
     * The field <tt>INITIAL_LIST_SIZE</tt> contains the initial size of the
     * collision list items in the cache.
     */
    private static final int INITIAL_LIST_SIZE = 3;

    /**
     * The field <tt>LSR_FILE_NAME</tt> contains the name of the ls-R file.
     */
    private static final String LSR_FILE_NAME = "ls-R";

    /**
     * The field <tt>TAG_PATH</tt> contains the name of the tag to identify
     * paths.
     */
    private static final String TAG_PATH = "path";

    /**
     * The field <tt>cache</tt> contains the map for the ls-R entries.
     */
    private Map<String, Object> cache = null;

    /**
     * The field <tt>initialCapacity</tt> contains the initial capacity of the
     * cache. If the value is less than 1 then the default of the underling
     * implementation is used.
     */
    private int initialCapacity = -1;

    /**
     * The field <tt>properties</tt> contains the properties provided for this
     * finder.
     */
    private Properties properties = System.getProperties();

    /**
     * Creates a new object.
     *
     * @param configuration the encapsulated configuration object
     *
     * @throws ConfigurationMissingException in case of an error
     */
    public LsrFinder(Configuration configuration)
            throws ConfigurationMissingException {

        super(configuration);

        String a = configuration.getAttribute("capacity");
        if (a != null && !"".equals(a)) {
            try {
                this.initialCapacity = Integer.parseInt(a);
            } catch (NumberFormatException e) {
                this.initialCapacity = -1;
            }
        }
    }

    /**
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public InputStream findResource(String name, String type)
            throws ConfigurationException {

        trace("Searching", name, type, null);

        if (cache == null) {
            initialize();
        }

        Configuration config = getConfiguration();
        Configuration cfg = config.findConfiguration(type);
        if (cfg == null) {
            String t = config.getAttribute(ATTR_DEFAULT);
            if (t == null) {
                throw new ConfigurationMissingAttributeException(ATTR_DEFAULT,
                    config);
            }
            cfg = config.getConfiguration(t);
            if (cfg == null) {
                trace("DefaultNotFound", type, t, null);
                return null;
            }
            trace("ConfigurationNotFound", type, t, null);
        }
        String t = config.getAttribute("skip");
        if (t != null && Boolean.valueOf(t).booleanValue()) {

            trace("Skipped", type, null, null);
            return null;
        }

        for (String ext : cfg.getValues(EXTENSION_TAG)) {

            Object c = cache.get(name + ext);
            if (c == null) {
                continue;
            } else if (c instanceof File) {
                File file = (File) c;
                trace("Try", file.toString(), null, null);
                if (file.canRead()) {
                    try {
                        InputStream stream = new FileInputStream(file);
                        trace("Found", file.toString(), null, null);
                        return stream;
                    } catch (FileNotFoundException e) {
                        // ignore unreadable files
                        trace("FoundUnreadable", file.toString(), null, null);
                        continue;
                    }
                }
            } else {

                for (File file : (List<File>) c) {
                    if (file != null) {
                        trace("Try", file.toString(), null, null);
                        if (!file.canRead()) {
                            try {
                                InputStream stream = new FileInputStream(file);
                                trace("Found", file.toString(), null, null);
                                return stream;
                            } catch (FileNotFoundException e) {
                                // ignore unreadable files
                                // this should not happen since it has been
                                // checked before
                                trace("FoundUnreadable", file.toString(), null,
                                    null);
                                continue;
                            }
                        }
                    }
                }
            }
        }
        trace("Failed", name, null, null);
        return null;
    }

    /**
     * Load the external cache file into memory.
     *
     * @throws ConfigurationException in case of an error
     */
    private void initialize() throws ConfigurationException {

        Configuration config = getConfiguration();
        Iterator<Configuration> it = config.iterator(TAG_PATH);
        if (!it.hasNext()) {
            throw new ConfigurationMissingException(TAG_PATH, config.toString());
        }

        cache =
                (initialCapacity > 0 ? new HashMap<String, Object>(
                    initialCapacity) : new HashMap<String, Object>());

        while (it.hasNext()) {
            Configuration cfg = it.next();
            String pathProperty = cfg.getAttribute(ATTR_PROPERTY);
            String name;
            if (pathProperty != null) {
                name = properties.getProperty(pathProperty);
                if (name == null) {
                    trace("UndefinedProperty", pathProperty, null, null);
                } else {
                    for (String s : name.split(System.getProperty(
                        "path.separator", ":"))) {
                        load(s);
                    }
                }
            } else {
                name = cfg.getValue();
                if (name != null && !name.equals("")) {
                    load(name);
                }
            }
        }
    }

    /**
     * Load the ls-R file.
     *
     * @param path the path for the ls-R file
     *
     * @throws ConfigurationException if an error occurred
     */
    @SuppressWarnings("unchecked")
    private void load(String path) throws ConfigurationException {

        long start = System.currentTimeMillis();
        File file = new File(path, LSR_FILE_NAME);
        if (!file.canRead()) {
            trace("UnreadableLsr", file.toString(), null, null);
            return;
        }

        File directory = new File(path);

        try {
            BufferedInputStream in =
                    new BufferedInputStream(new FileInputStream(file));
            for (int c = in.read(); c >= 0; c = in.read()) {
                if (c == '%') {
                    do {
                        c = in.read();
                    } while (c >= 0 && c != 10 && c != 13);
                } else if (c >= ' ') {
                    StringBuffer line = new StringBuffer();
                    do {
                        line.append((char) c);
                        c = in.read();
                    } while (c >= 0 && c != 10 && c != 13);
                    int len = line.length();
                    if (len != 0) {
                        if (line.charAt(len - 1) == ':') {
                            line.deleteCharAt(len - 1);
                            if (len > 2 && line.charAt(0) == '.'
                                    && line.charAt(1) == '/') {
                                line.delete(0, 2);
                            }
                            directory = new File(path, line.toString());
                        } else {
                            String key = line.toString();
                            Object cc = cache.get(key);
                            File value = new File(directory, line.toString());
                            if (cc == null) {
                                cache.put(key, value);
                            } else if (cc instanceof File) {
                                List<Object> list =
                                        new ArrayList<Object>(INITIAL_LIST_SIZE);
                                cache.put(key, list);
                                list.add(cc);
                                list.add(value);
                            } else {
                                List<File> list = (List<File>) cc;
                                list.add(value);
                            }
                        }
                    }
                }
            }

            // BufferedReader in = new BufferedReader(new FileReader(file));
            // int len;
            //
            // for (String line = in.readLine(); line != null; line = in
            // .readLine()) {
            // len = line.length();
            // if (len == 0 || line.charAt(0) == '%') {
            // continue;
            // } else if (line.charAt(len - 1) == ':') {
            // directory = new File(path, //
            // line.substring((line.startsWith("./") ? 2 : 0),
            // len - 1));
            // } else {
            // Object c = cache.get(line);
            // if (c == null) {
            // cache.put(line, new File(directory, line));
            // } else if (c instanceof File) {
            // List list = new ArrayList(INITIAL_LIST_SIZE);
            // list.add(c);
            // list.add(new File(directory, line));
            // cache.put(line, list);
            // } else {
            // List list = (List) c;
            // list.add(new File(directory, line));
            // }
            // }
            // }
            in.close();

        } catch (FileNotFoundException e) {
            throw new ConfigurationWrapperException(e);
        } catch (IOException e) {
            throw new ConfigurationIOException(null, e);
        }

        trace("DatabaseLoaded", file.toString(), //
            Long.toString(System.currentTimeMillis() - start), //
            Integer.toString(cache.size()));
        // PrintStream err = System.err;
        // err.print(file);
        // err.print('\t');
        // err.println(System.currentTimeMillis() - start);
    }

    /**
     * Setter for the properties.
     *
     * @param prop the new properties
     *
     * @see org.extex.resource.PropertyConfigurable#setProperties(
     *      java.util.Properties)
     */
    public void setProperties(Properties prop) {

        properties = prop;
    }

}
