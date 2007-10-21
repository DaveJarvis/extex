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

package org.extex.latexParser.impl.node;

import java.io.PrintStream;

import org.extex.scanner.type.token.Token;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MathEnvironment extends EnvironmentNode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>start</tt> contains the first start token.
     */
    private Token start;

    /**
     * The field <tt>end</tt> contains the end start token.
     */
    private Token end;

    /**
     * The field <tt>start2</tt> contains the optional second start token.
     */
    private Token start2;

    /**
     * The field <tt>end2</tt> contains the optional second end token.
     */
    private Token end2;

    /**
     * Creates a new object.
     * 
     * @param start the start token
     * @param start2 the optional second start token
     * @param end the end token
     * @param end2 the optional second end token
     * @param source the source
     * @param line the line number
     */
    public MathEnvironment(Token start, Token start2, Token end, Token end2,
            String source, int line) {

        super(null, null, null, source, line);
        this.start = start;
        this.start2 = start2;
        this.end = end;
        this.end2 = end2;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.node.EnvironmentNode#print(
     *      java.io.PrintStream)
     */
    @Override
    public void print(PrintStream stream) {

        stream.print(start.toText());
        if (start2 != null) {
            stream.print(start2.toText());
        }
        super.print(stream);
        stream.print(end.toText());
        if (end2 != null) {
            stream.print(end2.toText());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.node.EnvironmentNode#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param sb
     */
    @Override
    public StringBuilder toString(StringBuilder sb) {

        sb.append(start.toText());
        if (start2 != null) {
            sb.append(start2.toText());
        }
        super.toString(sb);
        sb.append(end.toText());
        if (end2 != null) {
            sb.append(end2.toText());
        }
        return sb;
    }

}
