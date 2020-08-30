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

package org.extex.interpreter.interaction;

import org.extex.core.exception.GeneralException;
import org.extex.interpreter.exception.InteractionUnknownException;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * This class provides a type-save enumeration of the interactions styles of
 * εχTeX. It defined constants for the supported interaction modes.
 * In addition it supports the visitor pattern to react on them.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class Interaction implements Serializable {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * The constant {@code BATCHMODE} contains the constant for batch mode.
     */
    public static final Interaction BATCHMODE = new Interaction() {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 1L;

    @Override
        public String getIndex() {

            return "0";
        }

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Interaction.BATCHMODE;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "\\batchmode";
        }

        /**
    *      java.lang.Object, java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public boolean visit(InteractionVisitor visitor, Object arg1,
                Object arg2, Object arg3) throws GeneralException {

            return visitor.visitBatchmode(arg1, arg2, arg3);
        }
    };

    /**
     * The constant {@code ERRORSTOPMODE} contains the constant for error stop
     * mode.
     */
    public static final Interaction ERRORSTOPMODE = new Interaction() {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 1L;

    @Override
        public String getIndex() {

            return "3";
        }

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Interaction.ERRORSTOPMODE;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "\\errorstopmode";
        }

        /**
    *      java.lang.Object, java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public boolean visit(InteractionVisitor visitor, Object arg1,
                Object arg2, Object arg3) throws GeneralException {

            return visitor.visitErrorstopmode(arg1, arg2, arg3);
        }

    };

    /**
     * The constant {@code NONSTOPMODE} contains the constant for non-stop
     * mode.
     */
    public static final Interaction NONSTOPMODE = new Interaction() {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 1L;

    @Override
        public String getIndex() {

            return "1";
        }

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Interaction.NONSTOPMODE;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "\\nonstopmode";
        }

        /**
    *      java.lang.Object, java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public boolean visit(InteractionVisitor visitor, Object arg1,
                Object arg2, Object arg3) throws GeneralException {

            return visitor.visitNonstopmode(arg1, arg2, arg3);
        }

    };

    /**
     * The constant {@code SCROLLMODE} contains the constant for scroll mode.
     */
    public static final Interaction SCROLLMODE = new Interaction() {

        /**
         * The constant {@code serialVersionUID} contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 1L;

    @Override
        public String getIndex() {

            return "2";
        }

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return Interaction.SCROLLMODE;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "\\scrollmode";
        }

        /**
    *      java.lang.Object, java.lang.Object, java.lang.Object)
         */
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public boolean visit(InteractionVisitor visitor, Object arg1,
                Object arg2, Object arg3) throws GeneralException {

            return visitor.visitScrollmode(arg1, arg2, arg3);
        }

    };

    /**
     * The constant {@code MODE_MAP} contains the list for mapping integers to
     * modes.
*/
    private static final Interaction[] MODE_MAP =
            {BATCHMODE, NONSTOPMODE, SCROLLMODE, ERRORSTOPMODE};

    /**
     * This is a factory method for interaction modes. It maps numerical values
     * to interaction mode instances. The instances are reused and may be
     * compared with ==.
     * 
     * @param mode the integer value for the interaction mode
     * 
     * @return the appropriate interaction mode constant
     * 
     * @throws InteractionUnknownException in case that the numerical value is
     *         out of range and does not correspond to an interaction mode
     */
    public static Interaction get(int mode) throws InteractionUnknownException {

        if (mode < 0 || mode >= MODE_MAP.length) {
            throw new InteractionUnknownException(Integer.toString(mode));
        }

        return MODE_MAP[mode];
    }

    /**
     * Find the integer number corresponding to an interaction
     * 
     * @param mode the mode to identify
     * 
     * @return the number of the mode
     * 
     * @throws InteractionUnknownException in case of an error
     */
    public static int get(Interaction mode) throws InteractionUnknownException {

        for (int i = 0; i < MODE_MAP.length; i++) {
            if (mode == MODE_MAP[i]) {
                return i;
            }
        }

        throw new InteractionUnknownException(mode == null
                ? ""
                : mode.toString());
    }

    /**
     * This is a factory method for interaction modes. It maps numerical values
     * to interaction mode instances. The instances are reused and may be
     * compared with ==.
     * 
     * Allowed values are the numbers 0 to 3 or the symbolic names batchmode
     * (0), nonstopmode (1), scrollmode (2), and errorstopmode (3). The symbolic
     * names can be abbreviated up to the least unique prefix, i.e. up to one
     * character.
     * 
     * @param mode the string representation for the mode
     * 
     * @return the appropriate interaction mode constant
     * 
     * @throws InteractionUnknownException in case that something is passed in
     *         which can not be interpreted as interaction mode
     */
    public static Interaction get(String mode)
            throws InteractionUnknownException {

        if (mode == null || "".equals(mode)) {
            throw new InteractionUnknownException("");
        } else if ("batchmode".startsWith(mode) || mode.equals("0")) {
            return BATCHMODE;
        } else if ("nonstopmode".startsWith(mode) || mode.equals("1")) {
            return NONSTOPMODE;
        } else if ("scrollmode".startsWith(mode) || mode.equals("2")) {
            return SCROLLMODE;
        } else if ("errorstopmode".startsWith(mode) || mode.equals("3")) {
            return ERRORSTOPMODE;
        }

        throw new InteractionUnknownException(mode);
    }

    /**
     * Creates a new object. This constructor is private to avoid that other
     * interaction modes than the predefined ones are used.
     */
    protected Interaction() {

    }

    /**
     * Get the numeric index of the interaction mode. According to the
     * definition of TeX the following mapping holds:
     * <table>
     * <caption>TBD</caption>
     * <tr>
* <td>BatchMode</td>
     * <td>0</td>
     * </tr>
     * <tr>
     * <td>NonstopMode</td>
     * <td>1</td>
     * </tr>
     * <tr>
     * <td>ScrollMode</td>
     * <td>2</td>
     * </tr>
     * <tr>
     * <td>ErrorstopMode</td>
     * <td>3</td>
     * </tr>
     * </table>
     * 
     * @return the numeric index
     */
    public abstract String getIndex();

    /**
     * This method provides an entry point for the visitor pattern.
     * 
     * @param visitor this argument contains the visitor which has initiated the
     *        request.
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * 
     * @return a boolean indicator
     * 
     * @throws GeneralException in case of an error
     * 
     * @see InteractionVisitor
     */
    @SuppressWarnings("rawtypes")
    public abstract boolean visit(InteractionVisitor visitor, Object arg1,
            Object arg2, Object arg3) throws GeneralException;

}
