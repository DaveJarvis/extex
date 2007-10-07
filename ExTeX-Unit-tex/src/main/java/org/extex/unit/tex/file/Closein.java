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
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;

/**
 * This class provides an implementation for the primitive <code>\closein</code>.
 * 
 * <doc name="closein">
 * <h3>The Primitive <tt>\closein</tt></h3>
 * <p>
 * The primitive takes one expanded integer argument. This argument denotes a
 * read register which will be closed if it is currently assigned to a file
 * &ndash; with {@link org.extex.unit.tex.file.Openin <tt>\openin</tt>}. If the
 * input file assigned to the given number has not been opened or has been
 * closed before then this primitive simply does nothing.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;closein&rang;
 *       &rarr; <tt>\closein</tt>  {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanInFileKey(Context,TokenSource,Typesetter)
 *        &lang;infile&nbsp;name&rang;} </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \closein5  </pre>
 *  <pre class="TeXSample">
 *    \closein\count120  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4441 $
 */
public class Closein extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Closein(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key =
                AbstractFileCode.scanInFileKey(context, source, typesetter);
        InFile file = context.getInFile(key);

        if (file != null) {
            file.close();
        }
        prefix.clearImmediate(); // strange; does this make any sense?
    }

}
