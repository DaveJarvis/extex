/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.pdf.api.destination;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.pdf.api.exception.PdftexDestinationTypeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.RuleNode;

/**
 * This is the abstract base class for destination types in PDF.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class DestType implements Serializable {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * The constant {@code FIT} contains the destination type fit.
     */
    public static final DestType FIT = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return FIT;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "fit";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFit(this);
        }

    };

    /**
     * The constant {@code FITB} contains the destination type fitb.
     */
    public static final DestType FITB = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return FITB;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "fitb";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitb(this);
        }

    };

    /**
     * The constant {@code FITBH} contains the destination type fitbh.
     */
    public static final DestType FITBH = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return FITBH;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "fitbh";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitbh(this);
        }

    };

    /**
     * The constant {@code FITBV} contains the destination type fitbv.
     */
    public static final DestType FITBV = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return FITBV;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "fitbv";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitbv(this);
        }

    };

    /**
     * The constant {@code FITH} contains the destination type fith.
     */
    public static final DestType FITH = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return FITH;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "fith";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFith(this);
        }

    };

    /**
     * The constant {@code FITV} contains the destination type fitv.
     */
    public static final DestType FITV = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return FITV;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "fitv";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitFitv(this);
        }

    };

    /**
     * The constant {@code XYZ} contains the destination type xyz.
     */
    public static final DestType XYZ = new DestType() {

        /**
         * The field {@code serialVersionUID} contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 2007L;

        /**
         * Return the singleton constant object after the serialized instance
         * has been read back in.
         *
         * @return the one and only instance of this object
         *
         * @throws ObjectStreamException never
         */
        protected Object readResolve() throws ObjectStreamException {

            return XYZ;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "xyz";
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.pdf.api.destination.DestType#visit(
         *      org.extex.pdf.api.destination.DestinationVisitor)
         */
        @Override
        public Object visit(DestinationVisitor visitor) {

            return visitor.visitXyz(this);
        }

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
            Typesetter typesetter, CodeToken name)
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
                    width = source.parseDimen(context, source, typesetter);
                } else if (source.getKeyword(context, "height")) {
                    height = source.parseDimen(context, source, typesetter);
                } else if (source.getKeyword(context, "depth")) {
                    depth = source.parseDimen(context, source, typesetter);
                } else {
                    break;
                }
            }

            return new FitrDestType(new RuleNode(width, height, depth, null,
                true));

        } else if (source.getKeyword(context, "zoom")) {
            long zoom = source.parseNumber(context, source, typesetter);
            return new ZoomDestType(zoom);
        }

        throw new PdftexDestinationTypeException(name.toText());
    }


    protected DestType() {

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
