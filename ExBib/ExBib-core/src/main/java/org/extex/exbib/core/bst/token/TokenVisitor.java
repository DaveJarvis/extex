/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token;

import org.extex.exbib.core.bst.token.impl.*;
import org.extex.exbib.core.exceptions.ExBibException;

/**
 * This interface implements the visitor pattern for tokens. The visitor pattern
 * shows how to avoid explicit switches over the class of an object.
 * <p>
 * Consider t is a {@link Token Token} of some kind and a class wants to act
 * differently for the different subtypes. Then this class implements this
 * interface and invokes the method
 * {@code {@link Token#visit(TokenVisitor, Object[]) visit}}. The appropriate
 * visit method will be invoked then.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface TokenVisitor {

  /**
   * Visitor method invoked by {@code block} tokens.
   *
   * @param block the block visited
   * @param args  the arguments
   * @throws ExBibException in case of an error
   */
  void visitBlock( TBlock block, Object... args ) throws ExBibException;

  /**
   * Visitor method invoked by {@code character} tokens.
   *
   * @param c    the character visited
   * @param args the arguments
   * @throws ExBibException in case of an error
   */
  void visitChar( TChar c, Object... args ) throws ExBibException;

  /**
   * Visitor method invoked by {@code field} tokens.
   *
   * @param field the field visited
   * @param args  the arguments
   * @throws ExBibException in case of an error
   */
  void visitField( TField field, Object... args ) throws ExBibException;

  /**
   * Visitor method invoked by {@code integer} tokens.
   *
   * @param integer the integer visited
   * @param args    the arguments
   * @throws ExBibException in case of an error
   */
  void visitInteger( TInteger integer, Object... args ) throws ExBibException;

  /**
   * Visitor method invoked by {@code option integer} tokens.
   *
   * @param option the integer option visited
   * @param args   the arguments
   * @throws ExBibException in case of an error
   */
  void visitIntegerOption( TIntegerOption option, Object... args )
      throws ExBibException;

  /**
   * Visitor method invoked by {@code literal} tokens.
   *
   * @param literal the literal visited
   * @param args    the arguments
   * @throws ExBibException in case of an error
   */
  void visitLiteral( TLiteral literal, Object... args ) throws ExBibException;

  /**
   * Visitor method invoked by {@code local integers} tokens.
   *
   * @param integer the local integer visited
   * @param args    the arguments
   * @throws ExBibException in case of an error
   */
  void visitLocalInteger( TLocalInteger integer, Object... args )
      throws ExBibException;

  /**
   * Visitor method invoked by accessors to the locator.
   *
   * @param localLocator the accessors visited
   * @param args         the arguments
   * @throws ExBibException in case of an error
   */
  void visitLocalLocator( TLocalLocator localLocator, Object[] args )
      throws ExBibException;

  /**
   * Visitor method invoked by {@code local string} tokens.
   *
   * @param string the local string visited
   * @param args   the arguments
   * @throws ExBibException in case of an error
   */
  void visitLocalString( TLocalString string, Object... args )
      throws ExBibException;

  /**
   * Visitor method invoked by {@code qliteral} tokens.
   *
   * @param qliteral the qliteral visited
   * @param args     the arguments
   * @throws ExBibException in case of an error
   */
  void visitQLiteral( TQLiteral qliteral, Object... args )
      throws ExBibException;

  /**
   * Visitor method invoked by {@code string} tokens.
   *
   * @param string the string visited
   * @param args   the arguments
   * @throws ExBibException in case of an error
   */
  void visitString( TString string, Object... args ) throws ExBibException;

  /**
   * Visitor method invoked by {@code option string} tokens.
   *
   * @param option the integer option visited
   * @param args   the arguments
   * @throws ExBibException in case of an error
   */
  void visitStringOption( TStringOption option, Object... args )
      throws ExBibException;

  /**
   * Visitor method invoked by {@code token list} tokens.
   *
   * @param list the list visited
   * @param args the arguments
   * @throws ExBibException in case of an error
   */
  void visitTokenList( TokenList list, Object... args ) throws ExBibException;

}
