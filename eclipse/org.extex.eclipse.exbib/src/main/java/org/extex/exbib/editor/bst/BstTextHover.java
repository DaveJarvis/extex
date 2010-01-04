/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.editor.bst;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.extex.exbib.editor.bst.model.BstModel;
import org.extex.exbib.editor.bst.model.BstModelNode;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public final class BstTextHover implements ITextHover {

    /**
     * The field <tt>model</tt> contains the ...
     */
    private final BstModel model;

    /**
     * Creates a new object.
     * 
     * @param model
     */
    public BstTextHover(BstModel model) {

        this.model = model;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.ITextHover#getHoverInfo(org.eclipse.jface.text.ITextViewer,
     *      org.eclipse.jface.text.IRegion)
     */
    @Override
    public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {

        try {
            String tag = textViewer.getDocument().get(hoverRegion.getOffset(), //
                hoverRegion.getLength());
            if (tag.matches("#-?[0-9]+")) {
                // TODO: info on number format
            }
            BstModelNode node = model.getNode(tag);
            if (node != null) {
                return node.getDescription();
            }
        } catch (BadLocationException e) {
            // fall through
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer,
     *      int)
     */
    @Override
    public IRegion getHoverRegion(ITextViewer textViewer, int offset) {

        IDocument doc = textViewer.getDocument();

        try {
            char c = doc.getChar(offset);
            if ("{}#%'\"".indexOf(c) >= 0 || Character.isWhitespace(c)) {
                return new Region(offset, 0);
            }

            boolean stringFlag = false;

            int i;
            for (i = offset - 1; i >= 0; i--) {
                c = doc.getChar(i);
                if (c == '%') {
                    // drop out of comments immediately
                    return new Region(offset, 0);
                }
                if (c == '"') {
                    // remember the number of string delimiters modulo 2
                    stringFlag = !stringFlag;
                }
                if (c == '\n' || c == '\r') {
                    // we found the beginning of the line
                    break;
                }
            }
            if (stringFlag) {
                // finally we end up in a string
                return new Region(offset, 0);
            }
            for (i = offset - 1; i >= 0; i--) {
                c = doc.getChar(i);
                if ("{}#%'\"".indexOf(c) >= 0 || Character.isWhitespace(c)) {
                    break;
                }
            }
            int start = i + 1;
            int len = doc.getLength();
            for (i = offset + 1; i < len; i++) {
                c = doc.getChar(i);
                if ("{}#%'\"".indexOf(c) >= 0 || Character.isWhitespace(c)) {
                    break;
                }
            }
            return new Region(start, i - start);

        } catch (BadLocationException e) {
            return new Region(offset, 0);
        }
    }
}
