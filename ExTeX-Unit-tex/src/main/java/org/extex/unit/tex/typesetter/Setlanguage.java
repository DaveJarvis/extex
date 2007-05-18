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

package org.extex.unit.tex.typesetter;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.ParagraphObserver;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;

/**
 * This class provides an implementation for the primitive
 * <code>\setlanguage</code>.
 * 
 * <doc name="setlanguage">
 * <h3>The Primitive <tt>\\</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;setlanguage&rang;
 *       &rarr; <tt>\setlanguage</tt> &lang;number&rang; </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \setlanguage2  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Setlanguage extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * This observer can be used to restore the value of the registers
     * <tt>language</tt> and <tt>lang</tt> t the end of a paragraph.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4770 $
     */
    private static class ParObserver implements ParagraphObserver {

        /**
         * The field <tt>context</tt> contains the interpreter context.
         */
        private Context context;

        /**
         * The field <tt>language</tt> contains the value of the language
         * register to restore.
         */
        private long language;

        /**
         * The field <tt>toks</tt> contains the value of the register lang to
         * restore.
         */
        private Tokens lang;

        /**
         * Creates a new object.
         * 
         * @param context the context
         */
        public ParObserver(Context context) {

            super();
            this.context = context;
            language = context.getCount("language").getValue();
            lang = context.getToks("lang");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.ParagraphObserver#atParagraph(
         *      org.extex.typesetter.type.NodeList)
         */
        public void atParagraph(NodeList nodes) throws Exception {

            context.setCount("language", language, false);
            context.setToks("lang", lang, false);
        }
    }

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public Setlanguage(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        typesetter.afterParagraph(new ParObserver(context));

        Token token = source.getToken(context);
        source.push(token);

        if (token instanceof LeftBraceToken) {
            Tokens tokens;
            try {
                tokens = source.getTokens(context, source, typesetter);
            } catch (EofException e) {
                throw new EofInToksException(printableControlSequence(context));
            }
            context.setToks("lang", tokens, false);
        } else {
            long no = source.parseInteger(context, source, typesetter);
            context.setCount("language", no, false);
            context.setToks("lang", Tokens.EMPTY, false);
        }
    }

}
