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
 * As in Prolog the occurs check is omitted. Thus it is possible to create a
 * cyclic structure leading to an infinite loop. The using code has to take care
 * of this.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Var implements GCode {

    /**
     * Unify two lists of variables.
     * 
     * @param l1 the first list
     * @param l2 the second list
     * 
     * @return the longer list
     */
    public static List<Var> unify(List<Var> l1, List<Var> l2) {

        int s1 = l1.size();
        int s2 = l2.size();
        if (s1 >= s2) {
            for (int i = 0; i < s2; i++) {
                l1.get(i).unify(l2.get(i));
            }
            return l1;
        }
        for (int i = 0; i < s1; i++) {
            l2.get(i).unify(l1.get(i));
        }
        return l2;
    }

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
     * The field <tt>age</tt> contains the age of the variable. It helps to
     * determine which variable to bind when two unbound variables are unified.
     */
    private int age;

    /**
     * Creates a new object.
     * 
     * @param name the name
     * @param age the age
     */
    protected Var(String name, int age) {

        this.name = name;
        this.age = age;
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

        writer.write(getType().toString());
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
     * 
     *        {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#unify(org.extex.exbib.bst2groovy.data.GCode)
     */
    public boolean unify(GCode other) {

        if (other == this) {
            // nothing to do
        } else if (reference != null) {
            return reference.unify(other);
        } else if (other instanceof Var) {
            Var vo = (Var) other;
            if (vo.reference != null) {
                return unify(vo.reference);
            } else if (vo.age > age) {
                vo.reference = this;
            } else {
                reference = vo;
            }
        } else {
            reference = other;
        }
        return true;
    }

}
