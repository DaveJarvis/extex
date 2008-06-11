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

import java.util.regex.Pattern;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function
 * <code>add.period$</code>
 * <p>
 * This function pops a string argument from the stack and inspects it. It the
 * argument ends in one of the characters period '.', exclamation mark '!', or
 * question mark '?' then the argument is pushed back to the stack. Otherwise a
 * period '.' is added to the argument and the result pushed to the stack.
 * 
 * When determining the last character closing braces are ignored.
 * </p>
 * <img src="doc-files/ad.period.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   FUNCTION {fin.entry}
 *   { add.period$
 *     write$
 *     newline$
 *   }
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:</dt>
 * <dd> Pops the top (string) literal, adds a `<code>.</code>' to it if the
 * last non`<code>}</code>' character isn't a `<code>.</code>', `<code>?</code>',
 * or `<code>!</code>, and pushes this resulting string. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd> The <code>built_in</code> function <code>add.period$</code> pops the
 * top (string) literal, adds a <code>period</code> to a nonnull string if its
 * last non<code>right_brace</code> character isn't a <code>period</code>,
 * <code>question_mark</code>, or <code>exclamation_mark</code>, and
 * pushes this resulting string back onto the stack. If the literal isn't a
 * string, it complains and pushes the null string. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class AddPeriod extends AbstractCode {

    /**
     * The field <tt>omitPattern</tt> contains the pattern to determine when
     * not to add a period.
     */
    private Pattern omitPattern = Pattern.compile(".*[.!?][}]*$");

    /**
     * Create a new object.
     */
    public AddPeriod() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public AddPeriod(String name) {

        super(name);
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
        String op = config.getValue("omitPattern");
        if (op != null) {
            omitPattern = Pattern.compile(op);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        Token a = processor.pop(locator);
        String value = a.getValue();

        if (!value.equals("") && !omitPattern.matcher(value).matches()) {
            a = new TString(value + ".", locator);
        } else if (!(a instanceof TString)) {
            a = new TString(value, locator);
        }

        processor.push(a);
    }

}
