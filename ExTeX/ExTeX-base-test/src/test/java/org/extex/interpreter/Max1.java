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
import org.extex.typesetter.*;
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
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Ignored because config/tex.xml is missing.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
@Ignore
public class Max1 {

    /**
     * Inner class to collect the things the typesetter sees.
     */
    private static class TestTypesetter
            implements
                Typesetter,
                TokenDelegateListMaker {

        private final StringBuilder sb = new StringBuilder();

        @Override
        public void add(FixedGlue g) {

            sb.append(g.toString());
        }

        @Override
        public void add(Node c) {

            // nothing to do
        }

        @Override
        public void addAndAdjust(NodeList list, TypesetterOptions options)
                throws TypesetterException {

            // not needed
        }

        @Override
        public void addSpace(TypesettingContext typesettingContext,
                FixedCount spacefactor) {

            sb.append(" ");
        }

        @Override
        public void afterParagraph(ParagraphObserver observer) {

            // not needed
        }

        @Override
        public void clearShipoutMark() {

            // not needed
        }

        @Override
        public NodeList complete(TypesetterOptions context) {

            return null;
        }

        @Override
        public void cr(Context context, TypesettingContext tc, UnicodeChar uc) {

            // not needed
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

            return null;
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
        public FixedDimen getPrevDepth() {

            return null;
        }

        
        @Override
        public long getSpacefactor() {

            return 0;
        }

        
        @Override
        public boolean isShipoutMark() {

            return false;
        }

        
        @Override
        public void leftBrace() {

            // not needed
        }

        @Override
        public boolean letter(UnicodeChar uc, TypesettingContext tc,
                Context context, TokenSource source, Locator locator) {

            return false;
        }

        @Override
        public void mathShift(Context context, TokenSource source, Token t)
                throws HelpingException {

            // not needed
        }

        @Override
        public void par() {

            sb.append("\n\\par\n");
        }

        @Override
        public void push(ListMaker listMaker) {

            // not needed
        }

        @Override
        public ListMaker pushListMaker(ListMakerType type, Locator locator)
                throws UnsupportedOperationException,
                    TypesetterException {

            // TODO gene: pushListMaker unimplemented
            return null;
        }

        
        @Override
        public void removeLastNode() {

            // nothing to do
        }

        
        @Override
        public void rightBrace() throws TypesetterException {

            // not needed
        }

        
        @Override
        public void setBackend(BackendDriver doc) {

            // nothing to do
        }

        
        @Override
        public void setNodeFactory(NodeFactory nodeFactory) {

            // not needed
        }

        
        @Override
        public void setOptions(TypesetterOptions options) {

            // not needed
        }

        
        @Override
        public void setOutputRoutine(OutputRoutine output) {

            // not needed
        }

        
        @Override
        public void setPageBuilder(PageBuilder pageBuilder) {

            // nothing to do
        }

        @Override
        public void setParagraphBuilder(ParagraphBuilder paragraphBuilder) {

            // nothing to do
        }

        
        @Override
        public void setPrevDepth(FixedDimen pd) {

            // nothing to do
        }

        
        @Override
        public void setSpacefactor(FixedCount f)
                throws InvalidSpacefactorException {

            // nothing to do
        }

        
        @Override
        public void shipout(NodeList nodes) {

            // nothing to do
        }

        @Override
        public void showlist(StringBuilder s, long l, long m) {

            // not needed
        }

        @Override
        public void showlists(StringBuilder s, long l, long m) {

            // not needed
        }

        @Override
        public void subscriptMark(Context context, TokenSource source,
                Typesetter typesetter, Token t) throws HelpingException {

            // not needed
        }

        @Override
        public void superscriptMark(Context context, TokenSource source,
                Typesetter typesetter, Token t) throws HelpingException {

            // not needed
        }

        @Override
        public void tab(Context context, TokenSource source, Token t) {

            // not needed
        }

        
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
