/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.raw;

import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.MarkupContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.alphabet.CrossreferenceLocationClass;
import org.extex.exindex.core.type.alphabet.LocationClass;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.util.StringUtils;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class represents a raw cross reference.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CrossReference implements Reference {

  /**
   * The constant {@code LOCALIZER} contains the localizer for obtaining
   * messages.
   */
  private static final Localizer LOCALIZER =
      LocalizerFactory.getLocalizer( CrossReference.class );

  /**
   * The field {@code layers} contains the reference list.
   */
  private final String[] layers;

  /**
   * The field {@code clazz} contains the class name.
   */
  private final String clazz;

  /**
   * Creates a new object.
   *
   * @param clazz  the type
   * @param layers the references
   */
  public CrossReference( String clazz, String[] layers ) {

    this.layers = layers;
    this.clazz = clazz;
  }

  /**
   * java.util.logging.Logger,
   * org.extex.exindex.core.type.raw.RawIndexentry, StructuredIndex,
   * org.extex.exindex.core.type.LocationClassContainer, java.util.List,
   * org.extex.exindex.core.type.attribute.AttributesContainer)
   */
  public boolean check( Logger logger, RawIndexentry entry,
                        StructuredIndex index,
                        LocationClassContainer crossrefClass,
                        List<OpenLocationReference> openPages,
                        AttributesContainer attributes ) {

    String layer = entry.getRef().getLayer();
    LocationClass cc = crossrefClass.lookupLocationClass( layer );
    if( !(cc instanceof CrossreferenceLocationClass) ) {
      logger.warning( LOCALIZER.format( "UndefinedCrossrefClass", layer ) );
      return false;
    }
    if( ((CrossreferenceLocationClass) cc).isUnverified() ) {
      return true;
    }
    if( index.containsKey( layers ) != null ) {
      // TODO lift this to multiple indices
      logger.warning( LOCALIZER.format( "UndefinedCrossref", toString() ) );
      return false;
    }
    return true;
  }

  /**
   * Getter for keys.
   *
   * @return the keys
   */
  public String[] getKeys() {

    return layers;
  }

  public String getLayer() {

    return clazz;
  }

  public String[] getLocation() {

    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append( "(" );
    boolean first = true;
    for( String s : layers ) {
      if( first ) {
        first = false;
      }
      else {
        sb.append( " " );
      }
      StringUtils.putPrintable( sb, s );
      sb.append( "\"" );
    }
    sb.append( ")" );
    return sb.toString();
  }

  /**
   * Write the cross-reference to a writer.
   *
   * @param writer          the writer
   * @param interpreter     the interpreter
   * @param markupContainer the container for markup
   * @param trace           the trace indicator
   * @throws IOException               in case of an I/O error
   * @throws LNonMatchingTypeException in case of an error
   */
  public void write( Writer writer, LInterpreter interpreter,
                     MarkupContainer markupContainer, boolean trace )
      throws IOException,
      LNonMatchingTypeException {

    Markup markupCrossrefLayerList =
        markupContainer.getMarkup( "markup-crossref-layer-list" );
    Markup markupCrossrefLayer =
        markupContainer.getMarkup( "markup-crossref-layer" );

    markupCrossrefLayerList.write( writer, markupContainer, clazz,
                                   Markup.OPEN, trace );

    boolean first = true;

    for( String layer : layers ) {
      if( first ) {
        first = false;
      }
      else {
        markupCrossrefLayerList.write( writer, markupContainer, clazz,
                                       Markup.SEP, trace );
      }

      markupCrossrefLayer.write( writer, markupContainer, clazz,
                                 Markup.OPEN, trace );
      writer.write( layer );
      markupCrossrefLayer.write( writer, markupContainer, clazz,
                                 Markup.CLOSE, trace );
    }
    markupCrossrefLayerList.write( writer, markupContainer, clazz,
                                   Markup.CLOSE, trace );
  }

}
