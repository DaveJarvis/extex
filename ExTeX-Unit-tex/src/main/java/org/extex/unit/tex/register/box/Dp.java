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

package org.extex.unit.tex.register.box;

import java.io.Serializable;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.NoHelpException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.CountConvertible;
import org.extex.scanner.DimenConvertible;
import org.extex.scanner.DimenParser;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\dp</code>.
 * 
 * <doc name="dp">
 * <h3>The Primitive <tt>\dp</tt></h3>
 * <p>
 * The primitive <tt>\dp</tt> refers to the depth of a box register. It can be
 * used in various contexts.
 * </p>
 * 
 * <h4>Execution of the Primitive</h4>
 * <p>
 * If the primitive is used in a context it initiated an assignment to the
 * actual depth of the box register. This has an effect only in the case that
 * the box register is not void.
 * </p>
 * <p>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;dp&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\dp</tt> {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,String)
 *        &lang;box register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.core.dimen#Dimen(org.extex.interpreter.context.Context,org.extex.interpreter.TokenSource)
 *        &lang;dimen&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 * 
 * </p>
 * 
 * <h4>Examples</h4>
 * <p>
 * 
 * <pre class="TeXSample">
 *    \dp42 = 12mm  </pre>
 *  <pre class="TeXSample">
 *    \dp42 = \dimen3  </pre>
 * 
 * </p>
 * 
 * <h4>Expansion of the Primitive</h4>
 * <p>
 * In an expansion context the primitive results in the the current depth of the
 * given box register. In case that the box register is empty the result is
 * 0&nbsp;pt.
 * </p>
 * 
 * <h4>Syntax</h4>
 * <p>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    <tt>\dp</tt> {@linkplain
 *      org.extex.scanner.CountParser#scanNumber(Context,TokenSource,Typesetter)
 *      &lang;8-bit&nbsp;number&rang;} </pre>
 * 
 * </p>
 * 
 * <h4>Examples</h4>
 * <p>
 * 
 * <pre class="TeXSample">
 *    \dimen0 = \dp42  </pre>
 * 
 * <pre class="TeXSample">
 *    \the\dp42  </pre>
 * 
 * </p>
 * 
 * <h4>Conversion to a Count</h4>
 * <p>
 * The primitive is convertible into a count. It can be used wherever an integer
 * quantity is expected.
 * </p>
 * 
 * <h4>Interaction with <tt>\the</tt></h4>
 * <p>
 * The primitive <tt>\the</tt> can be applied to this primitive. In this case
 * it results in a token list representing the value in points.
 * </p>
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Dp extends AbstractAssignment
        implements
            Serializable,
            ExpandableCode,
            Theable,
            CountConvertible,
            DimenConvertible {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Dp(String name) {

        super(name);
    }

    /**
     * The method <tt>assign</tt> is the core of the functionality of
     * {@link #execute(Flags, Context, TokenSource, Typesetter) execute()}.
     * This method is preferable to <tt>execute()</tt> since the
     * <tt>execute()</tt> method provided in this class takes care of
     * <tt>\afterassignment</tt> and <tt>\globaldefs</tt> as well.
     * 
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException, TypesetterException {

        String key = Setbox.getKey(context, source, typesetter, getName());
        source.getOptionalEquals(context);
        Dimen d = DimenParser.parse(context, source, typesetter);

        Box box = context.getBox(key);
        if (box != null) {
            box.setDepth(d);
        }
        // TODO gene: treatment of \global correct?
        prefix.clearGlobal();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return convertDimen(context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.DimenConvertible#convertDimen(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Box box = context.getBox(//
            Setbox.getKey(context, source, typesetter, getName()));
        return (box == null ? 0 : box.getDepth().getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.push(the(context, source, typesetter));
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
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        Box box = context.getBox(//
            Setbox.getKey(context, source, typesetter, getName()));
        FixedDimen d = (box == null ? Dimen.ZERO_PT : box.getDepth());
        try {
            return context.getTokenFactory().toTokens(d.toString());
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

}
