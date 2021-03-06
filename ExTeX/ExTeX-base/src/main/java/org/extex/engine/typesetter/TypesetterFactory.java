/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
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

package org.extex.engine.typesetter;

import org.extex.backend.BackendDriver;
import org.extex.engine.typesetter.page.PageFactoryImpl;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This is the factory for instances of {@link org.extex.typesetter.Typesetter
 * Typesetter}. This factory inherits its properties from the
 * {@link org.extex.framework.AbstractFactory AbstractFactory}. Among them the
 * support for configuration and logging.
 * <p>
 * Configuration
 *
 * <p>
 * Mainly the configuration needs to specify which class to use for the
 * Typesetter. The configuration provides a mapping from a type name to the
 * sub-configuration to be used. The name of the class is given as the argument
 * {@code class} of the sub-configuration as shown below.
 * </p>
 *
 * <pre>
 *   &lt;Typesetter default="TeX"&gt;
 *     &lt;TeX class="org.extex.typesetter.impl.TypesetterImpl"
 *          direction="lr"&gt;
 *       &lt;PageBuilder class="org.extex.typesetter.pageBuilder.impl.PageBuilderImpl"/&gt;
 *       &lt;ParagraphBuilder class="org.extex.typesetter.paragraphBuilder.texImpl.TeXParagraphBuilder"&gt;
 *         &lt;Hyphenator class="org.extex.typesetter.paragraphBuilder.texImpl.TeXParagraphBuilder"/&gt;
 *       &lt;/ParagraphBuilder&gt;
 *       &lt;NodeFactory class="org.extex.typesetter.type.node.factory.CachingNodeFactory"/&gt;
 *     &lt;/TeX&gt;
 *     &lt;develop class="org.extex.typesetter.impl.TypesetterImpl"
 *            direction="lr"&gt;
 *       &lt;PageBuilder class="org.extex.typesetter.pageBuilder.impl.PageBuilderImpl"/&gt;
 *       &lt;ParagraphBuilder class="org.extex.typesetter.(new JUnitCore())"/&gt;
 *       &lt;NodeFactory class="org.extex.typesetter.type.node.factory.CachingUnicodeNodeFactory"/&gt;
 *     &lt;/develop&gt;
 *   &lt;/Typesetter&gt;
 *  </pre>
 *
 * <p>
 * The named class need to implement the interface
 * {@link org.extex.typesetter.Typesetter Typesetter}. If this interface is not
 * implemented an error is raised.
 * </p>
 * <p>
 * The configuration is passed down to the new instance if it implements the
 * interface {@link org.extex.framework.configuration.Configurable Configurable}
 * .
 * </p>
 * <p>
 * If the class implements the interface
 * {@link org.extex.framework.logger.LogEnabled LogEnabled} then a logger is
 * passed to the new instance. For this purpose the factory itself is log
 * enabled to receive the logger.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TypesetterFactory extends AbstractFactory<Object> {


  public TypesetterFactory() {

  }

  /**
   * Make the node factory according to the specification.
   *
   * @param config the configuration to use
   * @return the node factory
   * @throws ConfigurationException in case of an configuration error
   */
  private NodeFactory makeNodeFactory( Configuration config )
      throws ConfigurationException {

    Configuration cfg = config.getConfiguration( "NodeFactory" );
    NodeFactory nodeFactory =
        (NodeFactory) createInstanceForConfiguration( cfg,
                                                      NodeFactory.class );
    if( nodeFactory instanceof LogEnabled ) {
      ((LogEnabled) nodeFactory).enableLogging( getLogger() );
    }
    if( nodeFactory instanceof Configurable ) {
      ((Configurable) nodeFactory).configure( cfg );
    }
    return nodeFactory;
  }

  /**
   * Make a new page builder according to the specification in the
   * configuration. The sub-configuration {@code PageBuilder} is used to
   * determine the requested properties.
   *
   * @param config     the configuration to use
   * @param context    the interpreter context
   * @param typesetter the typesetter associated with it
   * @return a new instance
   * @throws ConfigurationException in case of an configuration error
   * @throws TypesetterException    in case of an error
   */
  private PageBuilder makePageBuilder( Configuration config, Context context,
                                       Typesetter typesetter )
      throws TypesetterException {

    Configuration cfg = config.getConfiguration( "PageBuilder" );
    PageBuilder pageBuilder =
        (PageBuilder) createInstanceForConfiguration( cfg,
                                                      PageBuilder.class );
    pageBuilder.setContext( context );
    PageFactoryImpl pageFactory = new PageFactoryImpl();
    pageFactory.enableLogging( getLogger() );
    pageBuilder.setPageFactory( pageFactory );
    return pageBuilder;
  }

  /**
   * Make a new paragraph builder according to the specification in the
   * configuration. The sub-configuration {@code ParagraphBuilder} is
   * used to determine the requested properties.
   *
   * @param config      the configuration to use
   * @param options     the typesetter options
   * @param nodeFactory the node factory
   * @return the new instance
   * @throws ConfigurationException in case of an configuration error
   */
  private ParagraphBuilder makeParagraphBuilder( Configuration config,
                                                 TypesetterOptions options,
                                                 NodeFactory nodeFactory ) {

    Configuration cfg = config.getConfiguration( "ParagraphBuilder" );
    ParagraphBuilder builder =
        (ParagraphBuilder) createInstanceForConfiguration( cfg,
                                                           ParagraphBuilder.class );
    /*
     * if (builder instanceof HyphenationEnabled) { cfg =
     * cfg.getConfiguration("Hyphenator"); Hyphenator hyphenator =
     * (Hyphenator) createInstanceForConfiguration( cfg, Hyphenator.class);
     * ((HyphenationEnabled) builder).enableHyphenation(hyphenator); }
     */
    builder.setOptions( options );
    builder.setNodefactory( nodeFactory );
    return builder;
  }

  /**
   * Get an instance of a typesetter.
   *
   * @param type        the symbolic name of the configuration to use
   * @param context     the interpreter context
   * @param backend     the back-end driver
   * @param interpreter the interpreter
   * @return a new typesetter
   * @throws ConfigurationException in case of an configuration error
   * @throws TypesetterException    in case of another error
   */
  public Typesetter newInstance( String type, Context context,
                                 BackendDriver backend,
                                 Interpreter interpreter )
      throws TypesetterException {

    Configuration cfg = selectConfiguration( type );

    Typesetter typesetter =
        (Typesetter) createInstance( type, Typesetter.class );
    NodeFactory nodeFactory = makeNodeFactory( cfg );
    typesetter.setNodeFactory( nodeFactory );

    typesetter.setParagraphBuilder( makeParagraphBuilder( cfg,
                                                          (TypesetterOptions) context,
                                                          nodeFactory ) );

    typesetter.setPageBuilder( makePageBuilder( cfg, context, typesetter ) );

    typesetter.setOptions( (TypesetterOptions) context );
    typesetter.setBackend( backend );

    OutputRoutineFactory outputRoutineFactory = new OutputRoutineFactory();
    outputRoutineFactory.configure( cfg.getConfiguration( "OutputRoutine" ) );
    outputRoutineFactory.enableLogging( getLogger() );
    typesetter.setOutputRoutine(
        outputRoutineFactory.newInstance( interpreter ) );

    return typesetter;
  }

}
