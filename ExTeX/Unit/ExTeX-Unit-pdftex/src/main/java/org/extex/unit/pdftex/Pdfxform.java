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

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.box.Box;
import org.extex.pdf.api.PdftexSupport;
import org.extex.pdf.api.node.PdfXForm;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;

/**
 * This class provides an implementation for the primitive
 * {@code \pdfxform}.
 * 
 * <p>The Primitive {@code \pdfxform}</p>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;pdfxform&rang;
 *       &rarr; {@code \pdfxform} ... </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \pdfxform 42  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Pdfxform extends AbstractPdftexCode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Pdfxform(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws TypesetterException, HelpingException {

        PdftexSupport writer = ensurePdftex(context, typesetter);

        String attr = null;
        String resources = null;

        for (;;) {
            if (source.getKeyword(context, "attr")) {
                attr = source.scanTokensAsString(context, getToken());
            } else if (source.getKeyword(context, "resources")) {
                resources = source.scanTokensAsString(context, getToken());
            } else {
                break;
            }
        }

        long b = source.parseInteger(context, source, typesetter);
        Box box = context.getBox(Long.toString(b));
        NodeList nodes = (box == null ? null : box.getNodes());
        PdfXForm form = writer.getXForm(attr, resources, nodes);

        typesetter.add(form);

        prefix.clearImmediate();
    }

}
