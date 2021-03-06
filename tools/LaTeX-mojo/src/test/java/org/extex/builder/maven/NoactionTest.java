/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.builder.maven;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for {@link LaTeXMojo}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NoactionTest {

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void test1() throws Exception {

        NoactionMojo mojo = new NoactionMojo();
        mojo.setFile(new File("src/test/resources/document1.tex"));
        mojo.execute();
    }

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void test2() throws Exception {

        NoactionMojo mojo = new NoactionMojo();
        mojo.setFile(new File("src/test/resources/document2.tex"));
        mojo.execute();
    }

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test3() throws Exception {

        NoactionMojo mojo = new NoactionMojo();
        assertNotNull(mojo);
        mojo.setFile(new File("src/test/resources/document3.tex"));
        mojo.execute();
    }

}
