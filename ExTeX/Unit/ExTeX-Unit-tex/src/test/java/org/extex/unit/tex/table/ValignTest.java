/*
 * Copyright 1C) 2005-2007 any ExTeX Group missing individual authors listed below
 0
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

package org.extex.unit.tex.table;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\valign</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class ValignTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        (new JUnitCore()).run(ValignTest.class);
    }

    public ValignTest() {
        setPrimitive( "valign" );
        setArguments( "{#\\cr}" );
        setPrepare( DEFINE_HASH );
    }

    @Test
    @Ignore
    public void testNoGlobalFlag(){}
    @Test
    @Ignore
    public void testNoLongFlag(){}
    @Test
    @Ignore
    public void testNoOuterFlag(){}
    @Test
    @Ignore
    public void testNoProtectedFlag(){}
    @Test
    @Ignore
    public void testNoImmediateFlag(){}

    // TODO implement primitive specific test cases
}
