/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io;

import java.io.IOException;

import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This writer is a means to print something to two writers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MultiWriter implements Writer, Configurable {

    /**
     * The field {@code w1} contains the first writer.
     */
    private final Writer w1;

    /**
     * The field {@code w2} contains the second writer.
     */
    private final Writer w2;

    /**
     * Creates a new object.
     * 
     * @param a the first writer
     * @param b the second writer
     */
    public MultiWriter(Writer a, Writer b) {

        w1 = a;
        w2 = b;
    }

public void close() throws IOException {

        w1.close();
        w2.close();
    }

public void configure(Configuration config) throws ConfigurationException {

        if (w1 instanceof Configurable) {
            ((Configurable) w1).configure(config);
        }
        if (w2 instanceof Configurable) {
            ((Configurable) w2).configure(config);
        }
    }

public void flush() throws IOException {

        w1.flush();
        w2.flush();
    }

public void print(String... args) throws IOException {

        w1.print(args);
        w2.print(args);
    }

public void println(String... args) throws IOException {

        w1.println(args);
        w2.println(args);
    }

public void write(int c) throws IOException {

        w1.write(c);
        w2.write(c);
    }
}
