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
 * Contains the classes for the built-in functions of
 * the processor.
 * <p>
 *   Built-in functions are implemented as classes which are
 *   characterized as follows:
 * </p>
 * <ul>
 *   <li>They have a method {@code execute()} which performs the actions
 *     required.
 *   </li>
 *   <li>They have a constructor which takes a String argument which is the name
 *     under which this function is registered in the processor context.
 *   </li>
 * </ul>
 * <p>
 *   I.e. they implement the interface
 *   {@link org.extex.exbib.core.bst.code.Code Code}.
 * </p>
 * <p>
 *   The functions are either integrated in the processor statically like in 
 *   {@code {@link org.extex.exbib.core.bst.BstInterpreter099c BstInterpreter099c}}.
 *   Alternatively they can be loaded dynamically according to a configuration
 *   file like in {@code {@link org.extex.exbib.core.bst.BstInterpreter BstInterpreter}}.
 * </p>
 *
 *
 * <table> <caption>TBD</caption>
 *   <caption>Dependencies of the functions and implementations</caption>
 *   <tr>
 *     <th>Function Name</th>
 *     <th>Class</th>
 *     <th>Braces</th>
 *     <th>T<span 
 *     style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 *     >e</span>X</th>
 *     <th>Western</th>
 *   </tr>
 *   <tr>
 *     <td>{@code add.period$}</td>
 *     <td>{@code AddPeriod}</td>
 *     <td>ignore right braces</td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code call.type$}</td>
 *     <td>{@code CallType}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code change.case$}</td>
 *     <td>{@code ChangeCase}</td>
 *     <td>Consider brace level</td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code chr.to.int$}</td>
 *     <td>{@code ChrToInt}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code cite$}</td>
 *     <td>{@code Cite}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code *}</td>
 *     <td>{@code Concat}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code duplicate$}</td>
 *     <td>{@code Duplicate}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code empty$}</td>
 *     <td>{@code Empty}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code =}</td>
 *     <td>{@code Eq}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code format.name$}</td>
 *     <td>{@code FormatName}</td>
 *     <td>Consider brace level</td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code format.name$}</td>
 *     <td>{@code FormatName099}</td>
 *     <td>Consider brace level</td>
 *     <td></td>
 *     <td>Knows tie</td>
 *   </tr>
 *   <tr>
 *     <td>{@code &gt;}</td>
 *     <td>{@code Gt}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code if$}</td>
 *     <td>{@code If}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code int.to.chr$}</td>
 *     <td>{@code IntToChr}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code int.to.str$}</td>
 *     <td>{@code IntToStr}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code &lt;}</td>
 *     <td>{@code Lt}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code -}</td>
 *     <td>{@code Minus}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code missing$}</td>
 *     <td>{@code Missing}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code newline$}</td>
 *     <td>{@code Newline}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code num.names$}</td>
 *     <td>{@code NumNames}</td>
 *     <td>Consider brace level</td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code +}</td>
 *     <td>{@code Plus}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code pop$}</td>
 *     <td>{@code Pop}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code preamble$}</td>
 *     <td>{@code Preamble}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code purify$}</td>
 *     <td>{@code Purify}</td>
 *     <td>Consider brace level</td>
 *     <td>Knows special characters</td>
 *     <td>*</td>
 *   </tr>
 *   <tr>
 *     <td>{@code quote$}</td>
 *     <td>{@code Quote}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code :=}</td>
 *     <td>{@code Set}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code skip$}</td>
 *     <td>{@code Skip}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code stack$}</td>
 *     <td>{@code Stack}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code substring$}</td>
 *     <td>{@code Substring}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code swap$}</td>
 *     <td>{@code Swap}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code text.length$}</td>
 *     <td>{@code TextLength}</td>
 *     <td>Ignore braces</td>
 *     <td>Knows special characters</td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code text.prefix$}</td>
 *     <td>{@code TextPrefix}</td>
 *     <td>Consoder brace level</td>
 *     <td>Knows special charcters</td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code Top$}</td>
 *     <td>{@code Top}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code type$}</td>
 *     <td>{@code Type}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code warning$}</td>
 *     <td>{@code Warning}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code while$}</td>
 *     <td>{@code While}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *   </tr>
 *   <tr>
 *     <td>{@code width$}</td>
 *     <td>{@code Width}</td>
 *     <td>Sometimes ignores braces</td>
 *     <td>Knows special characters</td>
 *     <td>ASCII only</td>
 *   </tr>
 *   <tr>
 *     <td>{@code write$}</td>
 *     <td>{@code Write}</td>
 *     <td></td>
 *     <td></td>
 *     <td></td>
 *     </tr>
 *   </table>
 *
 */

package org.extex.exbib.core.bst.code.impl;

