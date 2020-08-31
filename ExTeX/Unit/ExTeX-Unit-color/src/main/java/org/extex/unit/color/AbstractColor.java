/*
 * Copyright (C) 2006-2009 The ExTeX Group and individual authors listed below
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

package org.extex.unit.color;

import org.extex.color.Color;
import org.extex.color.ColorUtil;
import org.extex.color.ColorVisitor;
import org.extex.color.model.CmykColor;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.HsvColor;
import org.extex.color.model.RgbColor;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Showable;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.color.ColorConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class is a abstract base class for color primitives.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractColor extends AbstractAssignment
    implements
    ColorConvertible,
    Theable,
    Showable {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2009L;
  /**
   * The field {@code theVisitor} contains the color visitor for converting
   * to a printable representation.
   */
  private static final ColorVisitor theVisitor = new ColorVisitor() {

    /**
     * {@inheritDoc}
     *
     * @see org.extex.color.ColorVisitor#visitCmyk(
     *org.extex.color.model.CmykColor,
     *       java.lang.Object)
     */
    public Object visitCmyk( CmykColor color, Object c )
        throws GeneralException {

      StringBuilder sb = new StringBuilder();
      ColorUtil.formatAlpha( sb, color.getAlpha() );
      sb.append( "cmyk {" );
      ColorUtil.formatComponent( sb, color.getCyan() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getMagenta() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getYellow() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getBlack() );
      sb.append( "}" );
      return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.color.ColorVisitor#visitGray(
     *org.extex.color.model.GrayscaleColor,
     *      java.lang.Object)
     */
    public Object visitGray( GrayscaleColor color, Object c )
        throws GeneralException {

      StringBuilder sb = new StringBuilder();
      ColorUtil.formatAlpha( sb, color.getAlpha() );
      sb.append( "gray {" );
      ColorUtil.formatComponent( sb, color.getGray() );
      sb.append( "}" );
      return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.color.ColorVisitor#visitHsv(
     *org.extex.color.model.HsvColor,
     *      java.lang.Object)
     */
    public Object visitHsv( HsvColor color, Object c )
        throws GeneralException {

      StringBuilder sb = new StringBuilder();
      ColorUtil.formatAlpha( sb, color.getAlpha() );
      sb.append( "hsv {" );
      ColorUtil.formatComponent( sb, color.getHue() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getSaturation() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getValue() );
      sb.append( "}" );
      return sb.toString();
    }

    /**
     * @see org.extex.color.ColorVisitor#visitRgb(
     *org.extex.color.model.RgbColor,
     *      java.lang.Object)
     */
    public Object visitRgb( RgbColor color, Object c )
        throws GeneralException {

      StringBuilder sb = new StringBuilder();
      ColorUtil.formatAlpha( sb, color.getAlpha() );
      sb.append( "rgb {" );
      ColorUtil.formatComponent( sb, color.getRed() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getGreen() );
      sb.append( " " );
      ColorUtil.formatComponent( sb, color.getBlue() );
      sb.append( "}" );
      return sb.toString();
    }

  };

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public AbstractColor( CodeToken token ) {

    super( token );
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.interpreter.type.Showable#show(
   *org.extex.interpreter.context.Context)
   */
  public Tokens show( Context context ) throws HelpingException {

    Color color = convertColor( context, null, null );
    try {
      return context.getTokenFactory().toTokens(
          (String) color.visit( theVisitor, null ) );
    } catch( HelpingException e ) {
      throw e;
    } catch( GeneralException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.interpreter.type.Theable#the(
   *org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource,
   * org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Color color = convertColor( context, source, typesetter );
    try {
      return context.getTokenFactory().toTokens(
          (String) color.visit( theVisitor, null ) );
    } catch( HelpingException e ) {
      throw e;
    } catch( GeneralException e ) {
      throw new NoHelpException( e );
    }
  }

}
