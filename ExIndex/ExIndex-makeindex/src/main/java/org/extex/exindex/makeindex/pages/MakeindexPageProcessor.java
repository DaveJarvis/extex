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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.exindex.core.type.page.LowerPage;
import org.extex.exindex.core.type.page.LowerRomanPage;
import org.extex.exindex.core.type.page.NumericPage;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.page.SomePage;
import org.extex.exindex.core.type.page.UpperPage;
import org.extex.exindex.core.type.page.UpperRomanPage;
import org.extex.exindex.makeindex.Parameters;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class provides some functions to process pages.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6622 $
 */
public class MakeindexPageProcessor implements PageProcessor {

    /**
     * The field <tt>map</tt> contains the mapping from classes to integers for
     * sorting.
     */
    private Map<Class<? extends PageReference>, Integer> map =
            new HashMap<Class<? extends PageReference>, Integer>();

    /**
     * The field <tt>comparator</tt> contains the comparator.
     */
    private Comparator<? super Pages> comparator = new Comparator<Pages>() {

        /**
         * Compare pages.
         * 
         * @param o1 the first pages
         * @param o2 the second pages
         * 
         * @return the result
         */
        public int compare(Pages o1, Pages o2) {

            int m1 = map.get(o1.getFrom().getClass()).intValue();
            int m2 = map.get(o2.getFrom().getClass()).intValue();

            if (m1 == m2) {
                return 0;
            }

            return m1 < m2 ? -1 : 1;
        }
    };

    /**
     * Creates a new object.
     * 
     * @param params the parameters
     * @param logger the logger
     */
    public MakeindexPageProcessor(Parameters params, Logger logger) {

        String option = params.getString("index:page-precedence");
        int precedence = 0;
        List<Class<? extends PageReference>> classes =
                new ArrayList<Class<? extends PageReference>>();
        classes.add(LowerRomanPage.class);
        classes.add(NumericPage.class);
        classes.add(LowerPage.class);
        classes.add(UpperRomanPage.class);
        classes.add(UpperPage.class);
        classes.add(SomePage.class);

        for (char c : option.toCharArray()) {
            Class<? extends PageReference> cl;
            switch (c) {
                case 'n':
                    cl = NumericPage.class;
                    break;
                case 'r':
                    cl = LowerRomanPage.class;
                    break;
                case 'R':
                    cl = UpperRomanPage.class;
                    break;
                case 'a':
                    cl = LowerPage.class;
                    break;
                case 'A':
                    cl = UpperPage.class;
                    break;
                default:
                    cl = SomePage.class;
            }
            map.put(cl, precedence++);
            classes.remove(cl);
        }

        for (Class<? extends PageReference> cl : classes) {
            map.put(cl, precedence++);
        }
    }

    /**
     * Take a list of pages and join them into a condensed list by considering
     * ranges.
     * 
     * @param pages the pages
     * @param logger the logger
     * 
     * @return the number of warnings written to the logger
     */
    public int join(List<Pages> pages, Logger logger) {

        int warnings = 0;
        PageRangeRange open = null;
        int openIndex = -1;

        for (int i = 0; i < pages.size(); i++) {
            Pages p = pages.get(i);
            if (p instanceof PageRangeOpen) {
                if (open != null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingClose"));
                    warnings++;
                }
                open = new PageRangeRange(p, p.getFrom());
                pages.set(i, open);
                openIndex = i;
            } else if (p instanceof PageRangeClose) {
                if (open == null) {
                    logger.warning(LocalizerFactory.getLocalizer(getClass())
                        .format("MissingOpen"));
                    warnings++;
                } else {
                    if (open.isOne()) {
                        pages.set(openIndex, 
                            new PageRangeRange(open, p.getFrom()));
                    }
                    open = null;
                    pages.remove(i);
                    i--;
                }
            } else if (open != null) {
                String oe = open.getEncap();
                if ((oe == null && p.getEncap() == null) || 
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
     * @param i the index
     * @param pages the list of pages
     * @param p the pages
     * @param other the other page range
     * 
     * @return the next index
     */
    private int joinTwo(List<Pages> pages, int i, Pages p, Pages other) {

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
        int otherFromOrd = otherFrom.getOrd();
        int fromOrd = from.getOrd();
        if (otherFromOrd < 0 || fromOrd < 0) {
            // TODO join identical pages
            return i;
        }

        if (p instanceof PageRangeRange) {
            PageReference to = ((PageRangeRange) p).getTo();
            int toOrd = to.getOrd();
            if (other instanceof PageRangeRange) {
                PageReference otherTo = ((PageRangeRange) other).getTo();
                int otherToOrd = otherTo.getOrd();

                if (otherFromOrd >= fromOrd) {
                    if (otherFromOrd <= toOrd) {
                        ((PageRangeRange) p).setTo(otherTo);
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
                        ((PageRangeRange) p).setTo(otherTo);
                        pages.remove(i);
                        return i - 1;
                    } else if (otherToOrd >= fromOrd) {
                        pages.remove(i);
                        return i - 1;
                    }
                }
            } else if (other instanceof PageRangeSingle) {
                if (otherFromOrd >= fromOrd && otherFromOrd <= toOrd) {
                    pages.remove(i);
                    return i - 1;
                } else if (otherFromOrd == toOrd + 1) {
                    ((PageRangeRange) p).setTo(otherFrom);
                    pages.remove(i);
                    return i - 1;
                }
            }
        } else if (p instanceof PageRangeMultiple) {
            PageReference to = ((PageRangeMultiple) p).getTo();
            int toOrd = to.getOrd();
            if (other instanceof PageRangeSingle) {
                if (otherFromOrd == toOrd || otherFromOrd == fromOrd) {
                    pages.remove(i);
                    return i - 1;
                } else if (otherFromOrd == toOrd + 1) {
                    p = new PageRangeRange(p, otherFrom);
                    pages.set(i - 1, p);
                    pages.remove(i);
                    return i - 1;
                }
            }
        } else if (p instanceof PageRangeSingle) {
            if (other instanceof PageRangeSingle) {
                if (otherFromOrd == fromOrd) {
                    pages.remove(i);
                    return i - 1;
                } else if (otherFromOrd == fromOrd + 1) {
                    p = new PageRangeMultiple(p, otherFrom);
                    pages.set(i - 1, p);
                    pages.remove(i);
                    return i - 1;
                }
            }
        }
        return i;
    }

    /**
     * Sort a list of pages.
     * 
     * @param list the list to be sorted
     */
    public void sort(List<Pages> list) {

        Collections.sort(list, comparator);
    }

}
