/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.maven.latex;

import org.apache.maven.plugin.logging.Log;

/**
 * This class is a test log which records the logging output in a buffer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TLog implements Log {

    /**
     * The field <tt>buffer</tt> contains the buffer.
     */
    private StringBuilder buffer;

    /**
     * Creates a new object.
     * 
     * @param buffer the buffer
     */
    public TLog(StringBuilder buffer) {

        this.buffer = buffer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#debug(java.lang.CharSequence)
     */
    public void debug(CharSequence content) {

        buffer.append("[debug] ");
        buffer.append(content);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#debug(java.lang.CharSequence,
     *      java.lang.Throwable)
     */
    public void debug(CharSequence content, Throwable error) {

        buffer.append("[debug] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#debug(java.lang.Throwable)
     */
    public void debug(Throwable error) {

        buffer.append("[debug] ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#error(java.lang.CharSequence)
     */
    public void error(CharSequence content) {

        buffer.append("[error] ");
        buffer.append(content);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#error(java.lang.CharSequence,
     *      java.lang.Throwable)
     */
    public void error(CharSequence content, Throwable error) {

        buffer.append("[error] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#error(java.lang.Throwable)
     */
    public void error(Throwable error) {

        buffer.append("[error] ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#info(java.lang.CharSequence)
     */
    public void info(CharSequence content) {

        buffer.append("[info] ");
        buffer.append(content);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#info(java.lang.CharSequence,
     *      java.lang.Throwable)
     */
    public void info(CharSequence content, Throwable error) {

        buffer.append("[info] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#info(java.lang.Throwable)
     */
    public void info(Throwable error) {

        buffer.append("[info] ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#isDebugEnabled()
     */
    public boolean isDebugEnabled() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#isErrorEnabled()
     */
    public boolean isErrorEnabled() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#isInfoEnabled()
     */
    public boolean isInfoEnabled() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#isWarnEnabled()
     */
    public boolean isWarnEnabled() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#warn(java.lang.CharSequence)
     */
    public void warn(CharSequence content) {

        buffer.append("[warning] ");
        buffer.append(content);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#warn(java.lang.CharSequence,
     *      java.lang.Throwable)
     */
    public void warn(CharSequence content, Throwable error) {

        buffer.append("[warning] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.logging.Log#warn(java.lang.Throwable)
     */
    public void warn(Throwable error) {

        buffer.append("[warning] ");
        buffer.append(error.toString());
    }

}
