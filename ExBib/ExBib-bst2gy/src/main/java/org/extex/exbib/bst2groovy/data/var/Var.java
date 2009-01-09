/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data.var;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class implements something like a Prolog variable. It has a name and an
 * optional binding to another variable. If the variable is unbound then its
 * name and value are used for printing and determining the type. If a reference
 * is present, i.e. the variable is bound, then those variable is used instead.
 * 
 * <p>
 * As in Prolog the occurs check is omitted. Thus it is possible to create
 * cyclic structure leading to an infinite loop. The using code has to take care
 * of this.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Var implements GCode {

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>type</tt> contains the return type.
     */
    private ReturnType type;

    /**
     * The field <tt>reference</tt> contains the reference to another variable
     * if this one is bound.
     */
    private GCode reference = null;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public Var(String name) {

        this.name = name;
    }

    /**
     * Check whether this variable is bound to the same base as another one.
     * 
     * @param other the other variable
     * 
     * @return <code>true</code> if this variable is bound to the same base as
     *         another one
     */
    public boolean eq(Var other) {

        GCode v = this;
        while (v instanceof Var && ((Var) v).reference != null) {
            v = ((Var) v).reference;
        }
        GCode w = other;
        while (w instanceof Var && ((Var) w).reference != null) {
            w = ((Var) w).reference;
        }
        return v == w;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public ReturnType getType() {

        if (type != null) {
            return type;
        }
        if (reference != null) {
            return reference.getType();
        }
        return ReturnType.UNKNOWN;

    }

    /**
     * Check whether this variable is an alias for another one.
     * 
     * @return the result of the check
     */
    public boolean isBound() {

        return reference != null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#optimize()
     */
    public GCode optimize() {

        return (reference != null ? reference.optimize() : this);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#optimize(java.util.List, int)
     */
    public int optimize(List<GCode> list, int index) {

        return index + 1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
     *      java.lang.String)
     */
    public void print(CodeWriter writer, String prefix) throws IOException {

        if (reference != null) {
            reference.print(writer, prefix);
        } else {
            writer.write(name);
        }
    }

    /**
     * Print the type of this instance.
     * 
     * @param writer the target writer
     * @param prefix the prefix for newlines
     * 
     * @throws IOException in case of an I/O error
     */
    public void printType(Writer writer, String prefix) throws IOException {

        String t = getType().toString();
        if (t != null) {
            writer.write(t);
        } else {
            writer.write("var");
        }
    }

    /**
     * Setter for the type.
     * 
     * @param type the type
     */
    public void setType(ReturnType type) {

        if (type != null && type != ReturnType.UNKNOWN) {
            this.type = type;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return name;
    }

    /**
     * Bind this instance to another one.
     * 
     * @param other the other instance to bind to
     */
    public void unify(GCode other) {

        if (other == this) {
            return;
        }
        if (other instanceof Var) {
            if (((Var) other).name.compareTo(name) < 0) {
                ((Var) other).unifyMe(this);
                return;
            }

            if (reference instanceof Var) {
                ((Var) reference).unify(other);
            }

            reference = other;
        } else if (reference == null) {
            reference = other;
        } else {
            GCode v = this;
            while (v instanceof Var && ((Var) v).reference != null) {
                v = ((Var) v).reference;
            }
            GCode w = other;
            while (w instanceof Var && ((Var) w).reference != null) {
                w = ((Var) w).reference;
            }

            if (v == w) {
                return;
            }

            // TODO gene: unify unimplemented
            throw new RuntimeException("unimplemented");
        }
    }

    /**
     * Bind this instance to another one.
     * 
     * @param other the other instance to bind to
     */
    protected void unifyMe(Var other) {

        if (reference instanceof Var) {
            ((Var) reference).unify(other);
        }

        reference = other;
    }

}
