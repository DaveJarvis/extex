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

package org.extex.exdoc.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.Path;
import org.extex.exdoc.ExDocHtml;
import org.extex.exdoc.ExDocTeX;
import org.extex.exdoc.ExDocXml;

/**
 * Provide an Ant task for the Exdoc functionality.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ExdocTask extends Task {

    /**
     * The field {@code format} contains the format to use.
     */
    private String format = "xml";

    /**
     * The field {@code output} contains the output attribute.
     */
    private String output = "exdoc";

    /**
     * The field {@code roots} contains the list of root directories to
     * consider.
     */
    private List<String> roots = new ArrayList<String>();

    /**
     * The field {@code verbose} contains the verbosity indicator.
     */
    private boolean verbose = false;

    /**
     * Add an inner dir set named roots.
     * 
     * @param dirSet the included dir set
     */
    public void addConfigured(DirSet dirSet) {

        DirectoryScanner ds = dirSet.getDirectoryScanner(getProject());
        File basedir = ds.getBasedir();
        String[] files = ds.getIncludedFiles();
        for (int i = 0; i < files.length; i++) {
            roots.add(new File(basedir, files[i]).toString());
        }

    }

    /**
     * Add an inner dir set named roots.
     * 
     * @param path the included dir set
     */
    public void addConfigured(Path path) {

        log("got path");
        for (String s : path.list()) {
            roots.add(s);
        }
    }

    /**
     * Run the task from within an ant build file.
     * 
     * @throws BuildException in case of an error
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        ExDocXml exdoc = null;
        if ("xml".equals(format)) {
            exdoc = makeExDocXml();
        } else if ("html".equals(format)) {
            exdoc = makeExDocHtml();
        } else if ("tex".equals(format)) {
            exdoc = makeExDocTeX();
        } else {
            throw new BuildException("format `" + format + "' is not supported");
        }
        int size = roots.size();
        if (size == 0) {
            throw new BuildException(
                "missing base directories; please use an embedded <dirset>");
        }
        String[] args = new String[size + 1];
        args[0] = "-";
        for (int i = 0; i < size; i++) {
            args[i + 1] = roots.get(i);
        }
        exdoc.setVerbose(verbose);
        exdoc.setOutput(output);
        try {
            exdoc.run(args);
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    /**
     * Create an Exdoc object for HTML translation.
     * 
     * @return Exdoc object
     */
    private ExDocXml makeExDocHtml() {

        ExDocXml exdoc = null;
        try {
            exdoc = new ExDocHtml() {

                /**
                 * Log an info message.
                 * 
                 * @param msg the message
                 * 
                 * @see org.extex.exdoc.util.Traverser#info(java.lang.String)
                 */
                @Override
                protected void info(String msg) {

                    if (verbose) {
                        log(msg);
                    }
                }

                /**
                 * Log a message.
                 * 
                 * @param msg the message
                 * 
                 * @see org.extex.exdoc.util.Traverser#warning(java.lang.String)
                 */
                @Override
                protected void warning(String msg) {

                    log(msg);
                }
            };
        } catch (ParserConfigurationException e) {
            throw new BuildException(e);
        } catch (TransformerConfigurationException e) {
            throw new BuildException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new BuildException(e);
        } catch (IOException e) {
            throw new BuildException(e);
        }
        return exdoc;
    }

    /**
     * Create an Exdoc object for TeX translation.
     * 
     * @return Exdoc object
     */
    private ExDocXml makeExDocTeX() {

        ExDocXml exdoc = null;
        try {
            exdoc = new ExDocTeX() {

            @Override
                protected void info(String msg) {

                    if (verbose) {
                        log(msg);
                    }
                }

            @Override
                protected void warning(String msg) {

                    log(msg);
                }
            };
        } catch (ParserConfigurationException e) {
            throw new BuildException(e);
        } catch (TransformerConfigurationException e) {
            throw new BuildException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new BuildException(e);
        } catch (IOException e) {
            throw new BuildException(e);
        }
        return exdoc;
    }

    /**
     * Create an Exdoc object for XML translation.
     * 
     * @return Exdoc object
     */
    private ExDocXml makeExDocXml() {

        ExDocXml exdoc = null;
        try {
            exdoc = new ExDocXml() {

                /**
                 * Log an info message.
                 * 
                 * @param msg the message
                 * 
                 * @see org.extex.exdoc.util.Traverser#info(java.lang.String)
                 */
                @Override
                protected void info(String msg) {

                    if (verbose) {
                        log(msg);
                    }
                }

                /**
                 * Log a message.
                 * 
                 * @param msg the message
                 * 
                 * @see org.extex.exdoc.util.Traverser#warning(java.lang.String)
                 */
                @Override
                protected void warning(String msg) {

                    log(msg);
                }
            };
        } catch (ParserConfigurationException e) {
            throw new BuildException(e);
        }
        return exdoc;
    }

    /**
     * Setter for format.
     * 
     * @param format the format to set
     */
    public void setFormat(String format) {

        this.format = format;
    }

    /**
     * Setter for output.
     * 
     * @param output the output to set
     */
    public void setOutput(String output) {

        this.output = output;
    }

    /**
     * Setter for verbose.
     * 
     * @param verbose the verbose to set
     */
    public void setVerbose(boolean verbose) {

        this.verbose = verbose;
    }

}
