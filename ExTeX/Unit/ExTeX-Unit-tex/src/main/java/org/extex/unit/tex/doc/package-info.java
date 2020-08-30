/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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
 *  Contains primitives for εχTeX's TeX mode.
 *
 * <p>The Count Parameter {@code \tracingrestores}</p>
 * <p>
 *  The count register {@code \tracingrestores} controls the
 *  logging of restore operations when the blocks are closed. The
 *  default implementation of εχTeX uses another
 *  mechanism. Thus this register has no effect at all.
 * </p>
 * <p>
 *  This register is a preassigned count register and can be used
 *  as such. Thus assignments and arithmetic can be performed on it.
 * </p>
 *
 * <p>Syntax</p>

 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;tracingrestores&rang;
 *       &rarr; {@code \tracingrestores} ...  </pre>
 *
 * <p>Examples</p>

 *  <pre class="TeXSample">
 *    \tracingrestores=1  </pre>
 *
 */

package org.extex.unit.tex.doc;

