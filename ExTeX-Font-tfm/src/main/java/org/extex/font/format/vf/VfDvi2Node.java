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

package org.extex.font.format.vf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.CoreFontFactory;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.font.exception.FontException;
import org.extex.font.format.dvi.DviInterpreter;
import org.extex.font.format.dvi.DviStack;
import org.extex.font.format.dvi.DviValues;
import org.extex.font.format.dvi.command.DviBOP;
import org.extex.font.format.dvi.command.DviChar;
import org.extex.font.format.dvi.command.DviCommand;
import org.extex.font.format.dvi.command.DviDown;
import org.extex.font.format.dvi.command.DviEOP;
import org.extex.font.format.dvi.command.DviExecuteCommand;
import org.extex.font.format.dvi.command.DviFntDef;
import org.extex.font.format.dvi.command.DviFntNum;
import org.extex.font.format.dvi.command.DviNOP;
import org.extex.font.format.dvi.command.DviPOP;
import org.extex.font.format.dvi.command.DviPost;
import org.extex.font.format.dvi.command.DviPostPost;
import org.extex.font.format.dvi.command.DviPre;
import org.extex.font.format.dvi.command.DviPush;
import org.extex.font.format.dvi.command.DviRight;
import org.extex.font.format.dvi.command.DviRule;
import org.extex.font.format.dvi.command.DviW;
import org.extex.font.format.dvi.command.DviX;
import org.extex.font.format.dvi.command.DviXXX;
import org.extex.font.format.dvi.command.DviY;
import org.extex.font.format.dvi.command.DviZ;
import org.extex.font.format.tfm.LoadableTfmFont;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.format.vf.command.VfCommandFontDef;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.FontImpl;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.SpecialNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.file.random.RandomAccessR;

/**
 * Convert Vf-Dvi to nodes.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class VfDvi2Node implements DviInterpreter, DviExecuteCommand {

    /**
     * The map for the extex fonts.
     */
    private Map<Integer, ExtexFont> extexfonts =
            new HashMap<Integer, ExtexFont>();

    /**
     * The node factory.
     */
    private NodeFactory factory;

    /**
     * The font factory.
     */
    private CoreFontFactory fontfactory;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer =
            LocalizerFactory.getLocalizer(VfDvi2Node.class);

    /**
     * The dvi stack.
     */
    private DviStack stack = new DviStack();

    /**
     * The typesetting context.
     */
    private TypesettingContext tc;

    /**
     * The typesetting context factory.
     */
    private TypesettingContextFactory tcFactory;

    /**
     * The dvi values.
     */
    private DviValues val = new DviValues();

    /**
     * The virtual char node.
     */
    private VirtualCharNode vCharNode;

    /**
     * The virtual font.
     */
    private VfFont vfFont;

    /**
     * The current x point.
     */
    private Dimen x = new Dimen();

    /**
     * Creates a new object.
     * 
     * @param uc The Unicode char.
     * @param tc The typesetting context.
     * @param factory The node factory.
     * @param tcFactory The typesetting context factory.
     * @param fontfactory The font factory.
     * @param vfFont The virtual font.
     * @param charpos The char pos in the font.
     */
    public VfDvi2Node(UnicodeChar uc, TypesettingContext tc,
            NodeFactory factory, TypesettingContextFactory tcFactory,
            CoreFontFactory fontfactory, int charpos, VfFont vfFont) {

        // this.uc = uc;
        this.tc = tc;
        this.factory = factory;
        this.tcFactory = tcFactory;
        this.fontfactory = fontfactory;
        // this.charpos = charpos;
        this.vfFont = vfFont;
        vCharNode = factory.getVirtualCharNode(tc, uc);

        val.clear();
        stack.clear();

    }

    /**
     * Check, if the font is changed.
     * 
     * If it changed, it create a new typesetting context with the new font.
     * 
     * @return Returns the actual extex font.
     */
    private ExtexFont checkFont() {

        ExtexFont ff = extexfonts.get(val.getF());
        FontKey ffkey = ff.getActualFontKey();
        Font tcf = tc.getFont();
        FontKey akey = tcf.getActualFontKey();

        if (!ffkey.eq(akey)) {
            tc = tcFactory.newInstance(tc, new FontImpl(ff));
        }
        return ff;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviBOP)
     */
    public void execute(DviBOP command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviChar)
     */
    public void execute(DviChar command) throws FontException {

        ExtexFont f = checkFont();
        UnicodeChar nuc = UnicodeChar.get(command.getCh());

        if (f instanceof LoadableTfmFont) {
            // a tfm font maps the position to unicode
            LoadableTfmFont tfmfont = (LoadableTfmFont) f;
            UnicodeChar tmpuc = tfmfont.getUnicodeChar(command.getCh());
            if (tmpuc != null) {
                nuc = tmpuc;
            }
        }
        FixedGlue width = f.getWidth(nuc);
        Node node = factory.getNode(tc, nuc);

        addNode(node);

        if (!command.isPut()) {
            val.addH((int) TfmFixWord.toValue(vfFont.getDesignSize(), width
                .getLength()));
        }

    }

    /**
     * Add the node to the virtual char node.
     * 
     * If (x,y) differed to (h,l), then a horizontal box is used.
     * 
     * @param node The node.
     */
    private void addNode(Node node) {

        if (isEquals()) {
            vCharNode.add(node);
            x.add(node.getWidth());
        } else {
            HorizontalListNode hbox = new HorizontalListNode();
            hbox.add(node);
            hbox.setMove(getXDiff());
            hbox.setShift(getYDiff());
            x.add(hbox.getWidth());
            vCharNode.add(hbox);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviDown)
     */
    public void execute(DviDown command) throws FontException {

        val.addV(command.getValue());

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviEOP)
     */
    public void execute(DviEOP command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviFntDef)
     */
    public void execute(DviFntDef command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviFntNum)
     */
    public void execute(DviFntNum command) throws FontException {

        val.setF(command.getFont());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviNOP)
     */
    public void execute(DviNOP command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPOP)
     */
    public void execute(DviPOP command) throws FontException {

        DviValues newval = stack.pop();
        val.setValues(newval);

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPost)
     */
    public void execute(DviPost command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPostPost)
     */
    public void execute(DviPostPost command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPre)
     */
    public void execute(DviPre command) throws FontException {

        // ignore

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPush)
     */
    public void execute(DviPush command) throws FontException {

        stack.push(val);

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviRight)
     */
    public void execute(DviRight command) throws FontException {

        val.addH(command.getValue());

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviRule)
     */
    public void execute(DviRule command) throws FontException {

        RuleNode node = new RuleNode(//
            // width
            TfmFixWord.toDimen(vfFont.getDesignSize(), command.getWidth()),
            // height
            TfmFixWord.toDimen(vfFont.getDesignSize(), command.getHeight()),
            // depth
            Dimen.ZERO_PT, tc, true);

        addNode(node);

        if (!command.isPut()) {
            val.addH(command.getWidth());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviW)
     */
    public void execute(DviW command) throws FontException {

        if (!command.isW0()) {
            val.setW(command.getValue());
        }
        val.addH(val.getW());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviX)
     */
    public void execute(DviX command) throws FontException {

        if (!command.isX0()) {
            val.setX(command.getValue());
        }
        val.addH(val.getX());

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviXXX)
     */
    public void execute(DviXXX command) throws FontException {

        SpecialNode node = new SpecialNode(command.getXXXString());
        addNode(node);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviY)
     */
    public void execute(DviY command) throws FontException {

        if (!command.isY0()) {
            val.setY(command.getValue());
        }
        val.addV(val.getY());

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviZ)
     */
    public void execute(DviZ command) throws FontException {

        if (!command.isZ0()) {
            val.setZ(command.getValue());
        }
        val.addV(val.getZ());

    }

    /**
     * Getter for the virtual char node.
     * 
     * @return the virtual char node.
     */
    public VirtualCharNode getVcharNode() {

        return vCharNode;
    }

    /**
     * Returns the x diff to h.
     * 
     * @return Returns the x diff to h.
     */
    private Dimen getXDiff() {

        Dimen hd = TfmFixWord.toDimen(vfFont.getDesignSize(), val.getH());
        hd.subtract(x);
        return hd;
    }

    /**
     * Returns the y diff to v.
     * 
     * y is always zero (baseline)!
     * 
     * @return Returns the y diff to v.
     */
    private Dimen getYDiff() {

        Dimen vd = TfmFixWord.toDimen(vfFont.getDesignSize(), val.getV());
        return vd;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.DviInterpreter#interpret(org.extex.util.file.random.RandomAccessR)
     */
    public void interpret(RandomAccessR rar) throws IOException, FontException {

        if (vCharNode == null) {
            throw new FontException(localizer.format("DVI.noVCharNode"));
        }

        loadFonts();

        while (!rar.isEOF()) {
            DviCommand command = DviCommand.getNextCommand(rar);

            if (command instanceof DviChar) {
                DviChar cc = (DviChar) command;
                execute(cc);
                continue;
            } else if (command instanceof DviRight) {
                DviRight cc = (DviRight) command;
                execute(cc);
                continue;
            } else if (command instanceof DviDown) {
                DviDown cc = (DviDown) command;
                execute(cc);
                continue;
            } else if (command instanceof DviW) {
                DviW cc = (DviW) command;
                execute(cc);
                continue;
            } else if (command instanceof DviX) {
                DviX cc = (DviX) command;
                execute(cc);
                continue;
            } else if (command instanceof DviY) {
                DviY cc = (DviY) command;
                execute(cc);
                continue;
            } else if (command instanceof DviZ) {
                DviZ cc = (DviZ) command;
                execute(cc);
                continue;
            } else if (command instanceof DviBOP) {
                DviBOP cc = (DviBOP) command;
                execute(cc);
                continue;
            } else if (command instanceof DviEOP) {
                DviEOP cc = (DviEOP) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPOP) {
                DviPOP cc = (DviPOP) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPush) {
                DviPush cc = (DviPush) command;
                execute(cc);
                continue;
            } else if (command instanceof DviRule) {
                DviRule cc = (DviRule) command;
                execute(cc);
                continue;
            } else if (command instanceof DviXXX) {
                DviXXX cc = (DviXXX) command;
                execute(cc);
                continue;
            } else if (command instanceof DviFntDef) {
                DviFntDef cc = (DviFntDef) command;
                execute(cc);
                continue;
            } else if (command instanceof DviFntNum) {
                DviFntNum cc = (DviFntNum) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPost) {
                DviPost cc = (DviPost) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPostPost) {
                DviPostPost cc = (DviPostPost) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPre) {
                DviPre cc = (DviPre) command;
                execute(cc);
                continue;
            }
            throw new FontException(localizer.format("DVI.forgetOpcode", String
                .valueOf(command.getOpcode())));
        }

    }

    /**
     * Check, if (x,y) is equals to (h,v) .
     * 
     * y is always zero (baseline)!
     * 
     * @return Returns <code>true</code>, if (x,y) is equals to (h,v).
     */
    private boolean isEquals() {

        Dimen hd = TfmFixWord.toDimen(vfFont.getDesignSize(), val.getH());
        Dimen vd = TfmFixWord.toDimen(vfFont.getDesignSize(), val.getV());

        if (x.eq(hd) && vd.isZero()) {
            return true;
        }

        return false;
    }

    /**
     * Load the {@link ExtexFont}s.
     * 
     * @throws FontException if a font error occurred.
     */
    private void loadFonts() throws FontException {

        Map<Integer, VfCommandFontDef> fonts = vfFont.getFonts();

        Iterator<Integer> it = fonts.keySet().iterator();
        while (it.hasNext()) {
            Integer number = it.next();
            VfCommandFontDef fcmd = fonts.get(number);
            FontKey key;
            if (fcmd.getScalefactorAsCount().getValue() != Dimen.ONE) {
                HashMap<String, FixedCount> m =
                        new HashMap<String, FixedCount>();
                m.put(FontKey.SCALE, new Count(fcmd.getScalefactorAsCount()
                    .getValue()
                        * 1000 / Dimen.ONE));
                key =
                        fontfactory.getFontKey(fcmd.getFontname(), fcmd
                            .getDesignsizeAsDimen(), m);
            } else {
                key =
                        fontfactory.getFontKey(fcmd.getFontname(), fcmd
                            .getDesignsizeAsDimen());
            }

            ExtexFont f = fontfactory.getInstance(key);
            extexfonts.put(number, f);
        }

    }
}
