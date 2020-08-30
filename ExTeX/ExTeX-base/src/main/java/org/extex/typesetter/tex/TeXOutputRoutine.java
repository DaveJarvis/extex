/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tex;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.extex.backend.BackendDriver;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.type.page.Page;

/**
 * This class provides a link between the typesetter and the interpreter for the
 * output routine. In TeX the output routine is implemented in TeX's macro language. In ??TeX the output routine is a Java class implementing a defined
 * interface. This class implements the interface required and forwards the
 * request for processing to the appropriate interpreter.
 * <p>
 * The flow of control is illustrated in the following sequence diagram
 * </p>
 * <img src="doc-files/SD_par.png" width="100%" />
 * 
 * <doc name="output" type="register"> <h3>The Tokens Register <tt>\output</tt></h3>
 * <p>
 * The tokens register <tt>\output</tt> contains the program executed whenever a
 * page is completed. If it is not defined then the built-in output routine will
 * be used.
 * </p>
 * <p>
 * The box register 255 is used to pass in the current vertical list. This list
 * is assumed to make it to the page. For this purpose the primitive
 * {@link org.extex.unit.tex.typesetter.output.Shipout \shipout} can be used.
 * </p>
 * <p>
 * The output routine is assumed to clear the box register 255. If some material
 * is left in this box register then this is considered an error.
 * </p>
 * <p>
 * The output routine is assumed to invoke
 * {@link org.extex.unit.tex.typesetter.output.Shipout \shipout}. This does not
 * have to happen at each invocation of the output routine. The count register
 * <tt>\maxdeadcycles</tt> determines how many invocations are allowed which do
 * not call {@link org.extex.unit.tex.typesetter.output.Shipout \shipout}. The
 * count register <tt>\deadcycles</tt> contains the number of dead cycles
 * encountered already.
 * </p>
 * 
 * <h4>Syntax</h4> The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;output&rang;
 *      &rarr; <tt>\output</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 *        &lang;tokens&rang;}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \output={}  </pre>
 * 
 * </doc>
 * 
 * 
 * <doc name="deadcycles" type="register"> <h3>The Count Parameter
 * <tt>\deadcycles</tt></h3>
 * <p>
 * The count register <tt>\deadcycles</tt> contains the number of attempts to
 * call the output routine without any material being shipped out. Usually the
 * output routine is expected to ship something out. Under some circumstances
 * the output is delayed. Thus a large number of dead cycles can indicate a
 * problem in the output routine. The register <tt>\deadcycles</tt> is compared
 * with the register <tt>\maxdeadcycles</tt> to decide when an intervention seem
 * appropriate.
 * </p>
 * 
 * <h4>Syntax</h4> The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;deadcycles&rang;
 *      &rarr; <tt>\deadcycles</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \deadcycles=0  </pre>
 * 
 * </doc>
 * 
 * 
 * <doc name="maxdeadcycles" type="register"> <h3>The Count Parameter
 * <tt>\maxdeadcycles</tt></h3>
 * <p>
 * The count register <tt>\maxdeadcycles</tt> contains the maximum number of
 * attempts to call the output routine without any material being shipped out.
 * The output routine is expected to ship something out. Under some
 * circumstances the output is delayed. Thus a large number of dead cycles can
 * indicate a problem in the output routine. The register <tt>\deadcycles</tt>
 * is compared with the register <tt>\maxdeadcycles</tt> to decide when an
 * intervention seem appropriate.
 * </p>
 * 
 * <h4>Syntax</h4> The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;maxdeadcycles&rang;
 *      &rarr; <tt>\maxdeadcycles</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 *        &lang;number&rang;}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \maxdeadcycles=1  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4483 $
 */
public class TeXOutputRoutine implements OutputRoutine, Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2011L;

    /**
     * The field <tt>OUTPUT_BOX</tt> contains the index of the box register to
     * communicate with the output routine.
     */
    private static final String OUTPUT_BOX = "255";

    /**
     * The field <tt>interpreter</tt> contains the interpreter.
     */
    private transient Interpreter interpreter;

    /**
     * The field <tt>rightBrace</tt> contains the group closing token.
     */
    private transient Token rightBrace;

    /**
     * The field <tt>output</tt> contains the token <i>output</i> in the default
     * name space.
     */
    private transient Token outputToken;

    /**
     * Creates a new object.
     * 
     * @param interpreter the interpreter
     * 
     * @throws CatcodeException in case of an error
     */
    public TeXOutputRoutine(Interpreter interpreter) throws CatcodeException {

        this.interpreter = interpreter;
        TokenFactory tokenFactory = interpreter.getContext().getTokenFactory();
        rightBrace =
                tokenFactory.createToken(Catcode.RIGHTBRACE, '}',
                    Namespace.DEFAULT_NAMESPACE);
        outputToken =
                tokenFactory.createToken(Catcode.ESCAPE, UnicodeChar.get('\\'),
                    "output", Namespace.DEFAULT_NAMESPACE);
    }

    /**
     * The output function is invoked to process a vertical list and put the
     * material on the page. In fact it should find its way to the document
     * writer &ndash; either immediately or later on.
     * 
     * @param page the nodes to put onto the page
     * @param backend the back-end driver to target the nodes to
     * 
     * @throws GeneralException in case of an error
     * @throws org.extex.framework.configuration.exception.ConfigurationException
     *         in case of an configuration error
     * 
     * @see org.extex.typesetter.output.OutputRoutine#output(org.extex.typesetter.type.page.Page,
     *      org.extex.backend.BackendDriver)
     */
    @Override
    public void output(Page page, BackendDriver backend)
            throws GeneralException {

        Context context = interpreter.getContext();
        Count deadcyles = context.getCount("deadcyles");

        Tokens output = context.getToks("output");
        if (output.length() == 0) {
            backend.shipout(page);
            deadcyles.set(0);
            return;
        }

        deadcyles.add(1);
        if (deadcyles.gt(context.getCount("maxdeadcycles"))) {
            throw new InterpreterException(LocalizerFactory.getLocalizer(
                TeXOutputRoutine.class).format("TTP.TooMuchDead",
                deadcyles.toString()));
        }

        Box box = context.getBox(OUTPUT_BOX);
        if (box != null && !box.isVoid()) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(TeXOutputRoutine.class),
                "TTP.NonEmptyOutBox", context.esc("box"), OUTPUT_BOX);
        }

        interpreter.push(rightBrace);
        interpreter.push(output);
        context.openGroup(GroupType.OUTPUT_GROUP, interpreter.getLocator(),
            this.outputToken);
        context.setBox(OUTPUT_BOX, new Box(page.getNodes()), false);
        interpreter.executeGroup();

        box = context.getBox(OUTPUT_BOX);
        if (box != null && !box.isVoid()) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(TeXOutputRoutine.class),
                "TTP.NonEmptyOutBoxAfter", context.esc("box"), OUTPUT_BOX);
        }
    }

    /**
     * Magic method for deserialization.
     * 
     * @return the reconnection result
     * 
     * @throws ObjectStreamException in case of an error
     */
    protected Object readResolve() throws ObjectStreamException {

        rightBrace = null;
        outputToken = null;
        return this;
    }

}
