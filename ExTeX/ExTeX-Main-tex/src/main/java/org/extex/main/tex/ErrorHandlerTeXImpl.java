/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
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

package org.extex.main.tex;

import org.extex.core.Locator;

/**
 * This is the error handler in <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> compatibility mode: the message is presented in a
 * <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo>-compatible way. The output is written out via the Logger.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ErrorHandlerTeXImpl extends ErrorHandlerImpl {

    /**
     * Creates a new object.
     */
    public ErrorHandlerTeXImpl() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.main.tex.ErrorHandlerImpl#showErrorLine(java.lang.String,
     *      org.extex.core.Locator)
     */
    @Override
    protected void showErrorLine(String message, Locator locator) {

        StringBuilder sb = new StringBuilder();
        String file = locator.getResourceName();
        String line = locator.getLine();
        int pointer = locator.getLinePointer();
        int lineNumber = locator.getLineNumber();
        file = (file == null ? "<>" : "<" + file + ">");

        sb.append(NL);
        sb.append('!');
        sb.append(message);
        sb.append(NL);
        sb.append(file);
        sb.append(NL);
        sb.append(line.substring(0, pointer - 1));
        sb.append(NL);
        for (int i = pointer + file.length(); i > 0; i--) {
            sb.append(' ');
        }
        sb.append(line.substring(pointer));
        sb.append(NL);
        sb.append("l.");
        sb.append(lineNumber >= 0 ? Integer.toString(lineNumber) : "?");
        sb.append(NL);

        getLogger().severe(sb.toString());
    }

}
