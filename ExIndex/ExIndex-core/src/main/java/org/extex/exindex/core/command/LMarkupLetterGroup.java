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
import org.extex.exindex.core.type.markup.MarkupTransform;
import org.extex.exindex.core.type.transform.Capitalize;
import org.extex.exindex.core.type.transform.Downcase;
import org.extex.exindex.core.type.transform.Upcase;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a letter group.
 *
 *
 * <p>The Command {@code markup-letter-group}</p>
 *
 * <p>
 * The command {@code markup-letter-group} can be used to specify the markup
 * for letter group lists.
 * </p>
 *
 * <pre>
 *  (markup-letter-group
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:group <i>group-name</i>]
 *     [:open-head <i>open-head-markup</i>]
 *     [:close-head <i>close-head-markup</i>]
 *     [:upcase | :downcase | :capitalize]
 *     [:force-printing]
 *  )
 * </pre>
 *
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 *
 * <pre>
 *  (markup-letter-group :open "\\begingroup " :close "\\endgroup ")
 * </pre>
 * <p>
 * TODO documentation incomplete
 *
 *
 * <p>Parameters</p>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. {@code markup-letter-group}).
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LMarkupLetterGroup extends AbstractLAdapter {

  /**
   * Creates a new object.
   *
   * @param name      the name of the function
   * @param container the index container
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public LMarkupLetterGroup( String name, IndexContainer container )
      throws SecurityException,
      NoSuchMethodException {

    super( name, container, new Arg[]{Arg.OPT_STRING( ":group", null ),
        Arg.OPT_STRING( ":open", "" ),
        Arg.OPT_STRING( ":close", "" ),
        Arg.OPT_STRING( ":open-head", "" ),
        Arg.OPT_STRING( ":close-head", "" ),
        Arg.OPT_BOOLEAN( ":upcase", Boolean.FALSE ),
        Arg.OPT_BOOLEAN( ":downcase", Boolean.FALSE ),
        Arg.OPT_BOOLEAN( ":capitalize", Boolean.FALSE ),
        Arg.OPT_BOOLEAN( ":force-printing", Boolean.FALSE )} );
  }

  /**
   * Take the markup for a letter group and store it.
   *
   * @param interpreter the interpreter
   * @param group       the name of the group
   * @param open        the open string
   * @param close       the close string
   * @param openHead    the open head string
   * @param closeHead   the close head string
   * @param upcase      the indicator for the upcase transform
   * @param downcase    the indicator for the downcase transform
   * @param capitalize  the indicator for the capitalize transform
   * @param force       force the printing of the group even if the group
   *                    is empty
   * @return {@code null}
   * @throws LSettingConstantException  should not happen
   * @throws InconsistentFlagsException in case of an error
   * @throws LNonMatchingTypeException  in case of an error
   */
  public LValue evaluate( LInterpreter interpreter, String group, String open,
                          String close, String openHead, String closeHead,
                          Boolean upcase,
                          Boolean downcase, Boolean capitalize, Boolean force )
      throws LSettingConstantException,
      InconsistentFlagsException,
      LNonMatchingTypeException {

    MarkupTransform markup = (MarkupTransform) getMarkup( interpreter );
    markup.set( group, open, close, openHead, closeHead,
                force == Boolean.TRUE );

    switch( (upcase.booleanValue() ? 1 : 0)
        | (downcase.booleanValue() ? 2 : 0)
        | (capitalize.booleanValue() ? 4 : 0) ) {
      case 1:
        markup.setTransform( group, new Upcase() );
        break;
      case 2:
        markup.setTransform( group, new Downcase() );
        break;
      case 3:
        throw new InconsistentFlagsException( null, ":upcase",
                                              ":downcase" );
      case 4:
        markup.setTransform( group, new Capitalize() );
        break;
      case 5:
        throw new InconsistentFlagsException( null, ":upcase",
                                              ":capitalize" );
      case 6:
      case 7:
        throw new InconsistentFlagsException( null, ":capitalize",
                                              ":downcase" );
      default:
        // do nothing
    }

    return null;
  }

}
