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
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class Macro extends Command {

    /**
     * The field <tt>arg</tt> contains the ...
     */
    private final VarMacro macro;

    /**
     * Creates a new object.
     * 
     * @param offset
     * @param macro
     */
    public Macro(int offset, VarMacro macro) {

        super(offset, "Macro", BstClass.COMMAND_MACRO);
        this.macro = macro;
    }

    /**
     * Getter for macro.
     * 
     * @return the macro
     */
    public VarMacro getMacro() {

        return macro;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return macro.getName();
    }

}
