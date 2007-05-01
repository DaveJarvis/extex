/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.dynamic.java;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.unit.Loader;
import org.extex.typesetter.Typesetter;

/**
 * This primitive initiates the loading of Java code and implements the
 * primitive <tt>\javaload</tt>.
 *
 * <doc name="javaload">
 * <h3>The Primitive <tt>\javaload</tt></h3>
 * <p>
 *  The primitive <tt>\javaload</tt> loads a java class and invokes its
 *  <tt>init()</tt> method. With this method it is possible to load
 *  larger extensions of <logo>ExTeX</logo> in one junk. There is no need to
 *  declare each single macro with <tt>\javadef</tt>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The general form of this primitive is
 * <pre class="syntax">
 *   &lang;javaload&rang;
 *       &rarr; <tt>\javaload</tt> &lang;tokens&rang;  </pre>
 * <p>
 *  The <i>&lang;tokens&rang;</i> is any specification of a list of
 *  tokens like a constant list enclosed in braces or a token register.
 *  The value of these tokens are taken and interpreted as the name of
 *  a Java class. This class is loaded if needed, instantiated, and its
 *  method
 *  {@link org.extex.unit.dynamic.java.Loadable#init(org.extex.interpreter.context.Context,org.extex.typesetter.Typesetter) init()}
 *  is invoked. The instantiation requires the empty constructor to be visible.
 * </p>
 *
 * <h4>Examples</h4>
 * <p>
 *  The following example illustrates the use of this primitive:
 * <pre class="TeXSample">
 *   \javaload{org.extex.extensions.Basic} </pre>
 * </p>
 * <p>
 *  For the loading of the Java class it is necessary that this Java
 *  class implements the interface
 *  {@link org.extex.unit.dynamic.java.Loadable Loadable}.
 * <pre class="JavaSample">
 *   <b>package</b> my.package;
 *
 *   <b>import</b> org.extex.interpreter.contect.Context;
 *   <b>import</b> org.extex.interpreter.primitives.dynamic.java.Loadable;
 *   <b>import</b> org.extex.typesetter.Typesetter;
 *   <b>import</b> org.extex.util.exception.GeneralException;
 *
 *   <b>class</b> MyModule <b>implements</b> Loadable {
 *
 *     <b>public</b> MyModule() {
 *       super();
 *       <i>// initialization code &ndash; if required</i>
 *     }
 *
 *     <b>public void</b> init(<b>final</b> Context context,
 *                      <b>final</b> Typesetter typesetter
 *                     ) <b>throws</b> GeneralException {
 *       <i>// implement the initialization code here</i>
 *     }
 *   } </pre>
 * </p>
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class JavaLoad extends AbstractCode implements Loader {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * This method is needed for the nativeload wrapper.
     *
     */
    public JavaLoad() {

        super("");
    }

    /**
     * Creates a new object.
     *
     * @param codeName the name for debugging
     */
    public JavaLoad(String codeName) {

        super(codeName);
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        load(context, source, typesetter);
    }

    /**
     * Perform a load operation.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.unit.Loader#load(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void load(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        String classname;
        try {
            classname = source.getTokens(context, source, typesetter).toText();
        } catch (EofException e) {
            throw new EofInToksException(printableControlSequence(context));
        }
        if ("".equals(classname)) {
            throw new HelpingException(getLocalizer(), "ClassNotFound",
                    classname);
        }
        Loadable component;

        try {
            component = (Loadable) (Class.forName(classname).newInstance());
            component.init(context, typesetter);
        } catch (ClassNotFoundException e) {
            throw new HelpingException(getLocalizer(), "ClassNotFound",
                    classname);
        } catch (InterpreterException e) {
            throw e;
        } catch (ClassCastException e) {
            throw new HelpingException(getLocalizer(), "ClassCast", classname,
                    Loadable.class.getName(), getName());
        } catch (Exception e) {
            throw new InterpreterException(e);
        }
    }

}
