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

package org.extex.scanner.stream.observer.reader;

import java.io.Reader;
import java.util.ArrayList;

/**
 * This class provides a type-safe list of observers for the open reader event.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4738 $
 */
public final class OpenReaderObserverList extends ArrayList<OpenReaderObserver>
        implements
            OpenReaderObserver {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
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
    public static OpenReaderObserver register(OpenReaderObserver list,
            OpenReaderObserver observer) {

        if (list instanceof OpenReaderObserverList) {
            ((OpenReaderObserverList) list).add(observer);
        } else if (list == null) {
            OpenReaderObserverList result = new OpenReaderObserverList();
            result.add(observer);
            return result;
        } else {
            OpenReaderObserverList result = new OpenReaderObserverList();
            result.add(list);
            result.add(observer);
            return result;
        }
        return list;
    }

    /**
     * Invoke all observers on the list to inform them of the reader which has
     * been opened.
     * 
     * @param reader the reader to be processed
     * 
     * @see org.extex.scanner.stream.observer.reader.OpenReaderObserver#update(
     *      java.io.Reader)
     */
    public void update(Reader reader) {

        for (OpenReaderObserver obs : this) {
            obs.update(reader);
        }
    }

}
