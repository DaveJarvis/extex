/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.listMaker;

import org.extex.core.Locator;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingMathException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.ListManager;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.InvalidSpacefactorException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterHelpingException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.listMaker.math.DisplaymathListMaker;
import org.extex.typesetter.listMaker.math.MathListMaker;

/**
 * This abstract class provides some methods common to all ListMakers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractListMaker implements TokenDelegateListMaker {

  /**
   * The field {@code locator} contains the locator pointing to the start.
   */
  private final Locator locator;

  /**
   * The field {@code manager} contains the manager to ask for global
   * changes.
   */
  private final ListManager manager;

  /**
   * Creates a new object.
   *
   * @param theManager the manager to ask for global changes
   * @param locator    the locator
   */
  public AbstractListMaker( ListManager theManager, Locator locator ) {

    this.manager = theManager;
    this.locator = locator;
  }

  /**
   * Getter for the localizer.
   *
   * @return the localizer
   */
  protected Localizer getLocalizer() {

    return LocalizerFactory.getLocalizer( this.getClass() );
  }

  /**
   * Getter for the locator.
   *
   * @return the locator
   * @see org.extex.typesetter.ListMaker#getLocator()
   */
  @Override
  public Locator getLocator() {

    return locator;
  }

  /**
   * Getter for manager.
   *
   * @return the manager.
   */
  public ListManager getManager() {

    return manager;
  }

  /**
   * Getter for the current mode.
   *
   * @return the mode which is one of the values defined in
   * {@link org.extex.typesetter.Mode Mode}.
   * @see org.extex.typesetter.ListMaker#getMode()
   */
  @Override
  public abstract Mode getMode();

  /**
   * Getter for the localizer.
   *
   * @return the localizer
   */
  protected Localizer getMyLocalizer() {

    return LocalizerFactory.getLocalizer( AbstractListMaker.class );
  }

  /**
   * Getter for the previous depth parameter.
   *
   * @return the previous depth
   * @throws TypesetterUnsupportedException in case of an error
   * @see org.extex.typesetter.ListMaker#getPrevDepth()
   */
  @Override
  public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

    throw new TypesetterUnsupportedException();
  }

  @Override
  public long getSpacefactor() throws TypesetterUnsupportedException {

    throw new TypesetterUnsupportedException();
  }

  @Override
  public void leftBrace() {

    // noop
  }

  /**
   * org.extex.interpreter.TokenSource,
   * org.extex.scanner.type.token.Token)
   */
  @Override
  public void mathShift( Context context, TokenSource source, Token t )
      throws TypesetterException,
      ConfigurationException,
      HelpingException {

    Token next = source.getToken( context );

    if( next == null ) {
      throw new TypesetterException(
          new MissingMathException( t.toString() ) );
    }
    else if( next.isa( Catcode.MATHSHIFT ) ) {
      manager.push(
          new DisplaymathListMaker( manager, source.getLocator() ) );
      source.push( context.getToks( "everydisplay" ) );
    }
    else {
      source.push( next );
      manager.push( new MathListMaker( manager, source.getLocator() ) );
      source.push( context.getToks( "everymath" ) );
    }
    context.setCount( "fam", -1, false );

  }

  /**
   * Notification method to deal the case that a right brace has been
   * encountered.
   *
   * @throws TypesetterException in case of an error
   * @see org.extex.typesetter.ListMaker#rightBrace()
   */
  @Override
  public void rightBrace() throws TypesetterException {

    // noop
  }

  /**
   * Setter for the previous depth parameter.
   *
   * @param pd the previous depth parameter
   * @throws TypesetterUnsupportedException in case of an error
   * @see org.extex.typesetter.ListMaker#setPrevDepth(org.extex.core.dimen.FixedDimen)
   */
  @Override
  public void setPrevDepth( FixedDimen pd )
      throws TypesetterUnsupportedException {

    throw new TypesetterUnsupportedException();
  }

  /**
   * Setter for the space factor.
   *
   * @param sf the space factor to set
   * @throws TypesetterUnsupportedException in case of an error
   * @throws InvalidSpacefactorException    in case of an invalid space factor
   * @see org.extex.typesetter.ListMaker#setSpacefactor(org.extex.core.count.FixedCount)
   */
  @Override
  public void setSpacefactor( FixedCount sf )
      throws TypesetterUnsupportedException,
      InvalidSpacefactorException {

    throw new TypesetterUnsupportedException();
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
   * org.extex.scanner.type.token.Token)
   */
  @Override
  public void subscriptMark( Context context, TokenSource source,
                             Typesetter typesetter, Token token )
      throws TypesetterException,
      HelpingException {

    throw new TypesetterException(
        new MissingMathException( token.toString() ) );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
   * org.extex.scanner.type.token.Token)
   */
  @Override
  public void superscriptMark( Context context, TokenSource source,
                               Typesetter typesetter, Token token )
      throws TypesetterException,
      HelpingException {

    throw new TypesetterException(
        new MissingMathException( token.toString() ) );
  }

  /**
   * org.extex.interpreter.TokenSource,
   * org.extex.scanner.type.token.Token)
   */
  @Override
  public void tab( Context context, TokenSource source, Token token )
      throws TypesetterException,
      ConfigurationException {

    throw new TypesetterHelpingException( getMyLocalizer(),
                                          "TTP.MisplacedTabMark",
                                          token.toString() );
  }

  /**
   * Get the string representation of this object for debugging purposes.
   *
   * @return the string representation
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    String name = getClass().getName();
    return name.substring( name.lastIndexOf( '.' ) + 1 );
  }

}
