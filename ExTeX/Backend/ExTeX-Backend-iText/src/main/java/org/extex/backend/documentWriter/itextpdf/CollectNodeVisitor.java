/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.itextpdf;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.GeneralException;
import org.extex.font.BackendFontManager;
import org.extex.font.exception.FontException;
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
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.WhatsItNode;

/**
 * Node visitor, which collect all nodes.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class CollectNodeVisitor implements NodeVisitor<Object, Object> {

    /**
     * The backend manager.
     */
    private final BackendFontManager manager;

    public CollectNodeVisitor(BackendFontManager manager) {

        this.manager = manager;
    }

    @Override
    public Object visitAdjust(AdjustNode node, Object value) {

        return null;
    }

    @Override
    public Object visitAfterMath(AfterMathNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitAlignedLeaders(AlignedLeadersNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitBeforeMath(BeforeMathNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitCenteredLeaders(CenteredLeadersNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitChar(CharNode node, Object value)
            throws GeneralException {

        try {

            UnicodeChar uc = node.getCharacter();
            Font font = node.getTypesettingContext().getFont();
            manager.recognize(font.getFontKey(), uc);

        } catch (FontException e) {
            throw new GeneralException(e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public Object visitDiscretionary(DiscretionaryNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitExpandedLeaders(ExpandedLeadersNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitGlue(GlueNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitHorizontalList(HorizontalListNode node, Object value)
            throws GeneralException {

        for (Node n : node) {
            n.visit(this, node);
        }
        return null;
    }

    @Override
    public Object visitInsertion(InsertionNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitKern(KernNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitLigature(LigatureNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitMark(MarkNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitPenalty(PenaltyNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitRule(RuleNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitSpace(SpaceNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitVerticalList(VerticalListNode node, Object value)
            throws GeneralException {

        for (Node n : node) {
            n.visit(this, node);
        }

        return null;
    }

    @Override
    public Object visitVirtualChar(VirtualCharNode node, Object value)
            throws GeneralException {

        return null;
    }

    @Override
    public Object visitWhatsIt(WhatsItNode node, Object value)
            throws GeneralException {

        return null;
    }

}
