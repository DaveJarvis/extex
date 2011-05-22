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

package org.extex.typesetter.impl;

import org.extex.backend.BackendDriver;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
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
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.listMaker.TokenDelegateListMaker;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * The dummy typesetter which does nothing but provide the appropriate
 * interface.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4526 $
 */
public class NullTypesetterImpl implements Typesetter, TokenDelegateListMaker {

    /**
     * The field <tt>backend</tt> contains the back-end driver for producing the
     * output.
     */
    private BackendDriver backend = null;

    /**
     * Creates a new object.
     */
    public NullTypesetterImpl() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#add(org.extex.core.glue.FixedGlue)
     */
    @Override
    public void add(FixedGlue g) throws TypesetterException {

        // nothing to do
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

        // nothing to do
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

        // nothing to do
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
            FixedCount spacefactor)
            throws TypesetterException,
                ConfigurationException {

        // nothing to do
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

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#clearShipoutMark()
     */
    @Override
    public void clearShipoutMark() {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#complete(TypesetterOptions)
     */
    @Override
    public NodeList complete(TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        return null;
    }

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param config the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) {

        // nothing to do
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

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#ensureHorizontalMode(org.extex.core.Locator)
     */
    @Override
    public ListMaker ensureHorizontalMode(Locator locator) {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#finish()
     */
    @Override
    public void finish() {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#getBackendDriver()
     */
    @Override
    public BackendDriver getBackendDriver() {

        return this.backend;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#getLastNode()
     */
    @Override
    public Node getLastNode() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#getListMaker()
     */
    @Override
    public ListMaker getListMaker() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#getLocator()
     */
    @Override
    public Locator getLocator() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#getManager()
     */
    @Override
    public ListManager getManager() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#getMode()
     */
    @Override
    public Mode getMode() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#getNodeFactory()
     */
    @Override
    public NodeFactory getNodeFactory() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#getPrevDepth()
     */
    @Override
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#getSpacefactor()
     */
    @Override
    public long getSpacefactor() throws TypesetterUnsupportedException {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#isShipoutMark()
     */
    @Override
    public boolean isShipoutMark() {

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#leftBrace()
     */
    @Override
    public void leftBrace() {

        // nothing to do
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

        return false;
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
                ConfigurationException,
                HelpingException {

        // nothing to do
    }

    /**
     * Emit a new paragraph. This might be a noop under certain circumstances.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.Typesetter#par()
     */
    @Override
    public void par() throws TypesetterException, ConfigurationException {

        // nothing to do
    }

    /**
     * Open a new list maker and put it in the top of the stack as current box.
     * 
     * @param listMaker the list maker
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.Typesetter#push(org.extex.typesetter.ListMaker)
     */
    @Override
    public void push(ListMaker listMaker) throws TypesetterException {

        // nothing to do
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

        return null;
    }

    /**
     * Removes the last node from the list. If the list is empty then nothing is
     * done.
     * 
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    @Override
    public void removeLastNode() {

        // nothing to do
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

        // nothing to do
    }

    /**
     * Setter for the back-end driver. The back-end driver is addressed whenever
     * a complete page has to be shipped out.
     * 
     * @param driver the new back-end driver
     * 
     * @see org.extex.typesetter.Typesetter#setBackend(org.extex.backend.BackendDriver)
     */
    @Override
    public void setBackend(BackendDriver driver) {

        backend = driver;
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

        // nothing to do
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

        // nothing to do
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

        // nothing to do
    }

    /**
     * Setter for the page builder. Since the page builder is not needed this is
     * a noop.
     * 
     * @param pageBuilder the new page builder
     * 
     * @see org.extex.typesetter.Typesetter#setPageBuilder(org.extex.typesetter.pageBuilder.PageBuilder)
     */
    @Override
    public void setPageBuilder(PageBuilder pageBuilder) {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#setParagraphBuilder(org.extex.typesetter.paragraphBuilder.ParagraphBuilder)
     */
    @Override
    public void setParagraphBuilder(ParagraphBuilder paragraphBuilder) {

        // not supported yet
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#setPrevDepth(org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void setPrevDepth(FixedDimen pd) {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#setSpacefactor(org.extex.core.count.FixedCount)
     */
    @Override
    public void setSpacefactor(FixedCount sf) {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#shipout(org.extex.typesetter.type.NodeList)
     */
    @Override
    public void shipout(NodeList nodes) {

        // nothing to do
    }

    /**
     * Print the status for <tt>\showlists</tt>.
     * 
     * @param sb the target buffer
     * @param depth the depth of the list display
     * @param breadth the breadth of the list display
     * 
     * @see org.extex.typesetter.ListMaker#showlist(StringBuilder, long, long)
     */
    @Override
    public void showlist(StringBuilder sb, long depth, long breadth) {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.Typesetter#showlists(StringBuilder, long, long)
     */
    public void showlists(StringBuilder sb, long depth, long breadth) {

        // nothing to do
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

        // nothing to do
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

        // nothing to do
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

        // nothing to do
    }

}
