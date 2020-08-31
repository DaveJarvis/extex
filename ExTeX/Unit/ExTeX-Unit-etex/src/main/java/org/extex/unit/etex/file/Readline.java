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

package org.extex.unit.etex.file;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.unit.tex.macro.util.MacroCode;
import org.extex.unit.tex.macro.util.MacroPattern;

import java.util.logging.Logger;

/**
 * This class provides an implementation for the primitive
 * {@code \readline}.
 *
 * <p>The Primitive {@code \readline}</p>
 * <p>
 * The primitive {@code \readline} read characters from an input stream until
 * the end of line is encountered. The characters are translated to tokens with
 * the category code OTHER except the white-space characters which receive the
 * category code SPACE. This mapping is performed ignoring the setting of
 * {@link org.extex.unit.tex.register.CatcodePrimitive {@code \catcode}}. The
 * resulting token list is bound to the control sequence given.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;readline&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \readline} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanInFileKey(Context, TokenSource, Typesetter)
 *        &lang;infile&nbsp;name&rang;} {@code to} {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang; </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 * \openin3= abc.def
 * \readline3 to \line
 * \closein3 </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Readline extends AbstractAssignment implements LogEnabled {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code TOKENIZER} contains the tokenizer to use for this
   * primitive.
   */
  private static final Tokenizer TOKENIZER = new Tokenizer() {

    /**
     * Getter for the category code of a character.
     *
     * @param c the Unicode character to analyze
     *
     * @return the category code of a character
     *
     * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
     */
    @Override
    public Catcode getCatcode( UnicodeChar c ) {

      return (c.getCodePoint() == ' ' ? Catcode.SPACE : Catcode.OTHER);
    }

    /**
     * Getter for the name space.
     *
     * @return the name space
     *
     * @see org.extex.scanner.api.Tokenizer#getNamespace()
     */
    @Override
    public String getNamespace() {

      return Namespace.DEFAULT_NAMESPACE;
    }

  };

  /**
   * The field {@code logger} contains the target channel for the message.
   */
  private transient Logger logger = null;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Readline( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key =
        AbstractFileCode.scanInFileKey( context, source, typesetter );

    if( !source.getKeyword( context, "to" ) ) {
      throw new HelpingException( getLocalizer(), "TTP.MissingToForRead",
                                  toText() );
    }
    CodeToken cs = source.getControlSequence( context, typesetter );

    InFile file = context.getInFile( key );

    if( file == null || !file.isOpen() ) {
      file = context.getInFile( null );
      if( file == null || !file.isOpen() ) {
        throw new HelpingException( getLocalizer(), "TTP.EOFinRead",
                                    toText() );
      }
    }
    if( !file.isFileStream() ) {
      Interaction interaction = context.getInteraction();
      if( interaction != Interaction.ERRORSTOPMODE ) {
        throw new HelpingException( getLocalizer(), "TTP.NoTermRead",
                                    toText() );
      }
    }

    if( file.isStandardStream() ) {
      logger.severe( cs.toText( context.escapechar() ) + "=" );
    }

    Tokens toks = file.read( context.getTokenFactory(), TOKENIZER );

    if( toks == null ) {
      throw new HelpingException( getLocalizer(), "TTP.EOFinRead" );
    }

    boolean longP = prefix.clearLong();
    boolean outerP = prefix.clearOuter();
    context.setCode( cs,
                     new MacroCode( cs, prefix, false,
                                    MacroPattern.EMPTY, toks ),
                     prefix.clearGlobal() );
    if( longP ) {
      prefix.setLong();
    }
    if( outerP ) {
      prefix.setOuter();
    }
  }

  /**
   * Setter for the logger.
   *
   * @param log the logger to use
   * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
   */
  @Override
  public void enableLogging( Logger log ) {

    this.logger = log;
  }

}
