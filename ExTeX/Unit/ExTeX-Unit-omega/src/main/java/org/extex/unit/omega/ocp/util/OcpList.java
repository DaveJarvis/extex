/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp.util;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Showable;
import org.extex.ocpware.type.OcpProgram;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.Addafterocplist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides a primitive which acts as container for a pipe of
 * &Omega;CP programs.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class OcpList extends AbstractCode
    implements
    Showable,
    OcplistConvertible {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2007L;

  /**
   * The field {@code map} contains the pipe.
   */
  private final Map<Long, List<OcpProgram>> map =
      new HashMap<Long, List<OcpProgram>>();

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public OcpList( CodeToken token ) {

    super( token );
  }

  /**
   * Add an &Omega;CP program at the end of the pipe.
   *
   * @param scaled  the index
   * @param program the program
   */
  public void addAfter( long scaled, OcpProgram program ) {

    Long l = Long.valueOf( scaled );
    List<OcpProgram> list = map.get( l );
    if( list == null ) {
      list = new ArrayList<OcpProgram>();
      map.put( l, list );
    }

    list.add( program );
  }

  /**
   * Add an &Omega;CP program at the beginning of the pipe.
   *
   * @param scaled  the index
   * @param program the program
   */
  public void addBefore( long scaled, OcpProgram program ) {

    Long l = Long.valueOf( scaled );
    List<OcpProgram> list = map.get( l );
    if( list == null ) {
      list = new ArrayList<OcpProgram>();
      map.put( l, list );
    }

    list.add( 0, program );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public OcpList convertOcplist( Context context, TokenSource source,
                                 Typesetter typesetter )
      throws HelpingException {

    OcpList result = new OcpList( null );

    for( Long k : map.keySet() ) {
      List<OcpProgram> progList = new ArrayList<OcpProgram>();
      result.map.put( k, progList );
      for( OcpProgram p : progList ) {
        progList.add( p );
      }
    }
    return result;
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    throw new HelpingException( LocalizerFactory
                                    .getLocalizer( Addafterocplist.class ),
                                "message" );
  }

  /**
   * Remove an &Omega;CP program from the pipe.
   *
   * @return the popped element
   */
  public OcpList pop() {

    // TODO gene: pop unimplemented
    throw new RuntimeException( "unimplemented" );
  }

  /**
   * Push an &Omega;CP program to the pipe.
   *
   * @param ocpList the list to push
   */
  public void push( OcpList ocpList ) {

    // TODO gene: push unimplemented
    throw new RuntimeException( "unimplemented" );
  }

  /**
   * Remove an &Omega;CP program at the end of the pipe.
   *
   * @param scaled  the index
   * @param program the program
   */
  public void removeAfter( long scaled, OcpProgram program ) {

    // TODO gene: removeAfter unimplemented
    throw new RuntimeException( "unimplemented" );
  }

  /**
   * Remove an &Omega;CP program at the beginning of the pipe.
   *
   * @param scaled  the index
   * @param program the program
   */
  public void removeBefore( long scaled, OcpProgram program ) {

    // TODO gene: removeBefore unimplemented
    throw new RuntimeException( "unimplemented" );
  }

  /**
   * org.extex.interpreter.context.Context)
   */
  public Tokens show( Context context ) throws HelpingException {

    StringBuilder sb = new StringBuilder( "select ocp list " );

    // TODO gene: show content?

    try {
      return context.getTokenFactory().toTokens( sb );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

}
