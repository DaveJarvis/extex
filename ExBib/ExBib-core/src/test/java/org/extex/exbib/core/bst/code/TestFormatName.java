/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * This file is part of ExBib a BibTeX compatible database.
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

package org.extex.exbib.core.bst.code;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.ProcessorBibtex099c;
import org.extex.exbib.core.bst.code.impl.FormatName099;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>format.name$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TestFormatName extends TestCase {

    /**
     * The main program just uses the text interface of JUnit.
     * 
     * @param args command line parameters are ignored
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * Generate a new test suite
     * 
     * @return the new test suite
     */
    public static Test suite() {

        return new TestSuite(TestFormatName.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestFormatName(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new ProcessorBibtex099c(new DBImpl(), new NullWriter(null), null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new FormatName099("format.name$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * Run a test.
     * 
     * @param names the names to extract one name from
     * @param pos the position
     * @param fmt the format
     * @param expected the expected result
     * 
     * @throws Exception in case of an error
     */
    private void testFormat(String names, int pos, String fmt, String expected)
            throws Exception {

        p.push(new TString(names));
        p.push(new TInteger(pos));
        p.push(new TString(fmt));
        new FormatName099("format.name$").execute(p, null, null);

        String s = p.popString(null).getValue();

        assertEquals(expected, s);
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> Test specifier f alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0110() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{f}", "G");
    }

    /**
     * <testcase> Test specifier v alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0111() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{v}", "");
    }

    /**
     * <testcase> Test specifier j alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0112() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{j}", "");
    }

    /**
     * <testcase> Test specifier ll alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat012() throws Exception {

        testFormat("Gerd Neugebauer and others", 2, "{ll}", "others");
    }

    /**
     * <testcase> Test a constant format. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat013() throws Exception {

        testFormat("Gerd Neugebauer and others", 1, "abcd", "abcd");
    }

    /**
     * <testcase> Test mixed constant and variable pieces. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat014() throws Exception {

        testFormat("Gerd Neugebauer and others", 1, "ab{ll}cd",
            "abNeugebauercd");
    }

    /**
     * <testcase> Test the specifier ll. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat015() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{ll}", "Neugebauer");
    }

    /**
     * <testcase> Test the specifier ff. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat016() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{ff}", "Gerd");
    }

    /**
     * <testcase> Test the specifier vv. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat017() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{vv}", "");
    }

    /**
     * <testcase> Test the specifier jj. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat018() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{jj}", "");
    }

    /**
     * <testcase> Test the specifier l. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat019() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{l}", "N");
    }

    /**
     * <testcase> Test the specifier ll. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat022() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{ll}", "Bergerac");
    }

    /**
     * <testcase> Test the specifier vv. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat023() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{vv}", "de");
    }

    /**
     * <testcase> Test the specifier ff. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat024() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{ff}", "Cyrano");
    }

    /**
     * <testcase> Test the specifier f with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0310() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{f.~}", "C.~L. X.~J. ");
    }

    /**
     * <testcase> Test the specifier ll with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat032() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ll-}", "Vall{\\'e}e~Poussin-");
    }

    /**
     * <testcase> Test the specifier vv with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat033() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{vv-}", "de~la-");
    }

    /**
     * <testcase> Test the specifier ff with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat034() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ff-}", "Charles Louis Xavier~Joseph-");
    }

    /**
     * <testcase> Test the specifier ll with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat035() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     * <testcase> Test the specifier vv with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat036() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{vv~}", "de~la ");
    }

    /**
     * <testcase> Test the specifier ff with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat037() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     * <testcase> Test the specifier l with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat038() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{l.}", "V.~P.");
    }

    /**
     * <testcase> Test the specifier v with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat039() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{v.}", "d.~l.");
    }

    /**
     * <testcase> Test the specifier ll alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat042() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{ll}", "Neugebauer");
    }

    /**
     * <testcase> Test the specifier ff alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat043() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{ff}", "Gerd");
    }

    /**
     * <testcase> Test the specifier vv alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat044() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{vv}", "");
    }

    /**
     * <testcase> Test the specifier jj alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat045() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{jj}", "");
    }

    /**
     * <testcase> Test the specifier l alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat046() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{l}", "N");
    }

    /**
     * <testcase> Test the specifier f alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat047() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{f}", "G");
    }

    /**
     * <testcase> Test the specifier v alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat048() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{v}", "");
    }

    /**
     * <testcase> Test the specifier j alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat049() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{j}", "");
    }

    /**
     * <testcase> Test the specifier ll alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat052() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{ll}", "Bergerac");
    }

    /**
     * <testcase> Test the specifier vv alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat053() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{vv}", "de");
    }

    /**
     * <testcase> Test the specifier ff alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat054() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{ff}", "Cyrano");
    }

    /**
     * <testcase> Test the specifier f with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0610() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{f.~}", "C.~L. X.~J. ");
    }

    /**
     * <testcase> Test the specifier ll with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat062() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ll }", "Vall{\\'e}e~Poussin ");
    }

    /**
     * <testcase> Test the specifier vv with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat063() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{vv }", "de~la ");
    }

    /**
     * <testcase> Test the specifier ff with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat064() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ff }", "Charles Louis Xavier~Joseph ");
    }

    /**
     * <testcase> Test ll format with postfix and several last part elements.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat065() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     * <testcase> Test vv format with postfix and several von part elements.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat066() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{vv~}", "de~la ");
    }

    /**
     * <testcase> Test ff format with postfix and several first part elements.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat067() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     * <testcase> Test v format with postfix and several last part elements.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat068() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{l.}", "V.~P.");
    }

    /**
     * <testcase> Test v format with postfix and several von part elements.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat069() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{v.}", "d.~l.");
    }

    /**
     * <testcase> Test ll format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat072() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{ll}", "Neugebauer");
    }

    /**
     * <testcase> Test ff format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat073() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{ff}", "Gerd");
    }

    /**
     * <testcase> Test vv format alone when there is no von part. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat074() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{vv}", "");
    }

    /**
     * <testcase> Test jj format alone when there is no junior part. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat075() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{jj}", "");
    }

    /**
     * <testcase> Test l format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat076() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{l}", "N");
    }

    /**
     * <testcase> Test f format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat077() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{f}", "G");
    }

    /**
     * <testcase> Test v format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat078() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{v}", "");
    }

    /**
     * <testcase> Test j format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat079() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{j}", "");
    }

    /**
     * <testcase> Test ll format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat082() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{ll}", "Bergerac");
    }

    /**
     * <testcase> Test vv format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat083() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{vv}", "de");
    }

    /**
     * <testcase> Test ff format alone. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat084() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{ff}", "Cyrano");
    }

    /**
     * <testcase> Test f format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0910() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{f.~}", "C.~L. X.~J. ");
    }

    /**
     * <testcase> Test ll format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0911() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll{:} }", "Vall{\\'e}e:Poussin ");
    }

    /**
     * <testcase> Test vv format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0912() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv{:} }", "de:la ");
    }

    /**
     * <testcase> Test ff format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0913() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff{:} }", "Charles:Louis:Xavier:Joseph ");
    }

    /**
     * <testcase> Test ll format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0914() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll{:}~}", "Vall{\\'e}e:Poussin ");
    }

    /**
     * <testcase> Test vv format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0915() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv{:}~}", "de:la ");
    }

    /**
     * <testcase> Test ff format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0916() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff{:}~}", "Charles:Louis:Xavier:Joseph ");
    }

    /**
     * <testcase> Test l format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0917() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{l{.:}.}", "V.:P.");
    }

    /**
     * <testcase> Test v format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0918() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{v{.:}.}", "d.:l.");
    }

    /**
     * <testcase> Test f format with postfix and infix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat0919() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{f{.:}.~}", "C.:L.:X.:J. ");
    }

    /**
     * <testcase> Test ll format with postfix space. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat092() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll }", "Vall{\\'e}e~Poussin ");
    }

    /**
     * <testcase> Test vv format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat093() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv }", "de~la ");
    }

    /**
     * <testcase> Test ff format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat094() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff }", "Charles Louis Xavier~Joseph ");
    }

    /**
     * <testcase> Test ll format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat095() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     * <testcase> Test vv format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat096() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv~}", "de~la ");
    }

    /**
     * <testcase> Test ff format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat097() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     * <testcase> Test l format with postfix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat098() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{l.}", "V.~P.");
    }

    /**
     * <testcase> Test v format. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat099() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{v.}", "d.~l.");
    }

    /**
     * <testcase> Test double tilde. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat1010() throws Exception {

        testFormat("{A.}~Nother", 1, "{ff~~}{vv~}{ll}{, jj}", "{A.}~Nother");
    }

    /**
     * <testcase> Test an empty group in a prefix. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat1011() throws Exception {

        testFormat("S.O. Meone", 1, "{{}f.~}{vv~}{ll}{, jj}", "{}S. Meone");
    }

    /**
     * <testcase> Test joined initials. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat102() throws Exception {

        testFormat("S.O. Meone", 1, "{ff~}{vv~}{ll}{, jj}", "S.O. Meone");
    }

    /**
     * <testcase> Test a single initial. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat103() throws Exception {

        testFormat("A. Nother", 1, "{ff~}{vv~}{ll}{, jj}", "A.~Nother");
    }

    /**
     * <testcase> Test space separated initials. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat104() throws Exception {

        testFormat("S. O. Meone and A. Nother and others", 1,
            "{ff~}{vv~}{ll}{, jj}", "S.~O. Meone");
    }

    /**
     * <testcase> Test several names. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat105() throws Exception {

        testFormat("Xavier Oscar Handley and S.O. Meone and A. Nother", 1,
            "{ff~}{vv~}{ll}{, jj}", "Xavier~Oscar Handley");
    }

    /**
     * <testcase> Test accented initials. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat106() throws Exception {

        testFormat("{\\'{E}}. Masterly", 1, "{f.~}{vv~}{ll}{, jj}",
            "{\\'{E}}.~Masterly");
    }

    /**
     * <testcase> Test double initials. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat107() throws Exception {

        testFormat("J.-P. Satre", 1, "{f.~}{vv~}{ll}{, jj}", "J.-P. Satre");
    }

    /**
     * <testcase> test double first names. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat108() throws Exception {

        testFormat("Jean-Paul Satre", 1, "{f.~}{vv~}{ll}{, jj}", "J.-P. Satre");

    }

    /**
     * <testcase> Test a first name in a group. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testFormat109() throws Exception {

        testFormat("{A.} Nother", 1, "{ff~}{vv~}{ll}{, jj}", "{A.} Nother");
    }

    /**
     * Test a no-name.
     * 
     * @param names the names
     * @param pos the position
     * 
     * @throws Exception in case of an error
     */
    private void testNoName(String names, int pos) throws Exception {

        try {
            p.push(new TString(names));
            p.push(new TInteger(pos));
            p.push(new TString("{l}"));
            new FormatName099("format.name$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> The first name in an empty list leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoName0() throws Exception {

        testNoName("", 1);
    }

    /**
     * <testcase> The second name in a one-name list leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoName1() throws Exception {

        testNoName("Gerd Neugebauer", 2);
    }

    /**
     * <testcase> The third name in a two-name list leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoName2() throws Exception {

        testNoName("Gerd Neugebauer and others", 3);
    }

    /**
     * <testcase> The third name in a two-name list leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoName3() throws Exception {

        testNoName("Gerd Neugebauer and A.U. Thor", 3);
    }

    /**
     * <testcase> The index -1 in an empty list leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoNameMinus1() throws Exception {

        testNoName("", -1);
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack1() throws Exception {

        try {
            p.push(new TString("0"));
            new FormatName099("format.name$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack2() throws Exception {

        try {
            p.push(new TInteger(0));
            p.push(new TString("0"));
            new FormatName099("format.name$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

}
