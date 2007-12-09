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

package org.extex.exindex.core.xindy;

import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.xindy.type.Attribute;
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
 * This is the adapter for the L system to define attributes.
 * 
 * <pre>
 *  (define-attributes attribute-list)
 * </pre>
 * 
 * It also serves as a container for the attributes collected.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineAttributes extends LFunction {

    /**
     * The field <tt>map</tt> contains the attributes.
     */
    private Map<String, Attribute> map = new HashMap<String, Attribute>();

    /**
     * The field <tt>org</tt> contains the reference number for ordering the
     * attributes.
     */
    private int ord = 0;

    /**
     * The field <tt>group</tt> contains the index of the group.
     */
    private int group = 0;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineAttributes(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param list the list of attributes
     * 
     * @return <tt>nil</tt>
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
                if (map.containsKey(key)) {
                    throw new LException(LocalizerFactory.getLocalizer(
                        getClass()).format("AlreadyDefined", key));
                }
                map.put(key, new Attribute(key, ord++, group++));
            } else if (val instanceof LList) {
                LList lst = (LList) val;

                for (LValue v : lst) {
                    key = LString.getString(v);
                    if (map.containsKey(key)) {
                        throw new LException(LocalizerFactory.getLocalizer(
                            getClass()).format("AlreadyDefined", key));
                    }
                    map.put(key, new Attribute(key, ord++, group));
                }
                group++;
            } else {
                throw new LNonMatchingTypeException("");
            }
        }
        return null;
    }

    /**
     * Getter for a named attribute
     * 
     * @param attibute the name of the attribute
     * 
     * @return the named attribute or <code>null</code> if not defined
     */
    public Attribute lookup(String attibute) {

        return map.get(attibute);
    }
}
