/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.language.impl;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.type.UnicodeChar;
import org.extex.type.UnicodeCharList;
import org.extex.util.framework.Registrar;

import de.dante.extex.typesetter.TypesetterOptions;
import de.dante.extex.typesetter.type.NodeList;
import de.dante.extex.typesetter.type.node.CharNode;
import de.dante.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This class implements the future pattern for a language object. The real
 * object creation or loading is delayed until it is clear whether the
 * loading or the creation should be performed.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FutureLanguage implements ManagedLanguage, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060306L;

    /**
     * The field <tt>creator</tt> contains the creator which should be contacted
     * to perform the real task.
     */
    private transient LanguageCreator creator = null;

    /**
     * The field <tt>language</tt> contains the language for which we are acting
     * as proxy.
     */
    private Language language = null;

    /**
     * The field <tt>name</tt> contains the name of the language for the
     * creator.
     */
    private String name;

    /**
     * Creates a new object.
     *
     * @param index the name of the language for the creator
     * @param creator the creator which should be contacted to perform the
     *  real task
     */
    public FutureLanguage(final String index, final LanguageCreator creator) {

        super();
        this.name = index;
        this.creator = creator;
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#addHyphenation(
     *      org.extex.type.UnicodeCharList,
     *      de.dante.extex.typesetter.TypesetterOptions)
     */
    public void addHyphenation(final UnicodeCharList word,
            final TypesetterOptions context) throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        language.addHyphenation(word, context);
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#addPattern(
     *      de.dante.extex.interpreter.type.tokens.Tokens)
     */
    public void addPattern(final Tokens pattern) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.addPattern(pattern);
    }

    /**
     * @see org.extex.language.word.WordTokenizer#findWord(
     *      de.dante.extex.typesetter.type.NodeList,
     *      int,
     *      org.extex.type.UnicodeCharList)
     */
    public int findWord(final NodeList nodes, final int start,
            final UnicodeCharList word) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        return language.findWord(nodes, start, word);
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#getLeftHyphenmin()
     */
    public long getLeftHyphenmin() throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.getLeftHyphenmin();
    }

    /**
     * @see org.extex.language.ligature.LigatureBuilder#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      de.dante.extex.interpreter.type.font.Font)
     */
    public UnicodeChar getLigature(final UnicodeChar c1, final UnicodeChar c2,
            final Font f) throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.getLigature(c1, c2, f);
    }

    /**
     * @see org.extex.language.Language#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#getRightHyphenmin()
     */
    public long getRightHyphenmin() throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.getRightHyphenmin();
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#hyphenate(
     *      de.dante.extex.typesetter.type.NodeList,
     *      de.dante.extex.typesetter.TypesetterOptions,
     *      org.extex.type.UnicodeChar,
     *      int,
     *      boolean,
     *      de.dante.extex.typesetter.type.node.factory.NodeFactory)
     */
    public boolean hyphenate(final NodeList nodelist,
            final TypesetterOptions context, final UnicodeChar hyphen,
            final int start, final boolean forall, final NodeFactory nodeFactory)
            throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.hyphenate(nodelist, context, hyphen, start, forall,
                nodeFactory);
    }

    /**
     * @see org.extex.language.ligature.LigatureBuilder#insertLigatures(
     *      de.dante.extex.typesetter.type.NodeList, int)
     */
    public int insertLigatures(final NodeList list, final int start)
            throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.insertLigatures(list, start);
    }

    /**
     * @see org.extex.language.word.WordTokenizer#insertShy(
     *      de.dante.extex.typesetter.type.NodeList,
     *      int,
     *      boolean[],
     *      de.dante.extex.typesetter.type.node.CharNode)
     */
    public void insertShy(final NodeList nodes, final int insertionPoint,
            final boolean[] spec, final CharNode hyphenNode)
            throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.insertShy(nodes, insertionPoint, spec, hyphenNode);
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#isHyphenActive()
     */
    public boolean isHyphenActive() throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.isHyphenActive();
    }

    /**
     * @see org.extex.language.word.WordTokenizer#normalize(
     *      org.extex.type.UnicodeCharList,
     *      de.dante.extex.typesetter.TypesetterOptions)
     */
    public UnicodeCharList normalize(final UnicodeCharList word,
            final TypesetterOptions options) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        return language.normalize(word, options);
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
     * @see de.dante.extex.language.impl.ManagedLanguage#setCreator(
     *      org.extex.language.LanguageManager)
     */
    public void setCreator(final LanguageCreator creator) {

        this.creator = creator;
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#setHyphenActive(boolean)
     */
    public void setHyphenActive(final boolean active)
            throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.setHyphenActive(active);
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#setLeftHyphenmin(long)
     */
    public void setLeftHyphenmin(final long left) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.setLeftHyphenmin(left);
    }

    /**
     * @see org.extex.language.Language#setName(java.lang.String)
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * @see org.extex.language.hyphenation.Hyphenator#setRightHyphenmin(long)
     */
    public void setRightHyphenmin(final long right) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.setRightHyphenmin(right);
    }

}
