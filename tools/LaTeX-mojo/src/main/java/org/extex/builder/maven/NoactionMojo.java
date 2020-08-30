/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.builder.maven;

/**
 * This plug-in is a L<span class="la">a</span><span class="t">T</span><span
 * class="e">e</span>X adapter for typesetting documentation. The goal tries to
 * analyze the input file to find the required programs and a minimal sequence
 * of commands to produce the desired output format. This information is logged.
 * No actions are performed. Thus no T<span class="e">e</span>X system is
 * required.
 * 
 * @goal noaction
 * @execute phase=compile
 * 
 * @since 1.0
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NoactionMojo extends LaTeXMojo {


    public NoactionMojo() {

        super(true);
    }

}
