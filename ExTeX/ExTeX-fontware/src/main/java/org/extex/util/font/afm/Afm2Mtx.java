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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.extex.font.exception.FontException;
import org.extex.font.format.afm.AfmCharMetric;
import org.extex.font.format.afm.AfmKernPairs;
import org.extex.font.format.afm.AfmParser;
import org.extex.font.format.texencoding.EncReader;
import org.extex.util.font.AbstractFontUtil;

/**
 * Create mtx files (fontinst) from a afm file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Afm2Mtx extends AbstractFontUtil {

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

        Afm2Mtx afm = new Afm2Mtx();

        if (args.length < PARAMETER) {
            afm.getLogger().severe(afm.getLocalizer().format("Afm2Mtx.Call"));
            System.exit(1);
        }

        String afmfile = "null";
        String encfile = "null";
        boolean raw = false;

        int i = 0;
        do {
            if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                if (i + 1 < args.length) {
                    afm.setOutdir(args[++i]);
                }
            } else if ("-e".equals(args[i]) || "--encfile".equals(args[i])) {
                if (i + 1 < args.length) {
                    encfile = args[++i];
                }
            } else if ("-r".equals(args[i]) || "--raw".equals(args[i])) {
                raw = true;
            } else {
                afmfile = args[i];
            }
            i++;
        } while (i < args.length);

        afm.doIt(afmfile, encfile, raw);

    }

    /**
     * The afm file.
     */
    private File afmfile;

    /**
     * The encoding reader list.
     */
    private List<String> enclist = new ArrayList<String>();

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * Creates a new object.
     */
    public Afm2Mtx() {

        super(Afm2Mtx.class);
    }

    /**
     * Create the encoding list.
     * 
     * @param encfile The file.
     * @throws IOException if an IO-error occurred.
     */
    private void createEnclist(String encfile) throws IOException {

        File efile = new File(encfile);
        InputStream ein = null;
        if (efile.canRead()) {
            ein = new FileInputStream(efile);
        }

        if (ein == null) {
            throw new FileNotFoundException(encfile);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(ein));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().length() > 0 && !line.trim().startsWith("%")) {
                // xl-e0.enc
                enclist.add(line);
            }
        }

        reader.close();
    }

    /**
     * Create the mtx file.
     * 
     * @param enc The encoding vector
     * @throws IOException if a io-error occurred.
     * @throws FontException if a font error occurred.
     */
    private void createMtx(String enc) throws IOException, FontException {

        String encname = removeExtensions(enc);
        String afmname = removeExtensions(afmfile);
        String filename = afmname + "-" + encname;

        File mtx = new File(getOutdir() + File.separator + filename + ".mtx");
        getLogger().severe(
            getLocalizer().format("Afm2Mtx.createfile", mtx.getAbsolutePath()));

        BufferedWriter writer = new BufferedWriter(new FileWriter(mtx));
        writer.write(createVersion("Afm2Mtx.created"));
        writer.write("\\relax\n\\metrics\n\\needsfontinstversion{1.929}\n\n");

        // writer.write("\\storemapdata{" + afmname + "}{\\fromafm{" + afmname
        // + "}{" + parser.getFontname() + "}}{}\n");
        writer.write("\\storemapdata{" + filename + "}{\\frommtx{" + afmname
                + "}}{\\reencodefont{" + encname + "}}\n\n");

        writer.write("\\setint{italicslant}{" + ((int) parser.getItalicangle())
                + "}\n");
        writer.write("\\setint{underlinethickness}{"
                + ((int) parser.getUnderlineThickness()) + "}\n");
        writer.write("\\setint{maxheight}{" + ((int) parser.getUry()) + "}\n");
        writer.write("\\setint{maxdepth_neg}{" + ((int) parser.getLly())
                + "}\n");
        writer.write("\\setint{capheight}{" + ((int) parser.getCapheight())
                + "}\n");
        writer
            .write("\\setint{xheight}{" + ((int) parser.getXheight()) + "}\n");
        writer.write("\\setint{ascender}{" + ((int) parser.getAscender())
                + "}\n");
        writer.write("\\setint{descender_neg}{" + ((int) parser.getDescender())
                + "}\n");
        writer.write("\n\n");

        EncReader encreader = new EncReader(new FileInputStream(enc));
        String[] table = encreader.getTable();

        for (int i = 0; i < table.length; i++) {
            String glyphname = table[i].replaceAll("/", "");
            AfmCharMetric cm = parser.getAfmCharMetric(glyphname);
            if (cm != null) {

                // metric
                writer.write("\\setscaledrawglyph");
                writer.write("{" + cm.getN() + "}");
                writer.write("{" + filename + "}");
                writer.write("{10pt}");
                writer.write("{1000}");
                writer.write("{" + i + "}");
                writer.write("{" + ((int) cm.getWidth()) + "}");
                writer.write("{" + ((int) cm.getHeight()) + "}");
                writer.write("{" + ((int) cm.getDepth()) + "}");
                writer.write("{0}\n");

                // kern
                List<AfmKernPairs> kplist = cm.getK();
                if (kplist != null) {
                    for (int k = 0, n = kplist.size(); k < n; k++) {
                        AfmKernPairs kp = kplist.get(k);
                        String post = kp.getCharpost();
                        // use only kerning for glyphs in the encoding vector
                        if (encreader.getPosition(post) >= 0) {
                            writer.write("\\setkern");
                            writer.write("{" + kp.getCharpre() + "}");
                            writer.write("{" + post + "}");
                            writer.write("{" + ((int) kp.getKerningsize())
                                    + "}\n");
                        }
                    }
                }

            } else {
                writer.write("% " + glyphname + " not found!\n");
            }
        }

        writer.write("\\endmetrics\n");
        writer.close();

    }

    /**
     * Create a raw mtx file.
     * 
     * @throws IOException if an IO-error occurred.
     */
    private void createRaw() throws IOException {

        String afmname = removeExtensions(afmfile);

        File mtx = new File(getOutdir() + File.separator + afmname + ".mtx");
        getLogger().severe(
            getLocalizer().format("Afm2Mtx.createfile", mtx.getAbsolutePath()));

        BufferedWriter writer = new BufferedWriter(new FileWriter(mtx));
        writer.write(createVersion("Afm2Mtx.created"));
        writer.write("\\relax\n\\metrics\n\\needsfontinstversion{1.929}\n\n");

        writer.write("\\storemapdata{" + afmname + "}{\\fromafm{" + afmname
                + "}{" + parser.getFontname() + "}}{}\n");

        writer.write("\\setint{italicslant}{" + ((int) parser.getItalicangle())
                + "}\n");
        writer.write("\\setint{underlinethickness}{"
                + ((int) parser.getUnderlineThickness()) + "}\n");
        writer.write("\\setint{maxheight}{" + ((int) parser.getUry()) + "}\n");
        writer.write("\\setint{maxdepth_neg}{" + ((int) parser.getLly())
                + "}\n");
        writer.write("\\setint{capheight}{" + ((int) parser.getCapheight())
                + "}\n");
        writer
            .write("\\setint{xheight}{" + ((int) parser.getXheight()) + "}\n");
        writer.write("\\setint{ascender}{" + ((int) parser.getAscender())
                + "}\n");
        writer.write("\\setint{descender_neg}{" + ((int) parser.getDescender())
                + "}\n");
        writer.write("\n\n");

        List<AfmCharMetric> cmlist = parser.getAfmCharMetrics();
        for (AfmCharMetric cm : cmlist) {
            if (cm != null) {

                // metric
                if (cm.getC() >= 0) {
                    writer.write("\\setrawglyph");
                } else {
                    writer.write("\\setnotglyph");
                }
                writer.write("{" + cm.getN() + "}");
                writer.write("{" + afmname + "}");
                writer.write("{10pt}");
                writer.write("{" + cm.getC() + "}");
                writer.write("{" + ((int) cm.getWidth()) + "}");
                writer.write("{" + ((int) cm.getHeight()) + "}");
                writer.write("{" + ((int) cm.getDepth()) + "}");
                writer.write("{0}\n");

                // kern
                List<AfmKernPairs> kplist = cm.getK();
                if (kplist != null) {
                    for (int k = 0, n = kplist.size(); k < n; k++) {
                        AfmKernPairs kp = kplist.get(k);
                        writer.write("\\setkern");
                        writer.write("{" + kp.getCharpre() + "}");
                        writer.write("{" + kp.getCharpost() + "}");
                        writer.write("{" + ((int) kp.getKerningsize()) + "}\n");
                    }
                }

            }
        }

        writer.write("\\endmetrics\n");
        writer.close();
    }

    /**
     * do it.
     * 
     * @param file The afm file name.
     * @param encfile The name of the encoding file.
     * @param raw Create a raw mtx file.
     * @throws Exception if an error occurs.
     */
    private void doIt(String file, String encfile, boolean raw)
            throws Exception {

        getLogger().severe(getLocalizer().format("Afm2Mtx.start", file));

        InputStream afmin = null;

        afmfile = new File(file);
        if (afmfile.canRead()) {
            afmin = new FileInputStream(afmfile);
        }

        if (afmin == null) {
            throw new FileNotFoundException(file);
        }

        createEnclist(encfile);
        parser = new AfmParser(afmin);

        if (raw) {
            createRaw();
        }

        for (int i = 0, n = enclist.size(); i < n; i++) {
            createMtx(enclist.get(i));
        }
        getLogger().severe(getLocalizer().format("Afm2Mtx.end"));

    }
}
