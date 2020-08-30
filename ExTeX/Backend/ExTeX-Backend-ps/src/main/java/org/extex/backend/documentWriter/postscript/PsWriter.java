/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.postscript;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.postscript.util.PsUnit;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.page.Page;

/**
 * This document writer produces multi-page PostScript documents.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PsWriter extends AbstractPostscriptWriter
        implements
            SingleDocumentStream {

    /**
     * The field {@code page} contains the byte arrays of the single pages.
     */
    private List<byte[]> page = new ArrayList<byte[]>();

    /**
     * The field {@code pageHeight} contains the height of the paper.
     */
    private Dimen pageHeight;

    /**
     * The field {@code pageWidth} contains the width of the paper.
     */
    private Dimen pageWidth;

    /**
     * The field {@code writer} contains the target output stream.
     */
    private PrintStream stream = null;

    /**
     * Creates a new object.
     * 
     * @param options the options for the document writer
     */
    public PsWriter(DocumentWriterOptions options) {

        super("ps");
        FixedDimen mediaWidth = options.getDimenOption("mediawidth");
        if (mediaWidth != null && mediaWidth.gt(Dimen.ZERO_PT)) {
            pageWidth = new Dimen(mediaWidth);
        } else {
            pageWidth =
                    new Dimen(Dimen.ONE * 210 * PsUnit.POINT_PER_100_IN
                            / (PsUnit.CM100_PER_IN * 10));
        }
        FixedDimen mediaHeight = options.getDimenOption("mediaheight");
        if (mediaHeight != null && mediaHeight.gt(Dimen.ZERO_PT)) {
            pageWidth = new Dimen(mediaHeight);
        } else {
            pageHeight =
                    new Dimen(Dimen.ONE * 297 * PsUnit.POINT_PER_100_IN
                            / (PsUnit.CM100_PER_IN * 10));
        }
    }

public void close() throws DocumentWriterException, IOException {

        boolean orderDesc = "Descend".equals(getParameter("PageOrder"));
        int pages = page.size();
        startFile(stream, "PS-Adobe-2.0");
        writeDsc(stream, "Pages", Integer.toString(pages));
        writeDsc(stream, "PageOrder", orderDesc ? "Descend" : "Ascend");
        writeBoundingBox(stream, pageWidth, pageHeight);

        writeFonts(stream, getFontManager());
        writeDsc(stream, "EndComments");

        stream.println("/TeXDict 300 dict def");
        getConverter().writeHeaders(stream);
        getFontManager().write(stream);
        getFontManager().reset();

        if (orderDesc) {
            for (int i = pages - 1; i >= 0; i--) {
                writePage(i);
            }
        } else {
            for (int i = 0; i < pages; i++) {
                writePage(i);
            }
        }
        writeDsc(stream, "EOF");
        stream.close();
        stream = null;
    }

    /**
*      java.io.OutputStream)
     */
    public void setOutputStream(OutputStream out) {

        this.stream = new PrintStream(out, true);
    }

    /**
*      org.extex.typesetter.type.page.Page)
     */
    public int shipout(Page p) throws GeneralException, IOException {

        if (p == null) {
            return 0;
        }
        page.add(getConverter().toPostScript(p));
        return 1;
    }

    /**
     * Write a single page to the output.
     * 
     * @param no the page number in the array
     * 
     * @throws IOException in case of an error during writing
     */
    private void writePage(int no) throws IOException {

        String n = Integer.toString(no + 1);
        writeDsc(stream, "Page", n, n);
        stream.write(page.get(no));
        stream.println("showpage");
    }

}
