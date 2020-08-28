/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.bst2groovy.exception.ChrToIntLengthException;
import org.extex.exbib.bst2groovy.exception.CommandWithArgumentsException;
import org.extex.exbib.bst2groovy.exception.CommandWithEntryException;
import org.extex.exbib.bst2groovy.exception.CommandWithReturnException;
import org.extex.exbib.bst2groovy.exception.ComplexFunctionException;
import org.extex.exbib.bst2groovy.exception.IfComplexException;
import org.extex.exbib.bst2groovy.exception.IfSyntaxException;
import org.extex.exbib.bst2groovy.exception.UnknownFunctionException;
import org.extex.exbib.bst2groovy.exception.UnknownVariableException;
import org.extex.exbib.bst2groovy.exception.WhileComplexException;
import org.extex.exbib.bst2groovy.exception.WhileSyntaxException;
import org.extex.exbib.bst2groovy.parameters.Parameter;
import org.extex.exbib.bst2groovy.parameters.ParameterType;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Ignore;
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
        @Override
        public void enableTracing(boolean flag) {


        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        @Override
        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            return new NamedInputStream(new ByteArrayInputStream(
                content.getBytes()), name);
        }
    }

    /**
     * The field <tt>POST_RUN</tt> contains the piece of code after the run
     * function.
     */
    public static final String POST_RUN = "  }\n" + "\n" + "}\n" + "\n"
            + "new Style(bibDB, bibWriter, bibProcessor).run()\n";

    /**
     * The field <tt>RUN</tt> contains the piece of code starting the run
     * function.
     */
    public static final String RUN = "\n" + "  void run() {\n";

    /**
     * The field <tt>HEAD</tt> contains the head of the style definition.
     */
    public static final String HEAD =
            "\n\n  Style(bibDB, bibWriter, bibProcessor) {\n\n"
                    + "    super(bibDB, bibWriter, bibProcessor)\n\n";

    /**
     * The field <tt>POSTFIX</tt> contains the post-fix string.
     */
    public static final String POSTFIX = HEAD + "  }\n" + RUN + POST_RUN;

    /**
     * The field <tt>CLASS_PREFIX</tt> contains the prefix code for the class.
     */
    public static final String CLASS_PREFIX = "\n"
            + "class Style extends org.extex.exbib.groovy.Style {";

    /**
     * The field <tt>PREFIX</tt> contains the prefix code.
     */
    public static final String PREFIX =
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
    protected static void run(String input, String output)
            throws ExBibImpossibleException,
                ExBibBstNotFoundException,
                ExBibException,
                IOException {

        run(input, output, false);
    }

    /**
     * Run a test.
     * 
     * @param input the input
     * @param output the output
     * @param optimize indicator for the optimizer
     * 
     * @throws ExBibImpossibleException in case of an error
     * @throws ExBibBstNotFoundException in case of an error
     * @throws ExBibException in case of an error
     * @throws IOException in case of an error
     */
    protected static void run(String input, String output, boolean optimize)
            throws ExBibImpossibleException,
                ExBibBstNotFoundException,
                ExBibException,
                IOException {

        Bst2Groovy bst2Groovy = new Bst2Groovy();
        bst2Groovy.setParameter(ParameterType.OPTIMIZE,
            new Parameter(optimize));

        Logger logger = Logger.getLogger(Bst2GroovyTest.class.getName());
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
    protected static void runOptimized(String input, String output)
            throws ExBibImpossibleException,
                ExBibBstNotFoundException,
                ExBibException,
                IOException {

        run(input, output, true);
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

        run("function{abc}{add.period$}",
            PREFIX
                    + HEAD
                    + "  }\n\n"
                    + "  String addPeriod(String s) {\n"
                    + "    return s == null || s == '' ? '' : s.matches(\".*[.!?]\") ? s : s + \".\"\n"
                    + "  }\n" + "\n" + "  String abc(p1) {\n"
                    + "    return addPeriod(p1)\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for change.case$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChangeCase1() throws Exception {

        run("function{abc}{change.case$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.ChangeCase\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n"
                    + "  String abc(p1, p2) {\n"
                    + "    return ChangeCase.changeCase(p2,\n"
                    + "                                 p1)\n" + "  }\n" + RUN
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

        run("function{abc}{chr.to.int$}",
            PREFIX
                    + HEAD
                    + "  }\n"
                    + "\n"
                    + "  int chrToInt(String s) {\n"
                    + "    if (s.length() != 1) {\n"
                    + "      bibProcessor.warning(\"argument to chrToInt has wrong length\")\n"
                    + "    }\n" + "    return s.charAt(0)\n" + "  }\n" + "\n"
                    + "  int abc(p1) {\n" + "    return chrToInt(p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for chr.to.int$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChrToInt2() throws Exception {

        run("function{abc}{\"x\" chr.to.int$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 120\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for chr.to.int$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ChrToIntLengthException.class)
    public void testChrToInt3() throws Exception {

        run("function{abc}{\"\" chr.to.int$}", null);
    }

    /**
     * <testcase> Test that the code for chr.to.int$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ChrToIntLengthException.class)
    public void testChrToInt4() throws Exception {

        run("function{abc}{\"xx\" chr.to.int$}", null);
    }

    /**
     * <testcase> Test that the code for cite$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCite1() throws Exception {

        run("function{abc}{cite$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getKey()\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a comment does not add code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComments1() throws Exception {

        run("% This is a comment",
            "// This is a comment\n// \n" + PREFIX + POSTFIX);
    }

    /**
     * <testcase> Test that a comment does not add code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComments2() throws Exception {

        run("%This is a comment",
            "// This is a comment\n// \n" + PREFIX + POSTFIX);
    }

    /**
     * <testcase> Test that a comment does not add code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComments3() throws Exception {

        run("%This is a comment\n% and another one",
            "// This is a comment\n// and another one\n// \n" + PREFIX
                    + POSTFIX);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat1() throws Exception {

        run("function{abc}{\"x\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(p1) {\n"
                    + "    return p1 + \"x\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat2() throws Exception {

        run("function{abc}{\"a\" \"b\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"a\" + \"b\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat2opt() throws Exception {

        runOptimized("function{abc}{\"a\" \"b\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"ab\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat3() throws Exception {

        run("function{abc}{\"\" \"b\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"\" + \"b\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat3opt() throws Exception {

        runOptimized("function{abc}{\"\" \"b\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"b\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat4() throws Exception {

        run("function{abc}{\"a\" \"\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"a\" + \"\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that * is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat4opt() throws Exception {

        runOptimized("function{abc}{\"a\" \"\" *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"a\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for duplicate$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate1() throws Exception {

        run("function{abc}{#2 duplicate$ +}",
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

        run("function{abc}{\"2\" duplicate$ *}",
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
    public void testDuplicate2opt() throws Exception {

        runOptimized("function{abc}{\"2\" duplicate$ *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"22\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for duplicate$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate3() throws Exception {

        run("function{abc}{#2 #3 + duplicate$ -}",
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

        run("function{abc}{empty$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int isEmpty(String s) {\n"
                    + "    return s == null || s.trim() == '' ? 1 : 0\n"
                    + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return isEmpty(p1) ? 1 : 0\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the entry itself does not add code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry1() throws Exception {

        run("entry {a b c}{}{}",
            PREFIX + POSTFIX);
    }

    /**
     * <testcase> Test that entry reading is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry2() throws Exception {

        run("entry{a}{b}{c}function{abc}{a}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getExpanded(\"a\",\n"
                    + "                             getDB())\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that entry writing is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry3() throws Exception {

        run("entry{a}{b}{c}function{abc}{'a := #0}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry, p1) {\n"
                    + "    entry.set(\"a\",\n              p1)\n"
                    + "    return 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that entry string reading is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry4() throws Exception {

        run("entry{a}{b}{c}function{abc}{b}",
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

        run("entry{a}{b}{c}function{abc}{'b := #0}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry, p1) {\n"
                    + "    entry.setLocal(\"b\",\n                   p1)\n"
                    + "    return 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that entry integer reading is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEntry6() throws Exception {

        run("entry{a}{b}{c}function{abc}{c}",
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

        run("entry{a}{b}{c}function{abc}{'c := #0}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(entry, p1) {\n"
                    + "    entry.setLocal(\"c\",\n                   p1)\n"
                    + "    return 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that = is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEq1() throws Exception {

        run("function{abc}{#1 =}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return 1 == p1 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that EXECUTE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute1() throws Exception {

        run("entry {a b c}{}{}\nexecute{skip$}",
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

        run("function{abc}{format.name$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.FormatName\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n"
                    + "  String abc(p1, p2, p3) {\n"
                    + "    return FormatName.formatName(p1,\n"
                    + "                                 p2,\n"
                    + "                                 p3)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that an unknown function leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownFunctionException.class)
    public void testFunction0() throws Exception {

        run("function{abc}{xxx}", null);
    }

    /**
     * <testcase> Test that an empty function is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction01() throws Exception {

        run("function{abc}{}",
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
    public void testFunction02() throws Exception {

        run("function{abc}{pop$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function with an integer return type is created
     * properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction03() throws Exception {

        run("function{abc}{#123}",
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
    public void testFunction04() throws Exception {

        run("function{abc}{\"abc\"}",
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
    public void testFunction05() throws Exception {

        run("function{abc}{#1 #2}", null);
    }

    /**
     * <testcase> Test that a function with two arguments is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction06() throws Exception {

        run("function{a}{pop$}function{abc}{a a}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void a(p1) {\n"
                    + "  }\n\n"
                    + "  void abc(p1, p2) {\n"
                    + "    a(p2)\n"
                    + "    a(p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function name created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction07() throws Exception {

        run("function{a.123}{pop$}function{v123}{pop$}function{aB_C}{a.123 v123}",
        PREFIX + HEAD + "  }\n\n"
                + "  void a123(p1) {\n"
                + "  }\n\n"
                + "  void v123_(p1) {\n"
                + "  }\n\n"
                + "  void abC(p1, p2) {\n"
                + "    a123(p2)\n"
                + "    v123_(p1)\n"
                + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function name created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction08() throws Exception {

        run("function{public}{pop$}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void public1(p1) {\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function name created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction09() throws Exception {

        run("function{h}{#1}function{f}{h}",
            PREFIX + HEAD + "  }\n\n"
                    + "  int h() {\n"
                    + "    return 1\n"
                    + "  }\n\n"
                    + "  int f() {\n"
                    + "    return h()\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function name created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction10() throws Exception {

        run("entry{x}{}{}function{f}{x}",
            PREFIX + HEAD + "  }\n\n"
                    + "  String f(entry) {\n"
                    + "    return entry.getExpanded(\"x\",\n"
                    + "                             getDB())\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function name created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction11() throws Exception {

        run("entry{x}{}{}function{f}{x pop$}function{g}{x pop$}",
            PREFIX + "\n\n  Map types = [\n"
                    + "    f : { entry -> f(entry) },\n"
                    + "    g : { entry -> g(entry) },\n" + "  ]" + HEAD
                    + "  }\n\n"
                    + "  void f(entry) {\n"
                    + "  }\n\n"
                    + "  void g(entry) {\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that a function name created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction12() throws Exception {

        run("entry{x}{}{}" + "function{f}{x pop$ pop$}"
                + "function{g}{x pop$ pop$ pop$}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void f(entry, p1) {\n"
                    + "  }\n\n"
                    + "  void g(entry, p1, p2) {\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the function arguments are created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction13() throws Exception {

        run("function{a}{write$ write$}"
                + "function{b}{a}"
                + "function{c}{\"A\" \"B\" a}"
                + "function{d}{\"A\" \"B\" b}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void a(p1, p2) {\n"
                    + "    write(p2)\n"
                    + "    write(p1)\n"
                    + "  }\n"
                    + "\n"
                    + "  void b(p1, p2) {\n"
                    + "    a(p1,\n"
                    + "      p2)\n"
                    + "  }\n"
                    + "\n"
                    + "  void c() {\n"
                    + "    a(\"A\",\n"
                    + "      \"B\")\n"
                    + "  }\n"
                    + "\n"
                    + "  void d() {\n"
                    + "    b(\"A\",\n"
                    + "      \"B\")\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the function arguments are created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunction13opt() throws Exception {

        runOptimized("function{a}{write$ write$}"
                + "function{b}{a}"
                + "function{c}{\"A\" \"B\" a}"
                + "function{d}{\"A\" \"B\" b}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void a(p1, p2) {\n"
                    + "    write(p2,\n"
                    + "          p1)\n"
                    + "  }\n"
                    + "\n"
                    + "  void b(p1, p2) {\n"
                    + "    a(p1,\n"
                    + "      p2)\n"
                    + "  }\n"
                    + "\n"
                    + "  void c() {\n"
                    + "    a(\"A\",\n"
                    + "      \"B\")\n"
                    + "  }\n"
                    + "\n"
                    + "  void d() {\n"
                    + "    b(\"A\",\n"
                    + "      \"B\")\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the function arguments are created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore("This functionality is not implemented yet.")
    public void testFunction20opt() throws Exception {

        runOptimized(
            "STRINGS { s t }\n"
                    + "INTEGERS { nameptr namesleft numnames }\n"
                    + "\n"
                    + "FUNCTION {format.names}\n"
                    + "{ 's :=\n"
                    + "  #1 'nameptr :=\n"
                    + "  s num.names$ 'numnames :=\n"
                    + "  numnames 'namesleft :=\n"
                    + "    { namesleft #0 > }\n"
                    + "    { s nameptr \"{ff~}{vv~}{ll}{, jj}\" format.name$ 't :=\n"
                    + "      nameptr #1 >\n"
                    + " { namesleft #1 >\n"
                    + "     { \", \" * t * }\n"
                    + "     { numnames #2 >\n"
                    + "     { \",\" * }\n"
                    + "     'skip$\n"
                    + "       if$\n"
                    + "       t \"others\" =\n"
                    + "     { \" et~al.\" * }\n"
                    + "     { \" and \" * t * }\n"
                    + "       if$\n"
                    + "     }\n"
                    + "   if$\n"
                    + " }\n"
                    + " 't\n"
                    + "      if$\n"
                    + "      nameptr #1 + 'nameptr :=\n"
                    + "      namesleft #1 - 'namesleft :=\n"
                    + "    }\n"
                    + "  while$\n"
                    + "}"
            ,
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.FormatName\n"
                    + "import org.extex.exbib.core.bst.code.impl.NumNames\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + HEAD + "  }\n\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the parameters of a function and the call are in the
     * correct order. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunctionA1() throws Exception {

        runOptimized(
            "STRINGS { s }\n"
                    + "\n"
                    + "FUNCTION {output.nonnull}\n"
                    + "{ 's :=\n"
                    + "  #1 #2 =\n"
                    + "    { \", \" * write$ }\n"
                    + "    { #1 #3 =\n"
                    + "        { add.period$ write$\n"
                    + "          newline$\n"
                    + "          \"\\newblock \" write$\n"
                    + "        }\n"
                    + "        { #1 #4 =\n"
                    + "            'write$\n"
                    + "            { add.period$ \" \" * write$ }\n"
                    + "          if$\n" + "        }\n"
                    + "      if$\n"
                    + "    }\n"
                    + "  if$\n"
                    + "  s\n"
                    + "}\n"
                    + "\n"
                    + "FUNCTION {output}\n"
                    + "{ duplicate$ empty$\n"
                    + "    'pop$\n" + "    'output.nonnull\n"
                    + "  if$\n"
                    + "}\n",
            PREFIX
                    + "\n\n  String s = ''"
                    + HEAD
                    + "  }\n"
                    + "\n"
                    + "  String addPeriod(String s) {\n"
                    + "    return s == null || s == '' ? '' : s.matches(\".*[.!?]\") ? s : s + \".\"\n"
                    + "  }\n"
                    + "\n"
                    + "  int isEmpty(String s) {\n"
                    + "    return s == null || s.trim() == '' ? 1 : 0\n"
                    + "  }\n"
                    + "\n"
                    + "  String outputNonnull(p1, p2) {\n"
                    + "    s = p2\n"
                    + "    write(addPeriod(p1) + \" \")\n"
                    + "    return s\n"
                    + "  }\n"
                    + "\n"
                    + "  String output(p1, p2) {\n"
                    + "    return ( isEmpty(p2) ? p1 : outputNonnull(p1,\n"
                    + "                                              p2) )\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the parameters of a function and the call are in the
     * correct order. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunctionA2() throws Exception {

        run("STRINGS { s t }\n"
                + "\n"
                + "FUNCTION {f}\n"
                + "{ \n"
                + "  #1 #2 =\n"
                + "    { 't :=\n"
                + "      's :=\n"
                + "    }\n"
                + "    { 't :=\n"
                + "      's :=\n"
                + "    }\n"
                + "  if$\n"
                + "}\n"
                + "\n"
                + "FUNCTION {g}\n"
                + "{ 't :=\n"
                + "  's :=\n"
                + "}\n"
                + "\n"
                + "FUNCTION {h}\n"
                + "{ \n"
                + "  \"A\" \"B\" f\n"
                + "}\n",
            PREFIX + "\n\n  String s = ''" + "\n  String t = ''" + HEAD
                    + "  }\n"
                    + "\n"
                    + "  void f(p1, p2) {\n"
                    + "    if (2 == 1 ? 1 : 0) {\n"
                    + "      t = p2\n"
                    + "      s = p1\n"
                    + "    } else {\n"
                    + "      t = p2\n"
                    + "      s = p1\n"
                    + "    }\n"
                    + "  }\n"
                    + "\n"
                    + "  void g(p1, p2) {\n"
                    + "    t = p2\n"
                    + "    s = p1\n"
                    + "  }\n"
                    + "\n"
                    + "  void h() {\n"
                    + "    f(\"A\",\n"
                    + "      \"B\")\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the parameters of a function and the call are in the
     * correct order. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunctionA2opt() throws Exception {

        runOptimized("STRINGS { s t }\n"
                + "\n"
                + "FUNCTION {f}\n"
                + "{ \n"
                + "  #1 #2 =\n"
                + "    { 't :=\n"
                + "      's :=\n"
                + "    }\n"
                + "    { 't :=\n"
                + "      's :=\n"
                + "    }\n"
                + "  if$\n"
                + "}\n"
                + "\n"
                + "FUNCTION {g}\n"
                + "{ 't :=\n"
                + "  's :=\n"
                + "}\n"
                + "\n"
                + "FUNCTION {h}\n"
                + "{ \n"
                + "  \"A\" \"B\" f\n"
                + "}\n",
            PREFIX + "\n\n  String s = ''" + "\n  String t = ''" + HEAD
                    + "  }\n"
                    + "\n"
                    + "  void f(p1, p2) {\n"
                    + "    t = p2\n"
                    + "    s = p1\n"
                    + "  }\n"
                    + "\n"
                    + "  void g(p1, p2) {\n"
                    + "    t = p2\n"
                    + "    s = p1\n"
                    + "  }\n"
                    + "\n"
                    + "  void h() {\n"
                    + "    f(\"A\",\n"
                    + "      \"B\")\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the parameters of a function and the call are in the
     * correct order. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunctionA3() throws Exception {

        runOptimized(""
                + "FUNCTION {aaa}\n"
                + "{ write$ write$\n"
                + "}\n"
                + "\n"
                + "FUNCTION {output}\n"
                + "{ duplicate$ empty$\n"
                + "    'pop$\n" + "    'aaa\n"
                + "  if$\n"
                + "}\n",
            PREFIX + "" + HEAD + "  }\n"
                    + "\n"
                    + "  int isEmpty(String s) {\n"
                    + "    return s == null || s.trim() == '' ? 1 : 0\n"
                    + "  }\n"
                    + "\n"
                    + "  void aaa(p1, p2) {\n"
                    + "    write(p2,\n"
                    + "          p1)\n"
                    + "  }\n"
                    + "\n"
                    + "  void output(p1, p2) {\n"
                    + "    if (! isEmpty(p2)) {\n"
                    + "      aaa(p1,\n"
                    + "          p2)\n"
                    + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that global.max$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobalMax1() throws Exception {

        run("function{abc}{global.max$}",
            PREFIX + HEAD + "  }\n" + "\n"
                    + "  private static final int GLOBAL_MAX = 65535\n\n"
                    + "  int abc() {\n" + "    return GLOBAL_MAX\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that > is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt1() throws Exception {

        run("function{abc}{>}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1, p2) {\n"
                    + "    return p1 > p2 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that > is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt2() throws Exception {

        run("function{abc}{#2 #1 >}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 2 > 1 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf01() throws Exception {

        run("function{abc}{#1 'skip$ 'skip$ if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    if (1) {\n    }\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf01opt() throws Exception {

        runOptimized("function{abc}{#1 'skip$ 'skip$ if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf02opt() throws Exception {

        runOptimized("function{abc}{#1 {#2} {#3} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf03opt() throws Exception {

        runOptimized("function{abc}{#1 {pop$ #2} {} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return 2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf04opt() throws Exception {

        runOptimized("function{abc}{#1 {#2} 'skip$ if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return 2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf05() throws Exception {

        run("function{abc}{#1 {newline$} 'skip$ if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    if (1) {\n" + "      newline()\n" + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf06() throws Exception {

        run("function{abc}{#1 'skip$ {newline$} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    if (1) {\n" + "    } else {\n" + "      newline()\n"
                    + "    }\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf07() throws Exception {

        run("function{abc}{#1 {newline$} {newline$} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    if (1) {\n" + "      newline()\n" + "    } else {\n"
                    + "      newline()\n" + "    }\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf08opt() throws Exception {

        runOptimized("function{abc}{#1 {#1 'skip$ 'skip$ if$} 'skip$ if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf09opt() throws Exception {

        runOptimized("function{abc}{#1 'skip$ {#1 'skip$ 'skip$ if$} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IfSyntaxException.class)
    public void testIf10() throws Exception {

        run("function{abc}{#1 newline$ {newline$} if$}", null);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IfSyntaxException.class)
    public void testIf11() throws Exception {

        run("function{abc}{#1 {newline$} newline$ if$}", null);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf12opt() throws Exception {

        runOptimized("function{abc}{{>} {#3} {#2} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1, p2) {\n"
                    + "    return ( p1 > p2 ? 3 : 2 )\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf13opt() throws Exception {

        runOptimized("function{abc}{#1 {>} {#3} {#2} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return ( p1 > 1 ? 3 : 2 )\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf14opt() throws Exception {

        runOptimized("function{abc}{\"\" {=} {#3} {#2} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return ( \"\" == p1 ? 3 : 2 )\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IfComplexException.class)
    public void testIf15() throws Exception {

        run("function{abc}{{pop$ pop$} {} {newline$} if$}", null);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf16() throws Exception {

        run("function{a}{#1}function{abc}{{a} {} {newline$} if$}",
            PREFIX + HEAD + "  }\n\n"
                    + "  int a() {\n"
                    + "    return 1\n"
                    + "  }\n\n"
                    + "  void abc() {\n"
                    + "    if (a()) {\n    } else {\n"
                    + "      newline()\n"
                    + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf17() throws Exception {

        run("function{abc}{{#2} {write$ #3} {pop$ #4} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    int v4\n" + "    if (2) {\n" + "      write(p1)\n"
                    + "      v4 = 3\n" + "    } else {\n" + "      v4 = 4\n"
                    + "    }\n" + "    return v4\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf18() throws Exception {

        run("function{abc}{{#1} {write$} {{#2} {write$} {write$} if$} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n"
                    + "    if (1) {\n" + "      write(p1)\n"
                    + "    } else {\n      if (2) {\n" + "        write(p1)\n"
                    + "      } else {\n" + "        write(p1)\n" + "      }\n"
                    + "    }\n  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf19() throws Exception {

        String s = "....,....,....,....,....,....";
        run("function{abc}{\"" + s + "\" \"\" {=} {pop$ #2} {} if$}",
            PREFIX
                    + HEAD
                    + "  }\n"
                    + "\n"
                    + "  int abc(p1) {\n"
                    + "    int v6\n"
                    + "    if (\"\" == \"....,....,....,....,....,....\" ? 1 : 0) {\n"
                    + "      v6 = 2\n" + "    } else {\n" + "      v6 = p1\n"
                    + "    }\n" + "    return v6\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf19opt() throws Exception {

        String s = "....,....,....,....,....,....";
        runOptimized("function{abc}{\"" + s + "\" \"\" {=} {pop$ #2} {} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return p1\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is treated properly when creating a conjunction.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf20opt() throws Exception {

        runOptimized(
            "function{abc}{duplicate$ #2 {>} {#9 {<} {newline$} {} if$} {} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n"
                    + "    if (p1 < 9 && p1 > 2) {\n" + "      newline()\n"
                    + "    }\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that if$ is treated properly when creating a conjunction.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf21opt() throws Exception {

        runOptimized(
            "function{abc}{#2 {>} {#1 #9 {<} {newline$} {} if$} {} if$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n"
                    + "    if (p1 > 2) {\n" + "      newline()\n" + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
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

        run("integers {a b c}",
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

        run("integers{a} function{abc}{a}",
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

        run("integers{a} function{abc}{'a :=}",
            PREFIX + "\n\n  int a = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1) {\n"
                    + "    a = p1\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for int.to.chr$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntToChr1() throws Exception {

        run("function{abc}{int.to.chr$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(p1) {\n"
                    + "    return Character.toString((char)(p1))\n" + "  }\n"
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

        run("function{abc}{int.to.str$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(p1) {\n"
                    + "    return Integer.toString(p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that ITERATE adds code to the main program. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIterate1() throws Exception {

        run("iterate{call.type$}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void callType(Entry entry) {\n"
                    + "    def typeFunction = types[entry.getType()]\n"
                    + "    if (typeFunction == null) {\n"
                    + "      typeFunction = types['default.type']\n"
                    + "      if (typeFunction == null) {\n"
                    + "        warning('missing default.type')\n"
                    + "        return\n"
                    + "      }\n"
                    + "    }\n"
                    + "    typeFunction(entry)\n"
                    + "  }\n\n"
                    + "  void run() {\n" + "    getDB().each {\n"
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
    public void testLocatorLine1() throws Exception {

        run("function{abc}{locator.line$ write$}",
            PREFIX + "\n\n  Map types = [\n"
                    + "    abc : { entry -> abc(entry) },\n" + "  ]" + HEAD
                    + "  }\n" + "\n" + "  void abc(entry) {\n"
                    + "    write(entry.getLocator().getLine())\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that "location.line$" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLocatorLine2() throws Exception {

        run("function{abc}{locator.line$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc(entry) {\n"
                    + "    return entry.getLocator().getLine()\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that assigning to locator.line$ is not possible.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownVariableException.class)
    public void testLocatorLine3() throws Exception {

        run("function{abc}{\"\" locator.line$ :=}", "");
    }

    /**
     * <testcase> Test that "location.resource$" is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLocatorResource1() throws Exception {

        run("function{abc}{locator.resource$ write$}",
            PREFIX + "\n\n  Map types = [\n"
                    + "    abc : { entry -> abc(entry) },\n" + "  ]" + HEAD
                    + "  }\n" + "\n" + "  void abc(entry) {\n"
                    + "    write(entry.getLocator().getResource())\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that assigning to locator.resource$ is not possible.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownVariableException.class)
    public void testLocatorResource2() throws Exception {

        run("function{abc}{\"\" locator.resource$ :=}", "");
    }

    /**
     * <testcase> Test that < is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLt1() throws Exception {

        run("function{abc}{<}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1, p2) {\n"
                    + "    return p1 < p2 ? 1 : 0\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that < is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLt2() throws Exception {

        run("function{abc}{#1 #2 <}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc() {\n"
                    + "    return 1 < 2 ? 1 : 0\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that @macro works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro1() throws Exception {

        run("macro{xxx}{\"y y y\"}",
            PREFIX
                    + HEAD
                    + "    [\n"
                    + "      xxx:  \"y y y\",\n"
                    + "    ].each { name, value -> defineString(name, value) }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that @macro works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro2() throws Exception {

        run("macro{x.x.x}{\"y y y\"}",
            PREFIX
                    + HEAD
                    + "    [\n"
                    + "      'x.x.x':  \"y y y\",\n"
                    + "    ].each { name, value -> defineString(name, value) }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that - is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus1() throws Exception {

        run("function{abc}{-}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1, p2) {\n"
                    + "    return p1 - p2\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that - is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus2() throws Exception {

        run("function{abc}{#1 #2 -}",
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

        run("function{abc}{missing$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1) {\n"
                    + "    return p1 == null\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that newline$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewline1() throws Exception {

        run("function{abc}{newline$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc() {\n"
                    + "    newline()\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNot1() throws Exception {

        run("integers{x} function{abc}{{>} {} {#2 'x :=} if$}",
            PREFIX + "\n\n  int x = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1, p2) {\n" + "    if (p1 > p2 ? 1 : 0) {\n"
                    + "    } else {\n" + "      x = 2\n"
                    + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNot1opt() throws Exception {

        runOptimized("integers{x} function{abc}{{>} {} {#2 'x :=} if$}",
            PREFIX + "\n\n  int x = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1, p2) {\n"
                    + "    if (p1 >= p2) {\n      x = 2\n"
                    + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNot2() throws Exception {

        run("integers{x} function{abc}{{<} {} {#2 'x :=} if$}",
            PREFIX + "\n\n  int x = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1, p2) {\n" + "    if (p1 < p2 ? 1 : 0) {\n"
                    + "    } else {\n" + "      x = 2\n"
                    + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNot2opt() throws Exception {

        runOptimized("integers{x} function{abc}{{<} {} {#2 'x :=} if$}",
            PREFIX + "\n\n  int x = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1, p2) {\n" + "    if (p1 <= p2) {\n"
                    + "      x = 2\n"
                    + "    }\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNot3() throws Exception {

        run("integers{x} function{abc}{{=} {} {#2 'x :=} if$}",
            PREFIX + "\n\n  int x = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1, p2) {\n"
                    + "    if (p2 == p1 ? 1 : 0) {\n" + "    } else {\n"
                    + "      x = 2\n    }\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that not is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNot3opt() throws Exception {

        runOptimized("integers{x} function{abc}{{=} {} {#2 'x :=} if$}",
            PREFIX + "\n\n  int x = 0" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1, p2) {\n"
                    + "    if (p2 != p1) {\n      x = 2\n    }\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for num.names$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumNames1() throws Exception {

        run("function{abc}{num.names$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.NumNames\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  String abc(p1) {\n"
                    + "    return NumNames.numNames(p1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that "OPTION INTEGER" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionInteger1() throws Exception {

        run("option integer {xxx}{#42}\n" + "function{abc}{xxx write$}",
            PREFIX + HEAD + "    [\n" + "      xxx: \"42\",\n"
                    + "    ].each { name, value ->\n"
                    + "      if (bibProcessor.getOption(name) == null) {\n"
                    + "        bibProcessor.setOption(name, value)\n"
                    + "      }\n" + "    }\n" + "  }\n" + "\n"
                    + "  void abc() {\n"
                    + "    write(bibProcessor.getOption(\"xxx\").getInt())\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that "OPTION INTEGER" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionInteger2() throws Exception {

        run("option integer {xxx}{#42}\n" + "function{abc}{#7 'xxx :=}",
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
                    + "    bibProcessor.setOption(\"xxx\",\n                           7)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that "OPTION STRING" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionString1() throws Exception {

        run("option string {xxx}{d e f}\n" + "function{abc}{xxx write$}",
            PREFIX + HEAD + "    [\n" + "      xxx: \"def\",\n"
                    + "    ].each { name, value ->\n"
                    + "      if (bibProcessor.getOption(name) == null) {\n"
                    + "        bibProcessor.setOption(name, value)\n"
                    + "      }\n" + "    }\n" + "  }\n" + "\n"
                    + "  void abc() {\n"
                    + "    write(bibProcessor.getOption(\"xxx\"))\n" + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that assigning an "OPTION STRING" is not possible.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownVariableException.class)
    public void testOptionString2() throws Exception {

        run("option string {xxx}{d e f}\n" + "function{abc}{xxx 'x :=}", "");
    }

    /**
     * <testcase> Test that "OPTION STRING" is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOptionString3() throws Exception {

        run("option string {xxx}{d e f}\n" + "function{abc}{\"\" 'xxx :=}",
            PREFIX + HEAD + "    [\n" + "      xxx: \"def\",\n"
                    + "    ].each { name, value ->\n"
                    + "      if (bibProcessor.getOption(name) == null) {\n"
                    + "        bibProcessor.setOption(name, value)\n"
                    + "      }\n" + "    }\n" + "  }\n"
                    + "\n"
                    + "  void abc() {\n"
                    + "    bibProcessor.setOption(\"xxx\",\n"
                    + "                           \"\")\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that + is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlus1() throws Exception {

        run("function{abc}{+}",
            PREFIX + HEAD + "  }\n" + "\n" + "  int abc(p1, p2) {\n"
                    + "    return p1 + p2\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that + is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlus2() throws Exception {

        run("function{abc}{#1 #2 +}",
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

        run("function{abc}{preamble$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return getDB().getPreambleExpanded()\n" + "  }\n"
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

        run("function{abc}{purify$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.Purify\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  String abc(p1) {\n"
                    + "    return Purify.purify(p1)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that ' on an unknown function croaks. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore("This functionality is not implemented yet.")
    public void testQ1() throws Exception {

        run("function{abc}{'a}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"\\\"\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that ' is treated properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testQ2() throws Exception {

        run("integers{a}function{abc}{'a}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"\\\"\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that quote$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testQuote1() throws Exception {

        run("function{abc}{quote$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"\\\"\"\n" + "  }\n" + RUN + POST_RUN);
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

        run("reverse{call.type$}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void callType(Entry entry) {\n"
                    + "    def typeFunction = types[entry.getType()]\n"
                    + "    if (typeFunction == null) {\n"
                    + "      typeFunction = types['default.type']\n"
                    + "      if (typeFunction == null) {\n"
                    + "        warning('missing default.type')\n"
                    + "        return\n"
                    + "      }\n"
                    + "    }\n"
                    + "    typeFunction(entry)\n"
                    + "  }\n"
                    + RUN + "    getDB().getEntries().reverse().each {\n"
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
     * <testcase> Test that assigning to a number is not possible. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownVariableException.class)
    public void testSet1() throws Exception {

        run("function{abc}{'x #1 :=}", "");
    }

    /**
     * <testcase> Test that assigning to a string is not possible. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownVariableException.class)
    public void testSet2() throws Exception {

        run("function{abc}{'x \".\" :=}", "");
    }

    /**
     * <testcase> Test that assigning to a block is not possible. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownVariableException.class)
    public void testSet3() throws Exception {

        run("function{abc}{'x {} :=}", "");
    }

    /**
     * <testcase> Test that SORT generates proper code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSort1() throws Exception {

        run("sort", PREFIX + HEAD + "  }\n" + RUN + "    getDB().sort()\n"
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

        run("strings {a b c}",
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

        run("strings{a} function{abc}{a}",
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

        run("strings{a} function{abc}{'a :=}",
            PREFIX + "\n\n  String a = ''" + HEAD + "  }\n" + "\n"
                    + "  void abc(p1) {\n"
                    + "    a = p1\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for substring$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstring1() throws Exception {

        run("function{abc}{substring$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.Substring\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n"
                    + "  String abc(p1, p2, p3) {\n"
                    + "    return Substring.substring(p1,\n"
                    + "                               p2,\n"
                    + "                               p3)\n" + "  }\n" + RUN
                    + POST_RUN);
    }

    /**
     * <testcase> Test that the code for swap$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSwap1() throws Exception {

        run("function{abc}{#1 #2 swap$ +}",
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

        run("function{abc}{\"1\" \"2\" swap$ *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"2\" + \"1\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for swap$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSwap2opt() throws Exception {

        runOptimized("function{abc}{\"1\" \"2\" swap$ *}",
            PREFIX + HEAD + "  }\n" + "\n" + "  String abc() {\n"
                    + "    return \"21\"\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for text.length$ is created properly.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTextLength1() throws Exception {

        run("function{abc}{text.length$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.TextLength\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  int abc(p1) {\n"
                    + "    return TextLength.textLength(p1)\n" + "  }\n" + RUN
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

        run("function{abc}{text.prefix$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.TextPrefix\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX
                    + HEAD
                    + "  }\n\n"
                    + "  String abc(p1, p2) {\n"
                    + "    return TextPrefix.textPrefix(p1,\n"
                    + "                                 p2)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the function toString() works correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString() throws Exception {

        assertEquals("<Bst2Groovy>", (new Bst2Groovy()).toString());
    }

    /**
     * <testcase> Test that the code for type$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType1() throws Exception {

        run("function{abc}{type$}",
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

        run("function{abc}{warning$}",
            PREFIX + HEAD + "  }\n\n"
                    + "  void abc(p1) {\n"
                    + "    warning(p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile01() throws Exception {

        run("function{abc}{{#1} 'skip$ while$}",
            PREFIX + HEAD
                    + "  }\n"
                    + "\n"
                    + "  void abc() {\n"
                    + "    while (1) {\n    }\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile02() throws Exception {

        run("function{abc}{{#1} {} while$}",
            PREFIX + HEAD + "  }\n"
                    + "\n"
                    + "  void abc() {\n"
                    + "    while (1) {\n    }\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = WhileSyntaxException.class)
    public void testWhile03() throws Exception {

        run("function{abc}{{#1} #1 while$}", null);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = WhileComplexException.class)
    public void testWhile04() throws Exception {

        run("function{abc}{{#1} {#1} while$}", null);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = WhileSyntaxException.class)
    public void testWhile05() throws Exception {

        run("function{abc}{\"\" {} while$}", null);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = WhileComplexException.class)
    public void testWhile06() throws Exception {

        run("function{abc}{{#1 #2} {} while$}", null);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile07() throws Exception {

        run("function{abc}{#1 {} while$}",
            PREFIX + HEAD + "  }\n"
                    + "\n"
                    + "  void abc() {\n"
                    + "    while (1) {\n    }\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile08() throws Exception {

        run("function{abc}{{#1} {pop$ #12} while$ }",
            PREFIX + HEAD + "  }\n"
                    + "\n"
                    + "  int abc() {\n"
                    + "    int v1\n"
                    + "    while (1) {\n"
                    + "      v1 = 12\n"
                    + "    }\n" +
                    "    return v1\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile09() throws Exception {

        run("function{abc}{#2 {#1} {pop$ #12} while$ }",
            PREFIX + HEAD + "  }\n"
                    + "\n"
                    + "  int abc() {\n"
                    + "    int v1 = 2\n"
                    + "    while (1) {\n"
                    + "      v1 = 12\n"
                    + "    }\n"
                    + "    return v1\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile10() throws Exception {

        run("function{abc}{#2 {#1 #1 =} {pop$ #12} while$ }",
            PREFIX + HEAD + "  }\n" + "\n"
                    + "  int abc() {\n"
                    + "    int v1 = 2\n"
                    + "    while (1) {\n"
                    + "      v1 = 12\n"
                    + "    }\n"
                    + "    return v1\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile10opt() throws Exception {

        runOptimized("function{abc}{#2 {#1 #1 =} {pop$ #12} while$ }",
            PREFIX + HEAD + "  }\n"
                    + "\n"
                    + "  int abc() {\n"
                    + "    int v1 = 2\n"
                    + "    while (1) {\n"
                    + "      v1 = 12\n"
                    + "    }\n"
                    + "    return v1\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile11() throws Exception {

        run("integers{x}"
                + "function{abc}{\n"
                + " {x #10 <} {{x #0 >}{pop$ #42}{#12}if$} while$"
                + "}",
            PREFIX + "\n\n  int x = 0"
                    + HEAD + "  }\n"
                    + "\n"
                    + "  int abc() {\n"
                    + "    int v1\n"
                    + "    while (x < 10) {\n"
                    + "      int v3\n"
                    + "      if (x > 0 ? 1 : 0) {\n"
                    + "        v3 = 42\n"
                    + "      } else {\n"
                    + "        v3 = 12\n"
                    + "      }\n"
                    + "      v1 = v3\n"
                    + "    }\n"
                    + "    return v1\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that while$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile11opt() throws Exception {

        runOptimized("integers{x}"
                + "function{abc}{\n"
                + " {x #10 <} {{x #0 >}{pop$ #42}{#12}if$} while$"
                + "}",
            PREFIX + "\n\n  int x = 0"
                    + HEAD + "  }\n"
                    + "\n"
                    + "  int abc() {\n"
                    + "    int v1\n"
                    + "    while (x < 10) {\n"
                    + "      int v3 = ( x > 0 ? 42 : 12 )\n"
                    + "      v1 = v3\n"
                    + "    }\n"
                    + "    return v1\n"
                    + "  }\n"
                    + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that the code for width$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWidth1() throws Exception {

        run("function{abc}{width$}",
            "import org.extex.exbib.core.Processor\n"
                    + "import org.extex.exbib.core.bst.code.impl.Width\n"
                    + "import org.extex.exbib.core.db.DB\n"
                    + "import org.extex.exbib.core.db.Entry\n"
                    + "import org.extex.exbib.core.io.Writer\n"
                    + CLASS_PREFIX + HEAD + "  }\n\n" + "  int abc(p1) {\n"
                    + "    return Width.width(p1)\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite1() throws Exception {

        run("function{abc}{write$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n"
                    + "    write(p1)\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite2() throws Exception {

        run("function{abc}{write$ write$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1, p2) {\n"
                    + "    write(p2)\n"
                    + "    write(p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite2opt() throws Exception {

        runOptimized("function{abc}{write$ write$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1, p2) {\n"
                    + "    write(p2,\n"
                    + "          p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite3() throws Exception {

        run("function{abc}{write$ write$ write$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1, p2, p3) {\n"
                    + "    write(p3)\n"
                    + "    write(p2)\n"
                    + "    write(p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite3opt() throws Exception {

        runOptimized("function{abc}{write$ write$ write$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1, p2, p3) {\n"
                    + "    write(p3,\n"
                    + "          p2,\n"
                    + "          p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite4() throws Exception {

        run("function{abc}{write$ #42}",
            PREFIX + HEAD + "  }\n" + "\n"
                    + "  int abc(p1) {\n"
                    + "    write(p1)\n"
                    + "    return 42\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite5opt() throws Exception {

        runOptimized("function{abc}{write$ #12}\n"
                + "function{d}{#99 abc  #34 write$ write$}\n",
            PREFIX + HEAD + "  }\n" + "\n"
                    + "  int abc(p1) {\n"
                    + "    write(p1)\n"
                    + "    return 12\n"
                    + "  }\n"
                    + "\n"
                    + "  void d() {\n"
                    + "    int v1 = abc(99)\n"
                    + "    write(34,\n"
                    + "          v1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWriteNewline1() throws Exception {

        run("function{abc}{write$ newline$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n"
                    + "    write(p1)\n"
                    + "    newline()\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWriteNewline1opt() throws Exception {

        runOptimized("function{abc}{write$ newline$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1) {\n"
                    + "    writeln(p1)\n" + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWriteNewline2() throws Exception {

        run("function{abc}{write$ write$ newline$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1, p2) {\n"
                    + "    write(p2)\n"
                    + "    write(p1)\n"
                    + "    newline()\n"
                    + "  }\n" + RUN + POST_RUN);
    }

    /**
     * <testcase> Test that write$ is created properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWriteNewline2opt() throws Exception {

        runOptimized("function{abc}{write$ write$ newline$}",
            PREFIX + HEAD + "  }\n" + "\n" + "  void abc(p1, p2) {\n"
                    + "    writeln(p2,\n"
                    + "            p1)\n"
                    + "  }\n" + RUN + POST_RUN);
    }

}
