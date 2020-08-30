/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.register.hash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * A Hash for Tokens.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class HashToks implements Serializable {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The hash
     */
    private HashMap<String, Tokens> map = null;


    public HashToks() {

        map = new HashMap<String, Tokens>();
    }

    /**
     * Creates a new object. get the {@code TokenSource} for a
     * {@code HashToks} (noexpand).
     * 
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws HelpingException if an error occurs.
     * @throws TypesetterException in case of an error in the typesetter
     */
    public HashToks(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        map = new HashMap<String, Tokens>();

        // { {key1}{value1} {key2}{value2} }
        Token token = source.getNonSpace(context);

        if (token == null) {
            throw new InterpreterMissingLeftBraceException();
        } else if (!token.isa(Catcode.LEFTBRACE)) {
            throw new InterpreterMissingLeftBraceException();
        }

        while (true) {
            String key = source.scanTokensAsString(context, null);
            if (key.trim().length() == 0) {
                throw new InterpreterMissingHashKeyException();
            }
            Tokens toks = source.getTokens(context, source, typesetter);
            if (toks == null) {
                throw new InterpreterMissingHashValueException();
            }
            put(key, toks);

            // next ?
            token = source.getNonSpace(context);
            if (token == null) {
                throw new InterpreterMissingRightBraceException();
            } else if (token.isa(Catcode.RIGHTBRACE)) {
                break;
            }
            source.push(token);
        }
    }

    /**
     * Contains the key
     * 
     * @param key the key
     * @return {@code true} if the key exists, otherwise {@code false}
     */
    public boolean containsKey(String key) {

        return map.containsKey(key);
    }

    /**
     * Return the tokens for a key.
     * 
     * @param key the key
     * @return the token for this key
     */
    public Tokens get(String key) {

        Tokens toks = map.get(key);
        if (toks == null) {
            toks = new Tokens();
        }
        return toks;
    }

    /**
     * Put the tokens on the hash with the key.
     * 
     * @param key the key
     * @param toks the tokens
     */
    public void put(String key, Tokens toks) {

        map.put(key, toks);
    }

    /**
     * Return the size of the hash.
     * 
     * @return the size of the hash
     */
    public int size() {

        return map.size();
    }

    /**
     * Return the value as {@code String}
     * 
     * @return the value as {@code String}
     */
    @Override
    public String toString() {

        StringBuilder buf = new StringBuilder();
        buf.append("{\n");
        String[] array = map.keySet().toArray(new String[map.size()]);
        Arrays.sort(array);
        for (String key : array) {
            buf.append('{' + key + '}');
            buf.append('{' + get(key).toText() + "}\n");
        }
        buf.append("}\n");
        return buf.toString();
    }
}
