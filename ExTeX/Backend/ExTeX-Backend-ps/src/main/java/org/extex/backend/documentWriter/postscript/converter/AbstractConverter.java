/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.postscript.converter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.backend.documentWriter.postscript.util.FontManager;
import org.extex.backend.documentWriter.postscript.util.HeaderManager;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.type.node.SpecialNode;

/**
 * This is the abstract base class for an PS converter.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractConverter implements PsConverter, ResourceAware {

    /**
     * The field <tt>headerManager</tt> contains the header manager.
     */
    private HeaderManager headerManager;

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field <tt>fontManager</tt> contains the font manager.
     */
    private FontManager fontManager;

    
    /**
     * Getter for fontManager.
     *
     * @return the fontManager
     */
    protected FontManager getFontManager() {
    
        return fontManager;
    }


    public AbstractConverter() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.postscript.converter.PsConverter#init(
     *      org.extex.backend.documentWriter.postscript.util.HeaderManager,
     *      org.extex.backend.documentWriter.postscript.util.FontManager)
     */
    public void init(HeaderManager headerMan, FontManager fontMan)
            throws IOException {

        headerManager = headerMan;
        fontManager = fontMan;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder resourceFinder) {

        this.finder = resourceFinder;
    }

    /**
     * Add some text from a resource to the header section.
     * 
     * @param name the name of the resource to add as header
     * 
     * @throws GeneralException in case of an error
     */
    private void specialHeader(String name) throws GeneralException {

        try {
            InputStream s = finder.findResource(name, "pro");
            if (s != null) {
                headerManager.add(s, name);
                s.close();
            } else {
                throw new DocumentWriterIOException(new FileNotFoundException());
            }
        } catch (ConfigurationException e) {
            throw new GeneralException(e);
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    /**
     * Find a PS resource and include its contents into the output stream.
     * 
     * @param out the target buffer
     * @param name the name of the resource
     * 
     * @throws GeneralException in case of an error
     */
    private void specialPsfile(PrintStream out, String name)
            throws GeneralException {

        try {
            InputStream s = finder.findResource(name, "ps");
            if (s != null) {
                int c;
                while ((c = s.read()) >= 0) {
                    out.append((char) c);
                }
                s.close();
            } else {
                throw new DocumentWriterIOException(new FileNotFoundException());
            }
        } catch (ConfigurationException e) {
            throw new GeneralException(e);
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    /**
     * Process a special node.
     * 
     * @param out the output stream
     * @param specialNode the node
     * 
     * @throws GeneralException in case of an error
     */
    protected void treatSpecial(PrintStream out, SpecialNode specialNode)
            throws GeneralException {

        String text = specialNode.getText();
        if (text == null || text.length() == 0) {
            return;
        }
        switch (text.charAt(0)) {
            case 'p':
                if (text.startsWith("ps:")) {
                    out.append(text.substring(3));
                } else if (text.startsWith("psfile=")) {
                    specialPsfile(out, text.substring(7));
                }
                break;
            case 'h':
                if (text.startsWith("header=")) {
                    specialHeader(text.substring(7));
                }
                break;
            case '"':
                out.append("gsave ");
                out.append(text.substring(1));
                out.append("grestore\n");
                break;
            case '!':
                try {
                    headerManager.add(text.substring(1), "!");
                } catch (IOException e) {
                    throw new GeneralException(e);
                }
                break;
            default:
                // ignored on purpose
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.postscript.converter.PsConverter#writeHeaders(
     *      PrintStream)
     */
    public void writeHeaders(PrintStream stream) throws IOException {

        headerManager.write(stream);
    }

}
