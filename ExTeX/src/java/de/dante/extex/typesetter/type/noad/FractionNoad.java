/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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
package de.dante.extex.typesetter.type.noad;

import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.typesetter.NodeList;
import de.dante.extex.typesetter.type.noad.util.MathContext;


/**
 * ...
 *
 * @see "TTP [683]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FractionNoad implements Noad {

    /**
     * The field <tt>denominator</tt> contains the denominator part.
     */
    private MathList denominator;

    /**
     * The field <tt>nominator</tt> contains the numerator part.
     */
    private MathList numerator;

    /**
     * The field <tt>thickness</tt> contains the thickness of the fraction rule.
     * The value <code>null</code> indicates that the default rule thickness of
     * the current size should be used.
     */
    private Dimen thickness = null;

    //TODO left/right delimiter

    /**
     * Creates a new object.
     *
     * @param denom the denominator
     * @param num the numerator
     */
    public FractionNoad(final MathList denom, final MathList num) {

        super();
        denominator = denom;
        numerator = num;
    }

    /**
     * @see de.dante.extex.typesetter.type.noad.Noad#toString(java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        // TODO unimplemented

    }

    /**
     * @see de.dante.extex.typesetter.type.noad.Noad#typeset(MathContext)
     */
    public NodeList typeset(final MathContext mathContext) {

        // TODO unimplemented
        return null;
    }

}
