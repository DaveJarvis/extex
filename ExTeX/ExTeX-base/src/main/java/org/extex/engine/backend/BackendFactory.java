/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.engine.backend;

import java.util.Properties;

import org.extex.backend.BackendDriver;
import org.extex.backend.documentWriter.DocumentWriterFactory;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.exception.BackendException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontAware;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * This class provides a factory for the back-end.
 * 
 * 
 * <p>
 * The class to be instantiated can implements one or more interfaces which
 * trigger special actions:
 * </p>
 * <dl>
 * <dt>{@link org.extex.framework.configuration.Configurable Configurable}</dt>
 * <dd>If this interface is implemented then a
 * {@link org.extex.framework.configuration.Configuration Configuration} is
 * passed in with the interface method.</dd>
 * <dt>{@link org.extex.framework.logger.LogEnabled LogEnabled}</dt>
 * <dd>If this interface is implemented then a {@link java.util.logging.Logger
 * Logger} is passed in with the interface method.</dd>
 * <dt>{@link org.extex.resource.ResourceAware ResourceAware}</dt>
 * <dd>If this interface is implemented then a
 * {@link org.extex.resource.ResourceFinder ResourceFinder} is passed in with
 * the interface method.</dd>
 * <dt>{@link org.extex.resource.PropertyAware PropertyAware}</dt>
 * <dd>If this interface is implemented then a {@link java.util.Properties
 * Properties} instance is passed in with the interface method.</dd>
 * <dt>{@link org.extex.color.ColorAware ColorAware}</dt>
 * <dd>If this interface is implemented then a
 * {@link org.extex.color.ColorConverter ColorConverter} instance is passed in
 * with the interface method.</dd>
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class BackendFactory extends AbstractFactory<BackendDriver> {

    /**
     * The field <tt>options</tt> contains the document writer options.
     */
    private DocumentWriterOptions options;

    /**
     * Creates a new object.
     */
    public BackendFactory() {

    }

    /**
     * Acquire an instance of a back-end driver.
     * 
     * @param type the type of the document writer
     * @param outFactory the output stream factory
     * @param finder the resource finder
     * @param properties the properties
     * @param creator the creator string
     * @param fontFactory the font factory
     * @param colorConverter the color converter
     * 
     * @return the new instance
     * 
     * @throws DocumentWriterException in case of an error
     * @throws ConfigurationException in case of an error in the configuration
     */
    public BackendDriver newInstance(String type,
            OutputStreamFactory outFactory, ResourceFinder finder,
            Properties properties, String creator, CoreFontFactory fontFactory,
            ColorConverter colorConverter) throws DocumentWriterException {

        BackendDriver backend = createInstance(BackendDriver.class);
        DocumentWriterFactory factory =
                new DocumentWriterFactory(getConfiguration().getConfiguration(
                    "DocumentWriter"), getLogger());
        factory.setResourceFinder(finder);
        factory.setOptions(options);

        try {
            backend.setDocumentWriterType(type);
        } catch (BackendException e) {
            throw new DocumentWriterException(e); // this should not happen
        }

        if (backend instanceof MultipleDocumentStream) {
            ((MultipleDocumentStream) backend)
                .setOutputStreamFactory(outFactory);
        }

        backend.setDocumentWriterFactory(factory);
        if (backend instanceof PropertyAware) {
            ((PropertyAware) backend).setProperties(properties);
        }
        if (backend instanceof ColorAware) {
            ((ColorAware) backend).setColorConverter(colorConverter);
        }
        if (backend instanceof ResourceAware) {
            ((ResourceAware) backend).setResourceFinder(finder);
        }
        if (backend instanceof FontAware) {
            ((FontAware) backend).setFontFactory(fontFactory);
        }
        backend.setParameter("Creator", creator);

        for (Configuration p : getConfiguration()) {
            String name = p.getAttribute("name");
            String s = p.getAttribute("value");
            if (s != null) {
                backend.setParameter(name, s);
            } else {
                s = p.getAttribute("property");
                if (s != null) {
                    backend.setParameter(name, properties.getProperty(s));
                } else {
                    throw new ConfigurationMissingAttributeException("value", p);
                }
            }
        }

        return backend;
    }

    /**
     * Setter for options.
     * 
     * @param options the options to set
     */
    public void setOptions(DocumentWriterOptions options) {

        this.options = options;
    }

}
