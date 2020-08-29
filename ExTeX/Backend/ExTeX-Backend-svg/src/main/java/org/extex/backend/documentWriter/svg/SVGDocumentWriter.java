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

package org.extex.backend.documentWriter.svg;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.exception.DocumentWriterIOException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configuration;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.page.Page;
import org.extex.util.Unit;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This is a SVG implementation of a document writer.
 *
 * TODO incomplete !!!
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4704 $
 */
public class SVGDocumentWriter
        implements
            DocumentWriter,
            MultipleDocumentStream,
            NodeVisitor<Object, Object> {

    /**
     * DIN-A4 height.
     */
    private static final double DINA4HEIGHT = 29.7d;

    /**
     * DIN-A4 width.
     */
    private static final double DINA4WIDTH = 21.0d;

    /**
     * current x position.
     */
    private final Dimen currentX = new Dimen();

    /**
     * current y position.
     */
    private final Dimen currentY = new Dimen();

    /**
     * debug.
     */
    private boolean debug = true;

    /**
     * The option for the document writer.
     */
    private final DocumentWriterOptions docoptions;

    /**
     * The Encoding.
     */
    private String encoding = "ISO-8859-1";

    /**
     * The field <tt>shippedPages</tt>.
     */
    private int shippedPages = 0;

    /**
     * The XML writer.
     */
    private XMLStreamWriter writer;

    /**
     * Creates a new object.
     *
     * @param cfg the configuration
     * @param options the options
     */
    public SVGDocumentWriter(Configuration cfg, DocumentWriterOptions options) {

        docoptions = options;
        FORMAT.setGroupingUsed(false);
        FORMAT.setMaximumFractionDigits(2);

        if (cfg != null) {
            String tmp = cfg.getAttribute("encoding");
            if (tmp != null && !tmp.equals("")) {
                encoding = tmp;
            }
            tmp = cfg.getAttribute("debug");
            if (tmp != null) {
                debug = Boolean.parseBoolean( tmp );
            }
        }
    }

    public void close()  {
    }

    public String getExtension() {

        return "svg";
    }

    /**
     * Set the Attribute for an element with sp, bp, mm.
     *
     * @param name the attribute-name
     * @param dimen the dimen
     * @throws IOException if an IO-error occurred.
     */
    private void setDimenLength(String name, FixedDimen dimen)
            throws IOException {

        FixedDimen d = dimen;
        if (dimen == null) {
            d = Dimen.ZERO_PT;
        }
        writer.writeAttribute(name, FORMAT.format(Unit.getDimenAsMM(d)) + "mm");
    }

    // /**
    // * @see
    // org.extex.backend.documentWriter.DocumentWriter#setOutputStream(java.io.OutputStream)
    // */
    // public void setOutputStream(OutputStream outStream) {

    // out = outStream;
    // }

    /**
     * the writer factory.
     */
    private OutputStreamFactory writerFactory;

    public void setOutputStreamFactory(OutputStreamFactory writerfactory) {

        writerFactory = writerfactory;
    }

    /**
     * The map for the parameters.
     */
    private final Map<String, String> param = new HashMap<>();

    public void setParameter(String name, String value) {

        param.put(name, value);
    }

    public int shipout(Page page)
            throws
        GeneralException {

        try {
            NodeList nodes = page.getNodes();

            writer =
                    new XMLStreamWriter(writerFactory.getOutputStream(null,
                        getExtension()), encoding);
            if (debug) {
                writer.setBeauty(true);
            }
            writer.setDocType("svg", "//W3C//DTD SVG 1.1//EN",
                "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd");
            writer.writeStartDocument();
            if (debug) {
                printParameterComment();
            }
            writer.writeStartElement("svg");
            writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
            writer.writeAttribute("version", "1.1");
            setPaperHW();

            // set start point
            currentX.set(Dimen.ONE_INCH);
            currentY.set(Dimen.ONE_INCH);

            nodes.visit(this, nodes);

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();

            shippedPages++;
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return shippedPages;
    }

    /**
     * Print the parameter as comment.
     *
     * @throws IOException if an error occurs.
     */
    private void printParameterComment() throws IOException {

        final StringBuilder buf = new StringBuilder();
        buf.append("\n");
        for( final String name : param.keySet() ) {
            buf.append( name );
            buf.append( "=" );
            buf.append( param.get( name ) );
            buf.append( "\n" );
        }
        writer.writeComment(buf.toString());
    }

    /**
     * The decimal format.
     */
    private static final NumberFormat FORMAT =
            NumberFormat.getInstance(Locale.US);

    /**
     * Set the paper height and width.
     *
     * @throws IOException if an IO-error occurred.
     */
    private void setPaperHW() throws IOException {

        // TeX primitives should set the papersize in any way:
        // o \paperwidth / \paperheight,
        // o \pdfpagewidth / \pdfpageheight <-- pdfTeX
        // o \mediawidth / \mediaheight <-- VTeX

        final String page = param.get("Paper");
        FixedDimen paperwidth;
        FixedDimen paperheight;

        if (page != null) {
          // TODO: need dimensions for page.equalsIgnoreCase("A4") == false
          paperwidth = Unit.createDimenFromCM( DINA4WIDTH);
          paperheight = Unit.createDimenFromCM( DINA4HEIGHT);
        } else if (docoptions != null) {
            paperwidth = docoptions.getDimenOption( "paperwidth");
            paperheight = docoptions.getDimenOption( "paperheight");
            if ( paperheight.getValue() == 0 || paperwidth.getValue() == 0) {
                // use DIN A4
                paperwidth = Unit.createDimenFromCM( DINA4WIDTH);
                paperheight = Unit.createDimenFromCM( DINA4HEIGHT);
            }
        } else {
            // use DIN A4
            paperwidth = Unit.createDimenFromCM( DINA4WIDTH);
            paperheight = Unit.createDimenFromCM( DINA4HEIGHT);
        }
        setDimenLength( "width", paperwidth );
        setDimenLength( "height", paperheight );
    }

    public Object visitAdjust(AdjustNode value, Object value2) {

        // Element element = new Element("adjust");
        // AdjustNode node = (AdjustNode) value;
        return null;
    }

    public Object visitAfterMath(AfterMathNode value, Object value2) {

        // Element element = new Element("aftermath");
        // AfterMathNode node = (AfterMathNode) value;
        return null;
    }

    public Object visitAlignedLeaders(AlignedLeadersNode value, Object value2) {

        // Element element = new Element("alignedleaders");
        // AlignedLeadersNode node = (AlignedLeadersNode) value;
        return null;
    }

    public Object visitBeforeMath(BeforeMathNode node, Object value2) {

        // Element element = new Element("beforemath");
        // BeforeMathNode node = (BeforeMathNode) value;
        return null;
    }

    public Object visitCenteredLeaders(CenteredLeadersNode node, Object value) {

        // Element element = new Element("centeredleaders");
        // CenteredLeadersNode node = (CenteredLeadersNode) value;
        return null;
    }

    public Object visitChar(CharNode node, Object value)
            throws DocumentWriterException {

        try {
            UnicodeChar uc = node.getCharacter();
            Font font = node.getTypesettingContext().getFont();

            // ------- text --------------
            writer.writeStartElement("text");
            setDimenLength("x", currentX);
            Dimen y = new Dimen(currentY);
            setDimenLength("y", y);
            writer.writeAttribute("font-family", font.getFontName() + ",serif");
            writer.writeAttribute("font-size", String.valueOf(Unit
                .getDimenAsPT(font.getEm())));
            writer.writeCharacters(uc.toString());
            writer.writeEndElement();

            // ---------------------------------
            writer.writeStartElement("rect");
            setDimenLength("x", currentX);
            y.subtract(node.getHeight());
            setDimenLength("y", y);
            setDimenLength("width", node.getWidth());
            Dimen h = new Dimen(node.getHeight());
            h.add(node.getDepth());
            setDimenLength("height", h);
            writer.writeAttribute("fill", "none");
            writer.writeAttribute("stroke", "blue");
            writer.writeAttribute("strike-width", "1pt");
            writer.writeEndElement();

            // -----------------------------------------------

            currentX.add(node.getWidth());
        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    public Object visitDiscretionary(DiscretionaryNode node, Object value) {

        // Element element = new Element("discretionary");
        // DiscretionaryNode node = (DiscretionaryNode) value;
        return null;
    }

    public Object visitExpandedLeaders(ExpandedLeadersNode node, Object value) {

        // Element element = new Element("expandedleaders");
        // ExpandedLeadersNode node = (ExpandedLeadersNode) value;
        return null;
    }

    public Object visitGlue(GlueNode node, Object value) {

        // Element element = new Element("glue");
        // GlueNode node = (GlueNode) value;
        // currentX.add(node.getWidth());
        // currentY.add(node.getHeight());
        // currentY.add(node.getDepth());
        return null;
    }

    public Object visitHorizontalList(HorizontalListNode node, Object value)
            throws
        GeneralException {

        try {
            writer.writeStartElement("rect");

            setDimenLength("x", currentX);
            setDimenLength("y", currentY);
            setDimenLength("width", node.getWidth());
            Dimen rH = new Dimen(node.getHeight());
            rH.add(node.getDepth());
            setDimenLength("height", rH);
            writer.writeAttribute("fill", "none");
            writer.writeAttribute("stroke", "red");
            writer.writeAttribute("strike-width", "1pt");

            writer.writeEndElement();

            // ------------------------------------------
            Dimen saveX = new Dimen(currentX);
            Dimen saveY = new Dimen(currentY);

            // set x to baseline
            currentY.add(node.getHeight());

            // baseline
            if (node.getDepth().getValue() != 0) {

                writer.writeStartElement("line");
                setDimenLength("x1", currentX);
                setDimenLength("y1", currentY);
                Dimen x2 = new Dimen(currentX);
                x2.add(node.getWidth());
                setDimenLength("x2", x2);
                setDimenLength("y2", currentY);
                writer.writeAttribute("stroke", "red");
                writer.writeAttribute("strike-width", "1pt");

                writer.writeEndElement();
            }

            for (Node n : node) {
                n.visit(this, node);
            }
            currentX.set(saveX);
            currentY.set(saveY);
            currentY.add(node.getHeight());
            currentY.add(node.getDepth());

        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    public Object visitInsertion(InsertionNode node, Object value) {

        // Element element = new Element("insertion");
        // InsertionNode node = (InsertionNode) value;
        return null;
    }

    public Object visitKern(KernNode node, Object value) {

        // Element element = new Element("kern");
        // KernNode node = (KernNode) value;
        return null;
    }

    public Object visitLigature(LigatureNode node, Object value) {

        // Element element = new Element("ligature");
        // LigatureNode node = (LigatureNode) value;
        // Node first = node.getFirst();
        // Node second = node.getSecond();
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
        return null;
    }

    public Object visitMark(MarkNode node, Object value) {

        // Element element = new Element("mark");
        // MarkNode node = (MarkNode) value;
        return null;
    }

    public Object visitPenalty(PenaltyNode node, Object value) {

        // Element element = new Element("penalty");
        // PenaltyNode node = (PenaltyNode) value;
        // element.setAttribute("penalty", String.valueOf(node.getPenalty()));
        return null;
    }

    public Object visitRule(RuleNode node, Object value) {

        // Element element = new Element("rule");
        // RuleNode node = (RuleNode) value;
        return null;
    }

    public Object visitSpace(SpaceNode node, Object value)
            throws DocumentWriterException {

        try {
            writer.writeStartElement("rect");
            setDimenLength("x", currentX);
            Dimen y = new Dimen(currentY);
            y.subtract(new Dimen(2 * Dimen.ONE));
            setDimenLength("y", y);
            setDimenLength("width", node.getWidth());
            writer.writeAttribute("height", "2pt");
            writer.writeAttribute("fill", "green");
            writer.writeEndElement();

            // ------------------------------------------
            currentX.add(node.getWidth());

        } catch (IOException e) {
            throw new DocumentWriterIOException(e);
        }
        return null;
    }

    public Object visitVerticalList(VerticalListNode node, Object value)
            throws
        GeneralException {

        try {
            writer.writeStartElement("rect");
            setDimenLength("x", currentX);
            setDimenLength("y", currentY);
            setDimenLength("width", node.getWidth());
            Dimen rH = new Dimen(node.getHeight());
            rH.add(node.getDepth());
            setDimenLength("height", rH);
            writer.writeAttribute("fill", "none");
            writer.writeAttribute("stroke", "yellow");
            writer.writeAttribute("strike-width", "2pt");
            writer.writeEndElement();

            // ------------------------------------------
            Dimen saveX = new Dimen(currentX);
            Dimen saveY = new Dimen(currentY);

            for (Node n : node) {
                n.visit(this, node);
            }
            currentX.set(saveX);
            currentY.set(saveY);
            currentY.add(node.getDepth());
            currentY.add(node.getHeight());

        } catch (IOException e) {
            throw new DocumentWriterIOException(e.getMessage());
        }
        return null;
    }

    public Object visitVirtualChar(VirtualCharNode node, Object value) {

        // TODO visitVirtualChar unimplemented
        return null;
    }

    public Object visitWhatsIt(WhatsItNode nde, Object value) {

        // Element element = new Element("whatsit");
        // WhatsItNode node = (WhatsItNode) value;
        return null;
    }
}
