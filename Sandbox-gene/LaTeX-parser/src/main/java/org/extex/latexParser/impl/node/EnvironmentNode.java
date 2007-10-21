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

import org.extex.latexParser.api.Node;
import org.extex.latexParser.api.NodeList;

/**
 * This class represents an environment.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EnvironmentNode extends NodeList {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>args</tt> contains the optional arguments.
     */
    private Node[] args;

    /**
     * The field <tt>opt</tt> contains the optional argument.
     */
    private Node opt;

    /**
     * The field <tt>name</tt> contains the name of the environment.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name of the environment
     * @param opt the optional argument or <code>null</code>
     * @param args the arguments
     * @param source the source of the environment
     * @param line the line number
     */
    public EnvironmentNode(String name, Node opt, Node[] args, String source,
            int line) {

        super(source, line);
        this.name = name;
        this.args = args;
        this.opt = opt;
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
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#print(java.io.PrintStream)
     */
    @Override
    public void print(PrintStream stream) {

        if (name != null) {
            stream.print("\\begin{");
            stream.print(name);
            stream.print("}");
        }
        if (opt != null) {
            stream.print('[');
            opt.print(stream);
            stream.print('}');
        }
        if (args != null) {
            for (Node n : args) {
                stream.print('{');
                n.print(stream);
                stream.print('}');
            }
        }
        super.print(stream);
        if (name != null) {
            stream.print("\\end{");
            stream.print(name);
            stream.print("}");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return toString(new StringBuilder()).toString();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param sb the target
     */
    @Override
    public StringBuilder toString(StringBuilder sb) {

        if (name != null) {
            sb.append("\\begin{");
            sb.append(name);
            sb.append("}");
        }
        if (opt != null) {
            sb.append('[');
            sb.append(opt.toString());
            sb.append('}');
        }
        if (args != null) {
            for (Node n : args) {
                sb.append('{');
                sb.append(n.toString());
                sb.append('}');
            }
        }
        super.toString(sb);
        if (name != null) {
            sb.append("\\end{");
            sb.append(name);
            sb.append("}");
        }
        return sb;
    }

}
