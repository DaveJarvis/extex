/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.makeindex.pages;

import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.makeindex.Parameters;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6622 $
 */
public class MakeindexPageProcessor implements PageProcessor {

    /**
     * The field <tt>rangeOpen</tt> contains the parameter range_open.
     */
    private String rangeOpen;

    /**
     * The field <tt>rangeClose</tt> contains the parameter range_close.
     */
    private String rangeClose;

    /**
     * The field <tt>delimR</tt> contains the range delimiter.
     */
    private String delimR;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * Creates a new object.
     * 
     * @param params the parameters
     * @param logger the logger
     */
    public MakeindexPageProcessor(Parameters params, Logger logger) {

        this.logger = logger;
        rangeOpen = Character.toString(params.getChar("index:range-open"));
        rangeClose = Character.toString(params.getChar("index:range-close"));
        delimR = params.getString("markup:range");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.makeindex.pages.PageProcessor#join(java.util.List)
     */
    public int join(List<PageRange> pages) {

        int warnings = 0;
        PageRange open = null;

        for (int i = 0; i < pages.size(); i++) {
            PageRange p = pages.get(i);
            if (p.isOpen()) {
                if (open != null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingClose"));
                    warnings++;
                }
                open = p;
                p.setDelimiter(delimR);
            } else if (p.isClose()) {
                if (open == null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingOpen"));
                    warnings++;
                } else {
                    open.setTo(p.getTo());
                    open = null;
                    pages.remove(i);
                    i--;
                }
            } else if (open != null) {
                String oe = open.getEncap();
                if ((oe == null && p.getEncap() == null) || //
                        (oe != null && oe.equals(p.getEncap()))) {
                    pages.remove(i);
                    i--;
                }
            } else if (i > 0 && pages.get(i - 1).join(p)) {
                pages.remove(i);
                i--;
            }
        }

        if (open != null) {
            logger.warning(LocalizerFactory.getLocalizer(getClass()).format(
                "MissingClose"));
            warnings++;
        }
        return warnings;
    }
}
