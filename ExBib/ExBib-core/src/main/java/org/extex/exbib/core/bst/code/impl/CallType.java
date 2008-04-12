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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * BibT<sub>E</sub>X built-in function <code>call.type$</code>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X documentation:</dt>
 * <dd> Executes the function whose name is the entry type of an entry. For
 * example if an entry is of type <code>book</code>, this function executes
 * the <code>book</code> function. When given as an argument to the
 * <code>ITERATE</code> command, <code>call.type$</code> actually produces
 * the output for the entries. For an entry with an unknown type, it executes
 * the function <code>default.type</code>. Thus you should define (before the
 * <code>READ</code> command) one function for each standard entry type as
 * well as a <code>default.type</code> function. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X web documentation:</dt>
 * <dd> The <i>built_in</i> function <code>call.type$</code> executes the
 * function specified in <code>type_list</code> for this entry unless it's
 * <code>undefined</code>, in which case it executes the default function
 * <code>default.type</code> defined in the <i>.bst</i> file, or unless it's
 * <code>empty</code>, in which case it does nothing. </dd
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class CallType extends AbstractCode {

    /** the name of the default type */
    private String defaultType = "default.type";

    /**
     * Creates a new object.
     */
    public CallType() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public CallType(String name) {

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
        String dt = config.getValue("default");
        if (dt != null && !dt.equals("")) {
            defaultType = dt;
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
