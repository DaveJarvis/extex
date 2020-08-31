/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.rules;

/**
 * This Rule applies a regular expression. The Java means for regular
 * expressions are used.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class RegexRule extends Rule {

  /**
   * Creates a new object.
   *
   * @param pattern     the pattern
   * @param replacement the replacement text
   * @param again       the indicator for repetition
   */
  public RegexRule( String pattern, String replacement, boolean again ) {

    super( pattern, replacement, again );
  }

  @Override
  public int apply( StringBuilder word, int index ) {

    // TODO gene: apply unimplemented
    throw new RuntimeException( "unimplemented" );
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append( "#Rule(\"" );
    sb.append( getPattern() );
    sb.append( "\" \"" );
    sb.append( getReplacement() );
    sb.append( "\"" );
    if( isAgain() ) {
      sb.append( " :again" );
    }
    sb.append( " :regex)" );
    return sb.toString();
  }

}
