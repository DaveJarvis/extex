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

import org.extex.font.format.xtf.XtfScriptList.LangSys;
import org.extex.font.format.xtf.XtfScriptList.Script;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Glyph substitution (GSUB).
 * 
 * <p>
 * To access GSUB information, clients should use the following procedure:
 * <p>
 * <ol>
 * <li>Locate the current script in the GSUB ScriptList table.</li>
 * <li>If the language system is known, search the script for the correct
 * LangSys table; otherwise, use the script's default language system
 * (DefaultLangSys table).</li>
 * <li>The LangSys table provides index numbers into the GSUB FeatureList table
 * to access a required feature and a number of additional features.</li>
 * <li>Inspect the FeatureTag of each feature, and select the features to apply
 * to an input glyph string. Each feature provides an array of index numbers
 * into the GSUB LookupList table.</li>
 * <li>Assemble all lookups from the set of chosen features, and apply the
 * lookups in the order given in the LookupList table.</li>
 * </ol>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OtfTableGSUB extends AbstractXtfSFLTable
        implements
            XtfTable,
            LookupTableFactory,
            XMLWriterConvertible {

    /**
     * Tag for a ligature.
     */
    private static final String LIGATURE = "liga";

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
        scriptList =
                new XtfScriptList(rar, de.getOffset() + scriptListOffset, this);

        // Feature List
        featureList =
                new XtfFeatureList(rar, de.getOffset() + featureListOffset,
                    this);

        // Lookup List
        lookupList =
                new XtfLookupList(rar, de.getOffset() + lookupListOffset, this,
                    this);
    }

    /**
     * @see org.extex.font.format.xtf.XtfScriptList#findLangSys(java.lang.String,
     *      java.lang.String)
     */
    public LangSys findLangSys(String tag, String language) {

        return scriptList.findLangSys(tag, language);
    }

    /**
     * Find the lookup.
     * 
     * @param tag The tag.
     * @param language The language.
     * @param feature The feature.
     */
    public void findLookup(String tag, String language, String feature) {

        LangSys langSys = findLangSys(tag, language);
        if (langSys != null) {

        }
    }

    /**
     * @see org.extex.font.format.xtf.XtfScriptList#findScript(java.lang.String)
     */
    public Script findScript(String tag) {

        return scriptList.findScript(tag);
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
     *      int, int, org.extex.font.format.xtf.XtfGlyphName)
     */
    public XtfLookupTable read(RandomAccessR rar, int type, int offset,
            XtfGlyphName xtfGlyph) throws IOException {

        switch (type) {
            case XtfLookup.GSUB_1_SINGLE:
                return XtfGSUBSingleTable.newInstance(rar, offset, this);
            case XtfLookup.GSUB_2_MULTIPLE:
                return XtfGSUBMultipleTable.newInstance(rar, offset, this);
            case XtfLookup.GSUB_3_ALTERNATE:
                return XtfGSUBAlternateTable.newInstance(rar, offset, this);
            case XtfLookup.GSUB_4_LIGATURE:
                return XtfGSUBLigatureTable.newInstance(rar, offset, this);
            case XtfLookup.GSUB_5_CONTEXT:
                return XtfGSUBContextTable.newInstance(rar, offset, this);
            case XtfLookup.GSUB_6_CHAINING_CONTEXTUAL:
                return XtfGSUBChainingTable.newInstance(rar, offset, this);
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
