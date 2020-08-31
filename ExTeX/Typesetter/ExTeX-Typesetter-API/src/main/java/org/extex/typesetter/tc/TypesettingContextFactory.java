/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tc;

import org.extex.color.Color;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.NullFont;

/**
 * This class provides a factory for a
 * {@link org.extex.typesetter.tc.TypesettingContext TypesettingContext}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TypesettingContextFactory extends AbstractFactory<Object> {

  /**
   * The constant {@code CLASS_ATTRIBUTE} contains the name of the attribute
   * used to extract the class name from the given configuration.
   */
  private static final String CLASS_ATTRIBUTE = "class";

  /**
   * The field {@code languageManager} contains the language manager.
   */
  private LanguageManager languageManager = null;

  /**
   * The field {@code theClass} contains the class to instantiate. It is kept
   * here to speed up the method {@code {@link #newInstance() newInstance()}}
   * .
   */
  private Class<?> theClass;


  public TypesettingContextFactory() {

  }

  /**
   * Configure the factory according to a given Configuration. The
   * configuration must have the attribute {@code class} which names a fully
   * qualified class name. This class is instantiated using its no-argument
   * constructor.
   *
   * <pre>
   *  &lt;typesettingContext class="the.class"/&gt;
   * </pre>
   *
   * @param configuration the configuration for this factory
   * @throws ConfigurationException in case of an error
   *                                <ul>
   *                                <li>ConfigurationMissingAttributeException in case that the
   *                                attribute {@code CLASS_ATTRIBUTE} is 
   *                                not set for the given
   *                                configuration.</li>
   *                                <li>ConfigurationInstantiationException
   *                                in case that the
   *                                instantiation of the given class causes
   *                                a SecurityException.</li>
   *                                <li>ConfigurationClassNotFoundException
   *                                in case that the named
   *                                class could not be loaded.</li>
   *                                </ul>
   * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
   */
  @Override
  public void configure( Configuration configuration )
      throws ConfigurationException {

    super.configure( configuration );

    String classname = configuration.getAttribute( CLASS_ATTRIBUTE );
    if( classname == null ) {
      throw new ConfigurationMissingAttributeException( CLASS_ATTRIBUTE,
                                                        configuration );
    }

    try {
      theClass = Class.forName( classname );
    } catch( SecurityException e ) {
      throw new ConfigurationInstantiationException( e );
    } catch( ClassNotFoundException e ) {
      throw new ConfigurationClassNotFoundException( classname,
                                                     configuration );
    }
  }

  /**
   * Getter for the initial instance.
   *
   * @return the initial instance
   * @throws ConfigurationException in case of an error
   */
  public TypesettingContext initial() {

    ModifiableTypesettingContext tc = newInstance();
    if( languageManager != null ) {
      tc.setLanguage( languageManager.getLanguage( "0" ) );
    }
    return tc;
  }

  /**
   * Factory method to acquire an instance of the TypesettingContext.
   *
   * @return an appropriate instance of the TypesettingContext.
   * @throws ConfigurationInstantiationException in case that the
   *                                             instantiation of the class 
   *                                             failed.
   */
  protected ModifiableTypesettingContext newInstance()
      throws ConfigurationInstantiationException {

    ModifiableTypesettingContext context;

    try {
      context = (ModifiableTypesettingContext) (theClass.newInstance());
      context.setFont( new NullFont() );
    } catch( InstantiationException e ) {
      throw new ConfigurationInstantiationException( e );
    } catch( IllegalAccessException e ) {
      throw new ConfigurationInstantiationException( e );
    }

    return context;
  }

  /**
   * Create a new instance of a typesetting context. The typesetting context
   * passed in as argument may not be under the control of this factory.
   *
   * @param tc the typesetting context to take over
   * @return a typesetting context with the same attributes as the argument
   * @throws ConfigurationException in case of an error
   */
  public TypesettingContext newInstance( TypesettingContext tc ) {

    if( languageManager != null ) {
      return newInstance( tc,
                          languageManager.getLanguage( tc.getLanguage()
                                                         .getName() ) );
    }
    return tc;
  }

  /**
   * Factory method to acquire an instance of the TypesettingContext.
   *
   * @param context the typesetting context to clone
   * @param color   the new value for the color
   * @return an appropriate instance of the TypesettingContext.
   * @throws ConfigurationInstantiationException in case that the
   *                                             instantiation of the class 
   *                                             failed.
   */
  public TypesettingContext newInstance( TypesettingContext context,
                                         Color color )
      throws ConfigurationInstantiationException {

    ModifiableTypesettingContext c = newInstance();
    c.set( context );
    c.setColor( color );

    return c;
  }

  /**
   * Factory method to acquire an instance of the TypesettingContext.
   *
   * @param context   the typesetting context to clone
   * @param direction the new value for the direction
   * @return an appropriate instance of the TypesettingContext.
   * @throws ConfigurationInstantiationException in case that the
   *                                             instantiation of the class 
   *                                             failed.
   */
  public TypesettingContext newInstance( TypesettingContext context,
                                         Direction direction )
      throws ConfigurationInstantiationException {

    ModifiableTypesettingContext c = newInstance();
    c.set( context );
    c.setDirection( direction );

    return c;
  }

  /**
   * Factory method to acquire an instance of the TypesettingContext.
   *
   * @param context the typesetting context to clone
   * @param font    the new value for the font
   * @return an appropriate instance of the TypesettingContext.
   * @throws ConfigurationInstantiationException in case that the
   *                                             instantiation of the class
   *                                             failed.
   */
  public TypesettingContext newInstance( TypesettingContext context, Font font )
      throws ConfigurationInstantiationException {

    ModifiableTypesettingContext c = newInstance();
    c.set( context );
    c.setFont( font );

    return c;
  }

  /**
   * Factory method to acquire an instance of the TypesettingContext with a
   * new value for the language.
   *
   * @param context  the typesetting context to clone
   * @param language the new value for the hyphenation table
   * @return an appropriate instance of the TypesettingContext.
   * @throws ConfigurationException in case of a configuration problem
   */
  public TypesettingContext newInstance( TypesettingContext context,
                                         Language language ) {

    ModifiableTypesettingContext c = newInstance();
    c.set( context );
    c.setLanguage( language );

    return c;
  }

  /**
   * Factory method to acquire an instance of the TypesettingContext with a
   * new value for the language. The language might be loaded if necessary.
   *
   * @param tc       the typesetting context to clone
   * @param language the new value for the hyphenation table
   * @return an appropriate instance of the TypesettingContext.
   * @throws ConfigurationException in case of a configuration problem
   */
  public TypesettingContext newInstance( TypesettingContext tc,
                                         String language ) {

    ModifiableTypesettingContext c = newInstance();
    c.set( tc );
    c.setLanguage( languageManager.getLanguage( language ) );

    return c;
  }

  /**
   * Setter for the language manager.
   *
   * @param languageManager the new language manager
   */
  public void setLanguageManager( LanguageManager languageManager ) {

    this.languageManager = languageManager;
  }

}
