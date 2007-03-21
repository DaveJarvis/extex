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

package org.extex.test.documentWriter;

import java.io.IOException;
import java.io.OutputStream;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.PdftexSupport;
import org.extex.backend.documentWriter.SingleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.font.Font;
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
import org.extex.typesetter.type.node.pdftex.PdfAnnotation;
import org.extex.typesetter.type.node.pdftex.PdfObject;
import org.extex.typesetter.type.node.pdftex.PdfRefXImage;
import org.extex.typesetter.type.node.pdftex.PdfXForm;
import org.extex.typesetter.type.page.Page;
import org.extex.unit.pdftex.util.action.ActionSpec;

/**
 * This is an implementation of a document writer which can act both as sample
 * and as tool for testing.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TestPdfDocumentWriter
        implements
            DocumentWriter,
            SingleDocumentStream,
            PdftexSupport,
            Configurable {

    /**
     * The field <tt>nodeVisitor</tt> contains the node visitor instance to use
     * in the form of an anonymous inner class.
     */
    private NodeVisitor nodeVisitor = new NodeVisitor() {

        /**
         * The field <tt>vmode</tt> contains the indicator that a vlist is
         * processed.
         */
        private boolean vmode = false;

        /**
         * Print a new line in vertical mode.
         *
         * @throws GeneralException in case of an error
         */
        private void nl() throws GeneralException {

            if (vmode) {
                try {
                    out.write('\n');
                } catch (IOException e) {
                    throw new GeneralException(e);
                }
            }
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
         *      org.extex.typesetter.type.node.AdjustNode,
         *      java.lang.Object)
         */
        public Object visitAdjust(final AdjustNode node, final Object oOut)
                throws GeneralException {

            write("\n");
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
         *      org.extex.typesetter.type.node.AfterMathNode,
         *      java.lang.Object)
         */
        public Object visitAfterMath(final AfterMathNode node, final Object oOut)
                throws GeneralException {

            if (node.getWidth().ne(Dimen.ZERO_PT)) {
                write(' ');
            }
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
         *      org.extex.typesetter.type.node.AlignedLeadersNode,
         *      java.lang.Object)
         */
        public Object visitAlignedLeaders(final AlignedLeadersNode node,
                final Object oOut) throws GeneralException {

            write(" ");
            node.visit(this, oOut);
            node.visit(this, oOut);
            write("  ");
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
         *      org.extex.typesetter.type.node.BeforeMathNode,
         *      java.lang.Object)
         */
        public Object visitBeforeMath(final BeforeMathNode node,
                final Object oOut) throws GeneralException {

            if (node.getWidth().ne(Dimen.ZERO_PT)) {
                write(' ');
            }
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
         *      org.extex.typesetter.type.node.CenteredLeadersNode,
         *      java.lang.Object)
         */
        public Object visitCenteredLeaders(final CenteredLeadersNode node,
                final Object oOut) throws GeneralException {

            write("  ");
            node.visit(this, oOut);
            node.visit(this, oOut);
            write("  ");
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitChar(
         *      org.extex.typesetter.type.node.CharNode,
         *      java.lang.Object)
         */
        public Object visitChar(final CharNode node, final Object oOut)
                throws GeneralException {

            write(node.getCharacter().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
         *      org.extex.typesetter.type.node.DiscretionaryNode,
         *      java.lang.Object)
         */
        public Object visitDiscretionary(final DiscretionaryNode node,
                final Object oOut) throws GeneralException {

            write("--");
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
         *      org.extex.typesetter.type.node.ExpandedLeadersNode,
         *      java.lang.Object)
         */
        public Object visitExpandedLeaders(final ExpandedLeadersNode node,
                final Object oOut) throws GeneralException {

            write("  ");
            node.visit(this, oOut);
            node.visit(this, oOut);
            write(" ");
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
         *      org.extex.typesetter.type.node.GlueNode,
         *      java.lang.Object)
         */
        public Object visitGlue(final GlueNode node, final Object oOut)
                throws GeneralException {

            if (vmode) {
                if (node.getHeight().ne(Dimen.ZERO_PT)
                        && node.getDepth().ne(Dimen.ZERO_PT)) {
                    write('\n');
                }
            } else {
                if (node.getWidth().ne(Dimen.ZERO_PT)) {
                    write(' ');
                }
            }
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
         *      org.extex.typesetter.type.node.HorizontalListNode,
         *      java.lang.Object)
         */
        public Object visitHorizontalList(final HorizontalListNode list,
                final Object oOut) throws GeneralException {

            boolean mode = vmode;
            vmode = false;
            for (int i = 0; i < list.size(); i++) {
                list.get(i).visit(this, oOut);
            }
            vmode = mode;
            nl();
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
         *      org.extex.typesetter.type.node.InsertionNode,
         *      java.lang.Object)
         */
        public Object visitInsertion(final InsertionNode node, final Object oOut)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitKern(
         *      org.extex.typesetter.type.node.KernNode,
         *      java.lang.Object)
         */
        public Object visitKern(final KernNode node, final Object oOut)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
         *      org.extex.typesetter.type.node.LigatureNode,
         *      java.lang.Object)
         */
        public Object visitLigature(final LigatureNode node, final Object oOut)
                throws GeneralException {

            write(node.getCharacter().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitMark(
         *      org.extex.typesetter.type.node.MarkNode,
         *      java.lang.Object)
         */
        public Object visitMark(final MarkNode node, final Object oOut)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
         *      org.extex.typesetter.type.node.PenaltyNode,
         *      java.lang.Object)
         */
        public Object visitPenalty(final PenaltyNode node, final Object oOut)
                throws GeneralException {

            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitRule(
         *      org.extex.typesetter.type.node.RuleNode,
         *      java.lang.Object)
         */
        public Object visitRule(final RuleNode node, final Object oOut)
                throws GeneralException {

            write("---");
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
         *      org.extex.typesetter.type.node.SpaceNode,
         *      java.lang.Object)
         */
        public Object visitSpace(final SpaceNode node, final Object oOut)
                throws GeneralException {

            write(' ');
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
         *      org.extex.typesetter.type.node.VerticalListNode,
         *      java.lang.Object)
         */
        public Object visitVerticalList(final VerticalListNode list,
                final Object oOut) throws GeneralException {

            boolean mode = vmode;
            vmode = true;
            for (int i = 0; i < list.size(); i++) {
                list.get(i).visit(this, oOut);
            }
            vmode = mode;
            nl();
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitChar(
         *      org.extex.typesetter.type.node.VirtualCharNode,
         *      java.lang.Object)
         */
        public Object visitVirtualChar(final VirtualCharNode node,
                final Object oOut) throws GeneralException {

            write(node.getCharacter().getCodePoint());
            return null;
        }

        /**
         * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
         *      org.extex.typesetter.type.node.WhatsItNode,
         *      java.lang.Object)
         */
        public Object visitWhatsIt(final WhatsItNode node, final Object oOut)
                throws GeneralException {

            return null;
        }

        /**
         * Write a char to out.
         *
         * @param s the char to write
         *
         * @throws GeneralException in case of an error
         */
        private void write(final int s) throws GeneralException {

            try {
                out.write(s);
            } catch (IOException e) {
                throw new GeneralException(e);
            }
        }

        /**
         * Write a string to out.
         *
         * @param s the string to write
         *
         * @throws GeneralException in case of an error
         */
        private void write(final String s) throws GeneralException {

            try {
                out.write(s.getBytes());
                if (vmode) {
                    out.write('\n');
                }
            } catch (IOException e) {
                throw new GeneralException(e);
            }

        }
    };

    /**
     * The field <tt>out</tt> contains the output stream to use.
     */
    private OutputStream out = null;

    /**
     * The field <tt>tree</tt> contains the indicator whether to use the tree
     * representation.
     */
    private boolean tree = true;

    /**
     * Creates a new object.
     *
     * @param opts the dynamic access to the context
     */
    public TestPdfDocumentWriter(final DocumentWriterOptions opts) {

        super();
    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    public void close() throws IOException {

        if (out != null) {
            out.close();
            out = null;
        }
    }

    /**
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(final Configuration config)
            throws ConfigurationException {

        tree = Boolean.valueOf(config.getAttribute("tree")).booleanValue();
    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
     */
    public String getExtension() {

        return "out";
    }

    /**
     * @see org.extex.backend.documentWriter.SingleDocumentStream#setOutputStream(
     *      java.io.OutputStream)
     */
    public void setOutputStream(final OutputStream outStream) {

        out = outStream;
    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String, java.lang.String)
     */
    public void setParameter(final String name, final String value) {

        //not needed
    }

    /**
     * Setter for tree.
     *
     * @param tree the tree to set
     */
    public void setTree(final boolean tree) {

        this.tree = tree;
    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(final Page page) throws DocumentWriterException {

        NodeList nodes = page.getNodes();
        try {
            if (tree) {
                StringBuffer sb = new StringBuffer();
                nodes.toString(sb, "\n", Integer.MAX_VALUE, Integer.MAX_VALUE);
                out.write(sb.toString().getBytes());
                out.write('\n');
            } else {
                nodes.visit(nodeVisitor, out);
                out.write('\n');
            }
        } catch (IOException e) {
            throw new DocumentWriterException(e);
        } catch (GeneralException e) {
            Throwable ex = e.getCause();
            throw (ex instanceof DocumentWriterException //
                    ? (DocumentWriterException) ex //
                    : new DocumentWriterException(e.getLocalizedMessage()));
        }
        return 1;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#getAnnotation(org.extex.typesetter.type.node.RuleNode, java.lang.String)
     */
    public PdfAnnotation getAnnotation(final RuleNode node,
            final String annotation) throws InterpreterException {

        // TODO gene: getAnnotation unimplemented
        return null;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#getObject(java.lang.String, boolean, java.lang.String)
     */
    public PdfObject getObject(final String attr, final boolean isStream,
            final String text) throws InterpreterException {

        // TODO gene: getObject unimplemented
        return null;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#getXForm(java.lang.String, java.lang.String, org.extex.interpreter.type.box.Box)
     */
    public PdfXForm getXForm(final String attr, final String resources,
            final Box box) throws InterpreterException {

        // TODO gene: getXForm unimplemented
        return null;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#getXImage(java.lang.String, org.extex.typesetter.type.node.RuleNode, java.lang.String, long, boolean)
     */
    public PdfRefXImage getXImage(final String resource, final RuleNode rule,
            final String attr, final long page, final boolean immediate)
            throws InterpreterException {

        // TODO gene: getXImage unimplemented
        return null;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfcatalog(java.lang.String, org.extex.unit.pdftex.util.action.ActionSpec)
     */
    public void pdfcatalog(final String text, final ActionSpec action) {

        // TODO gene: pdfcatalog unimplemented

    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdffontname(org.extex.interpreter.type.font.Font)
     */
    public String pdffontname(final Font font) {

        // TODO gene: pdffontname unimplemented
        return null;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdffontobjnum(org.extex.interpreter.type.font.Font)
     */
    public long pdffontobjnum(final Font font) {

        // TODO gene: pdffontobjnum unimplemented
        return 0;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfincludechars(org.extex.interpreter.type.font.Font, java.lang.String)
     */
    public void pdfincludechars(final Font font, final String text) {

        // TODO gene: pdfincludechars unimplemented

    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfinfo(java.lang.String)
     */
    public void pdfinfo(final String text) {

        // TODO gene: pdfinfo unimplemented

    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastannot()
     */
    public long pdflastannot() {

        // TODO gene: pdflastannot unimplemented
        return 0;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastobj()
     */
    public long pdflastobj() {

        // TODO gene: pdflastobj unimplemented
        return 0;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastxform()
     */
    public long pdflastxform() {

        // TODO gene: pdflastxform unimplemented
        return 0;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastximage()
     */
    public long pdflastximage() {

        // TODO gene: pdflastximage unimplemented
        return 0;
    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfnames(java.lang.String)
     */
    public void pdfnames(final String text) {

        // TODO gene: pdfnames unimplemented

    }

    /**
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfoutline(org.extex.unit.pdftex.util.action.ActionSpec, long, java.lang.String)
     */
    public void pdfoutline(final ActionSpec action, final long count,
            final String text) {

        // TODO gene: pdfoutline unimplemented

    }

}
