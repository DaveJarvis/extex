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

package org.extex.font.format.afm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.extex.font.exception.FontException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Parse a afm file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class AfmParser implements Serializable, XMLWriterConvertible {

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer =
            LocalizerFactory.getLocalizer(AfmParser.class);

    /**
     * Create a new object.
     * 
     * @param in The input.
     * 
     * @throws FontException if a font error occurred.
     */
    public AfmParser(InputStream in) throws FontException {

        try {
            // create a Reader (AFM use US_ASCII)
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in, "US-ASCII"));
            header = new AfmHeader();
            readAFMFile(reader);
            reader.close();
            in.close();
        } catch (IOException e) {
            throw new FontException(e.getMessage());
        }
    }

    /**
     * The header container.
     */
    private AfmHeader header;

    /**
     * The initialize size for the ArrayList.
     */
    private static final int ARRAYLISTINITSIZE = 256;

    /**
     * Represents the section CharMetrics in the AFM file.
     */
    private ArrayList<AfmCharMetric> afmCharMetrics =
            new ArrayList<AfmCharMetric>(ARRAYLISTINITSIZE);

    /**
     * Represents the section KerningPairs in the AFM file.
     */
    private List<AfmKernPairs> afmKerningPairs =
            new ArrayList<AfmKernPairs>(ARRAYLISTINITSIZE);

    /**
     * Map for Char-Name - Char-Number.
     */
    private Map<String, Integer> afmCharNameNumber =
            new HashMap<String, Integer>(ARRAYLISTINITSIZE);

    /**
     * base 16.
     */
    private static final int BASEHEX = 16;

    /**
     * Read the AFM-File.
     * 
     * @param reader The Reader fore the file input
     * @throws IOException if a io error occurred.
     * @throws FontException if a font error occurred.
     */
    private void readAFMFile(BufferedReader reader)
            throws IOException,
                FontException {

        // line
        String line;

        // read the AFM-header first and then the metrics
        boolean isMetrics = false;

        while ((line = reader.readLine()) != null) {

            // get the token from the line
            StringTokenizer tok = new StringTokenizer(line);

            // no more tokens
            if (!tok.hasMoreTokens()) {
                continue;
            }

            // read the command
            String command = tok.nextToken();

            // check the command
            if (command.equals("Comment")) {
                continue;
            } else if (command.equals("Notice")) {
                continue;
            } else if (command.equals("FontName")) {
                header.setFontname(tok.nextToken("\u00ff").substring(1));
            } else if (command.equals("FullName")) {
                header.setFullname(tok.nextToken("\u00ff").substring(1));
            } else if (command.equals("FamilyName")) {
                header.setFamilyname(tok.nextToken("\u00ff").substring(1));
            } else if (command.equals("Weight")) {
                header.setWeight(tok.nextToken("\u00ff").substring(1));
            } else if (command.equals("ItalicAngle")) {
                header.setItalicangle(Float.valueOf(tok.nextToken())
                    .floatValue());
            } else if (command.equals("IsFixedPitch")) {
                header.setFixedpitch(tok.nextToken().equals("true"));
            } else if (command.equals("CharacterSet")) {
                header.setCharacterset(tok.nextToken("\u00ff").substring(1));
            } else if (command.equals("FontBBox")) {
                header.setLlx(Float.valueOf(removeComma(tok.nextToken()))
                    .floatValue());
                header.setLly(Float.valueOf(removeComma(tok.nextToken()))
                    .floatValue());
                header.setUrx(Float.valueOf(removeComma(tok.nextToken()))
                    .floatValue());
                header.setUry(Float.valueOf(removeComma(tok.nextToken()))
                    .floatValue());
            } else if (command.equals("UnderlinePosition")) {
                header.setUnderlineposition(Float.valueOf(tok.nextToken())
                    .floatValue());
            } else if (command.equals("UnderlineThickness")) {
                header.setUnderlinethickness(Float.valueOf(tok.nextToken())
                    .floatValue());
            } else if (command.equals("EncodingScheme")) {
                header.setEncodingscheme(tok.nextToken("\u00ff").substring(1));
            } else if (command.equals("CapHeight")) {
                header
                    .setCapheight(Float.valueOf(tok.nextToken()).floatValue());
            } else if (command.equals("XHeight")) {
                header.setXheight(Float.valueOf(tok.nextToken()).floatValue());
            } else if (command.equals("Ascender")) {
                header.setAscender(Float.valueOf(tok.nextToken()).floatValue());
            } else if (command.equals("Descender")) {
                header
                    .setDescender(Float.valueOf(tok.nextToken()).floatValue());
            } else if (command.equals("StdHW")) {
                header.setStdhw(Float.valueOf(tok.nextToken()).floatValue());
            } else if (command.equals("StdVW")) {
                header.setStdvw(Float.valueOf(tok.nextToken()).floatValue());
            } else if (command.equals("StartCharMetrics")) {
                isMetrics = true;
                break;
            }
        }
        // metric not found
        if (!isMetrics) {
            throw new FontException(localizer
                .format("AfmParser.MissingStartCharMetrics"));
        }

        // create metric
        isMetrics = createMetric(reader, isMetrics);

        // read next command
        while ((line = reader.readLine()) != null) {
            StringTokenizer tok = new StringTokenizer(line);
            if (!tok.hasMoreTokens()) {
                continue;
            }
            String ident = tok.nextToken();
            if (ident.equals("EndFontMetrics")) {
                // end
                return;
            }
            if (ident.equals("StartKernPairs")) {
                isMetrics = true;
                break;
            }
        }
        if (!isMetrics) {
            throw new FontException(localizer
                .format("AfmParser.MissingEndFontMetrics"));
        }

        // read KernPairs
        while ((line = reader.readLine()) != null) {
            StringTokenizer tok = new StringTokenizer(line);
            if (!tok.hasMoreTokens()) {
                continue;
            }
            String ident = tok.nextToken();
            if (ident.equals("KPX")) {
                AfmKernPairs kp = new AfmKernPairs();
                kp.setCharpre(tok.nextToken());
                kp.setCharpost(tok.nextToken());
                kp.setKerningsize(Float.valueOf(tok.nextToken()).floatValue());
                afmKerningPairs.add(kp);

                // add the kerning to the char metric
                AfmCharMetric cm = getAfmCharMetric(kp.getCharpre());
                if (cm != null) {
                    cm.addK(kp);
                }

            } else if (ident.equals("EndKernPairs")) {
                isMetrics = false;
                break;
            }
        }
        if (isMetrics) {
            throw new FontException(localizer
                .format("AfmParser.MissingEndKernPairs"));
        }
    }

    /**
     * Create the Metric.
     * 
     * @param reader The reader
     * @param ism is metric
     * @return is metric
     * @throws IOException if an IO-error occurs
     * @throws FontException if a font-error occurs.
     */
    private boolean createMetric(BufferedReader reader, boolean ism)
            throws IOException,
                FontException {

        boolean isMetrics = ism;

        String line;
        // read the metric
        while ((line = reader.readLine()) != null) {

            // get the token from the line
            StringTokenizer tok = new StringTokenizer(line);

            // no more tokens
            if (!tok.hasMoreTokens()) {
                continue;
            }

            // get the command
            String command = tok.nextToken();
            if (command.equals("EndCharMetrics")) {
                isMetrics = false;
                break;
            }

            // default values
            AfmCharMetric cm = new AfmCharMetric();

            // get the token separate by ';'
            tok = new StringTokenizer(line, ";");
            while (tok.hasMoreTokens()) {
                StringTokenizer tokc = new StringTokenizer(tok.nextToken());
                if (!tokc.hasMoreTokens()) {
                    continue;
                }
                command = tokc.nextToken();

                // command ?
                if (command.equals("C")) {
                    cm.setC(Integer.parseInt(tokc.nextToken()));
                } else if (command.equals("CH")) {
                    cm.setC(Integer.parseInt(tokc.nextToken(), BASEHEX));
                } else if (command.equals("WX")) {
                    cm.setWx(Float.valueOf(tokc.nextToken()).floatValue());
                } else if (command.equals("N")) {
                    cm.setN(tokc.nextToken());
                } else if (command.equals("B")) {
                    cm.setBllx(Float.valueOf(tokc.nextToken()).floatValue());
                    cm.setBlly(Float.valueOf(tokc.nextToken()).floatValue());
                    cm.setBurx(Float.valueOf(tokc.nextToken()).floatValue());
                    cm.setBury(Float.valueOf(tokc.nextToken()).floatValue());
                } else if (command.equals("L")) {
                    cm.addL(tokc.nextToken().trim(), tokc.nextToken().trim());
                }
            }
            afmCharMetrics.add(cm);

            // store name and number
            if (afmCharNameNumber.containsKey(cm.getN())) {
                if (cm.getC() != -1) {
                    afmCharNameNumber.put(cm.getN(), new Integer(cm.getC()));
                }
            } else {
                afmCharNameNumber.put(cm.getN(), new Integer(cm.getC()));
            }
        }

        // metric close?
        if (isMetrics) {
            throw new FontException(localizer
                .format("AfmParser.MissingEndCharMetrics"));
        }
        return isMetrics;
    }

    /**
     * Remove all ',' in the string, if the string is <code>null</code>, a
     * empty string is returned.
     * 
     * @param s the string
     * @return the string without a ','
     */
    private String removeComma(String s) {

        if (s != null) {
            return s.replaceAll(",", "");
        }
        return "";
    }

    /**
     * Returns the id for a char name.
     * 
     * @param name The name of char.
     * @return Returns the id for a char name.
     */
    public String getIDforName(String name) {

        int id = -1;

        String n = name;

        if (name != null) {
            Integer i = afmCharNameNumber.get(name);
            if (i != null) {
                id = i.intValue();
            }
        } else {
            n = "null";
        }

        if (id >= 0) {
            return String.valueOf(id);
        }
        return n;
    }

    /**
     * Returns the afmCharMetrics.
     * 
     * @return Returns the afmCharMetrics.
     */
    public List<AfmCharMetric> getAfmCharMetrics() {

        return afmCharMetrics;
    }

    /**
     * Returns the afmCharNameNumber.
     * 
     * @return Returns the afmCharNameNumber.
     */
    public Map<String, Integer> getAfmCharNameNumber() {

        return afmCharNameNumber;
    }

    /**
     * Returns the afmKerningPairs.
     * 
     * @return Returns the afmKerningPairs.
     */
    public List<AfmKernPairs> getAfmKerningPairs() {

        return afmKerningPairs;
    }

    /**
     * Returns the header.
     * 
     * @return Returns the header.
     */
    public AfmHeader getHeader() {

        return header;
    }

    /**
     * Returns the char metric of a char.
     * 
     * @param c The char (number)
     * @return Returns the char metric of a char.
     */
    public AfmCharMetric getAfmCharMetric(int c) {

        AfmCharMetric cm = null;
        for (int i = 0, n = afmCharMetrics.size(); i < n; i++) {
            cm = afmCharMetrics.get(i);
            if (cm.getC() == c) {
                return cm;
            }
        }
        return null;
    }

    /**
     * Returns the char metric of a char.
     * 
     * @param name The char (name)
     * @return Returns the char metric of a char.
     */
    public AfmCharMetric getAfmCharMetric(String name) {

        AfmCharMetric cm = null;
        for (int i = 0, n = afmCharMetrics.size(); i < n; i++) {
            cm = afmCharMetrics.get(i);
            if (cm.getN().equals(name)) {
                return cm;
            }
        }
        return null;
    }

    /**
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("afm");
        writer.writeAttribute("name", header.getFontname());

        header.writeXML(writer);
        for (int i = 0; i < afmCharMetrics.size(); i++) {

            // glyph
            writer.writeStartElement("glyph");

            // get the AFMCharMertic object
            AfmCharMetric cm = afmCharMetrics.get(i);

            // create attributes
            writer.writeAttribute("ID", i);
            writer.writeAttribute("number", cm.getC());
            writer.writeAttribute("name", cm.getN());

            if (cm.getWx() != AfmHeader.NOTINIT) {
                writer.writeFormatAttribute("width", cm.getWx());
            } else {
                // calculate with from bbox
                if (cm.getBllx() != AfmHeader.NOTINIT) {
                    writer.writeFormatAttribute("width", cm.getBllx()
                            + cm.getBurx());
                }
            }

            if (cm.getBllx() != AfmHeader.NOTINIT) {
                if (cm.getBlly() < 0) {
                    writer.writeFormatAttribute("depth", -cm.getBlly());
                } else {
                    writer.writeAttribute("depth", "0");
                }
                if (cm.getBury() > 0) {
                    writer.writeFormatAttribute("height", cm.getBury());
                } else {
                    writer.writeAttribute("height", "0");
                }
            }
            writer.writeFormatAttribute("italic", header.getItalicangle());

            // kerning
            String glyphname = cm.getN();
            AfmKernPairs kp;

            for (int k = 0; k < afmKerningPairs.size(); k++) {
                kp = afmKerningPairs.get(k);
                if (kp.getCharpre().equals(glyphname)) {
                    writer.writeStartElement("kerning");
                    writer.writeAttribute("name", kp.getCharpost());
                    writer.writeFormatAttribute("size", kp.getKerningsize());
                    writer.writeEndElement();
                }
            }

            // ligature
            if (cm.getL() != null) {
                Iterator<String> iterator = cm.getL().keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    writer.writeStartElement("ligature");
                    writer.writeAttribute("letter", key);
                    String value = cm.getL().get(key);
                    writer.writeAttribute("lig", value);
                    writer.writeEndElement();
                }
            }

            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

}
