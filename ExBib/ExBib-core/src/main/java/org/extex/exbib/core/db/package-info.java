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
 * Contains the classes for the database of
 * εχBib and the entries stored in it.
 * <p>
 * The database can mainly be seen as a list of entries. There are several
 * methods to get hold of the information. See the documentation of the
 * interface {@link org.extex.exbib.core.db.DB DB} for details.
 * </p>
 * <p>
 * The interface {@link org.extex.exbib.core.db.DB DB} describes the
 * capabilities of a database. The implementation details are hidden.
 * Currently there is one implementation in
 * {@link org.extex.exbib.core.db.impl.DBImpl DBImpl}. Nevertheless
 * this class should not be addressed directly. Instead the factory
 * {@link org.extex.exbib.core.db.DBFactory DBFactory} should be used to get
 * a new instance of a database.
 * </p>
 *
 * <h2>The Entries</h2>
 * <p>
 * The entries are the entities stored in a database.
 * </p>
 * <p>
 * The Value Hierarchy
 *
 * <img src="doc-files/Value-hierarchy.gif" alt="Value Hierarchy">
 */
package org.extex.exbib.core.db;
