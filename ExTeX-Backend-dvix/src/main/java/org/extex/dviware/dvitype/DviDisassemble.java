/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.dviware.dvitype;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.dviware.Dvi;
import org.extex.dviware.DviProcessor;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This class provides a command line tool to disassemble a DVI file.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4704 $
 */
public class DviDisassemble implements DviProcessor {

    /**
     * The field <tt>condensed</tt> contains the indicator that sequences of
     * put_char instructions should be condensed.
     */
    private static boolean condensed = true;

    /**
     * The field <tt>hexLabel</tt> contains the indicator that the label
     * should be presented as hex number.
     */
    private static boolean hexLabel = true;

    /**
     * The constant <tt>PROP_CONFIG</tt> contains the name of the property for
     * the configuration resource to use.
     */
    protected static final String PROP_CONFIG = "extex.config";

    /**
     * The field <tt>showLabel</tt> contains the indicator that the label
     * should be shown as labels.
     */
    private static boolean showLabel = true;

    /**
     * The command line interface to dvitype.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(DviDisassemble.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);

        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (a.startsWith("-decimal")) {
                hexLabel = false;
            } else if (a.startsWith("-nolabel")) {
                showLabel = false;
            } else if (a.startsWith("-uncondensed")) {
                condensed = false;
            } else {
                process(a, logger);
            }
        }
    }

    /**
     * Process an input file.
     * 
     * @param arg the resource name to process
     * @param logger the logger
     */
    protected static void process(String arg, Logger logger) {

        Properties properties = System.getProperties();
        properties.setProperty(PROP_CONFIG, "config/extex");

        Configuration config;
        ResourceFinder finder;
        try {
            config =
                    new ConfigurationFactory().newInstance(properties
                        .getProperty(PROP_CONFIG));
            finder =
                    new ResourceFinderFactory()
                        .createResourceFinder(config
                            .getConfiguration("Resource"), logger, properties,
                            null);
            InputStream dvi = finder.findResource(arg, "dvi");

            if (dvi == null) {
                logger.severe("Resource `" + arg + "' not found");
                return;
            }
            new Dvi(dvi).parse(new DviDisassemble(System.out));

            dvi.close();
        } catch (Exception e) {
            logger.throwing("DviDisassemble", "process", e);
        }
    }

    /**
     * The field <tt>inString</tt> contains the indicator that a sequence of
     * characters has already been begun.
     */
    private boolean inString = false;

    /**
     * The field <tt>out</tt> contains the output stream.
     */
    private PrintStream out;

    /**
     * Creates a new object.
     * 
     * @param out the output stream
     */
    public DviDisassemble(PrintStream out) {

        super();
        this.out = out;
    }

    /**
     * A DVI <tt>bop</tt> instruction has been encountered. This instruction
     * signals the beginning of a new page.
     * 
     * @param off the current byte position in the input stream
     * @param c the array of page number indicators. The array has length 10. It
     *        is initialized from the count registers 0 to 9 at the time the
     *        page is shipped out.
     * @param p the pointer to the previous <tt>bop</tt> instruction or -1 for
     *        the first page
     * 
     * @see org.extex.dviware.DviProcessor#bop(int, int[], int)
     */
    public void bop(int off, int[] c, int p) {

        printLabel(off);
        out.print("bop ");
        out.print(c[0]);
        out.print(' ');
        out.print(c[1]);
        out.print(' ');
        out.print(c[2]);
        out.print(' ');
        out.print(c[3]);
        out.print(' ');
        out.print(c[4]);
        out.print(' ');
        out.print(c[5]);
        out.print(' ');
        out.print(c[6]);
        out.print(' ');
        out.print(c[7]);
        out.print(' ');
        out.print(c[8]);
        out.print(' ');
        out.print(c[9]);
        out.print(" 0x");
        out.println(Integer.toHexString(p));
    }

    /**
     * A DVI <tt>down</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param b the number of DVI units to move down. If negative then the
     *        current position moves upwards.
     * 
     * @see org.extex.dviware.DviProcessor#down(int, int)
     */
    public void down(int off, int b) {

        printLabel(off);
        out.print("down ");
        out.println(b);
    }

    /**
     * A DVI <tt>eop</tt> instruction has been encountered. This instruction
     * signals the end of a page.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#eop(int)
     */
    public void eop(int off) {

        printLabel(off);
        out.println("eop");
    }

    /**
     * A DVI <tt>fnt</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param k the new font number; this number is not negative
     * 
     * @see org.extex.dviware.DviProcessor#fnt(int, int)
     */
    public void fnt(int off, int k) {

        printLabel(off);
        out.print("fnt ");
        out.println(k);
    }

    /**
     * A DVI <tt>fntDef</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param k the number of the font
     * @param c ...
     * @param s ...
     * @param d ...
     * @param name the name of the font
     * 
     * @see org.extex.dviware.DviProcessor#fntDef(int, int, int, int, int,
     *      java.lang.String)
     */
    public void fntDef(int off, int k, int c, int s, int d, String name) {

        printLabel(off);
        out.print("fnt_def ");
        out.print(k);
        out.print(' ');
        out.print(c);
        out.print(' ');
        out.print(s);
        out.print(' ');
        out.print(d);
        out.print(' ');
        out.print('"');
        out.print(name);
        out.println('"');
    }

    /**
     * A DVI <tt>nop</tt> instruction has been encountered. This instruction
     * simply does nothing. It just occupies one byte in the input stream.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#nop(int)
     */
    public void nop(int off) {

        printLabel(off);
        out.println("nop");
    }

    /**
     * A DVI <tt>pop</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#pop(int)
     */
    public void pop(int off) {

        printLabel(off);
        out.println("pop");
    }

    /**
     * A DVI <tt>post</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param p ...
     * @param num the numerator
     * @param den the denominator
     * @param mag the magnification
     * @param l ...
     * @param u ...
     * @param sp ...
     * @param tp ...
     * 
     * @see org.extex.dviware.DviProcessor#post(int, int, int, int, int, int,
     *      int, int, int)
     */
    public void post(int off, int p, int num, int den, int mag, int l, int u,
            int sp, int tp) {

        printLabel(off);
        out.print("post 0x");
        out.print(Integer.toHexString(p));
        out.print(' ');
        out.print(num);
        out.print(' ');
        out.print(den);
        out.print(' ');
        out.print(mag);
        out.print(' ');
        out.print(l);
        out.print(' ');
        out.print(u);
        out.print(' ');
        out.print(sp);
        out.print(' ');
        out.println(tp);
    }

    /**
     * Invoke the callback on a POST_POST instruction. This is the last
     * instruction in a DVI file.
     * 
     * @param off the offset in the file of this instruction
     * @param bop the index of the last BOP instruction
     * @param id the id of this DVI version. Usually this is 2.
     * 
     * @see org.extex.dviware.DviProcessor#postPost(int, int, int)
     */
    public void postPost(int off, int bop, int id) {

        printLabel(off);
        out.print("post_post 0x");
        out.print(Integer.toHexString(bop));
        out.print(' ');
        out.println(id);
    }

    /**
     * A DVI <tt>pre</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param id ...
     * @param num the numerator
     * @param den the denominator
     * @param mag the magnification in permille
     * @param comment the comment string
     * 
     * @see org.extex.dviware.DviProcessor#pre(int, int, int, int, int,
     *      java.lang.String)
     */
    public void pre(int off, int id, int num, int den, int mag, String comment) {

        printLabel(off);
        out.print("pre ");
        out.print(id);
        out.print(' ');
        out.print(num);
        out.print(' ');
        out.print(den);
        out.print(' ');
        out.print(mag);
        out.print(' ');
        out.print('"');
        out.print(comment);
        out.println('"');
    }

    /**
     * Print the prefix before the opcode.
     * 
     * @param off the label, i.e. the address of the opcode
     */
    private void printLabel(int off) {

        if (inString) {
            inString = false;
            out.print("\"\n");
        }

        if (!showLabel) {
            // ignore
        } else if (hexLabel) {
            String s = Integer.toHexString(off);
            switch (s.length()) {
                case 0:
                    out.print('0');
                case 1:
                    out.print('0');
                case 2:
                    out.print('0');
                case 3:
                    out.print('0');
                default:
                    // continue
            }
            out.print(s);
        } else {
            out.print(Integer.toString(off));
        }
        out.print('\t');
    }

    /**
     * A DVI <tt>push</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#push(int)
     */
    public void push(int off) {

        printLabel(off);
        out.println("push");
    }

    /**
     * A DVI <tt>put_char</tt> instruction has been encountered.
     * 
     * @param off the current byte position
     * @param c the number of the character to set
     * 
     * @see org.extex.dviware.DviProcessor#putChar(int, int)
     */
    public void putChar(int off, int c) {

        printLabel(off);
        out.print("put_char ");
        out.print(Integer.toString(c));
        out.print("\t\t\t; ");
        out.println(Character.toString((char) c));
    }

    /**
     * A DVI <tt>put_rule</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param a the width
     * @param b the height
     * 
     * @see org.extex.dviware.DviProcessor#putRule(int, int, int)
     */
    public void putRule(int off, int a, int b) {

        printLabel(off);
        out.print("put_rule ");
        out.print(Integer.toString(a));
        out.print(' ');
        out.println(Integer.toString(b));
    }

    /**
     * A DVI <tt>right</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param b the distance to move in DVI units
     * 
     * @see org.extex.dviware.DviProcessor#right(int, int)
     */
    public void right(int off, int b) {

        printLabel(off);
        out.print("right ");
        out.println(b);
    }

    /**
     * A DVI <tt>set_char</tt> instruction has been encountered.
     * 
     * @param off the current byte position
     * @param c the number of the character to set
     * 
     * @see org.extex.dviware.DviProcessor#setChar(int, int)
     */
    public void setChar(int off, int c) {

        if (condensed) {
            if (!inString) {
                printLabel(off);
                out.print("\"");
                inString = true;
            }
            out.print(Character.toString((char) c));
        } else {
            printLabel(off);
            out.print("set_char_");
            out.print(Integer.toString(c));
            out.print("\t\t\t; ");
            out.println(Character.toString((char) c));
        }
    }

    /**
     * A DVI <tt>set_rule</tt> instruction has been encountered.
     * 
     * @param off the current byte position
     * @param a the width
     * @param b the height
     * 
     * @see org.extex.dviware.DviProcessor#setRule(int, int, int)
     */
    public void setRule(int off, int a, int b) {

        printLabel(off);
        out.print("set_rule ");
        out.print(Integer.toString(a));
        out.print(' ');
        out.println(Integer.toString(b));
    }

    /**
     * A DVI undefined instruction has been encountered. This callback is
     * invoked for the op-codes 250&ndash;255 which are undefined in <logo>TeX</logo>.
     * 
     * @param off the current byte position
     * @param opcode the opcode encountered
     * @param stream the input stream to read further bytes from
     * 
     * @see org.extex.dviware.DviProcessor#undef(int, int, java.io.InputStream)
     */
    public void undef(int off, int opcode, InputStream stream) {

        printLabel(off);
        out.println("0x");
        out.println(Integer.toHexString(opcode));
    }

    /**
     * A DVI <tt>w</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param b the distance to add in DVI units
     * 
     * @see org.extex.dviware.DviProcessor#w(int, int)
     */
    public void w(int off, int b) {

        printLabel(off);
        out.print("w ");
        out.println(b);
    }

    /**
     * A DVI <tt>w0</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#w0(int)
     */
    public void w0(int off) {

        printLabel(off);
        out.println("w0");
    }

    /**
     * A DVI <tt>x</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param b the distance to move in DVI units
     * 
     * @see org.extex.dviware.DviProcessor#x(int, int)
     */
    public void x(int off, int b) {

        printLabel(off);
        out.print("x ");
        out.println(b);
    }

    /**
     * A DVI <tt>x0</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#x0(int)
     */
    public void x0(int off) {

        printLabel(off);
        out.println("x0");
    }

    /**
     * A DVI <tt>xxx</tt> instruction has been encountered. This instruction
     * is used to pass some bytes uninterpreted to the DVI processor. In
     * <logo>TeX</logo> this is accomplished with the primitive
     * <tt>\special</tt>.
     * 
     * @param off the current byte position in the input stream
     * @param x the array of bytes carrying the content
     * 
     * @see org.extex.dviware.DviProcessor#xxx(int, byte[])
     */
    public void xxx(int off, byte[] x) {

        printLabel(off);
        out.print("xxx ");
        out.print('"');
        out.print(x);
        out.println('"');
    }

    /**
     * A DVI <tt>y</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param b the distance to move
     * 
     * @see org.extex.dviware.DviProcessor#y(int, int)
     */
    public void y(int off, int b) {

        printLabel(off);
        out.print("y ");
        out.println(b);
    }

    /**
     * A DVI <tt>y0</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @see org.extex.dviware.DviProcessor#y0(int)
     */
    public void y0(int off) {

        printLabel(off);
        out.println("y0");
    }

    /**
     * A DVI <tt>z</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * @param b the distance to move
     * @see org.extex.dviware.DviProcessor#z(int, int)
     */
    public void z(int off, int b) {

        printLabel(off);
        out.print("z ");
        out.println(b);
    }

    /**
     * A DVI <tt>z0</tt> instruction has been encountered.
     * 
     * @param off the current byte position in the input stream
     * 
     * @see org.extex.dviware.DviProcessor#z0(int)
     */
    public void z0(int off) {

        printLabel(off);
        out.println("z0");
    }

}
