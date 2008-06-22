/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token;

import java.io.IOException;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.io.Locator;

/**
 * A <code>Token</code> is the basic entity of which programs are made up.
 * Programs also handle <code>Token</code>s as their way to store
 * information.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public interface Token extends Code {

    /**
     * This method returns the expansion of a Token. The expansion is the
     * (recursive) concatenation of all constituents where macros are expanded
     * to their respective values.
     * 
     * @param processor the processor context
     * 
     * @return the expanded value of this Token
     */
    String expand(Processor processor);

    /**
     * Getter for the {@link org.extex.exbib.core.io.Locator Locator}. The
     * locator is a means to get information about where the token came from
     * originally. If no such information is available then <code>null</code>
     * is returned.
     * 
     * @return the locator or <code>null</code>
     */
    Locator getLocator();

    /**
     * Getter for the value. The value stores the real information in the Token.
     * Not all subtypes make use of the value but most of them do.
     * 
     * @return the value
     */
    String getValue();

    /**
     * Check whether the value has been set to <code>null</code> initially.
     * Usually the value <code>null</code> is treated as empty string. This
     * method is the only way to distinguish these two cases.
     * 
     * @return <code>true</code> iff the value has been set to
     *         <code>null</code>
     */
    boolean isNull();

    /**
     * Return a printable representation of this object.
     * 
     * @return the printable representation
     */
    String toString();

    /**
     * This is the entry point for a {@link TokenVisitor TokenVisitor} to start
     * visiting.
     * 
     * @param visitor the visitor
     * 
     * @throws IOException in case of an I/O error
     */
    void visit(TokenVisitor visitor) throws IOException;

}
