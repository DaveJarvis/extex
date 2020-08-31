/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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
 * This abstract class can be used as base for node visitors for which only a
 * few methods carry any functionality.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
@SuppressWarnings("RedundantThrows")
public abstract class AbstractNodeVisitor
    implements
    NodeVisitor<Object, Object> {

  /**
   * java.lang.Object)
   */
  public Object visitAdjust( AdjustNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitAfterMath( AfterMathNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitAlignedLeaders( AlignedLeadersNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitBeforeMath( BeforeMathNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitCenteredLeaders( CenteredLeadersNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitChar( CharNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitDiscretionary( DiscretionaryNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitExpandedLeaders( ExpandedLeadersNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitGlue( GlueNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitHorizontalList( HorizontalListNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitInsertion( InsertionNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitKern( KernNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitLigature( LigatureNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitMark( MarkNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitPenalty( PenaltyNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitRule( RuleNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitSpace( SpaceNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitVerticalList( VerticalListNode node, Object value )
      throws GeneralException {

    return null;
  }

  /**
   * java.lang.Object)
   */
  public Object visitWhatsIt( WhatsItNode node, Object value )
      throws GeneralException {

    return null;
  }

}
