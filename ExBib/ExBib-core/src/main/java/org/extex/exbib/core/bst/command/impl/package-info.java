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
 * Contains implementations of
 * {@link org.extex.exbib.core.bst.command.Command Command}s which are
 * executed when the BST program is run by the
 * {@link org.extex.exbib.core.bst.BstProcessor BstProcessor}.
 * <p>
 * The instructions read by the bst parser are divided into declarations and
 * commands. The declarations are evaluated immediately. The commands are stored
 * for later evaluation. Thus you will find only classes for commands here.
 * </p>
 */

package org.extex.exbib.core.bst.command.impl;

