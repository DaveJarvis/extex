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

import org.extex.exindex.core.type.attribute.Attribute;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is the adapter for the L system to define attributes. It also serves as
 * a container for the attributes collected.
 * 
*
 * <p>The Command {@code define-attributes}</p>
 * 
 * <p>
 * The command {@code define-attributes} can be used to specify the markup for
 * attribute group lists.
 * </p>
 * 
 * <pre>
 *  (define-attributes
 *     <i>attribute-list</i>
 *  )  </pre>
 * 
 * <p>
 * The command has an argument which is a list of string or lists of strings.
 * The elements of the <i>attribute-list</i> are attribute groups. Those
 * attributes in a group are tied together in the output.
 * </p>
 * 
 * <pre>
 *  (define-attributes ("example"))   </pre>
 * 
 * <p>
 * If only a string is given then the attribute group consists of this element
 * only. Thus the example above is equivalent to the following form:
 * </p>
 * 
 * <pre>
 *  (define-attributes (("example")))   </pre>
 * 
 * <p>
 * An arbitrary mixture of both form can be given.
 * </p>
 * 
 * <pre>
 *  (define-attributes ("definition" ("example" "code")))   </pre>
 * 
 * <p>
 * The example above defines two attribute groups. The first one consists of the
 * attribute {@code definition} only. The second attribute group contains the
 * two elements {@code example} and {@code code}.
 * </p>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LDefineAttributes extends LFunction {

    /**
     * The field {@code org} contains the reference number for ordering the
     * attributes.
     */
    private int ord = 0;

    /**
     * The field {@code group} contains the index of the group.
     */
    private int group = 0;

    /**
     * The field {@code container} contains the container for attributes.
     */
    private final AttributesContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container for attributes
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineAttributes(String name, AttributesContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
        this.container = container;
    }

    /**
     * Take an attribute group and store it.
     * 
     * @param interpreter the interpreter
     * @param list the list of attributes
     * 
     * @return {@code nil}
     * 
     * @throws LNonMatchingTypeException is types are wrong
     * @throws LException in case of an error
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, LList list)
            throws LNonMatchingTypeException,
                LSettingConstantException,
                LException {

        String key;

        for (LValue val : list) {
            if (val instanceof LString) {
                key = ((LString) val).getValue();
                if (container.isAttributeDefined(key)) {
                    throw new LException(LocalizerFactory.getLocalizer(
                        getClass()).format("AlreadyDefined", key));
                }
                container.defineAttribute(key, new Attribute(key, ord++,
                    group++));
            } else if (val instanceof LList) {
                LList lst = (LList) val;

                for (LValue v : lst) {
                    key = LString.stringValue(v);
                    if (container.isAttributeDefined(key)) {
                        throw new LException(LocalizerFactory.getLocalizer(
                            getClass()).format("AlreadyDefined", key));
                    }
                    container.defineAttribute(key, new Attribute(key, ord++,
                        group));
                }
                group++;
            } else {
                throw new LNonMatchingTypeException("");
            }
        }
        return null;
    }

}
