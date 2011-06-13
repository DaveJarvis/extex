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

package org.extex.interpreter.type.box;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingLeftBraceException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.ListMakers;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.VerticalListNode;

/**
 * This class is used to represent box registers. A box register can either be
 * void or be a horizontal or vertical list.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public class Box implements BoxOrRule, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>nodes</tt> contains the node list stored in this box. Thus
     * is either a {@link org.extex.typesetter.type.node.HorizontalListNode
     * HorizontalListNode} or a
     * {@link org.extex.typesetter.type.node.VerticalListNode VerticalListNode}
     * or it is <code>null</code>. In case of a <code>null</code> value the box
     * is void.
     */
    private NodeList nodes = null;

    /**
     * Creates a new object.
     * 
     * @param box the box to copy (shallow)
     */
    public Box(Box box) {

        nodes = (box == null ? null : box.getNodes());
    }

    /**
     * Creates a new object.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter stack
     * @param isHorizontal indicator whether a <tt>\hbox</tt> should be
     *        constructed. The alternative is a <tt>\vbox</tt>.
     * @param insert tokens to insert at the beginning or <code>null</code> for
     *        none
     * @param groupType the type of the group just entered
     * @param startToken the token which started the group
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of a typesetter error
     * @throws org.extex.framework.configuration.exception.ConfigurationException
     *         in case of an configuration error
     */
    public Box(Context context, TokenSource source, Typesetter typesetter,
            boolean isHorizontal, Tokens insert, GroupType groupType,
            Token startToken) throws HelpingException, TypesetterException {

        Token t = source.getToken(context);

        if (t == null) {
            throw new EofException();
        } else if (!t.isa(Catcode.LEFTBRACE)) {
            throw new MissingLeftBraceException(null);
        }

        ListMaker lm =
                typesetter.pushListMaker((isHorizontal
                        ? ListMakers.RESTRICTED_HORIZONTAL
                        : ListMakers.INNER_VERTICAL), source.getLocator());

        try {
            context.openGroup(groupType, source.getLocator(), startToken);
            source.push(insert);
            source.executeGroup();
        } finally {
            while (typesetter.getListMaker() != lm) {
                typesetter
                    .add(typesetter.complete((TypesetterOptions) context));
            }

            nodes = typesetter.complete((TypesetterOptions) context);
        }
    }

    /**
     * Creates a new object.
     * 
     * @param list the node list
     */
    public Box(NodeList list) {

        nodes = list;
    }

    /**
     * Clear the contents of the box. Afterwards the box is void.
     */
    public void clear() {

        nodes = null;
    }

    /**
     * Getter for the depth of this box.
     * 
     * @return the depth of this box or 0pt in case of a void box
     */
    public FixedDimen getDepth() {

        return (nodes == null ? Dimen.ZERO_PT : nodes.getDepth());
    }

    /**
     * Getter for the height of this box.
     * 
     * @return the height of this box or 0pt in case of a void box
     */
    public FixedDimen getHeight() {

        return (nodes == null ? Dimen.ZERO_PT : nodes.getHeight());
    }

    /**
     * Getter for the localizer.
     * 
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(Box.class);
    }

    /**
     * Getter for the move parameter. The move parameter describes how far from
     * its original position the box is moved leftwards or rightwards. Positive
     * values indicate a move rightwards.
     * 
     * @return the move parameter
     */
    public FixedDimen getMove() {

        return this.nodes.getMove();
    }

    /**
     * Getter for nodes.
     * 
     * @return the nodes.
     */
    public NodeList getNodes() {

        return nodes;
    }

    /**
     * Getter for the shift parameter. The shift parameter describes how far
     * from its original position the box is shifted up or down. Positive values
     * indicate a move upwards.
     * 
     * @return the shift parameter
     */
    public FixedDimen getShift() {

        return this.nodes.getShift();
    }

    /**
     * Getter for the width of this box.
     * 
     * @return the width of this box or 0pt in case of a void box
     */
    public FixedDimen getWidth() {

        return (nodes == null ? Dimen.ZERO_PT : nodes.getWidth());
    }

    /**
     * Checks whether the box is a horizontal box.
     * 
     * @return <tt>true</tt> iff the box is a horizontal box.
     */
    public boolean isHbox() {

        return (nodes != null && nodes instanceof HorizontalListNode);
    }

    /**
     * Checks whether the box is a vertical box.
     * 
     * @return <tt>true</tt> iff the box is a vertical box.
     */
    public boolean isVbox() {

        return (nodes != null && nodes instanceof VerticalListNode);
    }

    /**
     * Checks whether the box is void.
     * 
     * @return <tt>true</tt> iff the box is void.
     */
    public boolean isVoid() {

        return nodes == null;
    }

    /**
     * Setter for the depth of the box. If the box is void then this method
     * simply does nothing.
     * 
     * @param depth the new width
     */
    public void setDepth(Dimen depth) {

        if (nodes != null) {
            nodes.setDepth(depth);
        }
    }

    /**
     * Setter for the height of the box. If the box is void then this method
     * simply does nothing.
     * 
     * @param height the new width
     */
    public void setHeight(FixedDimen height) {

        if (nodes != null) {
            if (nodes instanceof VerticalListNode) {
                ((VerticalListNode) nodes).vpack(height);
            } else {
                nodes.setHeight(height);
            }
        }
    }

    /**
     * Setter for the move parameter. The move parameter describes hpw far from
     * its original position the box is moved leftwards or rightwards. Positive
     * values indicate a move rightwards.
     * 
     * @param d the new move parameter
     */
    public void setMove(Dimen d) {

        this.nodes.setMove(d);
    }

    /**
     * Setter for the shift parameter. The shift parameter describes hpw far
     * from its original position the box is shifted up or down. Positive values
     * indicate a move upwards.
     * 
     * @param d the new shift parameter
     */
    public void setShift(Dimen d) {

        this.nodes.setShift(d);
    }

    /**
     * Setter for the width of the box. If the box is void then this method
     * simply does nothing.
     * 
     * @param width the new width
     */
    public void setWidth(FixedDimen width) {

        if (nodes != null) {
            nodes.setWidth(width);
            if (nodes instanceof HorizontalListNode) {
                ((HorizontalListNode) nodes).hpack(width);
            }
        }
    }

    /**
     * Adjust the height of the box if it is not void.
     * 
     * @param spread the length to add to the height
     */
    public void spreadHeight(FixedDimen spread) {

        if (nodes != null) {
            Dimen x = new Dimen(nodes.getHeight());
            x.add(spread);
            setHeight(x);
        }
    }

    /**
     * Adjust the width of the box if it is not void.
     * 
     * @param spread the length to add to the width
     */
    public void spreadWidth(FixedDimen spread) {

        if (nodes != null) {
            Dimen x = new Dimen(nodes.getWidth());
            x.add(spread);
            setWidth(x);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (nodes == null) {
            return "void";
        }
        return nodes.toString();
    }

    /**
     * Split off material from a vlist of a certain height.
     * 
     * @param height the height of the material to cut off
     * @param logger the logger or <code>null</code>
     * 
     * @return a new vertical node list with the material
     * 
     * @throws OperationNotSupportedException in case that the Box is not a
     *         vlist
     * 
     * @see "TTP [977]"
     */
    public VerticalListNode vsplit(Dimen height, Logger logger)
            throws OperationNotSupportedException {

        if (!isVbox()) {
            throw new OperationNotSupportedException("vsplit");
        }

        return ((VerticalListNode) nodes).split(height, logger, logger);
    }

}
