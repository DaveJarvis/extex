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
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.*;
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
*/
@SuppressWarnings("unused")
public class NullTypesetterImpl implements Typesetter, TokenDelegateListMaker {

    /**
     * The field {@code backend} contains the back-end driver for producing the
     * output.
     */
    private BackendDriver backend = null;


    public NullTypesetterImpl() {

    }

@Override
    public void add(FixedGlue g) throws TypesetterException {

        // nothing to do
    }

@Override
    public void add(Node node) throws TypesetterException {

        // nothing to do
    }

    /**
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
     *        {@code null} to indicate that the default space factor should
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

@Override
    public void clearShipoutMark() {

        // nothing to do
    }

@Override
    public NodeList complete(TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        return null;
    }

    /**
*      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public void cr(Context context, TypesettingContext tc, UnicodeChar uc)
            throws TypesetterException {

        // nothing to do
    }

@Override
    public ListMaker ensureHorizontalMode(Locator locator) {

        return null;
    }

@Override
    public void finish() {

        // nothing to do
    }

@Override
    public BackendDriver getBackendDriver() {

        return this.backend;
    }

@Override
    public Node getLastNode() {

        return null;
    }

@Override
    public ListMaker getListMaker() {

        return null;
    }

@Override
    public Locator getLocator() {

        return null;
    }

@Override
    public ListManager getManager() {

        return null;
    }

@Override
    public Mode getMode() {

        return null;
    }

@Override
    public NodeFactory getNodeFactory() {

        return null;
    }

@Override
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        return null;
    }

@Override
    public long getSpacefactor() throws TypesetterUnsupportedException {

        return 0;
    }

@Override
    public boolean isShipoutMark() {

        return false;
    }

@Override
    public void leftBrace() {

        // nothing to do
    }

    /**
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

@Override
    public void setParagraphBuilder(ParagraphBuilder paragraphBuilder) {

        // not supported yet
    }

@Override
    public void setPrevDepth(FixedDimen pd) {

        // nothing to do
    }

@Override
    public void setSpacefactor(FixedCount sf) {

        // nothing to do
    }

@Override
    public void shipout(NodeList nodes) {

        // nothing to do
    }

    /**
     * Print the status for {@code \showlists}.
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

@Override
    public void showlists(StringBuilder sb, long depth, long breadth) {

        // nothing to do
    }

    /**
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
*      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void tab(Context context, TokenSource source, Token t)
            throws TypesetterException {

        // nothing to do
    }

}
