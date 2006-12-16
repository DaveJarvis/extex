/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
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

package org.extex.backend.documentWriter;

import java.util.logging.Logger;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.util.framework.AbstractFactory;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.configuration.exception.ConfigurationMissingAttributeException;

/**
 * This is the factory to provide an instance of a document writer.
 * 
 * 
 * <p>
 * The class to be instantiated can implements one or more interfaces which
 * trigger special actions:
 * </p>
 * <dl>
 * <dt>{@link org.extex.util.framework.configuration.Configurable Configurable}</dt>
 * <dd> If this interface is implemented then a
 * {@link org.extex.util.framework.configuration.Configuration Configuration} is
 * passed in with the interface method. </dd>
 * <dt>{@link org.extex.util.framework.logger.LogEnabled LogEnabled}</dt>
 * <dd> If this interface is implemented then a
 * {@link java.util.logging.Logger Logger} is passed in with the interface
 * method. </dd>
 * <dt>{@link org.extex.util.framework.i18n.Localizable Localizable}</dt>
 * <dd> If this interface is implemented then a
 * {@link org.extex.util.framework.i18n.Localizer Localizer} is passed in with
 * the interface method. </dd>
 * <dt>{@link org.extex.backend.documentWriter.SingleDocumentStream SingleDocumentStream}</dt>
 * <dd> If this interface is implemented then a
 * {@link java.io.OutputStream OutputStream} is passed in with the interface
 * method. </dd>
 * <dt>{@link org.extex.backend.documentWriter.MultipleDocumentStream MultipleDocumentStream}</dt>
 * <dd> If this interface is implemented then a
 * {@link org.extex.backend.outputStream.OutputStreamFactory OutputStreamFactory}
 * is passed in with the interface method. </dd>
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DocumentWriterFactory extends AbstractFactory {

    /**
     * The field <tt>defaultType</tt> contains the default type from the
     * configuration.
     */
    private String defaultType;

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration to use for the factory
     * @param logger the logger
     * 
     * @throws ConfigurationException in case of an error
     */
    public DocumentWriterFactory(final Configuration configuration,
            final Logger logger) throws ConfigurationException {

        super();
        enableLogging(logger);
        configure(configuration);
        defaultType = configuration.getAttribute("default");
        if (defaultType == null) {
            throw new ConfigurationMissingAttributeException("default",
                    configuration);
        }
    }

    /**
     * Provide a new instance of a document writer. The new instance is
     * initiated with the sub-configuration describing it.
     * <p>
     * If the generated instance implements the interface SingleDocumentStream
     * then the method setOutputStream of this interface is invoked.
     * </p>
     * <p>
     * If the generated instance implements the interface MultipleDocumentStream
     * then the method setOutputStreamFactory of this interface is invoked.
     * </p>
     * 
     * @param type the type of the document writer
     * @param options the dynamic access to the readable part of the context
     * @param outFactory the factory for further output streams
     * 
     * @return the new instance
     * 
     * @throws DocumentWriterException in case of a problem
     * @throws ConfigurationException in case of a configuration problem
     */
    public DocumentWriter newInstance(final String type,
            final DocumentWriterOptions options,
            final OutputStreamFactory outFactory)
            throws DocumentWriterException, ConfigurationException {

        String outputType = type;
        if (outputType == null || "".equals(outputType)) {
            outputType = defaultType;
        }

        DocumentWriter documentWriter = (DocumentWriter) createInstance(
                                                                        outputType,
                                                                        DocumentWriter.class,
                                                                        DocumentWriterOptions.class,
                                                                        options);

        outFactory.setExtension(documentWriter.getExtension());

        if (documentWriter instanceof SingleDocumentStream) {
            ((SingleDocumentStream) documentWriter).setOutputStream(outFactory
                    .getOutputStream(null, null));
        }
        if (documentWriter instanceof MultipleDocumentStream) {
            ((MultipleDocumentStream) documentWriter)
                    .setOutputStreamFactory(outFactory);
        }
        return documentWriter;
    }

}
