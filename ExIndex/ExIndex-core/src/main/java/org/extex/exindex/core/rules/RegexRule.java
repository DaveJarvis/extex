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

import java.io.PrintStream;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class RegexRule extends Rule {

    /**
     * Creates a new object.
     * 
     * @param pattern the pattern
     * @param replacement the replacement text
     * @param again the indicator for repetition
     */
    public RegexRule(String pattern, String replacement, boolean again) {

        super(pattern, replacement, again);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.rules.Rule#apply(java.lang.CharSequence, int)
     */
    @Override
    public int apply(CharSequence word, int index) {

        // TODO gene: apply unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        // TODO gene: print unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("#Rule(\"");
        sb.append(getPattern());
        sb.append("\" \"");
        sb.append(getReplacement());
        sb.append("\"");
        if (isAgain()) {
            sb.append(" :again");
        }
        sb.append(" :regex)");
        // TODO gene: toString unimplemented
        return sb.toString();
    }

}
