/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.editor.bst.scanner;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstPartitionScanner extends RuleBasedPartitionScanner {

    /**
     * The field <tt>BST_COMMENT</tt> contains the ...
     */
    public final static String BST_COMMENT = "%_comment";

    /**
     * The field <tt>BST_CODE</tt> contains the ...
     */
    public final static String BST_CODE = "%_code";

    /**
     * The field <tt>CODE_TOKEN</tt> contains the ...
     */
    private static final Token CODE_TOKEN = new Token(BST_CODE);

    /**
     * Creates a new object.
     * 
     */
    public BstPartitionScanner() {

        setDefaultReturnToken(CODE_TOKEN);

        setPredicateRules(new IPredicateRule[]{
                new EndOfLineRule("%", new Token(BST_COMMENT)), //
                new IPredicateRule() {

                    @Override
                    public IToken evaluate(ICharacterScanner scanner) {

                        int c = scanner.read();
                        if (c == '%') {
                            scanner.unread();
                            return Token.UNDEFINED;
                        } else if (c < 0) {
                            return Token.EOF;
                        }
                        do {
                            if (c == '%') {
                                scanner.unread();
                                return CODE_TOKEN;
                            }
                            if (c == '"') {
                                do {
                                    c = scanner.read();
                                } while (c >= 0 && c != '"');

                            }
                            c = scanner.read();
                        } while (c >= 0);
                        return CODE_TOKEN;
                    }

                    @Override
                    public IToken evaluate(ICharacterScanner scanner,
                            boolean resume) {

                        return evaluate(scanner);
                    }

                    @Override
                    public IToken getSuccessToken() {

                        return CODE_TOKEN;
                    }
                }});
    }
}
