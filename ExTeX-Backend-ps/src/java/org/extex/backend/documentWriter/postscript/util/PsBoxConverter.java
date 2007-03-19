/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.postscript.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.type.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.AdjustNode;
import org.extex.typesetter.type.node.AfterMathNode;
import org.extex.typesetter.type.node.AlignedLeadersNode;
import org.extex.typesetter.type.node.BeforeMathNode;
import org.extex.typesetter.type.node.CenteredLeadersNode;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.ExpandedLeadersNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.InsertionNode;
import org.extex.typesetter.type.node.KernNode;
import org.extex.typesetter.type.node.LigatureNode;
import org.extex.typesetter.type.node.MarkNode;
import org.extex.typesetter.type.node.PenaltyNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.SpaceNode;
import org.extex.typesetter.type.node.SpecialNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.WhatsItNode;
import org.extex.typesetter.type.page.Page;

/**
 * This class provides a converter to PostScript code which shows mainly the
 * boxes of the characters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PsBoxConverter implements PsConverter, NodeVisitor {

    /**
     * The field <tt>fm</tt> contains the font manager.
     */
    private FontManager fm = null;

    /**
     * The field <tt>showChars</tt> contains the indicator whether the
     * characters should be approximated in the output. If it is
     * <code>false</code> then only boxes are produced.
     */
    private boolean showChars = true;

    /**
     * The field <tt>trace</tt> contains the indicator whether the node names
     * should be produced in the output.
     */
    private boolean trace = true;

    /**
     * The field <tt>x</tt> contains the current x position.
     */
    private Dimen x = new Dimen();

    /**
     * The field <tt>y</tt> contains the current y position.
     */
    private Dimen y = new Dimen();

    /**
     * Creates a new object.
     */
    public PsBoxConverter() {

        super();
    }

    /**
     * Draw a little box showing the dimensions of the node.
     *
     * @param node the node to draw
     * @param out the target string buffer
     * @param height the height; this can be negative as well
     * @param box the name of the box command to use for printing
     */
    private void drawBox(final Node node, final StringBuffer out,
            final FixedDimen height, final String box) {

        if (height.ne(Dimen.ZERO_PT)) {
            PsUnit.toPoint(node.getWidth(), out, false);
            out.append(' ');
            PsUnit.toPoint(height, out, false);
            out.append(' ');
            PsUnit.toPoint(x, out, false);
            out.append(' ');
            PsUnit.toPoint(y, out, false);
            out.append(' ');
            out.append(box);
            out.append('\n');
        }
    }

    /**
     * This method draws a single box.
     * It makes use of a PostScript def to do the real job.
     *
     * @param node the node to draw
     * @param out the target string buffer
     * @param box the name of the box command to use for printing
     */
    private void drawBox(final Node node, final StringBuffer out,
            final String box) {

        if (trace) {
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }

        drawBox(node, out, node.getHeight(), box);

        Dimen depth = new Dimen(node.getDepth());
        depth.negate();
        drawBox(node, out, depth, box);
    }

    /**
     * Perform some initializations for each document.
     *
     * @param header the target writer
     *
     * @throws IOException in case of an error during the writing
     */
    public void init(final HeaderManager header) throws IOException {

        String name = this.getClass().getName().replace('.', '/') + ".ps";
        InputStream stream =
                getClass().getClassLoader().getResourceAsStream(name);
        if (stream != null) {
            header.add(stream, name.substring(name.lastIndexOf('/') + 1));
            stream.close();
        }
    }

    /**
     * Translate nodes into PostScript code.
     * This method traverses the nodes tree recursively and produces the
     * corresponding PostScript code for each node visited.
     *
     * @param page the nodes to translate into PostScript code
     * @param fontManager the font manager to inform about characters
     * @param headerManager the header manager
     *
     * @return the bytes representing the current page
     *
     * @throws DocumentWriterException in case of an error
     */
    public byte[] toPostScript(final Page page, final FontManager fontManager,
            final HeaderManager headerManager) throws DocumentWriterException {

        fm = fontManager;

        x.set(page.getMediaHOffset());
        y.set(page.getMediaHeight());
        y.subtract(page.getMediaVOffset());

        StringBuffer out = new StringBuffer();
        out.append("TeXDict begin\n");

        try {
            page.getNodes().visit(this, out);
        } catch (GeneralException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {

                throw new DocumentWriterIOException(cause);
            }
            throw new DocumentWriterException(e);
        }

        out.append("end\n");
        return out.toString().getBytes();
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
     *      org.extex.typesetter.type.node.AdjustNode,
     *      java.lang.Object)
     */
    public Object visitAdjust(final AdjustNode node, final Object oOut)
            throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
     *      org.extex.typesetter.type.node.AfterMathNode,
     *      java.lang.Object)
     */
    public Object visitAfterMath(final AfterMathNode node, final Object oOut)
            throws GeneralException {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
     *      org.extex.typesetter.type.node.AlignedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitAlignedLeaders(final AlignedLeadersNode node,
            final Object oOut) throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
     *      org.extex.typesetter.type.node.BeforeMathNode,
     *      java.lang.Object)
     */
    public Object visitBeforeMath(final BeforeMathNode node, final Object oOut)
            throws GeneralException {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
     *      org.extex.typesetter.type.node.CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Object visitCenteredLeaders(final CenteredLeadersNode node,
            final Object oOut) throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(
     *      org.extex.typesetter.type.node.CharNode,
     *      java.lang.Object)
     */
    public Object visitChar(final CharNode node, final Object oOut)
            throws GeneralException {

        StringBuffer out = (StringBuffer) oOut;
        drawBox(node, out, "box");

        TypesettingContext tc = node.getTypesettingContext();

        if (showChars) {
            PsUnit.toPoint(x, out, false);
            out.append(' ');
            PsUnit.toPoint(y, out, false);
            out.append(" moveto ");
            Font font = tc.getFont();
            UnicodeChar c = node.getCharacter();
            String f = fm.add(font, c);
            if (f != null) {
                out.append(f);
            }
            out.append('(');
            switch (c.getCodePoint()) {
                case '\\':
                case '(':
                case ')':
                    out.append('\\');
                    break;
                default:
                    // nothing to do
            }
            out.append(c.toString());
            out.append(") show\n");
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
     *      org.extex.typesetter.type.node.DiscretionaryNode,
     *      java.lang.Object)
     */
    public Object visitDiscretionary(final DiscretionaryNode node,
            final Object oOut) throws GeneralException {

        if (trace) {
            StringBuffer out = (StringBuffer) oOut;
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
     *      org.extex.typesetter.type.node.ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitExpandedLeaders(final ExpandedLeadersNode node,
            final Object oOut) throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
     *      org.extex.typesetter.type.node.GlueNode,
     *      java.lang.Object)
     */
    public Object visitGlue(final GlueNode node, final Object oOut)
            throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
     *      org.extex.typesetter.type.node.HorizontalListNode,
     *      java.lang.Object)
     */
    public Object visitHorizontalList(final HorizontalListNode node,
            final Object oOut) throws GeneralException {

        Dimen saveX = new Dimen(x);
        Dimen saveY = new Dimen(y);
        x.add(node.getMove());
        y.add(node.getShift());

        drawBox(node, (StringBuffer) oOut, "Box");
        Node n;
        int len = node.size();

        for (int i = 0; i < len; i++) {
            n = node.get(i);
            n.visit(this, oOut);
            x.add(n.getWidth());
        }

        x.set(saveX);
        y.set(saveY);

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
     *      org.extex.typesetter.type.node.InsertionNode,
     *      java.lang.Object)
     */
    public Object visitInsertion(final InsertionNode node, final Object oOut)
            throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(
     *      org.extex.typesetter.type.node.KernNode,
     *      java.lang.Object)
     */
    public Object visitKern(final KernNode node, final Object oOut)
            throws GeneralException {

        if (trace) {
            StringBuffer out = (StringBuffer) oOut;
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
     *      org.extex.typesetter.type.node.LigatureNode,
     *      java.lang.Object)
     */
    public Object visitLigature(final LigatureNode node, final Object oOut)
            throws GeneralException {

        return visitChar(node, oOut);
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(
     *      org.extex.typesetter.type.node.MarkNode,
     *      java.lang.Object)
     */
    public Object visitMark(final MarkNode node, final Object oOut)
            throws GeneralException {

        if (trace) {
            StringBuffer out = (StringBuffer) oOut;
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
     *      org.extex.typesetter.type.node.PenaltyNode,
     *      java.lang.Object)
     */
    public Object visitPenalty(final PenaltyNode node, final Object oOut)
            throws GeneralException {

        if (trace) {
            StringBuffer out = (StringBuffer) oOut;
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(
     *      org.extex.typesetter.type.node.RuleNode,
     *      java.lang.Object)
     */
    public Object visitRule(final RuleNode node, final Object oOut)
            throws GeneralException {

        drawBox(node, (StringBuffer) oOut, "box");
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
     *      org.extex.typesetter.type.node.SpaceNode,
     *      java.lang.Object)
     */
    public Object visitSpace(final SpaceNode node, final Object oOut)
            throws GeneralException {

        if (trace) {
            StringBuffer out = (StringBuffer) oOut;
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
     *      org.extex.typesetter.type.node.VerticalListNode,
     *      java.lang.Object)
     */
    public Object visitVerticalList(final VerticalListNode node,
            final Object oOut) throws GeneralException {

        Dimen saveX = new Dimen(x);
        Dimen saveY = new Dimen(y);
        x.add(node.getMove());
        y.add(node.getShift());

        drawBox(node, (StringBuffer) oOut, "Box");
        Node n;
        int len = node.size();

        for (int i = 0; i < len; i++) {
            n = node.get(i);
            n.visit(this, oOut);
            y.subtract(n.getHeight());
            y.subtract(n.getDepth());
        }

        x.set(saveX);
        y.set(saveY);

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
     *      org.extex.typesetter.type.node.VirtualCharNode,
     *      java.lang.Object)
     */
    public Object visitVirtualChar(final VirtualCharNode node, final Object oOut)
            throws GeneralException {

        return visitChar(node, oOut);
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
     *      org.extex.typesetter.type.node.WhatsItNode,
     *      java.lang.Object)
     */
    public Object visitWhatsIt(final WhatsItNode node, final Object oOut)
            throws GeneralException {

        StringBuffer out = (StringBuffer) oOut;
        drawBox(node, (StringBuffer) oOut, "box");

        if (node instanceof SpecialNode) {
            String text = ((SpecialNode) node).getText();
            if (text.startsWith("ps:")) {
                out.append(text.substring(3));
            } else {
                // unknown specials are ignored on purpose
            }
        }
        return null;
    }

}
