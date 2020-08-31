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
 * Expert 229 - 378
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public final class T2PredefinedCharsetExpert {

  /**
   * Predefined Charset (expert)
   */
  public static final String[] DATA = new String[ 379 ];

  /**
   * init the DATA
   */
  static {
    System.arraycopy( T2PredefinedCharsetISOAdobe.DATA, 0, DATA, 0,
                      T2PredefinedCharsetISOAdobe.DATA.length );

    DATA[ 1 ] = "space";
    DATA[ 13 ] = "comma";
    DATA[ 14 ] = "hyphen";
    DATA[ 15 ] = "period";
    DATA[ 27 ] = "colon";
    DATA[ 28 ] = "semicolon";
    DATA[ 99 ] = "fraction";
    DATA[ 229 ] = "exclamsmall";
    DATA[ 230 ] = "Hungarumlautsmall";
    DATA[ 231 ] = "dollaroldstyle";
    DATA[ 232 ] = "dollarsuperior";
    DATA[ 233 ] = "ampersandsmall";
    DATA[ 234 ] = "Acutesmall";
    DATA[ 235 ] = "parenleftsuperior";
    DATA[ 236 ] = "parenrightsuperior";
    DATA[ 237 ] = "twodotenleader";
    DATA[ 238 ] = "onedotenleader";
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
    DATA[ 249 ] = "commasuperior";
    DATA[ 250 ] = "threequartersemdash";
    DATA[ 251 ] = "periodsuperior";
    DATA[ 252 ] = "questionsmall";
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
    DATA[ 271 ] = "Circumflexsmall";
    DATA[ 272 ] = "hyphensuperior";
    DATA[ 273 ] = "Gravesmall";
    DATA[ 274 ] = "Asmall";
    DATA[ 275 ] = "Bsmall";
    DATA[ 276 ] = "Csmall";
    DATA[ 277 ] = "Dsmall";
    DATA[ 278 ] = "Esmall";
    DATA[ 279 ] = "Fsmall";
    DATA[ 280 ] = "Gsmall";
    DATA[ 281 ] = "Hsmall";
    DATA[ 282 ] = "Ismall";
    DATA[ 283 ] = "Jsmall";
    DATA[ 284 ] = "Ksmall";
    DATA[ 285 ] = "Lsmall";
    DATA[ 286 ] = "Msmall";
    DATA[ 287 ] = "Nsmall";
    DATA[ 288 ] = "Osmall";
    DATA[ 289 ] = "Psmall";
    DATA[ 290 ] = "Qsmall";
    DATA[ 291 ] = "Rsmall";
    DATA[ 292 ] = "Ssmall";
    DATA[ 293 ] = "Tsmall";
    DATA[ 294 ] = "Usmall";
    DATA[ 295 ] = "Vsmall";
    DATA[ 296 ] = "Wsmall";
    DATA[ 297 ] = "Xsmall";
    DATA[ 298 ] = "Ysmall";
    DATA[ 299 ] = "Zsmall";
    DATA[ 300 ] = "colonmonetary";
    DATA[ 301 ] = "onefitted";
    DATA[ 302 ] = "rupiah";
    DATA[ 303 ] = "Tildesmall";
    DATA[ 304 ] = "exclamdownsmall";
    DATA[ 305 ] = "centoldstyle";
    DATA[ 306 ] = "Lslashsmall";
    DATA[ 307 ] = "Scaronsmall";
    DATA[ 308 ] = "Zcaronsmall";
    DATA[ 309 ] = "Dieresissmall";
    DATA[ 310 ] = "Brevesmall";
    DATA[ 311 ] = "Caronsmall";
    DATA[ 312 ] = "Dotaccentsmall";
    DATA[ 313 ] = "Macronsmall";
    DATA[ 314 ] = "figuredash";
    DATA[ 315 ] = "hypheninferior";
    DATA[ 316 ] = "Ogoneksmall";
    DATA[ 317 ] = "Ringsmall";
    DATA[ 318 ] = "Cedillasmall";
    DATA[ 158 ] = "onequarter";
    DATA[ 155 ] = "onehalf";
    DATA[ 163 ] = "threequarters";
    DATA[ 319 ] = "questiondownsmall";
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
    DATA[ 347 ] = "Agravesmall";
    DATA[ 348 ] = "Aacutesmall";
    DATA[ 349 ] = "Acircumflexsmall";
    DATA[ 350 ] = "Atildesmall";
    DATA[ 351 ] = "Adieresissmall";
    DATA[ 352 ] = "Aringsmall";
    DATA[ 353 ] = "AEsmall";
    DATA[ 354 ] = "Ccedillasmall";
    DATA[ 355 ] = "Egravesmall";
    DATA[ 356 ] = "Eacutesmall";
    DATA[ 357 ] = "Ecircumflexsmall";
    DATA[ 358 ] = "Edieresissmall";
    DATA[ 359 ] = "Igravesmall";
    DATA[ 360 ] = "Iacutesmall";
    DATA[ 361 ] = "Icircumflexsmall";
    DATA[ 362 ] = "Idieresissmall";
    DATA[ 363 ] = "Ethsmall";
    DATA[ 364 ] = "Ntildesmall";
    DATA[ 365 ] = "Ogravesmall";
    DATA[ 366 ] = "Oacutesmall";
    DATA[ 367 ] = "Ocircumflexsmall";
    DATA[ 368 ] = "Otildesmall";
    DATA[ 369 ] = "Odieresissmall";
    DATA[ 370 ] = "OEsmall";
    DATA[ 371 ] = "Oslashsmall";
    DATA[ 372 ] = "Ugravesmall";
    DATA[ 373 ] = "Uacutesmall";
    DATA[ 374 ] = "Ucircumflexsmall";
    DATA[ 375 ] = "Udieresissmall";
    DATA[ 376 ] = "Yacutesmall";
    DATA[ 377 ] = "Thornsmall";
    DATA[ 378 ] = "Ydieresissmall";

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
  private T2PredefinedCharsetExpert() {


  }

}
