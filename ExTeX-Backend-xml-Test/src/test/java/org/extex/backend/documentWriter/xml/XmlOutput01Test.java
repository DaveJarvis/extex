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

    @Override
    protected void setUp() throws Exception {

        prop = System.getProperties();
        prop.setProperty("extex.output", "xml");
    }

    /**
     * Creates a new object.
     */
    public XmlOutput01Test() {

        super("xml backend test 01");
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

}
