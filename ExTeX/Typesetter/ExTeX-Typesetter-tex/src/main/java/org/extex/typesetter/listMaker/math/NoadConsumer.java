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

package org.extex.typesetter.listMaker.math;

import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.muskip.Mudimen;
import org.extex.core.muskip.Muskip;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;
import org.extex.typesetter.type.noad.Noad;

/**
 * This interface describes list makers which are able to consume a Noad.
 * This is usually the case for math list makers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface NoadConsumer extends ListMaker {

  /**
   * Add a mathematical glyph.
   *
   * @param mc the math code
   * @param tc the typesetting context
   * @throws TypesetterException in case of an error
   */
  void add( MathCode mc, TypesettingContext tc ) throws TypesetterException;

  /**
   * Add a mathematical delimiter.
   *
   * @param del the delimiter
   * @param tc  the typesetting context
   * @throws TypesetterException in case of an error
   */
  void add( MathDelimiter del, TypesettingContext tc )
      throws TypesetterException;

  /**
   * Add some math glue Noad to the internal list.
   *
   * @param glue the glue to add
   * @throws TypesetterException in case of an error
   */
  void add( Muskip glue ) throws TypesetterException;

  /**
   * Add some math dimen Noad to the internal list.
   *
   * @param skip the length to add
   * @throws TypesetterException in case of an error
   */
  void add( Mudimen skip ) throws TypesetterException;

  /**
   * Add an arbitrary Noad to the internal list if it is prepared to hold one.
   * This is usually the case in math modes.
   *
   * @param noad the noad to add
   * @throws TypesetterException in case of an error
   */
  void add( Noad noad ) throws TypesetterException;

  /**
   * Get access to the previous noad.
   *
   * @return the previous noad or {@code null} if there is none
   * @throws TypesetterException in case of an error
   */
  Noad getLastNoad() throws TypesetterException;

  /**
   * Open the group for a \left-\right construction.
   *
   * @param delimiter the delimiter to typeset on theleft side
   * @throws TypesetterException in case of an error
   */
  void left( MathDelimiter delimiter ) throws TypesetterException;

  /**
   * Middle in the group for a \left-\right construction.
   *
   * @param delimiter the delimiter to typeset here
   * @throws TypesetterException in case of an error
   */
  void middle( MathDelimiter delimiter ) throws TypesetterException;

  /**
   * Close the group for a {@code \left}-{@code \right} construction.
   *
   * @param delimiter the delimiter to typeset on the right side
   * @throws TypesetterException in case of an error
   */
  void right( MathDelimiter delimiter ) throws TypesetterException;

  /**
   * Process the input until a Noad is completed. A Noad is either a single
   * Noad or a list of Noads resulting from the processing of a block.
   *
   * @param flags      the flags to restore after processing
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @param primitive  the name of the primitive for error messages
   * @param groupType  the group type in case that a group needs to be opened
   * @return the Noad read or {@code null} if none could be gathered
   * @throws TypesetterException in case of an error
   * @throws HelpingException    in case of an error
   */
  Noad scanNoad( Flags flags, Context context, TokenSource source,
                 Typesetter typesetter, Token primitive, GroupType groupType )
      throws TypesetterException, HelpingException;

  /**
   * This method instructs the implementing class to use a fraction
   * construction. The math list collected so far is integrated into the
   * fraction noad.
   *
   * @param leftDelimiter  the left delimiter or {@code null} if none
   *                       should be used.
   * @param rightDelimiter the right delimiter or {@code null} if none
   *                       should be used.
   * @param ruleWidth      th width of the rule or {@code null} to indicate
   *                       that the default width should be used
   * @param tc             the typesetting context
   * @throws TypesetterException in case of an error
   */
  void switchToFraction( MathDelimiter leftDelimiter,
                         MathDelimiter rightDelimiter, FixedDimen ruleWidth,
                         TypesettingContext tc ) throws TypesetterException;

}
