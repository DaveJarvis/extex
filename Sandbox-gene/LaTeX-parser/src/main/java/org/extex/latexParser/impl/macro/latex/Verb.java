/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl.macro.latex;

import org.extex.core.UnicodeChar;
import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.SyntaxError;
import org.extex.latexParser.impl.node.VerbNode;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Verb implements Macro {

    /**
     * The field <tt>tokenizer</tt> contains the tokenizer to use for
     * categorizing characters.
     */
    class VerbTokenizer implements Tokenizer {

        protected UnicodeChar c;

        /**
         * Creates a new object.
         * 
         */
        public VerbTokenizer(UnicodeChar c) {

            super();
            this.c = c;
        }

        /**
         * Getter for the category code of a character.
         * 
         * @param c the Unicode character to analyze
         * 
         * @return the category code of a character
         * 
         * @see org.extex.scanner.api.Tokenizer#getCatcode(
         *      org.extex.core.UnicodeChar)
         */
        public Catcode getCatcode(UnicodeChar c) {

            if (c.equals(this.c)) {
                return Catcode.ESCAPE;
            }
            return Catcode.OTHER;
        }

        /**
         * Getter for the name space.
         * 
         * @return the name space
         * 
         * @see org.extex.scanner.api.Tokenizer#getNamespace()
         */
        public String getNamespace() {

            return "";
        }

    };

    /**
     * Creates a new object.
     * 
     * @param s
     */
    public Verb(String s) {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Macro#parse(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser) throws ScannerException {

        Token start = parser.getToken();
        if (start == null) {
            throw new SyntaxError("unexpected EOF");
        }
        final VerbNode verb = new VerbNode(token, start);

        Tokenizer tokenizer =
                parser.setTokenizer(new VerbTokenizer(start.getChar()));

        for (Token t = parser.getToken(); t != null; t = parser.getToken()) {
            if (t instanceof ControlSequenceToken) {
                parser.setTokenizer(tokenizer);
                return verb;
            }
            verb.add(t);
        }
        throw new SyntaxError("unexpected EOF in \\verb");
    }
}
