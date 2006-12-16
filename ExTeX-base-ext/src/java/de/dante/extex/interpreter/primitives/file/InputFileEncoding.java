/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.file;

import java.io.FileNotFoundException;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.scanner.TokenStream;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.configuration.exception.ConfigurationException;


/**
 * This class provides an implementation for the
 * primitive <code>\inputfileencoding</code>.
 * It use the given encoding for opening and not the
 * encoding in <code>\inputencoding</code>.
 * The filename can have space in his name.
 *
 * Example:
 *
 * <pre>
 * \inputfileencoding{encoding}{file.name}
 * </pre>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class InputFileEncoding extends InputFile {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public InputFileEncoding(final String name) {

        super(name);
    }

    /**
     * Scan the encoding and file name and open the file in the tokenizer
     * stream.
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

        String encoding = source.scanTokensAsString(context, getName());
        String name = scanFileName(context, source);
        TokenStreamFactory factory = source.getTokenStreamFactory();

        try {
            TokenStream stream = factory.newInstance(name, "tex", encoding);
            if (stream != null) {
                source.addStream(stream);
            } else {
                throw new InterpreterException(new FileNotFoundException(name));
            }
        } catch (ConfigurationException e) {
            throw new InterpreterException(e);
        }
    }
}