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

package org.extex.unit.tex.file;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.AbstractAssignment;
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
 * This class provides an implementation for the primitive {@code \read}.
 *
 * <p>The Primitive {@code \read}</p>
 * <p>
 * The primitive {@code \read} read a line of text from the given input
 * stream into a control sequence. The input stream should be opened with
 * {@linkplain org.extex.unit.tex.file.Openin {@code \openin}}. If a stream
 * name is used which has not been opened or has already been closed then the
 * default input stream is used instead.
 * </p>
 * <p>
 * The primitive can be prefixed with
 * {@linkplain org.extex.unit.tex.prefix.Global {@code \global}}. In this case
 * the assignment to the control sequence is global instead of the default of
 * assigning it locally to the current group.
 * </p>
 * <p>
 * The primitive implements an assignment. Thus the definition of
 * {@code \afterassignment} and {@code \globaldefs} are honored.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;read&rang;
 *      &rarr; &lang;optional prefix&rang;{@code \read} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanInFileKey(Context, TokenSource, Typesetter)
 *        &lang;infile&nbsp;name&rang;} {@code to} {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *   \openin3= abc.def
 *   \read3 to \line
 *   \closein3 </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Read extends AbstractAssignment implements LogEnabled {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code logger} contains the target channel for the message.
   */
  private transient Logger logger = null;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Read( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
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
      if( !file.isOpen() ) {
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
      logger.severe( cs.toText() + "=" );
    }

    Tokens toks =
        file.read( context.getTokenFactory(), context.getTokenizer() );
    if( toks == null ) {
      throw new HelpingException( getLocalizer(), "TTP.EOFinRead",
                                  toText() );
    }
    Flags f = prefix.copy();
    f.setGlobal( prefix.isGlobal() );
    context.setCode( cs, new MacroCode( cs, f, false, MacroPattern.EMPTY,
                                        toks ), prefix.clearGlobal() );
  }

  /**
   * Setter for the logger.
   *
   * @param log the logger to use
   * @see org.extex.framework.logger.LogEnabled#enableLogging(
   *java.util.logging.Logger)
   */
  public void enableLogging( Logger log ) {

    this.logger = log;
  }

}
