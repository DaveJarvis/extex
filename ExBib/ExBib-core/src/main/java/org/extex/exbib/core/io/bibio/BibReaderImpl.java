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

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.KeyValue;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibMissingKeyException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a reader for B<small>IB</small><span style="margin-left: -0.15em;"
 * >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X files. It extends the reader for the B<small>IB</small><span
 * style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X 0.99c format. The constructions supported roughly approximate the
 * features announced for B<small>IB</small><span style="margin-left: -0.15em;"
 * >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X 1.0 (which has net been published at the time of this writing
 * &ndash; and probably never will).
 * <p>
 * The following constructs are supported:
 * </p>
 * <ul>
 * <li>{@literal @alias}</li>
 * <li>{@literal @include}</li>
 * <li>{@literal @modify}</li>
 * </ul>
 * 
 * <h3>The {@literal @alias} Instruction</h3>
 * 
 * <p>
 * Define an alias for an existing entry. It has a new key and inherits all
 * fields form the other one. Aliased entries are includes at most once into the
 * output.
 * </p>
 * 
 * <pre>
 * {@literal @alias}{abc=xyz}
 * </pre>
 * 
 * 
 * <h3>The {@literal @include} Instruction</h3>
 * 
 * <p>
 * Include the named resource as if its contents where at the place of the
 * include instruction.
 * </p>
 * 
 * <pre>
 * {@literal @include}{some_resource}
 * </pre>
 * 
 * 
 * 
 * <h3>The {@literal @modify} Instruction</h3>
 * 
 * <p>
 * </p>
 * 
 * <pre>
 * {@literal @modify}{abc,
 *         title={The Title}}
 * </pre>
 * 
 * 
 * <h3>The {@literal @comment} Instruction</h3>
 * 
 * <p>
 * The {@literal @comment} is modified to take an argument in braces. Whatever
 * contained in this argument is ignored.
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibReaderImpl extends BibReader099Impl {

    /**
     * The field <tt>filenamePattern</tt> contains the pattern for file names.
     */
    private static Pattern filenamePattern = Pattern.compile("[^{}]*");

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public BibReaderImpl() throws ConfigurationException {

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
     *     } else if ("new_tag".equals(tag)) {
     *       . . .
     *     }
     *   }
     * </pre
     * 
     * @param tag the name of the item encountered. This String has been
     *        converted to lower case already.
     * @param db the database to store the information in
     * @param brace the String expected as terminating brace, i.e. ')' or '}'
     *        depending in the opening brace
     * @param locator the locator
     * 
     * @return <code>true</code> iff the item is special and has been handled
     *         successfully.
     * 
     * @throws ExBibException in case of an syntax error
     */
    @Override
    protected boolean handle(String tag, DB db, String brace, Locator locator)
            throws ExBibException {

        if ("include".equals(tag)) {
            String source = parseToken(filenamePattern);
            expect(brace);
            try {
                db.load(source, null);
            } catch (FileNotFoundException e) {
                throw new ExBibFileNotFoundException(source, locator);
            }

            return true;
        } else if ("alias".equals(tag)) {
            KeyValue pair = parseAssign();
            expect(brace);
            db.storeAlias(pair.getKey(), pair.getValue().toString(),
                getLocator());

            return true;
        } else if ("modify".equals(tag)) {
            String key = parseKey();

            if (key == null || "".equals(key)) {
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
                throw new ExBibUnexpectedException(//
                    (c > 0 ? Character.toString(c) : null), brace, getLocator());
            }

            return true;
        }

        return super.handle(tag, db, brace, locator);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.bibio.BibReader099Impl#handleComment(java.lang.StringBuilder,
     *      java.lang.String)
     */
    @Override
    protected void handleComment(StringBuilder comment, String tag)
            throws ExBibException {

        comment.append('@');
        comment.append(tag);
        char c = parseNextNonSpace(true);
        if (c != '{') {
            if (c != '@') {
                comment.append(' ');
            }
            return;
        }
        parseNextNonSpace(false);
        comment.append('{');
        comment.append(parseBlock(0));
        comment.append('}');
    }

}
