/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.exception;

import org.extex.framework.configuration.Configuration;

/**
 * This Exception is thrown when an attribute of configuration is requested
 * which is not found.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ConfigurationMissingAttributeException
    extends
    ConfigurationException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  private static final long serialVersionUID = 2010L;

  /**
   * The field {@code attribute} contains the name of the missing attribute.
   */
  private final String attribute;

  /**
   * Create a new object.
   *
   * @param attribute the message string
   * @param origin    the configuration in which the exception has occurred
   */
  public ConfigurationMissingAttributeException( String attribute,
                                                 Configuration origin ) {

    this( attribute, origin.toString() );
  }

  /**
   * Create a new object.
   *
   * @param attribute the message string
   * @param location  the location where the exception has occurred
   */
  public ConfigurationMissingAttributeException( String attribute,
                                                 String location ) {

    super( null, location );
    this.attribute = attribute;
  }

  /**
   * Getter for attribute.
   *
   * @return the attribute
   */
  public String getAttribute() {

    return attribute;
  }

  /**
   * Getter for the text prefix of this ConfigException. The text is taken
   * from the resource bundle {@code ConfigurationEception} under the key
   * {@code ConfigurationMissingAttributeException.Text}. The argument {0} is
   * replaced by the name of the missing attribute as passed to the
   * constructor.
   *
   * @return the text
   */
  @Override
  public String getText() {

    return getLocalizer().format(
        "ConfigurationMissingAttributeException.Text",
        attribute == null ? "" : attribute );
  }

}
