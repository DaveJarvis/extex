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

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.glue.Glue;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.arithmetic.Advanceable;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.interpreter.type.arithmetic.Multiplyable;
import org.extex.scanner.CountParser;
import org.extex.scanner.GlueConvertible;
import org.extex.scanner.GlueParser;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\skip</code>.
 * It sets the named skip register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <doc name="skip">
 * <h3>The Primitive <tt>\skip</tt></h3>
 * <p>
 * The primitive <tt>\skip</tt> provides access to a skip register. In a skip
 * register some glue value can be stored. A glue value consists of a natural
 * length and optionally some stretchability and shrinkability components.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;skip&rang;
 *        &rarr; &lang;optional prefix&rang; <tt>\skip</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context,TokenSource,Typesetter,String)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.scanner.GlueParser#parse(TokenSource,Context,Typesetter)
 *        &lang;glue&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
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
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public SkipPrimitive(String name) {

        super(name);
    }

    /**
     * This method is called when the macro <tt>\advance</tt> has been seen.
     * It performs the remaining tasks for the expansion.
     * 
     * @param prefix the prefix for the command
     * @param context the processor context
     * @param source the token source to parse
     * @param typesetter the typesetter
     * 
     * @see org.extex.interpreter.type.arithmetic.Advanceable#advance(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        Glue g = GlueParser.parse(source, context, typesetter);
        g.add(context.getGlue(key));
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getOptionalEquals(context);
        Glue g = GlueParser.parse(source, context, typesetter);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.GlueConvertible#convertGlue(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Glue convertGlue(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        return context.getGlue(key);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.arithmetic.Divideable#divide(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = CountParser.scanInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }

        Glue g = new Glue(context.getGlue(key));
        g.multiplyAll(1, value);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.arithmetic.Multiplyable#multiply(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        source.getKeyword(context, "by");
        long value = CountParser.scanInteger(context, source, null);

        Glue g = new Glue(context.getGlue(key));
        g.multiplyAll(value, 1);
        context.setGlue(key, g, prefix.clearGlobal());
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        String key = getKey(context, source, typesetter);
        try {
            return context.getTokenFactory().toTokens(
                context.getGlue(key).toString());
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

}
