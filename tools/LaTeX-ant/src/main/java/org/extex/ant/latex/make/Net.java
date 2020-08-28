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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.ant.latex.MakeException;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class Net {

    /**
     * The field <tt>metaRules</tt> contains the implicit pattern rules.
     */
    private List<PatternRule> metaRules = new ArrayList<PatternRule>();

    /**
     * The field <tt>rules</tt> contains the explicitly given rules for single
     * files.
     */
    private Map<File, Rule> rules = new HashMap<File, Rule>();

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param rule
     */
    public void addMetaRule(PatternRule rule) {

        metaRules.add(rule);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param target
     * @param rule
     */
    public void addRule(File target, Rule rule) {

        rules.put(target, rule);
    }

    /**
     * Find a rule to make a target.
     * 
     * @param target the target to find a rule for
     * 
     * @return the matching rule
     * 
     * @throws MakeException in case of an error or if no rule could be found
     */
    private Rule findRule(File target) throws MakeException {

        Rule r = rules.get(target);
        if (r != null) {
            return r;
        }
        if (target != null) {
            for (PatternRule p : metaRules) {
                if (p.matches(target)) {
                    return p;
                }
            }
        }
        throw new MakeException("unknown target: "
                + (target == null ? "null" : target.toString()));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param target
     * 
     * @return
     * 
     * @throws MakeException in case of an error
     */
    public boolean make(File target) throws MakeException {

        Rule r = findRule(target);

        if (r.isUpToDate(target, this)) {
            return false;
        }
        return r.apply(target, this);
    }

}
