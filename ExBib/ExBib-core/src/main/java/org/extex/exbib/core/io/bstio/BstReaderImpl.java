/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
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

package org.extex.exbib.core.io.bstio;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.impl.BstExecuteImpl;
import org.extex.exbib.core.bst.command.impl.BstIterateImpl;
import org.extex.exbib.core.bst.command.impl.BstReadImpl;
import org.extex.exbib.core.bst.command.impl.BstReverseImpl;
import org.extex.exbib.core.bst.command.impl.BstSortImpl;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TBlock;
import org.extex.exbib.core.bst.node.impl.TChar;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.bst.node.impl.TQLiteral;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.bst.node.impl.TokenList;
import org.extex.exbib.core.exceptions.ExBibEofException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedOfException;
import org.extex.exbib.core.i18n.Messages;
import org.extex.exbib.core.io.AbstractFileReader;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class implements a reader for bst files.
 * <p>
 * The syntax follows the definition of BibT<sub>E</sub>X 0.99c.
 * </p>
 * 
 * <small> <table>
 * <tr>
 * <td><i>all</i></td>
 * <td><tt>:==</tt></td>
 * <td><i>itemlist</i></td>
 * <tr>
 * <td><i>itemlist</i></td>
 * <td><tt>:==</tt></td>
 * <td></td>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>item</i> <i>itemlist</i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>item</i></td>
 * <td><tt>:==</tt></td>
 * <td><i>entry</i></td>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>integers</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>strings</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>sort</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>iterate</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>reverse</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>read</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>execute</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>function</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>macro</i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>entry</i></td>
 * <td><tt>:==</tt></td>
 * <td>'ENTRY' '{' <i>fieldnames</i> '}' '{' <i>integernames</i> '}' '{'
 * <i>stringnames</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>entrynames</i></td>
 * <td><tt>:==</tt></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>namelist<i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>integernames</i></td>
 * <td><tt>:==</tt></td>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>namelist<i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>stringnames</i></td>
 * <td><tt>:==</tt></td>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>namelist<i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>namelist</i></td>
 * <td><tt>:==</tt></td>
 * <td>name</td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>name<i> <i>namelist<i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>name</i></td>
 * <td><tt>:==</tt></td>
 * <td><tt>[^0-9\s={}#'"][^\s={}#]*</tt></td>
 * </tr>
 * 
 * <tr>
 * <td><i>integers</i></td>
 * <td><tt>:==</tt></td>
 * <td>'INTEGERS' '{' <i>integernames</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>integers</i></td>
 * <td><tt>:==</tt></td>
 * <td>'STRINGS' '{' <i>stringnames</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>execute</i></td>
 * <td><tt>:==</tt></td>
 * <td>'EXECUTE' '{' <i>name</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>iterate</i></td>
 * <td><tt>:==</tt></td>
 * <td>'ITERATE' '{' <i>name</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>reverse</i></td>
 * <td><tt>:==</tt></td>
 * <td>'REVERSE' '{' <i>name</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>sort</i></td>
 * <td><tt>:==</tt></td>
 * <td>'SORT'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>read</i></td>
 * <td><tt>:==</tt></td>
 * <td>'READ'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>macro</i></td>
 * <td><tt>:==</tt></td>
 * <td>'MACRO' '{' <i>name</i> '}' '{' <i>body</i> '}'
 * 
 * <tr>
 * <td><i>function</i></td>
 * <td><tt>:==</tt></td>
 * <td>'FUNCTION' '{' <i>name</i> '}' '{' <i>body</i> '}' </td>
 * </tr>
 * 
 * <tr>
 * <td><i>body</i></td>
 * <td><tt>:==</tt></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>bodyitem<i> <i>body<i></td>
 * </tr>
 * 
 * <tr>
 * <td><i>bodyitem</i></td>
 * <td><tt>:==</tt></td>
 * <td><i>name</i></td>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td>''' <i>name</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>string</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * <td><i>number</i></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><tt>|</tt></td>
 * '{'
 * <td><i>bodyitem</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>number</i></td>
 * <td><tt>:==</tt></td>
 * <td>'#' <tt>[0-9]+</tt></td>
 * </tr>
 * 
 * <tr>
 * <td><i>string</i></td>
 * <td><tt>:==</tt></td>
 * <td>'"' <tt>[^"]*</tt> '"'</td>
 * </tr>
 * 
 * </table> </small>
 * 
 * Example
 * 
 * <pre>
 * ENTRY {author}{}{}
 * INTEGERS {x y}
 * STRINGS {u.v}
 *
 * FUNCTION{abc}{
 *     x #2 > { "aha" write$ } { "ccc" write$ } if$
 *    "Hello " "World" * write$ newline$
 *    'skip$
 *    { aha }
 *    stack$
 *    #3 #4 + write$ newline$
 *    #3 #4 swap$ - write$ newline$
 *    x #1 + 'x :=
 *    x #1 + 'x :=
 *    x #1 + 'x :=
 *    x write$
 *    #0 'y :=
 *    {y #3 <} { y #1 + 'y := "." write$ } while$
 * }
 *
 * FUNCTION{def}{
 *     type$ " " * author * write$
 *     newline$
 * }
 *
 * MACRO {jan} {"January"}
 *
 * READ
 * EXECUTE{abc}
 * ITERATE{def}
 * REVERSE{def}
 * SORT
 *
 * </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BstReaderImpl extends AbstractFileReader implements BstReader {

    /** the pattern for whitespace */
    private static final Pattern patternEmpty = Pattern.compile("\\s*");

    /** the pattern for whitespace at the beginning */
    private static final Pattern patternLeadingSpaces =
            Pattern.compile("^\\s+");

    /** the pattern for a literal at the beginning */
    private static final Pattern patternLiteral =
            Pattern.compile("([^{}\\//\\\"\\' \t\n]+)");

    /** the pattern for newlines */
    private static Pattern patternNewline = Pattern.compile("[\n\r]");

    /** the pattern for numbers */
    private static final Pattern patternNumber =
            Pattern.compile("#([+-]?[0-9]+)");

    /**
     * the pattern for a string, i.e. something enclosed in double quotes not
     * containing a double quote
     */
    private static final Pattern patternString =
            Pattern.compile("\"([^\\\"]*)\"");

    /** the reader to get the input from */
    private Reader reader = null;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public BstReaderImpl() throws ConfigurationException {

        super();
    }

    /**
     * This method read the next Token and checks that is a TChar with the given
     * value. If this fails an exception is thrown.
     * 
     * @param value the expected value
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the expected character could be read
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected void expectChar(String value) throws ExBibException {

        Token token = nextToken();

        if (token == null) {
            throw new ExBibUnexpectedOfException(value, null, getLocator());
        }

        if (!(token instanceof TChar) || !token.getValue().equals(value)) {
            throw new ExBibUnexpectedException(value, token.toString(),
                getLocator());
        }
    }

    /**
     * This is the tokenizer method for this parser class.
     * 
     * @return the next token from the scanner or <code>null</code> if the end
     *         of file has been reached
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected Token nextToken() throws ExBibException {

        StringBuffer buffer = getBuffer();
        Matcher matcher;

        if (!canRead()) {
            return null;
        }

        for (;;) {
            while (buffer.length() > 0
                    && Character.isWhitespace(buffer.charAt(0))) {
                buffer.delete(0, 1);
            }

            while (patternEmpty.matcher(buffer).matches()) {
                if (read() == null) {
                    return null;
                }

                for (int i = buffer.length() - 1; i >= 0; i--) {
                    if (buffer.charAt(i) == '\n' || buffer.charAt(i) == '\r') {
                        buffer.delete(i, i + 1);
                    }
                }

                while (buffer.length() > 0
                        && Character.isWhitespace(buffer.charAt(0))) {
                    buffer.delete(0, 1);
                }
            }

            if (buffer.charAt(0) == '%') {
                buffer.delete(0, buffer.length());
            } else if ((matcher = patternNumber.matcher(buffer)).lookingAt()) {
                String val = matcher.group(1);
                buffer.delete(0, matcher.end());

                return new TInteger(val, getLocator());
            } else if ((matcher = patternLiteral.matcher(buffer)).lookingAt()) {
                String val = matcher.group(1).toLowerCase();
                buffer.delete(0, matcher.end());

                return new TLiteral(val, getLocator());
            } else if ((matcher = patternString.matcher(buffer)).lookingAt()) {
                String val = matcher.group(1);
                buffer.delete(0, matcher.end());

                return new TString(val, getLocator());
            } else if (buffer.length() > 0) {
                String c = buffer.substring(0, 1);
                buffer.delete(0, 1);

                return new TChar(c, getLocator());
            } else {
                throw new ExBibImpossibleException(">>" + buffer + "<<"); //$NON-NLS-2$
            }
        }
    }

    /**
     * This parsing routine takes the list of files from the processor context.
     * 
     * @param processor the processor context to store the result in
     * 
     * @throws ExBibException ...
     * @throws FileNotFoundException ...
     * @throws ConfigurationException ...
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the legal end
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    public void parse(Processor processor)
            throws ExBibException,
                FileNotFoundException,
                ConfigurationException {

        List<String> fileList = processor.getBibliographyStyles();

        for (int ptr = 0; ptr < fileList.size(); ptr++) {
            parse(processor, fileList.get(ptr));
        }
    }

    /**
     * This parsing routine takes a single file argument and parses its
     * contents.
     * 
     * @param processor the processor context
     * @param file the name of the file to parse
     * 
     * @throws ExBibException in case of an error
     * @throws FileNotFoundException in case that the requested file could not
     *         be opened for reading
     * @throws ConfigurationException in case that the reading apparatus detects
     *         a misconfiguration
     * @throws ExBibSyntaxException in case of an syntax error
     */
    public void parse(Processor processor, String file)
            throws ExBibException,
                FileNotFoundException,
                ConfigurationException {

        Token token;
        reader = open(file, "bst");

        while ((token = nextToken()) != null) {
            if (!processCommand(token, processor)) {
                throw new ExBibSyntaxException(Messages.format(
                    "BstReaderImpl.Unexpected_token_found", token.toString()),
                    getLocator());
            }
        }
    }

    /**
     * Parse a block and return the Tokens found.
     * 
     * <dl>
     * <dt>Example</dt>
     * <dd> <code>{ "abc" "def" * }</code> is parsed into the
     * {@link TokenList TokenList} consisting of two {@link TString TString}
     * tokens and a {@link TChar TChar} token. </dd>
     * </dl>
     * 
     * @return the contents of the block as {@link TokenList TokenList}
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the block was complete
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected TBlock parseBlock() throws ExBibException {

        expectChar("{");

        return parseBlockEnd();
    }

    /**
     * Parses the contents of a block up to the next matching '}'. The initial
     * '{' is assumed to be digested before this method has been invoked.
     * 
     * @return the collected Tokens in a TBlock container
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the block was complete
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    private TBlock parseBlockEnd() throws ExBibException {

        TBlock block = new TBlock(getLocator());
        Token token;

        while ((token = nextToken()) != null) {
            if (token instanceof TChar) {
                String val = token.getValue();

                if (val.equals("}")) {
                    return block;
                } else if (val.equals("{")) {
                    token = parseBlockEnd();
                } else if (val.equals("'")) {
                    token = nextToken();

                    if (!(token instanceof TLiteral)) {
                        throw new ExBibSyntaxException(
                            MessageFormat
                                .format(
                                    Messages
                                        .format("BstReaderImpl.Expected_literal_instead_of__"),
                                    new Object[]{token.toString()}),
                            getLocator());
                    }

                    token = new TQLiteral(null, token.getValue());
                } else {
                    throw new ExBibSyntaxException(Messages
                        .format("BstReaderImpl.unexpected_character")
                            + val);
                }
            }

            block.add(token);
        }

        throw new ExBibEofException(Messages
            .format("BstReaderImpl.while_reading_block"), getLocator());
    }

    /**
     * Parses a literal enclosed in braces.
     * 
     * @return the literal
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the literal argument was complete
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected TLiteral parseLiteralArg() throws ExBibException {

        expectChar("{");

        Token token = nextToken();

        if (!(token instanceof TLiteral)) {
            throw new ExBibSyntaxException(Messages
                .format("BstReaderImpl.Expected_literal_missing"), getLocator());
        }

        expectChar("}");

        return (TLiteral) token;
    }

    /**
     * Parse a list of literal tokens terminated by a closing brace.
     * 
     * @return the list of tokens
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the literal list was complete
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected TokenList parseLiteralList() throws ExBibException {

        TokenList literal = new TokenList(getLocator());
        expectChar("{");

        Token token;

        while (((token = nextToken()) != null) && token instanceof TLiteral) {
            literal.add(token);
        }

        if (token == null) {
            throw new ExBibEofException(Messages
                .format("BstReaderImpl.while_reading_literal_list"),
                getLocator());
        }

        if (!(token instanceof TChar) || !token.getValue().equals("}")) {
            throw new ExBibUnexpectedException(")", token.toString(),
                getLocator());
        }

        return literal;
    }

    /**
     * Parse a string argument, i.e. a string in double quotes enclosed in
     * braces.
     * 
     * @return the string as TString
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case that the end of file has been reached
     *         before the string argument was complete
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected TString parseStringArg() throws ExBibException {

        TString ret;
        expectChar("{");

        try {
            ret = (TString) nextToken();
        } catch (ClassCastException e) {
            throw new ExBibSyntaxException(Messages
                .format("BstReaderImpl.Expected_string_missing"), getLocator());
        }

        expectChar("}");

        return ret;
    }

    /**
     * Process a {@link Token Token} as a command. The following commands are
     * supported by this method:
     * <dl>
     * <dd>entry</dd>
     * <dd>execute</dd>
     * <dd>function</dd>
     * <dd>integers</dd>
     * <dd>iterate</dd>
     * <dd>macro</dd>
     * <dd>read</dd>
     * <dd>reverse</dd>
     * <dd>strings</dd>
     * <dd>sort</dd>
     * </dl>
     * 
     * @param token the Token to process
     * @param processor the processor context
     * 
     * @return <code>true</code> iff the given token has been handled
     *         successfully
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibEofException in case of an unexpected end of file
     * @throws ExBibSyntaxException in case of an syntax error
     * @throws ExBibImpossibleException in case something impossible happens
     */
    protected boolean processCommand(Token token, Processor processor)
            throws ExBibException {

        if (!(token instanceof TLiteral)) {
            return false;
        }

        String name = token.getValue().toLowerCase();

        switch (name.charAt(0)) {
            case 'e':

                if ("entry".equals(name)) {
                    List<String> entries = parseLiteralList().toStringList();
                    List<String> integers = parseLiteralList().toStringList();
                    List<String> strings = parseLiteralList().toStringList();
                    processor.setEntries(entries);
                    processor.setEntryIntegers(integers);
                    processor.setEntryStrings(strings);
                    return true;
                } else if ("execute".equals(name)) {
                    Token value = parseLiteralArg();
                    processor.addCommand(new BstExecuteImpl(value, token
                        .getLocator()));
                    return true;
                }

                break;
            case 'f':

                if ("function".equals(name)) {
                    String fname = parseLiteralArg().getValue();
                    TBlock body = parseBlock();
                    processor.addFunction(fname, new MacroCode(name, body
                        .getTokenList()));
                    return true;
                }

                break;
            case 'i':

                if ("integers".equals(name)) {
                    processor.setIntegers(parseLiteralList());
                    return true;
                } else if ("iterate".equals(name)) {
                    processor.addCommand(new BstIterateImpl(parseLiteralArg(),
                        token.getLocator()));
                    return true;
                }

                break;
            case 'm':

                if ("macro".equals(name)) {
                    String mname = parseLiteralArg().getValue();
                    processor.addMacro(mname, parseStringArg());
                    return true;
                }

                break;
            case 'r':

                if ("read".equals(name)) {
                    processor.addCommand(new BstReadImpl(token.getLocator()));
                    return true;
                } else if ("reverse".equals(name)) {
                    processor.addCommand(new BstReverseImpl(parseLiteralArg(),
                        token.getLocator()));
                    return true;
                }

                break;
            case 's':

                if ("strings".equals(name)) {
                    processor.setStrings(parseLiteralList());
                    return true;
                } else if ("sort".equals(name)) {
                    processor.addCommand(new BstSortImpl(token.getLocator()));
                    return true;
                }

                break;
        }

        return false;
    }
}