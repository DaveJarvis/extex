/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package de.dante.util.font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import de.dante.extex.font.type.afm.AfmFont;
import de.dante.util.configuration.Configuration;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.configuration.ConfigurationFactory;
import de.dante.util.resource.ResourceFinder;
import de.dante.util.resource.ResourceFinderFactory;
import de.dante.util.xml.XMLStreamWriter;

/**
 * Convert a AFM-file to a XML-file.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public final class AFM2XML extends AbstractFontUtil {

    /**
     * Create a new object.
     *
     * @throws ConfigurationException if a config-error occurs.
     */
    private AFM2XML() throws ConfigurationException {

        super();
    }

    /**
     * do it.
     *
     * @param args the comandline
     * @throws Exception if an error occurs.
     */
    private void doIt(final String[] args) throws Exception {

        File xmlfile = new File(args[1]);
        String fontname = args[0].replaceAll("\\.afm|\\.AFM", "");

        Configuration config = new ConfigurationFactory()
                .newInstance("config/extex.xml");

        //Configuration cfgfonts = config.getConfiguration("Fonts");

        Properties prop = new Properties();
        try {
            InputStream in = new FileInputStream(".extex");
            prop.load(in);
        } catch (Exception e) {
            prop.setProperty("extex.fonts", "src/font");
        }

        ResourceFinder finder = (new ResourceFinderFactory())
                .createResourceFinder(config.getConfiguration("Resource"),
                        null, prop);

        // afm-file
        InputStream afmin = finder.findResource(args[0], "");

        if (afmin == null) {
            throw new FileNotFoundException(args[0]);
        }

        AfmFont font = new AfmFont(afmin, fontname);

        // write to xml-file
        XMLStreamWriter writer = new XMLStreamWriter(new FileOutputStream(
                xmlfile), "ISO-8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        font.writeXML(writer);
        writer.writeEndDocument();
        writer.close();
    }

    /**
     * parameter
     */
    private static final int PARAMETER = 2;

    /**
     * main
     * @param args the commandlinearguments
     * @throws Exception if a error occurs.
     */
    public static void main(final String[] args) throws Exception {

        if (args.length != PARAMETER) {
            System.err
                    .println("java de.dante.util.font.AFM2XML <afm-file> <xml-file>");
            System.exit(1);
        }

        AFM2XML afm2xml = new AFM2XML();
        afm2xml.doIt(args);
    }
}