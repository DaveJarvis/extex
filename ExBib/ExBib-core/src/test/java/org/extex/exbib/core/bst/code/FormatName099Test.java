/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.FormatName;
import org.extex.exbib.core.bst.code.impl.FormatName099;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@code format.name$}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class FormatName099Test {

    /**
     * The field {@code p} contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Create a test object.
     * 
     * @return The test object
     */
    private FormatName makeFormatter() {

        return new FormatName099("format.name$");
    }

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        p = new BstInterpreter099c(new DBImpl(), new NullWriter(), null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     *  The empty stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmptyStack() throws Exception {

        try {
            makeFormatter().execute(p, null, null);
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

        p.push(new TString(names, null));
        p.push(new TInteger(pos, null));
        p.push(new TString(fmt, null));
        makeFormatter().execute(p, null, null);

        String s = p.popString(null).getValue();

        assertEquals(expected, s);
        assertNull(p.popUnchecked());
    }

    /**
     *  Test specifier f alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0110() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{f}", "G");
    }

    /**
     *  Test specifier v alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0111() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{v}", "");
    }

    /**
     *  Test specifier j alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0112() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{j}", "");
    }

    /**
     *  Test specifier ll alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat012() throws Exception {

        testFormat("Gerd Neugebauer and others", 2, "{ll}", "others");
    }

    /**
     *  Test a constant format.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat013() throws Exception {

        testFormat("Gerd Neugebauer and others", 1, "abcd", "abcd");
    }

    /**
     *  Test mixed constant and variable pieces.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat014() throws Exception {

        testFormat("Gerd Neugebauer and others", 1, "ab{ll}cd",
            "abNeugebauercd");
    }

    /**
     *  Test the specifier ll.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat015() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{ll}", "Neugebauer");
    }

    /**
     *  Test the specifier ff.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat016() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{ff}", "Gerd");
    }

    /**
     *  Test the specifier vv.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat017() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{vv}", "");
    }

    /**
     *  Test the specifier jj.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat018() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{jj}", "");
    }

    /**
     *  Test the specifier l.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat019() throws Exception {

        testFormat("Gerd Neugebauer", 1, "{l}", "N");
    }

    /**
     *  Test the specifier ll.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat022() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{ll}", "Bergerac");
    }

    /**
     *  Test the specifier vv.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat023() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{vv}", "de");
    }

    /**
     *  Test the specifier ff.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat024() throws Exception {

        testFormat("Cyrano de Bergerac", 1, "{ff}", "Cyrano");
    }

    /**
     *  Test the specifier f with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0310() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{f.~}", "C.~L. X.~J. ");
    }

    /**
     *  Test the specifier ll with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat032() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ll-}", "Vall{\\'e}e~Poussin-");
    }

    /**
     *  Test the specifier vv with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat033() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{vv-}", "de~la-");
    }

    /**
     *  Test the specifier ff with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat034() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ff-}", "Charles Louis Xavier~Joseph-");
    }

    /**
     *  Test the specifier ll with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat035() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     *  Test the specifier vv with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat036() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{vv~}", "de~la ");
    }

    /**
     *  Test the specifier ff with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat037() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     *  Test the specifier l with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat038() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{l.}", "V.~P.");
    }

    /**
     *  Test the specifier v with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat039() throws Exception {

        testFormat("Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin", 1,
            "{v.}", "d.~l.");
    }

    /**
     *  Test the specifier ll alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat042() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{ll}", "Neugebauer");
    }

    /**
     *  Test the specifier ff alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat043() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{ff}", "Gerd");
    }

    /**
     *  Test the specifier vv alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat044() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{vv}", "");
    }

    /**
     *  Test the specifier jj alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat045() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{jj}", "");
    }

    /**
     *  Test the specifier l alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat046() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{l}", "N");
    }

    /**
     *  Test the specifier f alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat047() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{f}", "G");
    }

    /**
     *  Test the specifier v alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat048() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{v}", "");
    }

    /**
     *  Test the specifier j alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat049() throws Exception {

        testFormat("Neugebauer, Gerd", 1, "{j}", "");
    }

    /**
     *  Test the specifier ll alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat052() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{ll}", "Bergerac");
    }

    /**
     *  Test the specifier vv alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat053() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{vv}", "de");
    }

    /**
     *  Test the specifier ff alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat054() throws Exception {

        testFormat("de Bergerac, Cyrano", 1, "{ff}", "Cyrano");
    }

    /**
     *  Test the specifier f with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0610() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{f.~}", "C.~L. X.~J. ");
    }

    /**
     *  Test the specifier ll with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat062() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ll }", "Vall{\\'e}e~Poussin ");
    }

    /**
     *  Test the specifier vv with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat063() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{vv }", "de~la ");
    }

    /**
     *  Test the specifier ff with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat064() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ff }", "Charles Louis Xavier~Joseph ");
    }

    /**
     *  Test ll format with postfix and several last part elements.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat065() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     *  Test vv format with postfix and several von part elements.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat066() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{vv~}", "de~la ");
    }

    /**
     *  Test ff format with postfix and several first part elements.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat067() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     *  Test v format with postfix and several last part elements.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat068() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{l.}", "V.~P.");
    }

    /**
     *  Test v format with postfix and several von part elements.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat069() throws Exception {

        testFormat("de la Vall{\\'e}e Poussin, Charles Louis Xavier Joseph", 1,
            "{v.}", "d.~l.");
    }

    /**
     *  Test ll format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat072() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{ll}", "Neugebauer");
    }

    /**
     *  Test ff format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat073() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{ff}", "Gerd");
    }

    /**
     *  Test vv format alone when there is no von part.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat074() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{vv}", "");
    }

    /**
     *  Test jj format alone when there is no junior part.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat075() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{jj}", "");
    }

    /**
     *  Test l format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat076() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{l}", "N");
    }

    /**
     *  Test f format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat077() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{f}", "G");
    }

    /**
     *  Test v format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat078() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{v}", "");
    }

    /**
     *  Test j format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat079() throws Exception {

        testFormat(" Neugebauer ,  Gerd ", 1, "{j}", "");
    }

    /**
     *  Test ll format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat082() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{ll}", "Bergerac");
    }

    /**
     *  Test vv format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat083() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{vv}", "de");
    }

    /**
     *  Test ff format alone.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat084() throws Exception {

        testFormat("  de  Bergerac ,  Cyrano ", 1, "{ff}", "Cyrano");
    }

    /**
     *  Test f format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0910() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{f.~}", "C.~L. X.~J. ");
    }

    /**
     *  Test ll format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0911() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll{:} }", "Vall{\\'e}e:Poussin ");
    }

    /**
     *  Test vv format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0912() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv{:} }", "de:la ");
    }

    /**
     *  Test ff format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0913() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff{:} }", "Charles:Louis:Xavier:Joseph ");
    }

    /**
     *  Test ll format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0914() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll{:}~}", "Vall{\\'e}e:Poussin ");
    }

    /**
     *  Test vv format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0915() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv{:}~}", "de:la ");
    }

    /**
     *  Test ff format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0916() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff{:}~}", "Charles:Louis:Xavier:Joseph ");
    }

    /**
     *  Test l format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0917() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{l{.:}.}", "V.:P.");
    }

    /**
     *  Test v format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0918() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{v{.:}.}", "d.:l.");
    }

    /**
     *  Test f format with postfix and infix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat0919() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{f{.:}.~}", "C.:L.:X.:J. ");
    }

    /**
     *  Test ll format with postfix space.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat092() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll }", "Vall{\\'e}e~Poussin ");
    }

    /**
     *  Test vv format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat093() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv }", "de~la ");
    }

    /**
     *  Test ff format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat094() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff }", "Charles Louis Xavier~Joseph ");
    }

    /**
     *  Test ll format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat095() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ll~}", "Vall{\\'e}e~Poussin ");
    }

    /**
     *  Test vv format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat096() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{vv~}", "de~la ");
    }

    /**
     *  Test ff format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat097() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{ff~}", "Charles Louis Xavier~Joseph ");
    }

    /**
     *  Test l format with postfix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat098() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{l.}", "V.~P.");
    }

    /**
     *  Test v format.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat099() throws Exception {

        testFormat(
            "  de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph ",
            1, "{v.}", "d.~l.");
    }

    /**
     *  Test double tilde.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat1010() throws Exception {

        testFormat("{A.}~Nother", 1, "{ff~~}{vv~}{ll}{, jj}", "{A.}~Nother");
    }

    /**
     *  Test an empty group in a prefix.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat1011() throws Exception {

        testFormat("S.O. Meone", 1, "{{}f.~}{vv~}{ll}{, jj}", "{}S. Meone");
    }

    /**
     *  Test joined initials.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat102() throws Exception {

        testFormat("S.O. Meone", 1, "{ff~}{vv~}{ll}{, jj}", "S.O. Meone");
    }

    /**
     *  Test a single initial.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat103() throws Exception {

        testFormat("A. Nother", 1, "{ff~}{vv~}{ll}{, jj}", "A.~Nother");
    }

    /**
     *  Test space separated initials.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat104() throws Exception {

        testFormat("S. O. Meone and A. Nother and others", 1,
            "{ff~}{vv~}{ll}{, jj}", "S.~O. Meone");
    }

    /**
     *  Test several names.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat105() throws Exception {

        testFormat("Xavier Oscar Handley and S.O. Meone and A. Nother", 1,
            "{ff~}{vv~}{ll}{, jj}", "Xavier~Oscar Handley");
    }

    /**
     *  Test accented initials.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat106() throws Exception {

        testFormat("{\\'{E}}. Masterly", 1, "{f.~}{vv~}{ll}{, jj}",
            "{\\'{E}}.~Masterly");
    }

    /**
     *  Test double initials.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat107() throws Exception {

        testFormat("J.-P. Satre", 1, "{f.~}{vv~}{ll}{, jj}", "J.-P. Satre");
    }

    /**
     *  test double first names.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat108() throws Exception {

        testFormat("Jean-Paul Satre", 1, "{f.~}{vv~}{ll}{, jj}", "J.-P. Satre");

    }

    /**
     *  Test a first name in a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat109() throws Exception {

        testFormat("{A.} Nother", 1, "{ff~}{vv~}{ll}{, jj}", "{A.} Nother");
    }

    /**
     *  Test a first name in a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat110() throws Exception {

        testFormat("{Jean-Paul} Satre", 1, "{f~}{v~}{ll}{, jj}", "J~Satre");
    }

    /**
     *  Test a first name in a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat111() throws Exception {

        testFormat("{\\AA{}re} Xxx", 1, "{f~}{v~}{ll}{, jj}", "{\\AA{}re}~Xxx");
    }

    /**
     *  Test a first name in a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat112() throws Exception {

        testFormat("{Arthur Charles} Clarke", 1, "{f~}{v~}{ll}{, jj}",
            "A~Clarke");
    }

    /**
     *  Test accented initials.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat113() throws Exception {

        testFormat("{\\'{E}}douard Masterly", 1, "{f.~}{vv~}{ll}{, jj}",
            "{\\'{E}}.~Masterly");
    }

    /**
     * * Test a no-name.
     * 
     * @param names the names
     * @param pos the position
     * 
     * @throws Exception in case of an error
     */
    private void testNoName(String names, int pos) throws Exception {

        try {
            p.push(new TString(names, null));
            p.push(new TInteger(pos, null));
            p.push(new TString("{l}", null));
            makeFormatter().execute(p, null, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }

    /**
     *  The first name in an empty list leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoName0() throws Exception {

        testNoName("", 1);
    }

    /**
     *  The second name in a one-name list leads to an error.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoName1() throws Exception {

        testNoName("Gerd Neugebauer", 2);
    }

    /**
     *  The third name in a two-name list leads to an error.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoName2() throws Exception {

        testNoName("Gerd Neugebauer and others", 3);
    }

    /**
     *  The third name in a two-name list leads to an error.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoName3() throws Exception {

        testNoName("Gerd Neugebauer and A.U. Thor", 3);
    }

    /**
     *  The index -1 in an empty list leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoNameMinus1() throws Exception {

        testNoName("", -1);
    }

    /**
     *  A short stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShortStack1() throws Exception {

        try {
            p.push(new TString("0", null));
            makeFormatter().execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     *  A short stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShortStack2() throws Exception {

        try {
            p.push(TokenFactory.T_ZERO);
            p.push(new TString("0", null));
            makeFormatter().execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

}
