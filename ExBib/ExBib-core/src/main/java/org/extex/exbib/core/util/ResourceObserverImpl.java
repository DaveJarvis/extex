/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.util;

import java.util.logging.Logger;

import org.extex.exbib.core.io.auxio.ResourceObserver;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class implements a logging {@link ResourceObserverImpl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ResourceObserverImpl implements ResourceObserver {

    /**
     * The constant {@code localizer} contains the localizer. It is not
     * static to allow the modification of the default locale at run time.
     */
    private final Localizer localizer;

    /**
     * The field {@code logger} contains the logger.
     */
    private final Logger logger;

    /**
     * The field {@code level} contains the level of inclusion.
     */
    private int level = 0;

    /**
     * Creates a new object.
     * 
     * @param logger the logger
     */
    public ResourceObserverImpl(Logger logger) {

        this.logger = logger;
        this.localizer =
                LocalizerFactory.getLocalizer(ResourceObserverImpl.class);
    }

    /**
*      java.lang.String, java.lang.String, java.lang.String)
     */
    public void observeClose(String resource, String type, String filename) {

        level--;
    }

    /**
*      java.lang.String, java.lang.String, java.lang.String)
     */
    public void observeOpen(String resource, String type, String filename) {

        logger.info(localizer.format(level == 0 ? "message0" : "message",
            filename, Integer.toString(level)));
        level++;
    }

}
