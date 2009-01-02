/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.VNumber;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents an integer valued field local to an entry. This class
 * is not related to externally stored values but used internally only.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TLocalInteger extends AbstractToken {

    /**
     * Create a new object.
     * 
     * @param locator the locator
     * @param value the name of the field
     */
    public TLocalInteger(String value, Locator locator) {

        super(value, locator);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.Code#execute(org.extex.exbib.core.bst.BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        if (entry == null) {
            throw new ExBibMissingEntryException(null, locator);
        }

        VNumber val = (VNumber) entry.getLocal(getValue());

        processor.push(val == null ? TokenFactory.T_ZERO : new TInteger(val
            .getContent(), locator));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.Token#visit(org.extex.exbib.core.bst.token.TokenVisitor,
     *      java.lang.Object[])
     */
    public void visit(TokenVisitor visitor, Object... args)
            throws ExBibException {

        visitor.visitLocalInteger(this, args);
    }

}
