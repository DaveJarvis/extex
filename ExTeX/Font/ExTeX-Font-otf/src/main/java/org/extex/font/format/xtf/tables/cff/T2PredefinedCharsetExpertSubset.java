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

package org.extex.font.format.xtf.tables.cff;

import java.util.HashMap;
import java.util.Map;

/**
 * Predefined Charset (expert subset).
 * <p>
 * Expert subset (changes in 231 - 346)
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public final class T2PredefinedCharsetExpertSubset {

  /**
   * Predefined Charset (expert subset)
   */
  public static final String[] DATA = new String[ 347 ];

  /**
   * init the DATA
   */
  static {
    System.arraycopy( T2PredefinedCharsetExpert.DATA, 0, DATA, 0,
                      T2PredefinedCharsetExpert.DATA.length );

    DATA[ 1 ] = "space";
    DATA[ 231 ] = "dollaroldstyle";
    DATA[ 232 ] = "dollarsuperior";
    DATA[ 235 ] = "parenleftsuperior";
    DATA[ 236 ] = "parenrightsuperior";
    DATA[ 237 ] = "twodotenleader";
    DATA[ 238 ] = "onedotenleader";
    DATA[ 13 ] = "comma";
    DATA[ 14 ] = "hyphen";
    DATA[ 15 ] = "period";
    DATA[ 99 ] = "fraction";
    DATA[ 239 ] = "zerooldstyle";
    DATA[ 240 ] = "oneoldstyle";
    DATA[ 241 ] = "twooldstyle";
    DATA[ 242 ] = "threeoldstyle";
    DATA[ 243 ] = "fouroldstyle";
    DATA[ 244 ] = "fiveoldstyle";
    DATA[ 245 ] = "sixoldstyle";
    DATA[ 246 ] = "sevenoldstyle";
    DATA[ 247 ] = "eightoldstyle";
    DATA[ 248 ] = "nineoldstyle";
    DATA[ 27 ] = "colon";
    DATA[ 28 ] = "semicolon";
    DATA[ 249 ] = "commasuperior";
    DATA[ 250 ] = "threequartersemdash";
    DATA[ 251 ] = "periodsuperior";
    DATA[ 253 ] = "asuperior";
    DATA[ 254 ] = "bsuperior";
    DATA[ 255 ] = "centsuperior";
    DATA[ 256 ] = "dsuperior";
    DATA[ 257 ] = "esuperior";
    DATA[ 258 ] = "isuperior";
    DATA[ 259 ] = "lsuperior";
    DATA[ 260 ] = "msuperior";
    DATA[ 261 ] = "nsuperior";
    DATA[ 262 ] = "osuperior";
    DATA[ 263 ] = "rsuperior";
    DATA[ 264 ] = "ssuperior";
    DATA[ 265 ] = "tsuperior";
    DATA[ 266 ] = "ff";
    DATA[ 109 ] = "fi";
    DATA[ 110 ] = "fl";
    DATA[ 267 ] = "ffi";
    DATA[ 268 ] = "ffl";
    DATA[ 269 ] = "parenleftinferior";
    DATA[ 270 ] = "parenrightinferior";
    DATA[ 272 ] = "hyphensuperior";
    DATA[ 300 ] = "colonmonetary";
    DATA[ 301 ] = "onefitted";
    DATA[ 302 ] = "rupiah";
    DATA[ 305 ] = "centoldstyle";
    DATA[ 314 ] = "figuredash";
    DATA[ 315 ] = "hypheninferior";
    DATA[ 158 ] = "onequarter";
    DATA[ 155 ] = "onehalf";
    DATA[ 163 ] = "threequarters";
    DATA[ 320 ] = "oneeighth";
    DATA[ 321 ] = "threeeighths";
    DATA[ 322 ] = "fiveeighths";
    DATA[ 323 ] = "seveneighths";
    DATA[ 324 ] = "onethird";
    DATA[ 325 ] = "twothirds";
    DATA[ 326 ] = "zerosuperior";
    DATA[ 150 ] = "onesuperior";
    DATA[ 164 ] = "twosuperior";
    DATA[ 169 ] = "threesuperior";
    DATA[ 327 ] = "foursuperior";
    DATA[ 328 ] = "fivesuperior";
    DATA[ 329 ] = "sixsuperior";
    DATA[ 330 ] = "sevensuperior";
    DATA[ 331 ] = "eightsuperior";
    DATA[ 332 ] = "ninesuperior";
    DATA[ 333 ] = "zeroinferior";
    DATA[ 334 ] = "oneinferior";
    DATA[ 335 ] = "twoinferior";
    DATA[ 336 ] = "threeinferior";
    DATA[ 337 ] = "fourinferior";
    DATA[ 338 ] = "fiveinferior";
    DATA[ 339 ] = "sixinferior";
    DATA[ 340 ] = "seveninferior";
    DATA[ 341 ] = "eightinferior";
    DATA[ 342 ] = "nineinferior";
    DATA[ 343 ] = "centinferior";
    DATA[ 344 ] = "dollarinferior";
    DATA[ 345 ] = "periodinferior";
    DATA[ 346 ] = "commainferior";

  }

  /**
   * The map for the names.
   */
  private static Map<String, Integer> names = null;

  /**
   * Returns the name or '.notdef' if number out of range.
   *
   * @param sid the sid for the name
   * @return Returns the name or '.notdef' if number out of range.
   */
  public static String getName( int sid ) {

    if( sid >= 0 && sid < DATA.length ) {
      String name = DATA[ sid ];
      if( name != null ) {
        return name;
      }
    }
    return ".notdef";
  }

  /**
   * Returns the SID for a name or -1 if not found.
   *
   * @param name the name
   * @return Returns the SID for a name or -1 if not found.
   */
  public static int getSID( String name ) {

    if( names == null ) {
      names = new HashMap<String, Integer>( DATA.length );
      for( int i = 0; i < DATA.length; i++ ) {
        String key = DATA[ i ];
        names.put( key, new Integer( i ) );
      }
    }
    Integer ii = names.get( name );
    if( ii != null ) {
      return ii.intValue();
    }

    return -1;
  }

  /**
   * no instance
   */
  private T2PredefinedCharsetExpertSubset() {


  }

}
