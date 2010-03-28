/*
 * Copyright (C) 2006-2010 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tex.paragraph;

import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.junit.runner.JUnitCore;

/**
 * This is the test class for the TeX paragraph builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TeXParagraphBuiderTest extends AbstractParagraphBuiderTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(TeXParagraphBuiderTest.class);
    }

    /**
     * This method creates a new paragraph builder to be tested.
     * 
     * @return the new paragraph builder
     * 
     * @see org.extex.typesetter.paragraphBuilder.AbstractParagraphBuiderTester#getParagraphBuilder()
     */
    @Override
    protected ParagraphBuilder getParagraphBuilder() {

        return new TeXParagraphBuilder();
    }

    /**
     * This method provides an indicator whether or not the tracing should be
     * written to the console. This method is meant to be overwritten by derived
     * classes to change the default behavior.
     * 
     * @return <code>true</code> iff the tracing is requested
     * 
     * @see org.extex.typesetter.paragraphBuilder.AbstractParagraphBuiderTester#traceonline()
     */
    @Override
    protected boolean traceonline() {

        return true;
    }

}
