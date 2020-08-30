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

import org.extex.sitebuilder.core.SiteBuilder;

/**
 * This class is the container for the {@code &lt;omit&gt;} tag from the Ant
 * build file. the text contained is stored internally and passed to a site
 * builder upon request.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OmitTag {

    /**
     * The field {@code text} contains the contents.
     */
    private StringBuilder text = new StringBuilder();

    /**
     * Add some text to the contents.
     * 
     * @param text the text to add to the contents
     */
    public void addText(String text) {

        this.text.append(text);
    }

    /**
     * Propagate the valued contained in the text to a site builder.
     * 
     * @param builder the target object
     */
    public void propagate(SiteBuilder builder) {

        builder.omit(text.toString());
    }

}
