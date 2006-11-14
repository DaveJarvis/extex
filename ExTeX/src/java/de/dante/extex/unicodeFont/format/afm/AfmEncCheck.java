/*
 * Copyright (C) 2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.unicodeFont.format.afm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import de.dante.extex.unicodeFont.exception.FontException;
import de.dante.extex.unicodeFont.format.tex.psfontmap.enc.EncReader;
import de.dante.util.framework.configuration.exception.ConfigurationException;
import de.dante.util.resource.ResourceFinder;

/**
 * Check, if the glyphs of some encoding vectors exists.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 1.1 $
 */

public class AfmEncCheck {

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * The resource finder.
     */
    private ResourceFinder finder;

    /**
     * Create a new object.
     *
     * @param afm   The afm parser.
     */
    public AfmEncCheck(final AfmParser afm, final ResourceFinder afinder) {

        parser = afm;
        finder = afinder;
    }

    // ----------------------------------------------

    /**
     * Font helvetica 10.
     */
    private static final Font F10 = new Font(Font.HELVETICA, 10);

    /**
     * Create a overview (pdf) of all glyphs about the position 
     * in a encoding vector.
     *
     * @param out       The output stream.
     * @param enclist   The encoding vectors.
     * @throws IOException if an IO-error occurred.
     * @throws DocumentException if a pdf-error occurred.
     * @throws FontException if a font-error occurred.
     * @throws ConfigurationException from the configuration system.
     */
    public void createPdfTable(final OutputStream out, final List enclist)
            throws IOException, DocumentException, FontException,
            ConfigurationException {

        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        EncReader[] encv = getEncodingVectors(enclist);

        // print header
        PdfPTable table1 = new PdfPTable(2);
        table1.getDefaultCell().setBorderWidth(0);
        table1.addCell("Font");
        table1.addCell(parser.getHeader().getFontname());
        document.add(table1);

        document.add(new Phrase("\n"));

        // print glyph table
        PdfPTable table = new PdfPTable(2 + encv.length);
        //        table.setWidthPercentage(100);

        table.addCell(new CellLeft("glyph"));
        table.addCell(new CellCenter("number"));

        for (int i = 0; i < encv.length; i++) {
            table.addCell(new CellCenter(encv[i].getEncname()));
        }

        ArrayList cmlist = parser.getAfmCharMetrics();

        for (int g = 0, n = cmlist.size(); g < n; g++) {
            AfmCharMetric cm = (AfmCharMetric) cmlist.get(g);
            String glyph = cm.getN();
            PdfPCell cell = new PdfPCell(new Phrase(glyph));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(new CellLeft(glyph));
            table.addCell(new CellCenter(String.valueOf(cm.getC())));

            for (int e = 0; e < encv.length; e++) {
                int pos = encv[e].getPosition(glyph);
                if (pos >= 0) {
                    table.addCell(new CellCenter(String.valueOf(pos)));
                } else {
                    table.addCell(new CellCenter(""));
                }
            }

        }

        document.add(table);
        document.close();
    }

    /**
     * Table cell (left)
     */
    private class CellLeft extends PdfPCell {

        /**
         * Create a new object.
         * @param s The string.
         */
        public CellLeft(String s) {

            super(new Phrase(s, F10));
            setHorizontalAlignment(Element.ALIGN_LEFT);
            setBorderWidth(1);
            setFixedHeight(18);
        }
    }

    /**
     * Table cell (center)
     */
    private class CellCenter extends PdfPCell {

        /**
         * Create a new object.
         * @param s The string.
         */
        public CellCenter(String s) {

            super(new Phrase(s, F10));
            setHorizontalAlignment(Element.ALIGN_CENTER);
            setBorderWidth(1);
            setFixedHeight(18);
        }
    }

    /**
     * Read all encoding vectors.
     * @param enclist   The encoding vectors as list.
     * @return Returns the encoding vectors as array.
     * @throws FontException if a font-error occurred.
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if an IO_error occurred.
     */
    private EncReader[] getEncodingVectors(final List enclist)
            throws FontException, IOException, ConfigurationException {

        EncReader[] encv = new EncReader[enclist.size()];

        for (int i = 0, n = enclist.size(); i < n; i++) {

            InputStream encin = null;
            File encfile = new File((String) enclist.get(i));
            if (encfile.canRead()) {
                encin = new FileInputStream(encfile);
            } else {
                // use the file finder
                encin = finder.findResource(encfile.getName(), "");
            }

            if (encin == null) {
                throw new FileNotFoundException(encfile.toString());
            }

            EncReader enc = new EncReader(encin);
            encv[i] = enc;
        }

        return encv;
    }

}
