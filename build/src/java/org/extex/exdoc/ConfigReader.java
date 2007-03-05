/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.extex.exdoc.util.ConfigurationInfo;
import org.extex.exdoc.util.UnitInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigReader {

    /**
     * The constant <tt>CONFIG_EXTENSION</tt> contains the ...
     */
    private static final String CONFIG_EXTENSION = ".xml";

    /**
     * The constant <tt>UNIT_EXTENSION</tt> contains the ...
     */
    private static final String UNIT_EXTENSION = CONFIG_EXTENSION;

    /**
     * TODO gene: missing JavaDoc
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        try {
            ConfigReader configReader = new ConfigReader();
            configReader.add(args[0]);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * The field <tt>builder</tt> contains the document builder for parsing the
     * XML file.
     */
    private DocumentBuilder builder;

    /**
     * The field <tt>component</tt> contains the ...
     */
    private List component = new ArrayList();

    /**
     * The field <tt>configurations</tt> contains the ...
     */
    private List configurations = new ArrayList();

    /**
     * The field <tt>units</tt> contains the ...
     */
    private List units = new ArrayList();

    /**
     * Creates a new object.
     *
     * @throws ParserConfigurationException ...
     */
    public ConfigReader() throws ParserConfigurationException {

        super();
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param cfg ...
     *
     * @throws ParserConfigurationException ...
     * @throws IOException ...
     * @throws SAXException ...
     */
    public void add(final String cfg)
            throws ParserConfigurationException,
                SAXException,
                IOException {

        Element root = readXml(new File(cfg));

        for (Node node = root.getFirstChild(); node != null; node =
                node.getNextSibling()) {
            if (node.getNodeName().equals("component")) {
                scan(((Element) node).getAttribute("name"));
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param file ...
     *
     * @return ...
     *
     * @throws FileNotFoundException
     * @throws SAXException
     * @throws IOException
     */
    private Element readXml(final File file)
            throws FileNotFoundException,
                SAXException,
                IOException {

        InputStream stream = new FileInputStream(file);
        Document doc = builder.parse(stream);
        stream.close();
        return doc.getDocumentElement();
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param name ...
     *
     * @throws SAXException ...
     * @throws IOException ...
     */
    private void scan(final String name) throws IOException, SAXException {

        component.add(name);

        File config =
                new File(new File(new File(new File("..", name), "src"),
                    "resources"), "config");
        String[] list = config.list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                String f = list[i];
                if (f.endsWith(CONFIG_EXTENSION)) {
                    scanConfig(config, f);
                }
            }
        }

        File unit = new File(config, "unit");
        list = unit.list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                String f = list[i];
                if (f.endsWith(UNIT_EXTENSION)) {
                    scanUnit(unit, f);
                }
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param base TODO
     * @param f ...
     *
     * @throws IOException ...
     * @throws SAXException ...
     */
    private void scanConfig(final File base, final String f)
            throws IOException,
                SAXException {

        String name = f.substring(0, f.length() - CONFIG_EXTENSION.length());
        ConfigurationInfo info;
                System.err.println("--> " + f);

        Element root = readXml(new File(base, f));
        NodeList bannerElements = root.getElementsByTagName("banner");
        if (bannerElements.getLength() > 0) {
            Node item = bannerElements.item(0);
            StringBuffer sb = new StringBuffer();
            for (Node node = item.getFirstChild(); node != null; node =
                    node.getNextSibling()) {
                sb.append(node.getNodeValue());
            }
            info = new ConfigurationInfo(name, sb.toString());
        } else {
            info = new ConfigurationInfo(name, "");
        }
        configurations.add(info);

        NodeList interpreterElements = root.getElementsByTagName("interpreter");
        if (interpreterElements.getLength() > 0) {
            NodeList unitElements = root.getElementsByTagName("unit");
            for (int i = unitElements.getLength() - 1; i >= 0; i--) {
                Node item = unitElements.item(i);
                String src = ((Element) item).getAttribute("src");
                if (src != null) {
                    info.addUnit(src.replaceFirst("([^/]*).xml$", "\\1"));
                }
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param base ...
     * @param f ...
     *
     * @throws IOException ...
     * @throws SAXException ...
     * @throws FileNotFoundException ...
     */
    private void scanUnit(final File base, final String f)
            throws FileNotFoundException,
                SAXException,
                IOException {

        String unit = f.substring(0, f.length() - UNIT_EXTENSION.length());
        UnitInfo info = new UnitInfo(unit);
        units.add(info);
        System.err.println("--- " + f);

        Element root = readXml(new File(base, f));
        NodeList primitiveElements = root.getElementsByTagName("primitives");
        for (int i = primitiveElements.getLength() - 1; i >= 0; i--) {
            Element item = (Element) primitiveElements.item(i);
            NodeList defines = item.getElementsByTagName("define");
            for (int j = defines.getLength() - 1; j >= 0; j--) {
                Element def = (Element) defines.item(j);
                String name = def.getAttribute("name");
                String c = def.getAttribute("class");

                info.add(name, c);
//                System.err.println("\t" + name);
            }
        }

    }

}
