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

package org.extex.typesetter.listMaker.math;

import java.util.Stack;
import java.util.logging.Logger;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingMathException;
import org.extex.core.muskip.Mudimen;
import org.extex.core.muskip.Muskip;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.ActiveCharacterToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.ListManager;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterHelpingException;
import org.extex.typesetter.listMaker.HorizontalListMaker;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;
import org.extex.typesetter.type.noad.FractionNoad;
import org.extex.typesetter.type.noad.GlueNoad;
import org.extex.typesetter.type.noad.KernNoad;
import org.extex.typesetter.type.noad.LeftNoad;
import org.extex.typesetter.type.noad.MathGlyph;
import org.extex.typesetter.type.noad.MathList;
import org.extex.typesetter.type.noad.MiddleNoad;
import org.extex.typesetter.type.noad.Noad;
import org.extex.typesetter.type.noad.NoadFactory;
import org.extex.typesetter.type.noad.NodeNoad;
import org.extex.typesetter.type.noad.RightNoad;
import org.extex.typesetter.type.noad.StyleNoad;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathFontParameter;
import org.extex.typesetter.type.node.AfterMathNode;
import org.extex.typesetter.type.node.BeforeMathNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.GenericNodeList;
import org.extex.typesetter.type.node.GlueNode;

/**
 * This is the list maker for the inline math formulae.
 * 
 * 
 * <doc name="mathsurround" type="register">
 * <h3>The Dimen Parameter <tt>\mathsurround</tt></h3>
 * <p>
 * The dimen parameter <tt>\mathsurround</tt> is used to put some spacing
 * around mathematical formulae. The value at the end of the formula is used
 * before and after the formula. This additional space will be discarded at the
 * end of a line.
 * </p>
 * 
 * </doc>
 * 
 * <doc name="everymath" type="register">
 * <h3>The Tokens Parameter <tt>\everymath</tt></h3>
 * <p>
 * The tokens parameter <tt>\everymath</tt> contains a list of tokens which is
 * inserted at the beginning of inline math. Those tokens take effect after the
 * math mode has been entered but before any tokens given explicitly.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *     &lang;everymath&rang;
 *       &rarr;  <tt>\everymath</tt> {@linkplain
 *         org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *         &lang;equals&rang;} {@linkplain
 *         org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *         &lang;tokens&rang;}
 * </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *     \everymath={\,}
 * </pre>
 * 
 * </doc>
 * 
 * <doc name="everymathend" type="register">
 * <h3>The Tokens Parameter <tt>\everymathend</tt></h3>
 * <p>
 * The tokens parameter <tt>\everymathend</tt> contains a list of tokens which
 * is inserted at the end of inline math. Those tokens take effect just before
 * the math mode is ended but after any tokens given explicitly.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *     &lang;everymathend&rang;
 *       &rarr;  <tt>\everymathend</tt> {@linkplain
 *         org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *         &lang;equals&rang;} {@linkplain
 *         org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *         &lang;tokens&rang;}
 * </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *     \everymathend={\,}
 * </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4496 $
 */
public class MathListMaker extends HorizontalListMaker
        implements
            NoadConsumer,
            LogEnabled {

    /**
     * This inner class is a memento of the state of the math list maker. It is
     * used to store to the stack and restore the state from the stack.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4496 $
     */
    private static class MathMemento {

        /**
         * The field <tt>block</tt> contains the indicator that this memento
         * corresponds to a block. Otherwise it corresponds to a \left-\right
         * pair.
         */
        private boolean block;

        /**
         * The field <tt>delimiter</tt> contains the left delimiter or
         * <code>null</code> for a block.
         */
        private MathDelimiter delimiter;

        /**
         * The field <tt>ip</tt> contains the insertion point.
         */
        private MathList ip;

        /**
         * The field <tt>noads</tt> contains the noads.
         */
        private Noad noads;

        /**
         * Creates a new object.
         * 
         * @param ip the insertion point to be saved in this memento
         * @param noads the noads to be saved in this memento
         * @param block indicator to distinguish blocks from \left-\right
         *        constructs. a Value of <code>true</code> indicates a block.
         */
        public MathMemento(MathList ip, Noad noads, boolean block) {

            this.ip = ip;
            this.noads = noads;
            this.block = block;
        }

        /**
         * Getter for delimiter.
         * 
         * @return the delimiter
         */
        public MathDelimiter getDelimiter() {

            return delimiter;
        }

        /**
         * Getter for the insertion point.
         * 
         * @return the insertion point
         */
        public MathList getInsertionPoint() {

            return this.ip;
        }

        /**
         * Getter for noads.
         * 
         * @return the noads
         */
        public Noad getNoads() {

            return this.noads;
        }

        /**
         * Getter for block indicator.
         * 
         * @return the block
         */
        protected boolean isBlock() {

            return this.block;
        }
    }

    /**
     * The field <tt>SCRIPTSCRIPTFONT</tt> contains the key for the
     * scriptscriptfont.
     */
    private static final String SCRIPTSCRIPTFONT = "scriptscriptfont";

    /**
     * The field <tt>SCRIPTFONT</tt> contains the key for the scriptfont.
     */
    private static final String SCRIPTFONT = "scriptfont";

    /**
     * The field <tt>TEXTFONT</tt> contains the key for the textfont.
     */
    private static final String TEXTFONT = "textfont";

    /**
     * The constant <tt>noadFactory</tt> contains the noad factory.
     */
    private static final NoadFactory NOAD_FACTORY = new NoadFactory();

    /**
     * This method checks that extension fonts have sufficient font dimen values
     * set.
     * 
     * @param options the options
     * 
     * @return <code>true</code> iff the needed font dimens are not present
     * 
     * @see "[TTP 1195]"
     */
    protected static boolean insufficientExtensionFonts(
            TypesetterOptions options) {

        Font textfont3 =
                options.getFont(MathFontParameter.key(options, TEXTFONT, "3"));
        if (textfont3.getFontDimen("8") == null) {
            return true;
        }

        Font scriptfont3 =
                options
                    .getFont(MathFontParameter.key(options, SCRIPTFONT, "3"));
        if (scriptfont3.getFontDimen("8") == null) {
            return true;
        }

        Font scriptscriptfont3 =
                options.getFont(MathFontParameter.key(options,
                    SCRIPTSCRIPTFONT, "3"));
        if (scriptscriptfont3.getFontDimen("8") == null) {
            return true;
        }
        return false;
    }

    /**
     * This method checks that symbol fonts have sufficient font dimen values
     * set. In fact only the fonts itself are checked. the font dimens are
     * checked when needed.
     * 
     * @param options the options
     * 
     * @return <code>true</code> iff the symbol fonts have the needed font
     *         dimens
     * 
     * @see "[TTP 1195]"
     */
    protected static boolean insufficientSymbolFonts(TypesetterOptions options) {

        Font textfont2 = options.getFont(MathFontParameter.key(options, //
            TEXTFONT, "2"));
        if (textfont2.getFontDimen("8") == null) {
            return true;
        }
        Font scriptfont2 = options.getFont(MathFontParameter.key(options, //
            SCRIPTFONT, "2"));
        if (scriptfont2.getFontDimen("8") == null) {
            return true;
        }
        Font scriptscriptfont2 = options.getFont(MathFontParameter.key(options, //
            SCRIPTSCRIPTFONT, "2"));
        if (scriptscriptfont2.getFontDimen("8") == null) {
            return true;
        }
        return false;
    }

    /**
     * The field <tt>closing</tt> contains the indicator that this list maker
     * is in the mode of processing the terminal tokens.
     */
    private boolean closing = false;

    /**
     * The field <tt>insertionPoint</tt> contains the the MathList to which
     * the next noads should be added.
     */
    private MathList insertionPoint;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger = null;

    /**
     * The field <tt>nodes</tt> contains the list of nodes encapsulated in
     * this instance.
     */
    private Noad noads;

    /**
     * The field <tt>stack</tt> contains the stack for parsing sub-formulae.
     */
    private Stack<MathMemento> stack = new Stack<MathMemento>();

    /**
     * Creates a new object.
     * 
     * @param manager the manager to ask for global changes
     * @param locator the locator
     */
    public MathListMaker(ListManager manager, Locator locator) {

        super(manager, locator);
        insertionPoint = new MathList();
        noads = insertionPoint;
    }

    /**
     * Add a glue node to the list.
     * 
     * @param g the glue to add
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.ListMaker#add( org.extex.core.glue.FixedGlue)
     */
    public void add(FixedDimen g) throws TypesetterException {

        insertionPoint.add(new NodeNoad(new GlueNode(g, true)));
    }

    /**
     * Add a mathematical glyph.
     * 
     * @param mc the math code
     * @param tc the typesetting context
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#add(
     *      org.extex.typesetter.type.math.MathCode,
     *      org.extex.typesetter.tc.TypesettingContext)
     */
    public void add(MathCode mc, TypesettingContext tc)
            throws TypesetterException {

        insertionPoint.add(NOAD_FACTORY.getNoad(mc, tc));
    }

    /**
     * Add a mathematical delimiter.
     * 
     * @param delimiter the delimiter
     * @param tc the typesetting context
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#add(
     *      org.extex.typesetter.type.math.MathDelimiter,
     *      org.extex.typesetter.tc.TypesettingContext)
     */
    public void add(MathDelimiter delimiter, TypesettingContext tc)
            throws TypesetterException {

        MathGlyph smallChar = delimiter.getSmallChar(); // TODO: gene why???
        insertionPoint.add(NOAD_FACTORY.getNoad(delimiter.getMathClass(),
            smallChar, tc));
    }

    /**
     * Add some math dimen Noad to the internal list.
     * 
     * @param skip the length to add
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#add(
     *      org.extex.core.muskip.Mudimen)
     */
    public void add(Mudimen skip) throws TypesetterException {

        insertionPoint.add(new KernNoad(skip));
    }

    /**
     * Add some math glue Noad to the internal list.
     * 
     * @param glue the glue to add
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#add(
     *      org.extex.core.muskip.Muskip)
     */
    public void add(Muskip glue) throws TypesetterException {

        insertionPoint.add(new GlueNoad(glue));
    }

    /**
     * Add an arbitrary Noad to the internal list if it is prepared to hold one.
     * This is usually the case in math modes.
     * 
     * @param noad the noad to add
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#add(
     *      org.extex.typesetter.type.noad.Noad)
     */
    public void add(Noad noad) throws TypesetterException {

        insertionPoint.add(noad);
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
     * @see org.extex.typesetter.ListMaker#add( org.extex.typesetter.type.Node)
     */
    @Override
    public void add(Node node)
            throws TypesetterException,
                ConfigurationException {

        if (node instanceof DiscretionaryNode) {
            NodeList postBreak = ((DiscretionaryNode) node).getPostBreak();
            if (postBreak != null && postBreak.size() != 0) {
                throw new TypesetterException(
                    new HelpingException(getLocalizer(), "TTP.IllegalMathDisc",
                        postBreak.toString()));
            }
        }

        insertionPoint.add(new NodeNoad(node));
    }

    /**
     * Spaces are ignored in math mode. Thus this method is a noop.
     * 
     * @param typesettingContext the typesetting context for the space
     * @param spacefactor the space factor to use for this space or
     *        <code>null</code> to indicate that the default space factor
     *        should be used.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.ListMaker#addSpace(
     *      org.extex.typesetter.tc.TypesettingContext, FixedCount)
     */
    @Override
    public void addSpace(TypesettingContext typesettingContext,
            FixedCount spacefactor)
            throws TypesetterException,
                ConfigurationException {

        // noop
    }

    /**
     * Close the node list. In the course of the closing, the Noad list is
     * translated into a Node list.
     * 
     * @param context the fragment of the context accessible for the typesetter
     * 
     * @return the node list enclosed in this instance
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.ListMaker#complete(TypesetterOptions)
     * @see "<logo>TeX</logo> &ndash; The Program [719]"
     */
    @Override
    public NodeList complete(TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        if (!stack.empty()) {
            MathMemento mm = stack.pop();
            throw new TypesetterException(new HelpingException(getLocalizer(),
                "TTP.MissingInserted", //
                (mm.isBlock() ? "}" : "\\right.")));
        }

        // see [TTP 1195]
        if (insufficientSymbolFonts(context)) {
            throw new TypesetterException(new HelpingException(getLocalizer(),
                "TTP.InsufficientSymbolFonts"));
        }
        // see [TTP 1195]
        if (insufficientExtensionFonts(context)) {
            throw new TypesetterException(new HelpingException(getLocalizer(),
                "TTP.InsufficientExtensionFonts"));
        }

        GenericNodeList list = new GenericNodeList();
        FixedDimen mathsurround = context.getDimenOption("mathsurround");
        // see [TTP 1196]
        list.add(new BeforeMathNode(mathsurround));
        noads.typeset(null, null, 0, list, new MathContext(StyleNoad.TEXTSTYLE,
            context), logger);
        // see [TTP 1196]
        list.add(new AfterMathNode(mathsurround));
        // see [TTP 1196]
        getManager().setSpacefactor(Count.THOUSAND);

        return list;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.HorizontalListMaker#cr(
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public void cr(Context context, TypesettingContext tc, UnicodeChar uc)
            throws TypesetterException {

        // noop
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
     * Getter for the contents of the insertion point. If the insertion point
     * does not contain an element then <code>null</code> is returned. If it
     * contains only one element then this element is returned. Otherwise the
     * complete list is returned.
     * 
     * @return the contents of the insertion point
     */
    protected Noad getInsertionPoint() {

        switch (insertionPoint.size()) {
            case 0:
                return null;
            case 1:
                return insertionPoint.get(0);
            default:
                return insertionPoint;
        }
    }

    /**
     * Get access to the previous noad.
     * 
     * @return the previous noad or <code>null</code> if there is none
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#getLastNoad()
     */
    public Noad getLastNoad() throws TypesetterException {

        return insertionPoint.getLastNoad();
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

        return null;
    }

    /**
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return this.logger;
    }

    /**
     * Getter for the current mode.
     * 
     * @return the mode which is one of the values defined in
     *         {@link org.extex.typesetter.Mode Mode}.
     * 
     * @see org.extex.typesetter.ListMaker#getMode()
     */
    @Override
    public Mode getMode() {

        return Mode.MATH;
    }

    /**
     * Getter for Noads.
     * 
     * @return the Noads.
     */
    protected Noad getNoads() {

        return this.noads;
    }

    /**
     * Getter for closing.
     * 
     * @return the closing
     */
    protected boolean isClosing() {

        return this.closing;
    }

    /**
     * Open the group for a \left-\right construction.
     * 
     * @param delimiter the delimiter to typeset on the left side
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#left(
     *      org.extex.typesetter.type.math.MathDelimiter)
     */
    public void left(MathDelimiter delimiter) throws TypesetterException {

        stack.push(new MathMemento(insertionPoint, noads, false));
        insertionPoint = new MathList();
        noads = new LeftNoad(insertionPoint, delimiter);
    }

    /**
     * Notification method to deal the case that a left brace has been
     * encountered.
     * 
     * @see org.extex.typesetter.ListMaker#leftBrace()
     */
    @Override
    public void leftBrace() {

        stack.push(new MathMemento(insertionPoint, noads, true));
        insertionPoint = new MathList();
        noads = insertionPoint;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.HorizontalListMaker#letter(
     *      org.extex.core.UnicodeChar,
     *      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.core.Locator)
     */
    @Override
    public boolean letter(UnicodeChar symbol, TypesettingContext tc,
            Context context, TokenSource source, Locator locator)
            throws TypesetterException {

        MathCode mcode = context.getMathcode(symbol);

        if (mcode.getMathClass() != null) {

            insertionPoint.add(NOAD_FACTORY.getNoad(mcode, tc));

        } else {
            try {
                ActiveCharacterToken t =
                        (ActiveCharacterToken) context.getTokenFactory()
                            .createToken(Catcode.ACTIVE, symbol,
                                Namespace.DEFAULT_NAMESPACE);
                source.push(t);

            } catch (CatcodeException e) {
                throw new TypesetterException(e);
            } catch (HelpingException e) {
                throw new TypesetterException(e);
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.AbstractListMaker#mathShift(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void mathShift(Context context, TokenSource source, Token t)
            throws TypesetterException,
                ConfigurationException,
                HelpingException {

        if (!closing) {
            Tokens toks = context.getToks("everymathend");
            if (toks != null && toks.length() != 0) {
                try {
                    source.push(t);
                    source.push(toks);
                } catch (HelpingException e) {
                    throw new TypesetterException(e);
                }
                closing = true;
                return;
            }
        }
        getManager().endParagraph();
    }

    /**
     * Middle in the group for a \left-\right construction.
     * 
     * @param delimiter the delimiter to typeset here
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#middle(
     *      org.extex.typesetter.type.math.MathDelimiter)
     */
    public void middle(MathDelimiter delimiter) throws TypesetterException {

        if (stack.empty()) {
            throw new TypesetterHelpingException(getLocalizer(),
                "TTP.ExtraOrForgotten", "$");
        }
        MathMemento memento = stack.peek();
        if (memento.isBlock()) {
            throw new TypesetterHelpingException(getLocalizer(),
                "TTP.ExtraOrForgotten", "\\right.");
        }

        insertionPoint = new MathList();
        noads = new MiddleNoad((LeftNoad) noads, delimiter, insertionPoint);
    }

    /**
     * Emitting a new paragraph is not supported in math mode. Thus an exception
     * is thrown.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * 
     * @see org.extex.typesetter.ListMaker#par()
     * @see "<logo>TeX</logo> &ndash; The Program [1047]"
     */
    @Override
    public void par() throws TypesetterException, ConfigurationException {

        getManager().endParagraph();
        throw new TypesetterException(new MissingMathException("\\par"));
    }

    /**
     * Removes the last node from the list. If the list is empty then nothing is
     * done.
     * 
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    @Override
    public void removeLastNode() {

        throw new UnsupportedOperationException();
    }

    /**
     * Close the group for a <tt>\left</tt>-<tt>\right</tt> construction.
     * 
     * @param delimiter the delimiter to typeset on the right side
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#right(
     *      org.extex.typesetter.type.math.MathDelimiter)
     */
    public void right(MathDelimiter delimiter) throws TypesetterException {

        if (stack.empty()) {
            throw new TypesetterHelpingException(getLocalizer(),
                "TTP.ExtraOrForgotten", "$");
        }
        MathMemento memento = stack.pop();
        if (memento.isBlock()) {
            throw new TypesetterHelpingException(getLocalizer(),
                "TTP.ExtraRigt", "\\right.");
        }

        LeftNoad n = (LeftNoad) noads;
        insertionPoint = memento.getInsertionPoint();
        noads = memento.getNoads();
        insertionPoint.add(new RightNoad(n, delimiter));
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

        if (stack.empty()) {
            throw new TypesetterHelpingException(getLocalizer(),
                "TTP.ExtraOrForgotten", "$");
        }
        MathMemento memento = stack.pop();
        if (!memento.isBlock()) {
            throw new TypesetterHelpingException(getLocalizer(),
                "TTP.ExtraOrForgotten", "\\right.");
        }
        Noad n = noads;
        insertionPoint = memento.getInsertionPoint();
        noads = memento.getNoads();
        insertionPoint.add(n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#scanNoad(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      Token, org.extex.interpreter.context.group.GroupType)
     */
    public Noad scanNoad(Flags flags, Context context, TokenSource source,
            Typesetter typesetter, Token primitive, GroupType groupType)
            throws TypesetterException,
                HelpingException {

        Flags f = null;
        if (flags != null) {
            f = flags.copy();
            flags.clear();
        }
        ListManager man = getManager();
        try {
            Token t = source.getToken(context);
            if (t == null) {
                throw new EofException(primitive.toText());
            }
            MathListMaker lm = new MathListMaker(man, source.getLocator());
            man.push(lm);
            if (t.isa(Catcode.LEFTBRACE)) {
                lm.leftBrace();
                context.openGroup(groupType, source.getLocator(), t);
                source.executeGroup();
            } else {
                source.execute(t, context, typesetter);
            }
        } catch (TypesetterException e) {
            throw e;
            // } catch (InterpreterException e) {
            // throw new TypesetterException(e);
        }
        if (flags != null) {
            flags.set(f);
        }
        return (((MathListMaker) man.pop())).getInsertionPoint();
    }

    /**
     * Setter for closing.
     * 
     * @param closing the closing to set
     */
    protected void setClosing(boolean closing) {

        this.closing = closing;
    }

    /**
     * Setter for insertionPoint.
     * 
     * @param insertionPoint the insertionPoint to set
     */
    protected void setInsertionPoint(MathList insertionPoint) {

        this.insertionPoint = insertionPoint;
    }

    /**
     * Print the status for <tt>\showlists</tt>.
     * 
     * @param sb the target buffer
     * @param depth the depth of the list display
     * @param breadth the breadth of the list display
     * 
     * @see org.extex.typesetter.ListMaker#showlist( java.lang.StringBuffer,
     *      long, long)
     */
    @Override
    public void showlist(StringBuffer sb, long depth, long breadth) {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.AbstractListMaker#subscriptMark(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void subscriptMark(Context context, TokenSource source,
            Typesetter typesetter, Token token)
            throws TypesetterException,
                HelpingException {

        Noad sub =
                scanNoad(null, context, source, typesetter, token,
                    GroupType.MATH_GROUP);
        if (insertionPoint.size() == 0) {
            add(new MathList());
        }
        Noad noad = insertionPoint.get(insertionPoint.size() - 1);
        if (noad.getSubscript() != null) {
            throw new TypesetterException(new HelpingException(getLocalizer(),
                "TTP.DoubleSubscript"));
        }

        noad.setSubscript(sub);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.AbstractListMaker#superscriptMark(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void superscriptMark(Context context, TokenSource source,
            Typesetter typesetter, Token token)
            throws TypesetterException,
                HelpingException {

        Noad sup =
                scanNoad(null, context, source, typesetter, token,
                    GroupType.MATH_GROUP);
        if (insertionPoint.size() == 0) {
            add(new MathList());
        }
        Noad noad = insertionPoint.get(insertionPoint.size() - 1);
        if (noad.getSuperscript() != null) {
            throw new TypesetterException(new HelpingException(getLocalizer(),
                "TTP.DoubleSuperscript"));
        }

        noad.setSuperscript(sup);
    }

    /**
     * This method instructs the implementing class to use a fraction
     * construction. The math list collected so far is integrated into the
     * fraction noad.
     * 
     * @param leftDelimiter the left delimiter or <code>null</code> if none
     *        should be used.
     * @param rightDelimiter the right delimiter or <code>null</code> if none
     *        should be used.
     * @param ruleWidth th width of the rule or <code>null</code> to indicate
     *        that the default width should be used
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.math.NoadConsumer#switchToFraction(
     *      org.extex.typesetter.type.math.MathDelimiter,
     *      org.extex.typesetter.type.math.MathDelimiter,
     *      org.extex.core.dimen.FixedDimen,
     *      org.extex.typesetter.tc.TypesettingContext)
     */
    public void switchToFraction(MathDelimiter leftDelimiter,
            MathDelimiter rightDelimiter, FixedDimen ruleWidth,
            TypesettingContext tc) throws TypesetterException {

        // see [TTP 1183]
        if (!(noads instanceof MathList)) {
            throw new TypesetterException(new HelpingException(getLocalizer(),
                "TTP.AmbiguousFraction"));
        }
        insertionPoint = new MathList();
        noads =
                new FractionNoad((MathList) noads, insertionPoint,
                    leftDelimiter, rightDelimiter, ruleWidth, tc);
    }

}
