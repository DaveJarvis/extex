/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.gps;

import java.io.IOException;

import org.extex.font.format.xtf.tables.AbstractXtfTable;
import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.XtfTableDirectory;
import org.extex.font.format.xtf.tables.XtfTableMap;
import org.extex.font.format.xtf.tables.gps.XtfFeatureList.Feature;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.LangSys;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.Script;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.LanguageSystemTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Abstract class for the GSUB and GPOS table.
 * 
 * <p>
 * To handle the sample tables: ScriptList, FeatureList, LookupList
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public abstract class AbstractXtfSFLTable extends AbstractXtfTable
        implements
            XtfTable,
            LookupTableFactory,
            XMLWriterConvertible {

    /**
     * The feature list.
     */
    protected XtfFeatureList featureList;

    /**
     * The lookup list.
     */
    protected XtfLookupList lookupList;

    /**
     * The script list.
     */
    protected XtfScriptList scriptList;

    /**
     * Create a new object
     * 
     * @param tablemap the table map
     * @param de directory entry
     * @param rar input
     * @throws IOException if an IO-error occurs
     */
    public AbstractXtfSFLTable(XtfTableMap tablemap,
            XtfTableDirectory.Entry de, RandomAccessR rar) throws IOException {

        super(tablemap);
        rar.seek(de.getOffset());

        // Header
        /* int version = */rar.readInt();
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
                new XtfLookupList(rar, de.getOffset(), de.getOffset()
                        + lookupListOffset, this, this);
    }

    /**
     * Find the language system.
     * 
     * @param tag The script tag.
     * @param language The language.
     * @return Returns the language system or {@code null}, if not
     *         found.
     */
    public LangSys findLangSys(ScriptTag tag, LanguageSystemTag language) {

        return scriptList.findLangSys(tag, language);
    }

    /**
     * Find the lookups.
     * 
     * @param tag The tag.
     * @param language The language.
     * @param feature The feature.
     * @return Returns the lookups, or {@code null}, if nothing is
     *         found.
     */
    public XtfLookup[] findLookup(ScriptTag tag, LanguageSystemTag language,
            FeatureTag feature) {

        LangSys langSys = findLangSys(tag, language);
        if (langSys != null) {
            int fidx = langSys.getFeatureTag(feature);
            if (fidx >= 0 && fidx < featureList.getFeatureCount()) {
                Feature ff = featureList.findFeature(feature);
                if (ff != null) {
                    return ff.getLookups();
                }
            }
        }
        return null;
    }

    /**
     * Find a script.
     * 
     * @param tag The script tag.
     * @return returns the script or {@code null}, if not found.
     */
    public Script findScript(ScriptTag tag) {

        return scriptList.findScript(tag);
    }

    /**
     * Getter for featureList.
     * 
     * @return the featureList
     */
    public XtfFeatureList getFeatureList() {

        return featureList;
    }

    public String getFeatureTag(int idx) {

        return featureList.getFeatureTag(idx);
    }

    /**
     * Getter for lookupList.
     * 
     * @return the lookupList
     */
    public XtfLookupList getLookupList() {

        return lookupList;
    }

    /**
     * Getter for scriptList.
     * 
     * @return the scriptList
     */
    public XtfScriptList getScriptList() {

        return scriptList;
    }

    public void writeXML(XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        scriptList.writeXML(writer);
        featureList.writeXML(writer);
        lookupList.writeXML(writer);
        writer.writeEndElement();
    }

}
