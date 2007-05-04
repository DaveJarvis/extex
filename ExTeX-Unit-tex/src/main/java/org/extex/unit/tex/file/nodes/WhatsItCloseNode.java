/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.file.nodes;

import java.io.IOException;
import java.util.logging.Logger;

import org.extex.core.exception.GeneralException;
import org.extex.framework.logger.LogEnabled;
import org.extex.scanner.type.file.OutFile;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.WhatsItNode;

/**
 * This WhatsIt node closes an out file on shipping.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WhatsItCloseNode extends WhatsItNode implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>key</tt> contains the key of the outfile to close.
     */
    private String key;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     *
     * @param theKey the key of the file to open
     */
    public WhatsItCloseNode(String theKey) {

        super();
        this.key = theKey;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.Node#atShipping(
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.typesetter.type.NodeVisitor,
     *      boolean)
     */
    public Node atShipping(PageContext context, Typesetter typesetter,
            NodeVisitor visitor, boolean inHMode)
            throws GeneralException {

        OutFile file = context.getOutFile(key);
        if (file != null) {
            try {
                file.close();
            } catch (IOException e) {
                logger.info(e.getLocalizedMessage() + "\n");
            }
        }

        return null;
    }

    /**
     * Setter for the logger.
     *
     * @param theLogger the new logger
     *
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger theLogger) {

        this.logger = theLogger;
    }

}
