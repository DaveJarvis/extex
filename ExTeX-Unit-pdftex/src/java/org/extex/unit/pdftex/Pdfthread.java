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

package org.extex.unit.pdftex;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.DimenParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.pdftex.PdfThread;
import org.extex.unit.pdftex.util.id.IdSpec;

/**
 * This class provides an implementation for the primitive <code>\pdfthread</code>.
 *
 * <doc name="pdfthread">
 * <h3>The Primitive <tt>\pdfthread</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;pdfthread&rang;
 *       &rarr; <tt>\pdfthread</tt> ... </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \pdfthread {abc.png}  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4791 $
 */
public class Pdfthread extends AbstractPdftexCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Pdfthread(final String name) {

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
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException,
                ConfigurationException {

        ensurePdftex(context, typesetter);
        String attr = null;
        Dimen width = null;
        Dimen height = null;
        Dimen depth = null;

        for (;;) {
            if (source.getKeyword(context, "width")) {
                width = DimenParser.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "height")) {
                height = DimenParser.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "depth")) {
                depth = DimenParser.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "attr")) {
                attr = source.scanTokensAsString(context, getName());
            } else {
                break;
            }
        }

        if (width == null) {
            width = Dimen.ONE_PT; //TODO gene:provide correct default
        }
        if (height == null) {
            height = Dimen.ONE_PT; //TODO gene:provide correct default
        }
        if (depth == null) {
            depth = Dimen.ONE_PT; //TODO gene:provide correct default
        }

        IdSpec id = IdSpec.parseIdSpec(context, source, typesetter, getName());

        PdfThread thread =
                new PdfThread(new RuleNode(width, height, depth, context
                    .getTypesettingContext(), true), attr, id);

        typesetter.add(thread);

        prefix.clearImmediate();
    }

}
