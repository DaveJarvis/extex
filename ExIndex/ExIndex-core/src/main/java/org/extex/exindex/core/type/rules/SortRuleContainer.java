/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exindex.core.type.rules;

import java.util.List;


/**
 * This interface describes a container for sort rules.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface SortRuleContainer {

  /**
   * Add some sort rule to the set at a given level.
   *
   * @param level the level
   * @param rule  the rule
   */
  void addSortRule( Integer level, Rule rule );

  /**
   * Add some sort rules to the set at a given level.
   *
   * @param level    the level
   * @param ruleList the rule list
   */
  void addSortRules( Integer level, List<Rule> ruleList );

  /**
   * Getter for the rule set of a given level which creates a sort rule if
   * none exists already.
   *
   * @param level the level
   * @return the rule set for the given level
   */
  SortRules lookupOrCreateSortRule( Integer level );

  /**
   * Getter for the rule set of a given level.
   *
   * @param level the level
   * @return the rule set for the given level or {@code null} for none
   */
  SortRules lookupSortRules( Integer level );

  /**
   * Getter for the maximum levels.
   *
   * @return the maximum level
   */
  int sortRuleSize();

}
