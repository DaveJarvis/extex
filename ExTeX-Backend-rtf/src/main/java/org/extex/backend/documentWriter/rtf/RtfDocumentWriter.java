/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.rtf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.color.model.RgbColor;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.interpreter.context.Color;
import org.extex.interpreter.type.font.Font;
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
 * This class provides a base implementation of a RTF document writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class RtfDocumentWriter extends RtfDocument
        implements
            DocumentWriter,
            SingleDocumentStream,
            ColorAware,
            Configurable {

    /**
     * The field <tt>color</tt> contains the current text color index.
     */
    private int color = 0;

    /**
     * The field <tt>converter</tt> contains the converter to acquire a color
     * in RGB mode.
     */
    private ColorConverter converter;

    /**
     * The field <tt>fontNo</tt> contains the font number currently active.
     */
    private int fontNo = 0;

    /**
     * The field <tt>fontSize</tt> contains the size of the current font in
     * half points (= 1/7200 in). The RTF default is 12 points which is the
     * value 24.
     */
    private long fontSize = 24;

    /**
     * The field <tt>options</tt> contains the options to use.
     */
    private DocumentWriterOptions options;

    /**
     * The field <tt>posX</tt> contains the x position on the current page.
     */
    private long posX;

    /**
     * The field <tt>posY</tt> contains the y position on the current page.
     */
    private long posY;

    /**
     * The field <tt>stream</tt> contains the target.
     */
    private OutputStream stream;

    /**
     * The field <tt>visitor</tt> contains the visitor carrying the methods
     * for translating nodes to RTF instructions.
     */
    private NodeVisitor visitor = new NodeVisitor() {

        /**
         * The state is a container for state information to be preserved by RTF
         * blocks.
         * 
         * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
         * @version $Revision$
         */
        class State {

            /**
             * The field <tt>color</tt> contains the color index.
             */
            private int color;

            /**
             * The field <tt>font</tt> contains the font index.
             */
            private int font;

            /**
             * The field <tt>fontSize</tt> contains the font size.
             */
            private long fontSize;

            /**
             * Creates a new object.
             * 
             * @param color the color index
             * @param font the font index
             * @param size the font size
             */
            public State(int color, int font, long size) {

                super();
                this.color = color;
                this.font = font;
                this.fontSize = size;
            }

            /**
             * Getter for color.
             * 
             * @return the color
             */
            public int getColor() {

                return this.color;
            }

            /**
             * Getter for font.
             * 
             * @return the font
             */
            public int getFont() {

                return this.font;
            }

            /**
             * Getter for fontSize.
             * 
             * @return the fontSize
             */
            public long getFontSize() {

                return this.fontSize;
            }
        }

        /**
         * The field <tt>horizontal</tt> contains the indicator that the
         * processing is in horizontal mode. Otherwise it is in vertical mode.
         */
        private boolean horizontal = true;

        /**
         * The field <tt>stack</tt> contains the stack for RTF group state
         * information.
         */
        private Stack<State> stack = new Stack<State>();

        /**
         * Emit the instruction to close a RTF group and restore the state from
         * the stack.
         * 
         * @param tag the tag to include
         * 
         * @throws GeneralException in case of an error
         */
        private void closeGroup(String tag) throws GeneralException {

            State state = stack.pop();
            fontNo = state.getFont();
            color = state.getColor();
            fontSize = state.getFontSize();
            try {
                put(tag);
                put("}");
            } catch (IOException e) {
                throw new GeneralException(e);
            }
        }

        /**
         * Emit the instruction to open a RTF group and save the state to the
         * stack.
         * 
         * @throws GeneralException in case of an error
         */
        private void openGroup() throws GeneralException {

            stack.push(new State(color, fontNo, fontSize));
            try {
                put("\n{");
            } catch (IOException e) {
                throw new GeneralException(e);
            }
        }

        /**
         * Write an int valued tag to the target stream.
         * 
         * @param tag the tag to precede the value
         * @param i
         * 
         * @throws IOException in case of an error
         */
        void putInt(String tag, int i) throws IOException {

            put(tag);
            put(Integer.toString(i));
            put((byte) ' ');
        }

        /**
         * Write an long valued tag to the target stream.
         * 
         * @param tag the tag to precede the value
         * @param i
         * 
         * @throws IOException in case of an error
         */
        void putLong(String tag, long i) throws IOException {

            put(tag);
            put(Long.toString(i));
            put((byte) ' ');
        }

        /**
         * Put a unit in twips preceded by a tag to the target stream.
         * 
         * @param tag the tag to precede the value
         * @param sp the value in scaled points
         * 
         * @throws IOException in case of an error
         */
        private void putTwip(String tag, long sp) throws IOException {

            put(tag);
            // 1^16 sp = 1pt
            // 1^16 sp * 7227/100= 72.27pt = 1in = 7200 twip / 20 = 360 twip
            // sp = 360 * 100 / 7227 / 10^16 twip
            put(Long.toString((sp * 125 / 803) >> 9));
            put((byte) ' ');
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
         *      org.extex.typesetter.type.node.AdjustNode, java.lang.Object)
         */
        public Object visitAdjust(AdjustNode node, Object value)
                throws GeneralException {

            // silently ignored
            return Boolean.TRUE;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
         *      org.extex.typesetter.type.node.AfterMathNode, java.lang.Object)
         */
        public Object visitAfterMath(AfterMathNode node, Object value)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
         *      org.extex.typesetter.type.node.AlignedLeadersNode,
         *      java.lang.Object)
         */
        public Object visitAlignedLeaders(AlignedLeadersNode node, Object value)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
         *      org.extex.typesetter.type.node.BeforeMathNode, java.lang.Object)
         */
        public Object visitBeforeMath(BeforeMathNode node, Object value)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
         *      org.extex.typesetter.type.node.CenteredLeadersNode,
         *      java.lang.Object)
         */
        public Object visitCenteredLeaders(CenteredLeadersNode node,
                Object value) throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitChar(
         *      org.extex.typesetter.type.node.CharNode, java.lang.Object)
         */
        public Object visitChar(CharNode node, Object code)
                throws GeneralException {

            try {
                Font font = node.getTypesettingContext().getFont();
                int f = mapFont(font);
                if (f != fontNo) {
                    putInt("\\f", f);
                    fontNo = f;
                }
                long fs = font.getActualSize().getValue() >> 15;
                if (fs != fontSize) {
                    putLong("\\fs", fs);
                    fontSize = fs;
                }
                Color col = node.getTypesettingContext().getColor();
                int cf = mapColor(converter.toRgb(col));
                if (cf != color) {
                    putLong("\\cf", cf);
                    color = cf;
                }

                int c = node.getCharacter().getCodePoint();

                if (c > 32 && c < 0x7f) {
                    put((byte) c);
                } else if (c < 0) {
                    // TODO impossible in RTF?
                } else if (c <= 0xff) {
                    put("\\'");
                    put(Integer.toHexString(c));
                } else if (c <= 0x7fff) {
                    put("\\uc1\\u");
                    put(Integer.toString(c));
                    put((byte) '*');
                } else if (c <= 0xffff) {
                    put("\\uc1\\u");
                    put(Integer.toString(-c));
                    put((byte) '*');
                }
            } catch (IOException e) {
                throw new GeneralException(e);
            }

            if (horizontal) {
                posX += node.getWidth().getValue();
                return Boolean.TRUE;
            }
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
         *      org.extex.typesetter.type.node.DiscretionaryNode,
         *      java.lang.Object)
         */
        public Object visitDiscretionary(DiscretionaryNode node, Object value)
                throws GeneralException {

            NodeList n = node.getNoBreak();
            if (n != null) {
                return n.visit(this, value);
            }
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
         *      org.extex.typesetter.type.node.ExpandedLeadersNode,
         *      java.lang.Object)
         */
        public Object visitExpandedLeaders(ExpandedLeadersNode node,
                Object value) throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
         *      org.extex.typesetter.type.node.GlueNode, java.lang.Object)
         */
        public Object visitGlue(GlueNode node, Object value)
                throws GeneralException {

            return null;
        }

        /**
         * @see "TTP [619]"
         * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
         *      org.extex.typesetter.type.node.HorizontalListNode,
         *      java.lang.Object)
         */
        public Object visitHorizontalList(HorizontalListNode node, Object value)
                throws GeneralException {

            boolean save = horizontal;
            horizontal = true;
            int size = node.size();
            openGroup();
            try {
                put("\\pvmrg\\phmrg");
                putTwip("\\posx", posX);
                putTwip("\\posy", posY);
            } catch (IOException e) {
                throw new GeneralException(e);
            }

            long x = posX;

            for (int i = 0; i < size; i++) {
                Node n = node.get(i);
                if (n.visit(this, value) == null) {
                    posX += node.getWidth().getValue();
                }
            }

            posX = x;
            closeGroup("\\par");
            horizontal = save;
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
         *      org.extex.typesetter.type.node.InsertionNode, java.lang.Object)
         */
        public Object visitInsertion(InsertionNode node, Object value)
                throws GeneralException {

            // silently ignored
            return Boolean.TRUE;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitKern(
         *      org.extex.typesetter.type.node.KernNode, java.lang.Object)
         */
        public Object visitKern(KernNode node, Object value)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
         *      org.extex.typesetter.type.node.LigatureNode, java.lang.Object)
         */
        public Object visitLigature(LigatureNode node, Object value)
                throws GeneralException {

            return visitChar(node, value);
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitMark(
         *      org.extex.typesetter.type.node.MarkNode, java.lang.Object)
         */
        public Object visitMark(MarkNode node, Object value)
                throws GeneralException {

            // silently ignored
            return Boolean.TRUE;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
         *      org.extex.typesetter.type.node.PenaltyNode, java.lang.Object)
         */
        public Object visitPenalty(PenaltyNode node, Object value)
                throws GeneralException {

            // silently ignored
            return Boolean.TRUE;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitRule(
         *      org.extex.typesetter.type.node.RuleNode, java.lang.Object)
         */
        public Object visitRule(RuleNode node, Object code)
                throws GeneralException {

            // TODO gene: correct the rectangle drawing
            try {
                put("{\\*\\do\\dobxpage\\dobypage");
                put("\\dpline");
                putTwip("\\dpptx", 0);
                putTwip("\\dppty", 0);
                putTwip("\\dpptx", posX);
                putTwip("\\dppty", posY);
                putTwip("\\dpx", node.getWidth().getValue());
                putTwip("\\dpy", node.getVerticalSize().getValue());
                putTwip("\\dpxsize", node.getWidth().getValue());
                putTwip("\\dpysize", node.getVerticalSize().getValue());
                putTwip("\\dplinew", 60);

                Color c = node.getTypesettingContext().getColor();
                if (c != null) {
                    RgbColor co = converter.toRgb(c);
                    if (co != null) {
                        putInt("\\dplinecor", co.getRed() >> 8);
                        putInt("\\dplinecog", co.getGreen() >> 8);
                        putInt("\\dplinecob", co.getBlue() >> 8);
                    }
                }
                put("}\n");
            } catch (IOException e) {
                throw new GeneralException(e);
            }

            // {\*\do\dobxpage\dobypage
            // \dpline
            // \dpptx0\dppty0
            // \dpptx2160\dppty1800
            // \dpx1620\dpy5760
            // \dpxsize2160\dpysize1800
            // \dplinew150
            // \dplinecor255\dplinecog0\dplinecob0}
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
         *      org.extex.typesetter.type.node.SpaceNode, java.lang.Object)
         */
        public Object visitSpace(SpaceNode node, Object value)
                throws GeneralException {

            // TODO gene: wrong; just for development

            try {
                put((byte) ' ');
            } catch (IOException e) {
                throw new GeneralException(e);
            }
            return Boolean.TRUE;
            // return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see "TTP [618]"
         * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
         *      org.extex.typesetter.type.node.VerticalListNode,
         *      java.lang.Object)
         */
        public Object visitVerticalList(VerticalListNode node, Object value)
                throws GeneralException {

            Node n;
            boolean save = horizontal;
            horizontal = false;
            // right(list, node.getShift().getValue());
            // down(list, node.getMove().getValue());
            int size = node.size();
            // int h0 = dviH;
            openGroup();
            long x = posX;

            for (int i = 0; i < size; i++) {
                n = node.get(i);
                posY += n.getHeight().getValue();
                // down(list, n.getHeight().getValue());
                if (n.visit(this, value) == null) {
                    posY += n.getDepth().getValue();
                }
                // right(list, h0 - dviH);
            }

            posX = x;
            horizontal = save;
            closeGroup("\\par");
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
         *      org.extex.typesetter.type.node.VirtualCharNode,
         *      java.lang.Object)
         */
        public Object visitVirtualChar(VirtualCharNode node, Object value)
                throws GeneralException {

            return visitChar(node, value);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
         *      org.extex.typesetter.type.node.WhatsItNode, java.lang.Object)
         */
        public Object visitWhatsIt(WhatsItNode node, Object value)
                throws GeneralException {

            if (node instanceof SpecialNode) {
                // List list = (List) value;
                // String text = ((SpecialNode) node).getText();
                // int length = text.length();
                // byte[] content = new byte[length];
                // for (int i = 0; i < length; i++) {
                // content[i] = (byte) text.charAt(i);
                // }
                // list.add(new DviXxx(content));
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
    public RtfDocumentWriter(DocumentWriterOptions options) {

        super();
        this.options = options;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    public void close() throws GeneralException, IOException {

        write(stream);
        stream.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) {

        // not needed
    }

    /**
     * Getter for the extension associated with this kind of output. For
     * instance <tt>pdf</tt> is the expected value for PDF files and
     * <tt>dvi</tt> is the expected value for DVI files.
     * 
     * @return the appropriate extension for file names
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
     */
    public String getExtension() {

        return "rtf";
    }

    /**
     * Setter for the color converter.
     * 
     * @param c the color converter
     * 
     * @see org.extex.color.ColorAware#setColorConverter(
     *      org.extex.color.ColorConverter)
     */
    public void setColorConverter(ColorConverter c) {

        this.converter = c;
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

        if ("Title".equals(name)) {
            setTitle(value);
        } else if ("Author".equals(name)) {
            setAuthor(value);
        } else if ("Creator".equals(name)) {
            setCreator(value);
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

        newPage(page.getColor());
        page.getNodes().visit(visitor, null);

        return 1;
    }

}