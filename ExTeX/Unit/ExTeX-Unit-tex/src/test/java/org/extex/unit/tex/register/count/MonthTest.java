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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.extex.test.count.AbstractCountRegisterTester;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\month</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class MonthTest extends AbstractCountRegisterTester {

    /**
     * Provide the current month as default value for the comparison.
     * 
     * @return the current mont as string
     */
    private static String getDefaultValue() {

        return new SimpleDateFormat("M").format(new Date());
    }

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(MonthTest.class);
    }

    /**
     * Creates a new object.
     */
    public MonthTest() {

        super("month", "", getDefaultValue());
    }

}