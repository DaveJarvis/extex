/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.backend.documentWriter.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterClosedChannelException;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
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
import org.extex.typesetter.type.page.Page;
import org.extex.util.Unit;
import org.extex.util.xml.XMLStreamWriter;

/**
 * This is a xml implementation of a document writer.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4704 $
 */
public class XMLDocumentWriter
        implements
            DocumentWriter,
            SingleDocumentStream,
            NodeVisitor,
            Configurable {

    /**
     * DIN-A4 height.
     */
    private static final double DINA4HEIGHT = 29.7d;

    /**
     * DIN-A4 width.
     */
    private static final double DINA4WIDTH = 21.0d;

    /**
     * format the output for decimal values.
     */
    private static final NumberFormat FORMAT =
            NumberFormat.getInstance(Locale.US);

    /**
     * max fraction.
     */
    private static final int MAXFRACTION = 4;

    /**
     * current x position.
     */
    private Dimen currentX = new Dimen();

    /**
     * current y position.
     */
    private Dimen currentY = new Dimen();

    /**
     * debug.
     */
    private boolean debug = true;

    /**
     * document writer options.
     */
    private DocumentWriterOptions docoptions;

    /**
     * encoding.
     */
    private String encoding = "ISO-8859-1";

    /**
     * xml indent.
     */
    private String indent = "   ";

    /**
     * xml newlines.
     */
    private boolean newlines = true;

    /**
     * The output stream.
     */
    private OutputStream out = null;

    /**
     * paper height.
     */
    private Dimen paperheight;

    /**
     * paper width.
     */
    private Dimen paperwidth;

    /**
     * The number of pages, which are ship out.
     */
    private int shippedPages = 0;

    /**
     * show visible chars.
     */
    private boolean showvisible = true;

    /**
     * xml trimallwhitespace TODO incomplete.
     */
    private boolean trimallwhitespace = true;

    /**
     * use bp.
     */
    private boolean usebp = true;

    /**
     * use mm.
     */
    private boolean usemm = true;

    /**
     * use sp.
     */
    private boolean usesp = true;

    /**
     * Creates a new object.
     * 
     * @param cfg the configuration
     * @param options the options
     */
    public XMLDocumentWriter(Configuration cfg, DocumentWriterOptions options) {

        super();
        docoptions = options;
        FORMAT.setGroupingUsed(false);
        FORMAT.setMaximumFractionDigits(MAXFRACTION);

        if (cfg != null) {
            String tmp = cfg.getAttribute("encoding");
            if (tmp != null && !tmp.equals("")) {
                encoding = tmp;
            }
            tmp = cfg.getAttribute("debug");
            if (tmp != null) {
                debug = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("showvisible");
            if (tmp != null) {
                showvisible = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("indent");
            if (tmp != null) {
                indent = tmp;
            }
            tmp = cfg.getAttribute("newlines");
            if (tmp != null) {
                newlines = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("trimallwhitespace");
            if (tmp != null) {
                trimallwhitespace = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("usesp");
            if (tmp != null) {
                usesp = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("usebp");
            if (tmp != null) {
                usebp = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("usemm");
            if (tmp != null) {
                usemm = Boolean.valueOf(tmp).booleanValue();
            }
        }
    }

    /**
     * Add some Attributes to the node-element.
     * 
     * @param node the node
     * @throws IOException if an error occurs.
     */
    private void addNodeAttributes(Node node) throws IOException {

        Dimen wd = new Dimen(node.getWidth());
        Dimen ht = new Dimen(node.getHeight());
        Dimen dp = new Dimen(node.getDepth());
        // TODO move + shift
        // Dimen move = new Dimen();
        // Dimen shift = new Dimen();

        // --------------------------------------------------------
        setDimenLength("x", currentX);
        setDimenLength("y", currentY);
        setDimenLength("width", wd);
        setDimenLength("height", ht);
        setDimenLength("depth", dp);
        // setDimenLength("move", move);
        // setDimenLength("shift", shift);

        // ---- debug ----
        if (debug) {
            writer.writeCharacters(node.toString());
        }
    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    public void close() throws DocumentWriterException {

        if (out != null) {
            try {
                if (writer != null) {
                    writer.writeEndElement();
                    writer.writeEndDocument();
                    writer.close();
                }
                out.close();
            } catch (IOException e) {
                throw new DocumentWriterIOException(e);
            }
            out = null;
        } else {
            throw new DocumentWriterClosedChannelException("closed channel");
        }
    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
     */
    public String getExtension() {

        return "xml";
    }

    /**
     * Set the Attribute for an element with sp, bp, mm.
     * 
     * @param name the attribute-name
     * @param dimen the dimen
     * @throws IOException if an error occurs.
     */
    private void setDimenLength(String name, Dimen dimen) throws IOException {

        Dimen d = dimen;
        if (dimen == null) {
            d = new Dimen();
        }
        if (usesp) {
            writer.writeAttribute(name + "_sp", String.valueOf(d.getValue()));
        }
        if (usebp) {
            writer.writeAttribute(name + "_bp", String.valueOf(Unit
                .getDimenAsBP(d)));
        }
        if (usemm) {
            String s = FORMAT.format(Unit.getDimenAsMM(d));
            writer.writeAttribute(name + "_mm", s);
        }
    }

    /**
     * @see org.extex.backend.documentWriter.SingleDocumentStream#setOutputStream(
     *      java.io.OutputStream)
     */
    public void setOutputStream(OutputStream outStream) {

        out = outStream;
    }

    /**
     * map for the parameters.
     */
    private Map<String, String> param = new HashMap<String, String>();

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {

        param.put(name, value);
    }

    /**
     * Print the parameter as comment.
     * 
     * @throws IOException if an error occurs.
     */
    private void printParameterComment() throws IOException {

        StringBuffer buf = new StringBuffer();
        buf.append("\n");
        Iterator<String> it = param.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            buf.append(name);
            buf.append("=");
            buf.append(param.get(name));
            buf.append("\n");
        }
        writer.writeComment(buf.toString());
    }

    /**
     * Print the parameter as element.
     * 
     * @throws IOException if an error occurs.
     */
    private void printParameterElement() throws IOException {

        writer.writeStartElement("parameter");
        Iterator<String> it = param.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            writer.writeStartElement(name);
            writer.writeCharacters(param.get(name));
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    /**
     * The XML stream writer.
     */
    private XMLStreamWriter writer;

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(Page page) throws GeneralException {

        NodeList nodes = page.getNodes();

        try {
            if (writer == null) {
                if (out == null) {
                    throw new DocumentWriterClosedChannelException(
                        "closed channel");
                }
                writer = new XMLStreamWriter(out, encoding);
                writer.setBeauty(newlines);
                writer.setIndent(indent);
                writer.writeStartDocument();
                printParameterComment();
                writer.writeStartElement("root");
                printParameterElement();
            }

            writer.writeStartElement("page");
            writer.writeAttribute("id", String.valueOf(shippedPages + 1));

            // TeX primitives should set the papersize in any way:
            // o \paperwidth / \paperheight,
            // o \pdfpagewidth / \pdfpageheight <-- pdfTeX
            // o \mediawidth / \mediaheight <-- VTeX
            if (docoptions != null) {
                paperwidth = (Dimen) docoptions.getDimenOption("paperwidth");
                paperheight = (Dimen) docoptions.getDimenOption("paperheight");
                if (paperheight.getValue() == 0 || paperwidth.getValue() == 0) {
                    // use DIN A4
                    paperwidth = Unit.createDimenFromCM(DINA4WIDTH);
                    paperheight = Unit.createDimenFromCM(DINA4HEIGHT);
                }
                setDimenLength("paperwidth", paperwidth);
                setDimenLength("paperheight", paperheight);
            } else {
                // use DIN A4
                paperwidth = Unit.createDimenFromCM(DINA4WIDTH);
                paperheight = Unit.createDimenFromCM(DINA4HEIGHT);
            }

            // set start point
            currentX.set(Dimen.ONE_INCH);
            currentY.set(Dimen.ONE_INCH);

            nodes.visit(this, nodes);

            shippedPages++;
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return 1;
    }

    /**
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration cfg) throws ConfigurationException {

        if (cfg != null) {
            String tmp = cfg.getAttribute("encoding");
            if (tmp != null && !tmp.equals("")) {
                encoding = tmp;
            }
            tmp = cfg.getAttribute("debug");
            if (tmp != null) {
                debug = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("showvisible");
            if (tmp != null) {
                showvisible = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("indent");
            if (tmp != null) {
                indent = tmp;
            }
            tmp = cfg.getAttribute("newlines");
            if (tmp != null) {
                newlines = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("trimallwhitespace");
            if (tmp != null) {
                trimallwhitespace = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("usesp");
            if (tmp != null) {
                usesp = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("usebp");
            if (tmp != null) {
                usebp = Boolean.valueOf(tmp).booleanValue();
            }
            tmp = cfg.getAttribute("usemm");
            if (tmp != null) {
                usemm = Boolean.valueOf(tmp).booleanValue();
            }
        }
    }

    // ----------------------------------------------
    // ----------------------------------------------
    // ----------------------------------------------
    // ----------------------------------------------

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(AdjustNode,
     *      java.lang.Object)
     */
    public Object visitAdjust(AdjustNode node, Object value2)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("adjust");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(AfterMathNode,
     *      java.lang.Object)
     */
    public Object visitAfterMath(AfterMathNode node, Object value2)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("aftermath");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(AlignedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitAlignedLeaders(AlignedLeadersNode node, Object value2)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("alignedleaders");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(BeforeMathNode,
     *      java.lang.Object)
     */
    public Object visitBeforeMath(BeforeMathNode node, Object value2)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("beforemath");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Object visitCenteredLeaders(CenteredLeadersNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("centeredleaders");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(CharNode,
     *      java.lang.Object)
     */
    public Object visitChar(CharNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("char");
            UnicodeChar uc = node.getCharacter();
            addNodeAttributes(node);
            writer.writeAttribute("font", node.getTypesettingContext()
                .getFont().getFontName());
            writer.writeAttribute("color", node.getTypesettingContext()
                .getColor().toString());
            writer.writeAttribute("codepoint", String
                .valueOf(uc.getCodePoint()));
            String ucname = uc.getUnicodeName();
            if (ucname != null) {
                writer.writeAttribute("unicode", ucname);
            }
            if (showvisible) {
                String c = ".";
                if (uc.isPrintable()) {
                    c = uc.toString();
                }
                writer.writeAttribute("visiblechar", c);
            }
            currentX.add(node.getWidth());
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(DiscretionaryNode,
     *      java.lang.Object)
     */
    public Object visitDiscretionary(DiscretionaryNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("discretionary");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitExpandedLeaders(ExpandedLeadersNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("expandedleaders");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(GlueNode,
     *      java.lang.Object)
     */
    public Object visitGlue(GlueNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("glue");
            addNodeAttributes(node);
            writer.writeEndElement();
            currentX.add(node.getWidth());
            currentY.add(node.getHeight());
            currentY.add(node.getDepth());
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(HorizontalListNode,
     *      java.lang.Object)
     */
    public Object visitHorizontalList(HorizontalListNode node, Object value)
            throws GeneralException {

        try {
            writer.writeStartElement("horizontallist");
            addNodeAttributes(node);

            Dimen saveX = new Dimen(currentX);
            Dimen saveY = new Dimen(currentY);

            for (Node n : node) {
                n.visit(this, node);
            }
            currentX.set(saveX);
            currentY.set(saveY);
            currentX.add(node.getWidth());

            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(InsertionNode,
     *      java.lang.Object)
     */
    public Object visitInsertion(InsertionNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("insertion");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(KernNode,
     *      java.lang.Object)
     */
    public Object visitKern(KernNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("kern");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(LigatureNode,
     *      java.lang.Object)
     */
    public Object visitLigature(LigatureNode node, Object value)
            throws GeneralException {

        try {
            writer.writeStartElement("ligature");
            addNodeAttributes(node);

            Node first = node.getLeft();
            Node second = node.getRight();
            if (first != null) {
                node.visit(this, first);
            }
            if (second != null) {
                node.visit(this, second);
            }

            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(MarkNode,
     *      java.lang.Object)
     */
    public Object visitMark(MarkNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("mark");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(PenaltyNode,
     *      java.lang.Object)
     */
    public Object visitPenalty(PenaltyNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("penalty");
            addNodeAttributes(node);
            writer.writeAttribute("penalty", String.valueOf(node.getPenalty()));
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(RuleNode,
     *      java.lang.Object)
     */
    public Object visitRule(RuleNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("rule");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(SpaceNode,
     *      java.lang.Object)
     */
    public Object visitSpace(SpaceNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("space");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(VerticalListNode,
     *      java.lang.Object)
     */
    public Object visitVerticalList(VerticalListNode node, Object value)
            throws GeneralException {

        try {
            writer.writeStartElement("verticallist");
            addNodeAttributes(node);

            Dimen saveX = new Dimen(currentX);
            Dimen saveY = new Dimen(currentY);

            for (Node n : node) {
                n.visit(this, node);
            }
            currentX.set(saveX);
            currentY.set(saveY);
            currentY.add(node.getDepth());
            currentY.add(node.getHeight());

            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
     *      org.extex.typesetter.type.node.VirtualCharNode, java.lang.Object)
     */
    public Object visitVirtualChar(VirtualCharNode node, Object value)
            throws GeneralException {

        try {
            writer.writeStartElement("virtualchar");
            addNodeAttributes(node);

            Dimen saveX = new Dimen(currentX);
            Dimen saveY = new Dimen(currentY);

            for (Node n : node) {
                n.visit(this, node);
            }
            currentX.set(saveX);
            currentY.set(saveY);
            currentX.add(node.getWidth());

            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    /**
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(WhatsItNode,
     *      java.lang.Object)
     */
    public Object visitWhatsIt(WhatsItNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("whatsit");
            addNodeAttributes(node);
            writer.writeEndElement();
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }
}