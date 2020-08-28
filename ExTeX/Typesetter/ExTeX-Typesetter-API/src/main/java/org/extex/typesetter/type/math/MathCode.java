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

import java.io.Serializable;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.type.noad.MathGlyph;

/**
 * This class represents a mathematical character. It consists of a class, a
 * family and a character code.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MathCode implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2006L;

    /**
     * The constant <tt>CHAR_MASK</tt> contains the mask for filtering a
     * character code from an integer.
     */
    private static final int CHAR_MASK = 0xff;

    /**
     * The field <tt>CLASS_SHIFT</tt> contains the shift value for the class.
     */
    private static final int CLASS_SHIFT = 12;

    /**
     * The constant <tt>FAMILY_MASK</tt> contains the mask for filtering the
     * family from an integer.
     */
    private static final int FAMILY_MASK = 0xf;

    /**
     * The field <tt>mathGlyph</tt> contains the glyph.
     */
    private MathGlyph mathGlyph;

    /**
     * The field <tt>mathClass</tt> contains the class.
     */
    private MathClass mathClass;

    /**
     * Creates a new object.
     * 
     * @param code the integer to analyze for the desired field values
     * 
     * @throws HelpingException in case of an error
     */
    public MathCode(long code) throws HelpingException {

        if (code < 0 || code > 0x8000) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(MathCode.class),
                "TTP.InvalidCode",
                Long.toString(code));
        } else if (code == 0x8000) {
            mathClass = null;
            mathGlyph = null;
        } else {
            mathClass = MathClass.getMathClass((int) (code >> CLASS_SHIFT));
            mathGlyph =
                    new MathGlyph((int) (code >> 8) & FAMILY_MASK,
                        UnicodeChar.get((int) (code & CHAR_MASK)));
        }
    }

    /**
     * Creates a new object.
     * 
     * @param mathClass the class
     * @param mathGlyph the glyph
     */
    public MathCode(MathClass mathClass, MathGlyph mathGlyph) {

        this.mathClass = mathClass;
        this.mathGlyph = mathGlyph;
    }

    /**
     * Getter for mathClass.
     * 
     * @return the mathClass.
     */
    public MathClass getMathClass() {

        return mathClass;
    }

    /**
     * Getter for mathGlyph.
     * 
     * @return the mathGlyph
     */
    public MathGlyph getMathGlyph() {

        return this.mathGlyph;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return mathClass.toString() + " " + mathGlyph.toString();
    }

    /**
     * Print the instance to a StringBuilder.
     * 
     * @param sb the target string buffer
     */
    public void toString(StringBuilder sb) {

        mathClass.toString(sb);
        sb.append(' ');
        mathGlyph.toString(sb);
    }

}
