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

package org.extex.unit.tex.typesetter.leaders;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.interpreter.type.box.RuleConvertible;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.AlignedLeadersNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.unit.tex.typesetter.spacing.HorizontalSkip;
import org.extex.unit.tex.typesetter.spacing.VerticalSkip;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive
 * <code>\leaders</code>.
 *
 * <doc name="leaders">
 * <h3>The Primitive <tt>\leaders</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;leaders&rang;
 *      &rarr; <tt>\leaders</tt> ...  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \leaders\hrule\hfill  </pre>
 *  <pre class="TeXSample">
 *    \leaders\hbox{ . }\hfill  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Leaders extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Leaders(final String name) {

        super(name);
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

        CodeToken cs = source.getControlSequence(context, typesetter);
        Code code = context.getCode(cs);

        if (code == null) {
            throw new UndefinedControlSequenceException(printable(context, cs));
        }

        boolean horizontal;

        Node node = null;
        if (code instanceof Boxable) {
            Box b = ((Boxable) code).getBox(context, source, typesetter, null);
            node = b.getNodes();
            horizontal = b.isHbox();
        } else if (code instanceof RuleConvertible) {
            node =
                    ((RuleConvertible) code).getRule(context, source,
                        typesetter);
            horizontal = ((RuleNode) node).isHorizontal();
        } else {
            throw new HelpingException(getLocalizer(), "TTP.BoxExpected");
        }

        CodeToken vskip = source.getControlSequence(context, typesetter);
        code = context.getCode(vskip);

        if (code == null) {
            throw new UndefinedControlSequenceException(//
                context.esc(vskip.getName()));
        }

        FixedGlue skip;

        if (horizontal) {
            if (!(code instanceof HorizontalSkip)) {
                throw new HelpingException(getLocalizer(),
                    "TTP.BadGlueAfterLeaders");
            }
            skip = ((HorizontalSkip) code).getGlue(context, source, typesetter);
        } else {
            if (!(code instanceof VerticalSkip)) {
                throw new HelpingException(getLocalizer(),
                    "TTP.BadGlueAfterLeaders");
            }
            skip = ((VerticalSkip) code).getGlue(context, source, typesetter);
        }

        try {
            typesetter.add(new AlignedLeadersNode(node, skip, horizontal));
        } catch (TypesetterException e) {
            throw new InterpreterException(e);
        } catch (ConfigurationException e) {
            throw new InterpreterException(e);
        }
    }

}
