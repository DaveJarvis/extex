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

package org.extex.doc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DocParser {

    /**
     * The field <tt>COMMENT_START</tt> contains the pattern to recognize the
     * start of a JavaDoc comment.
     */
    private static Pattern COMMENT_START = Pattern.compile(".*/\\*\\*.*");

    /**
     * The field <tt>COMMENT_END</tt> contains the pattern to recognize the
     * end of a JavaDoc comment.
     */
    private static Pattern COMMENT_END = Pattern.compile(".*\\*/.*");

    /**
     * The field <tt>DOC_START</tt> contains the pattern to recognize the
     * start of a doc section.
     */
    private static Pattern DOC_START = Pattern.compile(".*(<doc[ >].*)");

    /**
     * The field <tt>DOC_END</tt> contains the pattern to recognize the end of
     * a doc section.
     */
    private static Pattern DOC_END = Pattern.compile(".*</doc>.*");

    /**
     * The field <tt>PACKAGE</tt> contains the pattern to recognize a package
     * declaration.
     */
    private static Pattern PACKAGE = Pattern.compile("package ([^;]*);");

    /**
     * Creates a new object.
     */
    public DocParser() {

        super();
    }

    /**
     * Parse a file and collect a list of docs.
     * 
     * @param f the file to parse
     * 
     * @return the list of docs found
     * 
     * @throws IOException in case of an I/O error
     */
    public List<Doc> parse(File f) throws IOException {

        String pack = null;
        String type = f.getName().replaceAll("\\.java$", "");
        LineNumberReader reader =
                new LineNumberReader(new InputStreamReader(new FileInputStream(
                    f), "UTF-8"));
        try {
            Matcher m;
            List<Doc> result = new ArrayList<Doc>();

            for (String line = reader.readLine(); line != null; line =
                    reader.readLine()) {
                m = COMMENT_START.matcher(line);
                if (m.matches()) {
                    parseJavaDoc(reader, result, line, pack, type);
                }
                m = PACKAGE.matcher(line);
                if (m.matches()) {
                    pack = m.group(1);
                }
            }
            return result;

        } finally {
            reader.close();
        }
    }

    /**
     * Parse a single doc.
     * 
     * @param reader the reader to gather new lines from
     * @param l the line currently read
     * @param pack the package
     * 
     * @return the doc found
     * 
     * @throws IOException in case of an I/O error
     */
    private Doc parseDoc(LineNumberReader reader, String l, String pack)
            throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(l);

        for (String line = reader.readLine(); line != null; line =
                reader.readLine()) {
            if (DOC_END.matcher(line).matches()) {
                break;
            }
            sb.append('\n');
            sb.append(line.replaceFirst("[ ]*\\*[ ]?", ""));
        }

        int ix = sb.indexOf(">");
        CharSequence seq = sb.subSequence(4, ix);
        sb.delete(0, ix + 1);
        return Doc.get(seq, sb);
    }

    /**
     * Parse a complete JavaDoc section.
     * 
     * @param reader the reader to gather new lines from
     * @param result the list to append docs to
     * @param l the line currently read
     * @param pack the package
     * @param type the class or interface
     * 
     * @throws IOException in case of an I/O error
     */
    private void parseJavaDoc(LineNumberReader reader, List<Doc> result,
            String l, String pack, String type) throws IOException {

        List<Doc> docs = new ArrayList<Doc>();
        Matcher m;

        for (String line = l; line != null; line = reader.readLine()) {
            if (COMMENT_END.matcher(line).matches()) {
                break;
            }
            m = DOC_START.matcher(line);
            if (m.matches()) {
                docs.add(parseDoc(reader, m.group(1), pack));
            }
        }

        if (docs.size() == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String line = reader.readLine(); line != null; line =
                reader.readLine()) {
            int i = line.indexOf('{');
            if (i >= 0) {
                sb.append(line.subSequence(0, i));
                break;
            }
            sb.append(line);
        }

        String loc = pack + "." + type;
        int i = sb.indexOf("(");
        if (i >= 0) {
            sb.insert(i + 1, " ");
            sb.insert(i, " ");
            loc =
                    loc
                            + "#"
                            + parseMethod(sb.toString()
                                .replaceAll("\\)", " ) ")
                                .replaceAll(",", " , ").trim().split("\\s+"));
        }

        for (Doc d : docs) {
            d.setLocation(loc);
            result.add(d);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param a the array of tokens
     * 
     * @return the method description
     */
    private String parseMethod(String[] a) {

        StringBuilder s = new StringBuilder();
        int i = 1;
        while (!"(".equals(a[i])) {
            i++;
        }
        s.append(a[i - 1]);
        s.append('(');
        for (i++; i < a.length && !")".equals(a[i]); i++) {
            String ai = a[i + 1];
            if (!",".equals(ai) && !")".equals(ai)) {
                s.append(a[i]);
            }
        }
        s.append(')');

        return s.toString();
    }

}
