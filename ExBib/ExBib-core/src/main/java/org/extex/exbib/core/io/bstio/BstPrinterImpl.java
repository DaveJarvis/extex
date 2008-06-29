/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bstio;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Writer;

/**
 * This class provides an implementation of a printer for bst files. This
 * implementation produces output compatible to the B<small>IB</small>T<sub>E<c/sub>X
 * notation of bst files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BstPrinterImpl implements CommandVisitor {

    /**
     * The field <tt>writer</tt> contains the writer onto which the output
     * should be written.
     */
    private Writer writer;

    /**
     * The field <tt>in</tt> contains the string to produce an indentation for
     * code.
     */
    private String in = "    ";

    /**
     * The field <tt>nl</tt> contains the indicator that the last character in
     * the output stream is a newline.
     */
    private boolean nl = true;

    /**
     * The field <tt>processor</tt> contains the processor. It is only used
     * during printing to transport the processor to the visitor methods
     */
    private BstProcessor processor = null;

    /**
     * Creates a new object.
     * 
     * @param writer the target writer
     */
    public BstPrinterImpl(Writer writer) {

        super();
        this.writer = writer;
    }

    /**
     * This method produces the printed output of the bibliography style.
     * 
     * @param processor the processor context
     * 
     * @throws IOException in case that something goes wrong
     * @throws ExBibException in case that getting some information from the
     *         processor context fails
     */
    public void print(BstProcessor processor)
            throws IOException,
                ExBibException {

        this.processor = processor;
        writer.println("ENTRY {");

        for (String entry : processor.getEntries()) {
            writer.println(in, entry);
        }

        writer.print("  } {");

        List<String> integers = processor.getEntryIntegers();

        if (!integers.isEmpty()) {
            writer.println();

            for (String ints : integers) {
                writer.println(in, ints);
            }

            writer.print("  ");
        }

        writer.print("} {");

        List<String> strings = processor.getEntryStrings();

        if (!strings.isEmpty()) {
            writer.println();
            Collections.sort(strings);

            for (String s : strings) {
                writer.println(in, s);
            }

            writer.print("  ");
        }

        writer.println("}\n");

        integers = processor.getIntegers();

        if (!integers.isEmpty()) {
            writer.print("INTEGERS {");
            Collections.sort(integers);
            for (String key : integers) {
                writer.print("\n", in, key);
            }

            writer.println("\n}\n");
        }

        strings = processor.getStrings();

        if (!strings.isEmpty()) {
            writer.print("STRINGS {");
            Collections.sort(strings);

            for (String key : strings) {
                writer.print("\n", in, key);
            }

            writer.println("\n}\n");
        }

        List<String> functionVector = processor.getFunctionNames();
        Collections.sort(functionVector);

        for (String function : processor.getFunctionNames()) {
            Code code = processor.getFunction(function);
            writer.print("FUNCTION { ", function, " }{\n", in);
            this.nl = true;
            ((MacroCode) code).getToken().visit(this);
            writer.println("}\n");
        }

        writer.println();

        List<String> macroVector = processor.getMacroNames();
        Collections.sort(macroVector);

        for (String key : processor.getFunctionNames()) {
            String value = processor.getMacro(key);

            if (value != null) {
                writer.print("MACRO { ", key, " }{ \"");
                writer.print(value);
                writer.println("\" }");
            }
        }

        writer.println();

        for (Command command : processor) {
            command.visit(this);
            writer.println();
        }

        this.processor = null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(
     *      org.extex.exbib.core.bst.token.impl.TBlock)
     */
    public void visitBlock(TBlock block) throws IOException {

        String i = this.in;
        if (!this.nl) {
            writer.print("\n", in);
        }
        this.in = this.in + "    ";
        writer.print("{");
        this.nl = false;

        for (Token t : block) {
            t.visit(this);
        }
        writer.print(i, "}\n", in);
        this.nl = true;
        this.in = i;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(
     *      org.extex.exbib.core.bst.token.impl.TChar)
     */
    public void visitChar(TChar c) throws IOException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitExecute(
     *      org.extex.exbib.core.bst.command.Command)
     */
    public void visitExecute(Command command) throws IOException {

        writer.print("EXECUTE {");
        this.nl = false;
        command.getValue().visit(this);
        writer.print(" }");
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(
     *      org.extex.exbib.core.bst.token.impl.TField)
     */
    public void visitField(TField field) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print(field.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(
     *      org.extex.exbib.core.bst.token.impl.TInteger)
     */
    public void visitInteger(TInteger integer) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print("#", integer.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitIterate(
     *      org.extex.exbib.core.bst.command.Command)
     */
    public void visitIterate(Command command) throws IOException {

        writer.print("ITERATE {");
        this.nl = false;
        command.getValue().visit(this);
        writer.print(" }");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(
     *      org.extex.exbib.core.bst.token.impl.TLiteral)
     */
    public void visitLiteral(TLiteral literal) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print(literal.getValue());
        Code code = processor.getFunction(literal.getValue());
        if (code != null && !(code instanceof MacroCode)) {
            writer.print("\n", in);
            this.nl = true;
        } else {
            this.nl = false;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(
     *      org.extex.exbib.core.bst.token.impl.TLocalInteger)
     */
    public void visitLocalInteger(TLocalInteger integer) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print(integer.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(
     *      org.extex.exbib.core.bst.token.impl.TLocalString)
     */
    public void visitLocalString(TLocalString string) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print(string.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(
     *      org.extex.exbib.core.bst.token.impl.TQLiteral)
     */
    public void visitQLiteral(TQLiteral qliteral) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print("'", qliteral.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitRead(
     *      org.extex.exbib.core.bst.command.Command)
     */
    public void visitRead(Command command) throws IOException {

        writer.print("READ");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitReverse(
     *      org.extex.exbib.core.bst.command.Command)
     */
    public void visitReverse(Command command) throws IOException {

        writer.print("REVERSE {");
        this.nl = false;
        command.getValue().visit(this);
        writer.print(" }");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitSort(
     *      org.extex.exbib.core.bst.command.Command)
     */
    public void visitSort(Command command) throws IOException {

        writer.print("SORT");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(
     *      org.extex.exbib.core.bst.token.impl.TString)
     */
    public void visitString(TString string) throws IOException {

        if (!this.nl) {
            writer.print(" ");
        }
        writer.print("\"", string.getValue(), "\"");
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(
     *      org.extex.exbib.core.bst.token.impl.TokenList)
     */
    public void visitTokenList(TokenList list) throws IOException {

        for (Token t : list) {
            t.visit(this);
        }
    }

}
