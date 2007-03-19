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
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Mode;
import org.extex.typesetter.ParagraphObserver;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.VerticalListNode;

/**
 * This is the derived class for a list maker in inner vertical list mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class InnerVerticalListMaker extends AbstractListMaker {

    /**
     * The field <tt>afterParagraphObservers</tt> contains the observers to be
     * invoked after the paragraph has been completed.
     */
    private List afterParagraphObservers = new ArrayList();

    /**
     * The field <tt>nodes</tt> contains the list of nodes encapsulated.
     */
    private VerticalListNode nodes = new VerticalListNode();

    /**
     * This value contains the previous depth for baseline calculations. In
     * contrast to <logo>TeX</logo> the value null is used to indicate that the
     * next box on the vertical list should be exempt from the baseline
     * calculations.
     *
     * @see "<logo>TeX</logo> &ndash; The Program [212]"
     */
    private Dimen prevDepth = null;

    /**
     * Creates a new object.
     *
     * @param manager the manager to ask for global changes
     * @param locator the locator
     */
    public InnerVerticalListMaker(final ListManager manager,
            final Locator locator) {

        super(manager, locator);
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(final Node n)
            throws TypesetterException,
                ConfigurationException {

        nodes.add(n);
    }

    /**
     * @see org.extex.typesetter.ListMaker#addAndAdjust(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addAndAdjust(final NodeList list,
            final TypesetterOptions options)
            throws TypesetterException,
                ConfigurationException {

        int size = list.size();
        for (int i = 0; i < size; i++) {
            nodes.add(list.get(i));
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#add(
     *      org.extex.core.glue.FixedGlue)
     */
    public void add(final FixedGlue g) throws TypesetterException {

        nodes.addSkip(g);
    }

    /**
     * @see org.extex.typesetter.ListMaker#addSpace(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.core.count.Count)
     */
    public void addSpace(final TypesettingContext typesettingContext,
            final Count spacefactor)
            throws TypesetterException,
                ConfigurationException {

    }

    /**
     * @see org.extex.typesetter.ListMaker#afterParagraph(ParagraphObserver)
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

        return nodes;
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

        return Mode.INNER_VERTICAL;
    }

    /**
     * @see org.extex.typesetter.ListMaker#getPrevDepth()
     */
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        return prevDepth;
    }

    /**
     * @see org.extex.typesetter.ListMaker#letter(
     *      org.extex.core.UnicodeChar,
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.core.Locator)
     */
    public boolean letter(final UnicodeChar symbol,
            final TypesettingContext tc, final Context context,
            final TokenSource source, final Locator locator)
            throws TypesetterException {

        return getManager().ensureHorizontalMode(locator).letter(symbol, tc,
                context, source, locator);
    }

    /**
     * <tt>\par</tt> s are silently ignored in vertical mode.
     *
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
        // nothing more to do
    }

    /**
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    public void removeLastNode() {

        nodes.remove(nodes.size() - 1);
    }

    /**
     * @see org.extex.typesetter.ListMaker#setPrevDepth(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setPrevDepth(final FixedDimen pd) {

        if (prevDepth == null) {
            prevDepth = new Dimen(pd);
        } else {
            prevDepth.set(pd);
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#showlist(
     *      java.lang.StringBuffer, long, long)
     */
    public void showlist(final StringBuffer sb, final long l, final long m) {

        sb.append("prevdepth ");
        if (prevDepth == null) {
            sb.append("ignored");
        } else {
            prevDepth.toString(sb);
        }
        sb.append('\n');
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return super.toString() + "\n" + nodes.toString();
    }

}
