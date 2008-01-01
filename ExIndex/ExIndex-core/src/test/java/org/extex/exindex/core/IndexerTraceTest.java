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

package org.extex.exindex.core;

import org.junit.Test;

/**
 * This is a test suite for the {@link Indexer} checking the markup-trace
 * output.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexerTraceTest extends AbstractIndexerTester {

    static {
        register("XREF.raw",
            "(indexentry :tkey (\"abc\") :xref (\"IV\") :attr \"see\")");
        register("style1", "(define-crossref-class \"see\" :unverified)"
                + "(markup-trace :on)" + "(define-letter-group \"a\")");
    }

    /**
     * <testcase> Check that null values as parameters are accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        runTest(makeList("style1"), makeList("XREF.raw"), "<INDEX:OPEN>\n"
                + "<LETTER-GROUP-LIST:OPEN>\n"
                + "<LETTER-GROUP:OPEN[default]>\n"
                + "<LETTER-GROUP:OPEN_HEAD[default]>\n"
                + "default<LETTER-GROUP:CLOSE_HEAD[default]>\n"
                + "<INDEXENTRY:OPEN[0]>\n" + "<KEYWORD-LIST:OPEN[0]>\n"
                + "<KEYWORD:OPEN[0]>\n" + "abc<KEYWORD:CLOSE[0]>\n"
                + "<CROSSREF-LIST:OPEN[see]>\n"
                + "<CROSSREF-LAYER-LIST:OPEN[see]>\n"
                + "<CROSSREF-LAYER:OPEN[see]>\n"
                + "IV<CROSSREF-LAYER:CLOSE[see]>\n"
                + "<CROSSREF-LAYER-LIST:CLOSE[see]>\n"
                + "<CROSSREF-LIST:CLOSE[see]>\n" + "<KEYWORD-LIST:CLOSE[0]>\n"
                + "<INDEXENTRY:CLOSE[0]>\n" + "<LETTER-GROUP:CLOSE[default]>\n"
                + "<LETTER-GROUP-LIST:CLOSE>\n" + "<INDEX:CLOSE>\n", //
            "");
    }

}
