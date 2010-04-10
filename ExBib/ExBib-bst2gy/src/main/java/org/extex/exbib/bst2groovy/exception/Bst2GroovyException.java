/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.exception;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is a base exception for Bst2Groovy.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Bst2GroovyException extends RuntimeException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field <tt>tag</tt> contains the tag for the resource bundle.
     */
    private String tag;

    /**
     * The field <tt>arg</tt> contains the additional argument.
     */
    private String arg = "";

    /**
     * Creates a new object.
     * 
     * @param tag the tag for the localizer
     * @param message the message
     */
    public Bst2GroovyException(String tag, String message) {

        super(message);
        this.tag = tag;
    }

    /**
     * Creates a new object.
     * 
     * @param tag the tag for the localizer
     * @param message the message
     * @param arg the additional argument
     */
    public Bst2GroovyException(String tag, String message, String arg) {

        super(message);
        this.tag = tag;
        this.arg = arg;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.exception.ConfigurationException#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        Throwable cause = getCause();

        return getLocalizer().format(tag, super.getMessage(),
            cause != null ? cause.toString() : "", arg);
    }

    /**
     * Acquire the localizer for the current class.
     * 
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(getClass());
    }

}
