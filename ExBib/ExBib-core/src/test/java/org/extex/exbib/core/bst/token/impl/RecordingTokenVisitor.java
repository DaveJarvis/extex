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

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TStringOption;
import org.extex.exbib.core.bst.token.impl.TokenList;

/**
 * This is a {@link TokenVisitor} for the tests.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class RecordingTokenVisitor implements TokenVisitor {

    /**
     * The field <tt>t</tt> contains the last visited token.
     */
    private Token t = null;

    /**
     * Getter for the visited token.
     * 
     * @return the token
     */
    public Token getVisited() {

        return t;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
     *      java.lang.Object[])
     */
    public void visitBlock(TBlock block, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = block;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
     *      java.lang.Object[])
     */
    public void visitChar(TChar c, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = c;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
     *      java.lang.Object[])
     */
    public void visitField(TField field, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = field;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
     *      java.lang.Object[])
     */
    public void visitInteger(TInteger integer, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = integer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitIntegerOption(org.extex.exbib.core.bst.token.impl.TIntegerOption,
     *      java.lang.Object[])
     */
    public void visitIntegerOption(TIntegerOption option, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = option;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
     *      java.lang.Object[])
     */
    public void visitLiteral(TLiteral literal, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = literal;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
     *      java.lang.Object[])
     */
    public void visitLocalInteger(TLocalInteger integer, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = integer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
     *      java.lang.Object[])
     */
    public void visitLocalString(TLocalString string, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = string;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
     *      java.lang.Object[])
     */
    public void visitQLiteral(TQLiteral qliteral, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = qliteral;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
     *      java.lang.Object[])
     */
    public void visitString(TString string, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = string;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitStringOption(org.extex.exbib.core.bst.token.impl.TStringOption,
     *      java.lang.Object[])
     */
    public void visitStringOption(TStringOption option, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = option;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
     *      java.lang.Object[])
     */
    public void visitTokenList(TokenList list, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = list;
    }

}
