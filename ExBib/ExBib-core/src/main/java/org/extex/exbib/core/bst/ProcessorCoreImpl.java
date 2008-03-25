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

package org.extex.exbib.core.bst;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.TokenFactory;
import org.extex.exbib.core.bst.node.impl.TField;
import org.extex.exbib.core.bst.node.impl.TFieldInteger;
import org.extex.exbib.core.bst.node.impl.TFieldString;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.bst.node.impl.TokenList;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibIoException;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;
import org.extex.exbib.core.util.ObserverList;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This is the core implementation of a BibT<sub>E</sub>X processor.
 * <p>
 * ...
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class ProcessorCoreImpl implements Processor, Bibliography, Observable {

    /**
     * The field <tt>commands</tt> contains the list of commands to process.
     */
    private List<Command> commands;

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /** String[lowerCase] => String[original] */
    private Map<String, String> citations;

    /** String => Code */
    private Map<String, Code> functions = new HashMap<String, Code>();

    /** List of observers triggered when functions are added. */
    private ObserverList addFunctionObservers = new ObserverList();

    /** List of observers triggered when functions are changed. */
    private ObserverList changeFunctionObservers = new ObserverList();

    /** List of observers triggered when the parsing is started */
    private ObserverList endParseObservers = new ObserverList();

    /** List of observers triggered when the parsing is completed */
    private ObserverList parseObservers = new ObserverList();

    /** List of observers triggered when a Token is popped. */
    private ObserverList popObservers = new ObserverList();

    /** List of observers triggered when a token is pushed. */
    private ObserverList pushObservers = new ObserverList();

    /** List of observers triggered when processing a command */
    private ObserverList runObservers = new ObserverList();

    /** List of observers triggered when the parsing starts */
    private ObserverList startParseObservers = new ObserverList();

    /** List of observers triggered when the parsing ends */
    private ObserverList startReadObservers = new ObserverList();

    /** List of observers triggered by the execution of one step */
    private ObserverList stepObservers = new ObserverList();

    /** the list of bibliography databases to consider */
    private List<String> bibliographyDatabases;

    /** the list of bibliography styles to load and use */
    private List<String> bibliographyStyles;

    /** the list of entries */
    private List<String> theEntries;

    /** the list of local integers */
    private List<String> theEntryIntegers;

    /** the list of local strings */
    private List<String> theEntryStrings;

    /** the list of integers */
    private List<String> theIntegers;

    /** the list of strings */
    private List<String> theStrings;

    /** the stack: the central data structure for the execution of a program */
    private Stack<Token> literalStack = new Stack<Token>();

    /** the writer for logging purposes */
    private Logger logger = null;

    /** the output writer */
    private Writer outWriter = null;

    /**
     * This is an obsolete constant denoting the maximum size of an entry. The
     * value is read from the configuration or a default is used.
     */
    private int entryMax = 0x7fff;

    /**
     * This is an obsolete constant denoting the maximum size of an string. The
     * value is read from the configuration or a default is used.
     */
    private int globalMax = 0x7fff;

    /**
     * the minCrossrefs parameter. This value is overwritten from a
     * configuration and passed to the database.
     */
    private int minCrossrefs = 2;

    /** ... */
    private long warnings = 0;

    /**
     * Creates a new Processor object. This method is mainly meant to be used in
     * the factory. Please make sure that database, log writer, and out writer
     * are set immediately.
     * 
     * @throws ExBibImpossibleException if an programming error has been
     *         detected
     */
    public ProcessorCoreImpl() throws ExBibImpossibleException {

        super();

        try {
            reset();
        } catch (ExBibException e) {
            throw new ExBibImpossibleException(e);
        }
    }

    /**
     * Create a new Processor object.
     * 
     * @param db The database associated with this processor.
     * @param out This argument is a writer which receives the normal output. It
     *        is an object implementing the interface <code>Writer</code>.
     * @param log This argument is a writer which receives the logging output.
     *        It is an object implementing the interface <code>Writer</code>.
     * 
     * @throws ExBibImpossibleException if an programming error has been
     *         detected
     */
    public ProcessorCoreImpl(DB db, Writer out, Logger log)
            throws ExBibImpossibleException {

        super();
        this.db = db;
        this.outWriter = out;
        this.logger = log;

        try {
            reset();
        } catch (ExBibException e) {
            throw new ExBibImpossibleException(e);
        }
    }

    /**
     * Setter for bib data.
     * <p>
     * This setter takes an array of names of bibliography databases. Those are
     * stored in the processor context and passed to the database as required.
     * </p>
     * 
     * @param sa ...
     * 
     * @see org.extex.exbib.core.bst.Bibliography#addBibliographyDatabase(
     *      java.lang.String[])
     */
    public void addBibliographyDatabase(String[] sa) {

        for (int i = 0; i < sa.length; i++) {
            bibliographyDatabases.add(sa[i]);
        }
    }

    /**
     * Setter for bib style. The bib style is the name of he BST file to use for
     * processing the database.
     * 
     * @param style the new bib style
     * 
     * @see org.extex.exbib.core.bst.Bibliography#addBibliographyStyle(
     *      java.lang.String[])
     */
    public void addBibliographyStyle(String... style) {

        for (int i = 0; i < style.length; i++) {
            bibliographyStyles.add(style[i]);
        }
    }

    /**
     * Setter for citations. The citations already existing in the processor
     * context are augmented by the ones given.
     * 
     * @param sa the String list of citations
     * 
     * @see org.extex.exbib.core.bst.Bibliography#addCitation(java.lang.String[])
     */
    public void addCitation(String[] sa) {

        for (int i = 0; i < sa.length; i++) {
            citations.put(sa[i].toLowerCase(), sa[i]);
        }
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

    /**
     * Define a new function if not already defined in the processor context. If
     * the function has been defined already then a exception is thrown.
     * 
     * @param name name of the function
     * @param body code to be executed in case it is called
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    public void addFunction(String name, Code body)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        Locator locator = null; // TODO
        if (name == null || name.equals("")) {
            throw new ExBibIllegalValueException("Illegal function name",
                locator);
        }

        if (functions.get(name) != null) {
            throw new ExBibFunctionExistsException(name, locator);
        }

        functions.put(name, body);
    }

    /**
     * Store an additional <tt>STRING</tt> in the database. To delete a
     * <tt>STRING</tt> the value <code>null</code> can be used.
     * 
     * @param name the name of the macro to add
     * @param value the value as Token
     */
    public void addMacro(String name, Token value) {

        db.storeString(name, (value == null ? null : new Value(new VString(
            value.getValue()))));
    }

    /**
     * Change the meaning of a function definition. If the function does not
     * exist then an exception is thrown.
     * 
     * @param name the name of the function to change
     * @param body the new code for the function
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionUndefinedException in case that the function isn't
     *         defined yet
     */
    public void changeFunction(String name, Code body)
            throws ExBibIllegalValueException,
                ExBibFunctionUndefinedException {

        Locator locator = null; // TODO
        if (name == null || name.equals("")) {
            throw new ExBibIllegalValueException("Illegal function name",
                locator);
        }

        if (functions.get(name) == null) {
            throw new ExBibFunctionUndefinedException(name, locator);
        }

        functions.put(name, body);
    }

    /**
     * Get an iterator over all commands.
     * 
     * @return the iterator
     */
    public Iterator<Command> commandsIterator() {

        return commands.iterator();
    }

    /**
     * Configure the current instance. The following values are considered for
     * configuration:
     * <dl>
     * <dd>minCrossrefs</dd>
     * <dt>the minimum crossrefs considered when collapsing records </dt>
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
    public void configure(Configuration config) throws ConfigurationException {

        int i = config.getValueAsInteger("minCrossrefs", -1);

        if (i >= 0) {
            minCrossrefs = i;
        }

        i = config.getValueAsInteger("globalMax", -1);

        if (i >= 0) {
            try {
                changeFunction("global.max$", new TInteger(i));
                globalMax = i;
            } catch (ExBibException e) {
                throw new ConfigurationWrapperException(e);
            }
        }

        i = config.getValueAsInteger("entryMax", -1);

        if (i >= 0) {
            try {
                entryMax = i;
                changeFunction("entry.max$", new TInteger(i));
            } catch (ExBibException e) {
                throw new ConfigurationWrapperException(e);
            }
        }
    }

    /**
     * Getter for bib style. The bib style is the name of he BST file to use for
     * processing the database.
     * 
     * @see org.extex.exbib.core.bst.Processor#getBibliographyStyles()
     */
    public List<String> getBibliographyStyles() {

        return bibliographyStyles;
    }

    /**
     * Get the original cite key for a given key. I.e. the casing might be
     * different.
     * 
     * @param key the citation key
     * 
     * @return the original citation key
     */
    public String getCite(String key) {

        return (citations.get(key.toLowerCase()));
    }

    /**
     * Getter for the database.
     * 
     * @return the database
     */
    public DB getDB() {

        return db;
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getEntries()
     */
    public List<String> getEntries() {

        return theEntries;
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getEntryIntegers()
     */
    public List<String> getEntryIntegers() {

        return theEntryIntegers;
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getEntryStrings()
     */
    public List<String> getEntryStrings() {

        return theEntryStrings;
    }

    /**
     * Getter for function code.
     * 
     * @param name the name of the function to retrieve
     * 
     * @return the code for the function or null if none is found
     */
    public Code getFunction(String name) {

        return (functions.get(name));
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getFunctionNames()
     */
    public List<String> getFunctionNames() {

        List<String> sl = new ArrayList<String>();
        Iterator<String> iterator = functions.keySet().iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            Code code = (getFunction(name));

            if (code instanceof MacroCode
                    && ((MacroCode) code).getToken() instanceof TokenList) {
                sl.add(name);
            }
        }

        return sl;
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getIntegers()
     */
    public List<String> getIntegers() {

        return theIntegers;
    }

    /**
     * Getter for the log writer.
     * 
     * @return the log writer
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Getter for a certain macro.
     * 
     * @param name the name of the macro to search for
     * 
     * @return the expanded value of the macro or <code>null</code> if none
     *         has been found.
     */
    public String getMacro(String name) {

        return db.getExpandedMacro(name);
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getMacroNames()
     */
    public List<String> getMacroNames() {

        return db.getMacroNames();
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#getNumberOfWarnings()
     */
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

    /**
     * @see org.extex.exbib.core.bst.Processor#getStrings()
     */
    public List<String> getStrings() {

        return theStrings;
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#loadDB()
     */
    public void loadDB()
            throws ExBibException,
                FileNotFoundException,
                ConfigurationException {

        Iterator<String> iterator = bibliographyDatabases.iterator();

        while (iterator.hasNext()) {
            String file = iterator.next();

            startReadObservers.update(this, file);
            db.load(file, citations, this);
        }
    }

    /**
     * Pop an element from the stack. If the stack is empty an exception is
     * thrown.
     * 
     * <p>
     * The observers for <code>pop</code> are notified. They receive the token
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
     * The observers for <code>pop</code> are notified. They receive the token
     * as argument.
     * </p>
     * 
     * @param locator the locator
     * 
     * @return the integer token popped from the stack
     * 
     * @throws ExBibStackEmptyException in case that no element is left to pop
     * @throws ExBibMissingNumberException in case that no integer is found
     * 
     * @see org.extex.exbib.core.bst.Processor#popInteger(
     *      org.extex.exbib.core.io.Locator)
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
     * The observers for <code>pop</code> are notified. They receive the token
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
     * @see org.extex.exbib.core.bst.Processor#popString(
     *      org.extex.exbib.core.io.Locator)
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
     * <code>null</code> is returned.
     * 
     * <p>
     * The observers for <code>pop</code> are notified if something is popped
     * from the stack. In this case they receive the token as argument.
     * </p>
     * 
     * @return the top of the stack or <code>null</code>
     */
    public Token popUnchecked() {

        return (literalStack.empty() ? null : literalStack.pop());
    }

    /**
     * Run the procedures stored in the processor context. For each procedure
     * the hook `run' is called before it is executed.
     * 
     * @param writer the output writer
     * @param logger the logger
     * 
     * @throws ExBibException in case something went wrong
     * @throws ExBibIoException in case of an IOException
     */
    public void process(Writer writer, Logger logger) throws ExBibException {

        this.outWriter = writer;
        this.logger = logger;

        Iterator<Command> iterator = commands.iterator();

        while (iterator.hasNext()) {
            Command command = iterator.next();
            runObservers.update(this, command);
            command.execute(this, command.getLocator());
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new ExBibIoException(e);
        }
    }

    /**
     * Push the given token onto the stack.
     * 
     * <p>
     * The observers for <code>push</code> are notified. They receive the
     * token as argument.
     * </p>
     * 
     * @param token the token to push
     */
    public void push(Token token) {

        literalStack.push(token);
        pushObservers.update(this, token);
    }

    /**
     * @see org.extex.exbib.core.util.Observable#registerObserver(java.lang.String,
     *      org.extex.exbib.core.util.Observer)
     */
    public void registerObserver(String name, Observer observer)
            throws NotObservableException {

        if (name.equals("run")) {
            runObservers.add(observer);
        } else if (name.equals("step")) {
            stepObservers.add(observer);
        } else if (name.equals("push")) {
            pushObservers.add(observer);
        } else if (name.equals("pop")) {
            popObservers.add(observer);
        } else if (name.equals("startParse")) {
            startParseObservers.add(observer);
        } else if (name.equals("parse")) {
            parseObservers.add(observer);
        } else if (name.equals("endParse")) {
            endParseObservers.add(observer);
        } else if (name.equals("startRead")) {
            startReadObservers.add(observer);
        } else if (name.equals("addFunction")) {
            addFunctionObservers.add(observer);
        } else if (name.equals("changeFunction")) {
            changeFunctionObservers.add(observer);
        } else {
            throw new NotObservableException(name);
        }
    }

    /**
     * @see org.extex.exbib.core.bst.Bibliography#reset()
     */
    public void reset()
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        citations = new HashMap<String, String>();
        bibliographyStyles = new ArrayList<String>();
        bibliographyDatabases = new ArrayList<String>();
        theEntries = new ArrayList<String>();
        theEntryIntegers = new ArrayList<String>();
        theEntryStrings = new ArrayList<String>();
        theIntegers = new ArrayList<String>();
        theStrings = new ArrayList<String>();
        commands = new ArrayList<Command>();
        addFunction("global.max$", new TInteger(globalMax));
        addFunction("entry.max$", new TInteger(entryMax));
        addFunction("sort.key$", new TFieldString("sort.key$", null)); //$NON-NLS-2$
        addFunction("crossref", new TField("crossref")); //$NON-NLS-2$
    }

    /**
     * Setter for the database.
     * 
     * @param db the database to be used
     */
    public void setDB(DB db) {

        this.db = db;
    }

    /**
     * Setter for the entry names.
     * 
     * @param entries the list of entries
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    public void setEntries(List<String> entries)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        Iterator<String> iterator = entries.iterator();

        while (iterator.hasNext()) {
            String entry = iterator.next();
            theEntries.add(entry);
            addFunction(entry, new TField(entry));
        }
    }

    /**
     * Setter for local integers. The given arguments are added to the values
     * already stored.
     * 
     * @param integers the list of integers
     * 
     * @throws ExBibIllegalValueException in case that the name is
     *         <code>null</code> or empty
     * @throws ExBibFunctionExistsException in case that the named function
     *         already exists
     */
    public void setEntryIntegers(List<String> integers)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        Iterator<String> iterator = integers.iterator();

        while (iterator.hasNext()) {
            String e = iterator.next();
            theEntryIntegers.add(e);
            addFunction(e, new TFieldInteger(e, null));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Processor#setEntryStrings(java.util.List)
     */
    public void setEntryStrings(List<String> strings)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        Iterator<String> iterator = strings.iterator();

        while (iterator.hasNext()) {
            String e = iterator.next();
            theEntryStrings.add(e);
            addFunction(e, new TFieldString(e, null));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Processor#setIntegers(
     *      org.extex.exbib.core.bst.node.impl.TokenList)
     */
    public void setIntegers(TokenList list) throws ExBibException {

        Locator locator = null; // TODO
        Iterator<Token> iterator = list.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next().getValue();
            addFunction(name, new MacroCode(new TInteger("0", locator)));
            theIntegers.add(name);
        }
    }

    /**
     * Setter for the logger.
     * 
     * @param logger the new logger
     */
    public void setLogWriter(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for minCrossrefs.
     * 
     * @param min the new value.
     */
    public void setMinCrossrefs(int min) {

        minCrossrefs = min;
    }

    /**
     * Setter for the output writer.
     * 
     * @param writer
     */
    public void setOutWriter(Writer writer) {

        outWriter = writer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Processor#setStrings(org.extex.exbib.core.bst.node.impl.TokenList)
     */
    public void setStrings(TokenList list)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        Iterator<Token> iterator = list.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next().getValue();
            addFunction(name, new MacroCode(new TString("")));
            theStrings.add(name);
        }
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#step(java.lang.Object)
     */
    public void step(Object obj) {

        stepObservers.update(this, obj);
    }

    /**
     * @see org.extex.exbib.core.bst.Processor#warning(java.lang.String)
     */
    public void warning(String message) {

        if (logger != null) {
            logger.warning(message);
        }
        warnings++;
    }

}
