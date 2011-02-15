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

package org.extex.unit.tex.table.util;

import java.util.ArrayList;
import java.util.List;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.ListManager;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.AlignmentList;
import org.extex.typesetter.listMaker.RestrictedHorizontalListMaker;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.unit.tex.table.Noalign;
import org.extex.unit.tex.table.Omit;

/**
 * This class provides a list maker for horizontal alignments.
 * 
 * @see "TTP [770]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class HAlignListMaker extends RestrictedHorizontalListMaker
        implements
            AlignmentList {

    /**
     * This inner class is a container for the cell information in an alignment.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4770 $
     */
    protected static class Cell {

        /**
         * The field <tt>list</tt> contains the nodes of this cell.
         */
        private NodeList list = null;

        /**
         * The field <tt>span</tt> contains the indicator that this cell
         * should be joined with the next cell when generating boxes.
         */
        private boolean span = false;

        /**
         * Creates a new object.
         * 
         * @param nodes the nodes of this cell
         */
        public Cell(NodeList nodes) {

            list = nodes;
        }

        /**
         * Getter for list.
         * 
         * @return the list.
         */
        public NodeList getList() {

            return this.list;
        }

        /**
         * Getter for span.
         * 
         * @return the span.
         */
        public boolean isSpan() {

            return this.span;
        }

        /**
         * Setter for span.
         */
        public void setSpan() {

            this.span = true;
        }
    }

    /**
     * The constant <tt>FIXED</tt> contains the default format consisting of
     * the pure contents only.
     */
    private static final PreambleItem FIXED =
            new PreambleItem(Tokens.EMPTY, Tokens.EMPTY);

    /**
     * The field <tt>col</tt> contains the indicator for the current column.
     */
    private int col;

    /**
     * The field <tt>format</tt> contains the format currently in effect.
     */
    private PreambleItem format;

    /**
     * The field <tt>line</tt> contains the cells of the current line.
     */
    private Cell[] line;

    /**
     * The field <tt>preamble</tt> contains the preamble for this halign.
     */
    private List<PreambleItem> preamble;

    /**
     * The field <tt>rows</tt> contains the rows of this alignment.
     */
    private List<Cell[]> rows = new ArrayList<Cell[]>();

    /**
     * The field <tt>spread</tt> contains the indicator that the width should
     * be interpreted relative to the natural width.
     */
    private boolean spread;

    /**
     * The field <tt>maxWidth</tt> contains the maximal width of each column.
     */
    private Dimen[] maxWidth;

    /**
     * The field <tt>width</tt> contains the target width or <code>null</code>
     * to indicate that the natural width should be used.
     */
    private FixedDimen width;

    /**
     * Creates a new object.
     * 
     * @param manager the manager
     * @param context the interpreter context
     * @param source the token source
     * @param thePreamble the list of preamble items
     * @param theWidth the target width or <code>null</code> if the natural
     *        width should be used
     * @param theSpread indicator that the width should be interpreted relative
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public HAlignListMaker(ListManager manager, Context context,
            TokenSource source, List<PreambleItem> thePreamble,
            FixedDimen theWidth, boolean theSpread)
            throws HelpingException,
                TypesetterException {

        super(manager, source.getLocator());
        preamble = thePreamble;
        width = theWidth;
        spread = theSpread;
        clearLine(context, source);

        maxWidth = new Dimen[line.length];

        for (int i = 0; i < line.length; i++) {
            maxWidth[i] = new Dimen(0);
        }
    }

    /**
     * Clear all entries of the current line.
     * 
     * @param context the interpreter context
     * @param source the token source
     * 
     * @throws TypesetterException in case of an error
     */
    private void clearLine(Context context, TokenSource source)
            throws TypesetterException {

        col = 0;
        line = new Cell[preamble.size()];
        startCell(context, source);
    }

    /**
     * Close the node list. This means that everything is done to ship the
     * closed node list to the document writer. Nevertheless the invoking
     * application might decide not to modify the node list and continue
     * processing. In the other case some nodes might be taken from the node
     * list returned by this method. Then the processing has to continue with
     * the reduced node list.
     * 
     * @param context the typesetter options mapping a fragment of the
     *        interpreter context
     * 
     * @return the node list enclosed in this instance
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.typesetter.listMaker.RestrictedHorizontalListMaker#complete(
     *      org.extex.typesetter.TypesetterOptions)
     */
    @Override
    public NodeList complete(TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        NodeList result = new VerticalListNode();
        NodeList nl;

        for (int j = 0; j < rows.size(); j++) {
            NodeList row = new HorizontalListNode();
            line = rows.get(j);

            for (int i = 0; i < line.length; i++) {
                Cell cell = line[i];
                if (cell != null) {
                    nl = cell.getList();
                    if (nl instanceof HorizontalListNode) {
                        ((HorizontalListNode) nl).hpack(new Dimen(maxWidth[i]));
                        // TODO gene: check
                    } else {
                        // TODO gene: unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    row.add(nl);
                }
            }
            result.add(row);
        }

        Dimen w = sum(maxWidth);

        if (width != null) {
            if (spread) {
                w.add(width);
            } else {
                w.set(width);
            }
        }

        result.setWidth(w);

        return result;
    }

    /**
     * This method is invoked when a row in the alignment is complete and the
     * cells can be integrated. If some cells are not filled jet then they are
     * treated as empty.
     * 
     * @param context the interpreter context
     * @param source the token source
     * @param noalign the tokens to be inserted or <code>null</code>
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.AlignmentList#cr(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.type.NodeList)
     */
    public void cr(Context context, TokenSource source, NodeList noalign)
            throws TypesetterException {

        rows.add(line);
        if (noalign != null) {
            // TODO gene: insert noalign
        }
        clearLine(context, source);
    }

    /**
     * This method is invoked when a row in the alignment is complete and the
     * cells can be integrated. If some cells are not filled jet then they are
     * treated as empty. In contrast to the method
     * {@link #cr(Context, TokenSource, NodeList) cr()} this method is a noop
     * when the alignment is at the beginning of a row.
     * 
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.AlignmentList#crcr(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void crcr(Context context, TokenSource source, Typesetter typesetter)
            throws TypesetterException {

        if (col <= 0) {
            return;
        }
        NodeList noalign = null;
        try {
            Token token = source.getToken(context);
            Code code;

            if (token instanceof CodeToken
                    && (code = context.getCode((CodeToken) token)) instanceof Noalign) {
                noalign =
                        ((Noalign) code).exec(context, source, typesetter,
                            token);
            } else {
                source.push(token);
            }
        } catch (HelpingException e) {
            throw new TypesetterException(e);
        }
        cr(context, source, noalign);
    }

    /**
     * The invocation of this method indicates that the pattern for the current
     * cell should not be taken from the preamble but the default should be used
     * instead.
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.AlignmentList#omit()
     */
    public void omit() throws TypesetterException {

        // TODO gene: respect protected macros
    }

    /**
     * This method is invoked when a cell is complete which should be continued
     * in the next cell.
     * 
     * @param context the interpreter context
     * @param source the token source
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see org.extex.typesetter.listMaker.AlignmentList#span(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource)
     */
    public void span(Context context, TokenSource source)
            throws TypesetterException {

        if (col >= line.length) {
            new HelpingException(getLocalizer(), "TTP.ExtraAlignTab", "???");
        }
        col++;
    }

    /**
     * Start a new cell.
     * 
     * @param context the interpreter context
     * @param source the token source
     * 
     * @throws TypesetterException in case of an error
     */
    private void startCell(Context context, TokenSource source)
            throws TypesetterException {

        format = preamble.get(col);

        try {
            Token t = source.scanNonSpace(context);
            if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof Omit) {
                    format = FIXED;
                } else {
                    source.push(t);
                }
            } else {
                source.push(t);
            }
            source.push(format.getPre());
        } catch (HelpingException e) {
            throw new TypesetterException(e);
        }
    }

    /**
     * Compute the sum of an array of dimens.
     * 
     * @param d the dimen array
     * 
     * @return the sum in a new Dimen
     */
    public static Dimen sum(Dimen[] d) {

        Dimen sum = new Dimen();

        for (int i = 0; i < d.length; i++) {
            sum.add(d[i]);
        }
        return sum;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.listMaker.AbstractListMaker#tab(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public void tab(Context context, TokenSource source, Token token)
            throws TypesetterException,
                ConfigurationException {

        if (col >= line.length) {
            new HelpingException(getLocalizer(), "TTP.ExtraAlignTab", token
                .toString());
        }

        try {
            source.push(format.getPost()); // TODO gene: wrong! process the
            // tokens before closing
        } catch (HelpingException e) {
            throw new TypesetterException(e);
        }

        if (!(context instanceof TypesetterOptions)) {
            throw new ImpossibleException("context impl");
        }
        line[col] = new Cell(super.complete((TypesetterOptions) context));
        maxWidth[col].max(line[col].getList().getWidth());
        setNodes(new HorizontalListNode());
        col++;
        startCell(context, source);
    }

}
