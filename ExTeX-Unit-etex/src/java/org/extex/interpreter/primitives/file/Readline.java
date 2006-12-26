/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.file;

import java.util.logging.Logger;

import org.extex.interpreter.Flags;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.Tokenizer;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.primitives.file.AbstractFileCode;
import org.extex.interpreter.primitives.macro.util.MacroCode;
import org.extex.interpreter.primitives.macro.util.MacroPattern;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.file.InFile;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.logger.LogEnabled;


/**
 * This class provides an implementation for the primitive <code>\readline</code>.
 *
 * <doc name="readline">
 * <h3>The Primitive <tt>\readline</tt></h3>
 * <p>
 *  The primitive <tt>\readline</tt> read characters from an input stream until
 *  the end of line is encountered. The characters are translated to tokens
 *  with the category code OTHER except the white-space characters which receive
 *  the category code SPACE. This mapping is performed ignoring the setting of
 *  {@link org.extex.interpreter.primitives.register.CatcodePrimitive <tt>\catcode</tt>}.
 *  The resulting token list is bound to the control sequence given.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;readline&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\readline</tt> {@linkplain
 *        org.extex.interpreter.primitives.file.AbstractFileCode#scanInFileKey(Context,TokenSource,Typesetter)
 *        &lang;infile&nbsp;name&rang;} <tt>to</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context)
 *        &lang;control sequence&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang; </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 * \openin3= abc.def
 * \readline3 to \line
 * \closein3 </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Readline extends AbstractAssignment implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060411L;

    /**
     * The field <tt>TOKENIZER</tt> contains the tokenizer to use for this
     * primitive.
     */
    private static final Tokenizer TOKENIZER = new Tokenizer() {

        /**
         * @see org.extex.interpreter.Tokenizer#getCatcode(
         *      org.extex.type.UnicodeChar)
         */
        public Catcode getCatcode(final UnicodeChar c) {

            return (c.getCodePoint() == ' ' ? Catcode.SPACE : Catcode.OTHER);
        }

        /**
         * @see org.extex.interpreter.Tokenizer#getNamespace()
         */
        public String getNamespace() {

            return Namespace.DEFAULT_NAMESPACE;
        }

    };

    /**
     * The field <tt>logger</tt> contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Readline(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        String key = AbstractFileCode
                .scanInFileKey(context, source, typesetter);

        if (!source.getKeyword(context, "to")) {
            throw new HelpingException(getLocalizer(), "TTP.MissingToForRead",
                    printableControlSequence(context));
        }
        CodeToken cs = source.getControlSequence(context);

        InFile file = context.getInFile(key);

        if (file == null || !file.isOpen()) {
            file = context.getInFile(null);
            if (file == null || !file.isOpen()) {
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

        Tokens toks = file.read(context.getTokenFactory(), TOKENIZER);

        if (toks == null) {
            throw new HelpingException(getLocalizer(), "TTP.EOFinRead");
        }

        context.setCode(cs, new MacroCode(cs.getName(), prefix,
                MacroPattern.EMPTY, toks), prefix.clearGlobal());
    }

    /**
     * @see org.extex.util.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(final Logger theLogger) {

        this.logger = theLogger;
    }

}
