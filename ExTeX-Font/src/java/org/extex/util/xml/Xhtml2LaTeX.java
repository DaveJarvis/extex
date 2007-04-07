/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 */

package org.extex.util.xml;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Transform a xhtml file to LaTeX.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 * 
 */
public class Xhtml2LaTeX {

    /**
     * empty command.
     */
    private static final XhtmlCommand EMPTY = new XhtmlCommand() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#startElement(
         *      java.io.PrintWriter, org.xml.sax.Attributes)
         */
        public void startElement(PrintWriter o, Attributes attributes) {

            // do nothing
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#endElement(java.io.PrintWriter)
         */
        public void endElement(PrintWriter o) {

            // do nothing

        }
    };

    /**
     * The xhtml commands.
     */
    private static final XhtmlCommand[] CMDS = {/* 0: html */
    new XhtmlCommand() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#startElement(
         *      java.io.PrintWriter, org.xml.sax.Attributes)
         */
        public void startElement(PrintWriter out, Attributes a) {

            out.println("\\documentclass{scrartcl}");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#endElement(java.io.PrintWriter)
         */
        public void endElement(PrintWriter out) {

            out.println();
        }
    },
    /* 1: body */
    new XhtmlCommand() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#startElement(
         *      java.io.PrintWriter, org.xml.sax.Attributes)
         */
        public void startElement(PrintWriter out, Attributes attributes) {

            out.println("\\begin{document}");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#endElement(java.io.PrintWriter)
         */
        public void endElement(PrintWriter out) {

            out.println("\\end{document}");
        }
    },
    /* 2: h1 */
    new XhtmlCommand() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#startElement(
         *      java.io.PrintWriter, org.xml.sax.Attributes)
         */
        public void startElement(PrintWriter out, Attributes attributes) {

            out.print("\\section{");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XhtmlCommand#endElement(java.io.PrintWriter)
         */
        public void endElement(PrintWriter out) {

            out.println("}");
        }
    }

    };

    /**
     * The map for the commands.
     */
    private static Map<String, XhtmlCommand> commands = null;

    /**
     * the buffer size.
     */
    private static final int BUFFERSIZE = 0xffff;

    /**
     * The output.
     */
    private PrintWriter out;

    /**
     * Create a new object.
     * 
     * @param pout The Output.
     */
    public Xhtml2LaTeX(OutputStream pout) {

        createCommands();

        out =
                new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(pout), BUFFERSIZE));
    }

    /**
     * Create the commands.
     */
    private void createCommands() {

        if (commands == null) {
            commands = new HashMap<String, XhtmlCommand>();

            commands.put("html", CMDS[0]);
            commands.put("head", EMPTY);
            commands.put("meta", EMPTY);
            commands.put("title", EMPTY);
            commands.put("style", EMPTY);
            commands.put("body", CMDS[1]);
            commands.put("h1", CMDS[2]);
        }
    }

    /**
     * Transform a xhtml file to latex command.
     * 
     * @param xhtmlin The xhtml input.
     * @throws IOException if an error occurs.
     */
    public void transform(InputStream xhtmlin) throws IOException {

        try {

            BufferedInputStream input =
                    new BufferedInputStream(xhtmlin, BUFFERSIZE);

            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            MyDefaultHandler defaulthandler = new MyDefaultHandler();

            parser.parse(input, defaulthandler);

            input.close();

        } catch (ParserConfigurationException e) {
            throw new IOException(e.getMessage());
        } catch (SAXException e) {
            throw new IOException(e.getMessage());
        }

    }

    /**
     * Default handler.
     */
    private class MyDefaultHandler extends DefaultHandler {

        /**
         * Create a new object.
         */
        public MyDefaultHandler() {

            super();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.xml.sax.helpers.DefaultHandler#startElement(
         *      java.lang.String, java.lang.String, java.lang.String,
         *      org.xml.sax.Attributes)
         */
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {

            super.startElement(uri, localName, qName, attributes);

            XhtmlCommand cmd = commands.get(qName);
            if (cmd != null) {
                cmd.startElement(out, attributes);
            } else {
                out.println("% start " + qName);
            }

        }

        /**
         * {@inheritDoc}
         * 
         * @see org.xml.sax.helpers.DefaultHandler#endElement( java.lang.String,
         *      java.lang.String, java.lang.String)
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            super.endElement(uri, localName, qName);

            XhtmlCommand cmd = commands.get(qName);
            if (cmd != null) {
                cmd.endElement(out);
            } else {
                out.println("% end " + qName);
            }

        }

    }

    // ------------------------------------------------
    // ------------------------------------------------
    // ------------------------------------------------
    // ------------------------------------------------
    // ------------------------------------------------
    // ------------------------------------------------

    /**
     * The main method.
     * 
     * @param args The command line arguments.
     * @throws IOException if an error occurs.
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err
                .println("java de.dante.util.xml.Xhtml2LaTeX <xhtml> <out>");
        }

        InputStream xhtmlin = new FileInputStream(args[0]);
        OutputStream xout = new FileOutputStream(args[1]);

        Xhtml2LaTeX x = new Xhtml2LaTeX(xout);

        x.transform(xhtmlin);

    }

}
