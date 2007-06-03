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

package org.extex.unit.tex.register.box;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.Namespace;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\setbox</code>.
 * 
 * <doc name="setbox">
 * <h3>The Primitive <tt>\setbox</tt></h3>
 * <p>
 * The primitive <tt>\setbox</tt> takes a key for a box register and assigns
 * the following box to this register.
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>
 * The primitive is an assignment. This means that it respects
 * <tt>\globaldefs</tt>. The treatment of <tt>\afterassignment</tt> is
 * special The token stored for after assignment are inserted just after the box
 * as been opened.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;setbox&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\setbox</tt> {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context,TokenSource,Typesetter,String)
 *        &lang;box register name&rang;} &lang;box&rang;
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \setbox0\hbox{abc}  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Setbox extends AbstractAssignment {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Return the key (the number) for the box register.
     * 
     * <doc type="syntax" name="box register name">
     * <h3>A Box Register Name</h3>
     * <p>
     * A box register name determines under which key a box register can be
     * addressed. In <logo>TeX</logo> this used to be a positive number only.
     * This has been extended to allow also a token list in braces.
     * </p>
     * <p>
     * The alternative is controlled by the count register
     * <tt>\register.max</tt>. The following interpretation of the value of
     * this count is used:
     * <ul>
     * <li>If the value of this count register is negative then a arbitrary
     * non-negative number is allowed as register name as well as any list of
     * tokens enclosed in braces.</li>
     * <li>If the value of this count register is not-negative then a only a
     * non-negative number is allowed as register name which does not exceed the
     * value of the count register.</li>
     * </ul>
     * </p>
     * <p>
     * The value of the count register <tt>\register.max</tt> is set
     * differently for various configurations of <logo>ExTeX</logo>:
     * <ul>
     * <li><logo>TeX</logo> uses the value 255.</li>
     * <li><logo>eTeX</logo> uses the value 32767.</li>
     * <li><logo>Omega</logo> uses the value 65536.</li>
     * <li><logo>ExTeX</logo> uses the value -1.</li>
     * </ul>
     * </p>
     * <p>
     * Note that the register name <tt>\register.max</tt> contains a period.
     * Thus it can normally not be entered easily since the catcode of the
     * period is OTHER but needs to be LETTER. Thus you have to use a
     * temporarily reassigned category code (see
     * {@link org.extex.unit.tex.register.CatcodePrimitive <tt>\catcode}</tt>)
     * or use {@link org.extex.unit.tex.macro.Csname <tt>\csname</tt>}.
     * </p>
     * 
     * <h4>Syntax</h4>
     * 
     * <pre class="syntax">
     *   &lang;box register name&rang;
     *       &rarr; {@linkplain
     *        org.extex.interpreter.TokenSource#scanTokens(Context,boolean,boolean,String)
     *        &lang;tokens&rang;}
     *        | {@linkplain org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
     *        &lang;number&rang;}  </pre>
     * 
     * <h4>Examples</h4>
     * 
     * <pre class="TeXSample">
     *  123
     *  {abc}
     * </pre>
     * 
     * </doc>
     * 
     * 
     * @param context the interpreter context to use
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param name the name of the primitive for error messages
     * 
     * @return the key for the box register
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static String getKey(Context context, TokenSource source,
            Typesetter typesetter, String name)
            throws HelpingException,
                TypesetterException {

        String key = source.scanRegisterName(context, source, typesetter, name);

        if (Namespace.SUPPORT_NAMESPACE_BOX) {
            String namespace = context.getNamespace();
            return ("".equals(namespace) ? key : namespace + "#" + key);
        }
        return key;
    }

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Setbox(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String key = Setbox.getKey(context, source, typesetter, getName());
        source.getOptionalEquals(context);
        Flags f = prefix.copy();
        prefix.clear();
        Box box = source.getBox(prefix, context, typesetter, //
            context.getAfterassignment());
        context.setBox(key, box, f.clearGlobal());
        context.setAfterassignment(null);
        prefix.set(f);
    }

}
