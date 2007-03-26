/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.font;

import org.extex.core.UnicodeChar;
import org.extex.font.type.ModifiableFount;
import org.extex.font.type.VirtualFount;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.typesetter.type.node.VirtualCharNode;


/**
 * Implementation for a virtual font.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4728 $
 */
public class VirtualFontImpl implements VirtualFount {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
     */
    private static final long serialVersionUID = 2006L;

    /**
     * Create a new Object
     * @param afount  the fount
     */
    public VirtualFontImpl(ModifiableFount afount) {

        //super(afount);
    }

    /**
     * @see org.extex.font.type.VirtualFount#getVirtualCharNode(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.core.UnicodeChar)
     */
    public VirtualCharNode getVirtualCharNode(TypesettingContext context,
            UnicodeChar uc) {

//        VirtualCharNode cnode = new VirtualCharNode(context, uc);
//
//        InternalFount fount = getFount();
//        Glyph vglyph = fount.getGlyph(uc);
//
//        // h and v position
//        Dimen h = new Dimen(0);
//        Dimen v = new Dimen(0);
//
        // get all commands
        //        if (fount instanceof EFMFount) {
        //            EFMFount efm = (EFMFount) fount;
        //            ArrayList commands = efm.getCommands(uc);
        //            if (commands != null) {
        //                for (int i = 0; i < commands.size(); i++) {
        //                    Object o = commands.get(i);
        //                    if (o instanceof EfmChar) {
        //                        EfmChar c = (EfmChar) o;
        //                        String fontname = c.getFont();
        //                        Dimen fontsize = c.getFontsize();
        //                        BaseFont nf = null;
        //                        try {
        //                            nf = efm.getFontfactory().getInstance(fontname,
        //                                    fontsize);
        //                        } catch (Exception e) {
        //                            // eigentlich unmöglich, da BaseFont schon
        //                            // geladen worden ist
        //                            e.printStackTrace();
        //                            // TODO: handle exception
        //                        }
        //                        if (nf != null) {
        //                            ModifiableTypesettingContext newcontext = new TypesettingContextImpl(
        //                                    context);
        //                            newcontext.setFont(nf);
        //                            CharNode cn = new CharNode(newcontext, uc);
        //
        //                            calculateMoveShift(v, h, c, cn, cnode);
        //                        }
        //                    } else if (o instanceof EfmRule) {
        //                        EfmRule r = (EfmRule) o;
        //
        //                        RuleNode rn = new RuleNode(r.getWidth(), r.getHeight(),
        //                                Dimen.ZERO_PT, context);
        //
        //                        calculateMoveShift(v, h, r, rn, cnode);
        //
        //                    } else if (o instanceof EfmSpecial) {
        //                        EfmSpecial sp = (EfmSpecial) o;
        //                        SpecialNode sn = new SpecialNode(sp.getText());
        //                        cnode.add(sn);
        //                    }
        //                }
        //            }
        //}

//        // set the dimension from the glyph to the nodelist
//        cnode.setHeight(vglyph.getHeight());
//        cnode.setDepth(vglyph.getDepth());
//        cnode.setWidth(vglyph.getWidth());
// mgn: umbauen
        return null;// cnode;
    }

//    /**
//     * Calculate move / shift
//     * @param v     the v position
//     * @param h     the h position
//     * @param hvw   the EfmHVW  (EfmChar, EfmRule )
//     * @param node  the Node (CharNode, RuleNode )
//     * @param cnode the VirtualCharNode
//     */
//    private void calculateMoveShift(Dimen v, Dimen h,
//            EfmHVW hvw, AbstractNode node,
//            VirtualCharNode cnode) {
//
//        // calculate move and shift
//        // v goes down -> shift goes up
//        Dimen nodeh = hvw.getH();
//        Dimen nodev = hvw.getV();
//        nodev.negate();
//
//        // move = nodeh-h
//        Dimen move = new Dimen(nodeh);
//        move.subtract(h);
//
//        // shift = nodev-v
//        Dimen shift = new Dimen(nodev);
//        shift.subtract(v);
//
//        // set move / shift
//        //TODO MGN: node.setMove(move);
//        //TODO MGN: node.setShift(shift);
//        cnode.add(node);
//
//        // calculate new refpoint
//        // h = move + width
//        h.set(move);
//        h.add(node.getWidth());
//        // v = 0
//        v.set(0);
//    }
}