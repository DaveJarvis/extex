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

package org.extex.unit.tex.conditional;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.scanner.CountParser;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive <code>\ifnum</code>.
 * 
 * <doc name="ifnum">
 * <h3>The Primitive <tt>\ifnum</tt></h3>
 * <p>
 * The primitive <tt>\ifnum</tt> provides a conditional which compares two
 * numerical values. The comparison for equality, greater, and less are
 * possible.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ifnum&rang;
 *      &rarr; <tt>\ifnum</tt> {@linkplain
 *        org.extex.scanner.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;} &lang;op&rang; {@linkplain
 *        org.extex.scanner.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;} &lang;true text&rang; <tt>\fi</tt>
 *      | <tt>\ifnum</tt> {@linkplain
 *        org.extex.scanner.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;} &lang;op&rang; {@linkplain
 *        org.extex.scanner.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;} &lang;true text&rang; <tt>\else</tt> &lang;false text&rang; <tt>\fi</tt>
 *
 *    &lang;op&rang;
 *      &rarr; [&lt;]
 *      | [=]
 *      | [&gt;]  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \ifnum\count0&gt;42 abc \fi  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4439 $
 */
public class Ifnum extends AbstractIf {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Ifnum(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.conditional.AbstractIf#conditional(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long value = CountParser.scanInteger(context, source, typesetter);
        Token rel = source.getToken(context);
        if (rel == null) {
            throw new EofException(printableControlSequence(context));
        }
        if (rel.getCatcode() == Catcode.OTHER) {
            switch (rel.getChar().getCodePoint()) {
                case '<':
                    return (value < CountParser.scanInteger(context, source,
                        typesetter));
                case '=':
                    return (value == CountParser.scanInteger(context, source,
                        typesetter));
                case '>':
                    return (value > CountParser.scanInteger(context, source,
                        typesetter));
                default:
                    // fall-through
            }
        }

        throw new HelpingException(getLocalizer(), "TTP.IllegalIfnumOp",
            printableControlSequence(context));
    }

}
