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
import java.util.Map;

import org.extex.font.format.tfm.TfmCharInfoArray;
import org.extex.font.format.tfm.TfmCharInfoWord;
import org.extex.font.format.tfm.TfmConstants;
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
import org.extex.util.xml.XMLStreamWriter;

/**
 * Convert a tfm font to a xml file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Tfm2Xml extends AbstractFontUtil {

    /**
     * The xml visitor.
     */
    private class XmlVisitor implements TfmVisitor {

        /**
         * The xml writer.
         */
        private XMLStreamWriter writer;

        /**
         * Creates a new object.
         * 
         * @param writer The xml writer.
         */
        public XmlVisitor(XMLStreamWriter writer) {

            this.writer = writer;
        }

    @Override
        public void end() throws IOException {

            writer.writeEndElement();
            writer.writeEndDocument();

        }

    @Override
        public void start() throws IOException {

            writer.writeStartDocument();
            writer.writeStartElement("tfm");

        }

    @Override
        public void visitTfmCharInfoArray(TfmCharInfoArray charinfo)
                throws IOException {

            writer.writeStartElement("charinfo");
            TfmCharInfoWord[] ciwarray = charinfo.getCharinfoword();
            for (int i = 0; i < ciwarray.length; i++) {
                TfmCharInfoWord ciw = ciwarray[i];

                writer.writeStartElement("char");
                writer.writeAttribute("id", ciw.getCharid() + ciw.getBc());

                String c =
                        Character.toString((char) (ciw.getCharid() + ciw
                            .getBc()));
                if (c != null && c.trim().length() > 0) {
                    writer.writeAttribute("char", c);
                }
                if (ciw.getGlyphname() != null) {
                    writer.writeAttribute("glyph-name", ciw.getGlyphname()
                        .replaceAll("/", ""));
                }
                writer.writeAttribute("width_fw", ciw.getWidth().getValue());
                writer.writeAttribute("height_fw", ciw.getHeight().getValue());
                writer.writeAttribute("depth_fw", ciw.getDepth().getValue());
                writer.writeAttribute("italic_fw", ciw.getItalic().getValue());

                // ligature
                int ligstart = ciw.getLigkernstart();
                if (ligstart != TfmCharInfoWord.NOINDEX
                        && ciw.getLigKernTable() != null) {

                    for (int k = ligstart; k != TfmCharInfoWord.NOINDEX; k =
                            ciw.getLigKernTable()[k].nextIndex(k)) {
                        TfmLigKern lk = ciw.getLigKernTable()[k];

                        if (lk instanceof TfmLigature) {
                            TfmLigature lig = (TfmLigature) lk;

                            writer.writeStartElement("ligature");
                            writer.writeAttribute("letter-id",
                                String.valueOf(lig.getNextChar()));
                            String sl =
                                    Character
                                        .toString((char) lig.getNextChar());
                            if (sl != null && sl.trim().length() > 0) {
                                writer.writeAttribute("letter", sl.trim());
                            }

                            writer.writeAttribute("lig-id",
                                String.valueOf(lig.getAddingChar()));
                            String slig =
                                    Character.toString((char) lig
                                        .getAddingChar());
                            if (slig != null && slig.trim().length() > 0) {
                                writer.writeAttribute("lig", slig.trim());
                            }
                            writer.writeEndElement();
                        } else if (lk instanceof TfmKerning) {
                            TfmKerning kern = (TfmKerning) lk;

                            writer.writeStartElement("kerning");
                            writer.writeAttribute("id",
                                String.valueOf(kern.getNextChar()));
                            String sk =
                                    Character.toString((char) kern
                                        .getNextChar());
                            if (sk != null && sk.trim().length() > 0) {
                                writer.writeAttribute("char", sk.trim());
                            }
                            writer.writeAttribute("size_fw",
                                String.valueOf(kern.getKern().getValue()));
                            writer.writeEndElement();
                        }
                    }
                }
                writer.writeEndElement();

            }
            writer.writeEndElement();

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

            writer.writeStartElement("header");
            writer.writeAttribute("checksum", header.getChecksum());
            writer.writeAttribute("desingsize", header.getDesignsize());
            writer.writeAttribute("units", TfmConstants.CONST_1000);
            if (header.getCodingscheme() != null) {
                writer.writeAttribute("codingscheme", header.getCodingscheme());
            }
            if (header.getFontType() != null) {
                writer.writeAttribute("fonttype", header.getFontType()
                    .toString());
            }
            if (header.getFontfamily() != null) {
                writer.writeAttribute("fontfamily", header.getFontfamily());
            }
            writer.writeAttribute("sevenbitsafe", header.isSevenBitSafe());
            writer.writeAttribute("xeroxfacecode", header.getXeroxfacecode());
            if (header.getHeaderrest() != null) {
                for (int i = 0; i < header.getHeaderrest().length; i++) {
                    writer.writeStartElement("header");
                    writer.writeAttribute("id", i
                            + TfmHeaderArray.HEADER_REST_SIZE);
                    writer.writeAttribute("value", header.getHeaderrest()[i]);
                    writer.writeEndElement();
                }
            }
            writer.writeEndElement();

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

        }

    @Override
        public void visitTfmParamArray(TfmParamArray param) throws IOException {

            writer.writeStartElement("params");
            Map<String, TfmFixWord> p = param.getParam();

            for (String key : p.keySet()) {
                TfmFixWord value = param.getParam(key);
                writer.writeStartElement("param");
                writer.writeAttribute("name", key);
                writer.writeAttribute("value_fw", value.getValue());
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }

    @Override
        public void visitTfmReader(TfmReader tfmReader) throws IOException {

            writer.writeAttribute("name", tfmReader.getFontname());

        }

    @Override
        public void visitTfmWidthArray(TfmWidthArray width) throws IOException {

        }

    }

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
     * @param args The command line
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        Tfm2Xml tfm = new Tfm2Xml();

        if (args.length < PARAMETER) {
            tfm.getLogger().severe(tfm.getLocalizer().format("Tfm2Xml.Call"));
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


    public Tfm2Xml() {

        super(Tfm2Xml.class);
    }

    /**
     * doIt.
     * 
     * @param tfmfile The tfm file.
     * @throws IOException if a io error occurred.
     * @throws ConfigurationException from the configuration system.
     */
    public void doIt(String tfmfile) throws IOException, ConfigurationException {

        getLogger().severe(getLocalizer().format("Tfm2Xml.start", tfmfile));

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
        String xmlfile = getOutdir() + File.separator + fontname + ".xml";
        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream(xmlfile), ENCODING);
        writer.setBeauty(true);
        writer.setRemoveWhiteSpace(true);
        XmlVisitor visitor = new XmlVisitor(writer);
        visitor.start();
        reader.visit(visitor);
        visitor.end();
        writer.close();

        getLogger().severe(getLocalizer().format("Tfm2Xml.XmlCreate", xmlfile));

    }
}
