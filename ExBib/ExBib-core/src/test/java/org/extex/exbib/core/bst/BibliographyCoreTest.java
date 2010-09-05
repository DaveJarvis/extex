/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst;

import org.extex.exbib.core.util.NotObservableException;
import org.junit.Test;

/**
 * Test suite for {@link BibliographyCore}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7609 $
 */
public class BibliographyCoreTest {

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NotObservableException.class)
    public void test1() throws Exception {

        new BibliographyCore(null, null).registerObserver(null, null);
    }

}
