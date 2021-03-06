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

package org.extex.main.tex;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class implements the TeX version of a query file handler. It presents a prompt to
 * the user (**) and reads a file name from the console.
 * <p>
 * If the file name starts with a backslash then it is prepended to the code to
 * be executed. and no file name is returned.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class QueryFileHandlerTeXImpl implements QueryFileHandler {


    public QueryFileHandlerTeXImpl() {

    }

    /**
     * Read a line of characters from the standard input stream after a prompt
     * has been shown. Leading spaces are ignored. At end of file an exception
     * is thrown.
     * 
     * @param localizer the localizer
     * @param logger the logger
     * @param prompt the prompt to present before the input is allowed
     * 
     * @return the line read or {@code null} to signal EOF
     * 
     * @throws IOException in case of an error during IO. This is rather
     *         unlikely
     * @throws HelpingException in case of EOF on terminal
     */
    protected String promptAndReadLine(Localizer localizer, Logger logger,
            String prompt) throws IOException, HelpingException {

        logger.severe(localizer.format(prompt));

        StringBuilder sb = new StringBuilder();

        for (int c = System.in.read(); c > 0; c = System.in.read()) {
            if (c == '\n') {
                return sb.toString();
            } else if (c != '\r' && (c != ' ' || sb.length() > 0)) {
                sb.append((char) c);
            }
        }

        if (sb.length() > 0) {
            return sb.toString();
        }

        throw new HelpingException(localizer, "TTP.EOFonTerm");
    }

    /**
     * Query the name of a input file.
     * 
     * @param logger the logger stream for output messages
     * @param properties the properties
     * 
     * @return the file name or {@code null} if none has been given
     * 
     * @see org.extex.main.tex.QueryFileHandler#query(java.util.logging.Logger,
     *      java.util.Properties)
     */
    public String query(Logger logger, Properties properties) {

        Localizer localizer =
                LocalizerFactory.getLocalizer(QueryFileHandlerTeXImpl.class);
        String file;
        try {
            file = promptAndReadLine(localizer, logger, "TTP.PromptFile");

            while (file == null || "".equals(file)) {
                file = promptAndReadLine(localizer, logger, "TTP.PromptFile2");
            }

        } catch (HelpingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        if (file.startsWith("\\")) {
            String code = properties.getProperty("extex.code");
            code = file + " " + code;
            properties.setProperty("extex.code", code);
            return null;
        }
        return file;
    }

}
