/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.util.font.xtf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.extex.framework.configuration.exception.ConfigurationException;

import de.dante.util.font.AbstractFontUtil;

/**
 * Abstract class for all xtf tools.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfInfo extends AbstractFontUtil {

    /**
     * Create a new object.
     *
     * @param c The class for the logging.
     * @throws ConfigurationException if a configuration error occurs.
     */
    protected XtfInfo(Class c) throws ConfigurationException {

        super(c);

    }

    /**
     * Encoding for the dummy text.
     */
    public static final String ISO8859_1 = "ISO8859-1";

    /**
     * The buffer size.
     */
    public static final int BUFFERSIZE = 0xffff;

    /**
     * The directory for the ouput.
     */
    private String output = ".";

    /**
     * The name for the output file.
     */
    private String outputname = "xtfinfo";

    /**
     * Returns the complete output file name.
     *
     * @param ext The file extension with dot
     * @return Returns the complete output file name.
     */
    public String getOutputName(String ext) {

        return output + File.separator + outputname + ext;
    }

    //    /**
    //     * The xtf file name.
    //     */
    //    private String xtfFileName;
    //
    //    /**
    //     * The xtf parser.
    //     */
    //    private XtfReader parser;
    //
    //    /**
    //     * The xtf file object.
    //     */
    //    private File xtffile;

    //    /**
    //     * Print the xtf info and something else.
    //     *
    //     * @throws IOException  if a IO-error occurred.
    //     * @throws ConfigurationException from the configuration system.
    //     * @throws FontException if a font error occurred.
    //     * @throws DocumentException from iText
    //     */
    //    private void doIt() throws IOException, ConfigurationException,
    //            FontException, DocumentException {
    //
    //        if (props.getProperty("xtf.ttfinfo", "false").equals("true")) {
    //            printttfindo();
    //        }
    //
    //        if (props.getProperty("xtf.ttfcopy", "false").equals("true")) {
    //            copyttf();
    //        }
    //
    //        // read the xtf file
    //        InputStream xtfin = null;
    //
    //        // find directly the ttf file.
    //        xtfFileName = props.getProperty("xtf.file", "null");
    //        if (!xtfFileName.equals("null")) {
    //            xtffile = new File(xtfFileName);
    //
    //            if (xtffile.canRead()) {
    //                xtfin = new FileInputStream(xtffile);
    //            } else {
    //                // use the file finder
    //                xtfin = getFinder().findResource(xtffile.getName(), "");
    //            }
    //
    //            if (xtfin == null) {
    //                throw new FileNotFoundException(xtfFileName);
    //            }
    //
    //            parser = new XtfReader(xtfin);
    //
    //            if (props.getProperty("xtf.printhead", "true").equals("true")) {
    //                doHead();
    //            }
    //
    //            if (props.getProperty("xtf.listglyphs", "false").equals("true")) {
    //                listGlyphs();
    //            }
    //
    //            if (props.getProperty("xtf.charinfo", "false").equals("true")) {
    //                charInfo();
    //            }
    //
    //            if (props.getProperty("xtf.glyphinfo", "false").equals("true")) {
    //                glyphInfo();
    //            }
    //
    //            if (props.getProperty("xtf.printglyphs", "false").equals("true")) {
    //                printGlpyhs(props.getProperty("xtf.outdir", "."));
    //            }
    //        }
    //    }

    /**
     * Copy a source file to a destination file.
     *
     * @param src    The source file.
     * @param dst    The destination file.
     * @throws IOException if an IO-error occurred.
     */
    protected void copy(File src, File dst) throws IOException {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                src), BUFFERSIZE);
        BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(dst), BUFFERSIZE);

        byte[] buf = new byte[BUFFERSIZE];
        int size;
        while ((size = in.read(buf)) != -1) {
            out.write(buf, 0, size);
        }

        in.close();
        out.close();
    }

    //    /**
    //     * Print the ttf info.
    //     * @throws IOException if an IO-error occurred.
    //     */
    //    private void printttfindo() throws IOException {
    //
    //        File dir = new File(props.getProperty("xtf.ttfdir", "."));
    //        String outdir = props.getProperty("xtf.outdir", ".");
    //
    //        if (dir.isDirectory()) {
    //            String[] lists = dir.list(new FilenameFilter() {
    //
    //                /**
    //                 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
    //                 */
    //                public boolean accept(File directory, String name) {
    //
    //                    return name.matches(".*\\.[tToO][tT][fF]");
    //                }
    //            });
    //
    //            Arrays.sort(lists);
    //
    //            BufferedWriter out = new BufferedWriter(new FileWriter(outdir
    //                    + File.separator + "xtf.info"));
    //
    //            for (int i = 0; i < lists.length; i++) {
    //
    //                File file = new File(dir.getAbsolutePath() + File.separator
    //                        + lists[i]);
    //                if (file.canRead()) {
    //                    try {
    //                        parser = new XtfReader(new FileInputStream(file));
    //                        TtfTableNAME name = parser.getNameTable();
    //                        int[] platformids = name.getPlatformIDs();
    //
    //                        int pltid = TtfTableNAME.MICROSOFT;
    //                        if (!name.existsPlatfrom(TtfTableNAME.MICROSOFT)) {
    //                            // use the last one
    //                            pltid = platformids[platformids.length - 1];
    //                        }
    //                        out.write(file.getName());
    //                        out.write("\t;\t");
    //                        out.write(name.getRecordString(pltid,
    //                                TtfTableNAME.POSTSCRIPTNAME));
    //                        out.newLine();
    //                        out.flush();
    //                        getLogger().severe(
    //                                getLocalizer().format(
    //                                        "XtfInfo.TtfInfo",
    //                                        file.getName(),
    //                                        name.getRecordString(pltid,
    //                                                TtfTableNAME.POSTSCRIPTNAME)));
    //                    } catch (Exception e) {
    //                        // ignore error
    //                        e.printStackTrace();
    //                    }
    //                }
    //            }
    //            out.close();
    //        }
    //
    //    }

    /**
     * font size 6.
     */
    protected static final int FONT_6 = 6;

    /**
     * font size 12.
     */
    protected static final int FONT_12 = 12;

    /**
     * font size 18.
     */
    protected static final int FONT_18 = 18;

    /**
     * max. glyphs in a ttf font.
     */
    protected static final int MAXGLYPHS = 0xffff;

    /**
     * How much glyphs in one table row.
     */
    protected static final int BLOCK = 8;

    //    /**
    //     * Print the glyphs in a pdf file.
    //     * @param outdir    The directory for the output.
    //     * @throws DocumentException from iText
    //     * @throws IOException if an IO-error occurred.
    //     */
    //    private void printGlpyhs(String outdir) throws IOException,
    //            DocumentException {
    //
    //        Document document = new Document();
    //
    //        if (!xtffile.canRead()) {
    //            getLogger().severe(getLocalizer().format("XtfInfo.ErrPG"));
    //
    //        } else {
    //
    //            String pdfname = xtffile.getName().replaceAll("\\.[tToO][tT][fF]",
    //                    "");
    //            PdfWriter.getInstance(document, new FileOutputStream(outdir
    //                    + File.separator + pdfname + ".pdf"));
    //            document.open();
    //
    //            BaseFont hex = new BaseFont(BaseFont.HELVETICA, FONT_6);
    //
    //            BaseFont basefont = BaseFont.createFont(xtffile.getAbsolutePath(),
    //                    BaseFont.IDENTITY_H, true);
    //
    //            BaseFont font = new BaseFont(basefont, FONT_18, BaseFont.NORMAL);
    //            BaseFont font12 = new BaseFont(basefont, FONT_12, BaseFont.NORMAL);
    //
    //            // first page
    //
    //            PdfPTable table1 = new PdfPTable(2);
    //            table1.getDefaultCell().setBorderWidth(0);
    //            // table1.addCell("XtfInfo:    (ExTeX " + ExTeX.getVersion() + ")");
    //            table1.addCell("FontInfo");
    //            table1.addCell("");
    //            table1.addCell("Filename");
    //            table1.addCell(xtffile.getName());
    //            table1.addCell("Fullname");
    //            table1.addCell(parser.getFontFamilyName());
    //
    //            // name
    //            TtfTableNAME name = parser.getNameTable();
    //            int[] platformids = name.getPlatformIDs();
    //
    //            int pltid = TtfTableNAME.MICROSOFT;
    //            if (!name.existsPlatfrom(TtfTableNAME.MICROSOFT)) {
    //                // use the last one
    //                pltid = platformids[platformids.length - 1];
    //            }
    //
    //            table1.addCell("Platform");
    //            table1.addCell(TtfTableNAME.getPlatformName(pltid));
    //            table1.addCell("PSFontname");
    //            table1.addCell(name.getRecordString(pltid,
    //                    TtfTableNAME.POSTSCRIPTNAME));
    //            table1.addCell("Subfamily");
    //            table1.addCell(name.getRecordString(pltid,
    //                    TtfTableNAME.FONTSUBFAMILYNAME));
    //            table1.addCell("Familyname");
    //            table1.addCell(name.getRecordString(pltid,
    //                    TtfTableNAME.FONTFAMILYNAME));
    //            table1.addCell("Version");
    //            table1.addCell(name.getRecordString(pltid,
    //                    TtfTableNAME.VERSIONSTRING));
    //            table1.addCell("Number of Glpyhs");
    //            table1.addCell(String.valueOf(parser.getNumberOfGlyphs()));
    //            table1.addCell("Numbers");
    //            table1.addCell(new Phrase("01234567890", font12));
    //            table1.addCell("Umlaute");
    //            table1.addCell(new Phrase("Ö Ä Ü ö ä ü ß", font12));
    //            table1.addCell("Ligaturen");
    //            table1.addCell(new Phrase("fi ff ffl ffi", font12));
    //
    //            document.add(table1);
    //
    //            // add dummy text
    //            URL dummytxt = getClass().getResource("XtfInfo.txt");
    //            if (dummytxt != null) {
    //
    //                InputStream dummyin = dummytxt.openStream();
    //                if (dummyin != null) {
    //                    BufferedReader reader = new BufferedReader(
    //                            new InputStreamReader(dummyin, ISO8859_1));
    //
    //                    String zeile;
    //                    while ((zeile = reader.readLine()) != null) {
    //                        Phrase dummy = new Phrase(zeile, font);
    //                        document.add(new Paragraph(dummy));
    //                    }
    //                }
    //            }
    //            document.newPage();
    //
    //            // glyph table
    //            PdfPTable table = new PdfPTable(BLOCK);
    //            table.setWidthPercentage(100);
    //            table.getDefaultCell().setBorderWidth(1);
    //            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    //            for (int k = 0; k < MAXGLYPHS; k += BLOCK) {
    //
    //                boolean glyphexists = false;
    //                for (int b = 0; b < BLOCK; b++) {
    //                    if (basefont.charExists((char) (k + b))) {
    //                        glyphexists = true;
    //                        break;
    //                    }
    //                }
    //
    //                if (glyphexists) {
    //                    for (int b = 0; b < BLOCK; b++) {
    //
    //                        int kk = k + b;
    //                        char c = (char) kk;
    //
    //                        if (basefont.charExists(c)) {
    //                            Phrase ph = new Phrase(FONT_12, new String(
    //                                    new char[]{c}), font);
    //                            ph.add(new Phrase(FONT_12, "\n\n" + cst(kk) + "  ("
    //                                    + kk + ")", hex));
    //
    //                            String namettf = parser.mapCharCodeToGlyphname(kk,
    //                                    TtfTableCMAP.PLATFORM_MICROSOFT,
    //                                    TtfTableCMAP.ENCODING_ISO_ISO10646);
    //
    //                            if (namettf == null) {
    //                                namettf = "-xxx-";
    //                            }
    //                            Phrase glyphttf = new Phrase("\n" + namettf, hex);
    //
    //                            ph.add(glyphttf);
    //                            table.addCell(ph);
    //                        } else {
    //                            Phrase ph = new Phrase("\u00a0");
    //                            table.addCell(ph);
    //                        }
    //                    }
    //                }
    //            }
    //            document.add(table);
    //
    //        }
    //        document.close();
    //
    //    }

    /**
     * Convert a int to a tow digit hex string.
     * @param c The int.
     * @return Returns the hex value.
     */
    protected String cst(int c) {

        String s = "0000" + Integer.toHexString(c).toUpperCase();
        return "0x" + s.substring(s.length() - 4);
    }

    // ----------------------------------------------------------------
    // GETTER / SETTER
    // ----------------------------------------------------------------

    /**
     * Getter for output.
     *
     * @return Returns the output.
     */
    public String getOutput() {

        return output;
    }

    /**
     * Setter for output.
     *
     * @param out The output to set.
     */
    public void setOutput(String out) {

        output = out;
    }

    /**
     * Getter for outputname.
     *
     * @return Returns the outputname.
     */
    public String getOutputname() {

        return outputname;
    }

    /**
     * Setter for outputname.
     *
     * @param name The outputname to set.
     */
    public void setOutputname(String name) {

        outputname = name;
    }

    //    /**
    //     * CharInfo.
    //     */
    //    private void charInfo() {
    //
    //        TtfTableCMAP cmap = parser.getCmapTable();
    //        IndexEntry[] entries = cmap.getEntries();
    //
    //        int charcode = 1;
    //        try {
    //            charcode = Integer.parseInt(props.getProperty("xtf.charcode", "1"));
    //        } catch (NumberFormatException e) {
    //            // ignore
    //            charcode = -1;
    //        }
    //
    //        for (int i = 0; i < entries.length; i++) {
    //            int pid = entries[i].getPlatformId();
    //            int eid = entries[i].getEncodingId();
    //            String platformname = entries[i].getPlatformName();
    //            String encname = entries[i].getEncodingName();
    //            getLogger().severe(
    //                    getLocalizer()
    //                            .format("XtfInfo.CMAP", platformname, encname));
    //            String gn = parser.mapCharCodeToGlyphname(charcode, (short) pid,
    //                    (short) eid);
    //            String cchex = "0x" + Integer.toHexString(charcode);
    //            if (gn == null) {
    //                gn = "-";
    //            }
    //            getLogger().severe(
    //                    getLocalizer().format("XtfInfo.CHARMAP",
    //                            String.valueOf(charcode), cchex, gn));
    //
    //        }
    //
    //    }

    //    /**
    //     * GlyphInfo.
    //     */
    //    private void glyphInfo() {
    //
    //        TtfTablePOST post = parser.getPostTable();
    //        String glyphName = props.getProperty("xtf.glyphname", "A");
    //        int gpos = post.getGlyphNamePosition(glyphName);
    //
    //        getLogger()
    //                .severe(
    //                        getLocalizer().format("XtfInfo.GlyphPOS", glyphName,
    //                                String.valueOf(gpos),
    //                                "0x" + Integer.toHexString(gpos)));
    //
    //        if (gpos >= 0) {
    //            TtfTableHMTX hmtx = parser.getHmtxTable();
    //
    //            int width = hmtx.getAdvanceWidth(gpos);
    //            getLogger().severe(
    //                    getLocalizer().format("XtfInfo.GlyphWidth",
    //                            String.valueOf(width)));
    //
    //        }
    //
    //    }

    //    /**
    //     * List the glyphs.
    //     */
    //    private void listGlyphs() {
    //
    //        TtfTablePOST post = parser.getPostTable();
    //        String[] glyphs = post.getPsGlyphName();
    //        Arrays.sort(glyphs);
    //
    //        for (int i = 0; i < glyphs.length; i++) {
    //            getLogger().severe(
    //                    getLocalizer().format("XtfInfo.ListGlyph", glyphs[i]));
    //        }
    //
    //    }

    //    /**
    //     * Print the head information.
    //     */
    //    private void doHead() {
    //
    //        getLogger().severe(
    //                getLocalizer().format("XtfInfo.Head", ExTeX.getVersion()));
    //        getLogger().severe(
    //                getLocalizer().format("XtfInfo.Filename", xtfFileName));
    //        getLogger().severe(
    //                getLocalizer().format("XtfInfo.Fullname",
    //                        parser.getFontFamilyName()));
    //
    //        // name
    //        TtfTableNAME name = parser.getNameTable();
    //        int[] platformids = name.getPlatformIDs();
    //
    //        int pltid = TtfTableNAME.MICROSOFT;
    //        if (!name.existsPlatfrom(TtfTableNAME.MICROSOFT)) {
    //            // use the last one
    //            pltid = platformids[platformids.length - 1];
    //        }
    //
    //        getLogger().severe(
    //                getLocalizer().format("XtfInfo.Platform",
    //                        TtfTableNAME.getPlatformName(pltid)));
    //        getLogger().severe(
    //                getLocalizer().format(
    //                        "XtfInfo.PSName",
    //                        name
    //                                .getRecordString(pltid,
    //                                        TtfTableNAME.POSTSCRIPTNAME)));
    //        getLogger().severe(
    //                getLocalizer().format(
    //                        "XtfInfo.SubFamily",
    //                        name.getRecordString(pltid,
    //                                TtfTableNAME.FONTSUBFAMILYNAME)));
    //        getLogger().severe(
    //                getLocalizer()
    //                        .format(
    //                                "XtfInfo.Version",
    //                                name.getRecordString(pltid,
    //                                        TtfTableNAME.VERSIONSTRING)));
    //        getLogger().severe(
    //                getLocalizer().format("XtfInfo.NumGlyphs",
    //                        String.valueOf(parser.getNumberOfGlyphs())));
    //
    //    }

    //    /**
    //     * main.
    //     * @param args  The command line arguments.
    //     * @throws Exception if a error occurs.
    //     */
    //    public static void main(String[] args) throws Exception {
    //
    //        XtfInfo info = new XtfInfo();
    //        Properties p = info.getProperties();
    //
    //        if (!(p.getProperty("xtf.ttfinfo", "false").equals("true") || p
    //                .getProperty("xtf.ttfcopy", "false").equals("true"))
    //                || p.getProperty("xtf.printfonts", "false").equals("true")) {
    //
    //            if (args.length < PARAMETER) {
    //                info.getLogger().severe(
    //                        info.getLocalizer().format("XtfInfo.Call"));
    //                System.exit(1);
    //            }
    //
    //            int i = 0;
    //            do {
    //                if ("-p".equals(args[i]) || "--props".equals(args[i])) {
    //                    if (i + 1 < args.length) {
    //                        File propsfile = new File(args[++i]);
    //                        if (propsfile.canRead()) {
    //                            p.load(new FileInputStream(propsfile));
    //                        }
    //                    }
    //
    //                } else if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
    //                    if (i + 1 < args.length) {
    //                        p.setProperty("xtf.outdir", args[++i]);
    //                    }
    //                } else {
    //                    p.setProperty("xtf.file", args[i]);
    //                }
    //                i++;
    //            } while (i < args.length);
    //        }
    //        info.doIt();
    //    }

}
