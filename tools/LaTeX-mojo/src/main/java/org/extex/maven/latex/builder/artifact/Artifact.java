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

package org.extex.maven.latex.builder.artifact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.maven.latex.builder.DependencyNet;
import org.extex.maven.latex.builder.action.BuildAction;
import org.extex.maven.latex.builder.exception.MakeException;

/**
 * This class represents a simple artifact. It also can act as base class to
 * derive specialized artifacts from it.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Artifact {

    /**
     * The field <tt>file</tt> contains the file.
     */
    private File file;

    /**
     * The field <tt>content</tt> contains the cached content from the last
     * round.
     */
    private StringBuilder content = null;

    /**
     * The field <tt>dependencies</tt> contains the artifacts this one depends
     * on.
     */
    private List<Artifact> dependencies = new ArrayList<Artifact>();

    /**
     * The field <tt>actions</tt> contains the actions to be executed to update
     * the current artifact.
     */
    private List<BuildAction> actions = new ArrayList<BuildAction>();

    /**
     * The field <tt>lastModified</tt> contains the cached modification time.
     */
    private long lastModified = Long.MIN_VALUE;

    /**
     * Creates a new object.
     * 
     * @param file the file
     * 
     * @throws IOException in case of an I/O error
     */
    public Artifact(File file) throws IOException {

        this.file = file;
        if (file.exists()) {
            content = reload();
            lastModified = file.lastModified();
        }
    }

    /**
     * Analyze the artifact and augment the net.
     * 
     * @param net the net
     * 
     * @throws IOException in case of an I/O error
     */
    public void analyze(DependencyNet net) throws IOException {

        // nothing to do
    }

    /**
     * Make one step in building this artifact.
     * 
     * @param context the context mapping
     * @param logger the logger
     * @param simulate the simulation indicator
     * 
     * @return <code>true</code> iff the build has been completed
     * 
     * @throws MakeException in case of an error
     */
    public boolean build(Map<String, String> context, Logger logger,
            boolean simulate) throws MakeException {

        boolean fire = false;

        for (Artifact d : dependencies) {
            // TODO up to date
            if (!d.build(context, logger, simulate)) {
                // fire = true;
            }
        }

        if (!file.exists()) {
            fire = true;
            logger.info("Creating " + toString());
        } else {
            for (Artifact d : dependencies) {
                if (d.isNewer(this)) {
                    fire = true;
                    logger.info("Recreating " + toString() + " since "
                            + d.toString() + " is newer.");
                    break;
                }
            }
        }

        if (!fire) {
            return true;
        } else if (actions.size() == 0) {
            logger.warning("No actions found to create " + toString());
            if (!file.exists()) {
                logger.severe("Artifact " + toString()
                        + " could not be created");
            }
            return true;
        }

        for (BuildAction a : actions) {
            a.execute(this, context, logger, simulate);
        }

        if (!simulate && !file.exists()) {
            logger.severe("The actions did not create " + toString());
        }
        return true;
    }

    /**
     * Add a dependency.
     * 
     * @param a the artifact to which the dependency exists
     */
    public void dependsOn(Artifact a) {

        dependencies.add(a);
    }

    /**
     * Determine a derived file which has just another extension.
     * 
     * @param ext the extension without the dot
     * 
     * @return the derived file
     */
    public File derivedFile(String ext) {

        String name =
                file.getName().replaceAll("\\.[a-zA-Z0-9_]*", "") + "." + ext;
        return new File(file.getParentFile(), name);
    }

    /**
     * Getter for the file.
     * 
     * @return the file
     */
    public File getFile() {

        return file;
    }

    /**
     * Check whether the file contents has been modified and refresh the cache.
     * 
     * @return <code>true</code> iff the artifact does not exist or has recently
     *         been modified.
     * 
     * @throws IOException in case of an I/O error
     */
    public boolean isModified() throws IOException {

        if (!file.exists()) {
            content = null;
            lastModified = Long.MIN_VALUE;
            return true;
        }
        reload();
        return true;
    }

    /**
     * Compare the age against a reference artifact.
     * 
     * @param artifact the reference artifact
     * 
     * @return <code>true</code> iff the artifact is newer than the reference
     */
    private boolean isNewer(Artifact artifact) {

        return !file.exists()
                || file.lastModified() <= artifact.getFile().lastModified();
    }

    /**
     * Print this object to a writer.
     * 
     * @param w the writer
     * @param prefix the prefix
     */
    public void print(PrintWriter w, String prefix) {

        w.print(prefix);
        w.println(file.getName());
        for (Artifact d : dependencies) {
            w.print(prefix);
            w.print("\t-> ");
            w.print(d.getFile().getName());
            w.print("\n");
        }
        for (BuildAction a : actions) {
            w.print(prefix);
            w.print("\tdo ");
            a.print(w, "\t");
        }
    }

    /**
     * Add a build action.
     * 
     * @param list the list to add
     */
    public void provideActions(BuildAction... list) {

        if (actions.size() == 0) {
            for (BuildAction a : list) {
                actions.add(a);
            }
        }
    }

    /**
     * Reload the content of the file into memory.
     * 
     * @return the buffer containing the content
     * 
     * @throws IOException in case of an I/O error
     */
    private StringBuilder reload() throws IOException {

        StringBuilder buffer = new StringBuilder();
        Reader r = new BufferedReader(new FileReader(file));
        try {
            for (int c = r.read(); c >= 0; c = r.read()) {
                buffer.append((char) c);
            }
        } finally {
            r.close();
        }
        return buffer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return file.toString().replaceAll("\\\\", "/");
    }

}
