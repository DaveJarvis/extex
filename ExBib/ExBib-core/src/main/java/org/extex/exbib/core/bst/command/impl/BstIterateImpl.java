/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.command.impl;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.command.AbstractCommand;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.i18n.Messages;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents an <tt>ITERATE</tt> command.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BstIterateImpl extends AbstractCommand implements Command {

    /**
     * Creates a new object.
     * 
     * @param token the token
     * @param locator the locator
     * 
     * @throws ExBibException in case of an error
     */
    public BstIterateImpl(Token token, Locator locator) throws ExBibException {

        super(token, locator);

        if (token == null) {
            throw new ExBibImpossibleException(Messages
                .format("BstIterateImpl.Empty_token_for_ITERATE")); //$NON-NLS-1$
        }
    }

    /**
     * @see org.extex.exbib.core.bst.command.Command#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Locator locator)
            throws ExBibException {

        Token token = getValue();

        if (token == null) {
            throw new ExBibImpossibleException(Messages
                .format("BstIterateImpl.Empty_token_for_ITERATE")); //$NON-NLS-1$
        }

        List<Entry> rec = processor.getDB().getEntries();

        for (int i = 0; i < rec.size(); i++) {
            token.execute(processor, rec.get(i), getLocator());
        }
    }

    /**
     * Compute a printable string representation for this object.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        return "ITERATE { " + getValue() //$NON-NLS-1$

            .toString() + " }"; //$NON-NLS-1$
    }

    /**
     * @see org.extex.exbib.core.bst.command.Command#visit(
     *      org.extex.exbib.core.bst.command.CommandVisitor)
     */
    @Override
    public void visit(CommandVisitor visitor) throws IOException {

        visitor.visitIterate(this);
    }
}
