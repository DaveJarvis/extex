/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterFactory;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.exception.BackendDocumentWriterDefinedException;
import org.extex.backend.exception.BackendException;
import org.extex.backend.exception.BackendUnknownDocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.pageFilter.PagePipe;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.core.exception.GeneralException;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontAware;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.type.page.Page;

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
            PropertyAware,
            ColorAware,
            ResourceAware,
            FontAware {

    /**
     * This internal class acts as page counter as last element in the node
     * pipe.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4728 $
     */
    private class Counter implements PagePipe {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.pageFilter.PagePipe#close()
         */
        public void close() throws BackendException {

            if (documentWriter == null) {
                return;
            }
            try {
                documentWriter.close();
            } catch (GeneralException e) {
                new BackendException(e);
            } catch (IOException e) {
                throw new BackendException(e);
            }
            documentWriter = null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.pageFilter.PagePipe#setOutput(
         *      org.extex.backend.pageFilter.PagePipe)
         */
        public void setOutput(PagePipe next) {

            throw new RuntimeException("this should not happen");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.pageFilter.PagePipe#setParameter(
         *      java.lang.String, java.lang.String)
         */
        public void setParameter(String name, String value) {

            // nothing to do
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.pageFilter.PagePipe#shipout(
         *      org.extex.typesetter.type.page.Page)
         */
        public void shipout(Page nodes) throws BackendException {

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
     * The field <tt>colorConverter</tt> contains the color converter.
     */
    private ColorConverter colorConverter;

    /**
     * The field <tt>counter</tt> contains the counter page pipe which will
     * always be placed at the end of the of the pipe.
     */
    private PagePipe counter = new Counter();

    /**
     * The field <tt>documentWriter</tt> contains the document writer.
     */
    private DocumentWriter documentWriter = null;

    /**
     * The field <tt>documentWriterFactory</tt> contains the factory for new
     * document writers.
     */
    private DocumentWriterFactory documentWriterFactory;

    /**
     * The field <tt>documentWriterType</tt> contains the type of the document
     * writer to use.
     */
    private String documentWriterType = "?";

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field <tt>fontFactory</tt> contains the font factory.
     */
    private CoreFontFactory fontFactory;

    /**
     * The field <tt>pages</tt> contains the number of pages already sent to
     * the document writer.
     */
    private int pages = 0;

    /**
     * The field <tt>params</tt> contains the parameters to be passed to the
     * document writer.
     */
    private Map<String, String> params = new HashMap<String, String>();

    /**
     * The field <tt>pipeFirst</tt> contains the elements of the pipe.
     */
    private PagePipe pipeFirst = counter;

    /**
     * The field <tt>pipeLast</tt> contains the last item in the pipe.
     * Initially it is the counter. If the pipe is longer this value is the last
     * item before the counter.
     */
    private PagePipe pipeLast = counter;

    /**
     * The field <tt>properties</tt> contains the properties.
     */
    private Properties properties;

    /**
     * The field <tt>streamFactory</tt> contains the output stream factory.
     */
    private OutputStreamFactory streamFactory;


    public BackendDriverImpl() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.BackendDriver#add(
     *      org.extex.backend.pageFilter.PagePipe)
     */
    public void add(PagePipe processor) {

        processor.setOutput(counter);

        if (pipeFirst == counter) {
            pipeFirst = processor;
        } else {
            pipeLast.setOutput(processor);
        }

        pipeLast = processor;
    }

    /**
     * {@inheritDoc}
     * 
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
     * {@inheritDoc}
     * 
     * @see org.extex.backend.BackendDriver#getDocumentWriter()
     */
    public DocumentWriter getDocumentWriter() throws DocumentWriterException {

        if (documentWriter == null) {

            documentWriter = documentWriterFactory.newInstance(//
                documentWriterType, //
                streamFactory);
            if (documentWriter instanceof PropertyAware) {
                ((PropertyAware) documentWriter).setProperties(properties);
            }
            if (documentWriter instanceof ColorAware) {
                ((ColorAware) documentWriter).setColorConverter(colorConverter);
            }
            if (documentWriter instanceof ResourceAware) {
                ((ResourceAware) documentWriter).setResourceFinder(finder);
            }
            if (documentWriter instanceof FontAware) {
                ((FontAware) documentWriter).setFontFactory(fontFactory);
            }

            for (Entry<String, String> param : params.entrySet()) {
                documentWriter.setParameter(param.getKey(), param.getValue());
            }

            if (documentWriter instanceof MultipleDocumentStream) {
                ((MultipleDocumentStream) documentWriter)
                    .setOutputStreamFactory(streamFactory);
            }

        }
        return documentWriter;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.BackendDriver#getDocumentWriterType()
     */
    public String getDocumentWriterType() {

        return documentWriterType;
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
     * {@inheritDoc}
     * 
     * @see org.extex.color.ColorAware#setColorConverter(org.extex.color.ColorConverter)
     */
    public void setColorConverter(ColorConverter converter) {

        this.colorConverter = converter;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.BackendDriver#setDocumentWriterFactory(
     *      org.extex.backend.documentWriter.DocumentWriterFactory)
     */
    public void setDocumentWriterFactory(
            DocumentWriterFactory documentWriterFactory) {

        this.documentWriterFactory = documentWriterFactory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.BackendDriver#setDocumentWriterType(java.lang.String)
     */
    public void setDocumentWriterType(String type)
            throws BackendDocumentWriterDefinedException,
                BackendUnknownDocumentWriterException {

        if (documentWriterType.equals(type)) {
            return;
        }
        if (type == null) {
            throw new IllegalArgumentException("DocumentWriterType");
        }
        if (documentWriter != null) {
            throw new BackendDocumentWriterDefinedException();
        }
        if (documentWriterFactory != null && !documentWriterFactory.check(type)) {
            throw new BackendUnknownDocumentWriterException(type);
        }
        documentWriterType = type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.FontAware#setFontFactory(org.extex.font.CoreFontFactory)
     */
    public void setFontFactory(CoreFontFactory factory) {

        this.fontFactory = factory;
    }

    /**
     * Setter for the output stream.
     * 
     * @param factory the output stream
     * 
     * @see org.extex.backend.documentWriter.MultipleDocumentStream#setOutputStreamFactory(
     *      org.extex.backend.outputStream.OutputStreamFactory)
     */
    public void setOutputStreamFactory(OutputStreamFactory factory) {

        this.streamFactory = factory;
    }

    /**
     * Setter for a named parameter. Parameters are a general mechanism to
     * influence the behavior of the document writer. Any parameter not known by
     * the document writer has to be ignored.
     * <p>
     * If the document writer is in use already then the parameter is passed to
     * the document writer. Otherwise the parameter is stored until a document
     * writer is created. Then the parameter is passed on.
     * </p>
     * 
     * @param name the name of the parameter
     * @param value the value of the parameter
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {

        if (documentWriter != null) {
            documentWriter.setParameter(name, value);
        } else {
            params.put(name, value);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.PropertyAware#setProperties(java.util.Properties)
     */
    public void setProperties(Properties properties) {

        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder f) {

        this.finder = f;
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
     * @throws BackendException in case of an error
     * 
     * @see org.extex.backend.BackendDriver#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(Page page) throws BackendException {

        if (documentWriter == null) {
            getDocumentWriter(); // to force delayed creation;
        }
        int n = pages;
        pipeFirst.shipout(page);
        return pages - n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return documentWriterType;
    }

}
