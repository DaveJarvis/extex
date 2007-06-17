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
import java.util.HashMap;
import java.util.Map;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.postscript.util.FontManager;
import org.extex.backend.documentWriter.postscript.util.HeaderManager;
import org.extex.backend.documentWriter.postscript.util.PsBasicConverter;
import org.extex.backend.documentWriter.postscript.util.PsBoxConverter;
import org.extex.backend.documentWriter.postscript.util.PsConverter;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontAware;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.tc.font.Font;

/**
 * This is the abstract base class for document writers producing PostScript
 * code. Here some utility methods of general nature are collected.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractPostscriptWriter
        implements
            DocumentWriter,
            Configurable,
            MultipleDocumentStream,
            ResourceAware,
            FontAware,
            ColorAware {

    /**
     * The field <tt>boxed</tt> contains the indicator whether the box-only
     * converter should be used.
     */
    private boolean boxed;

    /**
     * The field <tt>colorConverter</tt> contains the color converter as set
     * from the managing instance.
     */
    private ColorConverter colorConverter = null;

    /**
     * The field <tt>converter</tt> contains the cached PostScript converter.
     */
    private PsConverter converter = null;

    /**
     * The field <tt>extension</tt> contains the extension.
     */
    private String extension = "ps";

    /**
     * The field <tt>finder</tt> contains the resource finder as set from the
     * managing instance.
     */
    private ResourceFinder finder = null;

    /**
     * The field <tt>fontFactory</tt> contains the font factory.
     */
    private CoreFontFactory fontFactory;

    /**
     * The field <tt>parameter</tt> contains the map for parameters.
     */
    private Map<String, String> parameter = new HashMap<String, String>();

    /**
     * The field <tt>writerFactory</tt> contains the output stream factory.
     */
    private OutputStreamFactory writerFactory;

    /**
     * Creates a new object.
     */
    public AbstractPostscriptWriter() {

        super();
        parameter.put("Creator", "ExTeX.psWriter");
        parameter.put("Title", "<title>");
        parameter.put("PageOrder", "Ascend");
    }

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param config the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        String s = config.getAttribute("boxed");
        this.boxed = (s == null ? false : Boolean.valueOf(s).booleanValue());

        s = config.getAttribute("extension");
        if (s != null) {
            extension = s;
        }
    }

    /**
     * Create a PostScript converter.
     * 
     * @param headerManager the header manager
     * 
     * @return the new converter
     * 
     * @throws IOException in case of an IO error
     */
    protected PsConverter getConverter(HeaderManager headerManager)
            throws IOException {

        if (converter != null) {
            return converter;
        }

        if (boxed) {
            converter = new PsBoxConverter();
        } else {
            converter = new PsBasicConverter();
        }
        if (converter instanceof ColorAware) {
            ((ColorAware) converter).setColorConverter(colorConverter);
        }
        if (converter instanceof ResourceAware) {
            ((ResourceAware) converter).setResourceFinder(finder);
        }

        headerManager.reset();
        converter.init(headerManager);
        return converter;
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

        return extension;
    }

    /**
     * Getter for fontFactory.
     * 
     * @return the fontFactory
     */
    public CoreFontFactory getFontFactory() {

        return fontFactory;
    }

    /**
     * Getter for a named parameter.
     * 
     * @param name the name of the parameter
     * 
     * @return the value of the parameter or <code>null</code> if none exists
     */
    protected String getParameter(String name) {

        return parameter.get(name);
    }

    /**
     * Acquire a new output stream.
     * 
     * @param type the type for the reference to the configuration file
     * 
     * @return the new output stream
     * 
     * @throws DocumentWriterException in case of an error
     */
    protected OutputStream newOutputStream(String type)
            throws DocumentWriterException {

        return writerFactory.getOutputStream(null, type);
    }

    /**
     * Setter for the color converter.
     * 
     * @param converter the color converter
     * 
     * @see org.extex.color.ColorAware#setColorConverter(
     *      org.extex.color.ColorConverter)
     */
    public void setColorConverter(ColorConverter converter) {

        this.colorConverter = converter;
    }

    /**
     * Setter for extension.
     *
     * @param extension the extension to set
     */
    protected void setExtension(String extension) {
    
        this.extension = extension;
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

        this.writerFactory = factory;
    }

    /**
     * Setter for a named parameter. Parameters are a general mechanism to
     * influence the behavior of the document writer. Any parameter not known by
     * the document writer has to be ignored.
     * 
     * @param name the name of the parameter
     * @param value the value of the parameter
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {

        parameter.put(name, value);
    }

    /**
     * Setter for the resource finder.
     * 
     * @param resourceFinder the resource finder
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder resourceFinder) {

        this.finder = resourceFinder;
    }

    /**
     * Write a meta comment according to the Document Structuring Conventions.
     * 
     * @param stream the target stream to write to
     * @param name the name of the DSC comment
     * 
     * @throws IOException in case of an error during writing
     */
    protected void writeDsc(OutputStream stream, String name)
            throws IOException {

        stream.write('%');
        stream.write('%');
        stream.write(name.getBytes());
        stream.write('\n');
    }

    /**
     * Write a meta comment according to the Document Structuring Conventions.
     * 
     * @param stream the target stream to write to
     * @param name the name of the DSC comment
     * @param value the value of the DSC comment
     * 
     * @throws IOException in case of an error during writing
     */
    protected void writeDsc(OutputStream stream, String name, String value)
            throws IOException {

        stream.write('%');
        stream.write('%');
        stream.write(name.getBytes());
        stream.write(':');
        stream.write(' ');
        stream.write(value.getBytes());
        stream.write('\n');

    }

    
    /**
     * Write a meta comment according to the Document Structuring Conventions
     * containing the <tt>DocumentFonts</tt>.
     * 
     * @param stream the target stream to write to
     * @param fontManager the font manager to ask for the fonts
     * 
     * @throws IOException in case of an error during writing
     */
    protected void writeFonts(OutputStream stream, FontManager fontManager)
            throws IOException {

        stream.write("%%DocumentFonts:".getBytes());

        for (Font f : fontManager.listFonts()) {
            stream.write(' ');
            stream.write(f.getFontName().getBytes());
        }
        stream.write('\n');
    }

}
