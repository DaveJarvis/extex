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

package org.extex.unit.tex.register.count;

import java.util.Calendar;

import org.extex.interpreter.primitives.register.count.AbstractCountRegisterTester;

/**
 * This is a test suite for the primitive <tt>\time</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class TimeTest extends AbstractCountRegisterTester {

    /**
     * The constant <tt>MINUTES_PER_HOUR</tt> contains the number of minutes per
     * hour.
     */
    private static final int MINUTES_PER_HOUR = 60;

    /**
     * Provide the current time as default value for the comparison.
     *
     * @return the current time as string
     */
    private static String getDefaultValue() {

        Calendar cal = Calendar.getInstance();
        return java.lang.Integer.toString(cal.get(Calendar.HOUR_OF_DAY)
                * MINUTES_PER_HOUR + cal.get(Calendar.MINUTE));
    }

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(TimeTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public TimeTest(String arg) {

        super(arg, "time", "", getDefaultValue());
    }

}