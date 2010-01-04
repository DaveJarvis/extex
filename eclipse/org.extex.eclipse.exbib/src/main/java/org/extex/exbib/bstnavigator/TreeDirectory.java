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

package org.extex.exbib.bstnavigator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Status;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
class TreeDirectory extends TreeNode {

    /**
     * The constant <tt>LSR_FILE_NAME</tt> contains the name of the index file.
     */
    private static final String LSR_FILE_NAME = "ls-R";

    /**
     * The field <tt>children</tt> contains the ...
     */
    private List<TreeNode> children = null;

    /**
     * The field <tt>directory</tt> contains the ...
     */
    private final File directory;

    private int noLeaves = 1;

    private boolean indexed = false;

    /**
     * Creates a new object.
     * 
     * @param directory
     * @param parent TODO
     */
    public TreeDirectory(File directory, TreeDirectory parent) {

        super(directory == null ? null : directory.getName(), parent);
        this.directory = directory;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param child
     */
    protected void addChild(TreeNode child) {

        if (children == null) {
            refresh();
        }
        children.add(child);
        child.setParent(this);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s
     * @return
     */
    private TreeDirectory createDir(String s) {

        if (children == null) {
            children = new ArrayList<TreeNode>();
        }

        for (TreeNode node : children) {
            if (node.getName().equals(s) && node instanceof TreeDirectory) {
                return (TreeDirectory) node;
            }
        }
        TreeDirectory dir = new TreeDirectory(new File(s), this);
        children.add(dir);
        return dir;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public TreeNode[] getChildren() {

        if (children == null) {
            refresh();
        }
        return children.toArray(new TreeNode[children.size()]);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public boolean hasChildren() {

        if (children == null) {
            refresh();
        }
        return children.size() > 0;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public boolean hasLeaves() {

        if (children == null) {
            refresh();
        }
        return noLeaves != 0;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public boolean isIndexed() {

        return indexed;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    protected boolean isRecognized(String name) {

        String loweredName = name.toLowerCase();
        return loweredName.endsWith(".bst") || loweredName.endsWith(".bib");
    }

    private boolean isRecognizedDirectory(String name) {

        return !name.startsWith(".");
    }

    /**
     * Load the ls-R file.
     * 
     * @param path the path for the ls-R file
     */
    private boolean load(File path) {

        indexed = true;

        long start = System.currentTimeMillis();
        File file = new File(path, LSR_FILE_NAME);
        if (!file.canRead()) {
            Activator.getDefault().getLog().log(
                new Status(Status.ERROR, Activator.PLUGIN_ID,
                    "Unable to read file " + file.toString()));
            return false;
        }

        TreeDirectory cwd = null;

        try {
            BufferedInputStream in =
                    new BufferedInputStream(new FileInputStream(file));
            for (int c = in.read(); c >= 0; c = in.read()) {
                if (c == '%') {
                    do {
                        c = in.read();
                    } while (c >= 0 && c != 10 && c != 13);
                } else if (c >= ' ') {
                    StringBuilder line = new StringBuilder();
                    do {
                        line.append((char) c);
                        c = in.read();
                    } while (c >= 0 && c != '\r' && c != '\n');
                    int len = line.length();
                    if (len != 0) {
                        if (line.charAt(len - 1) == ':') {
                            line.deleteCharAt(len - 1);
                            if (len > 2 && line.charAt(0) == '.'
                                    && line.charAt(1) == '/') {
                                line.delete(0, 2);
                            }
                            cwd = this;
                            for (String s : line.toString().split("/")) {
                                cwd = cwd.createDir(s);
                            }
                            // directory = new File(path, line.toString());

                        } else {
                            String key = line.toString();
                            if (isRecognized(key)) {
                                cwd.addChild(new TreeNode(key, cwd));
                            }
                        }
                    }
                }
            }

            in.close();

        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        Activator.getDefault().getLog()
            .log(
                new Status(Status.INFO, Activator.PLUGIN_ID, "Database "
                        + file.toString() + " loaded in "
                        + Long.toString(System.currentTimeMillis() - start)
                        + "ms"));
        return true;
    }

    /**
     * Reload the children from an index file or the file system.
     * 
     */
    protected void refresh() {

        if (children != null) {
            for (TreeNode node : children) {
                if (node instanceof TreeDirectory) {
                    removeChild(node);
                } else {
                    node.setParent(null);
                }
            }
        }
        children = new ArrayList<TreeNode>();
        if (directory == null) {
            return;
        }

        if (new File(directory, LSR_FILE_NAME).canRead()) {
            load(directory);
        } else {
            File[] fileList = directory.listFiles();
            noLeaves = 0;

            if (fileList != null) {
                for (File file : fileList) {
                    String name = file.getName();
                    if (file.isHidden()) {
                        // silently skipped
                    } else if (file.isDirectory()) {
                        if (isRecognizedDirectory(name)) {
                            TreeDirectory dir = new TreeDirectory(file, this);
                            children.add(dir);
                            if (noLeaves == 0) {
                                dir.refresh();
                            }
                            noLeaves++;
                        }
                    } else if (file.isFile() && isRecognized(name)) {
                        TreeNode node = new TreeNode(name, this);
                        node.setParent(this);
                        children.add(node);
                        noLeaves++;
                    }
                }
            }
        }
        if (noLeaves == 0) {
            for (TreeDirectory d = getParent(); d != null; d = d.getParent()) {
                d.noLeaves--;
            }
        }
    }

    /**
     * Remove a child node.
     * 
     * @param child the child node
     */
    protected void removeChild(TreeNode child) {

        if (children != null) {
            children.remove(child);
            child.setParent(null);
        }
    }

}
