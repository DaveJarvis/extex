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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.core.command.type.LocationClassContainer;
import org.extex.exindex.core.type.alphabet.LocationClass;
import org.extex.exindex.core.type.alphabet.VarLocationClass;
import org.extex.exindex.core.type.page.PageReference;
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
 * <doc command="define-location-class">
 * <h3>The Command <tt>define-location-class</tt></h3>
 * 
 * <p>
 * The command <tt>define-location-class</tt> can be used to define a location
 * class.
 * </p>
 * 
 * <pre>
 *  (define-letter-group <i>location-class-name</i> <i>layer-list</i>
 *     [:min-range-length <i>number</i>]
 *     [:hierdepth <i>depth</i>]
 *     [:var]
 *  )
 * </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (define-location-class "pages" ("arabic-numbers"))
 * </pre>
 * 
 * TODO documentation incomplete
 * 
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineLocationClass extends LFunction
        implements
            LocationClassContainer {

    /**
     * The constant <tt>SEP</tt> contains the tag :sep.
     */
    private static final LSymbol SEP = LSymbol.get(":sep");

    /**
     * The field <tt>map</tt> contains the mapping from names to page
     * references.
     */
    private Map<String, LocationClass> map =
            new HashMap<String, LocationClass>();

    /**
     * The field <tt>classes</tt> contains the ...
     */
    private List<LocationClass> classes = new ArrayList<LocationClass>();

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
     * Add a location class to the ones defined. An already defined location
     * class for the same key is silently overwritten.
     * 
     * @param key the key
     * @param value the value
     */
    public void add(String key, LocationClass value) {

        map.put(key, value);
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

        LocationClass pr;

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
            VarLocationClass varPage = new VarLocationClass();
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
                        LocationClass p = map.get(s);
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
        classes.add(pr);

        return LList.NIL;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.LocationClassContainer#lookup(java.lang.String)
     */
    public LocationClass lookup(String name) {

        return map.get(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.LocationClassContainer#makePageReference(java.lang.String,
     *      java.lang.String)
     */
    public PageReference makePageReference(String encap, String s) {

        for (LocationClass lc : classes) {
            PageReference pr = lc.match(encap, s);
            if (pr != null) {
                return pr;
            }
        }
        return null;
    }

}
