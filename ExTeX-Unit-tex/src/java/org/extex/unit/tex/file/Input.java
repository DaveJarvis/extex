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

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.scanner.TokenStream;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive <tt>\input</tt>.
 * It uses the standard encoding (see token register <tt>\fileencoding</tt> and
 * <code>extex.encoding</code>.
 *
 * <doc name="input">
 * <h3>The Primitive <tt>\input</tt></h3>
 * <p>
 *  The primitive <tt>\input</tt> takes as argument one file name and opens this
 *  file for reading. The following tokens are taken from this input stream.
 *  Thus the effect is as if the file contents where copied at the place of the
 *  primitive.
 * </p>
 * <p>
 *  If the file can not be opened for reading then an error is raised.
 * </p>
 * <p>
 *  The primitive also makes provisions that the information in
 *  <tt>\inputfilename</tt> and <tt>\inputlineno</tt> are set properly.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;input&rang;
 *       &rarr; <tt>\input</tt> &lang;file name&rang; </pre>
 *
 * <h4>Examples</h4>
 * <p>
 *  The traditional version of the file name parsing allows the following
 *  syntax:
 *  <pre class="TeXSample">
 *    \input file.name  </pre>
 *  If the parsing is not configured to be strict then the following syntax
 *  is allowed as well:
 *  <pre class="TeXSample">
 *    \input{file.name}  </pre>
 * </p>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4441 $
 */
public class Input extends AbstractFileCode {

    /**
     * The field <tt>FILE_TYPE</tt> contains the file type to create an input
     * stream for.
     */
    private static final String FILE_TYPE = "tex";

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 13022007L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Input(final String name) {

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
            throws InterpreterException {

        String name = scanFileName(context, source);
        String encoding = getEncoding(context);
        TokenStreamFactory factory = source.getTokenStreamFactory();

        TokenStream stream = factory.newInstance(name, FILE_TYPE, encoding);
        if (stream == null) {
            throw new HelpingException(getLocalizer(), "TTP.FileNotFound", name);
        }
        source.addStream(stream);
    }

}
