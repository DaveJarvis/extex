/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bstio;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalLocator;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TStringOption;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;
import org.extex.exbib.core.exceptions.ExBibIoException;
import org.extex.exbib.core.io.Writer;

/**
 * This class provides an implementation of a printer for bst files. This
 * implementation produces output compatible to the
 * B<small>IB</small>T<sub>E<c/sub>X notation of bst files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BstPrinterImpl implements CommandVisitor {

    /**
     * The field <tt>writer</tt> contains the writer onto which the output
     * should be written.
     */
    private final Writer writer;

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
     * Creates a new object.
     *
     * @param writer the target writer
     */
    public BstPrinterImpl(Writer writer) {

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

        writeEntry(processor);
        write("INTEGERS", processor.getIntegers());
        write("STRINGS", processor.getStrings());
        writeOptions(processor);

        List<String> functionVector = processor.getFunctionNames();
        Collections.sort(functionVector);

        for (String function : functionVector) {
            Code code = processor.getFunction(function);
            writer.print("FUNCTION { ", function, " }{\n", in);
            this.nl = true;
            ((MacroCode) code).getToken().visit(this, processor);
            writer.println("}\n");
        }

        writer.println();

        List<String> macroVector = processor.getMacroNames();
        Collections.sort(macroVector);

        for (String key : macroVector) {
            String value = processor.getMacro(key);

            if (value != null) {
                writer.print("MACRO { ", key, " }{ \"");
                writer.print(value);
                writer.println("\" }");
            }
        }

        writer.println();

        for (Command command : processor) {
            command.visit(this, processor);
            writer.println();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
     *      java.lang.Object[])
     */
    public void visitBlock(TBlock block, Object... args) throws ExBibException {

        String indent = this.in;
        if (!this.nl) {
            write("\n");
            write(in);
        }
        this.in = this.in + "    ";
        write("{");
        this.nl = false;

        for (Token t : block) {
            t.visit(this, args);
        }
        write(indent);
        write("}\n");
        write(in);
        this.nl = true;
        this.in = indent;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
     *      java.lang.Object[])
     */
    public void visitChar(TChar c, Object... args) {

        //
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitExecute(org.extex.exbib.core.bst.command.Command,
     *      java.lang.Object[])
     */
    public void visitExecute(Command command, Object... args)
            throws ExBibException {

        write("EXECUTE {");
        this.nl = false;
        command.getValue().visit(this, args);
        write(" }");
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
     *      java.lang.Object[])
     */
    public void visitField(TField field, Object... args) throws ExBibException {

        writeSpace();
        write(field.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
     *      java.lang.Object[])
     */
    public void visitInteger(TInteger integer, Object... args)
            throws ExBibException {

        writeSpace();
        write("#");
        write(integer.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitIntegerOption(org.extex.exbib.core.bst.token.impl.TIntegerOption,
     *      java.lang.Object[])
     */
    public void visitIntegerOption(TIntegerOption option, Object... args)
            throws ExBibException {

        write(option.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitIterate(org.extex.exbib.core.bst.command.Command,
     *      java.lang.Object[])
     */
    public void visitIterate(Command command, Object... args)
            throws ExBibException {

        write("ITERATE {");
        this.nl = false;
        command.getValue().visit(this, args);
        write(" }");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
     *      java.lang.Object[])
     */
    public void visitLiteral(TLiteral literal, Object... args)
            throws ExBibException {

        writeSpace();
        write(literal.getValue());
        Code code = ((BstProcessor) args[0]).getFunction(literal.getValue());
        if (code != null && !(code instanceof MacroCode)) {
            write("\n");
            write(in);
            this.nl = true;
        } else {
            this.nl = false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
     *      java.lang.Object[])
     */
    public void visitLocalInteger(TLocalInteger integer, Object... args)
            throws ExBibException {

        writeSpace();
        write(integer.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalLocator(org.extex.exbib.core.bst.token.impl.TLocalLocator,
     *      java.lang.Object[])
     */
    public void visitLocalLocator(TLocalLocator localLocator, Object[] args)
            throws ExBibIoException {

        writeSpace();
        write(localLocator.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
     *      java.lang.Object[])
     */
    public void visitLocalString(TLocalString string, Object... args)
            throws ExBibException {

        writeSpace();
        write(string.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
     *      java.lang.Object[])
     */
    public void visitQLiteral(TQLiteral qliteral, Object... args)
            throws ExBibException {

        writeSpace();
        write("'");
        write(qliteral.getValue());
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitRead(org.extex.exbib.core.bst.command.Command,
     *      java.lang.Object[])
     */
    public void visitRead(Command command, Object... args)
            throws ExBibException {

        write("READ");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitReverse(org.extex.exbib.core.bst.command.Command,
     *      java.lang.Object[])
     */
    public void visitReverse(Command command, Object... args)
            throws ExBibException {

        write("REVERSE {");
        this.nl = false;
        command.getValue().visit(this, args);
        write(" }");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.command.CommandVisitor#visitSort(org.extex.exbib.core.bst.command.Command,
     *      java.lang.Object[])
     */
    public void visitSort(Command command, Object... args)
            throws ExBibException {

        write("SORT");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
     *      java.lang.Object[])
     */
    public void visitString(TString string, Object... args)
            throws ExBibException {

        writeSpace();
        write("\"");
        write(string.getValue());
        write("\"");
        this.nl = false;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitStringOption(org.extex.exbib.core.bst.token.impl.TStringOption,
     *      java.lang.Object[])
     */
    public void visitStringOption(TStringOption option, Object... args)
            throws ExBibException {

        write(option.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
     *      java.lang.Object[])
     */
    public void visitTokenList(TokenList list, Object... args)
            throws ExBibException {

        for (Token t : list) {
            t.visit(this, args);
        }
    }

    /**
     * Print a string to the output writer.
     *
     * @param s the string to be printed
     *
     * @throws ExBibIoException in case of an I/O error
     */
    private void write(String s) throws ExBibIoException {

        try {
            writer.print(s);
        } catch (IOException e) {
            throw new ExBibIoException(e);
        }
    }

    /**
     * Write a list with a tag.
     *
     * @param tag the tag
     * @param list the list
     *
     * @throws ExBibIoException in case of an I/O error
     */
    private void write(String tag, List<String> list) throws ExBibIoException {

        if (!list.isEmpty()) {
            write(tag);
            write(" {");
            Collections.sort(list);

            for (String key : list) {
                write("\n");
                write(in);
                write(key);
            }

            write("\n}\n\n");
        }
    }

    /**
     * Write an entry
     *
     * @param processor the processor
     *
     * @throws ExBibIoException in case of an I/O error
     * @throws ExBibIllegalValueException in case of an illegal value
     * @throws ExBibFunctionExistsException in case of an existing function
     */
    private void writeEntry(BstProcessor processor)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException,
                ExBibIoException {

        write("ENTRY {");

        for (String entry : processor.getEntries()) {
            write("\n");
            write(in);
            write(entry);
        }
        write("\n  } {");
        writeList(processor.getEntryIntegers());
        write("} {");
        writeList(processor.getEntryStrings());
        write("}\n\n");
    }

    /**
     * Write a list.
     *
     * @param values the list
     *
     * @throws ExBibIoException in case of an I/O error
     */
    private void writeList(List<String> values) throws ExBibIoException {

        if (!values.isEmpty()) {
            Collections.sort(values);

            for (String s : values) {
                write("\n");
                write(in);
                write(s);
            }
            write("\n  ");
        }
    }

    /**
     * Write the options to the output writer. The options are written in
     * ascending order of their name.
     *
     * @param processor the processor
     *
     * @throws ExBibIoException in case of an I/O error
     */
    private void writeOptions(BstProcessor processor) throws ExBibIoException {

        Map<String, Token> ops = processor.getOptions();
        Set<String> keySet = ops.keySet();
        String[] ks = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(ks);
        for (String key : ks) {
            write("OPTION{");
            write(key);
            write("}{");
            write(ops.get(key).getValue());
            write("}\n");
        }
    }

    /**
     * Write a space if one is required.
     *
     * @throws ExBibIoException in case of an I/O error
     */
    private void writeSpace() throws ExBibIoException {

        if (!this.nl) {
            write(" ");
        }
    }

}
