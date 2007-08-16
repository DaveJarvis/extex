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

package org.extex.backend.documentWriter.xml;

import java.io.File;
import java.util.Properties;

import org.extex.test.EqualityValidator;
import org.extex.test.ExTeXLauncher;

/**
 * Test for the xml backend (ttf).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XmlOutput02Test extends ExTeXLauncher {

    /**
     * The properties.
     */
    private static Properties prop = null;

    /**
     * Creates a new object.
     */
    public XmlOutput02Test() {

        super("xml backend test 02");

        setConfig("base-xml.xml");

        // delete temp files after the test
        new File("texput.log").deleteOnExit();

    }

    @Override
    protected void setUp() throws Exception {

        prop = new Properties(System.getProperties());
        prop.setProperty("extex.output", "xml");
        prop.setProperty("extex.trace.font.files", "true");
        prop.setProperty("extex.trace.input.files", "true");
        prop.setProperty("extex.launcher.loglevel", "ALL");
    }

    /**
     * Test: use default output with ttf font 'LinLibertine'.
     * 
     * @throws Exception if an error occurred.
     */
    public void testDefaultOutput() throws Exception {

        // use default output
        prop.remove("extex.output");
        assertOutput(prop, // --- input code ---
            "\\font\\hugo=LinLibertine " + "\\hugo " + "Hugo " + "\\end",
            /* logValidator */null /* null */, /* outputValidator */
            new EqualityValidator("test", "Hugo" + TERM));

    }

    /**
     * Test: page size.
     * 
     * @throws Exception if an error occurred.
     */
    public void testPageSize() throws Exception {

        String[] comment = new String[]{"pagesize" // 1
                , "pagesize width" // 2
                , "Orientation" // 3
                , "pagesize height" // 4
        };
        String[] xpath =
                new String[]{"/root/parameter/param[@name=\"Paper\"]/@value" // 1
                        , "/root/page[1]/@paperwidth_mm" // 2
                        , "/root/parameter/param[@name=\"Orientation\"]/@value" // 3
                        , "/root/page[1]/@paperheight_mm" // 4
                };
        String[] expected = new String[]{"A4" // 1
                , "210" // 2
                , "Portrait" // 3
                , "297" // 4
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=LinLibertine " + "\\hugo " + "Hugo " + "\\end",
            /* logValidator */new PrintValidator(), /* outputValidator */
            new XmlValidator(comment, xpath, expected));

    }

    /**
     * Test the manager.
     * 
     * @throws Exception if an error occurred.
     */
    public void testManager01() throws Exception {

        String[] comment = new String[]{"manager exists" // 1
                , "font LinLibertine" // 2
                , "count chars" // 3
                , "test first char" // 4
                , "test last char" // 5
        };
        String[] xpath = new String[]{"count(/root/managerinfo)" // 1
                , "/root/managerinfo/manager[1]/@font" // 2
                , "count(/root/managerinfo/manager[1]/char)" // 3
                , "/root/managerinfo/manager[1]/char[1]/@id" // 4
                , "/root/managerinfo/manager[1]/char[4]/@id" // 5
        };
        String[] expected = new String[]{"1" // 1
                , "LinLibertine" // 2
                , "4" // 3
                , "72" // 4
                , "111" // 4
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=LinLibertine " + "\\hugo " + "Hugo " + "\\end",
            /* logValidator */null, /* outputValidator */
            new XmlValidator(comment, xpath, expected));
    }

    /**
     * Test the manager with identical chars.
     * 
     * @throws Exception if an error occurred.
     */
    public void testManager02() throws Exception {

        String[] comment = new String[]{"manager exists" // 1
                , "font LinLibertine" // 2
                , "count chars" // 3
                , "test first char" // 4
                , "test last char" // 5
        };
        String[] xpath = new String[]{"count(/root/managerinfo)" // 1
                , "/root/managerinfo/manager[1]/@font" // 2
                , "count(/root/managerinfo/manager[1]/char)" // 3
                , "/root/managerinfo/manager[1]/char[1]/@id" // 4
                , "/root/managerinfo/manager[1]/char[4]/@id" // 5
        };
        String[] expected = new String[]{"1" // 1
                , "LinLibertine" // 2
                , "4" // 3
                , "72" // 4
                , "111" // 5
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=LinLibertine " + "\\hugo " + "Hugo Hugo HHHH"
                    + "\\end",
            /* logValidator */null, /* outputValidator */
            new XmlValidator(comment, xpath, expected));
    }

    /**
     * Test the manager with tow fonts.
     * 
     * @throws Exception if an error occurred.
     */
    public void testManager03() throws Exception {

        String[] comment = new String[]{"manager exists" // 1
                , "font LinLibertine" // 2
                , "count chars" // 3
                , "test first char" // 4
                , "test last char" // 5
                , "font cmr10" // 6
                , "count chars cmr10" // 7
        };
        String[] xpath = new String[]{"count(/root/managerinfo)" // 1
                , "/root/managerinfo/manager[2]/@font" // 2
                , "count(/root/managerinfo/manager[2]/char)" // 3
                , "/root/managerinfo/manager[2]/char[1]/@id" // 4
                , "/root/managerinfo/manager[2]/char[4]/@id" // 5
                , "/root/managerinfo/manager[1]/@font" // 6
                , "count(/root/managerinfo/manager[1]/char)" // 7
        };
        String[] expected = new String[]{"1" // 1
                , "LinLibertine" // 2
                , "4" // 3
                , "72" // 4
                , "111" // 5
                , "cmr10" // 6
                , "8" // 7
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=LinLibertine " + "\\hugo " + "Hugo Hugo HHHH"
                    + "\\font\\peter=cmr10 " + "\\peter "
                    + "Peter Peter Hugo HHHH" + "\\end",
            /* logValidator */null, /* outputValidator */
            new XmlValidator(comment, xpath, expected));
    }
}
