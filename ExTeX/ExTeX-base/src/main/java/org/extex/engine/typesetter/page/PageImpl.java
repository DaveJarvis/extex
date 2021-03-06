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

package org.extex.engine.typesetter.page;

import org.extex.color.Color;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.page.Page;

/**
 * This class provides a transport object for pages. Beside the nodes it
 * contains additional administrative information.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PageImpl implements Page {

  /**
   * The field {@code background} contains the background color.
   */
  private Color background = null;

  /**
   * The field {@code mediaHeight} contains the height of the paper.
   */
  private final Dimen mediaHeight = new Dimen(
      Dimen.ONE_INCH.getValue() * 2970 / 254 );

  /**
   * The field {@code mediaHOffset} contains the horizontal offset of the
   * upper left corner of the contents on the page. In TeX this value is 
   * defined to be 1 in.
   */
  private final Dimen mediaHOffset = new Dimen( Dimen.ONE_INCH );

  /**
   * The field {@code mediaVOffset} contains the vertical offset of the upper
   * left corner of the contents on the page. In TeX this value is defined to
   * be 1 in.
   */
  private final Dimen mediaVOffset = new Dimen( Dimen.ONE_INCH );

  /**
   * The field {@code mediaWidth} contains the width of the physical paper.
   */
  private final Dimen mediaWidth =
      new Dimen( Dimen.ONE_INCH.getValue() * 2100 / 254 );

  /**
   * The field {@code nodes} contains the nodes describing the objects on the
   * page.
   */
  private final NodeList nodes;

  /**
   * The field {@code pageNo} contains the array of page number indicators.
   */
  private final FixedCount[] pageNo;

  /**
   * Creates a new object.
   *
   * @param nodes  the nodes to transport
   * @param pageNo the array of page numbers
   */
  public PageImpl( NodeList nodes, FixedCount[] pageNo ) {

    if( nodes == null ) {
      throw new IllegalArgumentException( "nodes" );
    }
    this.nodes = nodes.copy();
    this.pageNo = pageNo;
  }

  /**
   * Getter for the background color.
   *
   * @return the background color
   * @see org.extex.typesetter.type.page.Page#getColor()
   */
  public Color getColor() {

    return background;
  }

  /**
   * Getter for the height of the media.
   *
   * @return the height of the media
   * @see org.extex.typesetter.type.page.Page#getMediaHeight()
   */
  public Dimen getMediaHeight() {

    return mediaHeight;
  }

  /**
   * Getter for the horizontal offset of the media.
   *
   * @return the horizontal offset of the media
   * @see org.extex.typesetter.type.page.Page#getMediaHOffset()
   */
  public Dimen getMediaHOffset() {

    return mediaHOffset;
  }

  /**
   * Getter for the vertical offset of the media.
   *
   * @return the vertical offset of the media
   * @see org.extex.typesetter.type.page.Page#getMediaVOffset()
   */
  public Dimen getMediaVOffset() {

    return mediaVOffset;
  }

  /**
   * Getter for the width of the media.
   *
   * @return the width of the media
   * @see org.extex.typesetter.type.page.Page#getMediaWidth()
   */
  public Dimen getMediaWidth() {

    return mediaWidth;
  }

  /**
   * Getter for nodes.
   *
   * @return the nodes
   */
  public NodeList getNodes() {

    return this.nodes;
  }

  /**
   * Getter for the array of page numbers.
   *
   * @return the array of page numbers
   * @see org.extex.typesetter.type.page.Page#getPageNo()
   */
  public FixedCount[] getPageNo() {

    return pageNo;
  }

  /**
   * Setter for the background color.
   *
   * @param bg the background color
   * @see org.extex.typesetter.type.page.Page#setColor(org.extex.color.Color)
   */
  public void setColor( Color bg ) {

    this.background = bg;
  }

  /**
   * Setter for mediaHeight.
   *
   * @param mediaHeight the mediaHeight to set
   */
  public void setMediaHeight( Dimen mediaHeight ) {

    mediaHeight.set( mediaHeight );
  }

  /**
   * Setter for the horizontal offset of the media.
   *
   * @param offset the media horizontal offset
   * @see org.extex.typesetter.type.page.Page#setMediaHOffset(org.extex.core.dimen.Dimen)
   */
  public void setMediaHOffset( Dimen offset ) {

    mediaHOffset.set( offset );
  }

  /**
   * Setter for the vertical offset of the media.
   *
   * @param offset the media vertical offset
   * @see org.extex.typesetter.type.page.Page#setMediaVOffset(org.extex.core.dimen.Dimen)
   */
  public void setMediaVOffset( Dimen offset ) {

    mediaVOffset.set( offset );
  }

  /**
   * Setter for media width.
   *
   * @param width the mediaWidth to set
   * @see org.extex.typesetter.type.page.Page#setMediaWidth(org.extex.core.dimen.Dimen)
   */
  public void setMediaWidth( Dimen width ) {

    mediaWidth.set( width );
  }

  /**
   * Get the string representation of this object for debugging purposes.
   *
   * @return the string representation
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return nodes.toString();
  }

}
