/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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
 * This package contains primitives for ??TeX's
 * <span style="font-size:80%;">PDF</span>TeX mode.
 *
 * <doc name="pdftexversion" type="register">
 * <h3>The Count Parameter <tt>\pdftexversion</tt></h3>
 * <p>
 *  The count register <tt>\pdftexversion</tt> contains the version
 *  number of <span style="font-size:80%;">PDF</span>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X as an integer. The number is multiplied
 *  by 100 to show the fractional part. For instance the value 123 signals
 *  the version number 1.23 of <span style="font-size:80%;">PDF</span>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X.
 * </p>
 * <p>
 *  The version number is a preassigned count register and can be used
 *  as such. Thus assignments and arithmetic can be performed on it.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;pdftexversion&rang;
 *       &rarr; <tt>\pdftexversion</tt> ...  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \the\pdftexversion  </pre>
 *
 * </doc>
 *
 * <doc name="pdftexrevision" type="register">
 * <h3>The Tokens Parameter <tt>\pdftexrevision</tt></h3>
 * <p>
 *  The tokens register <tt>\pdftexrevision</tt> contains the revision
 *  of <span style="font-size:80%;">PDF</span>T<span style=
 *  "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 *  >e</span>X in use. Usually this is something like a letter.
 * </p>
 * <p>
 *  The revision number is a preassigned tokens register and can be used
 *  as such. Thus assignments can be performed on it.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;pdftexrevision&rang;
 *       &rarr; <tt>\pdftexrevision</tt> ...  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \the\pdftexrevision  </pre>
 *
 * </doc>
 */

package org.extex.unit.pdftex;

