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

package org.extex.ocpware.compiler.expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.AliasNotDefinedException;
import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.exception.StateNotDefinedException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.left.Left;
import org.extex.ocpware.compiler.left.BeginningLeft;
import org.extex.ocpware.compiler.left.EndLeft;
import org.extex.ocpware.compiler.left.LeftParser;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.ParserStream;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.compiler.sexpression.Expr;
import org.extex.ocpware.compiler.sexpression.SParser;
import org.extex.ocpware.compiler.state.RightState;
import org.extex.ocpware.compiler.state.StateChange;
import org.extex.ocpware.compiler.state.StatePop;
import org.extex.ocpware.compiler.state.StatePush;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class represents an expression.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Expression {

    /**
     * Parse an optional left state specification.
     * 
     * @param s the input stream
     * 
     * @return the left state or <code>null</code>
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private static String optionalLeftState(ParserStream s)
            throws IOException,
                SyntaxException {

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
     * Parse an expression.
     * 
     * @param stream the input stream
     * @return the list of expressions found
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static Expression parse(ParserStream stream)
            throws IOException,
                SyntaxException {

        int c = stream.skipSpace();
        if (c < 0) {
            return null;
        }

        stream.unread(c);
        Expression expression = new Expression();

        expression.setLeftState(optionalLeftState(stream));
        expression.setTotalLeft(totalLeft(stream));
        expression.setRight(right(stream));
        expression.setPushBack(parsePushBack(stream));
        expression.setRightState(parseOptionalRightState(stream));
        stream.expect(';');
        return expression;
    }

    /**
     * Parse a list of expressions.
     * 
     * @param stream the input stream
     * @return the list of expressions found
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public static List<Expression> parseExpressions(ParserStream stream)
            throws IOException,
                SyntaxException {

        List<Expression> result = new ArrayList<Expression>();

        for (Expression e = parse(stream); e != null; e = parse(stream)) {
            result.add(e);
        }

        return result;
    }

    /**
     * Parse a right state specification.
     * 
     * @param s the input stream
     * 
     * @return the right state or <code>null</code>
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private static RightState parseOptionalRightState(ParserStream s)
            throws IOException,
                SyntaxException {

        int c = s.skipSpace();
        if (c != '<') {
            s.unread(c);
            return null;
        }

        RightState state = null;
        String t = s.parseId();
        if ("push:".equals(t)) {
            t = s.parseId();
            state = new StatePush(t);
        } else if ("pop:".equals(t)) {
            state = new StatePop();
        } else {
            state = new StateChange(t);
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
     * @throws SyntaxException in case of a syntax error
     */
    private static List<Expr> parsePushBack(ParserStream s)
            throws IOException,
                SyntaxException {

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
     * @throws SyntaxException in case of a syntax error
     */
    private static Right right(ParserStream s)
            throws IOException,
                SyntaxException {

        s.expect('=');
        s.expect('>');
        return new Right(SParser.parse(s));
    }

    /**
     * Parse an optional total left list.
     * 
     * @param s the input stream
     * 
     * @return the list of left items
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private static List<Left> totalLeft(ParserStream s)
            throws IOException,
                SyntaxException {

        List<Left> result = new ArrayList<Left>();
        int c = s.skipSpace();
        s.unread(c);
        if (c == 'b') {
            s.expect("beg:");
            result = LeftParser.left(s);
            result.add(0, new BeginningLeft());
            c = s.skipSpace();
            s.unread(c);
        }

        if (c != '=' && c != '<' && !Character.isLetter(c)) {
            result = LeftParser.left(s);
            c = s.read();
            s.unread(c);
        }

        if (c == 'e') {
            s.expect("end:");
            result.add(new EndLeft());
        }

        return result;
    }

    /**
     * The field <tt>leftState</tt> contains the left state or
     * <code>null</code>.
     */
    String leftState;

    /**
     * The field <tt>pushBack</tt> contains the optional list of push-back
     * expressions.
     */
    private List<Expr> pushBack;

    /**
     * The field <tt>right</tt> contains the ...
     */
    private Right right;

    /**
     * The field <tt>rightState</tt> contains the ...
     */
    private RightState rightState;

    /**
     * The field <tt>state</tt> contains the ...
     */
    private RightState state;

    /**
     * The field <tt>totalLeft</tt> contains the ...
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
     * TODO gene: missing JavaDoc
     * 
     * @param cs the compiler state
     * 
     * @throws AliasNotDefinedException in case that no matching alias is known
     *         for a symbolic table reference
     * @throws ArgmentTooBigException in case that an argument is encountered
     *         which does not fit into two bytes
     * @throws IOException in case of an I/O error
     * @throws StateNotDefinedException
     * @throws TableNotDefinedException in case that no matching table is known
     *         for a symbolic table reference
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public void compile(CompilerState cs)
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException,
                StateNotDefinedException,
                TableNotDefinedException,
                AliasNotDefinedException {

        cs.setCurrentState(leftState);
        cs.incrExpr();
        outLeft(cs);
        // rightOffset = 0;

        if (right != null) {
            right.compile(cs);
            // right_offset=OTP_PBACK_OFFSET;
        }
        if (rightState != null) {
            rightState.compile(cs);
        }
        cs.putInstruction(OcpProgram.STOP);
        // TODO fill_in(left_false_holes);
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
    public Right getRight() {

        return right;
    }

    /**
     * Getter for state.
     * 
     * @return the state
     */
    public RightState getState() {

        return state;
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
     * TODO gene: missing JavaDoc
     * 
     * @param cs the compiler state
     * 
     * @return the list of left items
     * 
     * @throws AliasNotDefinedException in case that no matching alias is known
     *         for a symbolic table reference
     * @throws ArgmentTooBigException in case that an argument is encountered
     *         which does not fit into two bytes
     * @throws IOException in case of an I/O error
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    private List<Integer> outLeft(CompilerState cs)
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException,
                AliasNotDefinedException {

        State currentState = cs.getCurrentState();

        currentState.putInstruction(currentState.getNumExpr() == 1
                ? OcpProgram.LEFT_START
                : OcpProgram.LEFT_RETURN);

        List<Integer> leftFalseHoles = new ArrayList<Integer>();

        int size = totalLeft.size();
        for (int i = 0; i < size; i++) {
            Left l = totalLeft.get(i);
            List<Integer> holes = l.genLeft(currentState, cs);

            if (i < size - 1 //
                    && !(l instanceof BeginningLeft)
                    && !(totalLeft.get(i + 1) instanceof EndLeft)) {

                int ptr =
                        cs.getCurrentState().putInstruction(
                            OcpProgram.GOTO_NO_ADVANCE);
                // left_false_holes = cons(out_ptr-1,left_false_holes);
                leftFalseHoles.add(0, Integer.valueOf(ptr - 1));
            }

            if (holes != null) {
                leftFalseHoles.addAll(holes);
            }
        }

        return leftFalseHoles;
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
    public void setRight(Right right) {

        this.right = right;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param rightState
     */
    private void setRightState(RightState rightState) {

        this.rightState = rightState;
    }

    /**
     * Setter for state.
     * 
     * @param state the state to set
     */
    public void setState(RightState state) {

        this.state = state;
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
        if (right != null) {
            sb.append(right.toString());
        }
        if (pushBack != null) {
            sb.append(" <= ");
            for (Expr x : pushBack) {
                sb.append(x.toString());
            }
        }
        if (rightState != null) {
            sb.append(rightState.toString());
        }
        sb.append(";\n");
        return sb.toString();
    }

}
