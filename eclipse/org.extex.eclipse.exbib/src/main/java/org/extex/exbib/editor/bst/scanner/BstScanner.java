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

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.extex.exbib.editor.bst.BstWhitespaceDetector;
import org.extex.exbib.editor.bst.ColorManager;
import org.extex.exbib.editor.bst.properties.BstEditorPreferences;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstScanner extends RuleBasedScanner {

    /**
     * The field <tt>numberDetector</tt> contains the ...
     */
    private IWordDetector numberDetector = new IWordDetector() {

        @Override
        public boolean isWordPart(char c) {

            return Character.isDigit(c);
        }

        @Override
        public boolean isWordStart(char c) {

            return c == '#';
        }
    };

    /**
     * The field <tt>numberDetector</tt> contains the ...
     */
    private IWordDetector quoteDetector = new IWordDetector() {

        @Override
        public boolean isWordPart(char c) {

            return !Character.isWhitespace(c) && "{}#%\"\'".indexOf(c) < 0;
        }

        @Override
        public boolean isWordStart(char c) {

            return c == '\'';
        }
    };

    /**
     * Creates a new object.
     * 
     * @param manager the color manager
     */
    public BstScanner(ColorManager manager) {

        setDefaultReturnToken(new Token(new TextAttribute(manager
            .getColor(BstEditorPreferences.PREFERENCE_FG_DEFAULT))));

        Token commandToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_COMMAND),
                    null, TextAttribute.UNDERLINE));
        Token builtinToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_KEYWORD)));
        WordRule commandRule = new WordRule(new BstWordDetector(), //
            new Token(new TextAttribute(null)), true);
        commandRule.addWord("entry", commandToken);
        commandRule.addWord("function", commandToken);
        commandRule.addWord("macro", commandToken);
        commandRule.addWord("strings", commandToken);
        commandRule.addWord("integers", commandToken);
        commandRule.addWord("read", commandToken);
        commandRule.addWord("execute", commandToken);
        commandRule.addWord("sort", commandToken);
        commandRule.addWord("iterate", commandToken);
        commandRule.addWord("reverse", commandToken);

        commandRule.addWord("+", builtinToken);
        commandRule.addWord("*", builtinToken);
        commandRule.addWord("-", builtinToken);
        commandRule.addWord("=", builtinToken);
        commandRule.addWord(":=", builtinToken);
        commandRule.addWord("add.period$", builtinToken);
        commandRule.addWord("change.case$", builtinToken);
        commandRule.addWord("chr.to.int$", builtinToken);
        commandRule.addWord("cite$", builtinToken);
        commandRule.addWord("duplicate$", builtinToken);
        commandRule.addWord("empty$", builtinToken);
        commandRule.addWord("format.name$", builtinToken);
        commandRule.addWord("if$", builtinToken);
        commandRule.addWord("newline$", builtinToken);
        commandRule.addWord("num.names$", builtinToken);
        commandRule.addWord("pop$", builtinToken);
        commandRule.addWord("purify$", builtinToken);
        commandRule.addWord("quote$", builtinToken);
        commandRule.addWord("skip$", builtinToken);
        commandRule.addWord("substring$", builtinToken);
        commandRule.addWord("swap$", builtinToken);
        commandRule.addWord("text.length$", builtinToken);
        commandRule.addWord("text.prefix$", builtinToken);
        commandRule.addWord("type$", builtinToken);
        commandRule.addWord("warning$", builtinToken);
        commandRule.addWord("while$", builtinToken);
        commandRule.addWord("write$", builtinToken);

        setRules(new IRule[]{
                new EndOfLineRule("%", new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_COMMENT)))), //
                commandRule,
                new WordRule(numberDetector, new Token(
                    new TextAttribute(manager
                        .getColor(BstEditorPreferences.PREFERENCE_FG_NUMBER)))),
                new WordRule(quoteDetector, new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_QUOTE)))),
                new SingleLineRule("\"", "\"", new Token(
                    new TextAttribute(manager
                        .getColor(BstEditorPreferences.PREFERENCE_FG_STRING))),
                    '\0'), //
                new WhitespaceRule(new BstWhitespaceDetector())});
    }
}
