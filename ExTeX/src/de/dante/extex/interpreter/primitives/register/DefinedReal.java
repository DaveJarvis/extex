/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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
 *
 */
package de.dante.extex.interpreter.primitives.register;

import de.dante.extex.interpreter.TokenSource;
import de.dante.util.GeneralException;

/**
 * This class provides an implementation for a defined <code>\real</code>-register
 * with <code>\realdef</code>.
 * 
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class DefinedReal extends NamedReal {

	/**
	 * Creates a new object.
	 *
	 * @param name the name for debugging
	 */
	public DefinedReal(String name, String regname, long number) {
		super(name);
		this.number = number;
		this.regname = regname;
	}

	/**
	 * the number for the register
	 */
	private long number;

	/**
	 * the name of the register
	 */
	private String regname;

	/**
	 * Return the key (the number) for the register.
	 *
	 * @param source 	the tokensource
	 * @return Return the key for the register
	 * @throws GeneralException, if a error ocoured
	 */
	protected String getKey(TokenSource source) throws GeneralException {
		return regname + "#" + Long.toString(number);
	}
}
