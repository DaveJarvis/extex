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

package org.extex.sitebuilder.ant;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.tools.ant.BuildException;
import org.extex.sitebuilder.core.TreeBuilder;

/**
 * This class is a wrapper for the tree builder. It provides the setters needed
 * for the tree tag in an Ant build file.
 * <p>
 * Ant uses reflection for the definition of the arguments and the contents of
 * an XML tag. Thus it is necessary to provide a class which exposes just the
 * methods needed for Ant parsing.
 * </p>
 * 
 * <pre style="background:#eeeeee;">
 *  &lt;Tree
 *    dir="<i>base/directory</i>"&gt;
 *    processHtml="<i>value</i>"&gt;
 *    template="<i>template/file</i>" /&gt; </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TreeTag {

    /**
     * The field <tt>builder</tt> contains the wrapped tree builder.
     */
    private TreeBuilder builder;

    /**
     * Creates a new object.
     * 
     * @param treeBuilder the tree builder
     */
    public TreeTag(TreeBuilder treeBuilder) {

        builder = treeBuilder;
    }

    /**
     * Setter for the base directory.
     * 
     * @param dir the base directory to set
     */
    public void setDir(File dir) {

        try {
            builder.setBase(dir);
        } catch (FileNotFoundException e) {
            throw new BuildException(e.toString());
        }
    }

    /**
     * Setter for the processing flag.
     * 
     * @param onOff the value
     */
    public void setProcessHtml(boolean onOff) {

        builder.setTranslateHtml(onOff);
    }

    /**
     * Setter for template.
     * 
     * @param template the template to set
     */
    public void setTemplate(File template) {

        builder.setTemplate(template.toString());
    }

}
