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

/**
 * TODO gne: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBase {

    /**
     * The field <tt>template</tt> contains the template file.
     */
    private File template = null;

    /**
     * The field <tt>dir</tt> contains the base directory.
     */
    private File dir = null;

    /**
     * Getter for dir.
     * 
     * @return the dir
     */
    public File getDir() {

        return dir;
    }

    /**
     * Getter for template.
     * 
     * @return the template
     */
    public File getTemplate() {

        return template;
    }

    /**
     * Setter for dir.
     * 
     * @param dir the dir to set
     */
    public void setDir(File dir) {

        this.dir = dir;
    }

    /**
     * Setter for template.
     * 
     * @param template the template to set
     */
    public void setTemplate(File template) {

        this.template = template;
    }

}
