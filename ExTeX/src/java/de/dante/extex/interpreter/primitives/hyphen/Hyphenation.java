/*
 * Copyright (C) 2004 Michael Niedermair
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
package de.dante.extex.interpreter.primitives.hyphen;

import com.ibm.icu.util.StringTokenizer;

import de.dante.extex.hyphenation.HyphenationTable;
import de.dante.extex.interpreter.AbstractCode;
import de.dante.extex.interpreter.Flags;
import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.type.Count;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;
import de.dante.util.UnicodeChar;

/**
 * This class provides an implementation for the primitive <code>\hyphenation</code>.
 * <p>
 * Example:
 * 
 * <pre>
 *  \hyphenation{as-so-ciate as-so-ciates }
 * </pre>
 * 
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Hyphenation extends AbstractCode {

	/**
	 * Creates a new object.
	 * 
	 * @param name the name for debugging
	 */
	public Hyphenation(String name) {
		super(name);
	}

	/**
	 * Scan for hyphenation-values and store this values
	 * in the <code>HyphernationTable</code>. 
	 * The <code>HyphernationTable</code> are based on the
	 * value from <code>\language</code>.
	 *  
	 * @see de.dante.extex.interpreter.Code#execute(de.dante.extex.interpreter.Flags,
	 *      de.dante.extex.interpreter.context.Context,
	 *      de.dante.extex.interpreter.TokenSource,
	 *      de.dante.extex.typesetter.Typesetter)
	 */
	public void execute(Flags prefix, Context context, TokenSource source, Typesetter typesetter) throws GeneralException {
		
		String hypenation = source.scanTokensAsString();
		Count language = context.getCount("language");
		HyphenationTable ht = context.getHyphenationTable((int) language.getValue());

		StringTokenizer st = new StringTokenizer(hypenation);
		while (st.hasMoreTokens()) {
			String pat = st.nextToken().trim();
			ht.addHyphenation(createHyphenation(pat, context), pat);
		}
	}
	
	/**
	 * Cretate the name for the <code>HyphenationTable</code>.
	 * <p>
	 * All digits ([0-9]) from the <code>String</code> are removed.
	 * 
	 * @param pattern	the pattern
	 * @param context	the context
	 * @return	the name
	 */
	private String createHyphenation(String pattern, Context context) {
		String rt = "";
		String hyphenchar = new UnicodeChar((int)context.getCount("defaulthyphenchar").getValue()).toString();
		if (pattern != null) {
			rt = makeLowercase(pattern.replaceAll(hyphenchar, ""), context);
		}
		return rt;
	}

	/**
	 * Transform the <code>String</code> in lowercase (use lccode)
	 * @param s			the <code>String</code>
	 * @param context	the context
	 * @return the lowercase string
	 */
	private String makeLowercase(String s, Context context) {
		//		for (int i=0; i< s.length(); i++) {
		//			UnicodeChar uc = new UnicodeChar(s,i);
		//			int lc = context.get
		//		}
		return s.toLowerCase(); //TODO change toLowerCase to lccode
	}
}
