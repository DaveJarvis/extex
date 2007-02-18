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

package org.extex.typesetter.impl;

import org.extex.backend.BackendDriver;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
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
import org.extex.typesetter.listMaker.ListManager;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * The dummy typesetter which does nothing but provide the appropriate
 * interface.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4526 $
 */
public class NullTypesetterImpl implements Typesetter {

    /**
     * The field <tt>backend</tt> contains the back-end driver for
     * producing the output.
     */
    private BackendDriver backend = null;

    /**
     * Creates a new object.
     */
    public NullTypesetterImpl() {

        super();
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.interpreter.type.glue.FixedGlue)
     */
    public void add(final FixedGlue g) throws TypesetterException {

        // nothing to do
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
    public void add(final Node c) throws TypesetterException {

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
     * @see org.extex.typesetter.ListMaker#addAndAdjust(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addAndAdjust(final NodeList list,
            final TypesetterOptions options) throws TypesetterException {

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
            final Count spacefactor)
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
    public void afterParagraph(final ParagraphObserver observer) {

    }

    /**
     * Clear the internal state about shipouts.
     * The shipout mark is reset to <code>false</code>.
     *
     * @see org.extex.typesetter.Typesetter#clearShipoutMark()
     */
    public void clearShipoutMark() {

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
     * @see org.extex.typesetter.Typesetter#complete(TypesetterOptions)
     */
    public NodeList complete(final TypesetterOptions context)
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
     * @see org.extex.util.framework.configuration.Configurable#configure(
     *     org.extex.util.framework.configuration.Configuration)
     */
    public void configure(final Configuration config) {

        // nothing to do
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
     * @see org.extex.typesetter.Typesetter#ensureHorizontalMode(
     *      org.extex.type.Locator)
     */
    public ListMaker ensureHorizontalMode(Locator locator) {

        return null;
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
    public void finish() {

        // nothing to do
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

        return null;
    }

    /**
     * Getter for the current list maker.
     *
     * @return the top list maker or <code>null</code> if the stack is empty
     *
     * @see org.extex.typesetter.Typesetter#getListMaker()
     */
    public ListMaker getListMaker() {

        return null;
    }

    /**
     * Getter for the locator.
     *
     * @return the locator
     *
     * @see org.extex.typesetter.ListMaker#getLocator()
     */
    public Locator getLocator() {

        return null;
    }

    /**
     * Getter for the manager of the list maker stack.
     *
     * @return the manager
     *
     * @see org.extex.typesetter.Typesetter#getManager()
     */
    public ListManager getManager() {

        return null;
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

        return null;
    }

    /**
     * Getter for the NodeFactory.
     *
     * @return the node factory
     *
     * @see org.extex.typesetter.Typesetter#getNodeFactory()
     */
    public NodeFactory getNodeFactory() {

        return null;
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

        return null;
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

        return 0;
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

        return false;
    }

    /**
     * Notification method to deal the case that a left brace hs been
     * encountered.
     *
     * @see org.extex.typesetter.ListMaker#leftBrace()
     */
    public void leftBrace() {

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

        return false;
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
            final Token t) throws TypesetterException, ConfigurationException {

    }

    /**
     * Emit a new paragraph.
     * This might be a noop under certain circumstances.
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.Typesetter#par()
     */
    public void par() throws TypesetterException, ConfigurationException {

        // nothing to do
    }

    /**
     * Open a new list maker and put it in the top of the stack as current
     * box.
     *
     * @param listMaker the list maker
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.Typesetter#push(
     *      org.extex.typesetter.ListMaker)
     */
    public void push(final ListMaker listMaker) throws TypesetterException {

    }

    /**
     * Removes the last node from the list.
     * If the list is empty then nothing is done.
     *
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    public void removeLastNode() {

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

    }

    /**
     * Setter for the page builder.
     * Since the page builder is not needed this is a noop.
     *
     * @param pageBuilder the new page builder
     *
     * @see org.extex.typesetter.Typesetter#setPageBuilder(
     *       org.extex.typesetter.pageBuilder.PageBuilder)
     */
    public void setPageBuilder(final PageBuilder pageBuilder) {

    }

    /**
     * Setter for the paragraph builder.
     * Since the paragraph builder is not needed this is a noop.
     *
     * @param paragraphBuilder the new paragraph builder
     *
     * @see org.extex.typesetter.Typesetter#setParagraphBuilder(
     *       org.extex.typesetter.paragraphBuilder.ParagraphBuilder)
     */
    public void setParagraphBuilder(final ParagraphBuilder paragraphBuilder) {

    }

    /**
     * Setter for the previous depth parameter.
     *
     * @param pd the previous depth parameter
     *
     * @throws TypesetterUnsupportedException in case of an error
     *
     * @see org.extex.typesetter.ListMaker#setPrevDepth(
     *     org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setPrevDepth(final FixedDimen pd) {

        // nothing to do
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
     *     org.extex.interpreter.type.count.FixedCount)
     */
    public void setSpacefactor(final FixedCount f)
            throws InvalidSpacefactorException {

        // nothing to do
    }

    /**
     * Send a list of nodes to the document writer.
     * As a side effect the shipout mark is set.
     *
     * @param nodes the nodes to send to the typesetter
     *
     * @throws TypesetterException in case of an error
     *
     * @see org.extex.typesetter.Typesetter#shipout(
     *     org.extex.typesetter.type.NodeList)
     */
    public void shipout(final NodeList nodes) {

        // nothing to do
    }

    /**
     * Print the status for <tt>\showlists</tt>.
     *
     * @param sb the target buffer
     * @param depth the depth of the list display
     * @param breadth the breadth of the list display
     *
     * @see org.extex.typesetter.ListMaker#showlist(
     *      java.lang.StringBuffer, long, long)
     */
    public void showlist(final StringBuffer sb, final long l, final long m) {

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
     *      java.lang.StringBuffer,
     *      long,
     *      long)
     */
    public void showlists(final StringBuffer sb, final long l, final long m) {

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
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public void superscriptMark(final Context context,
            final TokenSource source, final Typesetter typesetter, final Token t)
            throws TypesetterException {

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
     * @see org.extex.typesetter.ListMaker#tab(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    public void tab(final Context context, final TokenSource source,
            final Token t) throws TypesetterException {

    }

}
