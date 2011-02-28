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

package org.extex.interpreter;

import static org.junit.Assert.assertEquals;

import org.extex.backend.BackendDriver;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.interpreter.context.Context;
import org.extex.scanner.stream.TokenStreamFactory;
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
import org.extex.typesetter.listMaker.TokenDelegateListMaker;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
public class Max1 {

    /**
     * Inner class to collect the things the typesetter sees.
     */
    private static class TestTypesetter
            implements
                Typesetter,
                TokenDelegateListMaker {

        /**
         */
        private StringBuilder sb = new StringBuilder();

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#add(org.extex.core.glue.FixedGlue)
         */
        @Override
        public void add(FixedGlue g) {

            sb.append(g.toString());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#add(org.extex.typesetter.type.Node)
         */
        @Override
        public void add(Node c) {

            // nothing to do
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#addAndAdjust(org.extex.typesetter.type.NodeList,
         *      org.extex.typesetter.TypesetterOptions)
         */
        @Override
        public void addAndAdjust(NodeList list, TypesetterOptions options)
                throws TypesetterException {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#addSpace(org.extex.typesetter.tc.TypesettingContext,
         *      FixedCount)
         */
        @Override
        public void addSpace(TypesettingContext typesettingContext,
                FixedCount spacefactor) {

            sb.append(" ");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#afterParagraph(ParagraphObserver)
         */
        @Override
        public void afterParagraph(ParagraphObserver observer) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#clearShipoutMark()
         */
        @Override
        public void clearShipoutMark() {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#complete(TypesetterOptions)
         */
        @Override
        public NodeList complete(TypesetterOptions context) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#cr(org.extex.interpreter.context.Context,
         *      org.extex.typesetter.tc.TypesettingContext,
         *      org.extex.core.UnicodeChar)
         */
        @Override
        public void cr(Context context, TypesettingContext tc, UnicodeChar uc) {

            // not needed
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

            return null;
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
        public FixedDimen getPrevDepth() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#getSpacefactor()
         */
        @Override
        public long getSpacefactor() {

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

            // not needed
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
                Context context, TokenSource source, Locator locator) {

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
                throws HelpingException {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#par()
         */
        @Override
        public void par() {

            sb.append("\n\\par\n");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#push(org.extex.typesetter.ListMaker)
         */
        @Override
        public void push(ListMaker listMaker) {

            // not needed
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

            // TODO gene: pushListMaker unimplemented
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#removeLastNode()
         */
        @Override
        public void removeLastNode() {

            // nothing to do
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#rightBrace()
         */
        @Override
        public void rightBrace() throws TypesetterException {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#setBackend(org.extex.backend.BackendDriver)
         */
        @Override
        public void setBackend(BackendDriver doc) {

            // nothing to do
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#setNodeFactory(org.extex.typesetter.type.node.factory.NodeFactory)
         */
        @Override
        public void setNodeFactory(NodeFactory nodeFactory) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#setOptions(org.extex.typesetter.TypesetterOptions)
         */
        @Override
        public void setOptions(TypesetterOptions options) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#setOutputRoutine(org.extex.typesetter.output.OutputRoutine)
         */
        @Override
        public void setOutputRoutine(OutputRoutine output) {

            // not needed
        }

        /**
         * {@inheritDoc}
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

            // nothing to do
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
        public void setSpacefactor(FixedCount f)
                throws InvalidSpacefactorException {

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
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ListMaker#showlist(java.lang.StringBuffer,
         *      long, long)
         */
        @Override
        public void showlist(StringBuffer s, long l, long m) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.Typesetter#showlists(java.lang.StringBuffer,
         *      long, long)
         */
        @Override
        public void showlists(StringBuffer s, long l, long m) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#subscriptMark(org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      org.extex.scanner.type.token.Token)
         */
        @Override
        public void subscriptMark(Context context, TokenSource source,
                Typesetter typesetter, Token t) throws HelpingException {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#superscriptMark(org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      org.extex.scanner.type.token.Token)
         */
        @Override
        public void superscriptMark(Context context, TokenSource source,
                Typesetter typesetter, Token t) throws HelpingException {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.listMaker.TokenDelegateListMaker#tab(org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.scanner.type.token.Token)
         */
        @Override
        public void tab(Context context, TokenSource source, Token t) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return sb.toString();
        }

    }

    /**
     * Perform a test.
     * 
     * @param in the input string
     * 
     * @return the result captured by the typesetter
     * 
     * @throws Exception in case of an error
     */
    private String doTest(String in) throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/tex.xml");

        Interpreter interpreter =
                new InterpreterFactory(config.getConfiguration("Interpreter"),
                    null).newInstance(null, null);
        TokenStreamFactory factory = new TokenStreamFactory("base");
        factory.configure(config.getConfiguration("Scanner"));
        // factory.setResourceFinder(finder);
        interpreter.setTokenStreamFactory(factory);

        TestTypesetter typesetter = new TestTypesetter();
        interpreter.setTypesetter(typesetter);

        // TODO gene provide a stream
        // TokenStream stream = new TokenStreamImpl(null, null, in, "");
        // interpreter.run(stream);

        return typesetter.toString();
    }

    /**
     * Trivial case: nothing in and nothing out
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty() throws Exception {

        assertEquals("", doTest(""));
    }

    /**
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro1() throws Exception {

        assertEquals("", doTest("\\relax"));
    }

    /**
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro2() throws Exception {

        assertEquals("\n\\par\n", doTest("\\par"));
    }

    /**
     * @throws Exception in case of an error
     */
    @Test
    public void testSingle1() throws Exception {

        assertEquals("a", doTest("a"));
    }

    /**
     * @throws Exception in case of an error
     */
    @Test
    public void testSingle2() throws Exception {

        assertEquals("A", doTest("A"));
    }

    /**
     * @throws Exception in case of an error
     */
    @Test
    public void testSingle3() throws Exception {

        assertEquals("2", doTest("2"));
    }

    /**
     * @throws Exception in case of an error
     */
    @Test
    public void testSingle4() throws Exception {

        assertEquals(".", doTest("."));
    }

}
