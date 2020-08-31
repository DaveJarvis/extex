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

package org.extex.exbib.core;

import org.extex.exbib.core.bst.Bibliography;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.exception.ConfigurationException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Logger;

/**
 * This interface describes the contract of all processors, i.e. the central
 * class containing the interpreter for the B<small>IB</small><span
 * style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X programming language.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Processor extends Bibliography, Configurable {

  /**
   * Store an additional {@code STRING} in the database. To delete a
   * {@code STRING} the value {@code null} can be used.
   *
   * @param name  the name of the macro to add
   * @param value the value as Token
   */
  void addMacro( String name, Token value );

  /**
   * Get the original cite key for a given key. I.e. the casing might be
   * different.
   *
   * @param key the cite key
   * @return the original cite key
   */
  String getCite( String key );

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
   * Getter for the logger.
   *
   * @return the logger
   */
  Logger getLogger();

  /**
   * Getter for a certain macro. If the requested macro is not defined
   * {@code null} is returned.
   *
   * @param name the name of the macro to search for
   * @return the expanded value of the macro or {@code null} if none has
   * been found.
   */
  String getMacro( String name );

  /**
   * Getter for the list of all defined macro names.
   *
   * @return the list of macro names
   */
  List<String> getMacroNames();

  /**
   * Getter for the number of warnings issued.
   *
   * @return the umber of warnings
   */
  long getNumberOfWarnings();

  /**
   * Getter for the output writer.
   *
   * @return the output writer
   */
  Writer getOutWriter();

  /**
   * Check if the entry type is known.
   *
   * @param type the entry type
   * @return {@code true} iff the entry type is known
   */
  boolean isKnown( String type );

  /**
   * Load all databases named in the processor context in turn.
   *
   * @throws ExBibException         in case that something went wrong
   * @throws ConfigurationException in case that the configuration is invalid
   * @throws FileNotFoundException  in case that the requested file or one of
   *                                the subsequent files (@input) could not
   *                                be found
   */
  void loadDatabases()
      throws ExBibException,
      FileNotFoundException,
      ConfigurationException;

  /**
   * Run the procedures stored in the processor context. For each procedure
   * the observers `run' are called before it is executed.
   *
   * @param outWriter the writer output is sent to
   * @return the number of warnings issued
   * @throws ExBibException in case of an error
   */
  long process( Writer outWriter ) throws ExBibException;

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
   * Setter for the database.
   *
   * @param db the database to use
   */
  void setDb( DB db );

  /**
   * Setter for the logger.
   *
   * @param logger the new logger
   */
  void setLogger( Logger logger );

  /**
   * This method can be used to issue a warning message.
   *
   * @param message the warning message to issue
   */
  void warning( String message );

}
