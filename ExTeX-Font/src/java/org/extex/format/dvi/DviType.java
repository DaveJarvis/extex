/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.font.FontFactory;
import org.extex.font.exception.FontException;
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
import org.extex.format.dvi.exception.DviFontNotFoundException;
import org.extex.format.dvi.exception.DviGlyphNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.type.font.Font;
import org.extex.util.file.random.RandomAccessR;

/**
 * DviType.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4728 $
 */

public class DviType implements DviInterpreter, DviExecuteCommand {

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
     * the writer
     */
    private PrintWriter w;

    /**
     * the magnification
     */
    private int mag;

    /**
     * dpi (default)
     */
    private static final double DEFAULTDPI = 300.0d;

    /**
     * resolution in dpi
     */
    private double resolution = DEFAULTDPI;

    /**
     * Create a new object.
     *
     * @param wout      the output writer
     * @param ff        the font factory
     */
    public DviType(PrintWriter wout, FontFactory ff) {

        w = wout;
        fontfactory = ff;
        fontmap = new HashMap();
        val = new DviValues();
        stack = new DviStack();
    }

    /**
     * Load the font.
     *
     * @throws DviException if a font not found.
     * @throws FontException if an font-error occurred.
     * @throws ConfigurationException from the config-system.
     */
    private void loadFont() throws DviException, FontException,
            ConfigurationException {

        for (int i = 0; i < fntdefs.size(); i++) {
            DviFntDef command = (DviFntDef) fntdefs.get(i);

            Integer key = new Integer(command.getFont());
            Dimen designsize = command.getScaleAsDimen();
            Count scale = command.getScaledAsCount(mag);
            String name = command.getFName();

            //            BaseFont f = fontfactory.getInstance(name, design size, scale, new Glue(
            //                    0), true, true);
            // mgn: umbauen
            Font f = null;//fontfactory.getInstance(new FontKey(name, designsize, 
            //scale, new Glue(0), true, true));
            if (f == null) {
                throw new DviFontNotFoundException(name);
            }
            fontmap.put(key, f);
        }
    }

    /**
     * the post command
     */
    private DviPost post;

    /**
     * the pre command
     */
    private DviPre pre;

    /**
     * the fnt_def commands
     */
    private List fntdefs;

    /**
     * format to 8 digit
     */
    private static final int FORM8 = 8;

    /**
     * NUM
     */
    private static final double NUM = 254000.0d;

    /**
     * The conversion factor 'conv' is figured as follows:
     * There are exactly n/d decimicrons per DVI unit,
     * and 254000 decimicrons per inch, and resolution pixels per inch.
     * Then we have to adjust this by the stated amount of magnification.
     */
    private double conv;

    /**
     * @see org.extex.format.dvi.DviInterpreter#interpret(
     *      org.extex.util.file.random.RandomAccessR)
     */
    public void interpret(RandomAccessR rar) throws IOException,
            DviException, FontException, ConfigurationException {

        // read post
        post = DviCommand.getPost(rar);

        // calculate conv
        conv = (post.getNum() / NUM) * (resolution / post.getDen());

        // read the fnt_def commands
        fntdefs = DviCommand.getFntDefs(rar);

        // read pre
        pre = DviCommand.getPre(rar);

        printHeader();
        printFont();
        loadFont();
        w.println();

        // reset pointer
        rar.seek(0);

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
            System.out.println("mist, einen vergessen opcode:"
                    + command.getOpcode());

        }
    }

    /**
     * round the pixel
     * @param v the value
     * @return Returns the rounded value
     */
    private int pixelround(int v) {

        return (int) Math.round(v * conv);
    }

    /**
     * It computes the number of pixels in the height or width of a rule.
     * @param v the value
     * @return Returns the number of pixels.
     */
    private int rulepixels(int v) {

        int n = (int) (conv * v);
        if (n < conv * v) {
            return n + 1;
        }
        return n;
    }

    /**
     * mag (fefault)
     */
    private static final int MAGDEFAULT = 1000;

    /**
     * mag div
     */
    private static final int MAGDIV = 10;

    /**
     * print the font
     */
    private void printFont() {

        for (int i = 0; i < fntdefs.size(); i++) {
            DviFntDef f = (DviFntDef) fntdefs.get(i);

            w.print("BaseFont ");
            w.print(f.getFont());
            w.print(": ");
            w.print(f.getFName());
            if (f.getScaled(post.getMag()) != MAGDEFAULT) {
                w.print(" scaled ");
                w.print(f.getScaled(post.getMag()));
            }
            w.print("---loaded at size ");
            w.print(f.getScale());
            w.println(" DVI units");
            if (f.getScaled(post.getMag()) != MAGDEFAULT) {
                w.print(" (this font is magnified ");
                w.print(f.getScaled(post.getMag()) / MAGDIV);
                w.println("%)");
            }
        }
    }

    /**
     * print the header
     */
    private void printHeader() {

        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(FORM8);
        w.println("This is DVItype, Version ExTeX");
        w.print("Resolution = ");
        w.print(nf.format(resolution));
        w.println(" pixels per inch");
        w.print("numerator/denominator=");
        w.print(post.getNum());
        w.print("/");
        w.print(post.getDen());
        w.println();
        w.print("magnification=");
        w.print(post.getMag());
        w.print(";       ");
        w
                .print(nf.format((post.getNum() / NUM)
                        * (resolution / post.getDen())));
        w.println(" pixels per DVI unit");
        w.print("'");
        w.print(pre.getComment());
        w.println("'");
        w.print("Postamble starts at byte ");
        w.print(post.getStartPointer());
        w.println(".");
        w.print("maxv=");
        w.print(post.getHeigthdepth());
        w.print(", maxh=");
        w.print(post.getWidth());
        w.print(", maxstackdepth=");
        w.print(post.getStackdepth());
        w.print(", totalpages=");
        w.println(post.getTotalpage());
    }

    /**
     * Print the values.
     * @param level the level
     */
    private void printValues(int level) {

        w.print("level ");
        w.print(level);
        w.print(":(h=");
        w.print(val.getH());
        w.print(",v=");
        w.print(val.getV());
        w.print(",w=");
        w.print(val.getW());
        w.print(",x=");
        w.print(val.getX());
        w.print(",y=");
        w.print(val.getY());
        w.print(",z=");
        w.print(val.getZ());
        w.print(",hh=");
        w.print(pixelround(val.getH()));
        w.print(",vv=");
        w.print(pixelround(val.getV()));
        w.println(")");
    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviBOP)
     */
    public void execute(DviBOP command) throws DviException,
            FontException, ConfigurationException {

        page++;
        val.clear();
        stack.clear();
        w.print(command.getStartPointer());
        w.print(": beginning of page ");
        w.println(page);
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviChar)
     */
    public void execute(DviChar command) throws DviException,
            FontException, ConfigurationException {

        Integer key = new Integer(val.getF());
        Font f = (Font) fontmap.get(key);
        if (f == null) {
            throw new DviFontNotFoundException(command.getName());
        }
        UnicodeChar uc = UnicodeChar.get(command.getCh());
        if (!f.hasGlyph(uc)) {
            throw new DviGlyphNotFoundException(String.valueOf(command.getCh()));
        }
        FixedDimen width = f.getWidth(uc).getLength();

        int oldh = val.getH();
        if (!command.isPut()) {
            val.addH((int) width.getValue());
        }
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        if (command.isSetPut()) {
            w.print(" ");
            w.print(command.getCh());
        }
        w.print(" h:=");
        w.print(oldh);
        w.print(width.getValue() >= 0 ? "+" : "");
        w.print(width.getValue());
        w.print("=");
        w.print(val.getH());
        w.print(", hh:=");
        w.println(pixelround(val.getH()));
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviDown)
     */
    public void execute(DviDown command) throws DviException,
            FontException, ConfigurationException {

        int oldv = val.getV();
        val.addV(command.getValue());
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" ");
        w.print(command.getValue());
        w.print(" v:=");
        w.print(oldv);
        w.print(command.getValue() >= 0 ? "+" : "");
        w.print(command.getValue());
        w.print("=");
        w.print(val.getV());
        w.print(", vv:=");
        w.println(pixelround(val.getV()));
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviEOP)
     */
    public void execute(DviEOP command) throws DviException,
            FontException, ConfigurationException {

        w.print(command.getStartPointer());
        w.print(": ");
        w.println(command.getName());
        w.println();
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviFntDef)
     */
    public void execute(DviFntDef command) throws DviException,
            FontException, ConfigurationException {

        if (!executepost) {
            w.print(command.getStartPointer());
            w.print(": ");
            w.print(command.getName());
            w.print(" ");
            w.print(command.getFont());
            w.print(": ");
            w.println(command.getFName());
        }
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviFntNum)
     */
    public void execute(DviFntNum command) throws DviException,
            FontException, ConfigurationException {

        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" current font is ");
        val.setF(command.getFont());
        Integer key = new Integer(val.getF());
        Font f = (Font) fontmap.get(key);
        if (f == null) {
            throw new DviFontNotFoundException(command.getName());
        }
        w.println(f.getFontName());
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPOP)
     */
    public void execute(DviPOP command) throws DviException,
            FontException, ConfigurationException {

        DviValues newval = stack.pop();
        val.setValues(newval);
        w.print(command.getStartPointer());
        w.print(": ");
        w.println(command.getName());
        printValues(stack.size());
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviNOP)
     */
    public void execute(DviNOP command) throws DviException,
            FontException, ConfigurationException {

        w.print(command.getStartPointer());
        w.print(": ");
        w.println(command.getName());

    }

    /**
     * execute psot
     */
    private boolean executepost = false;

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPost)
     */
    public void execute(DviPost command) throws DviException,
            FontException, ConfigurationException {

        executepost = true;
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPostPost)
     */
    public void execute(DviPostPost command) throws DviException,
            FontException, ConfigurationException {

        // do nothing
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPre)
     */
    public void execute(DviPre command) throws DviException,
            FontException, ConfigurationException {

        // do nothing
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPush)
     */
    public void execute(DviPush command) throws DviException,
            FontException, ConfigurationException {

        stack.push(val);
        w.print(command.getStartPointer());
        w.print(": ");
        w.println(command.getName());
        printValues(stack.size() - 1);
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviRight)
     */
    public void execute(DviRight command) throws DviException,
            FontException, ConfigurationException {

        int oldh = val.getH();
        val.addH(command.getValue());
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" ");
        w.print(command.getValue());
        w.print(" h:=");
        w.print(oldh);
        w.print(command.getValue() >= 0 ? "+" : "");
        w.print(command.getValue());
        w.print("=");
        w.print(val.getH());
        w.print(", hh:=");
        w.println(pixelround(val.getH()));
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviRule)
     */
    public void execute(DviRule command) throws DviException,
            FontException, ConfigurationException {

        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" height ");
        w.print(command.getHeight());
        w.print(", width ");
        w.print(command.getWidth());
        w.print(" (");
        w.print(rulepixels(command.getHeight()));
        w.print("x");
        w.print(rulepixels(command.getWidth()));
        w.println(" pixels)");
        if (!command.isPut()) {
            val.addH(command.getWidth());
        }
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviW)
     */
    public void execute(DviW command) throws DviException, FontException,
            ConfigurationException {

        int oldh = val.getH();
        if (!command.isW0()) {
            val.setW(command.getValue());
        }
        val.addH(val.getW());
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" ");
        w.print(val.getW());
        w.print(" h:=");
        w.print(oldh);
        w.print(val.getW() >= 0 ? "+" : "");
        w.print(val.getW());
        w.print("=");
        w.print(val.getH());
        w.print(", hh:=");
        w.println(pixelround(val.getH()));
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviX)
     */
    public void execute(DviX command) throws DviException, FontException,
            ConfigurationException {

        int oldh = val.getH();
        if (!command.isX0()) {
            val.setX(command.getValue());
        }
        val.addH(val.getX());
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" ");
        w.print(val.getX());
        w.print(" h:=");
        w.print(oldh);
        w.print(val.getX() >= 0 ? "+" : "");
        w.print(val.getX());
        w.print("=");
        w.print(val.getH());
        w.print(", hh:=");
        w.println(pixelround(val.getH()));
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviXXX)
     */
    public void execute(DviXXX command) throws DviException,
            FontException, ConfigurationException {

        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" '");
        w.print(command.getXXXString());
        w.println("'");
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviY)
     */
    public void execute(DviY command) throws DviException, FontException,
            ConfigurationException {

        int oldv = val.getV();
        if (!command.isY0()) {
            val.setY(command.getValue());
        }
        val.addV(val.getY());
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" ");
        w.print(val.getY());
        w.print(" v:=");
        w.print(oldv);
        w.print(val.getY() >= 0 ? "+" : "");
        w.print(val.getY());
        w.print("=");
        w.print(val.getV());
        w.print(", vv:=");
        w.println(pixelround(val.getV()));

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviZ)
     */
    public void execute(DviZ command) throws DviException, FontException,
            ConfigurationException {

        int oldv = val.getV();
        if (!command.isZ0()) {
            val.setZ(command.getValue());
        }
        val.addV(val.getZ());
        w.print(command.getStartPointer());
        w.print(": ");
        w.print(command.getName());
        w.print(" ");
        w.print(val.getZ());
        w.print(" v:=");
        w.print(oldv);
        w.print(val.getZ() >= 0 ? "+" : "");
        w.print(val.getZ());
        w.print("=");
        w.print(val.getV());
        w.print(", vv:=");
        w.println(pixelround(val.getV()));

    }
}
