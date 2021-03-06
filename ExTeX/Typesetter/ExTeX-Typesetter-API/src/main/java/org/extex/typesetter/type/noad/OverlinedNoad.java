/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathFontParameter;
import org.extex.typesetter.type.node.ExplicitKernNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.VerticalListNode;

import java.util.logging.Logger;

/**
 * This class provides an over-lining for the nucleus.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @see "TTP [687]"
 */
public class OverlinedNoad extends AbstractNucleusNoad {

  /**
   * Creates a new object.
   *
   * @param nucleus the nucleus to be underlined
   * @param tc      the typesetting context for the color
   */
  public OverlinedNoad( Noad nucleus, TypesettingContext tc ) {

    super( nucleus, tc );
  }

  /**
   * Add some information in the middle of the default toString method.
   *
   * @param sb    the target string buffer
   * @param depth the recursion depth
   * @see "TTP [696]"
   * @see org.extex.typesetter.type.noad.AbstractNoad#toStringAdd(
   *StringBuilder,
   * int)
   */
  @Override
  protected void toStringAdd( StringBuilder sb, int depth ) {

    sb.append( "overline" );
  }

  /**
   * Translate a Noad into a NodeList.
   *
   * @param noads       the list of noads currently processed
   * @param index       the index of the current node in the list
   * @param list        the list to add the nodes to. This list contains
   *                    the Nodes
   *                    previously typeset. Thus it can be used to look back
   * @param mathContext the context to consider
   * @param logger      the logger for debugging and tracing information
   * @throws TypesetterException    in case of a problem
   * @throws ConfigurationException in case of a configuration problem
   *                                <p>
   *                                {@inheritDoc}
   * @see "TTP [705,734]"
   * @see org.extex.typesetter.type.noad.Noad#typeset(
   *org.extex.typesetter.type.noad.Noad,
   * org.extex.typesetter.type.noad.NoadList,
   * int,
   * org.extex.typesetter.type.NodeList,
   * org.extex.typesetter.type.noad.util.MathContext,
   * java.util.logging.Logger)
   */
  public void typeset( Noad previousNoad, NoadList noads,
                       int index, NodeList list,
                       MathContext mathContext, Logger logger )
      throws TypesetterException,
      ConfigurationException {

    getSpacingClass().addClearance(
        (previousNoad != null ? previousNoad.getSpacingClass() : null),
        list, mathContext );

    HorizontalListNode hlist = new HorizontalListNode();
    FixedDimen thickness =
        mathContext
            .mathParameter( MathFontParameter.DEFAULT_RULE_THICKNESS );
    StyleNoad style = mathContext.getStyle();
    mathContext.setStyle( style.cramped() );
    Noad n = getNucleus();
    n.typeset( previousNoad, noads, index, hlist, mathContext, logger );
    mathContext.setStyle( style );

    setSpacingClass( n.getSpacingClass() );
    getSpacingClass().addClearance(
        (previousNoad != null ? previousNoad.getSpacingClass() : null),
        list, mathContext );

    VerticalListNode vlist = new VerticalListNode();
    vlist.add( new ExplicitKernNode( thickness, false ) );
    vlist.add( new RuleNode( hlist.getWidth(), thickness, Dimen.ZERO_PT,
                             getTypesettingContext(), true ) );
    vlist.add( new ExplicitKernNode( new Dimen( 3 * thickness.getValue() ),
                                     false ) );
    vlist.add( hlist );
    list.add( vlist );

    Dimen h = new Dimen( vlist.getHeight() );
    h.add( vlist.getDepth() );
    Dimen d = new Dimen( hlist.getDepth() );
    vlist.setDepth( d );
    h.subtract( d );
    vlist.setHeight( h );
  }

}
