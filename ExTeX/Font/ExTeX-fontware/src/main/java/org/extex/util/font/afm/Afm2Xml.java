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

package org.extex.util.font.afm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.extex.font.format.afm.AfmParser;
import org.extex.util.font.AbstractFontUtil;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Convert a afm file to a xml file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5594 $
 */
public class Afm2Xml extends AbstractFontUtil {

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

        Afm2Xml afm = new Afm2Xml();

        if (args.length < PARAMETER) {
            afm.getLogger().severe(afm.getLocalizer().format("Afm2Xml.Call"));
            System.exit(1);
        }

        String afmfile = "null";

        int i = 0;
        do {
            if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                if (i + 1 < args.length) {
                    afm.setOutdir(args[++i]);
                }

            } else {
                afmfile = args[i];
            }
            i++;
        } while (i < args.length);

        afm.doIt(afmfile);

    }


    public Afm2Xml() {

        super(Afm2Xml.class);
    }

    /**
     * do it.
     * 
     * @param file The afm file name.
     * @throws Exception if an error occurs.
     */
    public void doIt(String file) throws Exception {

        getLogger().severe(getLocalizer().format("Afm2Xml.start", file));

        InputStream afmin = null;

        // find directly the afm file.
        File afmfile = new File(file);

        if (afmfile.canRead()) {
            afmin = new FileInputStream(afmfile);
        }

        if (afmin == null) {
            throw new FileNotFoundException(file);
        }

        AfmParser parser = new AfmParser(afmin);

        String xmlfile =
                getOutdir()
                        + File.separator
                        + afmfile.getName().replaceAll("\\.[aA][fF][mM]",
                            ".xml");

        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream(xmlfile), ENCODING);
        writer.setBeauty(true);
        writer.setRemoveWhiteSpace(true);
        writer.writeStartDocument();
        parser.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

        getLogger().severe(getLocalizer().format("Afm2Xml.end", xmlfile));

    }

}
