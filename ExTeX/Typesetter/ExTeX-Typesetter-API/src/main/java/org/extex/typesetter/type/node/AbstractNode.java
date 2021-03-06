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

package org.extex.typesetter.type.node;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.Glue;
import org.extex.core.glue.WideGlue;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;

/**
 * This abstract class provides some methods common to all Nodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractNode implements Node {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2009L;

  /**
   * The constant {@code NO_CHAR} contains the empty array of CharNode.
   */
  protected static final CharNode[] NO_CHARS = new CharNode[ 0 ];

  /**
   * The field {@code depth} contains the depth of the node. The depth is the
   * extend of the node below the baseline.
   */
  private Glue depth;

  /**
   * The field {@code height} contains the height of the node. The height is
   * the extend of the node above the baseline.
   */
  private Glue height;

  /**
   * The field {@code localizer} contains the localizer.
   */
  private Localizer localizer = null;

  /**
   * This is the width of the node. The width is the extend of the node along
   * the baseline.
   */
  private Glue width;

  /**
   * Creates a new object. All dimensions (width, height, depth) are initially
   * set to 0pt.
   */
  public AbstractNode() {

    width = new Glue();
    height = new Glue();
    depth = new Glue();
  }

  /**
   * Creates a new object.
   *
   * @param aWidth the width of the node
   */
  public AbstractNode( FixedDimen aWidth ) {

    this.width = new Glue( aWidth );
    this.height = new Glue();
    this.depth = new Glue();
  }

  /**
   * Creates a new object.
   *
   * @param aWidth  the width of the node
   * @param aHeight the height of the node
   * @param aDepth  the depth of the node
   */
  public AbstractNode( FixedDimen aWidth, FixedDimen aHeight,
                       FixedDimen aDepth ) {

    this.width = new Glue( aWidth );
    this.height = new Glue( aHeight );
    this.depth = new Glue( aDepth );
  }

  /**
   * Add the flexible depth of the current node to the given glue.
   *
   * @param glue the glue to add to.
   * @see org.extex.typesetter.type.Node#addDepthTo(org.extex.core.glue.WideGlue)
   */
  @Override
  public void addDepthTo( WideGlue glue ) {

    glue.add( depth );
  }

  /**
   * Add the flexible height of the current node to the given glue.
   *
   * @param glue the glue to add to.
   * @see org.extex.typesetter.type.Node#addHeightTo(org.extex.core.glue.WideGlue)
   */
  @Override
  public void addHeightTo( WideGlue glue ) {

    glue.add( height );
  }

  /**
   * Add the flexible width of the current node to the given glue.
   *
   * @param glue the glue to add to.
   * @see org.extex.typesetter.type.Node#addWidthTo(org.extex.core.glue.WideGlue)
   */
  @Override
  public void addWidthTo( WideGlue glue ) {

    glue.add( width );
  }

  /**
   * Advance the depth by some length. The length can also be negative.
   *
   * @param x the length to add
   */
  public void advanceNaturalDepth( FixedDimen x ) {

    depth.add( x );
  }

  /**
   * Advance the height by some length. The length can also be negative.
   *
   * @param x the length to add
   */
  public void advanceNaturalHeight( FixedDimen x ) {

    height.add( x );
  }

  /**
   * Advance the width by some length. The length can also be negative.
   *
   * @param x the length to add
   */
  public void advanceNaturalWidth( FixedDimen x ) {

    width.add( x );
  }

  /**
   * org.extex.typesetter.Typesetter, org.extex.core.dimen.FixedDimen,
   * org.extex.core.dimen.FixedDimen)
   */
  @Override
  public Node atShipping( PageContext context, Typesetter typesetter,
                          FixedDimen posX, FixedDimen posY )
      throws GeneralException {

    return this;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {

    AbstractNode clone = (AbstractNode) super.clone();
    clone.width = new Glue( width );
    clone.height = new Glue( height );
    clone.depth = new Glue( depth );
    return clone;
  }

  /**
   * Compute the amount of adjustment needed to achieve a certain size.
   *
   * @param size the current size in scaled points
   * @param glue the glue
   * @param sum  the total stretchability or shrinkability
   * @return the adjustment
   */
  protected long computeAdjustment( long size, FixedGlue glue,
                                    FixedGlueComponent sum ) {

    FixedGlueComponent s =
        (size > 0 ? glue.getStretch() : glue.getShrink());

    int order = s.getOrder();
    long value = sum.getValue();
    if( order < sum.getOrder() || value == 0 ) {
      return 0;
    }

    long sValue = s.getValue();
    long adjust = sValue * size / value;
    if( order == 0 ) {
      if( adjust > sValue ) {
        adjust = sValue;
      }
      else if( adjust < -sValue ) {
        adjust = -sValue;
      }
    }
    return adjust;
  }

  /**
   * This method determines the number of characters contained in a node.
   *
   * @return the number of characters contained
   * @see org.extex.typesetter.type.Node#countChars()
   */
  @Override
  public int countChars() {

    return 0;
  }

  /**
   * Getter for the array of characters enclosed in this node.
   *
   * @return the array of characters
   * @see org.extex.typesetter.type.Node#getChars()
   */
  @Override
  public CharNode[] getChars() {

    return NO_CHARS;
  }

  /**
   * Getter for the depth of the node.
   *
   * @return the depth
   * @see org.extex.typesetter.type.Node#getDepth()
   */
  @Override
  public FixedDimen getDepth() {

    return depth.getLength();
  }

  /**
   * Getter for the height of the node.
   *
   * @return the height
   * @see org.extex.typesetter.type.Node#getHeight()
   */
  @Override
  public FixedDimen getHeight() {

    return height.getLength();
  }

  /**
   * Getter for localizer.
   *
   * @return the localizer
   */
  protected Localizer getLocalizer() {

    if( this.localizer == null ) {
      this.localizer = LocalizerFactory.getLocalizer( this.getClass() );
    }
    return this.localizer;
  }

  @Override
  public FixedDimen getNaturalDepth() {

    return depth.getLength();
  }

  @Override
  public FixedDimen getNaturalHeight() {

    return height.getLength();
  }

  /**
   * Getter for the natural width of the node.
   *
   * @return the width
   * @see #getWidth()
   */
  @Override
  public FixedDimen getNaturalWidth() {

    return width.getLength();
  }

  /**
   * Compute the vertical size of a node.
   *
   * @return the vertical size
   * @see org.extex.typesetter.type.Node#getVerticalSize()
   */
  @Override
  public FixedDimen getVerticalSize() {

    FixedDimen h = getHeight();
    Dimen d = new Dimen( getDepth() );

    if( h.ge( Dimen.ZERO ) ) {
      if( d.ge( Dimen.ZERO ) ) {
        d.add( h );
      }
      else {
        d.negate();
        d.max( h );
      }
    }
    else {
      if( d.ge( Dimen.ZERO ) ) {
        d.negate();
        d.min( h );
        d.negate();
      }
      else {
        d.add( h );
        d.negate();
      }
    }
    return d;
  }

  /**
   * Getter for the width of the node.
   *
   * @return the width
   * @see org.extex.typesetter.type.Node#getWidth()
   */
  @Override
  public FixedDimen getWidth() {

    return width.getLength();
  }

  /**
   * Assign the maximum of the current value and a comparison value to the
   * depth.
   *
   * @param x the length to compare to
   */
  public void maxDepth( FixedDimen x ) {

    if( depth.getLength().lt( x ) ) {
      depth.setLength( x );
    }
  }

  /**
   * Assign the maximum of the current value and a comparison value to the
   * height.
   *
   * @param x the length to compare to
   */
  public void maxHeight( FixedDimen x ) {

    if( height.getLength().lt( x ) ) {
      height.setLength( x );
    }
  }

  /**
   * Assign the maximum of the current value and a comparison value to the
   * width.
   *
   * @param x the length to compare to
   */
  public void maxWidth( FixedDimen x ) {

    if( width.getLength().lt( x ) ) {
      width.setLength( x );
    }
  }

  /**
   * Setter for the depth of the node.
   *
   * @param depth the node depth
   * @see org.extex.typesetter.type.Node#setDepth(org.extex.core.dimen.FixedDimen)
   */
  @Override
  public void setDepth( FixedDimen depth ) {

    this.depth.set( depth );
  }

  /**
   * Setter for the height of the node.
   *
   * @param height the new height
   * @see org.extex.typesetter.type.Node#setHeight(org.extex.core.dimen.FixedDimen)
   */
  @Override
  public void setHeight( FixedDimen height ) {

    this.height.set( height );
  }

  /**
   * Setter for the width of the node.
   *
   * @param width the new width
   * @see org.extex.typesetter.type.Node#setWidth(org.extex.core.dimen.FixedDimen)
   */
  @Override
  public void setWidth( FixedDimen width ) {

    this.width.set( width );
  }

  /**
   * Adjust the height of a flexible node. This method is a noop for any but
   * the flexible nodes.
   *
   * @param h   the desired height
   * @param sum the total sum of the glues
   * @see org.extex.typesetter.type.Node#spreadHeight(org.extex.core.dimen.FixedDimen,
   * FixedGlueComponent)
   */
  @Override
  public void spreadHeight( FixedDimen h, FixedGlueComponent sum ) {

    // noop
  }

  /**
   * Adjust the width of a flexible node. This method is a noop for any but
   * the flexible nodes.
   *
   * @param w   the desired width
   * @param sum the total sum of the glues
   * @see org.extex.typesetter.type.Node#spreadWidth(org.extex.core.dimen.FixedDimen,
   * org.extex.core.glue.FixedGlueComponent)
   */
  @Override
  public void spreadWidth( FixedDimen w, FixedGlueComponent sum ) {

    // noop
  }

  /**
   * This method returns the printable representation. This is meant to
   * produce a exhaustive form as it is used in tracing output to the log
   * file.
   *
   * @return the printable representation
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString( sb, "", Integer.MAX_VALUE, Integer.MAX_VALUE );
    return sb.toString();
  }

  /**
   * java.lang.String, int, int)
   */
  @Override
  public void toString( StringBuilder sb, String prefix, int breadth, int d ) {

    sb.append( getLocalizer().format( "String.Format" ) );
  }

  /**
   * Compute a text representation of this object.
   *
   * @param prefix the string prepended to each line of the resulting text
   * @return the text representation of this object
   */
  protected String toText( String prefix ) {

    StringBuilder sb = new StringBuilder();
    toText( sb, prefix );
    return sb.toString();
  }

  /**
   * Puts a text representation of the object into a string buffer.
   *
   * @param sb     the output string buffer
   * @param prefix the string prepended to each line of the resulting text
   * @see org.extex.typesetter.type.Node#toText(StringBuilder,
   * java.lang.String)
   */
  @Override
  public void toText( StringBuilder sb, String prefix ) {

    sb.append( getLocalizer().format( "Text.Format" ) );
  }
}
