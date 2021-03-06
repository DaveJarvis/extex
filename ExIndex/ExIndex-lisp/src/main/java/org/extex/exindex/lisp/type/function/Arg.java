/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.type.function;

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingParameterException;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.value.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides an argument descriptor for a function.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Arg {

  /**
   * The field {@code LIST} contains the descriptor for a list.
   */
  public static final Arg LIST = new Arg( null, LList.class, LList.NIL, false );

  /**
   * The field {@code QLIST} contains the descriptor for a quoted list.
   */
  public static final Arg QLIST = new Arg( null, LList.class, LList.NIL, true );

  /**
   * The field {@code QSYMBOL} contains the descriptor for a quoted list.
   */
  public static final Arg QSYMBOL = new Arg( null, LSymbol.class, null, true );

  /**
   * The field {@code VALUE} contains the specification for an arbitrary
   * value.
   */
  public static final Arg VALUE =
      new Arg( null, LValue.class, LList.NIL, false );

  /**
   * The field {@code QVALUE} contains the specification for a quoted
   * value.
   */
  public static final Arg QVALUE =
      new Arg( null, LValue.class, LList.NIL, true );

  /**
   * The field {@code STRING} contains the specification for a string.
   */
  public static final Arg STRING = new Arg( null, String.class, "", false ) {

    /**
     *      org.extex.exindex.lisp.type.value.LValue)
     */
    @Override
    public Object validate( LValue value ) throws LNonMatchingTypeException {

      if( !(value instanceof LString) ) {
        throw new LNonMatchingTypeException( "" );
      }
      return ((LString) value).getValue();
    }

  };

  /**
   * The field {@code LSTRING} contains the specification for a string.
   */
  public static final Arg LSTRING =
      new Arg( null, LString.class, new LString( "" ), false ) {

        /**
         *      org.extex.exindex.lisp.type.value.LValue)
         */
        @Override
        public Object validate( LValue value )
            throws LNonMatchingTypeException {

          if( !(value instanceof LString) ) {
            throw new LNonMatchingTypeException( "" );
          }
          return value;
        }

      };

  /**
   * Get the descriptor for an optional boolean.
   *
   * @param flag     the name of the boolean flag
   * @param fallback the default value
   * @return a new specification for the name
   */
  public static final Arg OPT_BOOLEAN( String flag, Boolean fallback ) {

    return new Arg( flag, Boolean.class, fallback, false ) {

      /**
       *      java.util.List, int, java.lang.Object[], int)
       */
      @Override
      public int parse( List<LValue> args, int ai, Object[] arguments,
                        int index ) {

        arguments[ index ] = Boolean.TRUE;
        return ai;
      }
    };
  }

  /**
   * Get the descriptor for an optional boolean.
   *
   * @param flag the name of the boolean flag
   * @return a new specification for the name
   */
  public static final Arg OPT_LBOOLEAN( String flag ) {

    return new Arg( flag, LBoolean.class, LBoolean.FALSE, false ) {

      /**
       *      java.util.List, int, java.lang.Object[], int)
       */
      @Override
      public int parse( List<LValue> args, int ai, Object[] arguments,
                        int index ) {

        arguments[ index ] = LBoolean.TRUE;
        return ai;
      }
    };
  }

  /**
   * Get the descriptor for an optional list.
   *
   * @param flag the name of the flag
   * @return the argument descriptor
   */
  public static final Arg OPT_LIST( String flag ) {

    return new OptionalArgWithParameter( flag, LList.class, LList.NIL, false ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a ) throws LNonMatchingTypeException {

        if( !(a instanceof LList) ) {
          throw new LNonMatchingTypeException( "" );
        }
        return a;
      }

    };
  }

  /**
   * Get the descriptor for an optional number.
   *
   * @param flag     the name of the flag
   * @param fallback the default vale used as fallback
   * @return the argument descriptor
   */
  public static final Arg OPT_LNUMBER( String flag, LNumber fallback ) {

    return new OptionalArgWithParameter( flag, LNumber.class, fallback,
                                         false ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a ) throws LNonMatchingTypeException {

        if( a instanceof LNumber ) {
          return a;
        }
        else if( a instanceof LDouble ) {
          return new LNumber( Long.valueOf(
              Math.round( ((LDouble) a).getValue() ) ).longValue() );
        }
        else {
          throw new LNonMatchingTypeException( "" );
        }
      }

    };
  }

  /**
   * Get the descriptor for an optional string.
   *
   * @param flag     the name of the flag
   * @param fallback the default value as fallback
   * @return the argument descriptor
   */
  public static final Arg OPT_LSTRING( String flag, LString fallback ) {

    return new OptionalArgWithParameter( flag, LString.class, fallback,
                                         false ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a ) throws LNonMatchingTypeException {

        if( a instanceof LString ) {
          return a;
        }
        throw new LNonMatchingTypeException( "" );
      }

    };
  }

  /**
   * Get the descriptor for an optional number.
   *
   * @param flag     the name of the flag
   * @param fallback the default value as fallback
   * @return the argument descriptor
   */
  public static final Arg OPT_NUMBER( String flag, Integer fallback ) {

    return new OptionalArgWithParameter( flag, Integer.class, fallback,
                                         false ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a ) throws LNonMatchingTypeException {

        if( a instanceof LNumber ) {
          return Integer.valueOf( (int) ((LNumber) a).getValue() );
        }
        else if( a instanceof LDouble ) {
          return Integer.valueOf( (int) Math.round( ((LDouble) a)
                                                        .getValue() ) );
        }
        else {
          throw new LNonMatchingTypeException( "" );
        }
      }

    };
  }

  /**
   * Get the descriptor for an optional list.
   *
   * @param flag the name of the flag
   * @return the argument descriptor
   */
  public static final Arg OPT_QLIST( String flag ) {

    return new OptionalArgWithParameter( flag, LList.class, LList.NIL, true ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a ) throws LNonMatchingTypeException {

        if( !(a instanceof LList) ) {
          throw new LNonMatchingTypeException( "" );
        }
        return a;
      }

    };
  }

  /**
   * Get the descriptor for an optional list of strings.
   *
   * @param flag the name of the flag
   * @return the argument descriptor
   */
  public static final Arg OPT_QSTRING_LIST( String flag ) {

    return new OptionalArgWithParameter( flag, LList.class, LList.NIL, true ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a ) throws LNonMatchingTypeException {

        if( !(a instanceof LList) ) {
          throw new LNonMatchingTypeException( "" );
        }
        List<String> l = new ArrayList<String>();
        for( LValue val : (LList) a ) {
          if( !(val instanceof LString) ) {
            throw new LNonMatchingTypeException( "" );
          }
          l.add( ((LString) val).getValue() );
        }
        return l;
      }

    };
  }

  /**
   * Get the descriptor for an optional string.
   *
   * @param flag     the name of the flag
   * @param fallback the default value
   * @return the argument descriptor
   */
  public static final Arg OPT_STRING( String flag, String fallback ) {

    return new OptionalArgWithParameter( flag, String.class, fallback, false ) {

      /**
       *      org.extex.exindex.lisp.type.value.LValue)
       */
      @Override
      public Object validate( LValue a )
          throws LMissingParameterException,
          LNonMatchingTypeException {

        if( a instanceof LString ) {
          return ((LString) a).getValue();
        }
        else if( a instanceof LSymbol ) {
          return ((LSymbol) a).getValue();
        }
        else if( a instanceof LNumber || a instanceof LDouble ) {
          return a.toString();
        }
        else {
          throw new LNonMatchingTypeException( "" );
        }
      }

    };
  }

  /**
   * Get the descriptor for a varargs specification sucking up all remaining
   * arguments.
   *
   * @return the argument descriptor
   */
  public static Arg VARARG() {

    return new Arg( null, LList.class, LList.NIL, false ) {

      /**
       *      int, java.lang.Object[], int)
       */
      @Override
      public int parse( List<LValue> args, int ai, Object[] arguments,
                        int index ) throws LException {

        LList list = new LList();
        int i = ai;
        int size = args.size();
        for( ; i < size; i++ ) {
          list.add( args.get( i ) );
        }

        arguments[ index ] = list;
        return i;
      }
    };
  }

  /**
   * The field {@code resultClass} contains the class of the result.
   */
  private final Class<?> resultClass;

  /**
   * The field {@code def} contains the default value.
   */
  private final Object def;

  /**
   * The field {@code flag} contains the name of the flag.
   */
  private final String flag;

  /**
   * The field {@code quoted} contains the indicator for quoted values.
   */
  private final boolean quoted;

  /**
   * Creates a new object.
   *
   * @param flag        the name of the flag
   * @param resultClass the result class
   * @param def         the default value
   * @param quoted      the indicator for quoted expressions
   */
  public Arg( String flag, Class<?> resultClass, Object def, boolean quoted ) {

    this.def = def;
    this.flag = flag;
    this.resultClass = resultClass;
    this.quoted = quoted;
  }

  /**
   * Getter for the default value.
   *
   * @return the default value
   */
  public Object getDefault() {

    return def;
  }

  /**
   * Getter for the flag.
   *
   * @return the flag; i.e. {@code null} as default
   */
  public String getFlag() {

    return flag;
  }

  /**
   * Getter for the result class
   *
   * @return the result class
   */
  public Class<?> getResultClass() {

    return resultClass;
  }

  /**
   * Getter for the indicator whether to protect the argument from evaluation.
   *
   * @return {@code true} if no evaluation is desirable
   */
  public boolean isQuoted() {

    return quoted;
  }

  /**
   * Parse an argument from the inputs and transfer it to the outputs.
   *
   * @param args      the source array
   * @param ai        the source index
   * @param arguments the target array
   * @param index     the index in the target array
   * @return the result
   * @throws LException in case of an error
   */
  public int parse( List<LValue> args, int ai, Object[] arguments, int index )
      throws LException {

    return ai;
  }

  /**
   * Validate and translate an argument.
   *
   * @param value the L value
   * @return the object to use
   * @throws LException in case of an error
   */
  public Object validate( LValue value ) throws LException {

    return value;
  }

}
