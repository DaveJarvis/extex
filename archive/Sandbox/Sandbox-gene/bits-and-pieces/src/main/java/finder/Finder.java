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

package finder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class Finder {


    private Finder() {

        //not used
    }

    /**
     * The command line interface.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PrintStream printStream = System.out;
        ClassLoader classLoader = Finder.class.getClassLoader();
        try {
            Enumeration<URL> r = classLoader.getResources("toc.index");
            while (r.hasMoreElements()) {
                URL el = r.nextElement();
                printStream.println(el.getPath());
                Properties properties = new Properties();
                InputStream s = el.openStream();
                properties.load(s);
                s.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
