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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;

import org.extex.font.format.xtf.XtfReader;
import org.extex.util.font.AbstractFontUtil;

/**
 * Extract the xtf information's the shell variables.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5594 $
 */
public class Xtf2Var extends AbstractFontUtil {

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

        Xtf2Var xtf = new Xtf2Var();

        if (args.length < PARAMETER) {
            xtf.getLogger().severe(xtf.getLocalizer().format("Xtf2Var.Call"));
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

    /**
     * Creates a new object.
     */
    public Xtf2Var() {

        super(Xtf2Var.class);
    }

    /**
     * do it.
     * 
     * @param file The afm file name.
     * @throws Exception if an error occurs.
     */
    public void doIt(String file) throws Exception {

        getLogger().severe(getLocalizer().format("Xtf2Var.start", file));

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

        String outfile =
                getOutdir()
                        + File.separator
                        + xtffile.getName().replaceAll("\\.[tToO][tT][fF]",
                            ".sh");

        BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));

        writer.write(createVersion("Xtf2Var.created"));
        writer.write(getLocalizer().format("Xtf2Var.NUMBEROFGLYPHS",
            String.valueOf(parser.getNumberOfGlyphs())));

        writer.write(getLocalizer().format("Xtf2Var.FONTNAME",
            parser.getFontFamilyName()));

        writer.close();

        getLogger().severe(getLocalizer().format("Xtf2Var.end", outfile));

    }

}
