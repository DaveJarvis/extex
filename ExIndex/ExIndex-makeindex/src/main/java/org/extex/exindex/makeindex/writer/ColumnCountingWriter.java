/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.writer;

import java.io.IOException;
import java.io.Writer;

/**
 * This writer keeps track of the current column.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ColumnCountingWriter extends Writer {

  /**
   * The field {@code tabSize} contains the width of a tab.
   */
  private final int tabSize = 8;

  /**
   * The field {@code writer} contains the next writer.
   */
  private final Writer writer;

  /**
   * The field {@code column} contains the current column.
   */
  private int column;

  /**
   * Creates a new object.
   *
   * @param writer the next writer
   */
  public ColumnCountingWriter( Writer writer ) {

    this.writer = writer;
  }

  @Override
  public void close() throws IOException {

    writer.close();
  }

  @Override
  public void flush() throws IOException {

    writer.flush();
  }

  /**
   * Getter for the column.
   *
   * @return the column
   */
  public int getColumn() {

    return column;
  }

  /**
   * Setter for column.
   *
   * @param column the column to set
   */
  public void setColumn( int column ) {

    this.column = column;
  }

  @Override
  public void write( char[] cbuf, int off, int len ) throws IOException {

    for( int i = off; i < off + len; i++ ) {
      switch( cbuf[ i ] ) {
        case '\n':
        case '\r':
          column = 0;
          break;
        case '\t':
          column += tabSize * ((column + 8) % 8);
          break;
        default:
          column++;
      }
    }
    writer.write( cbuf, off, len );
  }

}
