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

package org.extex.unit.tex.file;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.File;
import java.net.URL;

/**
 * This is a test suite for the primitive {@code \input}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class InputTest extends NoFlagsPrimitiveTester {

    /**
     * Derive the location of an empty file.
     */
    private final String mEmptyTex = getResourcePath( "tex/empty.tex");

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(String[] args) {
        (new JUnitCore()).run(InputTest.class);
    }

    public InputTest() {
        setPrimitive( "input" );
        setArguments( " " + mEmptyTex + " " );
        setPrepare( "\\nonstopmode" );
    }

    /**
     * <testcase primitive="\input"> Test case checking that a {@code \input}
     * works. 
     *
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertSuccess(// --- input code ---
            "\\input " + mEmptyTex,
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\input"> Test case checking that a {@code \input}
     * works. 
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\input DoesNotExist",
            // --- output channel ---
            "I can't find file `DoesNotExist'");
    }

    /**
     * Uses this class's classloader to find the resource with the given name.
     *
     * @param filename The file to find.
     * @return The absolute path to the file.
     */
    @SuppressWarnings("SameParameterValue")
    private String getResourcePath( final String filename ) {
        final URL url = getClass().getClassLoader().getResource( filename );
        assert url != null;

        final File f = new File( url.getFile() );
        return f.getAbsolutePath();
    }
}
