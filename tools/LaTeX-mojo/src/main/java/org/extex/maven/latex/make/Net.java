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

package org.extex.maven.latex.make;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.maven.latex.make.artifact.LatexArtifact;
import org.extex.maven.latex.make.exception.MakeException;

/**
 * This class represents a net of artifacts and dependencies.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Net {

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
    private Logger logger = Logger.getLogger(Net.class.getName());

    /**
     * The field <tt>master</tt> contains the master.
     */
    private Artifact master = null;

    /**
     * Creates a new object.
     */
    public Net() {

        map = new HashMap<String, Artifact>();
        context = new HashMap<String, String>();
        LatexArtifact.setup(this);
    }

    /**
     * Creates a new object.
     * 
     * @param file the master file
     * 
     * @throws IOException in case of an I/O error
     */
    public Net(File file) throws IOException {

        this();
        if (file == null) {
            throw new IllegalArgumentException("Net(null)");
        }
        String name = file.getAbsoluteFile().toString();
        master = new LatexArtifact(file);
        map.put(name, master);
        master.analyze(this);
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
        List<PlanItem> plan = new ArrayList<PlanItem>();
        a.plan(plan, context);

        for (PlanItem item : plan) {
            item.getAction().execute(item.getArtifact(), context, logger,
                simulate);
        }
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
     * Getter for an artifact.
     * 
     * @param file the file
     * 
     * @return the artifact or a new one if none is defined
     */
    public Artifact getArtifact(File file) {

        String key = file.getAbsoluteFile().toString();
        Artifact a = map.get(key);
        if (a == null) {
            a = new Artifact(file.getAbsoluteFile());
            map.put(key, a);
        }
        return a;
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
     * Getter for the master.
     * 
     * @return the master
     */
    public Artifact getMaster() {

        return master;
    }

    /**
     * Print this net.
     * 
     * @param w the writer
     * @param prefix the prefix
     */
    public void print(PrintWriter w, String prefix) {

        Artifact[] al = map.values().toArray(new Artifact[0]);
        // Arrays.sort(al);
        for (Artifact a : al) {
            a.print(w, prefix);
        }
    }

    /**
     * Add an artifact.
     * 
     * @param a the artifact to add
     * 
     * @return the old artifact stored under the same name or <code>null</code>
     */
    public Artifact putArtifact(Artifact a) {

        return map.put(a.getFile().toString(), a);
    }

    /**
     * Setter for the logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

}
