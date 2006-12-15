/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.file.InFile;
import org.extex.scanner.TokenStream;
import org.extex.util.framework.configuration.exception.ConfigurationException;

import de.dante.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\openin</code>.
 *
 * <doc name="openin">
 * <h3>The Primitive <tt>\openin</tt></h3>
 * <p>
 *  The primitive <tt>\openin</tt> tries to open a file or other named resource
 *  for reading. The reference is stored in a read register to be used with
 *  {@link de.dante.extex.interpreter.primitives.file.Read <tt>\read</tt>}.
 *  If the opening fails then the read register is void. This
 *  can be checked with the primitive
 *  {@link de.dante.extex.interpreter.primitives.conditional.Ifeof <tt>\ifeof</tt>}.
 * </p>
 * <p>
 *  The assignment to a read register is always global. The opening is always
 *  performed immediately. Thus no prefix is allowed.
 * </p>
 * <p>
 *  The stream should be closed with
 *  {@link de.dante.extex.interpreter.primitives.file.Closein <tt>\closein</tt>}
 *  when not needed any more.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;openin&rang;
 *      &rarr; <tt>\openin</tt>  {@linkplain
 *        de.dante.extex.interpreter.primitives.file.AbstractFileCode#scanInFileKey(Context,TokenSource,Typesetter)
 *        &lang;infile&nbsp;name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.primitive.file.AbstractFileCode#scanFileName(Context,TokenSource)
 *        &lang;file name&rang;}   </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *  \openin3= abc.def
 *  \read3 to \line
 *  \closein3 </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4441 $
 */
public class Openin extends AbstractFileCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060819L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Openin(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = AbstractFileCode
                .scanInFileKey(context, source, typesetter);
        source.getOptionalEquals(context);
        String name = scanFileName(context, source);

        InFile file;
        Interaction interaction = context.getInteraction();
        try {
            context.setInteraction(Interaction.BATCHMODE);
            TokenStream stream = source.getTokenStreamFactory().newInstance(
                    name, "tex", getEncoding(context));
            if (stream != null) {
                file = new InFile(stream, false);
            } else {
                file = null;
            }
        } catch (ConfigurationException e) {
            throw new InterpreterException(e);
        } finally {
            context.setInteraction(interaction);
        }
        context.setInFile(key, file, true);
    }

}
