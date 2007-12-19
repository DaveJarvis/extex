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

package org.extex.exindex.core.rules;

/**
 * This rule replaces a constant string with a constant replacement text. This
 * is the most simple case of a rule.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StringRule extends Rule {

    /**
     * Creates a new object.
     * 
     * @param pattern the pattern
     * @param replacement the replacement text
     * @param again the indicator for repetition
     */
    public StringRule(String pattern, String replacement, boolean again) {

        super(pattern, replacement, again);
        if (again && replacement.indexOf(pattern) >= 0) {
            throw new RuntimeException("infinite loop detected");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.rules.Rule#apply(StringBuilder, int)
     */
    @Override
    public int apply(StringBuilder sb, int index) {

        int ret = -1;
        String p = getPattern();
        int len = p.length();

        do {
            if (index + len > sb.length()) {
                return ret;
            }

            for (int i = 0; i < len; i++) {
                if (sb.charAt(index + i) != p.charAt(i)) {
                    return ret;
                }
            }
            sb.replace(index, index + len, getReplacement());
            ret = index + len;
        } while (isAgain());

        return ret;
    }

}
