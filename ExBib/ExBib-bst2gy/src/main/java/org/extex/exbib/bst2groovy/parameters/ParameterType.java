/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.parameters;

/**
 * This enumeration contains the legal values for parameters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public enum ParameterType {

    /**
     * The field {@code OPTIMIZE} contains the indicator for the boolean
     * parameter to turn on the optimization.
     */
    OPTIMIZE,

    /**
     * The field {@code STYLE_NAME} contains the name for the style name.
     */
    STYLE_NAME,

    /**
     * The field {@code TAB_SIZE} contains name for the tab size.
     */
    TAB_SIZE

}
