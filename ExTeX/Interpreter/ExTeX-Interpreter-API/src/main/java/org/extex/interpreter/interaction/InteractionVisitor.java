/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.interaction;

import org.extex.core.exception.GeneralException;

/**
 * This interface describes a visitor for interaction modes. With the help of
 * this interface the visitor pattern can be implemented.
 * <p>
 * The user of the visitor pattern has to provide an implementation of this
 * interface. Then the {@code visit} method is invoked and the caller is
 * forwarded to the appropriate {@code visit} method in the visitor.
 * </p>
 * <p>
 * Consider we have a mode constant at hand which is in fact a batch mode and we
 * invokes
 * </p>
 *
 * <pre>
 *   boolean boo = mode.visit(visitor, a, b, c);
 * </pre>
 *
 * <p>
 * then the following method in the object {@code visitor} is used:
 * </p>
 *
 * <pre>
 *   visitBatchmode(visitor, a, b, c)
 * </pre>
 *
 * 
 * @param <A1> the type of the first argument
 * @param <A2> the type of the second argument
 * @param <A3> the type of the third argument
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface InteractionVisitor<A1, A2, A3> {

    /**
     * Invoke the method in case of a batchmode interaction.
     * 
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * 
     * @return a boolean indicator
     * 
     * @throws GeneralException in case of an error
     */
    boolean visitBatchmode(A1 arg1, A2 arg2, A3 arg3)
            throws GeneralException;

    /**
     * Invoke the method in case of a non-stop mode interaction.
     * 
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * 
     * @return a boolean indicator
     * 
     * @throws GeneralException in case of an error
     */
    boolean visitNonstopmode(A1 arg1, A2 arg2, A3 arg3)
            throws GeneralException;

    /**
     * Invoke the method in case of a scroll mode interaction.
     * 
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * 
     * @return a boolean indicator
     * 
     * @throws GeneralException in case of an error
     */
    boolean visitScrollmode(A1 arg1, A2 arg2, A3 arg3)
            throws GeneralException;

    /**
     * Invoke the method in case of a error-stop mode interaction.
     * 
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * 
     * @return a boolean indicator
     * 
     * @throws GeneralException in case of an error
     */
    boolean visitErrorstopmode(A1 arg1, A2 arg2, A3 arg3)
            throws GeneralException;

}
