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

package org.extex.backend.documentWriter.pdf;

import java.awt.Color;
import java.io.IOException;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.backend.documentWriter.pdf.pdfbox.PdfBoxType1Font;
import org.extex.color.ColorVisitor;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
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
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.edit.PDPageContentStream;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.pdmodel.font.PDType1Font;
import org.pdfbox.pdmodel.graphics.path.BasePath;

/**
 * PDF NodeVisitor.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class PdfNodeVisitor implements NodeVisitor<Object, Object> {

    /**
     * linewidth.
     */
    private static final float LINEWIDTH = 0.1f;

    /**
     * current x position.
     */
    private Dimen currentX;

    /**
     * current y position.
     */
    private Dimen currentY;

    /**
     * paper height in BP.
     */
    private float phBP = PdfDocumentWriter.HEIGHT_A4_BP;

    /**
     * pdf content stream.
     */
    private PDPageContentStream contentStream;

    /**
     * the pdf color visitor.
     */
    private ColorVisitor colorVisitor;

    /**
     * the pdf document.
     */
    private PDDocument document;

    /**
     * Create a new object.
     * 
     * @param doc the pdf document
     * @param cs the pdf content stream
     * @param cx the current x
     * @param cy the current y
     */
    public PdfNodeVisitor(PDDocument doc, PDPageContentStream cs, Dimen cx,
            Dimen cy) {

        document = doc;
        contentStream = cs;
        currentX = cx;
        currentY = cy;
        colorVisitor = new PdfColorVisitor();
    }

    /**
     * Draw a box around the node (only for test).
     * 
     * @param node the node
     * @throws DocumentWriterException if an error occurred.
     */
    private void drawNode(Node node) throws DocumentWriterException {

        try {
            if (node instanceof VerticalListNode) {
                contentStream.setStrokingColor(Color.RED);
            } else if (node instanceof HorizontalListNode) {
                contentStream.setStrokingColor(Color.YELLOW);
            } else {
                contentStream.setStrokingColor(Color.GREEN);
            }
            float cx = Unit.getDimenAsBP(currentX);
            float cy = Unit.getDimenAsBP(currentY);
            float w = Unit.getDimenAsBP(node.getWidth());
            float h = Unit.getDimenAsBP(node.getHeight());
            float d = Unit.getDimenAsBP(node.getDepth());
            BasePath path = new BasePath();
            path.setLineWidth(LINEWIDTH);
            path.moveTo(cx, phBP - cy);
            path.lineTo(cx + w, phBP - cy);
            path.stroke();
            path.moveTo(cx, phBP - cy);
            path.lineTo(cx, phBP - cy + h);
            path.stroke();
            path.moveTo(cx + w, phBP - cy);
            path.lineTo(cx + w, phBP - cy + h);
            path.stroke();
            path.moveTo(cx + w, phBP - cy + h);
            path.lineTo(cx, phBP - cy + h);
            path.stroke();
            if (node.getDepth().getValue() != 0) {
                path.moveTo(cx, phBP - cy);
                path.lineTo(cx, phBP - cy - d);
                path.stroke();
                path.moveTo(cx, phBP - cy - d);
                path.lineTo(cx + w, phBP - cy - d);
                path.stroke();
                path.moveTo(cx + w, phBP - cy - d);
                path.lineTo(cx + w, phBP - cy);
                path.stroke();
            }
            contentStream.drawPath(path);
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
    }

    /**
     * Set the paper height.
     * 
     * @param ph The paper height to set.
     */
    public void setPaperheight(Dimen ph) {

        phBP = Unit.getDimenAsBP(ph);
    }

    // -----------------------------------------
    // -----------------------------------------
    // -----------------------------------------
    // -----------------------------------------
    // -----------------------------------------
    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(AdjustNode,
     *      java.lang.Object)
     */
    public Object visitAdjust(AdjustNode node, Object value2) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(AfterMathNode,
     *      java.lang.Object)
     */
    public Object visitAfterMath(AfterMathNode node, Object value2) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(AlignedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitAlignedLeaders(AlignedLeadersNode node, Object value2) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(BeforeMathNode,
     *      java.lang.Object)
     */
    public Object visitBeforeMath(BeforeMathNode node, Object value2) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Object visitCenteredLeaders(CenteredLeadersNode node, Object value) {

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
     * the pdf font.
     */
    private PDFont pdfont = PDType1Font.HELVETICA;

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(CharNode,
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
                pdfont = PdfBoxType1Font.getInstance(document, newfont);
                oldfountkey = newfountkey;
            }

            // the same color?
            if (!newcolor.equals(oldcolor)) {
                newcolor.visit(colorVisitor, contentStream);
                oldcolor = newcolor;
            }
            contentStream.beginText();
            contentStream.setFont(pdfont, (float) Unit.getDimenAsPT(newfont
                .getActualSize()));
            contentStream.moveTextPositionByAmount(Unit.getDimenAsBP(currentX),
                phBP - Unit.getDimenAsBP(currentY));
            contentStream.drawString(uc.toString());
            contentStream.endText();

            drawNode(node);

            currentX.add(node.getWidth());
            // } catch (DocumentException e) {
            // throw new DocumentWriterException(e);
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
            // } catch (FontException e) {
            // throw new DocumentWriterFontException(e);
            // } catch (PdfException e) {
            // throw new DocumentWriterPdfDocumentException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(DiscretionaryNode,
     *      java.lang.Object)
     */
    public Object visitDiscretionary(DiscretionaryNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitExpandedLeaders(ExpandedLeadersNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(GlueNode,
     *      java.lang.Object)
     */
    public Object visitGlue(GlueNode node, Object value) {

        currentX.add(node.getWidth());
        currentY.add(node.getHeight());
        currentY.add(node.getDepth());
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(HorizontalListNode,
     *      java.lang.Object)
     */
    public Object visitHorizontalList(HorizontalListNode node, Object value)
            throws GeneralException {

        // try {
        Dimen saveX = new Dimen(currentX);
        Dimen saveY = new Dimen(currentY);

        for (Node n : node) {
            n.visit(this, node);
        }
        currentX.set(saveX);
        currentY.set(saveY);

        drawNode(node);

        currentX.add(node.getWidth());
        // } catch (PdfException e) {
        // throw new DocumentWriterPdfDocumentException(e);
        // }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(InsertionNode,
     *      java.lang.Object)
     */
    public Object visitInsertion(InsertionNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(KernNode,
     *      java.lang.Object)
     */
    public Object visitKern(KernNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(LigatureNode,
     *      java.lang.Object)
     */
    public Object visitLigature(LigatureNode node, Object value) {

        // Element element = new Element("ligature");
        // addNodeAttributes(node, element);
        // Node first = node.getLeft();
        // Node second = node.getRight();
        // if (first != null) {
        // Element e = getNodeElement(first);
        // if (e != null) {
        // element.addContent(e);
        // }
        // }
        // if (second != null) {
        // Element e = getNodeElement(second);
        // if (e != null) {
        // element.addContent(e);
        // }
        // }
        // return element;
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(MarkNode,
     *      java.lang.Object)
     */
    public Object visitMark(MarkNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(PenaltyNode,
     *      java.lang.Object)
     */
    public Object visitPenalty(PenaltyNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(RuleNode,
     *      java.lang.Object)
     */
    public Object visitRule(RuleNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(SpaceNode,
     *      java.lang.Object)
     */
    public Object visitSpace(SpaceNode node, Object value) {

        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(VerticalListNode,
     *      java.lang.Object)
     */
    public Object visitVerticalList(VerticalListNode node, Object value)
            throws GeneralException {

        // try {
        Dimen saveX = new Dimen(currentX);
        Dimen saveY = new Dimen(currentY);

        for(Node n : node) {
            n.visit(this, node);
        }
        currentX.set(saveX);
        currentY.set(saveY);

        drawNode(node);

        currentY.add(node.getDepth());
        currentY.add(node.getHeight());
        // } catch (PdfException e) {
        // throw new DocumentWriterPdfDocumentException(e);
        // }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
     *      org.extex.typesetter.type.node.VirtualCharNode, java.lang.Object)
     */
    public Object visitVirtualChar(VirtualCharNode node, Object value)
            throws GeneralException {

        // TODO visitVirtualChar unimplemented
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(WhatsItNode,
     *      java.lang.Object)
     */
    public Object visitWhatsIt(WhatsItNode node, Object value) {

        return null;
    }
}
