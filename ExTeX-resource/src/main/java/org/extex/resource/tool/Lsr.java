/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.resource.tool;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class creates the output of 'ls -R'.
 *
 * <p>
 * With the method addexcludeDir() it is possible, to add directories, which are
 * excluded from the ls-R list.
 * </p>
 * <p>
 * With the method addexcludeRegExp() it is possible, to add a RegExp, which are
 * excluded an entry in each directory.
 * </p>
 * <p>
 * Files starting with a '.' are ignored!
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */
public class Lsr {

  /**
   * The name of the ls-R file.
   */
  private static final String LS_R = "ls-R";

  /**
   * main.
   *
   * @param args The command line. -excludeDir xxx -excludeRegExp xxx
   * @throws IOException if an io-error occurs.
   */
  public static void main( String[] args ) throws IOException {

    if( args.length >= 1 ) {
      Lsr lsr = new Lsr( new File( args[ 0 ] ) );
      int p = 1;
      while( p < args.length ) {
        if( args[ p ].equalsIgnoreCase( "-excludeDir" ) ) {
          if( p + 1 < args.length ) {
            lsr.addExcludeDir( args[ ++p ] );
            p++;
          }
          else {
            System.exit( printCommandLine( System.err ) );
          }
        }
        else if( args[ p ].equalsIgnoreCase( "-excludeRegExp" ) ) {
          if( p + 1 < args.length ) {
            lsr.addExcludeRegExp( args[ ++p ] );
            p++;
          }
          else {
            System.exit( printCommandLine( System.err ) );
          }
        }
        else {
          System.exit( printCommandLine( System.err ) );
        }
      }
      lsr.printLsr( null );
    }
    else {
      System.exit( printCommandLine( System.err ) );
    }
    System.exit( 0 );
  }

  /**
   * Print the command line.
   *
   * @param err the output stream
   * @return the exit code
   */
  private static int printCommandLine( PrintStream err ) {

    err.println( "java de.dante.util.resource.Lsr <directory> "
                     + "[-excludeDir xxx] [-excludeRegExp xxx] [...]" );
    return 1;
  }

  /**
   * The base directory for searching.
   */
  private final File basedirectory;

  /**
   * A list of directories, which are excluded from the ls-R.
   * <p>
   * Only in the top directory!
   * </p>
   */
  private final List<String> excludeDir;

  /**
   * A list of RegExp, which are excluded in each directory.
   */
  private final List<String> excludeRegExp;

  /**
   * The level for the directory depth.
   */
  private int level = 0;

  /**
   * Creates a new object.
   *
   * @param theBasedirectory The base directory for searching
   */
  public Lsr( File theBasedirectory ) {

    basedirectory = theBasedirectory;
    excludeDir = new ArrayList<String>();
    excludeRegExp = new ArrayList<String>();
  }

  /**
   * Add a directory in the exclude list.
   * <p>
   * Only in the top directory!
   * </p>
   *
   * @param name The name of the directory.
   */
  public void addExcludeDir( String name ) {

    excludeDir.add( name );
  }

  /**
   * Add a RegExp in the exclude list.
   *
   * @param name The name of the directory.
   */
  public void addExcludeRegExp( String name ) {

    excludeRegExp.add( name );
  }

  /**
   * Print the ls-R-information to the printStream.
   *
   * @param printStream The result is written to the {@code PrintStream}.
   * @throws IOException if an io-error occurs.
   */
  public void printLsr( PrintStream printStream ) throws IOException {

    Collections.sort( excludeDir );
    PrintStream out = printStream;
    if( out == null ) {
      out =
          new PrintStream(
              new FileOutputStream( basedirectory.getAbsolutePath()
                                        + File.separator + LS_R ) );
    }

    printLsr( out, basedirectory );

    out.close();
  }

  /**
   * Print the ls-R-information to the printStream.
   *
   * @param printStream The result is written to the {@code PrintStream}.
   * @param directory   The base directory for output.
   * @throws IOException if an io-error occurs.
   */
  private void printLsr( PrintStream printStream, File directory )
      throws IOException {

    if( directory != null ) {
      level++;
      File[] files = directory.listFiles( new FileFilter() {

        /**
         * @see java.io.FileFilter#accept(java.io.File)
         */
        public boolean accept( File pathname ) {

          // 'ls-R'
          if( pathname.getName().equals( LS_R ) ) {
            return false;
          }
          // '.'
          if( pathname.isFile() && pathname.getName().startsWith( "." ) ) {
            return false;
          }
          // exclude dir
          if( level == 1
              && excludeDir.size() > 0
              && pathname.isDirectory()
              && Collections.binarySearch( excludeDir,
                                           pathname.getName() ) >= 0 ) {
            return false;
          }
          // exclude regexp
          for( String regexp : excludeRegExp ) {
            if( regexp != null
                && pathname.getName().matches( regexp ) ) {
              return false;
            }
          }
          return true;
        }
      } );

      if( files != null ) {
        Arrays.sort( files );
        // replace base dir name with a '.'
        printStream.println( directory.toString().replaceFirst(
            basedirectory.toString(), "." )
                                 + ":" );
        for( int i = 0; i < files.length; i++ ) {
          printStream.println( files[ i ].getName() );
        }
        printStream.println();
        for( int i = 0; i < files.length; i++ ) {
          if( files[ i ].isDirectory() ) {
            printLsr( printStream, files[ i ] );
          }
        }
      }
      level--;
    }
  }
}
