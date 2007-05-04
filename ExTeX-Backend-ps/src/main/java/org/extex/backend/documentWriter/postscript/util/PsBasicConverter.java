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
import org.extex.color.Color;
import org.extex.color.ColorAware;
import org.extex.color.ColorConverter;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.RgbColor;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceConsumer;
import org.extex.resource.ResourceFinder;
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
 * This class provides a converter to PostScript code.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PsBasicConverter
        implements
            PsConverter,
            NodeVisitor<Object, StringBuffer>,
            ResourceConsumer,
            ColorAware {

    /**
     * This inner class is used to keep track of characters which are delayed
     * for output.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private class Buffer {

        /**
         * The field <tt>charBuffer</tt> contains the dynamic buffer.
         */
        private StringBuffer buffer = new StringBuffer();

        /**
         * The field <tt>currX</tt> contains the x coordinate for the first
         * character.
         */
        private Dimen currX = new Dimen();

        /**
         * The field <tt>currY</tt> contains the y coordinate for the first
         * character.
         */
        private Dimen currY = new Dimen(Long.MIN_VALUE);

        /**
         * The field <tt>empty</tt> contains the indicator that the buffer is
         * empty.
         */
        private boolean empty = true;

        /**
         * The field <tt>shift</tt> contains the indicator that the first
         * character is not on the same y coordinate as the previous one.
         */
        private boolean shift = false;

        /**
         * Creates a new object.
         */
        public Buffer() {

            super();
        }

        /**
         * Add a single character to the output.
         *
         * @param c the character to add
         * @param xx the x position
         * @param yy the y position
         */
        public void add(UnicodeChar c, Dimen xx, Dimen yy) {

            if (empty) {
                shift = currY.ne(yy);
                empty = false;
                this.currX.set(xx);
                this.currY.set(yy);
            }
            int cp = c.getCodePoint();
            switch (cp) {
                case '\\':
                case '(':
                case ')':
                    buffer.append('\\');
                    break;
                default:
                    if (cp < ' ' || cp >= 127) {
                        buffer.append('\\');
                        buffer.append(Integer.toOctalString(cp));
                        return;
                    }
                    // nothing to do
            }
            buffer.append(c.toString());
        }

        /**
         * Ship the collected characters out.
         *
         * @param out the target string buffer
         */
        public void clear(StringBuffer out) {

            if (empty) {
                return;
            }
            if (shift) {
                out.append("(");
                out.append(buffer);
                out.append(")");
                PsUnit.toPoint(currX, out, false);
                out.append(' ');
                PsUnit.toPoint(currY, out, false);
                out.append(" s\n");
            } else {
                out.append("(");
                out.append(buffer);
                out.append(")");
                PsUnit.toPoint(currX, out, false);
                out.append(" x\n");
            }

            buffer.delete(0, buffer.length());
            empty = true;
        }

        /**
         * Reset the stored vertical position.
         */
        public void reset() {

            currY.set(Long.MIN_VALUE);
        }
    }

    /**
     * The field <tt>buffer</tt> contains the character buffer.
     */
    private Buffer buffer = new Buffer();

    /**
     * The field <tt>cc</tt> contains the color converter.
     */
    private ColorConverter cc = null;

    /**
     * The field <tt>currentColor</tt> contains the current color to keep track
     * of the color changing commands.
     */
    private Color currentColor = null;

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field <tt>fm</tt> contains the font manager.
     */
    private FontManager fm = null;

    /**
     * The field <tt>hm</tt> contains the header manager.
     */
    private HeaderManager hm = null;

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
    public PsBasicConverter() {

        super();
    }

    /**
     * Perform some initializations for each document.
     *
     * @param header the header manager
     *
     * @throws IOException in case of an error while loading
     */
    public void init(HeaderManager header) throws IOException {

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
     *
     * @see org.extex.backend.documentWriter.postscript.util.PsConverter#toPostScript(
     *      org.extex.typesetter.type.page.Page,
     *      org.extex.backend.documentWriter.postscript.util.FontManager,
     *      org.extex.backend.documentWriter.postscript.util.HeaderManager)
     */
    public byte[] toPostScript(Page page, FontManager fontManager,
            HeaderManager headerManager) throws DocumentWriterException {

        x.set(page.getMediaHOffset());
        y.set(page.getMediaHeight());
        y.subtract(page.getMediaVOffset());

        fm = fontManager;
        hm = headerManager;
        buffer.reset();
        StringBuffer out = new StringBuffer("TeXDict begin\n");

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
     * @see org.extex.color.ColorAware#setColorConverter(
     *      org.extex.color.ColorConverter)
     */
    public void setColorConverter(ColorConverter converter) {

        cc = converter;
    }

    /**
     * @see org.extex.resource.ResourceConsumer#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder resourceFinder) {

        this.finder = resourceFinder;
    }

    /**
     * Add some text from a resource to the header section.
     *
     * @param name the name of the resource to add as header
     *
     * @throws GeneralException in case of an error
     */
    private void specialHeader(String name) throws GeneralException {

        try {
            InputStream s = finder.findResource(name, "pro");
            if (s != null) {
                hm.add(s, name);
                s.close();
            } else {
                throw new DocumentWriterIOException(new FileNotFoundException());
            }
        } catch (ConfigurationException e) {
            throw new GeneralException(e);
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    /**
     * Find a PS resource and include its contents into the output stream.
     *
     * @param out the target buffer
     * @param name the name of the resource
     *
     * @throws GeneralException in case of an error
     */
    private void specialPsfile(StringBuffer out, String name)
            throws GeneralException {

        try {
            InputStream s = finder.findResource(name, "ps");
            if (s != null) {
                int c;
                while ((c = s.read()) >= 0) {
                    out.append((char) c);
                }
                s.close();
            } else {
                throw new DocumentWriterIOException(new FileNotFoundException());
            }
        } catch (ConfigurationException e) {
            throw new GeneralException(e);
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    /**
     * Switch to another color.
     *
     * @param color the color to switch to
     * @param out the target buffer
     */
    private void switchColors(Color color, StringBuffer out) {

        out.append(' ');
        if (color instanceof GrayscaleColor) {
            PsUnit.toPoint(new Dimen(((GrayscaleColor) color).getGray()
                    * Dimen.ONE), out, false);
            out.append(" Cg\n");
        } else {
            RgbColor rgb = cc.toRgb(color);
            PsUnit.toPoint(new Dimen(rgb.getRed() * Dimen.ONE), out, false);
            out.append(' ');
            PsUnit.toPoint(new Dimen(rgb.getGreen() * Dimen.ONE), out, false);
            out.append(' ');
            PsUnit.toPoint(new Dimen(rgb.getBlue() * Dimen.ONE), out, false);
            out.append(" Cr\n");
        }
        currentColor = color;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
     *      org.extex.typesetter.type.node.AdjustNode,
     *      java.lang.Object)
     */
    public Object visitAdjust(AdjustNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
     *      org.extex.typesetter.type.node.AfterMathNode,
     *      java.lang.Object)
     */
    public Object visitAfterMath(AfterMathNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
     *      org.extex.typesetter.type.node.AlignedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitAlignedLeaders(AlignedLeadersNode node,
            StringBuffer oOut) throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
     *      org.extex.typesetter.type.node.BeforeMathNode,
     *      java.lang.Object)
     */
    public Object visitBeforeMath(BeforeMathNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
     *      org.extex.typesetter.type.node.CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Object visitCenteredLeaders(CenteredLeadersNode node,
            StringBuffer oOut) throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(
     *      org.extex.typesetter.type.node.CharNode,
     *      java.lang.Object)
     */
    public Object visitChar(CharNode node, StringBuffer out)
            throws GeneralException {

        TypesettingContext tc = node.getTypesettingContext();
        UnicodeChar c = node.getCharacter();
        Font font = tc.getFont();

        String fnt = fm.add(font, c);
        if (fnt != null) {
            buffer.clear(out);
            out.append(fnt);
        }

        Color color = tc.getColor();

        if (color != currentColor) {
            buffer.clear(out);
            switchColors(color, out);
        }

        buffer.add(c, x, y);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
     *      org.extex.typesetter.type.node.DiscretionaryNode,
     *      java.lang.Object)
     */
    public Object visitDiscretionary(DiscretionaryNode node,
            StringBuffer oOut) throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
     *      org.extex.typesetter.type.node.ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Object visitExpandedLeaders(ExpandedLeadersNode node,
            StringBuffer oOut) throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
     *      org.extex.typesetter.type.node.GlueNode,
     *      java.lang.Object)
     */
    public Object visitGlue(GlueNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
     *      org.extex.typesetter.type.node.HorizontalListNode,
     *      java.lang.Object)
     */
    public Object visitHorizontalList(HorizontalListNode node,
            StringBuffer out) throws GeneralException {

        buffer.clear(out);

        Dimen saveX = new Dimen(x);
        Dimen saveY = new Dimen(y);
        x.add(node.getMove());
        y.add(node.getShift());

        Node n;
        int len = node.size();

        for (int i = 0; i < len; i++) {
            n = node.get(i);
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
     *      org.extex.typesetter.type.node.InsertionNode,
     *      java.lang.Object)
     */
    public Object visitInsertion(InsertionNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(
     *      org.extex.typesetter.type.node.KernNode,
     *      java.lang.Object)
     */
    public Object visitKern(KernNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
     *      org.extex.typesetter.type.node.LigatureNode,
     *      java.lang.Object)
     */
    public Object visitLigature(LigatureNode node, StringBuffer oOut)
            throws GeneralException {

        return visitChar(node, oOut);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(
     *      org.extex.typesetter.type.node.MarkNode,
     *      java.lang.Object)
     */
    public Object visitMark(MarkNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
     *      org.extex.typesetter.type.node.PenaltyNode,
     *      java.lang.Object)
     */
    public Object visitPenalty(PenaltyNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(
     *      org.extex.typesetter.type.node.RuleNode,
     *      java.lang.Object)
     */
    public Object visitRule(RuleNode node, StringBuffer out)
            throws GeneralException {

        buffer.clear(out);

        TypesettingContext tc = node.getTypesettingContext();
        Color color = tc.getColor();
        if (color != currentColor) {
            switchColors(color, out);
        }

        PsUnit.toPoint(node.getWidth(), out, false);
        out.append(' ');
        PsUnit.toPoint(node.getHeight(), out, false);
        out.append(' ');
        PsUnit.toPoint(x, out, false);
        out.append(' ');
        PsUnit.toPoint(y, out, false);
        out.append(' ');
        out.append("rule");
        out.append('\n');

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
     *      org.extex.typesetter.type.node.SpaceNode,
     *      java.lang.Object)
     */
    public Object visitSpace(SpaceNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
     *      org.extex.typesetter.type.node.VerticalListNode,
     *      java.lang.Object)
     */
    public Object visitVerticalList(VerticalListNode node,
            StringBuffer out) throws GeneralException {

        buffer.clear(out);

        Dimen saveX = new Dimen(x);
        Dimen saveY = new Dimen(y);
        x.add(node.getMove());
        y.add(node.getShift());

        Node n;
        int len = node.size();

        for (int i = 0; i < len; i++) {
            n = node.get(i);
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
     *      org.extex.typesetter.type.node.VirtualCharNode,
     *      java.lang.Object)
     */
    public Object visitVirtualChar(VirtualCharNode node, StringBuffer oOut)
            throws GeneralException {

        buffer.clear(oOut);
        return visitChar(node, oOut);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
     *      org.extex.typesetter.type.node.WhatsItNode,
     *      java.lang.Object)
     */
    public Object visitWhatsIt(WhatsItNode node, StringBuffer out)
            throws GeneralException {

        buffer.clear(out);

        if (node instanceof SpecialNode) {
            String text = ((SpecialNode) node).getText();
            if (text == null || text.length() == 0) {
                return null;
            }
            switch (text.charAt(0)) {
                case 'p':
                    if (text.startsWith("ps:")) {
                        out.append(text.substring(3));
                    } else if (text.startsWith("psfile=")) {
                        specialPsfile(out, text.substring(7));
                    }
                    break;
                case 'h':
                    if (text.startsWith("header=")) {
                        specialHeader(text.substring(7));
                    }
                    break;
                case '"':
                    out.append("gsave ");
                    out.append(text.substring(1));
                    out.append("grestore\n");
                    break;
                case '!':
                    try {
                        hm.add(text.substring(1), "!");
                    } catch (IOException e) {
                        throw new GeneralException(e);
                    }
                    break;
                default:
                    // ignored on purpose
            }
        }
        return null;
    }
}
