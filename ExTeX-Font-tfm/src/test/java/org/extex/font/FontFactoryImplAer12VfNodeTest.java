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

package org.extex.font;

import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * Test for the font factory (vf with creation nodes).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplAer12VfNodeTest extends AbstractFontFactoryTester {

    /**
     * The font.
     */
    private static ExtexFont font;

    /**
     * The font key.
     */
    private static FontKey key;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    public FontFactoryImplAer12VfNodeTest()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("aer12");

            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font key.
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
    }

}
