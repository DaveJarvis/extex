/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.groovy;

import java.io.IOException;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.io.Writer;

/**
 * This class provides a base for derived implementations of a bibliography
 * style.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Style {

    /**
     * The field <tt>bibDB</tt> contains the database.
     */
    private DB bibDB;

    /**
     * The field <tt>bibWriter</tt> contains the writer.
     */
    private Writer bibWriter;

    /**
     * The field <tt>bibProcessor</tt> contains the processor.
     */
    private Processor bibProcessor;

    /**
     * Creates a new object.
     * 
     * @param bibDB the database
     * @param bibWriter the writer
     * @param bibProcessor the processor
     */
    public Style(DB bibDB, Writer bibWriter, Processor bibProcessor) {

        this.bibDB = bibDB;
        this.bibWriter = bibWriter;
        this.bibProcessor = bibProcessor;
    }

    /**
     * Define a new sting value.
     * 
     * @param name the name
     * @param value the value
     */
    public void defineString(String name, String value) {

        bibDB.storeString(name, new Value(new VString(value)));
    }

    /**
     * Getter for the database.
     * 
     * @return the database
     */
    public DB getDB() {

        return bibDB;
    }

    /**
     * Produce a newline in the output.
     * 
     * @throws IOException in case of an error
     */
    public void newline() throws IOException {

        bibWriter.println();
    }

    /**
     * Issue a warning message.
     * 
     * @param message the message
     */
    public void warning(String message) {

        bibProcessor.warning(message);
    }

    /**
     * Write some strings to the output stream.
     * 
     * @param values the arguments
     * 
     * @throws IOException in case of an error
     */
    public void write(String... values) throws IOException {

        bibWriter.print(values);
    }

    /**
     * Write some strings to the output stream and terminate it with a newline.
     * 
     * @param values the arguments to print
     * 
     * @throws IOException in case of an error
     */
    public void writeln(String... values) throws IOException {

        bibWriter.println(values);
    }

}
