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

package org.extex.backend;

import java.io.IOException;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.exception.BackendException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.pageFilter.PagePipe;
import org.extex.typesetter.type.page.Page;
import org.extex.util.exception.GeneralException;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.Configuration;

/**
 * This back-end driver can be used to combine several components.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class BackendDriverImpl
        implements
            BackendDriver,
            MultipleDocumentStream,
            Configurable {

    /**
     * This internal class acts as page counter as last element in the node pipe.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4728 $
     */
    private class Counter implements PagePipe {

        /**
         * @see org.extex.backend.nodeFilter.NodePipe#close()
         */
        public void close() throws BackendException {

            try {
                documentWriter.close();
            } catch (GeneralException e) {
                new BackendException(e);
            } catch (IOException e) {
                new BackendException(e);
            }
            documentWriter = null;
        }

        /**
         * @see org.extex.backend.nodeFilter.NodePipe#setNext(
         *      org.extex.backend.nodeFilter.NodePipe)
         */
        public void setOutput(final PagePipe next) {

            throw new RuntimeException("this should not happen");
        }

        /**
         * @see org.extex.backend.nodeFilter.NodePipe#setParameter(
         *      java.lang.String,
         *      java.lang.String)
         */
        public void setParameter(final String name, final String value) {

        }

        /**
         * @see org.extex.backend.nodeFilter.NodePipe#process(
         *      org.extex.typesetter.type.page.Page)
         */
        public void shipout(final Page nodes) throws BackendException {

            try {
                documentWriter.shipout(nodes);
            } catch (DocumentWriterException e) {
                throw e;
            } catch (GeneralException e) {
                throw new DocumentWriterException(e);
            } catch (IOException e) {
                throw new DocumentWriterException(e);
            }
            pages++;
        }

    }

    /**
     * The field <tt>counter</tt> contains the counter page pipe  which will
     * always be placed at the end of the of the pipe.
     */
    private PagePipe counter = new Counter();

    /**
     * The field <tt>documentWriter</tt> contains the document writer.
     */
    private DocumentWriter documentWriter = null;

    /**
     * The field <tt>pages</tt> contains the number of pages already sent to the
     * document writer.
     */
    private int pages = 0;

    /**
     * The field <tt>pipeFirst</tt> contains the elements of the pipe.
     */
    private PagePipe pipeFirst = counter;

    /**
     * The field <tt>pipeLast</tt> contains the last item in the pipe. Initially
     * it is the counter. If the pipe is longer this value is the last item
     * before the counter.
     */
    private PagePipe pipeLast = counter;

    /**
     * Creates a new object.
     */
    public BackendDriverImpl() {

        super();
    }

    /**
     * @see org.extex.backend.BackendDriver#add(
     *      org.extex.backend.pageFilter.PagePipe)
     */
    public void add(final PagePipe processor) {

        processor.setOutput(counter);

        if (pipeFirst == counter) {
            pipeFirst = processor;
        } else {
            pipeLast.setOutput(processor);
        }

        pipeLast = processor;
    }

    /**
     * @see org.extex.backend.BackendDriver#close()
     */
    public void close() throws BackendException {

        pipeFirst.close();

        if (documentWriter != null) {
            try {
                documentWriter.close();
            } catch (GeneralException e) {
                throw new BackendException(e);
            } catch (IOException e) {
                throw new BackendException(e);
            }
            documentWriter = null;
        }
    }

    /**
     * Configure an object according to a given Configuration.
     *
     * @param config the configuration object to consider
     *
     * @see org.extex.util.framework.configuration.Configurable#configure(
     *      org.extex.util.framework.configuration.Configuration)
     */
    public void configure(final Configuration config) {

    }

    /**
     * Getter for the document writer.
     *
     * @return the document writer
     *
     * @see org.extex.backend.BackendDriver#getDocumentWriter()
     */
    public DocumentWriter getDocumentWriter() {

        return documentWriter;
    }

    /**
     * Getter for the extension associated with this kind of output. For
     * instance <tt>pdf</tt> is the expected value for PDF files and
     * <tt>dvi</tt> is the expected value for DVI files.
     *
     * @return the appropriate extension for file names
     *
     * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
     */
    public String getExtension() {

        return documentWriter.getExtension();
    }

    /**
     * Getter for the number of pages already produced.
     *
     * @return the number of pages already shipped out
     *
     * @see org.extex.backend.BackendDriver#getPages()
     */
    public int getPages() {

        return pages;
    }

    /**
     * Setter for the document writer.
     *
     * @param docWriter the document writer
     *
     * @see org.extex.backend.BackendDriver#setDocumentWriter(
     *      org.extex.backend.documentWriter.DocumentWriter)
     */
    public void setDocumentWriter(final DocumentWriter docWriter) {

        documentWriter = docWriter;
    }

    /**
     * Setter for the output stream.
     *
     * @param factory the output stream
     *
     * @see org.extex.backend.documentWriter.MultipleDocumentStream#setOutputStreamFactory(
     *      org.extex.backend.outputStream.OutputStreamFactory)
     */
    public void setOutputStreamFactory(final OutputStreamFactory factory) {

        if (documentWriter instanceof MultipleDocumentStream) {
            ((MultipleDocumentStream) documentWriter)
                .setOutputStreamFactory(factory);
        }
    }

    /**
     * Setter for a named parameter.
     * Parameters are a general mechanism to influence the behavior of the
     * document writer. Any parameter not known by the document writer has to
     * be ignored.
     *
     * @param name the name of the parameter
     * @param value the value of the parameter
     *
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String,
     *      java.lang.String)
     */
    public void setParameter(final String name, final String value) {

        if (documentWriter != null) {
            documentWriter.setParameter(name, value);
        }
    }

    /**
     * This is the entry point for the document writer. Here it receives a
     * complete node list to be sent to the output writer. It can be assumed
     * that all values for width, height, and depth of the node lists are
     * properly filled. Thus all information should be present to place the
     * ink on the paper.
     *
     * @param page the page to send
     *
     * @return returns the number of pages shipped
     *
     * @throws BackendException in case of an error
     *
     * @see org.extex.backend.BackendDriver#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(final Page page) throws BackendException {

        int n = pages;
        pipeFirst.shipout(page);
        return pages - n;
    }

}
