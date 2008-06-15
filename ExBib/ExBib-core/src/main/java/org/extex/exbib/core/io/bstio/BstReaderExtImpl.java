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

package org.extex.exbib.core.io.bstio;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
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
     * The field <tt>clazz</tt> contains the class name. This works for
     * derived classes as well.
     */
    private Class<? extends BstReaderImpl> clazz;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException if the file can not be opened for reading
     */
    public BstReaderExtImpl() throws ConfigurationException {

        super();
        clazz = getClass();
    }

    /**
     * 
     * Process a {@link Token Token} as a command. The following commands are
     * supported by this method in addition to those supported by
     * {@link BstReaderImpl BstReaderImpl}:
     * <dl>
     * <dd>include</dd>
     * </dl>
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.bstio.BstReaderImpl#init()
     */
    @Override
    protected void init() {

        super.init();
        addInstruction("include", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                String fname = parseLiteralArg().getValue();

                try {
                    BstReaderImpl reader = clazz.newInstance();
                    Configuration cfg = getConfiguration();
                    if (cfg != null) {
                        reader.configure(cfg);
                    }
                    reader.parse(processor, fname);
                } catch (ExBibBstNotFoundException e) {
                    throw new ExBibBstNotFoundException(fname, locator);
                } catch (ConfigurationException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ExBibException(e);
                }
            }
        });
    }
}
