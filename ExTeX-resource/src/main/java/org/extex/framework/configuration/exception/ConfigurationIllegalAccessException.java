/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.exception;

/**
 * This exception is thrown when a dynamically loaded class has signaled an
 * illegal access.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationIllegalAccessException extends ConfigurationException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    private static final long serialVersionUID = 2010L;

    /**
     * Creates a new object.
     * 
     * @param cause the next {@link Throwable} in the list
     */
    public ConfigurationIllegalAccessException(Throwable cause) {

        super(null, cause);
    }

    /**
     * Getter for the text prefix of this ConfigException. The text is taken
     * from the resource bundle <tt>ConfigurationEception</tt> under the key
     * <tt>ConfigurationIllegalAccessException.Text</tt>. The argument {0} is
     * replaced by the message of the embedded cause as passed to the
     * constructor.
     * 
     * @return the text
     */
    @Override
    protected String getText() {

        Throwable cause = getCause();
        String localizedMessage =
                (cause == null ? "" : cause.getLocalizedMessage());
        return getLocalizer().format(
            "ConfigurationIllegalAccessException.Text", localizedMessage);
    }

}
