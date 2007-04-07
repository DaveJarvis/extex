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

package org.extex.font.format.xtf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.extex.util.file.random.RandomAccessR;

/**
 * Script Tags.
 * 
 * <p>
 * Language system tags identify the language systems supported in a OpenType
 * Layout font.
 * </p>
 * <p>
 * All tags are 4-byte character strings composed of a limited set of ASCII
 * characters in the 0x20-0x7E range. If a language system tag consists of three
 * or less lowercase letters, the letters are followed by the requisite number
 * of spaces (0x20), each consisting of a single byte.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 0001 $
 */
public class LanguageSystemTag extends Tag {

    /**
     * The map for the names.
     */
    private static Map<String, String> map = null;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @throws IOException if a io-error occurred.
     */
    public LanguageSystemTag(RandomAccessR rar) throws IOException {

        super(rar);

        if (map == null) {
            map = new HashMap<String, String>(400);
            map.put("ABA", "Abaza");
            map.put("ABK", "Abkhazian");
            map.put("ADY", "Adyghe");
            map.put("AFK", "Afrikaans");
            map.put("AFR", "Afar");
            map.put("AGW", "Agaw");
            map.put("ALT", "Altai");
            map.put("AMH", "Amharic");
            map.put("ARA", "Arabic");
            map.put("ARI", "Aari");
            map.put("ARK", "Arakanese");
            map.put("ASM", "Assamese");
            map.put("ATH", "Athapaskan");
            map.put("AVR", "Avar");
            map.put("AWA", "Awadhi");
            map.put("AYM", "Aymara");
            map.put("AZE", "Azeri");
            map.put("BAD", "Badaga");
            map.put("BAG", "Baghelkhandi");
            map.put("BAL", "Balkar");
            map.put("BAU", "Baule");
            map.put("BBR", "Berber");
            map.put("BCH", "Bench");
            map.put("BCR", "Bible Cree");
            map.put("BEL", "Belarussian");
            map.put("BEM", "Bemba");
            map.put("BEN", "Bengali");
            map.put("BGR", "Bulgarian");
            map.put("BHI", "Bhili");
            map.put("BHO", "Bhojpuri");
            map.put("BIK", "Bikol");
            map.put("BIL", "Bilen");
            map.put("BKF", "Blackfoot");
            map.put("BLI", "Balochi");
            map.put("BLN", "Balante");
            map.put("BLT", "Balti");
            map.put("BMB", "Bambara");
            map.put("BML", "Bamileke");
            map.put("BRE", "Breton");
            map.put("BRH", "Brahui");
            map.put("BRI", "Braj Bhasha");
            map.put("BRM", "Burmese");
            map.put("BSH", "Bashkir");
            map.put("BTI", "Beti");
            map.put("CAT", "Catalan");
            map.put("CEB", "Cebuano");
            map.put("CHE", "Chechen");
            map.put("CHG", "Chaha Gurage");
            map.put("CHH", "Chattisgarhi");
            map.put("CHI", "Chichewa");
            map.put("CHK", "Chukchi");
            map.put("CHP", "Chipewyan");
            map.put("CHR", "Cherokee");
            map.put("CHU", "Chuvash");
            map.put("CMR", "Comorian");
            map.put("COP", "Coptic");
            map.put("CRE", "Cree");
            map.put("CRR", "Carrier");
            map.put("CRT", "Crimean Tatar");
            map.put("CSL", "Church Slavonic");
            map.put("CSY", "Czech");
            map.put("DAN", "Danish");
            map.put("DAR", "Dargwa");
            map.put("DCR", "Woods Cree");
            map.put("DEU", "German (Standard)");
            map.put("DGR", "Dogri");
            map.put("DHV", "Dhivehi");
            map.put("DJR", "Djerma");
            map.put("DNG", "Dangme");
            map.put("DNK", "Dinka");
            map.put("DUN", "Dungan");
            map.put("DZN", "Dzongkha");
            map.put("EBI", "Ebira");
            map.put("ECR", "Eastern Cree");
            map.put("EDO", "Edo");
            map.put("EFI", "Efik");
            map.put("ELL", "Greek");
            map.put("ENG", "English");
            map.put("ERZ", "Erzya");
            map.put("ESP", "Spanish");
            map.put("ETI", "Estonian");
            map.put("EUQ", "Basque");
            map.put("EVK", "Evenki");
            map.put("EVN", "Even");
            map.put("EWE", "Ewe");
            map.put("FAN", "French Antillean");
            map.put("FAR", "Farsi");
            map.put("FIN", "Finnish");
            map.put("FJI", "Fijian");
            map.put("FLE", "Flemish");
            map.put("FNE", "Forest Nenets");
            map.put("FON", "Fon");
            map.put("FOS", "Faroese");
            map.put("FRA", "French (Standard)");
            map.put("FRI", "Frisian");
            map.put("FRL", "Friulian");
            map.put("FTA", "Futa");
            map.put("FUL", "Fulani");
            map.put("GAD", "Ga");
            map.put("GAE", "Gaelic");
            map.put("GAG", "Gagauz");
            map.put("GAL", "Galician");
            map.put("GAR", "Garshuni");
            map.put("GAW", "Garhwali");
            map.put("GEZ", "Ge'ez");
            map.put("GIL", "Gilyak");
            map.put("GMZ", "Gumuz");
            map.put("GON", "Gondi");
            map.put("GRN", "Greenlandic");
            map.put("GRO", "Garo");
            map.put("GUA", "Guarani");
            map.put("GUJ", "Gujarati");
            map.put("HAI", "Haitian");
            map.put("HAL", "Halam");
            map.put("HAR", "Harauti");
            map.put("HAU", "Hausa");
            map.put("HAW", "Hawaiin");
            map.put("HBN", "Hammer-Banna");
            map.put("HIL", "Hiligaynon");
            map.put("HIN", "Hindi");
            map.put("HMA", "High Mari");
            map.put("HND", "Hindko");
            map.put("HO", "Ho");
            map.put("HRI", "Harari");
            map.put("HRV", "Croatian");
            map.put("HUN", "Hungarian");
            map.put("HYE", "Armenian");
            map.put("IBO", "Igbo");
            map.put("IJO", "Ijo");
            map.put("ILO", "Ilokano");
            map.put("IND", "Indonesian");
            map.put("ING", "Ingush");
            map.put("INU", "Inuktitut");
            map.put("IRI", "Irish");
            map.put("IRT", "Irish Traditional");
            map.put("ISL", "Icelandic");
            map.put("ISM", "Inari Sami");
            map.put("ITA", "Italian");
            map.put("IWR", "Hebrew");
            map.put("JAV", "Javanese");
            map.put("JII", "Yiddish");
            map.put("JAN", "Japanese");
            map.put("JUD", "Judezmo");
            map.put("JUL", "Jula");
            map.put("KAB", "Kabardian");
            map.put("KAC", "Kachchi");
            map.put("KAL", "Kalenjin");
            map.put("KAN", "Kannada");
            map.put("KAR", "Karachay");
            map.put("KAT", "Georgian");
            map.put("KAZ", "Kazakh");
            map.put("KEB", "Kebena");
            map.put("KGE", "Khutsuri Georgian");
            map.put("KHA", "Khakass");
            map.put("KHK", "Khanty-Kazim");
            map.put("KHM", "Khmer");
            map.put("KHS", "Khanty-Shurishkar");
            map.put("KHV", "Khanty-Vakhi");
            map.put("KHW", "Khowar");
            map.put("KIK", "Kikuyu");
            map.put("KIR", "Kirghiz");
            map.put("KIS", "Kisii");
            map.put("KKN", "Kokni");
            map.put("KLM", "Kalmyk");
            map.put("KMB", "Kamba");
            map.put("KMN", "Kumaoni");
            map.put("KMO", "Komo");
            map.put("KMS", "Komso");
            map.put("KNR", "Kanuri");
            map.put("KOD", "Kodagu");
            map.put("KOK", "Konkani");
            map.put("KON", "Kikongo");
            map.put("KOP", "Komi-Permyak");
            map.put("KOR", "Korean");
            map.put("KOZ", "Komi-Zyrian");
            map.put("KPL", "Kpelle");
            map.put("KRI", "Krio");
            map.put("KRK", "Karakalpak");
            map.put("KRL", "Karelian");
            map.put("KRM", "Karaim");
            map.put("KRN", "Karen");
            map.put("KRT", "Koorete");
            map.put("KSH", "Kashmiri");
            map.put("KSI", "Khasi");
            map.put("KSM", "Kildin Sami");
            map.put("KUI", "Kui");
            map.put("KUL", "Kulvi");
            map.put("KUM", "Kumyk");
            map.put("KUR", "Kurdish");
            map.put("KUU", "Kurukh");
            map.put("KUY", "Kuy");
            map.put("KYK", "Koryak");
            map.put("LAD", "Ladin");
            map.put("LAH", "Lahuli");
            map.put("LAK", "Lak");
            map.put("LAM", "Lambani");
            map.put("LAO", "Lao");
            map.put("LAT", "Latin");
            map.put("LAZ", "Laz");
            map.put("LCR", "L-Cree");
            map.put("LDK", "Ladakhi");
            map.put("LEZ", "Lezgi");
            map.put("LIN", "Lingala");
            map.put("LMA", "Low Mari");
            map.put("LMB", "Limbu");
            map.put("LMW", "Lomwe");
            map.put("LSB", "Lower Sorbian");
            map.put("LSM", "Lule Sami");
            map.put("LTH", "Lithuanian");
            map.put("LUB", "Luba");
            map.put("LUG", "Luganda");
            map.put("LUH", "Luhya");
            map.put("LUO", "Luo");
            map.put("LVI", "Latvian");
            map.put("MAJ", "Majang");
            map.put("MAK", "Makua");
            map.put("MAL", "Malayalam Traditional");
            map.put("MAN", "Mansi");
            map.put("MAR", "Marathi");
            map.put("MAW", "Marwari");
            map.put("MBN", "Mbundu");
            map.put("MCH", "Manchu");
            map.put("MCR", "Moose Cree");
            map.put("MDE", "Mende");
            map.put("MEN", "Me'en");
            map.put("MIZ", "Mizo");
            map.put("MKD", "Macedonian");
            map.put("MLE", "Male");
            map.put("MLG", "Malagasy");
            map.put("MLN", "Malinke");
            map.put("MLR", "Malayalam Reformed");
            map.put("MLY", "Malay");
            map.put("MND", "Mandinka");
            map.put("MNG", "Mongolian");
            map.put("MNI", "Manipuri");
            map.put("MNK", "Maninka");
            map.put("MNX", "Manx Gaelic");
            map.put("MOK", "Moksha");
            map.put("MOL", "Moldavian");
            map.put("MON", "Mon");
            map.put("MOR", "Moroccan");
            map.put("MRI", "Maori");
            map.put("MTH", "Maithili");
            map.put("MTS", "Maltese");
            map.put("MUN", "Mundari");
            map.put("NAG", "Naga-Assamese");
            map.put("NAN", "Nanai");
            map.put("NAS", "Naskapi");
            map.put("NCR", "N-Cree");
            map.put("NDB", "Ndebele");
            map.put("NDG", "Ndonga");
            map.put("NEP", "Nepali");
            map.put("NEW", "Newari");
            map.put("NHC", "Norway House Cree");
            map.put("NIS", "Nisi");
            map.put("NIU", "Niuean");
            map.put("NKL", "Nkole");
            map.put("NLD", "Dutch");
            map.put("NOG", "Nogai");
            map.put("NOR", "Norwegian");
            map.put("NSM", "Northern Sami");
            map.put("NTA", "Northern Tai");
            map.put("NTO", "Esperanto");
            map.put("NYN", "Nynorsk");
            map.put("OCR", "Oji-Cree");
            map.put("OJB", "Ojibway");
            map.put("ORI", "Oriya");
            map.put("ORO", "Oromo");
            map.put("OSS", "Ossetian");
            map.put("PAA", "Palestinian Aramaic");
            map.put("PAL", "Pali");
            map.put("PAN", "Punjabi");
            map.put("PAP", "Palpa");
            map.put("PAS", "Pashto");
            map.put("PGR", "Polytonic Greek");
            map.put("PIL", "Pilipino");
            map.put("PLG", "Palaung");
            map.put("PLK", "Polish");
            map.put("PRO", "Provencal");
            map.put("PTG", "Portuguese");
            map.put("QIN", "Chin");
            map.put("RAJ", "Rajasthani");
            map.put("RCR", "R-Cree");
            map.put("RBU", "Russian Buriat");
            map.put("RIA", "Riang");
            map.put("RMS", "Rhaeto-Romanic");
            map.put("ROM", "Romanian");
            map.put("ROY", "Romany");
            map.put("RSY", "Rusyn");
            map.put("RUA", "Ruanda");
            map.put("RUS", "Russian");
            map.put("SAD", "Sadri");
            map.put("SAN", "Sanskrit");
            map.put("SAT", "Santali");
            map.put("SAY", "Sayisi");
            map.put("SEK", "Sekota");
            map.put("SEL", "Selkup");
            map.put("SGO", "Sango");
            map.put("SHN", "Shan");
            map.put("SIB", "Sibe");
            map.put("SID", "Sidamo");
            map.put("SIG", "Silte Gurage");
            map.put("SKS", "Skolt Sami");
            map.put("SKY", "Slovak");
            map.put("SLA", "Slavey");
            map.put("SLV", "Slovenian");
            map.put("SML", "Somali");
            map.put("SMO", "Samoan");
            map.put("SNA", "Sena");
            map.put("SND", "Sindhi");
            map.put("SNH", "Sinhalese");
            map.put("SNK", "Soninke");
            map.put("SOG", "Sodo Gurage");
            map.put("SOT", "Sotho");
            map.put("SQI", "Albanian");
            map.put("SRB", "Serbian");
            map.put("SRK", "Saraiki");
            map.put("SRR", "Serer");
            map.put("SSL", "South Slavey");
            map.put("SSM", "Southern Sami");
            map.put("SUR", "Suri");
            map.put("SVA", "Svan");
            map.put("SVE", "Swedish");
            map.put("SWA", "Swadaya Aramaic");
            map.put("SWK", "Swahili");
            map.put("SWZ", "Swazi");
            map.put("SXT", "Sutu");
            map.put("SYR", "Syriac");
            map.put("TAB", "Tabasaran");
            map.put("TAJ", "Tajiki");
            map.put("TAM", "Tamil");
            map.put("TAT", "Tatar");
            map.put("TCR", "TH-Cree");
            map.put("TEL", "Telugu");
            map.put("TGN", "Tongan");
            map.put("TGR", "Tigre");
            map.put("TGY", "Tigrinya");
            map.put("THA", "Thai");
            map.put("THT", "Tahitian");
            map.put("TIB", "Tibetan");
            map.put("TKM", "Turkmen");
            map.put("TMN", "Temne");
            map.put("TNA", "Tswana");
            map.put("TNE", "Tundra Nenets");
            map.put("TNG", "Tonga");
            map.put("TOD", "Todo");
            map.put("TRK", "Turkish");
            map.put("TSG", "Tsonga");
            map.put("TUA", "Turoyo Aramaic");
            map.put("TUL", "Tulu");
            map.put("TUV", "Tuvin");
            map.put("TWI", "Twi");
            map.put("UDM", "Udmurt");
            map.put("UKR", "Ukrainian");
            map.put("URD", "Urdu");
            map.put("USB", "Upper Sorbian");
            map.put("UYG", "Uyghur");
            map.put("UZB", "Uzbek");
            map.put("VEN", "Venda");
            map.put("VIT", "Vietnamese");
            map.put("WA", "Wa");
            map.put("WAG", "Wagdi");
            map.put("WCR", "West-Cree");
            map.put("WEL", "Welsh");
            map.put("WLF", "Wolof");
            map.put("XHS", "Xhosa");
            map.put("YAK", "Yakut");
            map.put("YBA", "Yoruba");
            map.put("YCR", "Y-Cree");
            map.put("YIC", "Yi Classic");
            map.put("YIM", "Yi Modern");
            map.put("ZHP", "Chinese Phonetic");
            map.put("ZHS", "Chinese Simplified");
            map.put("ZHT", "Chinese Traditional");
            map.put("ZND", "Zande");
            map.put("ZUL", "Zulu");

        }
    }

    /**
     * Returns the name of the tag.
     * 
     * @return Returns the name of the tag.
     */
    public String getName() {

        String name = map.get(toString().trim());
        if (name != null) {
            return name;
        }
        return "????";

    }

}
