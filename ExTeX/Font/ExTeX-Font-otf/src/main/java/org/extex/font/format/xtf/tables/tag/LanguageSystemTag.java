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

package org.extex.font.format.xtf.tables.tag;

import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Language system tags.
 *
 * <p>
 * Language system tags identify the language systems supported in a OpenType
 * Layout font.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public final class LanguageSystemTag extends Tag {

  /**
   * The map for the names.
   */
  private static final Map<String, LanguageSystemTag> map =
      new HashMap<String, LanguageSystemTag>( 400 );

  /**
   * The language system tag list.
   */
  static {
    getInstance( "ABA" ); // Abaza
    getInstance( "ABK" ); // Abkhazian
    getInstance( "ADY" ); // Adyghe
    getInstance( "AFK" ); // Afrikaans
    getInstance( "AFR" ); // Afar
    getInstance( "AGW" ); // Agaw
    getInstance( "ALT" ); // Altai
    getInstance( "AMH" ); // Amharic
    getInstance( "ARA" ); // Arabic
    getInstance( "ARI" ); // Aari
    getInstance( "ARK" ); // Arakanese
    getInstance( "ASM" ); // Assamese
    getInstance( "ATH" ); // Athapaskan
    getInstance( "AVR" ); // Avar
    getInstance( "AWA" ); // Awadhi
    getInstance( "AYM" ); // Aymara
    getInstance( "AZE" ); // Azeri
    getInstance( "BAD" ); // Badaga
    getInstance( "BAG" ); // Baghelkhandi
    getInstance( "BAL" ); // Balkar
    getInstance( "BAU" ); // Baule
    getInstance( "BBR" ); // Berber
    getInstance( "BCH" ); // Bench
    getInstance( "BCR" ); // Bible Cree
    getInstance( "BEL" ); // Belarussian
    getInstance( "BEM" ); // Bemba
    getInstance( "BEN" ); // Bengali
    getInstance( "BGR" ); // Bulgarian
    getInstance( "BHI" ); // Bhili
    getInstance( "BHO" ); // Bhojpuri
    getInstance( "BIK" ); // Bikol
    getInstance( "BIL" ); // Bilen
    getInstance( "BKF" ); // Blackfoot
    getInstance( "BLI" ); // Balochi
    getInstance( "BLN" ); // Balante
    getInstance( "BLT" ); // Balti
    getInstance( "BMB" ); // Bambara
    getInstance( "BML" ); // Bamileke
    getInstance( "BRE" ); // Breton
    getInstance( "BRH" ); // Brahui
    getInstance( "BRI" ); // Braj Bhasha
    getInstance( "BRM" ); // Burmese
    getInstance( "BSH" ); // Bashkir
    getInstance( "BTI" ); // Beti
    getInstance( "CAT" ); // Catalan
    getInstance( "CEB" ); // Cebuano
    getInstance( "CHE" ); // Chechen
    getInstance( "CHG" ); // Chaha Gurage
    getInstance( "CHH" ); // Chattisgarhi
    getInstance( "CHI" ); // Chichewa
    getInstance( "CHK" ); // Chukchi
    getInstance( "CHP" ); // Chipewyan
    getInstance( "CHR" ); // Cherokee
    getInstance( "CHU" ); // Chuvash
    getInstance( "CMR" ); // Comorian
    getInstance( "COP" ); // Coptic
    getInstance( "CRE" ); // Cree
    getInstance( "CRR" ); // Carrier
    getInstance( "CRT" ); // Crimean Tatar
    getInstance( "CSL" ); // Church Slavonic
    getInstance( "CSY" ); // Czech
    getInstance( "DAN" ); // Danish
    getInstance( "DAR" ); // Dargwa
    getInstance( "DCR" ); // Woods Cree
    getInstance( "DEU" ); // German (Standard)
    getInstance( "DGR" ); // Dogri
    getInstance( "DHV" ); // Dhivehi
    getInstance( "DJR" ); // Djerma
    getInstance( "DNG" ); // Dangme
    getInstance( "DNK" ); // Dinka
    getInstance( "DUN" ); // Dungan
    getInstance( "DZN" ); // Dzongkha
    getInstance( "EBI" ); // Ebira
    getInstance( "ECR" ); // Eastern Cree
    getInstance( "EDO" ); // Edo
    getInstance( "EFI" ); // Efik
    getInstance( "ELL" ); // Greek
    getInstance( "ENG" ); // English
    getInstance( "ERZ" ); // Erzya
    getInstance( "ESP" ); // Spanish
    getInstance( "ETI" ); // Estonian
    getInstance( "EUQ" ); // Basque
    getInstance( "EVK" ); // Evenki
    getInstance( "EVN" ); // Even
    getInstance( "EWE" ); // Ewe
    getInstance( "FAN" ); // French Antillean
    getInstance( "FAR" ); // Farsi
    getInstance( "FIN" ); // Finnish
    getInstance( "FJI" ); // Fijian
    getInstance( "FLE" ); // Flemish
    getInstance( "FNE" ); // Forest Nenets
    getInstance( "FON" ); // Fon
    getInstance( "FOS" ); // Faroese
    getInstance( "FRA" ); // French (Standard)
    getInstance( "FRI" ); // Frisian
    getInstance( "FRL" ); // Friulian
    getInstance( "FTA" ); // Futa
    getInstance( "FUL" ); // Fulani
    getInstance( "GAD" ); // Ga
    getInstance( "GAE" ); // Gaelic
    getInstance( "GAG" ); // Gagauz
    getInstance( "GAL" ); // Galician
    getInstance( "GAR" ); // Garshuni
    getInstance( "GAW" ); // Garhwali
    getInstance( "GEZ" ); // Ge'ez
    getInstance( "GIL" ); // Gilyak
    getInstance( "GMZ" ); // Gumuz
    getInstance( "GON" ); // Gondi
    getInstance( "GRN" ); // Greenlandic
    getInstance( "GRO" ); // Garo
    getInstance( "GUA" ); // Guarani
    getInstance( "GUJ" ); // Gujarati
    getInstance( "HAI" ); // Haitian
    getInstance( "HAL" ); // Halam
    getInstance( "HAR" ); // Harauti
    getInstance( "HAU" ); // Hausa
    getInstance( "HAW" ); // Hawaiin
    getInstance( "HBN" ); // Hammer-Banna
    getInstance( "HIL" ); // Hiligaynon
    getInstance( "HIN" ); // Hindi
    getInstance( "HMA" ); // High Mari
    getInstance( "HND" ); // Hindko
    getInstance( "HO" ); // Ho
    getInstance( "HRI" ); // Harari
    getInstance( "HRV" ); // Croatian
    getInstance( "HUN" ); // Hungarian
    getInstance( "HYE" ); // Armenian
    getInstance( "IBO" ); // Igbo
    getInstance( "IJO" ); // Ijo
    getInstance( "ILO" ); // Ilokano
    getInstance( "IND" ); // Indonesian
    getInstance( "ING" ); // Ingush
    getInstance( "INU" ); // Inuktitut
    getInstance( "IRI" ); // Irish
    getInstance( "IRT" ); // Irish Traditional
    getInstance( "ISL" ); // Icelandic
    getInstance( "ISM" ); // Inari Sami
    getInstance( "ITA" ); // Italian
    getInstance( "IWR" ); // Hebrew
    getInstance( "JAV" ); // Javanese
    getInstance( "JII" ); // Yiddish
    getInstance( "JAN" ); // Japanese
    getInstance( "JUD" ); // Judezmo
    getInstance( "JUL" ); // Jula
    getInstance( "KAB" ); // Kabardian
    getInstance( "KAC" ); // Kachchi
    getInstance( "KAL" ); // Kalenjin
    getInstance( "KAN" ); // Kannada
    getInstance( "KAR" ); // Karachay
    getInstance( "KAT" ); // Georgian
    getInstance( "KAZ" ); // Kazakh
    getInstance( "KEB" ); // Kebena
    getInstance( "KGE" ); // Khutsuri Georgian
    getInstance( "KHA" ); // Khakass
    getInstance( "KHK" ); // Khanty-Kazim
    getInstance( "KHM" ); // Khmer
    getInstance( "KHS" ); // Khanty-Shurishkar
    getInstance( "KHV" ); // Khanty-Vakhi
    getInstance( "KHW" ); // Khowar
    getInstance( "KIK" ); // Kikuyu
    getInstance( "KIR" ); // Kirghiz
    getInstance( "KIS" ); // Kisii
    getInstance( "KKN" ); // Kokni
    getInstance( "KLM" ); // Kalmyk
    getInstance( "KMB" ); // Kamba
    getInstance( "KMN" ); // Kumaoni
    getInstance( "KMO" ); // Komo
    getInstance( "KMS" ); // Komso
    getInstance( "KNR" ); // Kanuri
    getInstance( "KOD" ); // Kodagu
    getInstance( "KOK" ); // Konkani
    getInstance( "KON" ); // Kikongo
    getInstance( "KOP" ); // Komi-Permyak
    getInstance( "KOR" ); // Korean
    getInstance( "KOZ" ); // Komi-Zyrian
    getInstance( "KPL" ); // Kpelle
    getInstance( "KRI" ); // Krio
    getInstance( "KRK" ); // Karakalpak
    getInstance( "KRL" ); // Karelian
    getInstance( "KRM" ); // Karaim
    getInstance( "KRN" ); // Karen
    getInstance( "KRT" ); // Koorete
    getInstance( "KSH" ); // Kashmiri
    getInstance( "KSI" ); // Khasi
    getInstance( "KSM" ); // Kildin Sami
    getInstance( "KUI" ); // Kui
    getInstance( "KUL" ); // Kulvi
    getInstance( "KUM" ); // Kumyk
    getInstance( "KUR" ); // Kurdish
    getInstance( "KUU" ); // Kurukh
    getInstance( "KUY" ); // Kuy
    getInstance( "KYK" ); // Koryak
    getInstance( "LAD" ); // Ladin
    getInstance( "LAH" ); // Lahuli
    getInstance( "LAK" ); // Lak
    getInstance( "LAM" ); // Lambani
    getInstance( "LAO" ); // Lao
    getInstance( "LAT" ); // Latin
    getInstance( "LAZ" ); // Laz
    getInstance( "LCR" ); // L-Cree
    getInstance( "LDK" ); // Ladakhi
    getInstance( "LEZ" ); // Lezgi
    getInstance( "LIN" ); // Lingala
    getInstance( "LMA" ); // Low Mari
    getInstance( "LMB" ); // Limbu
    getInstance( "LMW" ); // Lomwe
    getInstance( "LSB" ); // Lower Sorbian
    getInstance( "LSM" ); // Lule Sami
    getInstance( "LTH" ); // Lithuanian
    getInstance( "LUB" ); // Luba
    getInstance( "LUG" ); // Luganda
    getInstance( "LUH" ); // Luhya
    getInstance( "LUO" ); // Luo
    getInstance( "LVI" ); // Latvian
    getInstance( "MAJ" ); // Majang
    getInstance( "MAK" ); // Makua
    getInstance( "MAL" ); // Malayalam Traditional
    getInstance( "MAN" ); // Mansi
    getInstance( "MAR" ); // Marathi
    getInstance( "MAW" ); // Marwari
    getInstance( "MBN" ); // Mbundu
    getInstance( "MCH" ); // Manchu
    getInstance( "MCR" ); // Moose Cree
    getInstance( "MDE" ); // Mende
    getInstance( "MEN" ); // Me'en
    getInstance( "MIZ" ); // Mizo
    getInstance( "MKD" ); // Macedonian
    getInstance( "MLE" ); // Male
    getInstance( "MLG" ); // Malagasy
    getInstance( "MLN" ); // Malinke
    getInstance( "MLR" ); // Malayalam Reformed
    getInstance( "MLY" ); // Malay
    getInstance( "MND" ); // Mandinka
    getInstance( "MNG" ); // Mongolian
    getInstance( "MNI" ); // Manipuri
    getInstance( "MNK" ); // Maninka
    getInstance( "MNX" ); // Manx Gaelic
    getInstance( "MOK" ); // Moksha
    getInstance( "MOL" ); // Moldavian
    getInstance( "MON" ); // Mon
    getInstance( "MOR" ); // Moroccan
    getInstance( "MRI" ); // Maori
    getInstance( "MTH" ); // Maithili
    getInstance( "MTS" ); // Maltese
    getInstance( "MUN" ); // Mundari
    getInstance( "NAG" ); // Naga-Assamese
    getInstance( "NAN" ); // Nanai
    getInstance( "NAS" ); // Naskapi
    getInstance( "NCR" ); // N-Cree
    getInstance( "NDB" ); // Ndebele
    getInstance( "NDG" ); // Ndonga
    getInstance( "NEP" ); // Nepali
    getInstance( "NEW" ); // Newari
    getInstance( "NHC" ); // Norway House Cree
    getInstance( "NIS" ); // Nisi
    getInstance( "NIU" ); // Niuean
    getInstance( "NKL" ); // Nkole
    getInstance( "NLD" ); // Dutch
    getInstance( "NOG" ); // Nogai
    getInstance( "NOR" ); // Norwegian
    getInstance( "NSM" ); // Northern Sami
    getInstance( "NTA" ); // Northern Tai
    getInstance( "NTO" ); // Esperanto
    getInstance( "NYN" ); // Nynorsk
    getInstance( "OCR" ); // Oji-Cree
    getInstance( "OJB" ); // Ojibway
    getInstance( "ORI" ); // Oriya
    getInstance( "ORO" ); // Oromo
    getInstance( "OSS" ); // Ossetian
    getInstance( "PAA" ); // Palestinian Aramaic
    getInstance( "PAL" ); // Pali
    getInstance( "PAN" ); // Punjabi
    getInstance( "PAP" ); // Palpa
    getInstance( "PAS" ); // Pashto
    getInstance( "PGR" ); // Polytonic Greek
    getInstance( "PIL" ); // Pilipino
    getInstance( "PLG" ); // Palaung
    getInstance( "PLK" ); // Polish
    getInstance( "PRO" ); // Provencal
    getInstance( "PTG" ); // Portuguese
    getInstance( "QIN" ); // Chin
    getInstance( "RAJ" ); // Rajasthani
    getInstance( "RCR" ); // R-Cree
    getInstance( "RBU" ); // Russian Buriat
    getInstance( "RIA" ); // Riang
    getInstance( "RMS" ); // Rhaeto-Romanic
    getInstance( "ROM" ); // Romanian
    getInstance( "ROY" ); // Romany
    getInstance( "RSY" ); // Rusyn
    getInstance( "RUA" ); // Ruanda
    getInstance( "RUS" ); // Russian
    getInstance( "SAD" ); // Sadri
    getInstance( "SAN" ); // Sanskrit
    getInstance( "SAT" ); // Santali
    getInstance( "SAY" ); // Sayisi
    getInstance( "SEK" ); // Sekota
    getInstance( "SEL" ); // Selkup
    getInstance( "SGO" ); // Sango
    getInstance( "SHN" ); // Shan
    getInstance( "SIB" ); // Sibe
    getInstance( "SID" ); // Sidamo
    getInstance( "SIG" ); // Silte Gurage
    getInstance( "SKS" ); // Skolt Sami
    getInstance( "SKY" ); // Slovak
    getInstance( "SLA" ); // Slavey
    getInstance( "SLV" ); // Slovenian
    getInstance( "SML" ); // Somali
    getInstance( "SMO" ); // Samoan
    getInstance( "SNA" ); // Sena
    getInstance( "SND" ); // Sindhi
    getInstance( "SNH" ); // Sinhalese
    getInstance( "SNK" ); // Soninke
    getInstance( "SOG" ); // Sodo Gurage
    getInstance( "SOT" ); // Sotho
    getInstance( "SQI" ); // Albanian
    getInstance( "SRB" ); // Serbian
    getInstance( "SRK" ); // Saraiki
    getInstance( "SRR" ); // Serer
    getInstance( "SSL" ); // South Slavey
    getInstance( "SSM" ); // Southern Sami
    getInstance( "SUR" ); // Suri
    getInstance( "SVA" ); // Svan
    getInstance( "SVE" ); // Swedish
    getInstance( "SWA" ); // Swadaya Aramaic
    getInstance( "SWK" ); // Swahili
    getInstance( "SWZ" ); // Swazi
    getInstance( "SXT" ); // Sutu
    getInstance( "SYR" ); // Syriac
    getInstance( "TAB" ); // Tabasaran
    getInstance( "TAJ" ); // Tajiki
    getInstance( "TAM" ); // Tamil
    getInstance( "TAT" ); // Tatar
    getInstance( "TCR" ); // TH-Cree
    getInstance( "TEL" ); // Telugu
    getInstance( "TGN" ); // Tongan
    getInstance( "TGR" ); // Tigre
    getInstance( "TGY" ); // Tigrinya
    getInstance( "THA" ); // Thai
    getInstance( "THT" ); // Tahitian
    getInstance( "TIB" ); // Tibetan
    getInstance( "TKM" ); // Turkmen
    getInstance( "TMN" ); // Temne
    getInstance( "TNA" ); // Tswana
    getInstance( "TNE" ); // Tundra Nenets
    getInstance( "TNG" ); // Tonga
    getInstance( "TOD" ); // Todo
    getInstance( "TRK" ); // Turkish
    getInstance( "TSG" ); // Tsonga
    getInstance( "TUA" ); // Turoyo Aramaic
    getInstance( "TUL" ); // Tulu
    getInstance( "TUV" ); // Tuvin
    getInstance( "TWI" ); // Twi
    getInstance( "UDM" ); // Udmurt
    getInstance( "UKR" ); // Ukrainian
    getInstance( "URD" ); // Urdu
    getInstance( "USB" ); // Upper Sorbian
    getInstance( "UYG" ); // Uyghur
    getInstance( "UZB" ); // Uzbek
    getInstance( "VEN" ); // Venda
    getInstance( "VIT" ); // Vietnamese
    getInstance( "WA" ); // Wa
    getInstance( "WAG" ); // Wagdi
    getInstance( "WCR" ); // West-Cree
    getInstance( "WEL" ); // Welsh
    getInstance( "WLF" ); // Wolof
    getInstance( "XHS" ); // Xhosa
    getInstance( "YAK" ); // Yakut
    getInstance( "YBA" ); // Yoruba
    getInstance( "YCR" ); // Y-Cree
    getInstance( "YIC" ); // Yi Classic
    getInstance( "YIM" ); // Yi Modern
    getInstance( "ZHP" ); // Chinese Phonetic
    getInstance( "ZHS" ); // Chinese Simplified
    getInstance( "ZHT" ); // Chinese Traditional
    getInstance( "ZND" ); // Zande
    getInstance( "ZUL" ); // Zulu

  }

  /**
   * Add a new language system tag.
   *
   * @param rar The input.
   * @return TODO
   * @throws IOException if a io-error occurred.
   */
  public static LanguageSystemTag add( RandomAccessR rar ) throws IOException {

    LanguageSystemTag lt = new LanguageSystemTag( rar );
    String name = lt.getTag();
    if( !map.containsKey( name ) ) {
      map.put( name, lt );
    }
    return lt;

  }

  /**
   * Get a new language system tag.
   *
   * @param name The name of the language system tag.
   * @return Returns the new language system tag.
   */
  public static LanguageSystemTag getInstance( String name ) {

    String xname = format( name );
    LanguageSystemTag lt = map.get( xname );
    if( lt == null ) {
      lt = new LanguageSystemTag( xname );
      map.put( xname, lt );
    }
    return lt;
  }

  /**
   * Check, if the name is in the language system tag list.
   *
   * @param name The name of the language system tag.
   * @return Returns {@code true}, if found, otherwise
   * {@code false}.
   */
  public static boolean isInList( String name ) {

    return map.containsKey( format( name ) );
  }

  /**
   * Creates a new object.
   *
   * @param rar The input.
   * @throws IOException if a io-error occurred.
   */
  private LanguageSystemTag( RandomAccessR rar ) throws IOException {

    super( rar );

  }

  /**
   * Creates a new object.
   *
   * @param name The name of the tag.
   */
  private LanguageSystemTag( String name ) {

    super( name );

  }

}
