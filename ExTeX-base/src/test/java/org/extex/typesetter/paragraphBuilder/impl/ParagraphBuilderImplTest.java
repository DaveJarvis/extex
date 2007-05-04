/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.paragraphBuilder.impl;

import junit.framework.TestCase;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ParagraphBuilderImplTest extends TestCase {

    /**
     * Inner class for the typesetter options.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private static class MockOptions implements TypesetterOptions {

        /**
         * Getter for a count register.
         *
         * @param name the name of the register
         *
         * @return the content of the count register
         *
         * @see org.extex.typesetter.TypesetterOptions#getCountOption(java.lang.String)
         */
        public FixedCount getCountOption(String name) {

            if (name.equals("tracingparagraphs")) {
                return new Count(0);
            } else if (name.equals("pretolerance")) {
                return new Count(300);
            } else if (name.equals("tolerance")) {
                return new Count(200);
            } else if (name.equals("hyphenpenalty")) {
                return new Count(20);
            } else if (name.equals("exhyphenpenalty")) {
                return new Count(30);
            }
            return new Count(0);
        }

        /**
         * Getter for a dimen register.
         *
         * @param name the name of the register
         *
         * @return the content of the dimen register
         *
         * @see org.extex.typesetter.TypesetterOptions#getDimenOption(java.lang.String)
         */
        public FixedDimen getDimenOption(String name) {

            if (name.equals("hsize")) {
                return new Dimen(300 * Dimen.ONE);
            }
            return Dimen.ZERO_PT;
        }

        /**
         * Getter for a current font register.
         *
         * @param name the name or the number of the register
         *
         * @return the named font register or <code>null</code> if none is set
         *
         * @see org.extex.typesetter.TypesetterOptions#getFont(
         *      java.lang.String)
         */
        public Font getFont(String name) {

            return null;
        }

        /**
         * Getter for a glue register.
         *
         * @param name the name of the register
         *
         * @return the content of the glue register
         *
         * @see org.extex.typesetter.TypesetterOptions#getGlueOption(
         *      java.lang.String)
         */
        public FixedGlue getGlueOption(String name) {

            if (name.equals("parfillskip")) {
                return new Glue(1000);
            }
            return new Glue(0);
        }

        /**
         * Getter for the lccode mapping of upper case characters to their
         * lower case equivalent.
         *
         * @param uc the upper case character
         *
         * @return the lower case equivalent or null if none exists
         *
         * @see org.extex.typesetter.TypesetterOptions#getLccode(
         *      org.extex.core.UnicodeChar)
         */
        public UnicodeChar getLccode(UnicodeChar uc) {

            return null;
        }

        /**
         * Getter for the current name space.
         *
         * @return the current name space
         *
         * @see org.extex.typesetter.TypesetterOptions#getNamespace()
         */
        public String getNamespace() {

            return null;
        }

        /**
         * Getter for the paragraph shape.
         *
         * @return the paragraph shape or <code>null</code> if no special shape
         *   is present
         *
         * @see org.extex.typesetter.TypesetterOptions#getParshape()
         */
        public ParagraphShape getParshape() {

            return null;
        }

        /**
         * Getter for the token factory. The token factory can be used to get new
         * tokens of some kind.
         *
         * @return the token factory
         *
         * @see org.extex.typesetter.TypesetterOptions#getTokenFactory()
         */
        public TokenFactory getTokenFactory() {

            return null;
        }

        /**
         * Getter for the typesetting context.
         *
         * @return the typesetting context
         *
         * @see org.extex.typesetter.TypesetterOptions#getTypesettingContext()
         */
        public TypesettingContext getTypesettingContext() {

            return null;
        }

        /**
         * Getter for the typesetting context factory.
         *
         * @return the typesetting context factory
         *
         * @see org.extex.typesetter.TypesetterOptions#getTypesettingContextFactory()
         */
        public TypesettingContextFactory getTypesettingContextFactory() {

            return null;
        }

        /**
         * Setter for the paragraph shape.
         *
         * @param shape the new paragraph shape
         *
         * @see org.extex.typesetter.TypesetterOptions#setParshape(
         *      org.extex.typesetter.paragraphBuilder.ParagraphShape)
         */
        public void setParshape(ParagraphShape shape) {

            // nothing to do
        }

        /**
         * {@inheritDoc}
         *
         * @see org.extex.typesetter.TypesetterOptions#setCountOption(java.lang.String, long)
         */
        public void setCountOption(String name, long value) {

            // nothing to do
        }

        /**
         * Getter for a muskip register.
         *
         * @param name the name of the register
         *
         * @return the muskip register value
         *
         * @see org.extex.typesetter.TypesetterOptions#getMuskip(java.lang.String)
         */
        public Muskip getMuskip(String name) {

            // TODO gene: getMuskip unimplemented
            return null;
        }
    }

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(ParagraphBuilderImplTest.class);
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        ParagraphBuilder builder = new ParagraphBuilderImpl();
        builder.setOptions(new MockOptions());
        HorizontalListNode nodes = new HorizontalListNode();
        NodeList n = builder.build(nodes);
        assertEquals(//
            "\\vbox(0.0pt+0.0pt)x0.01526pt\n"
                    + ".\\hbox(0.0pt+0.0pt)x0.01526pt\n"
                    + "..\\penalty 10000\n" + "..\\glue0.01526pt", n.toString());
    }
}
