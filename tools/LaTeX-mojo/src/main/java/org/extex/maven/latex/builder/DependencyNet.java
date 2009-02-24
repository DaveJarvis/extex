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
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.artifact.latex.LaTeXAnalyzer;
import org.extex.maven.latex.builder.artifact.latex.LaTeXMacroAnalyzer;
import org.extex.maven.latex.builder.artifact.latex.State;
import org.extex.maven.latex.builder.exception.MakeException;

/**
 * This class represents a net of artifacts and dependencies. The characteristic
 * is that there exist exactly one target to be build.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DependencyNet implements State {

    /**
     * The field <tt>map</tt> contains the mapping from names to the associated
     * artifacts.
     */
    private Map<String, Artifact> map;

    /**
     * The field <tt>parameters</tt> contains the parameters.
     */
    private Parameters parameters = new Parameters();

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
     * The field <tt>atLetter</tt> contains the indicator for the at catcode.
     */
    private boolean atLetter = false;

    /**
     * The field <tt>ANALYZER</tt> contains the analyzer.
     */
    private static final LaTeXAnalyzer LATEX_ANALYZER =
            new LaTeXMacroAnalyzer();

    /**
     * Creates a new object.
     */
    public DependencyNet() {

        map = new HashMap<String, Artifact>();
    }

    /**
     * Add an artifact.
     * 
     * @param a the artifact to add
     * 
     * @return the old artifact stored under the same name or <code>null</code>
     */
    public Artifact addArtifact(Artifact a) {

        if (a == null) {
            throw new IllegalArgumentException();
        }
        return map.put(a.getKey(), a);
    }

    /**
     * Analyze the artifact and augment the net.
     * 
     * @param artifact the artifact
     * 
     * @throws IOException in case of an I/O error
     */
    public void analyzeLaTeX(Artifact artifact) throws IOException {

        LATEX_ANALYZER.analyze(artifact, this);
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

        if (target == null) {
            throw new IllegalStateException("undefined target");
        }

        File outputDirectory = parameters.getOutputDirectory();
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new MakeException(logger, "net.out.dir", //
                outputDirectory.toString());
        }

        int lim = parameters.getLimit();

        for (int i = 1; i <= lim; i++) {
            logger.info(Message.get("net.building", //
                Integer.toString(i), target.toString()));
            if (!target.build(parameters, logger, simulate)) {
                return;
            }

        }
        throw new MakeException(logger, "net.limit.reached", //
            Integer.toString(parameters.getLimit()));
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
     * @param artifact the artifact
     * 
     * @return the full file
     * 
     * @throws FileNotFoundException in case no resource could be found
     */
    public File findFile(String fileName, String[] extensions, Artifact artifact)
            throws FileNotFoundException {

        File file = searchFile(fileName, extensions, artifact.getFile());
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
            a = new Artifact(file);
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
     * Getter for the parameters.
     * 
     * @return the parameters
     */
    public Parameters getParameters() {

        return parameters;
    }

    /**
     * Getter for the target.
     * 
     * @return the target
     * 
     * @throws IOException in case of an I/O error
     */
    public Artifact getTarget() throws IOException {

        return target;
    }

    /**
     * Getter for the atLetter.
     * 
     * @return the atLetter
     */
    public boolean isAtLetter() {

        return atLetter;
    }

    /**
     * Print this net.
     * 
     * @param w the writer
     * @param prefix the prefix
     */
    public void print(PrintWriter w, String prefix) {

        String[] keys = map.keySet().toArray(new String[map.size()]);
        Arrays.sort(keys);
        for (String key : keys) {
            map.get(key).print(w, prefix);
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
    public File searchFile(String fileName, String[] extensions, File base) {

        String name = fileName.replaceAll("\\.[a-zA-Z0-9_]*", "");

        File dir = base.getParentFile();

        for (String ext : extensions) {
            File f = new File(dir, name + ext);
            if (f.canRead()) {
                return f;
            }
        }

        for (String path : parameters.getTexinputs()) {
            dir = new File(path);
            if (dir.isDirectory()) {
                for (String ext : extensions) {
                    File f = new File(dir, name + ext);
                    if (f.canRead()) {
                        return f;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Setter for the atLetter.
     * 
     * @param atLetter the atLetter to set
     */
    public void setAtLetter(boolean atLetter) {

        this.atLetter = atLetter;
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
     * Setter for the master. The dependencies are analyzed and the net is
     * wired.
     * <p>
     * Note: This method must be invoked exactly once. Otherwise an
     * {@link IllegalStateException} is thrown.
     * </p>
     * 
     * @param file the master file; this can not be <code>null</code>
     * 
     * @throws MakeException in case of an error
     */
    public void wire(File file) throws MakeException {

        if (master != null) {
            throw new IllegalStateException("master already set");
        } else if (file == null) {
            throw new MakeException(logger, "net.missing.file");
        }
        File directory = parameters.getOutputDirectory();

        try {
            master = new Artifact(file);
            addArtifact(master);

            String format =
                    parameters.getTargetFormat().toUpperCase(Locale.ENGLISH);
            FileFormat fmt;
            try {
                fmt = FileFormat.valueOf(format);
            } catch (IllegalArgumentException e) {
                throw new MakeException(logger, "net.illegal.format",
                    parameters.getTargetFormat());
            }
            target = fmt.makeTarget(directory, file.getName(), this);

            analyzeLaTeX(master);
        } catch (FileNotFoundException e) {
            throw new MakeException(logger, "net.file.not.found", e
                .getMessage());
        } catch (IOException e) {
            throw new MakeException(logger, "thrown", e);
        }
    }

}
