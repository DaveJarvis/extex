/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder.core;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class enhances {@link File} with some additional features.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FileWrapper extends File implements FileFilter {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2011L;

    /**
     * The field <tt>relativePath</tt> contains the relative path to the top of
     * the tree.
     */
    private String relativePath;

    /**
     * The field <tt>title</tt> contains the cached title.
     */
    private String title = null;

    /**
     * The field <tt>depth</tt> contains the depth in the tree.
     */
    private int depth;

    /**
     * Creates a new object.
     * 
     * @param file the file
     * @param relativePath the path to the top of the tree
     */
    public FileWrapper(File file, String relativePath) {

        this(file, relativePath, 0);
    }

    /**
     * Creates a new object.
     * 
     * @param file the file
     * @param relativePath the path to the top of the tree
     * @param d the current depth in the tree
     */
    private FileWrapper(File file, String relativePath, int d) {

        super(file.getParent(), file.getName());
        this.relativePath = relativePath;
        depth = d;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File pathname) {

        if (pathname.isDirectory()) {
            return new File(pathname, "index.html").exists();
        }
        String name = pathname.getName();
        return name.endsWith(".html") && !name.equals("index.html");
    }

    /**
     * Getter for the files and directories contained in this one.
     * 
     * @return the files in wrappers
     */
    public FileWrapper[] files() {

        File[] files = listFiles(this);
        FileWrapper[] result = new FileWrapper[files.length];
        int i = 0;

        String path = depth == 0 
                ? relativePath
                : relativePath + "/" + getName();
        for (File f : files) {
            result[i++] = new FileWrapper(f, path, depth + 1);
        }
        return result;
    }

    /**
     * Retrieve the the full name relative to the root.
     * 
     * @return the full name relative to the root
     */
    public String getRelativeName() {

        return relativePath + "/" + getName();
    }

    /**
     * Getter for the relativePath.
     * 
     * @return the relativePath
     */
    public String getRelativePath() {

        return relativePath;
    }

    /**
     * Getter for the title.
     * 
     * @return the title
     */
    public String getTitle() {

        if (title != null) {
            return title;
        }
        File f = null;
        if (isDirectory()) {
            File index = new File(this, "index.html");
            if (index.exists()) {
                f = index;
            }
        } else if (isFile()) {
            f = this;
        }
        if (f != null) {
            try {
                ParsingReader reader = new ParsingReader(new FileReader(f));
                try {
                    if (reader.scanToTag("title")) {
                        StringBuilder buffer = new StringBuilder();
                        if (reader.scanToTag("/title", buffer)) {
                            title = buffer.toString().replaceAll("[\r\n]", "");
                            return title;
                        }
                    }
                } finally {
                    reader.close();
                }
            } catch (IOException e) {
                // fall through
            }
        }
        title = getName();
        return title;
    }
}
