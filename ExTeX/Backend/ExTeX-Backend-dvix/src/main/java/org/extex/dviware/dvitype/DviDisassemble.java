/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.dviware.dvitype;

import org.extex.dviware.Dvi;
import org.extex.dviware.DviProcessor;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a command line tool to disassemble a DVI file.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DviDisassemble implements DviProcessor {

  /**
   * The field {@code condensed} contains the indicator that sequences of
   * put_char instructions should be condensed.
   */
  private static boolean condensed = true;

  /**
   * The field {@code hexLabel} contains the indicator that the label should
   * be presented as hex number.
   */
  private static boolean hexLabel = true;

  /**
   * The constant {@code PROP_CONFIG} contains the name of the property for
   * the configuration resource to use.
   */
  protected static final String PROP_CONFIG = "extex.config";

  /**
   * The field {@code showLabel} contains the indicator that the label should
   * be shown as labels.
   */
  private static boolean showLabel = true;

  /**
   * The command line interface to dvitype.
   *
   * @param args the command line arguments
   */
  public static void main( String[] args ) {

    Logger logger = Logger.getLogger( DviDisassemble.class.getName() );
    logger.setUseParentHandlers( false );
    logger.setLevel( Level.ALL );

    Handler consoleHandler = new ConsoleHandler();
    consoleHandler.setFormatter( new LogFormatter() );
    consoleHandler.setLevel( Level.WARNING );
    logger.addHandler( consoleHandler );

    for( int i = 0; i < args.length; i++ ) {
      String a = args[ i ];
      if( a.startsWith( "-decimal" ) ) {
        hexLabel = false;
      }
      else if( a.startsWith( "-nolabel" ) ) {
        showLabel = false;
      }
      else if( a.startsWith( "-uncondensed" ) ) {
        condensed = false;
      }
      else {
        process( a, logger );
      }
    }
  }

  /**
   * Process an input file.
   *
   * @param arg    the resource name to process
   * @param logger the logger
   */
  protected static void process( String arg, Logger logger ) {

    Properties properties = System.getProperties();
    properties.setProperty( PROP_CONFIG, "config/extex" );

    Configuration config;
    ResourceFinder finder;
    try {
      config =
          ConfigurationFactory.newInstance( properties
                                                .getProperty( PROP_CONFIG ) );
      finder =
          new ResourceFinderFactory().createResourceFinder(
              config.getConfiguration( "Resource" ), logger,
              properties, null );
      InputStream dvi = finder.findResource( arg, "dvi" );

      if( dvi == null ) {
        logger.severe( "Resource `" + arg + "' not found" );
        return;
      }
      new Dvi( dvi ).parse( new DviDisassemble( System.out ) );

      dvi.close();
    } catch( Exception e ) {
      logger.throwing( "DviDisassemble", "process", e );
    }
  }

  /**
   * The field {@code inString} contains the indicator that a sequence of
   * characters has already been begun.
   */
  private boolean inString = false;

  /**
   * The field {@code out} contains the output stream.
   */
  private final PrintStream out;

  /**
   * Creates a new object.
   *
   * @param out the output stream
   */
  public DviDisassemble( PrintStream out ) {

    this.out = out;
  }

  /**
   * A DVI {@code bop} instruction has been encountered. This instruction
   * signals the beginning of a new page.
   *
   * @param off the current byte position in the input stream
   * @param c   the array of page number indicators. The array has length 
   *            10. It
   *            is initialized from the count registers 0 to 9 at the time the
   *            page is shipped out.
   * @param p   the pointer to the previous {@code bop} instruction or -1 for
   *            the first page
   * @see org.extex.dviware.DviProcessor#bop(int, int[], int)
   */
  public void bop( int off, int[] c, int p ) {

    printLabel( off );
    out.print( "bop " );
    out.print( c[ 0 ] );
    out.print( ' ' );
    out.print( c[ 1 ] );
    out.print( ' ' );
    out.print( c[ 2 ] );
    out.print( ' ' );
    out.print( c[ 3 ] );
    out.print( ' ' );
    out.print( c[ 4 ] );
    out.print( ' ' );
    out.print( c[ 5 ] );
    out.print( ' ' );
    out.print( c[ 6 ] );
    out.print( ' ' );
    out.print( c[ 7 ] );
    out.print( ' ' );
    out.print( c[ 8 ] );
    out.print( ' ' );
    out.print( c[ 9 ] );
    out.print( " 0x" );
    out.println( Integer.toHexString( p ) );
  }

  /**
   * A DVI {@code down} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param b   the number of DVI units to move down. If negative then the
   *            current position moves upwards.
   * @see org.extex.dviware.DviProcessor#down(int, int)
   */
  public void down( int off, int b ) {

    printLabel( off );
    out.print( "down " );
    out.println( b );
  }

  /**
   * A DVI {@code eop} instruction has been encountered. This instruction
   * signals the end of a page.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#eop(int)
   */
  public void eop( int off ) {

    printLabel( off );
    out.println( "eop" );
  }

  /**
   * A DVI {@code fnt} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param k   the new font number; this number is not negative
   * @see org.extex.dviware.DviProcessor#fnt(int, int)
   */
  public void fnt( int off, int k ) {

    printLabel( off );
    out.print( "fnt " );
    out.println( k );
  }

  /**
   * A DVI {@code fntDef} instruction has been encountered.
   *
   * @param off  the current byte position in the input stream
   * @param k    the number of the font
   * @param c    length of the font area
   * @param s    length of the font name
   * @param d    the font area
   * @param name the name of the font
   * @see org.extex.dviware.DviProcessor#fntDef(int, int, int, int, int,
   * java.lang.String)
   */
  public void fntDef( int off, int k, int c, int s, int d, String name ) {

    printLabel( off );
    out.print( "fnt_def " );
    out.print( k );
    out.print( ' ' );
    out.print( c );
    out.print( ' ' );
    out.print( s );
    out.print( ' ' );
    out.print( d );
    out.print( ' ' );
    out.print( '"' );
    out.print( name );
    out.println( '"' );
  }

  /**
   * A DVI {@code nop} instruction has been encountered. This instruction
   * simply does nothing. It just occupies one byte in the input stream.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#nop(int)
   */
  public void nop( int off ) {

    printLabel( off );
    out.println( "nop" );
  }

  /**
   * A DVI {@code pop} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#pop(int)
   */
  public void pop( int off ) {

    printLabel( off );
    out.println( "pop" );
  }

  /**
   * A DVI {@code post} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param bop the index of the last BOP instruction
   * @param num the numerator
   * @param den the denominator
   * @param mag the magnification
   * @param l   the maximum page height
   * @param u   the maximum page width
   * @param sp  stack depth
   * @param tp  number of pages
   * @see org.extex.dviware.DviProcessor#post(int, int, int, int, int, int,
   * int, int, int)
   */
  public void post( int off, int bop, int num, int den, int mag, int l, int u,
                    int sp, int tp ) {

    printLabel( off );
    out.print( "post 0x" );
    out.print( Integer.toHexString( bop ) );
    out.print( ' ' );
    out.print( num );
    out.print( ' ' );
    out.print( den );
    out.print( ' ' );
    out.print( mag );
    out.print( ' ' );
    out.print( l );
    out.print( ' ' );
    out.print( u );
    out.print( ' ' );
    out.print( sp );
    out.print( ' ' );
    out.println( tp );
  }

  /**
   * Invoke the callback on a POST_POST instruction. This is the last
   * instruction in a DVI file.
   *
   * @param off the offset in the file of this instruction
   * @param bop the index of the last BOP instruction
   * @param id  the id of this DVI version. Usually this is 2.
   * @see org.extex.dviware.DviProcessor#postPost(int, int, int)
   */
  public void postPost( int off, int bop, int id ) {

    printLabel( off );
    out.print( "post_post 0x" );
    out.print( Integer.toHexString( bop ) );
    out.print( ' ' );
    out.println( id );
  }

  /**
   * A DVI {@code pre} instruction has been encountered.
   *
   * @param off     the current byte position in the input stream
   * @param id      the id of this DVI version. Usually this is 2.
   * @param num     the numerator
   * @param den     the denominator
   * @param mag     the magnification in permille
   * @param comment the comment string
   * @see org.extex.dviware.DviProcessor#pre(int, int, int, int, int,
   * java.lang.String)
   */
  public void pre( int off, int id, int num, int den, int mag,
                   String comment ) {

    printLabel( off );
    out.print( "pre " );
    out.print( id );
    out.print( ' ' );
    out.print( num );
    out.print( ' ' );
    out.print( den );
    out.print( ' ' );
    out.print( mag );
    out.print( ' ' );
    out.print( '"' );
    out.print( comment );
    out.println( '"' );
  }

  /**
   * Print the prefix before the opcode.
   *
   * @param off the label, i.e. the address of the opcode
   */
  private void printLabel( int off ) {

    if( inString ) {
      inString = false;
      out.println( '"' );
    }

    if( showLabel ) {
      if( hexLabel ) {
        String s = Integer.toHexString( off );
        for( int i = 4 - s.length(); i > 0; i-- ) {
          out.print( '0' );
        }
        out.print( s );
      }
      else {
        out.print( off );
      }
      out.print( '\t' );
    }
  }

  /**
   * A DVI {@code push} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#push(int)
   */
  public void push( int off ) {

    printLabel( off );
    out.println( "push" );
  }

  /**
   * A DVI {@code put_char} instruction has been encountered.
   *
   * @param off the current byte position
   * @param c   the number of the character to set
   * @see org.extex.dviware.DviProcessor#putChar(int, int)
   */
  public void putChar( int off, int c ) {

    printLabel( off );
    out.print( "put_char " );
    out.print( c );
    out.print( "\t\t\t; " );
    out.println( (char) c );
  }

  /**
   * A DVI {@code put_rule} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param a   the width
   * @param b   the height
   * @see org.extex.dviware.DviProcessor#putRule(int, int, int)
   */
  public void putRule( int off, int a, int b ) {

    printLabel( off );
    out.print( "put_rule " );
    out.print( a );
    out.print( ' ' );
    out.println( b );
  }

  /**
   * A DVI {@code right} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param b   the distance to move in DVI units
   * @see org.extex.dviware.DviProcessor#right(int, int)
   */
  public void right( int off, int b ) {

    printLabel( off );
    out.print( "right " );
    out.println( b );
  }

  /**
   * A DVI {@code set_char} instruction has been encountered.
   *
   * @param off the current byte position
   * @param c   the number of the character to set
   * @see org.extex.dviware.DviProcessor#setChar(int, int)
   */
  public void setChar( int off, int c ) {

    if( condensed ) {
      if( !inString ) {
        printLabel( off );
        out.print( "\"" );
        inString = true;
      }
      out.print( (char) c );
    }
    else {
      printLabel( off );
      out.print( "set_char_" );
      out.print( c );
      out.print( "\t\t\t; " );
      out.println( (char) c );
    }
  }

  /**
   * A DVI {@code set_rule} instruction has been encountered.
   *
   * @param off the current byte position
   * @param a   the width
   * @param b   the height
   * @see org.extex.dviware.DviProcessor#setRule(int, int, int)
   */
  public void setRule( int off, int a, int b ) {

    printLabel( off );
    out.print( "set_rule " );
    out.print( a );
    out.print( ' ' );
    out.println( b );
  }

  /**
   * A DVI undefined instruction has been encountered. This callback is
   * invoked for the op-codes 250&ndash;255 which are undefined in
   * TeX.
   *
   * @param off    the current byte position
   * @param opcode the opcode encountered
   * @param stream the input stream to read further bytes from
   * @see org.extex.dviware.DviProcessor#undef(int, int, java.io.InputStream)
   */
  public void undef( int off, int opcode, InputStream stream ) {

    printLabel( off );
    out.println( "0x" );
    out.println( Integer.toHexString( opcode ) );
  }

  /**
   * A DVI {@code w} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param b   the distance to add in DVI units
   * @see org.extex.dviware.DviProcessor#w(int, int)
   */
  public void w( int off, int b ) {

    printLabel( off );
    out.print( "w " );
    out.println( b );
  }

  /**
   * A DVI {@code w0} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#w0(int)
   */
  public void w0( int off ) {

    printLabel( off );
    out.println( "w0" );
  }

  /**
   * A DVI {@code x} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param b   the distance to move in DVI units
   * @see org.extex.dviware.DviProcessor#x(int, int)
   */
  public void x( int off, int b ) {

    printLabel( off );
    out.print( "x " );
    out.println( b );
  }

  /**
   * A DVI {@code x0} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#x0(int)
   */
  public void x0( int off ) {

    printLabel( off );
    out.println( "x0" );
  }

  /**
   * A DVI {@code xxx} instruction has been encountered. This instruction is
   * used to pass some bytes uninterpreted to the DVI processor. In
   * TeX this is accomplished with the primitive
   * {@code \special}.
   *
   * @param off the current byte position in the input stream
   * @param x   the array of bytes carrying the content
   * @see org.extex.dviware.DviProcessor#xxx(int, byte[])
   */
  public void xxx( int off, byte[] x ) {

    printLabel( off );
    out.print( "xxx " );
    out.print( '"' );
    out.print( x );
    out.println( '"' );
  }

  /**
   * A DVI {@code y} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param b   the distance to move
   * @see org.extex.dviware.DviProcessor#y(int, int)
   */
  public void y( int off, int b ) {

    printLabel( off );
    out.print( "y " );
    out.println( b );
  }

  /**
   * A DVI {@code y0} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#y0(int)
   */
  public void y0( int off ) {

    printLabel( off );
    out.println( "y0" );
  }

  /**
   * A DVI {@code z} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @param b   the distance to move
   * @see org.extex.dviware.DviProcessor#z(int, int)
   */
  public void z( int off, int b ) {

    printLabel( off );
    out.print( "z " );
    out.println( b );
  }

  /**
   * A DVI {@code z0} instruction has been encountered.
   *
   * @param off the current byte position in the input stream
   * @see org.extex.dviware.DviProcessor#z0(int)
   */
  public void z0( int off ) {

    printLabel( off );
    out.println( "z0" );
  }

}
