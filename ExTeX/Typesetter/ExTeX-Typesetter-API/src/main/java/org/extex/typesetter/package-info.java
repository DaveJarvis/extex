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
 * This package contains the typesetter definitions for &epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X. The typesetter
 * has the aim of building lists of nodes and compute their optimal distribution.
 *
 *  <h1>The Way to the Document Writer</h1>
 *  <p>In this section we want to see which instances are involved to
 *  come to a page being sent to the
 *  {@link org.extex.backend.documentWriter.DocumentWriter DocumentWriter}.
 *  For this purpose we consider the sequence diagram in the figure below.</p>
 *  <div class="Figure">
 *   <img src="doc-files/SD_par.png" />
 *   <br />
 *   <caption>Sequence Diagram: A Page Break</caption>
 *  </div>
 *  <p>The start of out consideration is at the par primitive wich is
 *  triggered in TeX explicitely or implicitely when two empty lines are
 *  encountered. The primitive has a
 *  {@link org.extex.typesetter.Typesetter Typesetter} at hand and
 *  forwards the message to this typesetter.
 *  </p>
 *
 *  <p>The typesetter maintains a stack of 
 *  {@link org.extex.typesetter.ListMaker ListMaker}s. If
 *  the top of this stack is a horizontal list then it is closed first.
 *  this leaves a vertical list
 *  ({@link org.extex.typesetter.listMaker.HorizontalListMaker HorizontalListMaker}).
 *  The par message is propagated to the list maker at the top of the stack.
 *  </p>
 *
 *  <p>The horizontal list maker instructs his manager &ndash; which is
 *  also the typesetter &ndash; to build the paragraph. The typesetter
 *  propagates this message to the
 *  {@link org.extex.typesetter.paragraphBuilder.ParagraphBuilder ParagraphBuilder}.
 *  </p>
 *
 *  <p>When the contents is added to the topmost list maker &ndash;
 *  which is the global vertical list &ndash; the page builder is
 *  triggered. It tries to determine whether the page is full enough and
 *  invokes the output routine
 *  </p>
 *
 *  <p>The output routine is an implementation of the interface
 *  {@link org.extex.typesetter.output.OutputRoutine OutputRoutine}. The
 *  TeX implementation for the output routine executes some tokens with
 *  the help of an interpreter. In the course of this execution the
 *  primitive <tt>\shipout</tt> can be used to send some material to the
 *  document writer.
 *  </p>
 *
 *  <p>The primitive asks the typesetter to perform the shipout. Thus
 *  the material is finaly forwarded to the document writer.
 *  </p>
 */

package org.extex.typesetter;
