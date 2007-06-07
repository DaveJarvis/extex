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

package org.extex.typesetter.tc;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * This interface provides a limited set of writing directions. The writing
 * directions are defined as constants. The constructor is private to avoid that
 * additional directions are defined.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Direction implements Serializable {

    /**
     * This enumeration restricts the values which can be used as components of
     * a direction.
     */
    public enum Dir {
        /**
         * The field <tt>B</tt> contains the direction component for <i>bottom</i>.
         */
        B,
        /**
         * The field <tt>L</tt> contains the direction component for <i>left</i>.
         */
        L,
        /**
         * The field <tt>R</tt> contains the direction component for <i>right</i>.
         */
        R,
        /**
         * The field <tt>T</tt> contains the direction component for <i>top</i>.
         */
        T
    };

    /**
     * The constant <tt>LR</tt> contains the direction for left-to-right
     * languages.
     */
    public static final Direction LR = new Direction() {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2006L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return LR;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "LR";
        }

    };

    /**
     * The constant <tt>RL</tt> contains the direction for right-to-left
     * languages.
     */
    public static final Direction RL = new Direction() {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2006L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         * 
         * @return the one and only instance of this object
         * 
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return RL;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "RL";
        }

    };

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field <tt>beginningOfLine</tt> contains the direction at the
     * beginning of the line.
     */
    private Dir beginningOfLine;

    /**
     * The field <tt>beginningOfPage</tt> contains the direction at the
     * beginning of the page.
     */
    private Dir beginningOfPage;

    /**
     * The field <tt>topOfLine</tt> contains the direction at the top of the
     * line.
     */
    private Dir topOfLine;

    /**
     * Creates a new object.
     * 
     */
    private Direction() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param beginningOfPage the beginning of the page
     * @param beginningOfLine the beginning of the line
     * @param topOfLine the top of the line
     */
    public Direction(Dir beginningOfPage, Dir beginningOfLine, Dir topOfLine) {

        super();
        this.beginningOfPage = beginningOfPage;
        this.beginningOfLine = beginningOfLine;
        this.topOfLine = topOfLine;

    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return beginningOfPage.toString() + beginningOfLine.toString()
                + topOfLine.toString();
    }

}
