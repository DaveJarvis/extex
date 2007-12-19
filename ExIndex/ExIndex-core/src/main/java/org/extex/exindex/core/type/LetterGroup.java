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

package org.extex.exindex.core.type;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.parser.raw.Indexentry;
import org.extex.exindex.core.type.transform.Transform;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class represents a letter group.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LetterGroup extends ArrayList<IndexEntry> implements LValue {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>after</tt> contains the the letter groups preceding this
     * one.
     */
    private List<LetterGroup> after = new ArrayList<LetterGroup>();

    /**
     * The field <tt>before</tt> contains the letter groups following this
     * one. This value is cleared after the collecting phase.
     */
    private List<LetterGroup> before = new ArrayList<LetterGroup>();

    /**
     * The field <tt>name</tt> contains the name of the letter group.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public LetterGroup(String name) {

        super();
        this.name = name;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param g
     */
    public void after(LetterGroup g) {

        after.add(g);
        g.before.add(this);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param letterGroup
     */
    protected void clearAfter(LetterGroup letterGroup) {

        after.remove(letterGroup);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public void clearAfterBefore() {

        for (LetterGroup g : before) {
            g.clearAfter(this);
        }
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Get an arbitrary element from the before list or <code>null</code> if
     * this list is empty.
     * 
     * @return an arbitrary element from the before list or <code>null</code>
     *         if this list is empty
     */
    public LetterGroup getSomeAfter() {

        if (after.isEmpty()) {
            return null;
        }
        return after.get(0);
    }

    /**
     * Get an arbitrary element from the before list or <code>null</code> if
     * this list is empty.
     * 
     * @return an arbitrary element from the before list or <code>null</code>
     *         if this list is empty
     */
    public LetterGroup getSomeBefore() {

        if (before.isEmpty()) {
            return null;
        }
        return before.get(0);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print("#LetterGroup(\"");
        stream.print(name);
        stream.print("\"");
        if (before != null && !"".equals(before)) {
            stream.print(" :before \"");
            stream.print(before);
            stream.print("\"");
        }
        if (after != null && !"".equals(after)) {
            stream.print(" :after \"");
            stream.print(after);
            stream.print("\"");
        }
        // if (prefixes != null && !prefixes.isEmpty()) {
        // stream.print(" :prefixes (");
        // for (String s : prefixes) {
        // stream.print("\"");
        // stream.print(s);
        // stream.print("\" ");
        // }
        // stream.print(")");
        // }
        stream.print(")");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param entry
     */
    public void store(Indexentry entry) {

        // TODO
        // add(entry);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {

        return name + ": " + super.toString();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(Writer writer, LInterpreter interpreter)
            throws IOException {

        boolean first = true;
        interpreter
            .writeString(writer, "markup:letter-group-" + name + "-open");
        String openHead = "markup:letter-group-" + name + "-open-head";
        String closeHead = "markup:letter-group-" + name + "-close-head";
        if (interpreter.get(LSymbol.get(openHead)) != null
                || interpreter.get(LSymbol.get(closeHead)) != null) {
            interpreter.writeString(writer, openHead);
            LValue trans =
                    interpreter.get(LSymbol.get("markup:letter-group-" + name
                            + "-transform"));
            if (trans == null || !(trans instanceof Transform)) {
                writer.write(name);
            } else {
                writer.write(((Transform) trans).transform(name));
            }
            interpreter.writeString(writer, closeHead);
        }

        for (IndexEntry entry : this) {
            if (first) {
                first = false;
            } else {
                interpreter.writeString(writer, "markup:letter-group-" + name
                        + "-sep");
            }

            entry.write(writer, interpreter, 0);
        }
        interpreter.writeString(writer, "markup:letter-group-" + name
                + "-close");
    }

}
