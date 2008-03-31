/*
 * Copyright (C) 2003-2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.io.bibio;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.KeyValue;
import org.extex.exbib.core.exceptions.ExBibEofException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibMissingKeyException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a reader for BibTeX files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class BibReaderImpl extends BibReader099Impl implements BibReader {

    /**
     * The field <tt>filenamePattern</tt> contains the pattern for file names.
     */
    private static Pattern filenamePattern = Pattern.compile("[^{}]*");

    /**
     * Creates a new object.
     * 
     * @throws FileNotFoundException is thrown if the file could not be opened
     *         for reading
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public BibReaderImpl() throws FileNotFoundException, ConfigurationException {

        super();
    }

    /**
     * This is the extension mechanism to define handlers for special items in
     * the file.
     * <p>
     * Any item name is passed to this handler to be processed.
     * </p>
     * <p>
     * This method is meant to be overwritten in derived classes to implement
     * additional special items. As an example consider the following code:
     * </p>
     * 
     * <pre>
     *   protected boolean handle(String tag, DB db, Processor processor,
     *                            String brace) throws ExBibException {
     *     if (super.handle(tag,db,processor,brace)) {
     *     } else if (tag.equals("new_tag")) {
     *       . . .
     *     }
     *   }
     * </pre
     *
     * @param tag the name of the item encountered.
     * This String has been converted to lower case already.
     * @param db the database to store the information in
     * @param processor the processor context
     * @param brace the String expected as terminating brace, i.e. ')' or '}'
     * depending in the opening brace
     * @return <code>true</code> iff the item is special and has been handled
     * successfully.
     *
     * @throws ExBibEofException in case of an unexpected end of file
     * @throws ExBibSyntaxException in case of an syntax error
     */
    @Override
    protected boolean handle(String tag, DB db, Processor processor,
            String brace, Locator locator) throws ExBibException {

        if (tag.equals("include")) {
            String source = parseToken(filenamePattern);
            expect(brace);
            try {
                db.load(source, null, processor);
            } catch (FileNotFoundException e) {
                throw new ExBibFileNotFoundException(source, locator);
            }

            return true;
        } else if (tag.equals("alias")) {
            KeyValue pair = parseAssign();
            expect(brace);
            db.storeAlias(pair.getKey(), pair.getValue().expand(db),
                getLocator());

            return true;
        } else if (tag.equals("modify")) {
            String key = parseKey();

            if (key.equals("")) {
                throw new ExBibMissingKeyException(null, getLocator());
            }

            Entry entry = db.getEntry(key);
            if (entry == null) {
                throw new ExBibMissingEntryException(key, getLocator());
            }

            char c;

            for (c = parseNextNonSpace(false); c == ','; c =
                    parseNextNonSpace(false)) {
                c = parseNextNonSpace(true);

                if (c == '}' || c == ')') {
                    break;
                }

                KeyValue pair = parseAssign();
                entry.set(pair.getKey(), pair.getValue());
            }

            if (c != brace.charAt(0)) {
                if (c < 0) {
                    throw new ExBibUnexpectedException(null, brace,
                        getLocator());
                }

                throw new ExBibUnexpectedException(Character.toString(c),
                    brace, getLocator());
            }

            return true;
        }

        return super.handle(tag, db, processor, brace, locator);
    }
}
