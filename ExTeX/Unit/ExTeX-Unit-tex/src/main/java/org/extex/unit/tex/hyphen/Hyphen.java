/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.hyphen;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This class provides an implementation for the primitive {@code \-}.
 *
 * <p>The Primitive {@code \-}</p>
 * <p>
 * The primitive {@code \-} inserts a soft hyphenation into the current list.
 * The effect is that the current position is considered as point to insert a
 * hyphenation mark and break the line here.
 * </p>
 * <p>
 * TeX has another mechanism for describing conditional text
 * insertions when line breaking appears at a certain place. Those are
 * associated with the primitive {@link org.extex.unit.tex.hyphen.Discretionary
 * {@code \discretionary}}. In this context the primitive {@code \-} is an
 * abbreviation for {@code \discretionary{-}{}{}}.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;hyphen&rang;
 *       &rarr; {@code \-}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    abc\-def  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Hyphen extends AbstractCode {

  /**
   * The field {@code HYPHEN} contains the Unicode character for the hyphen.
   */
  private static final UnicodeChar HYPHEN = UnicodeChar.get( '-' );

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Hyphen( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    TypesettingContext tc = context.getTypesettingContext();
    NodeList hyphen = new HorizontalListNode( new CharNode( tc, HYPHEN ) );

    typesetter.add( new DiscretionaryNode( hyphen, null, null ) );
  }

}
