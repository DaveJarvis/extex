/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
     * <testcase> The callback for unknown options is triggered. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalStateException.class)
    public void testA10() throws Exception {

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
    public void testA11() throws Exception {

        CLI cli = new CLI();
        cli.declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String a) {

                assertEquals("xxx", a);
                throw new IllegalStateException();
            }

        });
        cli.run(new String[]{"xxx=123"});
    }

    /**
     * <testcase> The callback for unknown options is triggered. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA12() throws Exception {

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
    public void testA13() throws Exception {

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
    public void testA20() throws Exception {

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
    public void testA22() throws Exception {

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
    public void testA24() throws Exception {

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
    public void testA26() throws Exception {

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
    public void testA30() throws Exception {

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
    public void testA31() throws Exception {

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
     * <testcase> The callback for known string options without argument leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testA40() throws Exception {

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
    public void testA41() throws Exception {

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
    public void testA42() throws Exception {

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
    public void testA43() throws Exception {

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
     * <testcase> The callback for known number options without argument leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentCliException.class)
    public void testA50() throws Exception {

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
    public void testA51() throws Exception {

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
    public void testA52() throws Exception {

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
    public void testA53() throws Exception {

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
    public void testA54() throws Exception {

        CLI cli = new CLI();
        cli.declareOption("-xxx", new NumberOption(null) {

            @Override
            protected int run(String a, int value) {

                return -1;
            }

        });
        cli.run(new String[]{"-xx", ".123"});
    }

    /**
     * <testcase> A number option needs a numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NonNumericArgumentCliException.class)
    public void testA55() throws Exception {

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
