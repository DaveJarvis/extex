/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.font.type.vf.command;

import java.io.IOException;

import org.extex.font.exception.FontException;
import org.extex.font.type.vf.exception.VFWrongCodeException;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;


/**
 * VFCommand: post
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class VFCommandPost extends VFCommand {

    /**
     * Create e new object.
     *
     * @param rar       the input
     * @param ccode     the command code
     * @throws IOException if a IO-error occured
     * @throws FontException if a error reading the font.
     */
    public VFCommandPost(final RandomAccessR rar, final int ccode)
            throws IOException, FontException {

        super(ccode);

        if (ccode != POST) {
            throw new VFWrongCodeException(String.valueOf(ccode));
        }
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(final XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("post");
        writer.writeEndElement();
    }
}
