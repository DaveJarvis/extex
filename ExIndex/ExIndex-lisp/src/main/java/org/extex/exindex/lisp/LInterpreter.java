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

package org.extex.exindex.lisp;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LUndefinedFunctionException;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class represents an LInterpreter without predefined functions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LInterpreter {

    /**
     * This is the command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            new LInterpreter().topLevelLoop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LException e) {
            e.printStackTrace();
        }
    }

    /**
     * The field <tt>functionTable</tt> contains the table of all functions.
     */
    private Map<LSymbol, LFunction> functions =
            new HashMap<LSymbol, LFunction>();

    /**
     * The field <tt>valueTable</tt> contains the table of all values of
     * symbols.
     */
    private Map<LSymbol, LValue> bindings = new HashMap<LSymbol, LValue>();

    /**
     * Creates a new object.
     */
    public LInterpreter() {

        super();
    }

    /**
     * Register a new function: The old function is returned.
     * 
     * @param name the symbol under which the function should be registered
     * @param fct the function to register
     * 
     * @return the old binding
     */
    public LFunction defun(LSymbol name, LFunction fct) {

        return functions.put(name, fct);
    }

    /**
     * Register a new function: The old function is returned.
     * 
     * @param name the name under which the function should be registered
     * @param fct the function to register
     * 
     * @return the old binding
     */
    public LFunction defun(String name, LFunction fct) {

        return functions.put(LSymbol.get(name), fct);
    }

    /**
     * Evaluate a single node and return its result.
     * 
     * @param node the node to evaluate
     * 
     * @return the result
     * 
     * @throws LException in case of an error
     */
    public LValue eval(LValue node) throws LException {

        if (node instanceof LSymbol) {
            LValue n = bindings.get(node);
            return n != null ? n : LList.NIL;
        } else if (!(node instanceof LList)) {
            return node;
        }
        LList list = (LList) node;
        if (list.isEmpty()) {
            return node;
        }
        LValue fct = list.get(0);
        if (!(fct instanceof LSymbol)) {
            throw new LException(fct.toString());
        }
        LFunction f = functions.get(fct);
        if (f == null) {
            throw new LUndefinedFunctionException(fct.toString());
        }
        List<LValue> l = new ArrayList<LValue>();
        for (LValue val : list) {
            l.add(val);
        }

        return f.eval(this, l);
    }

    /**
     * Get the binding of a symbol.
     * 
     * @param symbol the symbol to look up
     * 
     * @return the binding or <code>null</code> if nothing is defined
     */
    public LValue get(LSymbol symbol) {

        return bindings.get(symbol);
    }

    /**
     * Load a file and eval all expressions found in it.
     * 
     * @param name the name of the file to load
     * 
     * @return the last expression read
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error in the interpreter
     */
    public LValue load(String name) throws IOException, LException {

        Reader reader = new FileReader(name);
        LParser parser = makeParser(name, reader);
        LValue node = LList.NIL;

        try {
            for (LValue n = parser.read(); n != null; n = parser.read()) {
                node = eval(n);
            }
        } finally {
            reader.close();
        }
        return node;
    }

    /**
     * Create a parser.
     * 
     * @param name the name of the resource
     * @param reader the reader
     * 
     * @return the parser
     */
    protected LParser makeParser(String name, Reader reader) {

        return new LParser(reader, name);
    }

    /**
     * Print all bindings to a stream.
     * 
     * @param stream the output stream
     */
    public void printBindings(PrintStream stream) {

        for (LSymbol k : bindings.keySet()) {
            stream.print("(setq ");
            stream.print(k.getValue());
            stream.print(" ");
            bindings.get(k).print(stream);
            stream.println(")");
        }
    }

    /**
     * Print all functions to a stream.
     * 
     * @param stream the output stream
     */
    public void printFunctions(PrintStream stream) {

        for (LSymbol k : functions.keySet()) {
            stream.print("(defun ");
            stream.print(k.getValue());
            stream.print(" ");
            functions.get(k).print(stream);
            stream.println(")");
        }
    }

    /**
     * Change the binding of a symbol.
     * 
     * @param symbol the symbol
     * @param value the value
     */
    public void setq(LSymbol symbol, LValue value) {

        bindings.put(symbol, value);
    }

    /**
     * Change the binding of a symbol.
     * 
     * @param symbol the symbol
     * @param value the value
     */
    public void setq(String symbol, LValue value) {

        bindings.put(LSymbol.get(symbol), value);
    }

    /**
     * Process the commands read from stdin.
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error in the interpreter
     */
    public void topLevelLoop() throws IOException, LException {

        topLevelLoop(new InputStreamReader(System.in), "<>");
    }

    /**
     * Process the commands read from a stream.
     * 
     * @param is the stream to read from
     * @param resource the resource description for error messages
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error in the interpreter
     */
    public void topLevelLoop(InputStream is, String resource)
            throws IOException,
                LException {

        topLevelLoop(new InputStreamReader(is), resource);
    }

    /**
     * Process the commands read from a reader.
     * 
     * @param r the reader
     * @param resource the resource description for error messages
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error in the interpreter
     */
    public void topLevelLoop(Reader r, String resource)
            throws IOException,
                LException {

        LParser parser = makeParser(resource, r);

        for (LValue node = parser.read(); node != null; node = parser.read()) {

            eval(node);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer
     * @param tag
     * 
     * @throws IOException in case of an I/O error
     */
    public void writeString(Writer writer, String tag) throws IOException {

        LValue x = get(LSymbol.get(tag));
        if (x instanceof LString) {
            writer.write(((LString) x).getValue());
            return;
        }

        // TODO gene: writeString unimplemented
        throw new RuntimeException("unimplemented");
    }
}
