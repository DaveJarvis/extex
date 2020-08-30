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

package org.extex.unit.tex.register.skip;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \skip}.
 * It sets the named skip register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <p>
 * All features are inherited from
 * {@link org.extex.unit.tex.register.skip.SkipParameter SkipParameter}. Just
 * the key has to be provided under which this Glue has to be stored. This key
 * is constructed from the name, a hash mark and the running number.
 * </p>
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \skip12=345pt plus 12em
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SkipParameter extends SkipPrimitive {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code key} contains the reference key.
     */
    private String key;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public SkipParameter(CodeToken token) {

        super(token);
        this.key = token.getName();
    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     * @param key the reference key
     */
    public SkipParameter(CodeToken token, String key) {

        super(token);
        this.key = key;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws TypesetterException {

        if (Namespace.SUPPORT_NAMESPACE_SKIP) {
            return context.getNamespace() + "\b" + key;
        }
        return key;
    }

}
