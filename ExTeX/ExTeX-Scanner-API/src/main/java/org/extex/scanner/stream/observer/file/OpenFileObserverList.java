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

package org.extex.scanner.stream.observer.file;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class provides a type-safe list of observers for the open file event.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4738 $
 */
public final class OpenFileObserverList extends ArrayList<OpenFileObserver>
        implements
            OpenFileObserver {

    /**
     * The field <tt>serialVersionUID</tt> contains th version number for
     * serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * Take a list and add an observer. If the list is <code>null</code> then
     * a new one is created.
     * 
     * @param list the input list or <code>null</code>
     * @param observer the observer to add
     * 
     * @return the input list or a new one with the observer added
     */
    public static OpenFileObserver register(OpenFileObserver list,
            OpenFileObserver observer) {

        if (list instanceof OpenFileObserverList) {
            ((OpenFileObserverList) list).add(observer);
        } else if (list == null) {
            OpenFileObserverList result = new OpenFileObserverList();
            result.add(observer);
            return result;
        } else {
            OpenFileObserverList result = new OpenFileObserverList();
            result.add(list);
            result.add(observer);
            return result;
        }
        return list;
    }

    /**
     * Invoke all observers on the list to inform them of the file which has
     * been opened.
     * 
     * @param filename the name of the file to be opened
     * @param filetype the type of the file to be opened. The type is resolved
     *        via the configuration to a file name pattern
     * @param stream the input stream to read from
     * 
     * @see org.extex.scanner.stream.observer.file.OpenFileObserver#update(
     *      java.lang.String, java.lang.String, java.io.InputStream)
     */
    public void update(String filename, String filetype, InputStream stream) {

        for (OpenFileObserver obs : this) {
            obs.update(filename, filetype, stream);
        }
    }

}
