/*
 * Copyright (C) 2005-2008 The ExTeX Group and individual authors listed below
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
import java.util.Map;
import java.util.logging.Logger;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This primitive provides a binding of a macro or active character to code in
 * some programming language. This code implements the primitive
 * <tt>\nativedef</tt>.
 * 
 * <doc name="nativedef"> <h3>The Primitive <tt>\nativedef</tt></h3>
 * <p>
 * The primitive <tt>\nativedef</tt> assigns a definition to a macro or active
 * character. This is done in a similar way as <tt>\def</tt> works. The
 * difference is that the definition has to be provided in form of a Java class
 * which glues in native code.
 * </p>
 * 
 * <h4>Syntax</h4> The general form of this primitive is
 * 
 * <pre class="syntax">
 *    &lang;nativedef&rang;
 *      &rarr; <tt>\nativedef</tt> &lang;type&rang; {@linkplain org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 * &lang;control sequence&rang;} &lang;name&rang; </pre>
 *
 * <p>
 * The <code>&lang;type&rang;</code> is any specification of a list of tokens
 * like a constant list enclosed in braces or a token register. The value of
 * these tokens are taken and resolved via the configuration. This appropriate
 * class is loaded if needed and instantiated. The instance is bound as code to
 * the <i>&lang;control sequence&rang;</i>.
 * </p>
 * <p>
 * The <code>&lang;control sequence&rang;</code> is any macro or active character.
 * If this token is missing or of the wrong type then an error is raised.
 * </p>
 * <p>
 * The <code>&lang;name&rang;</code> is any specification of a list of tokens
 * like a constant list enclosed in braces or a token register. The value of
 * these tokens are passed to the binding class to specify the target. For
 * instance the Java binding requires this to be name of the Java class
 * implementing the functionality.
 * </p>
 * <p>
 * The primitive <tt>\nativedef</tt> is local to the enclosing group as is
 * <tt>\def</tt>. And similar to <tt>\def</tt> the modifier <tt>\global</tt> can
 * be used to make the definition in all groups instead of the current group
 * only.
 * </p>
 * <p>
 * The primitive <tt>\nativedef</tt> also respects the count register
 * <tt>\globaldefs</tt> to enable general global assignment.
 * </p>
 * <p>
 * Since the primitive is classified as assignment the value of
 * <tt>\afterassignment</tt> is applied.
 * </p>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample"> 
 *   \nativedef{java}\x{my.primitive.MyPrimitive} </pre>
 * 
 * <p>
 * This example shows how the control sequence <tt>\x</tt> is bound to the Java
 * class <tt>my.primitive.MyPrimitive</tt>. This definition is local to the
 * current group.
 * </p>
 * 
 * <pre class="TeXSample"> 
 *   \global\nativedef{java}\x{my.primitive.MyPrimitive}
 * </pre>
 * 
 * <p>
 * This example shows how the control sequence <tt>\x</tt> is bound to the Java
 * class <tt>my.primitive.MyPrimitive</tt>. This definition is performed
 * globally.
 * </p>
 * 
 * <h4>Configuration</h4>
 * <p>
 * The supported types are determined in the configuration of the unit which
 * defines the primitive. Here a mapping is specified assigning a binding class
 * for each supported type. Thus it is possible to configure in the support for
 * several extension types. Currently a binding for Java is provided. In the
 * future other languages can be added easily.
 * </p>
 * 
 * <pre class="configuration"> 
 *   &lt;define name="nativedef"
 *           class="org.extex.interpreter.primitives.dynamic.NativeDef"&gt;
 *     &lt;load name="java"
 *           class="org.extex.interpreter.primitives.dynamic.java.JavaDef"/&gt;
 *   &lt;/define&gt; </pre>
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
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NativeDef extends AbstractAssignment
        implements
            Configurable,
            LogEnabled {

    /**
     * This inner class provides access to the functionality of an abstract
     * factory. It is here to overcome the deficiency of a missing multiple
     * inheritance in Java.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    protected static class Factory extends AbstractFactory {

        /**
         * Create a new instance of the class given by the attribute
         * <tt>class</tt> of the configuration.
         * 
         * @return the Code loaded
         * @throws ConfigurationException in case of an error
         */
        public Definer createLoad() throws ConfigurationException {

            return (Definer) createInstance(Definer.class);
        }
    }

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

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
     * @param token the initial token for the primitive
     */
    public NativeDef(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        String name;
        try {
            name = source.getTokens(context, source, typesetter).toText();
            Configuration cfg = map.get(name);
            if (cfg == null) {
                throw new HelpingException(getLocalizer(), "UnknownType", name,
                    toText());
            }

            Factory factory = new Factory();
            factory.enableLogging(logger);
            factory.configure(cfg);
            factory.createLoad().define(prefix, context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(toText());
        }
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

        for (Configuration cfg : config) {
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
     * Getter for logger.
     * 
     * @return the logger
     */
    protected Logger getLogger() {

        return this.logger;
    }

}