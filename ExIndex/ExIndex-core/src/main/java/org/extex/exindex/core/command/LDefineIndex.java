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
import org.extex.exindex.core.exception.IndexAliasLoopException;
import org.extex.exindex.core.exception.UnknownIndexException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define an index.
 * 
 * <doc type="exindex-command" command="define-index">
 * 
 * <h3>The Command <tt>define-index</tt></h3>
 * 
 * <p>
 * The command <tt>define-index</tt> can be used to define an index. Initially
 * only the default index with the name "" exists. Any entries directed to
 * another index are silently discarded.
 * </p>
 * 
 * <pre>
 *  (define-index
 *     <i>index-name</i>
 *     [:drop | :merge-to <i>index-name</i>]
 *     [:suffix <i>suffix</i>]
 *  )  </pre>
 * 
 * <p>
 * The command has as first argument the name of the index. The default index is
 * represented by the empty string ("").
 * </p>
 * 
 * <pre>
 *  (define-index "" :drop)  </pre>
 * 
 * <p>
 * The example above drops the default index. This might be useful when the
 * default index is not used for writing but just as a container for the
 * fallback parameters.
 * </p>
 * 
 * <pre>
 *  (define-index "abc" :merge-to "")  </pre>
 * 
 * <p>
 * The example above merges the index <tt>abc</tt> to the default index &ndash;
 * named with the empty name. This means that all entries sent to the index
 * <tt>abc</tt> end up in the default index.
 * </p>
 * 
 * <pre>
 *  (define-index "" :merge-to "default")  </pre>
 * 
 * <p>
 * This trick can be used to free the default index from all index entries and
 * send them to the index named <tt>default</tt>. Thus only fallback settings
 * remain in the index with the empty name.
 * </p>
 * 
 * <pre>
 *  (define-index "abc" :suffix ".ind")  </pre>
 * 
 * <p>
 * The example above declares the suffix for the the index <tt>abc</tt> to be
 * <tt>.ind</tt>. The default behavior is to use the name of the index preceded
 * by a period as suffix.
 * </p>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6728 $
 */
public class LDefineIndex extends AbstractLAdapter {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the index container
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineIndex(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, container, new Arg[]{Arg.LSTRING, //
                Arg.OPT_BOOLEAN(":drop", null), //
                Arg.OPT_STRING(":merge-to", null), //
                Arg.OPT_STRING(":suffix", null)});
    }

    /**
     * Take an index specification and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the index
     * @param drop the indicator to drop the index
     * @param mergeTo the name of the index to send the entries to
     * @param suffix the optional suffix
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException in case an argument is not a String
     * @throws LSettingConstantException should not happen
     * @throws UnknownIndexException in case of an undefined index mapping
     * @throws IndexAliasLoopException in case of a potential loop in aliases
     * @throws InconsistentFlagsException in case of inconsistent flags
     */
    public LValue evaluate(LInterpreter interpreter, LString name,
            Boolean drop, String mergeTo, String suffix)
            throws LNonMatchingTypeException,
                LSettingConstantException,
                UnknownIndexException,
                IndexAliasLoopException,
                InconsistentFlagsException {

        String indexName = name.getValue();
        StructuredIndex index =
                getContainer().defineIndex(indexName,
                    suffix == null ? "." + indexName : suffix);

        switch (((drop != null ? 1 : 0) | (mergeTo != null ? 2 : 0))) {
            case 0:
                break;
            case 1:
                index.setDropped(drop == Boolean.TRUE);
                break;
            case 2:
                mergeTo(mergeTo, index);
                break;
            case 3:
                throw new InconsistentFlagsException(null, ":drop", ":merge-to");
        }

        return null;
    }

    /**
     * Merge to an index.
     * 
     * @param mergeTo the name of the index to merge it to
     * @param index the index to map
     * 
     * @throws UnknownIndexException in case of an unknown index
     * @throws IndexAliasLoopException in case of a potential loop in aliases
     */
    private void mergeTo(String mergeTo, StructuredIndex index)
            throws UnknownIndexException,
                IndexAliasLoopException {

        StructuredIndex alias = getContainer().get(mergeTo);
        if (alias == null) {
            throw new UnknownIndexException(null, mergeTo);
        }
        int size = getContainer().getSize();

        while (index != alias && size-- > 0) {

            StructuredIndex a = alias.getAlias();
            if (a == null) {
                index.setAlias(alias);
                return;
            }
            alias = a;
        }
        throw new IndexAliasLoopException(null, mergeTo);
    }
}
