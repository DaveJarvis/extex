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

package org.extex.backend.documentWriter.itextpdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.extex.ExTeX;
import org.extex.core.exception.helping.HelpingException;

/**
 * run ExTeX with the pdf backend.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class RunPdfExTeX {

    /**
     * The constant <tt>DEFINE_BRACES</tt> contains the definition of the
     * usual category codes for braces { and }.
     */
    public static final String DEFINE_BRACES =
            "\\catcode`\\{=1 " + "\\catcode`\\}=2\\relax ";

    /**
     * The constant <tt>DEFINE_CATCODES</tt> contains the definition of the
     * usual category codes for {, }, $, &, #, ^, _, and ^^10.
     */
    public static final String DEFINE_CATCODES =
            "\\catcode`\\{=1 " + "\\catcode`\\}=2 " + "\\catcode`\\$=3 "
                    + "\\catcode`\\&=4 " + "\\catcode`\\#=6 "
                    + "\\catcode`\\^=7 " + "\\catcode`\\_=8 "
                    + "\\catcode`\\^^I=10 ";

    /**
     * main.
     * 
     * @param args The command line arguments.
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        Properties prop = new Properties(System.getProperties());
        prop.setProperty("extex.typesetter", "devel");
        prop.setProperty("extex.output", "itext2");
        File file = new File("test.pdf");

        run(prop, DEFINE_CATCODES + "\\font\\hugo=fxlr " + "\\hugo "
                + "Hugo : ^^^^016e" + "\\end", new FileOutputStream(file));
        System.out.println("create " + file.getPath());

        // File filexml = new File("test.xml");
        // File filedump = new File("test.dump");
        // prop.setProperty("extex.output", "xml");
        // run(prop, "\\font\\hugo=cmr12 " + "\\hugo " + "Hugo " + "\\end",
        // new FileOutputStream(filexml));
        // System.out.println("create " + filexml.getPath());
        //
        // prop.setProperty("extex.output", "dump");
        // run(prop, "\\font\\hugo=cmr12 " + "\\hugo " + "Hugo " + "\\end",
        // new FileOutputStream(filedump));
        // System.out.println("create " + filedump.getPath());

    }

    /**
     * Run ExTeX.
     * 
     * @param properties The properties.
     * @param code The code.
     * @param out The output stream.
     * @throws HelpingException if an error occurred.
     */
    public static void run(Properties properties, String code, OutputStream out)
            throws HelpingException {

        properties.setProperty("extex.code", code);
        properties.setProperty("extex.file", "");
        properties.setProperty("extex.nobanner", "true");
        properties.setProperty("extex.config", "base32-test.xml");
        properties.setProperty("extex.token.stream", "base32");

        ExTeX extex = new ExTeX(properties);
        extex.setOutStream(out);

        try {
            extex.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
