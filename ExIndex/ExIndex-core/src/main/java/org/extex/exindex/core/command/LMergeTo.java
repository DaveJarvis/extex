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

import org.extex.exindex.core.exception.UnknownAttributeException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.attribute.Attribute;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a rule set.
 *
 *
 * <p>The Command {@code merge-to}</p>
 *
 * <p>
 * The command {@code merge-to} can be used to add a merge rule. The merge rule
 * applies to the current index.
 * </p>
 *
 * <pre>
 *  (merge-to
 *     <i>from-attribute</i>
 *     <i>to-attribute</i>
 *     [:drop]
 *  )   </pre>
 *
 * <p>
 * The command has some arguments which are described in turn.
 * </p>
 *
 * <pre>
 *  (merge-to "abc" "ABC")   </pre>
 *
 * <p>
 * The first argument is a string with the name of the attribute to be mapped.
 * The second argument can be a string containing the name of the attribute with
 * which it should be merged.
 * </p>
 *
 * <pre>
 *  (merge-to "abc" "" :drop)   </pre>
 *
 * <p>
 * If the flag {@code :drop} is given then the attribute will be dropped.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LMergeTo extends LFunction {

  /**
   * The field {@code container} contains the container for indices.
   */
  private final IndexContainer container;

  /**
   * Creates a new object.
   *
   * @param name      the name of the function
   * @param container the container for indices
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public LMergeTo( String name, IndexContainer container )
      throws SecurityException,
      NoSuchMethodException {

    super( name, new Arg[]{Arg.LSTRING, Arg.LSTRING,
        Arg.OPT_LBOOLEAN( ":drop" )} );
    this.container = container;
  }

  /**
   * Take a sort rule and store it.
   *
   * @param interpreter the interpreter
   * @param from        the attribute from which to merge
   * @param to          the attribute to which to merge
   * @param drop        the drop indicator
   * @return {@code null}
   * @throws LSettingConstantException should not happen
   * @throws UnknownAttributeException in case that the attribute is unknown
   */
  public LValue evaluate( LInterpreter interpreter, LString from, LString to,
                          LBoolean drop )
      throws LSettingConstantException,
      UnknownAttributeException {

    String fromString = from.getValue();
    String toString = to.getValue();
    Attribute ai = container.lookupAttribute( fromString );
    if( ai == null ) {
      throw new UnknownAttributeException( null, fromString );
    }
    ai.setDrop( drop == LBoolean.TRUE );
    ai.setAlias( toString );
    return null;
  }

}
