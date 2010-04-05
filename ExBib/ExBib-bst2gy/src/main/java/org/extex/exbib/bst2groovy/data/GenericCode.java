/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class is a base class which handles a broad range of possible code
 * variants.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GenericCode implements GCode {

    /**
     * The field <tt>type</tt> contains the return type.
     */
    private ReturnType type;

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>args</tt> contains the arguments.
     */
    private GCode[] args;

    /**
     * The field <tt>entry</tt> contains the name of the entry or
     * <code>null</code>.
     */
    private String entry = null;

    /**
     * The field <tt>showArgs</tt> contains the indicator that argument braces
     * are needed.
     */
    private boolean showArgs;

    /**
     * Creates a new object.
     * 
     * @param type the type
     * @param name the name
     * @param args the argument indicator
     */
    public GenericCode(ReturnType type, String name, boolean args) {

        this(type, name);
        this.showArgs = args;
    }

    /**
     * Creates a new object.
     * 
     * @param type the type
     * @param name the name
     * @param args the arguments
     */
    public GenericCode(ReturnType type, String name, GCode... args) {

        this.type = type;
        this.name = name;
        this.args = args.clone();
        this.showArgs = true;
    }

    /**
     * Creates a new object.
     * 
     * @param type the type
     * @param name the name
     * @param entry the entry or <code>null</code>
     * @param args the arguments
     */
    public GenericCode(ReturnType type, String name, String entry,
            GCode... args) {

        this(type, name, args);
        this.entry = entry;
        this.showArgs = true;
    }

    /**
     * Getter for a named argument.
     * 
     * @param index the index
     * 
     * @return the argument
     */
    public GCode getArg(int index) {

        return args[index];
    }

    /**
     * Getter for the entry.
     * 
     * @return the entry
     */
    public String getEntry() {

        return entry;
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

        return type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#optimize()
     */
    public GCode optimize() {

        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].optimize();
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#optimize(java.util.List, int)
     */
    public int optimize(List<GCode> list, int index) {

        return index + 1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
     *      java.lang.String)
     */
    public void print(CodeWriter writer, String prefix) throws IOException {

        if (type == ReturnType.VOID) {
            writer.write(prefix);
        }
        writer.write(name);

        if (!showArgs) {
            return;
        }
        printOpenArg(writer);
        int col = writer.getColumn();
        boolean first = true;
        if (entry != null) {
            writer.write(entry);
            first = false;
        }

        for (GCode arg : args) {
            if (first) {
                first = false;
            } else {
                writer.write(",");
                writer.nl(col);
            }
            arg.print(writer, prefix);
        }
        printCloseArg(writer);
    }

    /**
     * Print the postfix for arguments to the writer.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    protected void printCloseArg(CodeWriter writer) throws IOException {

        writer.write(")");
    }

    /**
     * Print the prefix for arguments to the writer.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    protected void printOpenArg(CodeWriter writer) throws IOException {

        writer.write("(");
    }

    /**
     * Setter for the arguments.
     * 
     * @param index the index
     * @param value the value
     */
    public void setArg(int index, GCode value) {

        args[index] = value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringWriter writer = new StringWriter();
        try {
            print(new CodeWriter(writer), "\n");
        } catch (IOException e) {
            // This can't happen since a StringWriter doesn't throw
            // IOExceptions
        }
        return writer.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#unify(org.extex.exbib.bst2groovy.data.GCode)
     */
    public boolean unify(GCode other) {

        int size = args.length;
        if (other instanceof Var) {
            return other.unify(this);
        } else if (!(other instanceof GenericCode)
                || !((GenericCode) other).name.equals(name)
                || !(((GenericCode) other).type == type)
                || (entry == null && ((GenericCode) other).entry != null)
                || (entry != null && !entry.equals(((GenericCode) other).entry))
                || ((GenericCode) other).args.length != size) {
            return false;
        }
        for (int i = 0; i < size;) {
            if (!args[i].unify(((GenericCode) other).args[i])) {
                return false;
            }
        }
        return true;
    }

}
