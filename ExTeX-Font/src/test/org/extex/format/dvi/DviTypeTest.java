/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.format.dvi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.extex.font.FontFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationNoSuchMethodException;
import org.extex.resource.PropertyConfigurable;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;
import org.extex.util.file.random.RandomAccessInputFile;

/**
 * Test the DviType class.
 * <p>
 * needs -Xms64m -Xmx127m
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class DviTypeTest extends TestCase {

    /**
     * files.
     */
    private static final String[] FILES = {"lettrine", "listings", "andre"};

    /**
     * path.
     */
    private static final String PATH = "../ExTeX-Font/src/test/dvi/";

    /**
     * test DviXml.
     *
     * @param args the command line
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(DviTypeTest.class);
    }

    /**
     * finder.
     */
    private ResourceFinder finder;

    /**
     * The field <tt>props</tt> contains the merged properties from the system
     * properties and the properties loaded from <tt>.extex-test</tt>.
     */
    private Properties props = null;

    /**
     * Check the file.
     *
     * @param file the file to test
     * @throws IOException if an io-error occurs
     */
    private void checkFile(String file) throws IOException {

        LineNumberReader inorg =
                new LineNumberReader(new FileReader(PATH + file + ".dvitype"));
        LineNumberReader innew =
                new LineNumberReader(new FileReader(PATH + file + ".tmp"));

        Map maporg = new TreeMap();
        Map mapnew = new TreeMap();

        readFile(inorg, maporg);
        readFile(innew, mapnew);

        inorg.close();
        innew.close();

        Iterator it = maporg.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            String lineorg = killVVHH((String) maporg.get(key));
            String linenew = killVVHH((String) mapnew.get(key));
            // String lineorg = (String) maporg.get(key);
            // String linenew = (String) mapnew.get(key);
            assertEquals(lineorg, linenew);
        }
    }

    /**
     * Getter for props.
     *
     * @return the props
     */
    private Properties getProps() {

        if (props == null) {
            props = System.getProperties();

            File file = new File(".extex-test");
            if (file.canRead()) {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    props.load(inputStream);
                    inputStream.close();
                } catch (Exception e) {
                    // ignored on purpose
                    e.printStackTrace();
                }
            }
        }
        return (Properties) this.props.clone();
    }

    /**
     * remove the vv- or hh-value.
     *
     * @param s the String
     * @return Returns the String witout vv.. / hh..
     */
    private String killVVHH(String s) {

        int i = s.indexOf("vv");
        if (i >= 0) {
            return s.substring(0, i);
        }
        i = s.indexOf("hh");
        if (i >= 0) {
            return s.substring(0, i);
        }
        return s;
    }

    /**
     * make a font factory.
     *
     * @return Return the font factory
     * @throws Exception if an error occurs.
     */

    private FontFactory makeFontFactory() throws Exception {

        Configuration config =
                new ConfigurationFactory()
                    .newInstance("config/extex-test-font.xml");

        props = getProps();

        finder =
                (new ResourceFinderFactory()).createResourceFinder(config
                    .getConfiguration("Resource"), null, props, null);

        FontFactory fontFactory =
                makeFontFactory(config.getConfiguration("Fonts"));

        return fontFactory;
    }

    // --------------------------------------
    // --------------------------------------
    // --------------------------------------

    /**
     * Create a new font factory.
     *
     * @param config the configuration object for the font factory
     *
     * @return the new font factory
     *
     * @throws ConfigurationException in case that some kind of problems have
     *             been detected in the configuration
     */
    protected FontFactory makeFontFactory(Configuration config)
            throws ConfigurationException {

        FontFactory fontFactory;
        String fontClass = config.getAttribute("class");

        if (fontClass == null || fontClass.equals("")) {
            throw new ConfigurationMissingAttributeException("class", config);
        }

        // ResourceFinder fontFinder = (new ResourceFinderFactory())
        // .createResourceFinder(config.getConfiguration("Resource"),
        // null, getProps());
        // if (Boolean.valueOf(getProps().getProperty("extex.trace.font.files"))
        // .booleanValue()) {
        // fontFinder.enableTracing(true);
        // }

        try {
            fontFactory =
                    (FontFactory) (Class.forName(fontClass).getConstructor(
                        new Class[]{Configuration.class, ResourceFinder.class})
                        .newInstance(new Object[]{config, finder}));
        } catch (IllegalArgumentException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (SecurityException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationNoSuchMethodException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationClassNotFoundException(fontClass);
        }

        if (fontFactory instanceof PropertyConfigurable) {
            ((PropertyConfigurable) fontFactory).setProperties(props);
        }

        return fontFactory;
    }

    /**
     * Read the file.
     *
     * @param in the input
     * @param map the map
     * @throws IOException if an io-error occurs
     */
    private void readFile(LineNumberReader in, Map map)
            throws IOException {

        // read all line with start with a number "111:..."
        String line;
        while ((line = in.readLine()) != null) {
            if (line.matches("^[0-9]*:.*")) {
                int pos = line.indexOf(":");
                Integer key = new Integer(line.substring(0, pos));
                map.put(key, line.trim());
            }
        }
    }

    /**
     * ...
     *
     * @param name ...
     *
     * @throws Exception ...
     */
    private void run(String name) throws Exception {

        String file = PATH + name + ".dvi";
        RandomAccessInputFile rar = new RandomAccessInputFile(file);

        PrintWriter writer =
                new PrintWriter(new BufferedOutputStream(new FileOutputStream(
                    PATH + name + ".tmp")));
        DviType dvitype = new DviType(writer, makeFontFactory());

        dvitype.interpret(rar);
        rar.close();
        writer.close();
    }

    /**
     * test the dviXml interpreter.
     *
     * @throws IOException if a IO-error occurs
     */
    public void testandre() throws Exception {

        run(FILES[2]);
        checkFile(FILES[2]);
    }

    /**
     * test the dviXml interpreter.
     *
     * @throws IOException if a IO-error occurs
     */
    public void testlettrine() throws Exception {

        run(FILES[0]);
        checkFile(FILES[0]);
    }

    /**
     * test the dviXml interpreter.
     *
     * @throws IOException if a IO-error occurs
     */
    public void testlistings() throws Exception {

        run(FILES[1]);
        checkFile(FILES[1]);
    }

}
