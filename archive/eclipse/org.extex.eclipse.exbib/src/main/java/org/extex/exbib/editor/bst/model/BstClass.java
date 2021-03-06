/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.editor.bst.model;

/**
 * This enumeration classifies the nodes stored in the model.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public enum BstClass {
    BUILTIN,
    COMMAND_ENTRY,
    COMMAND_EXECUTE,
    COMMAND_FUNCTION,
    COMMAND_INTEGERS,
    COMMAND_ITERATE,
    COMMAND_MACRO,
    COMMAND_READ,
    COMMAND_REVERSE,
    COMMAND_STRINGS,
    COMMAND_SORT,
    FIELD,
    FUNCTION,
    MACRO,
    GLOBAL_STRING,
    GLOBAL_NUMBER,
    LOCAL_STRING,
    LOCAL_NUMBER,
    UNKNOWN
}
