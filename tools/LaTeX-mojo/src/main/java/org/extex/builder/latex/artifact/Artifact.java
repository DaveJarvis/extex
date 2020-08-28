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

package org.extex.builder.latex.artifact;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.extex.builder.latex.Message;
import org.extex.builder.latex.Parameters;
import org.extex.builder.latex.action.Action;
import org.extex.builder.latex.exception.MakeException;

/**
 * This class represents a simple artifact. It also can act as base class to
 * derive specialized artifacts from it.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Artifact {

    /**
     * The constant <tt>READ_JUNK_SIZE</tt> contains the number of bytes to read
     * from file.
     */
    private static final int READ_JUNK_SIZE = 1024;

    /**
     * The field <tt>file</tt> contains the file.
     */
    private File file;

    /**
     * The field <tt>checksum</tt> contains the cached MD5 checksum from the
     * last round.
     */
    private byte[] checksum = null;

    /**
     * The field <tt>dependencies</tt> contains the artifacts this one depends
     * on.
     */
    private List<Artifact> dependencies = new ArrayList<Artifact>();

    /**
     * The field <tt>actions</tt> contains the actions to be executed to update
     * the current artifact.
     */
    private List<Action> actions = new ArrayList<Action>();

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

        this.file = file.getAbsoluteFile();
        if (file.exists()) {
            checksum = reload();
            lastModified = file.lastModified();
        }
    }

    /**
     * Creates a new object.
     * 
     * @param directory the directory
     * @param name the file name
     * 
     * @throws IOException in case of an I/O error
     */
    public Artifact(File directory, String name) throws IOException {

        this(new File(directory, name));
    }

    /**
     * Make one step in building this artifact.
     * 
     * @param parameters the context mapping
     * @param logger the logger
     * @param simulate the simulation indicator
     * 
     * @return <code>true</code> iff the build has changed something
     * 
     * @throws MakeException in case of an error
     */
    public boolean build(Parameters parameters, Logger logger, boolean simulate)
            throws MakeException {

        boolean fire = false;

        for (Artifact d : dependencies) {
            if (d.build(parameters, logger, simulate)) {
                fire = true;
            }
        }

        if (!file.exists()) {
            fire = true;
            logger.info(Message.get("artifact.create", toString()));
        } else {
            for (Artifact d : dependencies) {
                if (!d.isUpToDate(this, logger)) {
                    fire = true;
                }
            }
        }

        if (!fire) {
            return false;
        } else if (actions.size() == 0) {
            logger.warning(Message.get("artifact.no.actions", toString()));
        }
        boolean modified = false;

        for (Action a : actions) {
            modified |= a.execute(this, parameters, logger, simulate);
        }

        if (simulate) {
            return false;
        } else if (!file.exists()) {
            throw new MakeException(logger, "artifact.create.error", toString());
        }
        return modified;
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
     * Compare two checksums.
     * 
     * @param cs1 checksum 1
     * @param cs2 checksum 2
     * 
     * @return <code>true</code> iff the two arrays are different
     */
    private boolean different(byte[] cs1, byte[] cs2) {

        if (cs1 == null) {
            return cs2 != null;
        } else if (cs2 == null) {
            return true;
        }

        for (int i = 0; i < cs1.length; i++) {
            if (cs1[i] != cs2[i]) {
                return true;
            }
        }
        return false;
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
     * Getter for the key.
     * 
     * @return the key
     */
    public String getKey() {

        return file.getAbsoluteFile().toString();
    }

    /**
     * Getter for the base name of the artifact's file.
     * 
     * @return the base name of the artifact's file
     */
    public String getName() {

        return file.getName();
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
            checksum = null;
            lastModified = Long.MIN_VALUE;
            return true;
        }
        reload();
        return true;
    }

    /**
     * Check whether the current artifact is up to date w.r.t. another artifact.
     * 
     * @param artifact the artifact
     * @param logger the logger
     * 
     * @return <code>true</code> iff the artifact exists and is up to date
     *         w.r.t. the argument
     * 
     * @throws MakeException in case of an I/O error
     */
    public boolean isUpToDate(Artifact artifact, Logger logger)
            throws MakeException {

        if (!file.exists()) {
            logger.info(Message.get("artifact.does.not.exist", file.getName()));
            return false;
        }
        if (file.lastModified() != lastModified) {
            byte[] cs;
            try {
                cs = reload();
            } catch (IOException e) {
                throw new MakeException(logger, "thrown", e);
            }
            if (checksum == null) {
                checksum = cs;
                lastModified = file.lastModified();
                return false;
            }
            if (different(checksum, cs)) {
                logger
                    .info(Message.get("artifact.new.checksum", file.getName()));
                checksum = cs;
                lastModified = file.lastModified();
            }
        }

        if (lastModified > artifact.lastModified) {

            logger.info(Message.get("artifact.up.to.date", file.getName()));
            return true;
        }

        logger.info(Message.get("artifact.not.up.to.date", file.getName()));
        return false;
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
        for (Action a : actions) {
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
    public void provideActions(Action... list) {

        if (actions.size() == 0) {
            for (Action a : list) {
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
    private byte[] reload() throws IOException {

        MessageDigest digest;
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
        InputStream r = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[READ_JUNK_SIZE];
        try {
            for (int n = r.read(buffer); n > 0; n = r.read(buffer)) {
                digest.update(buffer, 0, n);
            }
        } finally {
            r.close();
        }
        return digest.digest();
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
