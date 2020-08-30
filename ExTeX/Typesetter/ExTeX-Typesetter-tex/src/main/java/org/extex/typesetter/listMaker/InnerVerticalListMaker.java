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

package org.extex.typesetter.listMaker;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.*;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.VerticalListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the derived class for a list maker in inner vertical list mode.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InnerVerticalListMaker extends AbstractListMaker {

    /**
     * The field <tt>afterParagraphObservers</tt> contains the observers to be
     * invoked after the paragraph has been completed.
     */
    private final List<ParagraphObserver> afterParagraphObservers =
        new ArrayList<>();

    /**
     * The field <tt>nodes</tt> contains the list of nodes encapsulated.
     */
    private final VerticalListNode nodes = new VerticalListNode();

    /**
     * This value contains the previous depth for baseline calculations. In
     * contrast to TeX the value null is used to indicate that the next box on
     * the vertical list should be exempt from the baseline calculations.
     * 
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [212]"
     */
    private Dimen prevDepth = null;

    /**
     * Creates a new object.
     * 
     * @param manager the manager to ask for global changes
     * @param locator the locator
     */
    public InnerVerticalListMaker(ListManager manager, Locator locator) {

        super(manager, locator);
    }

    @Override
    public void add(FixedGlue g) throws TypesetterException {

        nodes.addSkip(g);
    }

    @Override
    public void add(Node n) {

        nodes.add(n);
    }

    @Override
    public void addAndAdjust(NodeList list, TypesetterOptions options)
            throws TypesetterException,
                ConfigurationException {

        int size = list.size();
        for (int i = 0; i < size; i++) {
            nodes.add(list.get(i));
        }
    }

    @Override
    public void addSpace(TypesettingContext typesettingContext,
            FixedCount spacefactor) {

        // spaces are ignored in vertical mode
    }

    @Override
    public void afterParagraph(ParagraphObserver observer) {

        afterParagraphObservers.add(observer);
    }

    @Override
    public NodeList complete(TypesetterOptions context) {

        return nodes;
    }

    @Override
    public void cr(Context context, TypesettingContext tc, UnicodeChar uc)
            throws TypesetterException {

        // TODO gene: CR in vertical mode
    }

    @Override
    public Node getLastNode() {

        return (nodes.isEmpty() ? null : nodes.get(nodes.size() - 1));
    }

    @Override
    public Mode getMode() {

        return Mode.INNER_VERTICAL;
    }

    @Override
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        return prevDepth;
    }

    @Override
    public boolean letter(UnicodeChar symbol, TypesettingContext tc,
            Context context, TokenSource source, Locator locator)
            throws TypesetterException {

        ListMaker lm = getManager().ensureHorizontalMode(locator);
        if (!(lm instanceof TokenDelegateListMaker)) {
            // TODO gene: letter unimplemented
            throw new RuntimeException("unimplemented");
        }
        return ((TokenDelegateListMaker) lm).letter(symbol, tc, context,
            source, locator);
    }

    /**
     * <tt>\par</tt> s are silently ignored in vertical mode.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#par()
     */
    @Override
    public void par() throws TypesetterException, ConfigurationException {

        try {
            // Note: the observers have to be run in reverse order to restore
            // the language properly.
            for (int i = afterParagraphObservers.size() - 1; i >= 0; i--) {
                afterParagraphObservers.get(i).atParagraph(nodes);
            }
        } catch (Exception e) {
            throw new TypesetterException(e);
        }
        // nothing more to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#removeLastNode()
     */
    @Override
    public void removeLastNode() {

        nodes.remove(nodes.size() - 1);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#setPrevDepth(org.extex.core.dimen.FixedDimen)
     */
    @Override
    public void setPrevDepth(FixedDimen pd) {

        if (prevDepth == null) {
            prevDepth = new Dimen(pd);
        } else {
            prevDepth.set(pd);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.ListMaker#showlist(StringBuilder, long, long)
     */
    public void showlist(StringBuilder sb, long l, long m) {

        sb.append("prevdepth ");
        if (prevDepth == null) {
            sb.append("ignored");
        } else {
            prevDepth.toString(sb);
        }
        sb.append('\n');
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return super.toString() + "\n" + nodes.toString();
    }

}
