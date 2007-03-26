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

package org.extex.exdoc;

/**
 * Collect the doc snippets from Java code and store them in XML files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5413 $
 */
public final class Exdoc {

    /**
     * The main program for this class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            if (args.length == 0) {
                new ExDocXml().run(args);
            } else if ("-xml".equals(args[0])) {
                args[0] = null;
                new ExDocXml().run(args);
            } else if ("-html".equals(args[0])) {
                args[0] = null;
                new ExDocHtml().run(args);
            } else if ("-tex".equals(args[0])) {
                args[0] = null;
                new ExDocTeX().run(args);
            } else {
                new ExDocXml().run(args);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Creates a new object.
     */
    private Exdoc() {

        // not used
    }

}
