/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.scanner.type.token;

/**
 * The code token extends the {@link org.extex.scanner.type.token.Token Token}
 * with the ability to retrieve a name space.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface CodeToken extends Token {

    /**
     * Create a new instance of the token where the name space is the default
     * name space and the other attributes are the same as for the current token.
     *
     * @return the new token
     */
    CodeToken cloneInDefaultNamespace();

    /**
     * Create a new instance of the token where the name space is the given one
     * and the other attributes are the same as for the current token.
     *
     * @param namespace the name space to use
     *
     * @return the new token
     */
    CodeToken cloneInNamespace(String namespace);

    /**
     * Getter for the name.
     * The name is the string representation without the escape character
     * in front.
     *
     * @return the name of the token
     */
    String getName();

    /**
     * Getter for the name space.
     *
     * @return the name space
     */
    String getNamespace();

}