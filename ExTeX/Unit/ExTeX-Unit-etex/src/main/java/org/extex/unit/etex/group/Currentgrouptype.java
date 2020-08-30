/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.group;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupTypeVisitor;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \currentgrouptype}.
 * 
 * <p>The Count Primitive {@code \currentgrouptype}</p>
 * <p>
 * The count primitive {@code \currentgrouptype} is a read-only count register.
 * It provides access to the current group type. This group type is
 * characterized according to the following list:
 * </p>
* <table>
 * <caption>TBD</caption>
 * <tr>
* <td>0</td>
 * <td>bottom level (no group)</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>simple group</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>hbox group</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>adjusted hbox group</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>vbox group</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>vtop group</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>align group</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>no align group</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>output group</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>math group</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>disc group</td>
 * </tr>
 * <tr>
 * <td>11</td>
 * <td>insert group</td>
 * </tr>
 * <tr>
 * <td>12</td>
 * <td>vcenter group</td>
 * </tr>
 * <tr>
 * <td>13</td>
 * <td>math choice group</td>
 * </tr>
 * <tr>
 * <td>14</td>
 * <td>semi simple group</td>
 * </tr>
 * <tr>
 * <td>15</td>
 * <td>math shift group</td>
 * </tr>
 * <tr>
 * <td>16</td>
 * <td>math left group</td>
 * </tr>
 * </table>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;currentgrouptype&rang;
 *      &rarr; {@code \currentgrouptype}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \count0=\currentgrouptype  </pre>
 * 
 * <pre class="TeXSample">
 *    \showthe\currentgrouptype  </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Currentgrouptype extends AbstractCode
        implements
            CountConvertible,
            Theable {

    /**
     * The constant {@code GTV} contains the group visitor to map the group
     * type to the integer representation of ε-TeX.
     */
    private static final GroupTypeVisitor<Long, Object> GTV =
            new GroupTypeVisitor<Long, Object>() {

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitAdjustedHboxGroup(java.lang.Object)
                 */
                @Override
                public Long visitAdjustedHboxGroup(Object arg) {

                    return ADJUSTED_HBOX;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitAlignGroup(java.lang.Object)
                 */
                @Override
                public Long visitAlignGroup(Object arg) {

                    return ALIGNMENT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitBottomLevelGroup(java.lang.Object)
                 */
                @Override
                public Long visitBottomLevelGroup(Object arg) {

                    return BOTTOM_LEVEL;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitDiscGroup(java.lang.Object)
                 */
                @Override
                public Long visitDiscGroup(Object arg) {

                    return DISCRETIONARY;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitHboxGroup(java.lang.Object)
                 */
                @Override
                public Long visitHboxGroup(Object arg) {

                    return HBOX;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitInsertGroup(java.lang.Object)
                 */
                @Override
                public Long visitInsertGroup(Object arg) {

                    return INSERT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathChoiceGroup(java.lang.Object)
                 */
                @Override
                public Long visitMathChoiceGroup(Object arg) {

                    return MATH_CHOICE;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathGroup(java.lang.Object)
                 */
                @Override
                public Long visitMathGroup(Object arg) {

                    return MATH;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathLeftGroup(java.lang.Object)
                 */
                @Override
                public Long visitMathLeftGroup(Object arg) {

                    return MATH_LEFT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathShiftGroup(java.lang.Object)
                 */
                @Override
                public Long visitMathShiftGroup(Object arg) {

                    return MATH_SHIFT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitNoAlignGroup(java.lang.Object)
                 */
                @Override
                public Long visitNoAlignGroup(Object arg) {

                    return NO_ALIGN;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitOutputGroup(java.lang.Object)
                 */
                @Override
                public Long visitOutputGroup(Object arg) {

                    return OUTPUT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitSemiSimpleGroup(java.lang.Object)
                 */
                @Override
                public Long visitSemiSimpleGroup(Object arg) {

                    return SEMI_SIMPLE;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitSimpleGroup(java.lang.Object)
                 */
                @Override
                public Long visitSimpleGroup(Object arg) {

                    return SIMPLE;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVboxGroup(java.lang.Object)
                 */
                @Override
                public Long visitVboxGroup(Object arg) {

                    return VBOX;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVcenterGroup(java.lang.Object)
                 */
                @Override
                public Long visitVcenterGroup(Object arg) {

                    return VCENTER;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVtopGroup(java.lang.Object)
                 */
                @Override
                public Long visitVtopGroup(Object arg) {

                    return VTOP;
                }
            };

    /**
     * The field {@code BOTTOM_LEVEL} contains the return value for a bottom
     * level group.
     */
    private static final Long BOTTOM_LEVEL = new Long(0);

    /**
     * The field {@code SIMPLE} contains the return value for a simple group.
     */
    private static final Long SIMPLE = new Long(1);

    /**
     * The field {@code DISCRETIONARY} contains the return value for a
     * discretionary group.
     */
    private static final Long DISCRETIONARY = new Long(10);

    /**
     * The field {@code INSERT} contains the return value for an insert group.
     */
    private static final Long INSERT = new Long(11);

    /**
     * The field {@code VCENTER} contains the return value for a vcenter group.
     */
    private static final Long VCENTER = new Long(12);

    /**
     * The field {@code MATH_CHOICE} contains the return value for a math
     * choice group.
     */
    private static final Long MATH_CHOICE = new Long(13);

    /**
     * The field {@code SEMI_SIMPLE} contains the return value for a
     * semi-simple group.
     */
    private static final Long SEMI_SIMPLE = new Long(14);

    /**
     * The field {@code MATH_SHIFT} contains the return value for a math shift
     * group.
     */
    private static final Long MATH_SHIFT = new Long(15);

    /**
     * The field {@code MATH_LEFT} contains the return value for a math left
     * group.
     */
    private static final Long MATH_LEFT = new Long(16);

    /**
     * The field {@code HBOX} contains the return value for a hbox group.
     */
    private static final Long HBOX = new Long(2);

    /**
     * The field {@code LONG_3} contains the return value for an adjusted hbox
     * group.
     */
    private static final Long ADJUSTED_HBOX = new Long(3);

    /**
     * The field {@code LONG_4} contains the return value for a vbox group.
     */
    private static final Long VBOX = new Long(4);

    /**
     * The field {@code LONG_5} contains the return value for a vtop group.
     */
    private static final Long VTOP = new Long(5);

    /**
     * The field {@code LONG_6} contains the return value for an alignment
     * group.
     */
    private static final Long ALIGNMENT = new Long(6);

    /**
     * The field {@code LONG_7} contains the return value for a no align group.
     */
    private static final Long NO_ALIGN = new Long(7);

    /**
     * The field {@code LONG_8} contains the return value for an output group.
     */
    private static final Long OUTPUT = new Long(8);

    /**
     * The field {@code LONG_9} contains the return value for a math group.
     */
    private static final Long MATH = new Long(9);

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Currentgrouptype(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return ((Long) context.getGroupType().visit(GTV, null)).longValue();
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens(
            convertCount(context, source, typesetter));
    }

}
