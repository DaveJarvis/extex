/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.exdoc.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Traverse a set of directories and extract comment marked with the XML tag
 * <tt>doc</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Traverser {

    /**
     * The constant <tt>PATTERN_AUTHOR</tt> contains the ...
     */
    private static final Pattern PATTERN_AUTHOR =
            Pattern.compile("^ * @author <a href=\"mailto:(.*)\">(.*)</a>");

    /**
     * The constant <tt>PATTERN_CLASS</tt> contains the ...
     */
    private static final Pattern PATTERN_CLASS =
            Pattern.compile("^public.* (class|interface)\\s+(\\w+)");

    /**
     * The constant <tt>PATTERN_METHOD</tt> contains the ...
     */
    private static final Pattern PATTERN_METHOD =
            Pattern
                .compile("^\\s*(private|public|protected)[a-zA-Z_0-9 ]* (\\w+)\\(");

    /**
     * The constant <tt>PATTERN_METHOD_I</tt> contains the ...
     */
    private static final Pattern PATTERN_METHOD_I =
            Pattern.compile("^\\s*([a-zA-Z_0-9 ]*) (\\w+)\\(");

    /**
     * The constant <tt>PATTERN_PACKAGE</tt> contains the ...
     */
    private static final Pattern PATTERN_PACKAGE =
            Pattern.compile("^package[ ]*([^;]*);");

    /**
     * The main program for this class.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        try {
            new Traverser().run(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * The field <tt>authors</tt> contains the mapping from classes to authors.
     */
    private Map authors = new HashMap();

    /**
     * The field <tt>output</tt> contains the output directory.
     */
    private String output = null;

    /**
     * The field <tt>verbose</tt> contains the ...
     */
    private boolean verbose = false;

    /**
     * Getter for output.
     *
     * @return the output
     */
    protected String getOutput() {

        return output;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param msg the message
     */
    protected void info(final String msg) {

        if (verbose) {
            System.err.println(msg);
        }
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
     * TODO gene: missing JavaDoc
     *
     * @param key ...
     * @param author ...
     */
    protected void out(final String key, final Author author) {

        authors.put(key, author);
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param key ...
     * @param cs ...
     * @throws Exception ...
     */
    protected void out(final String key, final StringBuffer cs)
            throws Exception {

    }

    /**
     * Process a HTML file.
     *
     * @param f the HTML file
     *
     * @throws Exception in case of an error
     */
    private void processHtml(final File f) throws Exception {

        if (verbose) {
            info(f.toString());
        }

        LineNumberReader in = new LineNumberReader(new FileReader(f));
        Stack stack = new Stack();
        String line;

        while ((line = in.readLine()) != null) {
            int offset = line.indexOf("<doc");
            if (offset >= 0) {
                stack.add(scanDoc(in, line, offset));
            }
        }

        in.close();
        treatDocs(stack, f.getParent().toString().replace('\\', '/')
            .replaceFirst(".*/src/(java|main/java)/", "").replace('/', '.'));
    }

    /**
     * Process a Java file.
     *
     * @param f the Java file
     * @throws Exception in case of an error
     */
    private void processJava(final File f) throws Exception {

        if (verbose) {
            info(f.toString());
        }

        LineNumberReader in = new LineNumberReader(new FileReader(f));
        Stack stack = new Stack();
        String packageName = "";
        String className = "";
        boolean auth = true;
        Matcher m;
        String line;
        Pattern p = null;

        while ((line = in.readLine()) != null) {

            m = PATTERN_PACKAGE.matcher(line);
            if (m.find()) {
                packageName = line.substring(m.start(1), m.end(1));
                break;
            }
        }
        while ((line = in.readLine()) != null) {
            m = PATTERN_CLASS.matcher(line);
            if (m.find()) {
                className = line.substring(m.start(2), m.end(2));
                treatDocs(stack, packageName, className);
                p = (line.substring(m.start(2), m.end(2)).equals("class") //
                        ? PATTERN_METHOD
                        : PATTERN_METHOD_I);
                break;
            }

            if (auth) {
                m = PATTERN_AUTHOR.matcher(line);
                if (m.find()) {
                    auth = false;
                    out(f.toString(), new Author(//
                        line.substring(m.start(2), m.end(2)), //
                        line.substring(m.start(1), m.end(1))));
                    continue;
                }
            }

            int offset = line.indexOf("<doc");
            if (offset >= 0) {
                stack.add(scanDoc(in, line, offset));
            }
        }

        while ((line = in.readLine()) != null) {

            m = p.matcher(line);
            if (m.find()) {
                String methodName = line.substring(m.start(2), m.end(2));
                treatDocs(stack, packageName, className, methodName);
                continue;
            }

            int offset = line.indexOf("<doc");
            if (offset >= 0) {
                stack.add(scanDoc(in, line, offset));
            }
        }
        in.close();
        if (verbose) {
            info("");
        }
    }

    /**
     * Run with the command line arguments.
     *
     * @param args the command line arguments
     *
     * @throws Exception in case of an error
     */
    public void run(final String[] args) throws Exception {

        boolean opt = true;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (opt && arg.startsWith("-")) {
                if ("-".equals(arg)) {
                    opt = false;
                } else {
                    i = runOption(arg, args, i);
                }
            } else {
                File file = new File(arg);
                if (verbose) {
                    info("Scanning " + file.toString());
                }
                if (!file.exists()) {
                    throw new Exception(file + ": no such directory\n");
                }
                try {
                    traverse(file);
                } catch (IOException e) {
                    throw new Exception(file + ": " + e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param arg the current argument
     * @param args the list of all arguments
     * @param i the index of arg
     *
     * @return the index of the last arg processed
     *
     * @throws Exception in case of an error
     */
    protected int runOption(final String arg, final String[] args, final int i)
            throws Exception {

        int idx = i;
        if ("-quiet".startsWith(arg)) {
            verbose = false;
        } else if ("-verbose".startsWith(arg)) {
            verbose = true;
        } else if ("-output".startsWith(arg)) {
            output = args[++idx];
        } else {
            throw new Exception("*** Unknown option: " + arg);
        }
        return idx;
    }

    /**
     * Scan the input stream for until </doc> is found.
     *
     * @param in the stream to read from
     * @param startLine the line already read
     * @param offset the offset to cut off the beginning of each line
     *
     * @return the characters read
     *
     * @throws IOException in case of an error
     */
    private CharSequence scanDoc(final LineNumberReader in,
            final String startLine, final int offset) throws IOException {

        String line = startLine;
        StringBuffer sb = new StringBuffer();
        do {
            if (line.length() >= offset) {
                sb.append(line.substring(offset));
            }
            sb.append('\n');
            line = in.readLine();
        } while (in != null && line.indexOf("</doc>") < 0);

        sb.append("</doc>");
        sb.append('\n');
        return sb;
    }

    /**
     * Setter for output.
     *
     * @param output the output to set
     */
    public void setOutput(final String output) {

        this.output = output;
    }

    /**
     * Setter for verbose.
     *
     * @param verbose the verbose to set
     */
    public void setVerbose(final boolean verbose) {

        this.verbose = verbose;
    }

    /**
     * Traverse a directory structure and call the processing methods
     * when appropriate files are found.
     *
     * @param file the current directory
     *
     * @throws Exception in case of an error
     */
    private void traverse(final File file) throws Exception {

        String name = file.getName();

        if (name.equals("CVS") || name.startsWith(".")) {
            return;
        } else if (file.isDirectory()) {
            File[] list = file.listFiles();
            File f;

            for (int i = 0; i < list.length; i++) {
                f = list[i];
                traverse(f);
            }

        } else if (!file.isFile()) {
            // ignore non-files
        } else if (name.endsWith(".java")) {
            processJava(file);
        } else if (name.endsWith(".html")) {
            processHtml(file);
        }
    }

    /**
     * Process a list of package level docs.
     *
     * @param stack the list of docs
     * @param packageName the name of the package
     *   class level docs
     * @throws Exception in case of an error
     */
    private void treatDocs(final Stack stack, final String packageName)
            throws Exception {

        while (!stack.isEmpty()) {
            CharSequence cs = (CharSequence) stack.pop();
            StringBuffer sb = new StringBuffer("<doc");
            sb.append(" package=\"");
            sb.append(packageName);
            sb.append("\"");
            sb.append(cs.subSequence(4, cs.length()));
            out(packageName, sb);
        }
    }

    /**
     * Process a list of class level docs.
     *
     * @param stack the list of docs
     * @param packageName the name of the package
     * @param className the name of the class
     *   class level docs
     * @throws Exception in case of an error
     */
    private void treatDocs(final Stack stack, final String packageName,
            final String className) throws Exception {

        String name = packageName + "." + className;

        while (!stack.isEmpty()) {
            CharSequence cs = (CharSequence) stack.pop();
            StringBuffer sb = new StringBuffer("<doc");
            sb.append(" package=\"");
            sb.append(packageName);
            sb.append("\" class=\"");
            sb.append(className);
            sb.append("\"");
            sb.append(cs.subSequence(4, cs.length()));
            out(name, sb);
        }
    }

    /**
     * Process a list of method level docs.
     *
     * @param stack the list of docs
     * @param packageName the name of the package
     * @param className the name of the class
     * @param methodName the name if the method or <code>null</code> for
     *   class level docs
     * @throws Exception in case of an error
     */
    private void treatDocs(final Stack stack, final String packageName,
            final String className, final String methodName) throws Exception {

        String name = packageName + "." + className + "#" + methodName;

        while (!stack.isEmpty()) {
            CharSequence cs = (CharSequence) stack.pop();
            StringBuffer sb = new StringBuffer("<doc");
            sb.append(" package=\"");
            sb.append(packageName);
            sb.append("\" class=\"");
            sb.append(className);
            sb.append("\" method=\"");
            sb.append(methodName);
            sb.append("\"");
            sb.append(cs.subSequence(4, cs.length()));
            out(name, sb);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param msg the message
     */
    protected void warning(final String msg) {

        System.err.println(msg);
    }
}
