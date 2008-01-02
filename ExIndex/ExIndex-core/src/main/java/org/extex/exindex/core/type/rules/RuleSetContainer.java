/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.rules;

import java.util.List;


/**
 * This interface describes a read-only container for rule sets.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface RuleSetContainer {

    /**
     * Add a named definition of a rule.
     * 
     * @param name the name of the rule
     * @param rule the rule itself
     */
    void addRule(String name, List<Rule> rule);

    /**
     * Find the definition of a rule set.
     * 
     * @param name the name of the rule set
     * 
     * @return the rule set or <code>null</code> if undefined
     */
    List<Rule> lookupRule(String name);

}
