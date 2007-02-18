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

package org.extex.unit.tex.table;

import java.util.List;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.MissingLeftBraceException;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.type.Locator;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.listMaker.HAlignListMaker;
import org.extex.typesetter.type.NodeList;

/**
 * This class provides an implementation for the primitive <code>\halign</code>.
 *
 * <doc name="halign">
 * <h3>The Primitive <tt>\halign</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;halign&rang;
 *       &rarr; <tt>\halign</tt> &lang;box specification&rang; <tt>{</tt> &lang;preamble&rang; <tt>\cr</tt> &lang;rows&rang; <tt>}</tt>
 *
 *    &lang;box specification&rang;
 *        &rarr;
 *         | <tt>to</tt> &lang;rule dimension&rang;
 *         | <tt>spread</tt> &lang;rule dimension&rang;
 *
 *    &lang;rows&rang;
 *       &rarr;
 *       | &lang;row&rang; &lang;rows&rang;
 *
 *    &lang;preamble&rang;
 *       &rarr; ...   </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \halign  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Halign extends AbstractAlign implements Boxable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param name the name for tracing and debugging
     */
    public Halign(final String name) {

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

        Flags f = prefix.copy();
        prefix.clear();
        typesetter.add(getNodes(context, source, typesetter));
        prefix.set(f);
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
            final Typesetter typesetter, final Token insert)
            throws InterpreterException {

        //TODO gene: treat insert
        return new Box(getNodes(context, source, typesetter));
    }

    /**
     * Getter for the nodes.
     *
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @return the list of nodes gathered
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     */
    private NodeList getNodes(final Context context, final TokenSource source,
            final Typesetter typesetter)
            throws InterpreterException {

        FixedDimen width = null;
        boolean spread = false;

        if (source.getKeyword(context, "to")) {
            width = Dimen.parse(context, source, typesetter);
        } else if (source.getKeyword(context, "spread")) {
            width = Dimen.parse(context, source, typesetter);
            spread = true;
        }
        Token t = source.getToken(context);
        Locator locator = source.getLocator();
        if (t == null) {
            throw new EofException(printableControlSequence(context));
        } else if (t.isa(Catcode.LEFTBRACE)) {
            List preamble = getPreamble(context, source);
            typesetter.push(new HAlignListMaker(typesetter.getManager(),
                context, source, preamble, width, spread));
        } else {
            throw new MissingLeftBraceException(
                printableControlSequence(context));
        }

        context.openGroup(GroupType.ALIGN_GROUP, locator, t); //gene: correct value?

        source.executeGroup();
        return typesetter.complete((TypesetterOptions) context);
    }

}
