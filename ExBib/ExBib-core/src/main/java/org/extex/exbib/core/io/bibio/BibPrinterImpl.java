/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.io.bibio;

import org.extex.exbib.core.db.*;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.bibio.options.Cased;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * This class writes a bibliographic database to a writer. The format used is
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibPrinterImpl
    implements BibPrinter, ValueVisitor, SectionVisitor {

  /**
   * The field {@code writer} contains the target writer.
   */
  private final Writer writer;

  /**
   * The field {@code preamblePrefix} contains the preamble prefix.
   */
  private String preamblePrefix = "@Preamble{";

  /**
   * The field {@code preamblePostfix} contains the preamble postfix.
   */
  private String preamblePostfix = "}\n\n";

  /**
   * The field {@code stringPrefix} contains the string prefix.
   */
  private String stringPrefix = "@String{";

  /**
   * The field {@code stringInfix} contains the string infix.
   */
  private String stringInfix = " = ";

  /**
   * The field {@code stringPostfix} contains the string postfix.
   */
  private String stringPostfix = "}\n";

  /**
   * The field {@code stringKeyCase} contains the string key case.
   */
  private Cased stringKeyCase = Cased.AsIs;

  /**
   * The field {@code entryPrefix} contains the entry prefix.
   */
  private String entryPrefix = "\n@";

  /**
   * The field {@code entryInfix} contains the entry infix.
   */
  private String entryInfix = "{ ";

  /**
   * The field {@code entryPostfix} contains the entry postfix.
   */
  private String entryPostfix = "\n}\n";

  /**
   * The field {@code entryKeyCase} contains the entry key case.
   */
  private Cased entryKeyCase = Cased.AsIs;

  /**
   * The field {@code entryTypeCase} contains the entry type case.
   */
  private Cased entryTypeCase = Cased.AsIs;

  /**
   * The field {@code fieldPrefix} contains the field prefix.
   */
  private String fieldPrefix = "\t";

  /**
   * The field {@code fieldInfix} contains the field infix.
   */
  private String fieldInfix = " = ";

  /**
   * The field {@code fieldPostfix} contains the field postfix.
   */
  private String fieldPostfix = ",\n";

  /**
   * The field {@code fieldKeyCase} contains the field key case.
   */
  private Cased fieldKeyCase = Cased.AsIs;

  /**
   * The field {@code sections} contains the sections.
   */
  private Section[] sections = new Section[]{Section.Preamble,
      Section.Strings, Section.Entries};

  /**
   * Creates a new object.
   *
   * @param writer the target writer
   */
  public BibPrinterImpl( Writer writer ) {

    this.writer = writer;
  }

  /**
   * Getter for entryInfix.
   *
   * @return the entryInfix
   */
  protected String getEntryInfix() {

    return entryInfix;
  }

  /**
   * Getter for entryKeyCase.
   *
   * @return the entryKeyCase
   */
  protected Cased getEntryKeyCase() {

    return entryKeyCase;
  }

  /**
   * Getter for entryPostfix.
   *
   * @return the entryPostfix
   */
  protected String getEntryPostfix() {

    return entryPostfix;
  }

  /**
   * Getter for entryPrefix.
   *
   * @return the entryPrefix
   */
  protected String getEntryPrefix() {

    return entryPrefix;
  }

  /**
   * Getter for entryTypeCase.
   *
   * @return the entryTypeCase
   */
  protected Cased getEntryTypeCase() {

    return entryTypeCase;
  }

  /**
   * Getter for fieldInfix.
   *
   * @return the fieldInfix
   */
  protected String getFieldInfix() {

    return fieldInfix;
  }

  /**
   * Getter for fieldKeyCase.
   *
   * @return the fieldKeyCase
   */
  protected Cased getFieldKeyCase() {

    return fieldKeyCase;
  }

  /**
   * Getter for fieldPostfix.
   *
   * @return the fieldPostfix
   */
  protected String getFieldPostfix() {

    return fieldPostfix;
  }

  /**
   * Getter for fieldPrefix.
   *
   * @return the fieldPrefix
   */
  protected String getFieldPrefix() {

    return fieldPrefix;
  }

  /**
   * Getter for preamblePostfix.
   *
   * @return the preamblePostfix
   */
  protected String getPreamblePostfix() {

    return preamblePostfix;
  }

  /**
   * Getter for preamblePrefix.
   *
   * @return the preamblePrefix
   */
  protected String getPreamblePrefix() {

    return preamblePrefix;
  }

  /**
   * Getter for sections.
   *
   * @return the sections
   */
  protected Section[] getSections() {

    return sections;
  }

  /**
   * Getter for stringInfix.
   *
   * @return the stringInfix
   */
  protected String getStringInfix() {

    return stringInfix;
  }

  /**
   * Getter for stringKeyCase.
   *
   * @return the stringKeyCase
   */
  protected Cased getStringKeyCase() {

    return stringKeyCase;
  }

  /**
   * Getter for stringPostfix.
   *
   * @return the stringPostfix
   */
  protected String getStringPostfix() {

    return stringPostfix;
  }

  /**
   * Getter for stringPrefix.
   *
   * @return the stringPrefix
   */
  protected String getStringPrefix() {

    return stringPrefix;
  }

  /**
   * Prints as database as B<small>IB</small><span
   * style="margin-left: -0.15em;" >T</span><span style=
   * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
   * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
   * >e</span>X file.
   *
   * @param db the database context
   * @throws IOException in case of an I/O error
   */
  public void print( DB db ) throws IOException {

    if( writer == null ) {
      return;
    }

    for( Section sec : sections ) {

      sec.visit( writer, this, db );
    }
  }

  /**
   * Setter for entryInfix.
   *
   * @param entryInfix the entryInfix to set
   */
  protected void setEntryInfix( String entryInfix ) {

    this.entryInfix = entryInfix;
  }

  /**
   * Setter for entryKeyCase.
   *
   * @param entryKeyCase the entryKeyCase to set
   */
  protected void setEntryKeyCase( Cased entryKeyCase ) {

    this.entryKeyCase = entryKeyCase;
  }

  /**
   * Setter for entryPostfix.
   *
   * @param entryPostfix the entryPostfix to set
   */
  protected void setEntryPostfix( String entryPostfix ) {

    this.entryPostfix = entryPostfix;
  }

  /**
   * Setter for entryPrefix.
   *
   * @param entryPrefix the entryPrefix to set
   */
  protected void setEntryPrefix( String entryPrefix ) {

    this.entryPrefix = entryPrefix;
  }

  /**
   * Setter for entryTypeCase.
   *
   * @param entryTypeCase the entryTypeCase to set
   */
  protected void setEntryTypeCase( Cased entryTypeCase ) {

    this.entryTypeCase = entryTypeCase;
  }

  /**
   * Setter for fieldInfix.
   *
   * @param fieldInfix the fieldInfix to set
   */
  protected void setFieldInfix( String fieldInfix ) {

    this.fieldInfix = fieldInfix;
  }

  /**
   * Setter for fieldKeyCase.
   *
   * @param fieldKeyCase the fieldKeyCase to set
   */
  protected void setFieldKeyCase( Cased fieldKeyCase ) {

    this.fieldKeyCase = fieldKeyCase;
  }

  /**
   * Setter for fieldPostfix.
   *
   * @param fieldPostfix the fieldPostfix to set
   */
  protected void setFieldPostfix( String fieldPostfix ) {

    this.fieldPostfix = fieldPostfix;
  }

  /**
   * Setter for fieldPrefix.
   *
   * @param fieldPrefix the fieldPrefix to set
   */
  protected void setFieldPrefix( String fieldPrefix ) {

    this.fieldPrefix = fieldPrefix;
  }

  /**
   * Setter for preamblePostfix.
   *
   * @param preamblePostfix the preamblePostfix to set
   */
  protected void setPreamblePostfix( String preamblePostfix ) {

    this.preamblePostfix = preamblePostfix;
  }

  /**
   * Setter for preamblePrefix.
   *
   * @param preamblePrefix the preamblePrefix to set
   */
  protected void setPreamblePrefix( String preamblePrefix ) {

    this.preamblePrefix = preamblePrefix;
  }

  /**
   * Setter for sections.
   *
   * @param sections the sections to set
   */
  protected void setSections( Section[] sections ) {

    this.sections = sections;
  }

  /**
   * Setter for stringInfix.
   *
   * @param stringInfix the stringInfix to set
   */
  protected void setStringInfix( String stringInfix ) {

    this.stringInfix = stringInfix;
  }

  /**
   * Setter for stringKeyCase.
   *
   * @param stringKeyCase the stringKeyCase to set
   */
  protected void setStringKeyCase( Cased stringKeyCase ) {

    this.stringKeyCase = stringKeyCase;
  }

  /**
   * Setter for stringPostfix.
   *
   * @param stringPostfix the stringPostfix to set
   */
  protected void setStringPostfix( String stringPostfix ) {

    this.stringPostfix = stringPostfix;
  }

  /**
   * Setter for stringPrefix.
   *
   * @param stringPrefix the stringPrefix to set
   */
  protected void setStringPrefix( String stringPrefix ) {

    this.stringPrefix = stringPrefix;
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitBlock( VBlock value, DB db ) throws IOException {

    writer.print( "{", value.getContent(), "}" );
  }

  public void visitEntries( DB db ) throws IOException {

    for( Entry e : db ) {
      writer.print( entryPrefix );
      entryTypeCase.write( writer, e.getType() );
      writer.print( entryInfix );
      entryKeyCase.write( writer, e.getKey() );
      for( String key : e.getKeys() ) {
        writer.print( fieldPostfix );
        writer.print( fieldPrefix );
        fieldKeyCase.write( writer, key );
        writer.print( fieldInfix );
        e.get( key ).visit( this, db );
      }

      writer.print( entryPostfix );
    }
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitMacro( VMacro value, DB db ) throws IOException {

    writer.print( value.getContent() );
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitNumber( VNumber value, DB db ) throws IOException {

    writer.print( value.getContent() );
  }

  public void visitPreamble( DB db ) throws IOException {

    Value preamble = db.getPreamble();

    if( preamble != null && !preamble.isEmpty() ) {
      writer.print( preamblePrefix );
      preamble.visit( this, db );
      writer.print( preamblePostfix );
    }
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitString( VString value, DB db ) throws IOException {

    writer.print( "\"", value.getContent(), "\"" );
  }

  public void visitStrings( DB db ) throws IOException {

    List<String> macroNames = db.getMacroNames();
    Collections.sort( macroNames );

    for( String name : macroNames ) {
      writer.print( stringPrefix );
      stringKeyCase.write( writer, name );
      writer.print( stringInfix );
      db.getMacro( name ).visit( this, db );
      writer.print( stringPostfix );
    }
  }

  /**
   * org.extex.exbib.core.db.DB)
   */
  public void visitValue( Value value, DB db ) throws IOException {

    boolean first = true;

    for( ValueItem item : value ) {

      if( first ) {
        first = false;
      }
      else {
        writer.print( " # " );
      }
      item.visit( this, db );
    }
  }

}
