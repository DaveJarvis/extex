/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.pl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Reader for a PL file.
 * 
 * TODO: incomplete
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*
 */
public class PlReader {

    /**
     * Container for a character.
     */
    public static class Chars {

        /**
         * The character.
         */
        private int ch = -1;

        /**
         * The map character.
         */
        private int mapch = -1;

        /**
         * Returns the ch.
         * 
         * @return Returns the ch.
         */
        public int getCh() {

            return ch;
        }

        /**
         * Returns the mapch.
         * 
         * @return Returns the mapch.
         */
        public int getMapch() {

            return mapch;
        }

        /**
         * The ch to set.
         * 
         * @param c The ch to set.
         */
        public void setCh(int c) {

            ch = c;
        }

        /**
         * The mapch to set.
         * 
         * @param c The mapch to set.
         */
        public void setMapch(int c) {

            mapch = c;
        }
    }

    /**
     * The command Character.
     */
    private class PlCharacter extends AbstractPlCommand {

    @Override
        public void execute(Reader reader) throws IOException {

            actual = new Chars();
            param = readParameter(reader);
            StringReader r = new StringReader(param);
            int ch = (int) readNumber(r);
            actual.setCh(ch);
            characters.put(new Integer(ch), actual);
            commandLoop(r);
            actual = null;
        }

    }

    /**
     * The command Map.
     */
    private class PlMap extends AbstractPlCommand {

    @Override
        public void execute(Reader reader) throws IOException {

            param = readParameter(reader);
            StringReader r = new StringReader(param);
            commandLoop(r);
        }
    }

    /**
     * The command NULL.
     */
    private class PlNULL extends AbstractPlCommand {

    @Override
        public void execute(Reader reader) throws IOException {

            param = readParameter(reader);
        }
    }

    /**
     * The command setchar.
     */
    private class PlSetChar extends AbstractPlCommand {

        /**
         * The character.
         */
        private int ch;

    @Override
        public void execute(Reader reader) throws IOException {

            param = readParameter(reader);
            StringReader r = new StringReader(param);
            ch = (int) readNumber(r);
            if (actual != null) {
                actual.setMapch(ch);
            }
        }

        /**
         * Returns the ch.
         * 
         * @return Returns the ch.
         */
        public int getCh() {

            return ch;
        }

    }

    /**
     * The command VTITLE.
     */
    private class PlVtitle extends AbstractPlCommand {

    @Override
        public void execute(Reader reader) throws IOException {

            param = readParameter(reader);
        }
    }

    /**
     * The actual character object.
     */
    private Chars actual = null;

    /**
     * The characters.
     */
    private final SortedMap<Integer, Chars> characters;

    /**
     * The commands.
     */
    private final Map<String, PlCommand> commands;

    /**
     * The pl commands.
     */
    private final Map<Object, Object> plcommands;

    /**
     * The pl null command.
     */
    private final PlCommand plnull = new PlNULL();

    /**
     * Create a new object.
     * 
     * @param file The file.
     * @throws IOException if an IO-error occurred.
     */
    public PlReader(File file) throws IOException {

        this(new FileInputStream(file));
    }

    /**
     * Create a new object.
     * 
     * @param in The input stream.
     * @throws IOException if an IO-error occurred.
     */
    public PlReader(InputStream in) throws IOException {

        commands = new HashMap<String, PlCommand>();
        plcommands = new HashMap<Object, Object>();
        characters = new TreeMap<Integer, Chars>();

        commands.put("VTITLE", new PlVtitle());
        commands.put("CHARACTER", new PlCharacter());
        commands.put("MAP", new PlMap());
        commands.put("SETCHAR", new PlSetChar());

        read(in);
    }

    /**
     * Create a new object.
     * 
     * @param file The file.
     * @throws IOException if an IO-error occurred.
     */
    public PlReader(String file) throws IOException {

        this(new File(file));
    }

    /**
     * Loop to read the commands.
     * 
     * @param reader The reader.
     * @throws IOException if an IO-error occurred.
     */
    private void commandLoop(Reader reader) throws IOException {

        String command;
        while ((command = readCommand(reader)) != null) {

            PlCommand cmd = commands.get(command);
            if (cmd != null) {
                cmd.execute(reader);
            } else {
                plnull.execute(reader);
            }
        }
    }

    /**
     * Returns the characters.
     * 
     * @return Returns the characters.
     */
    public SortedMap<Integer, Chars> getCharacters() {

        return characters;
    }

    /**
     * Returns the plcommands.
     * 
     * @return Returns the plcommands.
     */
    public Map<Object, Object> getPlcommands() {

        return plcommands;
    }

    /**
     * Read the file.
     * 
     * @param in The input.
     * @throws IOException if an IO-error occurred.
     */
    private void read(InputStream in) throws IOException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader( in,
                                                          StandardCharsets.US_ASCII ));

        commandLoop(reader);

        reader.close();
    }

    /**
     * Read a character from the reader (ignore cr and newline).
     * 
     * @param reader The reader.
     * @return Returns the character.
     * @throws IOException if an IO-error occurred.
     */
    private int readChar(Reader reader) throws IOException {

        int c;
        while ((c = reader.read()) != -1) {
            if (c != '\n' && c != '\r') {
                return c;
            }
            c = ' ';
        }

        return c;
    }

    /**
     * Read the command.
     * 
     * @param reader The reader.
     * @return Returns the command.
     * @throws IOException if an IO-error occurred.
     */
    private String readCommand(Reader reader) throws IOException {

        StringBuilder buf = new StringBuilder();

        boolean start = true;
        int c;
        while ((c = readChar(reader)) >= 0) {
            if (start && c == ' ') {
                // ignore space before the command
                continue;
            }
            start = false;
            if (c == '(') {
                start = false;
                continue;
            }
            if (c == ' ' || c == ')') {
                break;
            }
            buf.append((char) c);
        }
        String cmd = buf.toString().trim().toUpperCase();
        if (cmd.length() == 0) {
            return null;
        }
        return cmd;
    }

    /**
     * Read a number.
     * 
     * @param reader The reader.
     * @return Returns the number of -1 if not found.
     * @throws IOException if an IO-error occurred.s
     */
    private float readNumber(Reader reader) throws IOException {

        float f = -1.0f;
        String type = readCommand(reader);
        String value = readCommand(reader);

        if (type.equals("O")) {
            // octal
            f = Integer.parseInt(value, 8);
        } else if (type.equals("C")) {
            // char
            f = value.charAt(0);
        } else if (type.equals("R")) {
            // real
            f = Float.parseFloat(value);
        }

        return f;
    }

    /**
     * Read the parameter.
     * 
     * Read until ')' and brace level is zero.
     * 
     * @param reader The reader.
     * @return Returns the parameter.
     * @throws IOException if an IO-error occurred.
     */
    private String readParameter(Reader reader) throws IOException {

        StringBuilder buf = new StringBuilder();

        int level = 0;
        int c;
        while ((c = readChar(reader)) != -1) {
            if (c == '(') {
                level++;
            } else if (c == ')' && level > 0) {
                level--;
            } else if (c == ')' && level == 0) {
                break;
            }
            buf.append((char) c);
        }
        return buf.toString().trim();
    }

}
