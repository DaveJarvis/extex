/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf;

import java.io.IOException;

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Glyph substitution.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OtfTableGSUB extends AbstractXtfTable
        implements
            XtfTable,
            XMLWriterConvertible {

    /**
     * Version
     */
    private int version;

    /**
     * scriptlist
     */
    private XtfScriptList scriptList;

    /**
     * featurelist
     */
    private XtfFeatureList featureList;

    /**
     * lookuplist
     */
    private XtfLookupList lookupList;

    /**
     * Create a new object
     * 
     * @param tablemap the tablemap
     * @param de directory entry
     * @param rar input
     * @throws IOException if an IO-error occurs
     */
    OtfTableGSUB(XtfTableMap tablemap, XtfTableDirectory.Entry de,
            RandomAccessR rar) throws IOException {

        super(tablemap);
        rar.seek(de.getOffset());

        // GSUB Header
        version = rar.readInt();
        int scriptListOffset = rar.readUnsignedShort();
        int featureListOffset = rar.readUnsignedShort();
        int lookupListOffset = rar.readUnsignedShort();

        // Script List
        scriptList = new XtfScriptList(rar, de.getOffset() + scriptListOffset);

        // Feature List
        featureList =
                new XtfFeatureList(rar, de.getOffset() + featureListOffset);

        // Lookup List
        lookupList = new XtfLookupList(rar, de.getOffset() + lookupListOffset);
    }

    /**
     * Get the table type, as a table directory value.
     * 
     * @return Returns the table type
     */
    public int getType() {

        return XtfReader.GSUB;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "gsub";
    }

    /**
     * @return Returns the featureList.
     */
    public XtfFeatureList getFeatureList() {

        return featureList;
    }

    /**
     * @return Returns the lookupList.
     */
    public XtfLookupList getLookupList() {

        return lookupList;
    }

    /**
     * @return Returns the scriptList.
     */
    public XtfScriptList getScriptList() {

        return scriptList;
    }

    /**
     * @return Returns the version.
     */
    public int getVersion() {

        return version;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        writer.writeComment("incomplete");
        writer.writeEndElement();
    }

}