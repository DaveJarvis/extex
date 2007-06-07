/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.color;

import org.extex.color.Color;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\colordef</code>.
 * 
 * <doc name="colordef">
 * <h3>The Primitive <tt>\colordef</tt></h3>
 * <p>
 * The primitive <tt>\colordef</tt> defines a color variable and assigns it to
 * a control sequence. The color is initialized with a given color &ndash;
 * either a color constant or a color variable.
 * </p>
 * <p>
 * The control sequence can later be used wherever a color is expected.
 * </p>
 * <p>
 * The primitive <tt>\colordef</tt> constitutes an assignment. Thus the count
 * register <tt>\globaldefs</tt> and the token register
 * <tt>\afterassignment</tt> interact with it as for each assignment.
 * </p>
 * <p>
 * The primitive can be prefixed with the <tt>\global</tt> flag. In this case
 * the definition is performed globally. Otherwise the control sequence holds
 * the color value in the current group only. It is reset to the previous value
 * when the group is ended.
 * </p>
 * <p>
 * The color variable can be manipulated by assigning new colors to it. The
 * assignment is accomplished by specifying the new value after an optional
 * equals sign. Note that the assignment can not be prefixed by a
 * <tt>\global</tt> modifier since the scope has already been specified in the
 * declaration with <tt>\colordef</tt>.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;colordef&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\colordef</tt> {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;} &lang;color&rang;
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt>   </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \colordef\col alpha .1234 rgb {.2 .3 .4}  </pre>
 * 
 * <p>
 * </p>
 * 
 * <pre class="TeXSample">
 *    \global\colordef\col\color  </pre>
 * 
 * <p>
 * </p>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Colordef extends AbstractAssignment {

    /**
     * This class carries a color value for storing it as code in the context.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private static class ColorCode extends AbstractColor {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        protected static final long serialVersionUID = 20060528L;

        /**
         * The field <tt>color</tt> contains the color.
         */
        private Color color;

        /**
         * Creates a new object.
         * 
         * @param color the color
         * @param name the name of the primitive
         */
        public ColorCode(Color color, String name) {

            super(name);
            this.color = color;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.type.AbstractAssignment#assign(org.extex.interpreter.Flags,
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter)
         */
        @Override
        public void assign(Flags prefix, Context context, TokenSource source,
                Typesetter typesetter)
                throws HelpingException,
                    TypesetterException {

            color = ColorParser.parseColor(context, source, typesetter, //
                getName());
        }

        /**
         * This method converts something into a color. It might be necessary to
         * read further tokens to determine which value to use. For instance an
         * additional register number might be required. In this case the
         * additional arguments Context and TokenSource can be used.
         * 
         * @param context the interpreter context
         * @param source the source for new tokens
         * @param typesetter the typesetter to use for conversion
         * 
         * @return the converted value
         * 
         * @see org.extex.interpreter.type.color.ColorConvertible#convertColor(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter)
         */
        public Color convertColor(Context context, TokenSource source,
                Typesetter typesetter) throws HelpingException {

            return color;
        }

    }

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 20060528L;

    /**
     * Creates a new object.
     * 
     * @param name the name of the primitive for tracing
     */
    public Colordef(String name) {

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
     * @throws
     *        org.extex.framework.configuration.exception.ConfigurationException
     *        in case of an configuration error
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        Color color =
                ColorParser.parseColor(context, source, typesetter, getName());
        context.setCode(cs, new ColorCode(color, cs.getName()), prefix
            .clearGlobal());
    }

}
