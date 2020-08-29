/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.type.tokens;

import org.extex.core.UnicodeChar;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is a container for a list of
 * {@link org.extex.scanner.type.token.Token Token}s.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public class Tokens implements Serializable, FixedTokens, Iterable<Token> {

    /**
     * This constant is the empty token register.
     */
    public static final Tokens EMPTY = new ImmutableTokens();

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060415L;

    /**
     * The internal list of tokens
     */
    private final List<Token> tokens = new ArrayList<Token>();

    /**
     * Creates a new object which does not contain any elements.
     */
    public Tokens() {

    }

    /**
     * Creates a new object.
     * 
     * @param t the initial token
     */
    public Tokens(Token t) {

        tokens.add(t);
    }

    /**
     * Add another token to the end of the Tokens.
     * 
     * @param t The token to add
     */
    public void add(Token t) {

        tokens.add(t);
    }

    /**
     * Add another token list to the end of the Tokens.
     * 
     * @param toks the tokens to add
     */
    public void add(Tokens toks) {

        int len = toks.length();
        for (int i = 0; i < len; i++) {
            tokens.add(toks.get(i));
        }
    }

    /**
     * This method removes all elements from the tokens list. Afterwards the
     * list is empty.
     */
    public void clear() {

        tokens.clear();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Tokens)) {
            return false;
        }
        Tokens toks = (Tokens) object;
        int len = length();
        if (toks.length() != len) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (!get(i).equals(toks.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get a specified token from the toks register.
     * 
     * @param i the index for the token to get
     * 
     * @return the i<sup>th</sup> token or <code>null</code> if i is out of
     *         bounds
     */
    @Override
    public Token get(int i) {

        return (i >= 0 && i < tokens.size() ? tokens.get(i) : null);
    }

    /**
     * Returns a hash code value for the object.
     * 
     * @return a hash code value for this object.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int hash = length();
        for (int i = 0; i < length(); i++) {
            hash += get(i).getChar().getCodePoint();
        }
        return hash;
    }

    /**
     * Add a token to the list at a certain position.
     * 
     * @param index the index to add the token to
     * @param t the token to add
     */
    public void insert(int index, Token t) {

        tokens.add(index, t);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Token> iterator() {

        return tokens.iterator();
    }

    /**
     * Getter for the length of the token register, this is the number of
     * elements contained.
     * 
     * @return the number of elements in the token register
     */
    @Override
    public int length() {

        return tokens.size();
    }

    /**
     * Remove the first token from the list and return it.
     * 
     * @return the token taken from the front of the list or <code>null</code>
     *         if the list is empty
     */
    public Token pop() {

        if (tokens.size() == 0) {
            return null;
        }
        Token t = tokens.get(0);
        tokens.remove(0);
        return t;
    }

    /**
     * Push a token to the front of the list.
     * 
     * @param token the token to push
     */
    public void push(Token token) {

        tokens.add(0, token);
    }

    /**
     * Remove the last token from the list and return it. If the list is empty
     * then <code>null</code> is returned.
     * 
     * @return the first token or <code>null</code>
     */
    public Token removeFirst() {

        if (tokens.size() == 0) {
            return null;
        }
        return tokens.remove(0);
    }

    /**
     * Remove the last token from the list and return it. If the list is empty
     * then <code>null</code> is returned.
     * 
     * @return the last token or <code>null</code>
     */
    public Token removeLast() {

        if (tokens.size() == 0) {
            return null;
        }
        return tokens.remove(tokens.size() - 1);
    }

    /**
     * Determine the printable representation of the object and append it to a
     * list of Tokens.
     * 
     * @param toks the tokens to add to
     * @param factory the token factory
     * @param esc the escape character
     * 
     * @throws CatcodeException in case of an error
     */
    public void show(Tokens toks, TokenFactory factory, long esc)
            throws CatcodeException {

        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t instanceof ControlSequenceToken) {
                if (esc >= 0) {
                    toks.add(factory.createToken(Catcode.OTHER, (char) (esc),
                        Namespace.DEFAULT_NAMESPACE));
                }
                toks.add(factory.toTokens(((ControlSequenceToken) t).getName()));
            } else {
                toks.add(factory.createToken(Catcode.OTHER, t.getChar(),
                    Namespace.DEFAULT_NAMESPACE));
            }
        }
    }

    /**
     * Return a String, which show all tokens in the list.
     * 
     * @return a String, which show all tokens in the list
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    /**
     * Print the token into a StringBuilder.
     * 
     * @param sb the target string builder
     */
    public void toString(StringBuilder sb) {

        for (int i = 0; i < tokens.size(); i++) {
            tokens.get(i).toString(sb);
            sb.append("\n  ");
        }
    }

    /**
     * Return a String, which shows all tokens (in text format) in the list.
     * 
     * @return a String, which show all tokens (in text format) in the list
     * 
     * @see org.extex.scanner.type.tokens.FixedTokens#toText()
     */
    @Override
    public String toText() {

        StringBuilder sb = new StringBuilder();

        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            Token t = tokens.get(i);
            sb.append(t.toText());
            if (t instanceof ControlSequenceToken && i != size - 1) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    /**
     * Return a String, which shows all tokens (in text format) in the list.
     * 
     * @param esc the escape character to use
     * 
     * @return a String, which show all tokens (in text format) in the list
     * 
     * @see org.extex.scanner.type.tokens.FixedTokens#toText(UnicodeChar)
     */
    @Override
    public String toText(UnicodeChar esc) {

        StringBuilder sb = new StringBuilder();

        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            Token t = tokens.get(i);
            sb.append(t.toText(esc));
            if (t instanceof ControlSequenceToken && i != size - 1) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

}
