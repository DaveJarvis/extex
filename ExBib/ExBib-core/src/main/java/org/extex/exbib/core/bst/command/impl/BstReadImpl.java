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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.command.AbstractCommand;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.exceptions.ExBibConfigurationException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class represents a <tt>READ</tt> command.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BstReadImpl extends AbstractCommand implements Command {

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     */
    public BstReadImpl(Locator locator) {

        super(null, locator);
    }

    /**
     * @see org.extex.exbib.core.bst.command.Command#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Locator locator)
            throws ExBibException {

        try {
            processor.loadDB();
        } catch (FileNotFoundException e) {
            throw new ExBibException(e.getMessage(), e);
        } catch (ConfigurationException e) {
            throw new ExBibConfigurationException(e);
        }
    }

    /**
     * Compute a printable string representation for this object.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        return "READ";
    }

    /**
     * @see org.extex.exbib.core.bst.command.Command#visit(
     *      org.extex.exbib.core.bst.command.CommandVisitor)
     */
    @Override
    public void visit(CommandVisitor visitor) throws IOException {

        visitor.visitRead(this);
    }
}