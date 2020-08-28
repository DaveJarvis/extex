/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.ant.latex.make;

import java.io.File;

import org.extex.ant.latex.MakeException;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class NetTest {

    /**
     * <testcase> An empty net can not find a rule for <tt>null</tt>.
     * </testcase>
     * 
     * @throws MakeException in case of an error
     */
    @Test(expected = MakeException.class)
    public void test1() throws MakeException {

        new Net().make(null);
    }

    /**
     * <testcase> An empty net can not find a rule for the empty File.
     * </testcase>
     * 
     * @throws MakeException in case of an error
     */
    @Test(expected = MakeException.class)
    public void test2() throws MakeException {

        new Net().make(new File(""));
    }

    /**
     * <testcase> An empty net can not find a rule for a non existent File.
     * </testcase>
     * 
     * @throws MakeException in case of an error
     */
    @Test(expected = MakeException.class)
    public void test3() throws MakeException {

        new Net().make(new File("does_not_exist"));
    }

}
