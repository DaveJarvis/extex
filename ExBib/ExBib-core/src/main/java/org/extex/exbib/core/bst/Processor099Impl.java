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

import java.util.logging.Logger;

import org.extex.exbib.core.bst.code.impl.AddPeriod;
import org.extex.exbib.core.bst.code.impl.CallType;
import org.extex.exbib.core.bst.code.impl.ChangeCase;
import org.extex.exbib.core.bst.code.impl.ChrToInt;
import org.extex.exbib.core.bst.code.impl.Cite;
import org.extex.exbib.core.bst.code.impl.Concat;
import org.extex.exbib.core.bst.code.impl.Duplicate;
import org.extex.exbib.core.bst.code.impl.Empty;
import org.extex.exbib.core.bst.code.impl.Eq;
import org.extex.exbib.core.bst.code.impl.FormatName099;
import org.extex.exbib.core.bst.code.impl.Gt;
import org.extex.exbib.core.bst.code.impl.If;
import org.extex.exbib.core.bst.code.impl.IntToChr;
import org.extex.exbib.core.bst.code.impl.IntToStr;
import org.extex.exbib.core.bst.code.impl.Lt;
import org.extex.exbib.core.bst.code.impl.Minus;
import org.extex.exbib.core.bst.code.impl.Missing;
import org.extex.exbib.core.bst.code.impl.Newline;
import org.extex.exbib.core.bst.code.impl.NumNames;
import org.extex.exbib.core.bst.code.impl.Plus;
import org.extex.exbib.core.bst.code.impl.Pop;
import org.extex.exbib.core.bst.code.impl.Preamble;
import org.extex.exbib.core.bst.code.impl.Purify;
import org.extex.exbib.core.bst.code.impl.Quote;
import org.extex.exbib.core.bst.code.impl.Set;
import org.extex.exbib.core.bst.code.impl.Skip;
import org.extex.exbib.core.bst.code.impl.Stack;
import org.extex.exbib.core.bst.code.impl.Substring;
import org.extex.exbib.core.bst.code.impl.Swap;
import org.extex.exbib.core.bst.code.impl.TextLength;
import org.extex.exbib.core.bst.code.impl.TextPrefix;
import org.extex.exbib.core.bst.code.impl.Top;
import org.extex.exbib.core.bst.code.impl.Type;
import org.extex.exbib.core.bst.code.impl.Warning;
import org.extex.exbib.core.bst.code.impl.While;
import org.extex.exbib.core.bst.code.impl.Width;
import org.extex.exbib.core.bst.code.impl.Write;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Writer;

/**
 * This implementation of a processor provides the functionality of BibT<sub>E</sub>X
 * 0.99c.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Processor099Impl extends ProcessorCoreImpl {

    /**
     * Creates a new object.
     * 
     * @throws ExBibException in case that the constructor of the superclass
     *         throws one.
     */
    public Processor099Impl() throws ExBibException {

        super();
        init();
    }

    /**
     * Creates a new object.
     * 
     * @param db the database to use
     * @param outWriter the output writer
     * @param logger the logger
     * 
     * @throws ExBibException in case that the superclass has something to
     *         complain or one of the functions is already defined by the
     *         superclass
     */
    public Processor099Impl(DB db, Writer outWriter, Logger logger)
            throws ExBibException {

        super(db, outWriter, logger);
        init();
    }

    /**
     * Initializing all functions.
     * 
     * @throws ExBibException just in case some of the function is already
     *         defined. This should not happen.
     */
    private void init() throws ExBibException {

        addFunction(">", new Gt(">"));
        addFunction("<", new Lt("<"));
        addFunction("=", new Eq("="));
        addFunction("+", new Plus("+"));
        addFunction("-", new Minus("-"));
        addFunction("*", new Concat("*"));
        addFunction(":=", new Set(":="));
        addFunction("add.period$", new AddPeriod("add.period$"));
        addFunction("call.type$", new CallType("call.type$"));
        addFunction("change.case$", new ChangeCase("change.case$"));
        addFunction("chr.to.int$", new ChrToInt("chr.to.int$"));
        addFunction("cite$", new Cite("cite$"));
        addFunction("duplicate$", new Duplicate("duplicate$"));
        addFunction("empty$", new Empty("empty$"));
        addFunction("format.name$", new FormatName099("format.name$"));
        addFunction("if$", new If("if$"));
        addFunction("int.to.chr$", new IntToChr("int.to.chr$"));
        addFunction("int.to.str$", new IntToStr("int.to.str$"));
        addFunction("missing$", new Missing("missing$"));
        addFunction("newline$", new Newline("newline$"));
        addFunction("num.names$", new NumNames("num.names$"));
        addFunction("pop$", new Pop("pop$"));
        addFunction("preamble$", new Preamble("preamble$"));
        addFunction("purify$", new Purify("purify$"));
        addFunction("quote$", new Quote("quote$"));
        addFunction("skip$", new Skip("skip$"));
        addFunction("stack$", new Stack("stack$"));
        addFunction("substring$", new Substring("substring$"));
        addFunction("swap$", new Swap("swap$"));
        addFunction("text.length$", new TextLength("text.length$"));
        addFunction("text.prefix$", new TextPrefix("text.prefix$"));
        addFunction("top$", new Top("top$"));
        addFunction("type$", new Type("type$"));
        addFunction("warning$", new Warning("warning$"));
        addFunction("while$", new While("while$"));
        addFunction("width$", new Width("width$"));
        addFunction("write$", new Write("write$"));
    }

}
