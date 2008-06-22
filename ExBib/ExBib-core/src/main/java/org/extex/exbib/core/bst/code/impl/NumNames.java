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

package org.extex.exbib.core.bst.code.impl;

import java.util.List;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.name.Name;
import org.extex.exbib.core.name.NameFactory;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function
 * <code>num.names$</code>
 * <p>
 * This function takes a string argument from the stack and treats it as a list
 * of names. It pushes the number of names in the list as integer to the stack.
 * </p>
 * <p>
 * The names are separated by the word <tt>and</tt>. This word has to be
 * preceded and followed by whitespace characters. In addition this word has to
 * occur at brace level 0.
 * </p>
 * <img src="doc-files/num.names.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   editor num.names$ #1 &gt;
 *     { ", editors" * }
 *     { ", editor" * }
 *   if$
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (string) literal and pushes the number of names the string
 * represents---one plus the number of occurrences of the substring ``and''
 * (ignoring case differences) surrounded by non-null white-space at the top
 * brace level. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class NumNames extends AbstractCode {

    /**
     * Create a new object.
     */
    public NumNames() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public NumNames(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute( BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        TString names = processor.popString(locator);
        List<Name> namelist =
                NameFactory.getFactory().getNames(names.getValue(), locator);
        int n = namelist.size();
        processor.push((n == 0 ? TokenFactory.T_ZERO : n == 1
                ? TokenFactory.T_ONE
                : new TInteger(n, locator)));
    }

}
