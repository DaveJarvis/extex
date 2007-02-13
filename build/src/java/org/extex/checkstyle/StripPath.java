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
package org.extex.checkstyle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StripPath {

    /**
     * Command line interface.
     *
     * @param arg the command line arguments
     */
    public static void main(final String[] arg) {

        for (int i = 0; i < arg.length; i++) {
            try {
                process(arg[i]);
            } catch (FileNotFoundException e) {
                System.err.println(arg[i] + ": " + e.getMessage());
                System.exit(-1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    /**
     * ...
     *
     * @param name the file name to process
     *
     * @throws IOException in case of an error
     */
    private static void process(final String name) throws IOException {

        LineNumberReader reader = new LineNumberReader(new FileReader(name));
        String line;
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("<file name=\"[^\"]*[/\\\\]([^/\\\\]*)[/\\\\]src[/\\\\][a-zA-Z0-9_-]+([/\\\\].*)\"");

        while ((line = reader.readLine()) != null) {
            line = pattern.matcher(line).replaceFirst("<file name=\"$1$2\"");
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        FileWriter writer = new FileWriter(name);
        writer.write(sb.toString());
        writer.close();
    }

}
