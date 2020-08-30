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

package org.extex.font.format.dvi.command;

import org.extex.font.exception.FontException;

/**
 * Interface for a DVI command to execute.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public interface DviExecuteCommand {

    /**
     * execute DviChar.
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviChar command) throws FontException;

    /**
     * execute DviBOP
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviBOP command) throws FontException;

    /**
     * execute DviDown
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviDown command) throws FontException;

    /**
     * execute DviEOP
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviEOP command) throws FontException;

    /**
     * execute DviFntDef
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviFntDef command) throws FontException;

    /**
     * execute DviFntNum
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviFntNum command) throws FontException;

    /**
     * execute DviPOP
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviPOP command) throws FontException;

    /**
     * execute DviNOP
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviNOP command) throws FontException;

    /**
     * execute DviPost
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviPost command) throws FontException;

    /**
     * execute DviPostPost
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviPostPost command) throws FontException;

    /**
     * execute DviPre
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviPre command) throws FontException;

    /**
     * execute DviPUSH
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviPush command) throws FontException;

    /**
     * execute DviRight
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviRight command) throws FontException;

    /**
     * execute DviRule
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviRule command) throws FontException;

    /**
     * execute DviW
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviW command) throws FontException;

    /**
     * execute DviX
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviX command) throws FontException;

    /**
     * execute DviXXX
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviXXX command) throws FontException;

    /**
     * execute DviY
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviY command) throws FontException;

    /**
     * execute DviZ
     * 
     * @param command the command
     * @throws FontException if a font-error occurs.
     */
    void execute(DviZ command) throws FontException;
}
