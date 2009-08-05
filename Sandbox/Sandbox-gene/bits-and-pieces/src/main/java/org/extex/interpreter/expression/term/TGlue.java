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

package org.extex.interpreter.expression.term;

import org.extex.core.dimen.Dimen;
import org.extex.core.glue.Glue;
import org.extex.core.glue.WideGlue;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;

/*
 * This class encapsulates a glue value for the use in the expression evaluator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision: 4733 $
 */
/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public class TGlue extends WideGlue implements EType {

    /*
     * The field <tt>value</tt> contains the encapsulated value.
     * 
     * @uml.property name="value"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="value"
     * 
     * @uml.associationEnd
     */
    private Glue value = new Glue();

    /**
     * Creates a new object.
     *
     */
    public TGlue() {

        super();
    }

    /**
     * Creates a new object.
     *
     * @param val the value
     */
    public TGlue(Glue val) {

        super();
        value.set(val);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#add(
     *      org.extex.interpreter.expression.EType)
     */
    public EType add(EType t) {

        // TODO gene: add unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#and(
     *      org.extex.interpreter.expression.EType)
     */
    public EType and(EType t) throws UnsupportedException {

        throw new UnsupportedException("&&", toString());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#divide(
     *      org.extex.interpreter.expression.EType)
     */
    public EType divide(EType t) throws CastException {

        if (t instanceof TCount) {
            long x = ((TCount) t).getValue();
            value.multiplyAll(1, x);
            return this;
        } else if (t instanceof TDouble) {
            double x = ((TDouble) t).getValue();
            value.setLength(//
                new Dimen((long) (value.getLength().getValue() / x)));
            value.setStretch(//
                new Dimen((long) (value.getStretch().getValue() / x)));
            value.setShrink(//
                new Dimen((long) (value.getShrink().getValue() / x)));
            // TODO gene: divide unimplemented
            return this;
        }

        throw new CastException();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#eq(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean eq(EType t) throws CastException {

        if (t instanceof TGlue) {
            return new TBoolean(value.eq(((TGlue) t).value));
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#ge(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean ge(EType t) throws CastException {

        if (!(t instanceof TGlue)) {
            throw new CastException();
        }
        Glue x = ((TGlue) t).value;
        return new TBoolean(value.getLength().ge(x.getLength()));
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#gt(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean gt(EType t) throws CastException {

        if (!(t instanceof TGlue)) {
            throw new CastException();
        }
        Glue x = ((TGlue) t).value;
        return new TBoolean(value.getLength().gt(x.getLength()));
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#le(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean le(EType t) throws CastException {

        if (!(t instanceof TGlue)) {
            throw new CastException();
        }
        Glue x = ((TGlue) t).value;
        return new TBoolean(value.getLength().le(x.getLength()));
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#lt(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean lt(EType t) throws CastException {

        if (t instanceof TGlue) {
            Glue x = ((TGlue) t).value;
            return new TBoolean(value.getLength().lt(x.getLength()));
        }

        throw new CastException();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#multiply(
     *      org.extex.interpreter.expression.EType)
     */
    public EType multiply(EType t) {

        // TODO gene: multiply unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#ne(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean ne(EType t) throws CastException {

        if (t instanceof TGlue) {
            return new TBoolean(value.ne(((TGlue) t).value));
        }

        throw new CastException();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#negate()
     */
    public EType negate() {

        value.negateLength();
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#not()
     */
    public EType not() throws UnsupportedException {

        throw new UnsupportedException("!", toString());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#or(
     *      org.extex.interpreter.expression.EType)
     */
    public EType or(EType t) throws UnsupportedException {

        throw new UnsupportedException("||", toString());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#set(
     *      org.extex.interpreter.expression.EType)
     */
    public EType set(EType t) throws CastException {

        if (t instanceof TGlue) {
            value.set(((TGlue) t).value);
            return this;
        }
        throw new CastException();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.EType#subtract(
     *      org.extex.interpreter.expression.EType)
     */
    public EType subtract(EType t) throws CastException {

        if (t instanceof TGlue) {
            value.subtract(((TGlue) t).value);
            return this;
        }

        throw new CastException();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return LocalizerFactory.getLocalizer(TGlue.class).format("Format",
            super.toString());
    }

}
