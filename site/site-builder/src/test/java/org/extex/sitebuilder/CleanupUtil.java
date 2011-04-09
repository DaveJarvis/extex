/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder;

import java.io.File;
import java.io.IOException;

/**
 * Utilities for cleanup.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CleanupUtil {

    /**
     * Recursively delete a file or a directory tree.
     * 
     * @param dir the file or directory
     * 
     * @throws IOException in case of an error
     */
    public static void rmdir(File dir) throws IOException {

        if (dir.isDirectory()) {
            for (String f : dir.list()) {
                File file = new File(dir, f);
                if (file.isFile()) {
                    if (!file.delete()) {
                        throw new IOException("deletion failed: "
                                + file.toString());
                    }
                } else if (file.isDirectory()) {
                    rmdir(file);
                } else {
                    throw new IllegalStateException(
                        "strange file encountered: " + file.toString());
                }
            }
        }
        if (!dir.delete()) {
            throw new IOException("deletion failed: " + dir.toString());
        }
    }

}
