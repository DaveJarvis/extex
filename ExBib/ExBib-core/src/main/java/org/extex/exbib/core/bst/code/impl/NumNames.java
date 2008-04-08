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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.Name;
import org.extex.exbib.core.bst.node.TokenFactory;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * BibT<sub>E</sub>X built-in function <code>num.names$</code>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (string) literal and pushes the number of names the string
 * represents---one plus the number of occurrences of the substring ``and''
 * (ignoring case differences) surrounded by non-null white-space at the top
 * brace level. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X web documentation:</dt>
 * <dd>
 * 
 * </dd>
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

        super();
        setName(name);
    }

    /**
     * Count the number of names in a string. This one more than the number of
     * occurences of the string "and" in any cases at brace level 0 surrounded
     * by whitespace.
     * 
     * @param names the string to parse
     * 
     * @return the number of names found
     */
    protected int countNames(String names) {

        int level = 0;
        int ret = 1;

        for (int i = 0; i < names.length(); i++) {
            char c = names.charAt(i);

            if (Character.isWhitespace(c) && level == 0) {
                if (i < names.length() - 4
                        && (names.charAt(i + 1) == 'a' || names.charAt(i + 1) == 'A')
                        && (names.charAt(i + 2) == 'n' || names.charAt(i + 2) == 'N')
                        && (names.charAt(i + 3) == 'd' || names.charAt(i + 3) == 'D')
                        && Character.isWhitespace(names.charAt(i + 4))) {
                    i += 4;
                    ret++;
                }
            } else if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            }
        }

        return ret;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        TString names = processor.popString(locator);
        List<Name> namelist = names.getNames();

        if (namelist == null) {
            namelist = Name.parse(names.getValue(), locator);
        }

        int n = namelist.size();
        processor.push((n == 0 ? TokenFactory.T_ZERO : n == 1
                ? TokenFactory.T_ONE
                : new TInteger(n)));
    }

}
