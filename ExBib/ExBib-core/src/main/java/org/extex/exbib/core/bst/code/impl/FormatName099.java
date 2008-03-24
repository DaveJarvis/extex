/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.node.impl.TString;

/**
 * 
 * @author gene
 * @version $Revision: 1.3 $
 */
public class FormatName099 extends FormatName {

    /**
     * Create a new FormatName099 object.
     */
    public FormatName099() {

        super();
        setTie("~");
    }

    /**
     * Create a new FormatName099 object.
     * 
     * @param name
     */
    public FormatName099(String name) {

        super(name);
        setTie("~");
    }

    /**
     * This is the post-processor for the name formatting engine.
     * 
     * @param s the string to process
     * 
     * @return the processed string
     */
    @Override
    protected TString process(String s) {

        int i = s.length() - 1;

        if (i < 0) {
            return new TString(s);
        }

        StringBuffer sb = new StringBuffer(s);

        if (sb.charAt(i) == '~') {
            sb.setCharAt(i, ' ');
        }

        // boolean space = false; // indicator that the previous char was a
        // space
        // for (i = 0; i < sb.length(); i++) {
        // switch (sb.charAt(i)) {
        // case '\t':
        // sb.setCharAt(i, ' ');
        // // continue as if a space has been seen. NO BREAK!
        // case ' ':
        // if (space) {
        // sb.deleteCharAt(i--);
        // } else {
        // space = true;
        // }
        // break;
        // case '~':
        // if (space) {
        // sb.deleteCharAt(--i);
        // } else {
        // space = true;
        // }
        // break;
        // default:
        // space = false;
        // }
        // }
        // i = 0;
        // while ((i = sb.indexOf(" ", i)) >= 0) {
        // sb.deleteCharAt(i);
        // }
        // i = 0;
        // while ((i = sb.indexOf("~~", i)) >= 0) {
        // sb.deleteCharAt(i);
        // }
        // i = 0;
        // while ((i = sb.indexOf("~ ", i)) >= 0) {
        // sb.deleteCharAt(i);
        // }
        // i = 0;
        // while ((i = sb.indexOf(" ~", i)) >= 0) {
        // sb.deleteCharAt(i + 1);
        // }
        return new TString(sb.toString());
    }
}
