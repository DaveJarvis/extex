/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.unit.LoaderFactory;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This primitive initiates the loading of native code and implements the
 * primitive {@code \nativeload}.
 * 
 * <p>The Primitive {@code \nativeload}</p>
 * <p>
 * The primitive {@code \nativeload} loads some native language code. As
 * parameter the type of the native extension and a specification of the loader
 * to be used are given.
 * </p>
 * <p>
 * With this method it is possible to load larger extensions of
 * εχTeX in one junk. There is no need to declare each single macro
 * with {@code \def}. It is even possible to define extension macros in other
 * programming languages than the TeX language.
 * </p>
 * 
 * <p>Syntax</p>
 * <p>
 * The general form of this primitive is
 * </p>
 * 
 * <pre class="syntax"> 
 *    &lang;nativeload&rang;
 *      &rarr; {@code \nativeload} &lang;type&rang; &lang;tokens&rang; </pre>
 * 
 * <p>
 * The {@code &lang;type&rang;} is any specification of a list of tokens
 * like a constant list enclosed in braces or a token register. The value of
 * these tokens are taken and resolved via the configuration.
 * </p>
 * <p>
 * The {@code &lang;tokens&rang;} is any specification of a list of tokens
 * like a constant list enclosed in braces or a tokens register. For the Java
 * loader the value of these tokens are taken and interpreted as the name of a
 * Java class. This class is loaded if needed, instantiated, and its method
 * {@linkplain org.extex.unit.dynamic.java.Loadable#init(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter)
 * init()} is invoked. The instantiation requires the empty constructor to be
 * visible.
 * </p>
 * 
 * <p>Examples</p>

 * <p>
 * The following example illustrates the use of this primitive:
 * </p>
 * 
 * <pre class="TeXSample">
 *   \nativeload{java}{my.unit.MyUnitLoader} </pre>
 * 
 * <p>
 * For the loading of the Java class it is necessary that this Java class
 * implements the interface {@link org.extex.unit.dynamic.java.Loadable
 * Loadable}.
 * </p>
 *
 * <pre class="JavaSample">
 * <b>package</b> my.package;
 * 
 * <b>import</b> org.extex.interpreter.context.Context;
 * <b>import</b> org.extex.interpreter.primitives.dynamic.java.Loadable;
 * <b>import</b> org.extex.typesetter.Typesetter;
 * <b>import</b> org.extex.util.exception.GeneralException;
 * 
 * <b>class</b> MyModule <b>implements</b> Loadable {
 * 
 *     <b>public</b> MyModule() {
 *         super();
 *         <i>// initialization code &ndash; if required</i>
 *     }
 * 
 *     <b>public void</b> init(<b>final</b> Context context, <b>final</b> Typesetter
 * typesetter )
 *         <b>throws</b> GeneralException {
 *             <i>// implement the initialization code here</i>
 *     }
 * } </pre>
 *
 * <p>Configuration</p>

 * <p>
 * The supported types are determined in the configuration of the unit which
 * defines the primitive. Here a mapping is specified assigning a binding class
 * for each supported type. Thus it is possible to configure in the support for
 * several extension types. Currently a binding for Java is provided. In the
 * future other languages can be added easily.
 * </p>
 * 
 * <pre class="configuration">
 * &lt;define name="nativeload"
 *         class="org.extex.interpreter.primitives.dynamic.NativeLoad"&gt;
 *   &lt;load name="java"
 *         class="org.extex.interpreter.primitives.dynamic.java.JavaLoad"/&gt;
 * &lt;/define&gt; </pre>
 * 
 * <p>
 * The body of the define tag for the primitive may contain an arbitrary number
 * of load sections. Each load has the attribute name and class. The attribute
 * name determines the type. This corresponds to the type given in the first
 * argument of the primitive invocation.
 * </p>
 * <p>
 * The class attribute names the class which provides the binding to the target
 * programming language.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NativeLoad extends AbstractCode
        implements
            Configurable,
            LogEnabled {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code logger} contains the logger to use.
     */
    private transient Logger logger = null;

    /**
     * The field {@code map} contains the mapping from a symbolic name to a
     * configuration.
     */
    private final Map<String, Configuration> map =
            new HashMap<String, Configuration>();

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public NativeLoad(CodeToken token) {

        super(token);
    }

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param config the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

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
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String name;
        try {
            name = source.getTokens(context, source, typesetter).toText();
        } catch (EofException e) {
            throw new EofInToksException(toText(context));
        }
        Configuration cfg = map.get(name);
        if (cfg == null) {
            throw new HelpingException(getLocalizer(), "UnknownType", name,
                toText());
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
