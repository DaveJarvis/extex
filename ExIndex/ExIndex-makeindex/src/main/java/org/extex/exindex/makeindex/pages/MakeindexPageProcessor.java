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

import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.makeindex.Parameters;
import org.extex.exindex.makeindex.pages.Pages.Type;
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
    public int join(List<Pages> pages) {

        int warnings = 0;
        Pages open = null;

        for (int i = 0; i < pages.size(); i++) {
            Pages p = pages.get(i);
            Type type = p.getType();
            if (type == Pages.Type.OPEN) {
                if (open != null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingClose"));
                    warnings++;
                }
                open = p;
                p.setType(Pages.Type.RANGE);
            } else if (type == Pages.Type.CLOSE) {
                if (open == null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingOpen"));
                    warnings++;
                } else {
                    open.setTo(p.getTo());
                    if (open.isOne()) {
                        open.setType(Pages.Type.SINGLE);
                    } else {
                        open.setType(Pages.Type.RANGE);
                    }
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
            } else if (i > 0) {
                i = joinTwo(pages, i, pages.get(i - 1), p);
            }
        }

        if (open != null) {
            logger.warning(LocalizerFactory.getLocalizer(getClass()).format(
                "MissingClose"));
            warnings++;
        }
        return warnings;
    }

    /**
     * Try to join with another page range.
     * 
     * @param i
     * @param pages
     * 
     * @param other the other page range
     * 
     * @return <code>true</code> iff the joining succeeded
     */
    public int joinTwo(List<Pages> pages, int i, Pages p, Pages other) {

        String encap = p.getEncap();
        String otherEncap = other.getEncap();

        if (encap == null) {
            if (otherEncap != null) {
                return i;
            }
        } else if (!encap.equals(otherEncap)) {
            return i;
        }
        PageReference from = p.getFrom();
        PageReference otherFrom = other.getFrom();
        if (otherFrom.getClass() != from.getClass()) {
            return i;
        }
        PageReference to = p.getTo();
        PageReference otherTo = other.getTo();
        int otherFromOrd = otherFrom.getOrd();
        int otherToOrd = otherTo.getOrd();
        int fromOrd = from.getOrd();
        int toOrd = to.getOrd();
        if (otherFromOrd < 0 || otherToOrd < 0 || fromOrd < 0 || toOrd <= 0) {
            // TODO join identical pages
            return i;
        }

        Type type = p.getType();
        Type otherType = other.getType();

        if (type.equals(Type.RANGE) && otherType.equals(Type.RANGE)) {

            if (otherFromOrd >= fromOrd) {
                if (otherFromOrd <= toOrd) {
                    p.setTo(otherTo);
                    pages.remove(i);
                    return i - 1;
                } else if (otherToOrd <= toOrd) {
                    pages.remove(i);
                    return i - 1;
                }
            }
            if (otherFromOrd <= fromOrd) {
                if (otherToOrd >= toOrd) {
                    p.setFrom(otherFrom);
                    p.setTo(otherTo);
                    pages.remove(i);
                    return i - 1;
                } else if (otherToOrd >= fromOrd) {
                    from = otherFrom;
                    pages.remove(i);
                    return i - 1;
                }
            }
        } else if (type.equals(Type.RANGE) && otherType.equals(Type.SINGLE)) {
            if (otherFromOrd >= fromOrd && otherFromOrd <= toOrd) {
                pages.remove(i);
                return i - 1;
            } else if (otherFromOrd == toOrd + 1) {
                p.setTo(otherTo);
                pages.remove(i);
                return i - 1;
            }
        } else if (type.equals(Type.MULTIPLE) && otherType.equals(Type.SINGLE)) {
            if (otherFromOrd == toOrd || otherFromOrd == fromOrd) {
                pages.remove(i);
                return i - 1;
            } else if (otherFromOrd == toOrd + 1) {
                p.setTo(otherFrom);
                p.setType(Type.RANGE);
                pages.remove(i);
                return i - 1;
            }
        } else if (type.equals(Type.SINGLE) && otherType.equals(Type.SINGLE)) {
            if (otherFromOrd == fromOrd) {
                pages.remove(i);
                return i - 1;
            } else if (otherFromOrd == fromOrd + 1) {
                p.setType(Type.MULTIPLE);
                p.setTo(otherFrom);
                pages.remove(i);
                return i - 1;
            }
        }
        return i;
    }

}
