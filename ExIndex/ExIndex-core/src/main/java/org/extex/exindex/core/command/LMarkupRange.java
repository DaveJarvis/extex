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

package org.extex.exindex.core.command;

import org.extex.exindex.core.command.type.LMarkup;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define the markup of a range.
 * 
 * <doc command="markup-range">
 * <h3>The Command <tt>markup-range</tt></h3>
 * 
 * <p>
 * The command <tt>markup-range</tt> can be used to specify the markup for
 * ranges.
 * </p>
 * 
 * <pre>
 *  (markup-range
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:sep <i>separator</i>]
 *     [:length <i>length</i>]
 *     [:ignore-end]
 *     [:class <i>class</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-range :open "\\begingroup " :close "\\endgroup ")   </pre>
 * 
 * <p>
 * </p>
 * 
 * <p>
 * For the following examples we consider the index entries shown next:
 * </p>
 * 
 * <pre>
 *  (indexentry :tkey (("Tree")) :locref "2")
 *  (indexentry :tkey (("Tree")) :locref "3")
 *  (indexentry :tkey (("Tree")) :locref "4")
 *  (indexentry :tkey (("Tree")) :locref "6")
 *  (indexentry :tkey (("Tree")) :locref "7")
 *  (indexentry :tkey (("Tree")) :locref "42" :open-range)
 *  (indexentry :tkey (("Tree")) :locref "43" :close-range) </pre>
 * 
 * <pre>
 *  (markup-range :sep "--")   </pre>
 * 
 * <p>
 * </p>
 * <table>
 * <tr>
 * <td>Tree &nbsp;&nbsp;&nbsp;</td>
 * <td>2, 3, 4, 6, 7, 42&ndash;43</td>
 * </tr>
 * </table>
 * 
 * <pre>
 *  (markup-range :sep "--" :length 2)   </pre>
 * 
 * <p>
 * </p>
 * <table>
 * <tr>
 * <td>Tree &nbsp;&nbsp;&nbsp;</td>
 * <td>2&ndash;4, 6, 7, 42&ndash;43</td>
 * </tr>
 * </table>
 * 
 * <pre>
 *  (markup-range :sep "--" :length 1)   </pre>
 * 
 * <p>
 * </p>
 * <table>
 * <tr>
 * <td>Tree &nbsp;&nbsp;&nbsp;</td>
 * <td>2&ndash;4, 6&ndash;7, 42&ndash;43</td>
 * </tr>
 * </table>
 * 
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. <tt>markup-range</tt>).
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupRange extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupRange(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_STRING(":open", ""), //
                Arg.OPT_STRING(":close", ""), //
                Arg.OPT_STRING(":sep", ""), //
                Arg.OPT_STRING(":class", ""), //
                Arg.OPT_LNUMBER(":length", new LNumber(0)), //
                Arg.OPT_LBOOLEAN(":ignore-end")});
    }

    /**
     * Take the markup for a range and store it.
     * 
     * @param interpreter the interpreter
     * @param open the open string
     * @param close the close string
     * @param sep the separator
     * @param clazz the class
     * @param length the length
     * @param ignoreEnd the indicator to ignore the end
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String open, String close,
            String sep, String clazz, LNumber length, LBoolean ignoreEnd)
            throws LSettingConstantException,
                LNonMatchingTypeException {

        LValue container = interpreter.get(getName());
        if (!(container instanceof LMarkup)) {
            throw new LNonMatchingTypeException(null);
        }

        LMarkup markup = (LMarkup) container;

        markup.set(clazz, open, close, sep);
        markup
            .setNumber(clazz, 0, length == null ? 0 : (int) length.getValue());
        markup.setNumber(clazz, 1, ignoreEnd == LBoolean.TRUE ? 1 : 0);

        return null;
    }
}
