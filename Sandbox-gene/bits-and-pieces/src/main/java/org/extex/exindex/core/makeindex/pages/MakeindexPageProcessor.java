/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.makeindex.pages;

import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.makeindex.Parameters;
import org.extex.exindex.core.type.page.AbstractPage;
import org.extex.exindex.core.type.page.PageReference;
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
     * The field <tt>delimR</tt> contains the parameter delim_r.
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

        super();
        this.logger = logger;
        rangeOpen = Character.toString(params.getChar("range_open"));
        rangeClose = Character.toString(params.getChar("range_close"));
        delimR = params.getString("delim_r");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.makeindex.pages.PageProcessor#join(java.util.List)
     */
    public void join(List<PageReference> pages) {

        PageReference open = null;
        String openEncap = null;

        for (int i = 0; i < pages.size(); i++) {
            PageReference p = pages.get(i);
            String encap = p.getEncap();
            if (encap == null) {
                encap = "";
            }
            if (encap.startsWith(rangeOpen)) {
                if (open != null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingClose"));
                }
                open = p;
                openEncap = encap.substring(1);
                pages.remove(i);
                i--;
            } else if (encap.startsWith(rangeClose)) {
                if (open == null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingOpen"));
                } else {
                    String enc =
                            (encap.length() == 1 ? null : encap.substring(1));
                    StringBuilder range = new StringBuilder();
                    range.append(open.getPage());
                    range.append(delimR);
                    range.append(p.getPage());
                    pages.set(i, AbstractPage.get(range.toString(), enc));
                    open = null;
                }
            } else if (open != null) {
                if (openEncap.equals(encap)) {
                    pages.remove(i);
                    i--;
                } else {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("Incompatible"));
                }

            }
        }

        if (open != null) {
            logger.warning(LocalizerFactory.getLocalizer(getClass()).format(
                "MissingClose"));
        }
    }

}
