/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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
 * Contains definitions and implementations for the TTF/OTF font
 * handling in εχTeX.
 *
 * <p> The TTF/OTF-Format is described in:</p>
 * <ul>
 *    <li><a href="http://www.microsoft.com/typography/TrueTypeFonts.mspx">
 *       TrueType 1.0 Font Files</a>
 *       </li>
 *    <li><a href="http://developer.apple.com/fonts/TTRefMan/"> TrueType
 *       Reference Manual</a>
 *    </li>
 *    <li>
 *      <a href="http://www.adobe.com/type/opentype/">Adobe Fonts - OpenType</a>
 *    </li>
 *    <li>
 *      <a href="http://www.microsoft.com/typography/otspec/">OpenType specification</a>
 *    </li>
 * </ul>
 * <p>The javadoc uses intensely this documentation to describe the classes..
 * .</p>
 * <p> Some ideas of the following projects have inspired the code. </p>
 * <ul>
 *    <li> <a href="http://www.letterror.com/code/ttx/">TTX</a>
 *    </li>
 *    <li> <a href="http://fontforge.sourceforge.net/">FontForge</a>
 *    </li>
 *    <li> <a href="http://www.activemath.org/~adrianf/dvi2svg/">dvi2svg</a>
 *    </li>
 *    <li> <a href="http://xml.apache.org/batik/ttf2svg.html">ttf2svg</a>
 *    </li>
 *    <li> <a href="http://doubletype.sourceforge.net/">DoubleType</a>
 *    </li>
 *    <li> <a href="http://www.lcdf.org/type/">LCDF Type Software</a>
 *    </li>
 * </ul>
 */

package org.extex.font.format.xtf;

