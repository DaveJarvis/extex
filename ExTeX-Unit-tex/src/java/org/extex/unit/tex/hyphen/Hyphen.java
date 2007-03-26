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

package org.extex.unit.tex.hyphen;

import org.extex.core.UnicodeChar;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This class provides an implementation for the primitive <code>\-</code>.
 *
 * <doc name="-">
 * <h3>The Primitive <tt>\-</tt></h3>
 * <p>
 *  The primitive <tt>\-</tt> inserts a soft hyphenation into the current list.
 *  The effect is that the current position is considered as point to insert a
 *  hyphenation mark and break the line here.
 * </p>
 * <p>
 *  <logo>TeX</logo> has another mechanism for describing conditional text
 *  insertions when line breaking appears at a certain place. Those are
 *  associated with the primitive
 *  {@link org.extex.unit.tex.hyphen.Discretionary <tt>\discretionary</tt>}.
 *  In this context the primitive <tt>\-</tt> is an abbreviation for
 *  <tt>\discretionary{-}{}{}</tt>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;hyphen&rang;
 *       &rarr; <tt>\-</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    abc\-def  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Hyphen extends AbstractCode {

    /**
     * The field <tt>HYPHEN</tt> contains the Unicode character for the hyphen.
     */
    private static final UnicodeChar HYPHEN = UnicodeChar.get('-');

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Hyphen(String name) {

        super(name);
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        TypesettingContext tc = context.getTypesettingContext();
        NodeList hyphen = new HorizontalListNode(new CharNode(tc, HYPHEN));

        typesetter.add(new DiscretionaryNode(hyphen, null, null));
    }

}
