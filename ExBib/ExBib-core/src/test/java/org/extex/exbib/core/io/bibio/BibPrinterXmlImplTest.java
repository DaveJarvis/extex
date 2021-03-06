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
 * This is a test suite for {@link BibPrinterXMLImpl}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibPrinterXmlImplTest {

  /**
   * The field {@code RESULT_1} contains the result for test case 1.
   */
  private static final String RESULT_1 =
      "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
          + "<database>\n"
          + "  <preamble>\\newcommand{\\noopsort}[1]{} "
          + " \\newcommand{\\printfirst}[2]{#1} "
          + " \\newcommand{\\singleletter}[1]{#1} "
          + " \\newcommand{\\switchargs}[2]{#2#1} </preamble>\n"
          + "\n"
          + "  <string id=\"acm\">The OX Association for Computing " +
          "Machinery</string>\n"
          + "  <string id=\"stoc\"> Symposium on the Theory of " +
          "Computing</string>\n"
          + "  <string id=\"stoc-key\">OX{\\singleletter{stoc}}</string>\n"
          + "\n"
          + "  <article id=\"article-minimal\">\n"
          + "    <author>L[eslie] A. Aamport</author>\n"
          + "    <title>The Gnats and Gnus Document Preparation " +
          "System</title>\n"
          + "    <journal>\\mbox{G-Animal's} Journal</journal>\n"
          + "    <year>1986</year>\n"
          + "  </article>\n"
          + "\n"
          + "  <article id=\"article-full\">\n"
          + "    <author>L[eslie] A. Aamport</author>\n"
          + "    <title>The Gnats and Gnus Document Preparation " +
          "System</title>\n"
          + "    <journal>\\mbox{G-Animal's} Journal</journal>\n"
          + "    <year>1986</year>\n"
          + "    <volume>41</volume>\n"
          + "    <number>7</number>\n"
          + "    <pages>73+</pages>\n"
          + "    <month><macro name=\"jul\"/></month>\n"
          + "    <note>This is a full ARTICLE entry</note>\n"
          + "  </article>\n"
          + "\n"
          + "  <article id=\"article-crossref\">\n"
          + "    <crossref>WHOLE-JOURNAL</crossref>\n"
          + "    <key></key>\n"
          + "    <author>L[eslie] A. Aamport</author>\n"
          + "    <title>The Gnats and Gnus Document Preparation " +
          "System</title>\n"
          + "    <pages>73+</pages>\n"
          + "    <note>This is a cross-referencing ARTICLE entry</note>\n"
          + "  </article>\n"
          + "\n"
          + "  <article id=\"whole-journal\">\n"
          + "    <key>GAJ</key>\n"
          + "    <journal>\\mbox{G-Animal's} Journal</journal>\n"
          + "    <year>1986</year>\n"
          + "    <volume>41</volume>\n"
          + "    <number>7</number>\n"
          + "    <month><macro name=\"jul\"/></month>\n"
          + "    <note>The entire issue is devoted to gnats and gnus"
          + "\t\t(this entry is a cross-referenced ARTICLE (journal))</note>\n"
          + "  </article>\n"
          + "\n"
          + "  <inbook id=\"inbook-minimal\">\n"
          + "    <author>Donald E. Knuth</author>\n"
          + "    <title>Fundamental Algorithms</title>\n"
          + "    <publisher>Addison-Wesley</publisher>\n"
          + "    <year>{\\noopsort{1973b}}1973</year>\n"
          + "    <chapter>1.2</chapter>\n"
          + "  </inbook>\n"
          + "\n"
          + "  <inbook id=\"inbook-full\">\n"
          + "    <author>Donald E. Knuth</author>\n"
          + "    <title>Fundamental Algorithms</title>\n"
          + "    <volume>1</volume>\n"
          + "    <series>The Art of Computer Programming</series>\n"
          + "    <publisher>Addison-Wesley</publisher>\n"
          + "    <address>Reading, Massachusetts</address>\n"
          + "    <edition>Second</edition>\n"
          + "    <month>10~ <macro name=\"jan\"/></month>\n"
          + "    <year>{\\noopsort{1973b}}1973</year>\n"
          + "    <type>Section</type>\n"
          + "    <chapter>1.2</chapter>\n"
          + "    <pages>10--119</pages>\n"
          + "    <note>This is a full INBOOK entry</note>\n"
          + "  </inbook>\n"
          + "\n"
          + "  <inbook id=\"inbook-crossref\">\n"
          + "    <crossref>whole-set</crossref>\n"
          + "    <title>Fundamental Algorithms</title>\n"
          + "    <volume>1</volume>\n"
          + "    <series>The Art of Computer Programming</series>\n"
          + "    <edition>Second</edition>\n"
          + "    <year>{\\noopsort{1973b}}1973</year>\n"
          + "    <type>Section</type>\n"
          + "    <chapter>1.2</chapter>\n"
          + "    <note>This is a cross-referencing INBOOK entry</note>\n"
          + "  </inbook>\n"
          + "\n"
          + "  <book id=\"book-minimal\">\n"
          + "    <author>Donald E. Knuth</author>\n"
          + "    <title>Seminumerical Algorithms</title>\n"
          + "    <publisher>Addison-Wesley</publisher>\n"
          + "    <year>{\\noopsort{1973c}}1981</year>\n"
          + "  </book>\n"
          + "\n"
          + "  <book id=\"book-full\">\n"
          + "    <author>Donald E. Knuth</author>\n"
          + "    <title>Seminumerical Algorithms</title>\n"
          + "    <volume>2</volume>\n"
          + "    <series>The Art of Computer Programming</series>\n"
          + "    <publisher>Addison-Wesley</publisher>\n"
          + "    <address>Reading, Massachusetts</address>\n"
          + "    <edition>Second</edition>\n"
          + "    <month>10~ <macro name=\"jan\"/></month>\n"
          + "    <year>{\\noopsort{1973c}}1981</year>\n"
          + "    <note>This is a full BOOK entry</note>\n"
          + "  </book>\n"
          + "\n"
          + "  <book id=\"book-crossref\">\n"
          + "    <crossref>whole-set</crossref>\n"
          + "    <title>Seminumerical Algorithms</title>\n"
          + "    <volume>2</volume>\n"
          + "    <series>The Art of Computer Programming</series>\n"
          + "    <edition>Second</edition>\n"
          + "    <year>{\\noopsort{1973c}}1981</year>\n"
          + "    <note>This is a cross-referencing BOOK entry</note>\n"
          + "  </book>\n"
          + "\n"
          + "  <book id=\"whole-set\">\n"
          + "    <author>Donald E. Knuth</author>\n"
          + "    <publisher>Addison-Wesley</publisher>\n"
          + "    <title>The Art of Computer Programming</title>\n"
          + "    <series>Four volumes</series>\n"
          + "    <year>{\\noopsort{1973a}}{\\switchargs{--90}{1968}}</year>\n"
          + "    <note>Seven volumes planned (this is a cross-referenced set " +
          "of BOOKs)</note>\n"
          + "  </book>\n"
          + "\n"
          + "  <booklet id=\"booklet-minimal\">\n"
          + "    <key>Kn{\\printfirst{v}{1987}}</key>\n"
          + "    <title>The Programming of Computer Art</title>\n"
          + "  </booklet>\n"
          + "\n"
          + "  <booklet id=\"booklet-full\">\n"
          + "    <author>Jill C. Knvth</author>\n"
          + "    <title>The Programming of Computer Art</title>\n"
          + "    <howpublished>Vernier Art Center</howpublished>\n"
          + "    <address>Stanford, California</address>\n"
          + "    <month><macro name=\"feb\"/></month>\n"
          + "    <year>1988</year>\n"
          + "    <note>This is a full BOOKLET entry</note>\n"
          + "  </booklet>\n"
          + "\n"
          + "  <incollection id=\"incollection-minimal\">\n"
          + "    <author>Daniel D. Lincoll</author>\n"
          + "    <title>Semigroups of Recurrences</title>\n"
          + "    <booktitle>High Speed Computer and Algorithm " +
          "Organization</booktitle>\n"
          + "    <publisher>Academic Press</publisher>\n"
          + "    <year>1977</year>\n"
          + "  </incollection>\n"
          + "\n"
          + "  <incollection id=\"incollection-full\">\n"
          + "    <author>Daniel D. Lincoll</author>\n"
          + "    <title>Semigroups of Recurrences</title>\n"
          + "    <editor>David J. Lipcoll and D. H. Lawrie and A. H. " +
          "Sameh</editor>\n"
          + "    <booktitle>High Speed Computer and Algorithm " +
          "Organization</booktitle>\n"
          + "    <number>23</number>\n"
          + "    <series>Fast Computers</series>\n"
          + "    <chapter>3</chapter>\n"
          + "    <type>Part</type>\n"
          + "    <pages>179--183</pages>\n"
          + "    <publisher>Academic Press</publisher>\n"
          + "    <address>New York</address>\n"
          + "    <edition>Third</edition>\n"
          + "    <month><macro name=\"sep\"/></month>\n"
          + "    <year>1977</year>\n"
          + "    <note>This is a full INCOLLECTION entry</note>\n"
          + "  </incollection>\n"
          + "\n"
          + "  <incollection id=\"incollection-crossref\">\n"
          + "    <crossref>whole-collection</crossref>\n"
          + "    <author>Daniel D. Lincoll</author>\n"
          + "    <title>Semigroups of Recurrences</title>\n"
          + "    <pages>179--183</pages>\n"
          + "    <note>This is a cross-referencing INCOLLECTION entry</note>\n"
          + "  </incollection>\n"
          + "\n"
          + "  <book id=\"whole-collection\">\n"
          + "    <editor>David J. Lipcoll and D. H. Lawrie and A. H. " +
          "Sameh</editor>\n"
          + "    <title>High Speed Computer and Algorithm " +
          "Organization</title>\n"
          + "    <booktitle>High Speed Computer and Algorithm " +
          "Organization</booktitle>\n"
          + "    <number>23</number>\n"
          + "    <series>Fast Computers</series>\n"
          + "    <publisher>Academic Press</publisher>\n"
          + "    <address>New York</address>\n"
          + "    <edition>Third</edition>\n"
          + "    <month><macro name=\"sep\"/></month>\n"
          + "    <year>1977</year>\n"
          + "    <note>This is a cross-referenced BOOK (collection) " +
          "entry</note>\n"
          + "  </book>\n"
          + "\n"
          + "  <manual id=\"manual-minimal\">\n"
          + "    <key>Manmaker</key>\n"
          + "    <title>The Definitive Computer Manual</title>\n"
          + "  </manual>\n"
          + "\n"
          + "  <manual id=\"manual-full\">\n"
          + "    <author>Larry Manmaker</author>\n"
          + "    <title>The Definitive Computer Manual</title>\n"
          + "    <organization>Chips-R-Us</organization>\n"
          + "    <address>Silicon Valley</address>\n"
          + "    <edition>Silver</edition>\n"
          + "    <month><macro name=\"apr\"/> - <macro name=\"may\"/></month>\n"
          + "    <year>1986</year>\n"
          + "    <note>This is a full MANUAL entry</note>\n"
          + "  </manual>\n"
          + "\n"
          + "  <mastersthesis id=\"mastersthesis-minimal\">\n"
          + "    <author>{\\'{E}}douard Masterly</author>\n"
          + "    <title>Mastering Thesis Writing</title>\n"
          + "    <school>Stanford University</school>\n"
          + "    <year>1988</year>\n"
          + "  </mastersthesis>\n"
          + "\n"
          + "  <mastersthesis id=\"mastersthesis-full\">\n"
          + "    <author>{\\'{E}}douard Masterly</author>\n"
          + "    <title>Mastering Thesis Writing</title>\n"
          + "    <school>Stanford University</school>\n"
          + "    <type>Master's project</type>\n"
          + "    <address>English Department</address>\n"
          + "    <month><macro name=\"jun\"/> - <macro name=\"aug\"/></month>\n"
          + "    <year>1988</year>\n"
          + "    <note>This is a full MASTERSTHESIS entry</note>\n"
          + "  </mastersthesis>\n"
          + "\n"
          + "  <misc id=\"misc-minimal\">\n"
          + "    <key>Missilany</key>\n"
          + "    <note>This is a minimal MISC entry</note>\n"
          + "  </misc>\n"
          + "\n"
          + "  <misc id=\"misc-full\">\n"
          + "    <author>Joe-Bob Missilany</author>\n"
          + "    <title>Handing out random pamphlets in airports</title>\n"
          + "    <howpublished>Handed out at O'Hare</howpublished>\n"
          + "    <month><macro name=\"oct\"/></month>\n"
          + "    <year>1984</year>\n"
          + "    <note>This is a full MISC entry</note>\n"
          + "  </misc>\n"
          + "\n"
          + "  <inproceedings id=\"inproceedings-minimal\">\n"
          + "    <author>Alfred V. Oaho and Jeffrey D. Ullman and Mihalis " +
          "Yannakakis</author>\n"
          + "    <title>On Notions of Information Transfer in {VLSI} " +
          "Circuits</title>\n"
          + "    <booktitle>Proc. Fifteenth Annual ACM <macro " +
          "name=\"stoc\"/></booktitle>\n"
          + "    <year>1983</year>\n"
          + "  </inproceedings>\n"
          + "\n"
          + "  <inproceedings id=\"inproceedings-full\">\n"
          + "    <author>Alfred V. Oaho and Jeffrey D. Ullman and Mihalis " +
          "Yannakakis</author>\n"
          + "    <title>On Notions of Information Transfer in {VLSI} " +
          "Circuits</title>\n"
          + "    <editor>Wizard V. Oz and Mihalis Yannakakis</editor>\n"
          + "    <booktitle>Proc. Fifteenth Annual ACM <macro " +
          "name=\"stoc\"/></booktitle>\n"
          + "    <number>17</number>\n"
          + "    <series>All ACM Conferences</series>\n"
          + "    <pages>133--139</pages>\n"
          + "    <month><macro name=\"mar\"/></month>\n"
          + "    <year>1983</year>\n"
          + "    <address>Boston</address>\n"
          + "    <organization><macro name=\"acm\"/></organization>\n"
          + "    <publisher>Academic Press</publisher>\n"
          + "    <note>This is a full INPROCEDINGS entry</note>\n"
          + "  </inproceedings>\n"
          + "\n"
          + "  <inproceedings id=\"inproceedings-crossref\">\n"
          + "    <crossref>whole-proceedings</crossref>\n"
          + "    <author>Alfred V. Oaho and Jeffrey D. Ullman and Mihalis " +
          "Yannakakis</author>\n"
          + "    <title>On Notions of Information Transfer in {VLSI} " +
          "Circuits</title>\n"
          + "    <organization></organization>\n"
          + "    <pages>133--139</pages>\n"
          + "    <note>This is a cross-referencing INPROCEEDINGS entry</note>\n"
          + "  </inproceedings>\n"
          + "\n"
          + "  <proceedings id=\"proceedings-minimal\">\n"
          + "    <key><macro name=\"stoc-key\"/></key>\n"
          + "    <title>Proc. Fifteenth Annual <macro name=\"stoc\"/></title>\n"
          + "    <year>1983</year>\n"
          + "  </proceedings>\n"
          + "\n"
          + "  <proceedings id=\"proceedings-full\">\n"
          + "    <editor>Wizard V. Oz and Mihalis Yannakakis</editor>\n"
          + "    <title>Proc. Fifteenth Annual <macro name=\"stoc\"/></title>\n"
          + "    <number>17</number>\n"
          + "    <series>All ACM Conferences</series>\n"
          + "    <month><macro name=\"mar\"/></month>\n"
          + "    <year>1983</year>\n"
          + "    <address>Boston</address>\n"
          + "    <organization><macro name=\"acm\"/></organization>\n"
          + "    <publisher>Academic Press</publisher>\n"
          + "    <note>This is a full PROCEEDINGS entry</note>\n"
          + "  </proceedings>\n"
          + "\n"
          + "  <proceedings id=\"whole-proceedings\">\n"
          + "    <key><macro name=\"stoc-key\"/></key>\n"
          + "    <organization><macro name=\"acm\"/></organization>\n"
          + "    <title>Proc. Fifteenth Annual <macro name=\"stoc\"/></title>\n"
          + "    <address>Boston</address>\n"
          + "    <year>1983</year>\n"
          + "    <booktitle>Proc. Fifteenth Annual ACM <macro " +
          "name=\"stoc\"/></booktitle>\n"
          + "    <note>This is a cross-referenced PROCEEDINGS</note>\n"
          + "  </proceedings>\n"
          + "\n"
          + "  <phdthesis id=\"phdthesis-minimal\">\n"
          + "    <author>F. Phidias Phony-Baloney</author>\n"
          + "    <title>Fighting Fire with Fire: Festooning {F}rench " +
          "Phrases</title>\n"
          + "    <school>Fanstord University</school>\n"
          + "    <year>1988</year>\n"
          + "  </phdthesis>\n"
          + "\n"
          + "  <phdthesis id=\"phdthesis-full\">\n"
          + "    <author>F. Phidias Phony-Baloney</author>\n"
          + "    <title>Fighting Fire with Fire: Festooning {F}rench " +
          "Phrases</title>\n"
          + "    <school>Fanstord University</school>\n"
          + "    <type>{PhD} Dissertation</type>\n"
          + "    <address>Department of French</address>\n"
          + "    <month><macro name=\"jun\"/> - <macro name=\"aug\"/></month>\n"
          + "    <year>1988</year>\n"
          + "    <note>This is a full PHDTHESIS entry</note>\n"
          + "  </phdthesis>\n"
          + "\n"
          + "  <techreport id=\"techreport-minimal\">\n"
          + "    <author>Tom Terrific</author>\n"
          + "    <title>An {$O(n \\log n / \\! \\log\\log n)$} Sorting " +
          "Algorithm</title>\n"
          + "    <institution>Fanstord University</institution>\n"
          + "    <year>1988</year>\n"
          + "  </techreport>\n"
          + "\n"
          + "  <techreport id=\"techreport-full\">\n"
          + "    <author>Tom T{\\'{e}}rrific</author>\n"
          + "    <title>An {$O(n \\log n / \\! \\log\\log n)$} Sorting " +
          "Algorithm</title>\n"
          + "    <institution>Fanstord University</institution>\n"
          + "    <type>Wishful Research Result</type>\n"
          + "    <number>7</number>\n"
          + "    <address>Computer Science Department, Fanstord, " +
          "California</address>\n"
          + "    <month><macro name=\"oct\"/></month>\n"
          + "    <year>1988</year>\n"
          + "    <note>This is a full TECHREPORT entry</note>\n"
          + "  </techreport>\n"
          + "\n"
          + "  <unpublished id=\"unpublished-minimal\">\n"
          + "    <author>Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul " +
          "{\\={P}}ot</author>\n"
          + "    <title>Lower Bounds for Wishful Research Results</title>\n"
          + "    <note>Talk at Fanstord University (this is a minimal " +
          "UNPUBLISHED entry)</note>\n"
          + "  </unpublished>\n"
          + "\n"
          + "  <unpublished id=\"unpublished-full\">\n"
          + "    <author>Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul " +
          "{\\={P}}ot</author>\n"
          + "    <title>Lower Bounds for Wishful Research Results</title>\n"
          + "    <month><macro name=\"nov\"/> ,  <macro " +
          "name=\"dec\"/></month>\n"
          + "    <year>1988</year>\n"
          + "    <note>Talk at Fanstord University (this is a full " +
          "UNPUBLISHED entry)</note>\n"
          + "  </unpublished>\n"
          + "\n"
          + "  <misc id=\"random-note-crossref\">\n"
          + "    <key>Volume-2</key>\n"
          + "    <note>Volume~2 is listed under Knuth " +
          "\\cite{book-full}</note>\n"
          + "  </misc>\n" + "\n" + "</database>\n";

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
    new BibPrinterXMLImpl( new StringBufferWriter( buffer ) ).print( db );

    assertEquals( RESULT_1, buffer.toString().replaceAll( "\r", "" ) );
  }

}
