/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.box;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \setbox}.
 *
 * <p>The Primitive {@code \setbox}</p>
 * <p>
 * The primitive {@code \setbox} takes a key for a box register and assigns the
 * following box to this register.
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>
 * The primitive is an assignment. This means that it respects
 * {@code \globaldefs}. The treatment of {@code \afterassignment} is special
 * The token stored for after assignment are inserted just after the box as been
 * opened.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;setbox&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \setbox} {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context, TokenSource, Typesetter, CodeToken)
 *        &lang;box register name&rang;} &lang;box&rang;
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \setbox0\hbox{abc}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Setbox extends AbstractAssignment {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Return the key (the number) for the box register.
   *
   * <p>A Box Register Name</p>
   * <p>
   * A box register name determines under which key a box register can be
   * addressed. In TeX this used to be a positive number only. This has been
   * extended to allow also a token list in braces.
   * </p>
   * <p>
   * The alternative is controlled by the count register
   * {@code \register.max}. The following interpretation of the value of this
   * count is used:
   * </p>
   * <ul>
   * <li>If the value of this count register is negative then a arbitrary
   * non-negative number is allowed as register name as well as any list of
   * tokens enclosed in braces.</li>
   * <li>If the value of this count register is not-negative then a only a
   * non-negative number is allowed as register name which does not exceed the
   * value of the count register.</li>
   * </ul>
   * <p>
   * The value of the count register {@code \register.max} is set differently
   * for various configurations of εχTeX:
   * </p>
   * <ul>
   * <li>TeX uses the value 255.</li>
   * <li>ε-TeX uses the value 32767.</li>
   * <li> Omega uses the value 65536.</li>
   * <li>εχTeX uses the value -1.</li>
   * </ul>
   * <p>
   * Note that the register name {@code \register.max} contains a period.
   * Thus it can normally not be entered easily since the catcode of the
   * period is OTHER but needs to be LETTER. Thus you have to use a
   * temporarily reassigned category code (see
   * {@link org.extex.unit.tex.register.CatcodePrimitive {@code \catcode}})
   * or use {@link org.extex.unit.tex.macro.Csname {@code \csname}}.
   * </p>
   *
   * <p>Syntax</p>
   *
   * <pre class="syntax">
   *   &lang;box register name&rang;
   *       &rarr; {@linkplain
   *        org.extex.interpreter.TokenSource#scanTokens(Context, boolean, boolean, CodeToken)
   *        &lang;tokens&rang;}
   *        | {@linkplain org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;}  </pre>
   *
   * <p>Examples</p>
   *
   * <pre class="TeXSample">
   *  123
   *  {abc}
   * </pre>
   *
   * @param context    the interpreter context to use
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @param primitive  the name of the primitive for error messages
   * @return the key for the box register
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  public static String getKey( Context context, TokenSource source,
                               Typesetter typesetter, CodeToken primitive )
      throws HelpingException,
      TypesetterException {

    String key =
        source.scanRegisterName( context, source, typesetter, primitive );

    if( Namespace.SUPPORT_NAMESPACE_BOX ) {
      String namespace = context.getNamespace();
      return ("".equals( namespace ) ? key : namespace + "#" + key);
    }
    return key;
  }

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Setbox( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = Setbox.getKey( context, source, typesetter, getToken() );
    source.getOptionalEquals( context );
    Flags f = prefix.copy();
    prefix.clear();
    Box box = source.getBox( prefix, context, typesetter,
                             context.getAfterassignment() );
    context.setBox( key, box, f.clearGlobal() );
    context.setAfterassignment( null );
    prefix.set( f );
  }

}
