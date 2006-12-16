/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.namespace;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.primitives.macro.Let;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;


/**
 * This class provides an implementation for the primitive <code>\import</code>.
 *
 * <doc name="import">
 * <h3>The Primitive <tt>\import</tt></h3>
 * <p>
 *  The primitive <tt>\import</tt> defines all control sequences exported from
 *  the given name space into the current name space. Any definitions with the
 *  same name are overwritten.
 * </p>
 * <p>
 *  The definitions are usually performed local to the current group. If the
 *  prefix <tt>\global</tt> is given then the definition is made globally.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;import&rang;
 *      &rarr; &lang;prefix&rang; <tt>\import</tt> {@linkplain
 *      org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *      &lang;name space&rang;}
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *      | <tt>\global</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \import{de.dante.dtk}  </pre>
 *
 * </doc>
 *
 *
 * @see org.extex.interpreter.primitives.namespace.Export
 * @see org.extex.interpreter.primitives.namespace.Namespace
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Import extends Let {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Import(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String ns = source.getTokens(context, source, typesetter).toText();
        Tokens export = context.getToks(ns + "\bexport");
        String namespace = context.getNamespace();
        int length = export.length();

        for (int i = 0; i < length; i++) {
            Token t = export.get(i);
            if (t instanceof CodeToken) {
                if (context.getCode((CodeToken) t) == null) {
                    throw new HelpingException(getLocalizer(),
                            "Namespace.Import.undef", t.toString());
                } else {
                    let(prefix, context, //
                            ((CodeToken) t).cloneInNamespace(namespace), t);
                }
            }
        }
    }

}
