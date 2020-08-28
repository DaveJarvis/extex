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

package org.extex.util.font.texencoding;

import java.io.File;
import java.io.FileOutputStream;

import org.extex.font.format.texencoding.EncWriter;
import org.extex.util.font.AbstractFontUtil;

/**
 * Create a empty tex font encoding vector.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5594 $
 */
public class CreateEmptyEncoding extends AbstractFontUtil {

    /**
     * main.
     * 
     * @param args The command line.
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        CreateEmptyEncoding enc = new CreateEmptyEncoding();

        if (args.length > 0) {
            int i = 0;
            do {
                if ("-o".equals(args[i]) || "--outdir".equals(args[i])) {
                    if (i + 1 < args.length) {
                        enc.setOutdir(args[++i]);
                    }

                }
                i++;
            } while (i < args.length);
        }

        enc.doIt();
    }


    public CreateEmptyEncoding() {

        super(CreateEmptyEncoding.class);
    }

    /**
     * do it.
     * 
     * @throws Exception if an error occurs.
     */
    public void doIt() throws Exception {

        EncWriter writer = new EncWriter();

        String outfile = getOutdir() + File.separator + "empty.enc";

        writer.setEncname("EmptyEncoding");
        writer.setComments(true);
        writer.setHeaderComment(createVersion("CreateEmptyEncoding.created"));

        writer.write(new FileOutputStream(outfile));

        getLogger().severe(
            getLocalizer().format("CreateEmptyEncoding.end", outfile));

    }
}
