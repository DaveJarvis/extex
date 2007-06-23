/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.itextpdf;

import java.awt.Color;
import java.io.IOException;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.font.FontKey;
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
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.WhatsItNode;
import org.extex.util.Unit;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * PDF NodeVisitor.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class PdfNodeVisitor implements NodeVisitor<Object, Object> {

    /**
     * current x position.
     */
    private Dimen currentX = new Dimen();

    /**
     * current y position.
     */
    private Dimen currentY = new Dimen();

    /**
     * The pdf document.
     */
    private Document document;

    /**
     * The pdf writer.
     */
    private PdfWriter writer;

    /**
     * The direct content of the pdf page.
     */
    private PdfContentByte cb;

    /**
     * Creates a new object.
     * 
     * @param document The pdf document.
     * @param writer The pdf writer.
     */
    public PdfNodeVisitor(Document document, PdfWriter writer) {

        this.document = document;
        this.writer = writer;
        cb = writer.getDirectContent();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(org.extex.typesetter.type.node.AdjustNode,
     *      java.lang.Object)
     */
    public Object visitAdjust(AdjustNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitAdjust unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(org.extex.typesetter.type.node.AfterMathNode,
     *      java.lang.Object)
     */
    public Object visitAfterMath(AfterMathNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitAfterMath unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(org.extex.typesetter.type.node.AlignedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitAlignedLeaders(AlignedLeadersNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitAlignedLeaders unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(org.extex.typesetter.type.node.BeforeMathNode,
     *      java.lang.Object)
     */
    public Object visitBeforeMath(BeforeMathNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitBeforeMath unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(org.extex.typesetter.type.node.CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Object visitCenteredLeaders(CenteredLeadersNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitCenteredLeaders unimplemented
        return null;
    }

    /**
     * the color from the character before.
     */
    private org.extex.color.Color oldcolor = null;

    /**
     * the fount key from the character before.
     */
    private FontKey oldfountkey = null;

    /**
     * The base font.
     */
    private BaseFont bf;

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(org.extex.typesetter.type.node.CharNode,
     *      java.lang.Object)
     */
    public Object visitChar(CharNode node, Object value)
            throws GeneralException {

        try {
            UnicodeChar uc = node.getCharacter();
            Font newfont = node.getTypesettingContext().getFont();
            FontKey newfountkey = newfont.getFontKey();
            org.extex.color.Color newcolor =
                    node.getTypesettingContext().getColor();

            if (!newfountkey.equals(oldfountkey)) {
                bf =
                        BaseFont.createFont(BaseFont.HELVETICA,
                            BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                oldfountkey = newfountkey;
            }

            // the same color?
            if (!newcolor.equals(oldcolor)) {
                // newcolor.visit(colorVisitor, contentStream);
                oldcolor = newcolor;
            }
            cb.beginText();
            cb.setFontAndSize(bf, (float) Unit.getDimenAsPT(newfont
                .getActualSize()));
            cb.setTextMatrix(Unit.getDimenAsBP(currentX), pageSize.height()
                    - Unit.getDimenAsBP(currentY));
            cb.showText(uc.toString());
            cb.endText();

            drawNode(node);

            currentX.add(node.getWidth());
        } catch (Exception e) {
            throw new GeneralException(e.getMessage());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(org.extex.typesetter.type.node.DiscretionaryNode,
     *      java.lang.Object)
     */
    public Object visitDiscretionary(DiscretionaryNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitDiscretionary unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(org.extex.typesetter.type.node.ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitExpandedLeaders(ExpandedLeadersNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitExpandedLeaders unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(org.extex.typesetter.type.node.GlueNode,
     *      java.lang.Object)
     */
    public Object visitGlue(GlueNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitGlue unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(org.extex.typesetter.type.node.HorizontalListNode,
     *      java.lang.Object)
     */
    public Object visitHorizontalList(HorizontalListNode node, Object value)
            throws GeneralException {

        Dimen saveX = new Dimen(currentX);
        Dimen saveY = new Dimen(currentY);
        drawNode(node);

        for (Node n : node) {
            n.visit(this, node);
        }
        currentX.set(saveX);
        currentY.set(saveY);

        // drawNode(node);
        currentX.add(node.getWidth());

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(org.extex.typesetter.type.node.InsertionNode,
     *      java.lang.Object)
     */
    public Object visitInsertion(InsertionNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitInsertion unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(org.extex.typesetter.type.node.KernNode,
     *      java.lang.Object)
     */
    public Object visitKern(KernNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitKern unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(org.extex.typesetter.type.node.LigatureNode,
     *      java.lang.Object)
     */
    public Object visitLigature(LigatureNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitLigature unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(org.extex.typesetter.type.node.MarkNode,
     *      java.lang.Object)
     */
    public Object visitMark(MarkNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitMark unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(org.extex.typesetter.type.node.PenaltyNode,
     *      java.lang.Object)
     */
    public Object visitPenalty(PenaltyNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitPenalty unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(org.extex.typesetter.type.node.RuleNode,
     *      java.lang.Object)
     */
    public Object visitRule(RuleNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitRule unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(org.extex.typesetter.type.node.SpaceNode,
     *      java.lang.Object)
     */
    public Object visitSpace(SpaceNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitSpace unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(org.extex.typesetter.type.node.VerticalListNode,
     *      java.lang.Object)
     */
    public Object visitVerticalList(VerticalListNode node, Object value)
            throws GeneralException {

        Dimen saveX = new Dimen(currentX);
        Dimen saveY = new Dimen(currentY);
        drawNode(node);

        for (Node n : node) {
            n.visit(this, node);
        }
        currentX.set(saveX);
        currentY.set(saveY);

        // drawNode(node);
        currentY.add(node.getDepth());
        currentY.add(node.getHeight());

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(org.extex.typesetter.type.node.VirtualCharNode,
     *      java.lang.Object)
     */
    public Object visitVirtualChar(VirtualCharNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitVirtualChar unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(org.extex.typesetter.type.node.WhatsItNode,
     *      java.lang.Object)
     */
    public Object visitWhatsIt(WhatsItNode node, Object value)
            throws GeneralException {

        // TODO mgn: visitWhatsIt unimplemented
        return null;
    }

    /**
     * Set the x position.
     * 
     * @param x The x value.
     */
    public void setX(FixedDimen x) {

        currentX.set(x);

    }

    /**
     * Set the y position.
     * 
     * @param y The y value.
     */
    public void setY(FixedDimen y) {

        currentY.set(y);

    }

    /**
     * linewidth.
     */
    private static final float LINEWIDTH = 0.1f;

    /**
     * The page size.
     */
    private Rectangle pageSize;

    /**
     * Draw a box around the node (only for test).
     * 
     * @param node the node
     * @throws DocumentWriterException if an error occurred.
     */
    private void drawNode(Node node) throws DocumentWriterException {

        if (node instanceof VerticalListNode) {
            cb.setColorStroke(Color.RED);
        } else if (node instanceof HorizontalListNode) {
            cb.setColorStroke(Color.YELLOW);
        } else {
            cb.setColorStroke(Color.GREEN);
        }
        cb.setLineWidth(LINEWIDTH);
        float cx = Unit.getDimenAsBP(currentX);
        float cy = Unit.getDimenAsBP(currentY);
        float w = Unit.getDimenAsBP(node.getWidth());
        float h = Unit.getDimenAsBP(node.getHeight());
        float d = Unit.getDimenAsBP(node.getDepth());

        cb.moveTo(cx, pageSize.height() - cy);
        cb.lineTo(cx + w, pageSize.height() - cy);
        cb.moveTo(cx, pageSize.height() - cy);
        cb.lineTo(cx, pageSize.height() - cy + h);
        cb.moveTo(cx + w, pageSize.height() - cy);
        cb.lineTo(cx + w, pageSize.height() - cy + h);
        cb.moveTo(cx + w, pageSize.height() - cy + h);
        cb.lineTo(cx, pageSize.height() - cy + h);
        if (node.getDepth().getValue() != 0) {
            cb.moveTo(cx, pageSize.height() - cy);
            cb.lineTo(cx, pageSize.height() - cy - d);
            cb.stroke();
            cb.moveTo(cx, pageSize.height() - cy - d);
            cb.lineTo(cx + w, pageSize.height() - cy - d);
            cb.stroke();
            cb.moveTo(cx + w, pageSize.height() - cy - d);
            cb.lineTo(cx + w, pageSize.height() - cy);
            cb.stroke();
        }
    }

    /**
     * Set the page size.
     * 
     * @param pageSize The size of the page.
     */
    public void setPageSize(Rectangle pageSize) {

        this.pageSize = pageSize;

    }

}
