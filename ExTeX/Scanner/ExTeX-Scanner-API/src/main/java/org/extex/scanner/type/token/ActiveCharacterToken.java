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

package org.extex.scanner.type.token;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;

/**
 * This class represents an active character token. The active character token
 * is characterized by its name. This name is a single letter string.
 * <p>
 * This class has a protected constructor only. Use the factory
 * {@link org.extex.scanner.type.token.TokenFactory TokenFactory} to get an
 * instance of this class.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class ActiveCharacterToken extends AbstractToken implements CodeToken {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2011L;

    /**
     * The constant <tt>HASH_FACTOR</tt> contains the factor used to construct
     * the hash code.
     */
    private static final int HASH_FACTOR = 17;

    /**
     * The field <tt>namespace</tt> contains the namespace for this token.
     */
    private String namespace;

    /**
     * Creates a new object.
     * 
     * @param uc the string value
     * @param theNamespace the namespace
     */
    protected ActiveCharacterToken(UnicodeChar uc, String theNamespace) {

        super(uc);
        namespace = theNamespace;
    }

    /**
     * Create a new instance of the token where the name space is the default
     * name space and the other attributes are the same as for the current
     * token.
     * 
     * @return the new token
     * 
     * @see org.extex.scanner.type.token.CodeToken#cloneInDefaultNamespace()
     */
    @Override
    public CodeToken cloneInDefaultNamespace() {

        if (Namespace.DEFAULT_NAMESPACE.equals(namespace)) {
            return this;
        }
        return new ActiveCharacterToken(getChar(), Namespace.DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of the token where the name space is the given one.
     * 
     * @param theNamespace the name space to use
     * 
     * @return the new token
     * 
     * @see org.extex.scanner.type.token.CodeToken#cloneInNamespace(java.lang.String)
     */
    @Override
    public CodeToken cloneInNamespace(String theNamespace) {

        if (theNamespace == null || namespace.equals(theNamespace)) {
            return this;
        }
        return new ActiveCharacterToken(getChar(), theNamespace);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param other the reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj argument;
     *         <code>false</code> otherwise.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {

        if (other == null || !(other instanceof ActiveCharacterToken)) {
            return false;
        }
        ActiveCharacterToken othertoken = (ActiveCharacterToken) other;
        return (super.equals(other) && namespace.equals(othertoken.namespace));
    }

    /**
     * Getter for the catcode.
     * 
     * @return the catcode
     * 
     * @see org.extex.scanner.type.token.Token#getCatcode()
     */
    @Override
    public Catcode getCatcode() {

        return Catcode.ACTIVE;
    }

    /**
     * Getter for the name. The name is the string representation without the
     * escape character in front.
     * 
     * @return the name of the token
     * 
     * @see org.extex.scanner.type.token.CodeToken#getName()
     */
    @Override
    public String getName() {

        return "";
    }

    /**
     * Getter for the name space.
     * 
     * @return the name space
     * 
     * @see org.extex.scanner.type.token.CodeToken#getNamespace()
     */
    @Override
    public String getNamespace() {

        return namespace;
    }

    /**
     * Returns a hash code value for the object.
     * 
     * @return a hash code value for this object
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return super.hashCode() + HASH_FACTOR * namespace.hashCode();
    }

    /**
     * Get the string representation of this object for debugging purposes.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        return getLocalizer().format("ActiveCharacterToken.Text",
            super.toString());
    }

    /**
     * Print the token into a StringBuilder.
     * 
     * @param sb the target string builder
     * 
     * @see org.extex.scanner.type.token.Token#toString(java.lang.StringBuilder)
     */
    @Override
    public void toString(StringBuilder sb) {

        sb.append(getLocalizer().format("ActiveCharacterToken.Text",
            super.toString()));
    }

    /**
     * Invoke the appropriate visit method for the current class.
     * 
     * @param visitor the calling visitor
     * @param arg1 the first argument to pass
     * 
     * @return the result object
     * 
     * @throws Exception in case of an error
     * 
     * @see org.extex.scanner.type.token.Token#visit(org.extex.scanner.type.token.TokenVisitor,
     *      java.lang.Object)
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object visit(TokenVisitor visitor, Object arg1) throws Exception {

        return visitor.visitActive(this, arg1);
    }

}
