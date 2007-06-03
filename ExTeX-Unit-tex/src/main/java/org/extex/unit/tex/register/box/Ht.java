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

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\ht</code>.
 * 
 * <doc name="ht">
 * <h3>The Primitive <tt>\ht</tt></h3>
 * <p>
 * The primitive <tt>\ht</tt> refers to the height of a box register. It can
 * be used in various contexts.
 * </p>
 * <p>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ht&rang;
 *      &rarr; <tt>\ht</tt> {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,String)
 *        &lang;box register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.core.dimen#Dimen(Context,TokenSource)
 *        &lang;dimen&rang;}   </pre>
 * 
 * </p>
 * 
 * <h4>Examples</h4>
 * <p>
 * 
 * <pre class="TeXSample">
 *    \ht42  </pre>
 *  <pre class="TeXSample">
 *   \advance\dimen12 \ht0
 *   \ht12=123pt
 *  </pre>
 * 
 * </p>
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Ht extends AbstractAssignment
        implements
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
    public Ht(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = Setbox.getKey(context, source, typesetter, getName());
        source.getOptionalEquals(context);
        Dimen d = source.parseDimen(context, source, typesetter);

        Box box = context.getBox(key);
        if (box != null) {
            box.setHeight(d);
        }
        // TODO gene: treatment of \global correct?
        prefix.clearGlobal();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return convertDimen(context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.DimenConvertible#convertDimen(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Box box = context.getBox(//
            Setbox.getKey(context, source, typesetter, getName()));
        return (box == null ? 0 : box.getHeight().getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
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
            throws HelpingException,
                TypesetterException {

        Box box = context.getBox(//
            Setbox.getKey(context, source, typesetter, getName()));
        FixedDimen d = (box == null ? Dimen.ZERO_PT : box.getHeight());
        try {
            return context.getTokenFactory().toTokens(d.toString());
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

}
