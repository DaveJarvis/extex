
package org.extex.exindex.core.makeindex;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.extex.exindex.lisp.parser.ResourceLocator;

/**
 * This resource locator encapsulates a line number reader and can be used to
 * read single characters from the reader.
 * 
 */
public class ReaderLocator implements ResourceLocator {

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * The field <tt>reader</tt> contains the reader.
     */
    private LineNumberReader reader;

    /**
     * Creates a new object.
     * 
     * @param resource the resource
     * @param reader the reader
     */
    public ReaderLocator(String resource, Reader reader) {

        super();
        this.resource = resource;
        this.reader = new LineNumberReader(reader);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getLineNumber()
     */
    public String getLineNumber() {

        return Integer.toString(reader.getLineNumber());
    }

    /**
     * Getter for reader.
     * 
     * @return the reader
     */
    public LineNumberReader getReader() {

        return reader;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getResource()
     */
    public String getResource() {

        return resource;
    }

    /**
     * Read a character from the stream.
     * 
     * @return the next character or -1 at eof
     * 
     * @throws IOException in case of an error
     */
    public int read() throws IOException {

        return reader.read();
    }

}
