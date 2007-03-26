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

package org.extex.typesetter.pageBuilder.trivial;

import org.extex.backend.BackendDriver;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.context.Context;
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
 * @version $Revision:4483 $
 */
public class TrivialPageBuilder implements PageBuilder {

    /**
     * The field <tt>backend</tt> contains the target to receive
     * the pages.
     */
    private BackendDriver backend = null;

    /**
     * The field <tt>context</tt> contains the interpreter context.
     */
    private Context context = null;

    /**
     * The field <tt>options</tt> contains the options to control the behaviour.
     */
    private TypesetterOptions options = null;

    /**
     * The field <tt>pageFactory</tt> contains the page factory to use.
     */
    private PageFactory pageFactory = null;

    /**
     * Creates a new object.
     */
    public TrivialPageBuilder() {

        super();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.pageBuilder.PageBuilder#close()
     */
    public void close() throws TypesetterException {

        try {
            backend.close();
        } catch (GeneralException e) {
            throw new TypesetterException(e);
        }

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.pageBuilder.PageBuilder#flush(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.Typesetter)
     */
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
//            nodes.clear();
        } catch (GeneralException e) {
            throw new TypesetterException(e);
        }

    }

    /**
     * This is the entry point for the page builder. Here it receives a
     * complete node list to be sent to the output writer. It can be assumed
     * that all values for width, height, and depth of the node lists are
     * properly filled.
     *
     * @param nodes the nodes to send
     * @param typesetter the typesetter
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.pageBuilder.PageBuilder#inspectAndBuild(
     *      org.extex.typesetter.type.node.VerticalListNode,
     *      org.extex.typesetter.Typesetter)
     */
    public void inspectAndBuild(VerticalListNode nodes,
            Typesetter typesetter) throws TypesetterException {

        FixedDimen d = nodes.getVerticalSize();
        if (d.ge(options.getDimenOption("vsize"))) {

            flush(nodes, typesetter);
        }
    }

    /**
     * Setter for the back-end driver.
     * This has to be provided before the page builder can be active.
     *
     * @param backend the new document writer to use
     *
     * @see org.extex.typesetter.Typesetter#setBackend(
     *      org.extex.backend.BackendDriver)
     */
    public void setBackend(BackendDriver backend) {

        this.backend = backend;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.pageBuilder.PageBuilder#setContext(
     *      org.extex.interpreter.context.Context)
     */
    public void setContext(Context context) {

        this.context = context;
    }

    /**
     * Setter for options.
     *
     * @param options the options to set
     *
     * @see org.extex.typesetter.pageBuilder.PageBuilder#setOptions(
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void setOptions(TypesetterOptions options) {

        this.options = options;
    }

    /**
     * @see org.extex.typesetter.pageBuilder.PageBuilder#setOutputRoutine(
     *      org.extex.typesetter.output.OutputRoutine)
     */
    public void setOutputRoutine(OutputRoutine output) {

        // not supported
    }

    /**
     * @see org.extex.typesetter.pageBuilder.PageBuilder#setPageFactory(
     *      org.extex.typesetter.type.page.PageFactory)
     */
    public void setPageFactory(PageFactory factory) {

        pageFactory = factory;
    }

    /**
     * @see org.extex.typesetter.pageBuilder.PageBuilder#shipout(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.Typesetter)
     */
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
//            nodes.clear();
        } catch (GeneralException e) {
            throw new TypesetterException(e);
        }

    }

}
