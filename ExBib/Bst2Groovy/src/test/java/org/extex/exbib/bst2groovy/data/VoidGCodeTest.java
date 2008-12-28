/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.junit.Test;

/**
 * This is a test suite for VoidGCode.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VoidGCodeTest {

    /**
     * <testcase> toString() returns the empty string. </testcase>
     * 
     */
    @Test
    public void test1() {

        VoidGCode code = new VoidGCode() {

            @Override
            public void print(CodeWriter writer, String prefix)
                    throws IOException {

                //
            }
        };
        assertEquals("", code.toString());
    }

}
