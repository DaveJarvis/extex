/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.util.font.tfm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.SortedMap;
import java.util.TreeMap;

import org.extex.font.format.pl.PlWriter;
import org.extex.font.format.tfm.TfmCharInfoArray;
import org.extex.font.format.tfm.TfmCharInfoWord;
import org.extex.font.format.tfm.TfmDepthArray;
import org.extex.font.format.tfm.TfmExtenArray;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.format.tfm.TfmHeaderArray;
import org.extex.font.format.tfm.TfmHeaderLengths;
import org.extex.font.format.tfm.TfmHeightArray;
import org.extex.font.format.tfm.TfmItalicArray;
import org.extex.font.format.tfm.TfmKernArray;
import org.extex.font.format.tfm.TfmKerning;
import org.extex.font.format.tfm.TfmLigKern;
import org.extex.font.format.tfm.TfmLigKernArray;
import org.extex.font.format.tfm.TfmLigature;
import org.extex.font.format.tfm.TfmParamArray;
import org.extex.font.format.tfm.TfmReader;
import org.extex.font.format.tfm.TfmVisitor;
import org.extex.font.format.tfm.TfmWidthArray;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.util.font.AbstractFontUtil;

/**
 * Convert a tfm font to a pl file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Tfm2Pl extends AbstractFontUtil {

    /**
     * The pl visitor.
     */
    private class PlVisitor implements TfmVisitor {

        /**
         * The pl writer.
         */
        private PlWriter writer;

        /**
         * Creates a new object.
         * 
         * @param writer The pl writer.
         */
        public PlVisitor(PlWriter writer) {

            this.writer = writer;
        }

    @Override
        public void end() throws IOException {

        }

    @Override
        public void start() throws IOException {

        }

    @Override
        public void visitTfmCharInfoArray(TfmCharInfoArray charinfo)
                throws IOException {

            for (int i = 0; i < charinfo.getCharinfoword().length; i++) {

                TfmCharInfoWord ciw = charinfo.getCharinfoword()[i];
                if (ciw != null) {
                    writer.plopen("CHARACTER").addChar(
                        (short) (i + charinfo.getBc()));

                    writer.addFixWord(ciw.getWidth(), "CHARWD");
                    writer.addFixWord(ciw.getHeight(), "CHARHT");
                    writer.addFixWord(ciw.getDepth(), "CHARDP");
                    writer.addFixWord(ciw.getItalic(), "CHARIC");

                    if (ciw.foundEntry()) {
                        writer.plopen("COMMENT");
                        if (ciw.getGlyphname() != null) {
                            writer.plopen("NAME").addStr(ciw.getGlyphname())
                                .plclose();
                        }
                        if (ciw.getTop() != TfmCharInfoWord.NOCHARCODE) {
                            writer.plopen("TOP").addDec(ciw.getTop()).plclose();
                        }
                        if (ciw.getMid() != TfmCharInfoWord.NOCHARCODE) {
                            writer.plopen("MID").addDec(ciw.getMid()).plclose();
                        }
                        if (ciw.getBot() != TfmCharInfoWord.NOCHARCODE) {
                            writer.plopen("BOT").addDec(ciw.getBot()).plclose();
                        }
                        if (ciw.getRep() != TfmCharInfoWord.NOCHARCODE) {
                            writer.plopen("REP").addDec(ciw.getRep()).plclose();
                        }
                        // ligature
                        int ligstart = ciw.getLigkernstart();
                        if (ligstart != TfmCharInfoWord.NOINDEX
                                && ciw.getLigKernTable() != null) {

                            for (int k = ligstart; k != TfmCharInfoWord.NOINDEX; k =
                                    ciw.getLigKernTable()[k].nextIndex(k)) {
                                TfmLigKern lk = ciw.getLigKernTable()[k];

                                if (lk instanceof TfmLigature) {
                                    TfmLigature lig = (TfmLigature) lk;

                                    writer.plopen("LIG")
                                        .addChar(lig.getNextChar())
                                        .addChar(lig.getAddingChar()).plclose();
                                } else if (lk instanceof TfmKerning) {
                                    TfmKerning kern = (TfmKerning) lk;

                                    writer.plopen("KRN")
                                        .addChar(kern.getNextChar())
                                        .addReal(kern.getKern()).plclose();
                                }
                            }
                        }
                        writer.plclose();
                    }

                    writer.plclose();
                }
            }

        }

    @Override
        public void visitTfmDepthArray(TfmDepthArray depth) throws IOException {

        }

    @Override
        public void visitTfmExtenArray(TfmExtenArray exten) throws IOException {

        }

    @Override
        public void visitTfmHeaderArray(TfmHeaderArray header)
                throws IOException {

            if (header.getFontfamily() != null) {
                writer.plopen("FAMILY").addStr(header.getFontfamily())
                    .plclose();
            }
            if (header.getXeroxfacecode() >= 0) {
                writer.plopen("FACE").addFace(header.getXeroxfacecode())
                    .plclose();
            }

            if (header.getHeaderrest() != null) {
                for (int i = 0; i < header.getHeaderrest().length; i++) {
                    writer.plopen("HEADER")
                        .addDec(i + TfmHeaderArray.HEADER_REST_SIZE)
                        .addOct(header.getHeaderrest()[i]).plclose();
                }
            }
            if (header.getCodingscheme() != null) {
                writer.plopen("CODINGSCHEME").addStr(header.getCodingscheme())
                    .plclose();
            }
            writer.plopen("DESIGNSIZE").addReal(header.getDesignsize())
                .plclose();
            writer.addComment("DESIGNSIZE IS IN POINTS");
            writer.addComment("OTHER SIZES ARE MULTIPLES OF DESIGNSIZE");
            writer.plopen("CHECKSUM").addOct(header.getChecksum()).plclose();
            if (header.isSevenBitSafe()) {
                writer.plopen("SEVENBITSAFEFLAG")
                    .addBool(header.isSevenBitSafe()).plclose();
            }
        }

    @Override
        public void visitTfmHeaderLengths(TfmHeaderLengths lengths)
                throws IOException {

        }

    @Override
        public void visitTfmHeightArray(TfmHeightArray height)
                throws IOException {

        }

    @Override
        public void visitTfmItalicArray(TfmItalicArray italic)
                throws IOException {

        }

    @Override
        public void visitTfmKernArray(TfmKernArray kern) throws IOException {

        }

    @Override
        public void visitTfmLigKernArray(TfmLigKernArray ligkern)
                throws IOException {

            if (ligkern.getBoundaryChar() != TfmCharInfoWord.NOCHARCODE) {
                writer.plopen("BOUNDARYCHAR")
                    .addChar(ligkern.getBoundaryChar()).plclose();
            }
            if (ligkern.getLigKernTable().length > 0) {
                writer.plopen("LIGTABLE");
                for (int i = 0; i < ligkern.getCharinfo().getCharinfoword().length; i++) {
                    TfmCharInfoWord ciw =
                            ligkern.getCharinfo().getCharinfoword()[i];
                    if (ciw != null) {
                        if (ligkern.foundLigKern(ciw)) {
                            writer.plopen("LABEL").addChar(
                                (short) (i + ligkern.getBc()));
                            writer.plclose();

                            // ligature
                            int ligstart = ciw.getLigkernstart();
                            if (ligstart != TfmCharInfoWord.NOINDEX
                                    && ligkern.getLigKernTable() != null) {

                                for (int k = ligstart; k != TfmCharInfoWord.NOINDEX; k =
                                        ligkern.getLigKernTable()[k]
                                            .nextIndex(k)) {
                                    TfmLigKern lk =
                                            ligkern.getLigKernTable()[k];

                                    if (lk instanceof TfmLigature) {
                                        TfmLigature lig = (TfmLigature) lk;

                                        writer.plopen("LIG")
                                            .addChar(lig.getNextChar())
                                            .addChar(lig.getAddingChar())
                                            .plclose();
                                    } else if (lk instanceof TfmKerning) {
                                        TfmKerning kerning = (TfmKerning) lk;

                                        writer.plopen("KRN")
                                            .addChar(kerning.getNextChar())
                                            .addReal(kerning.getKern())
                                            .plclose();
                                    }
                                }
                            }
                            writer.plopen("STOP").plclose();
                        }
                    }
                }
                writer.plclose();
            }

        }

    @Override
        public void visitTfmParamArray(TfmParamArray param) throws IOException {

            writer.plopen("FONTDIMEN");

            SortedMap<String, TfmFixWord> p =
                    new TreeMap<String, TfmFixWord>(param.getParam());
            for (String key : p.keySet()) {
                TfmFixWord value = param.getParam(key);
                writer.plopen(key);
                writer.addReal(value).plclose();
            }
            writer.plclose();
        }

    @Override
        public void visitTfmReader(TfmReader tfmReader) throws IOException {

        }

    @Override
        public void visitTfmWidthArray(TfmWidthArray width) throws IOException {

        }

    }

    /**
     * parameter.
     */
    private static final int PARAMETER = 1;

    /**
     * main.
     * 
     * @param args The command line
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        Tfm2Pl tfm = new Tfm2Pl();

        if (args.length < PARAMETER) {
            tfm.getLogger().severe(tfm.getLocalizer().format("Tfm2Pl.Call"));
            System.exit(1);
        }

        String tfmfile = "null";

        int i = 0;
        do {
            if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                if (i + 1 < args.length) {
                    tfm.setOutdir(args[++i]);
                }

            } else {
                tfmfile = args[i];
            }
            i++;
        } while (i < args.length);

        tfm.doIt(tfmfile);

    }

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException from the configuration system.
     */
    public Tfm2Pl() throws ConfigurationException {

        super(Tfm2Pl.class);
    }

    /**
     * doIt.
     * 
     * @param tfmfile The tfm file.
     * @throws IOException if a io error occurred.
     * @throws ConfigurationException from the configuration system.
     */
    public void doIt(String tfmfile) throws IOException, ConfigurationException {

        getLogger().severe(getLocalizer().format("Tfm2Pl.start", tfmfile));

        InputStream tfmin = null;

        // find directly the tfm file.
        File tfm = new File(tfmfile);

        if (tfm.canRead()) {
            tfmin = new FileInputStream(tfm);
        }

        if (tfmin == null) {
            throw new FileNotFoundException(tfmfile);
        }

        String fontname = tfm.getName().replaceAll(".[tT][fF][mM]", "");
        TfmReader reader = new TfmReader(tfmin, fontname);
        String plfile = getOutdir() + File.separator + fontname + ".pl";

        PlWriter writer = new PlWriter(new FileOutputStream(plfile));
        PlVisitor visitor = new PlVisitor(writer);
        visitor.start();
        reader.visit(visitor);
        visitor.end();
        writer.close();

        getLogger().severe(getLocalizer().format("Tfm2Pl.PlCreate", plfile));

    }

}
