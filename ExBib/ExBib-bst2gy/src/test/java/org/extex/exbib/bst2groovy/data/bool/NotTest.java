/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data.bool;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class NotTest {

    /**
     * <testcase> Test method for
     * {@link org.extex.exbib.bst2groovy.data.bool.Not#print(org.extex.exbib.bst2groovy.io.CodeWriter, java.lang.String)}
     * . </testcase>
     */
    @Test
    public void testPrint1() throws Exception {

        Not n = new Not(new GIntegerConstant(34));
        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        n.print(codeWriter, "");
        codeWriter.close();
        assertEquals("! 34", w.toString());
    }

    /**
     * <testcase> Test method for
     * {@link org.extex.exbib.bst2groovy.data.bool.Not#print(org.extex.exbib.bst2groovy.io.CodeWriter, java.lang.String)}
     * . </testcase>
     */
    @Test
    public void testPrintAnd1() throws Exception {

        Not n =
                new Not(new And(new GIntegerConstant(12), new GIntegerConstant(
                    34)));
        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        n.print(codeWriter, "");
        codeWriter.close();
        assertEquals("! ( 34 && 12 )", w.toString());
    }

}