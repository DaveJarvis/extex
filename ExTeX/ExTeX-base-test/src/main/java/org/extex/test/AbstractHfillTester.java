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

package org.extex.test;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for horizontal filling primitives.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public abstract class AbstractHfillTester extends NoFlagsPrimitiveTester {

    /**
     * The field <tt>invocation</tt> contains the concatenation of primitive
     * name and arguments.
     */
    private String invocation = "";

    /**
     * The field <tt>prepare</tt> contains the preparation code.
     */
    private String prepare = DEFINE_BRACES;

    /**
     * The field <tt>fil</tt> contains the the unit inserted.
     */
    private String fil;

    public AbstractHfillTester() {
    }

    public void setPrimitive(final String primitive) {
        super.setPrimitive( primitive );
        this.invocation = primitive;
    }

    @Override
    public void setPrepare( final String prepare ) {
        super.setPrepare( prepare );
        this.prepare = prepare;
    }

    public void setFil( final String fil ) {
        this.fil = fil;
    }

    /**
     * <testcase> Test case showing that the primitive i horizontal mode has a
     * natural width of 0pt. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHbox1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\hbox{a\\" + invocation + " b} \\end",
            // --- error channel ---
            "ab" + TERM);
    }

    /**
     * <testcase> Test case checking that <tt>\hfi*</tt> is ignored at the
     * beginning of a paragraph. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnore1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\" + invocation + "\\end ",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that <tt>\hfi*</tt> is ignored at the
     * beginning of a paragraph. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testIgnore2() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\font\\f cmr10 \\f\\hsize=100pt \\" + invocation + " x\\end ",
            // --- output channel ---
            "\\vbox(4.30554pt+0.0pt)x100.0pt\n" + //
                    ".\\hbox(4.30554pt+0.0pt)x100.0pt\n" + //

                    "..\\glue0.0pt plus " + fil + "\n" + "..x\n");
    }

    /**
     * <testcase> Test case showing that the primitive in vertical mode appears
     * like a space. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVbox1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\vbox to 12pt{a\\" + invocation + " b} \\end",
            // --- error channel ---
            "a b\n\n" + TERM);
    }

    // TODO implement more primitive specific test cases
}
