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

package org.extex.doctools.pomCollector;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.extex.doctools.pomCollector.Main;
import org.junit.Test;

/**
 * This is a test suite for the pom collector.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MainTest {

    /**
     * Test method for {@link org.extex.doctools.pomCollector.Main#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        StringWriter w = new StringWriter();
        new Main().run(w);
        assertEquals("%%\n\\begin{PomList}\\end{PomList}\n%%\n", //
            w.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.pomCollector.Main#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        Main main = new Main();
        main.addBase(".");
        StringWriter w = new StringWriter();
        main.run(w);
        assertEquals(
            "%%\n\\begin{PomList}\n"
                    + "  \\begin{Pom}{org.extex}{PomCollector}{Pom Collector}\\end{Pom}\n"
                    + "\\end{PomList}\n%%\n", //
            w.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.pomCollector.Main#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testAll() throws Exception {

        Main main = new Main();
        main.addBase("../..");
        main.omit(".svn");
        StringWriter w = new StringWriter();
        main.run(w);
        assertEquals("%%\n\\begin{PomList}??\\end{PomList}\n%%\n", w.toString()
            .replaceAll("\r", ""));
    }

}
