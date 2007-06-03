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

package org.extex.unit.base.file;

import org.extex.core.exception.helping.BadFileNumberException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This abstract class provides some common methods for primitives dealing with
 * files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public abstract class AbstractFileCode extends AbstractCode
        implements
            Configurable {

    /**
     * The field <tt>DEFAULT_ENCODING</tt> contains the default encoding if
     * nothing else is found.
     */
    private static final String DEFAULT_ENCODING = "ISO8859-1";

    /**
     * The constant <tt>MAX_OUT_FILE_NO</tt> contains the maximum number of
     * input files.
     */
    public static final int MAX_IN_FILE_NO = 15;

    /**
     * The constant <tt>MAX_OUT_FILE_NO</tt> contains the maximal number of
     * output files.
     */
    public static final int MAX_OUT_FILE_NO = 15;

    /**
     * Scan the input source for some tokens making up the key for an infile
     * register. Currently only numbers in a certain range are allowed.
     *
     *
     * <doc name="infile name" type="syntax">
     * <h3>The Infile Name</h3>
     * <p>
     *  The infile name is a symbolic key to reference an input file.
     *  This is a number in the range from 0 to 15.
     * </p>
     *
     * <h4>Syntax</h4>
     *  The formal description of this primitive is the following:
     *  <pre class="syntax">
     *    &lang;infile&nbsp;name&rang;
     *      &rarr; {@linkplain
     *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
     *        &lang;number&rang;} </pre>
     * </doc>
     *
     *
     * @param context the interpreter context
     * @param source the token source to read from
     * @param typesetter the typesetter
     *
     * @return the key read in the form of a String
     *
     * @throws HelpingException in case of a failure
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static String scanInFileKey(Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,TypesetterException {

        long no = source.parseNumber(context, source, typesetter);
        String key = Long.toString(no);

        if (no < 0 || no > MAX_IN_FILE_NO) {
            throw new BadFileNumberException(key, //
                "0", Integer.toString(MAX_IN_FILE_NO));
        }

        return key;
    }

    /**
     * Scan the input source for some tokens making up the key for an outfile
     * register. Currently only numbers are allowed.
     *
     * <doc name="outfile name" type="syntax">
     * <h3>The Outfile Name</h3>
     * <p>
     *  The outfile name is a symbolic key to reference an output file.
     *  This is a number in the range from 0 to 15.
     * </p>
     *
     * <h4>Syntax</h4>
     *  The formal description of this primitive is the following:
     *  <pre class="syntax">
     *    &lang;infile&nbsp;name&rang;
     *      &rarr; {@linkplain
     *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
     *        &lang;number&rang;} </pre>
     * </doc>
     *
     *
     * @param context the interpreter context
     * @param source the token source to read from
     * @param typesetter the typesetter
     *
     * @return the key read in the form of a String
     *
     * @throws HelpingException in case of a failure
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static String scanOutFileKey(Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,TypesetterException {

        long no = source.parseNumber(context, source, typesetter);
        return Long.toString(no);
    }

    /**
     * The field <tt>strictTeX</tt> contains the boolean indicating whether or
     * not to adhere strictly to the rules of <logo>TeX</logo> for file name
     * parsing.
     */
    private boolean strictTeX = false;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public AbstractFileCode(String name) {

        super(name);
    }

    /**
     * Configure an object according to a given Configuration.
     *
     * @param config the configuration to use
     *
     * @throws ConfigurationException in case of an error
     *
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) {

        String strict = config.getAttribute("strict");
        strictTeX = (strict != null && Boolean.valueOf(strict).booleanValue());
    }

    /**
     * Return the encoding for the AbstractFileCode file.
     * <p>
     * First of all, <code>\fileencoding</code> is used,
     * if there is no
     * value, then the property <code>extex.encoding</code> is used,
     * or <code>ISO8859-1</code>,
     * if no entry exists.
     *
     * @param context the context
     *
     * @return the encoding for the AbstractFileCodefile
     */
    protected String getEncoding(Context context) {

        String encoding = context.getToks("fileencoding").toText().trim();
        if (encoding.length() == 0) {
            String enc = System.getProperty("extex.encoding");
            encoding = (enc != null ? enc : DEFAULT_ENCODING);
        }
        return encoding;
    }

    /**
     * Scan the file name.
     *
     * <doc type="syntax" name="filename">
     * This method parses the following syntactic entity:
     * <pre class="syntax">
     *   &lang;file name&rang; </pre>
     *
     * The scanning is performed in one of two ways:
     * <ul>
     * <li>If the first token is a left brace then a block is read until the
     *   matching right brace is found. On the way the tokens are expanded.
     * </li>
     * <li>Otherwise tokens are read until a space token is encountered.
     * </li>
     * </ul>
     *
     * </doc>
     *
     * @param context the processing context
     * @param source the source for new tokens
     *
     * @return the file name as string
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String scanFileName(Context context,
            TokenSource source) throws HelpingException, TypesetterException {

        Token t = source.scanNonSpace(context);

        if (t == null) {
            // Fall through to error
        } else if (strictTeX && t.isa(Catcode.LEFTBRACE)) {
            source.push(t);
            String name = source.scanTokensAsString(context, getName());
            if (name != null) {
                return name;
            }

        } else {
            StringBuffer sb = new StringBuffer(t.toText());

            for (t = source.getToken(context); //
            t != null && !(t instanceof SpaceToken); //
            t = source.getToken(context)) {
                sb.append(t.toText());
            }

            return sb.toString();
        }

        throw new EofException(printableControlSequence(context));
    }

}
