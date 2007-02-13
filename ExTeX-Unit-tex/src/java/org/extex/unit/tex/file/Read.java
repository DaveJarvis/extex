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

package org.extex.unit.tex.file;

import java.util.logging.Logger;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.file.InFile;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.unit.tex.macro.util.MacroCode;
import org.extex.unit.tex.macro.util.MacroPattern;
import org.extex.util.framework.logger.LogEnabled;

/**
 * This class provides an implementation for the primitive <code>\read</code>.
 *
 * <doc name="read">
 * <h3>The Primitive <tt>\read</tt></h3>
 * <p>
 *  The primitive <tt>\read</tt> read a line of text from the given input stream
 *  into a control sequence. The input stream should be opened with
 *  {@linkplain org.extex.unit.tex.file.Openin <tt>\openin</tt>}.
 *  If a stream name is used which has not been opened or
 *  has already been closed then the default input stream is used instead.
 * </p>
 * <p>
 *  The primitive can be prefixed with
 *  {@linkplain org.extex.unit.tex.prefix.Global <tt>\global</tt>}.
 *  In this case the assignment to the control sequence is global instead of
 *  the default of assigning it locally to the current group.
 * </p>
 * <p>
 *  The primitive implements an assignment. Thus the definition of
 *  <tt>\afterassignment</tt> and <tt>\globaldefs</tt> are honored.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;read&rang;
 *      &rarr; &lang;optional prefix&rang;<tt>\read</tt> {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanInFileKey(Context,TokenSource,Typesetter)
 *        &lang;infile&nbsp;name&rang;} <tt>to</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *   \openin3= abc.def
 *   \read3 to \line
 *   \closein3 </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4441 $
 */
public class Read extends AbstractAssignment implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20061001L;

    /**
     * The field <tt>logger</tt> contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Read(final String name) {

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
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key =
                AbstractFileCode.scanInFileKey(context, source, typesetter);

        if (!source.getKeyword(context, "to")) {
            throw new HelpingException(getLocalizer(), "TTP.MissingToForRead",
                printableControlSequence(context));
        }
        CodeToken cs = source.getControlSequence(context, typesetter);

        InFile file = context.getInFile(key);

        if (file == null || !file.isOpen()) {
            file = context.getInFile(null);
            if (!file.isOpen()) {
                throw new HelpingException(getLocalizer(), "TTP.EOFinRead",
                    printableControlSequence(context));
            }
        }
        if (!file.isFileStream()) {
            Interaction interaction = context.getInteraction();
            if (interaction != Interaction.ERRORSTOPMODE) {
                throw new HelpingException(getLocalizer(), "TTP.NoTermRead",
                    printableControlSequence(context));
            }
        }

        if (file.isStandardStream()) {
            logger.severe(printable(context, cs) + "=");
        }

        Tokens toks =
                file.read(context.getTokenFactory(), context.getTokenizer());
        if (toks == null) {
            throw new HelpingException(getLocalizer(), "TTP.EOFinRead",
                printableControlSequence(context));
        }
        context.setCode(cs, new MacroCode(cs.getName(), prefix,
            MacroPattern.EMPTY, toks), prefix.clearGlobal());
    }

    /**
     * Setter for the logger.
     *
     * @param log the logger to use
     *
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger log) {

        this.logger = log;
    }

}
