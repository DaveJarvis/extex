/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder;

import org.extex.sitebuilder.ant.SiteBuilderTaskTest;
import org.extex.sitebuilder.core.NewsBuilderTest;
import org.extex.sitebuilder.core.SiteBuilderTest;
import org.extex.sitebuilder.main.NewsBuilderMainTest;
import org.extex.sitebuilder.main.SiteBuilderMainTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the test suite for this directory.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({NewsBuilderTest.class, //
        NewsBuilderMainTest.class, //
        SiteBuilderMainTest.class, //
        SiteBuilderTest.class, //
        SiteBuilderTaskTest.class})
public class AllTests {

    // using annotations only

}
