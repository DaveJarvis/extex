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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.exception.ExBibImmutableException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.bst.token.impl.*;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code set$}
 * <p>
 * This function assigns a value to a variable or field. It takes two arguments
 * from the stack. The first argument is the name of the target. In general it
 * needs to be quoted. The second argument is the appropriate value.
 * </p>
 * <img src="doc-files/set.png" alt="set">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *     { namesleft #0 &gt; }
 *     {
 *       % some actions
 *
 *       namesleft #1 - 'namesleft :=
 *     }
 *   while$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pops the top two literals and assigns to the first (which must be a
 * global or entry variable) the value of the second.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Set extends AbstractCode {

  /**
   * The field {@code TV} contains the visitor for tokens.
   */
  private static final TokenVisitor TV = new TokenVisitor() {

    /**
     *      java.lang.Object[])
     */
    public void visitBlock( TBlock block, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitChar( TChar c, Object... args ) throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitField( TField field, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Entry entry = (Entry) args[ 1 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      entry.set( var, processor.popString( locator ).getValue() );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitInteger( TInteger integer, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitIntegerOption( TIntegerOption option, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitLiteral( TLiteral literal, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitLocalInteger( TLocalInteger integer, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Entry entry = (Entry) args[ 1 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      entry.setLocal( var, processor.popInteger( locator ).getInt() );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitLocalLocator( TLocalLocator localLocator, Object[] args )
        throws ExBibException {

      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      throw new ExBibImmutableException( var, var, locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitLocalString( TLocalString string, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Entry entry = (Entry) args[ 1 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      entry.setLocal( var, processor.popString( locator ).getValue() );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitQLiteral( TQLiteral qliteral, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitString( TString string, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitStringOption( TStringOption option, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

    /**
     *      java.lang.Object[])
     */
    public void visitTokenList( TokenList list, Object... args )
        throws ExBibException {

      BstProcessor processor = (BstProcessor) args[ 0 ];
      Locator locator = (Locator) args[ 2 ];
      String var = (String) args[ 3 ];
      processor.changeFunction( var, processor.pop( locator ), locator );
    }

  };

  /**
   * Create a new object.
   */
  public Set() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public Set( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    Token arg = processor.pop( locator );

    if( !(arg instanceof TLiteral) ) {
      throw new ExBibMissingLiteralException( arg.toString(), locator );
    }
    String name = arg.getValue();
    Code code = processor.getFunction( name );

    if( code instanceof Token ) {
      ((Token) code).visit( TV, processor, entry, locator, name );
    }
    else {
      processor.changeFunction( name, processor.pop( locator ), locator );
    }
  }

}
