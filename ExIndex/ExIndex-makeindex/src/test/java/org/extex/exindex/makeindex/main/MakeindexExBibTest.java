/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;

import org.junit.Test;

/**
 * This is a real life test suite for {@link Makeindex}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7821 $
 */
public class MakeindexExBibTest extends AbstractTester {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.makeindex.main.AbstractTester#makeInstance()
     */
    @Override
    protected Makeindex makeInstance() {

        return new Makeindex();
    }

    /**
     * <testcase> An index can be sorted. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test1() throws IOException {

        Locale.setDefault(Locale.US);
        Reader r = null;
        try {
            run(
                new String[]{"src/test/resources/makeindex/exbib-manual.idx",
                        "-s", "src/test/resources/makeindex/doc.ist", "-out",
                        "target/exbib-manual.ind"}, //
                BANNER
                        + "Scanning style file src/test/resources/makeindex/doc.ist...done (7 attributes\n"
                        + "redefined, 0 ignored).\n"
                        + "Scanning input file\n"
                        + "src/test/resources/makeindex/exbib-manual.idx...done (714 entries accepted, 0\n"
                        + "rejected)\n"
                        + "Sorting...done (6671 comparisons).\n"
                        + "Generating output file target/exbib-manual.ind...done (0 entries written, 0\n"
                        + "warnings).\n"
                        + "Output written in target/exbib-manual.ind.\n"
                        + "Transcript written in src/test/resources/makeindex/exbib-manual.ilg.\n",
                "", 0);
            File ind = new File("target/exbib-manual.ind");
            assertTrue("Missing " + ind.toString(), ind.exists());
            r = new BufferedReader(new FileReader(ind));
            StringBuilder buffer = new StringBuilder();
            for (int c = r.read(); c >= 0; c = r.read()) {
                if (c != '\r') {
                    buffer.append((char) c);
                }
            }
            r.close();
            r = null;
            r = new BufferedReader(new FileReader(//
                "src/test/resources/makeindex/exbib-manual.ind"));
            StringBuilder expected = new StringBuilder();
            for (int c = r.read(); c >= 0; c = r.read()) {
                if (c != '\r') {
                    expected.append((char) c);
                }
            }
            assertEquals(expected.toString(), buffer.toString());
        } finally {
            if (r != null) {
                r.close();
            }
            new File("target/exbib-manual.ind").delete();
        }
    }

}
