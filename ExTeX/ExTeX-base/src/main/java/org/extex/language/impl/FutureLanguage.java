/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.language.impl;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.framework.Registrar;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This class implements the future pattern for a language object. The real
 * object creation or loading is delayed until it is clear whether the
 * loading or the creation should be performed.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
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
    public FutureLanguage(String index, LanguageCreator creator) {

        this.name = index;
        this.creator = creator;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#addHyphenation(
     *      org.extex.core.UnicodeCharList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void addHyphenation(UnicodeCharList word,
            TypesetterOptions context) throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        language.addHyphenation(word, context);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#addPattern(
     *      org.extex.scanner.type.tokens.Tokens)
     */
    public void addPattern(Tokens pattern) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.addPattern(pattern);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.word.WordTokenizer#findWord(
     *      org.extex.typesetter.type.NodeList,
     *      int,
     *      org.extex.core.UnicodeCharList)
     */
    public int findWord(NodeList nodes, int start,
            UnicodeCharList word) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        return language.findWord(nodes, start, word);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#getLeftHyphenMin()
     */
    public long getLeftHyphenMin() throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.getLeftHyphenMin();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.ligature.LigatureBuilder#getLigature(
     *      org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar,
     *      org.extex.typesetter.tc.font.Font)
     */
    public UnicodeChar getLigature(UnicodeChar c1, UnicodeChar c2,
            Font f) throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.getLigature(c1, c2, f);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.Language#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#getRightHyphenMin()
     */
    public long getRightHyphenMin() throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.getRightHyphenMin();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#hyphenate(
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.TypesetterOptions,
     *      org.extex.core.UnicodeChar,
     *      int,
     *      boolean,
     *      org.extex.typesetter.type.node.factory.NodeFactory)
     */
    public boolean hyphenate(NodeList nodelist,
            TypesetterOptions context, UnicodeChar hyphen,
            int start, boolean forall, NodeFactory nodeFactory)
            throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.hyphenate(nodelist, context, hyphen, start, forall,
                nodeFactory);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.ligature.LigatureBuilder#insertLigatures(
     *      org.extex.typesetter.type.NodeList, int)
     */
    public int insertLigatures(NodeList list, int start)
            throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.insertLigatures(list, start);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.word.WordTokenizer#insertShy(
     *      org.extex.typesetter.type.NodeList,
     *      int,
     *      boolean[],
     *      org.extex.typesetter.type.node.CharNode)
     */
    public void insertShy(NodeList nodes, int insertionPoint,
            boolean[] spec, CharNode hyphenNode)
            throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.insertShy(nodes, insertionPoint, spec, hyphenNode);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#isHyphenating()
     */
    public boolean isHyphenating() throws HyphenationException {

        if (language == null) {
            language = creator.loadLanguageInstance(name);
        }
        return language.isHyphenating();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.word.WordTokenizer#normalize(
     *      org.extex.core.UnicodeCharList,
     *      org.extex.typesetter.TypesetterOptions)
     */
    public UnicodeCharList normalize(UnicodeCharList word,
            TypesetterOptions options) throws HyphenationException {

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
     * {@inheritDoc}
     *
     * @see org.extex.language.impl.ManagedLanguage#setCreator(
     *      org.extex.language.impl.LanguageCreator)
     */
    public void setCreator(LanguageCreator creator) {

        this.creator = creator;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#setHyphenating(boolean)
     */
    public void setHyphenating(boolean active)
            throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.setHyphenating(active);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#setLeftHyphenMin(long)
     */
    public void setLeftHyphenMin(long left) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.setLeftHyphenMin(left);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.Language#setName(java.lang.String)
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.language.hyphenation.Hyphenator#setRightHyphenMin(long)
     */
    public void setRightHyphenMin(long right) throws HyphenationException {

        if (language == null) {
            language = creator.createLanguageInstance(name);
        }
        language.setRightHyphenMin(right);
    }

}
