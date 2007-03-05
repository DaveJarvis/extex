/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
     * Add a glue node to the list.
     *
     * @param glue the glue to add
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.interpreter.type.glue.FixedGlue)
     */
    public void add(final FixedGlue glue) throws TypesetterException {

        listMaker.add(glue);
    }

    /**
     * Add an arbitrary node to the internal list of nodes gathered so far.
     * The node should not be one of the special nodes treated by methods of
     * their own.
     *
     * @param node the node to add
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#add(
     *     org.extex.typesetter.type.Node)
     */
    public void add(final Node node) throws TypesetterException {

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
     * Add a node list to the current list maker and adjust the spacing between
     * the elements of the list.
     *
     * @param list the list
     * @param options the options to use
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#addAndAdjust(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addAndAdjust(final NodeList list,
            final TypesetterOptions options) throws TypesetterException {

        listMaker.addAndAdjust(list, options);
    }

    /**
     * Add a space node to the list.
     *
     * @param typesettingContext the typesetting context for the space
     * @param spacefactor the space factor to use for this space or
     *  <code>null</code> to indicate that the default space factor should
     *  be used.
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#addSpace(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.interpreter.type.count.Count)
     */
    public void addSpace(final TypesettingContext typesettingContext,
            final Count spacefactor) throws TypesetterException {

        listMaker.addSpace(typesettingContext, null);
    }

    /**
     * Register an observer to be invoked at the end of the paragraph.
     *
     * @param observer the observer to register
     *
     * @see org.extex.typesetter.ListMaker#afterParagraph(
     *      ParagraphObserver)
     */
    public void afterParagraph(final ParagraphObserver observer) {

        listMaker.afterParagraph(observer);
    }

    /**
     * Invoke the paragraph builder on a list of nodes.
     *
     * @param nodes the nodes to make a paragraph from
     *
     * @return the vertical node list containing the lines of the paragraph
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.listMaker.ListManager#buildParagraph(
     *      org.extex.typesetter.type.node.HorizontalListNode)
     */
    public NodeList buildParagraph(final HorizontalListNode nodes)
            throws TypesetterException {

        return this.paragraphBuilder.build(nodes);
    }

    /**
     * Clear the internal state about shipouts.
     * The shipout mark is reset to <code>false</code>.
     *
     * @see #isShipoutMark()
     *
     * @see org.extex.typesetter.Typesetter#clearShipoutMark()
     */
    public void clearShipoutMark() {

        shipoutMark = false;
    }

    /**
     * Close the node list. This means that everything is done to ship the
     * closed node list to the document writer. Nevertheless the invoking
     * application might decide not to modify the node list and continue
     * processing. In the other case some  nodes might be taken from the node
     * list returned by this method. Then the processing has to continue with
     * the reduced node list.
     *
     * @param context the typesetter options mapping a fragment of the
     *  interpreter context
     *
     * @return the node list enclosed in this instance
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#complete(
     *      org.extex.typesetter.TypesetterOptions)
     */
    public NodeList complete(final TypesetterOptions context)
            throws TypesetterException {

        NodeList nodes = listMaker.complete(context);
        pop();
        return nodes;
    }

    /**
     * Process a carriage return.
     *
     * @param context the interpreter context
     * @param tc the typesetting context
     * @param uc the character
     *
     * @throws TypesetterException in case of an error
     *
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
     * Setter for the logger.
     *
     * @param theLogger the logger to use
     *
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
     * End the current paragraph.
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     *
     * @see org.extex.typesetter.listMaker.ListManager#endParagraph()
     */
    public void endParagraph() throws TypesetterException {

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
     * Switch to horizontal mode if necessary.
     * If the current mode is a horizontal mode then nothing is done.
     *
     * @param locator the locator
     *
     * @return the horizontal list maker
     *
     * @throws TypesetterException in case of an error
     *
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
     * Instructs the typesetter to perform any actions necessary for cleaning up
     * everything at the end of processing. This should involve a shipout of
     * any material still left unprocessed.
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     *
     * @see org.extex.typesetter.Typesetter#finish()
     */
    public void finish() throws TypesetterException {

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
     * Access the last node on the list.
     *
     * @return the last node in the current list or <code>null</code> if the
     *   list is empty
     *
     * @see org.extex.typesetter.ListMaker#getLastNode()
     */
    public Node getLastNode() {

        return listMaker.getLastNode();
    }

    /**
     * Getter for the current list maker.
     *
     * @return the top list maker or <code>null</code> if the stack is empty
     *
     * @see org.extex.typesetter.Typesetter#getListMaker()
     */
    public ListMaker getListMaker() {

        return listMaker;
    }

    /**
     * Getter for the locator.
     *
     * @return the locator
     *
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
     * Getter for the current mode.
     *
     * @return the mode which is one of the values defined in
     * {@link org.extex.typesetter.Mode Mode}.
     *
     * @see org.extex.typesetter.Typesetter#getMode()
     */
    public Mode getMode() {

        return listMaker.getMode();
    }

    /**
     * Getter for the NodeFactory.
     *
     * @return the node factory
     *
     * @see org.extex.typesetter.Typesetter#getNodeFactory()
     */
    public NodeFactory getNodeFactory() {

        return nodeFactory;
    }

    /**
     * Getter for the options object.
     *
     * @return the options
     *
     * @see org.extex.typesetter.listMaker.ListManager#getOptions()
     */
    public TypesetterOptions getOptions() {

        return options;
    }

    /**
     * Getter for the previous depth parameter.
     *
     * @return the previous depth
     *
     * @throws TypesetterUnsupportedException in case of an error
     *
     * @see org.extex.typesetter.ListMaker#getPrevDepth()
     */
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        return this.listMaker.getPrevDepth();
    }

    /**
     * Getter for the space factor.
     *
     * @return the space factor
     *
     * @throws TypesetterUnsupportedException in case of an error
     *
     * @see org.extex.typesetter.ListMaker#getSpacefactor()
     */
    public long getSpacefactor() throws TypesetterUnsupportedException {

        return this.listMaker.getSpacefactor();
    }

    /**
     * Query the shipout mark.
     * The shipout mark is an internal state which records whether or not the
     * shipout method has been called recently. This method can be used to
     * get the current state.
     * The method {@link #clearShipoutMark() clearShipoutMark()} can be used to
     * reset the shipout mark to <code>false</code>.
     * Initially the shipout mark is <code>false</code>.
     *
     * @return <code>true</code> iff there has been an invocation to the method
     *  {@link #shipout(NodeList) shipout()} since the last clearing
     * @see #clearShipoutMark()
     *
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
     * Add a letter to the current list or treat it in some other appropriate
     * way.
     *
     * @param tc the typesetting context
     * @param uc the character
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param locator the locator
     *
     * @return <code>true</code> iff the character has been discarded because
     *   it is not defined in the current font.
     *
     * @throws TypesetterException in case of an error
     *
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
     * Treat a math shift character.
     * Usually this leads to entering or leaving math mode &ndash; maybe after
     * inspection of a following token.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param t the actual math shift character token
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#mathShift(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    public void mathShift(final Context context, final TokenSource source,
            final Token t) throws TypesetterException {

        ensureHorizontalMode(source.getLocator());
        listMaker.mathShift(context, source, t);
    }

    /**
     * Emit a new paragraph.
     * This might be a noop under certain circumstances.
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#par()
     */
    public void par() throws TypesetterException {

        listMaker.par();

        if (saveStack.size() == 0) {
            pageBuilder.inspectAndBuild((VerticalListNode) listMaker
                .complete(options), this);
        }
    }

    /**
     * Discard the top of the stack of list makers.
     *
     * @return the list maker popped from the stack
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.listMaker.ListManager#pop()
     */
    public ListMaker pop() throws TypesetterException {

        if (saveStack.isEmpty()) {
            throw new ImpossibleException("Typesetter.EmptyStack");
        }
        ListMaker current = listMaker;
        this.listMaker = (ListMaker) (saveStack.remove(saveStack.size() - 1));
        return current;
    }

    /**
     * Push a new element to the stack of list makers.
     *
     * @param listMaker the new element to push
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.listMaker.ListManager#push(
     *      org.extex.typesetter.ListMaker)
     */
    public void push(final ListMaker listMaker) throws TypesetterException {

        saveStack.add(this.listMaker);
        this.listMaker = listMaker;
    }

    /**
     * Removes the last node from the list.
     * If the list is empty then nothing is done.
     *
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    public void removeLastNode() {

        if (listMaker != null) {
            listMaker.removeLastNode();
        }
    }

    /**
     * Notification method to deal the case that a right brace has been
     * encountered.
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.ListMaker#rightBrace()
     */
    public void rightBrace() throws TypesetterException {

        listMaker.rightBrace();
    }

    /**
     * Setter for the back-end driver.
     * The back-end driver is addressed whenever a complete page has to be
     * shipped out.
     *
     * @param driver the new back-end driver
     *
     * @see org.extex.typesetter.Typesetter#setBackend(
     *      org.extex.backend.BackendDriver)
     */
    public void setBackend(final BackendDriver driver) {

        backend = driver;
        pageBuilder.setBackend(driver);
    }

    /**
     * Setter for the node factory.
     *
     * @param nodeFactory the node factory
     *
     * @see org.extex.typesetter.Typesetter#setNodeFactory(
     *      org.extex.typesetter.type.node.factory.NodeFactory)
     */
    public void setNodeFactory(final NodeFactory nodeFactory) {

        this.nodeFactory = nodeFactory;
    }

    /**
     * Setter for the typesetter specific options.
     *
     * @param options the options to use
     *
     * @see org.extex.typesetter.Typesetter#setOptions(
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void setOptions(final TypesetterOptions options) {

        this.options = options;
        pageBuilder.setOptions(options);
    }

    /**
     * Setter for the output routine.
     *
     * @param output the output routine
     *
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
     * Setter for the previous depth parameter.
     *
     * @param pd the previous depth parameter
     *
     * @throws TypesetterUnsupportedException in case of an error
     *
     * @see org.extex.typesetter.ListMaker#setPrevDepth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setPrevDepth(final FixedDimen pd)
            throws TypesetterUnsupportedException {

        listMaker.setPrevDepth(pd);
    }

    /**
     * Setter for the space factor.
     *
     * @param sf the space factor to set
     *
     * @throws TypesetterUnsupportedException in case of an error
     * @throws InvalidSpacefactorException in case of an invalid space factor
     *
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
     * This method produces a diagnostic representation of the current lists in
     * a StringBuffer.
     *
     * @param sb the target string buffer
     * @param depth the depth for the display
     * @param breadth the breadth of the display
     *
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
            sb
                .append(localizer.format("Showlist.Format", lm.getMode()
                    .toString(), Integer.toString(lm.getLocator()
                    .getLineNumber())));
            lm.showlist(sb, depth, breadth);
        }
    }

    /**
     * Treat a subscript mark. This might be meaningful in math mode only.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param t the actual sub mark token
     *
     * @throws TypesetterException in case of an error
     *
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
     * Treat a superscript mark. This might be meaningful in math mode only.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param t the actual super mark token
     *
     * @throws TypesetterException in case of an error
     *
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
     * Treat a alignment tab character.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param t the actual tab token
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.Typesetter#tab(
     *      Context,
     *      TokenSource, org.extex.scanner.type.token.Token)
     */
    public void tab(final Context context, final TokenSource source,
            final Token t) throws TypesetterException {

        listMaker.tab(context, source, t);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return "stackSize = " + saveStack.size() + "\n" + listMaker.toString();
    }

}
