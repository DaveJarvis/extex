/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.table;

import java.util.List;

import de.dante.extex.i18n.EofHelpingException;
import de.dante.extex.i18n.MissingLeftBraceHelpingException;
import de.dante.extex.interpreter.Flags;
import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.scanner.Catcode;
import de.dante.extex.scanner.Token;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;

/**
 * This class provides an implementation for the primitive <code>\halign</code>.
 *
 * <doc name="halign">
 * <h3>The Primitive <tt>\halign</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 * <p>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;halign&rang;
 *       &rarr; <tt>\halign</tt>  </pre>
 * </p>
 * <p>
 *  Examples:
 *  <pre class="TeXSample">
 *    \halign  </pre>
 * </p>
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Halign extends AbstractAlign {

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Halign(final String name) {

        super(name);
    }

    /**
     * ...
     *
     * @param preamble ...
     * @param width the target width or <code>null</code> for the natural width
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use
     *
     * @throws GeneralException in case of an error
     */
    private void applyPreamble(final List preamble, final Dimen width,
            final Context context, final TokenSource source,
            final Typesetter typesetter) throws GeneralException {

        for (Token t = source.getToken(); t != null; t = source.getToken()) {

            //TODO execute() unimplemented
            throw new RuntimeException("unimplemented");
        }
        throw new EofHelpingException(printableControlSequence(context));
    }

    /**
     * @see de.dante.extex.interpreter.type.Code#execute(
     *      de.dante.extex.interpreter.Flags,
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource,
     *      de.dante.extex.typesetter.Typesetter)
     */
    public boolean execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws GeneralException {

        Dimen width = null;

        if (source.getKeyword("to")) {
            width = new Dimen(context, source);
        }
        Token t = source.getToken();
        if (t == null) {
            throw new EofHelpingException(printableControlSequence(context));
        } else if (t.isa(Catcode.LEFTBRACE)) {
            List preamble = getPreamble(context, source);
            applyPreamble(preamble, width, context, source, typesetter);
        } else {
            throw new MissingLeftBraceHelpingException(
                    printableControlSequence(context));
        }

        return true;
    }

}