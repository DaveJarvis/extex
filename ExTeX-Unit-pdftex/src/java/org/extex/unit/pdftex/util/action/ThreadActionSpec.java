/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex.util.action;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;
import org.extex.unit.pdftex.util.id.IdSpec;

/**
 * This class represents a thread action spec.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4409 $
 */
public class ThreadActionSpec extends ActionSpec {

    /**
     * Parse a thread action spec.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param name the name of the primitive
     *
     * @return the action spec found
     *
     * @throws InterpreterException in case of an error
     */
    public static ActionSpec parseActionSpec(final Context context,
            final TokenSource source, final Typesetter typesetter,
            final String name) throws InterpreterException {

        String file = null;
        if (source.getKeyword(context, "file")) {
            file = source.scanTokensAsString(context, name);
        }

        IdSpec id = IdSpec.parseIdSpec(context, source, typesetter, name);

        return new ThreadActionSpec(file, id);
    }

    /**
     * The field <tt>file</tt> contains the file.
     */
    private String file;

    /**
     * The field <tt>id</tt> contains the id.
     */
    private IdSpec id;

    /**
     * Creates a new object.
     *
     * @param file the file
     * @param id the id
     */
    public ThreadActionSpec(final String file, final IdSpec id) {

        super();
        this.file = file;
        this.id = id;
    }

    /**
     * Getter for id.
     * The id can either be a number or a name.
     * This value is not <code>null</code>.
     *
     * @return the id
     */
    protected IdSpec getId() {

        return this.id;
    }

    /**
     * Getter for file.
     * This value is not <code>null</code>.
     *
     * @return the file
     */
    protected String getFile() {

        return this.file;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("thread file ");
        sb.append(file);
        sb.append(" ");
        sb.append(id.toString());
        return sb.toString();
    }

    /**
     * This method is the entry point for the visitor pattern.
     *
     * @param visitor the visitor to call back
     *
     * @return an arbitrary return object
     *
     * @see org.extex.unit.pdftex.util.action.ActionSpec#visit(
     *      org.extex.unit.pdftex.util.action.ActionVisitor)
     */
    public Object visit(final ActionVisitor visitor) {

        return visitor.visitThread(this);
    }

}
