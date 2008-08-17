/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.doctools.pomCollector;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.extex.doctools.MissingArgumentException;
import org.extex.doctools.UnknownArgumentException;
import org.xml.sax.SAXException;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Main {

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            new Main().run(args);
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(-1);
        }
    }

    /**
     * The field <tt>bases</tt> contains the list of base directories to
     * consider.
     */
    private List<String> bases = new ArrayList<String>();

    /**
     * The field <tt>output</tt> contains the output file name or
     * <code>null</code> for stdout.
     */
    private String output = null;

    /**
     * The field <tt>omit</tt> contains the list of omitted files and
     * directories.
     */
    private List<String> omit = new ArrayList<String>();

    /**
     * The field <tt>DIR_FILTER</tt> contains the filter to select only
     * directories.
     */
    private static final FileFilter DIR_FILTER = new FileFilter() {

        public boolean accept(File f) {

            return f.isDirectory();
        }
    };

    /**
     * The field <tt>xslt</tt> contains the name of the xslt resource.
     */
    private String xslt = Main.class.getName().replace('.', '/') + ".xsl";

    /**
     * Add a base directory.
     * 
     * @param base the base directory
     */
    public void addBase(String base) {

        bases.add(base);
    }

    /**
     * Getter for the output.
     * 
     * @return the output
     */
    public String getOutput() {

        return output;
    }

    /**
     * Getter for the xslt.
     * 
     * @return the xslt
     */
    public String getXslt() {

        return xslt;
    }

    /**
     * Get the next argument and complain if none is found.
     * 
     * @param args the array of arguments
     * @param i the index of the next argument
     * 
     * @return the next argument
     * 
     * @throws MissingArgumentException in case of a missing argument
     */
    private String nextArgument(String[] args, int i)
            throws MissingArgumentException {

        if (i >= args.length) {
            throw new MissingArgumentException(args[i - 1]);
        }
        return args[i];
    }

    /**
     * Add a file to be omitted.
     * 
     * @param name the name
     */
    public void omit(String name) {

        omit.add(name);
    }

    /**
     * Process a POM by adding its contents to the buffer. A xml processing
     * instruction in the first line is discarded.
     * 
     * @param pom the POM file
     * @param buffer the target buffer
     * 
     * @throws IOException in case of an I/O error
     */
    private void processPom(File pom, StringBuilder buffer) throws IOException {

        LineNumberReader r = new LineNumberReader(new FileReader(pom));

        try {
            String line = r.readLine();
            if (line != null) {
                if (!line.startsWith("<?xml ")) {
                    buffer.append(line);
                }
            }

            for (int c = r.read(); c >= 0; c = r.read()) {
                buffer.append((char) c);
            }
        } finally {
            r.close();
        }
    }

    /**
     * Traverse a set of directory hierarchies collecting poms and store the
     * transformed result in a given output file. If no output file has been
     * given then the result is written to stdout.
     * 
     * @throws IOException in case of an I/O error
     * @throws SAXException in case of a SAX exception
     * @throws ParserConfigurationException in case of a parser configuration
     *         exception
     * @throws TransformerFactoryConfigurationError in case of a transformer
     *         factory configuration exception
     * @throws TransformerException in case of a transformer exception
     */
    public void run()
            throws IOException,
                ParserConfigurationException,
                SAXException,
                TransformerFactoryConfigurationError,
                TransformerException {

        Writer writer =
                output == null
                        ? new OutputStreamWriter(System.out)
                        : new FileWriter(output);
        try {
            run(writer);
        } finally {
            writer.flush();
            if (output != null) {
                writer.close();
            }
        }
    }

    /**
     * Traverse a directory tree and collect the POMs in a buffer.
     * 
     * @param file the file to consider
     * @param buffer the output writer
     * 
     * @throws IOException in case of an I/O error
     */
    private void run(File file, StringBuilder buffer) throws IOException {

        if (omit.contains(file.getName())) {
            return;
        }

        File pom = new File(file, "pom.xml");
        if (pom.exists()) {
            processPom(pom, buffer);

            for (File f : file.listFiles(DIR_FILTER)) {
                run(f, buffer);
            }
        }
    }

    /**
     * Process a list of strings like the command line options.
     * 
     * @param args the arguments
     * 
     * @throws IOException in case of an I/O error
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     * @throws SAXException
     * @throws MissingArgumentException
     * @throws UnknownArgumentException
     */
    public void run(String[] args)
            throws IOException,
                ParserConfigurationException,
                TransformerFactoryConfigurationError,
                SAXException,
                TransformerException,
                MissingArgumentException,
                UnknownArgumentException {

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                if ("-".equals(arg)) {
                    bases.add(nextArgument(args, ++i));
                } else if ("-output".startsWith(arg)) {
                    setOutput(nextArgument(args, ++i));
                } else if ("-omit".startsWith(arg)) {
                    omit.add(nextArgument(args, ++i));
                } else if ("-xsl".startsWith(arg)) {
                    xslt = nextArgument(args, ++i);
                } else {
                    throw new UnknownArgumentException(arg);
                }
            } else {
                bases.add(arg);
            }
        }

        run();
    }

    /**
     * Traverse a set of directory trees collecting poms and write the
     * transformed result to the given writer.
     * 
     * @param writer the writer for the output
     * 
     * @throws IOException in case of an I/O error
     * @throws TransformerException in case of an error in the transformer
     */
    public void run(Writer writer) throws IOException, TransformerException {

        InputStream in = getClass().getClassLoader().getResourceAsStream(xslt);
        if (in == null) {
            throw new FileNotFoundException(xslt);
        }
        Transformer transformer;
        try {
            transformer =
                    TransformerFactory.newInstance().newTransformer(
                        new StreamSource(in));
        } finally {
            in.close();
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("<top>");

        for (String base : bases) {
            File dir = new File(base);
            if (dir.isDirectory()) {
                run(dir, buffer);
            }
        }

        buffer.append("</top>");

        transformer.transform(
            //
            new StreamSource(new StringReader(buffer.toString())),
            new StreamResult(writer));
    }

    /**
     * Setter for the output. The empty string and the string containing a '-'
     * only are treated as <code>null</code>.
     * 
     * @param output the output to set
     */
    public void setOutput(String output) {

        this.output = "".equals(output) || "-".equals(output) ? null : output;
    }

    /**
     * Setter for the xslt.
     * 
     * @param xslt the xslt to set
     */
    public void setXslt(String xslt) {

        this.xslt = xslt;
    }

}
