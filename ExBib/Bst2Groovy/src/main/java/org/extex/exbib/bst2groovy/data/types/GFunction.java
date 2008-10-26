/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data.types;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.LinkContainer;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.local.GLocal;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.exception.UnknownReturnTypeException;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GFunction implements Compiler, GCode {

    /**
     * This inner class represents the invocation of a function.
     */
    private class Call implements GCode {

        /**
         * The field <tt>args</tt> contains the argument.
         */
        private List<GCode> args;

        /**
         * The field <tt>entryName</tt> contains the name of the entry.
         */
        private String entryName;

        /**
         * The field <tt>proc</tt> contains the the procedure indicator.
         */
        private boolean proc;

        /**
         * Creates a new object.
         * 
         * @param entryName the name of the entry to use or <code>null</code>
         * @param proc the procedure indicator
         * @param args the arguments
         */
        public Call(String entryName, boolean proc, List<GCode> args) {

            this.entryName = entryName;
            this.args = args;
            this.proc = proc;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#getType()
         */
        public ReturnType getType() {

            return returnValue.getType();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            if (proc) {
                writer.write(prefix);
            }
            writer.write(name);
            writer.write("(");
            boolean first = true;
            if (needsEntry) {
                first = false;
                writer.write(entryName);
            }
            for (GCode arg : args) {
                if (first) {
                    first = false;
                } else {
                    writer.write(", ");
                }
                arg.print(writer, prefix);
            }
            writer.write(")");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            StringBuilder buffer = new StringBuilder();
            if (proc) {
                buffer.append("\n");
            }
            buffer.append(name);
            buffer.append("(");
            boolean first = true;
            if (needsEntry) {
                first = false;
                buffer.append(entryName);
            }
            for (GCode arg : args) {
                if (first) {
                    first = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(arg.toString());
            }
            buffer.append(")");
            return buffer.toString();
        }
    }

    /**
     * The field <tt>useCount</tt> contains the ...
     */
    private int useCount = 0;

    /**
     * The field <tt>translationMap</tt> contains the already mapped names.
     */
    private static Map<String, String> translationMap =
            new HashMap<String, String>();

    /**
     * Translate a bst name int a groovy name for functions and variables.
     * 
     * @param value the value to translate
     * 
     * @return the translated string
     */
    public static String translate(String value) {

        String t = translationMap.get(value);
        if (t != null) {
            return t;
        }
        t = "_" + value.replaceAll("[^a-zA-Z0-9]", "_");
        translationMap.put(value, t);
        return t;
    }

    /**
     * The field <tt>name</tt> contains the method name.
     */
    private String name;

    /**
     * The field <tt>needsEntry</tt> contains the indicator whether the entry is
     * needed.
     */
    private boolean needsEntry;

    /**
     * The field <tt>code</tt> contains the body code.
     */
    private GCode code;

    /**
     * The field <tt>returnValue</tt> contains the return value.
     */
    private GCode returnValue;

    /**
     * The field <tt>parameters</tt> contains the parameters.
     */
    private List<GLocal> parameters;

    /**
     * Creates a new object.
     * 
     * @param returnValue the return value
     * @param name the name
     * @param parameters the list of parameters
     * @param code the body code
     * @param needsEntry
     */
    public GFunction(GCode returnValue, String name, List<GLocal> parameters,
            GCode code, boolean needsEntry) {

        this.returnValue = returnValue;
        this.name = name;
        this.parameters = parameters;
        this.code = code;
        this.needsEntry = needsEntry;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        List<GCode> args;
        int size = parameters.size();
        args = new ArrayList<GCode>(size);
        for (int i = size - 1; i >= 0; i--) {
            args.add(0, state.pop());
        }

        ReturnType t =
                returnValue == null ? ReturnType.CODE : returnValue.getType();
        if (t == ReturnType.STRING || t == ReturnType.INT) {
            state.push(new Call((needsEntry ? entryRefernce.getName() : null),
                false, args));
        } else if (t == ReturnType.CODE) {
            state.add(new Call((needsEntry ? entryRefernce.getName() : null),
                true, args));
        } else {
            throw new UnknownReturnTypeException(name);
        }
    }

    /**
     * Getter for the name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public ReturnType getType() {

        return returnValue == null ? null : returnValue.getType();
    }

    /**
     * Getter for the useCount.
     * 
     * @return the useCount
     */
    public int getUseCount() {

        return useCount;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public boolean needsEntry() {

        return needsEntry;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        writer.write(prefix);
        ReturnType t = (returnValue == null ? null : returnValue.getType());
        writer.write(t == null ? "def" : t.toString());
        writer.write(' ');
        writer.write(name);
        writer.write('(');
        boolean first = true;
        if (needsEntry) {
            first = false;
            writer.write("entry"); // TODO use correct entry name
        }
        for (GCode arg : parameters) {
            if (first) {
                first = false;
            } else {
                writer.write(", ");
            }
            arg.print(writer, prefix);
        }
        writer.write(") {");
        code.print(writer, prefix + Bst2Groovy.INDENT);

        if (returnValue != null) {
            writer.write(prefix);
            writer.write("  return ");
            returnValue.print(writer, prefix);
        }
        writer.write(prefix);
        writer.write("}\n");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "<" + name + ">";
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public void use() {

        useCount++;
    }

}
