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

package org.extex.unit.tex.file;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;

/**
 * This class provides an implementation for the primitive {@code \input}.
 * It uses the standard encoding (see token register {@code \fileencoding}
 * and {@code extex.encoding}.
 * 
 * <p>The Primitive {@code \input}</p>
 * <p>
 * The primitive {@code \input} takes as argument one file name and opens
 * this file for reading. The following tokens are taken from this input stream.
 * Thus the effect is as if the file contents where copied at the place of the
 * primitive.
 * </p>
 * <p>
 * If the file can not be opened for reading then an error is raised.
 * </p>
 * <p>
 * The primitive also makes provisions that the information in
 * {@code \inputfilename} and {@code \inputlineno} are set properly.
 * </p>
 * 
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 *
 * <pre class="syntax">
 *    &lang;input&rang;
 *       &rarr; {@code \input} &lang;file name&rang; </pre>
 * 
 * <p>Examples</p>

 * <p>
 * The traditional version of the file name parsing allows the following syntax:
 * </p>
 *
 * <pre class="TeXSample">
 *    \input file.name  </pre>
 *
 * <p>
 * If the parsing is not configured to be strict then the following syntax is
 * allowed as well:
 * </p>
 *
 * <pre class="TeXSample">
 *    \input{file.name}  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Input extends AbstractFileCode implements ExpandableCode {

    /**
     * The field {@code FILE_TYPE} contains the file type to create an input
     * stream for.
     */
    private static final String FILE_TYPE = "tex";

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
    public Input(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String name = scanFileName(context, source);
        TokenStream stream =
                source.getTokenStreamFactory().getStream(name, FILE_TYPE,
                    getEncoding(context));
        if (stream == null) {
            throw new HelpingException(getLocalizer(), "TTP.FileNotFound", name);
        }
        source.addStream(stream);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        String name = scanFileName(context, source);
        TokenStream stream =
                source.getTokenStreamFactory().getStream(name, FILE_TYPE,
                    getEncoding(context));
        if (stream == null) {
            throw new HelpingException(getLocalizer(), "TTP.FileNotFound", name);
        }
        source.addStream(stream);
    }

}
