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

import org.extex.backend.documentWriter.PdftexSupport;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.pdftex.PdfAnnotation;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive <code>\pdfannot</code>.
 *
 * <doc name="pdfannot">
 * <h3>The Primitive <tt>\pdfannot</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;pdfannot&rang;
 *       &rarr; <tt>\pdfannot</tt> ... </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \pdfannot {abc.png}  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4791 $
 */
public class Pdfannot extends AbstractPdftexCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Pdfannot(final String name) {

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

        PdftexSupport writer = ensurePdftex(context, typesetter);

        Dimen width = null;
        Dimen height = null;
        Dimen depth = null;

        for (;;) {
            if (source.getKeyword(context, "width")) {
                width = Dimen.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "height")) {
                height = Dimen.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "depth")) {
                depth = Dimen.parse(context, source, typesetter);
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

        String annotation = source.scanTokensAsString(context, getName());
        PdfAnnotation a =
                writer.getAnnotation(new RuleNode(width, height, depth, //
                    context.getTypesettingContext(), true), annotation);

        typesetter.add(a);
        prefix.clearImmediate();
    }

}
