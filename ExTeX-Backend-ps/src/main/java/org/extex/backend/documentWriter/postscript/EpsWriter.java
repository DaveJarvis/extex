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

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.postscript.util.FontManager;
import org.extex.backend.documentWriter.postscript.util.HeaderManager;
import org.extex.backend.documentWriter.postscript.util.PsConverter;
import org.extex.backend.documentWriter.postscript.util.PsUnit;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.page.Page;

/**
 * This document writer produces Encapsulated Postscript documents.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EpsWriter extends AbstractPostscriptWriter {

    /**
     * The field <tt>fontManager</tt> contains the font manager.
     */
    private FontManager fontManager;

    /**
     * The field <tt>headerManager</tt> contains the header manager.
     */
    private HeaderManager headerManager = new HeaderManager();

    /**
     * Creates a new object.
     * 
     * @param options the options for the document writer
     */
    public EpsWriter(DocumentWriterOptions options) {

        super();
        fontManager = new FontManager();
        setExtension("eps");
    }

    /**
     * This method is invoked upon the end of the processing. It does simply
     * nothing for this class.
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    public void close() {

        // nothing to do
    }

    /**
     * This is the entry point for the document writer. Here it receives a
     * complete node list to be sent to the output writer. It can be assumed
     * that all values for width, height, and depth of the node lists are
     * properly filled. Thus all information should be present to place the ink
     * on the paper.
     * 
     * @param page the page to send
     * 
     * @return returns the number of pages shipped
     * 
     * @throws GeneralException in case of a general exception<br>
     *         especially<br>
     *         DocumentWriterException in case of an error
     * @throws IOException in case of an IO exception
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(Page page) throws GeneralException, IOException {

        if (page == null) {
            return 0;
        }

        PsConverter converter = getConverter(headerManager);

        OutputStream stream = newOutputStream("eps");

        byte[] bytes = converter.toPostScript(page, fontManager, headerManager);

        stream.write("%!PS-Adobe-2.0 EPSF-2.0\n".getBytes());
        writeDsc(stream, "Creator", getParameter("Creator"));
        writeDsc(stream, "Title", getParameter("Title"));
        writeBB(stream, "BoundingBox", page.getNodes());
        writeHRBB(stream, "HiResBoundingBox", page.getNodes());
        writeFonts(stream, fontManager);
        writeDsc(stream, "EndComments");
        fontManager.write(stream);
        fontManager.clear();
        headerManager.write(stream);
        stream.write(bytes);
        writeDsc(stream, "EOF");
        stream.close();
        stream = null;
        return 1;
    }

    /**
     * Write a BoundingBox DSC to an output stream.
     * 
     * @param stream the target stream to write to
     * @param name the name of the DSC comment
     * @param nodes the nodes to extract the dimensions from
     * 
     * @throws IOException in case of an error during writing
     */
    private void writeBB(OutputStream stream, String name, NodeList nodes)
            throws IOException {

        StringBuffer sb = new StringBuffer();
        stream.write('%');
        stream.write('%');
        stream.write(name.getBytes());
        stream.write(':');
        stream.write(' ');
        stream.write("0 0 ".getBytes());
        PsUnit.toPoint(nodes.getWidth(), sb, true);
        stream.write(sb.toString().getBytes());
        sb.delete(0, sb.length() - 1);
        stream.write(' ');
        Dimen d = new Dimen(nodes.getHeight());
        d.add(nodes.getDepth());
        PsUnit.toPoint(d, sb, true);
        stream.write(sb.toString().getBytes());
        stream.write('\n');
    }

    /**
     * Write a HiResBoundingBox DSC to an output stream.
     * 
     * @param stream the target stream to write to
     * @param name the name of the DSC comment
     * @param nodes the nodes to extract the dimensions from
     * 
     * @throws IOException in case of an error during writing
     */
    private void writeHRBB(OutputStream stream, String name, NodeList nodes)
            throws IOException {

        StringBuffer sb = new StringBuffer();
        stream.write('%');
        stream.write('%');
        stream.write(name.getBytes());
        stream.write(':');
        stream.write(' ');
        stream.write("0 0 ".getBytes());
        PsUnit.toPoint(nodes.getWidth(), sb, false);
        stream.write(sb.toString().getBytes());
        sb.delete(0, sb.length() - 1);
        stream.write(' ');
        Dimen d = new Dimen(nodes.getHeight());
        d.add(nodes.getDepth());
        PsUnit.toPoint(d, sb, false);
        stream.write(sb.toString().getBytes());
        stream.write('\n');
    }

}
