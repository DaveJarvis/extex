/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.main.tex;

import java.io.IOException;
import java.util.logging.Logger;

import org.extex.core.Locator;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizable;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.ErrorHandler;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.interaction.InteractionVisitor;
import org.extex.main.errorHandler.editHandler.EditHandler;
import org.extex.scanner.type.token.Token;

/**
 * This is a simple implementation of the error handler interacting with the
 * user on the command line like <logo>TeX</logo> does.
 * <p>
 * The {@link HelpingException HelpingException} is capable of carrying a name
 * and two arguments for the error message. This class can be queried to provide
 * additional help concerning the error at hand. See
 * {@link HelpingException HelpingException} for details.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ErrorHandlerImpl implements ErrorHandler, LogEnabled, Localizable {

    /**
     * The field <tt>ENABLE_DEBUG</tt> contains the indicator for turning on
     * the debugging emulation.
     */
    private static final boolean ENABLE_DEBUG = false;

    /**
     * The constant <tt>NL</tt> contains the String with the newline
     * character, since it is needed several times.
     */
    protected static final String NL = "\n";

    /**
     * The field <tt>editHandler</tt> contains the handler to be invoked upon
     * a request to edit a file.
     */
    private EditHandler editHandler = null;

    /**
     * The field <tt>iv</tt> contains the interaction visitor with the
     * different behavior for the different interaction modes.
     */
    private InteractionVisitor<TokenSource, Context, GeneralException> iv =
            new InteractionVisitor<TokenSource, Context, GeneralException>() {

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.interaction.InteractionVisitor#visitBatchmode(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public boolean visitBatchmode(TokenSource source,
                        Context context, GeneralException ex)
                        throws GeneralException {

                    return true;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.interaction.InteractionVisitor#visitErrorstopmode(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public boolean visitErrorstopmode(TokenSource source,
                        Context context, GeneralException ex)
                        throws GeneralException {

                    if (ex.getCause() instanceof ConfigurationException) {
                        showErrorLine(logger, ex.getCause()
                            .getLocalizedMessage(), source.getLocator());
                    } else {
                        showErrorLine(logger, ex.getLocalizedMessage(), source
                            .getLocator());
                    }

                    try {
                        boolean firstHelp = true;

                        for (;;) {
                            String line =
                                    promptAndReadLine(localizer
                                        .format("ErrorHandler.Prompt"));
                            logger.config(line);

                            if (line.equals("") || line.equals(NL)) {
                                return true;
                            }
                            switch (line.charAt(0)) {
                                case '0':
                                case '9':
                                case '8':
                                case '7':
                                case '6':
                                case '5':
                                case '4':
                                case '3':
                                case '2':
                                case '1':
                                    int count = line.charAt(0) - '0';
                                    if (line.length() > 1
                                            && Character
                                                .isDigit(line.charAt(1))) {
                                        count =
                                                count * 10 + line.charAt(1)
                                                        - '0';
                                    }
                                    while (count-- > 0) {
                                        source.getToken(context);
                                    }
                                    firstHelp = false;
                                    break;
                                case 'd':
                                case 'D':
                                    if (ENABLE_DEBUG) {
                                        // TTP[84] TTP[1338]
                                        handleDebug();
                                    } else {
                                        logger.severe(localizer
                                            .format("ErrorHandler.help")
                                                + NL);
                                    }
                                    break;
                                case 'e':
                                case 'E':
                                    // TTP[84]
                                    if (editHandler != null
                                            && editHandler.edit(localizer, //
                                                source.getLocator())) {

                                        context
                                            .setInteraction(Interaction.SCROLLMODE);
                                        logger.info(localizer
                                            .format("ErrorHandler.scrollmode")
                                                + NL);
                                    }
                                    return true;
                                case 'i':
                                case 'I':
                                    source.addStream(source
                                        .getTokenStreamFactory().newInstance(
                                            line.substring(1)));
                                    break;
                                case 'h':
                                case 'H':

                                    String help;

                                    if (firstHelp) {
                                        help = ex.getHelp();
                                        if (help == null) {
                                            help = localizer.format(//
                                                "ErrorHandler.noHelp");
                                        }
                                    } else {
                                        help = localizer.format(//
                                            "ErrorHandler.noMoreHelp");
                                    }

                                    firstHelp = false;
                                    logger.severe(help + NL);
                                    break;
                                case 'q':
                                case 'Q':
                                    context
                                        .setInteraction(Interaction.BATCHMODE);
                                    logger.info(localizer
                                        .format("ErrorHandler.batchmode")
                                            + NL);
                                    return true;
                                case 'r':
                                case 'R':
                                    context
                                        .setInteraction(Interaction.NONSTOPMODE);
                                    logger.info(localizer
                                        .format("ErrorHandler.nonstopmode")
                                            + NL);
                                    return true;
                                case 's':
                                case 'S':
                                    context
                                        .setInteraction(Interaction.SCROLLMODE);
                                    logger.info(localizer
                                        .format("ErrorHandler.scrollmode")
                                            + NL);
                                    return true;
                                case 'x':
                                case 'X':
                                    return false;
                                default:
                                    logger.severe(localizer
                                        .format("ErrorHandler.help")
                                            + NL);
                            }
                        }

                    } catch (ConfigurationException e) {
                        throw new GeneralException(e);
                    }
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.interaction.InteractionVisitor#visitNonstopmode(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public boolean visitNonstopmode(TokenSource source,
                        Context context, GeneralException ex)
                        throws GeneralException {

                    return true;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.interaction.InteractionVisitor#visitScrollmode(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public boolean visitScrollmode(TokenSource source,
                        Context context, GeneralException ex)
                        throws GeneralException {

                    return false;
                }

            };

    /**
     * The field <tt>localizer</tt> contains the localizer.
     */
    private Localizer localizer;

    /**
     * The field <tt>logger</tt> contains the logger to write a protocol of
     * the interaction to. Note that the error has already been logged when this
     * handler is invoked. Thus only additional logging output should be
     * produced in this class.
     */
    private Logger logger = null;

    /**
     * Creates a new object.
     */
    public ErrorHandlerImpl() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.i18n.Localizable#enableLocalization(
     *      org.extex.framework.i18n.Localizer)
     */
    public void enableLocalization(Localizer theLocalizer) {

        this.localizer = theLocalizer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * Special treatment of the debug case. This method is meant to be redefined
     * to provide additional functionality.
     * 
     * @throws HelpingException in case of EOF on terminal
     * 
     * @see "TTP[1338]"
     */
    protected void handleDebug() throws HelpingException {

        for (;;) {
            String line =
                    promptAndReadLine(localizer
                        .format("ErrorHandler.DebugPrompt"));
            logger.config(line);

            if (line.startsWith("-")) {
                return;
                // TODO gene: handleDebug unimplemented
                /*
                 * } else if ("1".equals(line)) { } else if ("2".equals(line)) { }
                 * else if ("3".equals(line)) { } else if ("4".equals(line)) { }
                 * else if ("5".equals(line)) { } else if ("6".equals(line)) { }
                 * else if ("7".equals(line)) { } else if ("8".equals(line)) { }
                 * else if ("9".equals(line)) { } else if ("10".equals(line)) { }
                 * else if ("11".equals(line)) { } else if ("12".equals(line)) { }
                 * else if ("13".equals(line)) { } else if ("14".equals(line)) { }
                 * else if ("15".equals(line)) { } else if ("16".equals(line)) {
                 */
            }

            logger.config(localizer.format("ErrorHandler.DebugElsePrompt"));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.ErrorHandler#handleError(org.extex.core.exception.GeneralException,
     *      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.interpreter.context.Context)
     */
    public boolean handleError(GeneralException exception, Token t,
            TokenSource source, Context context) throws HelpingException {

        try {
            return context.getInteraction().visit(iv, source, context,
                exception);
        } catch (HelpingException e) {
            throw e;
        } catch (GeneralException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * Read a line of characters from the standard input stream after a prompt
     * has been shown. Leading spaces are ignored. At end of file an exception
     * is thrown.
     * 
     * @param prompt the prompt to display
     * 
     * @return the line read or <code>null</code> to signal EOF
     * 
     * @throws HelpingException in case of EOF on terminal
     */
    protected String promptAndReadLine(String prompt) throws HelpingException {

        logger.severe(prompt);

        StringBuffer sb = new StringBuffer();

        try {
            for (int c = System.in.read(); c > 0; c = System.in.read()) {
                if (c == '\n') {
                    sb.append((char) c);
                    return sb.toString();
                } else if (c == '\r') {
                    // ignored
                } else if (c != ' ' || sb.length() > 0) {
                    sb.append((char) c);
                }
            }
        } catch (IOException e) {
            throw new HelpingException(localizer, "TTP.EOFonTerm");
        }

        if (sb.length() > 0) {
            return sb.toString();
        }

        throw new HelpingException(localizer, "TTP.EOFonTerm");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.ErrorHandler#setEditHandler(
     *      org.extex.main.errorHandler.editHandler.EditHandler)
     */
    public void setEditHandler(EditHandler editHandler) {

        this.editHandler = editHandler;
    }

    /**
     * This method is invoked to present the current line causing the error.
     * 
     * @param logger the logger to use for output
     * @param message the error message
     * @param locator the locator for the error position
     */
    protected void showErrorLine(Logger logger, String message, Locator locator) {

        if (locator == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();

        for (Locator loc = locator; loc != null; loc = loc.getCause()) {

            String file = loc.getResourceName();
            int lineNumber = loc.getLineNumber();
            String line = loc.getLine();
            sb.append(NL);
            sb.append(NL);
            sb.append(file == null ? "" : file);
            sb.append(':');
            sb.append(lineNumber >= 0 ? Integer.toString(lineNumber) : "");
            sb.append(':');
            if (loc.getCause() == null) {
                sb.append(' ');
                sb.append(message);
            }
            sb.append(NL);
            sb.append(NL);
            if (line != null) {
                sb.append(NL);
                sb.append(line);
                sb.append(NL);
                for (int i = loc.getLinePointer(); i > 0; i--) {
                    sb.append('_');
                }
                sb.append('^');
                sb.append(NL);
            }
        }

        logger.severe(sb.toString());
    }

}
