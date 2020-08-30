/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
 * This is the abstract base class for rules.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class Rule {

    /**
     * Apply a list of rules to a string.
     * 
     * @param rules the rules to apply
     * @param text the initial text to apply it to
     * 
     * @return the transformed text
     */
    public static String apply(List<Rule> rules, String text) {

        StringBuilder sb = new StringBuilder(text);
        int mod = 0;
        for (Rule rule : rules) {
            int m = rule.apply(sb, mod);
            if (m >= 0) {
                mod = m;
            }
        }
        return sb.toString();
    }

    /**
     * The field {@code again} contains the flag :again.
     */
    private boolean again = false;

    /**
     * The field {@code pattern} contains the pattern.
     */
    private final String pattern;

    /**
     * The field {@code replacement} contains the replacement text.
     */
    private final String replacement;

    /**
     * Creates a new object.
     * 
     * @param pattern the pattern
     * @param replacement the replacement text
     * @param again the indicator for repetition
     */
    public Rule(String pattern, String replacement, boolean again) {

        this.pattern = pattern;
        this.replacement = replacement;
        this.again = again;
    }

    /**
     * Apply the rule to a word.
     * 
     * @param word the word to apply the rule to
     * @param index the index to start applying the rule
     * 
     * @return the index to continue the application or a negative value if this
     *         rule did not make any changes
     */
    public abstract int apply(StringBuilder word, int index);

    /**
     * Getter for pattern.
     * 
     * @return the pattern
     */
    public String getPattern() {

        return pattern;
    }

    /**
     * Getter for replacement.
     * 
     * @return the replacement
     */
    public String getReplacement() {

        return replacement;
    }

    /**
     * Getter for again.
     * 
     * @return the again
     */
    public boolean isAgain() {

        return again;
    }

@Override
    public String toString() {

        return ">" + pattern + "< ==> >" + replacement
                + (again ? "< :again" : "<");
    }

}
