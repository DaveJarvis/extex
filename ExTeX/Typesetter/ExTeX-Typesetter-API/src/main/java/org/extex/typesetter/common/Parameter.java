/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.common;

/**
 * This class contains some constants for parameters used in typesetters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class Parameter {

    /**
     * The field {@code BASELINESKIP} contains the key for the glue parameter
     * \baselineskip.
     * 
     * <p>The Parameter {@code \baselineskip}</p>
     * 
     * <p>
     * The parameter {@code \baselineskip} contains the desirable skip
     * between lines. The depth of the first line is subtracted from this value
     * to determine the automatic skip value to be inserted. If this value is
     * less than the value of {@code \lineskiplimit} then the value of
     * {@code \lineskip} is used instead.
     * </p>
     */
    public static final String BASELINESKIP = "baselineskip";

    /**
     * The field {@code LINESKIP} contains the key for the glue parameter
     * \lineskip.
     * 
     * <p>The Parameter {@code \lineskip}</p>
     * 
     * <p>
     * The parameter {@code \lineskip} contains the interline skip which is
     * used when the adjacent lines would come together too close. This is the
     * case when the interline glue inserted automatically is less than the
     * value of {@code \lineskiplimit}.
     * </p>
     */
    public static final String LINESKIP = "lineskip";

    /**
     * The field {@code LINESKIPLIMIT} contains the key for the glue
     * parameter \lineskiplimit.
     * 
     * <p>The Parameter {@code \lineskiplimit}</p>
     * 
     * <p>
     * The parameter {@code \lineskiplimit} contains the limit for the
     * automatic inter line skip inserted. The automatic inter line skip is
     * defendant on the value of the parameter {@code \baselineskip}. It the
     * automatic inter line skip is less than {@code \lineskiplimit} the
     * value of {@code \lineskip} is used instead.
     * </p>
     */
    public static final String LINESKIPLIMIT = "lineskiplimit";

    /**
     * Creates a new object but not for everybody.
     */
    private Parameter() {

        // 
    }

}
