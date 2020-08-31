/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.dvix;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.color.Color;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.color.model.CmykColor;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.RgbColor;
import org.extex.core.count.FixedCount;
import org.extex.core.exception.GeneralException;
import org.extex.dviware.Dvi;
import org.extex.dviware.type.DviBop;
import org.extex.dviware.type.DviCode;
import org.extex.dviware.type.DviDown;
import org.extex.dviware.type.DviEop;
import org.extex.dviware.type.DviFnt;
import org.extex.dviware.type.DviPostamble;
import org.extex.dviware.type.DviPreamble;
import org.extex.dviware.type.DviPutChar;
import org.extex.dviware.type.DviRight;
import org.extex.dviware.type.DviSetChar;
import org.extex.dviware.type.DviSetRule;
import org.extex.dviware.type.DviXxx;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.tc.font.Font;
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
import org.extex.typesetter.type.node.SpecialNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.WhatsItNode;
import org.extex.typesetter.type.page.Page;

/**
 * This class provides a base implementation of a DVI document writer.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class DviDocumentWriter
        implements
            DocumentWriter,
            SingleDocumentStream,
            ColorAware,
            Configurable {

    /**
     * The constant {@code MAX_4_BYTE} contains the maximal value of a signed
     * 4-byte value.
     */
    private static final int MAX_4_BYTE = 0x7fffffff;

    /**
     * The field {@code MINUTES_PER_OUR} contains the number of minutes per
     * hour.
     */
    private static final int MINUTES_PER_OUR = 60;

    /**
     * The field {@code bopPointer} contains the pointer to the last BOP.
     */
    private int bopPointer = -1;

    /**
     * The field {@code converter} contains the color converter to use.
     */
    private ColorConverter colorConverter = null;

    /**
     * The field {@code colorSpecials} contains the indicator whether or not
     * to include color specials.
     */
    private boolean colorSpecials = false;

    /**
     * The field {@code dviH} contains the h value of the DVI interpreter.
     */
    private int dviH;

    /**
     * The field {@code dviStack} contains the stack of the DVI interpreter.
     */
    private final Stack<int[]> dviStack = new Stack<int[]>();

    /**
     * The field {@code dviV} contains the v value of the DVI interpreter.
     */
    private int dviV;

    /**
     * The field {@code dviW} contains the w value of the DVI interpreter.
     */
    private int dviW;

    /**
     * The field {@code dviX} contains the x value of the DVI interpreter.
     */
    private int dviX;

    /**
     * The field {@code dviY} contains the y value of the DVI interpreter.
     */
    private int dviY;

    /**
     * The field {@code dviZ} contains the z value of the DVI interpreter.
     */
    private int dviZ;

    /**
     * The field {@code extension} contains the extension.
     */
    private String extension = "dvi";

    /**
     * The field {@code fontIndex} contains the font number currently active.
     */
    private int fontIndex;

    /**
     * The field {@code notInitialized} contains the indicator that the
     * preamble still needs to be written.
     */
    private boolean notInitialized = true;

    /**
     * The field {@code options} contains the options to use.
     */
    private final DocumentWriterOptions options;

    /**
     * The field {@code pointer} contains the index of the next byte to be
     * written.
     */
    private int pointer = 0;

    /**
     * The field {@code postamble} contains the postamble carrying the font
     * list.
     */
    private DviPostamble postamble;

    /**
     * The field {@code stacksize} contains the maximum depth of the stack
     * needed to process all push and pop instructions.
     */
    private int stacksize = 1;

    /**
     * The field {@code stream} contains the target.
     */
    private OutputStream stream;

    /**
     * The field {@code textColor} contains the current text color.
     */
    private Color textColor;

    /**
     * The field {@code visitor} contains the visitor carrying the methods
     * for translating nodes to DVI instructions.
     * <p>
     * Each node visited can assume that the reference point is positioned
     * properly. After the processing the current point should be on the
     * reference point again. The caller is responsible for modifying the
     * reference point.
     * </p>
     * <p>
     * If the return value is {@code null} then the processing has been
     * performed successfully. Otherwise the node should be ignored.
     * </p>
     */
    private final NodeVisitor<Boolean, List<DviCode>> visitor =
            new NodeVisitor<Boolean, List<DviCode>>() {

                /**
                 * The field {@code horizontal} contains the indicator that
                 * the processing is in horizontal mode. Otherwise it is in
                 * vertical mode.
                 */
                private boolean horizontal = true;

                /**
                 * Move the reference point to vertically. This means move it
                 * downwards or upwards if the argument is negative.
                 *
                 * @param dist the distance to move down
                 * @param list the list with DVI instructions.
                 */
                private void down(List<DviCode> list, long dist) {

                    if (dist != 0) {

                        dviV += dist;

                        for (int i = list.size() - 1; i >= 0; i--) {
                            DviCode n = list.get(i);
                            if (n instanceof DviDown) {
                                ((DviDown) n).add((int) dist);
                                return;
                            } else if (!(n instanceof DviRight)) {
                                break;
                            }
                        }

                        list.add(new DviDown((int) dist));
                    }
                }

                /**
                 * Pop the state from the DVI stack.
                 *
                 * @param list the list with DVI instructions.
                 */
                @SuppressWarnings("unused")
                protected void pop(List<DviCode> list) {

                    int size = list.size();
                    while (size > 0) {
                        DviCode code = list.get(size - 1);
                        if (code instanceof DviRight || code instanceof DviDown) {
                            list.remove(--size);
                        } else {
                            break;
                        }
                    }

                    list.add(DviCode.POP);
                    int[] frame = dviStack.pop();
                    dviW = frame[0];
                    dviX = frame[1];
                    dviY = frame[2];
                    dviZ = frame[3];
                }

                /**
                 * Push the state to the DVI stack.
                 *
                 * @param list the list with DVI instructions.
                 */
                @SuppressWarnings("unused")
                protected void push(List<DviCode> list) {

                    list.add(DviCode.PUSH);
                    dviStack.push(new int[]{dviW, dviX, dviY, dviZ});
                }

                /**
                 * Move the reference point to horizontally. This means move it
                 * rightwards or leftwards if the argument is negative.
                 *
                 * @param dist the distance to move right
                 * @param list the list with DVI instructions.
                 */
                private void right(List<DviCode> list, long dist) {

                    if (dist != 0) {
                        dviH += dist;

                        for (int i = list.size() - 1; i >= 0; i--) {
                            DviCode n = list.get(i);
                            if (n instanceof DviRight) {
                                ((DviRight) n).add((int) dist);
                                return;
                            } else if (!(n instanceof DviDown)) {
                                break;
                            }
                        }

                        list.add(new DviRight((int) dist));
                    }
                }

                /**
                 * Insert a color switching special if the current color is not
                 * set to the expected value and remember the current color.
                 *
                 * @param dviCode list of DVI instructions to add the special to
                 * @param color the new color
                 */
                private void switchColors(List<DviCode> dviCode, Color color) {

                    if (!color.equals(textColor)) {
                        textColor = color;
                        String cc = color(textColor);
                        if (cc != null) {
                            dviCode.add(new DviXxx("color " + cc));
                        }
                    }
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
                 *      org.extex.typesetter.type.node.AdjustNode,
                 *      java.lang.Object)
                 */
                public Boolean visitAdjust(AdjustNode node, List<DviCode> value)
                        throws GeneralException {

                    // silently ignored
                    return Boolean.TRUE;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
                 *      org.extex.typesetter.type.node.AfterMathNode,
                 *      java.lang.Object)
                 */
                public Boolean visitAfterMath(AfterMathNode node,
                        List<DviCode> value) throws GeneralException {

                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
                 *      org.extex.typesetter.type.node.AlignedLeadersNode,
                 *      java.lang.Object)
                 */
                public Boolean visitAlignedLeaders(AlignedLeadersNode node,
                        List<DviCode> value) throws GeneralException {

                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
                 *      org.extex.typesetter.type.node.BeforeMathNode,
                 *      java.lang.Object)
                 */
                public Boolean visitBeforeMath(BeforeMathNode node,
                        List<DviCode> value) throws GeneralException {

                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
                 *      org.extex.typesetter.type.node.CenteredLeadersNode,
                 *      java.lang.Object)
                 */
                public Boolean visitCenteredLeaders(CenteredLeadersNode node,
                        List<DviCode> value) throws GeneralException {

                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitChar(
                 *      org.extex.typesetter.type.node.CharNode,
                 *      java.lang.Object)
                 */
                public Boolean visitChar(CharNode node, List<DviCode> dviCode)
                        throws GeneralException {

                    Font font = node.getTypesettingContext().getFont();
                    int f = postamble.mapFont(font, dviCode);
                    if (f != fontIndex) {
                        dviCode.add(new DviFnt(f));
                        fontIndex = f;
                    }
                    if (colorSpecials) {
                        switchColors(dviCode, node.getTypesettingContext()
                            .getColor());
                    }

                    if (horizontal) {
                        dviH += node.getWidth().getValue();
                        dviCode.add(new DviSetChar(node.getCharacter()
                            .getCodePoint()));
                        return Boolean.TRUE; // do not move any more
                    }

                    dviCode.add(new DviPutChar(node.getCharacter()
                        .getCodePoint()));
                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
                 *      org.extex.typesetter.type.node.DiscretionaryNode,
                 *      java.lang.Object)
                 */
                public Boolean visitDiscretionary(DiscretionaryNode node,
                        List<DviCode> value) throws GeneralException {

                    NodeList n = node.getNoBreak();
                    if (n != null) {
                        return (Boolean) n.visit(this, value);
                    }
                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
                 *      org.extex.typesetter.type.node.ExpandedLeadersNode,
                 *      java.lang.Object)
                 */
                public Boolean visitExpandedLeaders(ExpandedLeadersNode node,
                        List<DviCode> value) throws GeneralException {

                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
                 *      org.extex.typesetter.type.node.GlueNode,
                 *      java.lang.Object)
                 */
                public Boolean visitGlue(GlueNode node, List<DviCode> value)
                        throws GeneralException {

                    return null;
                }

                /**
                 * @see "TTP [619]"
                 * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
                 *      org.extex.typesetter.type.node.HorizontalListNode,
                 *      java.lang.Object)
                 */
                public Boolean visitHorizontalList(HorizontalListNode node,
                        List<DviCode> list) throws GeneralException {

                    boolean save = horizontal;
                    horizontal = true;
                    down(list, node.getShift().getValue());
                    right(list, node.getMove().getValue());
                    int v0 = dviV;

                    for (Node n : node) {
                        if (n.visit(this, list) == null) {
                            right(list, n.getWidth().getValue());
                        }
                        down(list, dviV - v0);
                    }
                    horizontal = save;
                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
                 *      org.extex.typesetter.type.node.InsertionNode,
                 *      java.lang.Object)
                 */
                public Boolean visitInsertion(InsertionNode node,
                        List<DviCode> value) throws GeneralException {

                    // silently ignored
                    return Boolean.TRUE;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitKern(
                 *      org.extex.typesetter.type.node.KernNode,
                 *      java.lang.Object)
                 */
                public Boolean visitKern(KernNode node, List<DviCode> value)
                        throws GeneralException {

                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
                 *      org.extex.typesetter.type.node.LigatureNode,
                 *      java.lang.Object)
                 */
                public Boolean visitLigature(LigatureNode node,
                        List<DviCode> value) throws GeneralException {

                    return visitChar(node, value);
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitMark(
                 *      org.extex.typesetter.type.node.MarkNode,
                 *      java.lang.Object)
                 */
                public Boolean visitMark(MarkNode node, List<DviCode> value)
                        throws GeneralException {

                    // silently ignored
                    return Boolean.TRUE;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
                 *      org.extex.typesetter.type.node.PenaltyNode,
                 *      java.lang.Object)
                 */
                public Boolean visitPenalty(PenaltyNode node,
                        List<DviCode> value) throws GeneralException {

                    // silently ignored
                    return Boolean.TRUE;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitRule(
                 *      org.extex.typesetter.type.node.RuleNode,
                 *      java.lang.Object)
                 */
                public Boolean visitRule(RuleNode node, List<DviCode> dviCode)
                        throws GeneralException {

                    int width = (int) node.getWidth().getValue();
                    int height = (int) node.getHeight().getValue();

                    if (colorSpecials) {
                        switchColors(dviCode, node.getTypesettingContext()
                            .getColor());
                    }

                    dviCode.add(new DviSetRule(height, width));
                    dviH += width;
                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
                 *      org.extex.typesetter.type.node.SpaceNode,
                 *      java.lang.Object)
                 */
                public Boolean visitSpace(SpaceNode node, List<DviCode> value)
                        throws GeneralException {

                    return null;
                }

                /**
                 * @see "TTP [618]"
                 * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
                 *      org.extex.typesetter.type.node.VerticalListNode,
                 *      java.lang.Object)
                 */
                public Boolean visitVerticalList(VerticalListNode node,
                        List<DviCode> list) throws GeneralException {

                    boolean save = horizontal;
                    horizontal = false;
                    int v0 = dviV;
                    down(list, -node.getHeight().getValue());
                    down(list, node.getShift().getValue());
                    right(list, node.getMove().getValue());
                    int h0 = dviH;

                    for (Node n : node) {
                        down(list, n.getHeight().getValue());
                        if (n.visit(this, list) == null) {
                            down(list, n.getDepth().getValue());
                        }
                        right(list, h0 - dviH);
                    }

                    down(list, v0 - dviV); // ???
                    horizontal = save;
                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
                 *      org.extex.typesetter.type.node.VirtualCharNode,
                 *      java.lang.Object)
                 */
                public Boolean visitVirtualChar(VirtualCharNode node,
                        List<DviCode> value) throws GeneralException {

                    return visitChar(node, value);
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
                 *      org.extex.typesetter.type.node.WhatsItNode,
                 *      java.lang.Object)
                 */
                public Boolean visitWhatsIt(WhatsItNode node, List<DviCode> list)
                        throws GeneralException {

                    if (node instanceof SpecialNode) {
                        String text = ((SpecialNode) node).getText();
                        int length = text.length();
                        byte[] content = new byte[length];
                        for (int i = 0; i < length; i++) {
                            content[i] = (byte) text.charAt(i);
                        }
                        list.add(new DviXxx(content));
                        return null;
                    }
                    return null;
                }

            };

    /**
     * Creates a new object.
     *
     * @param options the document writer options
     */
    public DviDocumentWriter(DocumentWriterOptions options) {

        this.options = options;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    public void close() throws GeneralException, IOException {

        if (!notInitialized) {

            postamble.setBop(bopPointer);
            postamble.setOffset(pointer);
            pointer += postamble.write(stream);

            while (pointer % 4 != 0) {
                stream.write(Dvi.PADDING_BYTE);
                pointer++;
            }
        }

        stream.close();
    }

    /**
     * Get the color representation for the specials.
     *
     * @param color the color
     *
     * @return the string representation
     */
    private String color(Color color) {

        if (color instanceof RgbColor) {
            RgbColor rgb = (RgbColor) color;
            return "rgb " + ((double) rgb.getRed()) / Color.MAX_VALUE + " "
                    + ((double) rgb.getGreen()) / Color.MAX_VALUE + " "
                    + ((double) rgb.getBlue()) / Color.MAX_VALUE;
        } else if (color instanceof CmykColor) {
            CmykColor cmyk = (CmykColor) color;
            return "cmyk " + ((double) cmyk.getCyan()) / Color.MAX_VALUE + " "
                    + ((double) cmyk.getMagenta()) / Color.MAX_VALUE + " "
                    + ((double) cmyk.getYellow()) / Color.MAX_VALUE + " "
                    + ((double) cmyk.getBlack()) / Color.MAX_VALUE;
        } else if (color instanceof GrayscaleColor) {
            GrayscaleColor gray = (GrayscaleColor) color;
            return "gray " + ((double) gray.getGray()) / Color.MAX_VALUE;
        }
        if (colorConverter != null) {
            Color c = colorConverter.toRgb(color);
            if (c != null) {
                return color(c);
            }
            c = colorConverter.toCmyk(color);
            if (c != null) {
                return color(c);
            }
        }
        return null;
    }

    /**
     * Configure an object according to a given Configuration.
     *
     * @param config the configuration object to consider
     *
     * @throws ConfigurationException in case that something went wrong
     *
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        String col = config.getAttribute("color");
        if (col != null) {
            colorSpecials = Boolean.valueOf(col).booleanValue();
        }
        String ext = config.getAttribute("extension");
        if (ext != null) {
            extension = ext;
        }
    }

    /**
     * Getter for the extension associated with this kind of output. For
     * instance {@code pdf} is the expected value for PDF files and
     * {@code dvi} is the expected value for DVI files.
     *
     * @return the appropriate extension for file names
     *
     * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
     */
    public String getExtension() {

        return extension;
    }

    /**
     * Optimize the description of a single page.
     *
     * @param list the list of codes for the page
     */
    protected void optimize(List<DviXxx> list) {

        // not yet
    }

    /**
     * Setter for the color converter.
     *
     * @param converter the color converter
     *
     * @see org.extex.color.ColorAware#setColorConverter(
     *      org.extex.color.ColorConverter)
     */
    public void setColorConverter(ColorConverter converter) {

        this.colorConverter = converter;
    }

    /**
     * Setter for the output stream.
     *
     * @param writer the output stream
     *
     * @see org.extex.backend.documentWriter.SingleDocumentStream#setOutputStream(
     *      java.io.OutputStream)
     */
    public void setOutputStream(OutputStream writer) {

        this.stream = writer;
    }

    /**
     * Setter for a named parameter. Parameters are a general mechanism to
     * influence the behavior of the document writer. Any parameter not known by
     * the document writer has to be ignored.
     *
     * @param name the name of the parameter
     * @param value the value of the parameter
     *
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {

        if ("color".equals(name)) {
            colorSpecials = Boolean.valueOf(value).booleanValue();
        }
    }

    /**
     * This is the entry point for the document writer. Here it receives a
     * complete node list to be sent to the output writer. It can be assumed
     * that all values for width, height, and depth of the node lists are
     * properly filled. Thus all information should be present to place the ink
     * on the paper.
     *
     * @param page the page to send
     *
     * @return returns the number of pages shipped
     *
     * @throws GeneralException in case of a general exception<br>
     *         especially<br>
     *         DocumentWriterException in case of an error
     * @throws IOException in case of an IO exception
     *
     * @see org.extex.backend.documentWriter.DocumentWriter#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(Page page) throws GeneralException, IOException {

        if (page == null) {
            return 0;
        }

        if (notInitialized) {
            writePreamble();
            notInitialized = false;
        }
        int[] pageno = new int[10];
        FixedCount[] pn = page.getPageNo();
        for (int i = 0; i < pn.length; i++) {
            long val = pn[i].getValue();
            pageno[i] = (val > MAX_4_BYTE ? MAX_4_BYTE : (int) val);
        }
        int p = pointer;
        pointer += new DviBop(pageno, bopPointer).write(stream);
        bopPointer = p;
        dviH = 0;
        dviV = 0;
        dviW = 0;
        dviX = 0;
        dviY = 0;
        dviZ = 0;
        fontIndex = -1;
        List<DviXxx> dviCode = new ArrayList<DviXxx>();
        NodeList nodes = page.getNodes();

        if (colorSpecials) {
            String col = color(page.getColor());
            if (col != null) {
                dviCode.add(new DviXxx("background " + col));
            }
        }

        nodes.visit(visitor, dviCode);

        optimize(dviCode);

        int stackDepth = 0;

        for (int i = 0; i < dviCode.size(); i++) {
            DviCode code = dviCode.get(i);
            pointer += code.write(stream);
            if (code == DviCode.PUSH) {
                stackDepth++;
                if (stackDepth > stacksize) {
                    stacksize = stackDepth;
                }
            } else if (code == DviCode.POP) {
                stackDepth--;
            }
        }

        pointer += new DviEop().write(stream);
        postamble.recognizePage(nodes.getHeight(), nodes.getDepth(), nodes
            .getWidth(), stacksize);
        return 1;
    }

    /**
     * Ensure that a string has at least two characters length by padding it
     * with 0 if necessary.
     *
     * @param name the name of the count register
     *
     * @return the string representation of the value padded with a leading 0
     */
    private String two(String name) {

        String s = options.getCountOption(name).toString();
        return (s.length() > 1 ? s : "0" + s);
    }

    /**
     * Write the preamble.
     *
     * @throws IOException in case of an error
     *
     * @see "TTP [617]"
     */
    private void writePreamble() throws IOException {

        long time = options.getCountOption("time").getValue();
        String comment =
                " ExTeX output "
                        + two("year")
                        + "."
                        + two("month")
                        + "."
                        + two("day")
                        + ":"
                        + ((time / MINUTES_PER_OUR) * 100 + time
                    % MINUTES_PER_OUR);
        long mag = options.getMagnification();
        if (mag > MAX_4_BYTE) {
            mag = MAX_4_BYTE;
        }
        pointer += new DviPreamble((int) mag, comment).write(stream);

        postamble = new DviPostamble((int) mag);
    }

}
