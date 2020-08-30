/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.navigator;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ContentProvider implements ITreeContentProvider {

    /**
     * The field {@code invisibleRoot} contains the root of all evil.
     */
    private ContentDirectory root;

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    private String[] determineRoots() {

        String inputs = System.getenv("TEXINPUTS");
        String pathSeparator = System.getProperty("path.separator");
        if (inputs != null) {
            return inputs.split(pathSeparator);
        }
        inputs = System.getenv("TEXINPUTS.BIBTEX");
        if (inputs != null) {
            return inputs.split(pathSeparator);
        }

        ProcessBuilder pb =
                new ProcessBuilder("kpsewhich", "-expand-path=$TEXMF");
        try {
            Process p = pb.start();
            List<String> list = new ArrayList<String>();
            LineNumberReader in = null;
            try {
                in = new LineNumberReader(
                    new InputStreamReader(p.getInputStream()));
                for (String line = in.readLine(); line != null; line =
                        in.readLine()) {
                    for (String s : line.split(pathSeparator)) {
                        list.add(s);
                    }
                }
            } catch (IOException e) {
                Activator.getDefault().getLog().log(
                    new Status(Status.ERROR, Activator.PLUGIN_ID,
                        "Error while reading from process", e));
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    Activator.getDefault().getLog().log(
                        new Status(Status.ERROR, Activator.PLUGIN_ID,
                            "Could not close stream", e));
                }
                p.destroy();
            }
            return list.toArray(new String[list.size()]);
        } catch (IOException e) {
            Activator.getDefault().getLog().log(
                new Status(Status.WARNING, Activator.PLUGIN_ID,
                    "Command could not be run", e));
        }

        return new String[]{"."};
    }

public void dispose() {

    }

public Object[] getChildren(Object parent) {

        if (parent instanceof ContentDirectory) {
            return ((ContentDirectory) parent).getChildren();
        }
        if (parent instanceof ContentNode) {
            return new Object[0];
        }
        if (root == null) {
            root = new ContentDirectory(null, null);
            for (String dir : determineRoots()) {
                File directory = new File(dir);
                if (directory.isDirectory()) {
                    root.addChild(new ContentDirectory(directory, null));
                } else {
                    Activator.getDefault().getLog().log(
                        new Status(Status.INFO, Activator.PLUGIN_ID,
                            "Skipping " + dir));
                }
            }
        }
        return getChildren(root);
    }

public Object[] getElements(Object parent) {

        return getChildren(parent);
    }

public Object getParent(Object child) {

        if (child instanceof ContentNode) {
            return ((ContentNode) child).getParent();
        }
        return null;
    }

public boolean hasChildren(Object parent) {

        return parent instanceof ContentDirectory
                && ((ContentDirectory) parent).hasChildren();
    }

    public void inputChanged(Viewer v, Object oldInput, Object newInput) {

    }

}
