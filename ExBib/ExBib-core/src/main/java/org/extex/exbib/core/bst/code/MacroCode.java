/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class is a container for interpreted macro
 * {@link org.extex.exbib.core.bst.code.Code Code}. The actual action for
 * execution is attached to a token embedded in this class.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MacroCode extends AbstractCode {

    /**
     * The field {@code token } contains the value for the macro code.
     */
    private Token token = null;

    /**
     * Creates a new object.
     * 
     * @param name the name in the processor context
     * @param token the value
     */
    public MacroCode(String name, Token token) {

        super(name);
        this.token = token;
    }

    /**
     * {@inheritDoc}
     * <p>
     * It is a delegate method to the embedded token.
     * </p>
     * 
     * @see org.extex.exbib.core.bst.code.Code#execute( BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        token.execute(processor, entry, locator);
    }

    /**
     * Getter for the {@link Token} value. The token is embedded in the macro
     * code to carry the true meaning.
     * 
     * @return the {@link Token}
     */
    public Token getToken() {

        return token;
    }

    /**
     * Compute the string representation of this macro code. It is a delegate
     * method to the embedded token.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        return token.toString();
    }

}
