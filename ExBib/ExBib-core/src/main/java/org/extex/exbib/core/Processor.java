/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * This file is part of ExBib a BibTeX compatible database.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exbib.core;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exbib.core.bst.Bibliography;
import org.extex.exbib.core.bst.Code;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.bst.node.impl.TokenList;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This interface describes the contract of all processors, i.e. the central
 * class containing the interpreter for the B<small>IB</small>T<sub>E</sub>X
 * programming language.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public interface Processor extends Bibliography, Configurable {

    /**
     * Add a command to the list of commands to execute. The commands constitute
     * the main program. All commands in the command list are executed in turn
     * when the processor is run.
     * 
     * @param command the command to add
     */
    void addCommand(Command command);

    /**
     * Define a new function if not already defined in the processor context. A
     * function can be made undefined by specifying the code <code>null</code>
     * to it.
     * 
     * @param name the name of the function
     * @param body the Code associated with the function
     * @param locator The locator
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    void addFunction(String name, Code body, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

    /**
     * Store an additional <tt>STRING</tt> in the database. To delete a
     * <tt>STRING</tt> the value <code>null</code> can be used.
     * 
     * @param name the name of the macro to add
     * @param value the value as Token
     */
    void addMacro(String name, Token value);

    /**
     * Change the meaning of a function definition. If the function does not
     * exist then an exception is thrown.
     * 
     * @param name the name of the function to change
     * @param body the new code for the function
     * @param locator the locator
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionUndefinedException in case that the function isn't
     *         defined yet
     */
    void changeFunction(String name, Code body, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionUndefinedException;

    /**
     * Getter for an Iterator on the commands.
     * 
     * @return the iterator on commands
     */
    public Iterator<Command> commandsIterator();

    /**
     * Get the original cite key for a given key. I.e. the casing might be
     * different.
     * 
     * @param key the cite key
     * 
     * @return the original cite key
     */
    String getCite(String key);

    /**
     * Getter for the database.
     * 
     * @return the database
     */
    DB getDB();

    /**
     * Getter for the entries.
     * 
     * @return the list of entries
     */
    List<String> getEntries();

    /**
     * Getter for local integers. The given arguments are added to the values
     * already stored.
     * 
     * @return the list of integers
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
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
     * <code>null</code> is returned.
     * 
     * @param name the name of the function
     * 
     * @return the function definition or <code>null</code>
     */
    Code getFunction(String name);

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
     * Getter for the logger.
     * 
     * @return the logger
     */
    Logger getLogger();

    /**
     * Getter for a certain macro. If the requested macro is not defined
     * <code>null</code> is returned.
     * 
     * @param name the name of the macro to search for
     * 
     * @return the expanded value of the macro or <code>null</code> if none
     *         has been found.
     */
    String getMacro(String name);

    /**
     * Getter for the list of all defined macro names
     * 
     * @return the list of macro names
     */
    List<String> getMacroNames();

    /**
     * Getter for the number of warnings issued.
     * 
     * @return the umber of warnings
     */
    public long getNumberOfWarnings();

    /**
     * Getter for the output writer.
     * 
     * @return the output writer
     */
    Writer getOutWriter();

    /**
     * Getter for the names of global string variables.
     * 
     * @return the list of strings
     */
    List<String> getStrings();

    /**
     * Load all databases named in the processor context in turn.
     * 
     * @throws ExBibException in case that something went wrong
     * @throws ConfigurationException in case that the configuration is invalid
     * @throws FileNotFoundException in case that the requested file or one of
     *         the subsequent files (@input) could not be found
     */
    void loadDatabases()
            throws ExBibException,
                FileNotFoundException,
                ConfigurationException;

    /**
     * Pop an element from the stack. If the stack is empty an exception is
     * thrown.
     * 
     * @param locator the locator
     * 
     * @return the top of stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     */
    Token pop(Locator locator) throws ExBibStackEmptyException;

    /**
     * Pop an integer literal from the stack. If the stack is empty or the
     * element isn't an integer then an error condition is raised.
     * 
     * @param locator the locator
     * 
     * @return the top of stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     * @throws ExBibMissingNumberException in case that no integer is found
     */
    TInteger popInteger(Locator locator)
            throws ExBibStackEmptyException,
                ExBibMissingNumberException;

    /**
     * Pop an integer literal from the stack. If the stack is empty or the
     * element isn't an string then an error condition is raised.
     * 
     * @param locator the locator
     * 
     * @return the top of stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     * @throws ExBibMissingStringException in case that no integer is found
     */
    TString popString(Locator locator)
            throws ExBibStackEmptyException,
                ExBibMissingStringException;

    /**
     * Pop an element from the stack. If the stack is empty then
     * <code>null</code> is returned.
     * 
     * @return the top of stack or <codeC>null</code>
     */
    Token popUnchecked();

    /**
     * Run the procedures stored in the processor context. For each procedure
     * the observers `run' are called before it is executed.
     * 
     * @param outWriter the writer output is sent to
     * @param logger the logger
     * 
     * @return the number of warnings issued
     * 
     * @throws ExBibException in case of an error
     */
    long process(Writer outWriter, Logger logger) throws ExBibException;

    /**
     * Push the given item onto the stack.
     * 
     * @param token the token to push
     */
    void push(Token token);

    /**
     * Registers an observer.
     * 
     * @param name the name of the action to register for
     * @param observer the observer to invoke upon the action
     * 
     * @throws NotObservableException in case that the name does not correspond
     *         to an observable action
     */
    void registerObserver(String name, Observer observer)
            throws NotObservableException;

    /**
     * Setter for the database.
     * 
     * @param db the database to use
     */
    public void setDB(DB db);

    /**
     * Setter for the entry names.
     * 
     * @param entries the list of entries
     * @param locator the locator
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    void setEntries(List<String> entries, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

    /**
     * Setter for the local integers. The given arguments are added to the
     * values already stored.
     * 
     * @param integers the list of integers
     * @param locator the locator
     * @throws ExBibException in case that a name is given which is already
     *         defined
     */
    void setEntryIntegers(List<String> integers, Locator locator)
            throws ExBibException;

    /**
     * Setter for local strings. The given arguments are added to the values
     * already stored.
     * 
     * @param strings the list of strings
     * @param locator the locator
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    void setEntryStrings(List<String> strings, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

    /**
     * Setter for global integers. The given arguments are added to the values
     * already stored.
     * 
     * @param list the list of global integers
     * @param locator the locator
     * 
     * @throws ExBibException in case that a name is given which is already
     *         defined
     */
    void setIntegers(TokenList list, Locator locator) throws ExBibException;

    /**
     * Setter for the names of global string variables. The given names are
     * added to the ones already defined.
     * 
     * @param list the list of additional string names
     * @param locator the locator
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    void setStrings(TokenList list, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException;

    /**
     * This method should be invoked for every step in the execution to allow
     * the step observers to be informed.
     * 
     * @param obj the object which steps
     */
    void step(Object obj);

    /**
     * This method can be used to issue a warning message.
     * 
     * @param message the warning message to issue
     */
    void warning(String message);

}
