/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tex.pageBuilder.trivial;

import org.extex.backend.BackendDriver;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.page.Page;
import org.extex.typesetter.type.page.PageFactory;

/**
 * This is a first reference implementation of a page builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TrivialPageBuilder implements PageBuilder {

    /**
     * The field {@code backend} contains the target to receive the pages.
     */
    private BackendDriver backend = null;

    /**
     * The field {@code context} contains the interpreter context.
     */
    private Context context = null;

    /**
     * The field {@code options} contains the options to control the behaviour.
     */
    private TypesetterOptions options = null;

    /**
     * The field {@code pageFactory} contains the page factory to use.
     */
    private PageFactory pageFactory = null;


    public TrivialPageBuilder() {

    }

@Override
    public void close() throws TypesetterException {

        try {
            backend.close();
        } catch (GeneralException e) {
            throw new TypesetterException(e);
        }

    }

    /**
*      org.extex.typesetter.Typesetter)
     */
    @Override
    public void flush(NodeList nodes, Typesetter typesetter)
            throws TypesetterException {

        if (nodes.size() <= 0) {
            return;
        }
        try {
            Page page = pageFactory.newInstance(nodes, context, typesetter);
            if (page != null) {
                backend.shipout(page);
            }
            // nodes.clear();
        } catch (GeneralException e) {
            throw new TypesetterException(e);
        }

    }

    /**
     * This is the entry point for the page builder. Here it receives a complete
     * node list to be sent to the output writer. It can be assumed that all
     * values for width, height, and depth of the node lists are properly
     * filled.
     * 
     * @param nodes the nodes to send
     * @param typesetter the typesetter
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.pageBuilder.PageBuilder#inspectAndBuild(org.extex.typesetter.type.node.VerticalListNode,
     *      org.extex.typesetter.Typesetter)
     */
    @Override
    public void inspectAndBuild(VerticalListNode nodes, Typesetter typesetter)
            throws TypesetterException {

        FixedDimen d = nodes.getVerticalSize();
        if (d.ge(options.getDimenOption("vsize"))) {

            flush(nodes, typesetter);
        }
    }

    /**
     * Setter for the back-end driver. This has to be provided before the page
     * builder can be active.
     * 
     * @param backend the new document writer to use
     * 
     * @see org.extex.typesetter.Typesetter#setBackend(org.extex.backend.BackendDriver)
     */
    @Override
    public void setBackend(BackendDriver backend) {

        this.backend = backend;
    }

@Override
    public void setContext(PageContext context) {

        // TODO gene: beware of ClassCastException
        this.context = (Context) context;
    }

    /**
     * Setter for options.
     * 
     * @param options the options to set
     * 
     * @see org.extex.typesetter.pageBuilder.PageBuilder#setOptions(org.extex.typesetter.TypesetterOptions)
     */
    @Override
    public void setOptions(TypesetterOptions options) {

        this.options = options;
    }

@Override
    public void setOutputRoutine(OutputRoutine output) {

        // not supported
    }

@Override
    public void setPageFactory(PageFactory factory) {

        pageFactory = factory;
    }

    /**
*      org.extex.typesetter.Typesetter)
     */
    @Override
    public void shipout(NodeList nodes, Typesetter typesetter)
            throws TypesetterException {

        if (nodes.size() <= 0) {
            return;
        }
        try {
            Page page = pageFactory.newInstance(nodes, context, typesetter);
            if (page != null) {
                backend.shipout(page);
            }
            // nodes.clear();
        } catch (GeneralException e) {
            throw new TypesetterException(e);
        }

    }

}
