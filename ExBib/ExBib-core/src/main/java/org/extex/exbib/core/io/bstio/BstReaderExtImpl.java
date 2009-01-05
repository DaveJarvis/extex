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

package org.extex.exbib.core.io.bstio;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.bst.exception.ExBibNoNumberException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TStringOption;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedEofException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class implements a reader for BST files.
 * 
 * <p>
 * The syntax follows the definition of B<small>IB</small>T<sub>E</sub>X 0.99c
 * with the following extensions:
 * </p>
 * 
 * <small>
 * <table>
 * <tr>
 * <td><i>item</i></td>
 * <td><tt>+==</tt></td>
 * <td><i>include</i></td>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>option</i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>include</i></td>
 * <td><tt>:==</tt></td>
 * <td>'INCLUDE' '{' <i>resource</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>option</i></td>
 * <td><tt>:==</tt></td>
 * <td>'OPTION' 'INTEGER' '{' <i>name</i> '}' '{' <i>value</i> '}'</td>
 * <td>'OPTION' 'STRING' '{' <i>name</i> '}' '{' <i>value</i> '}'</td>
 * </tr>
 * 
 * </table>
 * </small>
 * 
 * Example
 * 
 * <pre>
 * INCLUDE {author}{}{}
 *
 * OPTION INTEGER {abc}{123}
 *
 * OPTION STRING {def}{the default value}
 * </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BstReaderExtImpl extends BstReaderImpl {

    /**
     * The field <tt>clazz</tt> contains the class name. This works for derived
     * classes as well.
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
     * Process a {@link Token Token} as a command. The following commands are
     * supported by this method in addition to those supported by
     * {@link BstReaderImpl BstReaderImpl}:
     * <dl>
     * <dd>include</dd>
     * <dd>option</dd>
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
        addInstruction("option", new Instruction() {

            /**
             * Parse an "option integer".
             * 
             * @param processor the processor
             * @param locator the locator
             * 
             * @throws ExBibException in case of an error
             */
            private void optionInteger(BstProcessor processor, Locator locator)
                    throws ExBibException {

                String name = parseLiteralArg().getValue();
                TokenList tokenList = parseBlock().getTokenList();
                if (tokenList.size() != 1) {
                    throw new ExBibNoNumberException(tokenList.getValue(),
                        locator);
                }
                Token t = tokenList.get(0);
                if (!(t instanceof TInteger)) {
                    throw new ExBibNoNumberException(tokenList.getValue(),
                        locator);
                }
                processor.setOption(name, t);
                processor.addFunction(name, new TIntegerOption(name, locator),
                    locator);
            }

            /**
             * Parse an "option string".
             * 
             * @param processor the processor
             * @param locator the locator
             * 
             * @throws ExBibException in case of an error
             */
            private void optionString(BstProcessor processor, Locator locator)
                    throws ExBibException {

                String name = parseLiteralArg().getValue();
                String value = parseBlock().getTokenList().getValue();
                processor.setOption(name, new TString(value, locator));
                processor.addFunction(name, new TStringOption(name, locator),
                    locator);
            }

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                Token token = nextToken();

                if (token == null) {
                    throw new ExBibUnexpectedEofException(null,
                        "string,integer", getLocator());
                }
                if (token instanceof TLiteral) {
                    String value = token.getValue();
                    if ("integer".equals(value)) {
                        optionInteger(processor, locator);
                        return;
                    } else if ("string".equals(value)) {
                        optionString(processor, locator);
                        return;
                    }
                }
                throw new ExBibUnexpectedException(
                    "option string | option integer", token.toString(),
                    getLocator());
            }
        });
    }

}
