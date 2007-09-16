/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional.analyze;

import java.util.HashMap;
import java.util.Map;

import org.extex.core.count.Count;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.etex.conditional.Ifcsname;
import org.extex.unit.etex.conditional.Ifdefined;
import org.extex.unit.etex.conditional.Iffontchar;
import org.extex.unit.tex.conditional.If;
import org.extex.unit.tex.conditional.Ifcase;
import org.extex.unit.tex.conditional.Ifcat;
import org.extex.unit.tex.conditional.Ifdim;
import org.extex.unit.tex.conditional.Ifeof;
import org.extex.unit.tex.conditional.Iffalse;
import org.extex.unit.tex.conditional.Ifhbox;
import org.extex.unit.tex.conditional.Ifhmode;
import org.extex.unit.tex.conditional.Ifinner;
import org.extex.unit.tex.conditional.Ifmmode;
import org.extex.unit.tex.conditional.Ifnum;
import org.extex.unit.tex.conditional.Ifodd;
import org.extex.unit.tex.conditional.Iftrue;
import org.extex.unit.tex.conditional.Ifvbox;
import org.extex.unit.tex.conditional.Ifvmode;
import org.extex.unit.tex.conditional.Ifvoid;
import org.extex.unit.tex.conditional.Ifx;

/**
 * This class provides an implementation for the primitive
 * <code>\currentiftype</code>.
 * 
 * <doc name="currentiftype">
 * <h3>The Primitive <tt>\currentiftype</tt></h3>
 * <p>
 * The primitive <tt>\currentiftype</tt> is an internal count register. It
 * returns an indication of the conditional currently in use. If no conditional
 * is active then <tt>0</tt> is returned. The following table lists the return
 * values for the different types of conditionals:
 * </p>
 * <table format="ll">
 * <tr>
 * <td><tt>/if</tt></td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td><tt>/ifcat</tt></td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td><tt>/ifnum</tt></td>
 * <td>3</td>
 * </tr>
 * <tr>
 * <td><tt>/ifdim</tt></td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td><tt>/ifodd</tt></td>
 * <td>5</td>
 * </tr>
 * <tr>
 * <td><tt>/ifvmode</tt></td>
 * <td>6</td>
 * </tr>
 * <tr>
 * <td><tt>/ifhmode</tt></td>
 * <td>7</td>
 * </tr>
 * <tr>
 * <td><tt>/ifmmode</tt></td>
 * <td>8</td>
 * </tr>
 * <tr>
 * <td><tt>/ifinner</tt></td>
 * <td>9</td>
 * </tr>
 * <tr>
 * <td><tt>/ifvoid</tt></td>
 * <td>10</td>
 * </tr>
 * <tr>
 * <td><tt>/ifhbox</tt></td>
 * <td>11</td>
 * </tr>
 * <tr>
 * <td><tt>/ifvbox</tt></td>
 * <td>12</td>
 * </tr>
 * <tr>
 * <td><tt>/ifx</tt></td>
 * <td>13</td>
 * </tr>
 * <tr>
 * <td><tt>/ifeof</tt></td>
 * <td>14</td>
 * </tr>
 * <tr>
 * <td><tt>/iftrue</tt></td>
 * <td>15</td>
 * </tr>
 * <tr>
 * <td><tt>/iffalse</tt></td>
 * <td>16</td>
 * </tr>
 * <tr>
 * <td><tt>/ifcase</tt></td>
 * <td>17</td>
 * </tr>
 * <tr>
 * <td><tt>/ifdefined</tt></td>
 * <td>18</td>
 * </tr>
 * <tr>
 * <td><tt>/ifcsname</tt></td>
 * <td>19</td>
 * </tr>
 * <tr>
 * <td><tt>/iffontchar</tt></td>
 * <td>20</td>
 * </tr>
 * </table>
 * <p>
 * The value returned by the primitive is negated if the expansion appears in
 * the else branch.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;currentiftype&rang;
 *     &rarr; <tt>\currentiftype</tt> </pre>
 * 
 * </p>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \count0=\currentiftype  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4435 $
 */
public class Currentiftype extends AbstractCode
        implements
            CountConvertible,
            Theable {

    /**
     * The field <tt>map</tt> contains the map from \if implementations to
     * long values.
     */
    private static final Map<Class<?>, Count> MAP =
            new HashMap<Class<?>, Count>();

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    {
        MAP.put(If.class, new Count(1));
        MAP.put(Ifcat.class, new Count(2));
        MAP.put(Ifnum.class, new Count(3));
        MAP.put(Ifdim.class, new Count(4));
        MAP.put(Ifodd.class, new Count(5));
        MAP.put(Ifvmode.class, new Count(6));
        MAP.put(Ifhmode.class, new Count(7));
        MAP.put(Ifmmode.class, new Count(8));
        MAP.put(Ifinner.class, new Count(9));
        MAP.put(Ifvoid.class, new Count(10));
        MAP.put(Ifhbox.class, new Count(11));
        MAP.put(Ifvbox.class, new Count(12));
        MAP.put(Ifx.class, new Count(13));
        MAP.put(Ifeof.class, new Count(14));
        MAP.put(Iftrue.class, new Count(15));
        MAP.put(Iffalse.class, new Count(16));
        MAP.put(Ifcase.class, new Count(17));
        MAP.put(Ifdefined.class, new Count(18));
        MAP.put(Ifcsname.class, new Count(19));
        MAP.put(Iffontchar.class, new Count(20));
    }

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Currentiftype(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Conditional conditional = context.getConditional();
        if (conditional == null) {
            return 0;
        }
        Count l = MAP.get(conditional.getPrimitive().getClass());
        return (l == null ? 0 : //
                conditional.isNeg() ? -l.getValue() : l.getValue());
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @throws CatcodeException in case of an error in token creation
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens( //
            convertCount(context, source, typesetter));
    }

}
