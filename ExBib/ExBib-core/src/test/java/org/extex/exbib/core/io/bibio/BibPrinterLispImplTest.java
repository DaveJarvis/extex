/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.io.StringBufferWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a test suite for {@link BibPrinterImpl}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibPrinterLispImplTest {

  /**
   * The field {@code RESULT_1} contains the result for test case 1.
   */
  private static final String RESULT_1 =
      "(database\n"
          + "  (preamble \"\\newcommand{\\\\noopsort}[1]{} \" "
          + "\"\\newcommand{\\\\printfirst}[2]{#1} \" "
          + "\"\\newcommand{\\\\singleletter}[1]{#1} \" "
          + "\"\\newcommand{\\\\switchargs}[2]{#2#1} \")\n"
          + "\n"
          + "  (string 'acm \"The OX Association for Computing " +
          "Machinery\")\n"
          + "  (string 'stoc \" Symposium on the Theory of " +
          "Computing\")\n"
          + "  (string 'stoc-key \"OX{\\\\singleletter{stoc}}\")\n"
          + "\n"
          + "  (entry 'article \"article-minimal\"\n"
          + "    (field 'author \"L[eslie] A. Aamport\")\n"
          + "    (field 'title \"The Gnats and Gnus Document " +
          "Preparation System\")\n"
          + "    (field 'journal \"\\mbox{G-Animal's} Journal\")\n"
          + "    (field 'year 1986)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'article \"article-full\"\n"
          + "    (field 'author \"L[eslie] A. Aamport\")\n"
          + "    (field 'title \"The Gnats and Gnus Document " +
          "Preparation System\")\n"
          + "    (field 'journal \"\\mbox{G-Animal's} Journal\")\n"
          + "    (field 'year 1986)\n"
          + "    (field 'volume 41)\n"
          + "    (field 'number 7)\n"
          + "    (field 'pages \"73+\")\n"
          + "    (field 'month 'jul )\n"
          + "    (field 'note \"This is a full ARTICLE entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'article \"article-crossref\"\n"
          + "    (field 'crossref \"WHOLE-JOURNAL\")\n"
          + "    (field 'key \"\")\n"
          + "    (field 'author \"L[eslie] A. Aamport\")\n"
          + "    (field 'title \"The Gnats and Gnus Document " +
          "Preparation System\")\n"
          + "    (field 'pages \"73+\")\n"
          + "    (field 'note \"This is a cross-referencing ARTICLE" +
          " entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'article \"whole-journal\"\n"
          + "    (field 'key \"GAJ\")\n"
          + "    (field 'journal \"\\mbox{G-Animal's} Journal\")\n"
          + "    (field 'year 1986)\n"
          + "    (field 'volume 41)\n"
          + "    (field 'number 7)\n"
          + "    (field 'month 'jul )\n"
          + "    (field 'note \"The entire issue is devoted to " +
          "gnats and gnus"
          + "\t\t(this entry is a cross-referenced ARTICLE (journal))\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'inbook \"inbook-minimal\"\n"
          + "    (field 'author \"Donald E. Knuth\")\n"
          + "    (field 'title \"Fundamental Algorithms\")\n"
          + "    (field 'publisher \"Addison-Wesley\")\n"
          + "    (field 'year \"{\\\\noopsort{1973b}}1973\")\n"
          + "    (field 'chapter \"1.2\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'inbook \"inbook-full\"\n"
          + "    (field 'author \"Donald E. Knuth\")\n"
          + "    (field 'title \"Fundamental Algorithms\")\n"
          + "    (field 'volume 1)\n"
          + "    (field 'series \"The Art of Computer " +
          "Programming\")\n"
          + "    (field 'publisher \"Addison-Wesley\")\n"
          + "    (field 'address \"Reading, Massachusetts\")\n"
          + "    (field 'edition \"Second\")\n"
          + "    (field 'month \"10~\" 'jan )\n"
          + "    (field 'year \"{\\\\noopsort{1973b}}1973\")\n"
          + "    (field 'type \"Section\")\n"
          + "    (field 'chapter \"1.2\")\n"
          + "    (field 'pages \"10--119\")\n"
          + "    (field 'note \"This is a full INBOOK entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'inbook \"inbook-crossref\"\n"
          + "    (field 'crossref \"whole-set\")\n"
          + "    (field 'title \"Fundamental Algorithms\")\n"
          + "    (field 'volume 1)\n"
          + "    (field 'series \"The Art of Computer " +
          "Programming\")\n"
          + "    (field 'edition \"Second\")\n"
          + "    (field 'year \"{\\\\noopsort{1973b}}1973\")\n"
          + "    (field 'type \"Section\")\n"
          + "    (field 'chapter \"1.2\")\n"
          + "    (field 'note \"This is a cross-referencing INBOOK " +
          "entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'book \"book-minimal\"\n"
          + "    (field 'author \"Donald E. Knuth\")\n"
          + "    (field 'title \"Seminumerical Algorithms\")\n"
          + "    (field 'publisher \"Addison-Wesley\")\n"
          + "    (field 'year \"{\\\\noopsort{1973c}}1981\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'book \"book-full\"\n"
          + "    (field 'author \"Donald E. Knuth\")\n"
          + "    (field 'title \"Seminumerical Algorithms\")\n"
          + "    (field 'volume 2)\n"
          + "    (field 'series \"The Art of Computer " +
          "Programming\")\n"
          + "    (field 'publisher \"Addison-Wesley\")\n"
          + "    (field 'address \"Reading, Massachusetts\")\n"
          + "    (field 'edition \"Second\")\n"
          + "    (field 'month \"10~\" 'jan )\n"
          + "    (field 'year \"{\\\\noopsort{1973c}}1981\")\n"
          + "    (field 'note \"This is a full BOOK entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'book \"book-crossref\"\n"
          + "    (field 'crossref \"whole-set\")\n"
          + "    (field 'title \"Seminumerical Algorithms\")\n"
          + "    (field 'volume 2)\n"
          + "    (field 'series \"The Art of Computer " +
          "Programming\")\n"
          + "    (field 'edition \"Second\")\n"
          + "    (field 'year \"{\\\\noopsort{1973c}}1981\")\n"
          + "    (field 'note \"This is a cross-referencing BOOK " +
          "entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'book \"whole-set\"\n"
          + "    (field 'author \"Donald E. Knuth\")\n"
          + "    (field 'publisher \"Addison-Wesley\")\n"
          + "    (field 'title \"The Art of Computer Programming\")\n"
          + "    (field 'series \"Four volumes\")\n"
          + "    (field 'year " +
          "\"{\\\\noopsort{1973a}}{\\\\switchargs{--90}{1968}}\")\n"
          + "    (field 'note \"Seven volumes planned (this is a " +
          "cross-referenced set of BOOKs)\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'booklet \"booklet-minimal\"\n"
          + "    (field 'key \"Kn{\\\\printfirst{v}{1987}}\")\n"
          + "    (field 'title \"The Programming of Computer Art\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'booklet \"booklet-full\"\n"
          + "    (field 'author \"Jill C. Knvth\")\n"
          + "    (field 'title \"The Programming of Computer Art\")\n"
          + "    (field 'howpublished \"Vernier Art Center\")\n"
          + "    (field 'address \"Stanford, California\")\n"
          + "    (field 'month 'feb )\n"
          + "    (field 'year 1988)\n"
          + "    (field 'note \"This is a full BOOKLET entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'incollection \"incollection-minimal\"\n"
          + "    (field 'author \"Daniel D. Lincoll\")\n"
          + "    (field 'title \"Semigroups of Recurrences\")\n"
          + "    (field 'booktitle \"High Speed Computer and " +
          "Algorithm Organization\")\n"
          + "    (field 'publisher \"Academic Press\")\n"
          + "    (field 'year 1977)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'incollection \"incollection-full\"\n"
          + "    (field 'author \"Daniel D. Lincoll\")\n"
          + "    (field 'title \"Semigroups of Recurrences\")\n"
          + "    (field 'editor \"David J. Lipcoll and D. H. Lawrie" +
          " and A. H. Sameh\")\n"
          + "    (field 'booktitle \"High Speed Computer and " +
          "Algorithm Organization\")\n"
          + "    (field 'number 23)\n"
          + "    (field 'series \"Fast Computers\")\n"
          + "    (field 'chapter 3)\n"
          + "    (field 'type \"Part\")\n"
          + "    (field 'pages \"179--183\")\n"
          + "    (field 'publisher \"Academic Press\")\n"
          + "    (field 'address \"New York\")\n"
          + "    (field 'edition \"Third\")\n"
          + "    (field 'month 'sep )\n"
          + "    (field 'year 1977)\n"
          + "    (field 'note \"This is a full INCOLLECTION " +
          "entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'incollection \"incollection-crossref\"\n"
          + "    (field 'crossref \"whole-collection\")\n"
          + "    (field 'author \"Daniel D. Lincoll\")\n"
          + "    (field 'title \"Semigroups of Recurrences\")\n"
          + "    (field 'pages \"179--183\")\n"
          + "    (field 'note \"This is a cross-referencing " +
          "INCOLLECTION entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'book \"whole-collection\"\n"
          + "    (field 'editor \"David J. Lipcoll and D. H. Lawrie" +
          " and A. H. Sameh\")\n"
          + "    (field 'title \"High Speed Computer and Algorithm " +
          "Organization\")\n"
          + "    (field 'booktitle \"High Speed Computer and " +
          "Algorithm Organization\")\n"
          + "    (field 'number 23)\n"
          + "    (field 'series \"Fast Computers\")\n"
          + "    (field 'publisher \"Academic Press\")\n"
          + "    (field 'address \"New York\")\n"
          + "    (field 'edition \"Third\")\n"
          + "    (field 'month 'sep )\n"
          + "    (field 'year 1977)\n"
          + "    (field 'note \"This is a cross-referenced BOOK " +
          "(collection) entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'manual \"manual-minimal\"\n"
          + "    (field 'key \"Manmaker\")\n"
          + "    (field 'title \"The Definitive Computer Manual\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'manual \"manual-full\"\n"
          + "    (field 'author \"Larry Manmaker\")\n"
          + "    (field 'title \"The Definitive Computer Manual\")\n"
          + "    (field 'organization \"Chips-R-Us\")\n"
          + "    (field 'address \"Silicon Valley\")\n"
          + "    (field 'edition \"Silver\")\n"
          + "    (field 'month 'apr  \"-\" 'may )\n"
          + "    (field 'year 1986)\n"
          + "    (field 'note \"This is a full MANUAL entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'mastersthesis \"mastersthesis-minimal\"\n"
          + "    (field 'author \"{\\\\'{E}}douard Masterly\")\n"
          + "    (field 'title \"Mastering Thesis Writing\")\n"
          + "    (field 'school \"Stanford University\")\n"
          + "    (field 'year 1988)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'mastersthesis \"mastersthesis-full\"\n"
          + "    (field 'author \"{\\\\'{E}}douard Masterly\")\n"
          + "    (field 'title \"Mastering Thesis Writing\")\n"
          + "    (field 'school \"Stanford University\")\n"
          + "    (field 'type \"Master's project\")\n"
          + "    (field 'address \"English Department\")\n"
          + "    (field 'month 'jun  \"-\" 'aug )\n"
          + "    (field 'year 1988)\n"
          + "    (field 'note \"This is a full MASTERSTHESIS " +
          "entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'misc \"misc-minimal\"\n"
          + "    (field 'key \"Missilany\")\n"
          + "    (field 'note \"This is a minimal MISC entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'misc \"misc-full\"\n"
          + "    (field 'author \"Joe-Bob Missilany\")\n"
          + "    (field 'title \"Handing out random pamphlets in " +
          "airports\")\n"
          + "    (field 'howpublished \"Handed out at O'Hare\")\n"
          + "    (field 'month 'oct )\n"
          + "    (field 'year 1984)\n"
          + "    (field 'note \"This is a full MISC entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'inproceedings \"inproceedings-minimal\"\n"
          + "    (field 'author \"Alfred V. Oaho and Jeffrey D. " +
          "Ullman and Mihalis Yannakakis\")\n"
          + "    (field 'title \"On Notions of Information Transfer" +
          " in {VLSI} Circuits\")\n"
          + "    (field 'booktitle \"Proc. Fifteenth Annual ACM\" " +
          "'stoc )\n"
          + "    (field 'year 1983)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'inproceedings \"inproceedings-full\"\n"
          + "    (field 'author \"Alfred V. Oaho and Jeffrey D. " +
          "Ullman and Mihalis Yannakakis\")\n"
          + "    (field 'title \"On Notions of Information Transfer" +
          " in {VLSI} Circuits\")\n"
          + "    (field 'editor \"Wizard V. Oz and Mihalis " +
          "Yannakakis\")\n"
          + "    (field 'booktitle \"Proc. Fifteenth Annual ACM\" " +
          "'stoc )\n"
          + "    (field 'number 17)\n"
          + "    (field 'series \"All ACM Conferences\")\n"
          + "    (field 'pages \"133--139\")\n"
          + "    (field 'month 'mar )\n"
          + "    (field 'year 1983)\n"
          + "    (field 'address \"Boston\")\n"
          + "    (field 'organization 'acm )\n"
          + "    (field 'publisher \"Academic Press\")\n"
          + "    (field 'note \"This is a full INPROCEDINGS " +
          "entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'inproceedings \"inproceedings-crossref\"\n"
          + "    (field 'crossref \"whole-proceedings\")\n"
          + "    (field 'author \"Alfred V. Oaho and Jeffrey D. " +
          "Ullman and Mihalis Yannakakis\")\n"
          + "    (field 'title \"On Notions of Information Transfer" +
          " in {VLSI} Circuits\")\n"
          + "    (field 'organization \"\")\n"
          + "    (field 'pages \"133--139\")\n"
          + "    (field 'note \"This is a cross-referencing " +
          "INPROCEEDINGS entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'proceedings \"proceedings-minimal\"\n"
          + "    (field 'key 'stoc-key )\n"
          + "    (field 'title \"Proc. Fifteenth Annual\" 'stoc )\n"
          + "    (field 'year 1983)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'proceedings \"proceedings-full\"\n"
          + "    (field 'editor \"Wizard V. Oz and Mihalis " +
          "Yannakakis\")\n"
          + "    (field 'title \"Proc. Fifteenth Annual\" 'stoc )\n"
          + "    (field 'number 17)\n"
          + "    (field 'series \"All ACM Conferences\")\n"
          + "    (field 'month 'mar )\n"
          + "    (field 'year 1983)\n"
          + "    (field 'address \"Boston\")\n"
          + "    (field 'organization 'acm )\n"
          + "    (field 'publisher \"Academic Press\")\n"
          + "    (field 'note \"This is a full PROCEEDINGS entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'proceedings \"whole-proceedings\"\n"
          + "    (field 'key 'stoc-key )\n"
          + "    (field 'organization 'acm )\n"
          + "    (field 'title \"Proc. Fifteenth Annual\" 'stoc )\n"
          + "    (field 'address \"Boston\")\n"
          + "    (field 'year 1983)\n"
          + "    (field 'booktitle \"Proc. Fifteenth Annual ACM\" " +
          "'stoc )\n"
          + "    (field 'note \"This is a cross-referenced " +
          "PROCEEDINGS\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'phdthesis \"phdthesis-minimal\"\n"
          + "    (field 'author \"F. Phidias Phony-Baloney\")\n"
          + "    (field 'title \"Fighting Fire with Fire: " +
          "Festooning {F}rench Phrases\")\n"
          + "    (field 'school \"Fanstord University\")\n"
          + "    (field 'year 1988)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'phdthesis \"phdthesis-full\"\n"
          + "    (field 'author \"F. Phidias Phony-Baloney\")\n"
          + "    (field 'title \"Fighting Fire with Fire: " +
          "Festooning {F}rench Phrases\")\n"
          + "    (field 'school \"Fanstord University\")\n"
          + "    (field 'type \"{PhD} Dissertation\")\n"
          + "    (field 'address \"Department of French\")\n"
          + "    (field 'month 'jun  \"-\" 'aug )\n"
          + "    (field 'year 1988)\n"
          + "    (field 'note \"This is a full PHDTHESIS entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'techreport \"techreport-minimal\"\n"
          + "    (field 'author \"Tom Terrific\")\n"
          + "    (field 'title \"An {$O(n \\\\log n / \\\\! " +
          "\\\\log\\\\log n)$} Sorting Algorithm\")\n"
          + "    (field 'institution \"Fanstord University\")\n"
          + "    (field 'year 1988)\n"
          + "  )\n"
          + "\n"
          + "  (entry 'techreport \"techreport-full\"\n"
          + "    (field 'author \"Tom T{\\\\'{e}}rrific\")\n"
          + "    (field 'title \"An {$O(n \\\\log n / \\\\! " +
          "\\\\log\\\\log n)$} Sorting Algorithm\")\n"
          + "    (field 'institution \"Fanstord University\")\n"
          + "    (field 'type \"Wishful Research Result\")\n"
          + "    (field 'number \"7\")\n"
          + "    (field 'address \"Computer Science Department, " +
          "Fanstord, California\")\n"
          + "    (field 'month 'oct )\n"
          + "    (field 'year 1988)\n"
          + "    (field 'note \"This is a full TECHREPORT entry\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'unpublished \"unpublished-minimal\"\n"
          + "    (field 'author \"Ulrich {\\\\\\\"{U}}nderwood and " +
          "Ned {\\\\~N}et and Paul {\\\\={P}}ot\")\n"
          + "    (field 'title \"Lower Bounds for Wishful Research " +
          "Results\")\n"
          + "    (field 'note \"Talk at Fanstord University (this " +
          "is a minimal UNPUBLISHED entry)\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'unpublished \"unpublished-full\"\n"
          + "    (field 'author \"Ulrich {\\\\\\\"{U}}nderwood and " +
          "Ned {\\\\~N}et and Paul {\\\\={P}}ot\")\n"
          + "    (field 'title \"Lower Bounds for Wishful Research " +
          "Results\")\n"
          + "    (field 'month 'nov  \", \" 'dec )\n"
          + "    (field 'year 1988)\n"
          + "    (field 'note \"Talk at Fanstord University (this " +
          "is a full UNPUBLISHED entry)\")\n"
          + "  )\n"
          + "\n"
          + "  (entry 'misc \"random-note-crossref\"\n"
          + "    (field 'key \"Volume-2\")\n"
          + "    (field 'note \"Volume~2 is listed under Knuth " +
          "\\\\cite{book-full}\")\n"
          + "  )\n" + "\n" + ")\n";

  /**
   * Test method for
   * {@link org.extex.exbib.core.io.bibio.BibPrinterImpl#print(org.extex.exbib.core.db.DB)}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testPrint1() throws Exception {

    DB db = BiblioTester.loadBib( "src/test/resources/bibtex/base/xampl" );
    StringBuffer buffer = new StringBuffer();
    new BibPrinterLispImpl( new StringBufferWriter( buffer ) ).print( db );

    assertEquals( RESULT_1, buffer.toString().replaceAll( "\r", "" ) );
  }

}
