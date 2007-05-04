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

package org.extex.unit.tex.typesetter.mark;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.MarkNode;

/**
 * This class provides an implementation for the primitive
 * <code>\marks</code>.
 *
 * <doc name="marks">
 * <h3>The Primitive <tt>\marks</tt></h3>
 * <p>
 *  The primitive <tt>\marks</tt> places a mark in the current list. The
 *  marks on the last page can be retrieved when the page is mounted in the
 *  output routine.
 *  The text stored within the mark is a list of tokens.
 * </p>
 * <p>
 *  The marks can be classified with an identifier. In <logo>eTeX</logo> the
 *  identifier is a number. In <logo>ExTeX</logo> in addition arbitrary
 *  token lists can be used as identifier.
 * </p>
 * <p>
 *  The <tt>\marks0</tt> is equivalent to <tt>\mark</tt>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;marks&rang;
 *      &rarr; <tt>\marks</tt> {@linkplain
 *        org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getKey(Context,TokenSource,Typesetter)
 *        &lang;mark name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \marks123{abc}  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Marks extends AbstractMarkCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060406L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Marks(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        String index = getKey(context, source, typesetter);
        Tokens toks =
                source.scanUnprotectedTokens(context, false, false, getName());
        typesetter.add(new MarkNode(toks, index));
    }

}
