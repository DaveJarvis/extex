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

package org.extex.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.doc.util.Doc;
import org.extex.doc.util.DocParser;
import org.extex.doc.writer.DocWriter;
import org.extex.doc.writer.html.HtmlDocWriter;
import org.extex.doc.writer.latex.LatexDocWriter;
import org.w3c.dom.Node;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DocMaker extends DocParser {

    /**
     * TODO gene: missing JavaDoc.
     */
    enum Output {
        /**
         * The field <tt>LATEX</tt> contains the ...
         */
        LATEX {

            @Override
            public DocWriter getWriter() {

                return new LatexDocWriter();
            }
        },
        /**
         * The field <tt>HTML</tt> contains the ...
         */
        HTML {

            @Override
            public DocWriter getWriter() {

                return new HtmlDocWriter();
            }
        },
        /**
         * The field <tt>VALIDATE</tt> contains the empty method.
         */
        VALIDATE {

            @Override
            public DocWriter getWriter() {

                return new DocWriter() {

                    public void execute(Map<String, Doc> map) {

                        //
                    }

                    public void setTarget(String targetDir) {

                        //
                    }
                };
            }
        },
        /**
         * The field <tt>TRACE</tt> contains the tracer.
         */
        TRACE {

            @Override
            public DocWriter getWriter() {

                return new DocWriter() {

                    public void execute(Map<String, Doc> map) {

                        for (String k : map.keySet()) {
                            Doc doc = map.get(k);
                            System.err.print(k);
                            System.err.print(" -> ");
                            System.err.println(doc.get("name"));
                        }
                    }

                    public void setTarget(String targetDir) {

                        //
                    }
                };
            }
        };

        /**
         * Method to get the doc writer.
         * 
         * @return the doc writer
         */
        public abstract DocWriter getWriter();
    };

    /**
     * Command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DocMaker docMaker = new DocMaker();

        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (a.equals("")) {
                continue;
            } else if (!a.startsWith("-")) {
                docMaker.addDir(a);
            } else if (a.equals("-")) {
                //
            } else if ("-verbose".startsWith(a)) {
                docMaker.setVerbose(true);
            } else if ("-output".startsWith(a)) {
                if (++i >= args.length) {
                    docMaker.error("missing argument for ", a);
                    return;
                }
                try {
                    docMaker.setOutput(args[i]);
                } catch (IllegalArgumentException e) {
                    docMaker.error("unkown mode: ", args[i]);
                    return;
                }
            } else if ("-classpath".startsWith(a) || "-cp".startsWith(a)) {
                if (++i >= args.length) {
                    docMaker.error("missing argument for ", a);
                    return;
                }
                for (String x : args[i].split(":")) {
                    docMaker.addDir(x);
                }
            } else if ("-targetDir".startsWith(a)) {
                if (++i >= args.length) {
                    docMaker.error("missing argument for ", a);
                    return;
                }
                docMaker.setTarget(args[i]);
            } else {
                docMaker.error("unkown argument: ", a);
                return;
            }
        }

        try {
            docMaker.execute();
        } catch (FileNotFoundException e) {
            docMaker.error("File not found: ", e.getMessage());
        } catch (IOException e) {
            docMaker.error(e.toString());
        }
    }

    /**
     * The field <tt>output</tt> contains the output mode.
     */
    private Output output = Output.TRACE;

    /**
     * The field <tt>path</tt> contains the directories to analyze.
     */
    private List<String> path = new ArrayList<String>();

    /**
     * The field <tt>verbose</tt> contains the indicator for verbosity.
     */
    private boolean verbose = false;

    /**
     * The field <tt>srcDir</tt> contains the source directory.
     */
    private String srcDir = "src/main/java";

    /**
     * The field <tt>configDir</tt> contains the configuration directory.
     */
    private String configDir = "src/main/resources/config";

    /**
     * The field <tt>unitDir</tt> contains the unit directory.
     */
    private String unitDir = "src/main/resources/config/unit";

    /**
     * The field <tt>targetDir</tt> contains the target directory.
     */
    private String targetDir = ".";

    /**
     * Creates a new object.
     */
    public DocMaker() {

        super();
    }

    /**
     * Add a directory to the class path.
     * 
     * @param dir the directory to add
     */
    public void addDir(String dir) {

        path.add(dir);
    }

    /**
     * Print an error message.
     * 
     * @param msg the messages
     */
    protected void error(String... msg) {

        for (String s : msg) {
            System.err.print(s);
        }
        System.err.println();
    }

    /**
     * Perform all actions according to the settings made before.
     * 
     * @throws IOException in case of an I/O error
     */
    public void execute() throws IOException {

        Map<String, Node> unitMap = new HashMap<String, Node>();
        Map<String, Node> configMap = new HashMap<String, Node>();

        for (String x : path) {
            traverseUnits(new File(x + "/" + unitDir), unitMap);
            traverseConfigs(new File(x + "/" + configDir), configMap);
        }

        Map<String, Doc> docMap = new HashMap<String, Doc>();

        for (String x : path) {
            traverse(new File(x + "/" + srcDir), docMap);
        }

        DocWriter writer = output.getWriter();
        writer.setTarget(targetDir);
        writer.execute(docMap);
    }

    /**
     * Getter for verbose.
     * 
     * @return the verbose
     */
    public boolean isVerbose() {

        return verbose;
    }

    /**
     * Print an error message.
     * 
     * @param msg the messages
     */
    protected void message(String... msg) {

        System.out.println(msg);
    }

    /**
     * Setter for the output mode.
     * 
     * @param out the output mode
     */
    public void setOutput(String out) {

        output = Output.valueOf(out);
    }

    /**
     * Setter for the target directory.
     * 
     * @param target the target directory
     */
    public void setTarget(String target) {

        this.targetDir = target;
    }

    /**
     * Setter for verbose.
     * 
     * @param verbose the verbose to set
     */
    public void setVerbose(boolean verbose) {

        this.verbose = verbose;
    }

    /**
     * Recursively traverse a directory tree and process the files found.
     * 
     * @param f the directory
     * @param map the map to collect the results in
     * 
     * @throws IOException in case of an I/O error
     */
    private void traverse(File f, Map<String, Doc> map) throws IOException {

        String name = f.getName();
        if ("RCS".equals(name) || name.startsWith(".")) {
            return;
        }

        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                traverse(file, map);
            }
        } else if (name.endsWith(".java")) {
            List<Doc> docs = parse(f);
            for (Doc d : docs) {
                map.put(d.getLocation(), d);
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param f the directory to scan for configurations
     * @param configMap
     * 
     * @throws FileNotFoundException in case that the given directory can not be
     *         read
     */
    private void traverseConfigs(File f, Map<String, Node> map)
            throws FileNotFoundException {

        if (!f.isDirectory()) {
            throw new FileNotFoundException(f.toString());
        }

        File[] files = f.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.getName().endsWith(".xml")) {

            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param file
     * @param unitMap
     */
    private void traverseUnits(File file, Map<String, Node> unitMap) {

        // TODO gene: traverseUnits unimplemented

    }

}
