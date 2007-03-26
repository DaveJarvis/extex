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

package org.extex.format.dvi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.extex.font.FontFactory;
import org.extex.font.exception.FontException;
import org.extex.font.format.pl.PlWriter;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.format.dvi.command.DviBOP;
import org.extex.format.dvi.command.DviChar;
import org.extex.format.dvi.command.DviCommand;
import org.extex.format.dvi.command.DviDown;
import org.extex.format.dvi.command.DviEOP;
import org.extex.format.dvi.command.DviExecuteCommand;
import org.extex.format.dvi.command.DviFntDef;
import org.extex.format.dvi.command.DviFntNum;
import org.extex.format.dvi.command.DviNOP;
import org.extex.format.dvi.command.DviPOP;
import org.extex.format.dvi.command.DviPost;
import org.extex.format.dvi.command.DviPostPost;
import org.extex.format.dvi.command.DviPre;
import org.extex.format.dvi.command.DviPush;
import org.extex.format.dvi.command.DviRight;
import org.extex.format.dvi.command.DviRule;
import org.extex.format.dvi.command.DviW;
import org.extex.format.dvi.command.DviX;
import org.extex.format.dvi.command.DviXXX;
import org.extex.format.dvi.command.DviY;
import org.extex.format.dvi.command.DviZ;
import org.extex.format.dvi.exception.DviException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.util.file.random.RandomAccessR;

/**
 * DVI to PL converter.
 *
 * <p>
 * Commands are taken from DVItype 3.4.
 * </p>
 *
 * @see <a href="package-summary.html#DVIformat">DVI-Format</a>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4728 $
 */

public class DviPl implements DviInterpreter, DviExecuteCommand {

    /**
     * page counter
     */
    private int page = 0;

    /**
     * the font factory
     */
    private FontFactory fontfactory;

    /**
     * the map for all sub fonts.
     */
    private Map fontmap;

    /**
     * the dvi stack
     */
    private DviStack stack;

    /**
     * the dvi values;
     */
    private DviValues val;

    /**
     * the weriter for pl
     */
    private PlWriter out;

    /**
     * Create a new object.
     *
     * @param ff        the fontfactroy
     */
    public DviPl(PlWriter plout, FontFactory ff) {

        out = plout;
        fontfactory = ff;
        fontmap = new HashMap();
        val = new DviValues();
        stack = new DviStack();
    }

    /**
     * the mag
     */
    private int mag;

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.DviInterpreter#interpret(
     *      org.extex.util.file.random.RandomAccessR)
     */
    public void interpret(RandomAccessR rar)
            throws IOException,
                DviException,
                FontException,
                ConfigurationException {

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
        }
    }

    /**
     * Set the font map.
     * @param fontm The font map to set.
     */
    public void setFontmap(Map fontm) {

        fontmap = fontm;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviBOP)
     */
    public void execute(DviBOP command)
            throws DviException,
                FontException,
                ConfigurationException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviChar)
     */
    public void execute(DviChar command)
            throws DviException,
                FontException,
                ConfigurationException {

        out.plopen("SETCHAR").addChar((short) command.getCh()).plclose();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviDown)
     */
    public void execute(DviDown command)
            throws DviException,
                FontException,
                ConfigurationException {

        val.addV(command.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviEOP)
     */
    public void execute(DviEOP command)
            throws DviException,
                FontException,
                ConfigurationException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviFntDef)
     */
    public void execute(DviFntDef command)
            throws DviException,
                FontException,
                ConfigurationException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviFntNum)
     */
    public void execute(DviFntNum command)
            throws DviException,
                FontException,
                ConfigurationException {

        val.setF(command.getFont());
        out.plopen("SELECTFONT").addDec(command.getFont()).plclose();

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPOP)
     */
    public void execute(DviPOP command)
            throws DviException,
                FontException,
                ConfigurationException {

        DviValues v = stack.pop();
        val.setValues(v);
        out.plopen("POP").plclose();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviNOP)
     */
    public void execute(DviNOP command)
            throws DviException,
                FontException,
                ConfigurationException {

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPost)
     */
    public void execute(DviPost command)
            throws DviException,
                FontException,
                ConfigurationException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPostPost)
     */
    public void execute(DviPostPost command)
            throws DviException,
                FontException,
                ConfigurationException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPre)
     */
    public void execute(DviPre command)
            throws DviException,
                FontException,
                ConfigurationException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPush)
     */
    public void execute(DviPush command)
            throws DviException,
                FontException,
                ConfigurationException {

        stack.push(val);
        out.plopen("PUSH").plclose();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviRight)
     */
    public void execute(DviRight command)
            throws DviException,
                FontException,
                ConfigurationException {

        val.addH(command.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviRule)
     */
    public void execute(DviRule command)
            throws DviException,
                FontException,
                ConfigurationException {

        if (!command.isPut()) {
            val.addH(command.getWidth());
        }
        TfmFixWord w = new TfmFixWord();
        TfmFixWord h = new TfmFixWord();
        w.setValue(command.getWidth());
        h.setValue(command.getHeight());

        out.plopen("SETRULE").addReal(h).addReal(w).plclose();

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviW)
     */
    public void execute(DviW command)
            throws DviException,
                FontException,
                ConfigurationException {

        // calculate
        if (!command.isW0()) {
            val.setW(command.getValue());
        }
        val.addH(val.getW());

        TfmFixWord fw = new TfmFixWord();
        if (command.isW0()) {
            fw.setValue(val.getW());
        } else {
            fw.setValue(command.getValue());
        }
        out.plopen("MOVERIGHT").addReal(fw).plclose();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviX)
     */
    public void execute(DviX command)
            throws DviException,
                FontException,
                ConfigurationException {

        if (!command.isX0()) {
            val.setX(command.getValue());
        }
        val.addH(val.getX());

        TfmFixWord fw = new TfmFixWord();
        if (command.isX0()) {
            fw.setValue(val.getX());
        } else {
            fw.setValue(command.getValue());
        }
        out.plopen("MOVERIGHT").addReal(fw).plclose();

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviXXX)
     */
    public void execute(DviXXX command)
            throws DviException,
                FontException,
                ConfigurationException {

        out.plopen("SPECIAL").addStr(command.getXXXString()).plclose();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviY)
     */
    public void execute(DviY command)
            throws DviException,
                FontException,
                ConfigurationException {

        if (!command.isY0()) {
            val.setY(command.getValue());
        }
        val.addV(val.getY());

        TfmFixWord fw = new TfmFixWord();
        if (command.isY0()) {
            fw.setValue(val.getY());
        } else {
            fw.setValue(command.getValue());
        }
        out.plopen("MOVEDOWN").addReal(fw).plclose();

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviZ)
     */
    public void execute(DviZ command)
            throws DviException,
                FontException,
                ConfigurationException {

        if (!command.isZ0()) {
            val.setZ(command.getValue());
        }
        val.addV(val.getZ());

        TfmFixWord fw = new TfmFixWord();
        if (command.isZ0()) {
            fw.setValue(val.getZ());
        } else {
            fw.setValue(command.getValue());
        }
        out.plopen("MOVEDOWN").addReal(fw).plclose();
    }
}
