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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.font.exception.FontException;
import org.extex.font.format.afm.AfmCharMetric;
import org.extex.font.format.afm.AfmParser;
import org.extex.font.format.texencoding.EncReader;
import org.extex.font.format.texencoding.EncWriter;
import org.extex.util.font.AbstractFontUtil;

/**
 * Create encoding vectors from a afm file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Afm2Enc extends AbstractFontUtil {

    /**
     * Container for the data: font name + encoding vector - glyph name -
     * number.
     * 
     * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
     * @version $Revision$
     */
    public class EncGlpyh {

        /**
         * The font.
         */
        private String font;

        /**
         * The font name (with the name of the encoding vector).
         */
        private String fonte = "";

        /**
         * The name of the glyph.
         */
        private String glyphname = "";

        /**
         * The number (Position in the encoding vector).
         */
        private int number;

        /**
         * Create a new object.
         * 
         * @param basefont The font
         * @param fontencname The fontname (with encoding vector)
         * @param gn The glyph name.
         * @param n The number.
         */
        public EncGlpyh(String basefont, String fontencname, String gn, int n) {

            font = basefont;
            fonte = fontencname;
            glyphname = gn;
            number = n;
        }

        /**
         * Returns the font.
         * 
         * @return Returns the font.
         */
        public String getFont() {

            return font;
        }

        /**
         * Returns the fonte.
         * 
         * @return Returns the fonte.
         */
        public String getFonte() {

            return fonte;
        }

        /**
         * Returns the glyphname.
         * 
         * @return Returns the glyphname.
         */
        public String getGlyphname() {

            return glyphname;
        }

        /**
         * Returns the number.
         * 
         * @return Returns the number.
         */
        public int getNumber() {

            return number;
        }

        /**
         * Returns the info from the class.
         * 
         * @return Returns the info from the class.
         */
        public String toString() {

            StringBuffer buf = new StringBuffer();
            // \DeclareTextGlyphX{fltr}{AEacute}{fltrla}{0}
            buf.append("\\DeclareTextGlyphX{");
            buf.append(font);
            buf.append("}{");
            buf.append(glyphname);
            buf.append("}{");
            buf.append(fonte);
            buf.append("}{");
            buf.append(number);
            buf.append("}");

            return buf.toString();
        }
    }

    /**
     * Number of glyphs.
     */
    public static final int NUMBEROFGLYPHS = 256;

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

            } else if ("-e".equals(args[i]) || "--encvector".equals(args[i])) {
                if (i + 1 < args.length) {
                    enclist.add(args[++i]);
                }

            } else if ("-n".equals(args[i]) || "--encname".equals(args[i])) {
                if (i + 1 < args.length) {
                    afm.setEncname(args[++i]);
                }
            } else {
                afmfile = args[i];
            }
            i++;
        } while (i < args.length);

        afm.doIt(afmfile, enclist);

    }

    /**
     * The afm file.
     */
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
     * The EncWriter.
     */
    private EncWriter encw;

    /**
     * A map for the glyphs.
     */
    private Map<String, EncGlpyh> glyphmap = new HashMap<String, EncGlpyh>();

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * Creates a new object.
     */
    public Afm2Enc() {

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
        encw = new EncWriter();
        String afm = removeExtensions(afmfile);
        for (int i = 0, n = names.size(); i < n; i++) {
            EncGlpyh eg =
                    new EncGlpyh(afm, afm + na, names.get(i).toString(), cnt);
            glyphmap.put(eg.getGlyphname(), eg);

            encw.setEncoding(cnt++, "/" + names.get(i));
            if (cnt == NUMBEROFGLYPHS) {
                na = encname + filechar;
                writeEncW(filechar, na, encw);

                encw = new EncWriter();
                cnt = 0;
                filecnt++;
                filechar = (char) (filechar + 1);
            }
        }
        if (cnt != 0) {
            na = encname + filechar;
            writeEncW(filechar, na, encw);
        }
        getLogger().severe(
            getLocalizer().format("Afm2Enc.createenc",
                String.valueOf(names.size()), String.valueOf(filecnt + 1)));

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
     * @throws FontException if a font error occurred.
     */
    private void readAllGlyphName(List<String> readenc)
            throws IOException,
                FontException {

        for (String encv : enclist) {

            InputStream encin = null;
            File encfile = new File(encv);
            if (encfile.canRead()) {
                encin = new FileInputStream(encfile);
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
     * Setter for encname.
     * 
     * @param encname the encname to set
     */
    public void setEncname(String encname) {

        this.encname = encname;
    }

    /**
     * Create encoding vectors.
     * 
     * @throws IOException if an IO-error occurred.
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    private void toEnc() throws IOException,

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

    /**
     * Write enc file.
     * 
     * @param filechar The file char
     * @param na The name.
     * @param encw The <code>EncWriter</code>.
     * @throws IOException if an IO-error occurred.
     */
    private void writeEncW(char filechar, String na, EncWriter encw)
            throws IOException {

        File newenc = new File(getOutdir() + File.separator + na + ".enc");
        encw.setComments(true);
        encw.setEncname(filechar + "Encoding");
        encw.setHeaderComment(createVersion("Afm2Enc.created"));
        encw.write(new FileOutputStream(newenc));
        getLogger().severe(
            getLocalizer().format("Afm2Enc.createfile",
                newenc.getAbsolutePath()));
    }

}
