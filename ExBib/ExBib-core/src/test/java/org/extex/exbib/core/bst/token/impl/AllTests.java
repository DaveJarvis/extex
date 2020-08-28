/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run all tests.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TBlockTest.class,
        TCharTest.class,
        TFieldTest.class,
        TField2Test.class,
        TIntegerTest.class,
        TLiteralTest.class,
        TQLiteralTest.class,
        TStringTest.class
})
public final class AllTests {

    // using annotations only
}
