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

package org.extex.backend.documentWriter.pdf;

import org.extex.backend.documentWriter.pdf.exception.DocumentWriterPdfBoxColorException;
import org.extex.color.ColorVisitor;
import org.extex.color.model.CmykColor;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.HsvColor;
import org.extex.color.model.RgbColor;
import org.extex.core.exception.GeneralException;
import org.pdfbox.pdmodel.edit.PDPageContentStream;

/**
 * Color visitor for pdf.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class PdfColorVisitor implements ColorVisitor {

    /**
     * div for 16-bit to 8 bit.
     */
    private static final int DIV = 0xff;

    /**
     * div for 16-bit to 0.0-1.0.
     */
    private static final double DIVD = 0xffff;

    /**
*      java.lang.Object)
     */
    @Override
    public Object visitCmyk(CmykColor color, Object value)
            throws GeneralException {

        try {

            PDPageContentStream contentstream = (PDPageContentStream) value;
            // TODO gene: Support the CMYK color model
            // contentstream.setStrokingColor(color.getCyan() / DIVD,
            // color.getMagenta() / DIVD, color.getYellow() / DIVD,
            // color.getBlack() / DIVD);

        } catch (Exception e) {
            throw new DocumentWriterPdfBoxColorException(e);
        }
        return null;
    }

    /**
*      java.lang.Object)
     */
    @Override
    public Object visitGray(GrayscaleColor color, Object value)
            throws GeneralException {

        try {

            PDPageContentStream contentstream = (PDPageContentStream) value;
            int intensity = color.getGray(); // TODO gene: provide proper
                                             // scaling
            contentstream.setStrokingColor(intensity, intensity, intensity);

        } catch (Exception e) {
            throw new DocumentWriterPdfBoxColorException(e);
        }
        return null;
    }

    /**
*      java.lang.Object)
     */
    @Override
    public Object visitHsv(HsvColor color, Object value)
            throws GeneralException {

        return null;
    }

    /**
*      java.lang.Object)
     */
    @Override
    public Object visitRgb(RgbColor color, Object value)
            throws GeneralException {

        try {

            PDPageContentStream contentstream = (PDPageContentStream) value;
            contentstream.setStrokingColor(color.getRed() / DIV,
                color.getGreen() / DIV, color.getBlue() / DIV);

        } catch (Exception e) {
            throw new DocumentWriterPdfBoxColorException(e);
        }
        return null;
    }
}
