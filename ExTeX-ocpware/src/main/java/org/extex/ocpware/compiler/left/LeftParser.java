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

package org.extex.ocpware.compiler.left;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.arith.Constant;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.parser.ParserStream;

/**
 * This utility class contains parser methods for left items.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public final class LeftParser {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return the list of left items recognized
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static ChoiceLeft choiceLeft(ParserStream s)
            throws IOException,
                SyntaxException {

        ChoiceLeft result = new ChoiceLeft();
        List<Left> ins = new ArrayList<Left>();
        result.add(new LeftList(ins));
        int c;
        do {
            ins.add(oneLeft(s));
            c = s.skipSpace();
            if (c == '|') {
                ins = new ArrayList<Left>();
                result.add(new LeftList(ins));
            }
        } while (c >= 0 && c != ')');

        return result;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s the input stream
     * 
     * @return the complete left item
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static Left completeLeft(ParserStream s)
            throws IOException,
                SyntaxException {

        int c = s.skipSpace();
        if (c == '"') {
            StringBuffer sb = new StringBuffer();
            for (;;) {
                c = s.read();
                if (c < 0) {
                    s.expect('"'); // force an error
                } else if (c == '"') {
                    c = s.read();
                    if (c < 0) {
                        s.expect('"'); // force an error
                    } else if (c != '"') {
                        s.unread(c);
                        break;
                    }
                }
                sb.append((char) c);
            }
            return new StringLeft(sb.toString());
        }
        s.unread(c);
        Left left = oneLeft(s);
        c = s.skipSpace();
        if (c != '<') {
            s.unread(c);
            return left;
        }

        int from = s.parseNumber(c);
        s.expect(',');
        c = s.skipSpace();
        if (c == '>') {
            return new PlusLeft(left, from);
        }
        s.unread(c);
        int to = s.parseNumber(c);
        s.expect('>');

        return new CompleteLeft(left, from, to);
    }

    /**
     * Parse a left item.
     * 
     * @param s the input stream
     * 
     * @return the left item acquired
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static Left oneLeft(ParserStream s)
            throws IOException,
                SyntaxException {

        int c = s.skipSpace();
        switch (c) {
            case '(':
                return choiceLeft(s);
            case '{':
                String t = s.parseId();
                s.expect('}');
                return new AliasLeft(t);
            case '^':
                s.expect('(');
                return new NotChoiceLeft(choiceLeft(s));
            case '.':
                return new WildCard();
            case '`':
            case '@':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                int n = s.parseNumber(c);
                c = s.skipSpace();
                if (c != '-') {
                    s.unread(c);
                    return new Constant(n);
                }
                return new DoubleLeft(n, s.parseNumber(s.skipSpace()));
            default:
                if (c >= 0) {
                    throw s.error(c);
                }
        }

        return null;
    }

    /**
     * Parse a list of left items.
     * 
     * @param s the input stream
     * 
     * @return the list of left items recognized
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static List<Left> left(ParserStream s)
            throws IOException,
                SyntaxException {

        List<Left> result = new ArrayList<Left>();
        int c;
        do {
            result.add(oneLeft(s));
            c = s.skipSpace();
            s.unread(c);
        } while (c >= 0 && c != '=' && c != 'e');

        return result;
    }

    /**
     * Creates a new object.
     */
    private LeftParser() {

        // unused
    }

}
