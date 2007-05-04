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

import org.extex.core.dimen.Dimen;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.scanner.CountParser;
import org.extex.scanner.DimenParser;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFit(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fit";
        }

        // TODO gene: readResolve()

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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitb(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitb";
        }

        // TODO gene: readResolve()

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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitbh(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitbh";
        }

        // TODO gene: readResolve()

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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitbv(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitbv";
        }

        // TODO gene: readResolve()

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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFith(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fith";
        }

        // TODO gene: readResolve()

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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitv(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "fitv";
        }

        // TODO gene: readResolve()

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
         * {@inheritDoc}
         * 
         * @see org.extex.unit.pdftex.util.destination.DestType#visit(
         *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
         */
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitXyz(this);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return "xyz";
        }

        // TODO gene: readResolve()

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
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static DestType parseDestType(Context context, TokenSource source,
            Typesetter typesetter, String name)
            throws HelpingException,
                TypesetterException {

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
                    width = DimenParser.parse(context, source, typesetter);
                } else if (source.getKeyword(context, "height")) {
                    height = DimenParser.parse(context, source, typesetter);
                } else if (source.getKeyword(context, "depth")) {
                    depth = DimenParser.parse(context, source, typesetter);
                } else {
                    break;
                }
            }

            return new FitrDestType(new RuleNode(width, height, depth, null,
                true));

        } else if (source.getKeyword(context, "zoom")) {
            long zoom = CountParser.scanInteger(context, source, typesetter);
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
    public abstract Object visit(DestinationVisitor visitor);

}
