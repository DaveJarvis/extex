/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.CodeExpander;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\noexpand</code>.
 *
 * <doc name="noexpand">
 * <h3>The Primitive <tt>\noexpand</tt></h3>
 * <p>
 *  The primitive <tt>\noexpand</tt> prevents a token from being expanded when
 *  collecting the expanded tokens for arguments of macros like <tt>\edef</tt>,
 *  <tt>\xdef</tt>, <tt>\message</tt>, and others.
 * </p>
 * <p>
 *  If the token following the <tt>\noexpand</tt> is not a control sequence or
 *  active character then the primitive does nothing at all. The primitive is
 *  also void if it occurs outside the said expansion context.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;noexpand&rang;
 *      &rarr; <tt>\noexpand</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \noexpand  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Noexpand extends AbstractCode
        implements
            ExpandableCode,
            CodeExpander {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060205L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Noexpand(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

    }

    /**
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

    }

    /**
     * @see org.extex.interpreter.type.CodeExpander#expandCode(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.interpreter.type.tokens.Tokens)
     */
    public void expandCode(final Context context, final TokenSource source,
            final Typesetter typesetter, final Tokens tokens)
            throws InterpreterException {

        Token token = source.getToken(context);
        if (token != null) {
            tokens.add(token);
        }
    }

}
