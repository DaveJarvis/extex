/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.exception.ExBibImmutableException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalLocator;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TStringOption;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function <code>set$</code>
 * <p>
 * This function assigns a value to a variable or field. It takes two arguments
 * from the stack. The first argument is the name of the target. In general it
 * needs to be quoted. The second argument is the appropriate value.
 * </p>
 * <img src="doc-files/set.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *     { namesleft #0 &gt; }
 *     { 
 *       % some actions
 * 
 *       namesleft #1 - 'namesleft :=
 *     }
 *   while$
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X documentation:
 * <dt>
 * <dd>Pops the top two literals and assigns to the first (which must be a
 * global or entry variable) the value of the second.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Set extends AbstractCode {

    /**
     * The field <tt>TV</tt> contains the visitor for tokens.
     */
    private static final TokenVisitor TV = new TokenVisitor() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
         *      java.lang.Object[])
         */
        public void visitBlock(TBlock block, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
         *      java.lang.Object[])
         */
        public void visitChar(TChar c, Object... args) throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
         *      java.lang.Object[])
         */
        public void visitField(TField field, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Entry entry = (Entry) args[1];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            entry.set(var, processor.popString(locator).getValue());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
         *      java.lang.Object[])
         */
        public void visitInteger(TInteger integer, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitIntegerOption(org.extex.exbib.core.bst.token.impl.TIntegerOption,
         *      java.lang.Object[])
         */
        public void visitIntegerOption(TIntegerOption option, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
         *      java.lang.Object[])
         */
        public void visitLiteral(TLiteral literal, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
         *      java.lang.Object[])
         */
        public void visitLocalInteger(TLocalInteger integer, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Entry entry = (Entry) args[1];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            entry.setLocal(var, processor.popInteger(locator).getInt());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalLocator(org.extex.exbib.core.bst.token.impl.TLocalLocator,
         *      java.lang.Object[])
         */
        public void visitLocalLocator(TLocalLocator localLocator, Object[] args)
                throws ExBibException {

            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            throw new ExBibImmutableException(var, var, locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
         *      java.lang.Object[])
         */
        public void visitLocalString(TLocalString string, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Entry entry = (Entry) args[1];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            entry.setLocal(var, processor.popString(locator).getValue());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
         *      java.lang.Object[])
         */
        public void visitQLiteral(TQLiteral qliteral, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
         *      java.lang.Object[])
         */
        public void visitString(TString string, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitStringOption(org.extex.exbib.core.bst.token.impl.TStringOption,
         *      java.lang.Object[])
         */
        public void visitStringOption(TStringOption option, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
         *      java.lang.Object[])
         */
        public void visitTokenList(TokenList list, Object... args)
                throws ExBibException {

            BstProcessor processor = (BstProcessor) args[0];
            Locator locator = (Locator) args[2];
            String var = (String) args[3];
            processor.changeFunction(var, processor.pop(locator), locator);
        }

    };

    /**
     * Create a new object.
     */
    public Set() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Set(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        Token arg = processor.pop(locator);

        if (!(arg instanceof TLiteral)) {
            throw new ExBibMissingLiteralException(arg.toString(), locator);
        }
        String name = arg.getValue();
        Code code = processor.getFunction(name);

        if (code instanceof Token) {
            ((Token) code).visit(TV, processor, entry, locator, name);
        } else {
            processor.changeFunction(name, processor.pop(locator), locator);
        }
    }

}
