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

package org.extex.font.format.dvi.command;

import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all DVI commands.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public abstract class DviCommand {

  /**
   * The length of {@code c} elements of the bob command.
   */
  public static final int BOP_LENGTH = 10;

  /**
   * Interpreter step for characters
   */
  private static final DviReadCommand SETCHAR = new DviReadCommand() {

    /**
     * set_char_0: Typeset character number 0 from font {@code f} such
     * that the reference point of the character is at {@code (h,v)}.
     * Then increase {@code h} by the width of that character. Note
     * that a character may have zero or negative width, so one cannot be
     * sure that {@code h} will advance after this command; but
     * {@code h} usually does increase.
     *
     *
     * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
     *      int, int)
     */
    @Override
    public DviCommand read( final RandomAccessR rar, final int opcode,
                            final int sp )
        throws IOException,
        FontException,
        ConfigurationException {

      return new DviChar( opcode, sp, opcode );
    }
  };

  /**
   * the min fontnum
   */
  private static final int MIN_FONTNUM = 172;

  /**
   * Interpreter step for characters
   */
  private static final DviReadCommand FNTNUM = new DviReadCommand() {

    /**
     * fnt_num_1 through fnt_num_63 (opcodes 172 to 234): Set
     * {@code f = 1}, ..., {@code f = 63}, respectively.
     *
     * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
     *      int, int)
     */
    @Override
    public DviCommand read( final RandomAccessR rar, final int opcode,
                            final int sp )
        throws IOException,
        FontException,
        ConfigurationException {

      int fn = opcode - MIN_FONTNUM + 1;

      return new DviFntNum( opcode, sp, fn );
    }
  };

  /**
   * Interpreter step for characters
   */
  private static final DviReadCommand UNDEF = new DviReadCommand() {

    /**
     * undefined opcode
     *
     *      int, int)
     */
    @Override
    public DviCommand read( final RandomAccessR rar, final int opcode,
                            final int sp ) throws IOException, FontException {

      // throw new DviUndefinedOpcodeException();
      return null;
    }
  };

  /**
   * the interpreter commands
   */
  private static final DviReadCommand[] OPCODEARRAY = {
      /**
       * set_char_0: Typeset character number 0 from font {@code f}
       * such that the reference point of the character is at
       * {@code (h,v)}. Then increase {@code h} by the width of
       * that character. Note that a character may have zero or negative
       * width, so one cannot be sure that {@code h} will advance
       * after this command; but {@code h} usually does increase.
      */
      SETCHAR, /* 0 */

      /**
       * set_char_1 through set_char_127 (opcodes 1 to 127): Do the
       * operations of {@code set_char_0}; but use the character
       * whose number matches the opcode, instead of character 0.
      */
      SETCHAR, /* 1 */
      SETCHAR, /* 2 */
      SETCHAR, /* 3 */
      SETCHAR, /* 4 */
      SETCHAR, /* 5 */
      SETCHAR, /* 6 */
      SETCHAR, /* 7 */
      SETCHAR, /* 8 */
      SETCHAR, /* 9 */
      SETCHAR, /* 10 */
      SETCHAR, /* 11 */
      SETCHAR, /* 12 */
      SETCHAR, /* 13 */
      SETCHAR, /* 14 */
      SETCHAR, /* 15 */
      SETCHAR, /* 16 */
      SETCHAR, /* 17 */
      SETCHAR, /* 18 */
      SETCHAR, /* 19 */
      SETCHAR, /* 20 */
      SETCHAR, /* 21 */
      SETCHAR, /* 22 */
      SETCHAR, /* 23 */
      SETCHAR, /* 24 */
      SETCHAR, /* 25 */
      SETCHAR, /* 26 */
      SETCHAR, /* 27 */
      SETCHAR, /* 28 */
      SETCHAR, /* 29 */
      SETCHAR, /* 30 */
      SETCHAR, /* 31 */
      SETCHAR, /* 32 */
      SETCHAR, /* 33 */
      SETCHAR, /* 34 */
      SETCHAR, /* 35 */
      SETCHAR, /* 36 */
      SETCHAR, /* 37 */
      SETCHAR, /* 38 */
      SETCHAR, /* 39 */
      SETCHAR, /* 40 */
      SETCHAR, /* 41 */
      SETCHAR, /* 42 */
      SETCHAR, /* 43 */
      SETCHAR, /* 44 */
      SETCHAR, /* 45 */
      SETCHAR, /* 46 */
      SETCHAR, /* 47 */
      SETCHAR, /* 48 */
      SETCHAR, /* 49 */
      SETCHAR, /* 50 */
      SETCHAR, /* 51 */
      SETCHAR, /* 52 */
      SETCHAR, /* 53 */
      SETCHAR, /* 54 */
      SETCHAR, /* 55 */
      SETCHAR, /* 56 */
      SETCHAR, /* 57 */
      SETCHAR, /* 58 */
      SETCHAR, /* 59 */
      SETCHAR, /* 60 */
      SETCHAR, /* 61 */
      SETCHAR, /* 62 */
      SETCHAR, /* 63 */
      SETCHAR, /* 64 */
      SETCHAR, /* 65 */
      SETCHAR, /* 66 */
      SETCHAR, /* 67 */
      SETCHAR, /* 68 */
      SETCHAR, /* 69 */
      SETCHAR, /* 70 */
      SETCHAR, /* 71 */
      SETCHAR, /* 72 */
      SETCHAR, /* 73 */
      SETCHAR, /* 74 */
      SETCHAR, /* 75 */
      SETCHAR, /* 76 */
      SETCHAR, /* 77 */
      SETCHAR, /* 78 */
      SETCHAR, /* 79 */
      SETCHAR, /* 80 */
      SETCHAR, /* 81 */
      SETCHAR, /* 82 */
      SETCHAR, /* 83 */
      SETCHAR, /* 84 */
      SETCHAR, /* 85 */
      SETCHAR, /* 86 */
      SETCHAR, /* 87 */
      SETCHAR, /* 88 */
      SETCHAR, /* 89 */
      SETCHAR, /* 90 */
      SETCHAR, /* 91 */
      SETCHAR, /* 92 */
      SETCHAR, /* 93 */
      SETCHAR, /* 94 */
      SETCHAR, /* 95 */
      SETCHAR, /* 96 */
      SETCHAR, /* 97 */
      SETCHAR, /* 98 */
      SETCHAR, /* 99 */
      SETCHAR, /* 100 */
      SETCHAR, /* 101 */
      SETCHAR, /* 102 */
      SETCHAR, /* 103 */
      SETCHAR, /* 104 */
      SETCHAR, /* 105 */
      SETCHAR, /* 106 */
      SETCHAR, /* 107 */
      SETCHAR, /* 108 */
      SETCHAR, /* 109 */
      SETCHAR, /* 110 */
      SETCHAR, /* 111 */
      SETCHAR, /* 112 */
      SETCHAR, /* 113 */
      SETCHAR, /* 114 */
      SETCHAR, /* 115 */
      SETCHAR, /* 116 */
      SETCHAR, /* 117 */
      SETCHAR, /* 118 */
      SETCHAR, /* 119 */
      SETCHAR, /* 120 */
      SETCHAR, /* 121 */
      SETCHAR, /* 122 */
      SETCHAR, /* 123 */
      SETCHAR, /* 124 */
      SETCHAR, /* 125 */
      SETCHAR, /* 126 */
      SETCHAR, /* 127 */
      /**
       * set1 128, c[1]: Same as {@code set_char_0}, except that
       * character number {@code c} is typeset. TeX82 uses this
       * command for characters in the range 128 &lt;= c &lt; 256.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt8() );
        }
      },

      /**
       * set2 129, c[2]: Same as {@code set1}, except that
       * {@code c} is two bytes long, so it is in the range 0 &lt;= c
       * &lt; 65536. TeX82 never uses this command, which is intended for
       * processors that deal with oriental languages; but a DVI processor
       * should allow character codes greater than 255. The processor may
       * then assume that these characters have the same width as the
       * character whose respective codes are {@code c mod 256}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt16() );
        }
      },
      /**
       * set3 130, c[3]: Same as set1, except that {@code c} is three
       * bytes long, so it can be as large as 2^24-1.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt24() );
        }
      },
      /**
       * set4 131, c[+4]: Same as {@code set1}, except that
       * {@code c} is four bytes long, possibly even negative.
       * Imagine that.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt() );
        }
      },
      /**
       * set_rule 132, a[+4] b[+4]: Typeset a solid black rectangle of
       * height {@code a} and width {@code b}, with its bottom
       * left corner at {@code (h,v)}. Then set
       * {@code h = h + b}. If either a &lt;= 0 or b &lt;= 0, nothing
       * should be typeset. Note that if b &lt; 0, the value of
       * {@code h} will decrease even though nothing else happens.
       * Programs that typeset from DVI files should be careful to make
       * the rules line up carefully with digitized characters, as
       * explained in connection with the {@code rule_pixels}
       * subroutine below.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviRule( opcode, sp, rar.readInt(), rar.readInt() );
        }
      },
      /**
       * put1 133, c[1]: Typeset character number {@code c} from font
       * {@code f} such that the reference point of the character is
       * at {@code (h,v)}. (The 'put' commands are exactly like the
       * 'set' commands, except that they simply put out a character or a
       * rule without moving the reference point afterwards.)
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readByteAsInt(), true );
        }
      },
      /**
       * put2 134, c[2]: Same as {@code set2}, except that
       * {@code h} is not changed.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt16(), true );
        }
      },
      /**
       * put3 135, c[3]: Same as {@code set3}, except that
       * {@code h} is not changed.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt24(), true );
        }
      },
      /**
       * put4 136, c[+4]: Same as {@code set4}, except that
       * {@code h} is not changed.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviChar( opcode, sp, rar.readInt(), true );
        }
      },

      /**
       * put_rule 137, a[+4] b[+4]: Same as {@code set_rule}, except
       * that {@code h} is not changed.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviRule( opcode, sp, rar.readInt(),
                              rar.readInt(), true );
        }
      },

      /**
       * nop 138: No operation, do nothing. Any number of {@code nop}
       * 's may occur between DVI commands, but a {@code nop} cannot
       * be inserted between a command and its parameters or between two
       * parameters.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviNOP( opcode, sp );
        }
      },

      /**
       * bop 139, c_0[+4] c_1[+4] ... c_9[+4] p[+4]: Beginning of a page:
       * Set {@code (h,v,w,x,y,z) = (0,0,0,0,0,0)} and set the stack
       * empty. Set the current font {@code f} to an undefined value.
       * The ten {@code c_i} parameters can be used to identify
       * pages, if a user wants to print only part of a DVI file; TeX82
       * gives them the values of {@code count0} ...
       * {@code count9} at the time {@code shipout} was invoked
       * for this page. The parameter {@code p} points to the
       * previous {@code bop} command in the file, where the first
       * {@code bop} has {@code p=-1}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int[] c = new int[ BOP_LENGTH ];
          for( int i = 0; i < BOP_LENGTH; i++ ) {
            c[ i ] = rar.readInt();
          }
          int p = rar.readInt();
          return new DviBOP( opcode, sp, c, p );
        }
      },

      /**
       * eop 140: End of page: Print what you have read since the previous
       * {@code bop}. At this point the stack should be empty.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviEOP( opcode, sp );
        }
      },

      /**
       * push 141: Push the current values of {@code (h,v,w,x,y,z)}
       * onto the top of the stack; do not change any of these values.
       * Note that {@code f} is not pushed.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviPush( opcode, sp );
        }
      },

      /**
       * pop 142: Pop the top six values off of the stack and assign them
       * to {@code (h,v,w,x,y,z)}. The number of pops should never
       * exceed the number of pushes, since it would be highly
       * embarrassing if the stack were empty at the time of a
       * {@code pop} command.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviPOP( opcode, sp );
        }
      },

      /**
       * right1 143, b[+1]: Set {@code h = h + b}, i.e., move right
       * {@code b} units. The parameter is a signed number in two's
       * complement notation, -128 &lt;= b &lt; 128; if b &lt; 0, the
       * reference point actually moves left.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviRight( opcode, sp, rar.readByte() );
        }
      },

      /**
       * right2: 144, b[+2]: Same as {@code right1}, except that
       * {@code b} is a two-byte quantity in the range -32768 &lt;= b
       * &lt; 32768.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviRight( opcode, sp, rar.readShort() );
        }
      },

      /**
       * right3 145, b[+3]: Same as {@code right1}, except that
       * {@code b} is a three-byte quantity in the range -2^23 &lt;=
       * b &lt; 2^23.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviRight( opcode, sp, rar.readSignInt24() );
        }
      },

      /**
       * right4 146, b[+4]: Same as {@code right1}, except that
       * {@code b} is a four-byte quantity in the range -2^31 &lt;= b
       * &lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviRight( opcode, sp, rar.readInt() );
        }
      },

      /**
       * w0 147: Set {@code h = h + w}; i.e., move right
       * {@code w} units. With luck, this parameterless command will
       * usually suffice, because the same kind of motion will occur
       * several times in succession; the following commands explain how
       * {@code w} gets particular values.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviW( opcode, sp, 0, true );
        }
      },

      /**
       * w1 148, b[+1]: Set {@code w = b} and {@code h = h + b}.
       * The value of {@code b} is a signed quantity in two's
       * complement notation, -128 &lt;= b /lt; 128. This command changes
       * the current {@code w} spacing and moves right by
       * {@code b}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviW( opcode, sp, rar.readByte() );
        }
      },

      /**
       * w2 149, b[+2]: Same as {@code w1}, but {@code b} is a
       * two-byte-long parameter, -32768 &lt;= b &lt; 32768.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviW( opcode, sp, rar.readShort() );
        }
      },

      /**
       * w3 150, b[+3]: Same as {@code w1}, but {@code b} is a
       * three-byte-long parameter, -2^23 &lt;= b &lt; 2^23.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviW( opcode, sp, rar.readSignInt24() );
        }
      },

      /**
       * w4 151, b[+4]: Same as {@code w1}, but {@code b} is a
       * four-byte-long parameter, -2^31 &lt;= b &lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviW( opcode, sp, rar.readInt() );
        }
      },

      /**
       * x0 152: Set {@code h = h + x}; i.e., move right
       * {@code x} units. The {@code x} commands are like the
       * {@code w} commands except that they involve {@code x}
       * instead of {@code w}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviX( opcode, sp, 0, true );
        }
      },

      /**
       * x1 153, b[+1]: Set {@code x = b} and {@code h =  h + b}
       * . The value of {@code b} is a signed quantity in two's
       * complement notation, -128 &lt;= b &lt; 128. This command changes
       * the current {@code x} spacing and moves right by
       * {@code b}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviX( opcode, sp, rar.readByte() );
        }
      },

      /**
       * x2 154, b[+2]: Same as {@code x1}, but {@code b} is a
       * two-byte-long parameter, -32768 &lt;= b &lt; 32768.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviX( opcode, sp, rar.readShort() );
        }
      },

      /**
       * x3 155, b[+3]: Same as {@code x1}, but {@code b} is a
       * three-byte-long parameter, -2^23 &lt;= b &lt; 2^23.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviX( opcode, sp, rar.readSignInt24() );
        }
      },

      /**
       * x4 156, b[+4]: Same as {@code x1}, but {@code b} is a
       * four-byte-long parameter, -2^31 &lt;= b /lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviX( opcode, sp, rar.readInt() );
        }
      },

      /**
       * down1 157, a[+1]: Set {@code v = v + a}, i.e., move down
       * {@code a} units. The parameter is a signed number in two's
       * complement notation, -128 &lt;= a &lt; 128; if a &lt; 0, the
       * reference point actually moves up.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviDown( opcode, sp, rar.readByte() );
        }
      },

      /**
       * down2 158, a[+2]: Same as {@code down1}, except that
       * {@code a} is a two-byte quantity in the range -32768 &lt;= a
       * &lt; 32768.
      */
      new DviReadCommand() {

        /**
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviDown( opcode, sp, rar.readShort() );
        }
      },

      /**
       * down3 159, a[+3]: Same as {@code down1}, except that
       * {@code a} is a three-byte quantity in the range -2^23 &lt;=
       * a &lt; 2^23.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviDown( opcode, sp, rar.readSignInt24() );
        }
      },

      /**
       * down4 160, a[+4]: Same as {@code down1}, except that
       * {@code a} is a four-byte quantity in the range -2^31 &lt;= a
       * &lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviDown( opcode, sp, rar.readInt() );
        }
      },

      /**
       * y0 161: Set {@code v = v + y}; i.e., move down
       * {@code y} units. With luck, this parameterless command will
       * usually suffice, because the same kind of motion will occur
       * several times in succession; the following commands explain how
       * {@code y} gets particular values.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviY( opcode, sp, 0, true );
        }
      },

      /**
       * y1 162, a[+1]: Set {@code y = a} and {@code v = v + a}.
       * The value of {@code a} is a signed quantity in two's
       * complement notation, -128 &lt;= a &lt; 128. This command changes
       * the current {@code y} spacing and moves down by
       * {@code a}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviY( opcode, sp, rar.readByte() );
        }
      },

      /**
       * y2 163, a[+2]: Same as {@code y1}, but {@code a} is a
       * two-byte-long parameter, -32768 &lt;= a &lt; 32768.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviY( opcode, sp, rar.readShort() );
        }
      },

      /**
       * y3 164, a[+3]: Same as {@code y1}, but {@code a} is a
       * three-byte-long parameter, -2^23 &lt;= a &lt; 2^23.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviY( opcode, sp, rar.readSignInt24() );
        }
      },

      /**
       * y4 165, a[+4]: Same as {@code y1}, but {@code a} is a
       * four-byte-long parameter, -2^31 &lt;= a &lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviY( opcode, sp, rar.readInt() );
        }
      },

      /**
       * z0 166: Set {@code v = v + z}; i.e., move down
       * {@code z} units. The {@code z} commands are like the
       * {@code y} commands except that they involve {@code z}
       * instead of {@code y}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviZ( opcode, sp, 0, true );
        }
      },

      /**
       * z1 167, a[+1]: Set {@code z = a} and {@code v = v + a}.
       * The value of {@code a} is a signed quantity in two's
       * complement notation, -128 &lt;= a &lt; 128. This command changes
       * the current {@code z} spacing and moves down by
       * {@code a}.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviZ( opcode, sp, rar.readByte() );
        }
      },

      /**
       * z2 168, a[+2]: Same as {@code z1}, but {@code a} is a
       * two-byte-long parameter, -32768 &lt;= a &lt; 32768.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviZ( opcode, sp, rar.readShort() );
        }
      },

      /**
       * z3 169, a[+3]: Same as {@code z1}, but {@code a} is a
       * three-byte-long parameter, -2^23 &lt;= a &lt; 2^23.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviZ( opcode, sp, rar.readSignInt24() );
        }
      },

      /**
       * z4 170, a[+4]: Same as {@code z1}, but {@code a} is a
       * four-byte-long parameter, -2^31 &lt;= a &lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviZ( opcode, sp, rar.readInt() );
        }
      },

      /**
       * fnt_num_0 171: Set {@code f = 0}. Font 0 must previously
       * have been defined by a {@code fnt_def} instruction.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviFntNum( opcode, sp, 0 );
        }
      },

      /**
       * fnt_num_1 through fnt_num_63 (opcodes 172 to 234): Set
       * {@code f = 1}, ..., {@code f = 63}, respectively.
      */
      FNTNUM, /* 1 */FNTNUM, /* 2 */FNTNUM, /* 3 */FNTNUM, /* 4 */
      FNTNUM, /* 5 */FNTNUM, /* 6 */FNTNUM, /* 7 */FNTNUM, /* 8 */
      FNTNUM, /* 9 */FNTNUM, /* 10 */FNTNUM, /* 11 */FNTNUM, /* 12 */
      FNTNUM, /* 13 */FNTNUM, /* 14 */FNTNUM, /* 15 */FNTNUM, /* 16 */
      FNTNUM, /* 17 */FNTNUM, /* 18 */FNTNUM, /* 19 */FNTNUM, /* 20 */
      FNTNUM, /* 21 */FNTNUM, /* 22 */FNTNUM, /* 23 */FNTNUM, /* 24 */
      FNTNUM, /* 25 */FNTNUM, /* 26 */FNTNUM, /* 27 */FNTNUM, /* 28 */
      FNTNUM, /* 29 */FNTNUM, /* 30 */FNTNUM, /* 31 */FNTNUM, /* 32 */
      FNTNUM, /* 33 */FNTNUM, /* 34 */FNTNUM, /* 35 */FNTNUM, /* 36 */
      FNTNUM, /* 37 */FNTNUM, /* 38 */FNTNUM, /* 39 */FNTNUM, /* 40 */
      FNTNUM, /* 41 */FNTNUM, /* 42 */FNTNUM, /* 43 */FNTNUM, /* 44 */
      FNTNUM, /* 45 */FNTNUM, /* 46 */FNTNUM, /* 47 */FNTNUM, /* 48 */
      FNTNUM, /* 49 */FNTNUM, /* 50 */FNTNUM, /* 51 */FNTNUM, /* 52 */
      FNTNUM, /* 53 */FNTNUM, /* 54 */FNTNUM, /* 55 */FNTNUM, /* 56 */
      FNTNUM, /* 57 */FNTNUM, /* 58 */FNTNUM, /* 59 */FNTNUM, /* 60 */
      FNTNUM, /* 61 */FNTNUM, /* 62 */FNTNUM, /* 63 */

      /**
       * fnt1 235, k[1]: Set {@code f = k}. TeX82 uses this command
       * for font numbers in the range 64 &lt;= k &lt; 256.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviFntNum( opcode, sp, rar.readInt8() );
        }
      },

      /**
       * fnt2 236, k[2]: Same as {@code fnt1}, except that
       * {@code k} is two bytes long, so it is in the range 0 &lt;= k
       * &lt; 65536. TeX82 never generates this command, but large font
       * numbers may prove useful for specifications of color or texture,
       * or they may be used for special fonts that have fixed numbers in
       * some external coding scheme.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviFntNum( opcode, sp, rar.readInt16() );
        }
      },

      /**
       * fnt3 237, k[3]: Same as {@code fnt1}, except that
       * {@code k} is three bytes long, so it can be as large as
       * 2^24-1.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviFntNum( opcode, sp, rar.readInt24() );
        }
      },

      /**
       * fnt4 238, k[+4]: Same as {@code fnt1}, except that
       * {@code k} is four bytes long; this is for the really big
       * font numbers (and for the negative ones).
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          return new DviFntNum( opcode, sp, rar.readInt() );
        }
      },

      /**
       * xxx1 239, k[1] x[k]: This command is undefined in general; it
       * functions as a {@code (k+2)}-byte {@code nop} unless
       * special DVI-reading programs are being used. TeX82 generates
       * {@code xxx1} when a short enough {@code special}
       * appears, setting {@code k} to the number of bytes being
       * sent. It is recommended that $x$ be a string having the form of a
       * keyword followed by possible parameters relevant to that keyword.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt8();
          int[] values = new int[ k ];
          for( int i = 0; i < k; i++ ) {
            values[ i ] = rar.readInt8();
          }

          return new DviXXX( opcode, sp, values );
        }
      },

      /**
       * xxx2 240, k[2] x[k]: Like {@code xxx1}, but 0 &lt;= k &lt;
       * 65536.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt16();
          int[] values = new int[ k ];
          for( int i = 0; i < k; i++ ) {
            values[ i ] = rar.readInt8();
          }

          return new DviXXX( opcode, sp, values );
        }
      },

      /**
       * xxx3 241, k[3] x[k]: Like {@code xxx1}, but 0 &lt;= k &lt;
       * 2^24.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt24();
          int[] values = new int[ k ];
          for( int i = 0; i < k; i++ ) {
            values[ i ] = rar.readInt8();
          }

          return new DviXXX( opcode, sp, values );
        }
      },

      /**
       * xxx4 242, k[4] x[k]: Like {@code xxx1}, but {@code k}
       * can be ridiculously large. TeX82 uses {@code xxx4} when
       * {@code xxx1} would be incorrect.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt();
          int[] values = new int[ k ];
          for( int i = 0; i < k; i++ ) {
            values[ i ] = rar.readInt8();
          }

          return new DviXXX( opcode, sp, values );
        }
      },

      /**
       * fnt_def1 243, k[1] c[4] s[4] d[4] a[1] l[1] n[a+l]: Define font
       * {@code k}, where 0 &lt;= k &lt; 256; font definitions will
       * be explained shortly.
       * <p>
       * The four-byte value {@code c} is the check sum.
       *
       * Parameter {@code s} contains a fixed-point scale factor that
       * is applied to the character widths in font {@code k}; font
       * dimensions in TFM files and other font files are relative to this
       * quantity, which is always positive and less than 227. It is given
       * in the same units as the other dimensions of the DVI file.
       *
       * Parameter {@code d} is similar to {@code s}; it is the
       * design size, and (like {@code s}) it is given in DVI units.
       * Thus, font {@code k} is to be used at
       * {@code mag * s / (1000 * d)} times its normal size.
       *
       * The remaining part of a font definition gives the external name
       * of the font, which is an ASCII string of length {@code a} +
       * {@code l}. The number a is the length of the area or
       * directory, and {@code l} is the length of the font name
       * itself; the standard local system font area is supposed to be
       * used when a = 0. The n field contains the area in its first a
       * bytes.
       * </p>
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readByteAsInt();
          int c = rar.readInt();
          int s = rar.readInt();
          int d = rar.readInt();
          int a = rar.readByteAsInt();
          int l = rar.readByteAsInt();
          StringBuilder bufa = new StringBuilder();
          StringBuilder bufl = new StringBuilder();
          for( int i = 0; i < a; i++ ) {
            bufa.append( (char) rar.readByteAsInt() );
          }
          for( int i = 0; i < l; i++ ) {
            bufl.append( (char) rar.readByteAsInt() );
          }

          return new DviFntDef( opcode, sp, k, c, s, d,
                                bufa.toString(), bufl.toString() );
        }
      },

      /**
       * fnt_def2 244, k[2] c[4] s[4] d[4] a[1] l[1] n[a+l]: Define font
       * {@code k}, where 0 &lt;= k &lt; 65536.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt16();
          int c = rar.readInt();
          int s = rar.readInt();
          int d = rar.readInt();
          int a = rar.readByteAsInt();
          int l = rar.readByteAsInt();
          StringBuilder bufa = new StringBuilder();
          StringBuilder bufl = new StringBuilder();
          for( int i = 0; i < a; i++ ) {
            bufa.append( (char) rar.readByteAsInt() );
          }
          for( int i = 0; i < l; i++ ) {
            bufl.append( (char) rar.readByteAsInt() );
          }

          return new DviFntDef( opcode, sp, k, c, s, d,
                                bufa.toString(), bufl.toString() );
        }
      },

      /**
       * fnt_def3 245, k[3] c[4] s[4] d[4] a[1] l[1] n[a+l]: Define font
       * {@code k}, where 0 &lt;= k &lt; 2^24.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt24();
          int c = rar.readInt();
          int s = rar.readInt();
          int d = rar.readInt();
          int a = rar.readByteAsInt();
          int l = rar.readByteAsInt();
          StringBuilder bufa = new StringBuilder();
          StringBuilder bufl = new StringBuilder();
          for( int i = 0; i < a; i++ ) {
            bufa.append( (char) rar.readByteAsInt() );
          }
          for( int i = 0; i < l; i++ ) {
            bufl.append( (char) rar.readByteAsInt() );
          }

          return new DviFntDef( opcode, sp, k, c, s, d,
                                bufa.toString(), bufl.toString() );
        }
      },

      /**
       * fnt_def4 246, k[+4] c[4] s[4] d[4] a[1] l[1] n[a+l]: Define font
       * {@code k}, where -2^31 &lt;= k &lt; 2^31.
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int k = rar.readInt();
          int c = rar.readInt();
          int s = rar.readInt();
          int d = rar.readInt();
          int a = rar.readByteAsInt();
          int l = rar.readByteAsInt();
          StringBuilder bufa = new StringBuilder();
          StringBuilder bufl = new StringBuilder();
          for( int i = 0; i < a; i++ ) {
            bufa.append( (char) rar.readByteAsInt() );
          }
          for( int i = 0; i < l; i++ ) {
            bufl.append( (char) rar.readByteAsInt() );
          }

          return new DviFntDef( opcode, sp, k, c, s, d,
                                bufa.toString(), bufl.toString() );
        }
      },

      /**
       * pre 247, i[1] num[4] den[4] mag[4] k[1] x[k]: Beginning of the
       * preamble; this must come at the very beginning of the file.
       * <p>
       * The {@code i} byte identifies DVI format. The next two
       * parameters, {@code num} and {@code den}, are positive
       * integers that define the units of measurement; they are the
       * numerator and denominator of a fraction by which all dimensions
       * in the DVI file could be multiplied in order to get lengths in
       * units of 10^-7 meters. (For example, there are exactly 7227 TeX
       * points in 254 centimeters, and TeX82 works with scaled points
       * where there are 2^16 sp in a point, so TeX82 sets num=25400000
       * and den= 7227 * 2^16 =473628672.) The {@code mag} parameter
       * is what TeX82 calls {@code mag}, i.e., 1000 times the
       * desired magnification. The actual fraction by which dimensions
       * are multiplied is therefore mn/1000d. Note that if a TeX source
       * document does not call for any {@code true} dimensions, and
       * if you change it only by specifying a different {@code mag}
       * setting, the DVI file that TeX creates will be completely
       * unchanged except for the value of mag in the preamble and
       * postamble. (Fancy DVI-reading programs allow users to override
       * the {@code mag} setting when a DVI file is being printed.)
       * Finally, {@code k} and {@code x} allow the DVI writer
       * to include a comment, which is not interpreted further. The
       * length of comment {@code x} is {@code k}, where 0 &lt;=
       * k &lt; 256.
       * </p>
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int i = rar.readByteAsInt();
          int num = rar.readInt();
          int den = rar.readInt();
          int mag = rar.readInt();
          int k = rar.readByteAsInt();
          StringBuilder buf = new StringBuilder();
          for( int x = 0; x < k; x++ ) {
            buf.append( (char) rar.readByteAsInt() );
          }
          return new DviPre( opcode, sp, i, num, den, mag,
                             buf.toString() );
        }
      },

      /**
       * post 248: Beginning of the postamble.
       *
       * <pre>
       * post p[4] num[4] den[4] mag[4] l[4] u[4] s[2] t[2]
       * </pre>
       *
       * <p>
       * Here {@code p} is a pointer to the final {@code bop} in
       * the file. The next three parameters, {@code num},
       * {@code den}, and {@code mag}, are duplicates of the
       * quantities that appeared in the preamble. Parameters
       * {@code l} and {@code u} give respectively the
       * height-plus-depth of the tallest page and the width of the widest
       * page, in the same units as other dimensions of the file. These
       * numbers might be used by a DVI-reading program to position
       * individual 'pages' on large sheets of film or paper; however, the
       * standard convention for output on normal size paper is to
       * position each page so that the upper left-hand corner is exactly
       * one inch from the left and the top. Experience has shown that it
       * is unwise to design DVI-to-printer software that attempts
       * cleverly to center the output; a fixed position of the upper left
       * corner is easiest for users to understand and to work with.
       * Therefore {@code l} and {@code u} are often ignored.
       * Parameter {@code s} is the maximum stack depth (i.e., the
       * largest excess of {@code push} commands over
       * {@code pop} commands) needed to process this file. Then
       * comes {@code t}, the total number of pages ({@code bop}
       * commands) present.
       * </p>
       * <p>
       * The postamble continues with font definitions, which are any
       * number of {@code fnt_def} commands as described above,
       * possibly interspersed with {@code nop} commands. Each font
       * number that is used in the DVI file must be defined exactly
       * twice: Once before it is first selected by a {@code fnt}
       * command, and once in the postamble.
       * </p>
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int p = rar.readInt();
          int n = rar.readInt();
          int d = rar.readInt();
          int m = rar.readInt();
          int l = rar.readInt();
          int u = rar.readInt();
          int s = rar.readInt16();
          int t = rar.readInt16();

          return new DviPost( opcode, sp, p, n, d, m, l, u, s, t );
        }
      },

      /**
       * post_post 249: Ending of the postamble.
       * <p>
       * The last part of the postamble, following the
       * {@code post_post} byte that signifies the end of the font
       * definitions, contains {@code q}, a pointer to the
       * {@code post} command that started the postamble. An
       * identification byte, {@code i}, comes next; this currently
       * equals 2, as in the preamble.
       * </p>
       * <p>
       * The {@code i} byte is followed by four or more bytes that
       * are all equal to the decimal number 223 (i.e., 337 in octal). TeX
       * puts out four to seven of these trailing bytes, until the total
       * length of the file is a multiple of four bytes, since this works
       * out best on machines that pack four bytes per word; but any
       * number of 223's is allowed, as long as there are at least four of
       * them. In effect, 223 is a sort of signature that is added at the
       * very end.
       * </p>
      */
      new DviReadCommand() {

        /**
         * @see org.extex.font.format.dvi.command.DviReadCommand#read(org.extex.util.file.random.RandomAccessR,
         *      int, int)
         */
        @Override
        public DviCommand read( final RandomAccessR rar,
                                final int opcode, final int sp )
            throws IOException,
            FontException {

          int q = rar.readInt();
          int i = rar.readByteAsInt();
          while( !rar.isEOF() ) {
            rar.readByteAsInt();
          }
          return new DviPostPost( opcode, sp, q, i );
        }
      },

      /**
       * Commands 250--255 are undefined at the present time.
      */
      UNDEF, /* 250 */
      UNDEF, /* 251 */
      UNDEF, /* 252 */
      UNDEF, /* 253 */
      UNDEF, /* 254 */
      UNDEF /* 255 */
  };

  /**
   * the end marker of the dvi file
   */
  private static final int ENDMARKER = 223;

  /**
   * opcode post
   */
  private static final int POST = 248;

  /**
   * opcode pre
   */
  private static final int PRE = 247;

  /**
   * q - 4 bytes
   */
  private static final int Q_LENGTH = 4;

  /**
   * the min fnt_def opcode
   */
  private static final int MIN_FNT_DEF = 243;

  /**
   * the max fnt_def opcode
   */
  private static final int MAX_FNT_DEF = 246;

  /**
   * Read the fnt_def commands.
   * <p>
   * The post commands must read before!
   * </p>
   *
   * @param rar the input
   * @return Returns the fnt_def commands.
   * @throws IOException   if an IO-error occurs.
   * @throws FontException if a font-error occurs.
   */
  public static List<DviFntDef> getFntDefs( final RandomAccessR rar )
      throws IOException,
      FontException {

    List<DviFntDef> list = new ArrayList<DviFntDef>();

    int sp = (int) rar.getPointer();
    int opcode = rar.readByteAsInt();
    while( opcode >= MIN_FNT_DEF && opcode <= MAX_FNT_DEF ) {
      DviFntDef fntdef =
          (DviFntDef) OPCODEARRAY[ opcode ].read( rar, opcode, sp );
      list.add( fntdef );
      sp = (int) rar.getPointer();
      opcode = rar.readByteAsInt();
    }

    return list;
  }

  /**
   * Reads the next dvi command.
   *
   * @param rar the input
   * @return Returns the next command
   * @throws IOException            if an IO-error occurs.
   * @throws FontException          if a font-error occurs.
   * @throws ConfigurationException in case of an configuration error
   */
  public static DviCommand getNextCommand( final RandomAccessR rar )
      throws IOException,
      FontException,
      ConfigurationException {

    if( rar.isEOF() ) {
      return null;
    }
    int sp = (int) rar.getPointer();
    int opcode = rar.readByteAsInt();
    return OPCODEARRAY[ opcode ].read( rar, opcode, sp );
  }

  /**
   * Reads the {@code DviPost} command.
   *
   * @param rar the input
   * @return Returns the {@code DviPost} command
   * @throws IOException            if an IO-error occurs.
   * @throws FontException          if a font-error occurs.
   * @throws ConfigurationException in case of an configuration error
   */
  public static DviPost getPost( final RandomAccessR rar )
      throws IOException,
      FontException,
      ConfigurationException {

    // jump to the end and read the pointer
    rar.seek( rar.length() - 1 );
    long pos = rar.getPointer();
    while( rar.readByteAsInt() == ENDMARKER ) {
      rar.seek( --pos );
    }

    // q (4 bytes)
    pos -= Q_LENGTH;
    rar.seek( pos );
    int pointer = rar.readInt();

    // read post
    rar.seek( pointer );
    int opcode = rar.readByteAsInt();
    if( opcode != POST ) {
      throw new FontException( "dvi post not found" );// 
      // DviPostNotFoundException();
    }
    return (DviPost) OPCODEARRAY[ opcode ].read( rar, opcode, pointer );
  }

  /**
   * Reads the {@code DviPre} command.
   *
   * @param rar the input
   * @return Returns the {@code DviPre} command
   * @throws IOException            if an IO-error occurs.
   * @throws FontException          if a font-error occurs.
   * @throws ConfigurationException in case of an configuration error
   */
  public static DviPre getPre( final RandomAccessR rar )
      throws IOException,
      FontException,
      ConfigurationException {

    // read pre
    rar.seek( 0 );
    int opcode = rar.readByteAsInt();
    if( opcode != PRE ) {
      throw new FontException( "dvi pre not found" );// 
      // DviPreNotFoundException();
    }
    return (DviPre) OPCODEARRAY[ opcode ].read( rar, opcode, 0 );
  }

  /**
   * the opcode of the command
   */
  private final int opc;

  /**
   * the start pointer (in the file) of the command
   */
  private final int startpointer;

  /**
   * Create a new object.
   *
   * @param opcode the opcode
   * @param sp     the start pointer
   */
  protected DviCommand( final int opcode, final int sp ) {

    opc = opcode;
    startpointer = sp;
  }

  /**
   * Returns the name of the command.
   *
   * @return Returns the name of the command.
   */
  public abstract String getName();

  /**
   * Returns the opcode of the command.
   *
   * @return Returns the opcode of the command.
   */
  public int getOpcode() {

    return opc;
  }

  /**
   * Returns the start pointer of the command.
   *
   * @return Returns the start pointer of the command.
   */
  public int getStartPointer() {

    return startpointer;
  }

  @Override
  public String toString() {

    return getName();
  }

}
