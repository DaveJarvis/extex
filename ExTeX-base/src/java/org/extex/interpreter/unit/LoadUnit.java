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

package org.extex.interpreter.unit;

import java.util.Iterator;
import java.util.logging.Logger;

import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.UnicodeChar;
import org.extex.core.exception.GeneralException;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.max.StringSource;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.InitializableCode;
import org.extex.interpreter.type.OutputStreamConsumer;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.macro.LetCode;

/**
 * This is a factory load to units from a configuration.
 * A unit is a configuration consisting of an optional setup class and a set of
 * primitives. When the unit is loaded the setup class is instantiated and run.
 * Then the primitives are created and registered in the context.
 *
 * <pre>
 *  &lt;unit name="the name"
 *        class="the.setup.Class"&gt;
 *    &lt;primitive&gt;
 *      &lt;define name="<i>name</i>" class="<i>class</i>"/&gt;
 *      &lt;define name="<i>name</i>" class="<i>class</i>"&gt;<i>value</i>&lt;/define&gt;
 *      &lt;define name="<i>name</i>" class="<i>class</i>"/&gt;
 *    &lt;/primitive&gt;
 *  &lt;/unit&gt;
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public final class LoadUnit extends AbstractFactory {

    /**
     * The field <tt>DEFINE_TAG</tt> contains the tag name used to find
     * definitions for primitives.
     */
    private static final String DEFINE_TAG = "define";

    /**
     * The constant <tt>NAME_ATTRIBUTE</tt> contains the name of the attribute
     * holding the name of the primitive to define.
     */
    private static final String NAME_ATTRIBUTE = "name";

    /**
     * The field <tt>NAMESPACE_ATTRIBUTE</tt> contains the attribute name
     * to find the name space for the new primitive.
     */
    private static final String NAMESPACE_ATTRIBUTE = "namespace";

    /**
     * Prepare the primitives according to their configuration. The given
     * configuration may contain sub-configurations with the name
     * <tt>primitives</tt> which includes the definition of primitives. Those
     * primitives are defined no matter if they are already defined or not.
     *
     * @param configuration the configuration
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param logger the logger to use
     * @param outputFactory the output stream factory
     *
     * @throws ConfigurationException in case of a configuration error
     * @throws GeneralException in case of an error
     */
    public static void loadUnit(Configuration configuration,
            Context context, TokenSource source,
            Typesetter typesetter, Logger logger,
            OutputStreamFactory outputFactory) throws GeneralException {

        TokenFactory tokenFactory = context.getTokenFactory();
        LoadUnit primitiveFactory = new LoadUnit();

        String name = configuration.getAttribute(NAME_ATTRIBUTE);
        if (name == null) {
            name = "?";
        }
        UnitInfo unitInfo;

        if (configuration.getAttribute(CLASS_ATTRIBUTE) != null) {
            UnitInfoFactory factory = new UnitInfoFactory();
            factory.enableLogging(logger);
            factory.configure(configuration);
            unitInfo = factory.createUnitInfo();
            unitInfo.setName(name);
        } else {
            unitInfo = new UnitInfo(name);
        }
        context.addUnit(unitInfo);

        if (unitInfo instanceof Loader) {
            ((Loader) unitInfo).load(context, source, typesetter);
        }

        Iterator iterator = configuration.iterator("primitives");
        while (iterator.hasNext()) {
            primitiveFactory.define((Configuration) iterator.next(),
                tokenFactory, context, typesetter, logger, outputFactory);
        }

        iterator = configuration.iterator("import");
        while (iterator.hasNext()) {
            String ns =
                    ((Configuration) iterator.next()).getAttribute("namespace");
            Tokens export = context.getToks(ns + "\bexport");
            String namespace = context.getNamespace();
            int length = export.length();

            for (int i = 0; i < length; i++) {
                Token t = export.get(i);
                if (t instanceof CodeToken) {
                    if (context.getCode((CodeToken) t) == null) {
                        throw new HelpingException(LocalizerFactory
                            .getLocalizer(LoadUnit.class),
                            "Loader.Import.undef", t.toString());
                    }
                    context.setCode(
                        ((CodeToken) t).cloneInNamespace(namespace),
                        (t instanceof CodeToken //
                                ? context.getCode((CodeToken) t)
                                : new LetCode(t)), //
                        false);
                }
            }
        }

        if (unitInfo instanceof StartableUnit) {
            ((StartableUnit) unitInfo).start(context, source, typesetter);
        }
    }

    /**
     * The field <tt>stringSource</tt> contains the reused object for string
     * parsing.
     */
    private StringSource stringSource = new StringSource();

    /**
     * Creates a new object.
     */
    private LoadUnit() {

        super();
    };

    /**
     * Scan a configuration and define the primitives found.
     *
     * @param configuration the configuration to scan
     * @param tokenFactory the token factory to use
     * @param context the interpreter context to register the primitive in
     * @param typesetter the typesetter
     * @param outputLogger the logger to produce output to
     * @param outputFactory the factory for new output streams
     *
     * @throws GeneralException In case of an error
     * @throws ConfigurationException in case of an error
     * <ul>
     *  <li>ConfigurationMissingAttributeException
     *    in case of a missing argument</li>
     *  <li>ConfigurationInstantiationException
     *    in case of an error during instantiation</li>
     *  <li>ConfigurationClassNotFoundException
     *    in case of a missing class</li>
     *  <li>ConfigurationWrapperException
     *    in case of another error which is wrapped</li>
     * </ul>
     */
    public void define(Configuration configuration,
            TokenFactory tokenFactory, Context context,
            Typesetter typesetter, Logger outputLogger,
            OutputStreamFactory outputFactory) throws GeneralException {

        enableLogging(outputLogger);
        UnicodeChar esc = UnicodeChar.get('\\');
        Iterator iterator = configuration.iterator(DEFINE_TAG);

        while (iterator.hasNext()) {
            Configuration cfg = (Configuration) iterator.next();

            String name = cfg.getAttribute(NAME_ATTRIBUTE);
            Code code =
                    (Code) createInstanceForConfiguration(cfg, Code.class, name);

            String namespace = cfg.getAttribute(NAMESPACE_ATTRIBUTE);
            if (namespace == null) {
                namespace = Namespace.DEFAULT_NAMESPACE;
            }

            context.setCode((CodeToken) tokenFactory.createToken(
                Catcode.ESCAPE, esc, name, namespace), code, true);
            if (code instanceof InitializableCode) {

                stringSource.reset(cfg.getValue());
                ((InitializableCode) code).init(context, stringSource,
                    typesetter);
            }
            if (code instanceof OutputStreamConsumer) {
                ((OutputStreamConsumer) code)
                    .setOutputStreamFactory(outputFactory);
            }
        }
    }

}
