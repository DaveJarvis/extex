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
 * &epsilon;&chi;Bib (or <tt>ExBib</tt> in pure ASCII) is a
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X-compatible database. It is an
 * attempt to provide an implementation of a bibliographic database to
 * be used with L<span style="text-transform:uppercase;font-size:75%;vertical-align: 0.45ex;margin-left: -0.36em;margin-right: -0.15em;"
 * >a</span>T<span style="text-transform:uppercase;font-size:90%;vertical-align: -0.4ex;margin-left: -0.2em;margin-right: -0.1em;line-height: 0;"
 * >e</span>X and friends.
 *   
 * <p style="text-align:center;">
 *   <a href="doc-files/components.gif" style="border:0pt;"><img
 *      style="border:0pt;"
 *      src="doc-files/components.gif"/></a>
 *   <br/>
 *   The components of &epsilon;&chi;Bib
 * </p>
 *   
 * <h2>Objectives</h2>
 *   
 * The development of &epsilon;&chi;Bib has been lead by several objectives:
 *   
 * <ol>
 *   <li>The result of &epsilon;&chi;Bib in compatibility mode should
 *     imitate as much as possible the behavior of
 *     B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X &ndash; for runs without errors
 *     or warnings. This should be valid for the output file only.
 *     &epsilon;&chi;Bib should not be concerned about the exact
 *     reproduction of tracing messages or the contents of the log
 *     file.
 *   </li>
 *   <li>The compatibility mode should <emph>not</emph> be the default.
 *     The default mode should be the &epsilon;&chi;Bib mode, where
 *     certain extensions are provided and some curious design
 *     decisions of B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X are not imitated.
 *   </li>
 *   <li>One objective of &epsilon;&chi;Bib was to provide a clean
 *     design which would make it easy to understand and enhance the
 *     program. As a result the whole program has been structured into
 *     several components which are tied together with interfaces and
 *     factories.
 *   </li>
 *   <li>&epsilon;&chi;Bib should be configurable. This means that it should be
 *     possible to use different implementations of some of the core
 *     components. This could be used for example to mimic the
 *     behavior of B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X 0.99c or
 *     B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X 1.0 (as described in a TUGboat article).
 *   </li>
 *   <li>As implementation language Perl has been used for a first
 *     prototype. Then the implementation language has been switched to
 *     Java to make use of the Unicode features of this languages as
 *     well as to be closer to &epsilon;&chi;T<span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X.
 *   </li>
 *   <li>As much as possible the inherent knowledge about
 *     T<span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X should be eliminated. If not possible it should be
 *     encapsulated in separate components to be exchangeable.
 *   </li>
 *   <li>&epsilon;&chi;Bib should be multi-lingual in the sense that all
 *     messages produced by the system should be adaptable to a local
 *     language. As a proof of concept the languages English and German
 *     are supported right from the start.
 *   </li>
 *   <li>An easy update path should be provided for
 *     B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X users: Old documents should be
 *     processable with the same results. Migration scripts should be
 *     provided whenever necessary.
 *   </li>
 * </ol>
 *   
 * <h2>Extensions</h2>
 *   
 * The following extensions can be envisaged:
 * <ol>
 *   <li>Implement the new features of B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X 1.0.</li>
 *   <li>Add new primitives to the BST language.</li>
 *   <li>Extend the syntax forB<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X files
 *     <ul>
 *       <li>Allow structured description of names.</li>
 *       <li>Make provisions for multi-language entries</li>
 *       <li>Allow structured documents: i.e. articles in journals &ndash;
 *         without <tt>crossref</tt></li>
 *     </ul>
 *   </li>
 *   <li>Implement the features of B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X 8.</li>
 * </ol>
 * 
 * <h2>Bug Fixes</h2>
 *   
 * The following defects of B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X should be cured:
 * <ol>
 *   <li>No restriction on the sequence of entries in the database. 
 *     I.e. referenced entries may preceded or follow the referencing entries.
 *   </li>
 *   <li>Better sorting has to be provided taking into account the various
 *     national conventions for sorting.
 *   </li>
 *   <li>No limit to some magical internal constants &ndash; like the
 *     line length.
 *   </li>
 *   <li>Bug fix: flush the output buffer at the end of the program and not only
 *     when <code>newline$</code> is encountered.
 *   </li>
 * </ol>
 *
 */

package org.extex.exbib.core;