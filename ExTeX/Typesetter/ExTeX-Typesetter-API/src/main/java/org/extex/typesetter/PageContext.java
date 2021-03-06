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

package org.extex.typesetter;

import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.muskip.Muskip;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.Font;

/**
 * This interface describes the possibilities of the typesetter to access its
 * options.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface PageContext {

  /**
   * Getter for a count register.
   *
   * @param name the name of the register
   * @return the content of the count register
   */
  FixedCount getCountOption( String name );

  /**
   * Getter for a dimen register.
   *
   * @param name the name of the register
   * @return the content of the dimen register
   */
  FixedDimen getDimenOption( String name );

  /**
   * Getter for a current font register.
   *
   * @param name the name or the number of the register
   * @return the named font register or {@code null} if none is set
   */
  Font getFont( String name );

  /**
   * Getter for a glue register.
   *
   * @param name the name of the register
   * @return the content of the glue register
   */
  FixedGlue getGlueOption( String name );

  /**
   * Getter for the lccode mapping of upper case characters to their
   * lower case equivalent.
   *
   * @param uc the upper case character
   * @return the lower case equivalent or null if none exists
   */
  UnicodeChar getLccode( UnicodeChar uc );

  /**
   * Getter for a muskip register.
   *
   * @param name the name of the register
   * @return the muskip register value
   */
  Muskip getMuskip( String name );

  /**
   * Getter for the current name space.
   *
   * @return the current name space
   */
  String getNamespace();

  /**
   * Getter for an output file descriptor.
   *
   * @param key the name or the number of the file register
   * @return the output file descriptor
   */
  OutFile getOutFile( String key );

  /**
   * Getter for the paragraph shape.
   *
   * @return the paragraph shape or {@code null} if no special shape
   * is present
   */
  ParagraphShape getParshape();

  /**
   * Getter for the token factory. The token factory can be used to get new
   * tokens of some kind.
   *
   * @return the token factory
   */
  TokenFactory getTokenFactory();

  /**
   * Getter for the typesetting context.
   *
   * @return the typesetting context
   */
  TypesettingContext getTypesettingContext();

  /**
   * Getter for the typesetting context factory.
   *
   * @return the typesetting context factory
   */
  TypesettingContextFactory getTypesettingContextFactory();

  /**
   * Setter for a count register.
   *
   * @param name  the name of the register
   * @param value the value
   * @throws GeneralException in case of an error
   */
  void setCountOption( String name, long value ) throws GeneralException;

  /**
   * Setter for a mark.
   * The information for first mark and top mark are updated if necessary.
   *
   * @param index the name of the mark
   * @param mark  the vale of the mark
   */
  void setMark( Object index, Tokens mark );

  /**
   * Setter for a outfile descriptor.
   *
   * @param key    the name or the number of the file register
   * @param file   the descriptor of the output file
   * @param global the indicator for the scope; {@code true} means all
   *               groups; otherwise the current group is affected only
   */
  void setOutFile( String key, OutFile file, boolean global );

  /**
   * Setter for the paragraph shape.
   *
   * @param shape the new paragraph shape
   */
  void setParshape( ParagraphShape shape );

}
