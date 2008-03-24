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

package org.extex.exbib.core.io.bstio;

import java.io.FileNotFoundException;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.exceptions.ExBibConfigurationException;
import org.extex.exbib.core.exceptions.ExBibEofException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class implements a reader for bst files.
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BstReaderExtImpl extends BstReaderImpl {

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException if the file can not be opened for reading
     */
    public BstReaderExtImpl() throws ConfigurationException {

        super();
    }

    /**
     * Process a {@link Token Token} as a command. The following commands are
     * supported by this method in addition to those supported by
     * {@link BstReaderImpl BstReaderImpl}:
     * <dl>
     * <dd>include</dd>
     * </dl>
     * 
     * @param token the token to process
     * @param processor the processor context
     * 
     * @return <code>true</code> iff the given toke has been handled
     *         successfully
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case of an unexpected end of file
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case something impossible happens
     */
    @Override
    protected boolean processCommand(Token token, Processor processor)
            throws ExBibException {

        if (!(token instanceof TLiteral)) {
            return false;
        }

        String name = token.getValue().toLowerCase();

        if (name.equals("include")) {
            String fname = parseLiteralArg().getValue();

            try {
                BstReader reader = this.getClass().newInstance();
                reader.parse(processor, fname);
            } catch (ConfigurationException e) {
                throw new ExBibConfigurationException(e);
            } catch (FileNotFoundException e) {
                throw new ExBibException(e);
            } catch (Exception e) {
                throw new ExBibException(e);
            }

            return true;
        }

        return super.processCommand(token, processor);
    }
}
