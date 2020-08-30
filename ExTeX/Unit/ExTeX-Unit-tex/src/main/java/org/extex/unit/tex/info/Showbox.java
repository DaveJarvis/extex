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

package org.extex.unit.tex.info;

import java.util.logging.Logger;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.register.box.Setbox;

/**
 * This class provides an implementation for the primitive {@code \showbox}
 * .
 * 
 * <p>The Primitive {@code \showbox}</p>
 * <p>
 * The primitive {@code \showbox} produces a listing of the box register given
 * as parameter. The listing is restricted in breadth and depth by the count
 * registers {@code \showboxbreadth} and {@code \showboxdepth} respectively.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;showbox&rang;
 *      &rarr; {@code \showbox} {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,CodeToken)
 *        &lang;box&nbsp;register&nbsp;name&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \showbox 1  </pre>
 * 
 *
 * 
 * <p>The Count Parameter {@code \showboxbreadth}</p>
 * <p>
 * The count register {@code \showboxbreadth} contains the breadth to which the
 * box produced by {@code \showbox} should be presented.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;showboxbreadth&rang;
 *      &rarr; {@code \showboxbreadth} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \showboxbreadth=16  </pre>
 * 
 *
 * 
 * <p>The Count Parameter {@code \showboxdepth}</p>
 * <p>
 * The count register {@code \showboxdepth} contains the depth to which the box
 * produced by {@code \showbox} should be presented.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;showboxdepth&rang;
 *      &rarr; {@code \showboxdepth} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \showboxdepth=16  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Showbox extends AbstractCode implements LogEnabled {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code logger} contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Showbox(CodeToken token) {

        super(token);
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = Setbox.getKey(context, source, typesetter, getToken());
        Box b = context.getBox(key);

        if (b == null) {
            logger.info(getLocalizer().format("TTP.Show.void",
                context.esc("box" + key)));
        } else {
            long depth = context.getCount("showboxdepth").getValue();
            long width = context.getCount("showboxbreadth").getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(context.esc("box" + key));
            sb.append("=\n");
            b.getNodes().toString(sb, "", (int) depth, (int) width);
            sb.append("\n");
            logger.info(sb.toString());
        }
        logger.info(getLocalizer().format("TTP.Show.OK"));
    }

}
