/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.node;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;

/**
 * The ligature node represents a ligature of several characters. Ligatures can
 * be build among characters from one common font only. The information where
 * and how to build ligatures comes from the font. The original characters are
 * contained in this node to be restored when required.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [143]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class LigatureNode extends CharNode implements Node {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>chars</tt> contains the cache of the list of plain char
     * nodes contained.
     */
    private CharNode[] chars;

    /**
     * The field <tt>left</tt> contains the first node combined in the
     * ligature.
     */
    private CharNode left;

    /**
     * The field <tt>second</tt> contains the node combined in the ligature.
     */
    private CharNode right;

    /**
     * Creates a new object.
     * 
     * @param context the typesetting context
     * @param uc the Unicode character
     * @param left the first node for the ligature
     * @param right the second node for the ligature
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [144]"
     */
    public LigatureNode(TypesettingContext context, UnicodeChar uc,
            CharNode left, CharNode right) {

        super(context, uc);
        this.left = left;
        this.right = right;
        CharNode[] uc1 = left.getChars();
        CharNode[] uc2 = right.getChars();
        chars = new CharNode[uc1.length + uc2.length];
        for (int i = 0; i < uc1.length; i++) {
            chars[i] = uc1[i];
        }
        for (int i = 0; i < uc2.length; i++) {
            chars[i + uc1.length] = uc2[i];
        }
    }

    /**
     * This method determines the number of characters contained in a node.
     * <ul>
     * <li>A CharNode has a single character</li>
     * <li>A LigatureNde has the number of characters which made up the
     * ligature</li>
     * <li>A NodeList has the number of characters given by the sum of the
     * number of characters of its elements</li>
     * <li>Any other node types has no character</li>
     * </ul>
     * 
     * @return the number of characters contained
     * 
     * @see org.extex.typesetter.type.Node#countChars()
     */
    @Override
    public int countChars() {

        return chars.length;
    }

    /**
     * Getter for the array of characters enclosed in this node.
     * 
     * @return the array of characters
     * 
     * @see org.extex.typesetter.type.Node#getChars()
     */
    @Override
    public CharNode[] getChars() {

        return chars;
    }

    /**
     * Getter for left node.
     * 
     * @return the left node
     */
    public CharNode getLeft() {

        return this.left;
    }

    /**
     * Getter for right node.
     * 
     * @return the right node
     */
    public CharNode getRight() {

        return this.right;
    }

    /**
     * This method returns the printable representation. This is meant to
     * produce a exhaustive form as it is used in tracing output to the log
     * file.
     * 
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * @param breadth the breadth
     * @param depth the depth
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [193]"
     * @see org.extex.typesetter.type.Node#toString( java.lang.StringBuffer,
     *      java.lang.String, int, int)
     */
    @Override
    public void toString(StringBuffer sb, String prefix, int breadth, int depth) {

        Font font = getTypesettingContext().getFont();
        sb.append(getLocalizer().format("String.Format",
            (font == null ? "*" : font.getFontName()),
            getCharacter().toString(), left.toString(), right.toString()));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor, java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object visit(NodeVisitor visitor, Object value)
            throws GeneralException {

        return visitor.visitLigature(this, value);
    }

}
