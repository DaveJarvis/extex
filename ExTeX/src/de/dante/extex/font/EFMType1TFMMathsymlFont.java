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

package de.dante.extex.font;

import org.jdom.Document;

import de.dante.extex.interpreter.type.Dimen;
import de.dante.extex.interpreter.type.Font;
import de.dante.util.GeneralException;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.file.FileFinder;

/**
 * This class implements a efm-type1-font (math-syml)
 * (create from a TFM-file).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class EFMType1TFMMathsymlFont extends EFMType1TFMNOFont implements Font {

    /**
     * names for the parameter
     */
    public static final String[] PARAM = {"SLANT", "SPACE", "STRETCH",
            "SHRINK", "XHEIGHT", "QUAD", "EXTRASPACE", "NUM1", "NUM2", "NUM3",
            "DENOM1", "DENOM2", "SUP1", "SUP2", "SUP3", "SUB1", "SUB2",
            "SUPDROP", "SUBDROP", "DELIM1", "DELIM2", "AXISHEIGHT"};

    /**
     * Creates a new object.
     * @param   doc         the efm-document
     * @param   fontname    the fontname
     * @param   size        the emsize of the font
     * @param   filefinder  the fileFinder-object
     * @throws GeneralException ...
     * @throws ConfigurationException ...
     */
    public EFMType1TFMMathsymlFont(final Document doc, final String fontname,
            final Dimen size, final FileFinder filefinder)
            throws GeneralException, ConfigurationException {

        super(doc, fontname, size, filefinder);
    }

    /**
     * Return String for this class.
     * @return the String for this class
     */
    public String toString() {

        return "<fontname (EFMType1TFMMathsyml): " + getFontName()
                + " with size " + getEmsize().toString() + " unitsperem = "
                + getUnitsperem() + " ex = " + getEx() + " em = "
                + getEm().toString() + " (with " + getEmpr() + "%)"
                + " number of glyphs = " + getGylphMapSize() + " >";
    }

}
