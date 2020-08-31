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

package org.extex.framework.i18n;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * The localizer is a convenience class which joins the features of a resource
 * bundle with the features of a message formatter.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Localizer extends Serializable {

  /**
   * Getter for the value of a format string associated to a given key.
   *
   * @param key the key in the resource bundle to search for
   * @return the resource string or the String {@code ???}<i>key</i>
   * {@code ???} if none is found
   */
  String format( String key );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key. The argument object's value of toString()
   * replaces the sub-string {@code '{0}'} in the format.
   *
   * @param fmt the key in the resource bundle to search for
   * @param a   the Object used for the sub-string {@code {0}}
   * @return the expanded format string
   */
  String format( String fmt, Object a );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key. The argument object's value of toString()
   * replaces the sub-string {@code '{0}'} and {@code '{1}'} in the format.
   *
   * @param fmt the key in the resource bundle to search for
   * @param a   the Object used for the sub-string {@code {0}}
   * @param b   the Object used for the sub-string {@code {1}}
   * @return the expanded format string
   */
  String format( String fmt, Object a, Object b );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key. The argument object's value of toString()
   * replaces the sub-string {@code '{0}'},{@code '{1}'}, and {@code '{2}'}
   * in the format.
   *
   * @param fmt the key in the resource bundle to search for
   * @param a   the Object used for the sub-string {@code {0}}
   * @param b   the Object used for the sub-string {@code {1}}
   * @param c   the Object used for the sub-string {@code {2}}
   * @return the expanded format string
   */
  String format( String fmt, Object a, Object b, Object c );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key. The argument object's value of toString()
   * replaces the sub-string {@code '{0}'},{@code '{1}'},{@code '{2}'}, and
   * {@code '{3}'} in the format.
   *
   * @param fmt the key in the resource bundle to search for
   * @param a   the Object used for the sub-string {@code {0}}
   * @param b   the Object used for the sub-string {@code {1}}
   * @param c   the Object used for the sub-string {@code {2}}
   * @param d   the Object used for the sub-string {@code {3}}
   * @return the expanded format string
   */
  String format( String fmt, Object a, Object b, Object c, Object d );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key. The argument object's value of toString()
   * replaces the sub-string {@code '{0}'},{@code '{1}'},{@code '{2}'}, and
   * {@code '{3}'} in the format.
   *
   * @param fmt the key in the resource bundle to search for
   * @param a   the Object used for the sub-string {@code {0}}
   * @param b   the Object used for the sub-string {@code {1}}
   * @param c   the Object used for the sub-string {@code {2}}
   * @param d   the Object used for the sub-string {@code {3}}
   * @param e   the Object used for the sub-string {@code {4}}
   * @return the expanded format string
   */
  String format( String fmt, Object a, Object b, Object c, Object d, Object e );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key. The argument object's value of toString()
   * replaces the substring {@code '{0}'},{@code '{1}'},{@code '{2}'}, and
   * so on in the format.
   *
   * @param fmt the key in the resource bundle to search for
   * @param a   the Object used for the substrings {@code {<i>n</i>}}
   * @return the expanded format string
   */
  String format( String fmt, Object[] a );

  /**
   * Getter for the value of a format string associated to a given key.
   *
   * @param key the key in the resource bundle to search for
   * @return the resource string or {@code null}
   */
  String getFormat( String key );

  /**
   * Get the value of a format string associated to a given key in the
   * resource bundle and print it to the given writer.
   *
   * @param writer the target output writer
   * @param fmt    the key in the resource bundle to search for
   */
  void message( PrintStream writer, String fmt );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key and print the result to a writer. The argument
   * object's value of toString() replaces the sub-string {@code '{0}'} in
   * the format.
   *
   * @param writer the target output writer
   * @param fmt    the key in the resource bundle to search for
   * @param a      the Object used for the substring {@code {0}}
   */
  void message( PrintStream writer, String fmt, Object a );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key and print the result to a writer. The argument
   * object's value of toString() replaces the sub-string {@code '{0}'} and
   * {@code '{1}'} in the format.
   *
   * @param writer the target output writer
   * @param fmt    the key in the resource bundle to search for
   * @param a      the Object used for the substring {@code {0}}
   * @param b      the Object used for the substring {@code {1}}
   */
  void message( PrintStream writer, String fmt, Object a, Object b );

  /**
   * Apply the given argument to the format string stored in the resource
   * bundle under the given key and print the result to a writer. The argument
   * object's value of toString() replaces the sub-string {@code '{0}'},
   * {@code '{1}'}, and {@code '{2}'} in the format.
   *
   * @param writer the target output writer
   * @param fmt    the key in the resource bundle to search for
   * @param a      the Object used for the sub-string {@code {0}}
   * @param b      the Object used for the sub-string {@code {1}}
   * @param c      the Object used for the sub-string {@code {2}}
   */
  void message( PrintStream writer, String fmt, Object a, Object b, Object c );

}
