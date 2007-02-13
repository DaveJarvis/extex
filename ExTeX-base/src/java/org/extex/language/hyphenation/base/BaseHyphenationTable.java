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

package org.extex.language.hyphenation.base;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.language.ModifiableLanguage;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.language.word.WordTokenizer;
import org.extex.type.UnicodeChar;
import org.extex.type.UnicodeCharList;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.unicode.Unicode;
import org.extex.util.framework.Registrar;

/**
 * This class stores the values for hyphenations and hyphenates words.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4142 $
 */
public class BaseHyphenationTable implements ModifiableLanguage, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Create the name for the <code>HyphenationTable</code>.
     *
     * @param pattern the pattern
     * @param context the interpreter context
     * @param key the container to store the key in
     *
     * @return the name
     */
    protected static boolean[] createHyphenation(final UnicodeCharList pattern,
            final TypesetterOptions context, final UnicodeCharList key) {

        int size = pattern.size();
        int len = 0;
        for (int i = 0; i < size; i++) {
            if (!pattern.get(i).equals(Unicode.SHY)) {
                len++;
            }
        }
        boolean[] vec = new boolean[len];

        UnicodeChar uc;
        int j = 0;

        for (int i = 0; i < size; i++) {
            uc = pattern.get(i);
            if (uc.equals(Unicode.SHY)) {
                vec[j] = true;
            } else {
                j++;
                key.add(uc);
            }
        }

        return vec;
    }

    /**
     * The field <tt>exceptionMap</tt> contains the exception words for
     * hyphenation.
     */
    private Map exceptionMap = new HashMap();

    /**
     * The field <tt>hyphenactive</tt> contains the indicator whether these
     * hyphenation is active. If the value is <code>false</code> then no
     * hyphenation points will be inserted.
     */
    private boolean hyphenactive = true;

    /**
     * The field <tt>lefthyphenmin</tt> contains the minimum distance from the
     * left side of a word before hyphenation is performed.
     */
    private long lefthyphenmin = 0;

    /**
     * The field <tt>ligatureBuilder</tt> contains the ligature builder.
     */
    private LigatureBuilder ligatureBuilder = null;

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>righthyphenmin</tt> contains the minimum distance from the
     * right side of a word before hyphenation is performed.
     */
    private long righthyphenmin = 0;

    /**
     * The field <tt>wordTokenizer</tt> contains the tokenizer to recognize
     * words.
     */
    private WordTokenizer wordTokenizer = null;

    /**
     * Creates a new object.
     */
    public BaseHyphenationTable() {

        super();
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#addHyphenation(
     *      org.extex.type.UnicodeCharList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addHyphenation(final UnicodeCharList word,
            final TypesetterOptions options) throws HyphenationException {

        UnicodeCharList key = new UnicodeCharList();
        boolean[] vec = createHyphenation(word, options, key);
        exceptionMap.put(wordTokenizer.normalize(key, options), vec);
    }

    /**
     * @see org.extex.language.Language#addPattern(
     *      Tokens)
     */
    public void addPattern(final Tokens pattern) throws HyphenationException {

    }

    /**
     * @see org.extex.language.word.WordTokenizer#findWord(
     *      org.extex.typesetter.type.NodeList,
     *      int,
     *      org.extex.type.UnicodeCharList)
     */
    public int findWord(final NodeList nodes, final int start,
            final UnicodeCharList word) throws HyphenationException {

        return wordTokenizer.findWord(nodes, start, word);
    }

    /**
     * @see org.extex.language.Language#getLeftHyphenmin()
     */
    public long getLeftHyphenmin() throws HyphenationException {

        return lefthyphenmin;
    }

    /**
     * @see org.extex.language.ligature.LigatureBuilder#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      org.extex.interpreter.type.font.Font)
     */
    public UnicodeChar getLigature(final UnicodeChar c1, final UnicodeChar c2,
            final Font f) throws HyphenationException {

        return this.ligatureBuilder.getLigature(c1, c2, f);
    }

    /**
     * @see org.extex.language.Language#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * @see org.extex.language.Language#getRightHyphenmin()
     */
    public long getRightHyphenmin() throws HyphenationException {

        return righthyphenmin;
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#hyphenate(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions,
     *      org.extex.type.UnicodeChar,
     *      int,
     *      boolean,
     *      org.extex.typesetter.type.node.factory.NodeFactory)
     */
    public boolean hyphenate(final NodeList nodes,
            final TypesetterOptions context, final UnicodeChar hyphen,
            final int start, final boolean forall, final NodeFactory nodeFactory)
            throws HyphenationException {

        if (hyphen == null || !hyphenactive || nodes.size() == 0
                || wordTokenizer == null) {
            return false;
        }
        CharNode hyphenNode = (CharNode) (nodeFactory.getNode(context
                .getTypesettingContext(), hyphen));

        boolean modified = false;
        UnicodeCharList word = new UnicodeCharList();
        int next = wordTokenizer.findWord(nodes, start, word);

        modified = hyphenateOne(nodes, context, start, word, hyphenNode);

        if (forall) {
            for (int i = next; i < nodes.size(); i = next) {
                next = wordTokenizer.findWord(nodes, i, word);

                modified = hyphenateOne(nodes, context, start, word, hyphenNode)
                        || modified;
            }
        }

        return modified;
    }

    /**
     * Hyphenate a single word.
     *
     * @param nodes the node list to consider
     * @param context the options to use
     * @param start the start index in the nodes
     * @param word the word to hyphenate
     * @param hyphenNode the node to use as hyphen
     *
     * @return <code>true</code> iff the the word has been found
     *
     * @throws HyphenationException in case of an error
     */
    public boolean hyphenateOne(final NodeList nodes,
            final TypesetterOptions context, final int start,
            final UnicodeCharList word, final CharNode hyphenNode)
            throws HyphenationException {

        boolean[] w = (boolean[]) exceptionMap.get(word);
        if (w == null) {
            return false;
        }

        boolean[] wc = (boolean[]) w.clone();
        for (int i = 0; i < lefthyphenmin; i++) {
            wc[i] = false;
        }
        for (int i = 0; i < righthyphenmin; i++) {
            wc[wc.length - i - 1] = false;
        }
        wordTokenizer.insertShy(nodes, start, wc, hyphenNode);

        return true;
    }

    /**
     * @see org.extex.language.ligature.LigatureBuilder#insertLigatures(
     *      org.extex.typesetter.type.NodeList, int)
     */
    public int insertLigatures(final NodeList list, final int start)
            throws HyphenationException {

        return this.ligatureBuilder.insertLigatures(list, start);
    }

    /**
     * @see org.extex.language.word.WordTokenizer#insertShy(
     *      org.extex.typesetter.type.NodeList,
     *      int,
     *      boolean[],
     *      org.extex.typesetter.type.node.CharNode)
     */
    public void insertShy(final NodeList nodes, final int insertionPoint,
            final boolean[] spec, final CharNode hyphenNode)
            throws HyphenationException {

        this.wordTokenizer.insertShy(nodes, insertionPoint, spec, hyphenNode);
    }

    /**
     * @see org.extex.language.Language#isHyphenActive()
     */
    public boolean isHyphenActive() throws HyphenationException {

        return hyphenactive;
    }

    /**
     * @see org.extex.language.word.WordTokenizer#normalize(
     *      org.extex.type.UnicodeCharList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public UnicodeCharList normalize(final UnicodeCharList word,
            final TypesetterOptions options) throws HyphenationException {

        return this.wordTokenizer.normalize(word, options);
    }

    /**
     * Magic method for deserialization.
     *
     * @return the reconnection result
     *
     * @throws ObjectStreamException in case of an error
     */
    protected Object readResolve() throws ObjectStreamException {

        return Registrar.reconnect(this);
    }

    /**
     * @see org.extex.language.Language#setHyphenActive(boolean)
     */
    public void setHyphenActive(final boolean active)
            throws HyphenationException {

        hyphenactive = active;
    }

    /**
     * @see org.extex.language.Language#setLeftHyphenmin(long)
     */
    public void setLeftHyphenmin(final long left) throws HyphenationException {

        lefthyphenmin = left;
    }

    /**
     * @see org.extex.language.ModifiableLanguage#setLigatureBuilder(
     *      org.extex.language.ligature.LigatureBuilder)
     */
    public void setLigatureBuilder(final LigatureBuilder builder) {

        this.ligatureBuilder = builder;
    }

    /**
     * Setter for name.
     *
     * @param name the name to set
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * @see org.extex.language.Language#setRightHyphenmin(long)
     */
    public void setRightHyphenmin(final long right) throws HyphenationException {

        righthyphenmin = right;
    }

    /**
     * Setter for wordTokenizer.
     *
     * @param wordTokenizer the wordTokenizer to set
     */
    public void setWordTokenizer(final WordTokenizer wordTokenizer) {

        this.wordTokenizer = wordTokenizer;
    }

}
