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
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
import org.extex.format.dvi.exception.DviBopEopException;
import org.extex.format.dvi.exception.DviException;
import org.extex.format.dvi.exception.DviFontNotFoundException;
import org.extex.format.dvi.exception.DviGlyphNotFoundException;
import org.extex.format.dvi.exception.DviMissingFontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.type.font.Font;
import org.extex.util.Unit;
import org.extex.util.file.random.RandomAccessR;
import org.jdom.Element;

/**
 * DVI to XML converter.
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

public class DviXml implements DviInterpreter, DviExecuteCommand {

    // TODO incompelte: change from element to xmlwriter
    /**
     * the parent element (change from some commands to nested other elements)
     */
    private Element parent;

    /**
     * Stack for the parents.
     * <p>
     * Each <code>bop</code> push the current parent element to the stack,
     * create a new Element and add it to the last parent.
     * Each <code>eop</code> pop the top element to parent.
     * </p>
     * <p>
     * The fntdef-Element are also stored in the post element, so post calls push
     * ans postpost calls pop before execute.
     * </p>
     */
    private Stack<Element> parentstack;

    /**
     * page counter
     */
    private int page = 0;

    /**
     * the fontfactory
     */
    private FontFactory fontfactory;

    /**
     * the map for all sub fonts.
     */
    private Map<Integer, Font> fontmap;

    /**
     * the dvi stack
     */
    private DviStack stack;

    /**
     * the dvi values;
     */
    private DviValues val;

    /**
     * Create a new object.
     * <p>
     * It is only usefull for small dvi-files
     * (or you have a lot of memory)
     * </p>
     *
     * @param element   the root element
     * @param ff        the fontfactroy
     */
    public DviXml(Element element, FontFactory ff) {

        parent = element;
        fontfactory = ff;
        parentstack = new Stack<Element>();
        fontmap = new HashMap<Integer, Font>();
        val = new DviValues();
        stack = new DviStack();
    }

    /**
     * show pt-values in elements
     */
    private boolean showPT;

    /**
     * the mag
     */
    private int mag;

    /**
     * Load the font.
     *
     * @param command   the command
     * @throws DviException if a font not found.
     * @throws FontException if an font-error occured.
     * @throws ConfigurationException from the config-system.
     */
    private void loadFont(DviFntDef command) throws DviException,
            FontException, ConfigurationException {

        Integer key = new Integer(command.getFont());
        Dimen designsize = command.getScaleAsDimen();
        Count scale = command.getScaledAsCount(mag);
        String name = command.getFName();

        //        BaseFont f = fontfactory.getInstance(name, designsize, scale, new Glue(0),
        //                true, true);
        // mgn: umbauen
        Font f = null;// fontfactory.getInstance(new FountKey(name, designsize, scale,
                //new Glue(0), true, true));
        if (f == null) {
            throw new DviFontNotFoundException(name);
        }
        fontmap.put(key, f);
    }

    /**
     * @param command   the command
     * @param element   the element
     */
    private void addFontAttributes(DviFntDef command,
            Element element) {

        element.setAttribute("font", String.valueOf(command.getFont()));
        element.setAttribute("checksum", String.valueOf(command.getChecksum()));
        element.setAttribute("scalefactor", String.valueOf(command.getScale()));
        element.setAttribute("designsize", String.valueOf(command
                .getDesignsize()));
        if (showPT) {
            element.setAttribute("scalefactor_pt", Unit
                    .getDimenAsPTString(new Dimen(command.getScale())));
            element.setAttribute("designsize_pt", Unit
                    .getDimenAsPTString(new Dimen(command.getDesignsize())));
        }
        element
                .setAttribute("scaled", command.getScaledAsCount(mag)
                        .toString());
        element.setAttribute("area", command.getArea());
        element.setAttribute("name", command.getFName());
    }

    /**
     * @see org.extex.format.dvi.DviInterpreter#interpret(
     *      org.extex.util.file.random.RandomAccessR)
     */
    public void interpret(RandomAccessR rar) throws IOException,
            DviException, FontException, ConfigurationException {

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
            //System.out.println("mist, einen vergessen opcode:"
            //        + command.getOpcode());

        }
    }

    /**
     * Read the next command as element.
     * All commands of a 'bop' are collected in one element.
     * <p>
     * This method is for large dvi files, to read each page in
     * bop-element separately.
     * </p>
     *
     * @param rar           the input
     * @return Returns the Element for the command
     * @throws IOException   if an IO-error occurs.
     * @throws DviException  if a DVI-error occurs.
     * @throws FontException if a font-error occurs.
     * @throws ConfigurationException from the config system.
     */
    public Element readNextElement(RandomAccessR rar) throws IOException,
            DviException, FontException, ConfigurationException {

        // store old parent
        Element old = parent;
        parent = null;

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
                break;
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
                break;
            } else if (command instanceof DviPostPost) {
                DviPostPost cc = (DviPostPost) command;
                execute(cc);
                break;
            } else if (command instanceof DviPre) {
                DviPre cc = (DviPre) command;
                execute(cc);
                break;
            }
        }
        Element rt = parent;
        // restore parent
        parent = old;
        return rt;
    }

    /**
     * Returns the font from the font map.
     * @return Returns the font from the font map.
     * @throws DviMissingFontException if the font is not found.
     */
    private Font getFont() throws DviMissingFontException {

        if (val.getF() == -1) {
            return null;
        }
        Font font = fontmap.get(new Integer(val.getF()));
        if (font == null) {
            throw new DviMissingFontException(String.valueOf(val.getF()));
        }
        return font;
    }

    /**
     * Set the values in the xml-element.
     * @param element   the element
     */
    private void setValues(Element element) {

        element.setAttribute("h", String.valueOf(val.getH()));
        element.setAttribute("v", String.valueOf(val.getV()));
        element.setAttribute("w", String.valueOf(val.getW()));
        element.setAttribute("x", String.valueOf(val.getX()));
        element.setAttribute("y", String.valueOf(val.getY()));
        element.setAttribute("z", String.valueOf(val.getZ()));
        if (showPT) {
            element.setAttribute("h_pt", Unit.getDimenAsPTString(new Dimen(val
                    .getH())));
            element.setAttribute("v_pt", Unit.getDimenAsPTString(new Dimen(val
                    .getV())));
            element.setAttribute("w_pt", Unit.getDimenAsPTString(new Dimen(val
                    .getW())));
            element.setAttribute("x_pt", Unit.getDimenAsPTString(new Dimen(val
                    .getX())));
            element.setAttribute("y_pt", Unit.getDimenAsPTString(new Dimen(val
                    .getY())));
            element.setAttribute("z_pt", Unit.getDimenAsPTString(new Dimen(val
                    .getZ())));
        }
    }

    /**
     * Set the BaseFont and Glyph-Info.
     * @param opcode    the opcode (char-id)
     * @param element   the element
     * @throws DviMissingFontException  if the font is missing.
     */
    private void setFontGlyphInfo(int opcode, Element element)
            throws DviMissingFontException {

        Font font = getFont();
        if (font != null) {
            element.setAttribute("font", font.getFontName());
            UnicodeChar uc = UnicodeChar.get(opcode);
            FixedDimen h = font.getHeight(uc).getLength();
            FixedDimen d = font.getDepth(uc).getLength();
            FixedDimen w = font.getWidth(uc).getLength();
            element.setAttribute("height", String.valueOf(h.getValue()));
            element.setAttribute("depth", String.valueOf(d.getValue()));
            element.setAttribute("width", String.valueOf(w.getValue()));
            if (showPT) {
                element.setAttribute("height_pt", Unit.getDimenAsPTString(h));
                element.setAttribute("depth_pt", Unit.getDimenAsPTString(d));
                element.setAttribute("width_pt", Unit.getDimenAsPTString(w));
            }
            val.addH((int) w.getValue());
        }
    }

    /**
     * Set the value for showPT.
     * @param show The showPT to set.
     */
    public void setShowPT(boolean show) {

        showPT = show;
    }

    /**
     * Set the font map.
     * @param fontm The font map to set.
     */
    public void setFontmap(Map<Integer, Font> fontm) {

        fontmap = fontm;
    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviBOP)
     */
    public void execute(DviBOP command) throws DviException,
            FontException, ConfigurationException {

        page++;
        val.clear();
        stack.clear();

        Element element = createElement(command);
        int[] c = command.getC();
        for (int i = 0; i < c.length; i++) {
            element.setAttribute("c" + String.valueOf(i), String.valueOf(c[i]));
        }
        element.setAttribute("p", String.valueOf(command.getP()));
        element.setAttribute("page", String.valueOf(page));
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }
        parentstack.push(parent);
        parent = element;
        val.clear();

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviChar)
     */
    public void execute(DviChar command) throws DviException,
            FontException, ConfigurationException {

        Integer key = new Integer(val.getF());
        Font f = fontmap.get(key);
        if (f == null) {
            throw new DviFontNotFoundException(command.getName());
        }
        UnicodeChar uc = UnicodeChar.get(command.getCh());
        if (!f.hasGlyph(uc)) {
            throw new DviGlyphNotFoundException(String.valueOf(command.getCh()));
        }
        FixedDimen width = f.getWidth(uc).getLength();

        Element element = createElement(command);
        element.setAttribute("char", String.valueOf(command.getCh()));
        setValues(element);
        setFontGlyphInfo(command.getOpcode(), element);
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

        if (!command.isPut()) {
            val.addH((int) width.getValue());
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviDown)
     */
    public void execute(DviDown command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        element.setAttribute("value", String.valueOf(command.getValue()));
        setValues(element);
        val.addV(command.getValue());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviEOP)
     */
    public void execute(DviEOP command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }
        if (parentstack.empty()) {
            throw new DviBopEopException();
        }
        parent = parentstack.pop();

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviFntDef)
     */
    public void execute(DviFntDef command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        addFontAttributes(command, element);
        loadFont(command);
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviFntNum)
     */
    public void execute(DviFntNum command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        element.setAttribute("id", String.valueOf(command.getFont()));
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }
        val.setF(command.getFont());

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPOP)
     */
    public void execute(DviPOP command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        DviValues v = stack.pop();
        val.setValues(v);
        setValues(element);
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviNOP)
     */
    public void execute(DviNOP command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPost)
     */
    public void execute(DviPost command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        element.setAttribute("p", String.valueOf(command.getPointer()));
        element.setAttribute("num", String.valueOf(command.getNum()));
        element.setAttribute("den", String.valueOf(command.getDen()));
        element.setAttribute("mag", String.valueOf(command.getMag()));
        element.setAttribute("heightplusdepth", String.valueOf(command
                .getHeigthdepth()));
        element.setAttribute("widthwidest", String.valueOf(command.getWidth()));
        element.setAttribute("stackdepth", String.valueOf(command
                .getStackdepth()));
        element.setAttribute("totalpage", String
                .valueOf(command.getTotalpage()));
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }
        parentstack.push(parent);
        parent = element;

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPostPost)
     */
    public void execute(DviPostPost command) throws DviException,
            FontException, ConfigurationException {

        if (parentstack.empty()) {
            throw new DviBopEopException();
        }
        parent = parentstack.pop();

        Element element = createElement(command);
        element.setAttribute("q", String.valueOf(command.getPointer()));
        element.setAttribute("identifies", String.valueOf(command
                .getIdentification()));
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPre)
     */
    public void execute(DviPre command) throws DviException,
            FontException, ConfigurationException {

        mag = command.getMag();
        Element element = createElement(command);
        element.setAttribute("identifies", String.valueOf(command
                .getIdentifier()));
        element.setAttribute("num", String.valueOf(command.getNum()));
        element.setAttribute("den", String.valueOf(command.getDen()));
        element.setAttribute("mag", String.valueOf(command.getMag()));
        if (command.getComment().length() > 0) {
            Element comment = new Element("comment");
            comment.setText(command.getComment());
            element.addContent(comment);
        }
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviPush)
     */
    public void execute(DviPush command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        setValues(element);
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }
        stack.push(val);

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviRight)
     */
    public void execute(DviRight command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        element.setAttribute("value", String.valueOf(command.getValue()));
        setValues(element);
        val.addH(command.getValue());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviRule)
     */
    public void execute(DviRule command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        element.setAttribute("height", String.valueOf(command.getHeight()));
        element.setAttribute("width", String.valueOf(command.getWidth()));
        setValues(element);
        if (!command.isPut()) {
            val.addH(command.getWidth());
        }
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviW)
     */
    public void execute(DviW command) throws DviException, FontException,
            ConfigurationException {

        Element element = createElement(command);
        setValues(element);
        if (!command.isW0()) {
            val.setW(command.getValue());
            element.setAttribute("value", String.valueOf(command.getValue()));
        }
        val.addH(val.getW());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviX)
     */
    public void execute(DviX command) throws DviException, FontException,
            ConfigurationException {

        Element element = createElement(command);
        setValues(element);
        if (!command.isX0()) {
            val.setX(command.getValue());
            element.setAttribute("value", String.valueOf(command.getValue()));
        }
        val.addH(val.getX());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviXXX)
     */
    public void execute(DviXXX command) throws DviException,
            FontException, ConfigurationException {

        Element element = createElement(command);
        element.setText(command.getXXXString());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviY)
     */
    public void execute(DviY command) throws DviException, FontException,
            ConfigurationException {

        Element element = createElement(command);
        setValues(element);
        if (!command.isY0()) {
            val.setY(command.getValue());
            element.setAttribute("value", String.valueOf(command.getValue()));
        }
        val.addV(val.getY());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * @see org.extex.format.dvi.command.DviExecuteCommand#execute(
     *      org.extex.format.dvi.command.DviZ)
     */
    public void execute(DviZ command) throws DviException, FontException,
            ConfigurationException {

        Element element = createElement(command);
        setValues(element);
        if (!command.isZ0()) {
            val.setZ(command.getValue());
            element.setAttribute("value", String.valueOf(command.getValue()));
        }
        val.addV(val.getZ());
        if (parent == null) {
            parent = element;
        } else {
            parent.addContent(element);
        }

    }

    /**
     * Create the element from a dvi command.
     * @param command   the command
     * @return Returns the element.
     */
    private Element createElement(DviCommand command) {

        Element e = new Element(command.getName());
        e.setAttribute("opcode", String.valueOf(command.getOpcode()));
        return e;
    }
}
