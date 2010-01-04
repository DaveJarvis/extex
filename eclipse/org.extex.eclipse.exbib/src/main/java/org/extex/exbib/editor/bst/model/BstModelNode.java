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

package org.extex.exbib.editor.bst.model;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This interface describes the capabilities of a unit of information stored in
 * the model.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public interface BstModelNode {

    /**
     * Getter for the classification.
     * 
     * @return the classification
     */
    BstClass getClassification();

    /**
     * Get the description of the element.
     * 
     * @return the description
     */
    String getDescription();

    /**
     * Get the children. If no children are present then the empty list is
     * returned.
     * 
     * @return the children
     */
    Object[] getElements();

    /**
     * Getter for the {@link ImageDescriptor}.
     * 
     * @return the image descriptor or <code>null</code>
     */
    ImageDescriptor getImageDescriptor();

    /**
     * Get the length for syntax highlighting.
     * 
     * @return the length
     */
    int getLength();

    /**
     * Get the name of the node.
     * 
     * @return the name of the node
     */
    String getName();

    /**
     * Get the offset of the node for navigating to the definition.
     * 
     * @return the offset
     */
    int getOffset();

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    boolean hasChildren();

}
