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

package org.extex.typesetter.type.noad;

import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.type.math.MathClass;
import org.extex.interpreter.type.math.MathClassVisitor;
import org.extex.interpreter.type.math.MathCode;
import org.extex.interpreter.type.math.MathDelimiter;

/**
 * This class is a factory for CharNoades.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class NoadFactory {

    /**
     * The constant <tt>VISITOR</tt> contains the math class visitor.
     */
    private static final MathClassVisitor VISITOR = new MathClassVisitor() {

        /**
         * Invoke the visitor method for a binary operator.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitBinary(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitBinary(final Object noad, final Object arg) {

            return new BinaryNoad((Noad) noad, (TypesettingContext) arg);
        }

        /**
         * Invoke the visitor method for a closing delimiter.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitClosing(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitClosing(final Object noad, final Object arg) {

            return new CloseNoad((Noad) noad, (TypesettingContext) arg);
        }

        /**
         * Invoke the visitor method for a large operator.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitLarge(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitLarge(final Object noad, final Object arg) {

            if (arg instanceof MathDelimiter) {
                return new CharNoad(((MathDelimiter) noad).getLargeChar(),
                    (TypesettingContext) arg);
            }
            // TODO gene: visitLarge() partially unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * Invoke the visitor method for a opening delimiter.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitOpening(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitOpening(final Object noad, final Object arg) {

            return new OpenNoad((Noad) noad, (TypesettingContext) arg);
        }

        /**
         * Invoke the visitor method for an ordinary symbol .
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitOrdinary(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitOrdinary(final Object noad, final Object arg) {

            return new CharNoad((MathGlyph) noad, (TypesettingContext) arg);
        }

        /**
         * Invoke the visitor method for a punctation symbol.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitPunctation(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitPunctation(final Object noad, final Object arg) {

            return new PunctationNoad((Noad) noad, (TypesettingContext) arg);
        }

        /**
         * Invoke the visitor method for a relation operator.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitRelation(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitRelation(final Object noad, final Object arg) {

            return new RelationNoad((Noad) noad, (TypesettingContext) arg);
        }

        /**
         * Invoke the visitor method for a variable width symbol.
         *
         * @param arg the argument
         * @param arg2 the second argument
         *
         * @return the result
         *
         * @see org.extex.interpreter.type.math.MathClassVisitor#visitVariable(
         *      java.lang.Object, java.lang.Object)
         */
        public Object visitVariable(final Object noad, final Object arg) {

            // TODO gene: difference to ordinary ??
            return new CharNoad((MathGlyph) noad, (TypesettingContext) arg);
        }
    };

    /**
     * Creates a new object.
     *
     */
    public NoadFactory() {

        super();
    }

    /**
     * Provides an instance of a {@link Noad Noad} of the appropriate type.
     *
     * @param mc the code of the character to use
     * @param tc the typesetting context
     *
     * @return an instance of a CharNoad
     */
    public Noad getNoad(final MathCode mc, final TypesettingContext tc) {

        return (Noad) mc.getMathClass().visit(VISITOR, mc.getMathGlyph(), tc);
    }

    /**
     * Provides an instance of a {@link Noad Noad} of the appropriate type.
     *
     * @param mathClass the math class
     * @param glyph the character
     * @param tc the typesetting context
     *
     * @return an instance of a CharNoad
     */
    public Noad getNoad(final MathClass mathClass, final MathGlyph glyph,
            final TypesettingContext tc) {

        return (Noad) mathClass.visit(VISITOR, glyph, tc);
    }

}
