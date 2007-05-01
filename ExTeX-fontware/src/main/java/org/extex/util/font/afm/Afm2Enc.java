/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.font.exception.FontException;
import org.extex.font.format.afm.AfmCharMetric;
import org.extex.font.format.afm.AfmParser;
import org.extex.font.format.encoding.EncReader;
import org.extex.framework.configuration.exception.ConfigurationException;


/**
 * Create encoding vectors from a afm file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5428 $
 */
public class Afm2Enc extends AfmUtil {

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

        Afm2Enc afm = new Afm2Enc();

        if (args.length < PARAMETER) {
            afm.getLogger().severe(afm.getLocalizer().format("Afm2Enc.Call"));
            System.exit(1);
        }

        String afmfile = "null";
        List<String> enclist = new ArrayList<String>();

        int i = 0;
        do {
            if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                if (i + 1 < args.length) {
                    afm.setOutdir(args[++i]);
                }

            } else if ("-v".equals(args[i]) || "--encvector".equals(args[i])) {
                if (i + 1 < args.length) {
                    enclist.add(args[++i]);
                }
            } else {
                afmfile = args[i];
            }
            i++;
        } while (i < args.length);

        afm.doIt(afmfile, enclist);

    }

    private File afmfile;

    /**
     * The list for the encoding vectors.
     */
    private List<String> enclist = new ArrayList<String>();

    /**
     * The name for the encoding vectors.
     */
    private String encname = "";

    /**
     * A map for the glyphs.
     */
    private Map<String, EncGlpyh> glyphmap;

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException from the configuration system.
     */
    public Afm2Enc() throws ConfigurationException {

        super(Afm2Enc.class);
    }

    /**
     * Create the encoding files.
     * 
     * @param names The names for the files.
     * @throws IOException if a IO-error occurred.
     */
    private void createEncFiles(List<String> names) throws IOException {

        int cnt = 0;
        int filecnt = 0;
        char filechar = 'a';
        String na = "";
        BufferedWriter out = null;
        String afm = removeExtensions(afmfile);
        for (int i = 0, n = names.size(); i < n; i++) {
            if (cnt == 0) {
                na = encname + filechar;
                File newenc =
                        new File(getOutdir() + File.separator + na + ".enc");
                out = new BufferedWriter(new FileWriter(newenc));
                out.write("% " + createVersion() + "\n");
                out.write("/" + na + "Encoding [\n");

                // if (tomap && mapout != null) {
                // mapout.write(na);
                // mapout.write(" ");
                // mapout.write(parser.getHeader().getFontname());
                // mapout.write(" ");
                // mapout.write("\" ");
                // mapout.write(na + "Encoding");
                // mapout.write(" \" ");
                // mapout.write("<");
                // mapout.write(na + ".enc");
                // mapout.write(" ");
                // mapout.write("<");
                // mapout.write(afmfile.getName().replaceAll(
                // "\\.[aA][fF][mM]", ".pfb"));
                // mapout.write("\n");
                // }

            }
            EncGlpyh eg =
                    new EncGlpyh(afm, afm + na, names.get(i).toString(), cnt);
            glyphmap.put(eg.getGlyphname(), eg);

            out.write("% " + cnt++ + "\n");
            out.write("/" + names.get(i) + "\n");
            if (cnt == NUMBEROFGLYPHS) {
                out.write("] def\n");
                out.close();
                cnt = 0;
                filecnt++;
                filechar = (char) (filechar + 1);
            }
        }
        if (cnt != 0) {
            for (int i = cnt; i < NUMBEROFGLYPHS; i++) {
                out.write("% " + i + "\n");
                out.write("/.notdef\n");
            }
            out.write("] def\n");
            out.close();
        }
        getLogger().severe(
            getLocalizer().format("AfmUtil.EncCreate",
                String.valueOf(names.size()), String.valueOf(filecnt)));

    }

    /**
     * Create the include file for the package.
     * 
     * @throws IOException if an IO-error occurred.
     */
    private void createIncSty() throws IOException {

        BufferedWriter out =
                new BufferedWriter(new FileWriter(getOutdir() + File.separator
                        + removeExtensions(afmfile) + ".inc"));

        Iterator<String> it = glyphmap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            EncGlpyh eg = glyphmap.get(key);
            out.write(eg.toString());
            out.newLine();
        }
        out.close();
    }

    /**
     * do it.
     * 
     * @param file The afm file name.
     * @param enc The lsit with the encoding vectors.
     * @throws Exception if an error occurs.
     */
    private void doIt(String file, List<String> enc) throws Exception {

        getLogger().severe(getLocalizer().format("Afm2Enc.start", file));

        enclist = enc;

        InputStream afmin = null;

        afmfile = new File(file);
        if (afmfile.canRead()) {
            afmin = new FileInputStream(afmfile);
        } else {
            // use the file finder
            afmin = getFinder().findResource(afmfile.getName(), "");
        }

        if (afmin == null) {
            throw new FileNotFoundException(file);
        }

        parser = new AfmParser(afmin);
        toEnc();

        getLogger().severe(getLocalizer().format("Afm2Enc.end"));

    }

    /**
     * Read all glyph names from the encoding vectors.
     * 
     * @param readenc The list for the names.
     * @throws IOException if an IO-error occurred.
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    private void readAllGlyphName(List<String> readenc)
            throws IOException,
                ConfigurationException,
                FontException {

        for (String encv : enclist) {

            InputStream encin = null;
            File encfile = new File(encv);
            if (encfile.canRead()) {
                encin = new FileInputStream(encfile);
            } else {
                // use the file finder
                encin = getFinder().findResource(encfile.getName(), "");
            }

            if (encin == null) {
                throw new FileNotFoundException(encv);
            }

            EncReader enc = new EncReader(encin);

            String[] table = enc.getTable();

            for (int k = 0; k < table.length; k++) {
                String name = table[k].replaceAll("/", "");
                readenc.add(name);

                EncGlpyh eg =
                        new EncGlpyh(removeExtensions(afmfile),
                            removeExtensions(afmfile) + removeExtensions(encv),
                            name, k);

                glyphmap.put(eg.getGlyphname(), eg);

            }

            // if (tomap && mapout != null) {
            // mapout.write(encname);
            // mapout.write(encv.replaceAll("\\.[eE][nN][cC]", ""));
            // mapout.write(" ");
            // mapout.write(parser.getHeader().getFontname());
            // mapout.write(" ");
            // mapout.write("\" ");
            // mapout.write(enc.getEncname());
            // mapout.write(" \" ");
            // mapout.write("<");
            // mapout.write(encv);
            // mapout.write(" ");
            // mapout.write("<");
            // mapout.write(afmfile.getName().replaceAll("\\.[aA][fF][mM]",
            // ".pfb"));
            // mapout.write("\n");
            // }

        }
    }

    /**
     * Read the glyph names.
     * 
     * @return Returns the glyph name list.
     */
    private List<String> readGlyphNames() {

        List<AfmCharMetric> cmlist = parser.getAfmCharMetrics();
        List<String> names = new ArrayList<String>(cmlist.size());

        for (AfmCharMetric cm : cmlist) {
            names.add(cm.getN());
        }
        return names;
    }

    /**
     * Remove existing glyph name from the list.
     * 
     * @param readenc The existing glyph names.
     * @param names The glyph names from the afm file.
     */
    private void removeExistingNames(List<String> readenc, List<String> names) {

        for (String name : readenc) {
            names.remove(name);
        }
    }

    /**
     * Create encoding vectors.
     * 
     * @throws IOException if an IO-error occurred.
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    private void toEnc()
            throws IOException,
                ConfigurationException,
                FontException {

        // read all glyphs from the encoding vectors
        List<String> readenc = new ArrayList<String>();
        readAllGlyphName(readenc);

        // get the glyph names form the afm file.
        List<String> names = readGlyphNames();
        Collections.sort(names);

        // remove all names from readenc in names
        removeExistingNames(readenc, names);

        createEncFiles(names);

        createIncSty();

    }

}
