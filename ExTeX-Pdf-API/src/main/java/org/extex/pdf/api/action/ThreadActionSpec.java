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

package org.extex.pdf.api.action;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.pdf.api.id.IdSpec;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class represents a thread action spec.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4409 $
 */
public class ThreadActionSpec extends ActionSpec {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Parse a thread action spec.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param primitive the name of the primitive
     *
     * @return the action spec found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static ActionSpec parseActionSpec(Context context,
            TokenSource source, Typesetter typesetter,
            CodeToken primitive) throws HelpingException,TypesetterException {

        String file = null;
        if (source.getKeyword(context, "file")) {
            file = source.scanTokensAsString(context, primitive);
        }

        IdSpec id = IdSpec.parseIdSpec(context, source, typesetter, primitive);

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
    public ThreadActionSpec(String file, IdSpec id) {

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
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("thread file ");
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
     * @see org.extex.pdf.api.action.ActionSpec#visit(
     *      org.extex.pdf.api.action.ActionVisitor)
     */
    @Override
    public Object visit(ActionVisitor visitor) {

        return visitor.visitThread(this);
    }

}
