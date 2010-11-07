/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.MissingResourceException;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class LocalizerFactoryTest {

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test(expected = MissingResourceException.class)
    public void test01() {

        LocalizerFactory.getLocalizer("java.lang.String").format("abc");
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test02() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test03() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy", "aaa"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test04() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy", "aaa", "bbb"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test05() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy", "aaa", "bbb", "ccc"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test06() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy", "aaa", "bbb", "ccc", "ddd"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test07() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy", "aaa", "bbb", "ccc", "ddd", "eee"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.String)}
     * .
     */
    @Test
    public void test10() {

        assertEquals(
            "???xyzzy???",
            LocalizerFactory.getLocalizer(ConfigurationException.class).format(
                "xyzzy", new String[]{"aaa", "bbb", "ccc", "ddd", "eee"}));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getFormat(String)} .
     */
    @Test
    public void testGetFormat1() {

        assertNull(LocalizerFactory.getLocalizer(ConfigurationException.class)
            .getFormat("xyzzy"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getFormat(String)} .
     */
    @Test
    public void testGetFormat2() {

        Locale.setDefault(Locale.ENGLISH);
        assertEquals("CBA",
            LocalizerFactory.getLocalizer(LocalizerFactoryTest.class)
                .getFormat("abc"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.Class)}
     * .
     */
    @Test(expected = MissingResourceException.class)
    public void testGetLocalizer1() {

        LocalizerFactory.getLocalizer(String.class).format("abc");
    }

    /**
     * Test method for
     * {@link org.extex.framework.i18n.LocalizerFactory#getLocalizer(java.lang.Class)}
     * .
     */
    @Test
    public void testGetLocalizer2() {

        Locale.setDefault(Locale.ENGLISH);
        assertEquals(
            "CBA",
            LocalizerFactory.getLocalizer(LocalizerFactoryTest.class).format(
                "abc"));
        Locale.setDefault(Locale.GERMAN);
        assertEquals(
            "ABC",
            LocalizerFactory.getLocalizer(LocalizerFactoryTest.class).format(
                "abc"));
    }

    /**
     * <testcase>...</testcase>
     */
    @Test
    public void testMessage1() {

        Locale.setDefault(Locale.ENGLISH);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream writer = new PrintStream(out);
        LocalizerFactory.getLocalizer(LocalizerFactoryTest.class).message(
            writer, "x");
        writer.close();
        assertEquals("X{0}X{1}X{2}X{3}X{4}\n",
            out.toString().replaceAll("\r", ""));
    }

    /**
     * <testcase>...</testcase>
     */
    @Test
    public void testMessage2() {

        Locale.setDefault(Locale.ENGLISH);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream writer = new PrintStream(out);
        LocalizerFactory.getLocalizer(LocalizerFactoryTest.class).message(
            writer, "x", "a");
        writer.close();
        assertEquals("XaX{1}X{2}X{3}X{4}\n", out.toString()
            .replaceAll("\r", ""));
    }

    /**
     * <testcase>...</testcase>
     */
    @Test
    public void testMessage3() {

        Locale.setDefault(Locale.ENGLISH);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream writer = new PrintStream(out);
        LocalizerFactory.getLocalizer(LocalizerFactoryTest.class).message(
            writer, "x", "a", "b");
        writer.close();
        assertEquals("XaXbX{2}X{3}X{4}\n", out.toString().replaceAll("\r", ""));
    }

    /**
     * <testcase>...</testcase>
     */
    @Test
    public void testMessage4() {

        Locale.setDefault(Locale.ENGLISH);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream writer = new PrintStream(out);
        LocalizerFactory.getLocalizer(LocalizerFactoryTest.class).message(
            writer, "x", "a", "b", "c");
        writer.close();
        assertEquals("XaXbXcX{3}X{4}\n", out.toString().replaceAll("\r", ""));
    }

}
