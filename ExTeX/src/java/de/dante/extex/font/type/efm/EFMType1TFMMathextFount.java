/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.font.type.efm;

import org.jdom.Document;

import de.dante.extex.font.FontFactory;
import de.dante.extex.font.FountKey;
import de.dante.extex.font.exception.FontException;
import de.dante.extex.font.type.ModifiableFount;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.resource.ResourceFinder;

/**
 * This class implements a efm-type1-font (math-ext)
 * (create from a TFM-file).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class EFMType1TFMMathextFount extends EFMType1TFMNOFount
        implements
            ModifiableFount {

    /**
     * names for the parameter
     */
    public static final String[] PARAM = {"SLANT", "SPACE", "STRETCH",
            "SHRINK", "XHEIGHT", "QUAD", "EXTRASPACE", "DEFAULTRULETHICKNESS",
            "BIGOPSPACING1", "BIGOPSPACING2", "BIGOPSPACING3", "BIGOPSPACING4",
            "BIGOPSPACING5"};

    /**
     * Creates a new object.
     * @param   doc         the efm-document
     * @param   key         the fount key
     * @param   filefinder  the ResourceFinder-object
     * @param   ff          the font factory
     * @throws ConfigurationException from the config system.
     * @throws FontException if a font error occurs.
     */
    public EFMType1TFMMathextFount(final Document doc, final FountKey key,
            final ResourceFinder filefinder, final FontFactory ff)
            throws ConfigurationException, FontException {

        super(doc, key, filefinder, ff);
    }

    /**
     * Return String for this class.
     * @return the String for this class
     */
    public String toString() {

        return "<fontname (EFMType1TFMMathext): " + getFontName()
                + " with size " + getEmsize().toString() + " unitsperem = "
                + getUnitsperem() + " ex = " + getEx() + " em = "
                + getEm().toString() + " (with " + getEmpr() + "%)"
                + " number of glyphs = " + getGylphMapSize() + " >";
    }

}