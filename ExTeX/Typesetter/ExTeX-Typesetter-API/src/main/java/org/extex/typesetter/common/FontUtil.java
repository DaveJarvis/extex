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

package org.extex.typesetter.common;

import java.util.logging.Logger;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.tc.font.Font;

/**
 * Font utility methods.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class FontUtil {

    /**
     * Create a new Object
     */
    private FontUtil() {
        // never used
    }

    /**
     * The constant {@code LOCALIZER} contains the localizer for this utility
     * class.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(FontUtil.class);

    /**
     * This method produces a log entry for lost characters if the count
     * register {@code tracinglostchars} is greater than zero.
     *
     * <p>The Count Parameter {@code \tracinglostchars}</p>
     * <p>
     *  The count parameter {@code \tracinglostchars} determines whether
     *  characters which are discarded are logged. Characters are discarded
     *  when the font at hand does not contain a glyph for the character.
     * </p>
     * <p>
     *  If the value of is greater {@code \tracinglostchars} than zero
     *  then a message is written to the log file. Otherwise the message
     *  is suppressed.
     * </p>
     *
     * <p>Examples</p>

     *  <pre class="TeXSample">
     *    \tracinglostchars=1  </pre>
     *
     *
     * @param logger the logger to write to
     * @param context the interpreter context
     * @param font the font queried
     * @param c the character not found
     *
     * @see "TTP [581]"
     */
    public static void charWarning(Logger logger,
            TypesetterOptions context, Font font,
            UnicodeChar c) {

        if (context.getCountOption("tracinglostchars").gt(Count.ZERO)) {
            logger.info(LOCALIZER.format("TTP.MissingChar", c.toString(), font
                .getFontName()));
        }
    }

}
