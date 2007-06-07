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

package org.extex.ant.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5413 $
 */
public class TuneAntLogTask extends Task {

    /**
     * The field <tt>file</tt> contains the nae of the file to be processed.
     */
    private String file = null;

    /**
     * Perform the actions.
     *
     * @throws BuildException in case of an error
     *
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        if (file == null) {
            throw new BuildException("missing file attribute");
        }
        File f = new File(file);
        if (!f.isFile()) {
            throw new BuildException("'" + file + "' is not a plain file.");
        }
        if (!f.canRead()) {
            throw new BuildException("file '" + file + "' not readable.");
        }

        char[] buffer = new char[(int) f.length()];
        try {
            Reader stream = new FileReader(file);
            stream.read(buffer);
            stream.close();
            StringBuffer sb = new StringBuffer();
            sb.append(buffer);
            int i = sb.indexOf("<build ");
            if (i >= 0) {
                Locale.setDefault(Locale.ENGLISH);
                String time =
                        DateFormat.getDateTimeInstance(DateFormat.LONG,
                            DateFormat.SHORT).format(new Date());
                sb.insert(i + 7, "date=\"" + time + "\" ");

                PrintStream out = new PrintStream(new FileOutputStream(file));
                out.print(sb.toString());
                out.close();
            }
        } catch (IOException e) {
            throw new BuildException(e.toString());
        }
    }

    /**
     * Getter for file.
     *
     * @return the file
     */
    public String getFile() {

        return file;
    }

    /**
     * Setter for file.
     *
     * @param file the file to set
     */
    public void setFile(String file) {

        this.file = file;
    }

}
