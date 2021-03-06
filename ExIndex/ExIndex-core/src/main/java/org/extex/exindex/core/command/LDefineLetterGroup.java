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

import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.LetterGroup;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define a letter group.
 *
 *
 * <p>The Command {@code define-letter-group}</p>
 *
 * <p>
 * The command {@code define-letter-group} can be used to define a letter group
 * or modify the attributes of an already defined letter group.
 * </p>
 * <p>
 * A letter group is a collection of index entries for which the sort key starts
 * with one of the prefixes of the letter group.
 * </p>
 *
 * <pre>
 *  (define-letter-group <i>letter-group-name</i>
 *     [:prefixes <i>list-of-prefixes</i>]
 *     [:before <i>before-letter-group-name</i>]
 *     [:after <i>after-letter-group-name</i>]
 *  )   </pre>
 *
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 *
 * <pre>
 *  (define-letter-group "A")   </pre>
 *
 * <p>
 * Name is the first argument of the command. This argument is a string. In the
 * simplest form the name is used as prefix as well since no prefix is given.
 * Thus this command is equivalent to the following one:
 * </p>
 *
 * <pre>
 *  (define-letter-group "A" :prefixes ("A"))   </pre>
 *
 * <p>
 * The list-of-prefixes is a list of strings which denote the prefixes which are
 * mapped into this letter group. This means that the longest prefix which
 * matches at the beginning of the sort key determines the letter group to be
 * used. If no letter group is found then the letter group named
 * {@code default} is used.
 * </p>
 *
 * <pre>
 *  (define-letter-group "A" :before "B")   </pre>
 *
 * <p>
 * The parameter {@code :before} places a constraint on the order. This means
 * that the letter group A has to precede the letter group B in any case. There
 * many also be some other letter groups in between.
 * </p>
 *
 * <pre>
 *  (define-letter-group "B" :after "A")   </pre>
 *
 * <p>
 * The parameter {@code :after} places a constraint on the order. This means
 * that the letter group B has to follow the letter group A in any case. There
 * many again be some other letter groups in between.
 * </p>
 *
 * <p>
 * When the letter groups are sorted it may turn out that the constraints are
 * impossible to solve because a cyclic order is requested. This leads to an
 * error message. Thus watch out for cycles in the constraints.
 * </p>
 *
 * <p>
 * When the letter groups are sorted the constraints may nit be sufficient to
 * determine a unique linear order for the letter groups. In this case the
 * system makes some additional assumptions to determine a linear order. Beware,
 * the result is not well-defined &ndash; except that all constrains given are
 * honored.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @see LDefineLetterGroups
 */
public class LDefineLetterGroup extends LFunction {

  /**
   * The field {@code container} contains the the container to store the
   * information in.
   */
  private final IndexContainer container;

  /**
   * Creates a new object.
   *
   * @param name      the name of the function
   * @param container the container to store the information in
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public LDefineLetterGroup( String name, IndexContainer container )
      throws SecurityException,
      NoSuchMethodException {

    super( name, new Arg[]{Arg.STRING,
        Arg.OPT_STRING( ":before", null ),
        Arg.OPT_STRING( ":after", null ),
        Arg.OPT_LIST( ":prefixes" )} );
    this.container = container;
  }

  /**
   * Take a letter group and store it.
   *
   * @param interpreter the interpreter
   * @param name        the name of the letter group and its main prefix
   * @param before      optionally the name of a letter group going before
   *                    this one
   * @param after       optionally the name of a letter group going after
   *                    this one
   * @param prefixes    TBD
   * @return {@code null}
   * @throws LException                in case of an error
   * @throws LSettingConstantException should not happen
   */
  public LValue evaluate( LInterpreter interpreter, String name,
                          String before, String after, LList prefixes )
      throws LException,
      LSettingConstantException {

    LetterGroup g = null;

    if( prefixes == null || prefixes.isEmpty() ) {
      g = container.defineLetterGroup( name, name );
    }
    else {
      String[] sa = new String[ prefixes.size() ];
      int i = 0;
      for( LValue val : prefixes ) {
        sa[ i++ ] = LString.stringValue( val );
      }

      g = container.defineLetterGroup( name, sa );
    }

    if( after != null ) {
      LetterGroup ag = container.defineLetterGroup( after, after );
      g.after( ag );
    }
    if( before != null ) {
      LetterGroup bg = container.defineLetterGroup( before, before );
      bg.after( g );
    }

    return null;
  }

}
