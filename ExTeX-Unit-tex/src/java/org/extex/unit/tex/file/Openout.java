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

import java.io.File;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.file.OutFile;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.node.WhatsItOpenNode;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive
 * <code>\openout</code>.
 *
 * <doc name="openout">
 * <h3>The Primitive <tt>\openout</tt></h3>
 * <p>
 *  The primitive <tt>\openout</tt> tries to open a file or other named resource
 *  for writing. The reference is stored in a write register to be used with
 *  {@link org.extex.unit.tex.file.Write <tt>\write</tt>].
 *  If the opening fails then the write register is void.
 * </p>
 * <p>
 *  The opening of a write register is delayed until the nodes are shipped out.
 *  If the invocation is prefixed with
 *  {@link org.extex.unit.tex.prefix.Immediate <tt>\immediate</tt>}
 *  then the resource is opened immediately.
 * </p>
 * <p>
 *  The primitive <tt>\openout</tt> is not considered as assignment. Nor can
 *  it be prefixed with <tt>\global</tt>. The definition of an output stream is
 *  always global.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;openout&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\openout</tt> {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanOutFileKey(Context,TokenSource,Typesetter)
 *        &lang;outfile&nbsp;name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanFileName(Context,TokenSource)
 *        &lang;file name&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\immediate</tt> &lang;optional prefix&rang;  </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *  \immediate\openout3= abc.def
 *  \write3{Hi there!}
 *  \closeout3 </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4441 $
 */
public class Openout extends AbstractFileCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Openout(final String name) {

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
            throws InterpreterException,ConfigurationException {

        String key = AbstractFileCode.scanOutFileKey(context, source, typesetter);

        source.getOptionalEquals(context);
        String name = scanFileName(context, source);

        OutFile file = new OutFile(new File(name));

        if (prefix.clearImmediate()) {
            file.open();
            context.setOutFile(key, file, true);
        } else {
            typesetter.add(new WhatsItOpenNode(key, file));
        }
    }

}
