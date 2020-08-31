/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.unit.Loader;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This primitive initiates the loading of Java code and implements the
 * primitive {@code \javaload}.
 *
 * <p>The Primitive {@code \javaload}</p>
 * <p>
 * The primitive {@code \javaload} loads a java class and invokes its
 * {@code init()} method. With this method it is possible to load larger
 * extensions of εχTeX in one junk. There is no need to declare each single
 * macro
 * with {@code \javadef}.
 * </p>
 *
 * <p>Syntax</p>
 * The general form of this primitive is
 *
 * <pre class="syntax">
 *   &lang;javaload&rang;
 *       &rarr; {@code \javaload} &lang;tokens&rang;  </pre>
 *
 * <p>
 * The <i>&lang;tokens&rang;</i> is any specification of a list of tokens like a
 * constant list enclosed in braces or a token register. The value of these
 * tokens are taken and interpreted as the name of a Java class. This class is
 * loaded if needed, instantiated, and its method
 * {@link org.extex.unit.dynamic.java.Loadable#init(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter)
 * init()} is invoked. The instantiation requires the empty constructor to be
 * visible.
 * </p>
 *
 * <p>Examples</p>
 *
 * <p>
 * The following example illustrates the use of this primitive:
 * </p>
 *
 * <pre class="TeXSample">
 *   \javaload{org.extex.extensions.Basic} </pre>
 *
 * <p>
 * For the loading of the Java class it is necessary that this Java class
 * implements the interface {@link org.extex.unit.dynamic.java.Loadable
 * Loadable}.
 * </p>
 *
 * <pre class="JavaSample">
 *   <b>package</b> my.package;
 *
 *   <b>import</b> org.extex.interpreter.contect.Context;
 *   <b>import</b> org.extex.interpreter.primitives.dynamic.java.Loadable;
 *   <b>import</b> org.extex.typesetter.Typesetter;
 *   <b>import</b> org.extex.util.exception.GeneralException;
 *
 *   <b>class</b> MyModule <b>implements</b> Loadable {
 *
 *     <b>public</b> MyModule() {
 *       super();
 *       <i>// initialization code &ndash; if required</i>
 *     }
 *
 *     <b>public void</b> init(<b>final</b> Context context,
 *                      <b>final</b> Typesetter typesetter
 *                     ) <b>throws</b> GeneralException {
 *       <i>// implement the initialization code here</i>
 *     }
 *   } </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class JavaLoad extends AbstractCode implements Loader {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object. This method is needed for the nativeload wrapper.
   */
  public JavaLoad() {

    super( null );
  }

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public JavaLoad( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    load( context, source, typesetter );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void load( Context context, TokenSource source, Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    String classname;
    try {
      classname = source.getTokens( context, source, typesetter ).toText();
    } catch( EofException e ) {
      throw new EofInToksException( toText( context ) );
    }
    if( "".equals( classname ) ) {
      throw new HelpingException( getLocalizer(), "ClassNotFound",
                                  classname );
    }
    Loadable component;

    try {
      component = (Loadable) (Class.forName( classname ).newInstance());
      component.init( context, typesetter );
    } catch( ClassNotFoundException e ) {
      throw new HelpingException( getLocalizer(), "ClassNotFound",
                                  classname );
    } catch( ClassCastException e ) {
      throw new HelpingException( getLocalizer(), "ClassCast", classname,
                                  Loadable.class.getName(), toText() );
    } catch( HelpingException e ) {
      throw e;
    } catch( Exception e ) {
      throw new NoHelpException( e );
    }
  }

}
