/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run all tests.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestAddPeriod.class, //
        TestCallType.class, //
        TestChangeCase.class, //
        TestChrToInt.class, //
        TestCite.class, //
        TestConcat.class, //
        TestDuplicate.class, //
        TestEmpty.class, //
        TestEq.class, //
        TestFormatName.class, //
        TestFormatName099.class, //
        TestGt.class, //
        TestIf.class, //
        TestIntToChr.class, //
        TestIntToStr.class, //
        TestLt.class, //
        TestMinus.class, //
        TestMissing.class, //
        TestNewline.class, //
        TestNumNames.class, //
        TestPlus.class, //
        TestPop.class, //
        TestPreamble.class, //
        TestPurify.class, //
        TestQuote.class, //
        TestSet.class, //
        TestSkip.class, //
        TestStack.class, //
        TestSubstring.class, //
        TestSwap.class, //
        TestTextLength.class, //
        TestTextPrefix.class, //
        TestTop.class, //
        TestType.class, //
        TestWarning.class, //
        TestWhile.class, //
        TestWidth.class, //
        TestWrite.class //
})
public final class AllTests {

    /**
     * Creates a new object.
     */
    private AllTests() {

        super();
    }

}
