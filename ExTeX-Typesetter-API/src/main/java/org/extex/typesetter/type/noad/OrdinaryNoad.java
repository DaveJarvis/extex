/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad;

import java.util.logging.Logger;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This Noad represents an ordinary character.
 *
 * @see "TTP [682]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class OrdinaryNoad extends AbstractNucleusNoad implements SimpleNoad {

    /**
     * Creates a new object.
     *
     * @param nucleus the nucleus
     * @param tc the typesetting context for the color
     */
    public OrdinaryNoad(Noad nucleus, TypesettingContext tc) {

        super(nucleus, tc);
        setSpacingClass(MathSpacing.ORD);
    }

    /**
     * Add some information in the middle of the default toString method.
     *
     * @param sb the target string buffer
     * @param depth the recursion depth
     *
     * @see "TTP [696]"
     * @see org.extex.typesetter.type.noad.AbstractNoad#toStringAdd(
     *      java.lang.StringBuffer,
     *      int)
     */
    @Override
    protected void toStringAdd(StringBuffer sb, int depth) {

        sb.append("mathord");
    }

    /**
     * Translate a Noad into a NodeList.
     *
     * @param previousNoad the previous noad
     * @param noads the list of noads currently processed
     * @param index the index of the current node in the list
     * @param list the list to add the nodes to. This list contains the Nodes
     *  previously typeset. Thus it can be used to look back
     * @param mathContext the context to consider
     * @param logger the logger for debugging and tracing information
     *
     * @throws TypesetterException in case of a problem
     * @throws ConfigurationException in case of a configuration problem
     *
     * @see "TTP [752]"
     * @see org.extex.typesetter.type.noad.Noad#typeset(
     *      org.extex.typesetter.type.noad.Noad,
     *      org.extex.typesetter.type.noad.NoadList,
     *      int,
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger)
     */
    public void typeset(Noad previousNoad, NoadList noads,
            int index, NodeList list,
            MathContext mathContext, Logger logger)
            throws TypesetterException,
                ConfigurationException {

        getSpacingClass().addClearance(
            (previousNoad != null ? previousNoad.getSpacingClass() : null),
            list, mathContext);

        Noad n = getNucleus();
        if (n != null) {
            n.typeset(previousNoad, noads, index, list, mathContext, logger);
        }

        FixedDimen delta = Dimen.ZERO_PT; // TODO gene: determine delta
        Node node =
                makeScripts(new HorizontalListNode(), mathContext, delta,
                    logger);
        list.add(node);
    }

    /**
     * Translate an OrdNoad into a NodeList.
     *
     * @param noads the list of noads currently processed
     * @param index the index of the current node in the list
     * @param list the list to add the nodes to. This list contains the Nodes
     *  previously typeset. Thus it can be used to look back
     * @param mathContext the context to consider
     * @param logger the logger for debugging and tracing information
     *
     * @return the index of the next noad to consider
     */
    public static final int make_ord(NoadList noads, int index,
            NodeList list, MathContext mathContext,
            Logger logger) {

        int i = index;
        //    var a: integer;  {address of lig/kern instruction}
        //    p,r: pointer;  {temporary registers for list manipulation}
        // begin restart:
        do {
            Noad noad = noads.get(i);
            if (noad.getSubscript() == null && noad.getSuperscript() == null
                    && noad instanceof CharNoad) {

                // if math_type(subscr(q))=empty then
                //    if math_type(supscr(q))=empty then
                //      if math_type(nucleus(q))=math_char then
                if (++i >= noads.size()) {
                    Noad n = noads.get(i);
                    //        begin p ? link(q);
                    //        if p ? null then
                    if (n instanceof SimpleNoad) {
                        //          if (type(p) ? ord_noad) ? (type(p) ? punct_noad) then
                        Noad nuc = ((AbstractNucleusNoad) n).getNucleus();
                        if (nuc instanceof CharNoad) {
                            //            if math_type(nucleus(p))=math_char then
                            //              if fam(nucleus(p))=fam(nucleus(q)) then
                            //                begin math_type(nucleus(q)) ? math_text_char; fetch(nucleus(q));
                            //                if char_tag(cur_i)=lig_tag then
                            //                  begin a ? lig_kern_start(cur_f)(cur_i); cur_c ? character(nucleus(p)); cur_i ? font_info[a].qqqq;
                            //                  if skip_byte(cur_i)>stop_flag then
                            //                    begin a ? lig_kern_restart(cur_f)(cur_i); cur_i ? font_info[a].qqqq;
                            //                    end ;
                            //                   loop begin ?If instruction cur_i is a kern with cur_c, attach the kern after q; or if it is a ligature with cur_c, combine noads q and p appropriately; then return if the cursor has moved past a noad, or goto restart 753?;
                            //                    if skip_byte(cur_i) ? stop_flag then return ;
                            //                    a ? a+qo(skip_byte(cur_i))+1; cur_i ? font_info[a].qqqq;
                            //                    end ;
                            //                  end ;
                            //                end ;
                            //        end ;
                            // exit: end ;
                        }
                    }
                }
            } else {
                return i;
            }
        } while (true);
    }

}
