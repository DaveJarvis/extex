/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.type.token;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;

/**
 * This class represents a control sequence token.
 * <p>
 * This class has a protected constructor only. Use the factory
 * {@link org.extex.scanner.type.token.TokenFactory TokenFactory}
 * to get an instance of this class.
 * </p>
 *
 * <p>
 *  Note that in contrast to <logo>TeX</logo> the escape character leading to
 *  this control sequence token is stored in the character code of the abstract
 *  base class.
 * </p>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class ControlSequenceToken extends AbstractToken implements CodeToken {

    /**
     * The constant <tt>HASH_FACTOR</tt> contains the factor used to construct
     * the hash code.
     */
    private static final int HASH_FACTOR = 17;

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field <tt>value</tt> contains the string value.
     */
    private String name;

    /**
     * The field <tt>namespace</tt> contains the name space for this token.
     */
    private String namespace;

    /**
     * Creates a new object from the first character of a String.
     * If the string is empty then a space character is used instead.
     *
     * @param esc the escape character
     * @param name the name of the control sequence &ndash; without the leading
     *  escape character token
     * @param namespace the name space
     */
    protected ControlSequenceToken(final UnicodeChar esc, final String name,
            final String namespace) {

        super(esc);
        this.namespace = namespace;
        this.name = name;
    }

    /**
     * Create a new instance of the token where the name space is the default
     * name space and the other attributes are the same as for the current token.
     *
     * @return the new token
     *
     * @see org.extex.scanner.type.token.CodeToken#cloneInDefaultNamespace()
     */
    public CodeToken cloneInDefaultNamespace() {

        if (Namespace.DEFAULT_NAMESPACE.equals(namespace)) {
            return this;
        }
        return new ControlSequenceToken(getChar(), name,
            Namespace.DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of the token where the name space is the given one
     * and the other attributes are the same as for the current token.
     *
     * @param theNamespace the name space to use
     *
     * @return the new token
     *
     * @see org.extex.scanner.type.token.CodeToken#cloneInNamespace(
     *      java.lang.String)
     */
    public CodeToken cloneInNamespace(final String theNamespace) {

        if (theNamespace == null || namespace.equals(theNamespace)) {
            return this;
        }
        return new ControlSequenceToken(getChar(), name, theNamespace);
    }

    /**
     * Compare the current token with a pair of catcode and character value.
     * This pair constitutes a virtual token. They are the same if the catcode
     * and the value are the same.
     *
     * @param cc the catcode
     * @param c the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.AbstractToken#equals(
     *      org.extex.scanner.type.Catcode, char)
     */
    public boolean equals(final Catcode cc, final char c) {

        return getCatcode() == cc && name.length() == 1 && name.charAt(0) == c;
    }

    /**
     * Compare the current token with a pair of catcode and String value. This
     * pair constitutes a virtual token. They are the same if the catcode and
     * the value are the same.
     *
     * @param cc the catcode
     * @param s the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.Token#equals(
     *      org.extex.scanner.type.Catcode, java.lang.String)
     */
    public boolean equals(final Catcode cc, final String s) {

        return getCatcode() == cc && name.equals(s);
    }

    /**
     * Compare the current token with a character value. They are the same if
     * the values are the same.
     *
     * @param c the value
     *
     * @return <code>true</code> iff the tokens are equal
     *
     * @see org.extex.scanner.type.token.AbstractToken#equals(char)
     */
    public boolean equals(final char c) {

        return name.length() == 1 && name.charAt(0) == c;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param   other   the reference object with which to compare.
     * @return  <code>true</code> if this object is the same as the obj
     *          argument; <code>false</code> otherwise.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object other) {

        if (other instanceof ControlSequenceToken) {
            ControlSequenceToken othertoken = (ControlSequenceToken) other;
            return (name.equals(othertoken.getName()) //
            && namespace.equals(othertoken.namespace));
        }
        return false;
    }

    /**
     * Getter for the catcode.
     *
     * @return the catcode
     *
     * @see org.extex.scanner.type.token.Token#getCatcode()
     */
    public Catcode getCatcode() {

        return Catcode.ESCAPE;
    }

    /**
     * Getter for the name.
     * The name is the string representation without the escape character
     * in front.
     *
     * @return the name of the token
     *
     * @see org.extex.scanner.type.token.CodeToken#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * Getter for the name space.
     *
     * @return the name space
     *
     * @see org.extex.scanner.type.token.CodeToken#getNamespace()
     */
    public String getNamespace() {

        return namespace;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return  a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return name.hashCode() + HASH_FACTOR * namespace.hashCode();
    }

    /**
     * Get the string representation of this object for debugging purposes.
     *
     * @return the string representation
     */
    public String toString() {

        UnicodeChar c = getChar();
        return getLocalizer().format("ControlSequenceToken.Text",
            (c != null ? c.toString() : ""), name, namespace);
    }

    /**
     * Print the token into a StringBuffer.
     *
     * @param sb the target string buffer
     *
     * @see org.extex.scanner.type.token.Token#toString(java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        sb.append(getLocalizer().format("ControlSequenceToken.Text",
            getChar().toString(), name, namespace));
    }

    /**
     * Return the text representation of this object.
     *
     * @return the text representation
     *
     * @see org.extex.scanner.type.token.AbstractToken#toText()
     */
    public String toText() {

        UnicodeChar c = getChar();
        if (c != null && c.getCodePoint() != 0) {
            return c.toString() + name;
        }

        return name;
    }

    /**
     * Return the printable representation of this token as it can be read back
     * in.
     *
     * @param esc the escape character
     *
     * @return the printable representation
     *
     * @see org.extex.scanner.type.token.Token#toText(
     *      org.extex.core.UnicodeChar)
     */
    public String toText(final UnicodeChar esc) {

        if (esc != null) {
            return esc.toString() + name;
        }

        return name;
    }

    /**
     * Invoke the appropriate visit method for the current class.
     * @param visitor the calling visitor
     * @param arg1 the first argument to pass
     *
     * @return the result object
     *
     * @throws Exception in case of an error
     *
     * @see org.extex.scanner.type.token.Token#visit(
     *      org.extex.scanner.type.token.TokenVisitor,
     *      java.lang.Object)
     */
    public Object visit(final TokenVisitor visitor, final Object arg1)
            throws Exception {

        return visitor.visitEscape(this, arg1);
    }

}
