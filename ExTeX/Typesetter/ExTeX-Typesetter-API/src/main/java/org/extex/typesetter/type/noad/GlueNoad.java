/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.node.GlueNode;

import java.util.logging.Logger;

/**
 * This Noad carries a muglue value. This value is translated into a GlueNode
 * with the translated glue value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class GlueNoad extends AbstractNoad {

  /**
   * The field {@code muglue} contains the glue.
   */
  private final Muskip muglue;

  /**
   * Creates a new object.
   *
   * @param muglue the glue
   */
  public GlueNoad( Muskip muglue ) {

    this.muglue = muglue;
  }

  /**
   * Getter for muglue.
   *
   * @return the muglue
   */
  public Muskip getMuglue() {

    return this.muglue;
  }

  /**
   * Getter for kill.
   *
   * @return the kill
   * @see org.extex.core.muskip.Muskip#isKill()
   */
  public boolean isKill() {

    return this.muglue.isKill();
  }

  /**
   * {@inheritDoc}
   *
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
      throws TypesetterException {

    if( previousNoad instanceof GlueNoad
        && ((GlueNoad) previousNoad).isKill() ) {
      StyleNoad style = mathContext.getStyle();
      if( style == StyleNoad.SCRIPTSTYLE
          || style == StyleNoad.SCRIPTSCRIPTSTYLE ) {
        return;
      }
    }

    setSpacingClass( previousNoad.getSpacingClass() );
    Glue glue = mathContext.convert( muglue );
    if( !glue.eq( FixedGlue.ZERO ) ) {
      list.add( new GlueNode( glue, true ) );
    }
  }

}
