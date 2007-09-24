/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.io.charsets;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * This is the definition of character set for the EBCDIC encoding.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EbcdicCharset extends Charset {

    /**
     * The field <tt>CODES</tt> contains the table of codes.
     */
    public static final char[] CODES = {//
            /* 00 */0,
            /* 01 */0,
            /* 02 */0,
            /* 03 */0,
            /* 04 */0,
            /* 05 */0,
            /* 06 */0,
            /* 07 */0,
            /* 08 */0,
            /* 09 */0,
            /* 0A */0,
            /* 0B */0,
            /* 0C */0x0C, // FF
                    /* 0D */0x0D, // CR
                    /* 0E */0,
                    /* 0F */0,
                    /* 10 */0,
                    /* 11 */0,
                    /* 12 */0,
                    /* 13 */0,
                    /* 14 */0,
                    /* 15 */0, // NL
                    /* 16 */0,
                    /* 17 */0,
                    /* 18 */0,
                    /* 19 */0,
                    /* 1A */0,
                    /* 1B */0,
                    /* 1C */0,
                    /* 1D */0,
                    /* 1E */0,
                    /* 1F */0,
                    /* 20 */0,
                    /* 21 */0,
                    /* 22 */0,
                    /* 23 */0,
                    /* 24 */0,
                    /* 25 */0x0A, // LF
                    /* 26 */0,
                    /* 27 */0x1B, // ESC
                    /* 28 */0,
                    /* 29 */0,
                    /* 2A */0,
                    /* 2B */0,
                    /* 2C */0,
                    /* 2D */0,
                    /* 2E */0,
                    /* 2F */0,
                    /* 30 */0,
                    /* 31 */0,
                    /* 32 */0,
                    /* 33 */0,
                    /* 34 */0,
                    /* 35 */0,
                    /* 36 */0,
                    /* 37 */0,
                    /* 38 */0,
                    /* 39 */0,
                    /* 3A */0,
                    /* 3B */0,
                    /* 3C */0,
                    /* 3D */0,
                    /* 3E */0,
                    /* 3F */0,
                    /* 40 */' ',
                    /* 41 */0,
                    /* 42 */0,
                    /* 43 */0,
                    /* 44 */0,
                    /* 45 */0,
                    /* 46 */0,
                    /* 47 */0,
                    /* 48 */0,
                    /* 49 */0,
                    /* 4A */'[',
                    /* 4B */'.',
                    /* 4C */'<',
                    /* 4D */'(',
                    /* 4E */'+',
                    /* 4F */'|',
                    /* 50 */'&',
                    /* 51 */0,
                    /* 52 */0,
                    /* 53 */0,
                    /* 54 */0,
                    /* 55 */0,
                    /* 56 */0,
                    /* 57 */0,
                    /* 58 */0,
                    /* 59 */0,
                    /* 5A */']',
                    /* 5B */'$',
                    /* 5C */'*',
                    /* 5D */')',
                    /* 5E */';',
                    /* 5F */'^',
                    /* 60 */'-',
                    /* 61 */'/',
                    /* 62 */0,
                    /* 63 */0,
                    /* 64 */0,
                    /* 65 */0,
                    /* 66 */0,
                    /* 67 */0,
                    /* 68 */0,
                    /* 69 */0,
                    /* 6A */0,
                    /* 6B */',',
                    /* 6C */'%',
                    /* 6D */'_',
                    /* 6E */'>',
                    /* 6F */'?',
                    /* 70 */0,
                    /* 71 */0,
                    /* 72 */0,
                    /* 73 */0,
                    /* 74 */0,
                    /* 75 */0,
                    /* 76 */0,
                    /* 77 */0,
                    /* 78 */0,
                    /* 79 */'`',
                    /* 7A */':',
                    /* 7B */'#',
                    /* 7C */'@',
                    /* 7D */'\'',
                    /* 7E */'=',
                    /* 7F */'"',
                    /* 80 */0,
                    /* 81 */'a',
                    /* 82 */'b',
                    /* 83 */'c',
                    /* 84 */'d',
                    /* 85 */'f',
                    /* 86 */'f',
                    /* 87 */'g',
                    /* 88 */'h',
                    /* 89 */'i',
                    /* 8A */0,
                    /* 8B */0,
                    /* 8C */0,
                    /* 8D */0,
                    /* 8E */0,
                    /* 8F */0,
                    /* 90 */0,
                    /* 91 */'j',
                    /* 92 */'k',
                    /* 93 */'l',
                    /* 94 */'m',
                    /* 95 */'n',
                    /* 96 */'o',
                    /* 97 */'p',
                    /* 98 */'q',
                    /* 99 */'r',
                    /* 9A */0,
                    /* 9B */0,
                    /* 9C */0,
                    /* 9D */0,
                    /* 9E */0,
                    /* 9F */0,
                    /* A0 */0,
                    /* A1 */'~',
                    /* A2 */'s',
                    /* A3 */'t',
                    /* A4 */'u',
                    /* A5 */'v',
                    /* A6 */'w',
                    /* A7 */'x',
                    /* A8 */'y',
                    /* A9 */'z',
                    /* AA */0,
                    /* AB */0,
                    /* AC */0,
                    /* AD */0,
                    /* AE */0,
                    /* AF */0,
                    /* B0 */0,
                    /* B1 */0,
                    /* B2 */0,
                    /* B3 */0,
                    /* B4 */0,
                    /* B5 */'§',
                    /* B6 */0,
                    /* B7 */0,
                    /* B8 */0,
                    /* B9 */0,
                    /* BA */0,
                    /* BB */0,
                    /* BC */0,
                    /* BD */0,
                    /* BE */0,
                    /* BF */0,
                    /* C0 */'{',
                    /* C1 */'A',
                    /* C2 */'B',
                    /* C3 */'C',
                    /* C4 */'D',
                    /* C5 */'E',
                    /* C6 */'F',
                    /* C7 */'G',
                    /* C8 */'H',
                    /* C9 */'I',
                    /* CA */0,
                    /* CB */0,
                    /* CC */0,
                    /* CD */0,
                    /* CE */0,
                    /* CF */0,
                    /* D0 */'}',
                    /* D1 */'J',
                    /* D2 */'K',
                    /* D3 */'L',
                    /* D4 */'M',
                    /* D5 */'N',
                    /* D6 */'O',
                    /* D7 */'P',
                    /* D8 */'Q',
                    /* D9 */'R',
                    /* DA */0,
                    /* DB */0,
                    /* DC */0,
                    /* DD */0,
                    /* DE */0,
                    /* DF */0,
                    /* E0 */'\\',
                    /* E1 */0,
                    /* E2 */'S',
                    /* E3 */'T',
                    /* E4 */'U',
                    /* E5 */'V',
                    /* E6 */'W',
                    /* E7 */'X',
                    /* E8 */'Y',
                    /* E9 */'Z',
                    /* EA */0,
                    /* EB */0,
                    /* EC */0,
                    /* ED */0,
                    /* EE */0,
                    /* EF */0,
                    /* F0 */'0',
                    /* F1 */'1',
                    /* F2 */'2',
                    /* F3 */'3',
                    /* F4 */'4',
                    /* F5 */'5',
                    /* F6 */'6',
                    /* F7 */'7',
                    /* F8 */'8',
                    /* F9 */'9',
                    /* FA */0,
                    /* FB */0,
                    /* FC */0,
                    /* FD */0,
                    /* FE */0,
                    /* FF */0};

    /**
     * The field <tt>decoder</tt> contains the decoder.
     */
    private CharsetDecoder decoder = null;

    /**
     * The field <tt>encoder</tt> contains the encoder.
     */
    private TableEncoder encoder = null;

    /**
     * Creates a new object.
     */
    public EbcdicCharset() {

        super("EBCDIC", new String[]{"EBCDIC-500", "EBCDIC-US"});
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.nio.charset.Charset#contains(java.nio.charset.Charset)
     */
    @Override
    public boolean contains(Charset cs) {

        // TODO gene: contains unimplemented
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.nio.charset.Charset#newDecoder()
     */
    @Override
    public CharsetDecoder newDecoder() {

        if (decoder == null) {
            decoder = new TableDecoder(this, CODES);
        }
        return decoder;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.nio.charset.Charset#newEncoder()
     */
    @Override
    public CharsetEncoder newEncoder() {

        if (encoder == null) {
            encoder = new TableEncoder(this, CODES);
        }
        return encoder;
    }

}
