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
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.type.box.Box;
import org.extex.typesetter.tc.font.Font;
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
     * The field <tt>nodeVisitor</tt> contains the node visitor instance to
     * use in the form of an anonymous inner class.
     */
    private NodeVisitor<Object, Object> nodeVisitor =
            new NodeVisitor<Object, Object>() {

                /**
                 * The field <tt>vmode</tt> contains the indicator that a
                 * vlist is processed.
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
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
                 *      org.extex.typesetter.type.node.AdjustNode,
                 *      java.lang.Object)
                 */
                public Object visitAdjust(AdjustNode node, Object oOut)
                        throws GeneralException {

                    write("\n");
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
                 *      org.extex.typesetter.type.node.AfterMathNode,
                 *      java.lang.Object)
                 */
                public Object visitAfterMath(AfterMathNode node, Object oOut)
                        throws GeneralException {

                    if (node.getWidth().ne(Dimen.ZERO_PT)) {
                        write(' ');
                    }
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
                        Object oOut) throws GeneralException {

                    write(" ");
                    node.visit(this, oOut);
                    node.visit(this, oOut);
                    write("  ");
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
                 *      org.extex.typesetter.type.node.BeforeMathNode,
                 *      java.lang.Object)
                 */
                public Object visitBeforeMath(BeforeMathNode node, Object oOut)
                        throws GeneralException {

                    if (node.getWidth().ne(Dimen.ZERO_PT)) {
                        write(' ');
                    }
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
                        Object oOut) throws GeneralException {

                    write("  ");
                    node.visit(this, oOut);
                    node.visit(this, oOut);
                    write("  ");
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitChar(
                 *      org.extex.typesetter.type.node.CharNode,
                 *      java.lang.Object)
                 */
                public Object visitChar(CharNode node, Object oOut)
                        throws GeneralException {

                    write(node.getCharacter().getCodePoint());
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
                        Object oOut) throws GeneralException {

                    write("--");
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
                        Object oOut) throws GeneralException {

                    write("  ");
                    node.visit(this, oOut);
                    node.visit(this, oOut);
                    write(" ");
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
                 *      org.extex.typesetter.type.node.GlueNode,
                 *      java.lang.Object)
                 */
                public Object visitGlue(GlueNode node, Object oOut)
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
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
                 *      org.extex.typesetter.type.node.HorizontalListNode,
                 *      java.lang.Object)
                 */
                public Object visitHorizontalList(HorizontalListNode list,
                        Object oOut) throws GeneralException {

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
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
                 *      org.extex.typesetter.type.node.InsertionNode,
                 *      java.lang.Object)
                 */
                public Object visitInsertion(InsertionNode node, Object oOut)
                        throws GeneralException {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitKern(
                 *      org.extex.typesetter.type.node.KernNode,
                 *      java.lang.Object)
                 */
                public Object visitKern(KernNode node, Object oOut)
                        throws GeneralException {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
                 *      org.extex.typesetter.type.node.LigatureNode,
                 *      java.lang.Object)
                 */
                public Object visitLigature(LigatureNode node, Object oOut)
                        throws GeneralException {

                    write(node.getCharacter().getCodePoint());
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitMark(
                 *      org.extex.typesetter.type.node.MarkNode,
                 *      java.lang.Object)
                 */
                public Object visitMark(MarkNode node, Object oOut)
                        throws GeneralException {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
                 *      org.extex.typesetter.type.node.PenaltyNode,
                 *      java.lang.Object)
                 */
                public Object visitPenalty(PenaltyNode node, Object oOut)
                        throws GeneralException {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitRule(
                 *      org.extex.typesetter.type.node.RuleNode,
                 *      java.lang.Object)
                 */
                public Object visitRule(RuleNode node, Object oOut)
                        throws GeneralException {

                    write("---");
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
                 *      org.extex.typesetter.type.node.SpaceNode,
                 *      java.lang.Object)
                 */
                public Object visitSpace(SpaceNode node, Object oOut)
                        throws GeneralException {

                    write(' ');
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
                 *      org.extex.typesetter.type.node.VerticalListNode,
                 *      java.lang.Object)
                 */
                public Object visitVerticalList(VerticalListNode list,
                        Object oOut) throws GeneralException {

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
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
                 *      org.extex.typesetter.type.node.VirtualCharNode,
                 *      java.lang.Object)
                 */
                public Object visitVirtualChar(VirtualCharNode node, Object oOut)
                        throws GeneralException {

                    write(node.getCharacter().getCodePoint());
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
                 *      org.extex.typesetter.type.node.WhatsItNode,
                 *      java.lang.Object)
                 */
                public Object visitWhatsIt(WhatsItNode node, Object oOut)
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
                private void write(int s) throws GeneralException {

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
                private void write(String s) throws GeneralException {

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
    public TestPdfDocumentWriter(DocumentWriterOptions opts) {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    public void close() throws IOException {

        if (out != null) {
            out.close();
            out = null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        tree = Boolean.valueOf(config.getAttribute("tree")).booleanValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#getExtension()
     */
    public String getExtension() {

        return "out";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.SingleDocumentStream#setOutputStream(
     *      java.io.OutputStream)
     */
    public void setOutputStream(OutputStream outStream) {

        out = outStream;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(
     *      java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {

        // not needed
    }

    /**
     * Setter for tree.
     * 
     * @param tree the tree to set
     */
    public void setTree(boolean tree) {

        this.tree = tree;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#shipout(
     *      org.extex.typesetter.type.page.Page)
     */
    public int shipout(Page page) throws DocumentWriterException {

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
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#getAnnotation(org.extex.typesetter.type.node.RuleNode,
     *      java.lang.String)
     */
    public PdfAnnotation getAnnotation(RuleNode node, String annotation)
            throws HelpingException {

        // TODO gene: getAnnotation unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#getObject(java.lang.String,
     *      boolean, java.lang.String)
     */
    public PdfObject getObject(String attr, boolean isStream, String text)
            throws HelpingException {

        // TODO gene: getObject unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#getXForm(java.lang.String,
     *      java.lang.String, org.extex.interpreter.type.box.Box)
     */
    public PdfXForm getXForm(String attr, String resources, Box box)
            throws HelpingException {

        // TODO gene: getXForm unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#getXImage(java.lang.String,
     *      org.extex.typesetter.type.node.RuleNode, java.lang.String, long,
     *      boolean)
     */
    public PdfRefXImage getXImage(String resource, RuleNode rule, String attr,
            long page, boolean immediate) throws HelpingException {

        // TODO gene: getXImage unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfcatalog(java.lang.String,
     *      org.extex.unit.pdftex.util.action.ActionSpec)
     */
    public void pdfcatalog(String text, ActionSpec action) {

        // TODO gene: pdfcatalog unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdffontname(org.extex.typesetter.tc.font.Font)
     */
    public String pdffontname(Font font) {

        // TODO gene: pdffontname unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdffontobjnum(org.extex.typesetter.tc.font.Font)
     */
    public long pdffontobjnum(Font font) {

        // TODO gene: pdffontobjnum unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfincludechars(org.extex.typesetter.tc.font.Font,
     *      java.lang.String)
     */
    public void pdfincludechars(Font font, String text) {

        // TODO gene: pdfincludechars unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfinfo(java.lang.String)
     */
    public void pdfinfo(String text) {

        // TODO gene: pdfinfo unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastannot()
     */
    public long pdflastannot() {

        // TODO gene: pdflastannot unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastobj()
     */
    public long pdflastobj() {

        // TODO gene: pdflastobj unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastxform()
     */
    public long pdflastxform() {

        // TODO gene: pdflastxform unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdflastximage()
     */
    public long pdflastximage() {

        // TODO gene: pdflastximage unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfnames(java.lang.String)
     */
    public void pdfnames(String text) {

        // TODO gene: pdfnames unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.backend.documentWriter.PdftexSupport#pdfoutline(org.extex.unit.pdftex.util.action.ActionSpec,
     *      long, java.lang.String)
     */
    public void pdfoutline(ActionSpec action, long count, String text) {

        // TODO gene: pdfoutline unimplemented

    }

}
