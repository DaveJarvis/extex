/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.interpreter.primitives.register.box;

import java.io.Serializable;

import de.dante.extex.interpreter.ExpandableCode;
import de.dante.extex.interpreter.Flags;
import de.dante.extex.interpreter.Theable;
import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.type.box.Box;
import de.dante.extex.interpreter.type.count.CountConvertible;
import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.interpreter.type.dimen.DimenConvertible;
import de.dante.extex.interpreter.type.tokens.Tokens;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;

/**
 * This class provides an implementation for the primitive <code>\wd</code>.
 *
 * <doc name="wd">
 * <h3>The Primitive <tt>\wd</tt></h3>
 * <p>
 *  ...
 * </p>
 * <p>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    <tt>\wd</tt> &lang;8-bit number&rang; </pre>
 * </p>
 * <p>
 *  Examples:
 *  <pre class="TeXSample">
 *    \wd42  </pre>
 * </p>
 * </doc>
 *
 *
 * <pre>
 *  \advance\dimen12 \wd0
 *  \wd12=123pt
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Wd extends Setbox implements Serializable, ExpandableCode,
    Theable, CountConvertible, DimenConvertible {

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public Wd(final String name) {
        super(name);
    }

    /**
     * @see de.dante.extex.interpreter.Code#execute(
     *      de.dante.extex.interpreter.Flags,
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource,
     *      de.dante.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws GeneralException {

        Box box = context.getBox(getKey(source, context.getNamespace()));
        source.getOptionalEquals();
        Dimen d = new Dimen(context, source);

        if (box != null) {
            box.setWidth(d);
        }

        prefix.clear();
    }

    /**
     * @see de.dante.extex.interpreter.ExpandableCode#expand(
     *      de.dante.extex.interpreter.Flags,
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource,
     *      de.dante.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws GeneralException {

        source.push(the(context, source));
    }

    /**
     * @see de.dante.extex.interpreter.Theable#the(
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource)
     */
    public Tokens the(final Context context, final TokenSource source)
            throws GeneralException {

        Box box = context.getBox(getKey(source, context.getNamespace()));
        Dimen d = (box == null ? Dimen.ZERO_PT : box.getWidth());
        return d.toToks(context.getTokenFactory());
    }

    /**
     * @see de.dante.extex.interpreter.type.count.CountConvertible#convertCount(
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource)
     */
    public long convertCount(final Context context, final TokenSource source)
            throws GeneralException {

        return convertDimen(context, source);
    }

    /**
     * @see de.dante.extex.interpreter.type.dimen.DimenConvertible#convertDimen(
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource)
     */
    public long convertDimen(final Context context, final TokenSource source)
            throws GeneralException {

        Box b = context.getBox(getKey(source, context.getNamespace()));
        return (b == null ? 0 : b.getWidth().getValue());
    }

}
