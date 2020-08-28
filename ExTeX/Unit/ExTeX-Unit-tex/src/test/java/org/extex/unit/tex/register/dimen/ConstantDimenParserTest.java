/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.dimen;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the constant dimen parser.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConstantDimenParserTest extends ExTeXLauncher {


    public ConstantDimenParserTest() {

    }

    /**
     * Test method for {@link org.extex.base.parser.ConstantDimenParser#parse(
     * org.extex.interpreter.context.Context, org.extex.interpreter.TokenSource,
     * org.extex.typesetter.Typesetter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse1() throws Exception {

        assertOutput("\\mathchardef\\T=1000 " + "\\dimen0=-\\T pt"
                + "\\showthe\\dimen0 " + "\\end",

            "> -1000.0pt.\n",

            "");
    }

    /**
     * Test method for {@link org.extex.base.parser.ConstantDimenParser#parse(
     * org.extex.interpreter.context.Context, org.extex.interpreter.TokenSource,
     * org.extex.typesetter.Typesetter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse2() throws Exception {

        assertOutput("\\dimen0=--12 pt" + "\\showthe\\dimen0 " + "\\end",

            "> 12.0pt.\n",

            "");
    }

    /**
     * Test method for {@link org.extex.base.parser.ConstantDimenParser#parse(
     * org.extex.interpreter.context.Context, org.extex.interpreter.TokenSource,
     * org.extex.typesetter.Typesetter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse3() throws Exception {

        assertOutput("\\dimen0=- -12 pt" + "\\showthe\\dimen0 " + "\\end",

            "> 12.0pt.\n",

            "");
    }

}
