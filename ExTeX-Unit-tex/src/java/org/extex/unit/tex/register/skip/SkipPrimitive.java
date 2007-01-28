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

package org.extex.unit.tex.register.skip;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.arithmetic.Advanceable;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.interpreter.type.arithmetic.Multiplyable;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.glue.Glue;
import org.extex.interpreter.type.glue.GlueConvertible;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.util.exception.GeneralException;

/**
 * This class provides an implementation for the primitive <code>\skip</code>.
 * It sets the named skip register to the value given,
 * and as a side effect all prefixes are zeroed.
 *
 * <doc name="skip">
 * <h3>The Primitive <tt>\skip</tt></h3>
 * <p>
 *  The primitive <tt>\skip</tt> provides access to a skip register. In a skip
 *  register some glue value can be stored. A glue value consists of a
 *  natural length and optionally some stretchability and shrinkability
 *  components.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;skip&rang;
 *        &rarr; &lang;optional prefix&rang; <tt>\skip</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context,TokenSource,Typesetter,String)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.type.glue.Glue#parse(TokenSource,Context,Typesetter)
 *        &lang;glue&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \skip 1em plus 1pt minus 1pt  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class SkipPrimitive extends AbstractSkip
        implements
            Advanceable,
            Multiplyable,
            Divideable,
            GlueConvertible,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public SkipPrimitive(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Advanceable#advance(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void advance(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        Glue g = Glue.parse(source, context, typesetter);
        g.add(context.getGlue(key));
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getOptionalEquals(context);
        Glue g = Glue.parse(source, context, typesetter);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.glue.GlueConvertible#convertGlue(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Glue convertGlue(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        String key = getKey(context, source, typesetter);
        return context.getGlue(key);
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Divideable#divide(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void divide(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = Count.scanInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(
                    printableControlSequence(context));
        }

        Glue g = new Glue(context.getGlue(key));
        g.multiplyAll(1, value);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.arithmetic.Multiplyable#multiply(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void multiply(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = Count.scanInteger(context, source, null);

        Glue g = new Glue(context.getGlue(key));
        g.multiplyAll(value, 1);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        String key = getKey(context, source, typesetter);
        try {
            return context.getGlue(key).toToks(context.getTokenFactory());
        } catch (InterpreterException e) {
            throw e;
        } catch (GeneralException e) {
            throw new InterpreterException(e);
        }
    }

}
