/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter;

import java.io.IOException;
import java.io.InputStream;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.font.CoreFontFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.ErrorLimitException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.loader.LoaderException;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This interface represents the outside view on an interpreter. It contains
 * everything needed to set it up and make it run.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [1029]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface Interpreter extends TokenSource {

    /**
     * Getter for the context
     * 
     * @return the context
     * 
     * @see #setContext(Context)
     */
    Context getContext();

    /**
     * Getter for the error handler. The error handler might not be set. In this
     * case <code>null</code> is returned.
     * 
     * @return the error handler currently registered
     * 
     * @see #setErrorHandler(ErrorHandler)
     */
    ErrorHandler getErrorHandler();

    /**
     * Getter for the interaction mode.
     * 
     * @return the interaction mode
     * 
     * @see #setInteraction(Interaction)
     */
    Interaction getInteraction();

    /**
     * Getter for the typesetter.
     * 
     * @return the typesetter
     * 
     * @see #setTypesetter(Typesetter)
     */
    Typesetter getTypesetter();

    /**
     * Load the format from an external source.
     * 
     * @param stream stream to read from
     * @param fmt the name of the format to be loaded
     * @param contextType name of the context from the configuration to use
     * @param languageType name of the language from the configuration to use
     * 
     * @throws IOException in case of an IO error
     * @throws LoaderException in case of an error during loading
     */
    void loadFormat(InputStream stream, String fmt, String contextType,
            String languageType) throws LoaderException, IOException;

    /**
     * Load a unit.
     * 
     * @param name the name of the configuration
     * @param finder the resource finder
     * 
     * @throws ConfigurationException in case of an error
     */
    void loadUnit(String name, ResourceFinder finder)
            throws ConfigurationException;

    /**
     * Process the current token streams by repeatedly reading a single token
     * and processing it until no token is left. The visitor pattern is used to
     * branch to the appropriate method for processing a single token.
     * 
     * @throws ConfigurationException in case of a configuration error
     * @throws ErrorLimitException in case that the error limit has been reached
     * @throws HelpingException in case of another error
     * @throws TypesetterException in case of an error in the typesetter
     * 
     * @see #run(TokenStream)
     */
    void run()
            throws ConfigurationException,
                ErrorLimitException,
                HelpingException,
                TypesetterException;

    /**
     * Add a token stream and start processing it.
     * 
     * @param stream the input stream to consider
     * @throws ConfigurationException in case of a configuration error
     * @throws ErrorLimitException in case that the error limit has been reached
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * 
     * @see #run()
     */
    void run(TokenStream stream)
            throws ConfigurationException,
                ErrorLimitException,
                HelpingException,
                TypesetterException;

    /**
     * Setter for the context. Use with care!
     * 
     * @return the old context
     * 
     * @param context the interpreter context
     * 
     * @see #getContext()
     */
    Context setContext(Context context);

    /**
     * Setter for the error handler. The value of <code>null</code> can be
     * used to delete the error handler currently set.
     * 
     * @param handler the new error handler
     * 
     * @see #getErrorHandler()
     */
    void setErrorHandler(ErrorHandler handler);

    /**
     * Setter for the font factory
     * 
     * @param fontFactory the new font factory
     */
    void setFontFactory(CoreFontFactory fontFactory);

    /**
     * Setter for the interaction mode.
     * 
     * @param interaction the interaction mode
     * 
     * @throws GeneralException in case of an error
     * 
     * @see #getInteraction()
     */
    void setInteraction(Interaction interaction) throws GeneralException;

    /**
     * Setter for the job name.
     * 
     * @param jobname the new value for the job name
     * 
     * @throws GeneralException in case of an error
     */
    void setJobname(String jobname) throws GeneralException;

    /**
     * Setter for the token stream factory. During the processing of the input
     * several occasions might come up where new token streams are needed. In
     * this case the factory can be used to acquire them.
     * 
     * @param factory the token stream factory
     * 
     * @throws ConfigurationException in case of en error in the configuration
     */
    void setTokenStreamFactory(TokenStreamFactory factory)
            throws ConfigurationException;

    /**
     * Setter for the typesetter.
     * 
     * @param typesetter the new typesetter
     * 
     * @see #getTypesetter()
     */
    void setTypesetter(Typesetter typesetter);

}
