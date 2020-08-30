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

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.resource.ImageDescriptor;
import org.extex.exbib.editor.Activator;

/**
 * This class is a base implementation of a {@link BstModelNode}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractBstModelNode implements BstModelNode {

    /**
     * The field {@code NO_OBJECTS} contains the empty array of objects. It is
     * used to signal that no children are present.
     */
    protected static final Object[] NO_OBJECTS = new Object[]{};

    /**
     * The field {@code name} contains the name.
     */
    private String name;

    /**
     * The field {@code offset} contains the offset within the document.
     */
    private int offset;

    /**
     * The field {@code elements} contains the children.
     */
    private final Object[] elements;

    /**
     * The field {@code classification} contains the class.
     */
    private final BstClass classification;

    /**
     * Creates a new object.
     * 
     * @param offset offset within the document
     * @param name the name
     * @param classification the class
     */
    public AbstractBstModelNode(int offset, String name, BstClass classification) {

        this(offset, name, classification, NO_OBJECTS);
    }

    /**
     * Creates a new object.
     * 
     * @param offset offset within the document
     * @param name the name
     * @param classification the class
     * @param elements the children
     */
    public AbstractBstModelNode(int offset, String name,
            BstClass classification, Object[] elements) {

        this.offset = offset;
        this.name = name;
        this.classification = classification;
        this.elements = elements;
    }

@Override
    public BstClass getClassification() {

        return classification;
    }

@Override
    public String getDescription() {

        return getDescription("description", getName());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param key
     * @param args
     * @return
     */
    protected String getDescription(String key, Object... args) {

        String path = getClass().getName().replace('.', '/');
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(path);
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException e) {
            return e.toString();
        }
    }

public Object[] getElements() {

        return elements;
    }

@Override
    public ImageDescriptor getImageDescriptor() {

        return Activator.getImageDescriptor("icons/outline/"
                + getClass().getSimpleName() + ".png");
    }

public int getLength() {

        return name.length();
    }

public String getName() {

        return name;
    }

public int getOffset() {

        return offset;
    }

@Override
    public boolean hasChildren() {

        return elements.length != 0;
    }

@Override
    public String toString() {

        return name;
    }
}
