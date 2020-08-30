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

package org.extex.util.font.xtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.extex.font.format.xtf.XtfReader;
import org.extex.util.font.AbstractFontUtil;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Convert a xtf font to a xml file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Xtf2Xml extends AbstractFontUtil {

    /**
     * The encoding for the xml output.
     */
    private static final String ENCODING = "ISO8859-1";

    /**
     * parameter.
     */
    private static final int PARAMETER = 1;

    /**
     * main.
     * 
     * @param args The command line.
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        Xtf2Xml xtf = new Xtf2Xml();

        if (args.length < PARAMETER) {
            xtf.getLogger().severe(xtf.getLocalizer().format("Xtf2Xml.Call"));
            System.exit(1);
        }

        String afmfile = "null";

        int i = 0;
        do {
            if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                if (i + 1 < args.length) {
                    xtf.setOutdir(args[++i]);
                }

            } else {
                afmfile = args[i];
            }
            i++;
        } while (i < args.length);

        xtf.doIt(afmfile);

    }


    public Xtf2Xml() {

        super(Xtf2Xml.class);
    }

    /**
     * do it.
     * 
     * @param file The afm file name.
     * @throws Exception if an error occurs.
     */
    public void doIt(String file) throws Exception {

        getLogger().severe(getLocalizer().format("Xtf2Xml.start", file));

        InputStream xtfin = null;

        // find directly the afm file.
        File xtffile = new File(file);

        if (xtffile.canRead()) {
            xtfin = new FileInputStream(xtffile);
        }

        if (xtfin == null) {
            throw new FileNotFoundException(file);
        }

        XtfReader parser = new XtfReader(xtfin);

        String xmlfile =
                getOutdir()
                        + File.separator
                        + xtffile.getName().replaceAll("\\.[tToO][tT][fF]",
                            ".xml");

        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream(xmlfile), ENCODING);
        writer.setBeauty(true);
        writer.setRemoveWhiteSpace(true);
        writer.writeStartDocument();
        parser.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

        getLogger().severe(getLocalizer().format("Xtf2Xml.end", xmlfile));

    }

}
