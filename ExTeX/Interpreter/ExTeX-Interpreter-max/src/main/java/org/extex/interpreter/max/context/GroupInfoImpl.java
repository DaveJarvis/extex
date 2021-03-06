/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.max.context;

import org.extex.core.Locator;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.token.Token;

/**
 * This class provides a reference implementation for a
 * {@link org.extex.interpreter.context.group.GroupInfo GroupInfo}. It is a mere
 * container with some getters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class GroupInfoImpl implements GroupInfo {

    /**
     * The field {@code groupStart} contains the group starting token.
     */
    private Token groupStart;

    /**
     * The field {@code groupType} contains the group type.
     */
    private GroupType groupType;

    /**
     * The field {@code locator} contains the locator.
     */
    private Locator locator;


    public GroupInfoImpl() {

        this(null, null, null);
    }

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     * @param groupType the group type
     * @param groupStart the starting token
     */
    public GroupInfoImpl(Locator locator, GroupType groupType, Token groupStart) {

        this.locator = locator;
        this.groupType = groupType;
        this.groupStart = groupStart;
    }

    /**
     * Getter for the starting token of the group. This value is null for the
     * global group.
     * 
     * @return the token which started the group
     * 
     * @see org.extex.interpreter.context.group.GroupInfo#getGroupStart()
     */
    public Token getGroupStart() {

        return groupStart;
    }

    /**
     * Getter for the group type.
     * 
     * @return the group type
     * 
     * @see org.extex.interpreter.context.group.GroupInfo#getGroupType()
     */
    public GroupType getGroupType() {

        return groupType;
    }

    /**
     * Getter for the locator describing where the group started. This value can
     * be null for the global group.
     * 
     * @return the locator
     * 
     * @see org.extex.interpreter.context.group.GroupInfo#getLocator()
     */
    public Locator getLocator() {

        return locator;
    }

    /**
     * Setter for groupStart.
     * 
     * @param groupStart the groupStart to set
     */
    public void setGroupStart(Token groupStart) {

        this.groupStart = groupStart;
    }

    /**
     * Setter for groupType.
     * 
     * @param type the groupType to set
     */
    public void setGroupType(GroupType type) {

        this.groupType = type;
    }

    /**
     * Setter for locator.
     * 
     * @param locator the locator to set
     */
    public void setLocator(Locator locator) {

        this.locator = locator;
    }

}
