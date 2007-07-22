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

package org.extex.ocpware.compiler.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.arith.ArithExpr;
import org.extex.ocpware.compiler.arith.Constant;
import org.extex.ocpware.compiler.left.Left;
import org.extex.ocpware.compiler.left.LeftBeg;
import org.extex.ocpware.compiler.left.LeftEnd;
import org.extex.ocpware.compiler.left.LeftParser;
import org.extex.ocpware.compiler.parser.ParserStream;
import org.extex.ocpware.compiler.sexpression.Expr;
import org.extex.ocpware.compiler.sexpression.SParser;
import org.extex.ocpware.compiler.state.NextRightState;
import org.extex.ocpware.compiler.state.PopRightState;
import org.extex.ocpware.compiler.state.PushRightState;
import org.extex.ocpware.compiler.state.RightState;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Expression {

    /**
     * Parse a list of expressions.
     * 
     * @param s the input stream
     * @return the list of expressions found
     * 
     * @throws IOException in case of an I/O error
     */
    public static List<Expression> parseExpressions(ParserStream s)
            throws IOException {

        List<Expression> result = new ArrayList<Expression>();

        for (int c = s.skipSpace(); c >= 0; c = s.skipSpace()) {
            s.unread(c);
            Expression ex = new Expression();
            ex.setLeftState(parseOptionalLeftState(s));
            ex.setTotalLeft(parseTotalLeft(s));
            ex.setRight(parseRight(s));
            ex.setPushBack(parsePushBack(s));
            ex.setRightState(parseOptionalRightState(s));
            s.expect(';');
            result.add(ex);
        }

        return result;
    }

    /**
     * Parse an optional left state specification.
     * 
     * @param s the input stream
     * 
     * @return the left state or <code>null</code>
     * 
     * @throws IOException in case of an I/O error
     */
    private static String parseOptionalLeftState(ParserStream s)
            throws IOException {

        int c = s.skipSpace();
        if (c != '<') {
            s.unread(c);
            return null;
        }

        String left = s.parseId();
        s.expect('>');
        return left;
    }

    /**
     * Parse a right state specification.
     * 
     * @param s the input stream
     * 
     * @return the right state or <code>null</code>
     * 
     * @throws IOException in case of an I/O error
     */
    private static RightState parseOptionalRightState(ParserStream s)
            throws IOException {

        int c = s.skipSpace();
        if (c != '<') {
            s.unread(c);
            return null;
        }

        RightState state = null;
        String t = s.parseId();
        if ("push".equals(t)) {
            s.expect(':');
            t = s.parseId();
            state = new PushRightState(t);
        } else if ("pop".equals(t)) {
            s.expect(':');
            state = new PopRightState();
        } else {
            state = new NextRightState(t);
        }
        s.expect('>');
        return state;
    }

    /**
     * Parse the push back tokens.
     * 
     * @param s the input stream
     * 
     * @return the push back list
     * 
     * @throws IOException in case of an I/O error
     */
    private static List<Expr> parsePushBack(ParserStream s) throws IOException {

        int c = s.skipSpace();
        if (c != '<') {
            s.unread(c);
            return null;
        }
        c = s.read();
        if (c != '=') {
            s.unread(c);
            s.unread('<');
            return null;
        }

        return SParser.parse(s);
    }

    /**
     * Parse a right list.
     * 
     * @param s the input stream
     * 
     * @return the list of expressions
     * 
     * @throws IOException in case of an I/O error
     */
    private static List<Expr> parseRight(ParserStream s) throws IOException {

        s.expect('=');
        s.expect('>');
        return SParser.parse(s);
    }

    /**
     * Parse an optional total left list.
     * 
     * @param s the input stream
     * 
     * @return the list of left items
     * 
     * @throws IOException in case of an I/O error
     */
    private static List<Left> parseTotalLeft(ParserStream s) throws IOException {

        List<Left> result;
        int c = s.skipSpace();
        s.unread(c);
        if (c == 'b') {
            s.expect("beg");
            s.expect(':');
            result = LeftParser.parseLeftList(s);
            result.add(0, new LeftBeg());
        } else {
            result = LeftParser.parseLeftList(s);
        }

        c = s.read();
        s.unread(c);
        if (c == 'e') {
            s.expect("end");
            s.expect(':');
            result.add(new LeftEnd());
        }

        return result;
    }

    /**
     * The field <tt>leftState</tt> contains the left state.
     */
    private String leftState;

    /**
     * The field <tt>pushBack</tt> contains the pushback list.
     */
    private List<Expr> pushBack;

    /**
     * The field <tt>right</tt> contains the right list.
     */
    private List<Expr> right;

    /**
     * The field <tt>rightState</tt> contains the optional right state.
     */
    private RightState rightState;

    /**
     * The field <tt>totalLeft</tt> contains the total left.
     */
    private List<Left> totalLeft;

    /**
     * Creates a new object.
     * 
     */
    public Expression() {

        super();
    }

    /**
     * Getter for leftState.
     * 
     * @return the leftState
     */
    public String getLeftState() {

        return leftState;
    }

    /**
     * Getter for pushBack.
     * 
     * @return the pushBack
     */
    public List<Expr> getPushBack() {

        return pushBack;
    }

    /**
     * Getter for right.
     * 
     * @return the right
     */
    public List<Expr> getRight() {

        return right;
    }

    /**
     * Getter for rightState.
     * 
     * @return the rightState
     */
    public RightState getRightState() {

        return rightState;
    }

    /**
     * Getter for totalLeft.
     * 
     * @return the totalLeft
     */
    public List<Left> getTotalLeft() {

        return totalLeft;
    }

    /**
     * Setter for leftState.
     * 
     * @param leftState the leftState to set
     */
    public void setLeftState(String leftState) {

        this.leftState = leftState;
    }

    /**
     * Setter for pushBack.
     * 
     * @param pushBack the pushBack to set
     */
    public void setPushBack(List<Expr> pushBack) {

        this.pushBack = pushBack;
    }

    /**
     * Setter for right.
     * 
     * @param right the right to set
     */
    public void setRight(List<Expr> right) {

        this.right = right;
    }

    /**
     * Setter for rightState.
     * 
     * @param rightState the rightState to set
     */
    public void setRightState(RightState rightState) {

        this.rightState = rightState;
    }

    /**
     * Setter for totalLeft.
     * 
     * @param totalLeft the totalLeft to set
     */
    public void setTotalLeft(List<Left> totalLeft) {

        this.totalLeft = totalLeft;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer("  ");

        if (leftState != null) {
            sb.append("<");
            sb.append(leftState);
            sb.append(">");
        }
        for (Left l : totalLeft) {
            sb.append(l.toString());
        }
        sb.append(" => ");
        for (Expr r : right) {
            if (r instanceof Constant) {
                sb.append(r.toString());
            } else if (r instanceof ArithExpr) {
                sb.append("#(");
                sb.append(r.toString());
                sb.append(")");
            } else {
                sb.append(r.toString());
            }
        }
        if (pushBack != null) {
            sb.append(" <= ");
            for (Expr s : pushBack) {
                sb.append(s.toString());
            }
        }
        if (rightState != null) {
            sb.append(rightState.toString());
        }
        sb.append(";");
        return sb.toString();
    }
}
