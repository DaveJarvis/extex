/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type;

import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.node.*;

/**
 * This interface implements part of the visitor pattern for nodes.
 * <p>
 * In object-oriented design a pattern has been established to let one class
 * react on the type of some object. Consider a tree composed of different types
 * of nodes all implementing a same base interface and some of them having
 * extensions of the interface. This is the situation for
 * {@link org.extex.typesetter.type.Node Node}s for which several types exist.
 * </p>
 * <p>
 * The original idea was to attach the desired functionality to the nodes and
 * let it have one common method to invoke it. This turns out not to be
 * practical for modular components where the algorithm might be exchanged. This
 * is the case for the
 * {@link org.extex.backend.documentWriter.DocumentWriter DocumentWriter} which
 * might produce Text, PDF, DVI, or some other output format. Any new
 * implementation of this interface would have the need to extend the Node
 * classes.
 * </p>
 * <p>
 * The other simplistic solution is to use a large switch, or cascaded
 * if-then-else, to differentiate the cases. This kind of code is cumbersome to
 * maintain. Whenever a new type of node is added you have to remember all
 * places which need adaption.
 * </p>
 * <p>
 * This problem is solved in the visitor pattern. Each class or interface of the
 * nodes has to provide one method called {@code visit()} here. This method
 * has as one argument the visitor which should be called back. The visitor is
 * defined by this interface. He has to provide a set of methods which allow him
 * to differentiate the types of the nodes.
 * </p>
 * <p>
 * Each node has to implement the method {@code visit()} in a way that the
 * appropriate method from the visitor interface is invoked. Thus as a result
 * the nodes and the algorithm are decoupled.
 * </p>
 *
 * <p>The Mechanics of the NodeVisitor</p>
 *
 * <p>
 * The actions during the use of a NodeVisitor is illustrated in the following
 * sequence diagram.
 * </p>
 * <div class="Figure"> <img src="doc-files/nodeVisitor.png" alt=""> <br>
 * <div class="caption">A sequence diagram for the NodeVisitor</div> </div>
 * <p>
 * In this diagram a NodeVisitor is assumed to process two nodes. The first one
 * is a {@link org.extex.typesetter.type.node.CharNode CharNode} and the second
 * one is a {@link org.extex.typesetter.type.node.GlueNode GlueNode}. We assume
 * that the concrete sub-type of {@link org.extex.typesetter.type.Node Node} to
 * be processed is not known. For instance it can be passed to the initial
 * method in a {@link org.extex.typesetter.type.NodeList NodeList}.
 * </p>
 * <p>
 * The first Node is processed by invoking the method {@code visit()}. The
 * first argument is the reference to the current instanceof the NodeVisitor.
 * Since the method is defined in CharNode to delegate it to the NodeVisitor by
 * invoking the method {@code visitChar()}. Now the real work can be
 * performed in the calling instance. Here the sub-type is known and can be
 * taken into account.
 * </p>
 * <p>
 * After the return to the caller the second node can be taken into account. The
 * procedure is the same: {@code visit()} is invoked. But now the delegation
 * used the method {@code visitGlue()}. Thus in the calling instance the
 * GlueNode can be processed specially.
 * </p>
 *
 * <p>Example Source Code</p>
 * <p>
 * Consider you have a class implementing DocumentWriter with a method which
 * needs to react differently on different node types. The first approximation
 * looks as follows:
 * </p>
 *
 * <pre class="JavaSample">
 *
 * public class MyDocumentWriter implements DocumentWriter {
 *
 *     public void myMethod(Node node) {
 *         <i>// Do something with node depending on its type</i>
 *     }
 * }
 * </pre>
 *
 * <p>
 * Now we can add the NodeVisitor interface. Thus we are forced to define a
 * bunch of methods declared in this interface:
 * </p>
 *
 * <pre class="JavaSample">
 *
 * public class MyDocumentWriter
 *     implements DocumentWriter<b>,
 *                NodeVisitor&lt;Boolean, Integer&gt;</b> {
 *
 *     public void myMethod(Node node) {
 *         <i>// Do something with node depending on its type</i>
 *     }
 * <b>
 *     public Boolean visitAdjust(AdjustNode node, Integer arg) {
 *         <i>// do something for adjust nodes</i>
 *     }
 *
 *     public Boolean visitChar(CharNode node, Integer arg) {
 *         <i>// do something for char nodes</i>
 *     }
 *
 *     <i>// and many others..</i>
 * </b>
 * }
 * </pre>
 *
 * <p>
 * Now we just have to make sure that those methods are invoked. This is done
 * with the method {@code visit()} of the Node. The signature allows us to
 * provide two additional arguments and receive a return value. Since we want to
 * do something with the node itself, this node is provided with the correct
 * type to the node visitor. The second argument can be casted to the
 * appropriate type.
 * </p>
 * <p>
 * In the {@code visit} methods we can now safely assume that the node is of
 * the named type and cast the object to have access to its public methods.
 * </p>
 *
 * <pre class="JavaSample">
 *
 * public class MyDocumentWriter
 *     implements DocumentWriter,
 *                NodeVisitor&lt;Boolean, Integer&gt; {
 *
 *     public void myMethod(Node node) {
 *         <b>node.visit(this, "some value");</b>
 *     }
 *
 *     public Object visitAdjust(AdjustNode node, Object arg) {
 *         <b>String s = (String) arg;</b>
 *         <i>// do something for adjust nodes</i>
 *     }
 *
 *     public Object visitChar(CharNode node, Object arg) {
 *         <b>String s = (String) arg;</b>
 *         <i>// do something for char nodes</i>
 *     }
 *
 *     <i>// and many others..</i>
 *
 * }
 * </pre>
 *
 * <p>
 * In the example above we have not used the additional argument or the return
 * value. In the {@code visit} methods we are free to use them in all ways we
 * like.
 * </p>
 * <p>
 * The definition of the parameters and the return value are rather general.
 * Thus it is possible to use the visitor pattern in several different
 * situations.
 * </p>
 * <p>
 * The visitor is not necessarily the class {@code MyDocumentWriter}. If
 * this class contains several methods which need to distinguish the types of
 * the nodes it is possible to use another class as visitor, e.g. an inner
 * class.
 * </p>
 *
 * @param <R> return type
 * @param <A> argument type
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface NodeVisitor<R, A> {

  /**
   * This method is called when an
   * {@link org.extex.typesetter.type.node.AdjustNode AdjustNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitAdjust( AdjustNode node, A value ) throws GeneralException;

  /**
   * This method is called when an
   * {@link org.extex.typesetter.type.node.AfterMathNode AfterMathNode} has
   * been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitAfterMath( AfterMathNode node, A value ) throws GeneralException;

  /**
   * This method is called when an
   * {@link org.extex.typesetter.type.node.AlignedLeadersNode
   * AlignedLeadersNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitAlignedLeaders( AlignedLeadersNode node, A value )
      throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.BeforeMathNode BeforeMathNode} has
   * been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitBeforeMath( BeforeMathNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.CenteredLeadersNode
   * CenteredLeadersNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitCenteredLeaders( CenteredLeadersNode node, A value )
      throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.CharNode CharNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitChar( CharNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.DiscretionaryNode
   * DiscretionaryNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitDiscretionary( DiscretionaryNode node, A value )
      throws GeneralException;

  /**
   * This method is called when an
   * {@link org.extex.typesetter.type.node.ExpandedLeadersNode
   * ExpandedLeadersNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitExpandedLeaders( ExpandedLeadersNode node, A value )
      throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.GlueNode GlueNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitGlue( GlueNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.HorizontalListNode
   * HorizontalListNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitHorizontalList( HorizontalListNode node, A value )
      throws GeneralException;

  /**
   * This method is called when an
   * {@link org.extex.typesetter.type.node.InsertionNode InsertionNode} has
   * been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitInsertion( InsertionNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.KernNode KernNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitKern( KernNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.LigatureNode LigatureNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitLigature( LigatureNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.MarkNode MarkNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitMark( MarkNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.PenaltyNode PenaltyNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitPenalty( PenaltyNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.RuleNode RuleNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitRule( RuleNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.SpaceNode SpaceNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitSpace( SpaceNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.VerticalListNode VerticalListNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitVerticalList( VerticalListNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.VirtualCharNode VirtualCharNode}
   * has been encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitVirtualChar( VirtualCharNode node, A value ) throws GeneralException;

  /**
   * This method is called when a
   * {@link org.extex.typesetter.type.node.WhatsItNode WhatsItNode} has been
   * encountered.
   *
   * @param node  the first parameter for the visitor is the node visited
   * @param value the second parameter for the visitor
   * @return the visitor specific value
   * @throws GeneralException in case of an error
   */
  R visitWhatsIt( WhatsItNode node, A value ) throws GeneralException;

}
