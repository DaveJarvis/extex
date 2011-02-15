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

package org.extex.typesetter.type.noad;

import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.math.MathClass;
import org.extex.typesetter.type.math.MathClassVisitor;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;

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
    private static final MathClassVisitor<Noad, Noad, TypesettingContext> VISITOR =
            new MathClassVisitor<Noad, Noad, TypesettingContext>() {

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitBinary(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitBinary(Noad noad, TypesettingContext arg) {

                    return new BinaryNoad(noad, arg);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitClosing(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitClosing(Noad noad, TypesettingContext arg) {

                    return new CloseNoad(noad, arg);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitLarge(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitLarge(Noad noad, TypesettingContext arg) {

                    if (arg instanceof MathDelimiter) {
                        return new CharNoad(((MathDelimiter) noad)
                            .getLargeChar(), arg);
                    }
                    // TODO gene: visitLarge() partially unimplemented
                    throw new RuntimeException("unimplemented");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitOpening(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitOpening(Noad noad, TypesettingContext arg) {

                    return new OpenNoad(noad, arg);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitOrdinary(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitOrdinary(Noad noad, TypesettingContext arg) {

                    return new CharNoad((MathGlyph) noad, arg);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitPunctation(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitPunctation(Noad noad, TypesettingContext arg) {

                    return new PunctationNoad(noad, arg);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitRelation(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitRelation(Noad noad, TypesettingContext arg) {

                    return new RelationNoad(noad, arg);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.interpreter.type.math.MathClassVisitor#visitVariable(
                 *      java.lang.Object, java.lang.Object)
                 */
                public Noad visitVariable(Noad noad, TypesettingContext arg) {

                    // TODO gene: difference to ordinary ??
                    return new CharNoad((MathGlyph) noad, arg);
                }
            };

    /**
     * Creates a new object.
     * 
     */
    public NoadFactory() {

    }

    /**
     * Provides an instance of a {@link Noad Noad} of the appropriate type.
     * 
     * @param mc the code of the character to use
     * @param tc the typesetting context
     * 
     * @return an instance of a CharNoad
     */
    public Noad getNoad(MathCode mc, TypesettingContext tc) {

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
    public Noad getNoad(MathClass mathClass, MathGlyph glyph,
            TypesettingContext tc) {

        return (Noad) mathClass.visit(VISITOR, glyph, tc);
    }

}
