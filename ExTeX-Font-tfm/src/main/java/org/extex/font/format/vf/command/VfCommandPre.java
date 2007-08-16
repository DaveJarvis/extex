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
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * VfCommand: pre
 * 
 * <p>
 * A preamble appears at the beginning, followed by a sequence of character
 * definitions, followed by a postamble. More precisely, the first byte of every
 * VF file must be the first byte of the following 'preamble command':
 * </p>
 * 
 * <pre>
 *    pre   247  i[1]  k[1]  x[k]  cs[4]  ds[4]
 * </pre>
 * 
 * <p>
 * Here <code>i</code> is the identification byte of VF, currently 202. The
 * string <code>x</code> is merely a comment, usually indicating the source of
 * the VF file. Parameters <code>cs</code> and <code>ds</code> are
 * respectively the check sum and the design size of the virtual font; they
 * should match the first two words in the header of the TFM file.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class VfCommandPre extends VfCommand {

    /**
     * the checksum
     */
    private int checksum;

    /**
     * the comment
     */
    private String comment;

    /**
     * the designsize
     */
    private TfmFixWord designsize;

    /**
     * the identification
     */
    private int identification;

    /**
     * Create e new object.
     * 
     * @param localizer The localizer for the messages.
     * @param rar the input.
     * @param ccode the command code.
     * @throws IOException if a IO-error occurred.
     * @throws FontException if a error reading the font.
     */
    public VfCommandPre(Localizer localizer, RandomAccessR rar, int ccode)
            throws IOException,
                FontException {

        super(localizer, ccode);

        if (ccode != PRE) {
            throw new FontException(getLocalizer().format("VF.WrongCode",
                String.valueOf(ccode)));
        }

        identification = rar.readByteAsInt();
        comment = readString(rar);
        checksum = rar.readInt();
        designsize =
                new TfmFixWord(rar.readInt(), TfmFixWord.FIXWORDDENOMINATOR);
    }

    /**
     * Returns the checksum.
     * 
     * @return Returns the checksum.
     */
    public int getChecksum() {

        return checksum;
    }

    /**
     * Returns the comment.
     * 
     * @return Returns the comment.
     */
    public String getComment() {

        return comment;
    }

    /**
     * Returns the designsize.
     * 
     * @return Returns the designsize.
     */
    public TfmFixWord getDesignsize() {

        return designsize;
    }

    /**
     * Returns the identification.
     * 
     * @return Returns the identification.
     */
    public int getIdentification() {

        return identification;
    }

    /**
     * Reads a character string from the header.
     * 
     * @param rar the input
     * @return the string
     * @throws IOException if an I/O error occurred.
     */
    private String readString(final RandomAccessR rar) throws IOException {

        int len = rar.readByteAsInt();
        StringBuffer buf = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            buf.append((char) rar.readByteAsInt());
        }
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("preamble");
        writer.writeAttribute("opcode", String.valueOf(getCommandCode()));
        writer.writeAttribute("identification", String.valueOf(identification));
        writer.writeAttribute("checksum", String.valueOf(checksum));
        writer.writeAttribute("designsize", designsize.toString());
        if (comment != null && comment.trim().length() > 0) {
            writer.writeStartElement("comment");
            writer.writeCharacters(comment);
            writer.writeEndElement();
        }
        writer.writeEndElement();

    }
}
