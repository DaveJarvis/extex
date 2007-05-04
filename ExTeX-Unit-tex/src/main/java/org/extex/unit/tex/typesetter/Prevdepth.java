/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.NoHelpException;
import org.extex.interpreter.exception.helping.CantUseInException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.CountConvertible;
import org.extex.scanner.DimenConvertible;
import org.extex.scanner.DimenParser;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;

/**
 * This class provides an implementation for the primitive
 * <code>\prevdepth</code>.
 * 
 * <doc name="prevdepth">
 * <h3>The Primitive <tt>\prevdepth</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;prevdepth&rang;
 *      &rarr; <tt>\prevdepth</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} &lang;dimen value&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \prevdepth=123pt  </pre>
 *  <pre class="TeXSample">
 *    \dimen0=\prevdepth  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Prevdepth extends AbstractAssignment
        implements
            CountConvertible,
            DimenConvertible,
            Theable {

    /**
     * The field <tt>IGNORE</tt> contains the numerical value which represents
     * the ignored value. This will be mapped to null.
     */
    private static final long IGNORE = -65536000;

    /**
     * The field <tt>IGNORE_DIMEN</tt> contains the value which represents the
     * ignored value. This will be mapped to null.
     */
    private static final Dimen IGNORE_DIMEN = new Dimen(IGNORE);

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
    public Prevdepth(String name) {

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
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getOptionalEquals(context);
        Dimen pd = DimenParser.parse(context, source, typesetter);
        if (pd.getValue() < IGNORE) {
            pd = null;
        }
        try {
            typesetter.setPrevDepth(pd);
        } catch (TypesetterUnsupportedException e) {
            throw new CantUseInException(printableControlSequence(context),
                typesetter.getMode().toString());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        FixedDimen prevDepth;
        try {
            prevDepth = typesetter.getListMaker().getPrevDepth();
        } catch (TypesetterUnsupportedException e) {
            throw new HelpingException(getLocalizer(), "TTP.ImproperSForPD",
                printableControlSequence(context));
        }

        return prevDepth != null ? prevDepth.getValue() : IGNORE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.DimenConvertible#convertDimen(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        FixedDimen prevDepth;
        try {
            prevDepth = typesetter.getListMaker().getPrevDepth();
        } catch (TypesetterUnsupportedException e) {
            throw new HelpingException(getLocalizer(), "TTP.ImproperSForPD",
                printableControlSequence(context));
        }

        return prevDepth != null ? prevDepth.getValue() : IGNORE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Theable#the(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        FixedDimen prevDepth;
        try {
            prevDepth = typesetter.getListMaker().getPrevDepth();
        } catch (TypesetterUnsupportedException e) {
            throw new HelpingException(getLocalizer(), "TTP.ImproperSForPD",
                printableControlSequence(context));
        }

        if (prevDepth == null) {
            prevDepth = IGNORE_DIMEN;
        }

        try {
            return context.getTokenFactory().toTokens(prevDepth.toString());
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
