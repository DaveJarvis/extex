/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

import org.extex.font.exception.FontException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Parse a afm file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

@SuppressWarnings("unused")
public class AfmParser implements Serializable, XMLWriterConvertible {

    /**
     * The initialize size for the ArrayList.
     */
    private static final int ARRAYLISTINITSIZE = 256;

    /**
     * base 16.
     */
    private static final int BASEHEX = 16;

    /**
     * The buffer size.
     */
    private static final int BUFFERSIZE = 0xffff;

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the section CharMetrics in the AFM file.
     */
    private final ArrayList<AfmCharMetric> afmCharMetrics =
        new ArrayList<>( ARRAYLISTINITSIZE );

    /**
     * Map for Char-Name - Char-Number.
     */
    private final Map<String, Integer> afmCharNameNumber =
        new HashMap<>( ARRAYLISTINITSIZE );

    /**
     * The afm data.
     */
    private byte[] afmdata;

    /**
     * Represents the section KerningPairs in the AFM file.
     */
    private final List<AfmKernPairs> afmKerningPairs = new ArrayList<>(
        ARRAYLISTINITSIZE );

    /**
     * The header container.
     */
    private final AfmHeader header;

    /**
     * The field {@code localizer} contains the localizer. It is initiated with
     * a localizer for the name of this class.
     */
    private final Localizer localizer = LocalizerFactory
        .getLocalizer(AfmParser.class);

    /**
     * The default encoding.
     */
    private final String[] defaultEncodingVector = new String[256];

    /**
     * Create a new object.
     * 
     * @param in The input.
     * 
     * @throws FontException if a font error occurred.
     */
    public AfmParser(InputStream in) throws FontException {

        Arrays.fill(defaultEncodingVector, ".notdef");

        try {
            BufferedReader reader = createReader(in);
            header = new AfmHeader();
            readAFMFile(reader);
            reader.close();
            in.close();
        } catch (IOException e) {
            throw new FontException(e.getMessage());
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
                switch( command ) {
                    case "C":
                        cm.setC( Integer.parseInt( tokc.nextToken() ) );
                        break;
                    case "CH":
                        cm.setC( Integer.parseInt( tokc.nextToken(),
                                                   BASEHEX ) );
                        break;
                    case "WX":
                        cm.setWx( Float.parseFloat( tokc.nextToken() ) );
                        break;
                    case "N":
                        cm.setN( tokc.nextToken() );
                        break;
                    case "B":
                        cm.setBllx( Float.parseFloat( tokc.nextToken() ) );
                        cm.setBlly( Float.parseFloat( tokc.nextToken() ) );
                        cm.setBurx( Float.parseFloat( tokc.nextToken() ) );
                        cm.setBury( Float.parseFloat( tokc.nextToken() ) );
                        break;
                    case "L":
                        cm.addL( tokc.nextToken().trim(),
                                 tokc.nextToken().trim() );
                        break;
                }
            }
            afmCharMetrics.add(cm);

            int pos = cm.getC();
            if (pos >= 0 && pos < 256) {
                defaultEncodingVector[pos] = cm.getN();
            }

            // store name and number
            if (afmCharNameNumber.containsKey(cm.getN())) {
                if (cm.getC() != -1) {
                    afmCharNameNumber.put( cm.getN(), cm.getC() );
                }
            } else {
                afmCharNameNumber.put( cm.getN(), cm.getC() );
            }
        }

        // metric close?
        if (isMetrics) {
            throw new FontException(
                localizer.format("AfmParser.MissingEndCharMetrics"));
        }
        return isMetrics;
    }

    /**
     * Create a reader and copy the font data.
     * 
     * @param in The input.
     * @return Returns the {@link BufferedReader}.
     * @throws IOException if a io-error occurred.
     */
    private BufferedReader createReader(InputStream in) throws IOException {

        // copy
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFERSIZE);
        byte[] buf = new byte[BUFFERSIZE];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        afmdata = out.toByteArray();
        out.close();

        // create a Reader (AFM use US_ASCII)
        ByteArrayInputStream bin = new ByteArrayInputStream(afmdata);
        return new BufferedReader(new InputStreamReader( bin,
                                                         StandardCharsets.US_ASCII ),
            BUFFERSIZE);
    }

    /**
     * Returns the char metric of a char.
     * 
     * @param c The char (number)
     * @return Returns the char metric of a char.
     */
    public AfmCharMetric getAfmCharMetric(int c) {

        AfmCharMetric cm;
        for( AfmCharMetric afmCharMetric : afmCharMetrics ) {
            cm = afmCharMetric;
            if( cm.getC() == c ) {
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

        AfmCharMetric cm;
        for( AfmCharMetric afmCharMetric : afmCharMetrics ) {
            cm = afmCharMetric;
            if( cm.getN().equals( name ) ) {
                return cm;
            }
        }
        return null;
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
     * Getter for the ascender.
     * 
     * @return the ascender
     * 
     * @see org.extex.font.format.afm.AfmHeader#getAscender()
     */
    public float getAscender() {

        return header.getAscender();
    }

    /**
     * Getter for the capital height.
     * 
     * @return the capital height
     * 
     * @see org.extex.font.format.afm.AfmHeader#getCapheight()
     */
    public float getCapheight() {

        return header.getCapheight();
    }

    /**
     * Getter for the character set.
     * 
     * @return the character set
     * 
     * @see org.extex.font.format.afm.AfmHeader#getCharacterset()
     */
    public String getCharacterset() {

        return header.getCharacterset();
    }

    /**
     * Getter for the comment.
     * 
     * @return the comment
     * 
     * @see org.extex.font.format.afm.AfmHeader#getComment()
     */
    public String getComment() {

        return header.getComment();
    }

    /**
     * Getter for defaultEncodingVector.
     * 
     * @return the defaultEncodingVector
     */
    public String[] getDefaultEncodingVector() {

        return defaultEncodingVector;
    }

    /**
     * Getter for the descender.
     * 
     * @return the descender
     * 
     * @see org.extex.font.format.afm.AfmHeader#getDescender()
     */
    public float getDescender() {

        return header.getDescender();
    }

    /**
     * Getter for the encoding scheme.
     * 
     * @return the encoding scheme
     * 
     * @see org.extex.font.format.afm.AfmHeader#getEncodingScheme()
     */
    public String getEncodingscheme() {

        return header.getEncodingScheme();
    }

    /**
     * Getter for the family name.
     * 
     * @return the family name
     * 
     * @see org.extex.font.format.afm.AfmHeader#getFamilyName()
     */
    public String getFamilyname() {

        return header.getFamilyName();
    }

    /**
     * Getter for the font data.
     * 
     * @return the font data
     */
    public byte[] getFontData() {

        return afmdata;
    }

    /**
     * Getter for the font name.
     * 
     * @return the font name
     * 
     * @see org.extex.font.format.afm.AfmHeader#getFontName()
     */
    public String getFontname() {

        return header.getFontName();
    }

    /**
     * Getter for the full font name.
     * 
     * @return the full font name
     */
    public String getFullname() {

        return header.getFullName();
    }

    /**
     * Getter for the header.
     * 
     * @return Returns the header
     */
    public AfmHeader getHeader() {

        return header;
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
                id = i;
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
     * Getter for the italic angle.
     * 
     * @return the italic angle
     * 
     * @see org.extex.font.format.afm.AfmHeader#getItalicAngle()
     */
    public float getItalicangle() {

        return header.getItalicAngle();
    }

    /**
     * Getter for the llx.
     * 
     * @return the llx
     * 
     * @see org.extex.font.format.afm.AfmHeader#getLlx()
     */
    public float getLlx() {

        return header.getLlx();
    }

    /**
     * Getter for the lly.
     * 
     * @return the lly
     * 
     * @see org.extex.font.format.afm.AfmHeader#getLly()
     */
    public float getLly() {

        return header.getLly();
    }

    /**
     * Getter for the notice.
     * 
     * @return the notice
     * 
     * @see org.extex.font.format.afm.AfmHeader#getNotice()
     */
    public String getNotice() {

        return header.getNotice();
    }

    /**
     * Getter for the number of glyphs.
     * 
     * @return the number of glyphs
     */
    public int getNumberOfGlyphs() {

        return afmCharMetrics.size();
    }

    /**
     * Getter for the stdhw.
     * 
     * @return the stdhw
     * 
     * @see org.extex.font.format.afm.AfmHeader#getStdhw()
     */
    public float getStdhw() {

        return header.getStdhw();
    }

    /**
     * Getter for the stdvw.
     * 
     * @return the stdvw
     * 
     * @see org.extex.font.format.afm.AfmHeader#getStdvw()
     */
    public float getStdvw() {

        return header.getStdvw();
    }

    /**
     * Getter for the underline position.
     * 
     * @return the underline position
     * 
     * @see org.extex.font.format.afm.AfmHeader#getUnderlineposition()
     */
    public float getUnderlinePosition() {

        return header.getUnderlineposition();
    }

    /**
     * Getter for the underline thickness.
     * 
     * @return the underline thickness
     * 
     * @see org.extex.font.format.afm.AfmHeader#getUnderlinethickness()
     */
    public float getUnderlineThickness() {

        return header.getUnderlinethickness();
    }

    /**
     * Getter for the urx.
     * 
     * @return the urx
     * 
     * @see org.extex.font.format.afm.AfmHeader#getUrx()
     */
    public float getUrx() {

        return header.getUrx();
    }

    /**
     * Getter for the ury.
     * 
     * @return the ury
     * 
     * @see org.extex.font.format.afm.AfmHeader#getUry()
     */
    public float getUry() {

        return header.getUry();
    }

    /**
     * Getter for the weight.
     * 
     * @return the weight
     * 
     * @see org.extex.font.format.afm.AfmHeader#getWeight()
     */
    public String getWeight() {

        return header.getWeight();
    }

    /**
     * Getter for the xheight.
     * 
     * @return the xheight
     * 
     * @see org.extex.font.format.afm.AfmHeader#getXheight()
     */
    public float getXheight() {

        return header.getXheight();
    }

    /**
     * Getter for the fixed pitch.
     * 
     * @return the fixed pitch
     * 
     * @see org.extex.font.format.afm.AfmHeader#isFixedPitch()
     */
    public boolean isFixedPitch() {

        return header.isFixedPitch();
    }

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

        label:
        while ((line = reader.readLine()) != null) {

            // get the token from the line
            StringTokenizer tok = new StringTokenizer( line);

            // no more tokens
            if (!tok.hasMoreTokens()) {
                continue;
            }

            // read the command
            String command = tok.nextToken();

            // check the command
            switch( command ) {
                case "Comment":
                    header.addComment( tok.nextToken( "\u00ff" ).trim() );
                    break;
                case "Notice":
                    header.addNotice( tok.nextToken( "\u00ff" )
                                         .substring( 1 ) );
                    break;
                case "FontName":
                    header.setFontname( tok.nextToken( "\u00ff" )
                                           .substring( 1 ) );
                    break;
                case "FullName":
                    header.setFullname( tok.nextToken( "\u00ff" )
                                           .substring( 1 ) );
                    break;
                case "FamilyName":
                    header.setFamilyname( tok.nextToken( "\u00ff" )
                                             .substring( 1 ) );
                    break;
                case "Weight":
                    header.setWeight( tok.nextToken( "\u00ff" )
                                         .substring( 1 ) );
                    break;
                case "ItalicAngle":
                    header.setItalicangle( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "IsFixedPitch":
                    header.setFixedpitch( tok.nextToken().equals( "true" ) );
                    break;
                case "CharacterSet":
                    header.setCharacterset( tok.nextToken( "\u00ff" )
                                               .substring( 1 ) );
                    break;
                case "FontBBox":
                    header.setLlx( Float.parseFloat( removeComma( tok.nextToken() ) ) );
                    header.setLly( Float.parseFloat( removeComma( tok.nextToken() ) ) );
                    header.setUrx( Float.parseFloat( removeComma( tok.nextToken() ) ) );
                    header.setUry( Float.parseFloat( removeComma( tok.nextToken() ) ) );
                    break;
                case "UnderlinePosition":
                    header.setUnderlineposition( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "UnderlineThickness":
                    header.setUnderlinethickness( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "EncodingScheme":
                    header.setEncodingscheme( tok.nextToken( "\u00ff" )
                                                 .substring( 1 ) );
                    break;
                case "CapHeight":
                    header
                        .setCapheight( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "XHeight":
                    header.setXheight( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "Ascender":
                    header.setAscender( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "Descender":
                    header
                        .setDescender( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "StdHW":
                    header.setStdhw( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "StdVW":
                    header.setStdvw( Float.parseFloat( tok.nextToken() ) );
                    break;
                case "StartCharMetrics":
                    isMetrics = true;
                    break label;
            }
        }
        // metric not found
        if (!isMetrics) {
            throw new FontException(
                localizer.format("AfmParser.MissingStartCharMetrics"));
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
            throw new FontException(
                localizer.format("AfmParser.MissingEndFontMetrics"));
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
                kp.setKerningsize( Float.parseFloat( tok.nextToken() ) );
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
            throw new FontException(
                localizer.format("AfmParser.MissingEndKernPairs"));
        }
    }

    /**
     * Remove all ',' in the string, if the string is {@code null}, a empty
     * string is returned.
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
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("afm");
        writer.writeAttribute("name", header.getFontName());

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
                    writer.writeFormatAttribute("width",
                        cm.getBllx() + cm.getBurx());
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
            writer.writeFormatAttribute("italic", header.getItalicAngle());

            // kerning
            String glyphname = cm.getN();
            AfmKernPairs kp;

            for( AfmKernPairs afmKerningPair : afmKerningPairs ) {
                kp = afmKerningPair;
                if( kp.getCharpre().equals( glyphname ) ) {
                    writer.writeStartElement( "kerning" );
                    writer.writeAttribute( "name", kp.getCharpost() );
                    writer.writeFormatAttribute( "size", kp.getKerningsize() );
                    writer.writeEndElement();
                }
            }

            // ligature
            if (cm.getL() != null) {
                for (String key : cm.getL().keySet()) {
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
