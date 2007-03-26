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
import org.extex.core.count.CountParser;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.DimenParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.pdftex.PdfRefXImage;

/**
 * This class provides an implementation for the primitive
 * <code>\pdfximage</code>.
 * 
 * <doc name="pdfximage">
 * <h3>The Primitive <tt>\pdfximage</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;pdfximage&rang;
 *       &rarr; <tt>\pdfximage</tt> ... </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \pdfximage {abc.png}  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4791 $
 */
public class Pdfximage extends AbstractPdftexCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public Pdfximage(String name) {

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
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws InterpreterException,
                ConfigurationException {

        PdftexSupport writer = ensurePdftex(context, typesetter);

        Dimen width = Dimen.ONE_PT; // TODO gene:provide correct default
        Dimen height = Dimen.ONE_PT; // TODO gene:provide correct default
        Dimen depth = Dimen.ONE_PT; // TODO gene:provide correct default
        String attr = null;
        long page = 0;

        for (;;) {
            if (source.getKeyword(context, "width")) {
                width = DimenParser.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "height")) {
                height = DimenParser.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "depth")) {
                depth = DimenParser.parse(context, source, typesetter);
            } else if (source.getKeyword(context, "attr")) {
                attr = source.scanTokensAsString(context, getName());
            } else if (source.getKeyword(context, "page")) {
                page = CountParser.scanInteger(context, source, typesetter);
            } else {
                break;
            }
        }

        String resource = source.scanTokensAsString(context, getName());

        PdfRefXImage image =
                writer.getXImage(resource, new RuleNode(width, height, depth,
                    context.getTypesettingContext(), true), attr, page, prefix
                    .isImmediate());

        typesetter.add(image);

        prefix.clearImmediate();
    }

}
