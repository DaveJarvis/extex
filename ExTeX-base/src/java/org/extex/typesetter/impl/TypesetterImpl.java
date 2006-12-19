/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.impl;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.extex.backend.BackendDriver;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.ImpossibleException;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.scanner.type.token.Token;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Mode;
import org.extex.typesetter.ParagraphObserver;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.InvalidSpacefactorException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.listMaker.HorizontalListMaker;
import org.extex.typesetter.listMaker.ListManager;
import org.extex.typesetter.listMaker.VerticalListMaker;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.InsertionNode;
import org.extex.typesetter.type.node.PenaltyNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.factory.CachingNodeFactory;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.i18n.Localizable;
import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.logger.LogEnabled;

/**
 * This is a reference implementation of the
 * {@link org.extex.typesetter.Typesetter Typesetter} interface.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4526 $
 */
public class TypesetterImpl
        implements
            Typesetter,
            ListManager,
            Localizable,
            LogEnabled {

    /**
     * The field <tt>backend</tt> contains the back-end driver for
     * producing the output.
     */
    private BackendDriver backend = null;

    /**
     * The field <tt>listMaker</tt> contains the current list maker for
     * efficiency. Thus we can avoid to peek at the stack whenever the list
     * maker is needed.
     */
    private ListMaker listMaker;

    /**
     * The field <tt>localizer</tt> contains the localizer.
     */
    private Localizer localizer;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private Logger logger = null;

    /**
     * The field <tt>charNodeFactory</tt> contains the factory to produce glyph
     * nodes.
     */
    private NodeFactory nodeFactory = new CachingNodeFactory();

    /**
     * The field <tt>options</tt> contains the context for accessing parameters.
     */
    private TypesetterOptions options;

    /**
     * The field <tt>outputRoutine</tt> contains the output routine.
     */
    private OutputRoutine outputRoutine = null;

    /**
     * The field <tt>pageBuilder</tt> contains the current page builder.
     */
    private PageBuilder pageBuilder = null;

    /**
     * The field <tt>paragraphBuilder</tt> contains the current paragraph
     * builder.
     */
    private ParagraphBuilder paragraphBuilder = null;

    /**
     * The field <tt>saveStack</tt> contains the stack of list makers.
     */
    private ArrayList saveStack = new ArrayList();

    /**
     * The field <tt>shipoutMark</tt> contains the recorded state of the
     * shipout mark. Initially the shipout mark is <code>false</code>.
     */
    private boolean shipoutMark = false;

    /**
     * Creates a new object and initializes it to receive material.
     * To make it fully functionality is required that the paragraph builder
     * and the ligature builder are provided before they are used.
     */
    public TypesetterImpl() {

        super();

        listMaker = new VerticalListMaker(this, new Locator("", 0, "", 0)); //TODO gene: find better initial locator
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.interpreter.type.glue.FixedGlue)
     */
    public void add(final FixedGlue glue) throws TypesetterException {

        listMaker.add(glue);
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *     org.extex.typesetter.type.Node)
     */
    public void add(final Node node)
            throws TypesetterException,
                ConfigurationException {

        if (node == null) {
            return;
        }

        listMaker.add(node);

        if (saveStack == null
                && (node instanceof PenaltyNode
                        || node instanceof InsertionNode
                        || node instanceof HorizontalListNode //
                || node instanceof VerticalListNode)) {

            pageBuilder.inspectAndBuild((VerticalListNode) listMaker
                    .complete(options), this);
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#addAndAdjust(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addAndAdjust(final NodeList list,
            final TypesetterOptions options)
            throws TypesetterException,
                ConfigurationException {

        listMaker.addAndAdjust(list, options);
    }

    /**
     * @see org.extex.typesetter.ListMaker#addSpace(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.interpreter.type.count.Count)
     */
    public void addSpace(final TypesettingContext typesettingContext,
            final Count spacefactor)
            throws TypesetterException,
                ConfigurationException {

        listMaker.addSpace(typesettingContext, null);
    }

    /**
     * @see org.extex.typesetter.ListMaker#afterParagraph(
     *      ParagraphObserver)
     */
    public void afterParagraph(final ParagraphObserver observer) {

        listMaker.afterParagraph(observer);
    }

    /**
     * @see org.extex.typesetter.listMaker.ListManager#buildParagraph(
     *      org.extex.typesetter.type.node.HorizontalListNode)
     */
    public NodeList buildParagraph(final HorizontalListNode nodes)
            throws TypesetterException {

        return this.paragraphBuilder.build(nodes);
    }

    /**
     * @see org.extex.typesetter.Typesetter#clearShipoutMark()
     */
    public void clearShipoutMark() {

        shipoutMark = false;
    }

    /**
     * @see org.extex.typesetter.ListMaker#complete(TypesetterOptions)
     */
    public NodeList complete(final TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        NodeList nodes = listMaker.complete(context);
        pop();
        return nodes;
    }

    /**
     * @see org.extex.typesetter.ListMaker#cr(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.type.UnicodeChar)
     */
    public void cr(final Context context, final TypesettingContext tc,
            final UnicodeChar uc) throws TypesetterException {

        listMaker.cr(context, tc, uc);
    }

    /**
     * Setter for the localizer.
     *
     * @param theLocalizer the new localizer
     *
     * @see org.extex.util.framework.i18n.Localizable#enableLocalization(
     *      org.extex.util.framework.i18n.Localizer)
     */
    public void enableLocalization(final Localizer theLocalizer) {

        localizer = theLocalizer;
    }

    /**
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger theLogger) {

        logger = theLogger;
        if (pageBuilder instanceof LogEnabled) {
            ((LogEnabled) pageBuilder).enableLogging(theLogger);
        }
        if (paragraphBuilder instanceof LogEnabled) {
            ((LogEnabled) paragraphBuilder).enableLogging(theLogger);
        }
    }

    /**
     * @see org.extex.typesetter.listMaker.ListManager#endParagraph()
     */
    public void endParagraph()
            throws TypesetterException,
                ConfigurationException {

        NodeList list = listMaker.complete(options);
        pop();
        if (list instanceof VerticalListNode) {
            listMaker.addAndAdjust(list, options);
        } else if (list instanceof HorizontalListNode) {
            listMaker.add(list);
        } else {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                listMaker.add(list.get(i));
            }
        }
    }

    /**
     * @see org.extex.typesetter.listMaker.ListManager#ensureHorizontalMode(
     *      org.extex.type.Locator)
     */
    public ListMaker ensureHorizontalMode(final Locator locator)
            throws TypesetterException {

        if (!(listMaker instanceof HorizontalListMaker)) {
            push(new HorizontalListMaker(this, locator));
        }
        return listMaker;
    }

    /**
     * @see org.extex.typesetter.Typesetter#finish()
     */
    public void finish() throws TypesetterException, ConfigurationException {

        par();
        pageBuilder.flush(listMaker.complete(options), this);
        if (saveStack != null && saveStack.size() != 0) {
            throw new InternalError("typesetter.saveStack.notEmpty");
        }
        pageBuilder.close();
    }

    /**
     * Getter for back-end.
     *
     * @return the back-end
     *
     * @see org.extex.typesetter.Typesetter#getBackendDriver()
     */
    public BackendDriver getBackendDriver() {

        return this.backend;
    }

    /**
     * @see org.extex.typesetter.ListMaker#getLastNode()
     */
    public Node getLastNode() {

        return listMaker.getLastNode();
    }

    /**
     * @see org.extex.typesetter.Typesetter#getListMaker()
     */
    public ListMaker getListMaker() {

        return listMaker;
    }

    /**
     * @see org.extex.typesetter.ListMaker#getLocator()
     */
    public Locator getLocator() {

        return listMaker.getLocator();
    }

    /**
     * Getter for the manager of the list maker stack.
     * This instance also acts as a manager.
     *
     * @return this instance
     *
     * @see org.extex.typesetter.Typesetter#getManager()
     */
    public ListManager getManager() {

        return this;
    }

    /**
     * @see org.extex.typesetter.Typesetter#getMode()
     */
    public Mode getMode() {

        return listMaker.getMode();
    }

    /**
     * @see org.extex.typesetter.Typesetter#getNodeFactory()
     */
    public NodeFactory getNodeFactory() {

        return nodeFactory;
    }

    /**
     * @see org.extex.typesetter.listMaker.ListManager#getOptions()
     */
    public TypesetterOptions getOptions() {

        return options;
    }

    /**
     * @see org.extex.typesetter.ListMaker#getPrevDepth()
     */
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        return this.listMaker.getPrevDepth();
    }

    /**
     * @see org.extex.typesetter.ListMaker#getSpacefactor()
     */
    public long getSpacefactor() throws TypesetterUnsupportedException {

        return this.listMaker.getSpacefactor();
    }

    /**
     * @see org.extex.typesetter.Typesetter#isShipoutMark()
     */
    public boolean isShipoutMark() {

        return shipoutMark;
    }

    /**
     * Notification method to deal the case that a left brace has been
     * encountered.
     */
    public void leftBrace() {

        listMaker.leftBrace();
    }

    /**
     * @see org.extex.typesetter.ListMaker#letter(
     *      org.extex.type.UnicodeChar,
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.type.Locator)
     */
    public boolean letter(final UnicodeChar uc, final TypesettingContext tc,
            final Context context, TokenSource source, final Locator locator)
            throws TypesetterException {

        return listMaker.letter(uc, tc, context, source, locator);
    }

    /**
     * @see org.extex.typesetter.ListMaker#mathShift(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    public void mathShift(final Context context, final TokenSource source,
            final Token t) throws TypesetterException, ConfigurationException {

        ensureHorizontalMode(source.getLocator());
        listMaker.mathShift(context, source, t);
    }

    /**
     * @see org.extex.typesetter.ListMaker#par()
     */
    public void par() throws TypesetterException, ConfigurationException {

        listMaker.par();

        if (saveStack.size() == 0) {
            pageBuilder.inspectAndBuild((VerticalListNode) listMaker
                    .complete(options), this);
        }
    }

    /**
     * @see org.extex.typesetter.listMaker.ListManager#pop()
     */
    public ListMaker pop() throws TypesetterException {

        if (saveStack.isEmpty()) {
            throw new ImpossibleException("Typesetter.EmptyStack");
        }
        ListMaker current = listMaker;
        listMaker = (ListMaker) (saveStack.remove(saveStack.size() - 1));
        return current;
    }

    /**
     * @see org.extex.typesetter.listMaker.ListManager#push(
     *      org.extex.typesetter.ListMaker)
     */
    public void push(final ListMaker list) throws TypesetterException {

        saveStack.add(listMaker);
        listMaker = list;
    }

    /**
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    public void removeLastNode() {

        if (listMaker != null) {
            listMaker.removeLastNode();
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#rightBrace()
     */
    public void rightBrace() throws TypesetterException {

        listMaker.rightBrace();
    }

    /**
     * @see org.extex.typesetter.Typesetter#setBackend(
     *      org.extex.backend.BackendDriver)
     */
    public void setBackend(final BackendDriver driver) {

        backend = driver;
        pageBuilder.setBackend(driver);
    }

    /**
     * @see org.extex.typesetter.Typesetter#setNodeFactory(
     *      org.extex.typesetter.type.node.factory.NodeFactory)
     */
    public void setNodeFactory(final NodeFactory nodeFactory) {

        this.nodeFactory = nodeFactory;
    }

    /**
     * @see org.extex.typesetter.Typesetter#setOptions(
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void setOptions(final TypesetterOptions options) {

        this.options = options;
        pageBuilder.setOptions(options);
    }

    /**
     * @see org.extex.typesetter.Typesetter#setOutputRoutine(
     *      org.extex.typesetter.output.OutputRoutine)
     */
    public void setOutputRoutine(final OutputRoutine output) {

        this.outputRoutine = output;
        if (this.pageBuilder != null) {
            this.pageBuilder.setOutputRoutine(output);
        }
    }

    /**
     * Setter for the page builder.
     *
     * @param pageBuilder the new page builder
     *
     * @see org.extex.typesetter.Typesetter#setPageBuilder(
     *      org.extex.typesetter.pageBuilder.PageBuilder)
     */
    public void setPageBuilder(final PageBuilder pageBuilder) {

        this.pageBuilder = pageBuilder;
        pageBuilder.setBackend(backend);
        pageBuilder.setOutputRoutine(this.outputRoutine);
        if (pageBuilder instanceof LogEnabled) {
            ((LogEnabled) pageBuilder).enableLogging(logger);
        }
    }

    /**
     * Setter for paragraph builder.
     *
     * @param parBuilder the paragraph builder to set.
     */
    public void setParagraphBuilder(final ParagraphBuilder parBuilder) {

        paragraphBuilder = parBuilder;
        if (paragraphBuilder instanceof LogEnabled) {
            ((LogEnabled) paragraphBuilder).enableLogging(logger);
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#setPrevDepth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setPrevDepth(final FixedDimen pd)
            throws TypesetterUnsupportedException {

        listMaker.setPrevDepth(pd);
    }

    /**
     * @see org.extex.typesetter.ListMaker#setSpacefactor(
     *      org.extex.interpreter.type.count.FixedCount)
     */
    public void setSpacefactor(final FixedCount sf)
            throws TypesetterUnsupportedException,
                InvalidSpacefactorException {

        listMaker.setSpacefactor(sf);
    }

    /**
     * This is the entry point for the document writer. Here it receives a
     * complete node list to be sent to the output writer.
     *
     * @param nodes the nodes to send
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.Typesetter#shipout(
     *      org.extex.typesetter.type.NodeList)
     */
    public void shipout(final NodeList nodes) throws TypesetterException {

        pageBuilder.shipout(nodes, this);
        shipoutMark = true;
    }

    /**
     * @see org.extex.typesetter.ListMaker#showlist(java.lang.StringBuffer,
     *       long, long)
     */
    public void showlist(final StringBuffer sb, final long depth,
            final long breadth) {

        listMaker.showlist(sb, depth, breadth);
    }

    /**
     * @see org.extex.typesetter.Typesetter#showlists(
     *      java.lang.StringBuffer, long, long)
     */
    public void showlists(final StringBuffer sb, final long depth,
            final long breadth) {

        sb.append(localizer.format("Showlist.Format", listMaker.getMode()
                .toString(), Integer.toString(listMaker.getLocator()
                .getLineNumber())));
        listMaker.showlist(sb, depth, breadth);

        for (int i = saveStack.size() - 1; i >= 0; i--) {
            ListMaker lm = (ListMaker) saveStack.get(i);
            sb.append(localizer.format("Showlist.Format", lm.getMode()
                    .toString(), Integer.toString(lm.getLocator()
                    .getLineNumber())));
            lm.showlist(sb, depth, breadth);
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#subscriptMark(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public void subscriptMark(final Context context, final TokenSource source,
            final Typesetter typesetter, final Token t)
            throws TypesetterException {

        listMaker.subscriptMark(context, source, typesetter, t);
    }

    /**
     * @see org.extex.typesetter.Typesetter#superscriptMark(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      Typesetter, org.extex.scanner.type.token.Token)
     */
    public void superscriptMark(final Context context,
            final TokenSource source, final Typesetter typesetter, final Token t)
            throws TypesetterException {

        listMaker.superscriptMark(context, source, typesetter, t);
    }

    /**
     * @see org.extex.typesetter.Typesetter#tab(
     *      Context,
     *      TokenSource, org.extex.scanner.type.token.Token)
     */
    public void tab(final Context context, final TokenSource source,
            final Token t) throws TypesetterException, ConfigurationException {

        listMaker.tab(context, source, t);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return "stackSize = " + saveStack.size() + "\n" + listMaker.toString();
    }

}
