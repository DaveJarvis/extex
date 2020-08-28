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

import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.alphabet.LocationClass;
import org.extex.exindex.core.type.alphabet.VarLocationClass;
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
 * <doc type="exindex-command" command="define-location-class">
 * 
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
 * letters; i.e. the characters 'a' to 'z'.</dd>
 * <dt>ALPHA</dt>
 * <dd>This location class consists of a non-empty sequence of uppercase
 * letters; i.e. the characters 'A' to 'Z'.</dd>
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
public class LDefineLocationClass extends LFunction {

    /**
     * The constant <tt>SEP</tt> contains the tag :sep.
     */
    private static final LSymbol SEP = LSymbol.get(":sep");

    /**
     * The field <tt>container</tt> contains the container for location classes.
     */
    private LocationClassContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container to store the information in
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineLocationClass(String name, LocationClassContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.QLIST,
                Arg.OPT_NUMBER(":min-range-length", Integer.valueOf(0)),
                Arg.OPT_NUMBER(":hierdepth", Integer.valueOf(0)),
                Arg.OPT_BOOLEAN(":var", Boolean.FALSE)});
        this.container = container;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the location class
     * @param layerList the list of layers
     * @param minRangeLength the minimum length of a range
     * @param hierdepth the hierarchy depth
     * @param var the var indicator
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String name,
            LList layerList, Integer minRangeLength, Integer hierdepth,
            Boolean var) throws LException {

        LocationClass lc;

        if (!var.booleanValue()) {
            if (layerList.size() != 1) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("NonVarLengthMismatch"));
            }
            String s = LString.stringValue(layerList.get(0));
            lc = container.lookupLocationClass(s);
            if (lc == null) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("UnknownClass", s));
            }

        } else if (minRangeLength.longValue() != 0) {
            throw new LException(LocalizerFactory.getLocalizer(getClass())
                .format("VarAndMinRange"));
        } else {
            VarLocationClass varPage = new VarLocationClass();
            lc = varPage;
            boolean sep = false;
            for (LValue val : layerList) {

                if (val == SEP) {
                    if (sep) {
                        throw new LException(LocalizerFactory.getLocalizer(
                            getClass()).format("DoubleSep"));
                    }
                    sep = true;
                } else {
                    String s = LString.stringValue(val);
                    if (sep) {
                        varPage.add(s);
                    } else {
                        LocationClass p = container.lookupLocationClass(s);
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

        container.addLocationClass(name, lc);
        return null;
    }

}
