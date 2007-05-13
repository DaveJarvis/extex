/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

import org.extex.base.parser.CountConvertible;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupTypeVisitor;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\currentgrouptype</code>.
 * 
 * <doc name="currentgrouptype">
 * <h3>The Count Primitive <tt>\currentgrouptype</tt></h3>
 * <p>
 * The count primitive <tt>\currentgrouptype</tt> is a read-only count
 * register. It provides access to the current group type. This group type is
 * characterized according to the following list:
 * </p>
 * <table format="rl">
 * <tr>
 * <td> 0</td>
 * <td>bottom level (no group)</td>
 * </tr>
 * <tr>
 * <td> 1</td>
 * <td>simple group</td>
 * </tr>
 * <tr>
 * <td> 2</td>
 * <td>hbox group</td>
 * </tr>
 * <tr>
 * <td> 3</td>
 * <td>adjusted hbox group</td>
 * </tr>
 * <tr>
 * <td> 4</td>
 * <td>vbox group</td>
 * </tr>
 * <tr>
 * <td> 5</td>
 * <td>vtop group</td>
 * </tr>
 * <tr>
 * <td> 6</td>
 * <td>align group</td>
 * </tr>
 * <tr>
 * <td> 7</td>
 * <td>no align group</td>
 * </tr>
 * <tr>
 * <td> 8</td>
 * <td>output group</td>
 * </tr>
 * <tr>
 * <td> 9</td>
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
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;currentgrouptype&rang;
 *      &rarr; <tt>\currentgrouptype</tt>  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \count0=\currentgrouptype  </pre>
 *  <pre class="TeXSample">
 *    \showthe\currentgrouptype  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4435 $
 */
public class Currentgrouptype extends AbstractCode
        implements
            CountConvertible,
            Theable {

    /**
     * The constant <tt>GTV</tt> contains the group visitor to map the group
     * type to the integer representation of <logo>eTeX</logo>.
     */
    private static final GroupTypeVisitor<Long, Object> GTV =
            new GroupTypeVisitor<Long, Object>() {

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitAdjustedHboxGroup(
                 *      java.lang.Object)
                 */
                public Long visitAdjustedHboxGroup(Object arg) {

                    return ADJUSTED_HBOX;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitAlignGroup(
                 *      java.lang.Object)
                 */
                public Long visitAlignGroup(Object arg) {

                    return ALIGNMENT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitBottomLevelGroup(
                 *      java.lang.Object)
                 */
                public Long visitBottomLevelGroup(Object arg) {

                    return BOTTOM_LEVEL;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitDiscGroup(
                 *      java.lang.Object)
                 */
                public Long visitDiscGroup(Object arg) {

                    return DISCRETIONARY;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitHboxGroup(
                 *      java.lang.Object)
                 */
                public Long visitHboxGroup(Object arg) {

                    return HBOX;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitInsertGroup(
                 *      java.lang.Object)
                 */
                public Long visitInsertGroup(Object arg) {

                    return INSERT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathChoiceGroup(
                 *      java.lang.Object)
                 */
                public Long visitMathChoiceGroup(Object arg) {

                    return MATH_CHOICE;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathGroup(
                 *      java.lang.Object)
                 */
                public Long visitMathGroup(Object arg) {

                    return MATH;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathLeftGroup(
                 *      java.lang.Object)
                 */
                public Long visitMathLeftGroup(Object arg) {

                    return MATH_LEFT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathShiftGroup(
                 *      java.lang.Object)
                 */
                public Long visitMathShiftGroup(Object arg) {

                    return MATH_SHIFT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitNoAlignGroup(
                 *      java.lang.Object)
                 */
                public Long visitNoAlignGroup(Object arg) {

                    return NO_ALIGN;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitOutputGroup(
                 *      java.lang.Object)
                 */
                public Long visitOutputGroup(Object arg) {

                    return OUTPUT;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitSemiSimpleGroup(
                 *      java.lang.Object)
                 */
                public Long visitSemiSimpleGroup(Object arg) {

                    return SEMI_SIMPLE;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitSimpleGroup(
                 *      java.lang.Object)
                 */
                public Long visitSimpleGroup(Object arg) {

                    return SIMPLE;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVboxGroup(
                 *      java.lang.Object)
                 */
                public Long visitVboxGroup(Object arg) {

                    return VBOX;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVcenterGroup(
                 *      java.lang.Object)
                 */
                public Long visitVcenterGroup(Object arg) {

                    return VCENTER;
                }

                /**
                 * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVtopGroup(
                 *      java.lang.Object)
                 */
                public Long visitVtopGroup(Object arg) {

                    return VTOP;
                }
            };

    /**
     * The field <tt>BOTTOM_LEVEL</tt> contains the return value for a bottom
     * level group.
     */
    private static final Long BOTTOM_LEVEL = new Long(0);

    /**
     * The field <tt>SIMPLE</tt> contains the return value for a simple group.
     */
    private static final Long SIMPLE = new Long(1);

    /**
     * The field <tt>DISCRETIONARY</tt> contains the return value for a
     * discretionary group.
     */
    private static final Long DISCRETIONARY = new Long(10);

    /**
     * The field <tt>INSERT</tt> contains the return value for an insert
     * group.
     */
    private static final Long INSERT = new Long(11);

    /**
     * The field <tt>VCENTER</tt> contains the return value for a vcenter
     * group.
     */
    private static final Long VCENTER = new Long(12);

    /**
     * The field <tt>MATH_CHOICE</tt> contains the return value for a math
     * choice group.
     */
    private static final Long MATH_CHOICE = new Long(13);

    /**
     * The field <tt>SEMI_SIMPLE</tt> contains the return value for a
     * semi-simple group.
     */
    private static final Long SEMI_SIMPLE = new Long(14);

    /**
     * The field <tt>MATH_SHIFT</tt> contains the return value for a math
     * shift group.
     */
    private static final Long MATH_SHIFT = new Long(15);

    /**
     * The field <tt>MATH_LEFT</tt> contains the return value for a math left
     * group.
     */
    private static final Long MATH_LEFT = new Long(16);

    /**
     * The field <tt>HBOX</tt> contains the return value for a hbox group.
     */
    private static final Long HBOX = new Long(2);

    /**
     * The field <tt>LONG_3</tt> contains the return value for an adjusted
     * hbox group.
     */
    private static final Long ADJUSTED_HBOX = new Long(3);

    /**
     * The field <tt>LONG_4</tt> contains the return value for a vbox group.
     */
    private static final Long VBOX = new Long(4);

    /**
     * The field <tt>LONG_5</tt> contains the return value for a vtop group.
     */
    private static final Long VTOP = new Long(5);

    /**
     * The field <tt>LONG_6</tt> contains the return value for an alignment
     * group.
     */
    private static final Long ALIGNMENT = new Long(6);

    /**
     * The field <tt>LONG_7</tt> contains the return value for a no align
     * group.
     */
    private static final Long NO_ALIGN = new Long(7);

    /**
     * The field <tt>LONG_8</tt> contains the return value for an output
     * group.
     */
    private static final Long OUTPUT = new Long(8);

    /**
     * The field <tt>LONG_9</tt> contains the return value for a math group.
     */
    private static final Long MATH = new Long(9);

    /**
     * The field <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060512L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Currentgrouptype(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return ((Long) context.getGroupType().visit(GTV, null)).longValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Theable#the(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException, TypesetterException {

        return context.getTokenFactory().toTokens( //
            convertCount(context, source, typesetter));
    }

}
