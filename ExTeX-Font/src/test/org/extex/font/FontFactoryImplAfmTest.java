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

import java.util.Properties;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.extex.font.type.other.NullFont;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.type.UnicodeChar;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.ConfigurationFactory;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.resource.PropertyConfigurable;
import org.extex.util.resource.ResourceConsumer;
import org.extex.util.resource.ResourceFinder;
import org.extex.util.resource.ResourceFinderFactory;

/**
 * Test for the font factory (afm).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplAfmTest extends TestCase {

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        // default size is 10pt
        FontKey key = factory.getFontKey("fxlr");

        ExtexFont font = factory.getInstance(key);

        assertNotNull(font);
        assertFalse(font instanceof NullFont);
        assertEquals("fxlr", font.getFontName());

        assertNotNull(font.getFontKey());
        assertEquals(key, font.getFontKey());
        assertNotNull(font.getActualFontKey());
        assertEquals(key, font.getActualFontKey());
        FixedDimen ds = font.getDesignSize();
        assertNotNull(ds);
        assertTrue(ds.toString(), new Dimen(Dimen.ONE * 10).eq(ds));
        assertNotNull(font.getActualSize());
        assertTrue(new Dimen(Dimen.ONE * 10).eq(font.getActualSize()));

        FixedDimen ex = font.getEx();
        assertNotNull(ex);
        // afm XHeight 431
        assertTrue(ex.toString(), new Dimen(Dimen.ONE * 10 * 431 / 1000).eq(ex));

        FixedDimen em = font.getEm();
        assertNotNull(em);
        assertTrue(em.toString(), new Dimen(Dimen.ONE * 10).eq(em));

        FixedDimen fd0 = font.getFontDimen("0");
        assertNotNull(fd0);
        assertTrue(fd0.toString(), Dimen.ZERO_PT.eq(fd0));

        FixedDimen fd1 = font.getFontDimen("1");
        assertNotNull(fd1);
        assertTrue(fd1.toString(), Dimen.ZERO_PT.eq(fd1));

        FixedCount sf = font.getScaleFactor();
        assertNotNull(sf);
        assertEquals(1, sf.getValue());

    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        // default size is 10pt
        FontKey key = factory.getFontKey("fxlr");

        ExtexFont font = factory.getInstance(key);

        assertNotNull(font);
        assertFalse(font instanceof NullFont);
        assertEquals("fxlr", font.getFontName());

        assertTrue(font.hasGlyph(UnicodeChar.get(' ')));
        assertFalse(font.hasGlyph(UnicodeChar.get(65535)));

        FixedGlue wx = font.getWidth(UnicodeChar.get(65535));
        assertNull(wx);

        // C 65 ; WX 695 ; N A ; B 2 -1 690 662
        FixedGlue w = font.getWidth(UnicodeChar.get('A'));
        assertNotNull(w);
        assertTrue(w.toString(), new Glue(Dimen.ONE * 10 * 695 / 1000).eq(w));

        FixedGlue h = font.getHeight(UnicodeChar.get('A'));
        assertNotNull(h);
        assertTrue(h.toString(), new Glue(Dimen.ONE * 10 * 662 / 1000).eq(h));

        FixedGlue d = font.getHeight(UnicodeChar.get('A'));
        assertNotNull(d);
        assertTrue(d.toString(), new Glue(Dimen.ONE * 10 * 1 / 1000).eq(d));
        
    }

    /**
     * Create the font factory.
     *
     * @return the font factory.
     * @throws ConfigurationException from the configuration system.
     */
    private CoreFontFactory makeFontFactory() throws ConfigurationException {

        CoreFontFactory factory = new FontFactoryImpl();

        Configuration config = new ConfigurationFactory()
                .newInstance(FontFactoryImplTest.class.getName().replaceAll(
                        "\\.", "/"));

        if (factory instanceof Configurable) {
            ((Configurable) factory)
                    .configure(config.getConfiguration("Fonts"));
        }
        if (factory instanceof ResourceConsumer) {
            Logger logger = Logger.getLogger("Test");
            ResourceFinder finder = new ResourceFinderFactory()
                    .createResourceFinder(config.getConfiguration("Resource"),
                            logger, new Properties(), null /*provider*/);

            ((ResourceConsumer) factory).setResourceFinder(finder);
        }
        if (factory instanceof PropertyConfigurable) {
            ((PropertyConfigurable) factory).setProperties(new Properties());
        }
        return factory;
    }
}
