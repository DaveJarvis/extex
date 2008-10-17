/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.info;

import org.extex.exbib.bst2groovy.Evaluator;
import org.extex.exbib.bst2groovy.data.EntryRefernce;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.evaluator.OpenEndedStack;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GetFieldInfo implements Info {

    /**
     * The field <tt>name</tt> contains the name of the field.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name
     */
    public GetFieldInfo(String name) {

        super();
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.info.Info#evaluate(org.extex.exbib.bst2groovy.data.EntryRefernce,
     *      org.extex.exbib.bst2groovy.evaluator.OpenEndedStack,
     *      org.extex.exbib.bst2groovy.data.GCodeContainer, Evaluator)
     */
    public void evaluate(EntryRefernce entryRefernce, OpenEndedStack stack,
            GCodeContainer code, Evaluator evaluator) {

        // TODO gene: evaluate unimplemented
        throw new RuntimeException("unimplemented");
    }

}
