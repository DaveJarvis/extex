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
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Interpreter;

/**
 * This Reader gets the characters from {@code System.in} but presents a
 * prompt before each line of input.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TeXInputReader extends Reader {

    /**
     * The field {@code logger} contains the logger to write output to.
     */
    private final Logger logger;

    /**
     * The field {@code prompt} contains the cached prompt to be shown before
     * each line of input.
     */
    private final String prompt;

    /**
     * The field {@code reader} contains the reader to do the real job.
     */
    private final Reader reader;

    /**
     * The field {@code showPrompt} contains the indicator that the prompt
     * needs to be shown next time.
     */
    private boolean showPrompt = true;

    /**
     * Creates a new object.
     * 
     * @param logger the logger to write to
     * @param charset the character set to use
     * @param interpreter the interpreter
     * 
     * @throws UnsupportedEncodingException in case that the encoding is not
     *         known
     */
    public TeXInputReader(Logger logger, String charset, Interpreter interpreter)
            throws UnsupportedEncodingException {

        this.logger = logger;
        reader = new InputStreamReader(System.in, charset);
        Localizer localizer =
                LocalizerFactory.getLocalizer(TeXInputReader.class);
        prompt = localizer.format("TTP.PromptInput");
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {

        reader.close();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#markSupported()
     */
    @Override
    public boolean markSupported() {

        return this.reader.markSupported();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#read()
     */
    @Override
    public int read() throws IOException {

        return this.reader.read();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#read(char[])
     */
    @Override
    public int read(char[] buffer) throws IOException {

        return this.reader.read(buffer);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] buffer, int startIndex, int len) throws IOException {

        /*
         * // if (interpreter.getContext().getInteraction().getIndex() !=
         * Interaction.ERRORSTOPMODE // .getIndex()) { // return -1; // }
         * 
         */
        if (showPrompt) {
            logger.severe(prompt);
            showPrompt = false;
        }
        int ret = reader.read(buffer, startIndex, len);
        for (int i = 0; i < ret; i++) {
            char c = buffer[startIndex + i];
            logger.fine(Character.toString(c));
            if (c == '\n') {
                showPrompt = true;
            }
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#ready()
     */
    @Override
    public boolean ready() throws IOException {

        return this.reader.ready();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#reset()
     */
    @Override
    public void reset() throws IOException {

        this.reader.reset();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.Reader#skip(long)
     */
    @Override
    public long skip(long n) throws IOException {

        return this.reader.skip(n);
    }

}
