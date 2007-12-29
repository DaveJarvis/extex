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

package org.extex.exindex.core.exception;

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.parser.ResourceLocator;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception signals that two conflicting flags have been encountered.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InconsistentFlagsException extends LException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>locator</tt> contains the resource locator.
     */
    private ResourceLocator locator;

    /**
     * The field <tt>flag1</tt> contains the first flag.
     */
    private String flag1;

    /**
     * The field <tt>flag2</tt> contains the second flag.
     */
    private String flag2;

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     * @param flag1 the first conflicting flag
     * @param flag2 the second conflicting flag
     */
    public InconsistentFlagsException(ResourceLocator locator, String flag1,
            String flag2) {

        super("");
        this.locator = locator;
        this.flag1 = flag1;
        this.flag2 = flag2;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        if (locator != null) {
            return LocalizerFactory.getLocalizer(
                InconsistentFlagsException.class).format("LocatedMessage",
                locator.getResource(), locator.getLineNumber(), flag1, flag2);
        }
        return LocalizerFactory.getLocalizer(InconsistentFlagsException.class)
            .format("Message", flag1, flag2);
    }

}
