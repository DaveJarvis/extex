/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl.macro.latex;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.core.UnicodeChar;
import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.Macro;
import org.extex.latexParser.impl.Parser;
import org.extex.latexParser.impl.node.AbstractNode;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;

/**
 * This class represents a \verb instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7667 $
 */
public class Verbatim extends AbstractNode implements Macro {

    /**
     * The field <tt>tokenizer</tt> contains the tokenizer to use for
     * categorizing characters.
     */
    class VerbTokenizer implements Tokenizer {

        /**
         * The field <tt>c</tt> contains the end character.
         */
        protected UnicodeChar c;

        /**
         * Creates a new object.
         * 
         * @param c the end character
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
         * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
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
     * The field <tt>list</tt> contains the contents.
     */
    private List<Token> list = new ArrayList<Token>();

    /**
     * The field <tt>openToken</tt> contains the opening and closing token.
     */
    private Token openToken;

    /**
     * The field <tt>cmd</tt> contains the command.
     */
    private Token cmd;

    /**
     * Creates a new object.
     * 
     * @param s
     */
    public Verbatim(String s) {

        super(null, 0);
    }

    /**
     * Creates a new object.
     * 
     * @param cmd the command
     * @param t the token to add
     * @param source the source
     * @param lineNumber the line number
     */
    public Verbatim(Token cmd, Token t, String source, int lineNumber) {

        super(source, lineNumber);
        this.cmd = cmd;
        openToken = t;
    }

    /**
     * Add a token to the list.
     * 
     * @param n the node to add
     * 
     * @return <code>true</code>
     * 
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean add(Token n) {

        return list.add(n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Macro#parse(org.extex.scanner.type.token.Token,
     *      org.extex.latexParser.impl.Parser)
     */
    public Node parse(Token token, Parser parser) throws ScannerException {

        // Token start = parser.getToken();
        // if (start == null) {
        // throw new SyntaxError(parser, "unexpected end of file for {0}",
        // token.toText());
        // }
        // final Verbatim verb =
        // new Verbatim(token, start, parser.getSource(), parser.getLineno());
        //
        // Tokenizer tokenizer =
        // parser.setTokenizer(new VerbTokenizer(start.getChar()));
        //
        // for (Token t = parser.getToken(); t != null; t = parser.getToken()) {
        // if (t instanceof ControlSequenceToken) {
        // parser.setTokenizer(tokenizer);
        // return verb;
        // }
        // verb.add(t);
        // }
        // throw new SyntaxError(parser, "unexpected end of file in {0}", token
        // .toText());

        // TODO gene: enclosing_method unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print(cmd.toText());
        stream.print(openToken.toText());
        for (Token n : list) {
            stream.print(n.toText());
        }
        stream.print(openToken.toText());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(cmd.toText());
        sb.append(openToken.toText());
        for (Token n : list) {
            sb.append(n.toText());
        }
        sb.append(openToken.toText());
        return super.toString();
    }

}
