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

package org.extex.maven.latex.make;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Artifact implements Iterable<Dependency> {

    /**
     * The field <tt>file</tt> contains the file.
     */
    private File file;

    /**
     * The field <tt>dependencies</tt> contains the artifacts this one depends
     * on.
     */
    private List<Dependency> dependencies = new ArrayList<Dependency>();

    /**
     * The field <tt>actions</tt> contains the actions to be executed to update
     * the current artifact.
     */
    private List<BuildAction> actions = new ArrayList<BuildAction>();

    /**
     * Creates a new object.
     * 
     * @param file the file
     */
    public Artifact(File file) {

        this.file = file;
    }

    /**
     * Add a build action.
     * 
     * @param list the list to add
     */
    public void addAction(BuildAction... list) {

        for (BuildAction a : list) {
            actions.add(a);
        }
    }

    /**
     * Add a dependency.
     * 
     * @param list the list to add
     */
    public void addDependencies(Dependency... list) {

        for (Dependency d : list) {
            dependencies.add(d);
        }
    }

    /**
     * Analyze the artifact and augment the net.
     * 
     * @param net the net
     * 
     * @throws IOException in case of an I/O error
     */
    public void analyze(Net net) throws IOException {

        // ignore
    }

    /**
     * Getter for the dependencies.
     * 
     * @return the dependencies
     */
    public List<Dependency> getDependencies() {

        return dependencies;
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
     * {@inheritDoc}
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Dependency> iterator() {

        return dependencies.iterator();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param plan the list of actions to enrich
     * @param context the environment
     */
    public void plan(List<PlanItem> plan, Map<String, String> context) {

        boolean add = false;

        for (Dependency d : dependencies) {
            if (!d.isUpToDate(this, context)) {
                add = true;
                d.plan(this, plan, context);
            }
        }

        if (add) {
            for (BuildAction a : actions) {
                plan.add(new PlanItem(a, this));
            }
        }
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
        for (Dependency d : dependencies) {
            d.print(w, prefix, "\t-> ");
        }
        for (BuildAction a : actions) {
            w.print("\tdo ");
            a.print(w, "\t   ");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return file.getName();
    }

}
