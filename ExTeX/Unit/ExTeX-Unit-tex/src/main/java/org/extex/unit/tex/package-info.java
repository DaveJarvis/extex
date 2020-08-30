/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
/**
 * Contains classes for the primitives which are mapped to
 * Java code. Those primitives are implemented directly &ndash; opposed to the
 * implementation as macros.
 * <p>
 * The binding to the appropriate names is performed during
 * initialization of the interpreter. The information may come from a
 * configuration file or a format file.
 * </p>
 *
 *
 * <p>Writing New Primitives</p>
 * <p>
 * The core primitives of εχTeX are written in Java and bound to
 * control sequences or active characters. In this section we will
 * explain how to write new primitives in Java.
 * </p>
 *
 * <p>Executable Code</p>
 *
 * <p>
 * Executable primitives are those primitives which can be invoked in
 * a left-hand-side context of the expansion. This is the case
 * whenever the next top-level macro is treated. You can consider for
 * example the treatment of the macro {@code \def} as such a case:
 * </p>
 * <pre class="TeXSample">
 *     \def\abc{123}</pre>
 * <p>
 * In this example {@code \def} is an executable primitive.
 * </p>
 * <p>
 * Executable code has to implement the interface {@link
 * org.extex.interpreter.type.Code Code}. Doing this directly is not
 * hard. Nevertheless the abstract base class {@link
 * org.extex.interpreter.type.AbstractCode AbstractCode} is provided
 * which contains default implementations for all methods already.
 * Thus only the interesting methods have to be overwritten.
 * </p>
 * <p>
 * In the simplest case only a constructor with one String argument and
 * the method {@code execute()} has to be defined. Such an empty
 * frame can be seen in the following example:
 * </p>
 * <pre class="JavaSample">
 *    <b>package</b> my.package;
 *
 *    <b>import</b> org.extex.interpreter.context.Context;
 *    <b>import</b> org.extex.interpreter.Flags;
 *    <b>import</b> org.extex.interpreter.TokenSource;
 *    <b>import</b> org.extex.interpreter.exception.InterpreterException;
 *    <b>import</b> org.extex.interpreter.type.AbstractCode;
 *    <b>import</b> org.extex.typesetter.Typesetter;
 *
 *    <b>class</b> MyPrimitive <b>extends</b> AbstractCode {
 *
 *      <b>public</b> MyPrimitive(<b>final</b> String name) {
 *        super(name);
 *        <i>// initialization code -- if required</i>
 *      }
 *
 *      <b>public boolean</b> execute(<b>final</b> Flags prefix,
 *                             <b>final</b> Context context,
 *                             <b>final</b> TokenSource source,
 *                             <b>final</b> Typesetter typesetter
 *                            ) <b>throws</b> InterpreterException {
 *        <i>// implement the execution behaviour here</i>
 *       <b>return</b> <b>true</b>;
 *     }
 *   }</pre>
 * <p>
 * n the method {@code execute()} you have access to other components.
 * his can be utilized to implement the desired functionality. The
 * ollowing parameters are provided:
 * </p>
 * <dl>
 *  <dt>{@code <b>{@link org.extex.interpreter.Flags Flags}</b> prefix}</dt>
 *  <dd>This parameter gives access to prefix arguments like
 *   {@code \immediate} or {@code \global}. For this purpose the
 *   class Flags provides appropriate getters.
 *   <p>
 *   You can even modify the flags passed to the method. Usually you
 *   should invoke {@code prefix.clear()} somewhere in your
 *   implementation when the prefix is not needed any more. If this
 *   method is omitted then the prefix is passed on to the next
 *   execution. This can be desirable if you want to implement a prefix
 *   primitive yourself.
 *   </p>
 *  </dd>
 *  <dt>{@code <b>{@link org.extex.interpreter.context.Context Context}</b>
 *  context}</dt>
 *  <dd>The context provides reading and writing access to the data
 *   stored in the processor. This information is the memory. It is
 *   written to file when dumping a format. Refer to the documentation
 *   of the interface Context for details.
 *  </dd>
 *  <dt>{@code <b>{@link org.extex.interpreter.TokenSource TokenSource}</b>
 *  source}</dt>
 *  <dd>The source provides access to the token stream. It can be used
 *   to get the next tokens if required. For example when implementing
 *   a primitive like {@code \def} it is necessary to read the next
 *   tokens as arguments: the macro name, the parameter pattern, and
 *   the expansion text.
 *   <p>
 *   The token source can also be used to push tokens to the input
 *   stream to be read back in later. This feature is used when
 *   implementing expandable primitives.
 *   </p>
 *  </dd>
 *  <dt>{@code <b>{@link org.extex.typesetter.Typesetter Typesetter}</b>
 *  typesetter}</dt>
 *  <dd>
 *   The typesetter is the component which collects nodes and finally
 *   sends them to the document writer. With access to this component
 *   it is possible to produce some output to the paper.
 *  </dd>
 * </dl>
 *
 * <p>
 *  The return value indicates how to deal with prefix flags. The usual
 *  behaviour is to return <b>true</b>. This indicates that the flags should be
 *  cleared afterwards. For those primitives which modify the prefix flags the
 *  return value <b>false</b> must be used.
 * </p>
 *
 * <p>Registering New Macros</p>
 *
 * <p>
 *  The primary way to register new macros is in the configuration file
 *  used by εχTeX. For example the default file is located
 *  in the package {@code config} and named {@code extex.xml}. There you
 *  can find lines like the following one:
 * </p>
 *  <pre class="Sample">
 *    <b>&lt;define</b> name="def"
 *             class="org.extex.interpreter.primitives.macro.Def"<b>/&gt;</b></pre>
 * <p>
 *  To add another primitive to εχTeX you should make a copy of this
 *  configuration file under a different name and add a line like the
 *  one shown above:
 * </p>
 *  <pre class="Sample">
 *    <b>&lt;define</b> name="myPrim"
 *             class="my.package.MyPrimitive"<b>/&gt;</b></pre>
 * <p>
 *  Now you can invoke εχTeX on the command line with the parameter
 *  {@code -configuration} or add a line {@code extex.config} to your
 *  {@code .extex} file pointing εχTeX to your new configuration file:
 * </p>
 *  <pre class="CLISample">
 *    extex -configuration config/myExTeX.xml</pre>
 * <p>
 *  This is enough. In the instance of εχTeX with these settings the
 *  new macro {@code \myPrim} is defined and points to your code for
 *  execution.
 * </p>
 *
 * <p>Registering New Macros Dynamically</p>
 *
 * <p>
 *  One extension provided with εχTeX contains a dynamic definition of
 *  new macros. Those macros are defined at runtime. The assignment of
 *  the Java code to the macro name can be controlled with the help of
 *  a primitive. Check out whether the macro {@code \javadef} is defined in
 *  one of the configuration files provided and consult the documentation.
 * </p>
 *
 * <p>Exceptions</p>
 *
 * <p>
 *  The implementing Java code for new primitives can signal abnormal
 *  situations with the help of exceptions. The exceptions used should
 *  be derived from {@link org.extex.interpreter.exception.InterpreterException
 *  InterpreterException}. {@code RuntimeException}s and {@code Error}s
 *  or derived classes should not be used.
 * </p>
 * <p>
 *  εχTeX provides means for externalizing strings. Thus it should be
 *  made easy to translate the messages to other languages. For this
 *  purpose the class {@link org.extex.framework.i18n.Localizer
 *  Localizer} is provided. See the documentation of this
 *  class for details.
 * </p>
 *
 * <p>Assignments</p>
 *
 * <p>
 *  Assignments are a special kind of executable code. TeX defines
 *  that the parameter {@code \globaldef} is evaluated and the macro
 *  {@code \afterassignment} has some effect. To ease the development
 *  of assignments the abstract base class {@link
 *  org.extex.interpreter.type.AbstractAssignment AbstractAssignment} is
 *  provided. This class defines the method {@code execute()}
 *  appropriately. The only task left is to overwrite the method
 *  {@code assign()} to perform the assignment.
 * </p>
 *  <pre class="JavaSample">
 *   <b>package</b> my.package;
 *
 *   <b>import</b> org.extex.interpreter.contect.Context;
 *   <b>import</b> org.extex.interpreter.Flags;
 *   <b>import</b> org.extex.interpreter.TokenSource;
 *   <b>import</b> org.extex.interpreter.exception.InterpreterException;
 *   <b>import</b> org.extex.interpreter.type.AbstractAssignment;
 *   <b>import</b> org.extex.typesetter.Typesetter;
 *
 *   <b>class</b> MyAssign <b>extends</b> AbstractAssignment {
 *
 *     <b>public</b> MyAssign(<b>final</b> String name) {
 *       super(name);
 *       <i>// initialization code -- if required</i>
 *     }
 *
 *     <b>public void</b> assign(<b>final</b> Flags prefix,
 *                        <b>final</b> Context context,
 *                        <b>final</b> TokenSource source,
 *                        <b>final</b> Typesetter typesetter
 *                       ) <b>throws</b> InterpreterException {
 *       <i>// implement the assignment here</i>
 *     }
 *   }</pre>
 * <p>
 *  The arguments of the method {@code assign()} are the same as the
 *  arguments of {@code execute()} described above. In contrast to the
 *  remarks made there it is not necessary to return something. The clearing
 *  of the flags is done in the abstract class automatically.
 * </p>
 *
 * <p>Expandable Code</p>
 *
 * <p>
 *  Some macros are expandable. This means that they can be used on the
 *  right-hand-side of an invocation as well. This feature is expressed
 *  by the interface {@link org.extex.interpreter.type.ExpandableCode
 *  ExpandableCode}. Since Java does not allow multiple inheritance
 *  no abstract base class is provided.
 * </p>
 * <p>
 *  To implement an expandable primitive it is sufficient to declare
 *  the interface for the class and implement the method
 *  {@code expand()}. This is sketched in the following example:
 * </p>
 *  <pre class="JavaSample">
 *   <b>package</b> my.package;
 *
 *   <b>import</b> org.extex.interpreter.contect.Context;
 *   <b>import</b> org.extex.interpreter.Flags;
 *   <b>import</b> org.extex.interpreter.TokenSource;
 *   <b>import</b> org.extex.interpreter.exception.InterpreterException;
 *   <b>import</b> org.extex.interpreter.type.AbstractCode;
 *   <b>import</b> org.extex.interpreter.type.ExpandableCode;
 *   <b>import</b> org.extex.typesetter.Typesetter;
 *
 *   <b>class</b> MyExpandable <b>extends</b> AbstractCode <b>implements</b> ExpandableCode {
 *
 *     <b>public</b> MyExpandable(<b>final</b> String name) {
 *       super(name);
 *       <i>// initialization code -- if required</i>
 *     }
 *
 *     <b>public boolean</b> execute(<b>final</b> Flags prefix,
 *                            <b>final</b> Context context,
 *                            <b>final</b> TokenSource source,
 *                            <b>final</b> Typesetter typesetter
 *                           ) <b>throws</b> InterpreterException {
 *       <i>// implement the execution behavior here</i>
 *       <b>return</b> <b>true</b>;
 *     }
 *
 *     <b>public void</b> evaluate(<b>final</b> Flags prefix,
 *                          <b>final</b> Context context,
 *                          <b>final</b> TokenSource source,
 *                          <b>final</b> Typesetter typesetter
 *                         ) <b>throws</b> InterpreterException {
 *       <i>// implement the evaluation behavior here</i>
 *     }
 *   }</pre>
 * <p>
 *  The parameters of {@code evaluate()} are identical to those of
 *  {@code execute()}. But note, that the expected behavior of
 *  {@code evaluate()} is that it does <em>not</em> modify the
 *  context or the typesetter but exclusively modifies the token
 *  source. Usually it reads some tokens and puts back its result to
 *  the token stream.
 * </p>
 *
 * <p>Conditionals &ndash; Also Called Ifs</p>
 *
 * <p>
 *  Conditionals are special because they modify the flow of control.
 *  In the macro programming language of TeX this may lead to a mode
 *  where tokens are absorbed at high speed. In this mode is necessary
 *  to identify conditionals to honor matching pairs of start and end
 *  tokens.
 * </p>
 * <p>
 *  All necessary actions are performed by the abstract base class
 *  {@link org.extex.unit.base.conditional.AbstractIf AbstractIf}. The only
 *  thing to do is to implement the method {@code conditional()} which computes
 *  whether the then or the else branch should be considered relevant. This is
 *  shown in the following example:
 * </p>
 *  <pre class="JavaSample">
 *   <b>package</b> my.package;
 *
 *   <b>import</b> org.extex.unit.base.conditional.AbstractIf;
 *   <b>import</b> org.extex.interpreter.contect.Context;
 *   <b>import</b> org.extex.interpreter.Flags;
 *   <b>import</b> org.extex.interpreter.TokenSource;
 *   <b>import</b> org.extex.interpreter.exception.InterpreterException;
 *   <b>import</b> org.extex.typesetter.Typesetter;
 *
 *   <b>class</b> MyIf <b>extends</b> AbstractIf {
 *
 *     <b>public</b> MyIf(<b>final</b> String name) {
 *       super(name);
 *       <i>// initialization code -- if required</i>
 *     }
 *
 *     <b>public boolean</b> conditional(<b>final</b> Flags prefix,
 *                                <b>final</b> Context context,
 *                                <b>final</b> TokenSource source,
 *                                <b>final</b> Typesetter typesetter
 *                               ) <b>throws</b> InterpreterException {
 *       <i>// implement the evaluation of the conditional here</i>
 *       <b>return</b> <i>result</i>;
 *     }
 *   }</pre>
 * <p>
 *  The parameters are the same as the parameters for
 *  {@code execute()} described above.
 *  </p>
 * <p>
 *  Note that any conditional is expandable automatically. Thus it
 *  should not modify the context or the typesetter.
 * </p>
 *
 * <p>Interaction With Other Macros</p>
 *
 * <p>
 *  Several primitives of εχTeX are implemented generically. Let us
 *  consider for example the macro {@code \the}. This primitive simply
 *  gathers the next token and delegates the task of providing an
 *  appropriate definition for {@code \the} to the definition of this
 *  token.
 * </p>
 * <p>
 *  The ability to be usable after {@code \the} is expressed with the
 *  help of the interface {@link org.extex.interpreter.type.Theable
 *  Theable}. Thus it is enough for a primitive to implement this
 *  interface if it needs to be usable after {@code \the}.
 * </p>
 * <p>
 *  The following list contains some macros of εχTeX and the
 *  related interfaces:
 * </p>
 * <table> <caption>TBD</caption>
 *  <tr>
 *   <td>{@code \advance}</td>
 *   <td>{@link org.extex.interpreter.type.code.Advanceable
 *     Advanceable}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \box}</td>
 *   <td>{@link org.extex.interpreter.type.box.Boxable Boxable}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \count}</td>
 *   <td>{@link org.extex.interpreter.parser.CountConvertible
 *     CountConvertible}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \dimen}</td>
 *   <td>{@link org.extex.interpreter.parser.DimenConvertible
 *     DimenConvertible}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \divide}</td>
 *   <td>{@link org.extex.interpreter.type.code.Divideable
 *     Dividable}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \font}</td>
 *   <td>{@link org.extex.interpreter.type.font.FontConvertible
 *     FontConvertible}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \multiply}</td>
 *   <td>{@link org.extex.interpreter.type.code.Multiplyable
 *     Multiplyable}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \show}</td>
 *   <td>{@link org.extex.interpreter.type.Showable Showable}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \showthe}</td>
 *   <td>{@link org.extex.interpreter.type.Theable Theable}</td>
 *  </tr>
 *  <tr>
 *   <td>{@code \the}</td>
 *   <td>{@link org.extex.interpreter.type.Theable Theable}</td>
 *  </tr>
 * </table>
 */
package org.extex.unit.tex;
