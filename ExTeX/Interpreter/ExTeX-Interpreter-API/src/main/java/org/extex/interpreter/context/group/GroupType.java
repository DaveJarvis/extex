/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * This class provides symbolic constants for the different group types.
 *
 * <p>
 * The following values are defined:
 * </p>
 * <table> <caption>TBD</caption>
 *  <tr><td> 0</td><td>bottom level (no group)</td></tr>
 *  <tr><td> 1</td><td>simple group</td></tr>
 *  <tr><td> 2</td><td>hbox group</td></tr>
 *  <tr><td> 3</td><td>adjusted hbox group</td></tr>
 *  <tr><td> 4</td><td>vbox group</td></tr>
 *  <tr><td> 5</td><td>vtop group</td></tr>
 *  <tr><td> 6</td><td>align group</td></tr>
 *  <tr><td> 7</td><td>no align group</td></tr>
 *  <tr><td> 8</td><td>output group</td></tr>
 *  <tr><td> 9</td><td>math group</td></tr>
 *  <tr><td>10</td><td>disc group</td></tr>
 *  <tr><td>11</td><td>insert group</td></tr>
 *  <tr><td>12</td><td>vcenter group</td></tr>
 *  <tr><td>13</td><td>math choice group</td></tr>
 *  <tr><td>14</td><td>semi simple group</td></tr>
 *  <tr><td>15</td><td>math shift group</td></tr>
 *  <tr><td>16</td><td>math left group</td></tr>
 * </table>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class GroupType implements Serializable {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 24012007L;

    /**
     * The field {@code ADJUSTED_HBOX_GROUP} contains the symbolic constant
     * for the adjusted hbox group.
     */
    public static final GroupType ADJUSTED_HBOX_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.ADJUSTED_HBOX_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitAdjustedHboxGroup(arg);
        }

    };

    /**
     * The field {@code ALIGN_GROUP} contains the symbolic constant
     * for the align group.
     */
    public static final GroupType ALIGN_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.ALIGN_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitAlignGroup(arg);
        }

    };

    /**
     * The field {@code BOTTOM_LEVEL_GROUP} contains the symbolic constant
     * for the bottom level group.
     */
    public static final GroupType BOTTOM_LEVEL_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.BOTTOM_LEVEL_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitBottomLevelGroup(arg);
        }

    };

    /**
     * The field {@code DISC_GROUP} contains the symbolic constant
     * for the disc group.
     */
    public static final GroupType DISC_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.DISC_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitDiscGroup(arg);
        }

    };

    /**
     * The field {@code HBOX_GROUP} contains the symbolic constant
     * for the hbox group.
     */
    public static final GroupType HBOX_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.HBOX_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitHboxGroup(arg);
        }

    };

    /**
     * The field {@code INSERT_GROUP} contains the symbolic constant
     * for the insert group.
     */
    public static final GroupType INSERT_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.INSERT_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitInsertGroup(arg);
        }

    };

    /**
     * The field {@code MATH_CHOICE_GROUP} contains the symbolic constant
     * for the math choice group.
     */
    public static final GroupType MATH_CHOICE_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.MATH_CHOICE_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitMathChoiceGroup(arg);
        }

    };

    /**
     * The field {@code MATH_GROUP} contains the symbolic constant
     * for the math group.
     */
    public static final GroupType MATH_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.MATH_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitMathGroup(arg);
        }

    };

    /**
     * The field {@code MATH_LEFT_GROUP} contains the symbolic constant
     * for the math left group.
     */
    public static final GroupType MATH_LEFT_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.MATH_LEFT_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitMathLeftGroup(arg);
        }

    };

    /**
     * The field {@code MATH_SHIFT_GROUP} contains the symbolic constant
     * for the math shift group.
     */
    public static final GroupType MATH_SHIFT_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.MATH_SHIFT_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitMathShiftGroup(arg);
        }

    };

    /**
     * The field {@code NO_ALIGN_GROUP} contains the symbolic constant
     * for the no align group.
     */
    public static final GroupType NO_ALIGN_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.NO_ALIGN_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitNoAlignGroup(arg);
        }

    };

    /**
     * The field {@code OUTPUT_GROUP} contains the symbolic constant
     * for the output group.
     */
    public static final GroupType OUTPUT_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.OUTPUT_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitOutputGroup(arg);
        }

    };

    /**
     * The field {@code SEMI_SIMPLE_GROUP} contains the symbolic constant
     * for the semi simple group.
     */
    public static final GroupType SEMI_SIMPLE_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.SEMI_SIMPLE_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitSemiSimpleGroup(arg);
        }

    };

    /**
     * The field {@code SIMPLE_GROUP} contains the symbolic constant
     * for the simple group.
     */
    public static final GroupType SIMPLE_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.SIMPLE_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitSimpleGroup(arg);
        }

    };

    /**
     * The field {@code VBOX_GROUP} contains the symbolic constant
     * for the vbox group.
     */
    public static final GroupType VBOX_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.VBOX_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitVboxGroup(arg);
        }

    };

    /**
     * The field {@code VCENTER_GROUP} contains the symbolic constant
     * for the vcenter group.
     */
    public static final GroupType VCENTER_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.VCENTER_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitVcenterGroup(arg);
        }

    };

    /**
     * The field {@code VTOP_GROUP} contains the symbolic constant
     * for the vtop group.
     */
    public static final GroupType VTOP_GROUP = new GroupType() {

        /**
         * The field {@code serialVersionUID} contains the id for serialization.
         */
        protected static final long serialVersionUID = 20060616L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return GroupType.VTOP_GROUP;
        }

        /**
         * This is the entry point to separate the different group types.
         *
         * @param visitor the group type visitor
         * @param arg an arbitrary argument to be used by the visitor
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupType#visit(
         *      org.extex.interpreter.context.group.GroupTypeVisitor,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(GroupTypeVisitor visitor, Object arg) {

            return visitor.visitVtopGroup(arg);
        }

    };

    /**
     * Creates a new object.
     * This constructor is private to avoid that other instances beside the
     * symbolic constants are created.
     */
    private GroupType() {

    }

    /**
     * This is the entry point to separate the different group types.
     *
     * @param visitor the group type visitor
     * @param arg an arbitrary argument to be used by the visitor
     *
     * @return some object
     */
    @SuppressWarnings({"rawtypes"})
    public abstract Object visit(GroupTypeVisitor visitor,
            Object arg);

}
