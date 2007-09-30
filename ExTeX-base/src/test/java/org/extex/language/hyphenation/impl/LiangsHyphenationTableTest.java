/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.language.hyphenation.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.MockContext;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.DuplicateHyphenationException;
import org.extex.language.hyphenation.liang.LiangsHyphenationTable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for LiangsHyphenationTable.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4527 $
 */
public class LiangsHyphenationTableTest {

    /**
     * This mock implementation is for test purposes only.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private static class MyMockContext extends MockContext {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 1L;

    }

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LiangsHyphenationTableTest.class);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the string specification
     * @param context the context
     * 
     * @return the tokens
     * 
     * @throws CatcodeException in case of problems in token creation
     */
    private static Tokens makeTokens(String s, Context context)
            throws CatcodeException {

        TokenFactory factory = context.getTokenFactory();
        Tokens tokens = new Tokens();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            tokens.add(factory.createToken((Character.isLetter(c)
                    ? Catcode.LETTER
                    : Catcode.OTHER), c, Namespace.DEFAULT_NAMESPACE));
        }
        return tokens;
    }

    /**
     * This test case checks that the insertion of two different pattern does
     * not lead to an exception.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        Context context = new MyMockContext();
        Language table = new LiangsHyphenationTable();

        table.addPattern(makeTokens("0a2b1c0", context));
        table.addPattern(makeTokens("0x2b1c0", context));
        assertTrue(true);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        Context context = new MyMockContext();
        LiangsHyphenationTable table = new LiangsHyphenationTable();

        table.addPattern(makeTokens("0a1b0", context));
        table.addPattern(makeTokens("0a2b1c0", context));
        assertTrue(true);
    }

    /**
     * This test case tests that the addPattern() method with identical
     * arguments leads to an exception.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        Context context = new MyMockContext();
        Language table = new LiangsHyphenationTable();

        table.addPattern(makeTokens("0a2b1c0", context));
        try {
            table.addPattern(makeTokens("0a2b1c0", context));
            assertFalse(true);
        } catch (DuplicateHyphenationException e) {
            assertTrue(true);
        }
    }

    /**
     * This test case tests that the addPattern() method with identical
     * arguments on the character positions leads to an exception.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr2() throws Exception {

        Context context = new MyMockContext();
        Language table = new LiangsHyphenationTable();

        table.addPattern(makeTokens("0a2b1c0", context));
        try {
            table.addPattern(makeTokens("0a3b2c0", context));
            assertFalse(true);
        } catch (DuplicateHyphenationException e) {
            assertTrue(true);
        }
    }
}
