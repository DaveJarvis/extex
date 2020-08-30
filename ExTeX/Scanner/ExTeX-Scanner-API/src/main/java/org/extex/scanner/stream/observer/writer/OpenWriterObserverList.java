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

package org.extex.scanner.stream.observer.writer;

import java.io.Writer;
import java.util.ArrayList;

/**
 * This class provides a type-safe list of observers for the open writer event.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class OpenWriterObserverList extends ArrayList<OpenWriterObserver>
        implements
            OpenWriterObserver {

    /**
     * The field {@code serialVersionUID} contains th version number for
     * serialization
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Take a list and add an observer. If the list is {@code null} then
     * a new one is created.
     * 
     * @param list the input list or {@code null}
     * @param observer the observer to add
     * 
     * @return the input list or a new one with the observer added
     */
    public static OpenWriterObserver register(OpenWriterObserver list,
            OpenWriterObserver observer) {

        if (list instanceof OpenWriterObserverList) {
            ((OpenWriterObserverList) list).add(observer);
        } else if (list == null) {
            OpenWriterObserverList result = new OpenWriterObserverList();
            result.add(observer);
            return result;
        } else {
            OpenWriterObserverList result = new OpenWriterObserverList();
            result.add(list);
            result.add(observer);
            return result;
        }
        return list;
    }

    /**
     * Invoke all observers on the list to inform them of the writer which has
     * been opened.
     * 
     * @param writer the writer to be processed
     * 
     * @see org.extex.scanner.stream.observer.reader.OpenReaderObserver#update(
     *      java.io.Reader)
     */
    public void update(Writer writer) {

        for (OpenWriterObserver obs : this) {
            obs.update(writer);
        }
    }

}
