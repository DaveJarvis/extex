/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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
package org.extex.ant.latex;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5413 $
 */
public class LatexTaskTest extends TestCase {

    /**
     * The constant <tt>VERBOSE</tt> contains the verbosity indicator.
     */
    private static final boolean VERBOSE = false;

    @Test
    public void test1() throws Exception {

        Process p = run("t1");
        assertEquals(1, p.exitValue());
    }

    @Test
    public void test2() throws Exception {

        Process p = run("t2");
        assertEquals(0, p.exitValue());
    }

    private Process run(String target) throws IOException, InterruptedException {

        Process p = Runtime.getRuntime()
                .exec(
                      new String[]{"ant", "-q", "-f",
                              "src/test/org/extex/ant/latex/testcases.xml",
                              target});
        int c;
        InputStream s;
        if (VERBOSE) {
            s = p.getInputStream();
            while ((c = s.read()) >= 0) {
                System.out.print((char) c);
            }
        }
        s = p.getErrorStream();
        while ((c = s.read()) >= 0) {
            System.out.print((char) c);
        }
        p.getOutputStream().close();
        p.waitFor();
        return p;
    }
}
