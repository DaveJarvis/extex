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

package org.extex.util.font.xtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.font.format.xtf.TtfTableCMAP;
import org.extex.font.format.xtf.TtfTableNAME;
import org.extex.font.format.xtf.XtfReader;
import org.extex.framework.configuration.exception.ConfigurationException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Print a glyph table from the fonts.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfPrintFonts extends XtfInfo {

    /**
     * parameter.
     */
    private static final int PARAMETER = 1;

    /**
     * The main method.
     *
     * @param args  The command line.
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        XtfPrintFonts info = new XtfPrintFonts();

        if (args.length < PARAMETER) {
            info.getLogger().severe(info.getLocalizer().format("XtfInfo.Call"));
            System.exit(1);
        }

        int i = 0;
        do {
            if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                if (i + 1 < args.length) {
                    info.setOutput(args[++i]);
                }
            } else if ("-p".equals(args[i]) || "--pdf".equals(args[i])) {
                info.setCreatePdf(true);
            } else if ("-n".equals(args[i]) || "--filename".equals(args[i])) {
                if (i + 1 < args.length) {
                    info.setOutputname(args[++i]);
                }
            } else {
                info.addXtfName(args[i]);
            }
            i++;
        } while (i < args.length);

        // set pdf (default) (only, if no more output format is defined)
        info.setCreatePdf(true);

        info.doIt();
    }

    /**
     * Create a pdf file.
     */
    private boolean createPdf = false;

    /**
     * The list for the xtf files.
     */
    private List<String> xtflist = new ArrayList<String>();

    /**
     * Creates a new object.
     *
     * @throws ConfigurationException if a configuration error occurs.
     */
    public XtfPrintFonts() throws ConfigurationException {

        super(XtfPrintFonts.class);
    }

    /**
     * Add a xtf name to the list.
     *
     * @param name  The xtf name.
     */
    public void addXtfName(String name) {

        if (name != null) {
            xtflist.add(name);
        }
    }

    /**
     * Do the work.
     *
     * @throws IOException if a IO-error occurred.
     * @throws ConfigurationException from the config system.
     * @throws DocumentException from the pdf system.
     */
    private void doIt() throws IOException, ConfigurationException,
            DocumentException {

        getLogger().severe(getLocalizer().format("XtfInfo.start"));

        // read the xtf files
        XtfReader[] reader = new XtfReader[xtflist.size()];
        String[] xtffiles = new String[xtflist.size()];
        BaseFont[] basefonts = new BaseFont[xtflist.size()];
        Font[] fonts = new Font[xtflist.size()];

        for (int i = 0; i < reader.length; i++) {

            xtffiles[i] = xtflist.get(i);
            File xtffile = new File(xtffiles[i]);
            InputStream xtfin = null;

            getLogger().severe(getLocalizer().format("XtfInfo.font", xtffile));

            if (xtffile.canRead()) {
                xtfin = new FileInputStream(xtffile);
            }
            //            else {
            //                // use the file finder
            //                xtfin = getFinder().findResource(xtffile.getName(), "");
            //            }

            if (xtfin == null) {
                throw new FileNotFoundException(xtffiles[i]);
            }

            reader[i] = new XtfReader(xtfin);

            basefonts[i] = BaseFont.createFont(xtffile.getAbsolutePath(),
                    BaseFont.IDENTITY_H, true);

            fonts[i] = new Font(basefonts[i], FONT_18, Font.NORMAL);

        }

        // create the pdf file
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(
                getOutputName(".pdf")));
        document.open();

        Font hex = new Font(Font.HELVETICA, FONT_6);
        Font gname = new Font(Font.HELVETICA, FONT_12);

        // glyph table
        PdfPTable table = new PdfPTable(reader.length + 1);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorderWidth(1);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // header
        Phrase p = new Phrase(
                getLocalizer().format("XtfInfo.Glyphname") + "\n", gname);
        p.add(new Phrase("\n", hex));
        table.addCell(p);

        for (int i = 0; i < reader.length; i++) {

            File xtffile = new File(xtffiles[i]);
            String fontname = xtffile.getName();

            // if there is a postscript-name, then use the postscript-name instead.
            TtfTableNAME name = reader[i].getNameTable();

            int[] platformids = name.getPlatformIDs();

            int pltid = TtfTableNAME.MICROSOFT;
            if (!name.existsPlatfrom(TtfTableNAME.MICROSOFT)) {
                // use the last one
                pltid = platformids[platformids.length - 1];
            }

            String psname = name.getRecordString(pltid,
                    TtfTableNAME.POSTSCRIPTNAME);
            if (psname != null) {
                fontname = psname;
            }

            table.addCell(new Phrase(fontname, hex));

        }

        // table
        for (int k = 0; k < MAXGLYPHS; k++) {

            if (glyphExists(k, basefonts)) {

                // first column
                String glyphname = getGlyphName(k, basefonts, reader);
                Phrase ph = new Phrase(glyphname, gname);
                ph.add(new Phrase("\n\n" + cst(k) + "  (" + k + ")", hex));
                table.addCell(ph);

                // next columns
                for (int i = 0; i < reader.length; i++) {
                    Phrase phf = new Phrase(FONT_18, new String(
                            new char[]{(char) k}), fonts[i]);
                    phf.add(new Phrase("\u00a0\n", hex));
                    table.addCell(phf);
                }
            }
        }
        document.add(table);
        document.close();

        getLogger().severe(
                getLocalizer().format("XtfInfo.stop", getOutputName(".pdf")));

    }

    /**
     * Check, if a glyph at the index exits in the fonts.
     *
     * @param idx       The index position.
     * @param basefonts The base fonts.
     * @param reader    The xtf parsers.
     * @return Returns <code>true</code>, if the glyph is found
     *         at least in one font.
     */
    private String getGlyphName(int idx, BaseFont[] basefonts,
            XtfReader[] reader) {

        String name = "not found";

        for (int b = 0; b < basefonts.length; b++) {
            if (basefonts[b].charExists((char) idx)) {

// commented out to fix a compiler error
//                String n = reader[b].mapCharCodeToGlyphname(idx,
//                        TtfTableCMAP.PLATFORM_MICROSOFT,
//                        TtfTableCMAP.ENCODING_ISO_ISO10646);
//
//                if (n != null) {
//                    name = n;
//                    break;
//                }
            }
        }
        return name;
    }

    // ----------------------------------------------------------------
    // GETTER / SETTER
    // ----------------------------------------------------------------

    /**
     * Check, if a glyph at the index exits in the fonts.
     *
     * @param idx       The index position.
     * @param basefonts The base fonts.
     * @return Returns <code>true</code>, if the glyph is found
     *         at least in one font.
     */
    private boolean glyphExists(int idx, BaseFont[] basefonts) {

        boolean glyphexists = false;

        for (int b = 0; b < basefonts.length; b++) {
            if (basefonts[b].charExists((char) idx)) {
                glyphexists = true;
                break;
            }
        }
        return glyphexists;
    }

    /**
     * Getter for createPdf.
     *
     * @return the createPdf
     */
    public boolean isCreatePdf() {

        return createPdf;
    }

    /**
     * Setter for createPdf.
     *
     * @param pdf The createPdf to set.
     */
    public void setCreatePdf(boolean pdf) {

        createPdf = pdf;
    }

}
