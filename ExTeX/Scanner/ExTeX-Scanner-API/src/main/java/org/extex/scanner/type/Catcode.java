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

package org.extex.scanner.type;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.extex.scanner.api.exception.CatcodeException;

/**
 * This enumeration defined the category codes for characters used in parsing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5563 $
 */
public enum Catcode implements Serializable {

    /**
     * The constant <tt>ACTIVE</tt> contains the unique object representing the
     * active catcode.
     */
    ACTIVE(13) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.ACTIVE;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "active";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For active characters the visitor method
         * visitActive() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case or an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitActive(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>COMMENT</tt> contains the unique object representing the
     * comment catcode. A comment is started with this catcode and lasts to the
     * end of the line.
     */
    COMMENT(14) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.COMMENT;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "comment";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For comment starting characters the visitor
         * method visitComment() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitComment(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>CR</tt> contains the unique object representing the cr
     * catcode.
     */
    CR(5) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.CR;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "cr";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For CR characters the visitor method
         * visitActive() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitCr(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>ESCAPE</tt> contains the unique object representing the
     * escape catcode used to initiate control sequences.
     */
    ESCAPE(0) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.ESCAPE;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "escape";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For escape characters the visitor method
         * visitEscape() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitEscape(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>IGNORE</tt> contains the unique object representing the
     * ignore catcode.
     */
    IGNORE(9) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.IGNORE;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "ignore";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For ignored characters the visitor method
         * visitIgnore() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitIgnore(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>INVALID</tt> contains the unique object representing the
     * invalid catcode.
     */
    INVALID(15) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.INVALID;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "invalid";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For invalid characters the visitor method
         * visitInvalid() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitInvalid(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>LEFTBRACE</tt> contains the unique object representing
     * the left brace catcode.
     */
    LEFTBRACE(1) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.LEFTBRACE;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "leftbrace";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For left brace characters the visitor method
         * visitLeftBrace() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitLeftBrace(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>LETTER</tt> contains the unique object representing the
     * letter catcode.
     */
    LETTER(11) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.LETTER;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "letter";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For letter characters the visitor method
         * visitLetter() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitLetter(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>MACROPARAM</tt> contains the unique object representing
     * the macro parameter catcode.
     */
    MACROPARAM(6) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.MACROPARAM;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "macroparam";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For macro parameter characters the visitor
         * method visitMacroParam() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitMacroParam(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>MATHSHIFT</tt> contains the unique object representing
     * the math shift catcode.
     */
    MATHSHIFT(3) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.MATHSHIFT;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "mathshift";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For math shift characters the visitor method
         * visitMathShift() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitMathShift(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>OTHER</tt> contains the unique object representing the
     * other catcode.
     */
    OTHER(12) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.OTHER;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "other";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For other characters the visitor method
         * visitOther() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitOther(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>RIGHTBRACE</tt> contains the unique object representing
     * the right brace catcode.
     */
    RIGHTBRACE(2) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.RIGHTBRACE;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "rightbrace";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For right brace characters the visitor
         * method visitRightBrace() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitRightBrace(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>SPACE</tt> contains the unique object representing the
     * space catcode.
     */
    SPACE(10) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.SPACE;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "space";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For space characters the visitor method
         * visitSpace() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitSpace(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>SUBMARK</tt> contains the unique object representing the
     * sub mark catcode.
     */
    SUBMARK(8) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.SUBMARK;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "submark";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For sub mark characters the visitor method
         * visitSubMark() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitSubMark(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>SUPMARK</tt> contains the unique object representing the
     * super mark catcode.
     */
    SUPMARK(7) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.SUPMARK;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "supmark";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For sup mark characters the visitor method
         * visitSupMark() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitSupMark(arg1, arg2, arg3);
        }

    },

    /**
     * The constant <tt>TABMARK</tt> contains the unique object representing the
     * tab mark catcode.
     */
    TABMARK(4) {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2011L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Catcode.TABMARK;
        }

        /**
         * Get the string representation of the catcode.
         * 
         * @return the string representation
         */
        @Override
        public String toString() {

            return "tabmark";
        }

        /**
         * Catcode supports the visitor pattern. This method is the entry for
         * visiting category codes. For tab mark characters the visitor method
         * visitTabMark() is invoked.
         * 
         * @param visitor the visitor
         * @param arg1 the first argument to pass
         * @param arg2 the second argument to pass
         * @param arg3 the third argument to pass
         * 
         * @return some return value form the visit
         * 
         * @throws Exception in case of an error
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Object visit(CatcodeVisitor visitor, Object arg1, Object arg2,
                Object arg3) throws Exception {

            return visitor.visitTabMark(arg1, arg2, arg3);
        }

    };

    /**
     * The field <tt>CATCODES</tt> contains an array of category codes where the
     * integer catcode can be used as index.
     */
    private static final Catcode[] CATCODES = {ESCAPE, LEFTBRACE, RIGHTBRACE,
            MATHSHIFT, TABMARK, CR, MACROPARAM, SUPMARK, SUBMARK, IGNORE,
            SPACE, LETTER, OTHER, ACTIVE, COMMENT, INVALID};

    /**
     * Getter for the maximal numerical catcode.
     * 
     * @return the maximal allowed category code
     */
    public static int getCatcodeMax() {

        return Catcode.values().length;
    }

    /**
     * Return a catcode for a given numerical value.
     * 
     * @param theCode the numerical code.
     * 
     * @return the catcode
     * 
     * @throws CatcodeException in case that the catcode is not in the allowed
     *         range
     */
    public static Catcode valueOf(int theCode) throws CatcodeException {

        if (theCode < 0 || theCode >= CATCODES.length) {
            throw new CatcodeException(Integer.toString(theCode));
        }

        return CATCODES[theCode];
    }

    /**
     * The field <tt>code</tt> contains the numerical value.
     */
    private int code;

    /**
     * Creates a new object.
     * 
     * @param code the numerical value
     */
    Catcode(int code) {

        this.code = code;
    }

    /**
     * Get the numerical representation for the Catcode.
     * 
     * @return the catcode as integer
     */
    public int getCode() {

        return code;
    }

    /**
     * Catcode support the visitor pattern. This method is the entry for
     * visiting the CATCODE. When this method is invoked in turn one of the
     * methods in {@link CatcodeVisitor} is called.
     * 
     * @param visitor the visitor
     * @param arg1 the first argument to pass
     * @param arg2 the second argument to pass
     * @param arg3 the third argument to pass
     * 
     * @return some return value form the visit
     * 
     * @throws Exception in case of an error
     */
    @SuppressWarnings("rawtypes")
    public abstract Object visit(CatcodeVisitor visitor, Object arg1,
            Object arg2, Object arg3) throws Exception;

}
