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

import org.extex.font.format.xtf.tables.AbstractXtfTable;
import org.extex.font.format.xtf.tables.XtfTableMap;

/**
 * Abstract class for the GSUB and GPOS table.
 * 
 * <p>
 * To handle the sample tables: ScriptList, FeatureList, LookupList
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractXtfSFLTable extends AbstractXtfTable {

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
     * Create a new object.
     * 
     * @param tm the table map
     */
    public AbstractXtfSFLTable(XtfTableMap tm) {

        super(tm);
    }

    /**
     * Getter for featureList.
     * 
     * @return the featureList
     */
    public XtfFeatureList getFeatureList() {

        return featureList;
    }

    /**
     * @see org.extex.font.format.xtf.tables.gps.XtfFeatureList#getFeatureTag(int)
     */
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

}
