/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.count;

import org.extex.interpreter.primitives.register.count.AbstractCountRegisterTester;

/**
 * This is a test suite for the primitive <tt>\lastlinefit</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class LastlinefitTest extends AbstractCountRegisterTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(LastlinefitTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public LastlinefitTest(String arg) {

        super(arg, "lastlinefit", "", "0");
        setConfig("etex-test");
    }

    //TODO implement the primitive specific test cases

}