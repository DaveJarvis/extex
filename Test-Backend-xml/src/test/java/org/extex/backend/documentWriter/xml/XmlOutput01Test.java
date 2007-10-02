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
 * Test for the xml backend.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XmlOutput01Test extends ExTeXLauncher {

    /**
     * The properties.
     */
    private static Properties prop = null;

    /**
     * Creates a new object.
     */
    public XmlOutput01Test() {

        super();

        setConfig("base-xml.xml");

        // delete temp files after the test
        new File("texput.log").deleteOnExit();

    }

    /**
     * TODO
     * 
     * @throws Exception
     */
    protected void setUp() throws Exception {

        prop = new Properties(System.getProperties());
        prop.setProperty("extex.output", "xml");
    }

    /**
     * Test: use default output with afm font 'fxlr'.
     * 
     * @throws Exception if an error occurred.
     */
    public void testDefaultOutput() throws Exception {

        // use default output
        prop.remove("extex.output");
        assertOutput(prop, // --- input code ---
            "\\font\\hugo=fxlr " + "\\hugo " + "Hugo " + "\\end",
            /* logValidator */null, /* outputValidator */
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
            "\\font\\hugo=fxlr " + "\\hugo " + "Hugo " + "\\end",
            /* logValidator */null, /* outputValidator */
            new XmlValidator(comment, xpath, expected));

    }

    /**
     * Test the manager.
     * 
     * @throws Exception if an error occurred.
     */
    public void testManager01() throws Exception {

        String[] comment = new String[]{"manager exists" // 1
                , "font fxlt" // 2
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
                , "fxlr" // 2
                , "4" // 3
                , "72" // 4
                , "111" // 4
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=fxlr " + "\\hugo " + "Hugo " + "\\end",
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
                , "font fxlt" // 2
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
                , "fxlr" // 2
                , "4" // 3
                , "72" // 4
                , "111" // 5
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=fxlr " + "\\hugo " + "Hugo Hugo HHHH" + "\\end",
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
                , "font fxlt" // 2
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
                , "fxlr" // 2
                , "4" // 3
                , "72" // 4
                , "111" // 5
                , "cmr10" // 6
                , "8" // 7
        };

        assertOutput(prop, // --- input code ---
            "\\font\\hugo=fxlr " + "\\hugo " + "Hugo Hugo HHHH"
                    + "\\font\\peter=cmr10 " + "\\peter "
                    + "Peter Peter Hugo HHHH" + "\\end",
            /* logValidator */null, /* outputValidator */
            new XmlValidator(comment, xpath, expected));
    }

    /**
     * Test the manager with tow fonts.
     * 
     * @throws Exception if an error occurred.
     */
    public void testNodeWidth01() throws Exception {

        String[] comment = new String[]{"only one char" // 1
                , "font fxlt" // 2
                , "width char" // 3
                , "codepoint" // 4
                , "char" // 5
                , "height" // 6
                , "depth" // 7
                , "width horizontal" // 8
                , "width vertical" // 9
        };
        String[] xpath = new String[]{"count(/root/page/verticallist//char)" // 1
                , "/root/page/verticallist/horizontallist/char[1]/@font" // 2
                , "/root/page/verticallist/horizontallist/char[1]/@width_sp" // 3
                , "/root/page/verticallist/horizontallist/char[1]/@codepoint" // 4
                , "/root/page/verticallist/horizontallist/char[1]/@visiblechar" // 5
                , "/root/page/verticallist/horizontallist/char[1]/@height_sp" // 6
                , "/root/page/verticallist/horizontallist/char[1]/@depth_sp" // 7
                , "/root/page/verticallist/horizontallist[1]/@width_sp" // 8
                , "/root/page/verticallist[1]/@width_sp" // 9
        };
        String[] expected = new String[]{"1" // 1
                , "fxlr" // 2
                , "458752" // 3
                , "72" // 4
                , "H" // 5
                , "423362" // 6
                , "655" // 7
                , "197066752" // 8
                , "197066752" // 9
        };

        // H
        assertOutput(prop, // --- input code ---
            "\\font\\hugo=fxlr " + "\\hugo " + "H" + "\\end",
            /* logValidator */null, /* outputValidator */
            new XmlValidator(comment, xpath, expected));
    }

}
