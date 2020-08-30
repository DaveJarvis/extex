/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.math;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.noad.ChoiceNoad;
import org.extex.typesetter.type.noad.Noad;

/**
 * This class provides an implementation for the primitive
 * {@code \mathchoice}.
 * 
 * <p>The Math Primitive {@code \mathchoice}</p>
 * <p>
 * The math primitive {@code \mathchoice} provides a switch on the current
 * style of math processing. The math processing is in one of the styles
 * <i>display</i>, <i>text</i>, <i>script</i>, and <i>scriptscript</i>. The
 * math styles influence the size of the typeset material and the spacing. The
 * primitive can be used to insert some material depending on the current math
 * style.
 * </p>
 * <p>
 * For each of the styles the material to be used must be given. The current
 * style determines which material should be expanded. The material for the
 * other styles is discarded.
 * </p>
 * <p>
 * The material is enclosed in braces &ndash; or to be precise in tokens with
 * the catcodes 1 (left brace) and 2 (right brace). The four cases lead to four
 * groups.
 * </p>
 * <p>
 * Outside the math mode the primitive raises an error.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mathchoice&rang;
 *       &rarr; {@code \mathchoice} {&lang;display material&rang;}{&lang;text material&rang;}{&lang;script material&rang;}{&lang;scriptscript material&rang;} </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \mathchoice{d}{t}{s}{ss}  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Mathchoice extends AbstractMathCode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Mathchoice(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws TypesetterException, HelpingException {

        Flags f = prefix.copy();
        prefix.clear();
        NoadConsumer nc = getListMaker(context, typesetter);
        Token t = getToken();
        Noad display =
                nc.scanNoad(prefix, context, source, typesetter, t,
                    GroupType.MATH_GROUP);
        Noad text =
                nc.scanNoad(prefix, context, source, typesetter, t,
                    GroupType.MATH_GROUP);
        Noad script =
                nc.scanNoad(prefix, context, source, typesetter, t,
                    GroupType.MATH_GROUP);
        Noad scriptScript =
                nc.scanNoad(prefix, context, source, typesetter, t,
                    GroupType.MATH_GROUP);
        nc.add(new ChoiceNoad(display, text, script, scriptScript));
        prefix.set(f);
    }

}
