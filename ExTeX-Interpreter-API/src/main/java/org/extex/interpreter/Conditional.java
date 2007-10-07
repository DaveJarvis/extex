/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
 * This class represents a conditional for a normal <tt>\if \else \fi</tt>
 * construct. It records which <tt>\if</tt> has initiated it and where this has
 * happened.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public class Conditional implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>branch</tt> contains the branch indicator.
     */
    private long branch;

    /**
     * The field <tt>locator</tt> contains the locator to the position of the
     * opening <tt>\if</tt>.
     */
    private Locator locator;

    /**
     * The field <tt>neg</tt> contains the indicator that the conditional has
     * been negated.
     */
    private boolean neg;

    /**
     * The field <tt>primitive</tt> contains the name of the primitive which
     * has lead to this conditional.
     */
    private Code primitive;

    /**
     * Creates a new object.
     *
     * @param locator the locator
     * @param primitive the primitive which started this conditional
     * @param branch <code>true</code> iff the then branch is taken
     * @param neg indicator that the conditional has been negated
     */
    public Conditional(Locator locator, Code primitive,
            long branch, boolean neg) {

        super();
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
     * The locator points to the initiating <tt>\if</tt>.
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
     * If it has the value <code>true</code> then the conditional is one of the
     * if-then-else constructs. Otherwise it is a <tt>\ifcase</tt> construction.
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
