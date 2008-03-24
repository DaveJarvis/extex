/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.code;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.code.impl.FormatName099;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
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

        p = new Processor099Impl(new DBImpl(), new NullWriter(null), null);
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
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
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
     * TODO gene: missing JavaDoc
     * 
     * @param names
     * @param pos
     * @param fmt
     * @param res
     * @throws Exception
     */
    private void testFormat(String names, int pos, String fmt, String res)
            throws Exception {

        p.push(new TString(names));
        p.push(new TInteger(pos));
        p.push(new TString(fmt));
        new FormatName099("format.name$").execute(p, null, null);

        String s = p.popString(null).getValue();

        if (!s.equals(res)) {
            System.err.println(fmt + ": " + res + " // " + s);
        }

        assertEquals(res, s);
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0110() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{f}", "G");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0111() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{v}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0112() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{j}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat012() throws Exception {

        testFormat("Gerd Neugebauer and others", 2, "{ll}", "others");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat013() throws Exception {

        testFormat("Gerd Neugebauer and others", 1, "abcd", "abcd");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat014() throws Exception {

        testFormat("Gerd Neugebauer and others", 1, "ab{ll}cd",
            "abNeugebauercd");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat015() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{ll}", "Neugebauer");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat016() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{ff}", "Gerd");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat017() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{vv}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat018() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{jj}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat019() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{l}", "N");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat022() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{ll}", "Bergerac");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat023() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{vv}", "de");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat024() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{ff}", "Cyrano");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0310() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{f.~}", "C.~L. X.~J. ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat032() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ll-}", "Vall{\\'e}e~Poussin-");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat033() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{vv-}", "de~la-");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat034() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ff-}", "Charles Louis Xavier~Joseph-");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat035() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat036() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{vv~}", "de~la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat037() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat038() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{l.}", "V.~P.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat039() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{v.}", "d.~l.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat042() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{ll}", "Neugebauer");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat043() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{ff}", "Gerd");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat044() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{vv}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat045() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{jj}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat046() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{l}", "N");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat047() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{f}", "G");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat048() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{v}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat049() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{j}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat052() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{ll}", "Bergerac");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat053() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{vv}", "de");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat054() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{ff}", "Cyrano");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0610() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{f.~}", "C.~L. X.~J. ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat062() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ll }", "Vall{\\'e}e~Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat063() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{vv }", "de~la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat064() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ff }", "Charles Louis Xavier~Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat065() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat066() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{vv~}", "de~la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat067() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat068() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{l.}", "V.~P.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat069() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{v.}", "d.~l.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat072() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{ll}", "Neugebauer");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat073() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{ff}", "Gerd");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat074() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{vv}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat075() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{jj}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat076() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{l}", "N");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat077() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{f}", "G");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat078() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{v}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat079() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{j}", "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat082() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{ll}", "Bergerac");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat083() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{vv}", "de");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat084() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{ff}", "Cyrano");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0910() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{f.~}", "C.~L. X.~J. ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0911() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll{:} }", "Vall{\\'e}e:Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0912() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv{:} }", "de:la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0913() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff{:} }", "Charles:Louis:Xavier:Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0914() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll{:}~}", "Vall{\\'e}e:Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0915() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv{:}~}", "de:la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0916() throws Exception {

        testFormat(

        "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ", 1,
            "{ff{:}~}", "Charles:Louis:Xavier:Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0917() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{l{.:}.}", "V.:P.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0918() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{v{.:}.}", "d.:l.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat0919() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{f{.:}.~}", "C.:L.:X.:J. ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat092() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll }", "Vall{\\'e}e~Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat093() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv }", "de~la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat094() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff }", "Charles Louis Xavier~Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat095() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat096() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv~}", "de~la ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat097() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat098() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{l.}", "V.~P.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat099() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{v.}", "d.~l.");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat102() throws Exception {

        testFormat("S.O. Meone", 1, "{ff~}{vv~}{ll}{, jj}", "S.O. Meone");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat103() throws Exception {

        testFormat("A. Nother", 1, "{ff~}{vv~}{ll}{, jj}", "A.~Nother");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat104() throws Exception {

        testFormat("S. O. Meone and A. Nother and others", 1,
            "{ff~}{vv~}{ll}{, jj}", "S.~O. Meone");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat105() throws Exception {

        testFormat("Xavier Oscar Handley and S.O. Meone and A. Nother", 1,
            "{ff~}{vv~}{ll}{, jj}", "Xavier~Oscar Handley");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat106() throws Exception {

        testFormat("{\\'{E}}. Masterly", 1, "{f.~}{vv~}{ll}{, jj}",
            "{\\'{E}}.~Masterly");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat107() throws Exception {

        testFormat("J.-P. Satre", 1, "{f.~}{vv~}{ll}{, jj}", "J.-P. Satre");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testFormat108() throws Exception {

        testFormat("Jean-Paul Satre", 1, "{f.~}{vv~}{ll}{, jj}", "J.-P. Satre");

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param names
     * @param pos
     * @throws Exception
     */
    private void testNoName(String names, int pos) throws Exception {

        try {
            p.push(new TString(names));
            p.push(new TInteger(pos));
            p.push(new TString("{l}"));
            new FormatName099("format.name$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibIllegalValueException e) {
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testNoName0() throws Exception {

        testNoName("", 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testNoName1() throws Exception {

        testNoName("Gerd Neugebauer", 2);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testNoName2() throws Exception {

        testNoName("Gerd Neugebauer and others", 3);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
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
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
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
