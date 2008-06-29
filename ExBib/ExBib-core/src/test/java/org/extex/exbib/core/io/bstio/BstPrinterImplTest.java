/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bstio;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstInterpreterCore;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.StringBufferWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for {@link BstPrinterImpl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BstPrinterImplTest {

    /**
     * The field <tt>RESULT_1</tt> contains the result for test case 1.
     */
    private final String RESULT_1 =
            "ENTRY {\n" //
                    + "    address\n" //
                    + "    author\n" //
                    + "    booktitle\n" //
                    + "    chapter\n" //
                    + "    edition\n" //
                    + "    editor\n" //
                    + "    howpublished\n" //
                    + "    institution\n" //
                    + "    journal\n" //
                    + "    key\n" //
                    + "    month\n" //
                    + "    note\n" //
                    + "    number\n" //
                    + "    organization\n" //
                    + "    pages\n" //
                    + "    publisher\n" //
                    + "    school\n" //
                    + "    series\n" //
                    + "    title\n" //
                    + "    type\n" //
                    + "    volume\n" //
                    + "    year\n" //
                    + "  } {} {\n" //
                    + "    label\n" //
                    + "  }\n" //
                    + "\n" //
                    + "INTEGERS {\n" //
                    + "    after.block\n" //
                    + "    after.sentence\n" //
                    + "    before.all\n" //
                    + "    len\n" //
                    + "    longest.label.width\n" //
                    + "    mid.sentence\n" //
                    + "    multiresult\n" //
                    + "    nameptr\n" //
                    + "    namesleft\n" //
                    + "    number.label\n" //
                    + "    numnames\n" //
                    + "    output.state\n" //
                    + "}\n" //
                    + "\n" //
                    + "STRINGS {\n" //
                    + "    longest.label\n" //
                    + "    s\n" //
                    + "    t\n" //
                    + "}\n" //
                    + "\n" //
                    + "FUNCTION { and }{\n" //
                    + "    'skip$\n" //
                    + "    { pop$\n" //
                    + "        #0    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { article }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.title \"title\" output.check new.block crossref\n" //
                    + "    missing$\n" //
                    + "    { journal\n" //
                    + "        emphasize \"journal\" output.check format.vol.num.pages output format.date \"year\" output.check    }\n" //
                    + "        { format.article.crossref output.nonnull format.pages output    }\n" //
                    + "        if$\n" //
                    + "    new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { author.editor.sort }{\n" //
                    + "    author\n" //
                    + "    empty$\n" //
                    + "    { editor\n" //
                    + "        empty$\n" //
                    + "        { key\n" //
                    + "            empty$\n" //
                    + "            { \"to sort, need author, editor, or key in \" cite$\n" //
                    + "                *\n" //
                    + "                warning$\n" //
                    + "                \"\"            }\n" //
                    + "                { key\n" //
                    + "                sortify            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            { editor\n" //
                    + "            sort.format.names        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { author\n" //
                    + "        sort.format.names    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { author.organization.sort }{\n" //
                    + "    author\n" //
                    + "    empty$\n" //
                    + "    { organization\n" //
                    + "        empty$\n" //
                    + "        { key\n" //
                    + "            empty$\n" //
                    + "            { \"to sort, need author, organization, or key in \" cite$\n" //
                    + "                *\n" //
                    + "                warning$\n" //
                    + "                \"\"            }\n" //
                    + "                { key\n" //
                    + "                sortify            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            { \"The \" #4 organization\n" //
                    + "            chop.word sortify        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { author\n" //
                    + "        sort.format.names    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { author.sort }{\n" //
                    + "    author\n" //
                    + "    empty$\n" //
                    + "    { key\n" //
                    + "        empty$\n" //
                    + "        { \"to sort, need author or key in \" cite$\n" //
                    + "            *\n" //
                    + "            warning$\n" //
                    + "            \"\"        }\n" //
                    + "            { key\n" //
                    + "            sortify        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { author\n" //
                    + "        sort.format.names    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { begin.bib }{\n" //
                    + "    preamble$\n" //
                    + "    empty$\n" //
                    + "    'skip$\n" //
                    + "    { preamble$\n" //
                    + "        write$\n" //
                    + "        newline$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    \"\\begin{thebibliography}{\" longest.label *\n" //
                    + "    \"}\" *\n" //
                    + "    write$\n" //
                    + "    newline$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { book }{\n" //
                    + "    output.bibitem author\n" //
                    + "    empty$\n" //
                    + "    { format.editors \"author and editor\" output.check    }\n" //
                    + "        { format.authors output.nonnull crossref\n" //
                    + "        missing$\n" //
                    + "        { \"author and editor\" editor\n" //
                    + "            either.or.check        }\n" //
                    + "            'skip$ if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    new.block format.btitle \"title\" output.check crossref\n" //
                    + "    missing$\n" //
                    + "    { format.bvolume output new.block format.number.series output new.sentence publisher\n" //
                    + "        \"publisher\" output.check address\n" //
                    + "        output    }\n" //
                    + "        { new.block format.book.crossref output.nonnull    }\n" //
                    + "        if$\n" //
                    + "    format.edition output format.date \"year\" output.check new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { booklet }{\n" //
                    + "    output.bibitem format.authors output new.block format.title \"title\" output.check howpublished\n" //
                    + "    address\n" //
                    + "    new.block.checkb howpublished\n" //
                    + "    output address\n" //
                    + "    output format.date output new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { chop.word }{\n" //
                    + "    's :=\n" //
                    + "    'len :=\n" //
                    + "    s #1 len substring$\n" //
                    + "    =\n" //
                    + "    { s len #1 +\n" //
                    + "        global.max$\n" //
                    + "        substring$\n" //
                    + "            }\n" //
                    + "        's if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { conference }{\n" //
                    + "    inproceedings}\n" //
                    + "\n" //
                    + "FUNCTION { default.type }{\n" //
                    + "    misc}\n" //
                    + "\n" //
                    + "FUNCTION { editor.organization.sort }{\n" //
                    + "    editor\n" //
                    + "    empty$\n" //
                    + "    { organization\n" //
                    + "        empty$\n" //
                    + "        { key\n" //
                    + "            empty$\n" //
                    + "            { \"to sort, need editor, organization, or key in \" cite$\n" //
                    + "                *\n" //
                    + "                warning$\n" //
                    + "                \"\"            }\n" //
                    + "                { key\n" //
                    + "                sortify            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            { \"The \" #4 organization\n" //
                    + "            chop.word sortify        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { editor\n" //
                    + "        sort.format.names    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { either.or.check }{\n" //
                    + "    empty$\n" //
                    + "    'pop$\n" //
                    + "    { \"can't use both \" swap$\n" //
                    + "        *\n" //
                    + "        \" fields in \" *\n" //
                    + "        cite$\n" //
                    + "        *\n" //
                    + "        warning$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { emphasize }{\n" //
                    + "    duplicate$\n" //
                    + "    empty$\n" //
                    + "    { pop$\n" //
                    + "        \"\"    }\n" //
                    + "        { \"{\\em \" swap$\n" //
                    + "        *\n" //
                    + "        \"}\" *\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { empty.misc.check }{\n" //
                    + "    author\n" //
                    + "    empty$\n" //
                    + "    title\n" //
                    + "    empty$\n" //
                    + "    howpublished\n" //
                    + "    empty$\n" //
                    + "    month\n" //
                    + "    empty$\n" //
                    + "    year\n" //
                    + "    empty$\n" //
                    + "    note\n" //
                    + "    empty$\n" //
                    + "    and and and and and key\n" //
                    + "    empty$\n" //
                    + "    not and\n" //
                    + "    { \"all relevant fields are empty in \" cite$\n" //
                    + "        *\n" //
                    + "        warning$\n" //
                    + "            }\n" //
                    + "        'skip$ if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { end.bib }{\n" //
                    + "    newline$\n" //
                    + "    \"\\end{thebibliography}\" write$\n" //
                    + "    newline$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { field.or.null }{\n" //
                    + "    duplicate$\n" //
                    + "    empty$\n" //
                    + "    { pop$\n" //
                    + "        \"\"    }\n" //
                    + "        'skip$ if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { fin.entry }{\n" //
                    + "    add.period$\n" //
                    + "    write$\n" //
                    + "    newline$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.article.crossref }{\n" //
                    + "    key\n" //
                    + "    empty$\n" //
                    + "    { journal\n" //
                    + "        empty$\n" //
                    + "        { \"need key or journal for \" cite$\n" //
                    + "            *\n" //
                    + "            \" to crossref \" *\n" //
                    + "            crossref\n" //
                    + "            *\n" //
                    + "            warning$\n" //
                    + "            \"\"        }\n" //
                    + "            { \"In {\\em \" journal\n" //
                    + "            *\n" //
                    + "            \"\\/}\" *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { \"In \" key\n" //
                    + "        *\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    \" \\cite{\" *\n" //
                    + "    crossref\n" //
                    + "    *\n" //
                    + "    \"}\" *\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.authors }{\n" //
                    + "    author\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { author\n" //
                    + "        format.names    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.book.crossref }{\n" //
                    + "    volume\n" //
                    + "    empty$\n" //
                    + "    { \"empty volume in \" cite$\n" //
                    + "        *\n" //
                    + "        \"'s crossref of \" *\n" //
                    + "        crossref\n" //
                    + "        *\n" //
                    + "        warning$\n" //
                    + "        \"In \"    }\n" //
                    + "        { \"Volume\" volume\n" //
                    + "        tie.or.space.connect \" of \" *\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    editor\n" //
                    + "    empty$\n" //
                    + "    editor\n" //
                    + "    field.or.null author\n" //
                    + "    field.or.null =\n" //
                    + "    or\n" //
                    + "    { key\n" //
                    + "        empty$\n" //
                    + "        { series\n" //
                    + "            empty$\n" //
                    + "            { \"need editor, key, or series for \" cite$\n" //
                    + "                *\n" //
                    + "                \" to crossref \" *\n" //
                    + "                crossref\n" //
                    + "                *\n" //
                    + "                warning$\n" //
                    + "                \"\" *\n" //
                    + "                            }\n" //
                    + "                { \"{\\em \" *\n" //
                    + "                series\n" //
                    + "                *\n" //
                    + "                \"\\/}\" *\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            { key\n" //
                    + "            *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { format.crossref.editor *\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    \" \\cite{\" *\n" //
                    + "    crossref\n" //
                    + "    *\n" //
                    + "    \"}\" *\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.btitle }{\n" //
                    + "    title\n" //
                    + "    emphasize}\n" //
                    + "\n" //
                    + "FUNCTION { format.bvolume }{\n" //
                    + "    volume\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { \"volume\" volume\n" //
                    + "        tie.or.space.connect series\n" //
                    + "        empty$\n" //
                    + "        'skip$\n" //
                    + "        { \" of \" *\n" //
                    + "            series\n" //
                    + "            emphasize *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "        \"volume and number\" number\n" //
                    + "        either.or.check    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.chapter.pages }{\n" //
                    + "    chapter\n" //
                    + "    empty$\n" //
                    + "    'format.pages\n" //
                    + "    { type\n" //
                    + "        empty$\n" //
                    + "        { \"chapter\"        }\n" //
                    + "            { type\n" //
                    + "            \"l\" change.case$\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "        chapter\n" //
                    + "        tie.or.space.connect pages\n" //
                    + "        empty$\n" //
                    + "        'skip$\n" //
                    + "        { \", \" *\n" //
                    + "            format.pages *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.crossref.editor }{\n" //
                    + "    editor\n" //
                    + "    #1 \"{vv~}{ll}\" format.name$\n" //
                    + "    editor\n" //
                    + "    num.names$\n" //
                    + "    duplicate$\n" //
                    + "    #2 >\n" //
                    + "    { pop$\n" //
                    + "        \" et~al.\" *\n" //
                    + "            }\n" //
                    + "        { #2 <\n" //
                    + "        'skip$\n" //
                    + "        { editor\n" //
                    + "            #2 \"{ff }{vv }{ll}{ jj}\" format.name$\n" //
                    + "            \"others\" =\n" //
                    + "            { \" et~al.\" *\n" //
                    + "                            }\n" //
                    + "                { \" and \" *\n" //
                    + "                editor\n" //
                    + "                #2 \"{vv~}{ll}\" format.name$\n" //
                    + "                *\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.date }{\n" //
                    + "    year\n" //
                    + "    empty$\n" //
                    + "    { month\n" //
                    + "        empty$\n" //
                    + "        { \"\"        }\n" //
                    + "            { \"there's a month but no year in \" cite$\n" //
                    + "            *\n" //
                    + "            warning$\n" //
                    + "            month\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { month\n" //
                    + "        empty$\n" //
                    + "        'year\n" //
                    + "        { month\n" //
                    + "            \" \" *\n" //
                    + "            year\n" //
                    + "            *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.edition }{\n" //
                    + "    edition\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { output.state mid.sentence =\n" //
                    + "        { edition\n" //
                    + "            \"l\" change.case$\n" //
                    + "            \" edition\" *\n" //
                    + "                    }\n" //
                    + "            { edition\n" //
                    + "            \"t\" change.case$\n" //
                    + "            \" edition\" *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.editors }{\n" //
                    + "    editor\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { editor\n" //
                    + "        format.names editor\n" //
                    + "        num.names$\n" //
                    + "        #1 >\n" //
                    + "        { \", editors\" *\n" //
                    + "                    }\n" //
                    + "            { \", editor\" *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.in.ed.booktitle }{\n" //
                    + "    booktitle\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { editor\n" //
                    + "        empty$\n" //
                    + "        { \"In \" booktitle\n" //
                    + "            emphasize *\n" //
                    + "                    }\n" //
                    + "            { \"In \" format.editors *\n" //
                    + "            \", \" *\n" //
                    + "            booktitle\n" //
                    + "            emphasize *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.incoll.inproc.crossref }{\n" //
                    + "    editor\n" //
                    + "    empty$\n" //
                    + "    editor\n" //
                    + "    field.or.null author\n" //
                    + "    field.or.null =\n" //
                    + "    or\n" //
                    + "    { key\n" //
                    + "        empty$\n" //
                    + "        { booktitle\n" //
                    + "            empty$\n" //
                    + "            { \"need editor, key, or booktitle for \" cite$\n" //
                    + "                *\n" //
                    + "                \" to crossref \" *\n" //
                    + "                crossref\n" //
                    + "                *\n" //
                    + "                warning$\n" //
                    + "                \"\"            }\n" //
                    + "                { \"In {\\em \" booktitle\n" //
                    + "                *\n" //
                    + "                \"\\/}\" *\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            { \"In \" key\n" //
                    + "            *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { \"In \" format.crossref.editor *\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    \" \\cite{\" *\n" //
                    + "    crossref\n" //
                    + "    *\n" //
                    + "    \"}\" *\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.names }{\n" //
                    + "    's :=\n" //
                    + "    #1 'nameptr :=\n" //
                    + "    s num.names$\n" //
                    + "    'numnames :=\n" //
                    + "    numnames 'namesleft :=\n" //
                    + "    { namesleft #0 >\n" //
                    + "            }\n" //
                    + "        { s nameptr \"{ff~}{vv~}{ll}{, jj}\" format.name$\n" //
                    + "        't :=\n" //
                    + "        nameptr #1 >\n" //
                    + "        { namesleft #1 >\n" //
                    + "            { \", \" *\n" //
                    + "                t *\n" //
                    + "                            }\n" //
                    + "                { numnames #2 >\n" //
                    + "                { \",\" *\n" //
                    + "                                    }\n" //
                    + "                    'skip$ if$\n" //
                    + "                t \"others\" =\n" //
                    + "                { \" et~al.\" *\n" //
                    + "                                    }\n" //
                    + "                    { \" and \" *\n" //
                    + "                    t *\n" //
                    + "                                    }\n" //
                    + "                    if$\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            't if$\n" //
                    + "        nameptr #1 +\n" //
                    + "        'nameptr :=\n" //
                    + "        namesleft #1 -\n" //
                    + "        'namesleft :=\n" //
                    + "            }\n" //
                    + "        while$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.number.series }{\n" //
                    + "    volume\n" //
                    + "    empty$\n" //
                    + "    { number\n" //
                    + "        empty$\n" //
                    + "        { series\n" //
                    + "            field.or.null        }\n" //
                    + "            { output.state mid.sentence =\n" //
                    + "            { \"number\"            }\n" //
                    + "                { \"Number\"            }\n" //
                    + "                if$\n" //
                    + "            number\n" //
                    + "            tie.or.space.connect series\n" //
                    + "            empty$\n" //
                    + "            { \"there's a number but no series in \" cite$\n" //
                    + "                *\n" //
                    + "                warning$\n" //
                    + "                            }\n" //
                    + "                { \" in \" *\n" //
                    + "                series\n" //
                    + "                *\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { \"\"    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.pages }{\n" //
                    + "    pages\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { pages\n" //
                    + "        multi.page.check\n" //
                    + "        { \"pages\" pages\n" //
                    + "            n.dashify tie.or.space.connect        }\n" //
                    + "            { \"page\" pages\n" //
                    + "            tie.or.space.connect        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.thesis.type }{\n" //
                    + "    type\n" //
                    + "    empty$\n" //
                    + "    'skip$\n" //
                    + "    { pop$\n" //
                    + "        type\n" //
                    + "        \"t\" change.case$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.title }{\n" //
                    + "    title\n" //
                    + "    empty$\n" //
                    + "    { \"\"    }\n" //
                    + "        { title\n" //
                    + "        \"t\" change.case$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.tr.number }{\n" //
                    + "    type\n" //
                    + "    empty$\n" //
                    + "    { \"Technical Report\"    }\n" //
                    + "        'type if$\n" //
                    + "    number\n" //
                    + "    empty$\n" //
                    + "    { \"t\" change.case$\n" //
                    + "            }\n" //
                    + "        { number\n" //
                    + "        tie.or.space.connect    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { format.vol.num.pages }{\n" //
                    + "    volume\n" //
                    + "    field.or.null number\n" //
                    + "    empty$\n" //
                    + "    'skip$\n" //
                    + "    { \"(\" number\n" //
                    + "        *\n" //
                    + "        \")\" *\n" //
                    + "        *\n" //
                    + "        volume\n" //
                    + "        empty$\n" //
                    + "        { \"there's a number but no volume in \" cite$\n" //
                    + "            *\n" //
                    + "            warning$\n" //
                    + "                    }\n" //
                    + "            'skip$ if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    pages\n" //
                    + "    empty$\n" //
                    + "    'skip$\n" //
                    + "    { duplicate$\n" //
                    + "        empty$\n" //
                    + "        { pop$\n" //
                    + "            format.pages        }\n" //
                    + "            { \":\" *\n" //
                    + "            pages\n" //
                    + "            n.dashify *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { inbook }{\n" //
                    + "    output.bibitem author\n" //
                    + "    empty$\n" //
                    + "    { format.editors \"author and editor\" output.check    }\n" //
                    + "        { format.authors output.nonnull crossref\n" //
                    + "        missing$\n" //
                    + "        { \"author and editor\" editor\n" //
                    + "            either.or.check        }\n" //
                    + "            'skip$ if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    new.block format.btitle \"title\" output.check crossref\n" //
                    + "    missing$\n" //
                    + "    { format.bvolume output format.chapter.pages \"chapter and pages\" output.check new.block format.number.series output new.sentence publisher\n" //
                    + "        \"publisher\" output.check address\n" //
                    + "        output    }\n" //
                    + "        { format.chapter.pages \"chapter and pages\" output.check new.block format.book.crossref output.nonnull    }\n" //
                    + "        if$\n" //
                    + "    format.edition output format.date \"year\" output.check new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { incollection }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.title \"title\" output.check new.block crossref\n" //
                    + "    missing$\n" //
                    + "    { format.in.ed.booktitle \"booktitle\" output.check format.bvolume output format.number.series output format.chapter.pages output new.sentence publisher\n" //
                    + "        \"publisher\" output.check address\n" //
                    + "        output format.edition output format.date \"year\" output.check    }\n" //
                    + "        { format.incoll.inproc.crossref output.nonnull format.chapter.pages output    }\n" //
                    + "        if$\n" //
                    + "    new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { init.state.consts }{\n" //
                    + "    #0 'before.all :=\n" //
                    + "    #1 'mid.sentence :=\n" //
                    + "    #2 'after.sentence :=\n" //
                    + "    #3 'after.block :=\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { initialize.longest.label }{\n" //
                    + "    \"\" 'longest.label :=\n" //
                    + "    #1 'number.label :=\n" //
                    + "    #0 'longest.label.width :=\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { inproceedings }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.title \"title\" output.check new.block crossref\n" //
                    + "    missing$\n" //
                    + "    { format.in.ed.booktitle \"booktitle\" output.check format.bvolume output format.number.series output format.pages output address\n" //
                    + "        empty$\n" //
                    + "        { organization\n" //
                    + "            publisher\n" //
                    + "            new.sentence.checkb organization\n" //
                    + "            output publisher\n" //
                    + "            output format.date \"year\" output.check        }\n" //
                    + "            { address\n" //
                    + "            output.nonnull format.date \"year\" output.check new.sentence organization\n" //
                    + "            output publisher\n" //
                    + "            output        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { format.incoll.inproc.crossref output.nonnull format.pages output    }\n" //
                    + "        if$\n" //
                    + "    new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { longest.label.pass }{\n" //
                    + "    number.label int.to.str$\n" //
                    + "    'label :=\n" //
                    + "    number.label #1 +\n" //
                    + "    'number.label :=\n" //
                    + "    label\n" //
                    + "    width$\n" //
                    + "    longest.label.width >\n" //
                    + "    { label\n" //
                    + "        'longest.label :=\n" //
                    + "        label\n" //
                    + "        width$\n" //
                    + "        'longest.label.width :=\n" //
                    + "            }\n" //
                    + "        'skip$ if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { manual }{\n" //
                    + "    output.bibitem author\n" //
                    + "    empty$\n" //
                    + "    { organization\n" //
                    + "        empty$\n" //
                    + "        'skip$\n" //
                    + "        { organization\n" //
                    + "            output.nonnull address\n" //
                    + "            output        }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        { format.authors output.nonnull    }\n" //
                    + "        if$\n" //
                    + "    new.block format.btitle \"title\" output.check author\n" //
                    + "    empty$\n" //
                    + "    { organization\n" //
                    + "        empty$\n" //
                    + "        { address\n" //
                    + "            new.block.checka address\n" //
                    + "            output        }\n" //
                    + "            'skip$ if$\n" //
                    + "            }\n" //
                    + "        { organization\n" //
                    + "        address\n" //
                    + "        new.block.checkb organization\n" //
                    + "        output address\n" //
                    + "        output    }\n" //
                    + "        if$\n" //
                    + "    format.edition output format.date output new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { mastersthesis }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.title \"title\" output.check new.block \"Master's thesis\" format.thesis.type output.nonnull school\n" //
                    + "    \"school\" output.check address\n" //
                    + "    output format.date \"year\" output.check new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { misc }{\n" //
                    + "    output.bibitem format.authors output title\n" //
                    + "    howpublished\n" //
                    + "    new.block.checkb format.title output howpublished\n" //
                    + "    new.block.checka howpublished\n" //
                    + "    output format.date output new.block note\n" //
                    + "    output fin.entry empty.misc.check}\n" //
                    + "\n" //
                    + "FUNCTION { multi.page.check }{\n" //
                    + "    't :=\n" //
                    + "    #0 'multiresult :=\n" //
                    + "    { multiresult not t empty$\n" //
                    + "        not and    }\n" //
                    + "        { t #1 #1 substring$\n" //
                    + "        duplicate$\n" //
                    + "        \"-\" =\n" //
                    + "        swap$\n" //
                    + "        duplicate$\n" //
                    + "        \",\" =\n" //
                    + "        swap$\n" //
                    + "        \"+\" =\n" //
                    + "        or or\n" //
                    + "        { #1 'multiresult :=\n" //
                    + "                    }\n" //
                    + "            { t #2 global.max$\n" //
                    + "            substring$\n" //
                    + "            't :=\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        while$\n" //
                    + "    multiresult}\n" //
                    + "\n" //
                    + "FUNCTION { n.dashify }{\n" //
                    + "    't :=\n" //
                    + "    \"\"\n" //
                    + "    { t empty$\n" //
                    + "        not    }\n" //
                    + "        { t #1 #1 substring$\n" //
                    + "        \"-\" =\n" //
                    + "        { t #1 #2 substring$\n" //
                    + "            \"--\" =\n" //
                    + "            not\n" //
                    + "            { \"--\" *\n" //
                    + "                t #2 global.max$\n" //
                    + "                substring$\n" //
                    + "                't :=\n" //
                    + "                            }\n" //
                    + "                {\n" //
                    + "                { t #1 #1 substring$\n" //
                    + "                    \"-\" =\n" //
                    + "                                    }\n" //
                    + "                    { \"-\" *\n" //
                    + "                    t #2 global.max$\n" //
                    + "                    substring$\n" //
                    + "                    't :=\n" //
                    + "                                    }\n" //
                    + "                    while$\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            { t #1 #1 substring$\n" //
                    + "            *\n" //
                    + "            t #2 global.max$\n" //
                    + "            substring$\n" //
                    + "            't :=\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        while$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { new.block }{\n" //
                    + "    output.state before.all =\n" //
                    + "    'skip$\n" //
                    + "    { after.block 'output.state :=\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { new.block.checka }{\n" //
                    + "    empty$\n" //
                    + "    'skip$ 'new.block if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { new.block.checkb }{\n" //
                    + "    empty$\n" //
                    + "    swap$\n" //
                    + "    empty$\n" //
                    + "    and 'skip$ 'new.block if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { new.sentence }{\n" //
                    + "    output.state after.block =\n" //
                    + "    'skip$\n" //
                    + "    { output.state before.all =\n" //
                    + "        'skip$\n" //
                    + "        { after.sentence 'output.state :=\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { new.sentence.checka }{\n" //
                    + "    empty$\n" //
                    + "    'skip$ 'new.sentence if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { new.sentence.checkb }{\n" //
                    + "    empty$\n" //
                    + "    swap$\n" //
                    + "    empty$\n" //
                    + "    and 'skip$ 'new.sentence if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { not }{\n" //
                    + "    { #0    }\n" //
                    + "        { #1    }\n" //
                    + "        if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { or }{\n" //
                    + "    { pop$\n" //
                    + "        #1    }\n" //
                    + "        'skip$ if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { output }{\n" //
                    + "    duplicate$\n" //
                    + "    empty$\n" //
                    + "    'pop$ 'output.nonnull if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { output.bibitem }{\n" //
                    + "    newline$\n" //
                    + "    \"\\bibitem{\" write$\n" //
                    + "    cite$\n" //
                    + "    write$\n" //
                    + "    \"}\" write$\n" //
                    + "    newline$\n" //
                    + "    \"\" before.all 'output.state :=\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { output.check }{\n" //
                    + "    't :=\n" //
                    + "    duplicate$\n" //
                    + "    empty$\n" //
                    + "    { pop$\n" //
                    + "        \"empty \" t *\n" //
                    + "        \" in \" *\n" //
                    + "        cite$\n" //
                    + "        *\n" //
                    + "        warning$\n" //
                    + "            }\n" //
                    + "        'output.nonnull if$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { output.nonnull }{\n" //
                    + "    's :=\n" //
                    + "    output.state mid.sentence =\n" //
                    + "    { \", \" *\n" //
                    + "        write$\n" //
                    + "            }\n" //
                    + "        { output.state after.block =\n" //
                    + "        { add.period$\n" //
                    + "            write$\n" //
                    + "            newline$\n" //
                    + "            \"\\newblock \" write$\n" //
                    + "                    }\n" //
                    + "            { output.state before.all =\n" //
                    + "            'write$\n" //
                    + "            { add.period$\n" //
                    + "                \" \" *\n" //
                    + "                write$\n" //
                    + "                            }\n" //
                    + "                if$\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "        mid.sentence 'output.state :=\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    s}\n" //
                    + "\n" //
                    + "FUNCTION { phdthesis }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.btitle \"title\" output.check new.block \"PhD thesis\" format.thesis.type output.nonnull school\n" //
                    + "    \"school\" output.check address\n" //
                    + "    output format.date \"year\" output.check new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { presort }{\n" //
                    + "    type$\n" //
                    + "    \"book\" =\n" //
                    + "    type$\n" //
                    + "    \"inbook\" =\n" //
                    + "    or 'author.editor.sort\n" //
                    + "    { type$\n" //
                    + "        \"proceedings\" =\n" //
                    + "        'editor.organization.sort\n" //
                    + "        { type$\n" //
                    + "            \"manual\" =\n" //
                    + "            'author.organization.sort 'author.sort if$\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "            }\n" //
                    + "        if$\n" //
                    + "    \"    \" *\n" //
                    + "    year\n" //
                    + "    field.or.null sortify *\n" //
                    + "    \"    \" *\n" //
                    + "    title\n" //
                    + "    field.or.null sort.format.title *\n" //
                    + "    #1 entry.max$\n" //
                    + "    substring$\n" //
                    + "    'sort.key$ :=\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { proceedings }{\n" //
                    + "    output.bibitem editor\n" //
                    + "    empty$\n" //
                    + "    { organization\n" //
                    + "        output    }\n" //
                    + "        { format.editors output.nonnull    }\n" //
                    + "        if$\n" //
                    + "    new.block format.btitle \"title\" output.check format.bvolume output format.number.series output address\n" //
                    + "    empty$\n" //
                    + "    { editor\n" //
                    + "        empty$\n" //
                    + "        { publisher\n" //
                    + "            new.sentence.checka        }\n" //
                    + "            { organization\n" //
                    + "            publisher\n" //
                    + "            new.sentence.checkb organization\n" //
                    + "            output        }\n" //
                    + "            if$\n" //
                    + "        publisher\n" //
                    + "        output format.date \"year\" output.check    }\n" //
                    + "        { address\n" //
                    + "        output.nonnull format.date \"year\" output.check new.sentence editor\n" //
                    + "        empty$\n" //
                    + "        'skip$\n" //
                    + "        { organization\n" //
                    + "            output        }\n" //
                    + "            if$\n" //
                    + "        publisher\n" //
                    + "        output    }\n" //
                    + "        if$\n" //
                    + "    new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { sort.format.names }{\n" //
                    + "    's :=\n" //
                    + "    #1 'nameptr :=\n" //
                    + "    \"\" s num.names$\n" //
                    + "    'numnames :=\n" //
                    + "    numnames 'namesleft :=\n" //
                    + "    { namesleft #0 >\n" //
                    + "            }\n" //
                    + "        { nameptr #1 >\n" //
                    + "        { \"   \" *\n" //
                    + "                    }\n" //
                    + "            'skip$ if$\n" //
                    + "        s nameptr \"{vv{ } }{ll{ }}{  ff{ }}{  jj{ }}\" format.name$\n" //
                    + "        't :=\n" //
                    + "        nameptr numnames =\n" //
                    + "        t \"others\" =\n" //
                    + "        and\n" //
                    + "        { \"et al\" *\n" //
                    + "                    }\n" //
                    + "            { t sortify *\n" //
                    + "                    }\n" //
                    + "            if$\n" //
                    + "        nameptr #1 +\n" //
                    + "        'nameptr :=\n" //
                    + "        namesleft #1 -\n" //
                    + "        'namesleft :=\n" //
                    + "            }\n" //
                    + "        while$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { sort.format.title }{\n" //
                    + "    't :=\n" //
                    + "    \"A \" #2 \"An \" #3 \"The \" #4 t chop.word chop.word chop.word sortify #1 global.max$\n" //
                    + "    substring$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { sortify }{\n" //
                    + "    purify$\n" //
                    + "    \"l\" change.case$\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { techreport }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.title \"title\" output.check new.block format.tr.number output.nonnull institution\n" //
                    + "    \"institution\" output.check address\n" //
                    + "    output format.date \"year\" output.check new.block note\n" //
                    + "    output fin.entry}\n" //
                    + "\n" //
                    + "FUNCTION { tie.or.space.connect }{\n" //
                    + "    duplicate$\n" //
                    + "    text.length$\n" //
                    + "    #3 <\n" //
                    + "    { \"~\"    }\n" //
                    + "        { \" \"    }\n" //
                    + "        if$\n" //
                    + "    swap$\n" //
                    + "    *\n" //
                    + "    *\n" //
                    + "    }\n" //
                    + "\n" //
                    + "FUNCTION { unpublished }{\n" //
                    + "    output.bibitem format.authors \"author\" output.check new.block format.title \"title\" output.check new.block note\n" //
                    + "    \"note\" output.check format.date output fin.entry}\n" //
                    + "\n" //
                    + "\n" //
                    + "MACRO { acmcs }{ \"ACM Computing Surveys\" }\n" //
                    + "MACRO { acta }{ \"Acta Informatica\" }\n" //
                    + "MACRO { apr }{ \"April\" }\n" //
                    + "MACRO { aug }{ \"August\" }\n" //
                    + "MACRO { cacm }{ \"Communications of the ACM\" }\n" //
                    + "MACRO { dec }{ \"December\" }\n" //
                    + "MACRO { feb }{ \"February\" }\n" //
                    + "MACRO { ibmjrd }{ \"IBM Journal of Research and Development\" }\n" //
                    + "MACRO { ibmsj }{ \"IBM Systems Journal\" }\n" //
                    + "MACRO { ieeese }{ \"IEEE Transactions on Software Engineering\" }\n" //
                    + "MACRO { ieeetc }{ \"IEEE Transactions on Computers\" }\n" //
                    + "MACRO { ieeetcad }{ \"IEEE Transactions on Computer-Aided Design of Integrated Circuits\" }\n" //
                    + "MACRO { ipl }{ \"Information Processing Letters\" }\n" //
                    + "MACRO { jacm }{ \"Journal of the ACM\" }\n" //
                    + "MACRO { jan }{ \"January\" }\n" //
                    + "MACRO { jcss }{ \"Journal of Computer and System Sciences\" }\n" //
                    + "MACRO { jul }{ \"July\" }\n" //
                    + "MACRO { jun }{ \"June\" }\n" //
                    + "MACRO { mar }{ \"March\" }\n" //
                    + "MACRO { may }{ \"May\" }\n" //
                    + "MACRO { nov }{ \"November\" }\n" //
                    + "MACRO { oct }{ \"October\" }\n" //
                    + "MACRO { scp }{ \"Science of Computer Programming\" }\n" //
                    + "MACRO { sep }{ \"September\" }\n" //
                    + "MACRO { sicomp }{ \"SIAM Journal on Computing\" }\n" //
                    + "MACRO { tcs }{ \"Theoretical Computer Science\" }\n" //
                    + "MACRO { tocs }{ \"ACM Transactions on Computer Systems\" }\n" //
                    + "MACRO { tods }{ \"ACM Transactions on Database Systems\" }\n" //
                    + "MACRO { tog }{ \"ACM Transactions on Graphics\" }\n" //
                    + "MACRO { toms }{ \"ACM Transactions on Mathematical Software\" }\n" //
                    + "MACRO { toois }{ \"ACM Transactions on Office Information Systems\" }\n" //
                    + "MACRO { toplas }{ \"ACM Transactions on Programming Languages and Systems\" }\n" //
                    + "\n" //
                    + "READ\n" //
                    + "ITERATE { presort }\n" //
                    + "SORT\n" //
                    + "EXECUTE { initialize.longest.label }\n" //
                    + "ITERATE { longest.label.pass }\n" //
                    + "EXECUTE { begin.bib }\n" //
                    + "EXECUTE { init.state.consts }\n" //
                    + "ITERATE { call.type$\n" //
                    + "     }\n" //
                    + "EXECUTE { end.bib }\n";

    /**
     * Run a test and compare the result.
     * 
     * @param processor the processor
     * @param expected the expected result
     * 
     * @throws IOException in case of an error
     * @throws ExBibException in case of an error
     */
    private void runTest(BstProcessor processor, String expected)
            throws IOException,
                ExBibException {

        StringBuffer buffer = new StringBuffer();
        Writer writer = new StringBufferWriter(buffer);
        new BstPrinterImpl(writer).print(processor);
        writer.close();
        assertEquals(expected, buffer.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test0() throws Exception {

        BstProcessor processor = new BstInterpreterCore();
        processor.setDB(new DBImpl());
        runTest(processor, "ENTRY {\n" + "  } {} {}\n\n\n\n");
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDB(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderImpl();
        r.setResourceFinder(new ResourceFinder() {

            public void enableTracing(boolean flag) {

                //
            }

            public NamedInputStream findResource(String name, String type)
                    throws ConfigurationException {

                try {
                    return new NamedInputStream(new FileInputStream(
                        "src/test/resources/bibtex/base/plain.bst"),
                        "plain.bst");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        r.parse(processor);
        runTest(processor, RESULT_1);
    }
}
