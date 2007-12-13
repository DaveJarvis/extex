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

import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.page.VarPage;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is the adapter for the L system to define a location class.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineLocationClass extends LFunction {

    /**
     * The constant <tt>SEP</tt> contains the tag :sep.
     */
    private static final LSymbol SEP = LSymbol.get(":sep");

    /**
     * The field <tt>map</tt> contains the mapping from names to page
     * references.
     */
    private Map<String, PageReference> map =
            new HashMap<String, PageReference>();

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineLocationClass(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.QLIST,
                Arg.OPT_NUMBER(":min-range-length"),
                Arg.OPT_NUMBER(":hierdepth"), //
                Arg.OPT_BOOLEAN(":var")});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the location class
     * @param layerList
     * @param minRangeLength
     * @param hierdepth
     * @param var
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String name,
            LList layerList, Long minRangeLength, Long hierdepth, Boolean var)
            throws LException {

        PageReference pr;

        if (!var.booleanValue()) {
            if (layerList.size() != 1) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("NonVarLengthMismatch"));
            }
            String s = LString.getString(layerList.get(0));
            pr = map.get(s);
            if (pr == null) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("UnknownClass", s));
            }

        } else if (minRangeLength.longValue() != 0) {
            throw new LException(LocalizerFactory.getLocalizer(getClass())
                .format("VarAndMinRange"));
        } else {
            VarPage varPage = new VarPage();
            pr = varPage;
            boolean sep = false;
            for (LValue val : layerList) {

                if (val == SEP) {
                    if (sep) {
                        throw new LException(LocalizerFactory.getLocalizer(
                            getClass()).format("DoubleSep"));
                    }
                    sep = true;
                } else {
                    String s = LString.getString(val);
                    if (sep) {
                        varPage.add(s);
                    } else {
                        PageReference p = map.get(s);
                        if (s == null) {
                            throw new LException(LocalizerFactory.getLocalizer(
                                getClass()).format("UnknownClass", s));
                        }
                        varPage.add(p);
                    }
                }
            }
            if (sep) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("MissingArg"));
            }
        }

        map.put(name, pr);

        return LList.NIL;
    }

    /**
     * Get a named location class.
     * 
     * @param name the name
     * 
     * @return the location class or <code>null</code>
     */
    public PageReference lookup(String name) {

        return map.get(name);
    }

}
