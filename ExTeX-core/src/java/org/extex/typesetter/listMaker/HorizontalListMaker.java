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

package org.extex.typesetter.listMaker;

import java.util.ArrayList;
import java.util.List;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.font.Font;
import org.extex.typesetter.Mode;
import org.extex.typesetter.ParagraphObserver;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.InvalidSpacefactorException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.ImplicitKernNode;
import org.extex.typesetter.type.node.SpaceNode;

/**
 * Maker for a horizontal list.
 * <p>
 * After <code>par()</code>, the line breaking and hyphenation are applied.
 * </p>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class HorizontalListMaker extends AbstractListMaker {

    /**
     * The constant <tt>DEFAULT_SPACEFACTOR</tt> contains the default value for
     * the space factor. It is 1000 according to <logo>TeX</logo>.
     */
    private static final int DEFAULT_SPACEFACTOR = 1000;

    /**
     * The constant <tt>SPACEFACTOR_THRESHOLD</tt> contains the threshold for
     * the space factor above which the space is handled different.
     */
    private static final int SPACEFACTOR_THRESHOLD = 2000;

    /**
     * The field <tt>afterParagraphObservers</tt> contains the observers to be
     * invoked after the paragraph has been completed.
     */
    private List afterParagraphObservers = new ArrayList();

    /**
     * The field <tt>nodes</tt> contains the node list encapsulated by this
     * class.
     */
    private HorizontalListNode nodes = new HorizontalListNode();

    /**
     * The field <tt>spaceFactor</tt> contains the current space factor.
     *
     * @see "<logo>TeX</logo> &ndash; The Program [212]"
     */
    private long spaceFactor = DEFAULT_SPACEFACTOR;

    /**
     * Creates a new object.
     *
     * @param manager the manager to ask for global changes
     * @param locator the locator
     */
    public HorizontalListMaker(final ListManager manager, final Locator locator) {

        super(manager, locator);
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(final Node c)
            throws TypesetterException,
                ConfigurationException {

        nodes.add(c);
        spaceFactor = DEFAULT_SPACEFACTOR;
    }

    /**
     * @see org.extex.typesetter.ListMaker#addAndAdjust(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addAndAdjust(final NodeList list,
            final TypesetterOptions options) throws TypesetterException {

        nodes.add(list); // TODO gene: correct?
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.core.glue.FixedGlue)
     */
    public void add(final FixedGlue g) throws TypesetterException {

        nodes.addSkip(g);
        spaceFactor = DEFAULT_SPACEFACTOR;
    }

    /**
     * @see org.extex.typesetter.ListMaker#addSpace(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.core.count.Count)
     */
    public void addSpace(final TypesettingContext context, final Count sfCount)
            throws TypesetterException,
                ConfigurationException {

        long sf = (sfCount != null ? sfCount.getValue() : spaceFactor);
        Glue space = new Glue(context.getFont().getSpace());

        // gene: maybe my interpretation of the TeXbook is slightly wrong
        if (sf != DEFAULT_SPACEFACTOR) { // normal case handled first
            if (sf == 0) {
                return;
            } else if (sf >= SPACEFACTOR_THRESHOLD) {
                TypesetterOptions options = getManager().getOptions();
                FixedGlue xspaceskip = options.getGlueOption("xspaceskip");
                FixedGlue spaceskip = options.getGlueOption("spaceskip");

                if (xspaceskip != null) {
                    space = xspaceskip.copy();
                } else if (spaceskip != null) {
                    space = spaceskip.copy();
                    space.multiplyStretch(sf, DEFAULT_SPACEFACTOR);
                    space.multiplyShrink(DEFAULT_SPACEFACTOR, sf);
                } else {
                    space = space.copy();
                    space.multiplyStretch(sf, DEFAULT_SPACEFACTOR);
                    space.multiplyShrink(DEFAULT_SPACEFACTOR, sf);
                }
            } else {
                space = space.copy();
                space.multiplyStretch(sf, DEFAULT_SPACEFACTOR);
                space.multiplyShrink(DEFAULT_SPACEFACTOR, sf);
            }
        }

        add(new SpaceNode(space));
    }

    /**
     * @see org.extex.typesetter.ListMaker#afterParagraph(
     *      org.extex.typesetter.ParagraphObserver)
     */
    public void afterParagraph(final ParagraphObserver observer) {

        afterParagraphObservers.add(observer);
    }

    /**
     * @see org.extex.typesetter.ListMaker#complete(TypesetterOptions)
     */
    public NodeList complete(final TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        return getManager().buildParagraph(nodes);
    }

    /**
     * @see org.extex.typesetter.ListMaker#cr(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.core.UnicodeChar)
     */
    public void cr(final Context context, final TypesettingContext tc,
            final UnicodeChar uc) throws TypesetterException {

    }

    /**
     * @see org.extex.typesetter.ListMaker#getLastNode()
     */
    public Node getLastNode() {

        return (nodes.empty() ? null : nodes.get(nodes.size() - 1));
    }

    /**
     * @see org.extex.typesetter.ListMaker#getMode()
     */
    public Mode getMode() {

        return Mode.HORIZONTAL;
    }

    /**
     * Getter for nodes.
     *
     * @return the nodes.
     */
    protected HorizontalListNode getNodes() {

        return this.nodes;
    }

    /**
     * @see org.extex.typesetter.ListMaker#getSpacefactor()
     */
    public long getSpacefactor() {

        return spaceFactor;
    }

    /**
     * Add a character node to the list.
     *
     * @param symbol the symbol to add
     * @param tc the typesetting context for the symbol
     * @param context the interpreter context
     * @param source the source of new tokens
     * @param locator the locator
     *
     * @see org.extex.typesetter.ListMaker#letter(
     *      org.extex.core.UnicodeChar,
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.core.Locator)
     * @see "The TeXbook [p.76]"
     */
    public boolean letter(final UnicodeChar symbol,
            final TypesettingContext tc, final Context context,
            final TokenSource source, final Locator locator)
            throws TypesetterException {

        UnicodeChar c = symbol;
        Node node;
        int size = nodes.size();
        if (size > 0) {
            Node n = nodes.get(size - 1);
            if (n instanceof CharNode) {
                CharNode cn = ((CharNode) n);
                Font f = tc.getFont();
                if (cn.getTypesettingContext().getFont().equals(f)) {
                    UnicodeChar cnc = cn.getCharacter();
                    UnicodeChar lig = tc.getLanguage().getLigature(cnc, symbol,
                            f);
                    if (lig != null) {
                        nodes.remove(size - 1);
                        c = lig;
                    } else {
                        FixedDimen kerning = f.getKerning(cnc, symbol);
                        if (kerning != null && kerning.ne(Dimen.ZERO_PT)) {
                            nodes.add(new ImplicitKernNode(kerning, true));
                        }
                    }
                }
            }
        }
        node = getManager().getNodeFactory().getNode(tc, c);
        if (node == null) {
            return true;
        }

        nodes.add(node);

        if (node instanceof CharNode) {
            int f = ((CharNode) node).getSpaceFactor();

            if (f != 0) {
                spaceFactor = (spaceFactor < DEFAULT_SPACEFACTOR
                        && f > DEFAULT_SPACEFACTOR //
                ? DEFAULT_SPACEFACTOR : f);
            }
        }
        return false;
    }

    /**
     * @see org.extex.typesetter.ListMaker#par()
     */
    public void par() throws TypesetterException, ConfigurationException {

        try {
            // Note: the observers have to be run in reverse order to restore
            // the language properly.
            for (int i = afterParagraphObservers.size() - 1; i >= 0; i--) {
                ((ParagraphObserver) afterParagraphObservers.get(i))
                        .atParagraph(nodes);
            }
        } catch (InterpreterException e) {
            throw new TypesetterException(e);
        }
        getManager().endParagraph();
    }

    /**
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    public void removeLastNode() {

        nodes.remove(nodes.size() - 1);
    }

    /**
     * Setter for nodes.
     *
     * @param nodes the nodes to set.
     */
    protected void setNodes(final HorizontalListNode nodes) {

        this.nodes = nodes;
    }

    /**
     * @see org.extex.typesetter.ListMaker#setSpacefactor(
     *      org.extex.core.count.FixedCount)
     */
    public void setSpacefactor(final FixedCount f)
            throws TypesetterUnsupportedException,
                InvalidSpacefactorException {

        long sf = f.getValue();
        if (sf <= 0) {
            throw new InvalidSpacefactorException();
        }
        spaceFactor = sf;
    }

    /**
     * @see org.extex.typesetter.ListMaker#showlist(
     *      java.lang.StringBuffer, long, long)
     */
    public void showlist(final StringBuffer sb, final long l, final long m) {

        sb.append("spacefactor ");
        sb.append(spaceFactor);
        sb.append('\n');
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return super.toString() + "\n" + nodes.toString();
    }

}