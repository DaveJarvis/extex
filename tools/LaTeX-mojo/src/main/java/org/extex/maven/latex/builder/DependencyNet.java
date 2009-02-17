/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.maven.latex.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.artifact.LatexArtifact;
import org.extex.maven.latex.builder.exception.MakeException;

/**
 * This class represents a net of artifacts and dependencies. The characteristic
 * is that there exist exactly one target to be build.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DependencyNet {

    /**
     * The field <tt>map</tt> contains the mapping from names to the associated
     * artifacts.
     */
    private Map<String, Artifact> map;

    /**
     * The field <tt>context</tt> contains the environment to store additional
     * information.
     */
    private Map<String, String> context;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger = Logger.getLogger(DependencyNet.class.getName());

    /**
     * The field <tt>master</tt> contains the master.
     */
    private Artifact master = null;

    /**
     * The field <tt>target</tt> contains the target.
     */
    private Artifact target = null;

    /**
     * Creates a new object.
     */
    public DependencyNet() {

        map = new HashMap<String, Artifact>();
        context = new HashMap<String, String>();
        LatexArtifact.setup(this);
    }

    /**
     * Add an artifact.
     * 
     * @param a the artifact to add
     * 
     * @return the old artifact stored under the same name or <code>null</code>
     */
    public Artifact addArtifact(Artifact a) {

        return map.put(a.getFile().getAbsoluteFile().toString(), a);
    }

    /**
     * Build the default target of the net.
     * 
     * @param simulate the indicator whether the commands should really be
     *        executed
     * 
     * @throws MakeException in case of an error
     */
    public void build(boolean simulate) throws MakeException {

        build(target.getFile().getAbsoluteFile().toString(), simulate);
    }

    /**
     * Build a target.
     * 
     * @param name the name of the target to build
     * @param simulate the indicator whether the commands should really be
     *        executed
     * 
     * @throws MakeException in case of an error
     */
    public void build(String name, boolean simulate) throws MakeException {

        Artifact a = map.get(name);
        if (a == null) {
            throw new MakeException(name + ": unknown target");
        }

        do {
            logger.info("building " + a.toString());
        } while (!a.build(context, logger, simulate));

        // LaTeX Warning: There were undefined references.
        // LaTeX Warning: Citation `abc' on page 1 undefined on input line 3.
        // LaTeX Warning: Label(s) may have changed. Rerun to get
        // cross-references right.
        // No file document2.ind.
        // Output written on target/document2.dvi (1 page, 280 bytes).
    }

    /**
     * Getter for a context item.
     * 
     * @param key the key
     * 
     * @return the context item for the given key or <code>null</code> if none
     *         exists
     */
    public String context(String key) {

        return context.get(key);
    }

    /**
     * Setter for a context item. If an item already exists for the given key
     * then it is overwritten. If none exists the a new one is created.
     * 
     * @param key the key
     * @param value the new value for the new item
     */
    public void context(String key, String value) {

        context.put(key, value);
    }

    /**
     * Set the fallback for a context item. This means the value is assigned if
     * the key does not have an associated value already.
     * 
     * @param key the key
     * @param value the fallback value for the item
     */
    public void contextFallback(String key, String value) {

        if (context.get(key) == null) {
            context.put(key, value);
        }
    }

    /**
     * Getter for an artifact.
     * 
     * @param file the file
     * 
     * @return the artifact or <code>null</code> if none is defined
     */
    public Artifact findArtifact(File file) {

        return map.get(file.getAbsoluteFile().toString());
    }

    /**
     * Find a resource.
     * 
     * @param fileName the file name
     * @param extensions the extensions
     * @param base the base file name
     * 
     * @return the full file
     * 
     * @throws FileNotFoundException in case no resource could be found
     */
    public File findFile(String fileName, String extensions, File base)
            throws FileNotFoundException {

        File file = searchFile(fileName, extensions, base);
        if (file == null) {
            throw new FileNotFoundException(fileName);
        }
        return file;
    }

    /**
     * Getter for an artifact.
     * 
     * @param file the file
     * 
     * @return the artifact or a new one if none is defined
     * 
     * @throws IOException in case of an I/O error
     */
    public Artifact getArtifact(File file) throws IOException {

        String key = file.getAbsoluteFile().toString();
        Artifact a = map.get(key);
        if (a == null) {
            a = new Artifact(file.getAbsoluteFile());
            map.put(key, a);
        }
        return a;
    }

    /**
     * Getter for a derived artifact from the target.
     * 
     * @param extension the extension
     * 
     * @return the artifact or a new one if none is defined
     * 
     * @throws IOException in case of an I/O error
     */
    public Artifact getDerivedTargetArtifact(String extension)
            throws IOException {

        return getArtifact(target.derivedFile(extension));
    }

    /**
     * Getter for the logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Getter for the master document.
     * 
     * @return the master document
     */
    public Artifact getMaster() {

        return master;
    }

    /**
     * Getter for the target.
     * 
     * @return the target
     * 
     * @throws IOException in case of an I/O error
     */
    public Artifact getTarget() throws IOException {

        if (target == null) {
            File masterFile = master.getFile();
            String name =
                    masterFile.getName().replaceAll("\\.[a-zA-Z0-9_]*", "")
                            + "." + context.get("target.format");
            File file = new File(masterFile.getParentFile(), name);
            target = new Artifact(file);
        }
        return target;
    }

    /**
     * Print this net.
     * 
     * @param w the writer
     * @param prefix the prefix
     */
    public void print(PrintWriter w, String prefix) {

        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            Artifact a = map.get(key);
            if (a != null) {
                a.print(w, prefix);
            }
        }
    }

    /**
     * Find a resource.
     * 
     * @param fileName the file name
     * @param extensions the extensions
     * @param base the base file name
     * 
     * @return the full file or <code>null</code>
     */
    public File searchFile(String fileName, String extensions, File base) {

        String name = fileName.replaceAll("\\.[a-zA-Z0-9_]*", "");

        for (String ext : extensions.split(":")) {
            File f = new File(base.getParentFile(), name + ext);
            if (f.exists()) {
                return f;
            }
        }
        return null;
    }

    /**
     * Setter for the logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for the master.
     * <p>
     * Note: This method must be invoked exactly once.
     * </p>
     * 
     * @param file the master file; this can not be <code>null</code>
     * 
     * @throws IllegalStateException if this method is invoked a second time
     * @throws IllegalArgumentException if the file is <code>null</code> or the
     *         output directory is no directory
     * @throws IOException in case of an I/O error
     */
    public void wire(File file) throws IllegalArgumentException, IOException {

        if (master != null) {
            throw new IllegalStateException("master already set");
        }
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        File directory = new File(context.get("output.directory"));
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.toString()
                    + ": not a directory");
        }

        master = new LatexArtifact(file);
        addArtifact(master);

        String format = context.get("target.format");
        target = FileFormat.valueOf(format.toUpperCase()).makeTarget(//
            directory, file.getName(), this);

        master.analyze(this);
    }

}
