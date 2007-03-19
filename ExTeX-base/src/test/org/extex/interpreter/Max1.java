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

package org.extex.interpreter;

import junit.framework.TestCase;

import org.extex.backend.BackendDriver;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.scanner.TokenStream;
import org.extex.scanner.base.TokenStreamImpl;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Mode;
import org.extex.typesetter.ParagraphObserver;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.InvalidSpacefactorException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.ListManager;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.Noad;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
public class Max1 extends TestCase {

    /**
     * Inner class to collect the things the typesetter sees.
     */
    private static class TestTypesetter implements Typesetter {

        /**
         */
        private StringBuffer sb = new StringBuffer();

        /**
         * @see org.extex.typesetter.ListMaker#addGlue(
         *      org.extex.core.glue.Glue)
         */
        public void add(final FixedGlue g) throws TypesetterException {

            sb.append(g.toString());
        }

        /**
         * @see org.extex.typesetter.ListMaker#add(
         *      org.extex.typesetter.type.noad.Noad)
         */
        public void add(final Noad noad) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#add(
         *      org.extex.interpreter.type.node.CharNode)
         */
        public void add(final Node c) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#addAndAdjust(
         *      org.extex.typesetter.type.NodeList,
         *      org.extex.typesetter.TypesetterOptions)
         */
        public void addAndAdjust(final NodeList list,
                final TypesetterOptions options) throws TypesetterException {

        }

        /**
         * @see org.extex.typesetter.ListMaker#addSpace(
         *      org.extex.interpreter.context.tc.TypesettingContext,
         *      org.extex.core.count.Count)
         */
        public void addSpace(final TypesettingContext typesettingContext,
                final Count spacefactor) {

            sb.append(" ");
        }

        /**
         * @see org.extex.typesetter.ListMaker#afterParagraph(ParagraphObserver)
         */
        public void afterParagraph(final ParagraphObserver observer) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#clearShipoutMark()
         */
        public void clearShipoutMark() {

        }

        /**
         * @see org.extex.typesetter.Typesetter#complete(TypesetterOptions)
         */
        public NodeList complete(final TypesetterOptions context) {

            return null;
        }

        /**
         * @see org.extex.framework.configuration.Configurable#configure(
         *      org.extex.framework.configuration.Configuration)
         */
        public void configure(final Configuration config) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#cr(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.context.tc.TypesettingContext,
         *      org.extex.core.UnicodeChar)
         */
        public void cr(final Context context, final TypesettingContext tc,
                final UnicodeChar uc) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#ensureHorizontalMode(
         *      org.extex.core.Locator)
         */
        public ListMaker ensureHorizontalMode(Locator locator) {

            return null;
        }

        /**
         * @see org.extex.typesetter.Typesetter#finish()
         */
        public void finish() {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#getBackendDriver()
         */
        public BackendDriver getBackendDriver() {

            return null;
        }

        /**
         * @see org.extex.typesetter.ListMaker#getLastNode()
         */
        public Node getLastNode() {

            return null;
        }

        /**
         * @see org.extex.typesetter.Typesetter#getListMaker()
         */
        public ListMaker getListMaker() {

            return null;
        }

        /**
         * @see org.extex.typesetter.ListMaker#getLocator()
         */
        public Locator getLocator() {

            return null;
        }

        /**
         * @see org.extex.typesetter.Typesetter#getManager()
         */
        public ListManager getManager() {

            return null;
        }

        /**
         * @see org.extex.typesetter.Typesetter#getMode()
         */
        public Mode getMode() {

            return null;
        }

        /**
         * @see org.extex.typesetter.Typesetter#getCharNodeFactory()
         */
        public NodeFactory getNodeFactory() {

            return null;
        }

        /**
         * @see org.extex.typesetter.ListMaker#getPrevDepth()
         */
        public FixedDimen getPrevDepth() {

            return null;
        }

        /**
         * @see org.extex.typesetter.ListMaker#getSpacefactor()
         */
        public long getSpacefactor() {

            return 0;
        }

        /**
         * @see org.extex.typesetter.Typesetter#isShipoutMark()
         */
        public boolean isShipoutMark() {

            return false;
        }

        /**
         * @see org.extex.typesetter.ListMaker#leftBrace()
         */
        public void leftBrace() {

        }

        /**
         * @see org.extex.typesetter.ListMaker#letter(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.context.tc.TypesettingContext,
         *      org.extex.core.UnicodeChar,
         *      org.extex.core.Locator)
         */
        public boolean letter(final UnicodeChar uc,
                final TypesettingContext tc, final Context context,
                TokenSource source, final Locator locator) {

            return false;
        }

        /**
         * @see org.extex.typesetter.ListMaker#mathShift(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.scanner.type.token.Token)
         */
        public void mathShift(final Context context, final TokenSource source,
                final Token t) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#open()
         */
        public void open() {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#par()
         */
        public void par() {

            sb.append("\n\\par\n");
        }

        /**
         * @see org.extex.typesetter.Typesetter#push(
         *      org.extex.typesetter.ListMaker)
         */
        public void push(final ListMaker listMaker) {

        }

        /**
         * @see org.extex.typesetter.ListMaker#removeLastNode()
         */
        public void removeLastNode() {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#rightBrace()
         */
        public void rightBrace() throws TypesetterException {

        }

        /**
         * @see org.extex.typesetter.Typesetter#setDocumentWriter(
         *      org.extex.backend.documentWriter.DocumentWriter)
         */
        public void setBackend(final BackendDriver doc) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#setLigatureBuilder(
         *      org.extex.core.ligatureBuilder.LigatureBuilder)
         */
        public void setLigatureBuilder(final LigatureBuilder ligatureBuilder) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#setNodeFactory(
         *      org.extex.typesetter.type.node.factory.NodeFactory)
         */
        public void setNodeFactory(final NodeFactory nodeFactory) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#setOptions(
         *      org.extex.typesetter.TypesetterOptions)
         */
        public void setOptions(final TypesetterOptions options) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#setOutputRoutine(
         *      org.extex.core.OutputRoutine)
         */
        public void setOutputRoutine(final OutputRoutine output) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#setPageBuilder(
         *      org.extex.typesetter.pageBuilder.PageBuilder)
         */
        public void setPageBuilder(final PageBuilder pageBuilder) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#setParagraphBuilder(
         *      org.extex.typesetter.paragraphBuilder.ParagraphBuilder)
         */
        public void setParagraphBuilder(final ParagraphBuilder paragraphBuilder) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#setParshape(
         *      org.extex.typesetter.paragraphBuilder.ParagraphShape)
         */
        public void setParshape(final ParagraphShape parshape) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#setPrevDepth(
         *      org.extex.core.dimen.Dimen)
         */
        public void setPrevDepth(final FixedDimen pd) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#setSpacefactor(int)
         */
        public void setSpacefactor(final FixedCount f)
                throws InvalidSpacefactorException {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.Typesetter#shipout(
         *      org.extex.typesetter.type.NodeList)
         */
        public void shipout(final NodeList nodes) {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#showlist(
         *      java.lang.StringBuffer, long, long)
         */
        public void showlist(final StringBuffer sb, final long l, final long m) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#showlists(
         *      java.lang.StringBuffer, long, long)
         */
        public void showlists(final StringBuffer sb, final long l, final long m) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#subscriptMark(
         *      Context,
         *      TokenSource, Typesetter, org.extex.scanner.type.token.Token)
         */
        public void subscriptMark(final Context context,
                final TokenSource source, final Typesetter typesetter,
                final Token t) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#superscriptMark(
         *      Context,
         *      TokenSource, Typesetter, org.extex.scanner.type.token.Token)
         */
        public void superscriptMark(final Context context,
                final TokenSource source, final Typesetter typesetter,
                final Token t) {

        }

        /**
         * @see org.extex.typesetter.Typesetter#tab(
         *      Context,
         *      TokenSource, org.extex.scanner.type.token.Token)
         */
        public void tab(final Context context, final TokenSource source,
                final Token t) {

        }

        /**
         * @see org.extex.typesetter.ListMaker#toggleDisplaymath()
         */
        public void toggleDisplaymath() {

            // nothing to do
        }

        /**
         * @see org.extex.typesetter.ListMaker#toggleMath()
         */
        public void toggleMath() {

            // nothing to do
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {

            return sb.toString();
        }

        /**
         * @see org.extex.typesetter.Typesetter#letter(
         *      Context,
         *      org.extex.interpreter.context.tc.TypesettingContext,
         *      org.extex.scanner.type.token.Token)
         */
        public void treatLetter(final TypesettingContext context, final Token t) {

        }

    }

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(Max1.class);
    }

    /**
     * Constructor for Max1.
     *
     * @param arg0 the name
     */
    public Max1(final String arg0) {

        super(arg0);
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
    private String doTest(final String in) throws Exception {

        Configuration config =
                new ConfigurationFactory().newInstance("config/extex.xml");

        Interpreter interpreter =
                new InterpreterFactory(config.getConfiguration("Interpreter"),
                    null).newInstance(null, null);
        TokenStreamFactory factory =
                new TokenStreamFactory(config.getConfiguration("Scanner"),
                    "base");
        interpreter.setTokenStreamFactory(factory);

        TestTypesetter typesetter = new TestTypesetter();
        interpreter.setTypesetter(typesetter);

        TokenStream stream = new TokenStreamImpl(null, null, in, "");
        interpreter.run(stream);

        return typesetter.toString();
    }

    /**
     * Trivial case: nothing in and nothing out
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        assertEquals("", doTest(""));
    }

    /**
     * @throws Exception in case of an error
     */
    public void testMacro1() throws Exception {

        assertEquals("", doTest("\\relax"));
    }

    /**
     * @throws Exception in case of an error
     */
    public void testMacro2() throws Exception {

        assertEquals("\n\\par\n", doTest("\\par"));
    }

    /**
     * @throws Exception in case of an error
     */
    public void testSingle1() throws Exception {

        assertEquals("a", doTest("a"));
    }

    /**
     * @throws Exception in case of an error
     */
    public void testSingle2() throws Exception {

        assertEquals("A", doTest("A"));
    }

    /**
     * @throws Exception in case of an error
     */
    public void testSingle3() throws Exception {

        assertEquals("2", doTest("2"));
    }

    /**
     * @throws Exception in case of an error
     */
    public void testSingle4() throws Exception {

        assertEquals(".", doTest("."));
    }

}
