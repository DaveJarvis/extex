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

package org.extex.backend.documentWriter.postscript.converter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.backend.documentWriter.postscript.util.PsUnit;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
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
public class PsBoxConverter extends AbstractConverter
        implements
            PsConverter,
            NodeVisitor<Object, PrintStream> {

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
    private void drawBox(Node node, PrintStream out, FixedDimen height,
            String box) {

        if (height.ne(Dimen.ZERO_PT)) {
            PsUnit.toPoint(node.getWidth(), out, false);
            out.write(' ');
            PsUnit.toPoint(height, out, false);
            out.write(' ');
            PsUnit.toPoint(x, out, false);
            out.write(' ');
            PsUnit.toPoint(y, out, false);
            out.write(' ');
            out.append(box);
            out.println();
        }
    }

    /**
     * This method draws a single box. It makes use of a PostScript def to do
     * the real job.
     * 
     * @param node the node to draw
     * @param out the target output destination
     * @param box the name of the box command to use for printing
     * 
     * @return <code>null</code>
     */
    private Object drawBox(Node node, PrintStream out, String box) {

        if (trace) {
            trace(out, node);
        }

        drawBox(node, out, node.getHeight(), box);

        Dimen depth = new Dimen(node.getDepth());
        depth.negate();
        drawBox(node, out, depth, box);
        return null;
    }

    /**
     * The field <tt>ps</tt> contains the PS code manager.
     */
    private Ps ps;

    /**
     * Translate nodes into PostScript code. This method traverses the nodes
     * tree recursively and produces the corresponding PostScript code for each
     * node visited.
     * 
     * @param page the nodes to translate into PostScript code
     * 
     * @return the bytes representing the current page
     * 
     * @throws DocumentWriterException in case of an error
     */
    public byte[] toPostScript(Page page) throws DocumentWriterException {

        x.set(page.getMediaHOffset());
        y.set(page.getMediaHeight());
        y.subtract(page.getMediaVOffset());

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream, true);
        out.println("TeXDict begin");

        try {
            page.getNodes().visit(this, out);
        } catch (GeneralException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {

                throw new DocumentWriterIOException(cause);
            }
            throw new DocumentWriterException(e);
        }

        out.println("end");
        return outStream.toByteArray();
    }

    /**
     * Add the current node type as comment to the postscript code for tracing.
     * 
     * @param out the output stream
     * @param node the node
     */
    private void trace(PrintStream out, Node node) {

        out.print("% ");
        String name = node.getClass().getName();
        out.println(name.substring(name.lastIndexOf('.') + 1));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
     *      org.extex.typesetter.type.node.AdjustNode, java.lang.Object)
     */
    public Object visitAdjust(AdjustNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
     *      org.extex.typesetter.type.node.AfterMathNode, java.lang.Object)
     */
    public Object visitAfterMath(AfterMathNode node, PrintStream out)
            throws GeneralException {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
     *      org.extex.typesetter.type.node.AlignedLeadersNode, java.lang.Object)
     */
    public Object visitAlignedLeaders(AlignedLeadersNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
     *      org.extex.typesetter.type.node.BeforeMathNode, java.lang.Object)
     */
    public Object visitBeforeMath(BeforeMathNode node, PrintStream out)
            throws GeneralException {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
     *      org.extex.typesetter.type.node.CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Object visitCenteredLeaders(CenteredLeadersNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(
     *      org.extex.typesetter.type.node.CharNode, java.lang.Object)
     */
    public Object visitChar(CharNode node, PrintStream out)
            throws GeneralException {

        drawBox(node, out, "box");

        TypesettingContext tc = node.getTypesettingContext();

        if (showChars) {
            PsUnit.toPoint(x, out, false);
            out.append(' ');
            PsUnit.toPoint(y, out, false);
            out.append(" moveto ");
            Font font = tc.getFont();
            UnicodeChar c = node.getCharacter();
            String f = getFontManager().add(font, c);
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
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
     *      org.extex.typesetter.type.node.DiscretionaryNode, java.lang.Object)
     */
    public Object visitDiscretionary(DiscretionaryNode node, PrintStream out)
            throws GeneralException {

        if (trace) {
            out.append("% ");
            String name = node.getClass().getName();
            out.append(name.substring(name.lastIndexOf('.') + 1));
            out.append('\n');
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
     *      org.extex.typesetter.type.node.ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitExpandedLeaders(ExpandedLeadersNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
     *      org.extex.typesetter.type.node.GlueNode, java.lang.Object)
     */
    public Object visitGlue(GlueNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
     *      org.extex.typesetter.type.node.HorizontalListNode, java.lang.Object)
     */
    public Object visitHorizontalList(HorizontalListNode node, PrintStream out)
            throws GeneralException {

        Dimen saveX = new Dimen(x);
        Dimen saveY = new Dimen(y);
        x.add(node.getMove());
        y.add(node.getShift());

        drawBox(node, out, "Box");

        for (Node n : node) {
            n.visit(this, out);
            x.add(n.getWidth());
        }

        x.set(saveX);
        y.set(saveY);

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
     *      org.extex.typesetter.type.node.InsertionNode, java.lang.Object)
     */
    public Object visitInsertion(InsertionNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(
     *      org.extex.typesetter.type.node.KernNode, java.lang.Object)
     */
    public Object visitKern(KernNode node, PrintStream out)
            throws GeneralException {

        if (trace) {
            trace(out, node);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
     *      org.extex.typesetter.type.node.LigatureNode, java.lang.Object)
     */
    public Object visitLigature(LigatureNode node, PrintStream out)
            throws GeneralException {

        return visitChar(node, out);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(
     *      org.extex.typesetter.type.node.MarkNode, java.lang.Object)
     */
    public Object visitMark(MarkNode node, PrintStream out)
            throws GeneralException {

        if (trace) {
            trace(out, node);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
     *      org.extex.typesetter.type.node.PenaltyNode, java.lang.Object)
     */
    public Object visitPenalty(PenaltyNode node, PrintStream out)
            throws GeneralException {

        if (trace) {
            trace(out, node);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(
     *      org.extex.typesetter.type.node.RuleNode, java.lang.Object)
     */
    public Object visitRule(RuleNode node, PrintStream out)
            throws GeneralException {

        return drawBox(node, out, "box");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
     *      org.extex.typesetter.type.node.SpaceNode, java.lang.Object)
     */
    public Object visitSpace(SpaceNode node, PrintStream out)
            throws GeneralException {

        if (trace) {
            trace(out, node);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
     *      org.extex.typesetter.type.node.VerticalListNode, java.lang.Object)
     */
    public Object visitVerticalList(VerticalListNode node, PrintStream out)
            throws GeneralException {

        Dimen saveX = new Dimen(x);
        Dimen saveY = new Dimen(y);
        x.add(node.getMove());
        y.add(node.getShift());

        drawBox(node, out, "Box");

        for (Node n : node) {
            n.visit(this, out);
            y.subtract(n.getHeight());
            y.subtract(n.getDepth());
        }

        x.set(saveX);
        y.set(saveY);

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
     *      org.extex.typesetter.type.node.VirtualCharNode, java.lang.Object)
     */
    public Object visitVirtualChar(VirtualCharNode node, PrintStream out)
            throws GeneralException {

        return visitChar(node, out);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
     *      org.extex.typesetter.type.node.WhatsItNode, java.lang.Object)
     */
    public Object visitWhatsIt(WhatsItNode node, PrintStream out)
            throws GeneralException {

        drawBox(node, out, "box");

        if (node instanceof SpecialNode) {
            treatSpecial(out, (SpecialNode) node);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.postscript.converter.AbstractConverter#writeHeaders(
     *      java.io.PrintStream)
     */
    @Override
    public void writeHeaders(PrintStream out) throws IOException {

        super.writeHeaders(out);
        ps.writeDict(out);
    }

}