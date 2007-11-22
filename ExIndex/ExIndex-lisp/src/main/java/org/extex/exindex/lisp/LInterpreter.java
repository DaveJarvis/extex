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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.LFunction;
import org.extex.exindex.lisp.type.LList;
import org.extex.exindex.lisp.type.LValue;
import org.extex.exindex.lisp.type.LSymbol;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LInterpreter {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            new LInterpreter().topLevelLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The field <tt>functionTable</tt> contains the ...
     */
    private Map<LSymbol, LFunction> functionTable =
            new HashMap<LSymbol, LFunction>();

    /**
     * The field <tt>valueTable</tt> contains the ...
     */
    private Map<LSymbol, LValue> valueTable = new HashMap<LSymbol, LValue>();

    /**
     * Creates a new object.
     * 
     */
    public LInterpreter() {

        super();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param node the node to evaluate
     * 
     * @return the result
     * @throws IOException
     */
    public LValue eval(LValue node) throws IOException {

        if (node instanceof LSymbol) {
            LValue n = valueTable.get(node);
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
            // TODO gene: topLevelLoop unimplemented
            throw new RuntimeException("unimplemented");
        }
        LSymbol function = (LSymbol) fct;

        LFunction f = functionTable.get(function);
        if (f == null) {
            // TODO gene: eval unimplemented
            throw new RuntimeException("unimplemented");
        }

        int size = list.size() - 1;
        LValue[] args = new LValue[size];
        for (int i = 1; i < size; i++) {
            args[i] = eval(list.get(i));
        }
        return f.eval(this, args);
    }

    /**
     * Load a file and eval all expressions found in it.
     * 
     * @param name the name of the file to load
     * 
     * @return the last expression read
     * 
     * @throws IOException in case of an I/O error
     */
    public LValue load(String name) throws IOException {

        InputStreamReader reader =
                new InputStreamReader(new FileInputStream(name));
        LParser parser = new LParser(reader);
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
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @param fct
     */
    public void register(LSymbol name, LFunction fct) {

        functionTable.put(name, fct);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @param fct
     */
    public void register(String name, LFunction fct) {

        functionTable.put(LSymbol.get(name), fct);
    }

    public void topLevelLoop() throws IOException {

        topLevelLoop(new InputStreamReader(System.in));
    }

    public void topLevelLoop(InputStream is) throws IOException {

        topLevelLoop(new InputStreamReader(is));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param r the reader
     * 
     * @throws IOException in case of an error
     */
    public void topLevelLoop(Reader r) throws IOException {

        LParser parser = new LParser(r);

        for (LValue node = parser.read(); node != null; node = parser.read()) {

            eval(node);
        }
    }

}
