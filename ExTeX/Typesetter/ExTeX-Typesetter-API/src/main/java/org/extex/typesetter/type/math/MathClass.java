/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.math;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * This class provides the classification of mathematical characters. In fact it
 * is a finite enumeration which exposes the values as constants.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class MathClass implements Serializable {

    /**
     * This is a inner class for a binary operator.
    */
    private static final class BinaryMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.BINARY;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("bin");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitBinary(arg, arg2);
        }
    }

    /**
     * This is a inner class for closing.
     */
    private static final class ClosingMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.CLOSING;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("close");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitClosing(arg, arg2);
        }
    }

    /**
     * This is a inner class for large operators.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class LargeMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.LARGE;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("large");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitLarge(arg, arg2);
        }
    }

    /**
     * This is a inner class for opening.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class OpeningMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.OPENING;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("open");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitOpening(arg, arg2);
        }
    }

    /**
     * This is a inner class for ordinary characters.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class OrdinaryMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.ORDINARY;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("ord");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitOrdinary(arg, arg2);
        }
    }

    /**
     * This is a inner class for punctation marks.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class PunctationMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.PUNCTATION;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("punct");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitPunctation(arg, arg2);
        }
    }

    /**
     * This is a inner class for relation symbols.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class RelationMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.RELATION;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("rel");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitRelation(arg, arg2);
        }
    }

    /**
     * This is a inner class for variable width characters.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class VariableMathClass extends MathClass {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return MathClass.VARIABLE;
        }

        /**
         * Append the printable representation of the current instance to the
         * string buffer.
         * 
         * @param sb the target string buffer
         */
        @Override
        public void toString(StringBuilder sb) {

            sb.append("var");
        }

        /**
         * Call a method in the visitor depending on the type. This method is
         * the entry point for the visitor pattern.
         * 
         * @param visitor the visitor to call
         * @param arg an arbitrary argument passed to the visitor
         * @param arg2 an arbitrary second argument passed to the visitor
         * 
         * @return an arbitrary return value
         * 
         * @see org.extex.typesetter.type.math.MathClass#visit(org.extex.typesetter.type.math.MathClassVisitor,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(MathClassVisitor visitor, Object arg, Object arg2) {

            return visitor.visitVariable(arg, arg2);
        }
    }

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 24012007L;

    /**
     * The field {@code BINARY} contains the instance representing the binary
     * class. This class has the code 2 in TeX.
     */
    public static final MathClass BINARY = new BinaryMathClass();

    /**
     * The field {@code CLOSING} contains the instance representing the closing
     * class. This class has the code 5 in TeX.
     */
    public static final MathClass CLOSING = new ClosingMathClass();

    /**
     * The field {@code LARGE} contains the instance representing the large
     * class. This class has the code 1 in TeX.
     */
    public static final MathClass LARGE = new LargeMathClass();

    /**
     * The field {@code OPENING} contains the instance representing the opening
     * class. This class has the code 4 in TeX.
     */
    public static final MathClass OPENING = new OpeningMathClass();

    /**
     * The field {@code ORDINARY} contains the instance representing the
     * ordinary class. This class has the code 0 in TeX.
     */
    public static final MathClass ORDINARY = new OrdinaryMathClass();

    /**
     * The field {@code PUNCTUATION} contains the instance representing the
     * punctation class. This class has the code 6 in TeX.
     */
    public static final MathClass PUNCTATION = new PunctationMathClass();

    /**
     * The field {@code RELATION} contains the instance representing the
     * relation class. This class has the code 3 in TeX.
     */
    public static final MathClass RELATION = new RelationMathClass();

    /**
     * The field {@code VARIABLE} contains the instance representing the
     * variable width class. This class has the code 7 in TeX.
     */
    public static final MathClass VARIABLE = new VariableMathClass();

    /**
     * The field {@code MC} contains the mapping from TeX numbers to instances.
     * 
     * @see MathClass#getMathClass(int)
     */
    private static final MathClass[] MC = {ORDINARY, LARGE, BINARY, RELATION,
            OPENING, CLOSING, PUNCTATION, VARIABLE, null};

    /**
     * Factory method for the math class which maps the TeX encoding into the appropriate instance. The following
     * table gives a mapping from TeX numbers to instances:
     * <table>
     * <caption>TBD</caption>
     * <tr>
     * <td>0</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#ORDINARY ORDINARY}</td>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#LARGE LARGE}</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#BINARY BINARY}</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#RELATION RELATION}</td>
     * </tr>
     * <tr>
     * <td>4</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#OPENING OPENING}</td>
     * </tr>
     * <tr>
     * <td>5</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#CLOSING CLOSING}</td>
     * </tr>
     * <tr>
     * <td>6</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#PUNCTATION
     * PUNCTATION}</td>
     * </tr>
     * <tr>
     * <td>7</td>
     * <td>{@link org.extex.typesetter.type.math.MathClass#VARIABLE VARIABLE}</td>
     * </tr>
     * </table>
     *
     * @param n the TeX-encoded index of the class
     * @return The mapped {@link MathClass} for the given index;
     */
    public static MathClass getMathClass( int n ) {
        return MC[n];
    }

    /**
     * Creates a new object. The constructor is protected to be open for
     * extensions in the future.
     */
    protected MathClass() {
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    /**
     * Append the printable representation of the current instance to the string
     * buffer.
     * 
     * @param sb the target string buffer
     */
    public abstract void toString(StringBuilder sb);

    /**
     * Call a method in the visitor depending on the type. This method is the
     * entry point for the visitor pattern.
     * 
     * @param visitor the visitor to call
     * @param arg an arbitrary argument passed to the visitor
     * @param arg2 an arbitrary second argument passed to the visitor
     * 
     * @return an arbitrary return value
     */
    @SuppressWarnings("rawtypes")
    public abstract Object visit(MathClassVisitor visitor, Object arg,
            Object arg2);

}
