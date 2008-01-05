/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command;

import java.lang.reflect.InvocationTargetException;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.lisp.exception.LSettingConstantException;

/**
 * This class is an indexer for testing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6688 $
 */
public class TestableIndexer extends Indexer {

    /**
     * Creates a new object.
     * 
     * @throws NoSuchMethodException in case of an error
     * @throws LSettingConstantException in case of an error
     * @throws SecurityException in case of an error
     * @throws InvocationTargetException in case of an error
     * @throws IllegalAccessException in case of an error
     * @throws InstantiationException in case of an error
     * @throws IllegalArgumentException in case of an error
     */
    public TestableIndexer()
            throws SecurityException,
                LSettingConstantException,
                NoSuchMethodException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        super();
    }

}
