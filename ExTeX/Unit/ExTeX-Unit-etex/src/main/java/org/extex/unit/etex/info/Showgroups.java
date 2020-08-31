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

package org.extex.unit.etex.info;

import org.extex.core.Locator;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupTypeVisitor;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

import java.util.logging.Logger;

/**
 * This class provides an implementation for the primitive
 * {@code \showgroups}.
 *
 * <p>The Primitive {@code \showgroups}</p>
 * <p>
 * The primitive {@code \showgroups} prints the list of group descriptions to
 * the log file. The list consists of descriptions of the currently open groups
 * in descending order.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;showgroups&rang;
 *       &rarr; {@code \showgroups}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \showgroups  </pre>
 *
 *
 * <p>Configuration</p>
 *
 *
 * <p>
 * The primitive takes an optional attribute named {@code format}. If this
 * attribute is present and has the value {@code short} then only the line
 * number is logged instead of the complete locator.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Showgroups extends AbstractCode
    implements
    LogEnabled,
    Configurable {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 20060616L;

  /**
   * The field {@code GTV} contains the group type visitor to map the group
   * types to keys for the localizer.
   */
  private static final GroupTypeVisitor<String, Object> GTV =
      new GroupTypeVisitor<String, Object>() {

        /**
         * This method is invoked when an adjusted hbox group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitAdjustedHboxGroup(
         *java.lang.Object)
         */
        public String visitAdjustedHboxGroup( Object arg ) {

          return "adjusted.hbox.group";
        }

        /**
         * This method is invoked when an align group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitAlignGroup(
         *java.lang.Object)
         */
        public String visitAlignGroup( Object arg ) {

          return "align.group";
        }

        /**
         * This method is invoked when a bottom level group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitBottomLevelGroup(
         *java.lang.Object)
         */
        public String visitBottomLevelGroup( Object arg ) {

          return "bottom.level.group";
        }

        /**
         * This method is invoked when a disc group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitDiscGroup(
         *java.lang.Object)
         */
        public String visitDiscGroup( Object arg ) {

          return "disc.group";
        }

        /**
         * This method is invoked when a hbox group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitHboxGroup(
         *java.lang.Object)
         */
        public String visitHboxGroup( Object arg ) {

          return "hbox.group";
        }

        /**
         * This method is invoked when an insert group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitInsertGroup(
         *java.lang.Object)
         */
        public String visitInsertGroup( Object arg ) {

          return "insert.group";
        }

        /**
         * This method is invoked when a math choice group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathChoiceGroup(
         *java.lang.Object)
         */
        public String visitMathChoiceGroup( Object arg ) {

          return "math.choice.group";
        }

        /**
         * This method is invoked when a math group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathGroup(
         *java.lang.Object)
         */
        public String visitMathGroup( Object arg ) {

          return "math.group";
        }

        /**
         * This method is invoked when a math left group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathLeftGroup(
         *java.lang.Object)
         */
        public String visitMathLeftGroup( Object arg ) {

          return "math.left.group";
        }

        /**
         * This method is invoked when a math shift group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitMathShiftGroup(
         *java.lang.Object)
         */
        public String visitMathShiftGroup( Object arg ) {

          return "math.shift.group";
        }

        /**
         * This method is invoked when a no align group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitNoAlignGroup(
         *java.lang.Object)
         */
        public String visitNoAlignGroup( Object arg ) {

          return "no.align.group";
        }

        /**
         * This method is invoked when a output group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitOutputGroup(
         *java.lang.Object)
         */
        public String visitOutputGroup( Object arg ) {

          return "output.group";
        }

        /**
         * This method is invoked when a semi simple group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitSemiSimpleGroup(
         *java.lang.Object)
         */
        public String visitSemiSimpleGroup( Object arg ) {

          return "semi.simple.group";
        }

        /**
         * This method is invoked when a simple group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitSimpleGroup(
         *java.lang.Object)
         */
        public String visitSimpleGroup( Object arg ) {

          return "simple.group";
        }

        /**
         * This method is invoked when a vbox group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVboxGroup(
         *java.lang.Object)
         */
        public String visitVboxGroup( Object arg ) {

          return "vbox.group";
        }

        /**
         * This method is invoked when a vcenter group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVcenterGroup(
         *java.lang.Object)
         */
        public String visitVcenterGroup( Object arg ) {

          return "vcenter.group";
        }

        /**
         * This method is invoked when a vtop group has been
         * encountered.
         *
         * @param arg the argument
         *
         * @return some object
         *
         * @see org.extex.interpreter.context.group.GroupTypeVisitor#visitVtopGroup(
         *java.lang.Object)
         */
        public String visitVtopGroup( Object arg ) {

          return "vtop.group";
        }

      };

  /**
   * The field {@code key} contains the key to be appended to the format
   * name.
   */
  private String key = "";

  /**
   * The field {@code logger} contains the target channel for the message.
   */
  private transient Logger logger = null;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Showgroups( CodeToken token ) {

    super( token );
  }

  /**
   * Configure an object according to a given Configuration.
   *
   * @param config the configuration object to consider
   * @throws ConfigurationException in case that something went wrong
   * @see org.extex.framework.configuration.Configurable#configure(
   *org.extex.framework.configuration.Configuration)
   */
  public void configure( Configuration config ) throws ConfigurationException {

    String format = config.getAttribute( "format" );
    if( "short".equals( format ) ) {
      key = ".short";
    }
  }

  /**
   * Setter for the logger.
   *
   * @param log the logger to use
   * @see org.extex.framework.logger.LogEnabled#enableLogging(
   *java.util.logging.Logger)
   */
  public void enableLogging( Logger log ) {

    this.logger = log;
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Localizer loc = getLocalizer();
    GroupInfo[] gi = context.getGroupInfos();
    GroupInfo g;
    Token start;
    Locator locator;

    for( int i = gi.length - 1; i >= 0; i-- ) {
      g = gi[ i ];
      String level = (i == 0 ? ""
          : loc.format( "log.level" + key, Integer.toString( i ) ));
      start = g.getGroupStart();
      String token = (start == null ? ""
          : loc.format( "log.token" + key,
                        start.toText( context.escapechar() ) ));
      locator = g.getLocator();
      String line =
          (locator == null ? ""
              : loc.format( "log.resource" + key, locator
              .getResourceName(), Integer.toString( locator
                                                        .getLineNumber() ) ));

      logger.info( loc.format( "log.pattern",
                               loc.format( (String) g.getGroupType()
                                                     .visit( GTV, null ) ),
                               level,
                               line,
                               token ) );
    }
  }

}
