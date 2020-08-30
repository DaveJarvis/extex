/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.cli.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * This exception signals an option needs a numeric argument and something else
 * is found.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NonNumericArgumentCliException extends CliException {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field {@code value} contains the value.
     */
    private final String value;

    /**
     * Creates a new object.
     * 
     * @param optionName the name of the option
     * @param optionValue the actual value
     */
    public NonNumericArgumentCliException(String optionName, String optionValue) {

        super(optionName);
        this.value = optionValue;
    }

@Override
    public String getLocalizedMessage() {

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        return MessageFormat.format(bundle.getString("message"), getMessage(),
            value);
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public String getValue() {

        return value;
    }

}
