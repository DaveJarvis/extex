/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.conditional;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.typesetter.Typesetter;


/**
 * This class provides an implementation for the primitive <code>\fi</code>.
 *
 * <doc name="fi">
 * <h3>The Primitive <tt>\fi</tt></h3>
 * <p>
 *  This primitive indicates the end of an conditional. As such it can not
 *  appear alone but only in combination with a preceding <tt>\if*</tt>.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;fi&rang;
 *     &rarr; <tt>\fi</tt>  </pre>
 * </p>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \fi  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4439 $
 */
public class Fi extends AbstractCode implements ExpandableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Fi(final String name) {

        super(name);
    }

    /**
     * Executes the primitive.
     * <p>
     *  This primitive can only be seen when a conditional has been opened
     *  before for which the else branch is expanded. Thus only the conditional
     *  stack has to be updated. If the conditional stack is already empty then
     *  an exception is raised.
     * </p>
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

        if (context.popConditional() == null) {
            throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                    printableControlSequence(context));
        }
    }

    /**
     * Expands the primitive.
     * <p>
     *  This primitive can only be seen when a conditional has been opened
     *  before for which the else branch is expanded. Thus only the conditional
     *  stack has to be updated. If the conditional stack is already empty then
     *  an exception is raised.
     * </p>
     *
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        if (context.popConditional() == null) {
            throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                    printableControlSequence(context));
        }
    }

}
