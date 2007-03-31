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

package org.extex.font.format.xtf.cff;

import java.io.IOException;
import java.util.List;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;

/**
 * Encoding.
 * <p>
 * Note: The encoding table in a CFF font is indexed by glyph index;
 * the first encoded glyph index is 1.
 * </p>
 * <p>
 * Encoding data is located via the offset operand to the Encoding operator in the Top DICT.
 * Only one Encoding operator can be specified per font except for CIDFonts which specify no encoding.
 * A glyph's encoding is specified by a 1-byte code that permits values in the range 0-255.
 * Each encoding is described by a format-type identifier byte followed by format-specific data.
 * Two formats are currently defined.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2TDOEncoding extends T2TDONumber {

    /**
     * Create a new object.
     *
     * @param stack the stack
     * @throws IOException if an IO-error occurs.
     */
    public T2TDOEncoding(List stack) throws IOException {

        super(stack, new short[]{ENCODING});
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    public String getName() {

        return "encoding";
    }

    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset)
            throws IOException {

        int offset = getInteger();

        if (offset > 0) {
            rar.seek(baseoffset + offset);

            int format = rar.readUnsignedByte();
        }
    }

    //    /* Note: The encoding table in a CFF font is indexed by glyph index;  */
    //    /* the first encoded glyph index is 1.  Hence, we read the character  */
    //    /* code (`glyph_code') at index j and make the assignment:            */
    //    /*                                                                    */
    //    /*    encoding->codes[glyph_code] = j + 1                             */
    //    /*                                                                    */
    //    /* We also make the assignment:                                       */
    //    /*                                                                    */
    //    /*    encoding->sids[glyph_code] = charset->sids[j + 1]               */
    //    /*                                                                    */
    //    /* This gives us both a code to GID and a code to SID mapping.        */
    //
    //    if ( offset > 1 )
    //    {
    //      encoding->offset = base_offset + offset;
    //
    //      /* we need to parse the table to determine its size */
    //      if ( FT_STREAM_SEEK( encoding->offset ) ||
    //           FT_READ_BYTE( encoding->format )   ||
    //           FT_READ_BYTE( count )              )
    //        goto Exit;
    //
    //      switch ( encoding->format & 0x7F )
    //      {
    //      case 0:
    //        {
    //          FT_Byte*  p;
    //
    //
    //          /* By convention, GID 0 is always ".notdef" and is never */
    //          /* coded in the font.  Hence, the number of codes found  */
    //          /* in the table is `count+1'.                            */
    //          /*                                                       */
    //          encoding->count = count + 1;
    //
    //          if ( FT_FRAME_ENTER( count ) )
    //            goto Exit;
    //
    //          p = (FT_Byte*)stream->cursor;
    //
    //          for ( j = 1; j <= count; j++ )
    //          {
    //            glyph_code = *p++;
    //
    //            /* Make sure j is not too big. */
    //            if ( j < num_glyphs )
    //            {
    //              /* Assign code to GID mapping. */
    //              encoding->codes[glyph_code] = (FT_UShort)j;
    //
    //              /* Assign code to SID mapping. */
    //              encoding->sids[glyph_code] = charset->sids[j];
    //            }
    //          }
    //
    //          FT_FRAME_EXIT();
    //        }
    //        break;
    //
    //      case 1:
    //        {
    //          FT_UInt  nleft;
    //          FT_UInt  i = 1;
    //          FT_UInt  k;
    //
    //
    //          encoding->count = 0;
    //
    //          /* Parse the Format1 ranges. */
    //          for ( j = 0;  j < count; j++, i += nleft )
    //          {
    //            /* Read the first glyph code of the range. */
    //            if ( FT_READ_BYTE( glyph_code ) )
    //              goto Exit;
    //
    //            /* Read the number of codes in the range. */
    //            if ( FT_READ_BYTE( nleft ) )
    //              goto Exit;
    //
    //            /* Increment nleft, so we read `nleft + 1' codes/sids. */
    //            nleft++;
    //
    //            /* compute max number of character codes */
    //            if ( (FT_UInt)nleft > encoding->count )
    //              encoding->count = nleft;
    //
    //            /* Fill in the range of codes/sids. */
    //            for ( k = i; k < nleft + i; k++, glyph_code++ )
    //            {
    //              /* Make sure k is not too big. */
    //              if ( k < num_glyphs && glyph_code < 256 )
    //              {
    //                /* Assign code to GID mapping. */
    //                encoding->codes[glyph_code] = (FT_UShort)k;
    //
    //                /* Assign code to SID mapping. */
    //                encoding->sids[glyph_code] = charset->sids[k];
    //              }
    //            }
    //          }
    //
    //          /* simple check; one never knows what can be found in a font */
    //          if ( encoding->count > 256 )
    //            encoding->count = 256;
    //        }
    //        break;
    //
    //      default:
    //        FT_ERROR(( "cff_encoding_load: invalid table format!\n" ));
    //        error = CFF_Err_Invalid_File_Format;
    //        goto Exit;
    //      }
    //
    //      /* Parse supplemental encodings, if any. */
    //      if ( encoding->format & 0x80 )
    //      {
    //        FT_UInt  gindex;
    //
    //
    //        /* count supplements */
    //        if ( FT_READ_BYTE( count ) )
    //          goto Exit;
    //
    //        for ( j = 0; j < count; j++ )
    //        {
    //          /* Read supplemental glyph code. */
    //          if ( FT_READ_BYTE( glyph_code ) )
    //            goto Exit;
    //
    //          /* Read the SID associated with this glyph code. */
    //          if ( FT_READ_USHORT( glyph_sid ) )
    //            goto Exit;
    //
    //          /* Assign code to SID mapping. */
    //          encoding->sids[glyph_code] = glyph_sid;
    //
    //          /* First, look up GID which has been assigned to */
    //          /* SID glyph_sid.                                */
    //          for ( gindex = 0; gindex < num_glyphs; gindex++ )
    //          {
    //            if ( charset->sids[gindex] == glyph_sid )
    //            {
    //              encoding->codes[glyph_code] = (FT_UShort)gindex;
    //              break;
    //            }
    //          }
    //        }
    //      }
    //    }
    //    else
    //    {
    //      FT_UInt i;
    //
    //
    //      /* We take into account the fact a CFF font can use a predefined */
    //      /* encoding without containing all of the glyphs encoded by this */
    //      /* encoding (see the note at the end of section 12 in the CFF    */
    //      /* specification).                                               */
    //
    //      switch ( (FT_UInt)offset )
    //      {
    //      case 0:
    //        /* First, copy the code to SID mapping. */
    //        FT_ARRAY_COPY( encoding->sids, cff_standard_encoding, 256 );
    //        goto Populate;
    //
    //      case 1:
    //        /* First, copy the code to SID mapping. */
    //        FT_ARRAY_COPY( encoding->sids, cff_expert_encoding, 256 );
    //
    //      Populate:
    //        /* Construct code to GID mapping from code to SID mapping */
    //        /* and charset.                                           */
    //
    //        encoding->count = 0;
    //
    //        for ( j = 0; j < 256; j++ )
    //        {
    //          /* If j is encoded, find the GID for it. */
    //          if ( encoding->sids[j] )
    //          {
    //            for ( i = 1; i < num_glyphs; i++ )
    //              /* We matched, so break. */
    //              if ( charset->sids[i] == encoding->sids[j] )
    //                break;
    //
    //            /* i will be equal to num_glyphs if we exited the above */
    //            /* loop without a match.  In this case, we also have to */
    //            /* fix the code to SID mapping.                         */
    //            if ( i == num_glyphs )
    //            {
    //              encoding->codes[j] = 0;
    //              encoding->sids [j] = 0;
    //            }
    //            else
    //            {
    //              encoding->codes[j] = (FT_UShort)i;
    //
    //              /* update encoding count */
    //              if ( encoding->count < j + 1 )
    //                encoding->count = j + 1;
    //            }
    //          }
    //        }
    //        break;
    //
    //      default:
    //        FT_ERROR(( "cff_encoding_load: invalid table format!\n" ));
    //        error = CFF_Err_Invalid_File_Format;
    //        goto Exit;
    //      }
    //    }

    /**
     * The encoding sids.
     */
    private static int encodingSid[];

    private static int encodingCode[];

    public static void createDefaultEncodingTable(int numberOfGlyphs,
            int[] charsetSid) {

        // We take into account the fact a CFF font can use a predefined
        // encoding without containing all of the glyphs encoded by this
        // encoding (see the note at the end of section 12 in the CFF
        // specification).

        encodingSid = T2StandardEncoding.getSidArray();
        encodingCode = new int[encodingSid.length];

        int count = 0;
        int i = 0;
        for (int j = 0; j < 256; j++) {
            // If j is encoded, find the GID for it.
            if (encodingSid[j] == 0) {
                for (i = 1; i < numberOfGlyphs; i++) {
                    // We matched, so break.
                    if (charsetSid[i] == encodingSid[j]) {
                        break;
                    }
                }
                /* i will be equal to num_glyphs if we exited the above */
                /* loop without a match.  In this case, we also have to */
                /* fix the code to SID mapping.                         */
                if (i == numberOfGlyphs) {
                    encodingCode[j] = 0;
                    encodingSid[j] = 0;
                } else {
                    encodingCode[j] = i;

                    /* update encoding count */
                    if (count < j + 1) {
                        count = j + 1;
                    }
                }
            }
        }
    }
}