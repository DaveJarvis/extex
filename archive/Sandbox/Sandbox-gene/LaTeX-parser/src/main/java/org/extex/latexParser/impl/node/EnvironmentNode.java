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

/**
 * This class represents an environment.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class EnvironmentNode extends GroupNode {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field {@code args} contains the optional arguments.
     */
    private Node[] args;

    /**
     * The field {@code opt} contains the optional argument.
     */
    private Node opt;

    /**
     * The field {@code name} contains the name of the environment.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name of the environment
     * @param opt the optional argument or {@code null}
     * @param args the arguments
     * @param source the source of the environment
     * @param line the line number
     */
    public EnvironmentNode(String name, Node opt, Node[] args, String source,
            int line) {

        super(null, source, line);
        this.name = name;
        this.args = args;
        this.opt = opt;
    }

    /**
     * Getter for the name of the environment.
     * 
     * @return the name
     */
    @Override
    public String getName() {

        return name;
    }

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

@Override
    public String toString() {

        return toString(new StringBuilder()).toString();
    }

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
