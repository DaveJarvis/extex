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
 * This package contains the classes for reading and writing bib files.
 * <p>
 *  Bib files have a fixed syntax in B<small>IB</small><span 
 *  style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *  >e</span>X. 
 *  This syntax is only one possible representation. 
 *  This package may contain several representation to be used for reading and
 *  writing.
 * </p>
 * 
 * <h1>Loading a Bib File</h1>
 * <p>
 *  In this walk through example we will see how a bib file can be loaded into
 *  a database. To start with we need a configuration for a resource finder.
 *  The configuration contains the search algorithm and the modules used for the
 *  search. In the simplest form a plain file search can be performed.
 * </p>
 * <p>
 *  The resource finder can be acquired from a <code>ConfigurationFactory</code>:
 * </p>
 * <pre>
 * Configuration cfg =
 *         ConfigurationFactory.newInstance("someFinder.xml");</pre>
 * <p>
 *  Now we can push the configuration into a <code>ResourceFinderFactory</code>
 *  to get a <code>ResourceFinder</code>. There are some other arguments which
 *  are ignored at the moment. We simply use <code>null</code> values for them.
 * </p>
 * <pre>
 * ResourceFinder finder =
 *         new ResourceFinderFactory().createResourceFinder(cfg, null,
 *             null, null);</pre>
 * <p>
 *  Next we need a reader for a bib file. <code>BibReaderImp</code> is used
 *  directly.
 * </p>
 * <pre>
 * BibReader r = new BibReaderImpl();</pre>
 * <p>
 *  The reader is not ready yet. It needs the resource finder. Thus we inject it
 *  via the setter.
 * </p>
 * <pre>
 * r.setResourceFinder(finder);</pre>
 * <p>
 *  Now the reader is ready and can be used to open a bib file. The argument
 *  given to <code>open()</code> is the name of a resource sought with the
 *  resource finder.
 * </p>
 * <pre>
 * r.open(bibFile);</pre>
 * <p>
 *  Well, we have a reader opened with the bib file. Before we can start parsing
 *  we need a database to store the results in. We use simply an implementation
 *  directly.
 * </p>
 * <pre>
 * DB db = new DBImpl();</pre>
 * <p>
 *  Now we are ready to parse the bib file and load the entries found into the
 *  data base.
 * </p>
 * <p>
 *  The reader is closed to free any resources which might still be allocated.
 * </p>
 * <pre>
 * try {
 *     r.load(db);
 * } finally {
 *     r.close();
 * } </pre>
 * <p>
 *  Finally the entries have made it into the database.
 * </p>
 *
 */

package org.extex.exbib.core.io.bibio;

