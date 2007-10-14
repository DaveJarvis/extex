/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.dump;

import org.extex.core.count.FixedCount;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.impl.TypesetterImpl;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;

/**
 * This class implements the typesetter interface but simply records the events
 * received.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4787 $
 */
public class DumpTypesetter extends TypesetterImpl {

    /**
     * Creates a new object.
     */
    public DumpTypesetter() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#add( org.extex.typesetter.type.Node)
     */
    @Override
    public void add(Node node)
            throws TypesetterException,
                ConfigurationException {

        super.add(node);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#add( org.extex.core.glue.FixedGlue)
     */
    @Override
    public void add(FixedGlue g) throws TypesetterException {

        super.add(g);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.impl.TypesetterImpl#addSpace(
     *      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.core.count.FixedCount)
     */
    @Override
    public void addSpace(TypesettingContext typesettingContext,
            FixedCount spacefactor)
            throws TypesetterException,
                ConfigurationException {

        super.addSpace(typesettingContext, spacefactor);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#complete(
     *      org.extex.typesetter.TypesetterOptions)
     */
    @Override
    public NodeList complete(TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        return super.complete(context);
    }

}
