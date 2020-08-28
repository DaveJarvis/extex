/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.NonNumericArgumentCliException;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.cli.exception.UnusedArgumentCliException;
import org.junit.Test;

/**
 * This is a test suite for {@link CLI}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CLITest {

    /**
     * <testcase> A <code>null</code> argument leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public void testA01() throws Exception {

        new CLI().run((String[]) null);
    }

    /**
     * <testcase> An empty array argument has no effect. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA02() throws Exception {

        new CLI().run(new String[]{});
    }

    /**
     * <testcase> A one element array argument of an unknown value without =
     * leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA03() throws Exception {

        try {
            new CLI().run(new String[]{"xxx"});
            assertTrue(false);
        } catch (UnknownOptionCliException e) {
            assertEquals("xxx", e.getMessage());
            Locale.setDefault(Locale.ENGLISH);
            assertEquals("The option xxx is not known", e.getLocalizedMessage());
        }
    }

    /**
     * <testcase> A one element array argument of an unknown value with = leads
     * to an error which reports the option name before the =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA04() throws Exception {

        try {
            new CLI().run(new String[]{"xxx=123"});
            assertTrue(false);
        } catch (UnknownOptionCliException e) {
            assertEquals("xxx", e.getMessage());
        }
    }

    /**
     * <testcase> The callback for known boolean options without argument
     * invokes the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBoolean1() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new BooleanOption(null) {

            @Override
            protected int run(String a, boolean value) {

                assertEquals(true, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx"}));
    }

    /**
     * <testcase> The callback for known boolean options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBoolean2() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new BooleanOption(null) {

            @Override
            protected int run(String a, boolean value) {

                assertEquals(true, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx", "qqq"}));
    }

    /**
     * <testcase> The callback for known boolean options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBoolean3() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new BooleanOption(null) {

            @Override
            protected int run(String a, boolean value) {

                assertEquals(false, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx=false"}));
    }

    /**
     * <testcase> Nothing to describe leads to an empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions01() throws Exception {

        assertEquals("", new CLI().describeOptions(null, null, null));
    }

    /**
     * <testcase> Nothing to describe leads to an empty string within
     * delimiters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions02() throws Exception {

        ResourceBundle bundle = new ResourceBundle() {

            @Override
            public Enumeration<String> getKeys() {

                return null;
            }

            @Override
            protected Object handleGetObject(String key) {

                if (key.equals("aa")) {
                    return "AA";
                }
                if (key.equals("bb")) {
                    return "BB";
                }
                return null;
            }
        };
        assertEquals("AABB", new CLI().describeOptions(bundle, "aa", "bb"));
    }

    /**
     * <testcase> Nothing to describe leads to an empty string within delimiters
     * from a resource bundle. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions03() throws Exception {

        assertEquals("AABB", new CLI().describeOptions(new ResourceBundle() {

            @Override
            public Enumeration<String> getKeys() {

                return null;
            }

            @Override
            protected Object handleGetObject(String key) {

                if (key.equals("aa")) {
                    return "AA";
                }
                if (key.equals("bb")) {
                    return "BB";
                }
                return null;
            }
        }, "aa", "bb"));
    }

    /**
     * <testcase> One item is described properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions10() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-qqq", new StringOption("-QQQ") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        assertEquals("AA\t-[qqq]--q--\nBB",
            cli.describeOptions(new ResourceBundle() {

                @Override
                public Enumeration<String> getKeys() {

                    return null;
                }

                @Override
                protected Object handleGetObject(String key) {

                    if (key.equals("<<")) {
                        return "AA";
                    }
                    if (key.equals(">>")) {
                        return "BB";
                    }
                    if (key.equals("-QQQ")) {
                        return "--q--";
                    }
                    return null;
                }
            }, "<<", ">>"));
    }

    /**
     * <testcase> Two items are described properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions11() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-qqq", new StringOption("-QQQ") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        cli.declareOption("-r", new StringOption("-R") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        assertEquals("A\t-[qqq]--q--\n\t-r--r--\nB",
            cli.describeOptions(new ResourceBundle() {

                @Override
                public Enumeration<String> getKeys() {

                    return null;
                }

                @Override
                protected Object handleGetObject(String key) {

                    if (key.equals("<<")) {
                        return "A";
                    }
                    if (key.equals(">>")) {
                        return "B";
                    }
                    if (key.equals("-R")) {
                        return "--r--";
                    }
                    if (key.equals("-QQQ")) {
                        return "--q--";
                    }
                    return null;
                }
            }, "<<", ">>"));
    }

    /**
     * <testcase> Two items are described properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions12() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-q", new StringOption("-R") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        cli.declareOption("-qqq", new StringOption("-QQQ") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        assertEquals("A\t-qq[q]--q--\n\t-[q]--r--\nB",
            cli.describeOptions(new ResourceBundle() {

                @Override
                public Enumeration<String> getKeys() {

                    return null;
                }

                @Override
                protected Object handleGetObject(String key) {

                    if (key.equals("<<")) {
                        return "A";
                    }
                    if (key.equals(">>")) {
                        return "B";
                    }
                    if (key.equals("-R")) {
                        return "--r--";
                    }
                    if (key.equals("-QQQ")) {
                        return "--q--";
                    }
                    return null;
                }
            }, "<<", ">>"));
    }

    /**
     * <testcase> Three items are described properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDescribeOptions13() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-q", new StringOption("-R") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        cli.declareOption("-r", new StringOption("-S") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        cli.declareOption("-qqq", new StringOption("-QQQ") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 0;
            }
        });
        assertEquals("A\t-qq[q]--q--\n\t-[q]--r--\n\t-r--s--\nB",
            cli.describeOptions(new ResourceBundle() {

                @Override
                public Enumeration<String> getKeys() {

                    return null;
                }

                @Override
                protected Object handleGetObject(String key) {

                    if (key.equals("<<")) {
                        return "A";
                    }
                    if (key.equals(">>")) {
                        return "B";
                    }
                    if (key.equals("-R")) {
                        return "--r--";
                    }
                    if (key.equals("-S")) {
                        return "--s--";
                    }
                    if (key.equals("-QQQ")) {
                        return "--q--";
                    }
                    return null;
                }
            }, "<<", ">>"));
    }

    /**
     * <testcase> A <code>null</code> argument leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public void testL01() throws Exception {

        new CLI().run((List<String>) null);
    }

    /**
     * <testcase> An empty list argument has no effect. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testL02() throws Exception {

        new CLI().run(new ArrayList<String>());
    }

    /**
     * <testcase> The callback for known long options without argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testLong1() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new LongOption(null) {

            @Override
            protected int run(String a, long value) {

                assertEquals(123, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx"}));
    }

    /**
     * <testcase> The callback for known long option passes its argument to the
     * callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLong2() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new LongOption(null) {

            @Override
            protected int run(String a, long value) {

                assertEquals(123, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx", "123"}));
    }

    /**
     * <testcase> The callback for known long option passes its argument to the
     * callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLong3() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new LongOption(null) {

            @Override
            protected int run(String a, long value) {

                assertEquals(123, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx=123"}));
    }

    /**
     * <testcase> The callback for known long options with wrong argument
     * craoks. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testLong4() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new LongOption(null) {

            @Override
            protected int run(String a, long value) {

                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx="}));
    }

    /**
     * <testcase> The callback for known long options with wrong argument
     * craoks. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testLong5() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new LongOption(null) {

            @Override
            protected int run(String a, long value) {

                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx=abc"}));
    }

    /**
     * <testcase> The callback for known long options with wrong argument
     * craoks. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testLong6() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new LongOption(null) {

            @Override
            protected int run(String a, long value) {

                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xxx", "abc"}));
    }

    /**
     * <testcase> The callback for unknown options is triggered. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalStateException.class)
    public void testNoArg01() throws Exception {

        CLI cli = new CLI();
        cli.declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("xxx", a);
                throw new IllegalStateException();
            }

        });
        cli.run(new String[]{"xxx"});
    }

    /**
     * <testcase> The callback for unknown options is not triggered if an
     * additional argument is found. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnusedArgumentCliException.class)
    public void testNoArg02() throws Exception {

        CLI cli = new CLI();
        cli.declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("xxx", a);
                throw new IllegalStateException();
            }

        });
        try {
            cli.run(new String[]{"xxx=123"});
        } catch (UnusedArgumentCliException e) {
            Locale.setDefault(Locale.ENGLISH);
            assertEquals("The argument of option xxx is unused",
                e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * <testcase> The callback for unknown options is triggered. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArg03() throws Exception {

        CLI cli = new CLI();
        cli.declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("xxx", a);
                return -42;
            }

        });
        assertEquals(-42, cli.run(new String[]{"xxx"}));
    }

    /**
     * <testcase> The callback for unknown options is triggered but can return
     * "ok". </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArg04() throws Exception {

        CLI cli = new CLI();
        cli.declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("xxx", a);
                return 1;
            }

        });
        assertEquals(1, cli.run(new String[]{"xxx"}));
    }

    /**
     * <testcase> The callback for known options without argument is triggered.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArg05() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("-xxx", a);
                return -42;
            }

        });
        assertEquals(-42, cli.run(new String[]{"-xxx"}));
    }

    /**
     * <testcase> The callback for known options without argument is triggered.
     * A negative argument terminates the processing. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownOptionCliException.class)
    public void testNoArg06() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("-xxx", a);
                return CLI.EXIT_CONTINUE;
            }

        });
        cli.run(new String[]{"-xxx", "123"});
    }

    /**
     * <testcase> The callback for known options without argument is triggered
     * when abbreviated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArg07() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("-xx", a);
                return -42;
            }

        });
        assertEquals(-42, cli.run(new String[]{"-xx"}));
    }

    /**
     * <testcase> The callback for known options without argument is triggered
     * when abbreviated. A negative argument terminates the processing.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownOptionCliException.class)
    public void testNoArg08() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("-xx", a);
                return CLI.EXIT_CONTINUE;
            }

        });
        cli.run(new String[]{"-xx", "123"});
    }

    /**
     * <testcase> The callback for known options is not overwritten. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArg09() throws Exception {

        CLI cli = new CLI();
        assertTrue(cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                return 1;
            }

        }));
        assertFalse(cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                return -1;
            }

        }));
        assertEquals(1, cli.run(new String[]{"-xxx"}));
    }

    /**
     * <testcase> The callback for known options is not overwritten but can be
     * extended. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArg10() throws Exception {

        CLI cli = new CLI();
        assertTrue(cli.declareOption("-xx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                return -2;
            }

        }));
        assertTrue(cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                return -1;
            }

        }));
        assertEquals(-1, cli.run(new String[]{"-xxx"}));
        assertEquals(-2, cli.run(new String[]{"-xx"}));
    }

    /**
     * <testcase> The callback for no-arg options without argument is accepted.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoArgProperty01() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new NoArgPropertyOption(null, "x", "abc", p));
        assertEquals(CLI.EXIT_CONTINUE, cli.run(new String[]{"-xxx"}));
        assertEquals("abc", p.get("x"));
    }

    /**
     * <testcase> The callback for known number options without argument leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testNumber01() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                assertTrue(false);
                return 1;
            }

        });
        cli.run(new String[]{"-xxx"});
    }

    /**
     * <testcase> The callback for known number options without argument leads
     * to an error when abbreviated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testNumber02() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                assertTrue(false);
                return 1;
            }

        });
        cli.run(new String[]{"-xx"});
    }

    /**
     * <testcase> The callback for known number options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumber03() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                assertEquals(123, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx", "123"}));
    }

    /**
     * <testcase> The callback for known number options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumber04() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                assertEquals(123, value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx=123"}));
    }

    /**
     * <testcase> A number option needs a numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testNumber05() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                return -1;
            }

        });
        try {
            cli.run(new String[]{"-xx", ".123"});
        } catch (NonNumericArgumentCliException e) {
            assertEquals(".123", e.getValue());
            Locale.setDefault(Locale.ENGLISH);
            assertEquals("The option -xx is not numeric",
                e.getLocalizedMessage());
            Locale.setDefault(Locale.GERMAN);
            assertEquals("Die Option -xx ist nicht nummerisch",
                e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * <testcase> A number option needs a numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testNumber06() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                return -1;
            }

        });
        cli.run(new String[]{"-xx=.123"});
    }

    /**
     * <testcase> The callback for known number options without argument leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testNumberProperty01() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberPropertyOption(null, null, null));
        cli.run(new String[]{"-xxx"});
    }

    /**
     * <testcase> The callback for known number options without argument leads
     * to an error when abbreviated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testNumberProperty02() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberPropertyOption(null, null, null));
        cli.run(new String[]{"-xx"});
    }

    /**
     * <testcase> The callback for known number options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumberProperty03() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new NumberPropertyOption(null, "x", p));
        assertEquals(CLI.EXIT_CONTINUE, cli.run(new String[]{"-xx", "123"}));
        assertEquals("123", p.get("x"));
    }

    /**
     * <testcase> The callback for known number options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumberProperty04() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new NumberPropertyOption(null, "x", p));
        assertEquals(CLI.EXIT_CONTINUE, cli.run(new String[]{"-xx=123"}));
        assertEquals("123", p.get("x"));
    }

    /**
     * <testcase> A number option needs a numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testNumberProperty05() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new NumberPropertyOption(null, "x", p));
        try {
            cli.run(new String[]{"-xx", ".123"});
        } catch (NonNumericArgumentCliException e) {
            assertEquals(".123", e.getValue());
            Locale.setDefault(Locale.ENGLISH);
            assertEquals("The option -xx is not numeric",
                e.getLocalizedMessage());
            Locale.setDefault(Locale.GERMAN);
            assertEquals("Die Option -xx ist nicht nummerisch",
                e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * <testcase> A number option needs a numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testNumberProperty06() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new NumberPropertyOption(null, "x", p));
        cli.run(new String[]{"-xx=.123"});
    }

    /**
     * <testcase> A <code>null</code> argument leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOption01() throws Exception {

        CLI cli = new CLI();
        cli.option("-x", "-xxx", new OptionalStringOption(null) {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                return 42;
            }
        }, "-y");
        assertEquals(CLI.EXIT_CONTINUE, cli.run(new String[]{}));
        assertEquals(42, cli.run(new String[]{"-x"}));
    }

    /**
     * <testcase> The callback for known string options without argument is
     * accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionalString01() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new OptionalStringOption(null) {

            @Override
            protected int run(String a, String value) {

                return 1;
            }

        });
        assertEquals(1, cli.run(new String[]{"-xxx"}));
    }

    /**
     * <testcase> The callback for known string options without argument is
     * accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionalString02() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new OptionalStringOption(null) {

            @Override
            protected int run(String a, String value) {

                return 1;
            }

        });
        assertEquals(1, cli.run(new String[]{"-xx"}));
    }

    /**
     * <testcase> The callback for known string options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionalString03() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new OptionalStringOption(null) {

            @Override
            protected int run(String a, String value) {

                assertEquals("qqq", value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx", "qqq"}));
    }

    /**
     * <testcase> The callback for known string options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionalString04() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new OptionalStringOption(null) {

            @Override
            protected int run(String a, String value) {

                assertEquals("qqq", value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx=qqq"}));
    }

    /**
     * <testcase> The callback for known string options without argument leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testString01() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new StringOption(null) {

            @Override
            protected int run(String a, String value) {

                assertTrue(false);
                return 1;
            }

        });
        cli.run(new String[]{"-xxx"});
    }

    /**
     * <testcase> The callback for known string options without argument leads
     * to an error when abbreviated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testString02() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new StringOption(null) {

            @Override
            protected int run(String a, String value) {

                assertTrue(false);
                return 1;
            }

        });
        cli.run(new String[]{"-xx"});
    }

    /**
     * <testcase> The callback for known string options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testString03() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new StringOption(null) {

            @Override
            protected int run(String a, String value) {

                assertEquals("qqq", value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx", "qqq"}));
    }

    /**
     * <testcase> The callback for known string options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testString04() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new StringOption(null) {

            @Override
            protected int run(String a, String value) {

                assertEquals("qqq", value);
                return -1;
            }

        });
        assertEquals(-1, cli.run(new String[]{"-xx=qqq"}));
    }

    /**
     * <testcase> The callback for known string options without argument leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testStringProperty01() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new StringPropertyOption(null, null, null));
        cli.run(new String[]{"-xxx"});
    }

    /**
     * <testcase> The callback for known string options without argument leads
     * to an error when abbreviated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testStringProperty02() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new StringPropertyOption(null, "x", p));
        cli.run(new String[]{"-xx"});
    }

    /**
     * <testcase> The callback for known string options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStringProperty03() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new StringPropertyOption(null, "x", p));
        assertEquals(CLI.EXIT_CONTINUE, cli.run(new String[]{"-xx", "qqq"}));
        assertEquals("qqq", p.get("x"));
    }

    /**
     * <testcase> The callback for known string options with argument invokes
     * the callback. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStringProperty04() throws Exception {

        CLI cli = new CLI();
        Properties p = new Properties();
        cli.declareOption("-xxx", new StringPropertyOption(null, "x", p));
        assertEquals(CLI.EXIT_CONTINUE, cli.run(new String[]{"-xx=qqq"}));
        assertEquals("qqq", p.get("x"));
    }

    /**
     * <testcase> The dafault for an yoption is <code>null</code> and this value
     * can be overwritten. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStringProperty10() throws Exception {

        Properties p = new Properties();
        StringPropertyOption sp = new StringPropertyOption(null, "x", p);
        assertNull(p.get("x"));
        sp.set("abc");
        assertEquals("abc", p.get("x"));
    }

    /**
     * <testcase> Nothing to describe leads to an empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString1() throws Exception {

        assertEquals("", new CLI().toString());
    }

    /**
     * <testcase> An option invokes a method of the surrounding class.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalStateException.class)
    public void testX1() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NoArgOption(null) {

            @Override
            protected int run(String a) {

                xxx();
                return -1;
            }

        });
        cli.run(new String[]{"-xx"});
    }

    /**
     * Test method for some test cases.
     * 
     */
    private void xxx() {

        throw new IllegalStateException();
    }

}
