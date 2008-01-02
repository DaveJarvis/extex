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

package org.extex.exindex.core.command;

import org.extex.exindex.core.exception.InconsistentFlagsException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define the markup for the index.
 * 
 * <doc command="markup-index">
 * <h3>The Command <tt>markup-index</tt></h3>
 * 
 * <p>
 * The command <tt>markup-index</tt> can be used to specify the markup for the
 * index as a whole.
 * </p>
 * 
 * <pre>
 *  (markup-index
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:flat | :tree | :hierdepth <i>depth</i>]
 *  )
 * </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-index :open "\\begin{theindex} " :close "\\end{theindex} ")
 * </pre>
 * 
 * <p>
 * The arguments <tt>:open</tt> and <tt>:close</tt> contain the markup to be
 * inserted at the beginning and at the end of the index.
 * </p>
 * 
 * <pre>
 *  (markup-index :open "\\begin{theindex} " :close "\\end{theindex} :flat ")
 * </pre>
 * 
 * <p>
 * The arguments <tt>:flat</tt>, <tt>:tree</tt>, and <tt>:hierdepth</tt>
 * are mutually exclusive. They determine how the index is structured. The
 * argument <tt>:flat</tt> suppresses any structuring. Keys of any depth are
 * combined into a single level. The result may look as follows:
 * </p>
 * <table>
 * <tr>
 * <td>structure </td>
 * <td>42&ndash;45</td>
 * </tr>
 * <tr>
 * <td>structure, tree </td>
 * <td align="right">42</td>
 * </tr>
 * <tr>
 * <td>structure, tree, depth </td>
 * <td align="right">43</td>
 * </tr>
 * <tr>
 * <td>structure, flat </td>
 * <td align="right">43</td>
 * </tr>
 * </table>
 * 
 * <pre>
 *  (markup-index :open "\\begin{theindex} " :close "\\end{theindex} :tree ")
 * </pre>
 * 
 * <p>
 * The argument <tt>:tree</tt> allows structuring to any depth. No keys are
 * combined into a single level. The result may look as follows:
 * </p>
 * <table>
 * <tr>
 * <td>structure </td>
 * <td>42&ndash;45</td>
 * </tr>
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;tree </td>
 * <td align="right">42</td>
 * </tr>
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depth </td>
 * <td align="right">43</td>
 * </tr>
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;flat </td>
 * <td align="right">43</td>
 * </tr>
 * </table>
 * 
 * <pre>
 *  (markup-index :open "\\begin{theindex} " :close "\\end{theindex} :hierdepth 1 ")
 * </pre>
 * 
 * <p>
 * The argument <tt>:hierdepth</tt> allows structuring to the depth given as
 * argument. The keys are combined into a single level if the given depth is
 * exceeded. The result may look as follows:
 * </p>
 * <table>
 * <tr>
 * <td>structure </td>
 * <td>42&ndash;45</td>
 * </tr>
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;tree </td>
 * <td align="right">42</td>
 * </tr>
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;tree, depth </td>
 * <td align="right">43</td>
 * </tr>
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;flat </td>
 * <td align="right">43</td>
 * </tr>
 * </table>
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. <tt>markup-index</tt>).
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupIndex extends AbstractLAdapter {

    /**
     * The field <tt>container</tt> contains the index.
     */
    private IndexContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the index
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupIndex(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, container, new Arg[]{Arg.OPT_STRING(":open", ""), //
                Arg.OPT_STRING(":close", ""), //
                Arg.OPT_BOOLEAN(":flat", Boolean.FALSE), //
                Arg.OPT_BOOLEAN(":tree", Boolean.FALSE), //
                Arg.OPT_LNUMBER(":hierdepth", null)});
        this.container = container;
    }

    /**
     * Take the markup for the index and store it.
     * 
     * @param interpreter the interpreter
     * @param open the open string
     * @param close the close string
     * @param flat the flat indicator
     * @param tree the tree indicator
     * @param hierdepth the depth limit
     * 
     * @return <tt>null</tt>
     * 
     * @throws InconsistentFlagsException in case of an error
     * @throws LSettingConstantException should not happen
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String open, String close,
            Boolean flat, Boolean tree, LNumber hierdepth)
            throws InconsistentFlagsException,
                LSettingConstantException,
                LNonMatchingTypeException {

        getMarkup(interpreter).setDefault(open, close);

        switch ((flat.booleanValue() ? 1 : 0) //
                | (tree.booleanValue() ? 1 : 0) //
                | (hierdepth != null ? 4 : 0)) {
            case 1:
                container.setDepth(0);
                break;
            case 0:
            case 2:
                container.setDepth(Integer.MAX_VALUE);
                break;
            case 3:
                throw new InconsistentFlagsException(null, ":flat", ":tree");
            case 4:
                container.setDepth((int) hierdepth.getValue());
                break;
            case 5:
                throw new InconsistentFlagsException(null, ":flat",
                    ":hierdepth");
            case 6:
            case 7:
                throw new InconsistentFlagsException(null, ":tree",
                    ":hierdepth");
        }

        return null;
    }

}
