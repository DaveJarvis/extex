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

package org.extex.unit.tex.typesetter.box;

import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;

import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.DimenParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.NodeList;
import org.extex.unit.tex.register.box.Setbox;

/**
 * This class provides an implementation for the primitive <code>\vsplit</code>.
 *
 * <doc name="vsplit">
 * <h3>The Primitive <tt>\vsplit</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;vsplit&rang;
 *       &rarr; <tt>\vsplit</tt> {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,String)
 *        &lang;box register name&rang;} </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \vsplit 2 to 123pt  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Vsplit extends AbstractCode implements Boxable, LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>logger</tt> contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Vsplit(final String name) {

        super(name);
    }

    /**
     * Setter for the logger.
     *
     * @param log the logger to use
     *
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger log) {

        this.logger = log;
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
     * @throws ConfigurationException in case of an configuration error
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

        NodeList nl = vsplit(context, source, typesetter);
        typesetter.add(nl);
    }

    /**
     * Getter for the content as Box.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use
     * @param insert the token to insert either at the beginning of the box or
     *   after the box has been gathered. If it is <code>null</code> then
     *   nothing is inserted
     *
     * @return an appropriate Box
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.unit.tex.register.box.BoxPrimitive#getBox(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public Box getBox(final Context context, final TokenSource source,
            final Typesetter typesetter, Token insert)
            throws InterpreterException {

        //TODO gene: treat insert
        return new Box(vsplit(context, source, typesetter));
    }

    /**
     * Perform the operation of the primitive <tt>\vsplit</tt> and return the
     * result as a NodeList.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the nodes of the vlist cut off
     *
     * @throws InterpreterException in case of an error
     */
    private NodeList vsplit(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        String key = Setbox.getKey(context, source, typesetter, getName());
        if (!source.getKeyword(context, "to")) {
            throw new HelpingException(getLocalizer(), "TTP.MissingToForVsplit");
        }
        Dimen ht = DimenParser.parse(context, source, typesetter);
        Box b = context.getBox(key);
        if (b == null || !b.isVbox()) {
            throw new HelpingException(getLocalizer(), "TTP.SplittingNonVbox",
                printableControlSequence(context), context.esc("vbox"));
        }
        // TODO gene: set splitmark etc
        try {
            return b
                .vsplit(ht, //
                    (Count.ONE.le(context.getCount("tracingpages"))
                            ? logger
                            : null));
        } catch (OperationNotSupportedException e) {
            // just to be sure. This should not happen
            throw new HelpingException(getLocalizer(), "TTP.SplittingNonVbox",
                printableControlSequence(context), context.esc("vbox"));
        }
    }

}
