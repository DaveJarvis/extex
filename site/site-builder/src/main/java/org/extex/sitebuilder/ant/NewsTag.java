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

import org.extex.sitebuilder.core.NewsBuilder;

/**
 * This class is a wrapper for the news builder. It provides the setters needed
 * for the news tag in an Ant build file.
 * <p>
 * Ant uses reflection for the definition of the arguments and the contents of
 * an XML tag. Thus it is necessary to provide a class which exposes just the
 * methods needed for Ant parsing.
 * </p>
 * 
 * <pre style="background:#eeeeee;">
 *  &lt;News
 *    output="<i>file</i>"&gt;
 *    max="<i>max value</i>"&gt;
 *    template="<i>template file</i>" /&gt; </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NewsTag {

    /**
     * The field <tt>builder</tt> contains the encapsulated news builder.
     */
    private NewsBuilder builder;

    /**
     * Creates a new object.
     * 
     * @param newsBuilder the news builder receiving the arguments
     */
    public NewsTag(NewsBuilder newsBuilder) {

        builder = newsBuilder;
    }

    /**
     * Setter for the max value.
     * 
     * @param max the max value to set
     */
    public void setMax(int max) {

        builder.setMax(max);
    }

    /**
     * Setter for the output file.
     * 
     * @param output the output file to set
     */
    public void setOutput(File output) {

        builder.setOutput(output);
    }

    /**
     * Setter for template file.
     * 
     * @param output the output file to set
     */
    public void setTemplate(String template) {

        builder.setTemplate(template);
    }

}
