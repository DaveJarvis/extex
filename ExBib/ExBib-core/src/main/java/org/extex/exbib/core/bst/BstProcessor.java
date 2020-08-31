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

package org.extex.exbib.core.bst;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.exceptions.*;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;

import java.util.List;

/**
 * This interface describes the contract of BST processors, i.e. the central
 * class containing the interpreter for the B<small>IB</small><span
 * style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X programming language.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface BstProcessor extends Processor, Iterable<Command> {

  /**
   * Add a command to the list of commands to execute. The commands constitute
   * the main program. All commands in the command list are executed in turn
   * when the processor is run.
   *
   * @param command the command to add
   */
  void addCommand( Command command );

  /**
   * Define a new function if not already defined in the processor context. A
   * function can be made undefined by specifying the code {@code null}
   * to it.
   *
   * @param name    the name of the function
   * @param body    the Code associated with the function
   * @param locator The locator
   * @throws ExBibIllegalValueException   in case that the name is
   *                                      {@code null} or empty
   * @throws ExBibFunctionExistsException in case that the named function
   *                                      already exists
   * @throws ExBibException               just in case
   */
  void addFunction( String name, Code body, Locator locator )
      throws ExBibIllegalValueException,
      ExBibFunctionExistsException,
      ExBibException;

  /**
   * Change the meaning of a function definition. If the function does not
   * exist then an exception is thrown.
   *
   * @param name    the name of the function to change
   * @param body    the new code for the function
   * @param locator the locator
   * @throws ExBibIllegalValueException      in case that the name is
   *                                         {@code null} or empty
   * @throws ExBibFunctionUndefinedException in case that the function isn't
   *                                         defined yet
   */
  void changeFunction( String name, Code body, Locator locator )
      throws ExBibIllegalValueException,
      ExBibFunctionUndefinedException;

  /**
   * Getter for local integers. The given arguments are added to the values
   * already stored.
   *
   * @return the list of integers
   * @throws ExBibIllegalValueException   in case that the name is
   *                                      {@code null} or empty
   * @throws ExBibFunctionExistsException in case that the named function
   *                                      already exists
   */
  List<String> getEntryIntegers()
      throws ExBibIllegalValueException,
      ExBibFunctionExistsException;

  /**
   * Getter for local strings.
   *
   * @return the entries
   */
  List<String> getEntryStrings();

  /**
   * Getter for function code. If the requested function is not defined then
   * {@code null} is returned.
   *
   * @param name the name of the function
   * @return the function definition or {@code null}
   */
  Code getFunction( String name );

  /**
   * Getter for the list of defined functions.
   *
   * @return the list of functions defined
   */
  List<String> getFunctionNames();

  /**
   * Getter for the list of global integers.
   *
   * @return the list of global integers
   */
  List<String> getIntegers();

  /**
   * Getter for an option.
   *
   * @param name the key
   * @return the option value or {@code null}
   */
  Token getOption( String name );

  /**
   * Getter for an option.
   *
   * @param key    the key
   * @param string the value in case that the option is not defined
   * @return the option value or the default fallback
   */
  Token getOption( String key, Token string );

  /**
   * Getter for the names of global string variables.
   *
   * @return the list of strings
   */
  List<String> getStrings();

  /**
   * Pop an element from the stack. If the stack is empty an exception is
   * thrown.
   *
   * @param locator the locator
   * @return the top of stack
   * @throws ExBibStackEmptyException in case that no element is left to pop
   */
  Token pop( Locator locator ) throws ExBibStackEmptyException;

  /**
   * Pop an integer literal from the stack. If the stack is empty or the
   * element isn't an integer then an error condition is raised.
   *
   * @param locator the locator
   * @return the top of stack
   * @throws ExBibStackEmptyException    in case that no element is left to pop
   * @throws ExBibMissingNumberException in case that no integer is found
   */
  TInteger popInteger( Locator locator )
      throws ExBibStackEmptyException,
      ExBibMissingNumberException;

  /**
   * Pop an integer literal from the stack. If the stack is empty or the
   * element isn't an string then an error condition is raised.
   *
   * @param locator the locator
   * @return the top of stack
   * @throws ExBibStackEmptyException    in case that no element is left to pop
   * @throws ExBibMissingStringException in case that no integer is found
   */
  TString popString( Locator locator )
      throws ExBibStackEmptyException,
      ExBibMissingStringException;

  /**
   * Pop an element from the stack. If the stack is empty then
   * {@code null} is returned.
   *
   * @return the top of stack or {@code null}
   */
  Token popUnchecked();

  /**
   * Push the given item onto the stack.
   *
   * @param token the token to push
   */
  void push( Token token );

  /**
   * Registers an observer.
   *
   * @param name     the name of the action to register for
   * @param observer the observer to invoke upon the action
   * @throws NotObservableException in case that the name does not correspond
   *                                to an observable action
   */
  void registerObserver( String name, Observer observer )
      throws NotObservableException;

  /**
   * Setter for the entry names.
   *
   * @param entries the list of entries
   * @param locator the locator
   * @throws ExBibIllegalValueException   in case that the name is
   *                                      {@code null} or empty
   * @throws ExBibFunctionExistsException in case that the named function
   *                                      already exists
   * @throws ExBibException               just in case
   */
  void setEntries( List<String> entries, Locator locator )
      throws ExBibIllegalValueException,
      ExBibFunctionExistsException,
      ExBibException;

  /**
   * Setter for the local integers. The given arguments are added to the
   * values already stored.
   *
   * @param integers the list of integers
   * @param locator  the locator
   * @throws ExBibException in case that a name is given which is already
   *                        defined
   */
  void setEntryIntegers( List<String> integers, Locator locator )
      throws ExBibException;

  /**
   * Setter for local strings. The given arguments are added to the values
   * already stored.
   *
   * @param strings the list of strings
   * @param locator the locator
   * @throws ExBibIllegalValueException   in case that the name is
   *                                      {@code null} or empty
   * @throws ExBibFunctionExistsException in case that the named function
   *                                      already exists
   * @throws ExBibException               in case of an error
   */
  void setEntryStrings( List<String> strings, Locator locator )
      throws ExBibIllegalValueException,
      ExBibFunctionExistsException,
      ExBibException;

  /**
   * Setter for global integers. The given arguments are added to the values
   * already stored.
   *
   * @param list    the list of global integers
   * @param locator the locator
   * @throws ExBibException in case that a name is given which is already
   *                        defined
   */
  void setIntegers( TokenList list, Locator locator ) throws ExBibException;

  /**
   * Setter for the names of global string variables. The given names are
   * added to the ones already defined.
   *
   * @param list    the list of additional string names
   * @param locator the locator
   * @throws ExBibIllegalValueException   in case that the name is
   *                                      {@code null} or empty
   * @throws ExBibFunctionExistsException in case that the named function
   *                                      already exists
   * @throws ExBibException               just in case
   */
  void setStrings( TokenList list, Locator locator )
      throws ExBibIllegalValueException,
      ExBibFunctionExistsException,
      ExBibException;

  /**
   * This method should be invoked for every step in the execution to allow
   * the step observers to be informed.
   *
   * @param obj the object which steps
   */
  void step( Object obj );

}
