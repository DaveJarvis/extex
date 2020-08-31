/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
 * Contains the ExIndex core. ExIndex is an index processor modeled
 * after the <a href="http://www.xindy.org">Xindy</a> index processor.
 *
 *
 * <h2>Components and Data Flow</h2>
 *
 * <p>
 * Index processing has the task to prepare an index. We consider index
 * processing in the context of a document preparation system. The author
 * prepares the document source in a way which allows it the document
 * preparation system to extract the markup of the raw index entries. Now the
 * index processor comes into play. It takes the raw index entries and
 * arranges things in a structured index. This procedure is controlled by an
 * dex style. The structured index might be used bay the document preparation
 * stem to include it into the final document.
 * p>
 * <div style="float:right;">
 *   <img src="doc-files/overview-diagram.png" alt="overview-diagram">
 * <p>
 *   Figure: Overview
 *
 * </div>
 * <p>
 *  This procedure is illustrated in the figure. This figure shows the source
 *  and intermediate results. Our view leaves it open where those data
 *  structures
 *  reside. In the classical scenario of TeX the intermediate data structures
 *  are
 *  stored in files. Nevertheless this needs not be true in any case.
 * </p>
 * <p>
 *  Form a user's point of view the index entries are marked in the document
 *  source. The construction of an index is a rather demanding task. Thus it
 *  needs a human intelligence at the beginning. Any attempt to automatically
 *  extract index entries without markup leads in general to a rather poor
 *  index.
 * </p>
 *
 *
 *
 * <h2>Key Mapping</h2>
 *
 * <div style="float:right;">
 *   <img src="doc-files/key-mapping-diagram.png" alt="key-mapping-diagram">
 * <p>
 *   Figure: Key Mapping
 *
 * </div>
 * <p>
 *  The index processor reads in data of a raw index entry. It needs to
 *  determine where to put the index entry in the structured index. For
 *  this purpose a special key is constructed in several phases. The
 *  initial key read is called the main key. The main key is usually
 *  passed in explicitly or derived from a key passed in.
 * </p>
 * <p>
 *  In a first phase the main key is mapped to the merge key. The merge
 *  key is transformed into the sort key in a second phase. The sort
 *  key is then used to determine the letter group for the entry and to
 *  sort it amoung all entries.
 * </p>
 *
 *
 *
 *
 * <h2>Inheritance of Parameters</h2>
 *
 * <div style="float:right;">
 *   <img src="doc-files/fallback-diagram.png" alt="fallback-diagram">
 * <p>
 *   Figure: Inheritance of Parameters
 *
 * </div>
 * <p>
 *  Parameters are inherited in two dimensions. One dimension is within
 *  an index and the other dimension is across indices.
 * </p>
 * <p>
 *  If a parameter for the markup is resolved then first the exact match
 *  is tried. If this fails to find a proper value the default value in
 *  the same index is tried. If this fails the exact match in the
 *  default index is tried and finally if everything else fails the
 *  default value in the default index.
 * </p>
 * <p>
 *  Thus at most four different locations for a markup parameter are
 *  taken into account. This is illustrated in the figure beside.
 * </p>
 * <p>
 *  Parameters are not considered themselves but only within a parameter
 *  group. The inheritance is used only if the preceding parameter
 *  groups are not defined. This helps to ensure a consistent setting
 *  for parameters.
 * </p>
 */

package org.extex.exindex.core;
