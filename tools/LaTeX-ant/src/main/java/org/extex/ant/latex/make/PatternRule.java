/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.ant.latex.make;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.extex.ant.latex.command.Command;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PatternRule extends Rule {

    /**
     * The field {@code pattern} contains the pattern for matching.
     */
    private Pattern pattern;

    /**
     * The field {@code sourcePattern} contains the ...
     */
    private String sourcePattern;

    /**
     * Creates a new object.
     * 
     * @param pattern
     * @param preconditions
     * @param commands
     */
    public PatternRule(String pattern, String sourcePattern,
            List<Command> commands) {

        super(null, null, commands);
        this.pattern = Pattern.compile(pattern);
        this.sourcePattern = sourcePattern;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param target
     * @return
     */
    public boolean matches(File target) {

        return pattern.matcher(target.getAbsolutePath()).matches();
    }

}
