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

package org.extex.unit.tex.table;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.AlignmentList;
import org.extex.typesetter.type.NodeList;

/**
 * This class provides an implementation for the primitive <code>\cr</code>.
 * 
 * <doc name="cr">
 * <h3>The Primitive <tt>\cr</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;cr&rang;
 *       &rarr; <tt>\cr</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \cr  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Cr extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060306L;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public Cr(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        NodeList noalign = null;
        ListMaker maker = typesetter.getListMaker();
        if (maker instanceof AlignmentList) {
            Token token = source.getToken(context); // TODO gene: respect
                                                    // protected
            if (token instanceof CodeToken) {
                Code code = context.getCode((CodeToken) token);
                if (code instanceof Noalign) {
                    noalign =
                            ((Noalign) code).exec(context, source, typesetter,
                                token);
                } else {
                    source.push(token);
                }
            } else {
                source.push(token);
            }

            ((AlignmentList) maker).cr(context, source, noalign);
        } else {
            throw new HelpingException(getLocalizer(), "TTP.MisplacedCrSpan",
                printableControlSequence(context));
        }
    }

}
