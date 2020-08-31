/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.core.command;

import org.extex.exindex.core.exception.InconsistentFlagsException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.rules.RegexRule;
import org.extex.exindex.core.type.rules.StringRule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a merge rule.
 *
 *
 * <p>The Command {@code merge-rule}</p>
 *
 * <p>
 * The command {@code merge-rule} can be used to add a merge rule.
 * </p>
 *
 * <pre>
 *  (merge-rule <i>pattern</i> <i>replacement</i>
 *     [:again]
 *     [:string | :bregexp | :eregexp]
 *  )
 * </pre>
 *
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 *
 * <pre>
 *  (merge-rule "abc" "ABC")
 * </pre>
 * <p>
 * TODO documentation incomplete
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LMergeRule extends LFunction {

  /**
   * The field {@code STRING} contains the bit mask for the type :string.
   */
  private static final int STRING = 1;

  /**
   * The field {@code BREGEX} contains the bit mask for the type :bregex.
   */
  private static final int BREGEX = 2;

  /**
   * The field {@code EREGEX} contains the bit mask for the type :eregex.
   */
  private static final int EREGEX = 4;

  /**
   * The field {@code container} contains the container for indices.
   */
  private final IndexContainer container;

  /**
   * Creates a new object.
   *
   * @param name      the name of the function
   * @param container the container to store the information
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public LMergeRule( String name, IndexContainer container )
      throws SecurityException,
      NoSuchMethodException {

    super( name, new Arg[]{Arg.STRING, Arg.STRING,
        Arg.OPT_BOOLEAN( ":again", Boolean.FALSE ),
        Arg.OPT_BOOLEAN( ":string", Boolean.FALSE ),
        Arg.OPT_BOOLEAN( ":bregexp", Boolean.FALSE ),
        Arg.OPT_BOOLEAN( ":eregexp", Boolean.FALSE )} );
    this.container = container;
  }

  // /**
  // * Apply all rules in the rule set to a string.
  // *
  // * @param in the input string
  // *
  // * @return the transformed string
  // */
  // public String apply(String in) {

  // StringBuilder sb = new StringBuilder(in);

  // for (int i = 0; i < sb.length();) {
  // i = apply(sb, i);
  // }

  // return sb.toString();
  // }

  // /**
  // * Apply all rules at a certain position.
  // *
  // * @param sb the target string builder
  // * @param start the staring index
  // *
  // * @return the next index
  // */
  // private int apply(StringBuilder sb, int start) {

  // for (Rule r : rules) {
  // int next = r.apply(sb, start);
  // if (next < 0) {
  // // try next rule
  // } else {
  // return next;
  // }
  // }
  // return start + 1;
  // }

  /**
   * Take a sort rule and store it.
   *
   * @param interpreter the interpreter
   * @param pattern     the pattern
   * @param replacement the replacement text
   * @param string      the indicator for string type rules
   * @param bregexp     the indicator for bregexp type rules
   * @param eregexp     the indicator for eregexp type rules
   * @param again       the optional indicator to restart the replacement 
   *                    cycle from
   *                    start
   * @return {@code null}
   * @throws LSettingConstantException  should not happen
   * @throws InconsistentFlagsException in case of an error
   */
  public LValue evaluate( LInterpreter interpreter, String pattern,
                          String replacement, Boolean string, Boolean bregexp,
                          Boolean eregexp, Boolean again )
      throws LSettingConstantException,
      InconsistentFlagsException {

    int t = 0;
    if( string != null && string.booleanValue() ) {
      t |= STRING;
    }
    else if( bregexp != null && bregexp.booleanValue() ) {
      t |= BREGEX;
    }
    else if( eregexp != null && eregexp.booleanValue() ) {
      t |= EREGEX;
    }
    if( t == 0 ) {
      if( pattern.matches( "[\\\\.*+[]]" ) ) {
        t = EREGEX;
      }
      else {
        t = STRING;
      }
    }

    switch( t ) {
      case STRING:
        container.addMergeRule( new StringRule( pattern, replacement,
                                                again.booleanValue() ) );
        break;
      case EREGEX:
        container.addMergeRule( new RegexRule( pattern, replacement,
                                               again.booleanValue() ) );
      case BREGEX:
        container.addMergeRule( new RegexRule( pattern, replacement,
                                               again.booleanValue() ) );
      case STRING | EREGEX:
        throw new InconsistentFlagsException( null, ":string",
                                              ":eregexp" );
      case STRING | BREGEX:
        throw new InconsistentFlagsException( null, ":string",
                                              ":bregexp" );
      case EREGEX | BREGEX:
      case STRING | EREGEX | BREGEX:
        throw new InconsistentFlagsException( null, ":eregexp",
                                              ":bregexp" );
      default:
        // that's all
    }

    return null;
  }

}
