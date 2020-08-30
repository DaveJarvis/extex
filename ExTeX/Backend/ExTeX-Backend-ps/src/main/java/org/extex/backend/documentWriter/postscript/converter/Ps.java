/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.postscript.converter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.backend.documentWriter.postscript.util.PsUnit;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.RgbColor;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.typesetter.type.Node;

/**
 * This utility class provides some routines for writing PostScript code.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Ps {

    /**
     * The field {@code code} contains the pieces of code for the dictionary.
     */
    private static Map<String, String> code = new HashMap<String, String>();

    static {
        code.put("eop", "/eop{}def");
        code.put("rule", "/rule{gsave 0 setlinewidth newpath moveto"
                + "exch dup 0 rlineto exch "
                + " 0 exch rlineto neg 0 rlineto closepath fill "
                + "grestore}def");
        code.put("s", "/s{moveto show}def");
        code.put("x", "/x{currentpoint exch pop moveto show}def");
        code.put("Cg", "/Cg{setgray}def");
        code.put("Cr", "/Cr{setrgbcolor}def");
        code.put("box", "/box{gsave 0 setgray .2 setlinewidth newpath "
                + "moveto exch dup 0 rlineto exch "
                + "0 exch rlineto neg 0 rlineto closepath stroke "
                + "grestore}def");
        code.put("Box", "/Box{gsave 0.75 setgray .2 setlinewidth newpath "
                + "moveto exch dup 0 rlineto exch "
                + "0 exch rlineto neg 0 rlineto closepath stroke "
                + "grestore}def");
    }

    /**
     * The field {@code lib} contains the library code to include.
     */
    private List<String> lib = new ArrayList<String>();

    /**
     * The field {@code used} contains the indicator for the fragments used.
     */
    private Map<String, Boolean> used = new HashMap<String, Boolean>();


    public Ps() {

    }

    /**
     * Add some code to the output stream and the library code.
     * 
     * @param out the output stream
     * @param key the key for the code
     */
    private void addLib(PrintStream out, String key) {

        out.println(key);
        if (used.get(key) == null) {
            used.put(key, Boolean.TRUE);
            lib.add(key);
        }
    }

    /**
     * Draw a little box showing the dimensions of the node.
     * 
     * @param out the target string buffer
     * @param node the node to draw
     * @param x the x coordinate
     * @param y the y coordinate
     */
    protected void drawBox(PrintStream out, Node node, FixedDimen x,
            FixedDimen y) {

        FixedDimen height = node.getHeight();
        if (height.ne(Dimen.ZERO_PT)) {
            PsUnit.toPoint(node.getWidth(), out, false);
            out.write(' ');
            PsUnit.toPoint(height, out, false);
            out.write(' ');
            PsUnit.toPoint(x, out, false);
            out.write(' ');
            PsUnit.toPoint(y, out, false);
            out.write(' ');
            addLib(out, "box");
        }
    }

    /**
     * Draw a little box showing the dimensions of the node.
     * 
     * @param out the target string buffer
     * @param node the node to draw
     * @param x the x coordinate
     * @param y the y coordinate
     */
    protected void drawGrayBox(PrintStream out, Node node, FixedDimen x,
            FixedDimen y) {

        FixedDimen height = node.getHeight();
        if (height.ne(Dimen.ZERO_PT)) {
            PsUnit.toPoint(node.getWidth(), out, false);
            out.write(' ');
            PsUnit.toPoint(height, out, false);
            out.write(' ');
            PsUnit.toPoint(x, out, false);
            out.write(' ');
            PsUnit.toPoint(y, out, false);
            out.write(' ');
            addLib(out, "Box");
        }
    }

    /**
     * Produce a eop hook invocation.
     * 
     * @param out the output stream
     */
    public void eop(PrintStream out) {

        out.write(' ');
        addLib(out, "eop");
    }

    /**
     * Put some text at a certain position.
     * 
     * @param out the output stream
     * @param text the text
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void putText(PrintStream out, CharSequence text, Dimen x, Dimen y) {

        out.write('(');
        out.append(text);
        out.write(')');
        PsUnit.toPoint(x, out, false);
        out.write(' ');
        PsUnit.toPoint(y, out, false);
        out.write(' ');
        addLib(out, "s");
    }

    /**
     * Put some text at a certain position given by x coordinate only.
     * 
     * @param out the output stream
     * @param text the text
     * @param x the x coordinate
     */
    public void putText(PrintStream out, CharSequence text, Dimen x) {

        out.write('(');
        out.append(text);
        out.write(')');
        PsUnit.toPoint(x, out, false);
        out.write(' ');
        addLib(out, "x");
    }

    /**
     * Produce a filled rectangle.
     * 
     * @param out the output stream
     * @param width the width of the rule
     * @param height the height of the rule
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void rule(PrintStream out, FixedDimen width, FixedDimen height,
            FixedDimen x, FixedDimen y) {

        PsUnit.toPoint(width, out, false);
        out.write(' ');
        PsUnit.toPoint(height, out, false);
        out.write(' ');
        PsUnit.toPoint(x, out, false);
        out.write(' ');
        PsUnit.toPoint(y, out, false);
        out.write(' ');
        addLib(out, "rule");
    }

    /**
     * Produce a PS setgray instruction.
     * 
     * @param out the output stream
     * @param gray the color
     */
    public void setgray(PrintStream out, GrayscaleColor gray) {

        PsUnit.toPoint(new Dimen(gray.getGray() * Dimen.ONE), out, false);
        out.write(' ');
        addLib(out, "Cg");
    }

    /**
     * Produce a PS setrgbcolor instruction.
     * 
     * @param out the output stream
     * @param rgb the color
     */
    public void setrgb(PrintStream out, RgbColor rgb) {

        PsUnit.toPoint(new Dimen(rgb.getRed() * Dimen.ONE), out, false);
        out.write(' ');
        PsUnit.toPoint(new Dimen(rgb.getGreen() * Dimen.ONE), out, false);
        out.write(' ');
        PsUnit.toPoint(new Dimen(rgb.getBlue() * Dimen.ONE), out, false);
        out.write(' ');
        addLib(out, "Cr");
    }

    /**
     * Write the dictionary of collected code.
     * 
     * @param out the output stream
     * 
     * @throws IOException in case of an I/O error
     */
    public void writeDict(PrintStream out) throws IOException {

        if (lib.isEmpty()) {
            return;
        }
        out.println("%%BeginProcSet: Ps.java");
        out.print("TeXDict begin ");
        for (String s : lib) {
            out.print(code.get(s));
            out.write(' ');
        }
        out.println("end");
        out.println("%%EndProcSet");
        lib.clear();
    }

}
