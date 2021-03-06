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
import static org.junit.Assert.assertTrue;

/**
 * This is a test suite for {@link BibPrinterImpl}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibPrinterImplTest {

  /**
   * The field {@code RESULT_1} contains the result for test case 1.
   */
  private static final String RESULT_1 =
      "@Preamble{\"\\newcommand{\\noopsort}[1]{} \" "
          + "# \"\\newcommand{\\printfirst}[2]{#1} \" "
          + "# \"\\newcommand{\\singleletter}[1]{#1} \" "
          + "# \"\\newcommand{\\switchargs}[2]{#2#1} \"}\n"
          + "\n"
          + "@String{acm = \"The OX Association for Computing Machinery\"}\n"
          + "@String{stoc = \" Symposium on the Theory of Computing\"}\n"
          + "@String{stoc-key = \"OX{\\singleletter{stoc}}\"}\n"
          + "\n"
          + "@article{ article-minimal,\n"
          + "\tauthor = {L[eslie] A. Aamport},\n"
          + "\ttitle = {The Gnats and Gnus Document Preparation System},\n"
          + "\tjournal = {\\mbox{G-Animal's} Journal},\n"
          + "\tyear = 1986\n"
          + "}\n"
          + "\n"
          + "@article{ article-full,\n"
          + "\tauthor = {L[eslie] A. Aamport},\n"
          + "\ttitle = {The Gnats and Gnus Document Preparation System},\n"
          + "\tjournal = {\\mbox{G-Animal's} Journal},\n"
          + "\tyear = 1986,\n"
          + "\tvolume = 41,\n"
          + "\tnumber = 7,\n"
          + "\tpages = \"73+\",\n"
          + "\tmonth = jul,\n"
          + "\tnote = \"This is a full ARTICLE entry\"\n"
          + "}\n"
          + "\n"
          + "@article{ article-crossref,\n"
          + "\tcrossref = {WHOLE-JOURNAL},\n"
          + "\tkey = \"\",\n"
          + "\tauthor = {L[eslie] A. Aamport},\n"
          + "\ttitle = {The Gnats and Gnus Document Preparation System},\n"
          + "\tpages = \"73+\",\n"
          + "\tnote = \"This is a cross-referencing ARTICLE entry\"\n"
          + "}\n"
          + "\n"
          + "@article{ whole-journal,\n"
          + "\tkey = \"GAJ\",\n"
          + "\tjournal = {\\mbox{G-Animal's} Journal},\n"
          + "\tyear = 1986,\n"
          + "\tvolume = 41,\n"
          + "\tnumber = 7,\n"
          + "\tmonth = jul,\n"
          + "\tnote = {The entire issue is devoted to gnats and gnus"
          + "\t\t(this entry is a cross-referenced ARTICLE (journal))}\n"
          + "}\n"
          + "\n"
          + "@inbook{ inbook-minimal,\n"
          + "\tauthor = \"Donald E. Knuth\",\n"
          + "\ttitle = \"Fundamental Algorithms\",\n"
          + "\tpublisher = \"Addison-Wesley\",\n"
          + "\tyear = \"{\\noopsort{1973b}}1973\",\n"
          + "\tchapter = \"1.2\"\n"
          + "}\n"
          + "\n"
          + "@inbook{ inbook-full,\n"
          + "\tauthor = \"Donald E. Knuth\",\n"
          + "\ttitle = \"Fundamental Algorithms\",\n"
          + "\tvolume = 1,\n"
          + "\tseries = \"The Art of Computer Programming\",\n"
          + "\tpublisher = \"Addison-Wesley\",\n"
          + "\taddress = \"Reading, Massachusetts\",\n"
          + "\tedition = \"Second\",\n"
          + "\tmonth = \"10~\" # jan,\n"
          + "\tyear = \"{\\noopsort{1973b}}1973\",\n"
          + "\ttype = \"Section\",\n"
          + "\tchapter = \"1.2\",\n"
          + "\tpages = \"10--119\",\n"
          + "\tnote = \"This is a full INBOOK entry\"\n"
          + "}\n"
          + "\n"
          + "@inbook{ inbook-crossref,\n"
          + "\tcrossref = \"whole-set\",\n"
          + "\ttitle = \"Fundamental Algorithms\",\n"
          + "\tvolume = 1,\n"
          + "\tseries = \"The Art of Computer Programming\",\n"
          + "\tedition = \"Second\",\n"
          + "\tyear = \"{\\noopsort{1973b}}1973\",\n"
          + "\ttype = \"Section\",\n"
          + "\tchapter = \"1.2\",\n"
          + "\tnote = \"This is a cross-referencing INBOOK entry\"\n"
          + "}\n"
          + "\n"
          + "@book{ book-minimal,\n"
          + "\tauthor = \"Donald E. Knuth\",\n"
          + "\ttitle = \"Seminumerical Algorithms\",\n"
          + "\tpublisher = \"Addison-Wesley\",\n"
          + "\tyear = \"{\\noopsort{1973c}}1981\"\n"
          + "}\n"
          + "\n"
          + "@book{ book-full,\n"
          + "\tauthor = \"Donald E. Knuth\",\n"
          + "\ttitle = \"Seminumerical Algorithms\",\n"
          + "\tvolume = 2,\n"
          + "\tseries = \"The Art of Computer Programming\",\n"
          + "\tpublisher = \"Addison-Wesley\",\n"
          + "\taddress = \"Reading, Massachusetts\",\n"
          + "\tedition = \"Second\",\n"
          + "\tmonth = \"10~\" # jan,\n"
          + "\tyear = \"{\\noopsort{1973c}}1981\",\n"
          + "\tnote = \"This is a full BOOK entry\"\n"
          + "}\n"
          + "\n"
          + "@book{ book-crossref,\n"
          + "\tcrossref = \"whole-set\",\n"
          + "\ttitle = \"Seminumerical Algorithms\",\n"
          + "\tvolume = 2,\n"
          + "\tseries = \"The Art of Computer Programming\",\n"
          + "\tedition = \"Second\",\n"
          + "\tyear = \"{\\noopsort{1973c}}1981\",\n"
          + "\tnote = \"This is a cross-referencing BOOK entry\"\n"
          + "}\n"
          + "\n"
          + "@book{ whole-set,\n"
          + "\tauthor = \"Donald E. Knuth\",\n"
          + "\tpublisher = \"Addison-Wesley\",\n"
          + "\ttitle = \"The Art of Computer Programming\",\n"
          + "\tseries = \"Four volumes\",\n"
          + "\tyear = \"{\\noopsort{1973a}}{\\switchargs{--90}{1968}}\",\n"
          + "\tnote = \"Seven volumes planned (this is a cross-referenced set" +
          " of BOOKs)\"\n"
          + "}\n"
          + "\n"
          + "@booklet{ booklet-minimal,\n"
          + "\tkey = \"Kn{\\printfirst{v}{1987}}\",\n"
          + "\ttitle = \"The Programming of Computer Art\"\n"
          + "}\n"
          + "\n"
          + "@booklet{ booklet-full,\n"
          + "\tauthor = \"Jill C. Knvth\",\n"
          + "\ttitle = \"The Programming of Computer Art\",\n"
          + "\thowpublished = \"Vernier Art Center\",\n"
          + "\taddress = \"Stanford, California\",\n"
          + "\tmonth = feb,\n"
          + "\tyear = 1988,\n"
          + "\tnote = \"This is a full BOOKLET entry\"\n"
          + "}\n"
          + "\n"
          + "@incollection{ incollection-minimal,\n"
          + "\tauthor = \"Daniel D. Lincoll\",\n"
          + "\ttitle = \"Semigroups of Recurrences\",\n"
          + "\tbooktitle = \"High Speed Computer and Algorithm " +
          "Organization\",\n"
          + "\tpublisher = \"Academic Press\",\n"
          + "\tyear = 1977\n"
          + "}\n"
          + "\n"
          + "@incollection{ incollection-full,\n"
          + "\tauthor = \"Daniel D. Lincoll\",\n"
          + "\ttitle = \"Semigroups of Recurrences\",\n"
          + "\teditor = \"David J. Lipcoll and D. H. Lawrie and A. H. " +
          "Sameh\",\n"
          + "\tbooktitle = \"High Speed Computer and Algorithm " +
          "Organization\",\n"
          + "\tnumber = 23,\n"
          + "\tseries = \"Fast Computers\",\n"
          + "\tchapter = 3,\n"
          + "\ttype = \"Part\",\n"
          + "\tpages = \"179--183\",\n"
          + "\tpublisher = \"Academic Press\",\n"
          + "\taddress = \"New York\",\n"
          + "\tedition = \"Third\",\n"
          + "\tmonth = sep,\n"
          + "\tyear = 1977,\n"
          + "\tnote = \"This is a full INCOLLECTION entry\"\n"
          + "}\n"
          + "\n"
          + "@incollection{ incollection-crossref,\n"
          + "\tcrossref = \"whole-collection\",\n"
          + "\tauthor = \"Daniel D. Lincoll\",\n"
          + "\ttitle = \"Semigroups of Recurrences\",\n"
          + "\tpages = \"179--183\",\n"
          + "\tnote = \"This is a cross-referencing INCOLLECTION entry\"\n"
          + "}\n"
          + "\n"
          + "@book{ whole-collection,\n"
          + "\teditor = \"David J. Lipcoll and D. H. Lawrie and A. H. " +
          "Sameh\",\n"
          + "\ttitle = \"High Speed Computer and Algorithm Organization\",\n"
          + "\tbooktitle = \"High Speed Computer and Algorithm " +
          "Organization\",\n"
          + "\tnumber = 23,\n"
          + "\tseries = \"Fast Computers\",\n"
          + "\tpublisher = \"Academic Press\",\n"
          + "\taddress = \"New York\",\n"
          + "\tedition = \"Third\",\n"
          + "\tmonth = sep,\n"
          + "\tyear = 1977,\n"
          + "\tnote = \"This is a cross-referenced BOOK (collection) entry\"\n"
          + "}\n"
          + "\n"
          + "@manual{ manual-minimal,\n"
          + "\tkey = \"Manmaker\",\n"
          + "\ttitle = \"The Definitive Computer Manual\"\n"
          + "}\n"
          + "\n"
          + "@manual{ manual-full,\n"
          + "\tauthor = \"Larry Manmaker\",\n"
          + "\ttitle = \"The Definitive Computer Manual\",\n"
          + "\torganization = \"Chips-R-Us\",\n"
          + "\taddress = \"Silicon Valley\",\n"
          + "\tedition = \"Silver\",\n"
          + "\tmonth = apr # \"-\" # may,\n"
          + "\tyear = 1986,\n"
          + "\tnote = \"This is a full MANUAL entry\"\n"
          + "}\n"
          + "\n"
          + "@mastersthesis{ mastersthesis-minimal,\n"
          + "\tauthor = \"{\\'{E}}douard Masterly\",\n"
          + "\ttitle = \"Mastering Thesis Writing\",\n"
          + "\tschool = \"Stanford University\",\n"
          + "\tyear = 1988\n"
          + "}\n"
          + "\n"
          + "@mastersthesis{ mastersthesis-full,\n"
          + "\tauthor = \"{\\'{E}}douard Masterly\",\n"
          + "\ttitle = \"Mastering Thesis Writing\",\n"
          + "\tschool = \"Stanford University\",\n"
          + "\ttype = \"Master's project\",\n"
          + "\taddress = \"English Department\",\n"
          + "\tmonth = jun # \"-\" # aug,\n"
          + "\tyear = 1988,\n"
          + "\tnote = \"This is a full MASTERSTHESIS entry\"\n"
          + "}\n"
          + "\n"
          + "@misc{ misc-minimal,\n"
          + "\tkey = \"Missilany\",\n"
          + "\tnote = \"This is a minimal MISC entry\"\n"
          + "}\n"
          + "\n"
          + "@misc{ misc-full,\n"
          + "\tauthor = \"Joe-Bob Missilany\",\n"
          + "\ttitle = \"Handing out random pamphlets in airports\",\n"
          + "\thowpublished = \"Handed out at O'Hare\",\n"
          + "\tmonth = oct,\n"
          + "\tyear = 1984,\n"
          + "\tnote = \"This is a full MISC entry\"\n"
          + "}\n"
          + "\n"
          + "@inproceedings{ inproceedings-minimal,\n"
          + "\tauthor = \"Alfred V. Oaho and Jeffrey D. Ullman and Mihalis " +
          "Yannakakis\",\n"
          + "\ttitle = \"On Notions of Information Transfer in {VLSI} " +
          "Circuits\",\n"
          + "\tbooktitle = \"Proc. Fifteenth Annual ACM\" # STOC,\n"
          + "\tyear = 1983\n"
          + "}\n"
          + "\n"
          + "@inproceedings{ inproceedings-full,\n"
          + "\tauthor = \"Alfred V. Oaho and Jeffrey D. Ullman and Mihalis " +
          "Yannakakis\",\n"
          + "\ttitle = \"On Notions of Information Transfer in {VLSI} " +
          "Circuits\",\n"
          + "\teditor = \"Wizard V. Oz and Mihalis Yannakakis\",\n"
          + "\tbooktitle = \"Proc. Fifteenth Annual ACM\" # STOC,\n"
          + "\tnumber = 17,\n"
          + "\tseries = \"All ACM Conferences\",\n"
          + "\tpages = \"133--139\",\n"
          + "\tmonth = mar,\n"
          + "\tyear = 1983,\n"
          + "\taddress = \"Boston\",\n"
          + "\torganization = ACM,\n"
          + "\tpublisher = \"Academic Press\",\n"
          + "\tnote = \"This is a full INPROCEDINGS entry\"\n"
          + "}\n"
          + "\n"
          + "@inproceedings{ inproceedings-crossref,\n"
          + "\tcrossref = \"whole-proceedings\",\n"
          + "\tauthor = \"Alfred V. Oaho and Jeffrey D. Ullman and Mihalis " +
          "Yannakakis\",\n"
          + "\ttitle = \"On Notions of Information Transfer in {VLSI} " +
          "Circuits\",\n"
          + "\torganization = \"\",\n"
          + "\tpages = \"133--139\",\n"
          + "\tnote = \"This is a cross-referencing INPROCEEDINGS entry\"\n"
          + "}\n"
          + "\n"
          + "@proceedings{ proceedings-minimal,\n"
          + "\tkey = STOC-key,\n"
          + "\ttitle = \"Proc. Fifteenth Annual\" # STOC,\n"
          + "\tyear = 1983\n"
          + "}\n"
          + "\n"
          + "@proceedings{ proceedings-full,\n"
          + "\teditor = \"Wizard V. Oz and Mihalis Yannakakis\",\n"
          + "\ttitle = \"Proc. Fifteenth Annual\" # STOC,\n"
          + "\tnumber = 17,\n"
          + "\tseries = \"All ACM Conferences\",\n"
          + "\tmonth = mar,\n"
          + "\tyear = 1983,\n"
          + "\taddress = \"Boston\",\n"
          + "\torganization = ACM,\n"
          + "\tpublisher = \"Academic Press\",\n"
          + "\tnote = \"This is a full PROCEEDINGS entry\"\n"
          + "}\n"
          + "\n"
          + "@proceedings{ whole-proceedings,\n"
          + "\tkey = STOC-key,\n"
          + "\torganization = ACM,\n"
          + "\ttitle = \"Proc. Fifteenth Annual\" # STOC,\n"
          + "\taddress = \"Boston\",\n"
          + "\tyear = 1983,\n"
          + "\tbooktitle = \"Proc. Fifteenth Annual ACM\" # STOC,\n"
          + "\tnote = \"This is a cross-referenced PROCEEDINGS\"\n"
          + "}\n"
          + "\n"
          + "@phdthesis{ phdthesis-minimal,\n"
          + "\tauthor = \"F. Phidias Phony-Baloney\",\n"
          + "\ttitle = \"Fighting Fire with Fire: Festooning {F}rench " +
          "Phrases\",\n"
          + "\tschool = \"Fanstord University\",\n"
          + "\tyear = 1988\n"
          + "}\n"
          + "\n"
          + "@phdthesis{ phdthesis-full,\n"
          + "\tauthor = \"F. Phidias Phony-Baloney\",\n"
          + "\ttitle = \"Fighting Fire with Fire: Festooning {F}rench " +
          "Phrases\",\n"
          + "\tschool = \"Fanstord University\",\n"
          + "\ttype = \"{PhD} Dissertation\",\n"
          + "\taddress = \"Department of French\",\n"
          + "\tmonth = jun # \"-\" # aug,\n"
          + "\tyear = 1988,\n"
          + "\tnote = \"This is a full PHDTHESIS entry\"\n"
          + "}\n"
          + "\n"
          + "@techreport{ techreport-minimal,\n"
          + "\tauthor = \"Tom Terrific\",\n"
          + "\ttitle = \"An {$O(n \\log n / \\! \\log\\log n)$} Sorting " +
          "Algorithm\",\n"
          + "\tinstitution = \"Fanstord University\",\n"
          + "\tyear = 1988\n"
          + "}\n"
          + "\n"
          + "@techreport{ techreport-full,\n"
          + "\tauthor = \"Tom T{\\'{e}}rrific\",\n"
          + "\ttitle = \"An {$O(n \\log n / \\! \\log\\log n)$} Sorting " +
          "Algorithm\",\n"
          + "\tinstitution = \"Fanstord University\",\n"
          + "\ttype = \"Wishful Research Result\",\n"
          + "\tnumber = \"7\",\n"
          + "\taddress = \"Computer Science Department, Fanstord, " +
          "California\",\n"
          + "\tmonth = oct,\n"
          + "\tyear = 1988,\n"
          + "\tnote = \"This is a full TECHREPORT entry\"\n"
          + "}\n"
          + "\n"
          + "@unpublished{ unpublished-minimal,\n"
          + "\tauthor = \"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul " +
          "{\\={P}}ot\",\n"
          + "\ttitle = \"Lower Bounds for Wishful Research Results\",\n"
          + "\tnote = \"Talk at Fanstord University (this is a minimal " +
          "UNPUBLISHED entry)\"\n"
          + "}\n"
          + "\n"
          + "@unpublished{ unpublished-full,\n"
          + "\tauthor = \"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul " +
          "{\\={P}}ot\",\n"
          + "\ttitle = \"Lower Bounds for Wishful Research Results\",\n"
          + "\tmonth = nov # \", \" # dec,\n"
          + "\tyear = 1988,\n"
          + "\tnote = \"Talk at Fanstord University (this is a full " +
          "UNPUBLISHED entry)\"\n"
          + "}\n"
          + "\n"
          + "@misc{ random-note-crossref,\n"
          + "\tkey = {Volume-2},\n"
          + "\tnote = \"Volume~2 is listed under Knuth \\cite{book-full}\"\n"
          + "}\n";

  /**
   * Test method for
   * {@link org.extex.exbib.core.io.bibio.BibPrinterImpl#print(org.extex.exbib.core.db.DB)}
   * .
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testPrint1() throws Exception {

    DB db = BiblioTester.loadBib( "src/test/resources/bibtex/base/xampl" );
    StringBuffer buffer = new StringBuffer();
    new BibPrinterImpl( new StringBufferWriter( buffer ) ).print( db );

    assertEquals( RESULT_1, buffer.toString().replaceAll( "\r", "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.exbib.core.io.bibio.BibPrinterImpl#print(org.extex.exbib.core.db.DB)}
   * .
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testPrint2() throws Exception {

    DB db = BiblioTester.loadBib( "src/test/resources/bibtex/base/xampl" );
    new BibPrinterImpl( null ).print( db );

    assertTrue( true );
  }

}
