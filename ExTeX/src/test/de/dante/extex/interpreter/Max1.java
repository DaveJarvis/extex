/*
 * Copyright (C) 2003-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter;

import junit.framework.TestCase;
import de.dante.extex.documentWriter.DocumentWriter;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.context.TypesettingContext;
import de.dante.extex.interpreter.type.count.Count;
import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.interpreter.type.glue.Glue;
import de.dante.extex.language.ligature.LigatureBuilder;
import de.dante.extex.scanner.stream.TokenStream;
import de.dante.extex.scanner.stream.TokenStreamFactory;
import de.dante.extex.scanner.stream.impl32.TokenStreamStringImpl;
import de.dante.extex.scanner.type.Token;
import de.dante.extex.typesetter.ListMaker;
import de.dante.extex.typesetter.Mode;
import de.dante.extex.typesetter.OutputRoutine;
import de.dante.extex.typesetter.ParagraphObserver;
import de.dante.extex.typesetter.Typesetter;
import de.dante.extex.typesetter.TypesetterOptions;
import de.dante.extex.typesetter.exception.TypesetterException;
import de.dante.extex.typesetter.listMaker.ListManager;
import de.dante.extex.typesetter.pageBuilder.PageBuilder;
import de.dante.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import de.dante.extex.typesetter.paragraphBuilder.ParagraphShape;
import de.dante.extex.typesetter.type.Node;
import de.dante.extex.typesetter.type.NodeList;
import de.dante.extex.typesetter.type.noad.Noad;
import de.dante.extex.typesetter.type.node.CharNodeFactory;
import de.dante.util.GeneralException;
import de.dante.util.UnicodeChar;
import de.dante.util.configuration.Configuration;
import de.dante.util.configuration.ConfigurationFactory;

/**
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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
         * @see de.dante.extex.typesetter.ListMaker#add(
         *      de.dante.extex.typesetter.type.noad.Noad)
         */
        public void add(final Noad noad) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#add(
         *      de.dante.extex.interpreter.type.node.CharNode)
         */
        public void add(final Node c) throws TypesetterException {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#addGlue(
         *      de.dante.extex.interpreter.type.glue.Glue)
         */
        public void addGlue(final Glue g) throws TypesetterException {

            sb.append(g.toString());
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#addSpace(
         *      de.dante.extex.interpreter.context.TypesettingContext,
         *      de.dante.extex.interpreter.type.count.Count)
         */
        public void addSpace(final TypesettingContext typesettingContext,
                final Count spacefactor) throws TypesetterException {

            sb.append(" ");
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#afterParagraph(ParagraphObserver)
         */
        public void afterParagraph(final ParagraphObserver observer) {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#clearShipoutMark()
         */
        public void clearShipoutMark() {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#complete(TypesetterOptions)
         */
        public NodeList complete(final TypesetterOptions context)
                throws TypesetterException {

            return null;
        }

        /**
         * @see de.dante.util.framework.configuration.Configurable#configure(
         *      de.dante.util.configuration.Configuration)
         */
        public void configure(final Configuration config) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#cr(
         *      de.dante.extex.interpreter.context.Context,
         *      de.dante.extex.interpreter.context.TypesettingContext,
         *      de.dante.util.UnicodeChar)
         */
        public void cr(final Context context, final TypesettingContext tc,
                final UnicodeChar uc) throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#finish()
         */
        public void finish() {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#getCharNodeFactory()
         */
        public CharNodeFactory getCharNodeFactory() {

            return null;
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#getLastNode()
         */
        public Node getLastNode() {

            return null;
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#getListMaker()
         */
        public ListMaker getListMaker() {

            return null;
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#getManager()
         */
        public ListManager getManager() {

            return null;
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#getMode()
         */
        public Mode getMode() {

            return null;
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#isShipoutMark()
         */
        public boolean isShipoutMark() {

            return false;
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#leftBrace()
         */
        public void leftBrace() {

        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#letter(
         *      Context,
         *      de.dante.extex.interpreter.context.TypesettingContext,
         *      de.dante.util.UnicodeChar)
         */
        public void letter(final Context context, final TypesettingContext tc,
                final UnicodeChar uc) throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#mathShift(
         *      Context, TokenSource, de.dante.extex.scanner.type.Token)
         */
        public void mathShift(final Context context, final TokenSource source,
                final Token t) throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#open()
         */
        public void open() {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#par()
         */
        public void par() throws TypesetterException {

            sb.append("\n\\par\n");
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#push(
         *      de.dante.extex.typesetter.ListMaker)
         */
        public void push(final ListMaker listMaker) throws TypesetterException {

            // TODO unimplemented
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#removeLastNode()
         */
        public void removeLastNode() {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#rightBrace()
         */
        public void rightBrace() throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setDocumentWriter(
         *      de.dante.extex.documentWriter.DocumentWriter)
         */
        public void setDocumentWriter(final DocumentWriter doc) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setLigatureBuilder(
         *      de.dante.extex.typesetter.ligatureBuilder.LigatureBuilder)
         */
        public void setLigatureBuilder(final LigatureBuilder ligatureBuilder) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setOptions(
         *      de.dante.extex.typesetter.TypesetterOptions)
         */
        public void setOptions(final TypesetterOptions options) {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setOutputRoutine(
         *      de.dante.extex.typesetter.OutputRoutine)
         */
        public void setOutputRoutine(final OutputRoutine output) {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setPageBuilder(
         *      de.dante.extex.typesetter.pageBuilder.PageBuilder)
         */
        public void setPageBuilder(final PageBuilder pageBuilder) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setParagraphBuilder(
         *      de.dante.extex.typesetter.paragraphBuilder.ParagraphBuilder)
         */
        public void setParagraphBuilder(final ParagraphBuilder paragraphBuilder) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#setParshape(
         *      de.dante.extex.typesetter.paragraphBuilder.ParagraphShape)
         */
        public void setParshape(final ParagraphShape parshape) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#setPrevDepth(
         *      de.dante.extex.interpreter.type.dimen.Dimen)
         */
        public void setPrevDepth(final Dimen pd) throws TypesetterException {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#setSpacefactor(int)
         */
        public void setSpacefactor(final Count f) throws TypesetterException {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#shipout(
         *      de.dante.extex.typesetter.type.NodeList)
         */
        public void shipout(final NodeList nodes) {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#subscriptMark(
         *      Context,
         *      TokenSource, de.dante.extex.scanner.type.Token)
         */
        public void subscriptMark(final Context context,
                final TokenSource source, final Token t)
                throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#superscriptMark(
         *      Context,
         *      TokenSource, de.dante.extex.scanner.type.Token)
         */
        public void superscriptMark(final Context context,
                final TokenSource source, final Token t)
                throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.Typesetter#tab(
         *      Context,
         *      TokenSource, de.dante.extex.scanner.type.Token)
         */
        public void tab(final Context context, final TokenSource source,
                final Token t) throws TypesetterException {

        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#toggleDisplaymath()
         */
        public void toggleDisplaymath() {

            // nothing to do
        }

        /**
         * @see de.dante.extex.typesetter.ListMaker#toggleMath()
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
         * @see de.dante.extex.typesetter.Typesetter#letter(
         *      Context,
         *      de.dante.extex.interpreter.context.TypesettingContext,
         *      de.dante.extex.scanner.type.Token)
         */
        public void treatLetter(final TypesettingContext context, final Token t)
                throws GeneralException {

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

        Configuration config = new ConfigurationFactory()
                .newInstance("config/extex.xml");

        // TODO gene: use FileFinder?
        InterpreterFactory interpreterFactory = new InterpreterFactory();
        interpreterFactory.configure(config.getConfiguration("Interpreter"));
        Interpreter interpreter = interpreterFactory.newInstance();
        TokenStreamFactory factory = new TokenStreamFactory(config
                .getConfiguration("Reader"), "base");
        interpreter.setTokenStreamFactory(factory);

        TestTypesetter typesetter = new TestTypesetter();

        interpreter.setTypesetter(typesetter);

        TokenStream stream = new TokenStreamStringImpl(in);
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