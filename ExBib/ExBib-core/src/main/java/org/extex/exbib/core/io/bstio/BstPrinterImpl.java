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
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TBlock;
import org.extex.exbib.core.bst.node.impl.TChar;
import org.extex.exbib.core.bst.node.impl.TField;
import org.extex.exbib.core.bst.node.impl.TFieldInteger;
import org.extex.exbib.core.bst.node.impl.TFieldString;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.bst.node.impl.TQLiteral;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.bst.node.impl.TokenList;
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

        writer.print("ENTRY {\n");

        Iterator<String> iterator = processor.getEntries().iterator();

        while (iterator.hasNext()) {
            writer.print("    ", iterator.next(), "\n");
        }

        writer.print("  } {");

        iterator = processor.getEntryIntegers().iterator();

        if (iterator.hasNext()) {
            writer.print("\n");

            while (iterator.hasNext()) {
                writer.print("    ", iterator.next(), "\n");
            }

            writer.print("  ");
        }

        writer.print("} {");

        iterator = processor.getEntryStrings().iterator();

        if (iterator.hasNext()) {
            writer.print("\n");

            while (iterator.hasNext()) {
                writer.print("    ", iterator.next(), "\n");
            }

            writer.print("  ");
        }

        writer.print("}\n\n");

        iterator = processor.getIntegers().iterator();

        if (iterator.hasNext()) {
            writer.print("INTEGERS {");

            while (iterator.hasNext()) {
                writer.print(" ", iterator.next());
            }

            writer.print(" }\n\n");
        }

        iterator = processor.getStrings().iterator();

        if (iterator.hasNext()) {
            writer.print("STRINGS {");

            while (iterator.hasNext()) {
                writer.print(" ", iterator.next());
            }

            writer.print(" }\n\n");
        }

        List<String> functionVector = processor.getFunctionNames();
        Collections.sort(functionVector);

        iterator = processor.getFunctionNames().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            Code code = processor.getFunction(key);
            writer.print("FUNCTION { ", key, " }{ ");
            ((MacroCode) code).getToken().visit(this);
            writer.print("}\n");
        }

        writer.print("\n");

        List<String> macroVector = processor.getMacroNames();
        Collections.sort(macroVector);
        iterator = macroVector.iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = processor.getMacro(key);

            if (value != null) {
                writer.print("MACRO { ", key, " }{ \"");
                writer.print(value);
                writer.print("\" }\n");
            }
        }

        writer.print("\n");

        Iterator<Command> iter = processor.commandsIterator();

        while (iter.hasNext()) {
            iter.next().visit(this);
            writer.print("\n");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitBlock(
     *      org.extex.exbib.core.bst.node.impl.TBlock)
     */
    public void visitBlock(TBlock block) throws IOException {

        writer.print("{");
        boolean first = true;

        for (Token t : block.getTokenList()) {
            if (first) {
                first = false;
            } else {
                writer.print(" ");
            }

            t.visit(this);
        }
        writer.print("}");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitChar(
     *      org.extex.exbib.core.bst.node.impl.TChar)
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

        writer.print("EXECUTE { ");
        command.getValue().visit(this);
        writer.print(" }");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitField(
     *      org.extex.exbib.core.bst.node.impl.TField)
     */
    public void visitField(TField field) throws IOException {

        writer.print(field.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitFieldInteger(
     *      org.extex.exbib.core.bst.node.impl.TFieldInteger)
     */
    public void visitFieldInteger(TFieldInteger integer) throws IOException {

        writer.print(integer.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitFieldString(
     *      org.extex.exbib.core.bst.node.impl.TFieldString)
     */
    public void visitFieldString(TFieldString string) throws IOException {

        writer.print(string.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitInteger(
     *      org.extex.exbib.core.bst.node.impl.TInteger)
     */
    public void visitInteger(TInteger integer) throws IOException {

        writer.print("#", integer.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitIterate(
     *      org.extex.exbib.core.bst.command.Command)
     */
    public void visitIterate(Command command) throws IOException {

        writer.print("ITERATE { ");
        command.getValue().visit(this);
        writer.print(" }");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitLiteral(
     *      org.extex.exbib.core.bst.node.impl.TLiteral)
     */
    public void visitLiteral(TLiteral literal) throws IOException {

        writer.print(literal.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitQLiteral(
     *      org.extex.exbib.core.bst.node.impl.TQLiteral)
     */
    public void visitQLiteral(TQLiteral qliteral) throws IOException {

        writer.print("'", qliteral.getValue());
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

        writer.print("REVERSE { ");
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
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitString(
     *      org.extex.exbib.core.bst.node.impl.TString)
     */
    public void visitString(TString string) throws IOException {

        writer.print("\"", string.getValue(), "\"");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitTokenList(
     *      org.extex.exbib.core.bst.node.impl.TokenList)
     */
    public void visitTokenList(TokenList list) throws IOException {

        boolean first = true;

        for (Token t : list) {
            if (first) {
                first = false;
            } else {
                writer.print(" ");
            }

            t.visit(this);
        }
    }

}
