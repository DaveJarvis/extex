/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional.analyze;

import org.extex.core.count.Count;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.etex.conditional.Ifcsname;
import org.extex.unit.etex.conditional.Ifdefined;
import org.extex.unit.etex.conditional.Iffontchar;
import org.extex.unit.tex.conditional.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides an implementation for the primitive
 * {@code \currentiftype}.
 *
 * <p>The Primitive {@code \currentiftype}</p>
 * <p>
 * The primitive {@code \currentiftype} is an internal count register. It
 * returns an indication of the conditional currently in use. If no conditional
 * is active then {@code 0} is returned. The following table lists the return
 * values for the different types of conditionals:
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td>{@code /if}</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>{@code /ifcat}</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td>{@code /ifnum}</td>
 * <td>3</td>
 * </tr>
 * <tr>
 * <td>{@code /ifdim}</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td>{@code /ifodd}</td>
 * <td>5</td>
 * </tr>
 * <tr>
 * <td>{@code /ifvmode}</td>
 * <td>6</td>
 * </tr>
 * <tr>
 * <td>{@code /ifhmode}</td>
 * <td>7</td>
 * </tr>
 * <tr>
 * <td>{@code /ifmmode}</td>
 * <td>8</td>
 * </tr>
 * <tr>
 * <td>{@code /ifinner}</td>
 * <td>9</td>
 * </tr>
 * <tr>
 * <td>{@code /ifvoid}</td>
 * <td>10</td>
 * </tr>
 * <tr>
 * <td>{@code /ifhbox}</td>
 * <td>11</td>
 * </tr>
 * <tr>
 * <td>{@code /ifvbox}</td>
 * <td>12</td>
 * </tr>
 * <tr>
 * <td>{@code /ifx}</td>
 * <td>13</td>
 * </tr>
 * <tr>
 * <td>{@code /ifeof}</td>
 * <td>14</td>
 * </tr>
 * <tr>
 * <td>{@code /iftrue}</td>
 * <td>15</td>
 * </tr>
 * <tr>
 * <td>{@code /iffalse}</td>
 * <td>16</td>
 * </tr>
 * <tr>
 * <td>{@code /ifcase}</td>
 * <td>17</td>
 * </tr>
 * <tr>
 * <td>{@code /ifdefined}</td>
 * <td>18</td>
 * </tr>
 * <tr>
 * <td>{@code /ifcsname}</td>
 * <td>19</td>
 * </tr>
 * <tr>
 * <td>{@code /iffontchar}</td>
 * <td>20</td>
 * </tr>
 * </table>
 * <p>
 * The value returned by the primitive is negated if the expansion appears in
 * the else branch.
 * </p>
 *
 * <p>Syntax</p>
 *
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 *
 * <pre class="syntax">
 *    &lang;currentiftype&rang;
 *     &rarr; {@code \currentiftype} </pre>
 *
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \count0=\currentiftype  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Currentiftype extends AbstractCode
    implements
    CountConvertible,
    Theable {

  /**
   * The field {@code map} contains the map from \if implementations to long
   * values.
   */
  private static final Map<Class<?>, Count> MAP =
      new HashMap<Class<?>, Count>();

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  {
    MAP.put( If.class, new Count( 1 ) );
    MAP.put( Ifcat.class, new Count( 2 ) );
    MAP.put( Ifnum.class, new Count( 3 ) );
    MAP.put( Ifdim.class, new Count( 4 ) );
    MAP.put( Ifodd.class, new Count( 5 ) );
    MAP.put( Ifvmode.class, new Count( 6 ) );
    MAP.put( Ifhmode.class, new Count( 7 ) );
    MAP.put( Ifmmode.class, new Count( 8 ) );
    MAP.put( Ifinner.class, new Count( 9 ) );
    MAP.put( Ifvoid.class, new Count( 10 ) );
    MAP.put( Ifhbox.class, new Count( 11 ) );
    MAP.put( Ifvbox.class, new Count( 12 ) );
    MAP.put( Ifx.class, new Count( 13 ) );
    MAP.put( Ifeof.class, new Count( 14 ) );
    MAP.put( Iftrue.class, new Count( 15 ) );
    MAP.put( Iffalse.class, new Count( 16 ) );
    MAP.put( Ifcase.class, new Count( 17 ) );
    MAP.put( Ifdefined.class, new Count( 18 ) );
    MAP.put( Ifcsname.class, new Count( 19 ) );
    MAP.put( Iffontchar.class, new Count( 20 ) );
  }

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Currentiftype( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Conditional conditional = context.getConditional();
    if( conditional == null ) {
      return 0;
    }
    Count l = MAP.get( conditional.getPrimitive().getClass() );
    return (l == null ? 0 :
        conditional.isNeg() ? -l.getValue() : l.getValue());
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws CatcodeException,
      HelpingException,
      TypesetterException {

    return context.getTokenFactory().toTokens(
        convertCount( context, source, typesetter ) );
  }

}
