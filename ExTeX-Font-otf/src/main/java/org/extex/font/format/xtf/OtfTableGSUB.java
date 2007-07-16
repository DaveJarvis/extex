/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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
            LookupTableFactory,
            XMLWriterConvertible {

    /**
     * featurelist
     */
    private XtfFeatureList featureList;

    /**
     * lookuplist
     */
    private XtfLookupList lookupList;

    /**
     * scriptlist
     */
    private XtfScriptList scriptList;

    /**
     * Version
     */
    private int version;

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
        lookupList =
                new XtfLookupList(rar, de.getOffset() + lookupListOffset, this);
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
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "gsub";
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
     * @return Returns the version.
     */
    public int getVersion() {

        return version;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.LookupTableFactory#lookupType(int)
     */
    public String lookupType(int type) {

        if (type >= 1 && type < XtfLookup.LOOKUP_TYPE_NAMES_GSUB.length - 1) {
            return XtfLookup.LOOKUP_TYPE_NAMES_GSUB[type - 1];
        }
        return "Unknown";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.LookupTableFactory#read(org.extex.util.file.random.RandomAccessR,
     *      int, int)
     */
    public XtfLookupTable read(RandomAccessR rar, int type, int offset)
            throws IOException {

        switch (type) {
            case XtfLookup.GSUB_1_SINGLE:
                return XtfGSUBSingleTable.newInstance(rar, offset);
            case XtfLookup.GSUB_2_MULTIPLE:
                return XtfGSUBMultipleTable.newInstance(rar, offset);
            case XtfLookup.GSUB_3_ALTERNATE:
                return XtfGSUBAlternateTable.newInstance(rar, offset);
            case XtfLookup.GSUB_4_LIGATURE:
                return XtfGSUBLigatureTable.newInstance(rar, offset);
            case XtfLookup.GSUB_5_CONTEXT:
                return XtfGSUBContextTable.newInstance(rar, offset);
            case XtfLookup.GSUB_6_CHAINING_CONTEXTUAL:
                return XtfGSUBChainingTable.newInstance(rar, offset);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        writer.writeAttribute("version", version);
        scriptList.writeXML(writer);
        featureList.writeXML(writer);
        lookupList.writeXML(writer);
        writer.writeEndElement();
    }

}
