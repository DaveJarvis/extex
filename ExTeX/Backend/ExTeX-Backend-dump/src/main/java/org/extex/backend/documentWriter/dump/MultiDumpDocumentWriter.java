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

package org.extex.backend.documentWriter.dump;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.MultipleDocumentStream;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.page.Page;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is an implementation of a document writer which puts of each page into a
 * new output stream.
 * <p>
 * It can act both as sample and as tool for testing.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MultiDumpDocumentWriter
        implements
            DocumentWriter,
            MultipleDocumentStream,
            Configurable {

    /**
     * The field <tt>extension</tt> contains the default extension.
     */
    private String extension = "out";

    /**
     * The field <tt>nodeVisitor</tt> contains the node visitor instance to use
     * in the form of an anonymous inner class.
     */
    private final NodeVisitor<Object, Object> nodeVisitor =
            new NodeVisitor<Object, Object>() {

                /**
                 * The field <tt>isVertical</tt> contains the indicator that a
                 * vlist is processed.
                 */
                private boolean isVertical = false;

                /**
                 * Print a newline in vertical mode.
                 * 
                 * @throws GeneralException in case of an error
                 */
                private void nl() throws GeneralException {

                    if (isVertical) {
                        try {
                            out.write('\n');
                        } catch (IOException e) {
                            throw new GeneralException(e);
                        }
                    }
                }

                /**
                 * This method is called when an
                 * {@link org.extex.typesetter.type.node.AdjustNode AdjustNode}
                 * has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(org.extex.typesetter.type.node.AdjustNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitAdjust(AdjustNode node, Object oOut)
                        throws GeneralException {

                    write("\n");
                    return null;
                }

                /**
                 * This method is called when an
                 * {@link org.extex.typesetter.type.node.AfterMathNode
                 * AfterMathNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(org.extex.typesetter.type.node.AfterMathNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitAfterMath(AfterMathNode node, Object oOut)
                        throws GeneralException {

                    if (node.getWidth().ne(Dimen.ZERO_PT)) {
                        write(' ');
                    }
                    return null;
                }

                /**
                 * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(org.extex.typesetter.type.node.AlignedLeadersNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitAlignedLeaders(AlignedLeadersNode node,
                        Object oOut) throws GeneralException {

                    write(" ");
                    node.visit(this, oOut);
                    node.visit(this, oOut);
                    write("  ");
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.BeforeMathNode
                 * BeforeMathNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(org.extex.typesetter.type.node.BeforeMathNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitBeforeMath(BeforeMathNode node, Object oOut)
                        throws GeneralException {

                    if (node.getWidth().ne(Dimen.ZERO_PT)) {
                        write(' ');
                    }
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.CenteredLeadersNode
                 * CenteredLeadersNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(org.extex.typesetter.type.node.CenteredLeadersNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitCenteredLeaders(CenteredLeadersNode node,
                        Object oOut) throws GeneralException {

                    write("  ");
                    node.visit(this, oOut);
                    node.visit(this, oOut);
                    write("  ");
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.CharNode CharNode} has
                 * been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitChar(org.extex.typesetter.type.node.CharNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitChar(CharNode node, Object oOut)
                        throws GeneralException {

                    write(node.getCharacter().getCodePoint());
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.DiscretionaryNode
                 * DiscretionaryNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(org.extex.typesetter.type.node.DiscretionaryNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitDiscretionary(DiscretionaryNode node,
                        Object oOut) throws GeneralException {

                    write("--");
                    return null;
                }

                /**
                 * This method is called when an
                 * {@link org.extex.typesetter.type.node.ExpandedLeadersNode
                 * ExpandedLeadersNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(org.extex.typesetter.type.node.ExpandedLeadersNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitExpandedLeaders(ExpandedLeadersNode node,
                        Object oOut) throws GeneralException {

                    write("  ");
                    node.visit(this, oOut);
                    node.visit(this, oOut);
                    write(" ");
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.GlueNode GlueNode} has
                 * been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitGlue(org.extex.typesetter.type.node.GlueNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitGlue(GlueNode node, Object oOut)
                        throws GeneralException {

                    if (isVertical) {
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
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.HorizontalListNode
                 * HorizontalListNode} has been encountered.
                 * 
                 * @param list the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(org.extex.typesetter.type.node.HorizontalListNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitHorizontalList(HorizontalListNode list,
                        Object oOut) throws GeneralException {

                    boolean mode = isVertical;
                    isVertical = false;
                    for (Node node : list) {
                        node.visit(this, oOut);
                    }
                    isVertical = mode;
                    nl();
                    return null;
                }

                /**
                 * This method is called when an
                 * {@link org.extex.typesetter.type.node.InsertionNode
                 * InsertionNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(org.extex.typesetter.type.node.InsertionNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitInsertion(InsertionNode node, Object oOut) {

                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.KernNode KernNode} has
                 * been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 *
                 * @see org.extex.typesetter.type.NodeVisitor#visitKern(org.extex.typesetter.type.node.KernNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitKern(KernNode node, Object oOut) {

                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.LigatureNode
                 * LigatureNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitLigature(org.extex.typesetter.type.node.LigatureNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitLigature(LigatureNode node, Object oOut)
                        throws GeneralException {

                    write(node.getCharacter().getCodePoint());
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.MarkNode MarkNode} has
                 * been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitMark(org.extex.typesetter.type.node.MarkNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitMark(MarkNode node, Object oOut) {

                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.PenaltyNode
                 * PenaltyNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(org.extex.typesetter.type.node.PenaltyNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitPenalty(PenaltyNode node, Object oOut) {

                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.RuleNode RuleNode} has
                 * been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitRule(org.extex.typesetter.type.node.RuleNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitRule(RuleNode node, Object oOut)
                        throws GeneralException {

                    write("---");
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.SpaceNode SpaceNode}
                 * has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitSpace(org.extex.typesetter.type.node.SpaceNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitSpace(SpaceNode node, Object oOut)
                        throws GeneralException {

                    write(' ');
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.VerticalListNode
                 * VerticalListNode} has been encountered.
                 * 
                 * @param list the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(org.extex.typesetter.type.node.VerticalListNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitVerticalList(VerticalListNode list,
                        Object oOut) throws GeneralException {

                    boolean mode = isVertical;
                    isVertical = true;
                    for (Node node : list) {
                        node.visit(this, oOut);
                    }
                    isVertical = mode;
                    nl();
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.CharNode CharNode} has
                 * been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @throws GeneralException in case of an error
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(org.extex.typesetter.type.node.VirtualCharNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitVirtualChar(VirtualCharNode node, Object oOut)
                        throws GeneralException {

                    write(node.getCharacter().getCodePoint());
                    return null;
                }

                /**
                 * This method is called when a
                 * {@link org.extex.typesetter.type.node.WhatsItNode
                 * WhatsItNode} has been encountered.
                 * 
                 * @param node the first parameter for the visitor is the node
                 *        visited
                 * @param oOut the second parameter for the visitor
                 * 
                 * @return <code>null</code>
                 * 
                 * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(org.extex.typesetter.type.node.WhatsItNode,
                 *      java.lang.Object)
                 */
                @Override
                public Object visitWhatsIt(WhatsItNode node, Object oOut) {

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
                        if (isVertical) {
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
     * The field <tt>outputStreamFactory</tt> contains the factory for output
     * streams.
     */
    private OutputStreamFactory outputStreamFactory;

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
    public MultiDumpDocumentWriter(DocumentWriterOptions opts) {

    }

    /**
     * @see org.extex.backend.documentWriter.DocumentWriter#close()
     */
    @Override
    public void close() {

        // nothing to do
    }

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param config the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration config) throws ConfigurationException {

        tree = Boolean.parseBoolean( config.getAttribute( "tree" ) );
        String s = config.getAttribute("extension");
        if (s != null) {
            extension = s;
        }
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
    @Override
    public String getExtension() {

        return extension;
    }

    /**
     * Setter for the output stream.
     * 
     * @param writerFactory the output stream
     * 
     * @see org.extex.backend.documentWriter.MultipleDocumentStream#setOutputStreamFactory(org.extex.backend.outputStream.OutputStreamFactory)
     */
    @Override
    public void setOutputStreamFactory(OutputStreamFactory writerFactory) {

        outputStreamFactory = writerFactory;
    }

    /**
     * Setter for a named parameter. Parameters are a general mechanism to
     * influence the behavior of the document writer. Any parameter not known by
     * the document writer has to be ignored.
     * 
     * @param name the name of the parameter
     * @param value the value of the parameter
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#setParameter(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void setParameter(String name, String value) {

        // not supported yet
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
     * @throws DocumentWriterException in case of an error
     * 
     * @see org.extex.backend.documentWriter.DocumentWriter#shipout(org.extex.typesetter.type.page.Page)
     */
    @Override
    public int shipout(Page page) throws DocumentWriterException {

        if (page == null) {
            return 0;
        }

        NodeList nodes = page.getNodes();
        out = outputStreamFactory.getOutputStream(null, null);
        try {
            if (tree) {
                StringBuilder sb = new StringBuilder();
                nodes.toString(sb, "\n", Integer.MAX_VALUE, Integer.MAX_VALUE);
                out.write(sb.toString().getBytes());
                out.write('\n');
            } else {
                nodes.visit(nodeVisitor, out);
                out.write('\n');
            }
            out.close();
        } catch (IOException e) {
            throw new DocumentWriterException(e);
        } catch (DocumentWriterException e) {
            throw e;
        } catch (GeneralException e) {
            Throwable ex = e.getCause();
            throw (ex instanceof DocumentWriterException 
                    ? (DocumentWriterException) ex 
                    : new DocumentWriterException(e.getLocalizedMessage()));
        }
        out = null;
        return 1;
    }

}
