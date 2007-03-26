/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import org.extex.core.UnicodeChar;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.scanner.type.token.ActiveCharacterToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.CrToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.MacroParamToken;
import org.extex.scanner.type.token.MathShiftToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.SubMarkToken;
import org.extex.scanner.type.token.SupMarkToken;
import org.extex.scanner.type.token.TabMarkToken;
import org.extex.scanner.type.token.TokenVisitor;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class holds an output file onto which tokens can be wrtitten.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4388 $
 */
public class OutFile implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * This anonymous inner class is used with the visitor pattern to map the
     * tokens to appropriate print strings.
     */
    private static final TokenVisitor VISITOR = new TokenVisitor() {

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitActive(
         *      org.extex.scanner.type.ActiveCharacterToken,
         *      java.lang.Object)
         */
        public Object visitActive(ActiveCharacterToken token,
                Object w) throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitCr(
         *      org.extex.scanner.type.CrToken, java.lang.Object)
         */
        public Object visitCr(CrToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitEscape(
         *      org.extex.scanner.type.ControlSequenceToken,
         *      java.lang.Object)
         */
        public Object visitEscape(ControlSequenceToken token,
                Object w) throws Exception {

            UnicodeChar c = token.getChar();
            if (c != null) {
                ((Writer) w).write(c.getCodePoint());
            }
            ((Writer) w).write(token.getName());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitLeftBrace(
         *      org.extex.scanner.type.LeftBraceToken,
         *      java.lang.Object)
         */
        public Object visitLeftBrace(LeftBraceToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitLetter(
         *      org.extex.scanner.type.LetterToken,
         *      java.lang.Object)
         */
        public Object visitLetter(LetterToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitMacroParam(
         *      org.extex.scanner.type.MacroParamToken,
         *      java.lang.Object)
         */
        public Object visitMacroParam(MacroParamToken token,
                Object w) throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitMathShift(
         *      org.extex.scanner.type.MathShiftToken,
         *      java.lang.Object)
         */
        public Object visitMathShift(MathShiftToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitOther(
         *      org.extex.scanner.type.OtherToken,
         *      java.lang.Object)
         */
        public Object visitOther(OtherToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitRightBrace(
         *      org.extex.scanner.type.RightBraceToken,
         *      java.lang.Object)
         */
        public Object visitRightBrace(RightBraceToken token,
                Object w) throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSpace(
         *      org.extex.scanner.type.SpaceToken,
         *      java.lang.Object)
         */
        public Object visitSpace(SpaceToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(
         *      org.extex.scanner.type.SubMarkToken,
         *      java.lang.Object)
         */
        public Object visitSubMark(SubMarkToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitSupMark(
         *      org.extex.scanner.type.SupMarkToken,
         *      java.lang.Object)
         */
        public Object visitSupMark(SupMarkToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.scanner.type.token.TokenVisitor#visitTabMark(
         *      org.extex.scanner.type.TabMarkToken,
         *      java.lang.Object)
         */
        public Object visitTabMark(TabMarkToken token, Object w)
                throws Exception {

            ((Writer) w).write(token.getChar().getCodePoint());
            return null;
        }
    };

    /**
     * The field <tt>file</tt> contains the file assigned to this instance.
     * If the value is <code>null</code> then it can never be opened.
     */
    private File file;

    /**
     * The field <tt>writer</tt> contains the real writer assigned to this
     * instance.
     */
    private transient Writer writer = null;

    /**
     * Creates a new object.
     *
     * @param name the file to write to
     */
    public OutFile(File name) {

        super();
        this.file = name;
    }

    /**
     * Close the current file.
     *
     * @throws IOException in case of an error
     */
    public void close() throws IOException {

        if (writer != null) {
            try {
                writer.close();
            } finally {
                writer = null;
            }
        }
    }

    /**
     * Check whether the output file is open.
     *
     * @return <code>true</code> iff the instance is open
     */
    public boolean isOpen() {

        return (null != writer);
    }

    /**
     * Open the current file.
     */
    public void open() {

        if (file != null) {
            try {
                writer = new BufferedWriter(new FileWriter(file));
            } catch (FileNotFoundException e) {
                // ignored on purpose
            } catch (IOException e) {
                // ignored on purpose
            }
        }
    }

    /**
     * Write some tokens to the output writer.
     *
     * @param toks tokens to write
     *
     * @throws InterpreterException in case of an error
     * @throws IOException in case of an IO error
     */
    public void write(Tokens toks)
            throws InterpreterException,
                IOException {

        if (writer == null) {
            return;
        }
        int len = toks.length();

        for (int i = 0; i < len; i++) {
            try {

                toks.get(i).visit(VISITOR, writer);

            } catch (IOException e) {
                throw e;
            } catch (InterpreterException e) {
                throw e;
            } catch (Exception e) {
                throw new InterpreterException(e);
            }
        }
    }

}
