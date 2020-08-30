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

package org.extex.builder.maven;

import org.apache.maven.plugin.logging.Log;

/**
 * This class is a test log which records the logging output in a buffer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TLog implements Log {

    /**
     * The field {@code buffer} contains the buffer.
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

public void debug(CharSequence content) {

        buffer.append("[debug] ");
        buffer.append(content);
    }

    /**
*      java.lang.Throwable)
     */
    public void debug(CharSequence content, Throwable error) {

        buffer.append("[debug] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public void debug(Throwable error) {

        buffer.append("[debug] ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public void error(CharSequence content) {

        buffer.append("[error] ");
        buffer.append(content);
        buffer.append('\n');
    }

    /**
*      java.lang.Throwable)
     */
    public void error(CharSequence content, Throwable error) {

        buffer.append("[error] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public void error(Throwable error) {

        buffer.append("[error] ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public void info(CharSequence content) {

        buffer.append("[info] ");
        buffer.append(content);
        buffer.append('\n');
    }

    /**
*      java.lang.Throwable)
     */
    public void info(CharSequence content, Throwable error) {

        buffer.append("[info] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public void info(Throwable error) {

        buffer.append("[info] ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public boolean isDebugEnabled() {

        return true;
    }

public boolean isErrorEnabled() {

        return true;
    }

public boolean isInfoEnabled() {

        return true;
    }

public boolean isWarnEnabled() {

        return true;
    }

public void warn(CharSequence content) {

        buffer.append("[warning] ");
        buffer.append(content);
        buffer.append('\n');
    }

    /**
*      java.lang.Throwable)
     */
    public void warn(CharSequence content, Throwable error) {

        buffer.append("[warning] ");
        buffer.append(content);
        buffer.append(" ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

public void warn(Throwable error) {

        buffer.append("[warning] ");
        buffer.append(error.toString());
        buffer.append('\n');
    }

}
