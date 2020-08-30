/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.context.group;

/**
 * This interface describes a visitor for group types.
 * 
 * @param <R> the return type
 * @param <A> the argument type
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface GroupTypeVisitor<R, A> {

    /**
     * This method is invoked when an adjusted hbox group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitAdjustedHboxGroup(A arg);

    /**
     * This method is invoked when an align group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitAlignGroup(A arg);

    /**
     * This method is invoked when a bottom level group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitBottomLevelGroup(A arg);

    /**
     * This method is invoked when a disc group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitDiscGroup(A arg);

    /**
     * This method is invoked when a hbox group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitHboxGroup(A arg);

    /**
     * This method is invoked when an insert group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitInsertGroup(A arg);

    /**
     * This method is invoked when a math choice group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitMathChoiceGroup(A arg);

    /**
     * This method is invoked when a math group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitMathGroup(A arg);

    /**
     * This method is invoked when a math left group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitMathLeftGroup(A arg);

    /**
     * This method is invoked when a math shift group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitMathShiftGroup(A arg);

    /**
     * This method is invoked when a no align group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitNoAlignGroup(A arg);

    /**
     * This method is invoked when a output group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitOutputGroup(A arg);

    /**
     * This method is invoked when a semi simple group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitSemiSimpleGroup(A arg);

    /**
     * This method is invoked when a simple group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitSimpleGroup(A arg);

    /**
     * This method is invoked when a vbox group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitVboxGroup(A arg);

    /**
     * This method is invoked when a vcenter group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitVcenterGroup(A arg);

    /**
     * This method is invoked when a vtop group has been encountered.
     * 
     * @param arg the argument
     * 
     * @return some object
     */
    R visitVtopGroup(A arg);

}
