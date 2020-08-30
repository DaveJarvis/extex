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

package org.extex.unit.dynamic.java;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.dynamic.Definer;

import java.lang.reflect.InvocationTargetException;

/**
 * This primitive provides a binding of a macro or active character to Java
 * code. This code implements the primitive {@code \javadef}.
 * 
 * <p>The Primitive {@code \javadef}</p>
 * <p>
 * The primitive {@code \javadef} attaches a definition to a macro or active
 * character. This is done in a similar way as {@code \def} works. The
 * difference is that the definition has to be provided in form of a Java class.
 * </p>
 * 
 * <p>Syntax</p>
 The general form of this primitive is
 * 
 * <pre class="syntax">
 *   &lang;javadef&rang;
 *       &rarr; {@code \javadef} {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;} <i>&lang;tokens&rang;</i> </pre>
 * 
 * <p>
 * The <i>&lang;control sequence&rang;</i> is any macro or active character. If
 * this token is missing or of the wrong type then an error is raised.
 * </p>
 * <p>
 * The <i>&lang;tokens&rang;</i> is any specification of a list of tokens like a
 * constant list enclosed in braces or a tokens register. The value of these
 * tokens are taken and interpreted as the name of a Java class. This class is
 * loaded if needed and instantiated. The instance is bound as code to the
 * <i>&lang;control sequence&rang;</i>.
 * </p>
 * <p>
 * The following example illustrates the use of this primitive:
 * </p>
 *
 * <pre class="TeXSample">
 *   \javadef\abc{org.extex.interpreter.primitive.Relax} </pre>
 * 
 * <p>
 * The primitive {@code \javadef} is local to the enclosing group as is
 * {@code \def}. And similar to {@code \def} the modifier {@code \global} can
 * be used to make the definition in all groups instead of the current group
 * only. This is shown in the following example:
 * </p>
 *
 * <pre class="TeXSample">
 *   \global\javadef\abc{org.extex.interpreter.primitive.Relax}
 * </pre>
 * 
 * <p>
 * The primitive {@code \javadef} also respects the count register
 * {@code \globaldefs} to enable general global assignment.
 * </p>
 * <p>
 * Since the primitive is classified as assignment the value of
 * {@code \afterassignment} is applied.
 * </p>
 * 
 * <p>Java Implementation</p>

 * <p>
 * Now we come to the Java side of the definition. The class given as
 * <i>&lang;tokens&rang;</i> must implement the interface
 * {@link org.extex.interpreter.type.Code Code}. The easiest way to achieve this
 * is by declaring a class derived from
 * {@link org.extex.interpreter.type.AbstractCode AbstractCode}.
 * </p>
 *
 * <pre class="JavaSample">
 *   <b>package</b> my.package;
 *
 *   <b>import</b> org.extex.interpreter.type.AbstractCode;
 *   <b>import</b> org.extex.interpreter.contect.Context;
 *   <b>import</b> org.extex.interpreter.Flags;
 *   <b>import</b> org.extex.interpreter.TokenSource;
 *   <b>import</b> org.extex.typesetter.Typesetter;
 *   <b>import</b> de.dante.util.GeneralException;
 *
 *   <b>class</b> MyPrimitive <b>extends</b> AbstractCode {
 *
 *     <b>public</b> MyPrimitive(<b>final</b> String name) {
 *       super(name);
 *       <i>// initialization code &ndash; if required</i>
 *     }
 *
 *     <b>public boolean</b> execute(<b>final</b> Flags prefix,
 *                            <b>final</b> Context context,
 *                            <b>final</b> TokenSource source,
 *                            <b>final</b> Typesetter typesetter
 *                           ) {
 *       <i>// implement the execution behavior here</i>
 *       <b>return</b> <b>true</b>;
 *     }
 *   } </pre>
 * <p>
 * There is more to say about primitives like how to write expandable primitives
 * or ifs. Those details can be found in section <i>Primitives</i>.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class JavaDef extends AbstractAssignment implements Definer {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object. This method is needed for the nativedef wrapper.
     */
    public JavaDef() {

        super(null);
    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public JavaDef(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        define(prefix, context, source, typesetter);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void define(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        String classname;
        try {
            classname = source.getTokens(context, source, typesetter).toText();
        } catch (EofException e) {
            throw new EofInToksException(toText(context));
        }
        if ("".equals(classname) || classname.matches(".*[^a-zA-Z0-9_.$].*")) {
            throw new HelpingException(getLocalizer(), "InvalidClassName",
                classname);
        }

        try {
            Code code = (Code) (Class.forName(classname).getConstructor(
                new Class[]{CodeToken.class}).newInstance(cs));
            context.setCode(cs, code, prefix.clearGlobal());

        } catch (IllegalArgumentException e) {
            throw new NoHelpException(e);
        } catch (SecurityException e) {
            throw new NoHelpException(e);
        } catch (InstantiationException e) {
            throw new NoHelpException(e);
        } catch (IllegalAccessException e) {
            throw new NoHelpException(e);
        } catch (InvocationTargetException e) {
            throw new NoHelpException(e);
        } catch (NoSuchMethodException e) {
            throw new HelpingException(getLocalizer(), "MethodNotFound",
                e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new HelpingException(getLocalizer(), "ClassNotFound",
                classname);
        } catch (ClassCastException e) {
            throw new HelpingException(getLocalizer(), "ClassCast", classname,
                Code.class.getName());
        }
    }

}
