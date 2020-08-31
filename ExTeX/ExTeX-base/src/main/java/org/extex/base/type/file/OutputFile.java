/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.base.type.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.file.OutFile;
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
 * This class holds an output file onto which tokens can be written.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OutputFile implements OutFile {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * This anonymous inner class is used with the visitor pattern to map the
     * tokens to appropriate print strings.
     */
    private static final TokenVisitor<Object, Writer> VISITOR =
            new TokenVisitor<Object, Writer>() {

                /**
            *      org.extex.scanner.type.token.ActiveCharacterToken,
                 *      java.lang.Object)
                 */
                public Object visitActive(ActiveCharacterToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitCr(
                 *      org.extex.scanner.type.token.CrToken, java.lang.Object)
                 */
                public Object visitCr(CrToken token, Writer w) throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitEscape(
                 *      org.extex.scanner.type.token.ControlSequenceToken,
                 *      java.lang.Object)
                 */
                public Object visitEscape(ControlSequenceToken token, Writer w)
                        throws Exception {

                    UnicodeChar c = token.getChar();
                    if (c != null) {
                        w.write(c.getCodePoint());
                    }
                    w.write(token.getName());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitLeftBrace(
                 *      org.extex.scanner.type.token.LeftBraceToken,
                 *      java.lang.Object)
                 */
                public Object visitLeftBrace(LeftBraceToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitLetter(
                 *      org.extex.scanner.type.token.LetterToken,
                 *      java.lang.Object)
                 */
                public Object visitLetter(LetterToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitMacroParam(
                 *      org.extex.scanner.type.token.MacroParamToken,
                 *      java.lang.Object)
                 */
                public Object visitMacroParam(MacroParamToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitMathShift(
                 *      org.extex.scanner.type.token.MathShiftToken,
                 *      java.lang.Object)
                 */
                public Object visitMathShift(MathShiftToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitOther(
                 *      org.extex.scanner.type.token.OtherToken,
                 *      java.lang.Object)
                 */
                public Object visitOther(OtherToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitRightBrace(
                 *      org.extex.scanner.type.token.RightBraceToken,
                 *      java.lang.Object)
                 */
                public Object visitRightBrace(RightBraceToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitSpace(
                 *      org.extex.scanner.type.token.SpaceToken,
                 *      java.lang.Object)
                 */
                public Object visitSpace(SpaceToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(
                 *      org.extex.scanner.type.token.SubMarkToken,
                 *      java.lang.Object)
                 */
                public Object visitSubMark(SubMarkToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitSupMark(
                 *      org.extex.scanner.type.token.SupMarkToken,
                 *      java.lang.Object)
                 */
                public Object visitSupMark(SupMarkToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }

                /**
                 * @see org.extex.scanner.type.token.TokenVisitor#visitTabMark(
                 *      org.extex.scanner.type.token.TabMarkToken,
                 *      java.lang.Object)
                 */
                public Object visitTabMark(TabMarkToken token, Writer w)
                        throws Exception {

                    w.write(token.getChar().getCodePoint());
                    return null;
                }
            };

    /**
     * The field {@code file} contains the file assigned to this instance. If
     * the value is {@code null} then it can never be opened.
     */
    private final File file;

    /**
     * The field {@code writer} contains the real writer assigned to this
     * instance.
     */
    private transient Writer writer = null;

    /**
     * Creates a new object.
     * 
     * @param name the file to write to
     */
    public OutputFile(File name) {

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
     * @return {@code true} iff the instance is open
     */
    public boolean isOpen() {

        return (null != writer);
    }

public void newline() throws IOException {

        if (writer != null) {
            writer.write('\n');
        }
    }

    /**
     * Open the current file.
     * 
*      java.lang.String, TokenStreamFactory)
     */
    public void open(String key, String encoding, TokenStreamFactory factory)
            throws UnsupportedEncodingException {

        if (file != null) {

            try {
                writer = factory.writerStream(new FileOutputStream(file),
                    key, encoding);
            } catch (FileNotFoundException e) {
                // ignored on purpose
            }
        }
    }

    /**
     * Interceptor method for writer initialization.
     * 
     * @param w the writer at hand
     * 
     * @return the writer to use instead
     */
    protected Writer use(Writer w) {

        return w;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.scanner.type.file.OutFile#write(org.extex.scanner.type.tokens.Tokens)
     */
    public boolean write(Tokens toks) throws HelpingException, IOException {

        if (writer == null) {
            return false;
        }
        Writer w = use(writer);
        int len = toks.length();

        for (int i = 0; i < len; i++) {
            try {

                toks.get(i).visit(VISITOR, w);

            } catch (IOException e) {
                throw e;
            } catch (HelpingException e) {
                throw e;
            } catch (Exception e) {
                throw new NoHelpException(e);
            }
        }
        return true;
    }

}
