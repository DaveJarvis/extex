/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.exception;

import org.extex.framework.i18n.Localizer;

/**
 * This class provides an Exception with the possibility to provide additional
 * help on the error encountered. Thus it has two levels of information: the
 * first level is the message and the second level is the additional help.
 * <p>
 * Both information strings are mapped via the
 * {@link org.extex.framework.i18n.Localizer Localizer} apparatus.
 * The key provided to this Exception is used as a key to find the format in
 * the resource bundle. For the localized message of the exception it is used
 * plain and for the help the string ".help" is appended.
 * </p>
 * <p>Example</p>
 * <p>
 * Consider the following lines in the resource (properties) file for the
 * localizer:
 * </p>
 * <pre>
 * abc.def = This is the message
 * abc.def.help = This is the help text. \
 *               It can even span several lines.
 * </pre>
 * <p>
 * Then the following instruction can safely be used:
 * </p>
 * <pre>
 *     throw new HelpingException(localizer, "abc.def");
 * </pre>
 * <p>
 * With this exception up to three arguments can be used. The String value of
 * those arguments are inserted into the message string for the placeholders
 * {0}, {1}, and {2}. Consider the following format definition in the resource
 * of the localizer:
 * </p>
 * <pre>
 * ghi = This is the {0} message: {2}
 * </pre>
 * <p>
 * Then the instruction
 * </p>
 * <pre>
 *     new HelpingException(localizer, "ghi", "first", "second", "third");
 * </pre>
 * <p>
 * will produce an exception with the following localized message:
 * </p>
 * <pre>
 * This is the first message: third
 * </pre>
 * <p>
 * Note that some special rules hold for strings in resource bundles:
 * </p>
 * <ul>
 * <li>The character {@code \} acts as escape character. In the combination
 *  {@code \n} it produces a newline.</li>
 * <li>If the character {@code \} is the last character of a line then the
 *  format is continued in the next line. The leading whitespace in the
 *  continuing line is silently removed.</li>
 * <li>The character {@code '} also has a special meaning. This usually means
 *  that you have to double a single quote in your format.</li>
 * </ul>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TypesetterHelpingException extends TypesetterException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * The constant {@code DEFAULT_TAG} contains the tag to be used if none
   * is given.
   */
  private static final String DEFAULT_TAG = "GeneralDetailedException.help";

  /**
   * The constant {@code DEFAULT_ARGUMENT} contains the argument if none
   * is given.
   */
  private static final String DEFAULT_ARGUMENT = "?";

  /**
   * The field {@code arg1} contains the first argument.
   */
  private final String arg1;

  /**
   * The field {@code arg2} contains the second argument.
   */
  private final String arg2;

  /**
   * The field {@code arg3} contains the third argument.
   */
  private final String arg3;

  /**
   * The field {@code localizer} contains the localizer.
   */
  private final Localizer localizer;

  /**
   * The field {@code tag} contains the name of the message to show.
   */
  private final String tag;

  /**
   * Creates a new object without variable arguments.
   *
   * @param messageTag   the message
   * @param theLocalizer the localizer to use
   */
  public TypesetterHelpingException( Localizer theLocalizer,
                                     String messageTag ) {

    this.tag = messageTag;
    this.localizer = theLocalizer;
    this.arg1 = DEFAULT_ARGUMENT;
    this.arg2 = DEFAULT_ARGUMENT;
    this.arg3 = DEFAULT_ARGUMENT;
  }

  /**
   * Creates a new object with one variable argument.
   *
   * @param messageTag   the message
   * @param a1           the first argument
   * @param theLocalizer the localizer to use
   */
  public TypesetterHelpingException( Localizer theLocalizer,
                                     String messageTag, String a1 ) {

    this.tag = messageTag;
    this.localizer = theLocalizer;
    this.arg1 = a1;
    this.arg2 = DEFAULT_ARGUMENT;
    this.arg3 = DEFAULT_ARGUMENT;
  }

  /**
   * Creates a new object with two variable arguments.
   *
   * @param messageTag   the message
   * @param a1           the first argument
   * @param a2           the second argument
   * @param theLocalizer the localizer to use
   */
  public TypesetterHelpingException( Localizer theLocalizer,
                                     String messageTag, String a1, String a2 ) {

    this.tag = messageTag;
    this.localizer = theLocalizer;
    this.arg1 = a1;
    this.arg2 = a2;
    this.arg3 = DEFAULT_ARGUMENT;
  }

  /**
   * Creates a new object with three variable arguments.
   *
   * @param messageTag   the message
   * @param a1           the first argument
   * @param a2           the second argument
   * @param a3           the third argument
   * @param theLocalizer the localizer to use
   */
  public TypesetterHelpingException( Localizer theLocalizer,
                                     String messageTag, String a1, String a2,
                                     String a3 ) {

    this.tag = messageTag;
    this.localizer = theLocalizer;
    this.arg1 = a1;
    this.arg2 = a2;
    this.arg3 = a3;
  }

  /**
   * Getter for further help information.
   *
   * @return the help information
   */
  @Override
  public String getHelp() {

    return localizer.format( tag + ".help", arg1, arg2, arg3 );
  }

  /**
   * Getter for further help information.
   *
   * @return the help information
   */
  @Override
  public String getLocalizedMessage() {

    return localizer.format( tag, arg1, arg2, arg3 );
  }

}