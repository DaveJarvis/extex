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

package org.extex.exbib.core.bst;

import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.*;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.*;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.bstio.BstReaderFactory;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.exbib.core.util.ObserverList;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is the core implementation of an interpreter for the
 * BibTeX language.
 * <p>
 * The core implementation contains the full functionality of an interpreter.
 * But not functions, strings, integers etc are predefined. Thus usually this
 * class should be used as a base class where derived classes take care of those
 * definitions.
 * </p>
 *
 * <p>
 * The Stack
 * </p>
 * <p>
 * The interpreter implements a stack based language. All communication to
 * functions is performed via the stack. The arguments of method invocations can
 * be pushed onto the stack and popped from it in the functions.
 * </p>
 * <p>
 * The Stack can keep different date types. They range from constant strings or
 * integers to pieces of executable code. The base interface of the stack items
 * is {@link Code}.
 * </p>
 * 
 * <p>The Strings</p>
 * <p>
 * The interpreter can keep string valued variables. They are accessed with a
 * name which is also a {@link String}.
 * </p>
 * 
 * <p>The Integers</p>
 * <p>
 * The interpreter can keep integer valued variables. They are accessed with a
 * name which is a {@link String}.
 * </p>
 * 
 * <p>The Entry Strings</p>
 * 
 * <p>The Entry Integers</p>
 * 
 * <p>The Database</p>
 * 
 * <p>The Observers</p>
 * <p>
 * Several operations in the interpreter can trigger an observer when the
 * corresponding event occurs. Thus client programs can register a handler to be
 * informed when such an event is recognized.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BstInterpreterCore extends BibliographyCore
        implements
            BstProcessor,
            ResourceAware,
            Iterable<Command>,
            FunctionContainer {

    /**
     * The field {@code MAX_NUMBER} contains the maximum number to be used as
     * default for various paremeters.
     */
    private static final int MAX_NUMBER = 0x7fff;

    /**
     * The field {@code commands} contains the list of commands to process.
     */
    private List<Command> commands;

    /**
     * The field {@code configuration} contains the configuration.
     */
    private Configuration configuration;

    /**
     * The field {@code finder} contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field {@code functions} contains the mapping from the name to the
     * code for functions.
     */
    private Map<String, Code> functions;

    /**
     * The field {@code addFunctionObservers} contains the list of observers
     * triggered when functions are added.
     */
    private final ObserverList addFunctionObservers = new ObserverList();

    /**
     * The field {@code changeFunctionObservers} contains the list of observers
     * triggered when functions are changed.
     */
    private final ObserverList changeFunctionObservers = new ObserverList();

    /**
     * The field {@code popObservers} contains the list of observers triggered
     * when a Token is popped.
     */
    private final ObserverList popObservers = new ObserverList();

    /**
     * The field {@code pushObservers} contains the list of observers triggered
     * when a token is pushed.
     */
    private final ObserverList pushObservers = new ObserverList();

    /**
     * The field {@code runObservers} contains the list of observers triggered
     * when processing a command.
     */
    private final ObserverList runObservers = new ObserverList();

    /**
     * The field {@code stepObservers} contains the list of observers triggered
     * by the execution of one step.
     */
    private final ObserverList stepObservers = new ObserverList();

    /**
     * The field {@code theEntryIntegers} contains the list of local integers.
     */
    private List<String> theEntryIntegers;

    /**
     * The field {@code theEntryStrings} contains the list of local strings.
     */
    private List<String> theEntryStrings;

    /**
     * The field {@code theIntegers} contains the list of integers.
     */
    private List<String> theIntegers;

    /**
     * The field {@code theStrings} contains the list of strings.
     */
    private List<String> theStrings;

    /**
     * The field {@code literalStack} contains the stack which is the central
     * data structure for the execution of a program.
     */
    private final Stack<Token> literalStack = new Stack<>();

    /**
     * The field {@code outWriter} contains the output writer.
     */
    private Writer outWriter;

    /**
     * This is an obsolete constant denoting the maximum size of an entry. The
     * value is read from the configuration or a default is used.
     */
    private int entryMax = MAX_NUMBER;

    /**
     * This is an obsolete constant denoting the maximum size of an string. The
     * value is read from the configuration or a default is used.
     */
    private int globalMax = MAX_NUMBER;

    /**
     * The field {@code warnings} contains the number of warnings.
     */
    private long warnings = 0;

    /**
     * Creates a new Processor object. This method is mainly meant to be used in
     * the factory. Please make sure that database, log writer, and out writer
     * are set immediately.
     * 
     * @throws ExBibImpossibleException if an programming error has been
     *         detected
     */
    public BstInterpreterCore() throws ExBibImpossibleException {

        this(null, null, null);
    }

    /**
     * Create a new Processor object.
     * 
     * @param db The database associated with this processor.
     * @param out This argument is a writer which receives the normal output. It
     *        is an object implementing the interface {@code Writer}.
     * @param log This argument is a writer which receives the logging output.
     *        It is an object implementing the interface {@code Writer}.
     * 
     * @throws ExBibImpossibleException if an programming error has been
     *         detected
     */
    public BstInterpreterCore(DB db, Writer out, Logger log)
            throws ExBibImpossibleException {

        super(db, log);
        functions = new HashMap<>();
        this.outWriter = out;

        reset();
    }

    /**
     * Add a command to the list of commands to execute. The commands constitute
     * the main program. All commands in the command list are executed in turn
     * when the processor is run.
     * 
     * @param command the command to add
     */
    public void addCommand(Command command) {

        commands.add(command);
    }

    public void addFunction(String name, Code body, Locator locator)
            throws
        ExBibException {

        if (name == null || "".equals(name)) {
            throw new ExBibIllegalValueException("Illegal function name",
                locator);
        }

        if (functions.put(name, body) != null) {
            throw new ExBibFunctionExistsException(name, locator);
        }

        if (addFunctionObservers != null) {
            addFunctionObservers.update(this, name);
        }
    }

    public void changeFunction(String name, Code body, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionUndefinedException {

        if (name == null || "".equals(name)) {
            throw new ExBibIllegalValueException("Illegal function name",
                locator);
        }

        if (functions.get(name) == null) {
            throw new ExBibFunctionUndefinedException(name, locator);
        }

        functions.put(name, body);
    }

    /**
     * Configure the current instance. The following values are considered for
     * configuration:
     * <dl>
     * <dd>minCrossrefs</dd>
     * <dt>the minimum crossrefs considered when collapsing records</dt>
     * <dd>globalMax</dd>
     * <dt>obsolete variable global.max$</dt>
     * <dd>entryMax</dd>
     * <dt>obsolete variable entries.max$</dt>
     * </dl>
     * 
     * @param config the configuration to consult
     * 
     * @throws ConfigurationException in case of an error
     */
    @Override
    public void configure(Configuration config) throws ConfigurationException {

        super.configure(config);
        this.configuration = config;
        Locator locator = new Locator(getClass().getName() + "#configure()", 0);
        int i = config.getValueAsInteger("globalMax", -1);

        if (i >= 0 && i != globalMax) {
            try {
                globalMax = i;
                changeFunction("global.max$", new TInteger(i, locator), locator);
            } catch (ExBibException e) {
                throw new ConfigurationWrapperException(e);
            }
        }

        i = config.getValueAsInteger("entryMax", -1);

        if (i >= 0 && i != entryMax) {
            try {
                entryMax = i;
                changeFunction("entry.max$", new TInteger(i, locator), locator);
            } catch (ExBibException e) {
                throw new ConfigurationWrapperException(e);
            }
        }
    }

    /**
     * Getter for the configuration.
     * 
     * @return the configuration
     */
    public Configuration getConfiguration() {

        return configuration;
    }

    public List<String> getEntryIntegers() {

        return theEntryIntegers;
    }

    public List<String> getEntryStrings() {

        return theEntryStrings;
    }

    /**
     * Getter for the finder.
     * 
     * @return the finder
     */
    public ResourceFinder getFinder() {

        return finder;
    }

    public Code getFunction(String name) {

        return (functions.get(name));
    }

    public List<String> getFunctionNames() {

        List<String> sl = new ArrayList<>();

        for (String name : functions.keySet()) {
            Code code = getFunction(name);
            if (code instanceof MacroCode
                    && ((MacroCode) code).getToken() instanceof TokenList) {
                sl.add(name);
            }
        }

        return sl;
    }

    public List<String> getIntegers() {

        return theIntegers;
    }

    public List<String> getMacroNames() {

        return getDB().getMacroNames();
    }

    public long getNumberOfWarnings() {

        return warnings;
    }

    /**
     * Getter for the output writer.
     * 
     * @return the output writer
     */
    public Writer getOutWriter() {

        return outWriter;
    }

    public List<String> getStrings() {

        return theStrings;
    }

    public boolean isKnown(String type) {

        return (null != getFunction(type));
    }

    /**
     * Get an iterator over all commands.
     * 
     * @return the iterator
     */
    public Iterator<Command> iterator() {

        return commands.iterator();
    }

    /**
     * Pop an element from the stack. If the stack is empty an exception is
     * thrown.
     * 
     * <p>
     * The observers for {@code pop} are notified. They receive the token
     * as argument.
     * </p>
     * 
     * @param locator the locator
     * 
     * @return the token popped from the stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     */
    public Token pop(Locator locator) throws ExBibStackEmptyException {

        if (literalStack.empty()) {
            throw new ExBibStackEmptyException(locator);
        }

        Token t = literalStack.pop();
        popObservers.update(this, t);

        return t;
    }

    /**
     * Pop an integer literal from the stack. If the stack is empty or the
     * element isn't an integer then an error condition is raised.
     * 
     * <p>
     * The observers for {@code pop} are notified. They receive the token
     * as argument.
     * </p>
     * 
     * @param locator the locator
     * 
     * @return the integer token popped from the stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     * @throws ExBibMissingNumberException in case that no integer is found
     */
    public TInteger popInteger(Locator locator)
            throws ExBibStackEmptyException,
                ExBibMissingNumberException {

        if (literalStack.empty()) {
            throw new ExBibStackEmptyException(locator);
        }

        Token t = literalStack.pop();

        if (!(t instanceof TInteger)) {
            throw new ExBibMissingNumberException(t.toString(), locator);
        }

        return (TInteger) t;
    }

    /**
     * Pop an integer literal from the stack. If the stack is empty or the
     * element isn't an string then an error condition is raised.
     * 
     * <p>
     * The observers for {@code pop} are notified. They receive the token
     * as argument.
     * </p>
     * 
     * @param locator the locator
     * 
     * @return the string token popped from the stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     * @throws ExBibMissingStringException in case that no string is found
     * 
     * @see org.extex.exbib.core.bst.BstProcessor#popString(org.extex.exbib.core.io.Locator)
     */
    public TString popString(Locator locator)
            throws ExBibStackEmptyException,
                ExBibMissingStringException {

        if (literalStack.empty()) {
            throw new ExBibStackEmptyException(locator);
        }

        Token tos = literalStack.pop();

        if (tos == null) {
            tos = TokenFactory.T_EMPTY;
        } else if (!(tos instanceof TString)) {
            throw new ExBibMissingStringException(tos.toString(), locator);
        }

        return (TString) tos;
    }

    /**
     * Pop an element from the stack. If the stack is empty then
     * {@code null} is returned.
     * 
     * <p>
     * The observers for {@code pop} are notified if something is popped
     * from the stack. In this case they receive the token as argument.
     * </p>
     * 
     * @return the top of the stack or {@code null}
     */
    public Token popUnchecked() {

        return (literalStack.empty() ? null : literalStack.pop());
    }

    public long process(Writer writer) throws ExBibException {

        if (configuration == null) {
            throw new ConfigurationMissingException("*");
        }
        this.outWriter = writer;

        BstReaderFactory bstReaderFactory =
                new BstReaderFactory(
                    configuration.getConfiguration("BstReader"), finder);
        bstReaderFactory.newInstance().parse(this);

        for (Command command : commands) {
            runObservers.update(this, command);
            command.execute(this, command.getLocator());
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new ExBibIoException(e);
        }
        return warnings;
    }

    /**
     * Push the given token onto the stack.
     * 
     * <p>
     * The observers for {@code push} are notified. They receive the token
     * as argument.
     * </p>
     * 
     * @param token the token to push
     */
    public void push(Token token) {

        literalStack.push(token);
        pushObservers.update(this, token);
    }

    @Override
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        if ("run".equals(name)) {
            runObservers.add(observer);
        } else if ("step".equals(name)) {
            stepObservers.add(observer);
        } else if ("push".equals(name)) {
            pushObservers.add(observer);
        } else if ("pop".equals(name)) {
            popObservers.add(observer);
        } else if ("addFunction".equals(name)) {
            addFunctionObservers.add(observer);
        } else if ("changeFunction".equals(name)) {
            changeFunctionObservers.add(observer);
        } else {
            super.registerObserver(name, observer);
        }
    }

    @Override
    public void reset() {

        Locator locator = new Locator(getClass().getName() + "#reset()", 0);

        super.reset();
        entryMax = MAX_NUMBER;
        globalMax = MAX_NUMBER;

        theEntryIntegers = new ArrayList<>();
        theEntryStrings = new ArrayList<>();
        theIntegers = new ArrayList<>();
        theStrings = new ArrayList<>();
        commands = new ArrayList<>();
        if (functions == null) {
            functions = new HashMap<>();
        }
        try {
            addFunction("sort.key$", new TLocalString("sort.key$", locator),
                locator);
            addFunction("crossref", new TField("crossref", locator), locator);
            addFunction("global.max$", new TInteger(globalMax, locator),
                locator);
            addFunction("entry.max$", new TInteger(entryMax, locator), locator);
        } catch (ExBibException e) {
            throw new ConfigurationWrapperException(e);
        }
    }

    public void setEntries(List<String> entries, Locator locator)
            throws ExBibException {

        for (String entry : entries) {
            addEntry(entry);
            addFunction(entry, new TField(entry, locator), locator);
        }
    }

    public void setEntryIntegers(List<String> integers, Locator locator)
            throws ExBibException {

        for (String name : integers) {
            theEntryIntegers.add(name);
            addFunction(name, new TLocalInteger(name, null), locator);
        }
    }

    public void setEntryStrings(List<String> strings, Locator locator)
            throws ExBibException {

        for (String name : strings) {
            theEntryStrings.add(name);
            addFunction(name, new TLocalString(name, null), locator);
        }
    }

    public void setIntegers(TokenList list, Locator locator)
            throws ExBibException {

        for (Token t : list) {
            String name = t.getValue();
            addFunction(name, new MacroCode(name, new TInteger("0", locator)),
                locator);
            theIntegers.add(name);
        }
    }

    /**
     * Setter for the output writer.
     * 
     * @param writer the writer
     */
    public void setOutWriter(Writer writer) {

        outWriter = writer;
    }

    public void setResourceFinder(ResourceFinder f) {

        this.finder = f;
    }

    public void setStrings(TokenList list, Locator locator)
            throws ExBibException {

        for (Token t : list) {
            String name = t.getValue();
            addFunction(name, new MacroCode(name, new TString("", locator)),
                locator);
            theStrings.add(name);
        }
    }

    public void step(Object obj) {

        stepObservers.update(this, obj);
    }

    @Override
    public void warning(String message) {

        super.warning(message);
        warnings++;
    }

}
