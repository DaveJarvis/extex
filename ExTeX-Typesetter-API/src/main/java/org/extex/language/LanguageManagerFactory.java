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

package org.extex.language;

import org.extex.backend.outputStream.OutputStreamConsumer;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * This class provides a factory for a
 * {@link org.extex.language.LanguageManager LanguageManager}.
 * 
 * 
 * <pre>
 * &lt;Language default="ExTeX"&gt;
 *
 *   &lt;TeX class="org.extex.language.impl.BaseLanguageManager"
 *        default="default"&gt;
 *     &lt;default
 *       class="org.extex.language.hyphenation.liang.LiangsHyphenationTable"&gt;
 *       &lt;LigatureBuilder
 *         class="org.extex.language.ligature.impl.LigatureBuilderImpl"/&gt;
 *       &lt;WordTokenizer
 *         class="org.extex.language.word.impl.TeXWords"/&gt;
 *     &lt;/default&gt;
 *   &lt;/TeX&gt;
 *
 *   &lt;ExTeX class="org.extex.language.impl.LoadingLanguageManager"
 *          default="default"&gt;
 *     &lt;default
 *       class="org.extex.language.hyphenation.liang.LiangsHyphenationTable"&gt;
 *       &lt;LigatureBuilder
 *         class="org.extex.language.ligature.impl.LigatureBuilderImpl"/&gt;
 *       &lt;WordTokenizer
 *         class="org.extex.language.word.impl.ExTeXWords"/&gt;
 *     &lt;/default&gt;
 *   &lt;/ExTeX&gt;
 *
 * &lt;/Language&gt;
 * </pre>
 * 
 * <p>
 * The class to be instantiated can implements one or more interfaces which
 * trigger special actions:
 * </p>
 * <dl>
 * <dt>{@link org.extex.framework.configuration.Configurable Configurable}</dt>
 * <dd> If this interface is implemented then a
 * {@link org.extex.framework.configuration.Configuration Configuration} is
 * passed in with the interface method. </dd>
 * <dt>{@link org.extex.framework.logger.LogEnabled LogEnabled}</dt>
 * <dd> If this interface is implemented then a
 * {@link java.util.logging.Logger Logger} is passed in with the interface
 * method. </dd>
 * <dt>{@link org.extex.resource.ResourceAware ResourceAware}</dt>
 * <dd> If this interface is implemented then a
 * {@link org.extex.resource.ResourceFinder ResourceFinder} is passed in with
 * the interface method. </dd>
 * <dt>{@link org.extex.backend.outputStream.OutputStreamConsumer OutputStreamConsumer}</dt>
 * <dd> If this interface is implemented then a
 * {@link org.extex.backend.outputStream.OutputStreamFactory OutputStreamFactory}
 * is passed in with the interface method. </dd>
 * </dl>
 * 
 * 
 * 
 * @see org.extex.framework.AbstractFactory
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4737 $
 */
public class LanguageManagerFactory extends AbstractFactory {

    /**
     * Get an instance of a
     * {@link org.extex.language.LanguageManager LanguageManager}. This method
     * selects one of the entries in the configuration. The selection is done
     * with the help of a type String. If the type is <code>null</code> or the
     * empty string then the default from the configuration is used.
     * 
     * @param type the type to use
     * @param outFactory the output stream factory to pass in
     * @param finder the resource finder to pass in
     * 
     * @return a new context
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    public LanguageManager newInstance(String type,
            OutputStreamFactory outFactory, ResourceFinder finder)
            throws ConfigurationException {

        LanguageManager manager =
                (LanguageManager) createInstance(type, LanguageManager.class);
        if (manager instanceof OutputStreamConsumer) {
            ((OutputStreamConsumer) manager).setOutputStreamFactory(outFactory);
        }
        if (manager instanceof ResourceAware) {
            ((ResourceAware) manager).setResourceFinder(finder);
        }

        return manager;
    }

}
