/*
 * Copyright (C) 2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.extex.toks;

import org.extex.interpreter.primitives.register.toks.AbstractToksRegisterTester;

/**
 * This is a test suite for the primitive <tt>\everymathend</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4697 $
 */
public class EverymathendTest extends AbstractToksRegisterTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(EverymathendTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public EverymathendTest(String arg) {

        super(arg, "everymathend", "", "");
        setConfig("extex-test");
    }

    //TODO implement more primitive specific test cases

}
