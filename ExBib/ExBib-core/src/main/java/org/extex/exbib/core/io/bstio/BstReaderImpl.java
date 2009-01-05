/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bstio;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.impl.BstExecute;
import org.extex.exbib.core.bst.command.impl.BstIterate;
import org.extex.exbib.core.bst.command.impl.BstRead;
import org.extex.exbib.core.bst.command.impl.BstReverse;
import org.extex.exbib.core.bst.command.impl.BstSort;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.exceptions.ExBibEofException;
import org.extex.exbib.core.exceptions.ExBibEofInBlockException;
import org.extex.exbib.core.exceptions.ExBibEofInLiteralListException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedEofException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.exbib.core.io.AbstractFileReader;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class implements a reader for bst files.
 * <p>
 * The syntax follows the definition of B<small>IB</small>T<sub>E</sub>X 0.99c.
 * </p>
 * 
 * <small>
 * <table>
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
 * <i>stringnames</i> '}'</td>
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
 * <td>'INTEGERS' '{' <i>integernames</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>integers</i></td>
 * <td><tt>:==</tt></td>
 * <td>'STRINGS' '{' <i>stringnames</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>execute</i></td>
 * <td><tt>:==</tt></td>
 * <td>'EXECUTE' '{' <i>name</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>iterate</i></td>
 * <td><tt>:==</tt></td>
 * <td>'ITERATE' '{' <i>name</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>reverse</i></td>
 * <td><tt>:==</tt></td>
 * <td>'REVERSE' '{' <i>name</i> '}'</td>
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
 * <td>'MACRO' '{' <i>name</i> '}' '{' <i>body</i> '}'</td>
 * </tr>
 * 
 * <tr>
 * <td><i>function</i></td>
 * <td><tt>:==</tt></td>
 * <td>'FUNCTION' '{' <i>name</i> '}' '{' <i>body</i> '}'</td>
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
 * <td>'{' <i>bodyitem</i> '}'</td>
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
 * </table>
 * </small>
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
 * @version $Revision$
 */
public class BstReaderImpl extends AbstractFileReader
        implements
            BstReader,
            Configurable {

    /**
     * The constant <tt>EMPTY_PATTERN</tt> contains the pattern for whitespace.
     */
    private static final Pattern EMPTY_PATTERN = Pattern.compile("\\s*");

    /**
     * The constant <tt>LITERAL_PATTERN</tt> contains the pattern for a literal
     * at the beginning.
     */
    private static final Pattern LITERAL_PATTERN =
            Pattern.compile("([^{}\\//\\\"\\' \t\n]+)");

    /**
     * The constant <tt>NUMBER_PATTERN</tt> contains the pattern for numbers.
     */
    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("#([+-]?[0-9]+)");

    /**
     * The constant <tt>STRING_PATTERN</tt> contains the pattern for a string,
     * i.e. something enclosed in double quotes not containing a double quote.
     */
    private static final Pattern STRING_PATTERN =
            Pattern.compile("\"([^\\\"]*)\"");

    /**
     * The field <tt>encoding</tt> contains the encoding for reading.
     */
    private String encoding = null;

    /**
     * The field <tt>instructionMap</tt> contains the mapping from normalized
     * names to instructions.
     */
    private Map<String, Instruction> instructionMap =
            new HashMap<String, Instruction>();

    /**
     * The field <tt>configuration</tt> contains the configuration.
     */
    private Configuration configuration;

    /**
     * The field <tt>comments</tt> contains the optionla buffer for comments.
     */
    private StringBuilder comments = null;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public BstReaderImpl() throws ConfigurationException {

        super();
        init();
    }

    /**
     * Add some instruction.
     * 
     * @param name the name
     * @param instruction the instruction
     */
    public void addInstruction(String name, Instruction instruction) {

        instructionMap.put(name.toLowerCase(Locale.ENGLISH), instruction);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        this.configuration = config;
        String enc = config.getAttribute("encoding");
        if (enc != null) {
            encoding = enc;
        }
    }

    /**
     * This method read the next Token and checks that is a TChar with the given
     * value. If this fails an exception is thrown.
     * 
     * @param value the expected value
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibUnexpectedEofException in case that the end of file has been
     *         reached before the expected character could be read
     * @throws ExBibImpossibleException in case of an internal error which
     *         should not happen
     */
    protected void expectChar(String value) throws ExBibException {

        Token token = nextToken();

        if (token == null) {
            throw new ExBibUnexpectedEofException(value, null, getLocator());
        }

        if (!(token instanceof TChar) || !token.getValue().equals(value)) {
            throw new ExBibUnexpectedException(value, token.toString(),
                getLocator());
        }
    }

    /**
     * Getter for configuration.
     * 
     * @return the configuration
     */
    public Configuration getConfiguration() {

        return configuration;
    }

    /**
     * Initialize the instructions.
     */
    protected void init() {

        addInstruction("entry", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                List<String> entries = parseLiteralList().toStringList();
                List<String> integers = parseLiteralList().toStringList();
                List<String> strings = parseLiteralList().toStringList();
                processor.setEntries(entries, locator);
                processor.setEntryIntegers(integers, locator);
                processor.setEntryStrings(strings, locator);
            }
        });
        addInstruction("execute", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                Token value = parseLiteralArg();
                processor.addCommand(new BstExecute(value, locator));
            }
        });
        addInstruction("function", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                String fname = parseLiteralArg().getValue();
                TBlock body = parseBlock();
                processor.addFunction(fname, new MacroCode(fname, body
                    .getTokenList()), locator);

            }
        });
        addInstruction("integers", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                processor.setIntegers(parseLiteralList(), locator);

            }
        });
        addInstruction("iterate", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                processor
                    .addCommand(new BstIterate(parseLiteralArg(), locator));

            }
        });
        addInstruction("macro", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                String mname = parseLiteralArg().getValue();
                processor.addMacro(mname, parseStringArg());
            }
        });
        addInstruction("read", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                processor.addCommand(new BstRead(locator));
            }
        });
        addInstruction("reverse", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                processor
                    .addCommand(new BstReverse(parseLiteralArg(), locator));

            }
        });
        addInstruction("strings", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                processor.setStrings(parseLiteralList(), locator);
            }
        });
        addInstruction("sort", new Instruction() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bstio.Instruction#parse(BstProcessor,
             *      Locator)
             */
            public void parse(BstProcessor processor, Locator locator)
                    throws ExBibException {

                processor.addCommand(new BstSort(locator));
            }
        });
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

        StringBuilder buffer = getBuffer();
        Matcher matcher;

        if (buffer == null) {
            return null;
        }

        for (;;) {
            while (buffer.length() > 0
                    && Character.isWhitespace(buffer.charAt(0))) {
                buffer.delete(0, 1);
            }

            while (EMPTY_PATTERN.matcher(buffer).matches()) {
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
                if (comments != null) {
                    comments.append(buffer);
                    comments.append('\n');
                }
                buffer.delete(0, buffer.length());
            } else if ((matcher = NUMBER_PATTERN.matcher(buffer)).lookingAt()) {
                String val = matcher.group(1);
                buffer.delete(0, matcher.end());

                return new TInteger(val, getLocator());
            } else if ((matcher = LITERAL_PATTERN.matcher(buffer)).lookingAt()) {
                String val = matcher.group(1).toLowerCase(Locale.ENGLISH);
                buffer.delete(0, matcher.end());

                return new TLiteral(val, getLocator());
            } else if ((matcher = STRING_PATTERN.matcher(buffer)).lookingAt()) {
                String val = matcher.group(1);
                buffer.delete(0, matcher.end());

                return new TString(val, getLocator());
            } else if (buffer.length() > 0) {
                char c = buffer.charAt(0);
                buffer.delete(0, 1);
                return new TChar(c, getLocator());
            } else {
                throw new ExBibImpossibleException(">>" + buffer + "<<");
            }
        }
    }

    /**
     * This parsing routine takes the list of files from the processor context.
     * 
     * @param processor the processor context to store the result in
     * 
     * @throws ExBibBstNotFoundException in case that the requested file could
     *         not be opened for reading
     * @throws ConfigurationException in case that the reading apparatus detects
     *         a misconfiguration
     * @throws ExBibException in case of an error
     */
    public void parse(BstProcessor processor)
            throws ExBibException,
                ExBibBstNotFoundException,
                ConfigurationException {

        for (String bst : processor.getBibliographyStyles()) {
            parse(processor, bst);
        }
    }

    /**
     * This parsing routine takes a single file argument and parses its
     * contents.
     * 
     * @param processor the processor context
     * @param bst the name of the bst file to parse
     * 
     * @throws ExBibBstNotFoundException in case that the requested bst could
     *         not be opened for reading
     * @throws ConfigurationException in case that the reading apparatus detects
     *         a misconfiguration
     * @throws ExBibException in case of an syntax error
     */
    public void parse(BstProcessor processor, String bst)
            throws ExBibException,
                ConfigurationException {

        try {
            open(bst, "bst", encoding);
        } catch (FileNotFoundException e) {
            throw new ExBibBstNotFoundException(bst, null);
        }

        for (Token token = nextToken(); token != null; token = nextToken()) {
            if (!processCommand(token, processor)) {
                throw new ExBibUnexpectedException(token.toString(), null,
                    getLocator());
            }
        }
    }

    /**
     * Parse a block and return the Tokens found.
     * 
     * <dl>
     * <dt>Example</dt>
     * <dd> <code>{ "abc" "def" * }</code> is parsed into the {@link TokenList
     * TokenList} consisting of two {@link TString TString} tokens and a
     * {@link TChar TChar} token.</dd>
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

        Locator start = getLocator();
        TBlock block = new TBlock(start);

        for (Token token = nextToken(); token != null; token = nextToken()) {

            if (token instanceof TChar) {
                String val = token.getValue();

                if (val.equals("}")) {
                    return block;
                } else if (val.equals("{")) {
                    token = parseBlockEnd();
                } else if (val.equals("'")) {
                    token = nextToken();

                    if (!(token instanceof TLiteral)) {
                        throw new ExBibMissingLiteralException((token == null
                                ? null
                                : token.toString()), getLocator());
                    }

                    token = new TQLiteral(token.getValue(), null);
                } else {
                    throw new ExBibUnexpectedException(val, null, getLocator());
                }
            }

            block.add(token);
        }

        throw new ExBibEofInBlockException(start);
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
            throw new ExBibMissingLiteralException((token == null
                    ? null
                    : token.toString()), getLocator());
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

        Locator start = getLocator();
        TokenList literal = new TokenList(start);
        expectChar("{");

        Token token;

        for (token = nextToken(); token != null && token instanceof TLiteral; token =
                nextToken()) {
            literal.add(token);
        }

        if (token == null) {
            throw new ExBibEofInLiteralListException(start);
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

        expectChar("{");
        Token t = nextToken();

        if (!(t instanceof TString)) {
            throw new ExBibMissingStringException(//
                t == null ? null : t.toString(), getLocator());
        }

        expectChar("}");

        return (TString) t;
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
    protected boolean processCommand(Token token, BstProcessor processor)
            throws ExBibException {

        if (!(token instanceof TLiteral)) {
            return false;
        }

        String name = token.getValue().toLowerCase(Locale.ENGLISH);
        Instruction instruction = instructionMap.get(name);

        if (instruction != null) {
            instruction.parse(processor, token.getLocator());
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.bstio.BstReader#setSaveComment(java.lang.StringBuilder)
     */
    public void setSaveComment(StringBuilder save) {

        this.comments = save;
    }
}
