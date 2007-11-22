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

import java.io.IOException;

import org.extex.exindex.lisp.builtin.Quote;
import org.extex.exindex.lisp.builtin.Require;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LEngine extends LInterpreter {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            new LEngine().topLevelLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new object.
     */
    public LEngine() {

        super();
        register("require", new Require());
        register("quote", new Quote());
    }

}
