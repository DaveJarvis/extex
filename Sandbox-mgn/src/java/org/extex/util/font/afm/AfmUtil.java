/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.extex.ExTeX;
import org.extex.font.format.afm.AfmParser;
import org.extex.font.format.texencoding.EncReader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.util.xml.XMLStreamWriter;

import de.dante.util.font.AbstractFontUtil;

/**
 * Abstract class for utilities for a afm file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5594 $
 */
public class AfmUtil extends AbstractFontUtil {

    /**
     * Container for the data: fontname + encoding vector - glyphname - number.
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
     * fixfactor.
     */
    private static final long FIXFACTOR = 0x100000L; // 2^{20}, the unit

    // -----------------------------------------------

    /**
     * Number of glyphs.
     */
    public static final int NUMBEROFGLYPHS = 256;

    // /**
    // * parameter.
    // */
    // private static final int PARAMETER = 1;
    //
    // /**
    // * main.
    // *
    // * @param args the command line arguments.
    // * @throws Exception if a error occurs.
    // */
    // public static void main(String[] args) throws Exception {
    //
    // AfmUtil afm = new AfmUtil();
    //
    // if (args.length < PARAMETER) {
    // afm.getLogger().severe(afm.getLocalizer().format("AfmUtil.Call"));
    // System.exit(1);
    // }
    //
    // boolean toxml = false;
    // String xmlname = "";
    // boolean toefm = false;
    // String efmname = "";
    // ArrayList enclist = new ArrayList();
    // boolean toenc = false;
    // String encname = "";
    // boolean tomap = false;
    // boolean topl = false;
    // boolean enccheck = false;
    // boolean missingGlyph = false;
    // String outdir = ".";
    // String file = "";
    //
    // int i = 0;
    // do {
    // if ("-x".equals(args[i]) || "--xml".equals(args[i])) {
    // if (i + 1 < args.length) {
    // toxml = true;
    // xmlname = args[++i];
    // }
    // } else if ("-e".equals(args[i]) || "--efm".equals(args[i])) {
    // if (i + 1 < args.length) {
    // toefm = true;
    // efmname = args[++i];
    // }
    //
    // } else if ("-c".equals(args[i]) || "--createenc".equals(args[i])) {
    // if (i + 1 < args.length) {
    // toenc = true;
    // encname = args[++i];
    // }
    // } else if ("--enccheck".equals(args[i])) {
    // enccheck = true;
    // } else if ("--missingglyph".equals(args[i])) {
    // missingGlyph = true;
    // } else if ("-v".equals(args[i]) || "--encvector".equals(args[i])) {
    // if (i + 1 < args.length) {
    // enclist.add(args[++i]);
    // }
    // } else if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
    // if (i + 1 < args.length) {
    // outdir = args[++i];
    // }
    // } else if ("-m".equals(args[i]) || "--map".equals(args[i])) {
    // tomap = true;
    // } else if ("-p".equals(args[i]) || "--pl".equals(args[i])) {
    // topl = true;
    // } else {
    // file = args[i];
    // }
    // i++;
    // } while (i < args.length);
    //
    // afm.setToxml(toxml);
    // afm.setXmlname(xmlname);
    // afm.setEnclist(enclist);
    // afm.setToenc(toenc);
    // afm.setEncname(encname);
    // afm.setOutdir(outdir);
    // afm.setTomap(tomap);
    // afm.setTopl(topl);
    // afm.setEnccheck(enccheck);
    // afm.setMissingGlyph(missingGlyph);
    //
    // afm.doIt(file);
    // }

    /**
     * The afm file.
     */
    private File afmfile;

    /**
     * efactor.
     */
    private float efactor = 1.0f;

    /**
     * Check the encoding.
     */
    private boolean enccheck = false;

    // /**
    // * The list for the encoding vectors.
    // */
    // private List<String> enclist = new ArrayList<String>();

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
     * Writer for the map file.
     */
    private BufferedWriter mapout = null;

    /**
     * Check, if for a encoding vector some glyphs are missing.
     */
    private boolean missingGlyph = false;

    /**
     * The directory for the output.
     */
    private String outdir = ".";

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * slant.
     */
    private float slant = 0.0f;

    /**
     * Create encoding vectors.
     */
    private boolean toenc = false;

    /**
     * Create a map file.
     */
    private boolean tomap = false;

    /**
     * Create a pl file.
     */
    private boolean topl = false;

    /**
     * Create a xml file.
     */
    private boolean toxml = false;

    /**
     * The xml file name.
     */
    private String xmlname = "";

    // /**
    // * Create the encoding files.
    // *
    // * @param names The names for the files.
    // * @throws IOException if a IO-error occurred.
    // */
    // private void createEncFiles(List<String> names) throws IOException {
    //
    // int cnt = 0;
    // int filecnt = 0;
    // char filechar = 'a';
    // String na = "";
    // BufferedWriter out = null;
    // String afm = removeExtensions(afmfile);
    // for (int i = 0, n = names.size(); i < n; i++) {
    // if (cnt == 0) {
    // na = encname + filechar;
    // File newenc = new File(outdir + File.separator + na + ".enc");
    // out = new BufferedWriter(new FileWriter(newenc));
    // out.write("% " + createVersion() + "\n");
    // out.write("/" + na + "Encoding [\n");
    //
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
    //
    // }
    // EncGlpyh eg =
    // new EncGlpyh(afm, afm + na, names.get(i).toString(), cnt);
    // glyphmap.put(eg.getGlyphname(), eg);
    //
    // out.write("% " + cnt++ + "\n");
    // out.write("/" + names.get(i) + "\n");
    // if (cnt == NUMBEROFGLYPHS) {
    // out.write("] def\n");
    // out.close();
    // cnt = 0;
    // filecnt++;
    // filechar = (char) (filechar + 1);
    // }
    // }
    // if (cnt != 0) {
    // for (int i = cnt; i < NUMBEROFGLYPHS; i++) {
    // out.write("% " + i + "\n");
    // out.write("/.notdef\n");
    // }
    // out.write("] def\n");
    // out.close();
    // }
    // getLogger().severe(
    // getLocalizer().format("AfmUtil.EncCreate",
    // String.valueOf(names.size()), String.valueOf(filecnt)));
    //
    // }

    // /**
    // * Create the include file for the package.
    // *
    // * @throws IOException if an IO-error occurred.
    // */
    // private void createIncSty() throws IOException {
    //
    // BufferedWriter out =
    // new BufferedWriter(new FileWriter(outdir + File.separator
    // + removeExtensions(afmfile) + ".inc"));
    //
    // Iterator<String> it = glyphmap.keySet().iterator();
    // while (it.hasNext()) {
    // String key = it.next();
    // EncGlpyh eg = glyphmap.get(key);
    // out.write(eg.toString());
    // out.newLine();
    // }
    // out.close();
    // }

    /**
     * Create a new object.
     * 
     * @param c The class for the logger.
     * @throws ConfigurationException from the configuration system.
     */
    protected AfmUtil(Class<?> c) throws ConfigurationException {

        super(c);

        glyphmap = new TreeMap<String, EncGlpyh>();
    }

    /**
     * Create a pl file.
     * 
     * @param enc The encoding vector
     * @param name The name of the pl file.
     * @throws IOException if an IO-error occurred.
     */
    private void createPl(EncReader enc, String name) throws IOException {

        // PlWriter pl =
        // new PlWriter(new FileOutputStream(outdir + File.separator
        // + name + ".pl"));
        //
        // pl.addComment(createVersion());
        // pl.plopen("FAMILY").addStr(
        // parser.getHeader().getFontname().toUpperCase()).plclose();
        // pl.plopen("CODINGSCHEME").addStr(enc.getEncname().toUpperCase())
        // .plclose();
        // pl.plopen("DESIGNSIZE").addReal(10.0f).plclose();
        // pl.addComment("DESIGNSIZE IS IN POINTS");
        // pl.addComment("OTHER SIZES ARE MULTIPLES OF DESIGNSIZE");
        // pl.plopen("CHECKSUM").addOct(0).plclose();
        //
        // printFontDimen(pl);
        //
        // printLigTable(enc, pl);
        //
        // printCharacter(enc, pl);
        //
        // // close
        // pl.close();
    }

    /**
     * Create the comment with ExTeX-version and date.
     * 
     * @return Returns the comment with the ExTeX-Version and the date.
     */
    protected String createVersion() {

        Calendar cal = Calendar.getInstance();
        return getLocalizer().format("AfmUtil.Version", ExTeX.getVersion(),
            cal.getTime().toString());
    }

    /**
     * do it.
     * 
     * @param file The afm file name.
     * @throws Exception if an error occurs.
     */
    private void doIt(String file) throws Exception {

        InputStream afmin = null;

        // find directly the afm file.
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

        if (enccheck) {
            enccheck();
        }

        if (missingGlyph) {
            missingGlyph();
        }

        if (toxml) {
            toXml();
        }

        if (tomap) {
            mapout =
                    new BufferedWriter(new FileWriter(outdir + File.separator
                            + encname + ".map"));
        }
        // if (toenc) {
        // toEnc();
        // }
        if (mapout != null) {
            mapout.close();
        }
    }

    /**
     * Check the encoding.
     * 
     * @throws IOException if an IO-error occurred.
     */
    private void enccheck() throws IOException {

        // try {
        // AfmEncCheck check = new AfmEncCheck(parser, getFinder());
        //
        // BufferedOutputStream out =
        // new BufferedOutputStream(new FileOutputStream(outdir
        // + File.separator + parser.getHeader().getFontname()
        // + ".pdf"));
        // check.createPdfTable(out, enclist);
        //
        // out.close();
        //
        // } catch (Exception e) {
        // throw new IOException(e.getMessage());
        // }
    }

    /**
     * Returns the enclist.
     * 
     * @return Returns the enclist.
     */
    public List<String> getEnclist() {

        return enclist;
    }

    /**
     * Returns the encname.
     * 
     * @return Returns the encname.
     */
    public String getEncname() {

        return encname;
    }

    /**
     * Getter for outdir.
     * 
     * @return Returns the outdir.
     */
    public String getOutdir() {

        return outdir;
    }

    // fixnum

    /**
     * Returns the xmlname.
     * 
     * @return Returns the xmlname.
     */
    public String getXmlname() {

        return xmlname;
    }

    /**
     * Returns the enccheck.
     * 
     * @return Returns the enccheck.
     */
    public boolean isEnccheck() {

        return enccheck;
    }

    /**
     * Returns the missingGlyph.
     * 
     * @return Returns the missingGlyph.
     */
    public boolean isMissingGlyph() {

        return missingGlyph;
    }

    /**
     * Returns the toenc.
     * 
     * @return Returns the toenc.
     */
    public boolean isToenc() {

        return toenc;
    }

    /**
     * Returns the tomap.
     * 
     * @return Returns the tomap.
     */
    public boolean isTomap() {

        return tomap;
    }

    /**
     * Returns the topl.
     * 
     * @return Returns the topl.
     */
    public boolean isTopl() {

        return topl;
    }

    /**
     * Returns the toxml.
     * 
     * @return Returns the toxml.
     */
    public boolean isToxml() {

        return toxml;
    }

    /**
     * Print missing glyphs.
     * 
     * @throws IOException if an IO-error occurred.
     */
    private void missingGlyph() throws IOException {

        // try {
        // AfmEncCheck check = new AfmEncCheck(parser, getFinder());
        //
        // BufferedOutputStream out =
        // new BufferedOutputStream(new FileOutputStream(outdir
        // + File.separator + parser.getHeader().getFontname()
        // + ".missing"));
        // check.printMissingGlyphs(out, enclist);
        //
        // out.close();
        //
        // } catch (Exception e) {
        // throw new IOException(e.getMessage());
        // }

    }

    // /**
    // * Print Character.
    // *
    // * @param enc The encoding table.
    // * @param pl The pl writer.
    // */
    // private void printCharacter(EncReader enc, PlWriter pl) {
    //
    // String[] table = enc.getTable();
    // for (int i = 0; i < table.length; i++) {
    //
    // String glyph = table[i].replaceAll("/", "");
    // AfmCharMetric cm = parser.getAfmCharMetric(glyph);
    //
    // if (cm != null) {
    // pl.plopen("CHARACTER").addChar((short) i);
    // pl.addComment(cm.getN());
    // pl.addCharMetric(scale((long) cm.getWx()), "CHARWD");
    // pl.addCharMetric(scale((long) cm.getBury()), "CHARHT");
    // pl.addCharMetric(scale(-(long) cm.getBlly()), "CHARDP");
    // if (cm.getBurx() > cm.getWx()) {
    // pl.addCharMetric(scale((long) (cm.getBurx() - cm.getWx())),
    // "CHARIC");
    // }
    // pl.plclose();
    // }
    // }
    // }

    // /**
    // * Print the FONTDIMEN.
    // *
    // * @param pl The pl writer.
    // */
    // private void printFontDimen(PlWriter pl) {
    //
    // pl.plopen("FONTDIMEN");
    //
    // // slant
    // double newslant =
    // slant
    // - efactor
    // * Math.tan(parser.getHeader().getItalicangle()
    // * (Math.PI / 180.0));
    // pl.plopen("SLANT").addReal((long) (FIXFACTOR * newslant + 0.5))
    // .plclose();
    //
    // // space
    // int fontspace = 0;
    // AfmCharMetric space = parser.getAfmCharMetric("space");
    // if (space != null) {
    // fontspace = (int) space.getWx();
    // } else {
    // space = parser.getAfmCharMetric(32);
    // if (space != null) {
    // fontspace = (int) space.getWx();
    // } else {
    // fontspace = transform(500, 0);
    // }
    // }
    // pl.plopen("SPACE").addReal(scale(fontspace)).plclose();
    //
    // // stretch
    // pl.plopen("STRETCH").addReal(
    // (parser.getHeader().isFixedpitch()
    // ? 0
    // : scale((long) (300 * efactor + 0.5)))).plclose();
    // // shrink
    // pl.plopen("SHRINK").addReal(
    // (parser.getHeader().isFixedpitch()
    // ? 0
    // : scale((long) (100 * efactor + 0.5)))).plclose();
    //
    // // xheight
    // pl.plopen("XHEIGHT").addReal(
    // scale((long) parser.getHeader().getXheight())).plclose();
    //
    // // quad
    // pl.plopen("QUAD").addReal(scale((long) (1000 * efactor + 0.5)))
    // .plclose();
    // pl.plclose();
    // }

    // /**
    // * Print LigTable.
    // *
    // * @param enc The encoding vector.
    // * @param pl The pl writer.
    // */
    // private void printLigTable(EncReader enc, PlWriter pl) {
    //
    // pl.plopen("LIGTABLE");
    //
    // String[] table = enc.getTable();
    // for (int i = 0; i < table.length; i++) {
    //
    // String glyph = table[i].replaceAll("/", "");
    // AfmCharMetric cm = parser.getAfmCharMetric(glyph);
    //
    // if (cm != null && (cm.isKerning() || cm.isLigatur())) {
    // pl.plopen("LABEL").addChar((short) i).plclose();
    // pl.addComment(cm.getN());
    // if (cm.isLigatur()) {
    // Map<String, String> ligmap = cm.getL();
    // Iterator<String> it = ligmap.keySet().iterator();
    // while (it.hasNext()) {
    // String letter = it.next();
    // String lig = ligmap.get(letter);
    // short posletter = (short) enc.getPosition(letter);
    // short poslig = (short) enc.getPosition(lig);
    // if (posletter >= 0 && poslig >= 0) {
    // pl.plopen("LIG").addChar(posletter).addChar(poslig)
    // .plclose();
    // }
    // }
    // }
    // if (cm.isKerning()) {
    // List<AfmKernPairs> kernlist = cm.getK();
    // for (int k = 0, n = kernlist.size(); k < n; k++) {
    // AfmKernPairs kp = kernlist.get(k);
    // String pre = kp.getCharpre();
    // String post = kp.getCharpost();
    // float size = kp.getKerningsize();
    // short pospre = (short) enc.getPosition(pre);
    // short pospost = (short) enc.getPosition(post);
    // if (pospre >= 0 && pospost >= 0) {
    // pl.plopen("KRN").addChar(pospost).addReal(
    // scale((long) size)).plclose();
    // }
    // }
    // }
    // pl.plopen("STOP").plclose();
    // }
    // }
    //
    // pl.plclose();
    // }

    // /**
    // * Read all glyph names from the encoding vectors.
    // *
    // * @param readenc The list for the names.
    // * @throws IOException if an IO-error occurred.
    // * @throws ConfigurationException from the configuration system.
    // * @throws FontException if a font error occurred.
    // */
    // private void readAllGlyphName(List<String> readenc)
    // throws IOException,
    // ConfigurationException,
    // FontException {
    //
    // for (String encv : enclist) {
    //
    // InputStream encin = null;
    // File encfile = new File(encv);
    // if (encfile.canRead()) {
    // encin = new FileInputStream(encfile);
    // } else {
    // // use the file finder
    // encin = getFinder().findResource(encfile.getName(), "");
    // }
    //
    // if (encin == null) {
    // throw new FileNotFoundException(encv);
    // }
    //
    // EncReader enc = new EncReader(encin);
    //
    // String[] table = enc.getTable();
    //
    // for (int k = 0; k < table.length; k++) {
    // String name = table[k].replaceAll("/", "");
    // readenc.add(name);
    //
    // EncGlpyh eg =
    // new EncGlpyh(removeExtensions(afmfile),
    // removeExtensions(afmfile) + removeExtensions(encv),
    // name, k);
    //
    // glyphmap.put(eg.getGlyphname(), eg);
    //
    // }
    //
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
    //
    // if (topl) {
    // createPl(enc, encname + encv.replaceAll("\\.[eE][nN][cC]", ""));
    // }
    // }
    // }

    // /**
    // * Read the glyph names.
    // *
    // * @return Returns the glyph name list.
    // */
    // private List<String> readGlyphNames() {
    //
    // List<AfmCharMetric> cmlist = parser.getAfmCharMetrics();
    // List<String> names = new ArrayList<String>(cmlist.size());
    //
    // for (AfmCharMetric cm : cmlist) {
    // names.add(cm.getN());
    // }
    // return names;
    // }

    // /**
    // * Remove existing glyph name from the list.
    // *
    // * @param readenc The existing glyph names.
    // * @param names The glyph names from the afm file.
    // */
    // private void removeExistingNames(List<String> readenc, List<String>
    // names) {
    //
    // for (String name : readenc) {
    // names.remove(name);
    // }
    // }

    /**
     * Returns only the name of a file.
     * 
     * @param f The file.
     * @return Returns only the name of a file.
     */
    protected String removeExtensions(File f) {

        return removeExtensions(f.getName());
    }

    /**
     * Returns only the name of a file.
     * 
     * @param n The name.
     * @return Returns only the name of a file.
     */
    protected String removeExtensions(String n) {

        StringBuffer buf = new StringBuffer();

        boolean dotfound = false;
        for (int i = n.length() - 1; i >= 0; i--) {
            char c = n.charAt(i);
            if (!dotfound && c == '.') {
                dotfound = true;
                continue;
            }
            if (dotfound) {
                if (c == '/' || c == '\\') {
                    break;
                }
                buf.insert(0, c);
            }
        }

        return buf.toString();
    }

    /**
     * scale (from afm2tfm.c).
     * 
     * @param value The value to scale
     * @return Scale the value.
     */
    private double scale(long value) {

        // (((what / 1000) << 20) + (((what % 1000) << 20) + 500) / 1000);
        return value / 1000d;
    }

    /**
     * Set the enccheck.
     * 
     * @param check The enccheck to set.
     */
    public void setEnccheck(boolean check) {

        enccheck = check;
    }

    /**
     * The enclist to set.
     * 
     * @param list The enclist to set.
     */
    public void setEnclist(List<String> list) {

        enclist = list;
    }

    /**
     * The encname to set.
     * 
     * @param name The encname to set.
     */
    public void setEncname(String name) {

        encname = name;
    }

    /**
     * Set the missingGlyph.
     * 
     * @param missingglyph The missingGlyph to set.
     */
    public void setMissingGlyph(boolean missingglyph) {

        this.missingGlyph = missingglyph;
    }

    /**
     * Setter for outdir.
     * 
     * @param out The outdir to set.
     */
    public void setOutdir(String out) {

        outdir = out;
    }

    /**
     * The toenc to set.
     * 
     * @param enc The toenc to set.
     */
    public void setToenc(boolean enc) {

        toenc = enc;
    }

    /**
     * The tomap to set.
     * 
     * @param map The tomap to set.
     */
    public void setTomap(boolean map) {

        tomap = map;
    }

    /**
     * The topl to set.
     * 
     * @param pl The topl to set.
     */
    public void setTopl(boolean pl) {

        topl = pl;
    }

    /**
     * The toxml to set.
     * 
     * @param xml The toxml to set.
     */
    public void setToxml(boolean xml) {

        toxml = xml;
    }

    /**
     * The xmlname to set.
     * 
     * @param name The xmlname to set.
     */
    public void setXmlname(String name) {

        xmlname = name;
    }

    // /**
    // * Create encoding vectors.
    // *
    // * @throws IOException if an IO-error occurred.
    // * @throws ConfigurationException from the configuration system.
    // * @throws FontException if a font error occurred.
    // */
    // private void toEnc()
    // throws IOException,
    // ConfigurationException,
    // FontException {
    //
    // // read all glyphs from the encoding vectors
    // List<String> readenc = new ArrayList<String>();
    // readAllGlyphName(readenc);
    //
    // // get the glyph names form the afm file.
    // List<String> names = readGlyphNames();
    // Collections.sort(names);
    //
    // // remove all names from readenc in names
    // removeExistingNames(readenc, names);
    //
    // createEncFiles(names);
    //
    // createIncSty();
    //
    // }

    // ---------------------------------------------
    // GETTER / SETTER
    // ---------------------------------------------

    /**
     * Export to xml.
     * 
     * @throws IOException if a IO-error occurred.
     */
    private void toXml() throws IOException {

        File xmlfile = new File(outdir + File.separator + xmlname);

        // write to xml-file
        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream(xmlfile), "ISO-8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        writer.writeComment(createVersion());
        parser.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

        getLogger().severe(getLocalizer().format("AfmUtil.XmlCreate", xmlname));
    }

    /**
     * transform (from afm2tfm.c).
     * 
     * @param x x
     * @param y y
     * @return The transform value.
     */
    private int transform(int x, int y) {

        double acc = efactor * x + slant * y;
        return (int) (acc >= 0 ? Math.floor(acc + 0.5) : Math.ceil(acc - 0.5));
    }

}
