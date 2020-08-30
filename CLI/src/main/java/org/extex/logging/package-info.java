/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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
 * Logging and Log Levels
 *
 * <p>
 * Contains support classes for logging.
 * εχTeX uses the definition of logging introduced
 * in Java 1.4. Thus most of the required functionality is already provided
 * by the Java programming language.
 * </p>
 *
 * <p>
 *  Log handlers can be used to redirect the log output to the
 *  destinations <em>log file</em> and <em>console</em>. Log levels are
 *  used to distinguish the different cases.
 * </p>
 * <dl>
 *  <dt>{@code Level.SEVERE}</dt>
 *  <dd>This level is used to signal a real problem. It should be
 *   logged to both streams.</dd>
 *  <dt>{@code Level.WARNING}</dt>
 *  <dd>This level is used to signal a warning.
 *  </dd>
 * </dl>
 */
package org.extex.logging;
