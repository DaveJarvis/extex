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

import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class Rule implements LValue {

    /**
     * The field <tt>again</tt> contains the flag :again.
     */
    boolean again = false;

    /**
     * The field <tt>pattern</tt> contains the ...
     */
    private String pattern;

    /**
     * The field <tt>replacement</tt> contains the replacement text.
     */
    private String replacement;

    /**
     * Creates a new object.
     * 
     * @param pattern
     * @param replacement
     * @param again
     */
    public Rule(String pattern, String replacement, boolean again) {

        super();
        this.pattern = pattern;
        this.replacement = replacement;
        this.again = again;
    }

    /**
     * Apply the rule to a word.
     * 
     * @param word the word to apply the rule to
     * 
     * @return the transformed word
     */
    public abstract String apply(String word);

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

}
