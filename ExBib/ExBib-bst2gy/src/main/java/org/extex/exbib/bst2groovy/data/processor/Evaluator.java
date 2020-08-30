/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data.processor;

import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.exceptions.ExBibException;

/**
 * This interface describes a (partial) evaluator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface Evaluator {

    /**
     * Evaluate a token.
     * 
     * @param token the token to evaluate
     * @param entryReference the entry reference
     * @param state the state
     * 
     * @throws ExBibException just in case
     */
    void evaluate(Token token, EntryReference entryReference,
            ProcessorState state) throws ExBibException;

    /**
     * Partially evaluate a token.
     * 
     * @param token the token to evaluate
     * @param entryReference the entry reference
     * @param state the state
     * 
     * @throws ExBibException just in case
     */
    void evaluatePartially(Token token, EntryReference entryReference,
            ProcessorState state) throws ExBibException;

    /**
     * Create a new processor state.
     * 
     * @param size the number of the internal stack items
     * 
     * @return a new processor state
     */
    ProcessorState makeState(int size);

}
