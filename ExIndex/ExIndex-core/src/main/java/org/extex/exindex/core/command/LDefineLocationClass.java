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
 *  (define-location-class
 *     <i>location-class-name</i>
 *     <i>layer-list</i>
 *     [:min-range-length <i>number</i>]
 *     [:hierdepth <i>depth</i>]
 *     [:var]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (define-location-class "pages" ("arabic-numbers"))   </pre>
 * 
 * <p>
 * Two arguments are mandatory. The first mandatory argument is the name of the
 * location class. It has a string value. In the example above the name is
 * <tt>pages</tt>
 * </p>
 * 
 * <p>
 * The second mandatory argument is a layer list. A layer list is a list of
 * location classes and separator specifications.
 * </p>
 * 
 * TODO documentation incomplete
 * 
 * <p>
 * The following build-in location classes exist.
 * </p>
 * <dl>
 * <dt>arabic-numbers</dt>
 * <dd>This location class consists of a non-empty sequence of digits.</dd>
 * <dt>alpha</dt>
 * <dd>This location class consists of a non-empty sequence of lowercase
 * letters; i.e. the characters 'a' to 'z'. </dd>
 * <dt>ALPHA</dt>
 * <dd>This location class consists of a non-empty sequence of uppercase
 * letters; i.e. the characters 'A' to 'Z'. </dd>
 * <dt>digits</dt>
 * <dd>This location class consists of the single digits; i.e. the characters
 * '0' to '9'.</dd>
 * <dt>roman-numbers-uppercase</dt>
 * <dd>This location class consists of a roman number made up of uppercase
 * letters. In the range of Unicode letters some special number symbols for
 * roman letters are defined as well. They are considered as well-</dd>
 * <dt>roman-numbers-lowercase</dt>
 * <dd>This location class consists of a roman number made up of lowercase
 * letters. In the range of Unicode letters some special number symbols for
 * roman letters are defined as well. They are considered as well-</dd>
 * </dl>
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
     * The field <tt>classes</tt> contains the list of classes already
     * defined.
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
     * @see org.extex.exindex.core.command.type.LocationClassContainer#lookup(
     *      java.lang.String)
     */
    public LocationClass lookup(String name) {

        return map.get(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.command.type.LocationClassContainer#makePageReference(
     *      java.lang.String, java.lang.String)
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

    /**
     * Order the location classes according to the given list. The other
     * location classes follow in the original order.
     * 
     * @param list the list of location class names
     * 
     * @throws LException in case that a location class name is not defined
     */
    public void order(String[] list) throws LException {

        List<LocationClass> newClasses = new ArrayList<LocationClass>();

        for (String s : list) {
            LocationClass lc = map.get(s);
            int i = classes.indexOf(lc);
            if (i < 0) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("UnknownClass", s));
            }
            newClasses.add(lc);
            classes.remove(i);
        }

        newClasses.addAll(classes);
        classes = newClasses;
    }

}
