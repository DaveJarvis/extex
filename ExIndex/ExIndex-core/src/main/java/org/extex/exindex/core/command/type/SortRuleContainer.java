/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command.type;

import java.util.List;

import org.extex.exindex.core.type.rules.Rule;

/**
 * This interface describes a container for sort rules.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface SortRuleContainer {

    /**
     * Add some sort rules to the set at a given level.
     * 
     * @param level the level
     * @param ruleList the rule list
     */
    public void add(int level, List<Rule> ruleList);

    /**
     * Getter for the rule set of a given level.
     * 
     * @param level the level
     * 
     * @return the rule set for the given level or <code>null</code> for none
     */
    public List<Rule> lookup(int level);

    /**
     * Getter for the maximum levels.
     * 
     * @return the maximum level
     */
    public int size();

}
