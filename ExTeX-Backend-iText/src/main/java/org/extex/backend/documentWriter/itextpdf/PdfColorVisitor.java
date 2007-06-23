/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.itextpdf;

import org.extex.color.ColorVisitor;
import org.extex.color.model.CmykColor;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.HsvColor;
import org.extex.color.model.RgbColor;
import org.extex.core.exception.GeneralException;

/**
 * Color visitor for pdf.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class PdfColorVisitor implements ColorVisitor {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.color.ColorVisitor#visitCmyk(org.extex.color.model.CmykColor,
     *      java.lang.Object)
     */
    public Object visitCmyk(CmykColor color, Object value)
            throws GeneralException {

        // TODO mgn: visitCmyk unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.color.ColorVisitor#visitGray(org.extex.color.model.GrayscaleColor,
     *      java.lang.Object)
     */
    public Object visitGray(GrayscaleColor color, Object value)
            throws GeneralException {

        // TODO mgn: visitGray unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.color.ColorVisitor#visitHsv(org.extex.color.model.HsvColor,
     *      java.lang.Object)
     */
    public Object visitHsv(HsvColor color, Object value)
            throws GeneralException {

        // TODO mgn: visitHsv unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.color.ColorVisitor#visitRgb(org.extex.color.model.RgbColor,
     *      java.lang.Object)
     */
    public Object visitRgb(RgbColor color, Object value)
            throws GeneralException {

        // TODO mgn: visitRgb unimplemented
        return null;
    }

}
