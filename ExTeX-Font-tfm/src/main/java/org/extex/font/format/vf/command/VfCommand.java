/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.vf.command;

import java.io.IOException;

import org.extex.font.exception.FontException;
import org.extex.framework.i18n.Localizer;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Abstract class for all vf commands.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class VfCommand implements XMLWriterConvertible {

    /**
     * pre
     */
    public static final int PRE = 247;

    /**
     * fnt def 1
     */
    public static final int FNT_DEF_1 = 243;

    /**
     * fnt def 2
     */
    public static final int FNT_DEF_2 = 244;

    /**
     * fnt def 3
     */
    public static final int FNT_DEF_3 = 245;

    /**
     * fnt def 4
     */
    public static final int FNT_DEF_4 = 246;

    /**
     * minimun character nr
     */
    public static final int MIN_CHARACTER = 0;

    /**
     * maximum character nr
     */
    public static final int MAX_CHARACTER = 242;

    /**
     * post
     */
    public static final int POST = 248;

    /**
     * Shift 8
     */
    public static final int SHIFT8 = 8;

    /**
     * Shift 16
     */
    public static final int SHIFT16 = 16;

    /**
     * the command code
     */
    private int ccode;

    /**
     * The localizer for the messages.
     */
    private Localizer localizer;

    /**
     * Create a new object. (only from subclasses)
     * 
     * @param c the command code
     */
    VfCommand(Localizer localizer, final int c) {

        this.localizer = localizer;
        ccode = c;
    }

    /**
     * Return the new instance of the command. The command read itself the data
     * from the input.
     * 
     * @param localizer The localizer for the messages.
     * @param rar the input.
     * @return Returns the new instance, or <code>null</code>, if no more
     *         data exists.
     * @throws FontException if a font-error occurs
     */
    public static VfCommand getInstance(Localizer localizer, RandomAccessR rar)
            throws FontException {

        try {
            // EOF ?
            if (rar.getPointer() >= rar.length()) {
                return null;
            }
            int c = rar.readByteAsInt();

            // characters
            if (c >= MIN_CHARACTER && c <= MAX_CHARACTER) {
                return new VfCommandCharacterPackets(localizer, rar, c);
            }
            switch (c) {
                case PRE:
                    return new VfCommandPre(localizer, rar, c);
                case FNT_DEF_1:
                case FNT_DEF_2:
                case FNT_DEF_3:
                case FNT_DEF_4:
                    return new VfCommandFontDef(localizer, rar, c);
                case POST:
                    return new VfCommandPost(localizer, rar, c);
                default:
                    throw new FontException(localizer.format("VF.WrongCode",
                        String.valueOf(c)));
            }
        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }
    }

    /**
     * Returns the type of the command.
     * 
     * @return Returns the type of the command.
     */
    public int getCommandCode() {

        return ccode;
    }

    /**
     * Getter for localizer.
     * 
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return localizer;
    }
}
