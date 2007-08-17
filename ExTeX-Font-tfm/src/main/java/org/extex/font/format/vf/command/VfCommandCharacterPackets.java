/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.vf.command;

import java.io.IOException;

import org.extex.font.exception.FontException;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.framework.i18n.Localizer;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * VfCommand: character packets
 * 
 * <p>
 * The preamble is followed by zero or more character packets, where each
 * character packet begins with a byte that is &lt;<243. Character packets have
 * two formats, one long and one short:
 * </p>
 * 
 * <pre>
 *    long_char  242     pl[4]  cc[4]   tfm[4]  dvi[pl]
 * </pre>
 * 
 * <p>
 * This long form specifies a virtual character in the general case.
 * </p>
 * 
 * <pre>
 *    short_char 0..241=pl[1]  cc[1]   tfm[3]  dvi[pl]
 * </pre>
 * 
 * <p>
 * This short form specifies a virtual character in the common case when 0 &lt;=
 * pl &lt;242 and 0 &lt;= cc &lt;256 and 0 &lt; &lt;= tfm &lt; 2^24. The ccode
 * is pl!
 * </p>
 * <p>
 * Here <code>pl</code> denotes the packet length following the
 * <code>tfm</code> value; <code>cc</code> is the character code; and
 * <code>tfm</code> is the character width copied from the TFM file for this
 * virtual font. There should be at most one character packet having any given
 * <code>cc</code> code.
 * </p>
 * <p>
 * The <code>dvi</code> bytes are a sequence of complete DVI commands,
 * properly nested with respect to <code>push</code> and <code>pop</code>.
 * All DVI operations are permitted except <code>bop</code>, <code>eop</code>,
 * and commands with opcodes &gt;=243. Font selection commands (<code>fnt_num0</code>
 * through <code>fnt4</code>) must refer to fonts defined in the preamble.
 * </p>
 * <p>
 * Dimensions that appear in the DVI instructions are analogous to
 * <code>fix_word</code> quantities; i.e., they are integer multiples of 2^-20
 * times the design size of the virtual font. For example, if the virtual font
 * has design size 10pt, the DVI command to move down 5pt would be a
 * <code>down</code> instruction with parameter 2^19. The virtual font itself
 * might be used at a different size, say 12pt; then that <code>down</code>
 * instruction would move down 6pt instead. Each dimension must be less than
 * 2^24 in absolute value.
 * </p>
 * <p>
 * Device drivers processing VF files treat the sequences of <code>dvi</code>
 * bytes as subroutines or macros, implicitly enclosing them with
 * <code>push</code> and <code>pop</code>. Each subroutine begins with
 * <code>w=x=y=z=0</code>, and with current font <code>f</code> the number
 * of the first-defined in the preamble (undefined if there's no such font).
 * After the <code>dvi</code> commands have been performed, the <code>h</code>
 * and <code>v</code> position registers of DVI format and the current font
 * <code>f</code> are restored to their former values; then, if the subroutine
 * has been invoked by a <code>set_char</code> or <code>set</code> command,
 * <code>h</code> is increased by the TFM width (properly scaled) - just as if
 * a simple character had been typeset.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class VfCommandCharacterPackets extends VfCommand {

    /**
     * the character code (cc)
     */
    private int charactercode;

    /**
     * the dvi commands
     */
    private byte[] dvi;

    /**
     * the packet length (pl)
     */
    private int packetlength;

    /**
     * the character width (from tfm file) (tfm)
     */
    private TfmFixWord width;

    /**
     * Creates a new object.
     * 
     * @param localizer The localizer.
     * @param rar The input.
     * @param ccode The command code.
     * @throws FontException if a font error occurred.
     * @throws IOException if an IO-error occurred.
     */
    public VfCommandCharacterPackets(Localizer localizer, RandomAccessR rar,
            int ccode) throws FontException, IOException {

        super(localizer, ccode);

        // check range
        if (ccode < MIN_CHARACTER || ccode > MAX_CHARACTER) {
            throw new FontException(getLocalizer().format("VF.WrongCode",
                String.valueOf(ccode)));
        }

        if (ccode == MAX_CHARACTER) {
            // long format
            packetlength = rar.readInt();
            charactercode = rar.readInt();
            width =
                    new TfmFixWord(rar.readInt(), TfmFixWord.FIXWORDDENOMINATOR);
        } else {
            // short format
            packetlength = ccode;
            charactercode = rar.readByteAsInt();

            // 24 bit
            width =
                    new TfmFixWord(rar.readInt24(),
                        TfmFixWord.FIXWORDDENOMINATOR);
        }
        dvi = new byte[packetlength];
        for (int i = 0; i < packetlength; i++) {
            dvi[i] = (byte) rar.readByteAsInt();
        }
    }

    /**
     * Getter for charactercode.
     * 
     * @return the charactercode
     */
    public int getCharactercode() {

        return charactercode;
    }

    /**
     * Getter for dvi.
     * 
     * @return the dvi
     */
    public byte[] getDvi() {

        return dvi;
    }

    /**
     * Getter for packetlength.
     * 
     * @return the packetlength
     */
    public int getPacketlength() {

        return packetlength;
    }

    /**
     * Getter for width.
     * 
     * @return the width
     */
    public TfmFixWord getWidth() {

        return width;
    }

    @Override
    public String toString() {

        return "charcode: " + charactercode;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("char");
        writer.writeAttribute("opcode", getCommandCode());
        writer.writeAttribute("char", charactercode);
        writer.writeAttribute("packetlength", packetlength);
        writer.writeAttribute("width", width.toString());

        writer.writeStartElement("dvi");

        try {
            VfDviXml dvixml = new VfDviXml(writer);
            dvixml.interpret(new RandomAccessInputArray(dvi));
        } catch (FontException e) {
            throw new IOException(e.getLocalizedMessage());
        }

        writer.writeEndElement();
        writer.writeEndElement();

    }

    // /**
    // * @see de.dante.extex.font.type.PlFormat#toPL(
    // * de.dante.extex.font.type.PlWriter)
    // */
    // public void toPL(final PlWriter out) throws IOException, FontException {
    //
    // // read the char from ther master-tfm
    // int bc = mastertfm.getLengths().getBc();
    // TFMCharInfoWord ciw =
    // mastertfm.getCharinfo().getCharInfoWord(charactercode - bc);
    //
    // out.plopen("CHARACTER").addChar((short) charactercode);
    // if (ciw != null) {
    // ciw.toPL(out);
    // }
    //
    // // print the map
    // out.plopen("MAP");
    // try {
    // RandomAccessR arar = new RandomAccessInputArray(dvi);
    //
    // DviPl pl = new DviPl(out, fontfactory);
    // pl.interpret(arar);
    // arar.close();
    // } catch (Exception e) {
    // throw new VFDviException(e.getMessage());
    // }
    // out.plclose();
    // out.plclose();
    // }
    //
    // /**
    // * Add glyph to the element
    // *
    // * @param element the element
    // * @throws FontException if a font-error occurs.
    // */
    // public void addGlyph(final Element element) throws FontException {
    //
    // // read the char from ther master-tfm
    // int bc = mastertfm.getLengths().getBc();
    // TFMCharInfoWord ciw =
    // mastertfm.getCharinfo().getCharInfoWord(charactercode - bc);
    //
    // // create glyph
    // Element glyph = new Element("glyph");
    //
    // glyph.setAttribute("ID", String.valueOf(charactercode));
    // glyph.setAttribute("glyph-number", String.valueOf(charactercode - bc));
    // String c = Character.toString((char) (charactercode - bc));
    // if (c != null && c.trim().length() > 0) {
    // glyph.setAttribute("char", c);
    // }
    // if (ciw.getGlyphname() != null) {
    // element.setAttribute("glyph-name", ciw.getGlyphname().substring(1));
    // }
    // ciw.addGlyph(glyph);
    //
    // try {
    // RandomAccessR arar = new RandomAccessInputArray(dvi);
    //
    // Element cmd = new Element("commands");
    // DviEfm dviefm = new DviEfm(cmd, fontfactory, fontmap);
    // dviefm.interpret(arar);
    // arar.close();
    // glyph.addContent(cmd);
    // } catch (Exception e) {
    // throw new VFDviException(e.getMessage());
    // }
    // element.addContent(glyph);
    // }
}
