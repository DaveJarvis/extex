/*
 * Copyright (C) 2006-2009 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.mode;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;

/**
 * This is the abstract base class for primitives dealing with an input or
 * output mode as defined by Omega.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractModeCode extends AbstractCode {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2009L;

  /**
   * The constant {@code INPUT_MODE} contains the key for the input mode.
   */
  protected static final String DEFAULT_INPUT_MODE = "defaultInputMode";

  /**
   * The constant {@code DEFAULT_INPUT_TRANSLATION} contains the key for
   * the default input translation.
   */
  protected static final String DEFAULT_INPUT_TRANSLATION =
      "defaultInputTranslation.";

  /**
   * The constant {@code OUTPUT_MODE} contains the key for the output mode.
   */
  protected static final String DEFAULT_OUTPUT_MODE = "defaultOutputMode";

  /**
   * The constant {@code DEFAULT_OUTPUT_TRANSLATION} contains the key for
   * the default output translation.
   */
  protected static final String DEFAULT_OUTPUT_TRANSLATION =
      "defaultOutputTranslation.";

  /**
   * The constant {@code INPUT_MODE} contains the key for the input mode.
   */
  protected static final String INPUT_MODE = "inputMode";

  /**
   * The constant {@code INPUT_TRANSLATION} contains the key for the input
   * translation.
   */
  protected static final String INPUT_TRANSLATION = "inputTranslation";

  /**
   * The constant {@code OUTPUT_MODE} contains the key for the output mode.
   */
  protected static final String OUTPUT_MODE = "outputMode";

  /**
   * The constant {@code OUTPUT_TRANSLATION} contains the key for the
   * output translation.
   */
  protected static final String OUTPUT_TRANSLATION = "outputTranslation";

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public AbstractModeCode( CodeToken token ) {

    super( token );
  }

  /**
   * Scan the token source for a keyword describing an input mode.
   *
   * @param context the interpreter context
   * @param source  the source for new tokens
   * @return mode
   * @throws HelpingException in case of an error
   */
  protected OmegaMode scanInputMode( Context context, TokenSource source )
      throws HelpingException {

    OmegaMode mode = scanMode( context, source );

    if( mode == null ) {
      Token token = source.getToken( context );
      if( token == null ) {
        throw new EofException();
      }
      source.push( token );

      throw new BadInputModeException();
    }

    return mode;
  }

  /**
   * Scan the token source for a keyword describing a mode.
   *
   * @param context the interpreter context
   * @param source  the source for new tokens
   * @return the mode or {@code null} if none is found
   * @throws HelpingException in case of an error
   */
  private OmegaMode scanMode( Context context, TokenSource source )
      throws HelpingException {

    if( source.getKeyword( context, "onebyte" ) ) {
      return OmegaMode.ONEBYTE;
    }
    else if( source.getKeyword( context, "ebcdic" ) ) {
      return OmegaMode.EBCDIC;
    }
    else if( source.getKeyword( context, "twobyte" ) ) {
      return OmegaMode.TWOBYTE;
    }
    else if( source.getKeyword( context, "twobyteLE" ) ) {
      return OmegaMode.TWOBYTE_LE;
    }

    return null;
  }

  /**
   * Scan the token source for a keyword describing an input mode.
   *
   * @param context the interpreter context
   * @param source  the source for new tokens
   * @return the mode
   * @throws HelpingException in case of an error
   */
  protected OmegaMode scanOutputMode( Context context, TokenSource source )
      throws HelpingException {

    OmegaMode mode = scanMode( context, source );

    if( mode == null ) {
      Token token = source.getToken( context );
      if( token == null ) {
        throw new EofException();
      }
      source.push( token );

      throw new BadOutputModeException();
    }

    return mode;
  }

}
