/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter;

import java.io.Serializable;

import org.extex.core.Locator;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;

/**
 * This class represents a conditional for a normal {@code \if \else \fi}
 * construct. It records which {@code \if} has initiated it and where this has
 * happened.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Conditional implements Serializable {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field {@code branch} contains the branch indicator.
     */
    private final long branch;

    /**
     * The field {@code locator} contains the locator to the position of the
     * opening {@code \if}.
     */
    private final Locator locator;

    /**
     * The field {@code neg} contains the indicator that the conditional has
     * been negated.
     */
    private final boolean neg;

    /**
     * The field {@code primitive} contains the name of the primitive which
     * has lead to this conditional.
     */
    private final Code primitive;

    /**
     * Creates a new object.
     *
     * @param locator the locator
     * @param primitive the primitive which started this conditional
     * @param branch {@code true} iff the then branch is taken
     * @param neg indicator that the conditional has been negated
     */
    public Conditional(Locator locator, Code primitive,
            long branch, boolean neg) {

        this.locator = locator;
        this.primitive = primitive;
        this.branch = branch;
        this.neg = neg;
    }

    /**
     * Getter for branch.
     *
     * @return the branch
     */
    public long getBranch() {

        return this.branch;
    }

    /**
     * Getter for the locator of this conditional.
     * The locator points to the initiating {@code \if}.
     *
     * @return the locator
     */
    public Locator getLocator() {

        return locator;
    }

    /**
     * Getter for primitive.
     *
     * @return the primitive
     */
    public Code getPrimitive() {

        return this.primitive;
    }

    /**
     * Getter for the primitive which started this conditional.
     *
     * @return the primitive name
     */
    public CodeToken getPrimitiveToken() {

        return this.primitive.getToken();
    }

    /**
     * Getter for the value of the conditional.
     * If it has the value {@code true} then the conditional is one of the
     * if-then-else constructs. Otherwise it is a {@code \ifcase} construction.
     *
     * @return the value
     */
    public boolean getValue() {

        return true;
    }

    /**
     * Getter for neg.
     *
     * @return the neg
     */
    public boolean isNeg() {

        return this.neg;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return primitive + "[" + locator.toString() + "]";
    }

}
