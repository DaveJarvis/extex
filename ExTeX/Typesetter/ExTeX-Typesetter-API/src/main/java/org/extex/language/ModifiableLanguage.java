/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.language.ligature.LigatureBuilder;
import org.extex.language.word.WordTokenizer;

/**
 * This interface extends the interface
 * {@link org.extex.language.Language Language} with the ability to receive
 * a {@link org.extex.language.ligature.LigatureBuilder LigatureBuilder}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface ModifiableLanguage extends Language {

    /**
     * Setter for the ligature builder.
     *
     * @param ligatureBuilder the ligature builder
     */
    void setLigatureBuilder(LigatureBuilder ligatureBuilder);

    /**
     * Setter for the word tokenizer.
     *
     * @param tokenizer the word tokenizer
     */
    void setWordTokenizer(WordTokenizer tokenizer);

}