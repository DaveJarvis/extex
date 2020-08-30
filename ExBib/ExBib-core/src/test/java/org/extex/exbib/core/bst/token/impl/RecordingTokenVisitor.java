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

/**
 * This is a {@link TokenVisitor} for the tests.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class RecordingTokenVisitor implements TokenVisitor {

    /**
     * The field {@code t} contains the last visited token.
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
*      java.lang.Object[])
     */
    public void visitBlock(TBlock block, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = block;
    }

    /**
*      java.lang.Object[])
     */
    public void visitChar(TChar c, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = c;
    }

    /**
*      java.lang.Object[])
     */
    public void visitField(TField field, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = field;
    }

    /**
*      java.lang.Object[])
     */
    public void visitInteger(TInteger integer, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = integer;
    }

    /**
*      java.lang.Object[])
     */
    public void visitIntegerOption(TIntegerOption option, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = option;
    }

    /**
*      java.lang.Object[])
     */
    public void visitLiteral(TLiteral literal, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = literal;
    }

    /**
*      java.lang.Object[])
     */
    public void visitLocalInteger(TLocalInteger integer, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = integer;
    }

    /**
*      java.lang.Object[])
     */
    public void visitLocalLocator(TLocalLocator localLocator, Object[] args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = localLocator;
    }

    /**
*      java.lang.Object[])
     */
    public void visitLocalString(TLocalString string, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = string;
    }

    /**
*      java.lang.Object[])
     */
    public void visitQLiteral(TQLiteral qliteral, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = qliteral;
    }

    /**
*      java.lang.Object[])
     */
    public void visitString(TString string, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = string;
    }

    /**
*      java.lang.Object[])
     */
    public void visitStringOption(TStringOption option, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = option;
    }

    /**
*      java.lang.Object[])
     */
    public void visitTokenList(TokenList list, Object... args) {

        if (t != null) {
            throw new IllegalStateException();
        }
        t = list;
    }

}
