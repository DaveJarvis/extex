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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>purify$</code>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (string) literal, removes non alphanumeric characters
 * except for white-space characters and hyphens and ties (these all get
 * converted to a space), removes certain alphabetic characters contained in the
 * control sequences associated with a ``macro character'', and pushes the
 * resulting string. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd> The <code>built_in</code> function <code>purify$</code> pops the top
 * (string) literal, removes non alphanumeric characters except for
 * <code>white_space</code> and <code>sep_char</code> characters (these get
 * converted to a <code>space</code>) and removes certain alphabetic
 * characters contained in the control sequences associated with a macro
 * character, and pushes the resulting string. If the literal isn't a string, it
 * complains and pushes the null string. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Purify extends AbstractCode {

    /**
     * The field <tt>macro</tt> contains the mapping of macro characters.
     */
    private Map<String, String> macro = new HashMap<String, String>();

    /**
     * Create a new object.
     */
    public Purify() {

        super();
        macro.put("l", "l");
        macro.put("L", "L");
        macro.put("i", "i");
        macro.put("j", "j");
        macro.put("o", "o");
        macro.put("O", "O");
        macro.put("aa", "aa");
        macro.put("AA", "AA");
        macro.put("ss", "ss");
        macro.put("oe", "oe");
        macro.put("OE", "OE");
        macro.put("ae", "ae");
        macro.put("AE", "AE");
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Purify(String name) {

        this();
        setName(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration config) throws ConfigurationException {

        super.configure(config);

        if (config == null) {
            return;
        }
        if (config.getAttribute("clear") != null) {
            macro = new HashMap<String, String>();
        }
        Iterator<Configuration> it = config.iterator("macro");
        while (it.hasNext()) {
            Configuration cfg = it.next();
            String s = cfg.getValue();
            macro.put(s, s);
        }
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

        StringBuilder sb =
                new StringBuilder(processor.popString(locator).getValue());
        int level = 0;

        for (int i = 0; i < sb.length();) {
            char c = sb.charAt(i);

            if (Character.isWhitespace(c) || c == '-' || c == '~') {
                sb.setCharAt(i++, ' ');
            } else if (c == '{') {
                sb.deleteCharAt(i);
                level++;

                if (level == 1 && i < sb.length() - 1 && sb.charAt(i) == '\\') {
                    int j = i;
                    i++;

                    while (i < sb.length() && Character.isLetter(sb.charAt(i))) {
                        i++;
                    }

                    String ctrl = (macro.get(sb.substring(j + 1, i)));

                    if (ctrl == null) {
                        sb.delete(j, i);
                        i = j;
                    } else {
                        sb.replace(j, i, ctrl);
                        i = j + ctrl.length();
                    }
                }
            } else if (c == '}') {
                sb.deleteCharAt(i);
                level--;
            } else if (!Character.isLetterOrDigit(c)) {
                sb.deleteCharAt(i);
            } else {
                i++;
            }
        }

        processor.push(new TString(sb.toString()));
    }

}
