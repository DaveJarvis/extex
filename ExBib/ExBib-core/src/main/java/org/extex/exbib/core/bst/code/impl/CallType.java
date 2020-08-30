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
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code call.type$}
 * <p>
 * This function looks at the current entry and calls the function with the same
 * name as the type. The name is normalized; i.e. translated to lower case.
 * </p>
 * <p>
 * If no such function is defined then the user-defined function
 * {@code default.type} is consulted and invoked instead.
 * </p>
 * <img src="doc-files/call.type.png" alt="call.type">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 * 
 * <pre>
 *   ITERATE {call.type$}
 * </pre>
 * 
 * <hr>
 * 
 * <dl>
 * <dt>BibTeX documentation:</dt>
 * <dd>Executes the function whose name is the entry type of an entry. For
 * example if an entry is of type {@code book}, this function executes the
 * {@code book} function. When given as an argument to the
 * {@code ITERATE} command, {@code call.type$} actually produces the
 * output for the entries. For an entry with an unknown type, it executes the
 * function {@code default.type}. Thus you should define (before the
 * {@code READ} command) one function for each standard entry type as well
 * as a {@code default.type} function.</dd>
 * </dl>
 * 
 * <dl>
 * <dt>BibTeX web documentation:</dt>
 * <dd>The <i>built_in</i> function {@code call.type$} executes the
 * function specified in {@code type_list} for this entry unless it's
 * {@code undefined}, in which case it executes the default function
 * {@code default.type} defined in the <i>.bst</i> file, or unless it's
 * {@code empty}, in which case it does nothing.</dd>
 * </dl>
 * 
 * 
 *  Configuration
 * <p>
 * The configuration can take an embedded element with the name {@code default}
 * . The text contained in this element is taken as the name of the function to
 * be called when no appropriate function is found. The default is
 * {@code default.type}.
 * </p>
 * 
 * <pre>
 *   &lt;function name="call.type$"
 *             class="org.extex.exbib.core.bst.code.impl.CallType"&gt;
 *     &lt;default&gt;default.type&lt;/default&gt;
 *   &lt;/function&gt;
 * </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CallType extends AbstractCode implements Configurable {

    /**
     * The field {@code defaultType} contains the name of the default type.
     */
    private String defaultType = "default.type";


    public CallType() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public CallType(String name) {

        super(name);
    }

public void configure(Configuration config) throws ConfigurationException {

        String dt = config.getValue("default");
        if (dt != null && !"".equals(dt)) {
            defaultType = dt;
        }
    }

    /**
*      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        if (entry == null) {
            throw new ExBibMissingEntryException(null, locator);
        }

        String name = entry.getType();

        if (processor.getFunction(name) != null) {
            new TLiteral(name, null).execute(processor, entry, locator);
        } else if (processor.getFunction(defaultType) != null) {
            new TLiteral(defaultType, null).execute(processor, entry, locator);
        }
    }

}
