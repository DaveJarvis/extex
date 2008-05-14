/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.StringBufferInputStream;
import java.util.Iterator;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Test;

/**
 * This is a test suite for the {@link TexConfiguration}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TexConfigurationTest {

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead02() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream("\\x{}"), "");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead03() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    new StringBufferInputStream("  \\xyz { } "), "");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead04() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    "  \\xyz [abc=def] { } "), "");
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead05() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    "  \\xyz [abc=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
        assertEquals("xyz", cfg.getName());
        Iterator<Configuration> iterator = cfg.iterator();
        assertFalse(iterator.hasNext());
        iterator = cfg.iterator("abc");
        assertFalse(iterator.hasNext());
        assertNull(cfg.findConfiguration("x"));
        assertNull(cfg.findConfiguration("x", "y"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead10() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    "  \\xyz { abc } "), "");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead11() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    "  \\xyz { {abc}} "), "");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead12() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    "  \\xyz [a= {{abc}}]{} "), "");
        assertEquals("{abc}", cfg.getAttribute("a"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment01() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    " % fff\n \\xyz [abc=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment02() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    " \\xyz % fff\n [abc=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment03() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    " \\xyz [abc % fff\n=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment04() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    " \\xyz [abc = % fff\n def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment05() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    " \\xyz [abc = % fff\n def,xxx={123}] { abc } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment06() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(new StringBufferInputStream(
                    " \\xyz [abc = % fff\n def,xxx={123}] {\\qwertz {}} "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> An empty stream signals a failure in locating the
     * configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationNotFoundException.class)
    public final void testReadError0() throws Exception {

        new TexConfiguration(null, "");
    }

    /**
     * <testcase> The empty stream is not valid. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError01() throws Exception {

        new TexConfiguration(new StringBufferInputStream(""), "");
    }

    /**
     * <testcase> The empty stream with whitespace only is not valid.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError02() throws Exception {

        new TexConfiguration(new StringBufferInputStream("  \t "), "");
    }

    /**
     * <testcase> The initial cs has to be followed by something. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError04() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [ or {. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError05() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx("), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [ and an attribute name.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError06() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx["), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError07() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError08() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a%=\n"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError09() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a= %=\n"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError10() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a %=\na"), "");
    }

    /**
     * <testcase> Attributes need to be closed by ]. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError11() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a=x\n"), "");
    }

    /**
     * <testcase> Attributes need to be closed by ]. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError12() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a=x b"), "");
    }

    /**
     * <testcase> Attributes need to be unique. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError13() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\xxx[a=x,a=y]"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError14() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError15() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\a{\\"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError16() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\={\\"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError17() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\a{\\="), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError18() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\a{"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError19() throws Exception {

        new TexConfiguration(new StringBufferInputStream("a"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError20() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\a[x=y"), "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError21() throws Exception {

        new TexConfiguration(new StringBufferInputStream("\\a[x={y\\"), "");
    }

}
