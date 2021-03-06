/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.io.bibio;

import org.extex.exbib.core.db.*;
import org.extex.exbib.core.io.Writer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * This class implements a writer for databases in Lisp format.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibPrinterLispImpl implements BibPrinter, ValueVisitor {

  /**
   * Translate the characters '"', '\n', '\r', and '\' to escaped characters.
   *
   * @param s the string to encode
   * @return the encoded version of the string
   */
  private static String encodeArg( String s ) {

    StringBuilder sb = new StringBuilder( s );

    for( int i = sb.length() - 1; i > 0; i-- ) {
      switch( sb.charAt( i ) ) {
        case '\\':
          sb.replace( i, i + 1, "\\\\" );
          break;
        case '"':
          sb.replace( i, i + 1, "\\\"" );
          break;
        case '\n':
          sb.replace( i, i + 1, "\\n" );
          break;
        case '\r':
          sb.replace( i, i + 1, "\\r" );
          break;
        default: // leave alone
      }
    }

    return sb.toString();
  }

  /**
   * Translate the argument to a string which can safely read as symbol.
   *
   * @param s the string to encode
   * @return the encoded version of the string
   */
  private static String encodeSymbol( String s ) {

    // not yet
    return s;
  }

  /**
   * The field {@code writer} contains the output writer.
   */
  private Writer writer = null;

  /**
   * Creates a new object.
   *
   * @param writer the target writer
   */
  public BibPrinterLispImpl( Writer writer ) {

    this.writer = writer;
  }

  /**
   * Print the database as XML.
   *
   * @param db the database context
   * @throws IOException in case of an I/O error
   */
  public void print( DB db ) throws IOException {

    writer.print( "(database\n" );

    Value preamble = db.getPreamble();

    if( preamble != null && !preamble.isEmpty() ) {
      writer.print( "  (preamble " );
      preamble.visit( this, db );
      writer.print( ")\n\n" );
    }

    List<String> macroNames = db.getMacroNames();
    Collections.sort( macroNames );

    for( String name : macroNames ) {
      writer.print( "  (string '", encodeSymbol( name ), " " );
      db.getMacro( name ).visit( this, db );
      writer.print( ")\n" );
    }

    for( Entry e : db ) {
      writer.print( "\n  (entry '", encodeSymbol( e.getType() ), " \"" );
      writer.print( e.getKey(), "\"" );

      for( String key : e.getKeys() ) {
        writer.print( "\n    (field '", encodeSymbol( key ), " " );
        e.get( key ).visit( this, db );
        writer.print( ")" );
      }

      writer.print( "\n  )\n" );
    }

    writer.print( "\n)\n" );
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitBlock( VBlock value, DB db ) throws IOException {

    writer.print( "\"", encodeArg( value.getContent() ), "\"" );
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitMacro( VMacro value, DB db ) throws IOException {

    writer.print( "'", encodeArg( value.getContent() ).toLowerCase(
        Locale.ENGLISH ), " " );
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitNumber( VNumber value, DB db ) throws IOException {

    writer.print( encodeSymbol( value.getContent() ) );
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitString( VString value, DB db ) throws IOException {

    writer.print( "\"", encodeArg( value.getContent() ), "\"" );
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitValue( Value value, DB db ) throws IOException {

    boolean first = true;

    for( ValueItem item : value ) {
      if( first ) {
        first = false;
      }
      else {
        writer.print( " " );
      }
      item.visit( this, db );
    }
  }

}
