/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.bst2groovy.exception.CommandWithArgumentsException;
import org.extex.exbib.bst2groovy.exception.CommandWithEntryException;
import org.extex.exbib.bst2groovy.exception.CommandWithReturnException;
import org.extex.exbib.bst2groovy.exception.ComplexFunctionException;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for Bst2Groovy.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Bst2GroovyTest {

    /**
     * This is the resource finder to inject some contents without external
     * files.
     * 
     */
    public static final class TestResourceFinder implements ResourceFinder {

        /**
         * The field <tt>content</tt> contains the content.
         */
        private String content = "";

        /**
         * Creates a new object.
         * 
         * @param content the content
         */
        public TestResourceFinder(String content) {

            this.content = content;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
         */
        public void enableTracing(boolean flag) {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            return new NamedInputStream(new ByteArrayInputStream(content
                .getBytes()), name);
        }
    }

    /**
     * The field <tt>POST_RUN</tt> contains the piece of code after the run
     * function.
     */
    private static final String POST_RUN =
            "  }\n" + "\n" + "}\n" + "\n"
                    + "new Style(bibDB, bibWriter, bibProcessor).run()\n";

    /**
     * The field <tt>RUN</tt> contains the piece of code starting the run
     * function.
     */
    private static final String RUN = "\n" + "  void run() {\n";

    /**
     * The field <tt>HEAD</tt> contains the head of the style definition.
     */
    private static final String HEAD =
            "\n\n  Style(bibDB, bibWriter, bibProcessor) {\n"
                    + "    this.bibDB = bibDB\n"
                    + "    this.bibWriter = bibWriter\n"
                    + "    this.bibProcessor = bibProcessor\n";

    /**
     * The field <tt>POSTFIX</tt> contains the ...
     */
    private static final String POSTFIX = HEAD + "  }\n" + RUN + POST_RUN;

    /**
     * The field <tt>CLASS_PREFIX</tt> contains the prefix code for the class.
     */
    private static final String CLASS_PREFIX = "\n" //
            + "class Style {\n\n" //
            + "  DB bibDB\n" //
            + "  Writer bibWriter\n" //
            + "  Processor bibProcessor";

    /**
     * The field <tt>PREFIX</tt> contains the prefix code.
     */
    private static final String PREFIX =
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" + CLASS_PREFIX;

    /**
     * Run a test.
     * 
     * @param input the input
     * @param output the output
     * 
     * @throws ExBibImpossibleException in case of an error
     * @throws ExBibBstNotFoundException in case of an error
     * @throws ExBibException in case of an error
     * @throws IOException in case of an error
     */
    private void run(String input, String output)
            throws ExBibImpossibleException,
                ExBibBstNotFoundException,
                ExBibException,
                IOException {

        Bst2Groovy bst2Groovy = new Bst2Groovy();
        Logger logger = Logger.getLogger(getClass().getName());
        logger.setLevel(Level.SEVERE);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        ResourceFinder finder = new TestResourceFinder(input);
        bst2Groovy.setResourceFinder(finder);

        Writer w = new StringWriter();
        bst2Groovy.run(w, "test");
        w.flush();
        assertEquals(output, w.toString().replaceAll("\r", ""));
    }

    /**
     * <testcase> Test the empty program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        run("", PREFIX + POSTFIX);
    }

    /**
     * <testcase> Test that add.period$ adds proper code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAddPeriod1() throws Exception {

        run(
            "function{abc}{add.period$}", //
            PREFIX
                    + HEAD
                    + "  }\n\n"
                    + "  String addPeriod(String s) {\n"
                    + "    return s == null || s == '' ? '' : s.matches(\".*[.!?]\") ? s : s + \".\"\n"
                    + "  }\n" + "\n" + "  String abc(v1) {\n"
                    + "    return addPeriod(v1)\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for change.case$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChangeCase1() throws Exception {

        run("function{abc}{change.case$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.ChangeCase\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n"
                    + "  String abc(v1, v2) {\n"
                    + "    return ChangeCase.changeCase(v1,\n"
                    + "                                 v2)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the code for chr.to.int$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChrToInt1() throws Exception {

        run(
            "function{abc}{chr.to.int$}", //
            PREFIX
                    + HEAD
                    + "  }\n"
                    + "\n"
                    + "  int chrToInt(String s) {\n"
                    + "    if (s.length() != 1) {\n"
                    + "      bstProcessor.warning(\"argument to chrToInt has wrong length\")\n"
                    + "    }\n" + "    return s.charAt(0)\n" + "  }\n" + "\n"
                    + "  int abc(v1) {\n" + "    return chrToInt(v1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for cite$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCite1() throws Exception {

        run("function{abc}{cite$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getKey()\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat1() throws Exception {

        run("function{abc}{\"x\" *}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(v1) {\n"
                    + "    return v1 + \"x\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat2() throws Exception {

        run("function{abc}{\"a\" \"b\" *}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"a\" + \"b\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for duplicate$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate1() throws Exception {

        run("function{abc}{#2 duplicate$ +}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 2 + 2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for duplicate$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate2() throws Exception {

        run("function{abc}{\"2\" duplicate$ *}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"2\" + \"2\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for duplicate$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate3() throws Exception {

        run("function{abc}{#2 #3 + duplicate$ -}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return (2 + 3) - (2 + 3)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that empty$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty1() throws Exception {

        run("function{abc}{empty$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  boolean isEmpty(String s) {\n"
                    + "    return s == null || s.trim() == ''\n" + "  }\n"
                    + "\n" + "  int abc(v1) {\n"
                    + "    return isEmpty(v1) ? 1 : 0\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the entry itself does not add code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry1() throws Exception {

        run("entry {a b c}{}{}", //
            PREFIX + POSTFIX);
    }

    /**
     * <testcase> Test that entry reading is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry2() throws Exception {

        run("entry{a}{b}{c}function{abc}{a}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getExpanded(\"a\",\n"
                    + "                             bibDB)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that entry writing is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry3() throws Exception {

        run("entry{a}{b}{c}function{abc}{'a := #0}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry, v1) {\n"
                    + "    entry.set(\"a\", v1)\n" + "    return 0\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that entry string reading is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry4() throws Exception {

        run("entry{a}{b}{c}function{abc}{b}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry) {\n"
                    + "    return entry.getLocalInt(\"b\")\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that entry string writing is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry5() throws Exception {

        run("entry{a}{b}{c}function{abc}{'b := #0}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry, v1) {\n"
                    + "    entry.setLocal(\"b\", v1)\n" + "    return 0\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that entry integer reading is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry6() throws Exception {

        run("entry{a}{b}{c}function{abc}{c}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getLocalString(\"c\")\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that entry integer writing is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry7() throws Exception {

        run("entry{a}{b}{c}function{abc}{'c := #0}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry, v1) {\n"
                    + "    entry.setLocal(\"c\", v1)\n" + "    return 0\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that = is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEq1() throws Exception {

        run("function{abc}{#1 =}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v1) {\n"
                    + "    return v1 == 1 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that EXECUTE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute1() throws Exception {

        run("entry {a b c}{}{}\nexecute{skip$}",//
            PREFIX + HEAD + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that EXECUTE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithReturnException.class)
    public void testExecuteError1() throws Exception {

        run("execute{+}", null);
    }

    /**
     * <testcase> Test that EXECUTE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithArgumentsException.class)
    public void testExecuteError2() throws Exception {

        run("execute{write$}", null);
    }

    /**
     * <testcase> Test that EXECUTE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithEntryException.class)
    public void testExecuteError3() throws Exception {

        run("execute{call.type$}", null);
    }

    /**
     * <testcase> Test that the code for format.name$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormatName1() throws Exception {

        run("function{abc}{format.name$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.FormatName\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n"
                    + "  String abc(v1, v2, v3) {\n"
                    + "    return FormatName.formatName(v3,\n"
                    + "                                 v2,\n"
                    + "                                 v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that an empty function is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction1() throws Exception {

        run("function{abc}{}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that a function with an argument is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction2() throws Exception {

        run("function{abc}{pop$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(v1) {\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function with an integer return type is created
     * properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction3() throws Exception {

        run("function{abc}{#123}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n    return 123\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function with a String return type is created
     * properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction4() throws Exception {

        run("function{abc}{\"abc\"}", //
            PREFIX + HEAD + "  }\n" + "\n"
                    + "  String abc() {\n    return \"abc\"\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that a function with multiple return values leads to an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ComplexFunctionException.class)
    public void testFunction5() throws Exception {

        run("function{abc}{#1 #2}", null);
    }

    /**
     * <testcase> Test that a function with two arguments is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction6() throws Exception {

        run("function{a}{pop$}function{abc}{a a}", //
            PREFIX + HEAD + "  }\n\n" //
                    + "  void a(v1) {\n" //
                    + "  }\n\n" //
                    + "  void abc(v1, v2) {\n" //
                    + "    a(v1)\n" //
                    + "    a(v2)\n" //
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that > is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt1() throws Exception {

        run("function{abc}{>}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v1, v2) {\n"
                    + "    return v2 > v1 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that > is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt2() throws Exception {

        run("function{abc}{#2 #1 >}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 2 > 1 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf1() throws Exception {

        run("function{abc}{#1 'skip$ 'skip$ if$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    if (! 1) {\n    }\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf2() throws Exception {

        run("function{abc}{#1 {#2} {#3} if$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return ( 1 ? 2 : 3 )\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf3() throws Exception {

        run("function{abc}{#1 {pop$ #2} {} if$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v3) {\n"
                    + "    return ( 1 ? 2 : v3 )\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that one integer is properly initialized. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntegers1() throws Exception {

        run("integers {abc}", PREFIX + "\n\n  int abc = 0" + POSTFIX);
    }

    /**
     * <testcase> Test that three integers are properly initialized. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntegers2() throws Exception {

        run("integers {a b c}", //
            PREFIX + "\n\n  int a = 0\n" + "  int b = 0\n" + "  int c = 0"
                    + POSTFIX);
    }

    /**
     * <testcase> Test that reading an integer variable works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntegers3() throws Exception {

        run("integers{a} function{abc}{a}", //
            PREFIX + "\n\n  int a = 0" + HEAD + "  }\n" + "\n"
                    + "  int abc() {\n" + "    return a\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that writing an integer variable works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntegers4() throws Exception {

        run("integers{a} function{abc}{'a :=}", //
            PREFIX + "\n\n  int a = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(v1) {\n" + "    a = v1\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the code for int.to.chr$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntToChr1() throws Exception {

        run("function{abc}{int.to.chr$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(v1) {\n"
                    + "    return Character.toString((char)(v1))\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for int.to.str$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntToStr1() throws Exception {

        run("function{abc}{int.to.str$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(v1) {\n"
                    + "    return Integer.toString(v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that ITERATE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIterate1() throws Exception {

        run("iterate{call.type$}",//
            PREFIX + HEAD + "  }\n\n" + "  void callType(Entry entry) {\n"
                    + "    def typeFunction = types[entry.getType()]\n"
                    + "    if (typeFunction == null) {\n"
                    + "      typeFunction = types['default.type']\n"
                    + "    }\n" + "    if (typeFunction == null) {\n"
                    + "      bstProcessor.warning('missing default.type')\n"
                    + "    } else {\n" + "      typeFunction(entry)\n"
                    + "    }\n"
                    + "  }\n\n" //
                    + "  void run() {\n" + "    bibDB.each {\n"
                    + "      callType(it)\n" + "    }\n" + POST_RUN);
    }

    /**
     * <testcase> Test that ITERATE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithReturnException.class)
    public void testIterateError1() throws Exception {

        run("iterate{+}", null);
    }

    /**
     * <testcase> Test that ITERATE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithArgumentsException.class)
    public void testIterateError2() throws Exception {

        run("iterate{write$}", null);
    }

    /**
     * <testcase> Test that "location.line$" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLocationLine1() throws Exception {

        run("function{abc}{locator.line$ write$}", //
            PREFIX + "\n\n  Map types = [\n"
                    + "    abc : { entry -> abc(entry) },\n" + "  ]" + HEAD
                    + "  }\n" + "\n" + "  void abc(entry) {\n"
                    + "    bibWriter.print(entry.getLocator().getLine())\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that "location.resource$" is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLocationResource1() throws Exception {

        run("function{abc}{locator.resource$ write$}", //
            PREFIX + "\n\n  Map types = [\n"
                    + "    abc : { entry -> abc(entry) },\n" + "  ]" + HEAD
                    + "  }\n" + "\n" + "  void abc(entry) {\n"
                    + "    bibWriter.print(entry.getLocator().getResource())\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that < is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLt1() throws Exception {

        run("function{abc}{<}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v1, v2) {\n"
                    + "    return v2 < v1 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that < is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLt2() throws Exception {

        run("function{abc}{#1 #2 <}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 1 < 2 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that - is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus1() throws Exception {

        run("function{abc}{-}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v1, v2) {\n"
                    + "    return v2 - v1\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that - is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus2() throws Exception {

        run("function{abc}{#1 #2 -}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 1 - 2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that missing$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissing1() throws Exception {

        run("function{abc}{missing$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v1) {\n"
                    + "    return v1 == null\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that newline$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewline1() throws Exception {

        run("function{abc}{newline$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    bibWriter.println()\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for num.names$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumNames1() throws Exception {

        run("function{abc}{num.names$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.NumNames\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  String abc(v1) {\n"
                    + "    return NumNames.numNames(v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that "OPTION INTEGER" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionInteger1() throws Exception {

        run(
            "option integer {xxx}{#42}\n" + "function{abc}{xxx write$}", //
            PREFIX
                    + HEAD
                    + "    [\n"
                    + "      xxx: \"42\",\n"
                    + "    ].each { name, value ->\n"
                    + "      if (bibProcessor.getOption(name) == null) {\n"
                    + "        bibProcessor.setOption(name, value)\n"
                    + "      }\n"
                    + "    }\n"
                    + "  }\n"
                    + "\n"
                    + "  void abc() {\n"
                    + "    bibWriter.print(bibProcessor.getOption(\"xxx\").getInt())\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that "OPTION STRING" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionString1() throws Exception {

        run("option string {xxx}{d e f}\n" + "function{abc}{xxx write$}", //
            PREFIX + HEAD + "    [\n" + "      xxx: \"def\",\n"
                    + "    ].each { name, value ->\n"
                    + "      if (bibProcessor.getOption(name) == null) {\n"
                    + "        bibProcessor.setOption(name, value)\n"
                    + "      }\n" + "    }\n" + "  }\n" + "\n"
                    + "  void abc() {\n"
                    + "    bibWriter.print(bibProcessor.getOption(\"xxx\"))\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that + is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlus1() throws Exception {

        run("function{abc}{+}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(v1, v2) {\n"
                    + "    return v2 + v1\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that + is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlus2() throws Exception {

        run("function{abc}{#1 #2 +}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 1 + 2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that preamble$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPreamble1() throws Exception {

        run("function{abc}{preamble$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return bibDB.getPreambleExpanded()\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for purify$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPurify1() throws Exception {

        run("function{abc}{purify$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.Purify\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  String abc(v1) {\n"
                    + "    return Purify.purify(v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that READ just adds a comment. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRead1() throws Exception {

        run("read", PREFIX + HEAD + "  }\n" + RUN + "    // read()\n"
                + POST_RUN);
    }

    /**
     * <testcase> Test that ITERATE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testReverse1() throws Exception {

        run("reverse{call.type$}",//
            PREFIX + HEAD + "  }\n\n" + "  void callType(Entry entry) {\n"
                    + "    def typeFunction = types[entry.getType()]\n"
                    + "    if (typeFunction == null) {\n"
                    + "      typeFunction = types['default.type']\n"
                    + "    }\n" + "    if (typeFunction == null) {\n"
                    + "      bstProcessor.warning('missing default.type')\n"
                    + "    } else {\n" + "      typeFunction(entry)\n    }\n"
                    + "  }\n" + RUN
                    + "    bibDB.getEntries().reverse().each {\n"
                    + "      callType(it)\n" + "    }\n" + POST_RUN);
    }

    /**
     * <testcase> Test that REVERSE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithReturnException.class)
    public void testReverseError1() throws Exception {

        run("reverse{+}", null);
    }

    /**
     * <testcase> Test that REVERSE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CommandWithArgumentsException.class)
    public void testReverseError2() throws Exception {

        run("reverse{write$}", null);
    }

    /**
     * <testcase> Test that SORT generates proper code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSort1() throws Exception {

        run("sort", PREFIX + HEAD + "  }\n" + RUN + "    bibDB.sort()\n"
                + POST_RUN);
    }

    /**
     * <testcase> Test that one String is properly initialized. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testString1() throws Exception {

        run("strings {abc}", PREFIX + "\n\n  String abc = ''" + POSTFIX);
    }

    /**
     * <testcase> Test that three Strings are properly initialized. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testString2() throws Exception {

        run("strings {a b c}", //
            PREFIX + "\n\n  String a = ''\n" + "  String b = ''\n"
                    + "  String c = ''" + POSTFIX);
    }

    /**
     * <testcase> Test that reading a String variable works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStrings3() throws Exception {

        run("strings{a} function{abc}{a}", //
            PREFIX + "\n\n  String a = ''" + HEAD + "  }\n" + "\n"
                    + "  String abc() {\n" + "    return a\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that writing a String variable works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStrings4() throws Exception {

        run("strings{a} function{abc}{'a :=}", //
            PREFIX + "\n\n  String a = ''" + HEAD + "  }\n" + "\n"
                    + "  void abc(v1) {\n" + "    a = v1\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the code for substring$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstring1() throws Exception {

        run("function{abc}{substring$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.Substring\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n"
                    + "  String abc(v1, v2, v3) {\n"
                    + "    return Substring.substring(v3,\n"
                    + "                               v2,\n"
                    + "                               v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the code for swap$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSwap1() throws Exception {

        run("function{abc}{#1 #2 swap$ +}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 2 + 1\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for swap$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSwap2() throws Exception {

        run("function{abc}{\"1\" \"2\" swap$ *}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"2\" + \"1\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for text.length$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTextLength1() throws Exception {

        run("function{abc}{text.length$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.TextLength\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  int abc(v1) {\n"
                    + "    return TextLength.textLength(v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the code for text.prefix$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTextPrefix1() throws Exception {

        run("function{abc}{text.prefix$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.TextPrefix\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX
                    + HEAD
                    + "  }\n\n" //
                    + "  String abc(v1, v2) {\n"
                    + "    return TextPrefix.textPrefix(v2,\n"
                    + "                                 v1)\n" //
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for type$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType1() throws Exception {

        run("function{abc}{type$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getType()\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that warning$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWarning1() throws Exception {

        run("function{abc}{warning$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(v1) {\n"
                    + "    bibProcessor.warning(v1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile1() throws Exception {

        run("function{abc}{{#1} 'skip$ while$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    while (1) {\n    }\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for width$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWidth1() throws Exception {

        run("function{abc}{width$}", //
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.Width\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n" //
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  int abc(v1) {\n"
                    + "    return Width.width(v1)\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite1() throws Exception {

        run("function{abc}{write$}", //
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(v1) {\n"
                    + "    bibWriter.print(v1)\n" + "  }\n" + RUN + POST_RUN);
    }

}
