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

package org.extex.language.hyphenation.liang;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class implements Liang's algorithm for hyphenation with a compressed
 * hyphenation tree. Th hyphenation tree is compressed when the object is
 * serialized. Afterwards no more patterns can be added.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CompressedLiangsHyphenationTable extends LiangsHyphenationTable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * This compression method traverses the hyphenation tree and makes
     * provisions to use common instances for equal hyphenation codes.
     *
     * @param tree the tree to traverse
     * @param map the map of common codes
     */
    private void compress(HyphenTree tree, Map<char[], char[]> map) {

        char[] hc = tree.getHyphenationCode();
        char[] code = map.get(hc);
        if (code == null) {
            map.put(hc, hc);
        } else {
            tree.setCode(code);
        }

        Iterator<HyphenTree> iter = tree.iterator();
        while (iter.hasNext()) {
            compress(iter.next(), map);
        }

    }

    /**
     * This method compresses the table upon dumping. It is one of the magic
     * methods invoked during serialization.
     *
     * @param out the output stream
     *
     * @throws IOException in case of an IO error
     */
    private void writeObject(ObjectOutputStream out) throws IOException {

        if (!isCompressed()) {
            compress(getPatterns(), new HashMap<char[], char[]>());
            setCompressed();
        }
        out.defaultWriteObject();
    }

}
