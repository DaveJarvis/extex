/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.tex.main.inputHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.extex.interpreter.Interpreter;
import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.i18n.LocalizerFactory;

/**
 * This Reader gets the characters from <tt>System.in</tt> but presents a
 * prompt before each line of input.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4445 $
 */
public class TeXInputReader extends Reader {

    /**
     * The field <tt>logger</tt> contains the logger to write output to.
     */
    private Logger logger;

    /**
     * The field <tt>prompt</tt> contains the cached prompt to be shown before
     * each line of input.
     */
    private String prompt;

    /**
     * The field <tt>reader</tt> contains the reader to do the real job.
     */
    private Reader reader;

    /**
     * The field <tt>showPrompt</tt> contains the indicator that the prompt
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
     *  known
     */
    public TeXInputReader(final Logger logger, final String charset,
            final Interpreter interpreter) throws UnsupportedEncodingException {

        super();
        this.logger = logger;
        reader = new InputStreamReader(System.in, charset);
        Localizer localizer = LocalizerFactory
                .getLocalizer(TeXInputReader.class);
        prompt = localizer.format("TTP.PromptInput");
    }

    /**
     * Close the stream.  Once a stream has been closed, further read(),
     * ready(), mark(), or reset() invocations will throw an IOException.
     * Closing a previously-closed stream, however, has no effect.
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.io.Reader#close()
     */
    public void close() throws IOException {

        reader.close();
    }

    /**
     * Tell whether this stream supports the mark() operation. The default
     * implementation always returns false. Subclasses should override this
     * method.
     *
     * @return true if and only if this stream supports the mark operation.
     *
     * @see java.io.Reader#markSupported()
     */
    public boolean markSupported() {

        return this.reader.markSupported();
    }

    /**
     * Read a single character.  This method will block until a character is
     * available, an I/O error occurs, or the end of the stream is reached.
     *
     * @return     The character read, as an integer in the range 0 to 65535
     *             (<tt>0x00-0xffff</tt>), or -1 if the end of the stream has
     *             been reached
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.io.Reader#read()
     */
    public int read() throws IOException {

        return this.reader.read();
    }

    /**
     * Read characters into an array.  This method will block until some input
     * is available, an I/O error occurs, or the end of the stream is reached.
     *
     * @param       buffer  Destination buffer
     *
     * @return      The number of characters read, or -1
     *              if the end of the stream
     *              has been reached
     *
     * @exception   IOException  If an I/O error occurs
     *
     * @see java.io.Reader#read(char[])
     */
    public int read(final char[] buffer) throws IOException {

        return this.reader.read(buffer);
    }

    /**
     * Read characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     *
     * @param      buffer  Destination buffer
     * @param      startIndex   Offset at which to start storing characters
     * @param      len   Maximum number of characters to read
     *
     * @return     The number of characters read, or -1 if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.io.Reader#read(char[], int, int)
     */
    public int read(final char[] buffer, final int startIndex, final int len)
            throws IOException {

        /*
         //        if (interpreter.getContext().getInteraction().getIndex() != Interaction.ERRORSTOPMODE
         //                .getIndex()) {
         //            return -1;
         //        }
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
     * Tell whether this stream is ready to be read.
     *
     * @return True if the next read() is guaranteed not to block for input,
     * false otherwise.  Note that returning false does not guarantee that the
     * next read will block.
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.io.Reader#ready()
     */
    public boolean ready() throws IOException {

        return this.reader.ready();
    }

    /**
     * Reset the stream.  If the stream has been marked, then attempt to
     * reposition it at the mark.  If the stream has not been marked, then
     * attempt to reset it in some way appropriate to the particular stream,
     * for example by repositioning it to its starting point.  Not all
     * character-input streams support the reset() operation, and some support
     * reset() without supporting mark().
     *
     * @exception  IOException  If the stream has not been marked,
     *                          or if the mark has been invalidated,
     *                          or if the stream does not support reset(),
     *                          or if some other I/O error occurs
     *
     * @see java.io.Reader#reset()
     */
    public void reset() throws IOException {

        this.reader.reset();
    }

    /**
     * Skip characters.  This method will block until some characters are
     * available, an I/O error occurs, or the end of the stream is reached.
     *
     * @param  n  The number of characters to skip
     *
     * @return    The number of characters actually skipped
     *
     * @exception  IllegalArgumentException  If <code>n</code> is negative.
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.io.Reader#skip(long)
     */
    public long skip(final long n) throws IOException {

        return this.reader.skip(n);
    }

}
