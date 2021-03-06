/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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
/**
 *  This is the Command Line Interface description and parser.
 *  The main design goals are the following ones:
 *  
 *  <ul>
 *  <li>The parser should have as less knowledge as possible about how the
 *    command line should look like.
 *  </li>
 *  <li>The parser should be able to parse POSIX compliant command lines.
 *    Nevertheless the collation of single letter options is not considered
 *    to be essential.
 *  </li>
 *  <li>
 *    The description should be usable to produce a usage description for the
 *    online help.
 *  </li>
 *  <li>
 *    The parser should not contain any fixed texts. It should be possible to
 *    extract all needed strings from a resource bundle.
 *  </li>
 *  <li>
 *    The parser should support abbreviated forms of long options.
 *  </li>
 *  <li>
 *    The parser should use a callback-like interface to allow arbitrary code
 *    to be executed during the parsing.
 *  </li>
 *  </ul>
 *
 */

package org.extex.cli;

