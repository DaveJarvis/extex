/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.dynamic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.unit.LoaderFactory;
import org.extex.typesetter.Typesetter;

/**
 * This primitive initiates the loading of native code and implements the
 * primitive <tt>\nativeload</tt>.
 *
 * <doc name="nativeload">
 * <h3>The Primitive <tt>\nativeload</tt></h3>
 * <p>
 *  The primitive <tt>\nativeload</tt> loads some native language code. As
 *  parameter the type of the native extension and a specification of the
 *  loader to be used are given.
 * </p>
 * <p>
 *  With this method it is possible to load
 *  larger extensions of <logo>ExTeX</logo> in one junk. There is no need to
 *  declare each single macro with <tt>\def</tt>. It is even possible to
 *  define extension macros in other programming languages than the
 *  <logo>TeX</logo> language.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The general form of this primitive is
 * <pre class="syntax">
 *   &lang;nativeload&rang;
 *       &rarr; <tt>\nativeload</tt> &lang;type&rang; &lang;tokens&rang; </pre>
 * <p>
 *  The <tt>&lang;type&rang;</tt> is any specification of a list of
 *  tokens like a constant list enclosed in braces or a token register.
 *  The value of these tokens are taken and resolved via the configuration.
 * </p>
 * <p>
 *  The <tt>&lang;tokens&rang;</tt> is any specification of a list of
 *  tokens like a constant list enclosed in braces or a tokens register.
 *  For the Java loader the value of these tokens are taken and interpreted as
 *  the name of a Java class. This class is loaded if needed, instantiated,
 *  and its method
 *  {@linkplain org.extex.unit.dynamic.java.Loadable#init(
 *    org.extex.interpreter.context.Context,
 *    org.extex.typesetter.Typesetter) init()}
 *  is invoked. The instantiation requires the empty constructor to be visible.
 * </p>
 *
 * <h4>Examples</h4>
 * <p>
 *  The following example illustrates the use of this primitive:
 *  <pre class="TeXSample">
 *    \nativeload{java}{my.unit.MyUnitLoader}  </pre>
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
 *
 * <h4>Configuration</h4>
 * <p>
 *  The supported types are determined in the configuration of the unit which
 *  defines the primitive. Here a
 *  mapping is specified assigning a binding class for each supported type.
 *  Thus it is possible to configure in the support for several extension types.
 *  Currently a binding for Java is provided. In the future other languages can
 *  be added easily.
 * </p>
 *
 * <pre class="Configuration">
 *  &lt;define name="nativeload"
 *          class="org.extex.interpreter.primitives.dynamic.NativeLoad"&gt;
 *    &lt;load name="java"
 *          class="org.extex.interpreter.primitives.dynamic.java.JavaLoad"/&gt;
 *  &lt;/define&gt;
 * </pre>
 *
 * <p>
 *  The body of the define tag for the primitive may contain an arbitrary number
 *  of load sections. Each load has the attribute name and class. The attribute
 *  name determines the type. This corresponds to the type given in the first
 *  argument of the primitive invocation.
 * </p>
 * <p>
 *  The class attribute names the class which provides the binding to the
 *  target programming language.
 * </p>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NativeLoad extends AbstractCode
        implements
            Configurable,
            LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 04022007L;

    /**
     * The field <tt>logger</tt> contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * The field <tt>map</tt> contains the mapping from a symbolic name to a
     * configuration.
     */
    private Map<String, Configuration> map =
            new HashMap<String, Configuration>();

    /**
     * Creates a new object.
     *
     * @param codeName the name of the primitive
     */
    public NativeLoad(String codeName) {

        super(codeName);
    }

    /**
     * Configure an object according to a given Configuration.
     *
     * @param config the configuration object to consider
     *
     * @throws ConfigurationException in case that something went wrong
     *
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config)
            throws ConfigurationException {

        Iterator<Configuration> iterator = config.iterator("load");
        while (iterator.hasNext()) {
            Configuration cfg = iterator.next();
            map.put(cfg.getAttribute("name"), cfg);
        }
    }

    /**
     * Setter for the logger.
     *
     * @param log the logger to use
     *
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
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
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *       org.extex.interpreter.Flags,
     *       org.extex.interpreter.context.Context,
     *       org.extex.interpreter.TokenSource,
     *       org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException, ConfigurationException {

        String name;
        try {
            name = source.getTokens(context, source, typesetter).toText();
        } catch (EofException e) {
            throw new EofInToksException(printableControlSequence(context));
        }
        Configuration cfg = map.get(name);
        if (cfg == null) {
            throw new HelpingException(getLocalizer(), "UnknownType", name,
                getName());
        }

        LoaderFactory factory = new LoaderFactory();
        factory.enableLogging(logger);
        factory.configure(cfg);
        factory.createLoad().load(context, source, typesetter);
    }

    /**
     * Getter for logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {

        return this.logger;
    }

}
