/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex.util.destination;

import java.io.Serializable;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.unit.pdftex.exception.InterpreterPdftexDestinationTypeException;

/**
 * This is the abstract base class for destination types in PDF.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4409 $
 */
public abstract class DestType implements Serializable {

    /**
     * The constant <tt>FIT</tt> contains the destination type fit.
     */
    public static final DestType FIT = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitFit(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fit";
        }

        //TODO gene: readResolve()

    };

    /**
     * The constant <tt>FITB</tt> contains the destination type fitb.
     */
    public static final DestType FITB = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitFitb(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitb";
        }

        //TODO gene: readResolve()

    };

    /**
     * The constant <tt>FITBH</tt> contains the destination type fitbh.
     */
    public static final DestType FITBH = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitFitbh(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitbh";
        }

        //TODO gene: readResolve()

    };

    /**
     * The constant <tt>FITBV</tt> contains the destination type fitbv.
     */
    public static final DestType FITBV = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitFitbv(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitbv";
        }

        //TODO gene: readResolve()

    };

    /**
     * The constant <tt>FITH</tt> contains the destination type fith.
     */
    public static final DestType FITH = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitFith(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fith";
        }

        //TODO gene: readResolve()

    };

    /**
     * The constant <tt>FITV</tt> contains the destination type fitv.
     */
    public static final DestType FITV = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitFitv(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitv";
        }

        //TODO gene: readResolve()

    };

    /**
     * The constant <tt>XYZ</tt> contains the destination type xyz.
     */
    public static final DestType XYZ = new DestType() {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * @see org.extex.unit.pdftex.util.destType.DestType#visit(
         *      org.extex.unit.pdftex.util.destType.DestTypeVisitor)
         */
        public Object visit(final DestinationVisitor visitor) {

            return visitor.visitXyz(this);
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "xyz";
        }

        //TODO gene: readResolve()

    };

    /**
     * Parse a destination type.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param name the name of the invoking primitive
     *
     * @return the destination type
     *
     * @throws InterpreterException in case of an error
     */
    public static DestType parseDestType(final Context context,
            final TokenSource source, final Typesetter typesetter,
            final String name) throws InterpreterException {

        if (source.getKeyword(context, "xyz")) {
            return XYZ;
        } else if (source.getKeyword(context, "fit")) {
            return FIT;
        } else if (source.getKeyword(context, "fith")) {
            return FITH;
        } else if (source.getKeyword(context, "fitv")) {
            return FITV;
        } else if (source.getKeyword(context, "fitb")) {
            return FITB;
        } else if (source.getKeyword(context, "fitbv")) {
            return FITBV;
        } else if (source.getKeyword(context, "fitbh")) {
            return FITBH;
        } else if (source.getKeyword(context, "fitr")) {

            Dimen width = null;
            Dimen height = null;
            Dimen depth = null;

            for (;;) {
                if (source.getKeyword(context, "width")) {
                    width = Dimen.parse(context, source, typesetter);
                } else if (source.getKeyword(context, "height")) {
                    height = Dimen.parse(context, source, typesetter);
                } else if (source.getKeyword(context, "depth")) {
                    depth = Dimen.parse(context, source, typesetter);
                } else {
                    break;
                }
            }

            return new FitrDestType(new RuleNode(width, height, depth, null,
                true));

        } else if (source.getKeyword(context, "zoom")) {
            long zoom = Count.scanInteger(context, source, typesetter);
            return new ZoomDestType(zoom);
        }

        throw new InterpreterPdftexDestinationTypeException(name);
    }

    /**
     * Creates a new object.
     */
    protected DestType() {

        super();
    }

    /**
     * This method is the entry point for the visitor pattern.
     *
     * @param visitor the visitor to call back
     *
     * @return an arbitrary return object
     */
    public abstract Object visit(final DestinationVisitor visitor);

}
