/*
 * Copyright (C) 2003  Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.interpreter.type;

import java.io.Serializable;

import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.scanner.Catcode;
import de.dante.extex.scanner.TokenFactory;
import de.dante.util.GeneralException;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Dimen extends GlueComponent implements Serializable {
    /** ... */
    public static final Dimen ZERO_PT = new Dimen(0);

    /** ... */
    public static final Dimen ONE_PT = new Dimen(1 << 16);

    /** This constant contains the internal representation for 1pt
     * @see "TeX -- The Program [101]"
     */
    public static final long ONE = 1 << 16;

    /**
     * Creates a new object.
     */
    public Dimen() {
        super();
    }

    /**
     * Creates a new object.
     */
    public Dimen(long value) {
        super(value);
    }

    public Dimen(TokenSource source, Context context) throws GeneralException {
        super(source,context,false);
    }

    /**
     * ...
     *
     * @param source ...
     * @param context ...
     *
     * @throws GeneralException ...
     */
    public void set(TokenSource source, Context context)
             throws GeneralException {
        set(source, context, false);
    }

    /**
     * ...
     *
     * @param source ...
     * @param context ...
     *
     * @throws GeneralException ...
     */
    public void set(String source, Context context)
             throws GeneralException {
        //set(source, context, false);
        //TODO
    }

    /**
     * @see de.dante.extex.interpreter.Advanceable#advance(int, de.dante.extex.interpreter.context.Context, de.dante.extex.interpreter.TokenSource)
     */
    public void advance(int prefix, Context context, TokenSource source)
                 throws GeneralException {
        // TODO 
    }

    /**
     * ...
     *
     * @return ...
     */
    public String toString() {
        return Long.toString(value) + "sp";
    }
    
    public void toString(StringBuffer sb) {
        sb.append(Long.toString(value));
        sb.append("sp");
    }

    /**
     * ...
     *
     * @param factory ...
     *
     * @return ...
     *
     * @throws GeneralException ...
     * @see "TeX -- The Program [103]"
     */
    public Tokens toToks(TokenFactory factory) throws GeneralException {
        Tokens toks = new Tokens();
        String s    = Long.toString(value/ONE);

        for (int i = 0; i < s.length(); i++) {
            toks.add(factory.newInstance(Catcode.OTHER,
                                         s.substring(i, i + 1)));
        }

        //TODO: decimal places and rounding
        toks.add(factory.newInstance(Catcode.LETTER, "p"));
        toks.add(factory.newInstance(Catcode.LETTER, "t"));

        return toks;
    }
}
