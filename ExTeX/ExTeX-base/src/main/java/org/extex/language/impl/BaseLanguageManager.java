/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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
import java.util.HashMap;
import java.util.Map;

import org.extex.framework.AbstractFactory;
import org.extex.framework.Registrar;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.language.ModifiableLanguage;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.language.word.WordTokenizer;

/**
 * This class manages the <code>HyphenationTable</code>s. It is a container
 * which can be asked to provide an appropriate instance. This instance is
 * either taken from existing instances or a new instance is created.
 * 
 * <h2>Configuration</h2>
 * 
 * This instance is configurable. The configuration is used to select the
 * appropriate class and optional parameters for a requested instance. In this
 * respect this class makes best use of the infrastructure of the
 * {@link org.extex.framework.AbstractFactory AbstractFactory}.
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4757 $
 */
public class BaseLanguageManager extends AbstractFactory<ModifiableLanguage>
        implements
            LanguageManager,
            Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2011L;

    /**
     * The field <tt>tables</tt> contains the mapping from index to hyphenation
     * table.
     */
    private Map<String, Language> tables = new HashMap<String, Language>();

    /**
     * Creates a new object.
     * 
     */
    public BaseLanguageManager() {

    }

    /**
     * Create a new language and put it into the table.
     * 
     * @param name the name of the language
     * 
     * @return the new instance of a language
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    protected Language createLanguage(String name)
            throws ConfigurationException {

        ModifiableLanguage lang;
        Configuration cfg = selectConfiguration(name);
        lang = createInstanceForConfiguration(cfg, ModifiableLanguage.class);

        Configuration config = cfg.findConfiguration("LigatureBuilder");
        lang.setLigatureBuilder(createInstanceForConfiguration(config,
            LigatureBuilder.class));

        config = cfg.findConfiguration("WordTokenizer");
        lang.setWordTokenizer(createInstanceForConfiguration(config,
            WordTokenizer.class));

        lang.setName(name);
        tables.put(name, lang);
        return lang;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.language.LanguageManager#getLanguage(java.lang.String)
     */
    @Override
    public Language getLanguage(String name) {

        Language table = tables.get(name);
        if (table == null) {
            table = createLanguage(name);
            tables.put(name, table);
        }
        return table;
    }

    /**
     * Getter for a localizer.
     * 
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(getClass());
    }

    /**
     * Getter for the tables.
     * 
     * @return the tables map
     */
    protected Map<String, Language> getTables() {

        return tables;
    }

    /**
     * Restore the internal state when the instance is loaded from file.
     * 
     * @return the object which should be used instead of the one read
     * 
     * @throws ObjectStreamException in case of an error
     */
    protected Object readResolve() throws ObjectStreamException {

        return Registrar.reconnect(this);
    }

}
