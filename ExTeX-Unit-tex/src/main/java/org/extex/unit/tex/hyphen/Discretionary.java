/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.hyphen;

import org.extex.core.Locator;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.ListMakers;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.TokenDelegateListMaker;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.DiscretionaryNode;

/**
 * This class provides an implementation for the primitive
 * <code>\discretionary</code>.
 * 
 * <doc name="discretionary">
 * <h3>The Primitive <tt>\discretionary</tt></h3>
 * <p>
 * The primitive <tt>\discretionary</tt> can be used to insert an optional
 * break point into the paragraph. The optional break point consists of three
 * parts. The first part is inserted into the paragraph if no line breaking
 * happens at this position. In case that the line breaking chooses this place
 * for a line break then the second part of the discretionary is inserted at the
 * end of the current line and the third part is inserted at the beginning of
 * the next line.
 * </p>
 * <p>
 * The three parts are given as three sequences of characters in braces. It may
 * be composed of characters, ligatures, and rules only.
 * </p>
 * <p>
 * In math mode the third part is forced to be empty.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;discretionary&rang;
 *      &rarr; <tt>\discretionary</tt>{...}{...}{...}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \discretionary{f-}{fi}{ffi}
 *    \discretionary{-}{}{}  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Discretionary extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Discretionary(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Tokens pre = source.getTokens(context, source, typesetter);
        Tokens post = source.getTokens(context, source, typesetter);
        Tokens nobreak = source.getTokens(context, source, typesetter);
        // CharNodeFactory cnf = new CharNodeFactory();
        TypesettingContext tc = context.getTypesettingContext();
        Locator locator = source.getLocator();

        typesetter.add(new DiscretionaryNode(fill(pre, tc, context, source,
            typesetter, locator), //
            fill(post, tc, context, source, typesetter, locator), //
            fill(nobreak, tc, context, source, typesetter, locator)));
    }

    /**
     * This method creates a Node list for a list of tokens.
     * 
     * @param tokens the tokens to put into a NodeList
     * @param tc the typesetting context
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param locator the locator pointing to the start
     * 
     * @return the node list or <code>null</code> if there are no tokens to
     *         put into the list
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     */
    private NodeList fill(Tokens tokens, TypesettingContext tc,
            Context context, TokenSource source, Typesetter typesetter,
            Locator locator) throws TypesetterException {

        if (tokens.length() == 0) {
            return null;
        }

        typesetter.pushListMaker(ListMakers.RESTRICTED_HORIZONTAL, locator);

        for (Token t : tokens) {
            ((TokenDelegateListMaker) typesetter).letter(t.getChar(), tc,
                context, source, locator);
        }
        return typesetter.complete((TypesetterOptions) context);
    }

}
