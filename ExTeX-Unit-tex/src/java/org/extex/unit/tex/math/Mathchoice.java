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

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.noad.ChoiceNoad;
import org.extex.typesetter.type.noad.Noad;

/**
 * This class provides an implementation for the primitive
 * <code>\mathchoice</code>.
 *
 * <doc name="mathchoice">
 * <h3>The Math Primitive <tt>\mathchoice</tt></h3>
 * <p>
 *  The math primitive <tt>\mathchoice</tt> provides a switch on the current
 *  style of math processing. The math processing is in one of the styles
 *  <i>display</i>, <i>text</i>, <i>script</i>, and <i>scriptscript</i>. The
 *  math styles influence the size of the typeset material and the spacing. The
 *  primitive can be used to insert some material depending on the current
 *  math style.
 * </p>
 * <p>
 *  For each of the styles the material to be used must be given. The current
 *  style determines which material should be expanded. The material for the
 *  other styles is discarded.
 * </p>
 * <p>
 *  The material is enclosed in braces &ndash; or to be precise in tokens with
 *  the catcodes 1 (left brace) and 2 (right brace). The four cases lead to four
 *  groups.
 * </p>
 * <p>
 *  Outside the math mode the primitive raises an error.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;mathchoice&rang;
 *       &rarr; <tt>\mathchoice</tt> {&lang;display material&rang;}{&lang;text material&rang;}{&lang;script material&rang;}{&lang;scriptscript material&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \mathchoice{d}{t}{s}{ss}  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Mathchoice extends AbstractMathCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Mathchoice(final String name) {

        super(name);
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        Flags f = prefix.copy();
        prefix.clear();
        NoadConsumer nc = getListMaker(context, typesetter);
        String n = getName();
        Noad display =
                nc.scanNoad(prefix, context, source, typesetter, n,
                    GroupType.MATH_GROUP);
        Noad text =
                nc.scanNoad(prefix, context, source, typesetter, n,
                    GroupType.MATH_GROUP);
        Noad script =
                nc.scanNoad(prefix, context, source, typesetter, n,
                    GroupType.MATH_GROUP);
        Noad scriptScript =
                nc.scanNoad(prefix, context, source, typesetter, n,
                    GroupType.MATH_GROUP);
        nc.add(new ChoiceNoad(display, text, script, scriptScript));
        prefix.set(f);
    }

}
