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

import org.extex.core.exception.ImpossibleException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathSpacing;

import java.util.logging.Logger;

/**
 * This noad provides a switch construction depending on the current style.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @see "TTP [689]"
 */
public class ChoiceNoad implements Noad {

  /**
   * The field {@code display} contains the noads used in display style.
   */
  private final Noad display;

  /**
   * The field {@code script} contains the noads used in script style.
   */
  private final Noad script;

  /**
   * The field {@code scriptScript} contains the noads used in scriptscript
   * style.
   */
  private final Noad scriptScript;

  /**
   * The field {@code spacingClass} contains the spacing class.
   */
  private MathSpacing spacingClass = MathSpacing.UNDEF;

  /**
   * The field {@code text} contains the noads used in text style.
   */
  private final Noad text;

  /**
   * Creates a new object.
   *
   * @param displayMath      the noads used in display style
   * @param textMath         the noads used in text style
   * @param scriptMath       the noads used in script style
   * @param scriptscriptMath the noads used in scriptscript style
   */
  public ChoiceNoad( Noad displayMath, Noad textMath, Noad scriptMath,
                     Noad scriptscriptMath ) {

    display = displayMath;
    text = textMath;
    script = scriptMath;
    scriptScript = scriptscriptMath;
  }

  /**
   * Getter for spacing class.
   *
   * @return the spacing class
   * @see org.extex.typesetter.type.noad.Noad#getSpacingClass()
   */
  @Override
  public MathSpacing getSpacingClass() {

    return spacingClass;
  }

  /**
   * Getter for the subscript.
   *
   * @return the subscript.
   * @see org.extex.typesetter.type.noad.Noad#getSubscript()
   */
  @Override
  public Noad getSubscript() {

    return null;
  }

  /**
   * Getter for the superscript.
   *
   * @return the superscript.
   * @see org.extex.typesetter.type.noad.Noad#getSuperscript()
   */
  @Override
  public Noad getSuperscript() {

    return null;
  }

  @Override
  public void setSpacingClass( MathSpacing spacingClass ) {

    this.spacingClass = spacingClass;
  }

  /**
   * Setter for the subscript.
   *
   * @param subscript the subscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSubscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSubscript( Noad subscript ) {

    throw new UnsupportedOperationException( "setSubscript()" );
  }

  /**
   * Setter for the superscript.
   *
   * @param superscript the superscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSuperscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSuperscript( Noad superscript ) {

    throw new UnsupportedOperationException( "setSuperscript()" );
  }

  /**
   * Produce a printable representation of the noad in a StringBuilder.
   *
   * @param sb the string buffer
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder)
   */
  @Override
  public void toString( StringBuilder sb ) {

    toString( sb, Integer.MAX_VALUE );
  }

  /**
   * Produce a printable representation to a certain depth of the noad.
   *
   * @param sb    the string buffer
   * @param depth the depth to which the full information should be given
   * @see "TTP [695]"
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder, int)
   */
  @Override
  public void toString( StringBuilder sb, int depth ) {

    sb.append( "\\mathchoice" );
    sb.append( "D" );
    display.toString( sb, depth );
    sb.append( "T" );
    text.toString( sb, depth );
    sb.append( "S" );
    script.toString( sb, depth );
    sb.append( "s" );
    scriptScript.toString( sb, depth );
  }

  /**
   * @see org.extex.typesetter.type.noad.Noad#typeset(org.extex.typesetter.type.noad.Noad,
   * org.extex.typesetter.type.noad.NoadList, int,
   * org.extex.typesetter.type.NodeList,
   * org.extex.typesetter.type.noad.util.MathContext,
   * java.util.logging.Logger)
   */
  @Override
  public void typeset( Noad previousNoad, NoadList noads, int index,
                       NodeList list, MathContext mathContext, Logger logger )
      throws TypesetterException,
      ConfigurationException {

    StyleNoad style = mathContext.getStyle();

    if( style == StyleNoad.DISPLAYSTYLE ) {
      display.typeset( previousNoad, noads, index, list, mathContext,
                       logger );
      spacingClass = display.getSpacingClass();
    }
    else if( style == StyleNoad.TEXTSTYLE ) {
      text.typeset( previousNoad, noads, index, list, mathContext, logger );
      spacingClass = text.getSpacingClass();
    }
    else if( style == StyleNoad.SCRIPTSTYLE ) {
      script.typeset( previousNoad, noads, index, list, mathContext,
                      logger );
      spacingClass = script.getSpacingClass();
    }
    else if( style == StyleNoad.SCRIPTSCRIPTSTYLE ) {
      scriptScript.typeset( previousNoad, noads, index, list, mathContext,
                            logger );
      spacingClass = scriptScript.getSpacingClass();
    }
    else {
      throw new ImpossibleException( "illegal style" );
    }
  }

}
