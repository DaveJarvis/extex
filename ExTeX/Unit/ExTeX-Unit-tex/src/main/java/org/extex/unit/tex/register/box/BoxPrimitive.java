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
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\box</code>.
 * 
 * <doc name="box"> <h3>The Primitive <tt>\box</tt></h3>
 * <p>
 * The primitive <tt>\box</tt> inserts the contents of the named box register at
 * the current position. In addition the box register is cleared.
 * </p>
 * <p>
 * If the primitive is used on the right hand side of a box assignment then the
 * box is cleared and the former contents is used for the assignment.
 * </p>
 * 
 * <h4>Syntax</h4> The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;box&rang;
 *      &rarr; <tt>\box</tt> {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,CodeToken)
 *        &lang;box register name&rang;} </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \box42  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class BoxPrimitive extends AbstractCode implements Boxable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public BoxPrimitive(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = Setbox.getKey(context, source, typesetter, getToken());
        Box box = context.getBox(key);
        if (box != null) {
            typesetter.add(box.getNodes());
            box.clear();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.box.Boxable#getBox(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    @Override
    public Box getBox(Context context, TokenSource source,
            Typesetter typesetter, Token insert)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        String key = Setbox.getKey(context, source, typesetter, getToken());
        Box b = context.getBox(key);
        Box box = new Box(b);
        if (b != null) {
            b.clear();
        }
        if (insert != null) {
            source.push(insert);
        }
        return box;
    }

}
