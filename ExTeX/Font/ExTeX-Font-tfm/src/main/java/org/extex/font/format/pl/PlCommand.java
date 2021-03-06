/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 */

package org.extex.font.format.pl;

import java.io.IOException;
import java.io.Reader;

/**
 * Interface for a PL command.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public interface PlCommand {

  /**
   * Execute the PL command.
   *
   * @param reader The reader.
   * @throws IOException if an IO-error occurred.
   */
  void execute( Reader reader ) throws IOException;

  /**
   * Returns the parameter.
   *
   * @return Returns the parameter.
   */
  String getParameter();

  /**
   * Set the parameter.
   *
   * @param p The parameter.
   */
  void setParameter( String p );
}
