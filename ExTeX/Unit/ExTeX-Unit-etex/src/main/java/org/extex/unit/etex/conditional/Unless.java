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

package org.extex.unit.etex.conditional;

import org.extex.core.exception.helping.CantUseAfterException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;
import org.extex.unit.tex.conditional.Ifcase;

/**
 * This class provides an implementation for the primitive <code>\if</code>.
 * 
 * <doc name="unless"> <h3>The Primitive <tt>&#x005c;unless</tt></h3>
 * <p>
 * <strong>Copied of the <logo>&epsilon;-T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> reference</strong>.
 * </p>
 * <p>
 * TeX has, by design, a rather sparse set of conditional
 * primitives: <tt>\ifeof</tt>, <tt>\ifodd</tt>, <tt>\ifvoid</tt>, etc., have no
 * complementary counterparts. Whilst this normally poses no problems since each
 * accepts both a <tt>\then</tt> (implicit) and an <tt>\else</tt> (explicit)
 * part, they fall down when used as the final <tt>\if</tt>... of a
 * <tt>\loop</tt> ... <tt>\if</tt> ... <tt>\repeat</tt> construct, since no
 * <tt>\else</tt> is allowed after the final <tt>\if</tt>....
 * <tt>&#x005c;unless</tt> allows the sense of all Boolean conditionals to be
 * inverted, and thus (for example) <tt>&#x005c;unless</tt> <tt>\ifeof</tt>
 * yields true iff end-of-file has not yet been reached.
 * </p>
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>
 * Examples:
 * 
 * <pre class="TeXSample">
 *    &#x005c;unless\if\x\y not ok \fi  </pre>
 * 
 * </p>
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 * @version $Revision: 4770 $
 */
public class Unless extends AbstractCode implements ExpandableCode, PrefixCode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for the
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Unless(CodeToken token) {

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

        CodeToken token = source.getControlSequence(context, typesetter);
        Code code = context.getCode(token);

        if (!code.isIf() || code instanceof Ifcase) {
            throw new CantUseAfterException(token.toText(context.escapechar()),
                toText(context));
        }

        if (!((AbstractIf) code).conditional(context, source, typesetter)) {
            context.pushConditional(source.getLocator(), true, code, 1, true);
        } else if (AbstractIf.skipToElseOrFi(context, source, getToken())) {
            context.pushConditional(source.getLocator(), true, code, -1, true);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        execute(prefix, context, source, typesetter);
    }

}
