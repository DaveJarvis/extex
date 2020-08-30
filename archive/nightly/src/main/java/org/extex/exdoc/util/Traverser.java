/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Traverse a set of directories and extract comments marked with the XML tag
 * <tt>doc</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5413 $
 */
public class Traverser {

    /**
     * The constant <tt>PATTERN_AUTHOR</tt> contains the pattern for the
     * author.
     */
    private static final Pattern PATTERN_AUTHOR =
            Pattern.compile("@author\\s+<a href=\"mailto:(.*)\">(.*)</a>");

    /**
     * The constant <tt>PATTERN_CLASS</tt> contains the pattern for the class
     * or interface.
     */
    private static final Pattern PATTERN_CLASS =
            Pattern.compile("^public.* (class|interface)\\s+(\\w+)");

    /**
     * The constant <tt>PATTERN_METHOD</tt> contains the pattern for the
     * method of a class.
     */
    private static final Pattern PATTERN_METHOD =
            Pattern
                .compile("^\\s*(private|public|protected)[a-zA-Z_0-9 ]* (\\w+)\\(");

    /**
     * The constant <tt>PATTERN_METHOD_I</tt> contains the pattern for the
     * method of an interface.
     */
    private static final Pattern PATTERN_METHOD_I =
            Pattern.compile("^\\s*([a-zA-Z_0-9 ]*) (\\w+)\\(");

    /**
     * The constant <tt>PATTERN_PACKAGE</tt> contains the pattern for a
     * package.
     */
    private static final Pattern PATTERN_PACKAGE =
            Pattern.compile("^package[ ]*([^;]*);");

    /**
     * Add an attribute to a StringBuffer if the attribute value is not
     * <code>null</code>.
     * 
     * @param sb the target string buffer
     * @param attribute the name of the attribute
     * @param value the value of the attribute of <code>null</code>
     */
    private static void addAttribute(StringBuffer sb, String attribute,
            String value) {

        if (value != null) {
            sb.append(" ");
            sb.append(attribute);
            sb.append("=\"");
            sb.append(value);
            sb.append("\"");
        }
    }

    /**
     * Create an appropriate logger.
     * 
     * @return the logger
     */
    public static Logger createLogger() {

        Logger logger = Logger.getLogger(Traverser.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.WARNING);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new LogFormatter());
        logger.addHandler(consoleHandler);
        return logger;
    }

    /**
     * The main program for this class.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger logger = createLogger();
        try {
            new Traverser(logger).run(args);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }

    /**
     * The field <tt>authors</tt> contains the mapping from classes to
     * authors.
     */
    private Map<String, Author> authors = new HashMap<String, Author>();

    /**
     * The field <tt>builder</tt> contains the document builder for parsing
     * the XML files.
     */
    private DocumentBuilder builder;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger = null;

    /**
     * The field <tt>output</tt> contains the output directory.
     */
    private String output = null;

    /**
     * The field <tt>verbose</tt> contains the indicator for verbosity.
     */
    private boolean verbose = false;

    /**
     * Creates a new object.
     * 
     * @throws ParserConfigurationException in case of a parser error
     */
    public Traverser() throws ParserConfigurationException {

        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        builder.setEntityResolver(new ExdocEntityResolver());
    }

    /**
     * Creates a new object.
     * 
     * @param logger the logger
     * 
     * @throws ParserConfigurationException in case of a parser error
     */
    public Traverser(Logger logger) throws ParserConfigurationException {

        this();
        setLogger(logger);
    }

    /**
     * Getter for authors.
     * 
     * @return the authors
     */
    public Map<String, Author> getAuthors() {

        return authors;
    }

    /**
     * Getter for builder.
     * 
     * @return the builder
     */
    protected DocumentBuilder getBuilder() {

        return builder;
    }

    /**
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Getter for output.
     * 
     * @return the output
     */
    protected String getOutput() {

        if (output == null) {
            output = DIR_TARGET + "/exdoc";
        }
        return output;
    }

    /**
     * Log an informative message.
     * 
     * @param msg the message
     */
    protected void info(String msg) {

        if (verbose && logger != null) {
            logger.info(msg);
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
     * Register an author for a file.
     * 
     * @param key the name of the file
     * @param author the author
     */
    protected void out(String key, Author author) {

        authors.put(key, author);
    }

    /**
     * Register a doc entry.
     * 
     * @param key the key
     * @param cs the doc entry
     * 
     * @throws Exception in case of an error
     */
    protected void out(String key, StringBuffer cs) throws Exception {

        // to be overwritten in derived classes
    }

    /**
     * Process a HTML file. Most probably this is a file <tt>package.html</tt>.
     * 
     * @param f the HTML file
     * 
     * @throws Exception in case of an error
     */
    private void processHtml(File f) throws Exception {

        trace("html: " + f.toString());

        LineNumberReader in = new LineNumberReader(new FileReader(f));
        Stack<CharSequence> stack = new Stack<CharSequence>();
        String line;
        Matcher m;
        Author author = null;

        while ((line = in.readLine()) != null) {
            int offset = line.indexOf("<doc");
            if (offset >= 0) {
                stack.add(scanDoc(in, line, offset));
            } else if (author == null) {
                m = PATTERN_AUTHOR.matcher(line);
                if (m.find()) {
                    author = new Author(
                        line.substring(m.start(2), m.end(2)), 
                        line.substring(m.start(1), m.end(1)));
                    out(f.toString(), author);
                    continue;
                }
            }
        }

        in.close();
        treatDocs(stack, author, f.getParent().replace('\\', '/').replaceFirst(
            ".*/src/(java|main/java)/", "").replace('/', '.'));
        trace("html: done");
    }

    /**
     * Process a Java file.
     * 
     * @param f the Java file
     * 
     * @throws Exception in case of an error
     */
    private void processJava(File f) throws Exception {

        trace("java: " + f.toString());

        LineNumberReader in = new LineNumberReader(new FileReader(f));
        Stack<CharSequence> stack = new Stack<CharSequence>();
        String packageName = "";
        String className = "";
        Matcher m;
        String line;
        Pattern p = PATTERN_METHOD;
        Author author = null;

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
                treatDocs(stack, author, packageName, className);
                p = (line.substring(m.start(2), m.end(2)).equals("class") 
                        ? PATTERN_METHOD
                        : PATTERN_METHOD_I);
                break;
            }

            if (author == null) {
                m = PATTERN_AUTHOR.matcher(line);
                if (m.find()) {
                    author = new Author(
                        line.substring(m.start(2), m.end(2)), 
                        line.substring(m.start(1), m.end(1)));
                    out(f.toString(), author);
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
                treatDocs(stack, author, packageName, className, methodName);
                continue;
            }

            int offset = line.indexOf("<doc");
            if (offset >= 0) {
                stack.add(scanDoc(in, line, offset));
            }
        }
        in.close();
        trace("java: done");
    }

    /**
     * Process an XML file.
     * 
     * @param file the file
     * 
     * @throws IOException in case of an I/O error
     */
    protected void processXml(File file) throws IOException {

        // to be overwritten in derived classes
    }

    /**
     * Run with the command line arguments. A value of <code>null</code> is
     * ignored.
     * 
     * @param args the command line arguments
     * 
     * @throws Exception in case of an error
     */
    public void run(String[] args) throws Exception {

        boolean opt = true;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg == null) {
                // ignored
            } else if (opt && arg.startsWith("-")) {
                if ("-".equals(arg)) {
                    opt = false;
                } else {
                    i = runOption(arg, args, i);
                    opt = true;
                }
            } else {
                File file = new File(arg);
                info("Scanning " + file.toString());
                if (!file.exists()) {
                    throw new Exception(file + ": no such directory\n");
                }
                try {
                    traverse(file);
                } catch (IOException e) {
                    throw new Exception(file + ": " + e.getLocalizedMessage());
                }
                opt = true;
            }
        }
    }

    /**
     * Process an option starting with a minus in an argument list and return
     * the index of the last argument processed.
     * 
     * @param arg the current argument
     * @param args the list of all arguments
     * @param i the index of the current argument
     * 
     * @return the index of the last argument processed
     * 
     * @throws Exception in case of an unknown option
     */
    protected int runOption(String arg, String[] args, int i) throws Exception {

        int idx = i;
        if ("-quiet".startsWith(arg)) {
            verbose = false;
            logger.setLevel(Level.SEVERE);
        } else if ("-verbose".startsWith(arg)) {
            verbose = true;
            logger.setLevel(Level.INFO);
        } else if ("-debug".startsWith(arg)) {
            verbose = true;
            logger.setLevel(Level.FINE);
        } else if ("-output".startsWith(arg)) {
            output = args[++idx];
        } else {
            throw new Exception("Unknown option: " + arg);
        }
        return idx;
    }

    /**
     * Scan the input stream until &lt;/doc&gt; is found.
     * 
     * @param in the stream to read from
     * @param startLine the line already read
     * @param offset the offset to cut off the beginning of each line
     * 
     * @return the characters read
     * 
     * @throws IOException in case of an error
     */
    private CharSequence scanDoc(LineNumberReader in, String startLine,
            int offset) throws IOException {

        String line = startLine;
        StringBuffer sb = new StringBuffer();
        do {
            if (line.length() >= offset) {
                sb.append(line.substring(offset));
            }
            sb.append('\n');
            line = in.readLine();
        } while (line != null && line.indexOf("</doc>") < 0);

        sb.append("</doc>");
        sb.append('\n');
        return sb;
    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
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

    /**
     * Log an informative message.
     * 
     * @param msg the message
     */
    protected void trace(String msg) {

        if (verbose && logger != null) {
            logger.fine(msg);
        }
    }

    /**
     * Traverse a directory structure and call the processing methods when
     * appropriate files are found.
     * 
     * @param file the current directory
     * 
     * @throws Exception in case of an error
     */
    private void traverse(File file) throws Exception {

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
        } else if (name.endsWith(".xml")) {
            processXml(file);
        }
    }

    /**
     * Process a list of package level docs.
     * 
     * @param stack the list of docs
     * @param author the author
     * @param packageName the name of the package class level docs
     * 
     * @throws Exception in case of an error
     */
    private void treatDocs(Stack<? extends CharSequence> stack, Author author,
            String packageName) throws Exception {

        while (!stack.isEmpty()) {
            CharSequence cs = stack.pop();
            StringBuffer sb = new StringBuffer("<doc");
            addAttribute(sb, "package", packageName);
            if (author != null) {
                addAttribute(sb, "authorName", author.getName());
                addAttribute(sb, "authorEmail", author.getEmail());
            }
            sb.append(cs.subSequence(4, cs.length()));
            out(packageName, sb);
        }
    }

    /**
     * Process a list of class level docs.
     * 
     * @param stack the list of docs
     * @param author the author
     * @param packageName the name of the package
     * @param className the name of the class level docs
     * 
     * @throws Exception in case of an error
     */
    private void treatDocs(Stack<? extends CharSequence> stack, Author author,
            String packageName, String className) throws Exception {

        String name = packageName + "." + className;

        while (!stack.isEmpty()) {
            CharSequence cs = stack.pop();
            StringBuffer sb = new StringBuffer("<doc");
            addAttribute(sb, "package", packageName);
            addAttribute(sb, "class", className);
            if (author != null) {
                addAttribute(sb, "authorName", author.getName());
                addAttribute(sb, "authorEmail", author.getEmail());
            }
            sb.append(cs.subSequence(4, cs.length()));
            out(name, sb);
        }
    }

    /**
     * Process a list of method level docs.
     * 
     * @param stack the list of docs
     * @param author the author
     * @param packageName the name of the package
     * @param className the name of the class
     * @param methodName the name if the method or <code>null</code> for class
     *        level docs
     * @throws Exception in case of an error
     */
    private void treatDocs(Stack<? extends CharSequence> stack, Author author,
            String packageName, String className, String methodName)
            throws Exception {

        String name = packageName + "." + className + "#" + methodName;

        while (!stack.isEmpty()) {
            CharSequence cs = stack.pop();
            StringBuffer sb = new StringBuffer("<doc");
            addAttribute(sb, "package", packageName);
            addAttribute(sb, "class", className);
            addAttribute(sb, "method", methodName);
            if (author != null) {
                addAttribute(sb, "authorName", author.getName());
                addAttribute(sb, "authorEmail", author.getEmail());
            }
            sb.append(cs.subSequence(4, cs.length()));
            out(name, sb);
        }
    }

    /**
     * Issue a warning.
     * 
     * @param msg the message
     */
    protected void warning(String msg) {

        if (logger != null) {
            logger.warning(msg);
        }
    }

}
