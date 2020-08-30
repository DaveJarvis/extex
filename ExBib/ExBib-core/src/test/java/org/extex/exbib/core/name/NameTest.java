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

package org.extex.exbib.core.name;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.junit.Test;

/**
 * This is a test suite for the class {@link Name}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NameTest {

    /**
     * Run a test with one last and several first names.
     * 
     * @param in the input string
     * @param last the single last name
     * @param first the list of first names
     * 
     * @throws ExBibNoNameException in case of an error
     * @throws ExBibImpossibleException in case of an error
     * @throws ExBibSyntaxException in case of an error
     */
    void checkLF(String in, String last, String... first)
            throws ExBibNoNameException,
                ExBibImpossibleException,
                ExBibSyntaxException {

        Name n = new Name(in, null);
        assertEquals(1, n.getLast().size());
        assertEquals(last, n.getLast().get(0));
        assertEquals(first.length, n.getFirst().size());
        int i = 0;
        List<String> fl = n.getFirst();
        for (String f : first) {
            assertEquals(f, fl.get(i++));
        }
        assertEquals(0, n.getJr().size());
        assertEquals(0, n.getVon().size());
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameAristoteles1() throws Exception {

        checkLF("Aristoteles", "Aristoteles");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameAristoteles2() throws Exception {

        checkLF("{Aristoteles}", "{Aristoteles}");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameBeethoven1() throws Exception {

        Name n = new Name("Ludwig van Beethoven", null);
        assertEquals(1, n.getLast().size());
        assertEquals("Beethoven", n.getLast().get(0));
        assertEquals(1, n.getFirst().size());
        assertEquals("Ludwig", n.getFirst().get(0));
        assertEquals(1, n.getVon().size());
        assertEquals("van", n.getVon().get(0));
        assertEquals(0, n.getJr().size());
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameDavis1() throws Exception {

        Name n = new Name("Davis, Jr., Sammy", null);
        assertEquals(1, n.getLast().size());
        assertEquals("Davis", n.getLast().get(0));
        assertEquals(1, n.getFirst().size());
        assertEquals("Sammy", n.getFirst().get(0));
        assertEquals(0, n.getVon().size());
        assertEquals(1, n.getJr().size());
        assertEquals("Jr.", n.getJr().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameDavis2() throws Exception {

        Name n = new Name("Sammy Davis, jr.", null);
        assertEquals(1, n.getLast().size());
        assertEquals("Davis", n.getLast().get(0));
        assertEquals(1, n.getFirst().size());
        assertEquals("Sammy", n.getFirst().get(0));
        assertEquals(0, n.getVon().size());
        assertEquals(1, n.getJr().size());
        assertEquals("jr.", n.getJr().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameDonQuixote1() throws Exception {

        String[] first = {"Don", "Quixote"};
        String[] von = {"de", "la"};
        Name n = new Name("Don Quixote de la Mancha", null);
        assertEquals(1, n.getLast().size());
        assertEquals("Mancha", n.getLast().get(0));
        assertEquals(first.length, n.getFirst().size());
        int i = 0;
        List<String> fl = n.getFirst();
        for (String f : first) {
            assertEquals(f, fl.get(i++));
        }
        assertEquals(0, n.getJr().size());
        assertEquals(2, n.getVon().size());
        List<String> vl = n.getVon();
        i = 0;
        for (String v : von) {
            assertEquals(v, vl.get(i++));
        }
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testNameError0() throws Exception {

        new Name(null, null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError1() throws Exception {

        new Name("", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError10() throws Exception {

        new Name("{}", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError2() throws Exception {

        new Name("         ", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError3() throws Exception {

        new Name(",", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError4() throws Exception {

        new Name(" ,", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError5() throws Exception {

        new Name(" ,,", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError6() throws Exception {

        new Name("A, B, C, D", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError7() throws Exception {

        new Name(", C", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError8() throws Exception {

        new Name("C,", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testNameError9() throws Exception {

        new Name("A, C, ", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameHentry1() throws Exception {

        checkLF("  {Henry VII} ", "{Henry VII}");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameKnuth1() throws Exception {

        checkLF("Donald Ervin Knuth", "Knuth", "Donald", "Ervin");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameKnuth2() throws Exception {

        checkLF("Donald E. Knuth", "Knuth", "Donald", "E.");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameKnuth3() throws Exception {

        checkLF("D. E. Knuth", "Knuth", "D.", "E.");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameKnuth4() throws Exception {

        checkLF("D.E. Knuth", "Knuth", "D.E.");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameLamport1() throws Exception {

        checkLF("Leslie Lamport", "Lamport", "Leslie");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameLamport2() throws Exception {

        checkLF(" Leslie   Lamport  ", "Lamport", "Leslie");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameLamport3() throws Exception {

        checkLF("Lamport, Leslie", "Lamport", "Leslie");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameLamport4() throws Exception {

        checkLF("  Lamport  ,   Leslie  ", "Lamport", "Leslie");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNameMozart1() throws Exception {

        checkLF("Johannes Chrysostomus Wolfgangus Theophilus Mozart", "Mozart",
            "Johannes", "Chrysostomus", "Wolfgangus", "Theophilus");
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse1() throws Exception {

        List<Name> list = Name.parse("{Barnes and Noble}", null);
        assertEquals(1, list.size());
        assertEquals("{Barnes and Noble}", list.get(0).getLast().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse2() throws Exception {

        List<Name> list = Name.parse("Barnes and Noble", null);
        assertEquals(2, list.size());
        assertEquals("Barnes", list.get(0).getLast().get(0));
        assertEquals("Noble", list.get(1).getLast().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse3() throws Exception {

        List<Name> list = Name.parse("  Barnes   and   Noble  ", null);
        assertEquals(2, list.size());
        assertEquals("Barnes", list.get(0).getLast().get(0));
        assertEquals("Noble", list.get(1).getLast().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse4() throws Exception {

        List<Name> list = Name.parse("Barnes   AND   Noble", null);
        assertEquals(2, list.size());
        assertEquals("Barnes", list.get(0).getLast().get(0));
        assertEquals("Noble", list.get(1).getLast().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse5() throws Exception {

        List<Name> list = Name.parse("Barnes   AnD   Noble", null);
        assertEquals(2, list.size());
        assertEquals("Barnes", list.get(0).getLast().get(0));
        assertEquals("Noble", list.get(1).getLast().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse6() throws Exception {

        List<Name> list = Name.parse("Barnes and others", null);
        assertEquals(2, list.size());
        assertEquals("Barnes", list.get(0).getLast().get(0));
        assertEquals("others", list.get(1).getLast().get(0));
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testParseErr1() throws Exception {

        Name.parse("von", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testParseErr2() throws Exception {

        Name.parse("von von", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNameException.class)
    public final void testParseErr3() throws Exception {

        Name.parse("von, von", null);
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToString1() throws Exception {

        assertEquals(
            "Johannes Chrysostomus Wolfgangus Theophilus Mozart",
            new Name("Johannes Chrysostomus Wolfgangus Theophilus Mozart", null)
                .toString());
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToString2() throws Exception {

        assertEquals("Ludwig van Beethoven", new Name("Ludwig van Beethoven",
            null).toString());
    }

    /**
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToString3() throws Exception {

        assertEquals("Sammy Davis jr.",
            new Name("Sammy Davis jr.", null).toString());
    }

}
