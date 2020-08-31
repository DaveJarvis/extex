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
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define a crossref layer.
 *
 *
 * <p>The Command {@code markup-crossref-layer}</p>
 *
 * <p>
 * The command {@code markup-crossref-layer} can be used to specify the markup
 * for crossref layer.
 * </p>
 *
 * <pre>
 *  (markup-crossref-layer
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:sep <i>separator</i>]
 *     [:class <i>class</i>]
 *  )   </pre>
 *
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 *
 * <pre>
 *  (markup-crossref-layer :open "\\begingroup " :close "\\endgroup ")  </pre>
 * <p>
 * TODO documentation incomplete
 *
 *
 * <p>Parameters</p>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. {@code markup-crossref-layer}).
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LMarkupCrossrefLayer extends AbstractLAdapter {

  /**
   * Creates a new object.
   *
   * @param name      the name of the function
   * @param container the index container
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public LMarkupCrossrefLayer( String name, IndexContainer container )
      throws SecurityException,
      NoSuchMethodException {

    super( name, container, new Arg[]{Arg.OPT_STRING( ":open", "" ),
        Arg.OPT_STRING( ":close", "" ),
        Arg.OPT_STRING( ":class", null )} );
  }

  /**
   * Take the markup for a cross reference layer and store it.
   *
   * @param interpreter the interpreter
   * @param open        the open string
   * @param close       the close string
   * @param clazz       the class
   * @return {@code null}
   * @throws LSettingConstantException should not happen
   * @throws LNonMatchingTypeException in case of an error
   */
  public LValue evaluate( LInterpreter interpreter, String open, String close,
                          String clazz )
      throws LSettingConstantException,
      LNonMatchingTypeException {

    getMarkup( interpreter ).set( clazz, open, close );

    return null;
  }

}
