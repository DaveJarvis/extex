/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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
import java.util.List;
import java.util.logging.Logger;

import org.extex.backend.BackendDriver;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.ListMakers;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.ListMakerType;
import org.extex.typesetter.ListManager;
import org.extex.typesetter.Mode;
import org.extex.typesetter.ParagraphObserver;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.InvalidSpacefactorException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.listMaker.HorizontalListMaker;
import org.extex.typesetter.listMaker.TokenDelegateListMaker;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.InsertionNode;
import org.extex.typesetter.type.node.PenaltyNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.factory.CachingNodeFactory;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This is a reference implementation of the
 * {@link org.extex.typesetter.Typesetter Typesetter} interface.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4526 $
 */
public class TypesetterImpl extends ListMakerFactory
        implements
            Typesetter,
            ListManager,
            TokenDelegateListMaker,
            LogEnabled {

    /**
     * The field <tt>backend</tt> contains the back-end driver for producing the
     * output.
     */
    private BackendDriver backend = null;

    /**
     * The field <tt>listMaker</tt> contains the current list maker for
     * efficiency. Thus we can avoid to peek at the stack whenever the list
     * maker is needed.
     */
    private TokenDelegateListMaker listMaker;

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
    private List<ListMaker> saveStack = new ArrayList<ListMaker>();

    /**
     * The field <tt>shipoutMark</tt> contains the recorded state of the
     * ship-out mark. Initially the ship-out mark is <code>false</code>.
     */
    private boolean shipoutMark = false;

    /**
     * Creates a new object and initializes it to receive material. To make it
     * fully functionality is required that the paragraph builder and the
     * ligature builder are provided before they are used.
     */
    public TypesetterImpl() {

        listMaker = createListMaker(this, ListMakers.VERTICAL, //
            new Locator("", 0, "", 0));
        // TODO gene: find better initial locator
    }

    /**
     * Add a glue node to the list.
     * 
     * @param glue the glue to add
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.ListMaker#add(org.extex.core.glue.FixedGlue)
     */
    @Override
    public void add(FixedGlue glue) throws TypesetterException {

        listMaker.add(glue);
    }

    /**
     * Add an arbitrary node to the internal list of nodes gathered so far. The
     * node should not be one of the special nodes treated by methods of their
     * own.
     * 
     * @param node the node to add
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.ListMaker#add(org.extex.typesetter.type.Node)
     */
    @Override
    public void add(Node node) throws TypesetterException {

        if (node == null) {
            return;
        }

        listMaker.add(node);

        if (saveStack == null
                && (node instanceof PenaltyNode
                        || node instanceof InsertionNode
                        || node instanceof HorizontalListNode //
                || node instanceof VerticalListNode)) {

            pageBuilder.inspectAndBuild(
                (VerticalListNode) listMaker.complete(options), this);
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
     * @see org.extex.typesetter.ListMaker#addAndAdjust(org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    @Override
    public void addAndAdjust(NodeList list, TypesetterOptions options)
            throws TypesetterException {

        listMaker.addAndAdjust(list, options);
    }

    /**
     * Add a space node to the list.
     * 
     * @param typesettingContext the typesetting context for the space
     * @param spacefactor the space factor to use for this space or
     *        <code>null</code> to indicate that the default space factor should
     *        be used.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.ListMaker#addSpace(org.extex.typesetter.tc.TypesettingContext,
     *      FixedCount)
     */
    @Override
    public void addSpace(TypesettingContext typesettingContext,
            FixedCount spacefactor) throws TypesetterException {

        listMaker.addSpace(typesettingContext, null);
    }

    /**
     * Register an observer to be invoked at the end of the paragraph.
     * 
     * @param observer the observer to register
     * 
     * @see org.extex.typesetter.ListMaker#afterParagraph(ParagraphObserver)
     */
    @Override
    public void afterParagraph(ParagraphObserver observer) {

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
     * @see org.extex.typesetter.ListManager#buildParagraph(org.extex.typesetter.type.node.HorizontalListNode)
     */
    @Override
    public NodeList buildParagraph(HorizontalListNode nodes)
            throws TypesetterException {

        return this.paragraphBuilder.build(nodes);
    }

    /**
     * Clear the internal state about ship-outs. The ship-out mark is reset to
     * <code>false</code>.
     * 
     * @see #isShipoutMark()
     * 
     * @see org.extex.typesetter.Typesetter#clearShipoutMark()
     */
    @Override
    public void clearShipoutMark() {

        shipoutMark = false;
    }

    /**
     * Close the node list. This means that everything is done to ship the
     * closed node list to the document writer. Nevertheless the invoking
     * application might decide not to modify the node list and continue
     * processing. In the other case some nodes might be taken from the node
     * list returned by this method. Then the processing has to continue with
     * the reduced node list.
     * 
     * @param context the typesetter options mapping a fragment of the
     *        interpreter context
     * 
     * @return the node list enclosed in this instance
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.ListMaker#complete(org.extex.typesetter.TypesetterOptions)
     */
    @Override
    public NodeList complete(TypesetterOptions context)
            throws TypesetterException {

        NodeList nodes = listMaker.complete(context);
        pop();
        return nodes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#cr(org.extex.interpreter.context.Context,
     *      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public void cr(Context context, TypesettingContext tc, UnicodeChar uc)
            throws TypesetterException {

        listMaker.cr(context, tc, uc);
    }

    /**
     * Setter for the logger.
     * 
     * @param theLogger the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
    public void enableLogging(Logger theLogger) {

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
     * @see org.extex.typesetter.ListManager#endParagraph()
     */
    @Override
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
     * Switch to horizontal mode if necessary. If the current mode is a
     * horizontal mode then nothing is done.
     * 
     * @param locator the locator
     * 
     * @return the horizontal list maker
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.ListManager#ensureHorizontalMode(org.extex.core.Locator)
     */
    @Override
    public ListMaker ensureHorizontalMode(Locator locator)
            throws TypesetterException {

        if (!(listMaker instanceof HorizontalListMaker)) { // TODO gene: dirty!
            pushListMaker(ListMakers.HORIZONTAL, locator);
        }
        return listMaker;
    }

    /**
     * Instructs the typesetter to perform any actions necessary for cleaning up
     * everything at the end of processing. This should involve a ship-out of
     * any material still left unprocessed.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     * 
     * @see org.extex.typesetter.Typesetter#finish()
     */
    @Override
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
    @Override
    public BackendDriver getBackendDriver() {

        return this.backend;
    }

    /**
     * Access the last node on the list.
     * 
     * @return the last node in the current list or <code>null</code> if the
     *         list is empty
     * 
     * @see org.extex.typesetter.ListMaker#getLastNode()
     */
    @Override
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
    @Override
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
    @Override
    public Locator getLocator() {

        return listMaker.getLocator();
    }

    /**
     * Getter for the manager of the list maker stack. This instance also acts
     * as a manager.
     * 
     * @return this instance
     * 
     * @see org.extex.typesetter.Typesetter#getManager()
     */
    @Override
    public ListManager getManager() {

        return this;
    }

    /**
     * Getter for the current mode.
     * 
     * @return the mode which is one of the values defined in
     *         {@link org.extex.typesetter.Mode Mode}.
     * 
     * @see org.extex.typesetter.Typesetter#getMode()
     */
    @Override
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
    @Override
    public NodeFactory getNodeFactory() {

        return nodeFactory;
    }

    /**
     * Getter for the options object.
     * 
     * @return the options
     * 
     * @see org.extex.typesetter.ListManager#getOptions()
     */
    @Override
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
    @Override
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
    @Override
    public long getSpacefactor() throws TypesetterUnsupportedException {

        return this.listMaker.getSpacefactor();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#isShipoutMark()
     */
    @Override
    public boolean isShipoutMark() {

        return shipoutMark;
    }

    /**
     * Notification method to deal the case that a left brace has been
     * encountered.
     */
    @Override
    public void leftBrace() {

        listMaker.leftBrace();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#letter(org.extex.core.UnicodeChar,
     *      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.core.Locator)
     */
    @Override
    public boolean letter(UnicodeChar uc, TypesettingContext tc,
            Context context, TokenSource source, Locator locator)
            throws TypesetterException {

        return listMaker.letter(uc, tc, context, source, locator);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#mathShift(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void mathShift(Context context, TokenSource source, Token t)
            throws TypesetterException,
                HelpingException {

        ensureHorizontalMode(source.getLocator());
        listMaker.mathShift(context, source, t);
    }

    /**
     * Emit a new paragraph. This might be a noop under certain circumstances.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.ListMaker#par()
     */
    @Override
    public void par() throws TypesetterException {

        listMaker.par();

        if (saveStack.size() == 0) {
            pageBuilder.inspectAndBuild(
                (VerticalListNode) listMaker.complete(options), this);
        }
    }

    /**
     * Discard the top of the stack of list makers.
     * 
     * @return the list maker popped from the stack
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.ListManager#pop()
     */
    @Override
    public TokenDelegateListMaker pop() throws TypesetterException {

        if (saveStack.isEmpty()) {
            throw new ImpossibleException("Typesetter.EmptyStack");
        }
        TokenDelegateListMaker current = listMaker;
        // TODO gene: beware of ClassCastException
        this.listMaker =
                (TokenDelegateListMaker) saveStack.remove(saveStack.size() - 1);
        return current;
    }

    /**
     * Push a new element to the stack of list makers.
     * 
     * @param listMaker the new element to push
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.ListManager#push(org.extex.typesetter.ListMaker)
     */
    @Override
    public void push(ListMaker listMaker) throws TypesetterException {

        saveStack.add(this.listMaker);
        if (listMaker instanceof TokenDelegateListMaker) {
            this.listMaker = (TokenDelegateListMaker) listMaker;
        } else {
            throw new RuntimeException("unimpemented");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#pushListMaker(org.extex.typesetter.ListMakerType,
     *      org.extex.core.Locator)
     */
    @Override
    public ListMaker pushListMaker(ListMakerType type, Locator locator)
            throws UnsupportedOperationException,
                TypesetterException {

        TokenDelegateListMaker lm = createListMaker(this, type, locator);
        push(lm);
        return lm;
    }

    /**
     * Removes the last node from the list. If the list is empty then nothing is
     * done.
     * 
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    @Override
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
    @Override
    public void rightBrace() throws TypesetterException {

        listMaker.rightBrace();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#setBackend(org.extex.backend.BackendDriver)
     */
    @Override
    public void setBackend(BackendDriver driver) {

        backend = driver;
        pageBuilder.setBackend(driver);
    }

    /**
     * Setter for the node factory.
     * 
     * @param nodeFactory the node factory
     * 
     * @see org.extex.typesetter.Typesetter#setNodeFactory(org.extex.typesetter.type.node.factory.NodeFactory)
     */
    @Override
    public void setNodeFactory(NodeFactory nodeFactory) {

        this.nodeFactory = nodeFactory;
    }

    /**
     * Setter for the typesetter specific options.
     * 
     * @param options the options to use
     * 
     * @see org.extex.typesetter.Typesetter#setOptions(org.extex.typesetter.TypesetterOptions)
     */
    @Override
    public void setOptions(TypesetterOptions options) {

        this.options = options;
        pageBuilder.setOptions(options);
    }

    /**
     * Setter for the output routine.
     * 
     * @param output the output routine
     * 
     * @see org.extex.typesetter.Typesetter#setOutputRoutine(org.extex.typesetter.output.OutputRoutine)
     */
    @Override
    public void setOutputRoutine(OutputRoutine output) {

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
     * @see org.extex.typesetter.Typesetter#setPageBuilder(org.extex.typesetter.pageBuilder.PageBuilder)
     */
    @Override
    public void setPageBuilder(PageBuilder pageBuilder) {

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
    @Override
    public void setParagraphBuilder(ParagraphBuilder parBuilder) {

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
     * @see org.extex.typesetter.ListMaker#setPrevDepth(org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void setPrevDepth(FixedDimen pd)
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
     * @see org.extex.typesetter.ListMaker#setSpacefactor(org.extex.core.count.FixedCount)
     */
    @Override
    public void setSpacefactor(FixedCount sf)
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
     * @see org.extex.typesetter.Typesetter#shipout(org.extex.typesetter.type.NodeList)
     */
    @Override
    public void shipout(NodeList nodes) throws TypesetterException {

        pageBuilder.shipout(nodes, this);
        shipoutMark = true;
    }

    /**
     * @see org.extex.typesetter.ListMaker#showlist(StringBuilder, long, long)
     */
    @Override
    public void showlist(StringBuilder sb, long depth, long breadth) {

        listMaker.showlist(sb, depth, breadth);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#showlists(StringBuilder, long, long)
     */
    @Override
    public void showlists(StringBuilder sb, long depth, long breadth) {

        localizer = LocalizerFactory.getLocalizer(getClass());

        sb.append(localizer.format("Showlist.Format", listMaker.getMode()
            .toString(), Integer.toString(listMaker.getLocator()
            .getLineNumber())));
        listMaker.showlist(sb, depth, breadth);

        for (int i = saveStack.size() - 1; i >= 0; i--) {
            ListMaker lm = saveStack.get(i);
            sb.append(localizer.format("Showlist.Format", //
                lm.getMode().toString(), //
                Integer.toString(lm.getLocator().getLineNumber())));
            lm.showlist(sb, depth, breadth);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#subscriptMark(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void subscriptMark(Context context, TokenSource source,
            Typesetter typesetter, Token t)
            throws TypesetterException,
                HelpingException {

        listMaker.subscriptMark(context, source, typesetter, t);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#superscriptMark(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void superscriptMark(Context context, TokenSource source,
            Typesetter typesetter, Token t)
            throws TypesetterException,
                HelpingException {

        listMaker.superscriptMark(context, source, typesetter, t);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#tab(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void tab(Context context, TokenSource source, Token t)
            throws TypesetterException {

        listMaker.tab(context, source, t);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "stackSize = " + saveStack.size() + "\n" + listMaker.toString();
    }

}
