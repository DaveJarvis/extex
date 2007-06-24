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

package org.extex;

import java.util.Properties;

import junit.framework.TestCase;

import org.extex.font.CoreFontFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.resource.ResourceFinder;

/**
 * ExTeX-TestRunner.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class ExTeXRunner extends TestCase {

    /**
     * myextex
     */
    private MyExTeX extex;

    /**
     * @throws Exception ...
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        extex = new MyExTeX(System.getProperties(), ".extex-test");

    }

    /**
     * inner ExTeX class.
     */
    public static class MyExTeX extends ExTeX {

        /**
         * Creates a new object and initializes the properties from given
         * properties and possibly from a user's properties in the file
         * <tt>.extex</tt>.
         * The user properties are loaded from the users home directory and the
         * current directory.
         *
         * @param theProperties the properties to consider
         * @param dotFile the name of the local configuration file. In the case
         *            that this value is <code>null</code> no user properties
         *            will be considered.
         *
         * @throws Exception in case of an error
         */
        public MyExTeX(Properties theProperties, String dotFile)
                throws Exception {

            super(theProperties, dotFile);
            makeConfig();
        }

        /**
         * Creates a new object and supplies some properties for those keys which
         * are not contained in the properties already.
         * A detailed list of the properties supported can be found in section
         * <a href="#settings">Settings</a>.
         *
         * @param theProperties the properties to start with. This object is
         *  used and modified. The caller should provide a new instance if this is
         *  not desirable.
         *
         * @throws Exception in case of an error
         */
        public MyExTeX(Properties theProperties) throws Exception {

            super(theProperties);
            makeConfig();
        }

        /**
         * the config
         */
        private Configuration config;

        /**
         * create the config
         */
        private void makeConfig() {

            config = ConfigurationFactory.newInstance("config/extex.xml");
        }

        /**
         * the finder
         */
        private ResourceFinder finder;

        /**
         * Returns the finder.
         * @return Returns the finder.
         */
        public ResourceFinder getResourceFinder() {

            if (finder == null) {
                finder =
                        makeResourceFinder(config.findConfiguration("Resource"));
            }
            return finder;
        }

        /**
         * the font factory
         */
        private CoreFontFactory fontFactory;

        /**
         * Returns the font factory.
         * @return Returns the font factory.
         */
        public CoreFontFactory getFontFactory() {

            if (fontFactory == null) {
                fontFactory =
                        makeFontFactory(config.getConfiguration("Fonts"),
                            getResourceFinder());
            }
            return fontFactory;
        }
    }
}
