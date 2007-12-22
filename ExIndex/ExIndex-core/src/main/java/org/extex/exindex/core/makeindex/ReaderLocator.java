package org.extex.exindex.core.makeindex;

import java.io.LineNumberReader;

import org.extex.exindex.lisp.parser.ResourceLocator;

/**
 * TODO gene: missing JavaDoc.
 * 
 */
public class ReaderLocator implements ResourceLocator {

    /**
     * The field <tt>resource</tt> contains the ...
     */
    private String resource;

    /**
     * The field <tt>reader</tt> contains the ...
     */
    private LineNumberReader reader;

    /**
     * Creates a new object.
     * 
     * @param resource
     * @param reader
     */
    public ReaderLocator(String resource, LineNumberReader reader) {

        super();
        this.resource = resource;
        this.reader = reader;
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
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getResource()
     */
    public String getResource() {

        return resource;
    }

}