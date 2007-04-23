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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.extex.exdoc.util.ConfigurationInfo;
import org.extex.exdoc.util.LogFormatter;
import org.extex.exdoc.util.PrimitiveInfo;
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
 * @version $Revision:5413 $
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
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(ConfigReader.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new LogFormatter());
        logger.addHandler(consoleHandler);

        try {
            ConfigReader configReader = new ConfigReader(logger);
            configReader.add(args[0]);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }

    /**
     * The field <tt>builder</tt> contains the document builder for parsing
     * the XML file.
     */
    private DocumentBuilder builder;

    /**
     * The field <tt>component</tt> contains the ...
     */
    private List<String> component = new ArrayList<String>();

    /**
     * The field <tt>configurations</tt> contains the ...
     */
    private List<ConfigurationInfo> configurations =
            new ArrayList<ConfigurationInfo>();

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * The field <tt>units</tt> contains the ...
     */
    private List<UnitInfo> units = new ArrayList<UnitInfo>();

    /**
     * Creates a new object.
     * 
     * @throws ParserConfigurationException in case of a parser error
     */
    public ConfigReader() throws ParserConfigurationException {

        super();
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Creates a new object.
     * 
     * @param logger the logger
     * 
     * @throws ParserConfigurationException in case of a parser error
     */
    public ConfigReader(Logger logger) throws ParserConfigurationException {

        this();
        setLogger(logger);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param cfg ...
     * 
     * @throws ParserConfigurationException ...
     * @throws SAXException in case of a parser error
     * @throws IOException in case of an I/O error
     */
    public void add(String cfg)
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
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param file ...
     * 
     * @return ...
     * 
     * @throws SAXException in case of a parser error
     * @throws IOException in case of an I/O error
     */
    private Element readXml(File file) throws SAXException, IOException {

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
     * @throws SAXException in case of a parser error
     * @throws IOException in case of an I/O error
     */
    private void scan(String name) throws IOException, SAXException {

        component.add(name);

        File config =
                new File(new File(new File(new File("..", name), "src"),
                    "resources"), "config");
        String[] list = config.list();
        if (list != null) {
            for (String f : list) {
                if (f.endsWith(CONFIG_EXTENSION)) {
                    scanConfig(config, f);
                }
            }
        }

        File unit = new File(config, "unit");
        list = unit.list();
        if (list != null) {
            for (String f : list) {
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
     * @throws SAXException in case of a parser error
     * @throws IOException in case of an I/O error
     */
    private void scanConfig(File base, String f)
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
     * @throws SAXException in case of a parser error
     * @throws IOException in case of an I/O error
     */
    private void scanUnit(File base, String f) throws SAXException, IOException {

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

                info.add(new PrimitiveInfo(name, null, c));
            }
        }

    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

}
