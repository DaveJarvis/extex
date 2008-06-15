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

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>warning$</code>
 * <p>
 * This function pops a string from the stack and prints it as a warning to the
 * log stream. The message is terminated by a newline character. The line in the
 * log file may be rewrapped to fit into a given line length.
 * </p>
 * <p>
 * An empty stack leads to an error as well as a wrong type argument on the
 * stack.
 * </p>
 * <img src="doc-files/warning.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   "there's a number but no series in " cite$ * warning$ 
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (string) literal and prints it following a warning message.
 * This also increments a count of the number of warning messages issued. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd> The <code>built_in</code> function <code>warning$</code> pops the
 * top (string) literal and prints it following a warning message. This is
 * implemented as a special <code>built_in</code> function rather than using
 * the <code>top$</code> function so that it can <code>mark_warning</code>.
 * </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class Warning extends AbstractCode {

    /**
     * The field <tt>localizer</tt> contains the cached localizer.
     */
    private transient Localizer localizer = null;

    /**
     * Create a new object.
     */
    public Warning() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Warning(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      BstProcessor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        Token arg = processor.popString(locator);

        if (localizer == null) {
            localizer = LocalizerFactory.getLocalizer(getClass());
        }

        processor.warning(localizer.format("Message", arg.getValue()));
    }

}
